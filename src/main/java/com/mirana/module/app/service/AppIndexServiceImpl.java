package com.mirana.module.app.service;

import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.exception.ServiceException;
import com.mirana.module.common.dao.IPicGalleryDao;
import com.mirana.module.common.model.McPicGallery;
import com.mirana.module.common.model.McUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppIndexServiceImpl extends BaseServiceImpl<McUser> implements IAppIndexService {

	@Resource
	private IPicGalleryDao picgalleryDao;

	@Override
	public void fillIndexData (Model model) {
		try {
			List<McPicGallery> picList = new ArrayList<McPicGallery>();
			// 查询轮播记录
			Where where = new Where.Builder().EQ("groupId", 1).OrderBy("sort", OrderBy.ASC).build();
			picList = picgalleryDao.findByWhere(where);
			model.addAttribute("picList", picList);
		} catch (Exception e) {
			throw new ServiceException("获取首页数据时发生异常", e);
		}
	}
}
