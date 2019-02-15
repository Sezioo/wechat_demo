package com.sezioo.wechat_demo.dto;


import org.codehaus.jackson.annotate.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WechatUserToken {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@JsonProperty("openid")
	private String openId;
	
	private String scope;
}
