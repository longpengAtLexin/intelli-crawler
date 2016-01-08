package intelli.crawler.worker.crawlers;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PagingConfig;
import intelli.crawler.worker.aop.BeforeProcessor;
import intelli.crawler.worker.extractors.PagingExtractor;



/**
 * 分页爬虫执行;
 * 
 * @author penglong;
 *
 */
public class PagingCrawlerExecutor  extends AbstractCrawlerExecutor
{
	
	public static final Logger LOG = LoggerFactory.getLogger(PagingCrawlerExecutor.class);
	
	private  PagingConfig pagingConfig;

	
	
	public  PagingCrawlerExecutor(CrawlerTaskConfig taskInfo) 
	{
		super(taskInfo);
		
	}
	public  PagingCrawlerExecutor(CrawlerTaskConfig taskInfo ,List<BeforeProcessor> processors ) 
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
		     this.pagingConfig = taskConfig.getPagingConfig();
		     getAndSetPagingSeedUrls();
		}
		
	}	
	
	/**
	 * 获取所有分页的url，并将其作为种子url,加入爬取列表;
	 */
	private void getAndSetPagingSeedUrls()
	{
		String currentSeedUrl = pagingConfig.getPagingSeedUrl();
		crawler.addSeed(currentSeedUrl);
		String pagingUrlPattern = pagingConfig.getPagingUrlPattern();
		
		Pattern p = Pattern.compile(pagingUrlPattern);
		
		int initPagingNum = pagingConfig.getInitPagingNum();
		final AtomicInteger pagingCounter = new AtomicInteger(initPagingNum);  // 当前第几页;
		
		int pagingNum = pagingConfig.getPagingNum(); //一共多少页;
		short stepSize = pagingConfig.getStepSize(); // 分页步长;
		
		Matcher matcher = p.matcher(currentSeedUrl);     
		
		if(matcher.find())
		{
			int index = matcher.end() ;  // 最后一个匹配分页模式(pattern ) 的字符;
			String startStr = currentSeedUrl.substring(0,index - Integer.toString(initPagingNum).length());
			LOG.info("分页url 前半部分:{}",startStr);
			
			String postStr = currentSeedUrl.substring(index);
			LOG.info("分页url 后半部分:{}",postStr);
			
			int nextPagingValue = initPagingNum;
			String nextPageUrl = null;
			 for(int i=0;i<pagingNum;i++)
			 {
				 nextPagingValue = pagingCounter.addAndGet(stepSize);
				 nextPageUrl = startStr +nextPagingValue +postStr;
				 crawler.addSeed(nextPageUrl);
			 }
			 LOG.info( "分页爬虫执行者【"+super.executorName+"】的seedUrls:{}",JSON.toJSONString(super.crawler.getSeeds()));
			
		}else
			LOG.warn( "分页的seedUrls:【{}】与分页url模式:【{}】不匹配，因此只爬取当前页！",currentSeedUrl,pagingUrlPattern);
		
	}
	
	

}
