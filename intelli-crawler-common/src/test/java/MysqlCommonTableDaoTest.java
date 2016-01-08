import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.MysqlCommonTableDao;


/**
 *  测试
 * @author penglong
 *
 */
public class MysqlCommonTableDaoTest 
{
	
	public static void main(String[] args) 
	{
		IocManager iocManager = IocManager.getInstance();
		
		MysqlCommonTableDao  mysqlCommonTableDao = iocManager.getDao("mysqlCommonTableDao");
		//insertTest(mysqlCommonTableDao) ;
		
		batchInsertTest( mysqlCommonTableDao );
		
		
	}
	
	
	public static void testIsTableExist( MysqlCommonTableDao  mysqlCommonTableDao )
	{
		boolean result =false;
		try 
		{
			result = mysqlCommonTableDao.isTableExist("crawlertaskinfo_");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println( result );
	}

	
	public static void createTableTest( MysqlCommonTableDao  mysqlCommonTableDao )
	{
		CommonTable table = new CommonTable();
        
        List<PropertyInfo> props = new LinkedList<PropertyInfo>();
        PropertyInfo p1 = new  PropertyInfo("name", "h1.h1","车型");
        PropertyInfo p2 = new  PropertyInfo("price", "span.font_jiage","价格");
        PropertyInfo p3= new  PropertyInfo("unit", "span.font_wan","单位");
        
        props.add(p1);
        props.add(p2);
        props.add(p3);
        
        table.setProps(props);
        table.setTablename("58TongChengErShouChe");
        table.setTabledesc("58同城二手车");
		
		try
		{
			mysqlCommonTableDao.createTable(table);
			
		} catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	
	public static void  insertTest( MysqlCommonTableDao  mysqlCommonTableDao )
	{
		CommonTable table = new CommonTable();
        
        List<PropertyInfo> props = new LinkedList<PropertyInfo>();
        PropertyInfo p1 = new  PropertyInfo("name", "h1.h1","车型");
        PropertyInfo p2 = new  PropertyInfo("price", "span.font_jiage","价格");
        PropertyInfo p3= new  PropertyInfo("unit", "span.font_wan","单位");
        
        p1.setFieldvalue("1111");
        p2.setFieldvalue("1111");
        p3.setFieldvalue("1111");
        
        props.add(p1);
        props.add(p2);
        props.add(p3);
        
        table.setProps(props);
        table.setTablename("58TongChengErShouChe");
        table.setTabledesc("58同城二手车");
        try 
        {
			mysqlCommonTableDao.insert(table);
		} catch (SQLException e) 
        {
			e.printStackTrace();
		}
   }
	
	public static void  batchInsertTest( MysqlCommonTableDao  mysqlCommonTableDao )
	{
		List<CommonTable>  lst = new LinkedList<CommonTable>();
		for( int i = 0;i<3;i++)
		{
			CommonTable table = new CommonTable();
	        
	        List<PropertyInfo> props = new LinkedList<PropertyInfo>();
	        PropertyInfo p1 = new  PropertyInfo("name", "h1.h1","车型");
	        PropertyInfo p2 = new  PropertyInfo("price", "span.font_jiage","价格");
	        PropertyInfo p3= new  PropertyInfo("unit", "span.font_wan","单位");
	        
	        p1.setFieldvalue("1111" +i);
	        p2.setFieldvalue("1111"+i);
	        p3.setFieldvalue("1111" +i);
	        
	        props.add(p1);
	        props.add(p2);
	        props.add(p3);
	        
	        table.setProps(props);
	        table.setTablename("58TongChengErShouChe");
	        table.setTabledesc("58同城二手车");
	        lst.add(table);
		}
		
        try 
        {
			mysqlCommonTableDao.batchInsert(lst);
		} catch (SQLException e) 
        {
			e.printStackTrace();
		}
   }
	
	
}
