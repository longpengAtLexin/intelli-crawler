package intelli.crawler.worker.extractors;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.hbase.HbaseCommonTableDao;
import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * 标准爬虫任务抽取;
 * <br/>
 * 不支持分页，登录，ajax 等;
 * @author penglong
 *
 */
public class StandardExtractor extends AbstractExtractor 
{
	public static final Logger LOG = LoggerFactory.getLogger(StandardExtractor.class);
	public StandardExtractor(Page page, ExtractorParams params) 
	{
		super(page, params);
		extractUrlParttern  = params.getString("extractUrlParttern");
		crawlerTaskId  = params.getString("crawlerTaskId");
		outputDB = params.getString("outputDB");
		if(StringUtils.isEmpty(outputDB)||outputDB.equals(CrawlerTaskConfig.OutputDB_MySql))
		{
			LOG.info("提取数据输出至MYSQL");
			commonTableDao = IocManager.getInstance().getDao("mysqlCommonTableDao");
		}else
		{
			LOG.info("提取数据输出至HBASE");
			commonTableDao = new HbaseCommonTableDao();
		}
		for(Object o : params.values())
		{
			if( o instanceof  CommonTable )
			{
				table = (CommonTable) o;
				List<PropertyInfo> props = table.getProps();
				if(props==null || props.size()==0)
					throw new IllegalArgumentException("待提取字段信息(fieldArr)不能为空!");
			}
		}
	}

	

}
