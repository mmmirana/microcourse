package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McCategory;
import com.mirana.module.common.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * CategoryServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:21
 */
@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<McCategory> implements ICategoryService {

	@Resource(name = "categoryDao")
	@Override
	public void setBaseDao (IBaseDao<McCategory> baseDao) {
		super.setBaseDao(baseDao);
	}

}
