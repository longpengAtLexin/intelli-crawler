package intelli.crawler.common.config;

import java.util.Set;

/**
 * 待爬取网站的代理配置信息，如果不能直接爬取目标网站，则通过设置代理ip,通过代理ip 间接爬取。
 * 默认代理类型 HTTP;
 * @author penglong
 *
 */
public class ProxyConfig 
{
	
	/**
	 * 代理集合;
	 * 
	 */
	private Set<ProxyInfo>  proxyInfos;
	
	/**
	 * 代理信息;
	 * @author penglong
	 *
	 */
	public static class ProxyInfo
	{
		/**
		 * 代理Ip;
		 */
		private String proxyIp;
	
		/**
		 * 代理端口;
		 */
		private int proxyPort;

		
		public ProxyInfo(String proxyIp ,int proxyPort)
		{
			this.proxyIp = proxyIp;
			this.proxyPort = proxyPort;
		}
		public ProxyInfo(){}
		
		/**
		 * 获取代理Ip;
		 * @return
		 */
		public String getProxyIp() 
		{
			return proxyIp;
		}

		/**
		 * 设置代理IP;
		 * @param proxyIp
		 */
		public void setProxyIp(String proxyIp) 
		{
			this.proxyIp = proxyIp;
		}

		/**
		 * 获取代理端口;
		 * @return
		 */
		public int getProxyPort() 
		{
			return proxyPort;
		}

		/**
		 * 设置代理端口;
		 * @param proxyPort
		 */
		public void setProxyPort(int proxyPort) 
		{
			this.proxyPort = proxyPort;
		}
	}

	/**
	 * 获取代理集合信息;
	 * @return 获取代理信息;
	 */
	public Set<ProxyInfo> getProxyInfos() 
	{
		return proxyInfos;
	}

	/**
	 * 设置代理信息;
	 * @param proxyInfos 代理集合;
	 */
	public void setProxyInfos(Set<ProxyInfo> proxyInfos) 
	{
		this.proxyInfos = proxyInfos;
	}
	
	/**
	 * 增加一条代理信息;
	 * @param proxyInfo
	 */
	public void addProxyInfo(ProxyInfo proxyInfo)
	{
		proxyInfos.add(proxyInfo);
	}
	
	
}
