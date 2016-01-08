package intelli.crawler.worker.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PagingConfig;
import intelli.crawler.common.config.ProxyConfig;
import intelli.crawler.common.config.ProxyConfig.ProxyInfo;
import intelli.crawler.worker.aop.BeforeProcessor;
import intelli.crawler.worker.aop.SetProxyBeforeProcessor;
import intelli.crawler.worker.crawlers.AbstractCrawlerExecutor;
import intelli.crawler.worker.crawlers.PagingCrawlerExecutor;
import intelli.crawler.worker.crawlers.StandardCrawlerExecutor;

/**
 * CrawlerExecutor 创建工厂;
 * 
 * 根据爬虫任务的配置信息创建指定类型的爬虫,主要有以下几种场景;
 * <ol>
 * <li> 网页需登录;
 * <li> 网页禁用爬虫;
 * <li> 网页需分页爬取;
 * <li> 网页懒加载内容爬取, ajax加载网页内容;
 * 
 * </ol>
 * @author penglong
 *
 */
public class CrawlerExecutorFactory 
{
	
	 private static final CrawlerExecutorFactory instance = new CrawlerExecutorFactory();
	 
	 private CrawlerExecutorFactory(){};
	 
	 public static CrawlerExecutorFactory getInstance()
	 {
		 return instance;
	 }
	 
	 /**
	  * 创建爬虫执行者;
	  * @param crawlerTaskConfig 爬虫任务配置;
	  * @return
	  */
	 public AbstractCrawlerExecutor createCrawlerExecutor(CrawlerTaskConfig crawlerTaskConfig)
	 {
		 AbstractCrawlerExecutor executor = null;
		 PagingConfig pagingConfig = crawlerTaskConfig.getPagingConfig();
		 if(pagingConfig != null)
		 {
			 List<BeforeProcessor> processors = getAopProcessor(crawlerTaskConfig);
			 if(processors!=null)
				 executor = new PagingCrawlerExecutor(crawlerTaskConfig,processors);
			 else
				 executor = new PagingCrawlerExecutor(crawlerTaskConfig);
		 }
			 
		 else
		 {
			 List<BeforeProcessor> processors = getAopProcessor(crawlerTaskConfig);
			 if(processors!=null)
				 executor = new StandardCrawlerExecutor(crawlerTaskConfig,processors);
			 else
				 executor = new StandardCrawlerExecutor(crawlerTaskConfig);
		 }
		return executor;
	 }
	 
	 /**
	  * 爬虫是否有代理、登录等前置操作;
	  * @param crawlerTaskConfig
	  */
	 private List<BeforeProcessor> getAopProcessor(CrawlerTaskConfig crawlerTaskConfig)
	 {
		 ProxyConfig proxyConfig = crawlerTaskConfig.getProxyConfig();
		 if(proxyConfig != null)
		 {
			 Set<ProxyInfo> set_  = proxyConfig.getProxyInfos();
			 if(set_!= null &&set_.size()>0)
			 {
				 List<BeforeProcessor> processors = new LinkedList<BeforeProcessor>();
				 BeforeProcessor processor = new SetProxyBeforeProcessor();
				 processors.add(processor);
				 
				 return processors;
			 }
		 }
		return null;
	 }
	 
	 

}
