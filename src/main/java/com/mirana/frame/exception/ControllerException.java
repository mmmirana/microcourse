package com.mirana.frame.exception;

import com.mirana.frame.constants.ExceptionConstants;

/**
 * @Title Controller层的异常
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/17 14:40:01
 */
public class ControllerException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ControllerException (String message) {
		super(message);
		this.type = ExceptionConstants.TPYE_CONTROLLER;
		handleException(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ControllerException (String message, Throwable cause) {
		super(message, cause);
		this.type = ExceptionConstants.TPYE_CONTROLLER;
		handleException(message, cause);
	}
}
