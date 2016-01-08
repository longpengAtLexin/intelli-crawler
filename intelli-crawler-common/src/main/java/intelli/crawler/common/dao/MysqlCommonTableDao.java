package intelli.crawler.common.dao;



import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.util.SqlBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("mysqlCommonTableDao")
public class MysqlCommonTableDao extends CommonTableDao
{
	private static Logger LOG = LoggerFactory.getLogger(MysqlCommonTableDao.class);
	
	
	@Autowired
    private com.github.abel533.sql.SqlMapper sqlMapper;

	public MysqlCommonTableDao()
	{
		// 初始化 tableMap;看  哪些表已经创建
		
	}
	
	@Override
	public Integer insert(CommonTable record) throws SQLException {
		beforeDml( record );
		String sql = SqlBuilder.buildSql(record);
		Map<String,Object> valueMap = SqlBuilder.buildValueMap(record);
		return sqlMapper.insert(sql, valueMap);
	}

	@Override
	public Integer batchInsert(List<CommonTable> records) throws SQLException 
	{
		if(records==null ||records.size()==0)
			return null;
		beforeDml( records.get(0) );
		String sql = SqlBuilder.buildBatchInsertSql(records);
		System.out.println( sql );
		
		 return sqlMapper.insert(sql);
	}

	
	@Override
	public boolean isTableExist(String tableName) throws SQLException
	{
		final String sql = "select  table_name  from information_schema.TABLES where table_name = '"+tableName+"' ";
		List<Map<String, Object>> lst = sqlMapper.selectList(sql);
		return lst.size()>0;
	}

	@Override
	public boolean createTable(CommonTable record) throws SQLException 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ");
		sb.append(record.getTablename().toLowerCase());
		sb.append("(");
		List<PropertyInfo> props = record.getProps();
		for(PropertyInfo prop :props)
		{
			sb.append("`");
			sb.append(prop.getFieldname());
			sb.append("`");
			sb.append("	varchar(200)"); // 也间接说明爬虫数据不适合存放在mysql 数据库中;
			sb.append(",");
		}
		sb.append("`url` 	text  not null, ");
		sb.append("`requestTime` 	timestamp  not null, ");
		
		sb.append("id int unsigned primary key auto_increment");
		sb.append(") default charset=utf8");
		sqlMapper.update(sb.toString());
		return true;
	}
	
	/**
	 *  易引发线程安全性问题,所以用 link MysqlCommonTableDao.class 锁保护起来;
	 * @param record
	 * @throws SQLException
	 */
	@GuardedBy("MysqlCommonTableDao.class")
	private void beforeDml( CommonTable record ) throws SQLException
	{
		
		synchronized (MysqlCommonTableDao.class) 
		{
			Boolean existTable = MysqlTableExistence.tableExistenceMap.get(record.getTablename().toLowerCase());
			if(existTable==null|| existTable.booleanValue()==false)
			{
				LOG.warn("库中无表:{},尝试创建.",record.getTablename().toLowerCase());
				createTable(record);
				LOG.warn("创建表:{}成功.",record.getTablename().toLowerCase());
				MysqlTableExistence.tableExistenceMap.put(record.getTablename().toLowerCase(), Boolean.TRUE);
			}
		}
	}
	
}
