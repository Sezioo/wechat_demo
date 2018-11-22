package com.sezioo.wechar_demo.dto;

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
public class ImageMessage extends BaseMessage {
	
	@XStreamAlias("PicUrl")
	private String picUrl;
	
	@XStreamAlias("MediaId")
	private String mediaId;
}
