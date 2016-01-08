package intelli.crawler.worker.crawlers;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PagingConfigOld;
import intelli.crawler.worker.aop.BeforeProcessor;
import intelli.crawler.worker.extractors.PagingExtractor;



/**
 * 分页爬虫执行;
 * 注意，目前主要有两种方式;
 * <ol>
 * <li>{@link PagingConfigOld#ParametricPagingType}</li>
 * <li>{@link PagingConfigOld#RestfulPagingType}</li>
 * 
 * </ol>
 * 
 * @author penglong;
 *
 */
public class PagingCrawlerExecutorOld  extends AbstractCrawlerExecutor
{
	
	public static final Logger LOG = LoggerFactory.getLogger(PagingCrawlerExecutorOld.class);
	
	private  PagingConfigOld pagingConfig;

	private static final String Slashes_CHAR ="/";
	
	private static final String AND_CHAR ="&";
	
	private static final String Equal_CHAR ="=";
	
	public  PagingCrawlerExecutorOld(CrawlerTaskConfig taskInfo) 
	{
		super(taskInfo);
		
	}
	public  PagingCrawlerExecutorOld(CrawlerTaskConfig taskInfo ,List<BeforeProcessor> processors ) 
	{
		super(taskInfo, processors);
	}
	@Override
	protected void initExtractorInfo() 
	{
		//初始化抽取参数;
		
		
		List<CommonTable>  tables = taskConfig.getTables(); // 一个页面需抽取多个表信息;
		
		for(CommonTable table :tables)
		{
			 ExtractorParams params = new ExtractorParams();
			 
			 params.put(table.getTablename(), table); //待抽取数据的字段信息;
		     params.put("extractUrlParttern", table.getExtractUrlPattern() ); // 待抽取页面的url格式;
		     params.put("crawlerTaskId", taskConfig.getId()); //爬虫任务ID;
		     params.put("outputDB", taskConfig.getOutputDB()); //输出数据库类型;
		     
		     params.put(taskConfig.getPagingConfig().getClass().getName(), taskConfig.getPagingConfig());
		     crawler.addExtractor(table.getExtractUrlPattern() , PagingExtractor.class,params);
		    // this.pagingConfig = taskConfig.getPagingConfig();
		     getAndSetPagingSeedUrls();
		}
		
	}	
	
	private void getAndSetPagingSeedUrls()
	{
		String currentSeedUrl = pagingConfig.getPagingSeedUrl();
		crawler.addSeed(currentSeedUrl);
		final AtomicInteger pagingCounter = new AtomicInteger(0);
		
		switch (pagingConfig.getPagingType())
		{
			case PagingConfigOld.ParametricPagingType:
				getAndSetSeedUrlsForParametric(currentSeedUrl, pagingCounter);
				break;
			case PagingConfigOld.RestfulPagingType:
				getAndSetSeedUrlsForRestful(currentSeedUrl, pagingCounter);
				break;
		}
		
		LOG.info( "分页爬虫执行者【"+super.executorName+"】的seedUrls:{}",JSON.toJSONString(super.crawler.getSeeds()));
		 
	}
	
	private void getAndSetSeedUrlsForParametric(String currentSeedUrl,AtomicInteger pagingCounter)
	{
		 int startIndex = currentSeedUrl.indexOf(AND_CHAR +pagingConfig.getPagingName()+Equal_CHAR);
		 if(startIndex<0) 
			 return ;
		 int endIndex = currentSeedUrl.indexOf(AND_CHAR, startIndex+pagingConfig.getPagingName().length()+2);
		 try
		 {
			 int currentPagingValue = Integer.parseInt(currentSeedUrl.substring(startIndex+pagingConfig.getPagingName().length()+2, endIndex)) ;
			 pagingCounter.set(currentPagingValue);
			 int nextPagingValue = currentPagingValue;
			 String nextPageUrl = null;
			 for(int i=0;i<pagingConfig.getPagingNum();i++)
			 {
				 nextPagingValue = pagingCounter.addAndGet(pagingConfig.getStepSize());
				 nextPageUrl = currentSeedUrl.replace(AND_CHAR+pagingConfig.getPagingName()+Equal_CHAR+currentPagingValue+AND_CHAR,
						 AND_CHAR+pagingConfig.getPagingName()+Equal_CHAR+nextPagingValue+AND_CHAR);
				 crawler.addSeed(nextPageUrl);
			 }
		 }catch(Exception e)
		 {
			 LOG.warn("获取当前页面的url【"+currentSeedUrl+"】的分页seedUrl失败:",e);
		 }
	}
	
	private void getAndSetSeedUrlsForRestful(String currentSeedUrl,AtomicInteger pagingCounter)
	{
		int startIndex = currentSeedUrl.indexOf(Slashes_CHAR +pagingConfig.getPagingName());
		 if(startIndex<0) 
			 return ;
		 int endIndex = currentSeedUrl.indexOf(Slashes_CHAR, startIndex+pagingConfig.getPagingName().length());
		 try
		 {
			 int currentPagingValue = Integer.parseInt(currentSeedUrl.substring(startIndex+pagingConfig.getPagingName().length()+1, endIndex)) ;
			 pagingCounter.set(currentPagingValue);
			 int nextPagingValue = currentPagingValue;
			 String nextPageUrl = null;
			 for(int i=0;i<pagingConfig.getPagingNum();i++)
			 {
				  nextPagingValue = pagingCounter.addAndGet(pagingConfig.getStepSize());
				  nextPageUrl = currentSeedUrl.replace(Slashes_CHAR+pagingConfig.getPagingName()+currentPagingValue+Slashes_CHAR,
						 Slashes_CHAR+pagingConfig.getPagingName()+nextPagingValue+Slashes_CHAR);
				  crawler.addSeed(nextPageUrl);
			 }
		}catch(Exception e)
		 {
			 LOG.warn("获取当前页面的url【"+currentSeedUrl+"】的分页seedUrl失败:",e);
		 }
	}

}
