package com.mirana.module.userhome.service.impl;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.service.BaseServiceImpl;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.db.base.query.WhereUtils;
import com.mirana.frame.exception.ServiceException;
import com.mirana.frame.spring.SpringRequestUtils;
import com.mirana.frame.utils.result.Result;
import com.mirana.frame.utils.result.ResultUtils;
import com.mirana.module.common.model.McUser;
import com.mirana.module.userhome.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * UserServiceImpl.java
 *
 * @Title
 * @Description
 * @CreatedBy Assassin_DBGenerator
 * @DateTime 2017/12/14 12:22:20
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<McUser> implements IUserService {

	@Resource(name = "userDao")
	@Override
	public void setBaseDao (IBaseDao<McUser> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public Result login (String username, String password) {
		Result result = null;

		Where[] orWhere = new Where[]{ //
			WhereUtils.EQ("username", username), // 用户名
			WhereUtils.EQ("phone", username), // 手机
			WhereUtils.EQ("email", username) // 邮箱
		};

		Where loginWhere = new Where.Builder().Or(orWhere).EQ("password", password).build();

		try {
			McUser user = getBaseDao().findUniqueByWhere(loginWhere);
			if (user == null) {
				result = ResultUtils.buildFailed("抱歉，账户或密码错误！");
			} else {
				SpringRequestUtils.getCurrentSession().setAttribute(SysConstants.SESSION_KEY_ACTIVEUSER, user);
				result = ResultUtils.buildSuccess();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = ResultUtils.buildError();
		}
		return result;
	}

	@Override
	public Result register (String username, String phone, String email, String password) {
		try {
			// 校验注册信息是否异常
			int count = getBaseDao().countByWhere(WhereUtils.EQ("username", username));
			if (count > 0) {
				return ResultUtils.buildFailed("用户名太抢手了，再重新挑选一个呗！");
			}
			count = getBaseDao().countByWhere(WhereUtils.EQ("phone", phone));
			if (count > 0) {
				return ResultUtils.buildFailed("手机号码已被注册，请确认您的手机号码！");
			}
			count = getBaseDao().countByWhere(WhereUtils.EQ("email", email));
			if (count > 0) {
				return ResultUtils.buildFailed("电子邮箱已被注册，换个姿势再来一次！");
			}
			// 注册用户
			McUser user = new McUser();
			user.setUsername(username);
			user.setPhone(phone);
			user.setEmail(email);
			user.setPassword(password);
			int affectrows = getBaseDao().insert(user);
			if (affectrows > 0) {
				return ResultUtils.buildSuccess();
			} else {
				return ResultUtils.buildFailed();
			}
		} catch (SQLException e) {
			throw new ServiceException("用户注册时发生异常！", e);
		}
	}

}
