package com.mirana.frame.utils.result;

import com.mirana.frame.utils.result.Result.ResultBuilder;

/**
 * 结果集构造Builder接口
 *
 * @Title
 * @Description
 * @Created wangzf
 * @DateTime 2017/06/01 09:01:23
 */
public interface IResultBuilder {

	/**
	 * 添加msg
	 *
	 * @param msg
	 * @return ResultBuilder
	 */
	public ResultBuilder setMsg (String msg);

	/**
	 * 添加键值对参数
	 *
	 * @param result
	 * @return
	 */
	public ResultBuilder setResult (Object result);

	/**
	 * 创建result
	 *
	 * @return Result
	 */
	public Result build ();

}
