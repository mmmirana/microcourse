package com.mirana.frame.utils;

import com.mirana.frame.exception.UtilsException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 驼峰命名工具类
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 13:58:14
 */
public class CamelUtils {

	private static final Logger log = LoggerFactory.getLogger(CamelUtils.class);

	private static final String UNDERLINE = "_";

	/**
	 * 格式化字符串<br>
	 * <br>
	 * 规则：忽略开头所有空白字符，以字母开头，后面只包含[A-Za-z0-9_]或者空白字符
	 *
	 * @param param
	 * @return 去掉所有的空白字符，首字母小写的字符串
	 */
	public static String formatString (String param) {
		// 必须符合的规则：忽略开头所有空白字符，以字母开头，后面只包含[A-Za-z0-9_]或者空白字符
		if (!param.matches("^\\s*[A-Za-z][\\s|\\w]*$")) {
			throw new UtilsException(
				"The parameter '" + param + "'should begin with English Letter and end with [A-Za-z0-9_] if ignore all blank char...");
		}

		// 将任意的空白字符替换为""
		if (param.matches(".*\\s.*")) {
			log.debug("The parameter '" + param + "' contains blank char which whiil be replaced by \"\" forcibly...");
			param = param.replaceAll("\\s", "");
		}

		char firstChar = Character.toLowerCase(param.charAt(0));
		String camelString = firstChar + param.substring(1);
		return camelString;
	}

	/**
	 * 将一个字符串转化为驼峰命名的规则
	 *
	 * @param param
	 * @return
	 */
	public static String toCamelString (String param) {
		// 最终的驼峰命名的字符串
		StringBuffer paramSB = new StringBuffer();

		// 先格式化
		String formatStr = formatString(param);
		// 按照下划线分割
		String[] splitStrArray = formatStr.split(UNDERLINE + "+");
		for (int i = 0; i < splitStrArray.length; i++) {
			String splitItem = splitStrArray[i];
			if (i != 0) {
				splitItem = Character.toUpperCase(splitItem.charAt(0)) + splitItem.substring(1);
			}
			paramSB.append(splitItem);
		}
		return paramSB.toString();
	}

	/**
	 * 将一个字符串转化为下划线的规则
	 *
	 * @param param
	 * @return
	 */
	public static String toUnderlineString (String param) {
		// 最终的驼峰命名的字符串
		StringBuffer paramSB = new StringBuffer();

		// 先格式化
		String formatStr = formatString(param);
		// 按照下划线分割
		String[] splitStrArray = formatStr.split(UNDERLINE + "+");
		for (int i = 0; i < splitStrArray.length; i++) {
			String splitItem = splitStrArray[i];
			if (i != 0) {
				splitStrArray[i] = UNDERLINE + Character.toLowerCase(splitItem.charAt(0)) + splitItem.substring(1);
			}
		}
		formatStr = StringUtils.join(splitStrArray);
		for (int i = 0; i < formatStr.length(); i++) {
			char charitem = formatStr.charAt(i);
			if (Character.isUpperCase(charitem)) {
				paramSB.append(UNDERLINE + Character.toLowerCase(charitem));
			} else {
				paramSB.append(charitem);
			}
		}
		return paramSB.toString();
	}
}
