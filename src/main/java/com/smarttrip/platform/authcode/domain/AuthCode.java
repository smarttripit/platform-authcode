package com.smarttrip.platform.authcode.domain;

public class AuthCode {
	//根据key来存储和查找验证码
	private String key;
	//验证码
	private String code;
	//验证码发送时间
	private long sendTime;
	//单位时间内首个验证码发送的时间
	private long firstTime;
	//单位时间内的累计发送数
	private long sendCount = 0;
	//本次验证码验证失败的次数
	private long wrongVerifyCount = 0;
	
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
	public long getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(long firstTime) {
		this.firstTime = firstTime;
	}
	public long getSendCount() {
		return sendCount;
	}
	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}
	public long getWrongVerifyCount() {
		return wrongVerifyCount;
	}
	public void setWrongVerifyCount(long wrongVerifyCount) {
		this.wrongVerifyCount = wrongVerifyCount;
	}
}
