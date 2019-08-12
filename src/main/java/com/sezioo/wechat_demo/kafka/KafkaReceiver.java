package com.sezioo.wechat_demo.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sezioo.wechat_demo.tm.entity.RpTransactionMessage;
import com.sezioo.wechat_demo.util.JsonMapper;

@Component
public class KafkaReceiver {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaReceiver.class);

//	@KafkaListener(topics={"TEST-TOPIC"})
	public void listen(ConsumerRecord<?, ?> record) {
		String data = (String)record.value();
		if(StringUtils.isNotEmpty(data)) {
			log.info("receive topic : {}",record.topic());
			log.info("receive data : {}",data);
			RpTransactionMessage rpTransactionMessage = JsonMapper.parse(data, RpTransactionMessage.class);
			log.info(rpTransactionMessage.toString());
		}
	}
}
