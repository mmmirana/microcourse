package com.mirana.frame.utils.date;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title 日期工具类
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/16 16:48:16
 */
public class DateUtils {

	/**
	 * 年
	 **/
	public static final int INT_YEAR         = Calendar.YEAR;
	/**
	 * 月
	 **/
	public static final int INT_MONTH        = Calendar.MONTH;
	/**
	 * 日
	 **/
	public static final int INT_DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
	/**
	 * 星期
	 **/
	public static final int INT_DAY_OF_WEEK  = Calendar.DAY_OF_WEEK;
	/**
	 * 时
	 **/
	public static final int INT_HOUR         = Calendar.HOUR_OF_DAY;
	/**
	 * 分
	 **/
	public static final int INT_MINUTE       = Calendar.MINUTE;
	/**
	 * 秒
	 **/
	public static final int INT_SECOND       = Calendar.SECOND;

	/**
	 * 私有无参构造
	 */
	private DateUtils () {
		super();
	}

	/**
	 * 按照传入的pattern格式，转化Date为String
	 *
	 * @param date    要转换的日期
	 * @param pattern 要转化的日期格式 {@link DatePattern}
	 * @return
	 */
	public static String format (Date date, DatePattern pattern) {
		Assert.notNull(date, "Parameter 'date' must not be null!");
		Assert.notNull(pattern, "Parameter 'pattern' must not be null!");

		return format(date, pattern.getPatternStr());
	}

	/**
	 * 按照传入的自定义pattern格式，转化Date为String
	 *
	 * @param date    要转换的日期
	 * @param patternStr 自定义日期格式
	 * @return
	 */
	public static String format (Date date, String patternStr) {
		Assert.notNull(date, "Parameter 'date' must not be null!");
		Assert.hasLength(patternStr, "Parameter 'patternStr' must not be null and not the empty String!");

		return getDateFormat(patternStr).format(date);
	}

	/**
	 * 按照现有的 {@link DatePattern}的格式，转化String为Date
	 *
	 * @param dateStr 要转换的日期字符串
	 * @return
	 */
	public static Date parseDate (String dateStr) {
		Assert.hasLength(dateStr, "Parameter 'strDate' must not be null and not the empty String!");

		// 根据传入的时间判断时间字符串的格式
		DatePattern pattern = DateRegexpUtils.getPattern(dateStr);
		return parseDate(dateStr, pattern.getPatternStr());
	}

	/**
	 * 按照传入的pattern格式，转化String为Date
	 *
	 * @param strDate 要转换的日期字符串
	 * @param pattern 日期字符串的日期格式 {@link DatePattern}
	 * @return
	 */
	public static Date parseDate (String strDate, DatePattern pattern) {
		Assert.hasLength(strDate, "Parameter 'strDate' must not be null and not the empty String!");
		Assert.notNull(pattern, "Parameter 'pattern' must not be null!");

		return parseDate(strDate, pattern.getPatternStr());
	}

	/**
	 * 按照传入的自定义pattern格式，转化String为Date
	 *
	 * @param strDate    要转换的日期字符串
	 * @param patternStr 日期字符串的日期格式
	 * @return
	 */
	public static Date parseDate (String strDate, String patternStr) {
		Assert.hasLength(strDate, "Parameter 'strDate' must not be null and not the empty String!");
		Assert.hasLength(patternStr, "Parameter 'patternStr' must not be null!");

		try {
			Date date = getDateFormat(patternStr).parse(strDate);
			return date;
		} catch (ParseException e) {
			throw new UtilsException("Error: DateUtils.parseDate()", e);
		}
	}

	/**
	 * 将某个时间格式的字符串转化为toPattern时间格式的字符串
	 *
	 * @param fromDateStr 原先的时间字符串
	 * @param toPattern   要转化的字符串的时间格式
	 * @return
	 */
	public static String format (String fromDateStr, DatePattern toPattern) {
		Assert.hasLength(fromDateStr, "Parameter 'fromDateStr' must not be null and not the empty String!");
		Assert.notNull(toPattern, "Parameter 'toPattern' must not be null!");

		Date date = parseDate(fromDateStr);
		return format(date, toPattern);
	}

