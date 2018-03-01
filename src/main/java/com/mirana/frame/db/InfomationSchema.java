package com.mirana.frame.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表的概要信息
 *
 * @author Administrator
 */
public class InfomationSchema implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// 概要——所属数据库
	private String table_schema;
	// 表名
	private String table_name;
	// 引擎
	private String engin;
	// 表的行数
	private String table_rows;
	// 创建时间
	private Date   create_time;
	// 更新时间
	private Date   update_time;
	// 字符编码
	private String table_collation;
	// 表的注释
	private String table_comment;

	public String getTable_schema () {
		return table_schema;
	}

	public void setTable_schema (String table_schema) {
		this.table_schema = table_schema;
	}

	public String getTable_name () {
		return table_name;
	}

	public void setTable_name (String table_name) {
		this.table_name = table_name;
	}

	public String getEngin () {
		return engin;
	}

	public void setEngin (String engin) {
		this.engin = engin;
	}

	public String getTable_rows () {
		return table_rows;
	}

	public void setTable_rows (String table_rows) {
		this.table_rows = table_rows;
	}

	public Date getCreate_time () {
		return create_time;
	}

	public void setCreate_time (Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time () {
		return update_time;
	}

	public void setUpdate_time (Date update_time) {
		this.update_time = update_time;
	}

	public String getTable_collation () {
		return table_collation;
	}

	public void setTable_collation (String table_collation) {
		this.table_collation = table_collation;
	}

	public String getTable_comment () {
		return table_comment;
	}

	public void setTable_comment (String table_comment) {
		this.table_comment = table_comment;
	}
}
