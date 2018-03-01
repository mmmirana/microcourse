package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McUsercomment;
import com.mirana.module.common.service.IUsercommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UsercommentServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/15 02:02:00
 */
@Service("usercommentService")
public class UsercommentServiceImpl extends BaseServiceImpl<McUsercomment> implements IUsercommentService {

	@Resource(name = "usercommentDao")
	@Override
	public void setBaseDao (IBaseDao<McUsercomment> baseDao) {
		super.setBaseDao(baseDao);
	}

}
