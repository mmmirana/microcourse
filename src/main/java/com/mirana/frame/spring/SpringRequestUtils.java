package com.mirana.frame.spring;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SpringRequestUtils {
	/**
	 * 获取当前的request
	 *
	 * @return
	 */
	public static HttpServletRequest getCurrentRequest () {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取当前session
	 *
	 * @return
	 */
	public static HttpSession getCurrentSession () {
		HttpServletRequest request = getCurrentRequest();
		if (request == null) {
			return null;
		} else {
			return request.getSession();
		}
	}
}
