package com.mirana.frame.interceptor;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.spring.SpringCtxUtils;
import com.mirana.frame.utils.RedisUtils;
import com.mirana.frame.utils.RequestUtils;
import com.mirana.frame.utils.SysPropUtils;
import com.mirana.frame.utils.date.DatePattern;
import com.mirana.frame.utils.date.DateUtils;
import com.mirana.frame.utils.json.JsonUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * spring mvc 全局的拦截器
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月5日下午5:31:38
 */
public class AllInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println(request.getRequestURL());

		Date startTime = new Date();// 记录请求的开始时间

		String ip = RequestUtils.getIpAddress(request);// 访问来源ip
		// IpBean ipbean = IpUtils.query(ip);// 访问来源ip的实体
		String requestMethod = request.getMethod();// get post
		Boolean isAjax = RequestUtils.isAjax(request);// 是否是ajax
		String requestURL = request.getRequestURL().toString();// 记录请求的url
		Map<String, Object> paramMap = new HashMap<String, Object>();// 请求的参数
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String param = (String) paramNames.nextElement();
			String value = request.getParameter(param);
			paramMap.put(param, value);
		}

		request.setAttribute(SysConstants.INTERCEPTOR_KEY_STARTTIME, startTime);
		request.setAttribute(SysConstants.INTERCEPTOR_KEY_REQUESTURL, requestURL);
		request.setAttribute(SysConstants.REQUEST_PARAMS, paramMap);

		StringBuffer logstr = new StringBuffer();
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- All Interceptor Log Request --------");
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- From: " + ip);
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- RequestURL: " + requestURL);
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- RequestMethod: " + requestMethod + ", isAjax: " + isAjax);
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- Params: " + JsonUtils.stringify(paramMap));

		LogUtils.debug(logstr.toString());

		RedisUtils redisUtils = (RedisUtils) SpringCtxUtils.getBean(RedisUtils.class);

		List<Object> ipaddrs = redisUtils.lGet("interceptorIpaddress", 0, -1);
		if (!ipaddrs.contains(ip)) {
			redisUtils.lSet("interceptorIpaddress", ip);
		}
		ipaddrs = redisUtils.lGet("interceptorIpaddress", 0, -1);
		System.out.println("ipaddrs: " + JsonUtils.stringify(ipaddrs));

		return true;
	}

	@Override
	public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Date endTime = new Date();
		Date startTime = (Date) request.getAttribute(SysConstants.INTERCEPTOR_KEY_STARTTIME);
		String requestURL = (String) request.getAttribute(SysConstants.INTERCEPTOR_KEY_REQUESTURL);

		StringBuffer logstr = new StringBuffer();
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- All Interceptor Log Request --------");
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- RequestURL: " + requestURL);
		logstr.append(SysPropUtils.LINE_SEPARATOR + "-------- TimeConsuming: " + (endTime.getTime() - startTime.getTime()) + " ms , StartTime: "
			+ DateUtils.format(startTime, DatePattern.DATETIME_SLASH) + " , EndTime: " + DateUtils.format(endTime, DatePattern.DATETIME_SLASH));

		LogUtils.debug(logstr.toString());
	}

}
