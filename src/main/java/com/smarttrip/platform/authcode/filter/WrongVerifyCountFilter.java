package com.smarttrip.platform.authcode.filter;

import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;

/**
 * 限制验证码试错的次数
 * @author songjiesdnu@163.com
 *
 */
public class WrongVerifyCountFilter implements AuthCodeFilter {
	private int MaxWrongCount = 0;

	@Override
	public AuthCodeSendResult onSendBefore(String key, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthCodeSendResult onSendAfter(String key, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthCodeVerifyResult onVerifyBefore(String key, String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthCodeVerifyResult onVerifyAfter(
			AuthCodeVerifyResult authCodeVerifyResult) {
		// TODO Auto-generated method stub
		return null;
	}

}
