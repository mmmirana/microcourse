package com.mirana.frame.utils;

import org.apache.commons.lang3.StringUtils;

public class ObjectUtils {
	public static boolean isBlank (Object obj) {
		if (obj == null || StringUtils.isBlank(obj.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank (Object obj) {
		return !isBlank(obj);
	}
}
