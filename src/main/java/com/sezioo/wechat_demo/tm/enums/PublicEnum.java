package com.sezioo.wechat_demo.tm.enums;

public enum PublicEnum {
	YES(1,"YES"),NO(2,"NO");
	private String name;
	private int index;
	
	private PublicEnum(int index,String name) {
		this.index = index;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
