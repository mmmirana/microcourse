package com.mirana.module.app.service;

import com.mirana.frame.base.service.IBaseService;
import com.mirana.module.common.model.McUser;
import org.springframework.ui.Model;

public interface IAppIndexService extends IBaseService<McUser> {

	/**
	 * 填充首页数据
	 *
	 * @param model
	 */
	void fillIndexData (Model model);

}
