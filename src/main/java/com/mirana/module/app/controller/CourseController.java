package com.mirana.module.app.controller;

import com.mirana.frame.base.controller.BaseController;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.SQLJsonKey;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.utils.RequestUtils;
import com.mirana.frame.utils.json.JsonUtils;
import com.mirana.frame.utils.result.Result;
import com.mirana.frame.utils.result.ResultUtils;
import com.mirana.module.common.model.McCourse;
import com.mirana.module.common.service.ICourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/course")
public class CourseController extends BaseController {

	@Resource
	private ICourseService courseService;

	@RequestMapping("/getHotCourse")
	@ResponseBody
	public Result getHotCourse () {
		try {
			String hotTags = JsonUtils.stringify(new Integer[]{1});
			Where where = new Where.Builder().EQ("categoryId", 1L)// 分类
				.JSON(SQLJsonKey.JSON_CONTAINS, "tagIds", hotTags)// tag
				.OrderBy("createdTime", OrderBy.DESC)// orderby
				.build();

			List<McCourse> courselist = courseService.findByWhere(where);
			return ResultUtils.buildSuccess(courselist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.buildError();
		}
	}

	/**
	 * 教育技术专题课程
	 *
	 * @return
	 */
	@RequestMapping("/getJyjsCourse")
	@ResponseBody
	public Result getJyjsCourse () {
		try {
			Where where = new Where.Builder().EQ("categoryId", 2L)// 分类
				// .JSON(SQLJsonKey.JSON_CONTAINS, "tagIds", newTags)// tag
				.OrderBy("createdTime", OrderBy.DESC)// orderby
				.build();
			List<McCourse> courselist = courseService.findByWhere(where);
			return ResultUtils.buildSuccess(courselist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.buildError();
		}
	}

	@RequestMapping("/getNewCourse")
	@ResponseBody
	public Result getNewCourse () {
		try {
			String newTags = JsonUtils.stringify(new Integer[]{2});
			Where where = new Where.Builder().EQ("categoryId", 1L)// 分类
				.JSON(SQLJsonKey.JSON_CONTAINS, "tagIds", newTags)// tag
				.OrderBy("createdTime", OrderBy.DESC)// orderby
				.build();
			List<McCourse> courselist = courseService.findByWhere(where);
			return ResultUtils.buildSuccess(courselist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.buildError();
		}
	}

	/**
	 * 添加收藏
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFavourate")
	@ResponseBody
	public Result addFavourate (HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getAttr(request, SysConstants.REQUEST_PARAMS);
		return courseService.addFavourate(paramMap);
	}

	/**
	 * 添加笔记
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addNotes")
	@ResponseBody
	public Result addNotes (HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getAttr(request, SysConstants.REQUEST_PARAMS);
		return courseService.addNotes(paramMap);
	}

	/**
	 * 获取所有的笔记
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotes")
	@ResponseBody
	public Result getNotes (HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getAttr(request, SysConstants.REQUEST_PARAMS);
		return courseService.getNotes(paramMap);
	}

	/**
	 * 添加评论
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public Result addComment (HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getAttr(request, SysConstants.REQUEST_PARAMS);
		return courseService.addComment(paramMap);
	}

	/**
	 * 获取所有的笔记
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComment")
	@ResponseBody
	public Result getComment (HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getAttr(request, SysConstants.REQUEST_PARAMS);
		return courseService.getComment(paramMap);
	}
}
