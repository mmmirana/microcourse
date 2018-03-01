package com.mirana.frame.db.base.exception;

import com.mirana.frame.exception.DaoException;

/**
 * 数据库异常类
 * <p>
 * DBException.java
 *
 * @Description
 * @Created Administrator
 * @DateTime 2017年8月27日下午9:26:11
 */
public class DBException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DBException (String message, Throwable cause) {
		super(message, cause);
	}

	public DBException (String message) {
		super(message);
	}

}
