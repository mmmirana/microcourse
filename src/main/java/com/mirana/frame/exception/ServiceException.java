package com.mirana.frame.exception;

import com.mirana.frame.constants.ExceptionConstants;

/**
 * @Title Service层的异常
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/17 14:36:43
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ServiceException (String message) {
		super(message);
		this.type = ExceptionConstants.TPYE_SERVICE;
		handleException(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceException (String message, Throwable cause) {
		super(message, cause);
		this.type = ExceptionConstants.TPYE_SERVICE;
		handleException(message, cause);
	}

}
