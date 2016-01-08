package intelli.crawler.worker.crawlers;


import java.util.List;

import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.worker.aop.BeforeProcessor;
import intelli.crawler.worker.extractors.StandardExtractor;

/**
 * 标准爬虫;
 * <br/>
 * 不支持分页，登录，ajax 等;
 * @author penglong
 *
 */
public class StandardCrawlerExecutor extends AbstractCrawlerExecutor
{
	
	public StandardCrawlerExecutor(CrawlerTaskConfig taskInfo) 
	{
		super(taskInfo);
		
	}
	public StandardCrawlerExecutor(CrawlerTaskConfig taskInfo, List<BeforeProcessor> processors) 
	{
		super(taskInfo,processors);
	}

	@Override
	protected void initExtractorInfo() 
	{
		List<CommonTable> tables = taskConfig.getTables();
		
		for(CommonTable table :tables)
		{
			//初始化抽取参数;
			 ExtractorParams params = new ExtractorParams();
			 params.put(table.getTablename(), table); //待抽取的字段信息;
		     params.put("extractUrlParttern", table.getExtractUrlPattern() ); //抽取数据的页面模式;
		     params.put("crawlerTaskId", taskConfig.getId()); //爬虫任务ID;
		     params.put("outputDB", taskConfig.getOutputDB()); //输出数据库类型;
			 crawler.addExtractor(table.getExtractUrlPattern() , StandardExtractor.class,params);
		}
	}
	
}
	
	
	
	
	
	
	
	
	

