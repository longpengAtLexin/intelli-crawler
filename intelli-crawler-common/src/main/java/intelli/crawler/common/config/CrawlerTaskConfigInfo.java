package intelli.crawler.common.config;

import intelli.crawler.common.comm.CrawlResult.CrawlerStatus;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * 爬虫任务配置信息;
 * @author penglong
 *
 *  已过时，不推荐使用;
 */
@Deprecated
public class CrawlerTaskConfigInfo implements Serializable
{
	
	private static final long serialVersionUID = -7304852504417142596L;

	/**
	 * 任务id;
	 */
	private String id;
	
	/**
	 * 所属父任务组的id
	 */
	private String pid;
	
	/**
	 * 任务名称;
	 */
	private String name;
	
	/**
	 * 种子urls(起始urls);<br/>
	 * 可能有多个;
	 */
	private Set<String>  seedurls;
	
	/**
	 * url 匹配模式;
	 */
	private Set<String>  urlpatterns;
	
	
	
	/**
	 * 需提取的数据信息;
	 * <br/>
	 * 包含待提取数据的字段信息;
	 */
	private CommonTable  table;
	
	/**
	 * 爬虫深度;
	 * <br/>
	 * 缺省遍历深度为 10;
	 */
	private int crawlDepth  = DefaultCrawlDepth;
	
	/**
	 *  缺省遍历深度: 10;
	 */
	public static final int DefaultCrawlDepth = 10;
	/**
	 * 登录配置;
	 */
	private LoginConfig loginConfig;
	
	/**
	 * 分页配置;
	 */
	private PagingConfigOld pagingConfig;
	
	/**
	 * 待爬取网站的代理配置信息，如果不能直接爬取目标网站，则通过设置代理ip,通过代理ip间接爬取;
	 */
	private ProxyConfig proxyConfig;

	/**
	 * 爬虫任务的线程数;
	 */
	private int threadNum  = DefaultThreadNum;
	
	public static final int DefaultThreadNum= 10;
	
	/**
	 * 爬虫状态;
	 */
	private int status = CrawlerStatus.NEW.getValue();
	
	public CrawlerTaskConfigInfo()
	{
		seedurls = new HashSet<String>();
		urlpatterns = new HashSet<String>();
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid) 
	{
		this.pid = pid;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Set<String> getSeedurls()
	{
		return seedurls;
	}

	public void addSeedurl(String seedurl) 
	{
		this.seedurls.add(seedurl);
	}
	
	public void setSeedurls(Set<String> seedurls)
	{
		this.seedurls = seedurls;
	}

	public Set<String> getUrlpatterns()
	{
		return urlpatterns;
	}

	public void addUrlpattern(String urlpattern) 
	{
		this.urlpatterns.add(urlpattern);
	}
	
	public void setUrlpatterns(Set<String> urlpatterns) 
	{
		this.urlpatterns = urlpatterns;
	}

	public LoginConfig getLoginConfig() 
	{
		return loginConfig;
	}

	public void setLoginConfig(LoginConfig loginConfig)
	{
		this.loginConfig = loginConfig;
	}

	public PagingConfigOld getPagingConfig() 
	{
		return pagingConfig;
	}

	public void setPagingConfig(PagingConfigOld pagingConfig) 
	{
		this.pagingConfig = pagingConfig;
	}

	public CommonTable getTable() 
	{
		return table;
	}

	public void setTable(CommonTable table)
	{
		this.table = table;
	}

	

	/**
	 * 获取爬虫深度;
	 */
	public int getCrawlDepth()
	{
		return crawlDepth;
	}

	/**
	 * 设置爬虫深度;
	 * @param crawlDepth
	 */
	public void setCrawlDepth(int crawlDepth)
	{
		this.crawlDepth = crawlDepth;
	}
	
	/**
	 * 获取爬虫任务线程数;
	 * @return
	 */
	public int getThreadNum() 
	{
		return threadNum;
	}

	/**
	 * 设置爬虫任务线程数;
	 * @param threadNum
	 */
	public void setThreadNum(int threadNum) 
	{
		this.threadNum = threadNum;
	}
	
	
	
	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}
	/**
	 * 获取爬虫状态;
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 设置爬虫状态;
	 * @param status
	 */
	public void setStatus(int status) 
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
	
	

}
