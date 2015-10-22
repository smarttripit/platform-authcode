package com.smarttrip.platform.authcode.domain;

public class AuthCodeVerifyResult {
	public static String RIGHT = "right";
	public static String WRONG = "wrong";
	public static String EXPIRED = "expired";
	//校验结果
	private String result;
	//提示信息
	private String msg;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
