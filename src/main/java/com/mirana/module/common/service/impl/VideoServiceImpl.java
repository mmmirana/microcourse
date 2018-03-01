package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McVideo;
import com.mirana.module.common.service.IVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * VideoServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:22
 */
@Service("videoService")
public class VideoServiceImpl extends BaseServiceImpl<McVideo> implements IVideoService {

	@Resource(name = "videoDao")
	@Override
	public void setBaseDao (IBaseDao<McVideo> baseDao) {
		super.setBaseDao(baseDao);
	}

}
