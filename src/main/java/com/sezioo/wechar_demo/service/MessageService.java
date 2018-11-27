package com.sezioo.wechar_demo.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sezioo.wechar_demo.dto.Image;
import com.sezioo.wechar_demo.dto.ImageMessage;
import com.sezioo.wechar_demo.dto.ImageMessageResponse;
import com.sezioo.wechar_demo.dto.TextMessage;
import com.sezioo.wechar_demo.util.XmlUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	public String messageProcess(String message) {
		String xml = "";
		
		TextMessage baseMessage = XmlUtils.xmlToBean(TextMessage.class, message);
		if("text".equalsIgnoreCase(baseMessage.getMsgType())) {
			xml = textMessageProcess(message);
		}else if ("image".equalsIgnoreCase(baseMessage.getMsgType())) {
			xml = imageMessageProcess(message);
		}
		
		
		return xml;
	}
	
	/**
	 * 处理文本信息
	 * @param message
	 * @return
	 */
	public String textMessageProcess(String message) {
		log.info("接受到文本消息：{}",message);
		TextMessage receiveMessage = XmlUtils.xmlToBean(TextMessage.class, message);
		TextMessage responseMessage = new TextMessage();
		BeanUtils.copyProperties(receiveMessage, responseMessage);
		responseMessage.setFromUserName(receiveMessage.getToUserName());
		responseMessage.setToUserName(receiveMessage.getFromUserName());
		responseMessage.setContent("发送图片有惊喜！");
		String xml = XmlUtils.beanToXml(responseMessage);
		return xml;
	}
	
	/**
	 * 处理图片信息
	 * @param message
	 * @return
	 */
	public String imageMessageProcess(String message) {
		log.info("接受到图片消息：{}",message);
		ImageMessage receiveMessage = XmlUtils.xmlToBean(ImageMessage.class, message);
		ImageMessageResponse responseMessage = new ImageMessageResponse();
		responseMessage.setFromUserName(receiveMessage.getToUserName());
		responseMessage.setToUserName(receiveMessage.getFromUserName());
		responseMessage.setCreateTime(new Date());
		responseMessage.setMsgType("image");
		responseMessage.setImage(new Image("A28pKdW3HxVqcxYvxdldSHdWC4KVHhJXyRlErRoMngCorcaeZAGFfl5Llk4ifnGO"));;
		String xml = XmlUtils.beanToXml(responseMessage);
		return xml;
	}
}
