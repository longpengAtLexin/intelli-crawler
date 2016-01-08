package intelli.crawler.common.comm;

import java.io.Serializable;

/**
 * rpc 的响应信息；
 * @author penglong
 *
 */
public class RpcResponse implements Serializable
{
	private static final long serialVersionUID = -89911610336274178L;

	/**
	 * 请求Id号;
	 */
	private String requestId;
	
	/**
	 * rpc过程中的异常信息，如果有;
	 */
    private Throwable throwable;
    
    /**
     * 调用返回的结果信息;
     */
    private Object result;

    /**
     * 响应码信息;
     */
    private ErrorCode errInfo;
    
	public String getRequestId() 
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ErrorCode getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(ErrorCode errInfo) {
		this.errInfo = errInfo;
	}
    
    
}
