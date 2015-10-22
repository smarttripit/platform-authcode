package com.smarttrip.platform.authcode.codegenerator;

import java.util.Random;

public class DefaultCodeGenerator implements CodeGenerator {
	private int codeLength = 6;

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	@Override
	public String generateAuthCode() {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<codeLength; i++){
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
