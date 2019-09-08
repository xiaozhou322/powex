package com.gt.result;

/**
 * Json数据返回状态吗
 * @author zhouyong
 *
 */
public enum ResultCode {
	/** 成功 */
	SUCCESS(1, "成功"),
	/** 操作失败 */
	FAIL(0, "操作失败"),
	/** 数据已存在 */
	SUCCESS_IS_HAVE(-101, "数据已存在"),
	/** 没有结果 */
	NOT_DATA(-102, "没有结果"),
	/** 没有登录 */
	NOT_LOGIN(-103, "没有登录"),
	/** 发生异常 */
	EXCEPTION(-104, "发生异常"),
	/** 系统错误 */
	SYS_ERROR(-105, "系统错误"),
	/** 参数错误 */
	PARAMS_ERROR(-106, "参数错误 "),
	/** 不支持或已经废弃 */
	NOT_SUPPORTED(-107, "不支持或已经废弃"),
	/** AuthCode错误 */
	INVALID_AUTHCODE(-108, "无效的AuthCode"),
	/** 太频繁的调用 */
	TOO_FREQUENT(-109, "太频繁的调用"),
	/** 未知的错误 */
	UNKNOWN_ERROR(-110, "未知错误"),
	/** 未设置方法 */
	NOT_METHOD(-111, "未设置方法");
	
	private ResultCode(int value, String msg) {
		this.val = value;
		this.msg = msg;
	}

	public int val() {
		return val;
	}

	public String msg() {
		return msg;
	}

	private int val;
	private String msg;
}
