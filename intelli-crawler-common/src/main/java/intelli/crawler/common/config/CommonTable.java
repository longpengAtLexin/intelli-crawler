package intelli.crawler.common.config;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 通用表，用于配置爬虫任务需提取的信息;
 * <br/>
 * 一个通用的配置模型一般如下：
 * @author penglong
 *
 */
public class CommonTable implements Serializable
{
	
	private static final long serialVersionUID = 266672728949999184L;

	/**
	 * 	表名;
	 */
	private String tablename;
	
	/**
	 * 表名描述;
	 */
	private String tabledesc;
	
	/**
	 * 数据抽取页面的url 模式;
	 * <br/>
	 * 即:待抽取数据的页面 url 需要匹配的模式;
	 */
	private String extractUrlPattern;
	
	
	/**
	 * 在哪个url 上采集的信息;
	 *  <br/>
	 *  该选项不是配置内容;
	 */
	private String url;
	
	/**
	 * 请求上述 {@link #url} 的时间;
	 * <br/>
	 *  该选项不是配置内容;
	 */
	private Date requestTime;
	
	
	
	/**
	 * 创建时间;
	 */
	private Date createTime;
	
	/**
	 * 每个页面的表记录条数;
	 */
	private int numPerPage = DefualtNumPerPage;
	
	/**
	 * 每个页面一条表记录;
	 */
	public static final int DefualtNumPerPage = 1;
	
	/**
	 * 每个页面多条表记录;
	 */
	public static final int MutiNumPerPage = 2;
	
	
	/**
	 * 页面只有一条记录时的属性集;
	 */
	private List<PropertyInfo> props;
	
	/**
	 * 页面有多条数据时的数据集, 一般指的是列表有 grid 或者 Table 型的数据结构,
	 * <br/>
	 * 如：http://www.proxy360.cn/default.aspx
	 */
	private List<DataRow> dataRows;
	

	public String getTablename() 
	{
		return tablename;
	}

	public void setTablename(String tablename)
	{
		this.tablename = tablename;
	}

	public List<PropertyInfo> getProps() 
	{
		return props;
	}

	public void setProps(List<PropertyInfo> props) 
	{
		this.props = props;
	}

	public String getTabledesc() 
	{
		return tabledesc;
	}

	public void setTabledesc(String tabledesc)
	{
		this.tabledesc = tabledesc;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}

	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	/**
	 * 
	 * @return 数据抽取页面的url 模式;
	 */
	public String getExtractUrlPattern()
	{
		return extractUrlPattern;
	}

	/**
	 *  设置 数据抽取页面的url 模式;
	 * @param extractUrlPattern
	 */
	public void setExtractUrlPattern(String extractUrlPattern)
	{
		this.extractUrlPattern = extractUrlPattern;
	}

	public Date getRequestTime() 
	{
		return requestTime;
	}

	public void setRequestTime(Date requestTime) 
	{
		this.requestTime = requestTime;
	}

	public int getNumPerPage() 
	{
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) 
	{
		this.numPerPage = numPerPage;
	}

	
	public List<DataRow> getDataRows() 
	{
		return dataRows;
	}

	public void setDataRows(List<DataRow> dataRows) 
	{
		this.dataRows = dataRows;
	}
	
	
	
	
	
}
