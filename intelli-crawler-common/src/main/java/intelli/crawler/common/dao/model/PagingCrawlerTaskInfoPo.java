package intelli.crawler.common.dao.model;

public class PagingCrawlerTaskInfoPo extends CrawlerTaskInfoPo
{
	/**
	 * 偏移量;
	 */
	protected int start;
	
	/**
	 * 每页限制数;
	 */
	protected int limit;

	/**
	 * 开始时间;
	 */
	protected String startDate;

	/**
	 * 结束时间;
	 */
	protected String endDate;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
