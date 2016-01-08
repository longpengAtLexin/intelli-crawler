package intelli.crawler.worker.core;

import intelli.crawler.common.config.CommonTable;
import intelli.crawler.common.dao.model.ExtractedUrl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  提取数据池;一个缓冲地段;
 * @author penglong
 *
 */
public class ExtractedDataPool 
{
	public static  final BlockingQueue<CommonTable> extractDataMq = new LinkedBlockingQueue<CommonTable>();
	
	public static  final BlockingQueue<ExtractedUrl> urlMq = new LinkedBlockingQueue<ExtractedUrl>();
	
	
	
}
