package intelli.crawler.common.dao.hbase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RowkeyGenerator 
{
	
	private static final SimpleDateFormat DateFormate = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String generate(String tablename)
	{
		return tablename+getNowAsString()+(Math.random()*9000+1000);
	}
	
	private static String getNowAsString()
	{
		return DateFormate.format(new Date());
	}
}
