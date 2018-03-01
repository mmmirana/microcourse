package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.module.common.model.McNotes;
import com.mirana.module.common.service.INotesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * NotesServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/24 16:21:58
 */
@Service("notesService")
public class NotesServiceImpl extends BaseServiceImpl<McNotes> implements INotesService {

	@Resource(name = "notesDao")
	@Override
	public void setBaseDao (IBaseDao<McNotes> baseDao) {
		super.setBaseDao(baseDao);
	}

}
