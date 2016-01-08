package intelli.crawler.common.dao;

import java.sql.SQLException;
import java.util.List;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.dao.mapper.AbstractMapper;

/**
 * 爬虫实体对象的存储方式,可能会有以下两种实现：
 * <br/>
 * <ul>
 * <li>mysql</li>
 * <li>hbase</li>
 * </ul>
 * @author penglong
 *
 */
public  abstract class CommonTableDao implements AbstractMapper<CommonTable>
{

	/**
	 * 判断表是否存在
	 * @param tableName 表名; 
	 * @return 
	 */
	public abstract boolean isTableExist(String tableName) throws SQLException;
	
	/**
	 * 根据提取的数据信息，创建表,表名为 CommonTable.tablename;
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	public abstract boolean createTable(CommonTable record) throws SQLException;
	
	/**
	 * 插入一条提取记录;
	 * 
	 */
	public  abstract Integer insert(CommonTable record) throws SQLException;

	/**
	 * 批量插入提取的记录;
	 */
	public abstract Integer batchInsert(List<CommonTable> records) throws SQLException ;

	@Override
	public Integer updateByPrimaryKey(CommonTable record) throws SQLException {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CommonTable> select(CommonTable record) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CommonTable selectByPrimaryKey(String id) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer deleteByPrimaryKey(String id) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
}
