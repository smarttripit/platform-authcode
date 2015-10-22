package com.smarttrip.platform.authcode.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author songjiesdnu@163.com
 */
public class UUIDUtils {
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
}