package com.mirana.frame.utils.json;

import java.lang.reflect.Type;

public class JsonUtils {

	/**
	 * 将一个对象转化为json字符串
	 *
	 * @param object
	 * @return
	 */
	public static String stringify (Object object) {
		return FastjsonUtils.stringify(object);
	}

	/**
	 * 将一个json字符串转化为java对象
	 *
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T parse (String text, Class<T> clazz) {
		return FastjsonUtils.parse(text, clazz);
	}

	/**
	 * 将一个json字符串转化为其他复杂的java对象，比如List, Array, Map
	 *
	 * @param text
	 * @param type
	 * @return
	 */
	public static <T> T parse (String text, Type type) {
		return FastjsonUtils.parse(text, type);
	}

	/**
	 * 将一个对象转化为json字符串并格式化
	 *
	 * @param object
	 * @return
	 */
	public static String format (Object object) {
		return formatJson(stringify(object));
	}

	/**
	 * 格式化json字符串
	 */
	private static String formatJson (String jsonStr) {
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
				case '{':
				case '[':
					jsonForMatStr.append(c + "\n");
					level++;
					break;
				case ',':
					jsonForMatStr.append(c + "\n");
					break;
				case '}':
				case ']':
					jsonForMatStr.append("\n");
					level--;
					jsonForMatStr.append(getLevelStr(level));
					jsonForMatStr.append(c);
					break;
				default:
					jsonForMatStr.append(c);
					break;
			}
		}

		return jsonForMatStr.toString();

	}

	private static String getLevelStr (int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}
}
