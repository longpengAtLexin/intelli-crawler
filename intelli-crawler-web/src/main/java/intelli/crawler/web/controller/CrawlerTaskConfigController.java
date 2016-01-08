package intelli.crawler.web.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import intelli.crawler.common.comm.CrawlResult.CrawlerStatus;
import intelli.crawler.common.comm.RpcResponse;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PagingConfig;
import intelli.crawler.common.dao.CrawlerTaskInfoDao;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.service.CrawlerTaskService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

@Controller
public class CrawlerTaskConfigController 
{
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	private CrawlerTaskInfoDao dao = IocManager.getInstance().getDao("crawlerTaskInfoDao");
	
	private  ApplicationContext appContext;
	@ResponseBody
	@RequestMapping("/saveCrawlerConfigInfo")
	public Map<String,Object> saveCrawlerConfigInfo(HttpServletRequest request ) 
	{
		Map<String,Object> resp = new HashMap<String, Object>();
		String crawlerTaskName = null;
		try
		{
			String configInfo = request.getParameter("configInfo");
			log.info("保存任务配置信息:{}" ,configInfo);
		
			CrawlerTaskConfig configObj = JSON.parseObject(configInfo, CrawlerTaskConfig.class);
			crawlerTaskName = configObj.getName();
			CrawlerTaskInfoPo po = new CrawlerTaskInfoPo();
			
			String id = UUID.randomUUID().toString();
			String pid = UUID.randomUUID().toString();
			
			configObj.setId(id);
			configObj.setPid(pid);
			
			po.setId(id);
			po.setPid(pid);
			po.setName(configObj.getName());
			po.setOutputDB(configObj.getOutputDB());
			po.setConfstr(JSON.toJSONString(configObj));
			po.setStatus(CrawlerStatus.NEW.name());
			po.setCreateTime(new Date());
			po.setModifyTime(new Date());
			dao.insert(po);
			
			resp.put("result", true);
			
		}catch(SQLException e)
		{
			log.info("保存 爬虫任务 :{} 失败,error: {}" ,crawlerTaskName,e);
			resp.put("result", false);
			resp.put("error", e.getMessage());
		}catch(Exception e)
		{
			log.info("保存 爬虫任务 :{} 失败,error: {}" ,crawlerTaskName,e);
			resp.put("result", false);
			resp.put("error", e.getMessage());
		}
		
		return resp;
	}
	
