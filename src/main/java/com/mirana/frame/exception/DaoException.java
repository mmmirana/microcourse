package com.mirana.frame.exception;

import com.mirana.frame.constants.ExceptionConstants;

/**
 * @Title Dao层的异常
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/17 14:09:20
 */
public class DaoException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public DaoException (String message) {
		super(message);
		this.type = ExceptionConstants.TPYE_DAO;
		handleException(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DaoException (String message, Throwable cause) {
		super(message, cause);
		this.type = ExceptionConstants.TPYE_DAO;
		handleException(message, cause);
	}

}
