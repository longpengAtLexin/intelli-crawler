package intelli.crawler.worker.aop;

import java.net.Proxy;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.crawler.MultiExtractorCrawler;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.RandomProxyGenerator;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.ProxyConfig;
import intelli.crawler.common.config.ProxyConfig.ProxyInfo;

/**
 * 爬取数据前设置代理.
 * @author penglong
 *
 */
public class SetProxyBeforeProcessor implements BeforeProcessor
{
	public static final Logger LOG = LoggerFactory.getLogger(SetProxyBeforeProcessor.class);
	
	/**
	 * 为爬虫 设置代理信息;
	 * @param taskInfo 任务配置信息->代理配置信息;
	 * 
	 * @param crawler 待设置代理的爬虫;
	 */
	public void process(CrawlerTaskConfig taskInfo ,MultiExtractorCrawler crawler)
	{
		ProxyConfig proxyConfig = taskInfo.getProxyConfig();
		if(proxyConfig!=null)
		{
			Set<ProxyInfo>  proxyInfos = proxyConfig.getProxyInfos();
			if(proxyInfos!=null&&proxyInfos.size()>0)
			{
				HttpRequesterImpl httpRequester = new HttpRequesterImpl();
				Iterator<ProxyInfo > it = proxyInfos.iterator();
				if(proxyInfos.size() ==1 )
				{
					// 记录日志;
					ProxyInfo tmp = it.next();
					if(validateProxyInfo(tmp)) // 代理有效
					{
						httpRequester.setProxy(tmp.getProxyIp(),tmp.getProxyPort(), Proxy.Type.HTTP);
						LOG.info("爬虫任务【Id:{} ,Name:{}】 已设置代理服务器, 代理Ip:{},端口:{}", taskInfo.getId() ,taskInfo.getName(),tmp.getProxyIp(), tmp.getProxyPort());
					}else
						LOG.warn("检测到代理服务器无效, 代理Ip:{},端口:{}", tmp.getProxyIp(), tmp.getProxyPort());
					
				}else
				{
					RandomProxyGenerator proxyGenerator = new RandomProxyGenerator();
					while(it.hasNext())
					{
						ProxyInfo tmp = it.next();
						if(validateProxyInfo(tmp)) // 代理有效
						{
							proxyGenerator.addProxy(tmp.getProxyIp(), tmp.getProxyPort(), Proxy.Type.HTTP);
						}else
							LOG.warn("检测到代理服务器无效, 代理Ip:{},端口:{}", tmp.getProxyIp(), tmp.getProxyPort());
					}
					if(proxyGenerator.getProxys().size()>0)
					{
						httpRequester.setProxyGenerator(proxyGenerator);
						LOG.info("爬虫任务【Id:{} ,Name:{}】 已设置随机代理服务器列表, 代理信息:{}", taskInfo.getId() ,taskInfo.getName(),JSON.toJSONString(proxyInfos));
					}
				}
				crawler.setHttpRequester(httpRequester);
			}
		}
		
	}

	/**
	 * 利用百度网站测试 代理信息的是否有效;
	 * @param proxyInfo
	 * @return
	 */
	protected boolean validateProxyInfo(ProxyInfo proxyInfo)
	{
		return true;
	}
}
