package com.smarttrip.platform.authcode.codegenerator;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCodeGenerator implements CodeGenerator {
	private static Logger logger = LoggerFactory.getLogger(DefaultCodeGenerator.class);
	private int codeLength = 6;

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	@Override
	public String generateAuthCode() {
		logger.debug("generateAuthCode方法 开始");
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<codeLength; i++){
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		logger.debug("generateAuthCode方法 结束");
		return sb.toString();
	}

}
