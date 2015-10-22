package com.smarttrip.platform.authcode.msgsender;

public interface MsgSender {
	/**
	 * 根据验证码的key发送消息
	 * @param key：验证码的key
	 */
	public void sendMsg(String key, String code);
}
