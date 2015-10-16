package com.smarttrip.platform.authcode.domain;

public class AuthCode {
	//根据key来存储和查找验证码
	private String key;
	//验证码
	private String code;
	//验证码发送时间
	long sendTime;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
}
