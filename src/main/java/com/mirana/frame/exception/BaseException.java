package com.mirana.frame.exception;

import com.mirana.frame.utils.log.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title 自定义异常的超类
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/17 14:09:29
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 异常类型
	 **/
	protected String type;

	protected BaseException () {
		super();
	}

	protected BaseException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	protected BaseException (String message, Throwable cause) {
		super(message, cause);
	}

	protected BaseException (String message) {
		super(message);
	}

	protected BaseException (Throwable cause) {
		super(cause);
		LogUtils.error("[ Exception ] Cause: " + cause.toString());
		cause.printStackTrace();
	}

	public String getType () {
		return type;
	}

	public void setType (String type) {
		this.type = type;
	}

	protected void handleException (Throwable cause) {
		handleException(null, cause);
	}

	protected void handleException (String message) {
		handleException(message, null);
	}

	protected void handleException (String message, Throwable cause) {
		// TODO handle exception
		LogUtils.error("[ Exception ] Type: " + type + ", Msg: " + message + ", Cause: " + cause.toString());
		// cause.printStackTrace();
	}

}
