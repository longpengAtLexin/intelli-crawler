import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfigInfo;
import intelli.crawler.common.config.PagingConfigOld;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.worker.core.DefaultCrawlerTaskServiceImpl;


/**
 * 分页爬虫执行测试;
 * @author penglong
 *
 */

@Deprecated
public class PagingCrawlerExecutorTest 
{
	
	
	public static void main(String[] args) 
	{
		//testRegex();
		/*testRegex();*/
		/**
		 * 
		 */
		testCrawler();
		
	}
	
	/**
	 * 分页爬取 58同城 二手车信息;
	 */
	public static void testCrawler()
	{
		CrawlerTaskConfigInfo info = new CrawlerTaskConfigInfo();
		
		//----------------- 起始url 设置;
		String seedurl1 = "http://sz.58.com/ershouche/pn2/?minprice=8_10&utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti&PGTID=174577279189690766449314300&ClickID=1";
		String seedurl2 ="http://sz.58.com/ershouche/?minprice=8_10&utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti&PGTID=192740215189690830325488781&ClickID=1";
		//String seedurl3 = "http://sz.58.com/ershouche/23911618728508x.shtml?utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti&psid=134333535189690961099004230&entinfo=23911618728508_0&PGTID=134333535189690961099004230&ClickID=1&iuType=p_0";
		Set<String> seedurls = new HashSet<String>();
		
		//seedurls.add(seedurl1);
		seedurls.add(seedurl2);
		//seedurls.add(seedurl3);
		
		//---------------- 爬取的目标 url 模式;
		String urlpattern1 = "http://sz.58.com/ershouche/pn.*";
		String urlpattern2 = "http://sz.58.com/ershouche/.*";
		String urlpattern3 = "-.*\\.(jpg|png|gif).*";
		
		Set<String> urlpatterns = new HashSet<String>();
		urlpatterns.add(urlpattern1);
		urlpatterns.add(urlpattern2);
		urlpatterns.add(urlpattern3);
		
		//--------------- 数据提取页面的 url 的模式;
		String  extractUrlPattern = "http://sz.58.com/ershouche/[0-9]+x.shtml.*";
		//urlpatterns.add(extractUrlPattern);
		//-------------------- 分页配置;
		
		PagingConfigOld pagingConfig = new PagingConfigOld();
		pagingConfig.setIsPaging((short)1);
		pagingConfig.setPagingName("pn");
		pagingConfig.setPagingType((short)1);
		pagingConfig.setPagingSeedUrl(seedurl1);
		pagingConfig.setPagingNum((short)2);
		
		//-----------------------  数据提取信息配置;
		CommonTable table = new CommonTable();
        
        List<PropertyInfo> props = new LinkedList<PropertyInfo>();
        PropertyInfo p1 = new  PropertyInfo("name", "h1.h1","车型");
        PropertyInfo p2 = new  PropertyInfo("price", "span.font_jiage","价格");
        PropertyInfo p3 = new  PropertyInfo("unit", "span.font_wan","单位");
        
        props.add(p1);
        props.add(p2);
        props.add(p3);
        
        table.setProps(props);
        table.setTablename("58TongChengErShouChe");
        table.setTabledesc("58同城二手车");
        table.setExtractUrlPattern(extractUrlPattern);
		
		
		//-------------------------------------
        info.setSeedurls(seedurls);
		info.setUrlpatterns(urlpatterns);
		
		info.setPagingConfig(pagingConfig);
		info.setTable(table);
		
		info.setName("58同城二手车");
		info.setCrawlDepth(2);
		
		info.setThreadNum(50);
		
		String crawlerTaskId = UUID.randomUUID().toString();
		info.setId(crawlerTaskId);
		//----------------------
		DefaultCrawlerTaskServiceImpl.crawlerRecordCounterMap.put (crawlerTaskId,new AtomicInteger(0));
		//PagingCrawlerExecutor pagingCrawlerExecutor = new PagingCrawlerExecutor( info);
		
		
		//CrawlResult  result = pagingCrawlerExecutor.start();
		//System.out.println("爬虫结果……");
		//System.out.println( JSON.toJSONString(result) );
	}
	
	private static void testRegex()
	{
	  String  extractUrlPattern = "http://sz.58.com/ershouche/pn.*";
		//String  extractUrlPattern = "http://sz.58.com/ershouche/[0-9]+x.shtml?.*";
		
		Pattern pattern = Pattern.compile(extractUrlPattern);
		Matcher matcher = pattern.matcher("http://sz.58.com/ershouche/pn56");
		boolean b= matcher.matches();
		System.out.println(b );
	}
	
	
	private static void testRegex2()
	{
		String  extractUrlPattern = "http://www.zhihu.com/people/[^/]*";
		
		Pattern pattern = Pattern.compile(extractUrlPattern);
		Matcher matcher = pattern.matcher("http://www.zhihu.com/people/ou-yang-jie-90");
		boolean b= matcher.matches();
		System.out.println(b );
	}
}
