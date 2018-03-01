package com.mirana.frame.utils.date;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期时间格式枚举
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/30 10:07:32
 */
public enum DatePattern {

	// 日期时间格式

	/**
	 * 【日期时间】空格符号 yyyy MM dd HH:mm:ss
	 **/
	DATETIME_BLANK("yyyy MM dd HH:mm:ss"),

	/**
	 * 【日期时间】短横线符号 yyyy-MM-dd HH:mm:ss
	 **/
	DATETIME_DASH("yyyy-MM-dd HH:mm:ss"),

	/**
	 * 【日期时间】斜杠符号 yyyy/MM/dd HH:mm:ss
	 **/
	DATETIME_SLASH("yyyy/MM/dd HH:mm:ss"),

	/**
	 * 【日期时间】无符号 yyyyMMddHHmmss
	 **/
	DATETIME_NOSYMBOL("yyyyMMddHHmmss"),

	// 日期格式

	/**
	 * 【日期】空格符号 yyyy MM dd
	 **/
	DATE_BLANK("yyyy MM dd"),

	/**
	 * 【日期】短横线符号 yyyy-MM-dd
	 **/
	DATE_DASH("yyyy-MM-dd"),

	/**
	 * 【日期】斜杠符号 yyyy/MM/dd
	 **/
	DATE_SLASH("yyyy/MM/dd"),

	/**
	 * 【日期】无符号 yyyyMMdd
	 **/
	DATE_NOSYMBOL("yyyyMMdd"),

	// 时间格式

	/**
	 * 【时间】冒号符号 HH:mm:ss
	 **/
	TIME_COLON("HH:mm:ss"),

	/**
	 * 【时间】 无符号HHmmss
	 **/
	TIME_NOSYMBOL("HHmmss");

	/**
	 * 日期时间格式的集合
	 **/
	private static List<DatePattern> datetimePatternList = new ArrayList<DatePattern>();

	/**
	 * 日期格式的集合
	 **/
	private static List<DatePattern> datePatternList = new ArrayList<DatePattern>();

	/**
	 * 日期格式的集合
	 **/
	private static List<DatePattern> timePatternList = new ArrayList<DatePattern>();

	static {
		datetimePatternList.add(DatePattern.DATETIME_BLANK);
		datetimePatternList.add(DatePattern.DATETIME_DASH);
		datetimePatternList.add(DatePattern.DATETIME_NOSYMBOL);
		datetimePatternList.add(DatePattern.DATETIME_SLASH);

		datePatternList.add(DatePattern.DATE_BLANK);
		datePatternList.add(DatePattern.DATE_DASH);
		datePatternList.add(DatePattern.DATE_NOSYMBOL);
		datePatternList.add(DatePattern.DATE_SLASH);

		timePatternList.add(DatePattern.TIME_COLON);
		timePatternList.add(DatePattern.TIME_NOSYMBOL);

	}

	private String patternStr;

	private DatePattern (String patternStr) {
		this.patternStr = patternStr;
	}

	public static List<DatePattern> getDatetimePatternList () {
		return datetimePatternList;
	}

	public static List<DatePattern> getDatePatternList () {
		return datePatternList;
	}

	public static List<DatePattern> getTimePatternList () {
		return timePatternList;
	}

	/**
	 * 获取格式的字符串
	 *
	 * @return
	 */
	public String getPatternStr () {
		return patternStr;
	}

	public void setPatternStr (String patternStr) {
		this.patternStr = patternStr;
	}

}
