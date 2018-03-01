package com.mirana.frame.utils;

import com.mirana.frame.constants.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * <p>
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * <p>
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	 * <p>
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress (HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 判断当前请求是否是Ajax请求
	 *
	 * @param request
	 * @return
	 */
	public static Boolean isAjax (HttpServletRequest request) {
		Boolean isAjax = false;// 默认false
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null) {
			isAjax = requestType.equalsIgnoreCase("XMLHttpRequest");
		}
		return isAjax;
	}

	/**
	 * 获取request请求的一些相关参数，放入Map中 <br>
	 * 相关key:<br>
	 * {@link SysConstants#REQUEST_CTX}<br>
	 * {@link SysConstants#REQUEST_CTX_REALPATH}<br>
	 * {@link SysConstants#REQUEST_SERVLET_PATH}<br>
	 * {@link SysConstants#REQUEST_SERVER_NAME}<br>
	 * {@link SysConstants#REQUEST_SERVER_PORT}<br>
	 * {@link SysConstants#REQUEST_URI}<br>
	 * {@link SysConstants#REQUEST_URL}<br>
	 * {@link SysConstants#REQUEST_Query_String}<br>
	 * {@link SysConstants#REQUEST_BATHPATH}<br>
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestMap (HttpServletRequest request) {

		String ctx = request.getContextPath();
		String ctxRealPath = request.getSession().getServletContext().getRealPath("");
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ctx + "/";

		String servletPath = request.getServletPath();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		String uri = request.getRequestURI();
		StringBuffer url = request.getRequestURL();
		String queryString = request.getQueryString();

		Map<String, String> requestMap = new HashMap<String, String>();

		requestMap.put(SysConstants.REQUEST_CTX, ctx);
		requestMap.put(SysConstants.REQUEST_CTX_REALPATH, ctxRealPath);
		requestMap.put(SysConstants.REQUEST_BATHPATH, basePath);

		requestMap.put(SysConstants.REQUEST_SERVLET_PATH, servletPath);
		requestMap.put(SysConstants.REQUEST_SERVER_NAME, serverName);
		requestMap.put(SysConstants.REQUEST_SERVER_PORT, serverPort + "");

		requestMap.put(SysConstants.REQUEST_URI, uri);
		requestMap.put(SysConstants.REQUEST_URL, url + "");
		requestMap.put(SysConstants.REQUEST_Query_String, queryString);

		return requestMap;
	}

	/**
	 * 获取项目根路径 <br>
	 * {@link SysConstants#REQUEST_CTX}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestCtx (HttpServletRequest request) {
		String ctx = request.getContextPath();
		return ctx;
	}

	/**
	 * 获取项目根目录的绝对路径 <br>
	 * {@link SysConstants#REQUEST_CTX_REALPATH}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestCtxRealPath (HttpServletRequest request) {
		String ctxRealPath = request.getSession().getServletContext().getRealPath("");
		return ctxRealPath;
	}

	/**
	 * 获取项目的绝对路径（与MyEclipse的默认BasePath保持一致） <br>
	 * {@link SysConstants#REQUEST_BATHPATH}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestBasePath (HttpServletRequest request) {
		String ctxpath = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ctxpath + "/";
		return basePath;
	}

	/**
	 * 获取Servlet访问的uri(servletPath) <br>
	 * {@link SysConstants#REQUEST_SERVLET_PATH}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestServletPath (HttpServletRequest request) {
		String servletPath = request.getServletPath();
		return servletPath;
	}

	/**
	 * 获取serverName <br>
	 * {@link SysConstants#REQUEST_SERVER_NAME}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestServerName (HttpServletRequest request) {
		return request.getServerName();
	}

	/**
	 * 获取端口号serverPort <br>
	 * {@link SysConstants#REQUEST_SERVER_PORT}
	 *
	 * @param request
	 * @return
	 */
	public static int getRequestServerPort (HttpServletRequest request) {
		return request.getServerPort();
	}

	/**
	 * 获取当前request的uri <br>
	 * {@link SysConstants#REQUEST_URI}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestUri (HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * 获取当前request的url <br>
	 * {@link SysConstants#REQUEST_URL}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestUrl (HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		return url.toString();
	}

	/**
	 * 获取当前request的请求参数 <br>
	 * {@link SysConstants#REQUEST_Query_String}
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestQueryString (HttpServletRequest request) {
		return request.getQueryString();
	}

	/**
	 * 获取项目根目录的绝对路径 <br>
	 * {@link SysConstants#REQUEST_CTX_REALPATH}
	 *
	 * @param request
	 * @return
	 */
	public static String getBasePath (HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 获取request属性值
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttr (HttpServletRequest request, String key) {
		return (T) request.getAttribute(key);
	}

	/**
	 * 设置request属性值
	 *
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setAttr (HttpServletRequest request, String key, Object value) {
		request.setAttribute(key, value);
	}

	/**
	 * 移除request属性值
	 *
	 * @param request
	 * @param key
	 */
	public static void rmAttr (HttpServletRequest request, String key) {
		request.removeAttribute(key);
	}

	/**
	 * 获取session属性值
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr (HttpServletRequest request, String key) {
		return (T) request.getSession().getAttribute(key);
	}

	/**
	 * 设置session属性值
	 *
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setSessionAttr (HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	/**
	 * 移除session属性值
	 *
	 * @param request
	 * @param key
	 */
	public static void rmSessionAttr (HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

}
