package com.mirana.frame.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SysConstants {

	// ------------------------------------------------------------------
	// charset
	public static final Charset DEFAULT_CHARSET      = StandardCharsets.UTF_8;
	public static final String  DEFAULT_CHARSET_NAME = StandardCharsets.UTF_8.name();

	// ------------------------------------------------------------------
	// 分页参数
	/**
	 * 当前页码
	 */
	public static final String PAGE_PARAMS_PAGENO   = "page_params_pageno";
	/**
	 * 每页显示的条目
	 */
	public static final String PAGE_PARAMS_PAGESIZE = "page_params_pagesize";

	// ------------------------------------------------------------------
	// 数据库主键
	/**
	 * 数据库主键key名称，这里定义为id
	 */
	public static final String DB_PRIMARY_KEY = "id";

	// ------------------------------------------------------------------
	// 拦截器相关
	/**
	 * 当前请求的method
	 */
	public static final String INTERCEPTOR_KEY_IP            = "interceptor_key_ip";
	/**
	 * 当前请求的method
	 */
	public static final String INTERCEPTOR_KEY_REQUESTMETHOD = "interceptor_key_requestmethod";
	/**
	 * 当前请求的method
	 */
	public static final String INTERCEPTOR_KEY_ISAJAX        = "interceptor_key_isajax";
	/**
	 * 当前请求的url
	 */
	public static final String INTERCEPTOR_KEY_REQUESTURL    = "interceptor_key_requesturl";
	/**
	 * 当前请求url携带的参数
	 */
	public static final String INTERCEPTOR_KEY_REQUESTPARAMS = "interceptor_key_requestparams";
	/**
	 * 当前请求开始时间
	 */
	public static final String INTERCEPTOR_KEY_STARTTIME     = "interceptor_key_starttime";
	/**
	 * 当前请求结束时间
	 */
	public static final String INTERCEPTOR_KEY_ENDTIME       = "interceptor_key_endtime";

	// ------------------------------------------------------------------
	// session相关
	/**
	 * 默认的管理员名称
	 */
	public static final String DEFAULT_ADMIN             = "ADMIN";
	/**
	 * 默认的系统名称
	 */
	public static final String DEFAULT_SYSTEM            = "SYSTEM";
	/**
	 * session中的系统管理员
	 */
	public static final String SESSION_KEY_ADMINISTRATOR = "activeadmin";
	/**
	 * session中的user
	 */
	public static final String SESSION_KEY_ACTIVEUSER    = "activeuser";

	// ------------------------------------------------------------------
	// request 相关
	public static final String REQUEST_CTX          = "request_ctx";
	public static final String REQUEST_CTX_REALPATH = "request_ctx_realpath";
	public static final String REQUEST_BATHPATH     = "request_bathpath";

	public static final String REQUEST_SERVLET_PATH = "request_servlet_path";
	public static final String REQUEST_SERVER_NAME  = "request_server_name";
	public static final String REQUEST_SERVER_PORT  = "request_server_port";

	public static final String REQUEST_URI          = "request_uri";
	public static final String REQUEST_URL          = "request_url";
	public static final String REQUEST_Query_String = "request_query_string";

	// ------------------------------------------------------------------
	// request 参数
	public static final String REQUEST_PARAMS = "request_params";
}
