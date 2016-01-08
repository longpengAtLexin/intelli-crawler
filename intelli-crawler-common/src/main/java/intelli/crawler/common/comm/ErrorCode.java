package intelli.crawler.common.comm;

/**
 * rpc 过程中的响应码信息;
 * @author penglong
 *
 */
public enum ErrorCode 
{
	Success(0,"成功"),
	
	SqlError(1,"数据库异常"),
	
	EmptyTaskConfig(2,"爬虫配置信息未找到"),
	
	LaunchError(3,"爬虫启动异常"),
	
	StopError(4,"爬虫停止异常"),
	
	RefreshSuccess(5,"爬虫任务刷新成功"),
	
	RefreshError(6,"爬虫任务刷新失败");
	
	private int code;
	
	private String desc;
	
	private ErrorCode(int code,String desc )
	{
		this.code = code;
		this.desc = desc;
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
