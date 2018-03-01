package com.mirana.module.app.controller;

import com.mirana.frame.base.controller.BaseController;
import com.mirana.module.app.service.IAppIndexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 前台App首页IndexController
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月5日下午3:14:40
 */
@Controller
public class AppIndexController extends BaseController {

	@Resource
	private IAppIndexService indexService;

	/**
	 * 到达首页
	 *
	 * @return
	 */
	@RequestMapping(value = {"", "/", "/index"})
	public String index (Model model) {

		indexService.fillIndexData(model);

		return "/app/index";
	}
}
