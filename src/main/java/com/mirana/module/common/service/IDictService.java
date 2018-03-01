package com.mirana.module.common.service;

import com.mirana.frame.base.service.IBaseService;
import com.mirana.module.common.model.McDict;

import java.sql.SQLException;
import java.util.List;

/**
 * IDictService.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:23
 */
public interface IDictService extends IBaseService<McDict> {
	/**
	 * 根据字典类型id获取数据字段集合
	 *
	 * @param typeid
	 * @return
	 * @throws SQLException
	 */
	public List<McDict> getDictByTypeid (Long typeid) throws SQLException;
}
