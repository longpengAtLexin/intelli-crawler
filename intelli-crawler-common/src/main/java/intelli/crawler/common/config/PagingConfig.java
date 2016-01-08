package intelli.crawler.common.config;

import java.io.Serializable;

public class PagingConfig implements Serializable
{

	
	private static final long serialVersionUID = -4445921115020965005L;
	
	/**
	 * 是否有分页;
	 * <ol>
	 * <li> 无分页为0</li>
	 * <li> 有分页为1</li/>
	 * <ol/>
	 */
	private short  isPaging = 0;
	
	/**
	 * 分页的种子url ;
	 */
	private String pagingSeedUrl;
	/**
	 * 分页url 模式;
	 * 
	 */
	private String pagingUrlPattern;
	
	/**
	 * 当前 页面数; 一般是第一页，个别是第二页;
	 */
	private int  initPagingNum;
	
	/**
	 * 页面数,即多少页;
	 */
	private short pagingNum = DefaultPagingNum;
	
	public static final  short DefaultPagingNum = 10;
	/**
	 * 分页页面步长，如搜狗的为“1”，百度为“10”；
	 * <br/>
	 * 一般为"1";
	 */
	private short stepSize = DefaultStepSize;

	/**
	 * 默认分页页面步长，
	 * <br/>
	 * 如搜狗的为“1”，百度为“10”；
	 * <br/>
	 * 默认值为"1";
	 */
	public static final  short DefaultStepSize = 1;

	public short getIsPaging() 
	{
		return isPaging;
	}

	public void setIsPaging(short isPaging) 
	{
		this.isPaging = isPaging;
	}

	public String getPagingSeedUrl()
	{
		return pagingSeedUrl;
	}

	public void setPagingSeedUrl(String pagingSeedUrl) 
	{
		this.pagingSeedUrl = pagingSeedUrl;
	}

	public String getPagingUrlPattern() 
	{
		return pagingUrlPattern;
	}

	public void setPagingUrlPattern(String pagingUrlPattern) 
	{
		this.pagingUrlPattern = pagingUrlPattern;
	}

	public int getInitPagingNum() 
	{
		return initPagingNum;
	}

	public void setInitPagingNum(int initPagingNum) 
	{
		this.initPagingNum = initPagingNum;
	}

	public short getPagingNum() 
	{
		return pagingNum;
	}

	public void setPagingNum(short pagingNum) 
	{
		this.pagingNum = pagingNum;
	}

	public short getStepSize() 
	{
		return stepSize;
	}

	public void setStepSize(short stepSize)
	{
		this.stepSize = stepSize;
	}
	
	
	

}
