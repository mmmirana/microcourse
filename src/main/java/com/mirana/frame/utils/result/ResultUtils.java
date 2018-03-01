package com.mirana.frame.utils.result;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.json.JsonUtils;
import com.mirana.frame.utils.result.Result.ResultBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title ajax result结果集
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/16 21:42:05
 */
public class ResultUtils {

	/**
	 * 构造自定义ResultBuildr
	 **/
	private static ResultBuilder getResultBuilder (int code) {
		return new ResultBuilder(code);
	}

	// 构造自定义的结果
	public static Result build (int code) {
		return getResultBuilder(code).build();
	}

	// 构造自定义的结果，携带code
	public static Result build (int code, String msg) {
		return getResultBuilder(code).setMsg(msg).build();
	}

	// 构造自定义的结果，携带code和result
	public static Result build (int code, String msg, Object result) {
		return getResultBuilder(code).setMsg(msg).setResult(result).build();
	}

	/**
	 * 构建操作成功的Result
	 *
	 * @return
	 */
	public static Result buildSuccess () {
		return buildSuccess(null, null);
	}

	/**
	 * 构建操作成功的Result
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static Result buildSuccess (String msg) {
		return buildSuccess(msg, null);
	}

	/**
	 * 构建操作成功的Result
	 *
	 * @param result 结果集
	 * @return
	 */
	public static Result buildSuccess (Object result) {
		return buildSuccess(null, result);
	}

	/**
	 * 构建操作成功的Result
	 *
	 * @param msg    提示信息
	 * @param result 结果集
	 * @return
	 */
	public static Result buildSuccess (String msg, Object result) {
		if (StringUtils.isBlank(msg)) {
			msg = "操作成功";
		}
		return getResultBuilder(Result.CODE_SUCCESS).setMsg(msg).setResult(result).build();
	}

	/**
	 * 构建操作失败的Result
	 *
	 * @return
	 */
	public static Result buildFailed () {
		return buildFailed(null, null);
	}

	/**
	 * 构建操作失败的Result
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static Result buildFailed (String msg) {
		return buildFailed(msg, null);
	}

	/**
	 * 构建操作失败的Result
	 *
	 * @param result 提示信息
	 * @return
	 */
	public static Result buildFailed (Object result) {
		return buildFailed(null, result);
	}

	/**
	 * 构建操作失败的Result
	 *
	 * @param msg    提示信息
	 * @param result 结果集
	 * @return
	 */
	public static Result buildFailed (String msg, Object result) {
		if (StringUtils.isBlank(msg)) {
			msg = "抱歉，操作失败";
		}
		return getResultBuilder(Result.CODE_FAILED).setMsg(msg).setResult(result).build();
	}

	/**
	 * 构建系统异常的Result
	 *
	 * @return
	 */
	public static Result buildError () {
		return buildError("抱歉，系统异常", null);
	}

	/**
	 * 构建系统异常的Result
	 *
	 * @param msg 提示信息
	 * @return
	 */
	public static Result buildError (String msg) {
		return buildError(msg, null);
	}

	/**
	 * 构建系统异常的Result
	 *
	 * @param result 结果集
	 * @return
	 */
	public static Result buildError (Object result) {
		return buildError("抱歉，系统异常", result);
	}

	/**
	 * 构建系统异常的Result
	 *
	 * @param msg    提示信息
	 * @param result 结果集
	 * @return
	 */
	public static Result buildError (String msg, Object result) {
		if (StringUtils.isBlank(msg)) {
			msg = "抱歉，系统异常";
		}
		return getResultBuilder(Result.CODE_ERROR).setMsg(msg).setResult(result).build();
	}

	// 构造自定义的结果
	public static String buildJson (int code, String msg) {
		return resultToJson(build(code, msg));
	}

	// 构造自定义的结果，携带结果集
	public static String buildJson (int code, String msg, Object result) {
		return resultToJson(build(code, msg, result));
	}

	// 将Result结果转化为json字符串
	private static String resultToJson (Result result) {
		try {
			return JsonUtils.stringify(result);
		} catch (Exception e) {
			throw new UtilsException("AjaxResult转换json发生异常", e);
		}
	}
}
