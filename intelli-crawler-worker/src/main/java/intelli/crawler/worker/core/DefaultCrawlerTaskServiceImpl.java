package intelli.crawler.worker.core;


import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import intelli.crawler.common.comm.CrawlResult;
import intelli.crawler.common.comm.ErrorCode;
import intelli.crawler.common.comm.RpcResponse;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.MysqlCommonTableDao;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.service.CrawlerTaskService;
import intelli.crawler.worker.crawlers.AbstractCrawlerExecutor;


/**
 * 	爬虫任务服务实现;
 * @author penglong
 *
 */
@Service
public class DefaultCrawlerTaskServiceImpl  implements  CrawlerTaskService
{
	private static Logger LOG = LoggerFactory.getLogger(MysqlCommonTableDao.class);
	
	/**
	 * <爬虫任务Id-任务执行者> 映射;
	 */
	 public static  Map<String, AbstractCrawlerExecutor> crawlerMap =Collections.synchronizedMap(new HashMap<String, AbstractCrawlerExecutor>());
	 
	 /**
	  * <爬虫任务Id-已抽取条数> 映射;
	 */
	 public  static Map<String, AtomicInteger> crawlerRecordCounterMap =Collections.synchronizedMap(new HashMap<String, AtomicInteger>());
	
	 private Object lock = new Object();
	 
	 /**
	  * 爬虫配置信息 dao
	  */
	 private intelli.crawler.common.dao.CrawlerTaskInfoDao crawlerTaskInfoDao = IocManager.getInstance().getDao("crawlerTaskInfoDao");
	 
	 /**
		 * 启动爬虫任务;
		 * @param taskId  任务Id;
		 * @return
		 */
	public RpcResponse start(String taskId) 
	{
		
		/*// 测试并发!
		// 经过测试，one request per thread !
		System.out.println( "当前线程 ："  +Thread.currentThread().getName() +" ***" + Thread.currentThread().getId());

		System.out.println("接受请求时间 ：" +System.currentTimeMillis() );
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e)
		 {
			e.printStackTrace();
		}*/
		
		RpcResponse resp = new RpcResponse();
		AbstractCrawlerExecutor crawlerExecutor = null;
		synchronized (lock) 
		{
			crawlerExecutor = crawlerMap.get(taskId);
			if(crawlerExecutor==null)
			{
				CrawlerTaskConfig configInfo = null;
				try 
				{
					configInfo = this.getCrawlerTaskConfigInfo(taskId);
				} catch (SQLException e)
				{
					LOG.warn("查找爬虫任务配置信息出错,Id:{}  配置信息为空！",taskId,e);
					resp.setThrowable(e);
					resp.setErrInfo(ErrorCode.SqlError);
					return resp;
				}
				if(configInfo==null)
				{
					LOG.warn("爬虫任务,Id:{}  配置信息为空！",taskId);
					resp.setErrInfo(ErrorCode.EmptyTaskConfig);
					return resp;
				}
				crawlerExecutor = CrawlerExecutorFactory.getInstance().createCrawlerExecutor(configInfo);
				crawlerMap.put(taskId, crawlerExecutor);
				crawlerRecordCounterMap.put(taskId, new AtomicInteger(0));
			}
		}
		try
		{
			LOG.info("尝试启动爬虫任务,Id:{} ",taskId);
			CrawlResult result = crawlerExecutor.start();
			resp.setResult(result); //爬虫结果设置;
		}catch(Throwable e)
		{
			
			resp.setThrowable(e);
			resp.setErrInfo(ErrorCode.LaunchError);
			LOG.info("启动爬虫任务,Id:{} ,异常:",taskId,e);
			return resp;
		}
		
		resp.setErrInfo(ErrorCode.Success);	
		return resp;
	}

	/**
	 * 停止爬虫任务;
	 * @param taskId 任务Id;
	 * @return
	 */
	public RpcResponse stop(String taskId) 
	{
		RpcResponse resp = new RpcResponse();
		try 
		{
			AbstractCrawlerExecutor crawlerExecutor = crawlerMap.get(taskId);
			crawlerExecutor.stop();
			resp.setErrInfo(ErrorCode.Success);	
		} catch (Throwable e)
		{
			LOG.info("尝试停止爬虫任务异常,Id:{} ，,e:{}",taskId,e);
			resp.setErrInfo(ErrorCode.StopError);	
			resp.setThrowable(e);
		}
		return resp;
	}

	/**
	 * 暂停任务;
	 * @param taskId
	 * @return
	 */
	public RpcResponse pause(String taskId)
	{
		
		return null;
	}

	public RpcResponse resume(String taskId)
	{
		return null;
	}
	
	/**
	 * 恢复任务;
	 * </br>
	 * 主要针对 pause(String taskId)后的恢复;
	 * @param taskId
	 * @return
	 */
	private CrawlerTaskConfig getCrawlerTaskConfigInfo(String taskId) throws SQLException
	{
		CrawlerTaskConfig configInfo = null;
		try 
		{
			CrawlerTaskInfoPo info = crawlerTaskInfoDao.selectByPrimaryKey(taskId);
			if(info!= null)
			{
				String configStr = info.getConfstr();
				if(!StringUtils.isEmpty(configStr));
					configInfo = JSON.parseObject(configStr, CrawlerTaskConfig.class);
					if(StringUtils.isEmpty(configInfo.getId()))
						configInfo.setId(info.getId());
			}
		} catch (SQLException e)
		{
			LOG.info("获取爬虫任务信息失败:{}",e);
			throw e;
		}
		return configInfo;
		
	}

	/**
	 * @param taskId 任务Id;
	 * 刷新任务，针对前段页面修改后，对爬虫任务刷新.
	 * 
	 * @return 刷新结果响应;
	 */
	@Override
	public RpcResponse refresh(String taskId) 
	{
		RpcResponse resp = new RpcResponse();
		
		synchronized (DefaultCrawlerTaskServiceImpl.class) 
		{
			AbstractCrawlerExecutor crawlerExecutor = crawlerMap.get(taskId);
			if(crawlerExecutor ==null)
			{
				resp.setErrInfo(ErrorCode.RefreshError);
				resp.setThrowable(new IllegalStateException("crawlerMap缓存中无当前任务:【"+taskId+"】信息!"));
				
			}else
			{
				CrawlerTaskConfig configInfo = null;
				try 
				{
					configInfo = this.getCrawlerTaskConfigInfo(taskId);
				} catch (SQLException e)
				{
					LOG.warn("查找爬虫任务配置信息出错,Id:{}  配置信息为空！",taskId,e);
					resp.setThrowable(e);
					resp.setErrInfo(ErrorCode.SqlError);
					return resp;
				}
				if(configInfo==null)
				{
					LOG.warn("爬虫任务,Id:{}  配置信息为空！",taskId);
					resp.setErrInfo(ErrorCode.EmptyTaskConfig);
					return resp;
				}
				crawlerExecutor = CrawlerExecutorFactory.getInstance().createCrawlerExecutor(configInfo);
				crawlerMap.put(taskId, crawlerExecutor);
				resp.setErrInfo(ErrorCode.RefreshSuccess);  // 刷新成功！
				
			}
		}
		
		return resp;
	}

}
