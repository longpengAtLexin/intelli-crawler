import intelli.crawler.common.dao.hbase.HbaseClient;


import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class HbaseClientTest 
{
	
	
	public static void main(String[] args) 
	{
	
		try {
			List<String> names = HbaseClient.listTableExist();
			
			System.out.println(JSON.toJSONString(names) );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
