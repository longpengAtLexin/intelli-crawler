package intelli.crawler.worker.aop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.crawler.MultiExtractorCrawler;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.LoginConfig;
import intelli.crawler.common.config.LoginConfig.NameValuePair;

/**
 * 爬取数据前的登录操作;
 * @author penglong
 *
 */
public class LoginBeforeProcessor  implements BeforeProcessor
{

	private static Logger LOG = LoggerFactory.getLogger(LoginBeforeProcessor.class);
	
	public void process(CrawlerTaskConfig taskInfo ,MultiExtractorCrawler crawler) 
	{
		LoginConfig loginConfig = taskInfo.getLoginConfig();
		if(loginConfig!=null)
		{
			if(loginConfig.getNeedLogin()==LoginConfig.Login_No)
				LOG.warn("爬虫任务【"+taskInfo.getName()+"】 不需要登录！");
			else
			{
				try 
				{
					login(loginConfig);
				} catch (Exception e)
				{
					LOG.warn("爬虫任务【"+taskInfo.getName()+"】 登录出错，error:{}",e);
				}
			}
				
		}
		else
			LOG.warn("爬虫任务【"+taskInfo.getName()+"】 不需要登录！");
		
	}
	
	/**
	 * 执行模拟登录;
	 * 
	 * 该登录不适用于有验证码的情况;
	 *
	 * @param loginConfig 登录配置信息;
	 * 
	 * @return 登陆成功后的cookie信息;
	 */
	private String login(LoginConfig loginConfig) throws Exception
	{
		String loginUrl = loginConfig.getLoginUrl();
		
		if(StringUtils.isEmpty(loginUrl))
			throw new IllegalArgumentException("LoginUrl of loginConfig must not be null!");
		LOG.info("开始登录网站:{}" ,loginUrl);
		
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 
		 /*** -----------step1--------------***/
		 HttpGet get = new HttpGet(loginUrl);
		 
		 HttpHost proxy = new HttpHost("localhost", 8888, "http");  
	     RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
	     get.setConfig(config);
	     
		 get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
		 get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		 //get.addHeader("Cookie", "_reg_key_=GDzSFwgtdomLxxPTcxEo");
		 /** cookie设置**/
		 CookieStore cookieStore = new BasicCookieStore();
         HttpClientContext localContext = HttpClientContext.create();
         localContext.setCookieStore(cookieStore);
         
         CloseableHttpResponse  resp1 = null;
         HttpEntity entity1 = null;
         try 
         {
			  resp1 = httpClient.execute(get,localContext);
			  entity1 = resp1.getEntity();
	             
	         StatusLine statusLine1 = resp1.getStatusLine();
	         LOG.info("请求网站:{}的响应吗:{}" ,loginUrl,statusLine1.getStatusCode());
	         if (statusLine1.getStatusCode() >= 300) 
	         {
                 throw new HttpResponseException(
                		 statusLine1.getStatusCode(),
                		 statusLine1.getReasonPhrase());
             }
         } catch (ClientProtocolException e1) 
         {
        	 LOG.warn("请求网站:{}出错，error:{}" ,loginUrl,e1);
			throw e1;
		} catch (IOException e1)
         {
			 LOG.warn("请求网站:{}出错，error:{}" ,loginUrl,e1);
			 throw e1;
		}finally
		{
			InputStream is = null;
			try 
			{
				is = entity1.getContent();
				resp1.close();
				is.close();
			
			} catch (IOException e)
			{
				resp1 = null;
				is=null;
			}
		}
        /*** -----------step2--------------***/
         
         final String url2 = "https://www.oschina.net/action/user/before_login";
         
         HttpPost httpPost2 = new HttpPost(url2);
         httpPost2.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
         httpPost2.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
         
         //httpPost2.addHeader("Cookie", "_reg_key_=GDzSFwgtdomLxxPTcxEo");
         httpPost2.setConfig(config);
         
         List<org.apache.http.NameValuePair> formParams2 = new ArrayList<org.apache.http.NameValuePair>();
         formParams2.add(new BasicNameValuePair("email", ""));
        /** CloseableHttpResponse  resp2 = null;
         HttpEntity entity2 = null;
         try 
         {
			httpPost2.setEntity(new UrlEncodedFormEntity(formParams2));
			resp2 = httpClient.execute(httpPost2,localContext);
			entity2 = resp2.getEntity();
            StatusLine statusLine = resp2.getStatusLine();
            LOG.info("请求网站:{}的响应吗:{}" ,url2,statusLine.getStatusCode());
            
            System.out.println(JSON.toJSONString(entity2) );
            System.out.println(" Cookie 信息：" +JSON.toJSONString(cookieStore.getCookies()) );
            
         } catch (IOException e) 
         {
			
			e.printStackTrace();
		}finally
		{
			InputStream is = null;
			try 
			{
				is = entity2.getContent();
				resp2.close();
				is.close();
			
				
			} catch (IOException e)
			{
				resp1 = null;
				is=null;
			}
		}**/
         
        /* boolean hasCaptcha = loginConfig.isHasCaptcha(); //是否有验证码;
         if(hasCaptcha)
         {
        	 String captchaUrl = loginConfig.getCaptchaUrl();
        	 if(StringUtils.isEmpty(captchaUrl))
     			throw new IllegalArgumentException("CaptchaUrl of loginConfig must not be null if the hasCaptcha has been setted true !");
        	 CaptchaHelper.getInstance().getCaptcha(captchaUrl, loginUrl, localContext);
         }*/
         
         
         
         /*** -----------step3--------------***/
         try 
         {
        	 Set<NameValuePair> nameValuePairs = loginConfig.getNameValuePairs();
     		if(nameValuePairs == null || nameValuePairs.isEmpty())
     			throw new IllegalArgumentException("NameValuePairs of loginConfig must not be null!");
     		
     		 String formAction = loginConfig.getFormAction();
     		 if(StringUtils.isEmpty(formAction))
    			throw new IllegalArgumentException("formAction of loginConfig must not be null!");
     		  LOG.info("登录的formAction:{}的参数:{}" ,formAction, JSON.toJSONString(nameValuePairs));
     		  
              HttpPost httpPost = new HttpPost(formAction);
              httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
              httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
             
              httpPost.setConfig(config);
     	     
              List<org.apache.http.NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
              
              Iterator<NameValuePair> it = nameValuePairs.iterator();
              while(it.hasNext())
              {
             	 NameValuePair nameValue = it.next();
             	 formParams.add(new BasicNameValuePair(nameValue.name, nameValue.value));
              }
             httpPost.setEntity(new UrlEncodedFormEntity(formParams));

             CloseableHttpResponse  resp = httpClient.execute(httpPost,localContext);
             
           
             HttpEntity entity = resp.getEntity();
             StatusLine statusLine = resp.getStatusLine();
             
             LOG.info("登录的formAction:{}的响应吗:{}" ,formAction,statusLine.getStatusCode());
             if (statusLine.getStatusCode() >= 300) 
             {
                 throw new HttpResponseException(
                         statusLine.getStatusCode(),
                         statusLine.getReasonPhrase());
             }
             String result = EntityUtils.toString(entity, "UTF-8");
             System.out.println( "结果：" + result );
             if(entity!=null)
             {
            	 InputStream is = entity.getContent();
            	 is.close();
             }      
             resp.close();
             System.out.println(" 登录后 Cookie 信息：" +JSON.toJSONString(cookieStore.getCookies()) );
             System.out.println("登录返回信息："+result );
             
            
             
           
             
             return null;
             
         } catch (Exception e)
         {
        	 LOG.warn("登录网站:{} 失败,error:{}" ,loginUrl ,e);
        	 throw new RuntimeException("登录网站【"+loginUrl+"】失败！",e);
         }
        
        //return null;
		
	}
	
	
	
