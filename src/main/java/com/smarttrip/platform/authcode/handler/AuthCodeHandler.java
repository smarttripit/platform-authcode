package com.smarttrip.platform.authcode.handler;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttrip.platform.authcode.codegenerator.CodeGenerator;
import com.smarttrip.platform.authcode.domain.AuthCode;
import com.smarttrip.platform.authcode.domain.AuthCodeSendResult;
import com.smarttrip.platform.authcode.domain.AuthCodeVerifyResult;
import com.smarttrip.platform.authcode.msgsender.MsgSender;
import com.smarttrip.platform.authcode.repository.AuthCodeRepository;
import com.smarttrip.platform.authcode.util.UUIDUtils;


public class AuthCodeHandler {
	private static Logger logger = LoggerFactory.getLogger(AuthCodeHandler.class);
	//验证码有效期，单位：秒。默认是5分钟。负数表示不限制（不推荐）
	private int validPeriod = 5*60;
	//限制发送次数的时间单位，单位：秒。默认值是一天。
	private int sendCountPeriod = 24*60*60; 
	//sendCountPeriod时间内，最大发送次数。默认是100。
	private int maxSendCount = 100;
	//最大验证失败次数。默认是5.
	private int maxWrongVerifyCount = 5;
	//存储验证码的仓库
	private AuthCodeRepository repository;
	//将验证码作为一条消息发送出去
	private MsgSender msgSender;
	//验证码生成器
	private CodeGenerator codeGenerator;
	//扫描线程的时间间隔，单位：秒。默认5分钟。
	private long threadScanPeriod = 5*60;
	
	private CleanUpThread cleanUpThread = new CleanUpThread("验证码repository清理线程");
	
	public AuthCodeHandler(){
		this(true);//默认启动
	}
	
	public AuthCodeHandler(boolean startCleanUpThread){
		if(startCleanUpThread){
			startCleanUpThread();
		}
	}
	
	//启动清理线程
	private void startCleanUpThread(){
		if(!cleanUpThread.isStarted){
			synchronized(cleanUpThread){
				if(!cleanUpThread.isStarted){
					cleanUpThread.isStarted = true;
					cleanUpThread.start();
				}
			}
		}
	}
	
	public void setAuthCodeRepository(AuthCodeRepository repository){
		this.repository = repository;
	}
	
	public void setMsgSender(MsgSender msgSender){
		this.msgSender = msgSender;
	}
	
	public void setCodeGenerator(CodeGenerator codeGenerator){
		this.codeGenerator = codeGenerator;
	}
	
	public void setValidPeriod(int validPeriod) {
		this.validPeriod = validPeriod;
	}

	public void setSendCountPeriod(int sendCountPeriod) {
		this.sendCountPeriod = sendCountPeriod;
	}

	public void setMaxSendCount(int maxSendCount) {
		this.maxSendCount = maxSendCount;
	}

	public void setMaxWrongVerifyCount(int maxWrongVerifyCount) {
		this.maxWrongVerifyCount = maxWrongVerifyCount;
	}
	
	/**
	 * 发送验证码
	 * @param key
	 * @return
	 */
	public AuthCodeSendResult send(String key){
		logger.debug("send 开始");
		logger.debug("key:" + key);
		if(key == null  ||  key.equals("")){
			throw new IllegalArgumentException("key不能为空");
		}
		AuthCode authCode = repository.get(key);
		AuthCodeSendResult authCodeSendResult = beforeSend(authCode);//发送验证码之前检查
		if(authCodeSendResult.getResult() != null  &&  authCodeSendResult.getResult().equals(AuthCodeSendResult.FAIL)){
			return authCodeSendResult;
		}
		String code = codeGenerator.generateAuthCode();//生成验证码
		msgSender.sendMsg(key, code);//发送带有验证码的消息
		String sendId = UUIDUtils.getUUID();
		authCodeSendResult = new AuthCodeSendResult();
		authCodeSendResult.setResult(AuthCodeSendResult.SUCCESS);
		authCodeSendResult.setSendId(sendId);
		if(authCode == null){
			authCode = new AuthCode();
		}
		authCode.setCode(code);
		authCode.setSendTime(new Date().getTime());
		authCode.setKey(key);
		authCode.setSendCount(authCode.getSendCount() + 1);
		repository.set(authCode);
		logger.debug("send 结束");
		return authCodeSendResult;
	}
	