	/**
	 * 当前年
	 *
	 * @return
	 */
	public static int getNowYear () {
		return getCalendar().get(INT_YEAR);
	}

	/**
	 * 当前月
	 *
	 * @return
	 */
	public static int getNowMonth () {
		return getCalendar().get(INT_MONTH) + 1;
	}

	/**
	 * 当前日
	 *
	 * @return
	 */
	public static int getNowDayOfMonth () {
		return getCalendar().get(INT_DAY_OF_MONTH);
	}

	/**
	 * 当前星期
	 *
	 * @return
	 */
	public static int getNowDayOfWeek () {
		return getCalendar().get(INT_DAY_OF_WEEK);
	}

	/**
	 * 当前时
	 *
	 * @return
	 */
	public static int getNowHours () {
		return getCalendar().get(INT_HOUR);
	}

	/**
	 * 当前分
	 *
	 * @return
	 */
	public static int getNowMinutes () {
		return getCalendar().get(INT_MINUTE);
	}

	/**
	 * 当前秒
	 *
	 * @return
	 */
	public static int getNowSeconds () {
		return getCalendar().get(INT_SECOND);
	}

	/**
	 * 年
	 *
	 * @param date
	 * @return
	 */
	public static int getYear (Date date) {
		return getCalendarByDate(date).get(INT_YEAR);
	}

	/**
	 * 月
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth (Date date) {
		return getCalendarByDate(date).get(INT_MONTH) + 1;
	}

	/**
	 * 日
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth (Date date) {
		return getCalendarByDate(date).get(INT_DAY_OF_MONTH);
	}

	/**
	 * 星期
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek (Date date) {
		return getCalendarByDate(date).get(INT_DAY_OF_WEEK);
	}

	/**
	 * 时
	 *
	 * @param date
	 * @return
	 */
	public static int getHours (Date date) {
		return getCalendarByDate(date).get(INT_HOUR);
	}

	/**
	 * 分
	 *
	 * @param date
	 * @return
	 */
	public static int getMinutes (Date date) {
		return getCalendarByDate(date).get(INT_MINUTE);
	}

	/**
	 * 秒
	 *
	 * @param date
	 * @return
	 */
	public static int getSeconds (Date date) {
		return getCalendarByDate(date).get(INT_SECOND);
	}

