package intelli.crawler.common.dao;

import intelli.crawler.common.dao.util.TableExistenceInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * mysql 表存在信息;
 * @author penglong
 *
 */
public class MysqlTableExistence 
{
	private static Logger LOG = LoggerFactory.getLogger(MysqlCommonTableDao.class);
	
	public static final Map<String,Boolean> tableExistenceMap = Collections.synchronizedMap(new HashMap<String,Boolean>());
	
	static 
	{
		initTableExistenceInfo();
	}
	
	private static void initTableExistenceInfo()
	{
		try 
		{
			com.github.abel533.sql.SqlMapper sqlMapper = IocManager.getInstance().getService("sqlMapper");
			final String sql = "show tables";
			List<TableExistenceInfo> lst = sqlMapper.selectList(sql,TableExistenceInfo.class);
			for(TableExistenceInfo tmp : lst)
			{
				tableExistenceMap.put(tmp.Tables_in_crawlerdb, Boolean.TRUE);
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
