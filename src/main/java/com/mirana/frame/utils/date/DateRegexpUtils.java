package com.mirana.frame.utils.date;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.log.LogUtils;
import sun.misc.Regexp;

import java.util.List;

/**
 * 验证日期时间格式的正则工具
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/30 10:27:21
 */
public class DateRegexpUtils {

	/**
	 * 验证字符串datetimeStr是否是日期时间格式 <br>
	 * <br>
	 * 验证规则：<br>
	 * 1、yyyy MM dd HH:mm:ss<br>
	 * 2、yyyy-MM-dd HH:mm:ss<br>
	 * 3、yyyy/MM/dd HH:mm:ss<br>
	 * 4、yyyyMMddHHmmss<br>
	 *
	 * @param datetimeStr 要验证的日期时间格式字符串
	 * @return
	 */
	public static boolean matchDatetime (String datetimeStr) {
		Assert.notNull(datetimeStr, "Parameter 'datetimeStr' must not be null...");

		boolean isMatch = false;
		List<DatePattern> datetimePatternList = DatePattern.getDatetimePatternList();
		for (DatePattern pattern : datetimePatternList) {
			boolean itemMatch = matchDatetime(datetimeStr, pattern);
			if (itemMatch) {
				isMatch = true;
			}
		}
		return isMatch;
	}

	/**
	 * 验证字符串dateStr是否是日期格式 <br>
	 * <br>
	 * 验证规则：<br>
	 * 1、yyyy MM dd<br>
	 * 2、yyyy-MM-dd<br>
	 * 3、yyyy/MM/dd<br>
	 * 4、yyyyMMdds<br>
	 *
	 * @param dateStr 要验证的日期格式字符串
	 * @return
	 */
	public static boolean matchDate (String dateStr) {
		Assert.notNull(dateStr, "Parameter 'dateStr' must not be null...");

		boolean isMatch = false;
		List<DatePattern> datePatternList = DatePattern.getDatePatternList();
		for (DatePattern pattern : datePatternList) {
			boolean itemMatch = matchDate(dateStr, pattern);
			if (itemMatch) {
				isMatch = true;
			}
		}
		return isMatch;
	}

	/**
	 * 验证字符串timeStr是否是时间格式 <br>
	 * <br>
	 * 验证规则：<br>
	 * 1、HH:mm:ss<br>
	 * 2、HHmmss<br>
	 *
	 * @param timeStr 要验证的时间格式字符串
	 * @return
	 */
	public static boolean matchTime (String timeStr) {
		Assert.notNull(timeStr, "Parameter 'timeStr' must not be null...");

		boolean isMatch = false;
		List<DatePattern> timePatternList = DatePattern.getTimePatternList();
		for (DatePattern pattern : timePatternList) {
			boolean itemMatch = matchTime(timeStr, pattern);
			if (itemMatch) {
				isMatch = true;
			}
		}
		return isMatch;
	}

	/**
	 * 是否匹配对应[ DatePattern ]格式的日期时间 <br>
	 * 验证规则：<br>
	 * 1、yyyy MM dd HH:mm:ss<br>
	 * 2、yyyy-MM-dd HH:mm:ss<br>
	 * 3、yyyy/MM/dd HH:mm:ss<br>
	 * 4、yyyyMMddHHmmss<br>
	 *
	 * @param datetimeStr
	 * @param pattern
	 * @return
	 */
	public static boolean matchDatetime (String datetimeStr, DatePattern pattern) {
		Assert.notNull(datetimeStr, "Parameter 'datetimeStr' must not be null...");

		String dateSymbol = "";
		String timeSymbol = "";
		String dateTimeSplit = "";
		switch (pattern) {
			case DATETIME_BLANK:
				dateSymbol = " ";
				timeSymbol = ":";
				dateTimeSplit = " ";

				break;
			case DATETIME_DASH:
				dateSymbol = "-";
				timeSymbol = ":";
				dateTimeSplit = " ";
				break;
			case DATETIME_SLASH:
				dateSymbol = "/";
				timeSymbol = ":";
				dateTimeSplit = " ";
				break;
			case DATETIME_NOSYMBOL:
				dateSymbol = "";
				timeSymbol = "";
				dateTimeSplit = "";
				break;
			default:
				throw new UtilsException("Unsupport's DatePattern...");
		}
		// 日期时间的正则表达式
		String datetimeReg = getDateTimeReg(dateSymbol, timeSymbol, dateTimeSplit).exp;
		return datetimeStr.matches(datetimeReg);
	}

