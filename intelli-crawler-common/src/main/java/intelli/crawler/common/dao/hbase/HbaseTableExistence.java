package intelli.crawler.common.dao.hbase;


import intelli.crawler.common.dao.MysqlCommonTableDao;
import intelli.crawler.common.dao.MysqlTableExistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * hbase中表存在信息;
 * @author penglong
 *
 */
public class HbaseTableExistence 
{
	
	private static Logger LOG = LoggerFactory.getLogger(MysqlCommonTableDao.class);
	
	public static final Map<String,Boolean> tableExistenceMap = Collections.synchronizedMap(new HashMap<String,Boolean>());
	
	static 
	{
		initTableExistenceInfo();
	}
	
	/**
	 * 初始化表存在信息;
	 */
	private static void initTableExistenceInfo()
	{
		try 
		{
			List<String> lst = HbaseClient.listTableExist();
			for(String tablename : lst)
			{
				tableExistenceMap.put(tablename.toUpperCase(), Boolean.TRUE);
			}
		} catch (Throwable e) 
		{
			LOG.info("初始化表的存在信息失败:", e);
		}
	}
	
	public static void main(String[] args) 
	{
		System.out.println(JSON.toJSONString(MysqlTableExistence.tableExistenceMap) );
	}

}
