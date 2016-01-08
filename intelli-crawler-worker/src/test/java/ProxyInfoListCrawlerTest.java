import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.worker.core.DefaultCrawlerTaskServiceImpl;
import intelli.crawler.worker.crawlers.StandardCrawlerExecutor;


/**
 * 代理列表爬虫服务器;
 * @author penglong
 *
 */
public class ProxyInfoListCrawlerTest 
{
	public static void main(String[] args) 
	{
		CrawlerTaskConfig info = new CrawlerTaskConfig();
		
		String seedurl1 = "http://www.proxy360.cn/Region/China";
		
		Set<String> seedurls = new HashSet<String>();
		seedurls.add(seedurl1);
		
		String urlpattern1 = "http://www.proxy360.cn/Region/.*";
		String urlpattern2 = "";
		String urlpattern3 = "-.*\\.(jpg|png|gif).*";
		
		Set<String> urlpatterns = new HashSet<String>();
		urlpatterns.add(urlpattern1);
		//urlpatterns.add(urlpattern2);
		urlpatterns.add(urlpattern3);
		
		String  extractUrlPattern = "http://www.proxy360.cn/Region/.*";
		
		CommonTable table = new CommonTable();
		List<PropertyInfo> props = new LinkedList<PropertyInfo>();
        PropertyInfo p1 = new  PropertyInfo("name", "div.proxylistitem","代理信息");
        props.add(p1);
     
        List<CommonTable> tables = new LinkedList<CommonTable>();
        tables.add(table);
        
        
        table.setProps(props);
        table.setTablename("ProxyInfo");
        table.setTabledesc("代理信息");
        table.setExtractUrlPattern(extractUrlPattern);
        
        table.setNumPerPage(CommonTable.MutiNumPerPage); // 每行多条记录;
        
      //-------------------------------------
        info.setSeedurls(seedurls);
		info.setUrlpatterns(urlpatterns);
		
		
		info.setTables(tables);
		
		info.setName("代理信息");
		info.setCrawlDepth(1);
		
		info.setThreadNum(50);
		
		String crawlerTaskId = UUID.randomUUID().toString();
		info.setId(crawlerTaskId);
		
		//----------------------
		DefaultCrawlerTaskServiceImpl.crawlerRecordCounterMap.put (crawlerTaskId,new AtomicInteger(0));
		
		StandardCrawlerExecutor crawler = new StandardCrawlerExecutor(info);
		
		crawler.start();
		
		
        
        
        
        
        
        
        
	}
}
