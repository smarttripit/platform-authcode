package com.smarttrip.platform.authcode.domain;

public class AuthCodeSendResult {
	//每发送一个验证码都会产生一条消息
	private String msgId;
	//发送结果
	private String result;
	//提示信息
	private String msg;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
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
