package com.smarttrip.platform.authcode.handler;

import java.util.Date;
import java.util.Random;

import com.smarttrip.platform.authcode.domain.AuthCode;
import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;

public abstract class AuthCodeHandler {
	//验证码有效期，单位：秒。默认是5分钟。
	private int validPeriod = 5*60;
	
	/**
	 * 发送验证码
	 */
	public AuthCodeSendResult send(String key){
		if(key == null  ||  key.equals("")){
			throw new IllegalArgumentException("key不能为空");
		}
		AuthCodeSendResult rtn = new AuthCodeSendResult();
		String code = generateAuthCode(6);
		
		return rtn;
	}
	
	/**
	 * 校验验证码
	 * @param key
	 * @param userCode：用户输入的验证码
	 * @return
	 */
	public AuthCodeVerifyResult verify(String key, String userCode){
		if(key == null || key.equals("") || userCode == null || userCode.equals("")){
			throw new IllegalArgumentException("key或者code不能为空");
		}
		AuthCodeVerifyResult rtn = new AuthCodeVerifyResult();
		AuthCode authCode = getAuthCodeByKey(key);
		if(authCode == null){
			rtn.setResult("wrong");
			rtn.setMsg("验证码不正确");
		}
		long nowTime = new Date().getTime();
		long sendTime = authCode.getSendTime();
		if(sendTime - nowTime  >  validPeriod){
			rtn.setResult("expired");
			rtn.setMsg("验证码已过期");
			return rtn;
		}
		if(userCode.equals(authCode.getCode())){
			rtn.setResult("right");
			rtn.setMsg("验证码正确");
		}else{
			rtn.setResult("wrong");
			rtn.setMsg("验证码不正确");
		}
		return rtn;
	}
	
	protected AuthCode getAuthCodeByKey(String key){
		return null;
	}
	
	/**
	 * 生成验证码字符串
	 * @param length
	 * @return
	 */
	protected String generateAuthCode(int length){
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<length; i++){
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
