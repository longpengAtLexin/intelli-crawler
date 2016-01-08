package intelli.crawler.common.config;

import intelli.crawler.common.comm.CrawlResult.CrawlerStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 爬虫任务配置信息;
 * @author penglong
 *
 */
public class CrawlerTaskConfig implements Serializable
{
	private static final long serialVersionUID = 3663755794239087219L;

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
	 * 
	 * 一张网页可能提取多张表信息;
	 */
	private List<CommonTable>  tables;
	
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
	private PagingConfig pagingConfig;
	
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
	
	private String outputDB = OutputDB_MySql;
	
	public static final String OutputDB_MySql = "MYSQL";
	
	public static final  String OutputDB_Hbase = "HBASE";
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getSeedurls() {
		return seedurls;
	}

	public void setSeedurls(Set<String> seedurls) {
		this.seedurls = seedurls;
	}

	public Set<String> getUrlpatterns() {
		return urlpatterns;
	}

	public void setUrlpatterns(Set<String> urlpatterns) {
		this.urlpatterns = urlpatterns;
	}

	public List<CommonTable> getTables() {
		return tables;
	}

	public void setTables(List<CommonTable> tables) {
		this.tables = tables;
	}

	public int getCrawlDepth() {
		return crawlDepth;
	}

	public void setCrawlDepth(int crawlDepth) {
		this.crawlDepth = crawlDepth;
	}

	public LoginConfig getLoginConfig() {
		return loginConfig;
	}

	public void setLoginConfig(LoginConfig loginConfig) {
		this.loginConfig = loginConfig;
	}

	public PagingConfig getPagingConfig() {
		return pagingConfig;
	}

	public void setPagingConfig(PagingConfig pagingConfig) {
		this.pagingConfig = pagingConfig;
	}

	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) 
	{
		this.status = status;
	}

	public String getOutputDB() {
		return outputDB;
	}

	public void setOutputDB(String outputDB) 
	{
		this.outputDB = outputDB;
	}
	
	
	
}
