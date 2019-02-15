package com.sezioo.wechat_demo.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@XStreamAlias("xml")
public class TextMessage extends BaseMessage{
	
	@XStreamAlias("Content")
	private String content;
}
