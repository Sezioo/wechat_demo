package com.sezioo.wechar_demo.dto;

import java.util.Date;

import com.sezioo.wechar_demo.xstreamConverter.XstreamDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseMessageResponse {
	
	@XStreamAlias("ToUserName")
	protected String toUserName;
	
	@XStreamAlias("FromUserName")
	protected String fromUserName;
	
	@XStreamAlias("CreateTime")
	@XStreamConverter(value=XstreamDateConverter.class)
	protected Date createTime;
	
	@XStreamAlias("MsgType")
	protected String msgType;
	
}
