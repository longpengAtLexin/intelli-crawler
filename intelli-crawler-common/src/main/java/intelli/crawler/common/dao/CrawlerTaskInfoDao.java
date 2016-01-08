package intelli.crawler.common.dao;

import java.sql.SQLException;
import java.util.List;

import intelli.crawler.common.dao.mapper.CrawlerTaskInfoPoMapper;
import intelli.crawler.common.dao.model.CountModel;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.dao.model.PagingCrawlerTaskInfoPo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("crawlerTaskInfoDao")
public class CrawlerTaskInfoDao implements CrawlerTaskInfoPoMapper
{
	@Autowired
    private CrawlerTaskInfoPoMapper crawlerTaskInfoPoMapper;

	public Integer insert(CrawlerTaskInfoPo record) throws SQLException {
		
		return crawlerTaskInfoPoMapper.insert(record);
	}

	public Integer batchInsert(List<CrawlerTaskInfoPo> records)
			throws SQLException {
		
		return crawlerTaskInfoPoMapper.batchInsert(records);
	}

	public Integer updateByPrimaryKey(CrawlerTaskInfoPo record)
			throws SQLException {
		
		return crawlerTaskInfoPoMapper.updateByPrimaryKey(record);
	}

	public List<CrawlerTaskInfoPo> select(CrawlerTaskInfoPo record)
			throws SQLException {
		
		return crawlerTaskInfoPoMapper.select(record);
	}

	public CrawlerTaskInfoPo selectByPrimaryKey(String id) throws SQLException {
		
		return crawlerTaskInfoPoMapper.selectByPrimaryKey(id);
	}

	public Integer deleteByPrimaryKey(String id) throws SQLException {
		
		return crawlerTaskInfoPoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<CrawlerTaskInfoPo> selectPaging(PagingCrawlerTaskInfoPo po) throws SQLException {
		
		return crawlerTaskInfoPoMapper.selectPaging(po);
	}

	@Override
	public CountModel count(PagingCrawlerTaskInfoPo record) throws SQLException {
		
		return crawlerTaskInfoPoMapper.count(record);
	}
	
	
}
