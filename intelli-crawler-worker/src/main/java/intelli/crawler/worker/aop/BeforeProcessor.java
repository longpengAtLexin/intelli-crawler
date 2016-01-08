package intelli.crawler.worker.aop;

import cn.edu.hfut.dmic.webcollector.crawler.MultiExtractorCrawler;
import intelli.crawler.common.config.CrawlerTaskConfig;


/**
 * 爬取网页数据前需要执行的操作,如：
 * <ul>
 * <li>登录</li>
 * <li>设置代理等</li>
 * </ul>
 * @author penglong
 *
 */
public interface BeforeProcessor 
{
	/**
	 * 爬取网页数据前，执行Aop 操作;
	 * @param taskInfo 爬虫任务配置信息;
	 */
	void process(CrawlerTaskConfig  taskInfo ,MultiExtractorCrawler crawler);
}
