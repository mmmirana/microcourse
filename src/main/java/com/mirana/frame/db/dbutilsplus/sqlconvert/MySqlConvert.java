package com.mirana.frame.db.dbutilsplus.sqlconvert;

import com.mirana.frame.db.base.mapping.ModelMapping;
import com.mirana.frame.db.base.mapping.SqlParamPairs;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.db.base.sqlconvert.BaseSqlConvert;
import com.mirana.frame.db.base.sqlconvert.WhereSqlParams;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/17 11:51:36
 */
public class MySqlConvert extends BaseSqlConvert {

	/**
	 * 私有午餐构造
	 */
	private MySqlConvert () {
		super();
	}

	/**
	 * 单例获取外部类的对象
	 *
	 * @return
	 */
	public static MySqlConvert getInstance () {
		return MySqlConvertHolder.MYSQLCONVERT_INSTANCE;
	}

	@Override
	public SqlParamPairs createInsertPairs (ModelMapping mapping, Map<String, Object> setMap) {
		// 数据库表名
		String tablename = mapping.getTableName();

		// 数据库列，名称数组
		List<String> columnList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();

		for (String key : setMap.keySet()) {
			String renamekey = renameKey(mapping, key);
			if (StringUtils.isNotBlank(renamekey)) {
				columnList.add("`" + renamekey + "`");
				valueList.add(setMap.get(key));
			}
		}

		String columnsInsertSql = StringUtils.join(columnList, ", ");
		String[] placeholderArr = new String[columnList.size()];
		for (int i = 0; i < placeholderArr.length; i++) {
			placeholderArr[i] = "?";
		}

		String placeholderSql = StringUtils.join(placeholderArr, ", ");

		String insertSql = "INSERT INTO `" + tablename + "` (" + columnsInsertSql + ") VALUES (" + placeholderSql
			+ ");";

		SqlParamPairs pairs = new SqlParamPairs();
		pairs.setSql(insertSql);
		pairs.setValues(valueList.toArray());
		return pairs;
	}

	@Override
	public SqlParamPairs createUpdatePairs (ModelMapping mapping, Map<String, Object> setMap, Where where) {

		// 数据库表名
		String tablename = mapping.getTableName();
		// 数据库列，名称数组
		List<String> columnList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();

		// 循环遍历Map
		for (String key : setMap.keySet()) {
			String renamekey = renameKey(mapping, key);
			if (StringUtils.isNotBlank(renamekey)) {
				columnList.add("`" + renamekey + "` = ?");
				valueList.add(setMap.get(key));
			}
		}

		String columnsUpdateSql = StringUtils.join(columnList, ", ");

		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE `" + tablename + "` SET ");
		updateSql.append(columnsUpdateSql);
		updateSql.append(" WHERE ");

		WhereSqlParams updateSqlParams = createWhereSql(COMPOSITE_AND, mapping, where);
		updateSql.append(updateSqlParams.getSql());
		valueList.addAll(updateSqlParams.getParams());

		SqlParamPairs pairs = new SqlParamPairs();
		pairs.setSql(updateSql.toString());
		pairs.setValues(valueList.toArray());

		return pairs;
	}

	@Override
	public SqlParamPairs createDeletePairs (ModelMapping mapping, Where where) {

		// 数据库表名
		String tablename = mapping.getTableName();
		// 根据where创建查询的语句
		WhereSqlParams whereSqlParams = createWhereSql(COMPOSITE_AND, mapping, where);
		List<Object> deleteParams = whereSqlParams.getParams();

		StringBuffer deleteSql = new StringBuffer();

		deleteSql.append("DELETE FROM `" + tablename + "`");
		deleteSql.append(" WHERE 1 = 1 AND " + whereSqlParams.getSql());

		SqlParamPairs pairs = new SqlParamPairs();
		pairs.setSql(deleteSql.toString());
		pairs.setValues(deleteParams.toArray());

		return pairs;

	}

	@Override
	public SqlParamPairs createQueryPairs (ModelMapping mapping, Where where) {
		// 根据where创建查询的语句
		WhereSqlParams whereMap = createWhereSql(COMPOSITE_AND, mapping, where);
		// where sql
		String whereSql = whereMap.getSql();
		// where params
		List<Object> whereParams = whereMap.getParams();
		// 数据库表名
		String tablename = mapping.getTableName();

		StringBuffer querySql = new StringBuffer();
		querySql.append("SELECT * FROM `" + tablename + "`");

		if (!StringUtils.isBlank(whereSql)) {
			querySql.append(" WHERE 1 = 1 AND " + whereSql);
		}
		SqlParamPairs pairs = new SqlParamPairs();
		pairs.setSql(querySql.toString());
		pairs.setValues(whereParams.toArray());

		return pairs;
	}

	@Override
	public SqlParamPairs createCountPairs (ModelMapping mapping, Where where) {
		// 根据where创建查询的语句
		WhereSqlParams whereMap = createWhereSql(COMPOSITE_AND, mapping, where);
		// where sql
		String whereSql = whereMap.getSql();
		// where params
		List<Object> whereParams = whereMap.getParams();
		// 数据库表名
		String tablename = mapping.getTableName();

		StringBuffer querySql = new StringBuffer();
		querySql.append("SELECT COUNT(1) FROM `" + tablename + "`");

		if (!StringUtils.isBlank(whereSql)) {
			querySql.append(" WHERE 1 = 1 AND " + whereSql);
		}
		SqlParamPairs pairs = new SqlParamPairs();
		pairs.setSql(querySql.toString());
		pairs.setValues(whereParams.toArray());

		return pairs;
	}

	/**
	 * 静态内部类持有一个外部类的实例
	 *
	 * @Title
	 * @Description
	 * @Created Assassin
	 * @DateTime 2017/06/18 10:57:33
	 */
	public static class MySqlConvertHolder {
		public static final MySqlConvert MYSQLCONVERT_INSTANCE = new MySqlConvert();
	}
}
