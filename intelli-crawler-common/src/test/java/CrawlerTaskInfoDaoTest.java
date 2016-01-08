import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import intelli.crawler.common.dao.CrawlerTaskInfoDao;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.model.CountModel;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.dao.model.PagingCrawlerTaskInfoPo;


public class CrawlerTaskInfoDaoTest 
{

	public static void main(String[] args) {
		
		test();
		//testCount();
		//testPaging();
	}
	
	
	public static void test()
	{
	IocManager iocManager = IocManager.getInstance();
		
		CrawlerTaskInfoDao  crawlerTaskInfoDao = iocManager.getDao("crawlerTaskInfoDao");
		
		for(int i=1;i<100;i++){
		
		CrawlerTaskInfoPo po = new CrawlerTaskInfoPo();

		po.setId("任务"+i);
		po.setPid(UUID.randomUUID().toString());
		po.setConfstr("1111111111111111111");
		po.setCreateTime(new Date());
		po.setModifyTime(new Date());
		
		po.setName("任务"+i);
		
		long startTime = System.currentTimeMillis();
		//CrawlerTaskInfoPo po = new CrawlerTaskInfoPo();

		//po.setPid("e5d4b993-aa4d-42c7-a543-63ff8a8870e6");
	
		
		try 
		{
			crawlerTaskInfoDao.insert(po);
			
			//CrawlerTaskInfoPo po = crawlerTaskInfoDao.selectByPrimaryKey("f6558caf-7bf8-4b1c-b196-dc6a367c976c");
			
			//List<CrawlerTaskInfoPo> list  = crawlerTaskInfoDao.select(po);
			
			long endTime = System.currentTimeMillis();
			
			System.out.println( endTime - startTime);
			//System.out.println( JSON.toJSONString(list) );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		
		System.out.println("成功！" );
	}
	
	
	public static void testCount()
	{
		IocManager iocManager = IocManager.getInstance();
		
		CrawlerTaskInfoDao  crawlerTaskInfoDao = iocManager.getDao("crawlerTaskInfoDao");
		try {
			CountModel  count  = crawlerTaskInfoDao.count(null);
			
			System.out.println( JSON.toJSONString(count) );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void testPaging()
	{
		IocManager iocManager = IocManager.getInstance();
		
		CrawlerTaskInfoDao  crawlerTaskInfoDao = iocManager.getDao("crawlerTaskInfoDao");
		
		try
		{
			PagingCrawlerTaskInfoPo po = new PagingCrawlerTaskInfoPo();
			
			po.setStart(1);
			po.setLimit(3);
			
			List<CrawlerTaskInfoPo>  pos = crawlerTaskInfoDao.selectPaging(po);
			System.out.println( JSON.toJSONString(pos) );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
