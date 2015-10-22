package com.smarttrip.platform.authcode.handler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.smarttrip.platform.authcode.codegenerator.CodeGenerator;
import com.smarttrip.platform.authcode.codegenerator.DefaultCodeGenerator;
import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;
import com.smarttrip.platform.authcode.msgsender.DefaultMsgSender;
import com.smarttrip.platform.authcode.msgsender.MsgSender;
import com.smarttrip.platform.authcode.repository.AuthCodeRepository;
import com.smarttrip.platform.authcode.repository.MapRepository;


public class AuthCodeHandlerTest {
	private AuthCodeHandler authCodeHandler;
	private AuthCodeRepository repository;
	private MsgSender msgSender;
	private CodeGenerator codeGenerator;
	
	@Before
	public void setUp(){
		authCodeHandler = new AuthCodeHandler();
		msgSender = new DefaultMsgSender();
		codeGenerator = new DefaultCodeGenerator();
		repository = new MapRepository();
		authCodeHandler.setAuthCodeRepository(repository);
		authCodeHandler.setMsgSender(msgSender);
		authCodeHandler.setCodeGenerator(codeGenerator);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSend_null() {
		String key = null;
		authCodeHandler.send(key);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSend_empty() {
		String key = "";
		authCodeHandler.send(key);
	}
	
	@Test
	public void testSend_success(){
		String key = "15210220001";
		AuthCodeSendResult sendResult = authCodeHandler.send(key);
		assertEquals(sendResult.getResult(), AuthCodeSendResult.SUCCESS);
	}
	
	@Test
	public void testVerify_right(){
		String key = "15210220002";
		String key2 = "15210220003";
		authCodeHandler.send(key);
		String userCode = "123456";
		AuthCodeVerifyResult result = authCodeHandler.verify(key2, userCode);
		assertEquals(result.getResult(), AuthCodeVerifyResult.RIGHT);
	}
	
}
