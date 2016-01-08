package intelli.crawler.common.dao.hbase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.CommonTableDao;

/**
 * 抽取对象dao操作,Hbase 实现;
 * @author penglong
 *
 * * 基于 template.hbase,公司操作hbase的一个规范标准;
 */
public class HbaseCommonTableDao  extends CommonTableDao
{

	private static Logger LOG = LoggerFactory.getLogger(HbaseCommonTableDao.class);
	
	@Override
	public boolean isTableExist(String tableName) throws SQLException 
	{
		return HbaseClient.isTableExist(tableName);
	}

	@Override
	public boolean createTable(CommonTable record) throws SQLException
	{
		
		HbaseClient.createTableStandard(record.getTablename().toUpperCase(), true);
		return true;
	}

	@Deprecated
	private void createInternalOld(CommonTable record) throws SQLException
	{
		List<String> columnFamilyList = new LinkedList<String>();
		List<PropertyInfo> props = record.getProps();
		for(PropertyInfo prop :props)
		{
			columnFamilyList.add(prop.getFieldname().toUpperCase());
		}
		columnFamilyList.add("URL");
		columnFamilyList.add("REQUESTTIME");
		String[] arr= columnFamilyList.toArray(new String[]{});
		HbaseClient.createTable(record.getTablename().toUpperCase(), arr, true);
		
	}
	
	
	@Override
	public Integer insert(CommonTable record) throws SQLException
	{
		beforeDml(record);
		HbaseClient.insertTableStandard(record);
		return 1;
	}
	
	@Deprecated
	private void insertInternalOld( CommonTable record ) throws SQLException
	{
		List<HbaseCell> cellList = new ArrayList<HbaseCell>();
		
		List<PropertyInfo> propLst = record.getProps();
		String tablename = record.getTablename().toUpperCase();
		
		String rowkey = RowkeyGenerator.generate(tablename);
		for(PropertyInfo prop :propLst)
		{
			HbaseCell cell = new HbaseCell(rowkey, prop.getFieldname().toUpperCase(), null, prop.getFieldvalue());
			cellList.add(cell);
		}
		HbaseCell cell1 = new HbaseCell(rowkey, "URL", null, record.getUrl());
		HbaseCell cell2 = new HbaseCell(rowkey, "REQUESTTIME", null, String.valueOf(record.getRequestTime().getTime()));
		cellList.add(cell1);
		cellList.add(cell2);
		
		beforeDml(record);
		HbaseClient.putDatas(record.getTablename().toUpperCase(), cellList);
	}

	@Override
	public Integer batchInsert(List<CommonTable> records) throws SQLException 
	{
		beforeDml(records.get(0));
		for(CommonTable record :records)
		{
			insert(record);
		}
		return records.size();
	}
	
	@GuardedBy("HbaseCommonTableDao.class")
	private void beforeDml( CommonTable record ) throws SQLException
	{
		synchronized (HbaseCommonTableDao.class) 
		{
			Boolean existTable = HbaseTableExistence.tableExistenceMap.get(record.getTablename().toUpperCase());
			if(existTable==null|| existTable.booleanValue()==false)
			{
				LOG.warn("库中无表:{},尝试创建.",record.getTablename().toUpperCase());
				createTable(record);
				LOG.warn("创建表:{}成功.",record.getTablename());
				HbaseTableExistence.tableExistenceMap.put(record.getTablename().toUpperCase(), Boolean.TRUE);
			}
		}
	}

}
