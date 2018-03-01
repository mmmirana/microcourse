package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McTest;
import com.mirana.module.common.service.ITestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TestServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/31 13:33:42
 */
@Service("testService")
public class TestServiceImpl extends BaseServiceImpl<McTest> implements ITestService {

	@Resource(name = "testDao")
	@Override
	public void setBaseDao (IBaseDao<McTest> baseDao) {
		super.setBaseDao(baseDao);
	}

}
