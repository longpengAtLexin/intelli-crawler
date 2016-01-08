package intelli.crawler.common.comm;

import java.io.Serializable;

/**
 * rpc 的请求信息;
 * @author penglong
 *
 */
public class RpcRequest implements Serializable
{
	
	private static final long serialVersionUID = 4193381598243822810L;

	/**
	 * 请求Id号;
	 */
	private String requestId;
	
	/**
	 * 请求参数;
	 */
	private Object requestParam ;
	
	public String getRequestId() 
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Object getRequestParam() 
	{
		return requestParam;
	}

	public void setRequestParam(Object requestParam)
	{
		this.requestParam = requestParam;
	}

	
}
