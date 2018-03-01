package com.mirana.frame.utils;

import org.springframework.web.context.ContextLoader;

public class SysPropUtils {

	private static final String KEY_LINE_SEPARATOR = "line.separator";
	/**
	 * 换行符
	 */
	public static final String LINE_SEPARATOR = System.getProperty(KEY_LINE_SEPARATOR);
	private static final String KEY_PATH_SEPARATOR = "path.separator";
	/**
	 * 路径分隔符
	 */
	public static final String PATH_SEPARATOR = System.getProperty(KEY_PATH_SEPARATOR);
	private static final String KEY_FILE_SEPARATOR = "file.separator";
	/**
	 * 文件路径分隔符
	 */
	public static final String FILE_SEPARATOR = System.getProperty(KEY_FILE_SEPARATOR);
	private static final String KEY_USER_DIR       = "user.dir";
	/**
	 * 获取项目根路 <br>
	 * <br>
	 * 在Java项目中获取的地址是"E:\workspace\EclipseMarsProjects\microcourse"<br>
	 * <br>
	 * 在Web项目中获取的地址是"D:\Program Files\Eclipse\eclipse_mars"
	 */
	public static final String USER_DIR = System.getProperty(KEY_USER_DIR);

	public static void setProp (String key, String value) {
		System.setProperty(key, value);
	}

	public static String getProp (String key) {
		return System.getProperty(key);
	}

	/**
	 * 获取Web应用的根目录
	 *
	 * @return
	 */
	public static String getWebRootPath () {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("");
	}

}
