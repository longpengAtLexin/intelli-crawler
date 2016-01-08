package intelli.crawler.common.dao.model;

import java.util.Date;


/**
 * 
 * @author penglong
 * <br/>
 * 爬虫任务信息 ;
 *<br/>
 * 该类用于序列化  {@link intelli.crawler.common.config.CrawlerTaskConfigInfo} 后持久化到数据库中;
 */
public class CrawlerTaskInfoPo 
{

	/**
	 * 任务id;
	 */
	protected String id;
	
	
	/**
	 * 所属父任务组的id
	 */
	protected String pid;
	
	/**
	 * 爬虫任务名称;
	 */
	protected String name;
	
	/**
	 * 配置信息;
	 */
	protected String confstr;
	
	
	/**
	 * 状态;
	 */
	protected String status;
	
	/**
	 * 输出数据库类型;
	 */
	private String outputDB;
	
	/**
	 * 创建时间;
	 */
	protected Date createTime;
	
	/**
	 * 上次修改时间;
	 */
	protected Date modifyTime;
	
	
	public String getId() {
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

	public String getConfstr() 
	{
		return confstr;
	}

	public void setConfstr(String confstr) 
	{
		this.confstr = confstr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) 
	{
		this.modifyTime = modifyTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutputDB() {
		return outputDB;
	}

	public void setOutputDB(String outputDB) {
		this.outputDB = outputDB;
	}
}
