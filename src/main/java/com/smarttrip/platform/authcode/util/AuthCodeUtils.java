package com.smarttrip.platform.authcode.util;

import java.util.Random;

public class AuthCodeUtils {
	public static String generateAuthCode(int length){
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
