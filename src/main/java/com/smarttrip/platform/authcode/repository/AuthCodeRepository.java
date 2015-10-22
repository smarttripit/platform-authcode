package com.smarttrip.platform.authcode.repository;

import java.util.Map;

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
//	public void remove(AuthCode authCode);
	public void remove(String key);
	
	/**
	 * 获取所有的验证码，为扫描线程使用
	 * @return
	 */
	public Map<String, AuthCode> getAll();
}
