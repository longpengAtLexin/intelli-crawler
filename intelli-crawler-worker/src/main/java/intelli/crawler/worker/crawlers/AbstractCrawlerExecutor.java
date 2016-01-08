package intelli.crawler.worker.crawlers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import intelli.crawler.common.comm.CrawlResult;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.worker.aop.BeforeProcessor;
import intelli.crawler.worker.core.DefaultCrawlerTaskServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.hfut.dmic.webcollector.crawler.MultiExtractorCrawler;

/**
 *  爬虫执行实体;
 * @author penglong
 *
 */
public abstract class AbstractCrawlerExecutor 
{
	public static final Logger LOG = LoggerFactory.getLogger(AbstractCrawlerExecutor.class);
	
	/**
	 * 执行者名称;
	 */
	protected String executorName;
	
	/**
	 * 爬虫任务配置信息;
	 */
	protected CrawlerTaskConfig taskConfig;
	
	/**
	 * 爬虫
	 */
	protected MultiExtractorCrawler crawler;
	
	private List<BeforeProcessor> processors;
	
	public static final int topN = 1000;
	
	protected AbstractCrawlerExecutor( CrawlerTaskConfig  taskConfig )
	{
		this(taskConfig, null);
	}
	
	protected AbstractCrawlerExecutor( CrawlerTaskConfig  taskConfig ,List<BeforeProcessor> processors)
	{
		this.taskConfig = taskConfig;
		this.executorName = taskConfig.getName()+"-Executor";
		this.processors = processors;
		initBaseInfo();
		initExtractorInfo();
	}
	
	protected void initBaseInfo()
	{
		crawler = new MultiExtractorCrawler(taskConfig.getName(), true);
		
		// [start 初始化url 入口]
		Set<String> seeds = taskConfig.getSeedurls();
		Iterator<String > it_seeds = seeds.iterator();
		while(it_seeds.hasNext())
		{
			crawler.addSeed(it_seeds.next());
		}
		
		//不希望爬取包含#?的链接，同时不希望爬取jpg或者png文件;这些放在配置页面去做;
         //crawler.addRegex("-.*#.*");
        // crawler.addRegex("-.*\\?.*");
		//crawler.addRegex("-.*\\.(jpg|png|gif).*");
		
		//[start 初始化url匹配模式]
        Set<String>  urlpatterns = taskConfig.getUrlpatterns();
        Iterator<String > it_urlpatterns= urlpatterns.iterator();
		while(it_urlpatterns.hasNext())
		{
			crawler.addRegex(it_urlpatterns.next());
		}
		
		if(processors!=null&&processors.size()>0)
		{
			for(BeforeProcessor processor : processors)
			{
				processor.process(taskConfig ,crawler);
			}
		}
		
		//[start 其他设置]
		crawler.setThreads(taskConfig.getThreadNum());
		crawler.setTopN(topN);
	}
	
	/**
	 * 初始化数据提取信息;
	 */
	protected abstract void initExtractorInfo();
	
	/**
	 * 开始爬虫任务;
	 */
	public CrawlResult start()
	{
		CrawlResult result = null;
		int crawlDepth = taskConfig.getCrawlDepth();
		try 
		{
			result = crawler.start(crawlDepth);
			// 非空判断;
			if(result!=null)
			{
				result.setCrawlerTaskId(taskConfig.getId());
				result.setCrawlerTaskName(taskConfig.getName());
				AtomicInteger counter = DefaultCrawlerTaskServiceImpl.crawlerRecordCounterMap.get(taskConfig.getId());
				result.setExtractRecords(counter.get());
			}
		} catch (Exception e) 
		{
			LOG.warn( "爬虫任务【"+ executorName +"】启动失败！", e);
			throw new IllegalArgumentException(e);
		}
		return result;
	}
	
	/**
	 * 停止执行;
	 */
	public void stop()
	{
		crawler.stop();
	}
	
	
	
	
	
	
	
}
