package com.sezioo.wechat_demo.tm.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sezioo.wechat_demo.exceptions.MessageBizException;
import com.sezioo.wechat_demo.kafka.KafkaSender;
import com.sezioo.wechat_demo.property.KafkaProperty;
import com.sezioo.wechat_demo.tm.dao.RpTransactionMessageMapper;
import com.sezioo.wechat_demo.tm.entity.RpTransactionMessage;
import com.sezioo.wechat_demo.tm.enums.MessageStatusEnum;
import com.sezioo.wechat_demo.tm.enums.PublicEnum;
import com.sezioo.wechat_demo.util.JsonMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class RpTransactionMessageServiceImpl implements RpTransactionMessageService {
	
	@Autowired
	private RpTransactionMessageMapper messageMapper;
	
	@Autowired
	private KafkaSender kafkaSender;
	
	@Autowired
	private KafkaProperty kafkaProperty;
	
	private static final Logger log = LoggerFactory.getLogger(RpTransactionMessageServiceImpl.class);
	
	@Override
	public int saveMessageWaitingConfirm(RpTransactionMessage rpTransactionMessage) throws MessageBizException {
		if (rpTransactionMessage == null) {
			log.info("保存的消息为空");
			throw new MessageBizException("保存的消息为空");
		}
		if(StringUtils.isEmpty(rpTransactionMessage.getConsumerQueue())) {
			log.info("所保存消息的消费队列为空");
			throw new MessageBizException("所保存消息的消费队列为空");
		}
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setStatus(MessageStatusEnum.WAITING_CONFIRM.name());
		rpTransactionMessage.setMessageSendTimes(0);
		int insertSelective = messageMapper.insertSelective(rpTransactionMessage);
		log.info("消息保存成功");
		return insertSelective;
	}

	@Override
	public void confirmAndSendMessage(String messageId) throws MessageBizException {
		if (StringUtils.isBlank(messageId)) {
			log.info("发送消息的消息ID为空");
			throw new MessageBizException("发送消息的消息ID为空");
		}
		RpTransactionMessage rpTransactionMessage = messageMapper.selectByPrimaryKey(messageId);
		if (rpTransactionMessage == null) {
			log.info("发送的消息ID不存在:{}",messageId);
			throw new MessageBizException("发送的消息ID不存在:"+messageId);
		}
		rpTransactionMessage.setStatus(MessageStatusEnum.SENDING.name());
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setMessageSendTimes(rpTransactionMessage.getMessageSendTimes()+1);
		messageMapper.updateByPrimaryKeySelective(rpTransactionMessage);
		kafkaSender.send(rpTransactionMessage);
		log.info("消息发送成功，messageId={}",messageId);
	}

	@Override
	public int saveAndSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException {
		if (rpTransactionMessage == null) {
			log.info("保存发送的消息为空");
			throw new MessageBizException("保存发送的消息为空");
		}
		if(StringUtils.isEmpty(rpTransactionMessage.getConsumerQueue())) {
			log.info("所保存发送消息的消费队列为空");
			throw new MessageBizException("所保存发送消息的消费队列为空");
		}
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setMessageSendTimes(rpTransactionMessage.getMessageSendTimes()+1);
		rpTransactionMessage.setStatus(MessageStatusEnum.SENDING.name());
		int insertCount = messageMapper.insert(rpTransactionMessage);
		log.info("消息保存成功:{}",JsonMapper.obj2String(rpTransactionMessage));
		kafkaSender.send(rpTransactionMessage);
		log.info("消息发送成功,topic = {},message = {}",rpTransactionMessage.getConsumerQueue(),JsonMapper.obj2String(rpTransactionMessage));
		return insertCount;
	}

	@Override
	public void directSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException {
		if (rpTransactionMessage == null) {
			log.info("发送的消息为空");
			throw new MessageBizException("发送的消息为空");
		}
		if(StringUtils.isEmpty(rpTransactionMessage.getConsumerQueue())) {
			log.info("所发送消息的消费队列为空");
			throw new MessageBizException("所发送消息的消费队列为空");
		}
		kafkaSender.send(rpTransactionMessage);
		log.info("发送消息成功: topic = {} , message = {}",rpTransactionMessage.getConsumerQueue(),rpTransactionMessage);
	}

	@Override
	public void reSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException {
		if (rpTransactionMessage == null) {
			log.info("发送的消息为空");
			throw new MessageBizException("发送的消息为空");
		}
		if(StringUtils.isEmpty(rpTransactionMessage.getConsumerQueue())) {
			log.info("所发送消息的消费队列为空");
			throw new MessageBizException("所发送消息的消费队列为空");
		}
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setMessageSendTimes(rpTransactionMessage.getMessageSendTimes()+1);
		messageMapper.updateByPrimaryKeySelective(rpTransactionMessage);
		log.info("消息保存成功:{}",JsonMapper.obj2String(rpTransactionMessage));
		kafkaSender.send(rpTransactionMessage);
		log.info("发送消息成功: topic = {} , message = {}",rpTransactionMessage.getConsumerQueue(),JsonMapper.obj2String(rpTransactionMessage));
	}

	@Override
	public void reSendMessageByMessageId(String messageId) throws MessageBizException {
		RpTransactionMessage rpTransactionMessage = messageMapper.selectByPrimaryKey(messageId);
		if (rpTransactionMessage == null) {
			log.info("发送的消息为空");
			throw new MessageBizException("发送的消息为空");
		}
		if(StringUtils.isEmpty(rpTransactionMessage.getConsumerQueue())) {
			log.info("所发送消息的消费队列为空");
			throw new MessageBizException("所发送消息的消费队列为空");
		}
		int maxTimes = kafkaProperty.getMaxTimes();
		if(rpTransactionMessage.getMessageSendTimes()>=maxTimes) {
			rpTransactionMessage.setAreadlyDead(PublicEnum.YES.name());
		}
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setMessageSendTimes(rpTransactionMessage.getMessageSendTimes()+1);
		messageMapper.updateByPrimaryKeySelective(rpTransactionMessage);
		log.info("消息保存成功:{}",JsonMapper.obj2String(rpTransactionMessage));
		kafkaSender.send(rpTransactionMessage);
		log.info("发送消息成功: topic = {} , message = {}",rpTransactionMessage.getConsumerQueue(),JsonMapper.obj2String(rpTransactionMessage));
	}

	@Override
	public void setMessageToAreadlyDead(String messageId) throws MessageBizException {
		if(StringUtils.isBlank(messageId)) {
			throw new MessageBizException("messageId为空");
		}
		RpTransactionMessage rpTransactionMessage = messageMapper.selectByPrimaryKey(messageId);
		if(rpTransactionMessage == null) {
			throw new MessageBizException("根据消息ID="+messageId+",查找到的消息为空");
		}
		rpTransactionMessage.setEditTime(new Date());
		rpTransactionMessage.setAreadlyDead(PublicEnum.YES.name());
		messageMapper.updateByPrimaryKeySelective(rpTransactionMessage);

	}

	@Override
	public RpTransactionMessage getMessageByMessageId(String messageId) throws MessageBizException {
		if (StringUtils.isBlank(messageId)) {
			throw new MessageBizException("messageId为空");
		}
		Example example = new Example(RpTransactionMessage.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("messageId", messageId);
		List<RpTransactionMessage> rpTransactionMessages = messageMapper.selectByExample(example);
		if (rpTransactionMessages == null) {
			throw new MessageBizException("根据消息ID="+messageId+",查找到的消息为空");
		} else if(rpTransactionMessages.size()>1) {
			for (RpTransactionMessage rpTransactionMessageTemp : rpTransactionMessages) {
				log.info("messageId={},message:{}",messageId,rpTransactionMessageTemp);
			}
			throw new MessageBizException("根据消息ID="+messageId+",查找到多条消息");
		}
		log.info("messageId={},message:{}",messageId,JsonMapper.obj2String(rpTransactionMessages.get(0)));
		return rpTransactionMessages.get(0);
	}

	@Override
	public void deleteMessageByMessageId(String messageId) throws MessageBizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException {
		log.info("-------开始发送超时信息，topic={}----------",queueName);
		int pageSize = 1000;
		if (batchSize > 0 && batchSize <= 100) {
			pageSize = 100;
		}else if (batchSize > 100 && batchSize <= 5000) {
			pageSize = batchSize;
		}else if (batchSize > 5000) {
			pageSize = 5000;
		}else {
			pageSize = 1000;
		}
		Example example = new Example(RpTransactionMessage.class);
//		example.orderBy("messageDataType asc");
		example.setOrderByClause("messageDataType asc");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("consumerQueue", queueName);
		criteria.andEqualTo("areadlyDead", PublicEnum.YES.name());
		PageHelper.startPage(0, pageSize);
		List<RpTransactionMessage> messages = messageMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(messages)) {
			log.info("topic = {},超时的消息为空。",queueName);
			throw new MessageBizException("topic = "+ queueName +",超时的消息为空。"); 
		}
		messages.forEach(message->{
			message.setMessageSendTimes(message.getMessageSendTimes()+1);
			message.setEditTime(new Date());
			message.setStatus(MessageStatusEnum.SENDING.name());
			message.setAreadlyDead(PublicEnum.NO.name());
			reSendMessage(message);
		});
		PageInfo<RpTransactionMessage> pageInfo = new PageInfo<>(messages);
		for(int i=2;i<=pageInfo.getPages();i++) {
			PageHelper.startPage(i, pageSize);
			List<RpTransactionMessage> messagesTemp = messageMapper.selectByExample(example);
			messagesTemp.forEach(message->{
				message.setMessageSendTimes(message.getMessageSendTimes()+1);
				message.setEditTime(new Date());
				message.setStatus(MessageStatusEnum.SENDING.name());
				message.setAreadlyDead(PublicEnum.NO.name());
				reSendMessage(message);
			});
		}
		log.info("-----------发送超时信息结束，total={}-------------",pageInfo.getTotal());
	}

}