	public static void main(String[] args) 
	{
		
		String  loginUrl = "https://www.oschina.net/home/login";
		String formAction = "https://www.oschina.net/action/user/hash_login";
		LoginBeforeProcessor processor = new LoginBeforeProcessor();
		
		CrawlerTaskConfig taskInfo = new CrawlerTaskConfig();
		
		// [start 登录信息设置]
		LoginConfig loginConfig = new LoginConfig();
		loginConfig.setNeedLogin(LoginConfig.Login_Yes);
		
		loginConfig.setLoginUrl(loginUrl);
		loginConfig.setFormAction(formAction);
		Set<NameValuePair> nameValuePairs = new HashSet<LoginConfig.NameValuePair>();
		
		
		NameValuePair pair1 = new NameValuePair("email","644120242@qq.com");
		NameValuePair pair2 = new NameValuePair("pwd","b5572beec0fffc04e3240e78372f69eafe90b12a");
		
		NameValuePair pair3 = new NameValuePair("verifyCode","");
		NameValuePair pair4 = new NameValuePair("save_login","1");
		
		
		nameValuePairs.add(pair2);
		nameValuePairs.add(pair1);
		nameValuePairs.add(pair3);
		nameValuePairs.add(pair4);
		
		loginConfig.setNameValuePairs(nameValuePairs);
		// [end 登录信息设置 ]
		
		taskInfo.setLoginConfig(loginConfig);
		processor.process(taskInfo, null);
		
	}

}
