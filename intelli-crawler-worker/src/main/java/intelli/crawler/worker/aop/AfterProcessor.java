package intelli.crawler.worker.aop;

import intelli.crawler.common.config.CrawlerTaskConfig;

/**
 * 爬取网页数据后需要执行的操作,如：
 * <ul>
 * <li>ajax 异步请求需懒加载的数据</li>
 * <li></li>
 * </ul>
 * @author penglong
 *
 */
public interface AfterProcessor 
{
	/**
	 * 爬取网页数据后，执行Aop 操作;
	 * @param taskInfo 爬虫任务配置信息;
	 */
	void process(CrawlerTaskConfig  taskInfo);

}