	/**
	 * 匹配对应[ DatePattern ]格式的日期 <br>
	 * 验证规则：<br>
	 * 1、yyyy MM dd<br>
	 * 2、yyyy-MM-dd<br>
	 * 3、yyyy/MM/dd<br>
	 * 4、yyyyMMdds<br>
	 *
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean matchDate (String dateStr, DatePattern pattern) {
		Assert.notNull(dateStr, "Parameter 'dateStr' must not be null...");

		String dateSymbol = "";
		switch (pattern) {
			case DATE_BLANK:
				dateSymbol = " ";
				break;
			case DATE_DASH:
				dateSymbol = "-";
				break;
			case DATE_SLASH:
				dateSymbol = "/";
				break;
			case DATE_NOSYMBOL:
				dateSymbol = "";
				break;
			default:
				throw new UtilsException("Unsupport's DatePattern...");
		}
		// 日期正则表达式
		String dateReg = getDateReg(dateSymbol).exp;
		return dateStr.matches(dateReg);
	}

	/**
	 * 是否匹配对应[ DatePattern ]格式的时间 <br>
	 * 验证规则：<br>
	 * 1、HH:mm:ss<br>
	 * 2、HHmmss<br>
	 *
	 * @param timeStr
	 * @param pattern
	 * @return
	 */
	public static boolean matchTime (String timeStr, DatePattern pattern) {
		Assert.notNull(timeStr, "Parameter 'timeStr' must not be null...");

		String timeSymbol = "";
		switch (pattern) {
			case TIME_COLON:
				timeSymbol = ":";
				break;
			case TIME_NOSYMBOL:
				timeSymbol = "";
				break;
			default:
				throw new UtilsException("Unsupport's DatePattern...");
		}
		// 日期正则表达式
		String timeReg = getTimeReg(timeSymbol).exp;
		return timeStr.matches(timeReg);
	}

	/**
	 * 根据传入的【日期时间字符串|日期字符串|时间字符串】获取对应的格式枚举
	 *
	 * @param datestr
	 * @return
	 */
	public static DatePattern getPattern (String datestr) {
		Assert.notNull(datestr, "Parameter 'datestr' must not be null...");

		DatePattern pattern = null;
		// 判断是否是日期时间格式
		DatePattern datetimePattern = getDatetimePattern(datestr);
		if (datetimePattern != null) {
			pattern = datetimePattern;
		} else {
			// 如果不是日期时间格式，那么判断是否是日期格式
			DatePattern datePattern = getDatePattern(datestr);
			if (datePattern != null) {
				pattern = datePattern;
			} else {
				// 如果也不是日期格式，判断是否是时间格式
				DatePattern timePattern = getTimePattern(datestr);
				if (timePattern != null) {
					pattern = timePattern;
				} else {
					LogUtils.info("抱歉，无法识别您的时间格式：[ " + datestr + " ]");
				}
			}
		}
		return pattern;
	}

	/**
	 * 根据传递的日期时间字符串， 获取对应的日期时间格式
	 *
	 * @param datetimeStr
	 * @return
	 */
	public static DatePattern getDatetimePattern (String datetimeStr) {
		Assert.notNull(datetimeStr, "Parameter 'datetimeStr' must not be null...");

		List<DatePattern> datetimePatternList = DatePattern.getDatetimePatternList();
		for (DatePattern pattern : datetimePatternList) {
			boolean isMatch = matchDatetime(datetimeStr, pattern);
			if (isMatch) {
				return pattern;
			}
		}
		return null;
	}

