package com.sezioo.wechat_demo.param;

import java.util.Date;

import javax.xml.crypto.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LinkParam {
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
}
