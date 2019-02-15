package com.sezioo.wechat_demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatButton {

	private List<WechatButton> subBottons;
	
	private String type;
	
	private String name;
	
	private String key;
	
	private String url;
	
	private String mediaId;
	
	private String appId;
	
	private String pagePath;
}
