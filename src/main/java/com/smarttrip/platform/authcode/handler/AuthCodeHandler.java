package com.smarttrip.platform.authcode.handler;

import java.util.Random;

import com.smarttrip.platform.authcode.domain.AuthCode;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;
import com.smarttrip.platform.authcode.generator.AuthCodeGenerator;

public abstract class AuthCodeHandler {
	
	/**
	 * 发送验证码
	 */
	public void send(String key){
		String code = generateAuthCode(6);
	}
	
	/**
	 * 校验验证码
	 * @param authCode
	 */
	public AuthCodeVerifyResult verify(String key, String code){
		AuthCodeVerifyResult result = new AuthCodeVerifyResult();
		
		
		return result;
	}
	
	protected String getAuthCodeByKey(String key){
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
