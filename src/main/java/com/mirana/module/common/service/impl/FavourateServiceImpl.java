package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McFavourate;
import com.mirana.module.common.service.IFavourateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * FavourateServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/25 02:49:47
 */
@Service("favourateService")
public class FavourateServiceImpl extends BaseServiceImpl<McFavourate> implements IFavourateService {

	@Resource(name = "favourateDao")
	@Override
	public void setBaseDao (IBaseDao<McFavourate> baseDao) {
		super.setBaseDao(baseDao);
	}

}
