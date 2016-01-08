package intelli.crawler.worker.output;

import java.util.List;

public interface OutputCollector<E>
{
	void collect(E  e );
	
	void batchCollect(List<E>  list);
	
}
