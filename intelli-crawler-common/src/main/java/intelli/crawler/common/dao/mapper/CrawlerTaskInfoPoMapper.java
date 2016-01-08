package intelli.crawler.common.dao.mapper;

import java.sql.SQLException;
import java.util.List;

import intelli.crawler.common.dao.model.CountModel;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.dao.model.PagingCrawlerTaskInfoPo;


/**
 * 扩展 针对 CrawlerTaskInfoPo 对应表的访问方法，CrawlerTaskInfoPo一般存储在关系数据库中.
 * @author penglong
 *
 */
public interface CrawlerTaskInfoPoMapper  extends AbstractMapper<CrawlerTaskInfoPo>
{
	
	List<CrawlerTaskInfoPo>  selectPaging(PagingCrawlerTaskInfoPo po) throws SQLException ;
	
	CountModel  count(PagingCrawlerTaskInfoPo record ) throws SQLException ;
}
