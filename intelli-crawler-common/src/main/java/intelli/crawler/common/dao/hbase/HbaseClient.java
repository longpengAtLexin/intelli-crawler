package intelli.crawler.common.dao.hbase;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseClient 
{
	private static Logger LOG = LoggerFactory.getLogger(HbaseClient.class);
	
	private static Configuration configuration = null;
	
   public static final String DefaultColumnFamily = "info";
	static 
	{
		try 
		{
			if(System.getProperty("os.name").toLowerCase().indexOf("windows")>-1)
			 {
				 File workaround = new File(".");
				 new File("./bin").mkdirs();
				 new File("./bin/winutils.exe").createNewFile();
				 System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());
				 
			 }
			configuration = HBaseConfiguration.create();
		} catch (IOException e) 
		{
			LOG.info("初始化HbaseClient出错,error:{}",e);
		}
	}
	
	public static boolean isTableExist(String tableName) throws SQLException
	{
		HBaseAdmin hAdmin = null;
		try 
		{
			hAdmin = new HBaseAdmin(configuration);

			if (hAdmin.tableExists(tableName)) 
				return true;
			else
				return false;

			
		} catch (IOException e)
		{
			LOG.error("访问表:{}是否存在出错,error:{}", tableName,e);
			throw new SQLException(e);
		} finally 
		{
			try 
			{
				if (hAdmin != null)
				{
					hAdmin.close();
				}
			} catch (IOException e) 
			{
				hAdmin = null;
			}
		}
	}
	
	
	public static List<String> listTableExist() throws SQLException
	{
		List<String> names = new LinkedList<String>();
		HBaseAdmin hAdmin = null;
		try 
		{
			hAdmin = new HBaseAdmin(configuration);

			TableName[] tableNameArr = hAdmin.listTableNames();
			for(int i=0;i<tableNameArr.length;i++)
			{
				names.add(tableNameArr[i].getNameAsString());
			}

		} catch (IOException e)
		{
			LOG.error("查看所有表名出错,error:{}",e);
			throw new SQLException(e);
		} finally 
		{
			try 
			{
				if (hAdmin != null)
				{
					hAdmin.close();
				}
			} catch (IOException e) 
			{
				hAdmin = null;
			}
		}
		return names;
	}
	
	
	 
	public static void createTable(String tableName, String[] columnFamilys, boolean isDele) throws SQLException
	{
		HBaseAdmin hAdmin = null;
		try {
			hAdmin = new HBaseAdmin(configuration);

			if (hAdmin.tableExists(tableName)) {
				LOG.info("表: {} 已经存在", tableName);
				if (isDele) 
				{
					hAdmin.disableTable(tableName);
					hAdmin.deleteTable(tableName);
				} else
					return;
			}

			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));

			// 描述列族
			for (String columnFamily : columnFamilys) 
			{
				tableDesc.addFamily(new HColumnDescriptor(columnFamily));
			}

			// 建表
			hAdmin.createTable(tableDesc);
			LOG.info("成功创建表: {}", tableName);
		} catch (Throwable e)
		{
			LOG.error("建表:{} 错误,error:{}", tableName,e);
			throw new SQLException(e);
		} finally 
		{
			try 
			{
				if (hAdmin != null) 
				{
					hAdmin.close();
				}
			} catch (IOException e) 
			{
				hAdmin = null;
			}
		}
	}
	/**
	 * 按照 公司约定建表,每个表只有一个列族，info.
	 * @param tableName
	 * @param isDele
	 * @throws SQLException
	 */
	public static void createTableStandard(String tableName, boolean isDele) throws SQLException
	{
		HBaseAdmin hAdmin = null;
		try 
		{
			hAdmin = new HBaseAdmin(configuration);

			if (hAdmin.tableExists(tableName)) 
			{
				LOG.info("表: {} 已经存在", tableName);
				if (isDele) 
				{
					hAdmin.disableTable(tableName);
					hAdmin.deleteTable(tableName);
				} else
					return;
			}

			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
			tableDesc.setMemStoreFlushSize(268435456);
			tableDesc.setValue(HTableDescriptor.SPLIT_POLICY, "HexStringSplit"); //NUMREGIONS => 20 
			
			HColumnDescriptor column = new HColumnDescriptor(DefaultColumnFamily);
			column.setMaxVersions(1);
			column.setCompressionType(Algorithm.SNAPPY);
			column.setDataBlockEncoding(DataBlockEncoding.PREFIX_TREE);
			tableDesc.addFamily(column);
			// 建表
			hAdmin.createTable(tableDesc);
			LOG.info("成功创建表: {}", tableName);
		}catch(Throwable e)
		{
			LOG.error("建表:{} 错误,error:{}", tableName,e);
			throw new SQLException(e);
		} finally 
		{
			try 
			{
				if (hAdmin != null) 
				{
					hAdmin.close();
				}
			} catch (IOException e) 
			{
				hAdmin = null;
			}
		}
	}
	
	/**
	 * 
	 * 按照 公司约定建表,每个表只有一个列族，info,其余作为qualifier
	 * @param record
	 * @throws SQLException
	 */
	public static void insertTableStandard(CommonTable record) throws SQLException
	{
		String tablename = record.getTablename().toUpperCase();
		String rowkey = RowkeyGenerator.generate(tablename);
		List<PropertyInfo> propLst = record.getProps();
		Put p = new Put(Bytes.toBytes(rowkey));
		for(PropertyInfo prop :propLst)
		{
			p.add(Bytes.toBytes(DefaultColumnFamily), Bytes.toBytes(prop.getFieldname()), Bytes.toBytes(prop.getFieldvalue()));
		}
		
		p.add(Bytes.toBytes(DefaultColumnFamily), Bytes.toBytes("URL"), Bytes.toBytes(record.getUrl()));
		p.add(Bytes.toBytes(DefaultColumnFamily), Bytes.toBytes("REQUESTTIME"), Bytes.toBytes(record.getRequestTime().getTime()));
		
		HTableInterface table = null;
		try 
		{
			table = new HTable(configuration, tablename);
			table.put(p);
			
		} catch (IOException e)
		{
			LOG.error("hbase插入表:{},数据错误,error:{}", tablename,e);
			throw new SQLException(e);
		} finally 
		{
			try
			{
				if (table != null)
					table.close();
			} catch (IOException e)
			{
				table = null;
			}
		}
		
	}
	
	public static void putDatas(String tableName, List<HbaseCell> hbCellList) throws SQLException
	{
		HTableInterface table = null;
		try 
		{
			table = new HTable(configuration, tableName);
			
			List<Put> listPuts = new ArrayList<Put>();
			for (HbaseCell data : hbCellList) 
			{
				Put put = packToPut(data);
				listPuts.add(put);
			}
			table.put(listPuts);
			LOG.info("插入: {} 个put", listPuts.size());
		} catch (IOException e)
		{
			LOG.error("hbase插入表:{},数据错误,error:{}", tableName,e);
			throw new SQLException(e);
		} finally 
		{
			try
			{
				if (table != null)
					table.close();
			} catch (IOException e)
			{
				table = null;
			}
		}
	}
	
	
	private static Put packToPut(String rowKey, String colFamily, String column, String colValue) 
	{
		Put p = new Put(Bytes.toBytes(rowKey));
		if(StringUtils.isEmpty(column))
			p.add(Bytes.toBytes(colFamily), null, Bytes.toBytes(colValue));
		else	
			p.add(Bytes.toBytes(colFamily), Bytes.toBytes(column), Bytes.toBytes(colValue));
		return p;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private static Put packToPut(HbaseCell data) 
	{
		List<String> dataValues = data.getAllVlalue();
		return packToPut(dataValues.get(0), dataValues.get(1), dataValues.get(2), dataValues.get(3));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
