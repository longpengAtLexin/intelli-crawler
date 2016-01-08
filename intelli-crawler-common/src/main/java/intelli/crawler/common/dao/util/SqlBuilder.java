package intelli.crawler.common.dao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;

public final class SqlBuilder 
{
	public static final String DbName = "crawlerdb";
	
	/**
	 * 根据提取的记录信息，创建含占位符的sql语句;
	 * @param commonTable
	 * @return
	 */
	public static String buildSql(CommonTable commonTable)
	{
		StringBuilder  sb = new StringBuilder();
		sb.append("Insert into ");
		sb.append(commonTable.getTablename());
		List<PropertyInfo> propLst = commonTable.getProps();
		sb.append("(");
		for(PropertyInfo prop :propLst)
		{
			sb.append(prop.getFieldname());
			sb.append(" ,");
		}
		sb.append("url , requestTime ,");
		sb = new  StringBuilder (sb.substring(0, sb.length()-1) );
		sb.append(")");
		sb.append("values (");
		
		List<PropertyInfo> props = commonTable.getProps();
		
		for(PropertyInfo prop :props)
		{
			sb.append("#{");
			sb.append(prop.getFieldname());
			sb.append("},");
		}
		sb.append("#{url} , #{requestTime} ,");
		sb = new  StringBuilder (sb.substring(0, sb.length()-1) );
		sb.append(")");
		return sb.toString();
	}
	
	
	/**
	 * 创建 sql 占位符字段的 名称-值 映射;
	 * @param commonTable
	 * @return
	 */
	public static Map<String,Object> buildValueMap(CommonTable commonTable)
	{
		
		Map<String,Object> valueMap = new HashMap<String,Object>();
		List<PropertyInfo> props = commonTable.getProps();
		for(PropertyInfo prop :props)
		{
			valueMap.put(prop.getFieldname(), prop.getFieldvalue());
		}
		valueMap.put("url", commonTable.getUrl());
		valueMap.put("requestTime", commonTable.getRequestTime());
		
		return valueMap;
		
	}
	
	/**
	 * 根据提取的数据信息（多条记录）构建批量插入的语句(适用于mysql);
	 * @param records 待插入的记录;
	 * @return
	 */
	public static String buildBatchInsertSql(List<CommonTable> records)
	{
		StringBuilder  sb = new StringBuilder();
		sb.append("Insert into ");
		sb.append(records.get(0).getTablename());
		List<PropertyInfo> propLst = records.get(0).getProps();
		sb.append("(");
		for(PropertyInfo prop :propLst)
		{
			sb.append(prop.getFieldname());
			sb.append(" ,");
		}
		sb.append("url , requestTime ,");
		sb = new  StringBuilder (sb.substring(0, sb.length()-1) );
		sb.append(")");
		sb.append("values ");
		
		for(CommonTable commonTable : records)
		{
			sb.append("(");
			
			List<PropertyInfo> props = commonTable.getProps();
			
			for(PropertyInfo prop :props)
			{
				sb.append(prop.getFieldvalue());
				sb.append(",");
			}
			sb.append(commonTable.getUrl());
			sb.append(",");
			sb.append(commonTable.getRequestTime());
			sb.append(",");
			
			sb = new  StringBuilder (sb.substring(0, sb.length()-1) );
			sb.append("),");
			
		}
		return sb.substring(0, sb.length()-1);
	}
	
}
