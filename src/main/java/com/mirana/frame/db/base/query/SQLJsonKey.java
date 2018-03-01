package com.mirana.frame.db.base.query;

/**
 * Sql JSON数据相关操作（Mysql5.7及以上）
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月8日下午5:16:11
 */
public enum SQLJsonKey {
	/**
	 * 插入数据
	 **/
	JSON_INSERT,

	/**
	 * 替换数据
	 **/
	JSON_REPLACE,

	/**
	 * 向数组尾部追加数据
	 **/
	JSON_ARRAY_APPEND,

	/**
	 * 从指定位置移除数据
	 **/
	JSON_REMOVE,

	/**
	 * 更新数据
	 **/
	JSON_MERGE,

	/**
	 * 是否包含
	 **/
	JSON_CONTAINS,

	/**
	 * 获取搜索结果
	 **/
	JSON_SEARCH,

	/**
	 * 获取某个或多个节点的值
	 **/
	JSON_EXTRACT,
}
