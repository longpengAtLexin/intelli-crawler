package intelli.crawler.common.service;

import intelli.crawler.common.comm.RpcResponse;

/**
 * 爬虫任务服务;
 * <br/>
 * 提供给Web 服务器的接口;
 * @author penglong
 *
 */
public interface CrawlerTaskService 
{
	/**
	 * 启动爬虫任务;
	 * @param taskId  任务Id;
	 * @return
	 */
	RpcResponse start(String taskId);
	
	
	/**
	 * 停止爬虫任务;
	 * @param taskId 任务Id;
	 * @return
	 */
	RpcResponse stop(String taskId);
	
	/**
	 * 暂停任务;
	 * @param taskId
	 * @return
	 */
	RpcResponse pause(String taskId);
	
	/**
	 * 恢复任务;
	 * </br>
	 * 主要针对 pause(String taskId)后的恢复;
	 * @param taskId
	 * @return
	 */
	RpcResponse resume(String taskId);
	
	/**
	 * 刷新任务，针对前段页面修改后，对爬虫任务刷新.
	 * @param taskId
	 * @return
	 */
	RpcResponse refresh(String taskId);
	
}
