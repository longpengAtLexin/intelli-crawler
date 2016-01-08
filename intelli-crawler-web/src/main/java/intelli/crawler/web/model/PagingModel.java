package intelli.crawler.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bui 分页模型;
 * @author penglong
 *
 */
public class PagingModel implements Serializable {
   
	private static final long serialVersionUID = -5730095046847673597L;

	/*public static final int PAGE_SIZE = 12;*/

   /* // 总页数
    private int total;
    
    // 当前页
    private int page = 1;*/
	

 /*   // 总记录数
    private int records;*/
	
	/**
	 * 总页数;
	 */
	private int results;

    // 结果集
    private List<?> rows = new ArrayList<Object>();

	
	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getResults() {
		return results;
	}

	public void setResults(int results) {
		this.results = results;
	}

	
    
}
