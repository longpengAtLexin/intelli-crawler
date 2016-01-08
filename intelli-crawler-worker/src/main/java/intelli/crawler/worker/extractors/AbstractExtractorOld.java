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

import com.alibaba.fastjson.JSON;

import cn.edu.hfut.dmic.webcollector.extract.Extractor;
import cn.edu.hfut.dmic.webcollector.extract.ExtractorParams;
import cn.edu.hfut.dmic.webcollector.model.Page;
import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.config.PropertyInfo;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.MysqlCommonTableDao;
import intelli.crawler.common.dao.MysqlExtractedUrlDao;
import intelli.crawler.common.dao.model.ExtractedUrl;
import intelli.crawler.worker.core.DefaultCrawlerTaskServiceImpl;


/**
 * 抽象的提取器;
 * @author penglong
 *
 */
@Deprecated
public abstract class AbstractExtractorOld extends Extractor
{
	public AbstractExtractorOld(Page page, ExtractorParams params)
	{
		super(page, params);
		
	}
	
	/**
	 * 爬虫任务Id;
	 */
	protected String crawlerTaskId;
	/**
	 * 待提取的数据信息;
	 */
	protected CommonTable record;
	
	/**
	 * 数据抽取页面 url 匹配信息;
	 */
	protected String extractUrlParttern;
	
	private static  final IocManager iocManager = IocManager.getInstance();
	
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
		extractInternal(this.record);
	}
	
	/**
	 * 原来版本;
	 * @param record
	 * @throws Exception
	 */
	public void extractInternal(CommonTable record) throws Exception 
	{
		List<PropertyInfo> props = record.getProps();
		String currentUrl = getUrl();
		record.setUrl(currentUrl);
		record.setRequestTime(new Date());
		
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
	}
	
	@Override
	public void output() throws Exception 
	{
		System.out.println( JSON.toJSONString(record) );
		//ExtractedDataPool.mq.put(record);
		System.out.println( "============================" );
		//System.out.println("当前记录数 :【" +ExtractedDataPool.mq.size()+"】");
		AtomicInteger counter = DefaultCrawlerTaskServiceImpl.crawlerRecordCounterMap.get(crawlerTaskId);
		System.out.println( "任务Id :"+crawlerTaskId );
		System.out.println("当前记录数 :【" +counter.addAndGet(1)+"】");
		MysqlCommonTableDao  mysqlCommonTableDao = iocManager.getDao("mysqlCommonTableDao");
		mysqlCommonTableDao.insert(record);
		
		// 保存抽取过数据 的url;
		ExtractedUrl url = new ExtractedUrl();
		url.setUrl(record.getUrl());
		url.setRequestTime(record.getRequestTime());
		
		MysqlExtractedUrlDao extractedUrlDao =  iocManager.getDao("mysqlExtractedUrlDao");
		extractedUrlDao.insert(url);
		
	}
	
}
