package intelli.crawler.worker.extractors;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.extract.Extractor;
import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import cn.edu.hfut.dmic.webcollector.model.Page;
import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.DataRow;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.CommonTableDao;
import intelli.crawler.common.dao.model.ExtractedUrl;
import intelli.crawler.worker.core.DefaultCrawlerTaskServiceImpl;


/**
 * 抽象的提取器;
 * @author penglong
 *
 */
public abstract class AbstractExtractor extends Extractor
{
	private static Logger LOG = LoggerFactory.getLogger(AbstractExtractor.class);
	
	public AbstractExtractor(Page page, ExtractorParams params)
	{
		super(page, params);
		
	}
	
	/**
	 * 爬虫任务Id;
	 */
	protected String crawlerTaskId;
	/**
	 * 待提取的数据信息 , 一个提取器 负责提取一个表;
	 */
	protected CommonTable table;
	
	/**
	 * 数据抽取页面 url 匹配信息;
	 */
	protected String extractUrlParttern;
	
	/**
	 * 输出数据库类型;
	 */
	protected String outputDB;
	
	
	
	//private static  final IocManager iocManager = IocManager.getInstance();
	
	protected CommonTableDao commonTableDao ;
	/*private static  final AtomicInteger counter = new AtomicInteger(0);*/
	/**
	 * 
	 * 注意 urlParttern 不能为空;
	 * <br/>
	 * 因为抽取数据的页面必须满足指定的格式，否则无法抽取到数据;
	 */
	@Override
	public boolean shouldExecute() 
	{
		if(StringUtils.isEmpty(extractUrlParttern))
			throw new IllegalArgumentException("数据抽取页面的urlPattern 不能为空!");// 因为抽取数据的页面必须满足指定的格式，否则无法抽取到数据;
		return Pattern.matches(extractUrlParttern, getUrl());
	}
	
	@Override
	public void extract() throws Exception 
	{
		extractInternal(this.table);
	}
	
	/**
	 * 原来版本;
	 * @param record
	 * @throws Exception
	 */
	public void extractInternal(CommonTable table) throws Exception 
	{
		String currentUrl = getUrl();
		
			if(table.getNumPerPage()==CommonTable.DefualtNumPerPage)  //每页只有一条记录;
			{
				List<PropertyInfo> props = table.getProps();
				table.setUrl(currentUrl);
				table.setRequestTime(new Date());
				
				for(PropertyInfo p : props)
				{
					if(p.getCounter() == PropertyInfo.DefaultCounter)
					{
						String value = selectText(p.getFieldpath(),null);
						p.setFieldvalue(value);
					}else  // p 对应的  fieldpath 在网页中有多个;
					{
						Elements els = select(p.getFieldpath());
						 Iterator<Element> it = els.iterator();
						 List<String> values = new LinkedList<String>();
						 while(it.hasNext())
						 {
							 String value = it.next().text();
							 if(!StringUtils.isEmpty(value))
							 {
								 value = value.trim();
								 if(!StringUtils.isEmpty(value))
								 {
									 values.add(value);
								 }
							 }
								 
						 }
						 p.setFieldvalue( JSON.toJSONString(values));
					}
				}
			}else if(table.getNumPerPage()==CommonTable.MutiNumPerPage) //每页有多条记录,
			{
				List<PropertyInfo> props = table.getProps(); // 配置信息;
				table.setUrl(currentUrl);
				table.setRequestTime(new Date());
				
				Elements els = select(props.get(0).getFieldpath());
				int size = els.size();  // 该页面一共 size 条记录;
				
				List<DataRow> dataRows = new LinkedList<DataRow>();
				if(size >0)
				{
					for(int i=0;i<size;i++)
					{
						CommonTable record = new CommonTable();
						cloneTableMetaInfo(record,table);
						
						List<PropertyInfo>  recordData = new LinkedList<PropertyInfo>();
						for(PropertyInfo p : props)
						{
							String value = selectText(p.getFieldpath(), i, "");
							PropertyInfo tmp = new PropertyInfo(p.getFieldname(),p.getFieldpath(),p.getFielddescription(),value);
							recordData.add(tmp);
						}
						record.setProps(recordData);
						DataRow row = new DataRow(i,record);
						dataRows.add(row);
					}
					
				}
				table.setDataRows(dataRows);
				
			}
		
		
	}
	
	@Override
	public void output() throws Exception 
	{
		LOG.info("提取记录:{}", JSON.toJSONString(table) );
		//ExtractedDataPool.mq.put(record);
		
		
		AtomicInteger counter = DefaultCrawlerTaskServiceImpl.crawlerRecordCounterMap.get(crawlerTaskId);
		//MysqlCommonTableDao  commonTableDao = iocManager.getDao("mysqlCommonTableDao");
		
		
		ExtractedUrl url = new ExtractedUrl(); // 保存抽取过数据 的url;
		url.setUrl(getUrl());
		
		if(table.getNumPerPage()==CommonTable.DefualtNumPerPage) //每页只有一条记录;
		{
			counter.addAndGet(1);
			commonTableDao.insert(table);
			url.setRequestTime(table.getRequestTime());
		}
		else if(table.getNumPerPage()==CommonTable.MutiNumPerPage) //每页有多条记录,
		{
			List<DataRow>  rows = table.getDataRows();
			counter.addAndGet(rows.size());
			url.setRequestTime(table.getRequestTime());
			
			for(DataRow row :rows)
			{
				commonTableDao.insert(row.getRecord());
			}
		}
		LOG.info("任务【id:{},表名:{}】 当前提取记录数:{}" , crawlerTaskId, table.getTablename(), counter.get());
		//MysqlExtractedUrlDao extractedUrlDao =  iocManager.getDao("mysqlExtractedUrlDao");
		//extractedUrlDao.insert(url);
		
	}
	
	/**
	 * 复制表的元信息,以便保存;
	 */
	private void cloneTableMetaInfo(CommonTable target,CommonTable src)
	{
		target.setTabledesc(src.getTabledesc());
		target.setTablename(src.getTablename());
		target.setUrl(getUrl());
		target.setRequestTime(src.getRequestTime());
	}
	
	
}
