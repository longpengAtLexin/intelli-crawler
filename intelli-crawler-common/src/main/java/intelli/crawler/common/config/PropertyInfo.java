package intelli.crawler.common.config;

import java.io.Serializable;

/**
 * 字段信息;
 * <br/>
 * <br/>
 * <b>由用户在客户端页面配置</b>
 * @author penglong
 *
 */
public class PropertyInfo implements Serializable
{
	private static final long serialVersionUID = -4415128512947194393L;

	/**
	 * 域的字段名
	 */
	private String fieldname;
	
	/**
	 * 字段的路径;
	 * <ol>
	 * <li>基于 css path</li>
	 * <li>基于 xpath</li>
	 * </ol>
	 */
	private String fieldpath;
	
	/**
	 * 字段的中文描述;
	 */
	private String fielddescription;
	
	/**
	 * 字段值，这个不是配置的，是依据 fieldpath 提取的.
	 * <p> 注：当fieldpath 在网页中只有一个时，则如实提取该值，<br/>
	 * 		当 fieldpath 在网页中有多个时，则将多个值的暂存在List<String>容器中，然后序列化List<String>,存入该 fieldvalue 中。 </p>
	 */
	private String fieldvalue;
	
	/**
	 * <p>该信息在网页中出现的个数，一般而言是一个，有时也会有多个。
	 *      <br/>
	 *		 如一张个人信息的网页中,其姓名、性别、年龄信息只有一个，
	 * 	但工作经历可能有多个，假如该人曾经服务过多家企业。</p>
	 * 
	 *  <p>  默认为1个，但只要该信息不为1，则说明有多个。</p>
	 */
	private short counter = DefaultCounter;
	
	/**
	 * 该信息在网页中出现的个数,默认为一个;
	 */
	public final static short DefaultCounter = 1;
	
	/**
	 * 该信息在网页中出现多个;
	 */
	public final static short Muti = 2;
	
	public PropertyInfo(String fieldname,String fieldpath ,String fielddescription)
	{
		this.fieldname = fieldname;
		this.fieldpath = fieldpath;
		this.fielddescription = fielddescription;
		
	}
	public PropertyInfo(String fieldname,String fieldpath ,String fielddescription,String fieldvalue)
	{
		this.fieldname = fieldname;
		this.fieldpath = fieldpath;
		this.fielddescription = fielddescription;
		this.fieldvalue = fieldvalue;
	}
	
	/**
	 * 没有默认的构造函数,FastJSON 反序列化成对象时出错！
	 */
	public PropertyInfo(){}
	
	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	

	public String getFieldpath() {
		return fieldpath;
	}

	public void setFieldpath(String fieldpath) {
		this.fieldpath = fieldpath;
	}

	public String getFielddescription() {
		return fielddescription;
	}

	public void setFielddescription(String fielddescription) {
		this.fielddescription = fielddescription;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public short getCounter() {
		return counter;
	}

	public void setCounter(short counter) {
		this.counter = counter;
	}

	
	
	
	
	
	
}
