package intelli.crawler.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import intelli.crawler.common.dao.mapper.ExtractedUrlMapper;
import intelli.crawler.common.dao.model.ExtractedUrl;

/**
 * 保存已经抽取数据的url 记录,
 * 可能有两种实现方式：
 * <ul>
 * <li> Mysql</li>
 * <li> Hbase</li>
 * </ul>
 * @author penglong
 *
 */
@Service("mysqlExtractedUrlDao")
public class MysqlExtractedUrlDao  implements ExtractedUrlMapper
{

	@Autowired
	private ExtractedUrlMapper extractedUrlMapper;

	@Override
	public Integer insert(ExtractedUrl record) throws SQLException {
		
		return extractedUrlMapper.insert(record);
	}

	@Override
	public Integer batchInsert(List<ExtractedUrl> records) throws SQLException {
		
		return extractedUrlMapper.batchInsert(records);
	}

	@Override
	public Integer updateByPrimaryKey(ExtractedUrl record) throws SQLException {
		
		return extractedUrlMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ExtractedUrl> select(ExtractedUrl record) throws SQLException {
		
		return extractedUrlMapper.select(record);
	}

	@Override
	public ExtractedUrl selectByPrimaryKey(String id) throws SQLException {
		
		return extractedUrlMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) throws SQLException {
		
		return extractedUrlMapper.deleteByPrimaryKey(id);
	}
	

}
