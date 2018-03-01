package com.mirana.module.common.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.exception.ServiceException;
import com.mirana.frame.spring.SpringRequestUtils;
import com.mirana.frame.utils.RequestUtils;
import com.mirana.frame.utils.result.Result;
import com.mirana.frame.utils.result.ResultUtils;
import com.mirana.module.common.dao.IFavourateDao;
import com.mirana.module.common.dao.INotesDao;
import com.mirana.module.common.dao.IUsercommentDao;
import com.mirana.module.common.model.*;
import com.mirana.module.common.service.ICourseService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * CourseServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:21
 */
@Service("courseService")
public class CourseServiceImpl extends BaseServiceImpl<McCourse> implements ICourseService {

	@Resource
	private IFavourateDao favourateDao;

	@Resource
	private INotesDao notesDao;

	@Resource
	private IUsercommentDao usercommentDao;

	@Resource(name = "courseDao")
	@Override
	public void setBaseDao (IBaseDao<McCourse> baseDao) {
		super.setBaseDao(baseDao);
	}

	/**
	 * 添加收藏
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public Result addFavourate (Map<String, Object> paramMap) {
		try {
			Long courseid = MapUtils.getLong(paramMap, "courseid");
			String remarks = MapUtils.getString(paramMap, "remarks");

			// 当前用户就是评论者
			McUser sessionUser = RequestUtils.getSessionAttr(SpringRequestUtils.getCurrentRequest(), SysConstants.SESSION_KEY_ACTIVEUSER);
			// 当前课程
			McCourse course = getBaseDao().findByPk(courseid);

			// 先查找是否存在
			Where ifexistWhere = new Where.Builder().EQ("courseid", courseid).EQ("favourateuserid", sessionUser.getId()).build();
			int count = favourateDao.countByWhere(ifexistWhere);
			if (count >= 1) {
				return ResultUtils.buildFailed("您已收藏过此视频！");
			}

			McFavourate favourate = new McFavourate();
			favourate.setFavourateuserid(sessionUser.getId());
			favourate.setFavourateuserphone(sessionUser.getPhone());
			favourate.setCourseid(courseid);
			favourate.setCoursename(course.getName());
			favourate.setRemarks(remarks);
			favourate.setCategoryid(course.getCategoryId());
			favourate.setCategoryname(course.getCategoryName());

			int affectrows = favourateDao.insert(favourate);

			if (affectrows == 1) {
				return ResultUtils.buildSuccess("添加成功");
			} else {
				return ResultUtils.buildFailed("抱歉，添加失败");
			}
		} catch (Exception e) {
			throw new ServiceException("用户收藏出错，paramMap:" + paramMap, e);
		}
	}

	/**
	 * 添加笔记
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public Result addNotes (Map<String, Object> paramMap) {
		try {
			Long courseid = MapUtils.getLong(paramMap, "courseid");
			String content = MapUtils.getString(paramMap, "content");

			// 当前用户就是评论者
			McUser sessionUser = RequestUtils.getSessionAttr(SpringRequestUtils.getCurrentRequest(), SysConstants.SESSION_KEY_ACTIVEUSER);
			// 当前课程
			McCourse course = getBaseDao().findByPk(courseid);

			McNotes notes = new McNotes();
			notes.setNoteuserid(sessionUser.getId());
			notes.setNoteuserphone(sessionUser.getPhone());
			notes.setCourseid(courseid);
			notes.setCoursename(course.getName());
			notes.setContent(content);
			notes.setCategoryid(course.getCategoryId());
			notes.setCategoryname(course.getCategoryName());

			int affectrows = notesDao.insert(notes);

			if (affectrows == 1) {
				return ResultUtils.buildSuccess("添加成功");
			} else {
				return ResultUtils.buildFailed("抱歉，添加失败");
			}
		} catch (Exception e) {
			throw new ServiceException("用户添加笔记出错，paramMap:" + paramMap, e);
		}
	}

	/**
	 * 添加评论
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public Result addComment (Map<String, Object> paramMap) {
		try {
			Long courseid = MapUtils.getLong(paramMap, "courseid");
			Long point = MapUtils.getLong(paramMap, "point");
			String content = MapUtils.getString(paramMap, "content");

			// 当前用户就是评论者
			McUser commentator = RequestUtils.getSessionAttr(SpringRequestUtils.getCurrentRequest(), SysConstants.SESSION_KEY_ACTIVEUSER);
			// 当前课程
			McCourse course = getBaseDao().findByPk(courseid);

			McUsercomment usercomment = new McUsercomment();
			usercomment.setCommentatorid(commentator.getId());
			usercomment.setCategoryname(commentator.getUsername());
			usercomment.setCourseid(courseid);
			usercomment.setCoursename(course.getName());
			usercomment.setPoint(point);
			usercomment.setContent(content);
			usercomment.setCategoryid(course.getCategoryId());
			usercomment.setCategoryname(course.getCategoryName());

			int affectrows = usercommentDao.insert(usercomment);

			if (affectrows == 1) {
				return ResultUtils.buildSuccess("添加成功");
			} else {
				return ResultUtils.buildFailed("抱歉，添加失败");
			}
		} catch (Exception e) {
			throw new ServiceException("用户评论出错，paramMap:" + paramMap, e);
		}
	}

	@Override
	public Result getNotes (Map<String, Object> paramMap) {
		try {

			Long courseid = MapUtils.getLong(paramMap, "courseid");
			Long activeuserid = MapUtils.getLong(paramMap, "activeuserid");

			Where where = new Where.Builder().EQ("noteuserid", activeuserid).EQ("courseid", courseid).OrderBy("createdTime", OrderBy.DESC).build();

			List<McNotes> noteslist = notesDao.findByWhere(where);
			return ResultUtils.buildSuccess(noteslist);
		} catch (Exception e) {
			throw new ServiceException("获取笔记出错，paramMap:" + paramMap, e);
		}
	}

	@Override
	public Result getComment (Map<String, Object> paramMap) {
		try {

			Long courseid = MapUtils.getLong(paramMap, "courseid");
			Where where = new Where.Builder().EQ("courseid", courseid).OrderBy("createdTime", OrderBy.DESC).build();
			List<McUsercomment> commentlist = usercommentDao.findByWhere(where);
			return ResultUtils.buildSuccess(commentlist);
		} catch (Exception e) {
			throw new ServiceException("获取评论列表出错，paramMap:" + paramMap, e);
		}
	}

}
