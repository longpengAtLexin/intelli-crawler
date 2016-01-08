package intelli.crawler.common.dao.model;

import java.util.Date;

/**
 * 提取过数据的url 信息，包括url源和 请求时间;
 * @author penglong
 *
 */
public class ExtractedUrl 
{
	/**
	 * 
	 */
	private String id;
	/**
	 * 提取过的url;
	 */
	private String url;
	
	/**
	 * 请求上述{@link #url} 的时间;
	 */
	private Date requestTime;

	
	
	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public Date getRequestTime() 
	{
		return requestTime;
	}

	public void setRequestTime(Date requestTime) 
	{
		this.requestTime = requestTime;
	}
	
	
	
}
