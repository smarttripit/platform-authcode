package com.smarttrip.platform.authcode.domain;

public class AuthCodeSendResult {
	public static String FAIL = "failed";
	public static String SUCCESS = "success";
	
	//每发送一个验证码都产生一个唯一的id
	private String sendId;
	//发送结果（success or failed）
	private String result;
	//提示信息的代码
	private String tipCode;
	//提示信息
	private String tipMsg;
	
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTipCode() {
		return tipCode;
	}
	public void setTipCode(String tipCode) {
		this.tipCode = tipCode;
	}
	public String getTipMsg() {
		return tipMsg;
	}
	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
}
