import java.util.concurrent.CountDownLatch;

import intelli.crawler.common.comm.RpcResponse;
import intelli.crawler.common.service.CrawlerTaskService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;


public class SpringRmiTest 
{
	public static void main(String[] args) 
	{
	
		final ApplicationContext ctx = new ClassPathXmlApplicationContext( "conf/spring-context.xml");  
		
		final int ThreadNum= 10;
		
		final CountDownLatch  startLatch = new CountDownLatch(1);
		
		final CountDownLatch  endtLatch = new CountDownLatch(ThreadNum);
		
		
		
		Thread[] threadArr = new  Thread[ThreadNum];
		for(int i = 0; i<ThreadNum;i++)
		{
			threadArr[i] =  new Thread(new Runnable() {
				
				public void run() 
				{
					try 
					{
						startLatch.await();
					} catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					CrawlerTaskService crawlerTaskService = (CrawlerTaskService) ctx.getBean("crawlerTaskService");  
			        
					RpcResponse resp = crawlerTaskService.start("1111111111111");
					
					System.out.println(JSON.toJSONString(resp)  );
					
					endtLatch.countDown();
				}
			});
			
			threadArr[i].start();
		}
		
		
		
		long startTime = System.currentTimeMillis();
		
		startLatch.countDown();
	
		try 
		{
			endtLatch.await();
		} catch (InterruptedException e)
		{
			
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(   "共耗时 ："+ (endTime-startTime) +"ms ; 平均耗时："  +(endTime-startTime) /ThreadNum+"ms");
		
	}
	
	
	
	
	
	
	
	
	
	
}
