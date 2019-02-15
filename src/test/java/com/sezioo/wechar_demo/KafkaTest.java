package com.sezioo.wechar_demo;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sezioo.wechat_demo.DemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=DemoApplication.class)
@WebAppConfiguration
public class KafkaTest {
	
	private Producer<String, String> producer ;
	
	private Consumer<String, String> consumer;
	
	public static final String TOPIC = "TEST-TOPIC";
	
	@Before
	public void init() {
		//initProducer();
		initConsumer();
	}
	
	public void initProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "47.106.124.225:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		producer = new KafkaProducer<String,String>(props);
	}
	
	public void initConsumer() {
		Properties props = new Properties();
        props.put("bootstrap.servers", "47.106.124.225:9092");
        //每个消费者分配独立的组号
        props.put("group.id", "test2");
        //如果value合法，则自动提交偏移量
        props.put("enable.auto.commit", "true");
        //设置多久一次更新被消费消息的偏移量
        props.put("auto.commit.interval.ms", "1000");
        //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        props.put("session.timeout.ms", "30000");
        //自动重置offset
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
	}
	
	@Test
	public void producerTest() {
		int messageNO = 100;
		int messageCount = 1000;
		while(messageNO<messageCount) {
			String key = String.valueOf(messageNO);
			String message = "hello kafka message " + key;
			producer.send(new ProducerRecord<String, String>(TOPIC, key, message));
			System.out.println(message);
			messageNO++;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void consumerTest() {
		consumer.subscribe(Arrays.asList(TOPIC));
		while (true) {
			ConsumerRecords<String,String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset=%d,key= %s ,value= %s ",record.offset(),record.key(),record.value());
				System.out.println();
			}
		}
	}

}
