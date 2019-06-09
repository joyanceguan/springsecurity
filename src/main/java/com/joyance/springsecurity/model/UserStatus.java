package com.joyance.springsecurity.model;

public enum UserStatus {

	NORMAL(1,"启用"),
	FROZEN(2,"冻结");
	
	private UserStatus(int key,String value){
		this.key = key;
		this.value = value;
	}
	
	private int key;
	private String value;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static UserStatus valueOf(int key){
		switch (key) {
		    case 1:
		    	return NORMAL;
		    case 2:
		    	return FROZEN;
		    default:
		    	break;
		    }
		return null;
	}
}
