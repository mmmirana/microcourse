package com.mirana.frame.db.dbutilsplus.exception;

import com.mirana.frame.db.base.exception.DBException;

/**
 * 自定义DBUtilsPlus异常
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/28 15:54:18
 */
public class DBPlusException extends DBException {

	private static final long serialVersionUID = 1L;

	public DBPlusException (String message) {
		super(message);
	}

	public DBPlusException (String message, Throwable cause) {
		super(message, cause);
	}
}