	/**
	 * 修改爬虫任务配置;
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editCrawlerConfigInfo")
	public Map<String,Object> editCrawlerConfigInfo(HttpServletRequest request ) 
	{
		String name = null;
		Map<String,Object> resp = new HashMap<String, Object>();
		
		int crawlDepth =  0;
		if(!StringUtils.isEmpty(request.getParameter("crawlDepth")))
			crawlDepth =  Integer.parseInt(request.getParameter("crawlDepth"))  ;
		
		int initPagingNum =  0;
		if(!StringUtils.isEmpty(request.getParameter("initPagingNum")))
			initPagingNum =  Integer.parseInt(request.getParameter("initPagingNum"))  ;
		
		int isPaging = 0;
		if(!StringUtils.isEmpty(request.getParameter("isPaging")))
			isPaging =  Integer.parseInt(request.getParameter("isPaging"))  ;
		
		name = request.getParameter("name");
		
		int pagingNum =  0;
		if(!StringUtils.isEmpty(request.getParameter("pagingNum")))
			pagingNum =  Integer.parseInt(request.getParameter("pagingNum")) ;
		
		int stepSize =  0;
		if(!StringUtils.isEmpty(request.getParameter("stepSize")))
			stepSize =  Integer.parseInt(request.getParameter("stepSize")) ;
		
		int threadNum =  0;
		if(!StringUtils.isEmpty(request.getParameter("threadNum")))
			threadNum =  Integer.parseInt(request.getParameter("threadNum")) ;
		
		String outputDB = request.getParameter("outputDB");
		String pagingSeedUrl = request.getParameter("pagingSeedUrl");
		String pagingUrlPattern = request.getParameter("pagingUrlPattern");
		
		String taskId = null;
		try
		{
			String configInfo = request.getParameter("confstr");
			CrawlerTaskConfig configObj = JSON.parseObject(configInfo, CrawlerTaskConfig.class);
			
			configObj.setCrawlDepth(crawlDepth);
			configObj.setName(name);
			configObj.setStatus(CrawlerStatus.NEW.getValue());
			configObj.setThreadNum(threadNum);
			configObj.setOutputDB(outputDB);
			
			PagingConfig pagingConfig = null;
			if(isPaging==1)
			{
				pagingConfig = new PagingConfig();
				pagingConfig.setInitPagingNum(initPagingNum);
				pagingConfig.setIsPaging((short)isPaging);
				pagingConfig.setPagingNum((short) pagingNum);
				pagingConfig.setPagingSeedUrl(pagingSeedUrl);
				pagingConfig.setPagingUrlPattern(pagingUrlPattern);
				pagingConfig.setStepSize((short)stepSize);
			}
			
			configObj.setPagingConfig(pagingConfig);
			taskId = configObj.getId();
			CrawlerTaskInfoPo po = dao.selectByPrimaryKey(taskId);
			po.setConfstr(  JSON.toJSONString(configObj));
			po.setModifyTime(new Date());
			po.setName(name);
			po.setOutputDB(outputDB);
			po.setStatus(CrawlerStatus.NEW.toString());  // 状态置为 NEW;
			dao.updateByPrimaryKey(po);
			
			CrawlerTaskService  crawlerTaskService = appContext.getBean("crawlerTaskService" ,CrawlerTaskService.class);  
			RpcResponse rpcResp = crawlerTaskService.refresh(taskId); //刷新后台进程的任务配置;
			
			Throwable throwable = rpcResp.getThrowable();
			if(throwable != null)
			{
				log.warn("刷新后台爬虫任务Id:{}配置出错, error:{}, throwable:{}", taskId,rpcResp.getErrInfo().getDesc() ,throwable);
			}else
				log.info("刷新后台爬虫任务Id:{}配置成功, error:{}.", taskId,rpcResp.getErrInfo().getDesc() );
			resp.put("result", true);
			
		} catch (Exception e) 
		{
			log.info("更新 爬虫任务 :{} 失败,error: {}" ,name,e);
			//resp.put("result", false);
			resp.put("error", e.getMessage());
		}
		
		
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/deleteCrawlerConfigInfo")
	public Map<String,Object> deleteCrawlerConfigInfo(HttpServletRequest request ) 
	{
		Map<String,Object> resp = new HashMap<String, Object>();
		try
		{
			String ids = request.getParameter("ids");
			List<String> idLst = JSON.parseArray(ids,String.class);
		
			for(String id :idLst)
			{
				dao.deleteByPrimaryKey(id);
			}
			resp.put("success", true);
			
		}catch(SQLException e)
		{
			resp.put("success", false);
			resp.put("error", e.getMessage());
		}catch(Exception e)
		{
			resp.put("success", false);
			resp.put("error", e.getMessage());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/startCrawlerConfigInfos")
	public Map<String,Object> startCrawlerTasks(HttpServletRequest request ) 
	{
		ServletContext  scontext = request.getSession().getServletContext();
		appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(scontext); 
		
		CrawlerTaskService  crawlerTaskService = appContext.getBean("crawlerTaskService" ,CrawlerTaskService.class);  
		Map<String,Object> resp = new HashMap<String, Object>();
		try
		{
			String ids = request.getParameter("ids");
			List<String> idLst = JSON.parseArray(ids,String.class);
		
			for(String id :idLst)
			{
				 crawlerTaskService.start(id);
			}
			resp.put("success", true);
			
		}catch(Exception e)
		{
			resp.put("success", false);
			resp.put("error", e.getMessage());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/startCrawlerConfigInfo")
	public Map<String,Object> startCrawlerTask(HttpServletRequest request ) 
	{
		ServletContext  scontext = request.getSession().getServletContext();
		appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(scontext); 
		
		CrawlerTaskService  crawlerTaskService = appContext.getBean("crawlerTaskService" ,CrawlerTaskService.class);  
		Map<String,Object> resp = new HashMap<String, Object>();
		long startTime = System.currentTimeMillis();
		String id = request.getParameter("id");
		log.info("爬虫任务:{}开始启动.",id);
		try
		{
			RpcResponse rpcResp = crawlerTaskService.start(id);
			
			if(rpcResp.getErrInfo().getCode()==0)
			{
				resp.put("success", true);
				resp.put("crawlerResult", rpcResp.getResult());
				
			}else
			{
				resp.put("success", false);
				resp.put("error", rpcResp.getErrInfo().getDesc());
			}
			log.info("爬虫任务:{} 结果:{}.",id, JSON.toJSONString(rpcResp));
			
		}catch(Exception e)
		{
			resp.put("success", false);
			resp.put("error", e.getMessage());
			log.info("爬虫任务:{}爬取失败:{}.",id ,e);
		}
		long endTime = System.currentTimeMillis();
		log.info("爬虫任务:{}启动耗时:{}.",id, (endTime -startTime)/1000);
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/stopCrawlerConfigInfo")
	public Map<String,Object> stopCrawlerTask(HttpServletRequest request ) 
	{
		ServletContext  scontext = request.getSession().getServletContext();
		appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(scontext); 
		
		CrawlerTaskService  crawlerTaskService = appContext.getBean("crawlerTaskService" ,CrawlerTaskService.class);  
		Map<String,Object> resp = new HashMap<String, Object>();
		long startTime = System.currentTimeMillis();
		String id = request.getParameter("id");
		log.info("停止爬虫任务:{}",id);
		try
		{
			RpcResponse rpcRes = crawlerTaskService.stop(id);
			if(rpcRes.getErrInfo().getCode()==0)
			{
				resp.put("success", true);
			}else
			{
				resp.put("success", false);
				resp.put("error", rpcRes.getErrInfo().getDesc());
			}
		}catch(Exception e)
		{
			resp.put("success", false);
			resp.put("error", e.getMessage());
		}
		long endTime = System.currentTimeMillis();
		log.info("爬虫任务:{}停止耗时:{}.",id, (endTime -startTime)/1000);
		return resp;
	}
	
}
