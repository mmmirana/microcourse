package com.mirana.module.common.service;

import com.mirana.frame.base.service.IBaseService;
import com.mirana.frame.utils.result.Result;
import com.mirana.module.common.model.McCourse;

import java.util.Map;

/**
 * ICourseService.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:21
 */
public interface ICourseService extends IBaseService<McCourse> {

	/**
	 * 添加收藏
	 *
	 * @param paramMap
	 * @return
	 */
	Result addFavourate (Map<String, Object> paramMap);

	/**
	 * 添加笔记
	 *
	 * @param paramMap
	 * @return
	 */
	Result addNotes (Map<String, Object> paramMap);

	/**
	 * 用户评论
	 *
	 * @param paramMap
	 * @return
	 */
	Result addComment (Map<String, Object> paramMap);

	/**
	 * 获取笔记
	 *
	 * @param paramMap
	 * @return
	 */
	Result getNotes (Map<String, Object> paramMap);

	/**
	 * 获取评论
	 *
	 * @param paramMap
	 * @return
	 */
	Result getComment (Map<String, Object> paramMap);

}
