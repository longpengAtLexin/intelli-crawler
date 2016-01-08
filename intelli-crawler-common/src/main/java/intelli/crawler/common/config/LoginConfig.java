package intelli.crawler.common.config;

import java.io.Serializable;
import java.util.Set;

/**
 * 	爬虫登录配置;
 * @author penglong
 *
 */
public class LoginConfig implements Serializable
{
	
	private static final long serialVersionUID = 5585171313551166752L;

	/**
	 * 是否需要登录;
	 * <ol>
	 * <li> 不需要登录为0</li>
	 * <li> 需要登录为1</li/>
	 * <ol/>
	 */
	private short  needLogin = Login_No;
	
	public static final short Login_Yes = 1;
	
	public static final short Login_No= 0;
	
	/**
	 * 登录的url; 利用其获取cookie,请求头等信息;
	 */
	private String loginUrl ;
	
	/**
	 * form 表单中的 action ;
	 */
	private String formAction ;
	
	/**
	 * 登录参数;
	 */
	private Set<NameValuePair> nameValuePairs;
	
	/**
	 * 是否有验证码;
	 */
	private boolean hasCaptcha;
	
	/**
	 * 获取验证码的url;
	 */
	private String captchaUrl;
	
	/**
	 * 登录成功返回的信息;
	 */
	private String successMsg;
	
	/**
	 * 登录失败的信息;
	 */
	private String errorMsg;
	
	
	/**
	 * 属性名-属性值对;
	 * @author penglong
	 * 
	 */
	public static class NameValuePair implements Serializable
	{
		private static final long serialVersionUID = 5962368226461450538L;

		/**
		 * 属性名;
		 */
		public String name;
		
		/**
		 * 属性值;
		 */
		public String value;
		
		public NameValuePair(){}
		
		public NameValuePair(String name , String value)
		{
			this.name = name;
			this.value = value;
		}
	}
	
	public short getNeedLogin() 
	{
		return needLogin;
	}
	public void setNeedLogin(short needLogin)
	{
		this.needLogin = needLogin;
	}
	public Set<NameValuePair> getNameValuePairs() 
	{
		return nameValuePairs;
	}

	public void setNameValuePairs(Set<NameValuePair> nameValuePairs)
	{
		this.nameValuePairs = nameValuePairs;
	}

	public String getLoginUrl() 
	{
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}
	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) 
	{
		this.formAction = formAction;
	}
	public boolean isHasCaptcha()
	{
		return hasCaptcha;
	}
	public void setHasCaptcha(boolean hasCaptcha) 
	{
		this.hasCaptcha = hasCaptcha;
	}
	public String getCaptchaUrl() 
	{
		return captchaUrl;
	}
	public void setCaptchaUrl(String captchaUrl) 
	{
		this.captchaUrl = captchaUrl;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
