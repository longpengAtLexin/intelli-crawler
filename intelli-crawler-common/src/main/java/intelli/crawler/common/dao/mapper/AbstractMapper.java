package intelli.crawler.common.dao.mapper;

import java.sql.SQLException;
import java.util.List;




/**
 * 
 * @author penglong
 *
 * @param <T>  实体数据;
 */
public interface AbstractMapper<T>
{
    Integer  insert(T record) throws SQLException ;
    
    Integer  batchInsert(List<T>  records) throws SQLException ;
    
    Integer updateByPrimaryKey(T record) throws SQLException ;
    
    List<T>  select(T record) throws SQLException ;
    
    T selectByPrimaryKey(String id)  throws SQLException ;
    
    Integer  deleteByPrimaryKey(String id) throws SQLException ;
    
    
}
