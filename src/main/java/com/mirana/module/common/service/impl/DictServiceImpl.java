package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.Where;
import com.mirana.module.common.model.McDict;
import com.mirana.module.common.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * DictServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:23
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<McDict> implements IDictService {

	@Resource(name = "dictDao")
	@Override
	public void setBaseDao (IBaseDao<McDict> baseDao) {
		super.setBaseDao(baseDao);
	}

	/**
	 * 根据字典类型id获取数据字段集合
	 *
	 * @param typeid
	 * @return
	 * @throws SQLException
	 * @see com.mirana.module.common.service.IDictService#getDictByTypeid(java.lang.Long)
	 */
	@Override
	public List<McDict> getDictByTypeid (Long typeid) throws SQLException {
		Where where = new Where.Builder().EQ("typeid", typeid).EQ("isEnable", true).OrderBy("sort", OrderBy.ASC).build();
		return findByWhere(where);
	}

}
