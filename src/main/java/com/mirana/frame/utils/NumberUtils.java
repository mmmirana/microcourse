package com.mirana.frame.utils;

import java.text.NumberFormat;

/**
 * @Title 数字工具类
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月27日上午10:09:45
 */
public class NumberUtils {

	/**
	 * 格式化数字为保留两位小数的字符串
	 *
	 * @param number
	 * @return
	 */
	public static String roundFormat (Number number) {
		return roundFormat(number, 2);
	}

	/**
	 * 保留指定小数位数，格式化数字为字符串
	 *
	 * @param number 要格式化的数字
	 * @param digits 要保留的小数位数
	 * @return
	 */
	public static String roundFormat (Number number, int digits) {
		NumberFormat numberformat = NumberFormat.getNumberInstance();
		// 小数位数
		numberformat.setMaximumFractionDigits(digits);
		numberformat.setMinimumFractionDigits(digits);
		return numberformat.format(number);
	}
}
