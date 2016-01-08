package intelli.crawler.worker.schedule;

import java.io.Serializable;
import java.util.Map;

/**
 * 待提取的数据模型;
 * @author penglong
 *
 */
public class ExtractModel implements Serializable
{

	private static final long serialVersionUID = -5145782969493540451L;

	public String taskId;
	
	/**
	 * url and its content mappings
	 */
	public Map<String,String > urls;
	
}
