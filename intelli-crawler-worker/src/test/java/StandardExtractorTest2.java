import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfigInfo;
import intelli.crawler.common.config.PropertyInfo;




/**
 * 爬一页博客园;
 * @author penglong
 *
 */

@Deprecated
public class StandardExtractorTest2 
{
	public static void main(String[] args) 
	{
		
		/*testRegex();*/
		testCrawler();
		
	}
	
	/**
	 * 爬博客园内容;
	 */
	public static void testCrawler()
	{
		
		
		CrawlerTaskConfigInfo info = new CrawlerTaskConfigInfo();
		
		String seedurl1 = "http://www.cnblogs.com/yakun/p/3589437.html";
		
		Set<String> seedurls = new HashSet<String>();
		seedurls.add(seedurl1);
		
		String urlpattern1 = "http://www.cnblogs.com/\\w+/p/[0-9]+\\.html";
		Set<String> urlpatterns = new HashSet<String>();
		urlpatterns.add(urlpattern1);
		
		String  extractUrlPattern = "http://www.cnblogs.com/\\w+/p/[0-9]+\\.html";
		
		CommonTable table = new CommonTable();
        
        List<PropertyInfo> props = new LinkedList<PropertyInfo>();
        PropertyInfo p1 = new  PropertyInfo("titile", "h1.postTitle","标题");
        PropertyInfo p2 = new  PropertyInfo("author", "div.weiboShow_developer_detail","作者");
        PropertyInfo p3 = new  PropertyInfo("summary", "div#cnblogs_post_body > p > span","摘要");
        p3.setCounter((short)2);
        props.add(p1);
        props.add(p2);
        props.add(p3);
        
        table.setProps(props);
        table.setTablename("CnBlogs");
        table.setTabledesc("博客园信息");
        table.setExtractUrlPattern(extractUrlPattern);
        
        info.setSeedurls(seedurls);
		info.setUrlpatterns(urlpatterns);
		
		info.setTable(table);
        
		info.setName("58同城二手车");  // name 是必填信息;
		info.setCrawlDepth(5);
		
		info.setThreadNum(5);
		
		//StandardCrawlerExecutor executor = new StandardCrawlerExecutor(info);
		
		//executor.start();
	}
}
