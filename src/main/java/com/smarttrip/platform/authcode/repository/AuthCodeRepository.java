package com.smarttrip.platform.authcode.repository;

import com.smarttrip.platform.authcode.domain.AuthCode;

public interface AuthCodeRepository {
	/**
	 * 获取验证码
	 * @param key
	 * @return
	 */
	public AuthCode get(String key);
	
	/**
	 * 存储验证码
	 * @param authCode
	 */
	public void set(AuthCode authCode);
	
	/**
	 * 删除一个验证码
	 * @param authCode
	 */
	public void remove(AuthCode authCode);
}
