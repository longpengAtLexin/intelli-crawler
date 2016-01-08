package intelli.crawler.common.dao.hbase;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义cell 单元，一个cell 包含rowKey,列簇，列名，列值
 * 
 * @author guanjichao
 *
 */
public class HbaseCell {
	private String rowKey = "";
	private String colFamily = "";
	private String colName = "";
	private String colValue = "";

	public HbaseCell(String rowKey, String colFamily, String colName, String colValue) {
		super();
		this.rowKey = rowKey;
		this.colFamily = colFamily;
		this.colName = colName;
		this.colValue = colValue;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getColFamily() {
		return colFamily;
	}

	public void setColFamily(String colFamily) {
		this.colFamily = colFamily;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColValue() {
		return colValue;
	}

	public void setColValue(String colValue) {
		this.colValue = colValue;
	}

	public List<String> getAllVlalue() {
		List<String> list = new ArrayList<String>();
		list.add(rowKey);
		list.add(colFamily);
		list.add(colName);
		list.add(colValue);
		return list;
	}
}
