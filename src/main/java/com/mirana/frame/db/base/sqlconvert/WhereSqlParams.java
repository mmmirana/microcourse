package com.mirana.frame.db.base.sqlconvert;

import java.util.List;

public class WhereSqlParams {

	private String sql;// 根据where查询生成的部分sql语句

	private List<Object> params;// sql语句占位符对应的参数值

	public String getSql () {
		return sql;
	}

	public void setSql (String sql) {
		this.sql = sql;
	}

	public List<Object> getParams () {
		return params;
	}

	public void setParams (List<Object> params) {
		this.params = params;
	}

}
