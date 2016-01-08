package intelli.crawler.worker.extractors;

import java.util.List;






import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.CrawlerTaskConfig;
import intelli.crawler.common.config.PagingConfigOld;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.hbase.HbaseCommonTableDao;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import cn.edu.hfut.dmic.webcollector.model.Page;

public  class PagingExtractor extends AbstractExtractor
{
	public static final Logger LOG = LoggerFactory.getLogger(PagingExtractor.class);

	
	/**
	 * 分页配置信息;
	 */
	protected PagingConfigOld pagingConfig ;
	
	
	public PagingExtractor(Page page, ExtractorParams params) 
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
			}else if(o instanceof  PagingConfigOld) 
			{
				pagingConfig = (PagingConfigOld) o;
			}
		}
	}

	/**
	 * 根据当前页面的 url 获取下一个页面的url;
	 * @param currentUrl 当前页面的url;
	 * @return 下一页的页面url;
	 */
	/*protected abstract String getUrlOfNextPage(String currentUrl);*/
	
	
}
