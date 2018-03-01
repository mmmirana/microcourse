package com.mirana.frame.db.base.sqlconvert;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.exception.DBException;
import com.mirana.frame.db.base.mapping.ModelMapping;
import com.mirana.frame.db.base.mapping.SqlParamPairs;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.Page;
import com.mirana.frame.db.base.query.SQLJsonKey;
import com.mirana.frame.db.base.query.Where;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Sql转化抽象类
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/17 15:16:36
 */
public abstract class BaseSqlConvert {

	/**
	 * 复合条件And
	 **/
	protected static final String COMPOSITE_AND = "AND";

	/**
	 * 复合条件Or
	 **/
	protected static final String COMPOSITE_OR = "OR";

	/********************* 华丽丽的分割线 *********************/

	public abstract SqlParamPairs createInsertPairs (ModelMapping mapping, Map<String, Object> setMap);

	public abstract SqlParamPairs createUpdatePairs (ModelMapping mapping, Map<String, Object> setMap, Where where);

	public abstract SqlParamPairs createDeletePairs (ModelMapping mapping, Where where);

	public abstract SqlParamPairs createQueryPairs (ModelMapping mapping, Where where);

	public abstract SqlParamPairs createCountPairs (ModelMapping mapping, Where where);

	/**
	 * 创建where查询语句和对应占位符的值
	 *
	 * @param mapping
	 * @param where
	 * @return
	 */
	protected WhereSqlParams createWhereSql (String CompositeTag, ModelMapping mapping, Where where) {

		StringBuffer whereSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// one key, no parameters
		List<String> isNullList = where.getIsNullList();
		List<String> isNotNullList = where.getIsNotNullList();

		// one key, one parameter
		Map<String, Object> ltMap = where.getLtMap();
		Map<String, Object> leMap = where.getLeMap();
		Map<String, Object> eqMap = where.getEqMap();
		Map<String, Object> neMap = where.getNeMap();
		Map<String, Object> geMap = where.getGeMap();
		Map<String, Object> gtMap = where.getGtMap();
		Map<String, Object> likeMap = where.getLikeMap();

		// no json key, one json map( one key, one value )
		Map<SQLJsonKey, Map<String, Object>> jsonMap = where.getJsonMap();

		// one key, one more parameters
		Map<String, Object[]> betweenMap = where.getBetweenMap();
		Map<String, Object[]> inMap = where.getInMap();

		// one key,one page parameter
		Map<String, Integer> pageMap = where.getPageMap();

		// one key, one more where parameters
		List<Where> andWhereList = where.getAndWhereList();
		List<Where> orWhereList = where.getOrWhereList();

		// one key, one orderby parameter
		Map<String, OrderBy> orderMap = where.getOrderbyMap();

		for (String isNullKey : isNullList) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, isNullKey) + "` IS NULL");
		}
		for (String isNotNullKey : isNotNullList) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, isNotNullKey) + "` IS NOT NULL");
		}

		for (Map.Entry<String, Object> entry : ltMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` < ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : leMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` <= ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : eqMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` = ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : neMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` <> ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : geMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` >= ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : gtMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` > ?");
			params.add(entry.getValue());
		}
		for (Map.Entry<String, Object> entry : likeMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` LIKE ?");
			params.add(entry.getValue());
		}

		for (Map.Entry<SQLJsonKey, Map<String, Object>> entry : jsonMap.entrySet()) {
			SQLJsonKey sqlJsonKey = entry.getKey();
			Map<String, Object> jsonQueryMap = entry.getValue();
			switch (sqlJsonKey) {
				case JSON_CONTAINS:
					for (Map.Entry<String, Object> jsonQueryEntry : jsonQueryMap.entrySet()) {
						String columnKey = renameKey(mapping, jsonQueryEntry.getKey());
						whereSql.append(" " + CompositeTag + " JSON_CONTAINS (`" + columnKey + "`, CONVERT(? using utf8mb4))");
						// whereSql.append(" " + CompositeTag + " JSON_CONTAINS (`" + columnKey + "`, ?)");
						params.add(jsonQueryEntry.getValue());
					}
					break;

				default:
					break;
			}
		}

		for (Entry<String, Object[]> entry : betweenMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` BETWEEN ? AND ?");
			Object[] value = entry.getValue();
			params.add(value[0]);
			params.add(value[1]);
		}
		for (Entry<String, Object[]> entry : inMap.entrySet()) {
			whereSql.append(" " + CompositeTag + " `" + renameKey(mapping, entry.getKey()) + "` IN (?)");
			Object[] value = entry.getValue();
			params.add(value);
		}

		// and 查询
		WhereSqlParams andWhereSqlParams = createAndWhereSql(mapping, andWhereList);
		whereSql.append(andWhereSqlParams.getSql());
		params.addAll(andWhereSqlParams.getParams());

		// or 查询
		WhereSqlParams orWhereSqlParams = createOrWhereSql(mapping, orWhereList);
		whereSql.append(orWhereSqlParams.getSql());
		params.addAll(orWhereSqlParams.getParams());

		// 最后添加排序
		List<String> orderSqlArray = new ArrayList<String>();
		for (Map.Entry<String, OrderBy> entry : orderMap.entrySet()) {
			String orderKey = entry.getKey();
			String orderValue = entry.getValue().name();
			orderSqlArray.add("`" + renameKey(mapping, orderKey) + "` " + orderValue);
		}
		if (!orderSqlArray.isEmpty()) {
			whereSql.append(" ORDER BY " + StringUtils.join(orderSqlArray, ", "));
		}

		// page 分页查询
		if (pageMap != null) {
			int pageNo = MapUtils.getInteger(pageMap, SysConstants.PAGE_PARAMS_PAGENO, Page.DEFAULT_PAGE_NO);
			int pageSize = MapUtils.getInteger(pageMap, SysConstants.PAGE_PARAMS_PAGESIZE, Page.DEFAULT_PAGE_SIZE);

			int offset = (pageNo - 1) * pageSize;
			int rows = pageSize;

			whereSql.append(" LIMIT " + offset + "," + rows + " ");
		}

		/**
		 * create new {link #WhereSqlParams}
		 */
		WhereSqlParams whereSqlParams = new WhereSqlParams();
		whereSqlParams.setSql(whereSql.toString().replaceFirst(" " + CompositeTag + " ", ""));
		whereSqlParams.setParams(params);

		return whereSqlParams;
	}

	/**
	 * 根据And Where List生成对应的聚合sql语句
	 *
	 * @param mapping
	 * @param andWhereList
	 * @return
	 */
	private WhereSqlParams createAndWhereSql (ModelMapping mapping, List<Where> andWhereList) {
		List<String> andSqlList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();
		for (Where andWhere : andWhereList) {
			WhereSqlParams andItemSqlpParams = createWhereSql(COMPOSITE_AND, mapping, andWhere);
			andSqlList.add("( " + andItemSqlpParams.getSql() + " )");
			valueList.addAll(andItemSqlpParams.getParams());
		}

		WhereSqlParams andWhereSqlParams = new WhereSqlParams();
		andWhereSqlParams
			.setSql(andSqlList.isEmpty() ? "" : " " + COMPOSITE_AND + " ( " + StringUtils.join(andSqlList, " " + COMPOSITE_AND + " ") + " )");
		andWhereSqlParams.setParams(valueList);
		return andWhereSqlParams;
	}

	/**
	 * 根据Or Where List生成对应的聚合sql语句
	 *
	 * @param mapping
	 * @param orWhereList
	 * @return
	 */
	private WhereSqlParams createOrWhereSql (ModelMapping mapping, List<Where> orWhereList) {
		List<String> orSqlList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();
		for (Where orWhere : orWhereList) {
			WhereSqlParams orItemSqlpParams = createWhereSql(COMPOSITE_AND, mapping, orWhere);
			orSqlList.add("( " + orItemSqlpParams.getSql() + " )");
			valueList.addAll(orItemSqlpParams.getParams());
		}

		WhereSqlParams orWhereSqlParams = new WhereSqlParams();
		orWhereSqlParams
			.setSql(orSqlList.isEmpty() ? "" : " " + COMPOSITE_AND + " ( " + StringUtils.join(orSqlList, " " + COMPOSITE_OR + " ") + " )");
		orWhereSqlParams.setParams(valueList);
		return orWhereSqlParams;
	}

	/**
	 * 重置key
	 *
	 * @param mapping
	 * @param key
	 * @return
	 */
	protected String renameKey (ModelMapping mapping, String key) {
		if (key == null) {
			return null;
		}

		String[] fieldNames = mapping.getFieldNames();
		String[] columnNames = mapping.getColumnNames();

		String renameKey = null;
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldname = fieldNames[i];
			if (key.equals(fieldname)) {
				renameKey = columnNames[i];
				break;
			}
		}
		if (renameKey == null) {
			throw new DBException("In class " + mapping.getModelClass().getSimpleName() + ", couldn't find field '" + key + "' or the field '" + key
				+ "' doesn't have annotation " + mapping.getAnnoClassColumn().getSimpleName() + " !");
		}
		return renameKey;
	}

}