	/**
	 * 获取某个时间的当月的第一天的时间
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getFirstDayOfMonth (Date date, DatePattern pattern) {
		Assert.notNull(date, "Parameter 'date' must not be null!");

		Date convertDate = parseDate(format(date, pattern), pattern);

		Calendar cal = getCalendar();
		cal.setTime(convertDate);
		// 第一天
		cal.add(INT_DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取上个月的第一天的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfLastMonth (Date date, DatePattern pattern) {
		Date firstDayOfMonth = getFirstDayOfMonth(date, pattern);
		Map<Integer, Integer> offsetMap = new HashMap<Integer, Integer>();
		offsetMap.put(INT_MONTH, -1);
		return offset(firstDayOfMonth, offsetMap);
	}

	/**
	 * 获取下个月的第一天的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfNextMonth (Date date, DatePattern pattern) {
		Date firstDayOfMonth = getFirstDayOfMonth(date, pattern);
		Map<Integer, Integer> offsetMap = new HashMap<Integer, Integer>();
		offsetMap.put(INT_MONTH, 1);
		return offset(firstDayOfMonth, offsetMap);
	}

	/**
	 * 根据传入的Date参数偏移一定的时间
	 *
	 * @param date      给定的时间
	 * @param offsetMap 偏移量的Map
	 * @return
	 */
	public static Date offset (Date date, Map<Integer, Integer> offsetMap) {
		Assert.notNull(date, "Parameter 'date' must not be null!");

		Calendar cal = getCalendarByDate(date);
		// 设置年的偏移量
		if (offsetMap.containsKey(INT_YEAR) && offsetMap.get(INT_YEAR) != null) {
			cal.add(INT_YEAR, offsetMap.get(INT_YEAR));
		}
		// 设置月份的偏移量
		if (offsetMap.containsKey(INT_MONTH) && offsetMap.get(INT_MONTH) != null) {
			cal.add(INT_MONTH, offsetMap.get(INT_MONTH));
		}
		// 设置日的偏移量
		if (offsetMap.containsKey(INT_DAY_OF_MONTH) && offsetMap.get(INT_DAY_OF_MONTH) != null) {
			cal.add(INT_DAY_OF_MONTH, offsetMap.get(INT_DAY_OF_MONTH));
		}
		// 设置时的偏移量
		if (offsetMap.containsKey(INT_HOUR) && offsetMap.get(INT_HOUR) != null) {
			cal.add(INT_HOUR, offsetMap.get(INT_HOUR));
		}
		// 设置分的偏移量
		if (offsetMap.containsKey(INT_MINUTE) && offsetMap.get(INT_MINUTE) != null) {
			cal.add(INT_MINUTE, offsetMap.get(INT_MINUTE));
		}
		// 设置秒的偏移量
		if (offsetMap.containsKey(INT_SECOND) && offsetMap.get(INT_SECOND) != null) {
			cal.add(INT_SECOND, offsetMap.get(INT_SECOND));
		}

		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的年份
	 *
	 * @param date       给定的时间
	 * @param yearOffset 年份偏移量
	 * @return
	 */
	public static Date offsetYear (Date date, int yearOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_YEAR, yearOffset);
		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的月份
	 *
	 * @param date        给定的时间
	 * @param monthOffset 月份偏移量
	 * @return
	 */
	public static Date offsetMonth (Date date, int monthOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_MONTH, monthOffset);
		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的天数
	 *
	 * @param date             给定的时间
	 * @param dayOfMonthOffset 天数偏移量
	 * @return
	 */
	public static Date offsetDayOfMonth (Date date, int dayOfMonthOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_DAY_OF_MONTH, dayOfMonthOffset);
		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的小时
	 *
	 * @param date       给定的时间
	 * @param hourOffset 天数偏移量
	 * @return
	 */
	public static Date offsetHour (Date date, int hourOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_HOUR, hourOffset);
		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的分钟
	 *
	 * @param date         给定的时间
	 * @param minuteOffset 分钟偏移量
	 * @return
	 */
	public static Date offsetMinute (Date date, int minuteOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_MINUTE, minuteOffset);
		return cal.getTime();
	}

	/**
	 * 根据传入的Date参数偏移一定的秒
	 *
	 * @param date         给定的时间
	 * @param secondOffset 秒偏移量
	 * @return
	 */
	public static Date offsetSecond (Date date, int secondOffset) {
		Calendar cal = getCalendarByDate(date);
		cal.add(INT_SECOND, secondOffset);
		return cal.getTime();
	}

	/**
	 * 根据自定义Date格式获取DateFormat对象
	 *
	 * @param patternStr
	 * @return
	 */
	private static DateFormat getDateFormat (String patternStr) {
		Assert.hasLength(patternStr, "Parameter 'patternStr' must not be null and not the empty String!");

		SimpleDateFormat sdf = new SimpleDateFormat(patternStr);
		return sdf;
	}

	/**
	 * 获取Calendar当前时间实例
	 *
	 * @return
	 */
	private static Calendar getCalendar () {
		return Calendar.getInstance();
	}

	/**
	 * 根据传入的Date参数获取Calendar实例
	 *
	 * @param date
	 * @return
	 */
	private static Calendar getCalendarByDate (Date date) {
		Assert.notNull(date, "Parameter 'date' must not be null!");

		Calendar calendar = getCalendar();
		calendar.setTime(date);
		return calendar;
	}
}
