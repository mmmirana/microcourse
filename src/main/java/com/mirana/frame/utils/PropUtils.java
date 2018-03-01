package com.mirana.frame.utils;

import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Title 属性文件获取属性值的工具类
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/23 16:47:55
 */
public class PropUtils {

	private static Map<String, Map<String, String>> propMap = new LinkedHashMap<String, Map<String, String>>();

	/**
	 * 加载配置文件
	 *
	 * @param baseName 文件路径
	 * @param alias    当前属性文件别名
	 * @param trim     value是否trim
	 */
	public static Map<String, String> use (String baseName, String alias, boolean trim) {
		Assert.hasLength(baseName);

		// 定义map
		Map<String, String> cntPropMap = new LinkedHashMap<String, String>();

		// 读取属性文件
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		Enumeration<String> keysEnum = bundle.getKeys();
		while (keysEnum.hasMoreElements()) {
			String key = (String) keysEnum.nextElement();
			String value = bundle.getString(key);
			if (!StringUtils.isEmpty(value)) {
				cntPropMap.put(key, trim ? value.trim() : value);
			}
		}

		// 将别名和属性map放入总的属性文件中
		propMap.put(alias, cntPropMap);

		return cntPropMap;

	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static String get (String alias, String key) {
		return getString(alias, key);
	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static String getString (String alias, String key) {
		Map<String, String> aliasPropMap = propMap.get(alias);
		if (aliasPropMap == null) {
			LogUtils.warn("Current property files doesn't contails alias " + alias
				+ ", you could load your property file like this: PropUtils.use(filepath, alias, trim)...");
			return null;
		} else {
			return aliasPropMap.get(key);
		}

	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static Integer getInt (String alias, String key) {
		String value = getString(alias, key);
		if (value == null) {
			return null;
		} else {
			return NumberUtils.createInteger(value);
		}
	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static Long getLong (String alias, String key) {
		String value = getString(alias, key);
		if (value == null) {
			return null;
		} else {
			return NumberUtils.createLong(value);
		}
	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static Float getFloat (String alias, String key) {
		String value = getString(alias, key);
		if (value == null) {
			return null;
		} else {
			return NumberUtils.createFloat(value);
		}
	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static Double getDouble (String alias, String key) {
		String value = getString(alias, key);
		if (value == null) {
			return null;
		} else {
			return NumberUtils.createDouble(value);
		}
	}

	/**
	 * 获取资源文件的属性值
	 *
	 * @param alias 加载属性文件时使用的别名
	 * @param key   属性key
	 * @return
	 */
	public static Boolean getBoolean (String alias, String key) {
		String value = getString(alias, key);
		if (value == null) {
			return null;
		} else {
			return BooleanUtils.toBoolean(value);
		}
	}

}
