package com.sezioo.wechat_demo.dto;


import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.collect.Lists;

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
public class WechatUserInfo {
	@JsonProperty("openid")
	private String openId;
	private String nickname;
	private Integer sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private List<String> privilege = Lists.newArrayList();
	private String unionid;
}
