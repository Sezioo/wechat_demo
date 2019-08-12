package com.sezioo.wechar_demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sezioo.wechat_demo.DemoApplication;
import com.sezioo.wechat_demo.tm.entity.RpTransactionMessage;
import com.sezioo.wechat_demo.tm.service.RpTransactionMessageService;
import com.sezioo.wechat_demo.util.UUIDGenerateUtils;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=DemoApplication.class)
@WebAppConfiguration
@Slf4j
public class TMTest {
	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;
	
	@Test
	public void saveMessageTest() {
		RpTransactionMessage message = new RpTransactionMessage();
		message.setConsumerQueue("TEST-TOPIC");
		
		for(int i=1;i<200;i++) {
			String messageBody = "test - " + i;
			message.setMessageBody(messageBody);
			String uuid = UUIDGenerateUtils.generateUUID();
			message.setId(uuid);
			message.setMessageId(uuid);
			int confirm = rpTransactionMessageService.saveMessageWaitingConfirm(message);
			log.info("save message success,totals = {}",confirm);
		}
	}
	
	@Test
	public void sendMessageTest() {
		rpTransactionMessageService.reSendAllDeadMessageByQueueName("TEST-TOPIC", 100);
	}

}
