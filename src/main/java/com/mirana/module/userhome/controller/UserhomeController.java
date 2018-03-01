package com.mirana.module.userhome.controller;

import com.mirana.frame.base.controller.BaseController;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.Page;
import com.mirana.frame.db.base.query.PageUtils;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.utils.RequestUtils;
import com.mirana.frame.utils.result.Result;
import com.mirana.frame.utils.result.ResultUtils;
import com.mirana.module.common.model.*;
import com.mirana.module.common.service.ICourseService;
import com.mirana.module.common.service.IFavourateService;
import com.mirana.module.common.service.INotesService;
import com.mirana.module.common.service.IUsercommentService;
import com.mirana.module.userhome.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户个人中心
 * Created by Administrator on 2017/12/31.
 */
@Controller
@RequestMapping("/userhome")
public class UserhomeController extends BaseController {


	@Resource
	private IUserService userService;

	@Resource
	private INotesService noteService;

	@Resource
	private IUsercommentService usercommentService;

	@Resource
	private IFavourateService favourateService;

	@Resource
	private ICourseService courseService;

	/**
	 * 到达个人中心首页
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index () {
		return "/userhome/index";
	}

	/**
	 * 到达登录页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin () {
		return "/userhome/login";
	}

	/**
	 * 到达注册页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/toRegister")
	public String toRegister () {
		return "/userhome/register";
	}

	/**
	 * 登陆请求
	 *
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login (HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		return userService.login(username, password);
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Result register (HttpServletRequest request) {

		String username = request.getParameter("username");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (StringUtils.isBlank(username)) {
			return ResultUtils.buildFailed("用户名不能为空");
		}
		if (StringUtils.isBlank(phone)) {
			return ResultUtils.buildFailed("手机号不能为空");
		}
		if (StringUtils.isBlank(email)) {
			return ResultUtils.buildFailed("邮箱不能为空");
		}
		if (StringUtils.isBlank(password)) {
			return ResultUtils.buildFailed("密码不能为空");
		}

		return userService.register(username, phone, email, password);

	}

	/**
	 * 注销用户
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Result logout (HttpServletRequest request) {
		RequestUtils.rmSessionAttr(request, SysConstants.SESSION_KEY_ADMINISTRATOR);
		RequestUtils.rmSessionAttr(request, SysConstants.SESSION_KEY_ACTIVEUSER);
		return ResultUtils.buildSuccess();
	}

	/**
	 * 到达笔记页面
	 *
	 * @return
	 */
	@RequestMapping("/toNotes")
	public String toNotes () {
		return "userhome/notes";
	}

	/**
	 * 获取笔记
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotes")
	@ResponseBody
	public Result getNotes (HttpServletRequest request) {
		try {
			McUser activeuser = RequestUtils.getSessionAttr(request, SysConstants.SESSION_KEY_ACTIVEUSER);
			int pageNo = Integer.valueOf(request.getParameter("pageno"));
			int pageSize = Integer.valueOf(request.getParameter("pagesize"));

			Where where = new Where.Builder().EQ("noteuserid", activeuser.getId()).build();
			Page<McNotes> notesPage = noteService.findByPage(pageNo, pageSize, where);
			return ResultUtils.buildSuccess(notesPage);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultUtils.buildError("系统异常");
		}
	}

	/**
	 * 到达我的评论页面
	 */
	@RequestMapping("/toComments")
	public String toComments () {
		return "userhome/comments";
	}

	/**
	 * 获取评论
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComments")
	@ResponseBody
	public Result getComments (HttpServletRequest request) {
		try {
			McUser activeuser = RequestUtils.getSessionAttr(request, SysConstants.SESSION_KEY_ACTIVEUSER);//noteuserid
			int pageNo = Integer.valueOf(request.getParameter("pageno"));
			int pageSize = Integer.valueOf(request.getParameter("pagesize"));

			Where where = new Where.Builder().EQ("commentatorid", activeuser.getId()).build();
			Page<McUsercomment> usercommentPage = usercommentService.findByPage(pageNo, pageSize, where);
			return ResultUtils.buildSuccess(usercommentPage);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultUtils.buildError();
		}
	}

	/**
	 * 到达我的收藏页面
	 */
	@RequestMapping("/toFavourate")
	public String toFavourate () {
		return "userhome/favourate";
	}

	/**
	 * 获取我的收藏
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFavourate")
	@ResponseBody
	public Result getFavourate (HttpServletRequest request) {
		try {
			McUser activeuser = RequestUtils.getSessionAttr(request, SysConstants.SESSION_KEY_ACTIVEUSER);//noteuserid
			int pageNo = Integer.valueOf(request.getParameter("pageno"));
			int pageSize = Integer.valueOf(request.getParameter("pagesize"));

			Where where = new Where.Builder().EQ("favourateuserid", activeuser.getId()).Page(pageNo, pageSize).build();
			Page<McFavourate> favouratePage = favourateService.findByPage(pageNo, pageSize, where);
			List<McCourse> courselist = new ArrayList<McCourse>();
			for (McFavourate favourate : favouratePage.getPageresult()) {
				Where courseidwhere = new Where.Builder().EQ("id", favourate.getCourseid()).build();
				McCourse course = courseService.findUniqueByWhere(courseidwhere);
				courselist.add(course);
			}

			Page<McCourse> coursepage = PageUtils.buildPage(favouratePage.getPageno(), favouratePage.getPagesize(), favouratePage.getTotalrows(), courselist);

			return ResultUtils.buildSuccess(coursepage);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultUtils.buildError();
		}
	}
}
