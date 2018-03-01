package com.mirana.frame.db.base.mapping;

import com.mirana.frame.utils.SysPropUtils;
import com.mirana.frame.utils.json.JsonUtils;

/**
 * Sql语句及其对应的值
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 22:11:48
 */
public class SqlParamPairs {

	/**
	 * sql语句
	 **/
	private String sql;

	/**
	 * 数据库字段值的数组
	 **/
	private Object[] values;

	public String getSql () {
		return sql;
	}

	public void setSql (String sql) {
		this.sql = sql;
	}

	public Object[] getValues () {
		return values;
	}

	public void setValues (Object[] values) {
		this.values = values;
	}

	@Override
	public String toString () {
		return SysPropUtils.LINE_SEPARATOR + "[SQL   ]: " + this.getSql() + SysPropUtils.LINE_SEPARATOR + "[VALUES]: "
			+ JsonUtils.stringify(this.getValues());
	}

}