package intelli.crawler.worker.schedule;

import java.util.concurrent.BlockingQueue;


public  abstract class AbstractScheduler <E>
{
	/**
	 * 待调度的队列;
	 */
	protected BlockingQueue<E> queue;
	
	/**
	 * 调度;
	 */
	public abstract  void schedule();
}
