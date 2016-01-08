package intelli.crawler.common.config;

import java.io.Serializable;


@Deprecated
public class PagingConfigOld implements Serializable
{
	private static final long serialVersionUID = 3464328226913095443L;

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
	 * 分页参数名;
	 * <br/>
	 * 如搜狗的分页参数名为“page”,百度的分页参数名为“pn”
	 */
	private String pagingName;
	
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
	
	/**
	 * 分页类型;
	 * <ol>
	 * <li> 参数型为0， 参见：{@link PagingConfigOld.ParametricPagingType}</li>
	 * <li> restful型为 1，参见：{@link PagingConfigOld.RestfulPagingType} </li>
	 * </ol>
	 *
	 */
	private short pagingType  = ParametricPagingType;
	
	/**
	 *  <p>
	 * 注意：该提取方式不适用于分页参数作为url 路径的restful型 url ;
	 * 如58同城的分页url:http://sz.58.com/ershouche/pn3/?minprice=8_10……
	 * 其分页参数为pn,位于url路径中，而不是作为url参数给出。
	 * </p>
	 * <p>
	 * 该提取方式适用于分页参数作为url的参数给出，即分页参数位于url后的?中;
	 * 如百度：https://www.baidu.com/s?wd=java&pn=10&oq=java&tn=baiduhome_pg
	 * 中的 pn=10,位于?后，两个 & 符号间;
	 *</p>
	 * <br/>
	 */
	public static final  short ParametricPagingType = 0;
	
	/**
	 * <p>
	 * 注意：该提取方式只适用于分页参数作为url 路径的restful型 url ;
	 * 如58同城的分页url:http://sz.58.com/ershouche/pn3/?minprice=8_10……
	 * 其分页参数为pn,位于url路径中，而不是作为url参数给出。
	 * </p>
	 * <p>
	 * 该提取方式不适用于分页参数作为url的参数给出，即分页参数位于url后的?中;
	 * 如百度：https://www.baidu.com/s?wd=java&pn=10&oq=java&tn=baiduhome_pg
	 * 中的 pn=10,位于?后，两个 & 符号间;
	 *</p>
	 * <br/>
	 */
	public static final  short RestfulPagingType = 1;
	
	public short getIsPaging()
	{
		return isPaging;
	}

	public void setIsPaging(short isPaging) 
	{
		this.isPaging = isPaging;
	}

	public String getPagingName() 
	{
		return pagingName;
	}

	public void setPagingName(String pagingName) 
	{
		this.pagingName = pagingName;
	}

	/**
	 * 获取分页步长; 
	 * <br/>
	 * 分页页面步长，如搜狗的为“1”，百度为“10”；
	 * <br/>
	 * 一般为"1";
	 * @return
	 */
	public short getStepSize() 
	{
		return stepSize;
	}

	/**
	 * 设置分页步长;
	 * <br/>
	 * 分页页面步长，如搜狗的为“1”，百度为“10”；
	 * <br/>
	 * 一般为"1";
	 * @param stepSize
	 */
	public void setStepSize(short stepSize) 
	{
		this.stepSize = stepSize;
	}

	/**
	 * 获取分页类型;
	 * <ol>
	 * <li> 参数型为0</li>
	 * <li> restful 型为 1 </li>
	 * </ol>
	 */
	public short getPagingType() 
	{
		return pagingType;
	}

	/**
	 * 设置分页类型;
	 * <ol>
	 * <li> 参数型为0</li>
	 * <li> restful 型为 1 </li>
	 * </ol>
	 */
	public void setPagingType(short pagingType) 
	{
		this.pagingType = pagingType;
	}

	/**
	 * 获取页面数,即多少页;
	 * @return
	 */
	public short getPagingNum() 
	{
		return pagingNum;
	}

	/**
	 * 设置页面数,即多少页;
	 * @param pagingNum
	 */
	public void setPagingNum(short pagingNum) 
	{
		this.pagingNum = pagingNum;
	}

	/**
	 * 获取分页的 seedurl
	 * @return
	 */
	public String getPagingSeedUrl() 
	{
		return pagingSeedUrl;
	}

	/**
	 * 设置分页的seedurl;
	 * @param pagingSeedUrl
	 */
	public void setPagingSeedUrl(String pagingSeedUrl)
	{
		this.pagingSeedUrl = pagingSeedUrl;
	}
	
}
