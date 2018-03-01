package com.mirana.frame.db.dbutilsplus.dbtemplate;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.mapping.SqlParamPairs;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.db.dbutilsplus.DBPlus;
import com.mirana.frame.db.dbutilsplus.annoparse.DbutilsPlusAnnoParse;
import com.mirana.frame.db.dbutilsplus.handler.AnnoRowProcessor;
import com.mirana.frame.db.dbutilsplus.sqlconvert.MySqlConvert;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.SysPropUtils;
import com.mirana.frame.utils.json.JsonUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DBUtils模板
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/19 21:30:21
 */
public class DBUtilsTemplate {

	private static DBPlus plus = new DBPlus();

	static {
		plus.registerSqlConvert(MySqlConvert.getInstance());
		plus.registerDbAnnoParse(DbutilsPlusAnnoParse.getInstance());
	}

	@Resource(name = "dataSource")
	private DataSource dataSource;
	private QueryRunner queryRunner;

	/**
	 * 插入数据操作
	 *
	 * @param model
	 * @throws SQLException
	 */
	public int insert (BaseModel model) throws SQLException {
		SqlParamPairs pairs = plus.buildInsertPairs(model);
		LogUtils.info(pairs.toString());

		int affectedRows = getQueryRunner().update(pairs.getSql(), pairs.getValues());

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.affect *****]: " + affectedRows);
		LogUtils.info(sql.toString());

		return affectedRows;

	}

	/**
	 * 插入数据操作返回主键ID
	 *
	 * @param model
	 * @throws SQLException
	 */
	public Long insertAndGetPk (BaseModel model) throws SQLException {
		SqlParamPairs pairs = plus.buildInsertPairs(model);
		LogUtils.info(pairs.toString());

		Long pk = getQueryRunner().insert(pairs.getSql(), new ScalarHandler<Long>(), pairs.getValues());

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Insert> DBUTILS.pk     *****]: " + pk);
		LogUtils.info(sql.toString());

		return pk;

	}

	/**
	 * 更新操作
	 *
	 * @param setMap 要更新的值
	 * @param where  条件
	 * @return
	 * @throws SQLException
	 */
	public int update (Class<? extends BaseModel> modelClass, Map<String, Object> setMap, Where where) throws SQLException {
		SqlParamPairs pairs = plus.buildUpdatePairs(modelClass, setMap, where);
		LogUtils.info(pairs.toString());

		int affectedRows = getQueryRunner().update(pairs.getSql(), pairs.getValues());

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Update> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Update> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Update> DBUTILS.affect *****]: " + affectedRows);
		LogUtils.info(sql.toString());

		return affectedRows;
	}

	/**
	 * 删除操作
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public <T extends BaseModel> int delete (Class<T> modelClass, Where where) throws SQLException {
		SqlParamPairs pairs = plus.buildDeletePairs(modelClass, where);
		LogUtils.info(pairs.toString());

		int affectedRows = getQueryRunner().update(pairs.getSql(), pairs.getValues());

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Delete> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Delete> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Delete> DBUTILS.affect *****]: " + affectedRows);
		LogUtils.info(sql.toString());

		return affectedRows;
	}

	/**
	 * 查询
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public <T extends BaseModel> List<T> query (Class<T> modelClass, Where where) throws SQLException {

		SqlParamPairs pairs = plus.buildQueryPairs(modelClass, where);
		List<T> modelList = getQueryRunner().query(pairs.getSql(), new BeanListHandler<T>(modelClass, new AnnoRowProcessor()), pairs.getValues());

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Query> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Query> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Query> DBUTILS.size   *****]: " + modelList.size());
		LogUtils.info(sql.toString());

		return modelList;
	}

	/**
	 * 计数
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public <T extends BaseModel> int count (Class<T> modelClass, Where where) throws SQLException {
		SqlParamPairs pairs = plus.buildCountPairs(modelClass, where);
		LogUtils.info(pairs.toString());

		int count = getQueryRunner().query(pairs.getSql(), new ScalarHandler<Long>(1), pairs.getValues()).intValue();

		StringBuffer sql = new StringBuffer();
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Count> DBUTILS.sql    *****]: " + pairs.getSql());
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Count> DBUTILS.values *****]: " + JsonUtils.stringify(pairs.getValues()));
		sql.append(SysPropUtils.LINE_SEPARATOR + "[***** <Count> DBUTILS.count  *****]: " + count);
		LogUtils.info(sql.toString());

		return count;
	}

	/**
	 * 执行原生sql语句，一般用于插入、更新、删除，这里使用的是sql字段，非实体字段
	 *
	 * @param sql    执行的sql语句
	 * @param params sql参数
	 * @return
	 * @throws SQLException
	 */
	public int execute (String sql, Object... params) throws SQLException {

		int affectrows = getQueryRunner().update(sql, params);

		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.sql    *****]: " + sql);
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.values *****]: " + JsonUtils.stringify(params));
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.affect  *****]: " + affectrows);
		LogUtils.info(sqlbuffer.toString());

		return affectrows;
	}

	/**
	 * 执行原生sql语句查询结果集，这里使用的是sql字段，非实体字段
	 *
	 * @param rsh    {@link ResultSetHandler}
	 * @param sql    执行的sql语句
	 * @param params sql参数
	 * @return
	 * @throws SQLException
	 */
	public <T> T executeQuery (ResultSetHandler<T> rsh, String sql, Object... params) throws SQLException {
		T result = getQueryRunner().query(sql, rsh, params);

		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.sql    *****]: " + sql);
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.values *****]: " + JsonUtils.stringify(params));
		sqlbuffer.append(SysPropUtils.LINE_SEPARATOR + "[***** <Execute> DBUTILS.result *****]: " + JsonUtils.stringify(result));
		LogUtils.info(sqlbuffer.toString());

		return result;
	}

	public DataSource getDatasource () {
		return dataSource;
	}

	public void setDataSource (DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 从DataSource中获取QueryRunner
	 *
	 * @return
	 */
	private QueryRunner getQueryRunner () {
		Assert.notNull(dataSource, "The datasoure must not be null...");
		if (queryRunner == null) {
			queryRunner = new QueryRunner(dataSource);
		}
		return queryRunner;
	}

}
