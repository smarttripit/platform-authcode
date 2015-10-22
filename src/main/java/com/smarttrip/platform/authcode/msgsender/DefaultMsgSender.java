package com.smarttrip.platform.authcode.msgsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMsgSender implements MsgSender {
	private static Logger logger = LoggerFactory.getLogger(DefaultMsgSender.class);

	@Override
	public void sendMsg(String key, String code) {
		logger.debug("key:" + key + ";code:" + code);
		
	}

}
