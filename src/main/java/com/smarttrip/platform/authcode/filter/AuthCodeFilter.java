package com.smarttrip.platform.authcode.filter;

import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;

public interface AuthCodeFilter {
	
	AuthCodeSendResult onSendBefore(String key, String code);

	AuthCodeSendResult onSendAfter(String key, String code);
	
	AuthCodeVerifyResult onVerifyBefore(String key, String userCode);
	
	AuthCodeVerifyResult onVerifyAfter(AuthCodeVerifyResult authCodeVerifyResult);
}
