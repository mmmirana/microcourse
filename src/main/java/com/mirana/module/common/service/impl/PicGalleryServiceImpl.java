package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McPicGallery;
import com.mirana.module.common.service.IPicGalleryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * PicGalleryServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/15 21:39:36
 */
@Service("picgalleryService")
public class PicGalleryServiceImpl extends BaseServiceImpl<McPicGallery> implements IPicGalleryService {

	@Resource(name = "picgalleryDao")
	@Override
	public void setBaseDao (IBaseDao<McPicGallery> baseDao) {
		super.setBaseDao(baseDao);
	}

}
