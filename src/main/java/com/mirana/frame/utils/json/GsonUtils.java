package com.mirana.frame.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mirana.frame.utils.date.DatePattern;

import java.lang.reflect.Type;

/**
 * Gson工具类（json <--> bean）
 *
 * @author wangzf
 * @datetime 2016年7月23日11:10:41
 */
public class GsonUtils {

	/**
	 * Gson的静态实例，日期格式yyyy/MM/dd HH:mm:ss
	 */
	private static Gson gson = new GsonBuilder()
		// Date类型序列化
		.setDateFormat(DatePattern.DATETIME_DASH.getPatternStr())
		// 内部类不序列化
		.disableInnerClassSerialization()
		// 创建Gson示例
		.create();

	/**
	 * 私有无参构造
	 */
	private GsonUtils () {
		super();
	}

	public static String stringify (Object object) {
		return gson.toJson(object);
	}

	public static <T> T parse (String text, Class<T> clazz) throws JsonSyntaxException {
		return gson.fromJson(text, clazz);
	}

	public static <T> T parse (String text, Type type) throws JsonSyntaxException {
		return gson.fromJson(text, type);
	}

}
