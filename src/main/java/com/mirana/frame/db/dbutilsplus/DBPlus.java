package com.mirana.frame.db.dbutilsplus;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.annoparse.BaseDBAnnoParse;
import com.mirana.frame.db.base.mapping.ModelMapping;
import com.mirana.frame.db.base.mapping.SqlParamPairs;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.db.base.sqlconvert.BaseSqlConvert;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * dbutils数据访问框架入口
 * <p>
 * <br>
 * <p>
 * 非单例，有的框架里面可能要访问多个不同的数据库
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/17 11:46:34
 */
public class DBPlus {

	private static final Logger log = LoggerFactory.getLogger(DBPlus.class);

	// 注册的sql转化类
	private BaseSqlConvert sqlConvert;

	// 注册的注解解析类
	private BaseDBAnnoParse dbAnnoParse;

	// mappingMap
	private Map<String, ModelMapping> mappingMap = new ConcurrentHashMap<String, ModelMapping>();

	/**
	 * 私有无参构造
	 */
	public DBPlus () {
		super();
	}

	/**
	 * 注册BaseSqlConver
	 *
	 * @param sqlConvert
	 */
	public void registerSqlConvert (BaseSqlConvert sqlConvert) {
		// 判断是否注册了SqlConvert
		Assert.notNull(sqlConvert, "The SqlConvert must not be null...");

		this.sqlConvert = sqlConvert;
	}

	/**
	 * 注册BaseAnnoParse
	 *
	 * @param dbAnnoParse
	 */
	public void registerDbAnnoParse (BaseDBAnnoParse dbAnnoParse) {
		// 判断是否注册了DBAnnoParse
		Assert.notNull(dbAnnoParse, "The DBAnnoParse must not be null...");
		// 对应的注解不能为null
		Assert.notNull(dbAnnoParse.getTableAnnoClazz(), "The DBAnnoParse'tableAnnoClass must not be null...");
		Assert.notNull(dbAnnoParse.getPkAnnoClazz(), "The DBAnnoParse'pkAnnoClass must not be null...");
		Assert.notNull(dbAnnoParse.getColumnAnnoClazz(), "The DBAnnoParse'columnAnnoClass must not be null...");

		this.dbAnnoParse = dbAnnoParse;
	}

	/**
	 * 构建一个insert的sqlParamsPairs
	 *
	 * @param model
	 * @return
	 */
	public SqlParamPairs buildInsertPairs (BaseModel model) {
		log.debug("Insert For Model: " + model.getClass() + " ...");
		// modelMapping
		ModelMapping mapping = getMapping(model.getClass());

		Map<String, Object> setMap = BeanUtils.bean2map(model, false);

		return sqlConvert.createInsertPairs(mapping, setMap);
	}

	public SqlParamPairs buildUpdatePairs (Class<? extends BaseModel> modelClass, Map<String, Object> setMap,
										   Where where) {
		// modelMapping
		ModelMapping mapping = getMapping(modelClass);

		return sqlConvert.createUpdatePairs(mapping, setMap, where);
	}

	/**
	 * 构建一个delete的SqlParamsPairs
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 */
	public SqlParamPairs buildDeletePairs (Class<? extends BaseModel> modelClass, Where where) {
		log.debug("Delete For Model: " + modelClass + " ...");

		// modelMapping
		ModelMapping mapping = getMapping(modelClass);

		return sqlConvert.createDeletePairs(mapping, where);
	}

	/**
	 * 构建一个query的SqlParamsPairs
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 */
	public SqlParamPairs buildQueryPairs (Class<? extends BaseModel> modelClass, Where where) {
		log.debug("Query For Model: " + modelClass + " ...");

		// modelMapping
		ModelMapping mapping = getMapping(modelClass);

		return sqlConvert.createQueryPairs(mapping, where);
	}

	/**
	 * 构建一个计数的SqlParamsPairs
	 *
	 * @param modelClass
	 * @param where
	 * @return
	 */
	public SqlParamPairs buildCountPairs (Class<? extends BaseModel> modelClass, Where where) {
		// modelMapping
		ModelMapping mapping = getMapping(modelClass);

		return sqlConvert.createCountPairs(mapping, where);
	}

	public BaseSqlConvert getSqlConvert () {
		return sqlConvert;
	}

	public BaseDBAnnoParse getDbAnnoParse () {
		return dbAnnoParse;
	}

	/**
	 * 获取model的mapping
	 *
	 * @param modelClass
	 * @return
	 */
	public ModelMapping getMapping (Class<? extends BaseModel> modelClass) {

		// 判断model是否为null
		Assert.notNull(modelClass, "The parameter model must not be null...");

		// 判断是否注册了SqlConvert
		Assert.notNull(this.sqlConvert, "You must register SqlConver at first");
		// 判断是否注册了DBAnnoParse
		Assert.notNull(this.dbAnnoParse, "You must register DBAnnoParse at first");

		String modelclassname = modelClass.getName();
		ModelMapping mapping = mappingMap.get(modelclassname);

		if (mapping == null) {
			mapping = dbAnnoParse.parse(modelClass);
			mappingMap.put(modelclassname, mapping);
		}
		return mapping;
	}

	public SqlParamPairs buildInsertAndGetPkPairs (BaseModel model) {
		return null;
	}

}
