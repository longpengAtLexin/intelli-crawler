package intelli.crawler.worker;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 爬虫工作线程.
 * @author penglong
 *
 */
public class CrawlerWorkerMain 
{
	private static Logger LOG = LoggerFactory.getLogger(CrawlerWorkerMain.class);
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		try 
	     {
			long startTime = System.currentTimeMillis();
			Thread.currentThread().setUncaughtExceptionHandler( new UncaughtExceptionHandler() 
			{
				public void uncaughtException(Thread t, Throwable e) 
				{
					LOG.warn("The CrawlerWorkerMain process will exit  because ocurred error: ", e);
					System.exit(-1);
				}
			});
		 new ClassPathXmlApplicationContext("conf/spring-context.xml");  
		 
		 long endTime = System.currentTimeMillis();
		 LOG.info("The crawler worker process has launched successfully ,cost time {} ms!" ,(endTime - startTime));
         final CountDownLatch latch = new CountDownLatch(1);
         
         latch.await();
		} catch (Throwable e) 
	     {
			LOG.error("Error occurred while crawler worker process launched,error : ", e);
			System.exit(-1);
		}
		
		
	}
	
	
}
