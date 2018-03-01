package com.mirana.frame.db.base.query;

import java.util.Map;

/**
 * Where工具
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年9月24日上午2:28:02
 */
public class WhereUtils {

	/**
	 * 创建一个is null查询的Where对象
	 *
	 * @param key
	 * @return
	 */
	public static Where IsNull (String key) {
		return getBuilder().IsNull(key).build();
	}

	/**
	 * 创建一个is not null查询的Where对象
	 *
	 * @param key
	 * @return
	 */
	public static Where IsNotNull (String key) {
		return getBuilder().IsNotNull(key).build();
	}

	/**
	 * 创建一个less than查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where LT (String key, Object value) {
		return getBuilder().LT(key, value).build();
	}

	/**
	 * 创建一个less equal查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where LE (String key, Object value) {
		return getBuilder().LE(key, value).build();
	}

	/**
	 * 创建一个equal查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where EQ (String key, Object value) {
		return getBuilder().EQ(key, value).build();
	}

	/**
	 * 创建一个多equal查询的Where对象
	 *
	 * @param paramMap
	 * @return
	 */
	public static Where EQ (Map<String, Object> paramMap) {
		Where.Builder builder = getBuilder();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			builder.EQ(key, value);
		}
		return builder.build();
	}

	/**
	 * 创建一个great equal查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where GE (String key, Object value) {
		return getBuilder().GE(key, value).build();
	}

	/**
	 * 创建一个great than查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where GT (String key, Object value) {
		return getBuilder().GT(key, value).build();
	}

	/**
	 * 创建一个like查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where LIKE (String key, Object value) {
		return getBuilder().Like(key, value).build();
	}

	/**
	 * 创建一个not equal查询的Where对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Where NE (String key, Object value) {
		return getBuilder().NE(key, value).build();
	}

	/**
	 * 创建一个json字段查询的Where对象
	 *
	 * @param sqlJsonKey json查询的类型
	 * @param key        字段
	 * @param value      值
	 * @return
	 */
	public static Where JSON (SQLJsonKey sqlJsonKey, String key, String value) {
		return getBuilder().JSON(sqlJsonKey, key, value).build();
	}

	/**
	 * 创建一个between查询的Where对象
	 *
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Where BETWEEN (String key, Object begin, Object end) {
		return getBuilder().Between(key, begin, end).build();
	}

	/**
	 * 创建一个in查询的Where对象
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static Where IN (String key, Object[] values) {
		return getBuilder().In(key, values).build();
	}

	/**
	 * 创建一个page查询的Where对象
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页显示记录数
	 * @return
	 */
	public static Where PAGE (int pageNo, int pageSize) {
		return getBuilder().Page(pageNo, pageSize).build();
	}

	/**
	 * 创建一个add查询的Where对象
	 *
	 * @param andWheres
	 * @return
	 */
	public static Where AND (Where... andWheres) {
		return getBuilder().And(andWheres).build();
	}

	/**
	 * 创建一个or查询的Where对象
	 *
	 * @param orWheres
	 * @return
	 */
	public static Where OR (Where... orWheres) {
		return getBuilder().Or(orWheres).build();
	}

	/**
	 * 创建一个orderby排序的Where对象
	 *
	 * @param key     key
	 * @param orderby orderby
	 * @return
	 */
	public static Where ORDERBY (String key, OrderBy orderby) {
		return getBuilder().OrderBy(key, orderby).build();
	}

	/**
	 * 创建一个WhereBuilder对象，以便构建Where对象
	 *
	 * @return
	 */
	private static Where.Builder getBuilder () {
		return new Where.Builder();
	}

}
