package com.mirana.module.userhome.service;

import com.mirana.frame.base.service.IBaseService;
import com.mirana.frame.utils.result.Result;
import com.mirana.module.common.model.McUser;

/**
 * IUserService.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:20
 */
public interface IUserService extends IBaseService<McUser> {
	/**
	 * 用户登陆
	 *
	 * @param username 用户名/手机号/Email
	 * @param password 密码
	 * @return
	 */
	public Result login (String username, String password);

	/**
	 * 注册
	 *
	 * @param username 用户名
	 * @param phone    手机号
	 * @param email    邮箱
	 * @param password 密码
	 * @return
	 */
	public Result register (String username, String phone, String email, String password);
}
