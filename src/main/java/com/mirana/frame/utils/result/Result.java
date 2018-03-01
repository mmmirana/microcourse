package com.mirana.frame.utils.result;

/**
 * @Title 结果集
 * @Description
 * @Created wangzf
 * @DateTime 2017/06/01 09:01:29
 */
public class Result {
	// 成功的结果类型
	public static final int CODE_SUCCESS = 1;
	// 失败的结果类型
	public static final int CODE_FAILED  = 2;
	// 警告
	public static final int CODE_WARN    = 3;
	// 错误
	public static final int CODE_ERROR   = 4;

	/**
	 * 返回的类型
	 **/
	private final int    code;
	/**
	 * 返回的提示信息
	 **/
	private final String msg;
	/**
	 * 返回的参数
	 **/
	private final Object result;

	private Result (ResultBuilder builder) {
		this.code = builder.code;
		this.msg = builder.msg;
		this.result = builder.result;
	}

	public int getCode () {
		return code;
	}

	public String getMsg () {
		return msg;
	}

	public Object getResult () {
		return result;
	}

	/**
	 * ResultBuilder
	 *
	 * @author wangzf
	 */
	public static class ResultBuilder implements IResultBuilder {
		private int    code;
		private String msg;
		private Object result;

		/**
		 * @param code
		 */
		public ResultBuilder (int code) {
			super();
			this.code = code;
		}

		public ResultBuilder setMsg (String msg) {
			this.msg = msg;
			return this;
		}

		public ResultBuilder setResult (Object result) {
			this.result = result;
			return this;
		}

		public Result build () {
			Result result = new Result(this);
			return result;
		}
	}
}