	/**
	 * 验证码发送之前做检查
	 * @param authCode
	 * @return
	 */
	protected AuthCodeSendResult beforeSend(AuthCode authCode){
		logger.debug("beforeSend方法 开始");
		AuthCodeSendResult rtn = new AuthCodeSendResult();
		if(authCode == null){
			return rtn;
		}
		long nowTime = new Date().getTime();
		long firstTime = authCode.getFirstTime();
		long sendCount = authCode.getSendCount();
		logger.debug("nowTime:" + nowTime + ";firstTime:" + firstTime + ";sendCount:" + sendCount);
		if(nowTime - firstTime >= sendCountPeriod*1000){
			//重置AuthCode属性
			authCode.setFirstTime(nowTime);
			authCode.setSendCount(1);
			return rtn;
		}
		if(sendCount >= maxSendCount){
			rtn.setResult(AuthCodeSendResult.FAIL);
			rtn.setTipCode("threshold");
			rtn.setTipMsg("单位时间内发送次数达到上限");
			logger.warn("单位时间内发送次数达到上限");
		}
		logger.debug("beforeSend方法 结束");
		return rtn;
	}
	
	/**
	 * 校验验证码
	 * @param key
	 * @param userCode：用户输入的验证码
	 * @return
	 */
	public AuthCodeVerifyResult verify(String key, String userCode){
		logger.debug("verify方法 开始");
		if(key == null || key.equals("") || userCode == null || userCode.equals("")){
			throw new IllegalArgumentException("key或者code不能为空");
		}
		AuthCode authCode = repository.get(key);
		AuthCodeVerifyResult rtn = beforeVerify(authCode);
		if(rtn.getResult() != null  &&  !rtn.getResult().equals(AuthCodeVerifyResult.RIGHT)){
			return rtn;
		}
		if(authCode == null){
			rtn.setResult(AuthCodeVerifyResult.WRONG);
			rtn.setMsg("验证码不正确");
			logger.debug("验证码不正确");
			return rtn;
		}
		long nowTime = new Date().getTime();
		long sendTime = authCode.getSendTime();
		if(nowTime - sendTime  >  validPeriod*1000){
			rtn.setResult(AuthCodeVerifyResult.EXPIRED);
			rtn.setMsg("验证码已过期");
			logger.debug("验证码已过期");
			return rtn;
		}
		if(userCode.equals(authCode.getCode())){
			rtn.setResult(AuthCodeVerifyResult.RIGHT);
			logger.debug("验证码正确");
			rtn.setMsg("验证码正确");
		}else{
			rtn.setResult(AuthCodeVerifyResult.WRONG);
			logger.debug("验证码不正确");
			rtn.setMsg("验证码不正确");
		}
		if(rtn.getResult().equals(AuthCodeVerifyResult.RIGHT)){
			repository.remove(authCode.getKey());
		}
		logger.debug("verify方法 结束");
		return rtn;
	}
	
	/**
	 * 验证码校验之前做检查
	 * @param authCode
	 * @return
	 */
	protected AuthCodeVerifyResult beforeVerify(AuthCode authCode){
		logger.debug("beforeVerify方法 开始");
		AuthCodeVerifyResult rtn = new AuthCodeVerifyResult();
		if(authCode == null){
			return rtn;
		}
		long wrongVerifyCount = authCode.getWrongVerifyCount();
		if(wrongVerifyCount >= maxWrongVerifyCount){
			rtn.setResult(AuthCodeVerifyResult.WRONG);
			rtn.setMsg("验证码验证次数超过上限");
			logger.debug("验证码验证次数超过上限");
			repository.remove(authCode.getKey());
		}
		logger.debug("beforeVerify方法 结束");
		return rtn;
	}
	
	private class CleanUpThread extends Thread{
		private boolean isStarted = false;
		
		public CleanUpThread(String threadName){
			super(threadName);
		}
		
		public void run(){
			while(true){
				logger.debug("开始一次清理");
				if(repository != null){
					Map<String, AuthCode> all = repository.getAll();
					if(all != null  &&  all.size() > 0){
						Iterator<String> it = all.keySet().iterator();
						while(it.hasNext()){
							String key = it.next();
							AuthCode authCode = all.get(key);
							long nowTime = new Date().getTime();
							long sendTime = authCode.getSendTime();
							if(nowTime - sendTime >= validPeriod*1000){
								logger.debug("清理掉一个验证码。key:" + key);
								repository.remove(key);
							}
						}
					}
				}
				logger.debug("结束一次清理");
				try {
					Thread.sleep(threadScanPeriod*1000);
				} catch (InterruptedException e) {
					logger.warn("清理线程被中断");
				}
			}
		}
	}
}
