package com.sezioo.wechat_demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sezioo.wechat_demo.tm.entity.RpTransactionMessage;
import com.sezioo.wechat_demo.util.JsonMapper;

@Component("kafkaSender")
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(RpTransactionMessage message) {
		String data = JsonMapper.obj2String(message);
		kafkaTemplate.send(message.getConsumerQueue(), data);
	}
}
