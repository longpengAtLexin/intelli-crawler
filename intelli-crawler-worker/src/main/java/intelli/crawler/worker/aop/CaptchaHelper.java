package intelli.crawler.worker.aop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class CaptchaHelper 
{
	private static Logger LOG = LoggerFactory.getLogger(CaptchaHelper.class);
	
	private static CaptchaHelper helper = new CaptchaHelper();
	
	private CaptchaHelper(){};
	
	public static CaptchaHelper getInstance()
	{
		return helper;
	}
	
	public String getCaptcha(String captchaUrl,String loginUrl ,HttpClientContext localContext) throws Exception
	{
		 CloseableHttpClient client = HttpClients.createDefault();
		 
		 HttpGet get = new HttpGet(captchaUrl);
		 
		 HttpHost proxy = new HttpHost("localhost", 8888, "http");  
	     RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		 get.setConfig(config);
		 
	     get.addHeader("Accept", "image/webp,*/*;q=0.8");
	     //get.addHeader("Accept-Encoding", "gzip,deflate");
	     //get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
	     get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
	     get.addHeader("Referer", loginUrl);
	     
	     CloseableHttpResponse  resp = null;
	     try 
	     {
				resp = client.execute(get, localContext);
				 HttpEntity entity = resp.getEntity();
				this.saveAsImageFile(entity);
				 StatusLine statusLine1 = resp.getStatusLine();
		         LOG.info("请求验证码:{}的响应吗:{}" ,captchaUrl,statusLine1.getStatusCode());
				if (statusLine1.getStatusCode() >= 300) 
		         {
	                 throw new HttpResponseException(
	                		 statusLine1.getStatusCode(),
	                		 statusLine1.getReasonPhrase());
	             }
	     } catch (UnsupportedOperationException e) 
	     {
	    	 LOG.warn("请求验证码【"+captchaUrl+"】 出错，error:{}",e);
				throw e;
		 } catch (IOException e)
	     {
			 LOG.warn("请求验证码【"+captchaUrl+"】 出错，error:{}",e);
			 throw e;
	     }finally
	     {
	    	 try 
				{
					resp.close();
				} catch (IOException e)
				{
					resp = null;
				}
	     }
		System.out.println( "返回的 cookie 信息 ：" +JSON.toJSONString(localContext.getCookieStore().getCookies() ));	
		return null;
	}
	
	/**
	 * 把响应实体 作为图片文件保存起来;
	 * @param entity 响应实体;
	 */
	private void saveAsImageFile(HttpEntity entity )
	{
		Random rand = new Random();
		try 
		{
			OutputStream outstream = new FileOutputStream(new File("C:/Users/penglong/Desktop/vercode"+rand.nextInt(100)+ ".png"));
			entity.writeTo(outstream);
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		CaptchaHelper captchaHelper = new CaptchaHelper();
		
		CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCookieStore(cookieStore);
		
		try 
		{
			captchaHelper.getCaptcha("https://www.oschina.net/action/user/captcha","https://www.oschina.net/home/login", localContext);
		} catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
