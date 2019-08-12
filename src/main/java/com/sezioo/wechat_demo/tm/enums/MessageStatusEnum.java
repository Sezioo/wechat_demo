package com.sezioo.wechat_demo.tm.enums;

public enum MessageStatusEnum {
	WAITING_CONFIRM("CONFIRMING",1),SENDING("SENDING",2);
	
	private String name;
	
	private Integer index;
	
	private MessageStatusEnum(String name,Integer index) {
		this.name = name;
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
}