	/**
	 * 根据传递的日期字符串， 获取对应的日期格式
	 *
	 * @param dateStr
	 * @return
	 */
	public static DatePattern getDatePattern (String dateStr) {
		Assert.notNull(dateStr, "Parameter 'dateStr' must not be null...");

		List<DatePattern> datePatternList = DatePattern.getDatePatternList();
		for (DatePattern pattern : datePatternList) {
			boolean isMatch = matchDate(dateStr, pattern);
			if (isMatch) {
				return pattern;
			}
		}
		return null;
	}

	/**
	 * 根据传递的时间字符串， 获取对应的时间格式
	 *
	 * @param timeStr
	 * @return
	 */
	public static DatePattern getTimePattern (String timeStr) {
		Assert.notNull(timeStr, "Parameter 'timeStr' must not be null...");

		List<DatePattern> timePatternList = DatePattern.getTimePatternList();
		for (DatePattern pattern : timePatternList) {
			boolean isMatch = matchTime(timeStr, pattern);
			if (isMatch) {
				return pattern;
			}
		}
		return null;
	}

	/**
	 * 根据年月日分隔符、时分秒分隔符、日期和时间之间的分隔符来获取对应的正则表达式
	 *
	 * @param dateSymbol    年月日分隔符
	 * @param timeSymbol    时分秒分割符
	 * @param dateTimeSplit 日期和时间之间的分隔符
	 * @return
	 */
	public static Regexp getDateTimeReg (String dateSymbol, String timeSymbol, String dateTimeSplit) {
		Assert.notNull(dateSymbol, "The parameter 'dateTimeSplit' must not be null...");
		Assert.notNull(timeSymbol, "The parameter 'dateTimeSplit' must not be null...");
		Assert.notNull(dateTimeSplit, "The parameter 'dateTimeSplit' must not be null...");

		String dateRegStr = getDateReg(dateSymbol).exp;
		String timeRegStr = getTimeReg(timeSymbol).exp;
		Regexp dateTimeReg = new Regexp(dateRegStr + dateTimeSplit + timeRegStr);
		return dateTimeReg;
	}

	/**
	 * * 根据date的分割标志获取对应的正则表达式 <br>
	 * <br>
	 * 规则如下：<br>
	 * yyyy：年必须四位，范围0001-9999<br>
	 * MM：月必须两位，范围01-12<br>
	 * dd：日必须两位<br>
	 * 1、3、5、7、8、10、12月范围01-31<br>
	 * 4、6、9、11月范围01-30<br>
	 * 2月范围01-29（不判断闰年或者平年）<br>
	 *
	 * @param dateSymbol 年月日分隔符
	 * @return
	 */
	public static Regexp getDateReg (String dateSymbol) {
		Assert.notNull(dateSymbol, "The parameter 'dateSymbol' must not be null...");

		// yyyy-MM-dd | yyyy/MM/dd | yyyy MM dd | yyyyMMdd
		Regexp dateReg = new Regexp("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})" + dateSymbol + "(((0[13578]|1[02])"
			+ dateSymbol + "(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)" + dateSymbol + "(0[1-9]|[12][0-9]|30))|(02" + dateSymbol
			+ "(0[1-9]|[1][0-9]|2[0-9])))");
		return dateReg;
	}

	/**
	 * 根据time的分割标志获取对应的正则表达式 <br>
	 * <br>
	 * 规则如下：<br>
	 * HH：时必须两位，范围00-23<br>
	 * mm：分必须两位，范围00-59<br>
	 * ss：秒必须两位范围00-59<br>
	 *
	 * @param timeSymbol
	 * @return
	 */
	public static Regexp getTimeReg (String timeSymbol) {
		Assert.notNull(timeSymbol, "The parameter 'timeSymbol' must not be null...");

		// HH:mm:ss | HHmmss
		Regexp timeReg = new Regexp("([0|1][0-9]|[2][0-3])" + timeSymbol + "([0-5][0-9])" + timeSymbol + "(([0-5][0-9]))");
		return timeReg;
	}
}
