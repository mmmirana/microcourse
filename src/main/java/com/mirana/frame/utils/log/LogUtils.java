package com.mirana.frame.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogUtils
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年12月10日上午11:42:10
 */
public class LogUtils {

	public static void trace (Object msg) {
		console(LogLevel.TRACE, msg);
	}

	public static void debug (Object msg) {
		console(LogLevel.DEBUG, msg);
	}

	public static void info (Object msg) {
		console(LogLevel.INFO, msg);
	}

	public static void warn (Object msg) {
		console(LogLevel.WARN, msg);
	}

	public static void error (Object msg) {
		console(LogLevel.ERROR, msg);
	}

	private static void console (int loglevel, Object msg) {
		if (msg == null) {
			msg = "";
		}

		// -----------start 获取调用的来源堆栈信息
		StackTraceElement[] froms = Thread.currentThread().getStackTrace();
		StackTraceElement from = froms[3];
		String className = from.getClassName();
		Class<?> clazz = LogUtils.class;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("className cannot find");
		}
		String methodName = from.getMethodName();
		int lineNumber = from.getLineNumber();
		// -----------end 获取调用的来源堆栈信息

		Logger log = LoggerFactory.getLogger(clazz);
		String stacktraceInfo = String.format("[ %s.%s(%s.java:%s) ] - ", clazz.getSimpleName(), methodName,
			clazz.getSimpleName(), lineNumber);

		msg = stacktraceInfo + msg;

		switch (loglevel) {
			case LogLevel.TRACE:
				log.trace(msg.toString());
				break;
			case LogLevel.DEBUG:
				log.debug(msg.toString());
				break;
			case LogLevel.INFO:
				log.info(msg.toString());
				break;
			case LogLevel.WARN:
				log.warn(msg.toString());
				break;
			case LogLevel.ERROR:
				log.error(msg.toString());
				break;
			default:
				log.info(msg.toString());
				break;
		}
	}

	public static void main (String[] args) {

		LogUtils.trace("mclog");
		LogUtils.debug("mclog");
		LogUtils.info("mclog");
		LogUtils.warn("mclog");
		LogUtils.error("mclog");

	}

}
