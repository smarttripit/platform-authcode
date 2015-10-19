package com.smarttrip.platform.authcode.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;

/**
 * 限制单位时间内发送次数的拦截器
 * @author songjie
 *
 */
public class SendCountFilter implements AuthCodeFilter {
	private Map<String, Integer> sendCountMap = new HashMap<String, Integer>();
	private Map<String, Long> startTimeMap = new HashMap<String, Long>();
	private long period;//周期长度
	private int maxSendCount;//周期内最多发送次数
	

	@Override
	public AuthCodeSendResult onSendBefore(String key, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthCodeSendResult onSendAfter(String key, String code) {
		AuthCodeSendResult rtn = new AuthCodeSendResult();
		rtn.setResult(AuthCodeSendResult.SUCCESS);
		if(key == null  ||  key.equals("")){
			return null;
		}
		long startTime = startTimeMap.get(key);
		long nowTime = new Date().getTime();
		if(nowTime - startTime > period){
			startTimeMap.put(key, nowTime);
			sendCountMap.put(key, 1);
		}else{
			int sendCount = sendCountMap.get(key);
			if(sendCount < maxSendCount){
				int newSendCount = sendCount + 1;
				sendCountMap.put(key, newSendCount);
			}else{
				rtn.setResult(AuthCodeSendResult.FAIL);
				rtn.setMsg("发送次数达到上限");
			}
		}
		return rtn;
	}

	@Override
	public AuthCodeVerifyResult onVerifyBefore(String key, String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthCodeVerifyResult onVerifyAfter(AuthCodeVerifyResult authCodeVerifyResult) {
		// TODO Auto-generated method stub
		return null;
	}

}
