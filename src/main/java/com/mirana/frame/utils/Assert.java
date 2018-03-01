package com.mirana.frame.utils;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.file.FileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.Collection;

/**
 * 断言
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/24 19:03:20
 */
public class Assert {

	/**
	 * 断言一个表达式的结果为true
	 *
	 * @param expression
	 */
	public static void isTrue (boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 断言一个表达式的结果为true
	 *
	 * @param expression
	 * @param message
	 */
	public static void isTrue (boolean expression, String message) {
		if (!expression) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个字符串有长度
	 *
	 * @param text
	 */
	public static void hasLength (String text) {
		hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * 断言一个字符串有长度
	 *
	 * @param text
	 * @param message
	 */
	public static void hasLength (String text, String message) {
		if (text == null || text.length() == 0) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个字符串有除了whitespace之外的文本
	 *
	 * @param text
	 */
	public static void hasText (String text) {
		hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, or blank");
	}

	/**
	 * 断言一个字符串有除了whitespace之外的文本
	 *
	 * @param text
	 * @param message
	 */
	public static void hasText (String text, String message) {
		if (text == null || text.trim().length() == 0) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个对象不为null对象
	 *
	 * @param object
	 */
	public static void notNull (Object object) {
		notNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * 断言一个对象不为null对象
	 *
	 * @param object
	 * @param message
	 */
	public static void notNull (Object object, String message) {
		if (object == null) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个数组不是空数组
	 *
	 * @param array
	 */
	public static void notEmpty (Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言一个数组不是空数组
	 *
	 * @param array
	 * @param message
	 */
	public static void notEmpty (Object[] array, String message) {
		if (ArrayUtils.isEmpty(array)) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个集合不是空集合
	 *
	 * @param collection
	 */
	public static void notEmpty (Collection<?> collection) {
		notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言一个集合不是空集合
	 *
	 * @param collection
	 * @param message
	 */
	public static void notEmpty (Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new UtilsException(message);
		}
	}

	/**
	 * 断言一个数组不为null，并且不包含null的对象
	 *
	 * @param array
	 */
	public static void noNullElements (Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not be null and not contain any null elements");
	}

	/**
	 * 断言一个数组不为null，并且不包含null的对象
	 *
	 * @param array
	 * @param message
	 */
	public static void noNullElements (Object[] array, String message) {
		notNull(array, message);
		for (Object element : array) {
			if (element == null) {
				throw new UtilsException(message);
			}
		}
	}

	/**
	 * 断言一个集合不为null，并且不包含null的对象
	 *
	 * @param collection
	 */
	public static void noNullElements (Collection<?> collection) {
		noNullElements(collection, "[Assertion failed] - this collection must not be null and not contain any null elements");
	}

	/**
	 * 断言一个集合不为null，并且不包含null的对象
	 *
	 * @param collection
	 * @param message
	 */
	public static void noNullElements (Collection<?> collection, String message) {
		notNull(collection, message);
		for (Object element : collection) {
			if (element == null) {
				throw new UtilsException(message);
			}
		}
	}

	/**
	 * 断言，目标值小于被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 */
	public static void lessThan (int number, int compareNumber) {
		lessThan(number, compareNumber, "The parameter number's value:" + number + " must less than the comparenumber's value:" + compareNumber);
	}

	/**
	 * 断言，目标值<被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 * @param message       断言失败提示信息
	 */
	public static void lessThan (int number, int compareNumber, String message) {
		isTrue(number < compareNumber, message);
	}

	/**
	 * 断言，目标值<=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 */
	public static void lessEqual (int number, int compareNumber) {
		lessEqual(number, compareNumber, "The parameter number's value:" + number + " must less equal the comparenumber's value:" + compareNumber);
	}

	/**
	 * 断言，目标值<=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 * @param message       断言失败提示信息
	 */
	public static void lessEqual (int number, int compareNumber, String message) {
		isTrue(number <= compareNumber, message);
	}

	/**
	 * 断言，目标值=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 */
	public static void equal (int number, int compareNumber) {
		equal(number, compareNumber, "The parameter number's value:" + number + " must equal the comparenumber's value:" + compareNumber);
	}

	/**
	 * 断言，目标值=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 * @param message       断言失败提示信息
	 */
	public static void equal (int number, int compareNumber, String message) {
		isTrue(number == compareNumber, message);
	}

	/**
	 * 断言，目标值>=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 */
	public static void greatEqual (int number, int compareNumber) {
		greatEqual(number, compareNumber, "The parameter number's value:" + number + " must great equal the comparenumber's value:" + compareNumber);
	}

	/**
	 * 断言，目标值>=被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 * @param message       断言失败提示信息
	 */
	public static void greatEqual (int number, int compareNumber, String message) {
		isTrue(number >= compareNumber, message);
	}

	/**
	 * 断言，目标值>被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 */
	public static void greatThan (int number, int compareNumber) {
		greatThan(number, compareNumber, "The parameter number's value:" + number + " must great than the comparenumber's value:" + compareNumber);
	}

	/**
	 * 断言，目标值>被比较的值
	 *
	 * @param number        目标值
	 * @param compareNumber 被比较的值
	 * @param message       断言失败提示信息
	 */
	public static void greatThan (int number, int compareNumber, String message) {
		isTrue(number > compareNumber, message);
	}

	/**
	 * 断言文件存在
	 *
	 * @param filepath
	 */
	public static void fileExist (String filepath) {
		hasText(filepath);
		fileExist(new File(filepath));
	}

	/**
	 * 断言文件存在
	 *
	 * @param file
	 */
	public static void fileExist (File file) {
		if (!FileUtils.isExist(file)) {
			throw new UtilsException("File: " + file.getAbsolutePath() + " is not found!");
		}
	}
}
