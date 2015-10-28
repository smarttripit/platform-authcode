package com.smarttrip.platform.authcode.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.smarttrip.platform.authcode.domain.AuthCode;

public class MapRepository implements AuthCodeRepository {
	Map<String, AuthCode> map = new ConcurrentHashMap<String, AuthCode>();
	
	@Override
	public AuthCode get(String key) {
		return map.get(key);
	}

	@Override
	public void set(AuthCode authCode) {
		String key = authCode.getKey();
		map.put(key, authCode);
	}

	@Override
	public void remove(String key){
		map.remove(key);
	}
//	public void remove(AuthCode authCode) {
//		map.remove(authCode.getKey());
//	}

	@Override
	public Map<String, AuthCode> getAll() {
		return map;
	}
}
