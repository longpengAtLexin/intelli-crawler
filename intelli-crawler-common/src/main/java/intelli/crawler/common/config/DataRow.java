package intelli.crawler.common.config;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 数据行;
 * @author penglong
 *
 */
public class DataRow implements Serializable 
{
	private static final long serialVersionUID = 5657058784815147396L;

	/**
	 * 数据行索引;
	 */
	private int index;
	
	/**
	 * 行数据，只保存数据信息;
	 */
	private CommonTable record;

	public DataRow(){}
	
	public DataRow(int index,CommonTable record)
	{
		this.index = index;
		this.record = record;
	}
	
	public int getIndex() 
	{
		return index;
	}

	public void setIndex(int index) 
	{
		this.index = index;
	}

	public CommonTable getRecord() 
	{
		return record;
	}

	public void setRecord(CommonTable record) 
	{
		this.record = record;
	}

	@Override
	public String toString()
	{
		
		return JSON.toJSONString(this);
	}
	
	
}
