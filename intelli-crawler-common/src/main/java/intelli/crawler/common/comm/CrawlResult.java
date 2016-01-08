package intelli.crawler.common.comm;

import java.io.Serializable;

/**
 * 爬虫任务结果;
 * @author penglong
 *
 */
public class CrawlResult implements Serializable
{
	private static final long serialVersionUID = -4866447666509148070L;

	/**
	 * 爬虫任务Id;
	 */
	private String crawlerTaskId;
	
	/**
	 * 爬虫任务名;
	 */
	private String crawlerTaskName;

	/**
	 * 耗时,单位second;
	 */
	private long costTime;
	
	/**
	 * 爬取的urls数;
	 */
	private int totalUrls;
	
	/**
	 * 爬取深度;
	 */
	private int depth;
	
	/**
	 * 抽取结果;
	 */
	private int extractRecords;
	
	private CrawlerStatus status;
	
	
	public static enum CrawlerStatus
	{
		NEW(1),
		RUNNING(2),
		EXCEPTION_TERMINATED(3),
		COMPLETED(4);
		
		private int value;
		
		private CrawlerStatus(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
	}

	public String getCrawlerTaskId() {
		return crawlerTaskId;
	}

	public void setCrawlerTaskId(String crawlerTaskId) {
		this.crawlerTaskId = crawlerTaskId;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public int getTotalUrls() {
		return totalUrls;
	}

	public void setTotalUrls(int totalUrls) {
		this.totalUrls = totalUrls;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getExtractRecords() {
		return extractRecords;
	}

	public void setExtractRecords(int extractRecords) {
		this.extractRecords = extractRecords;
	}

	public CrawlerStatus getStatus() {
		return status;
	}

	public void setStatus(CrawlerStatus status) {
		this.status = status;
	}

	public String getCrawlerTaskName() {
		return crawlerTaskName;
	}

	public void setCrawlerTaskName(String crawlerTaskName) {
		this.crawlerTaskName = crawlerTaskName;
	}
	
	
	
	
	
	
	
	
	
	
}
