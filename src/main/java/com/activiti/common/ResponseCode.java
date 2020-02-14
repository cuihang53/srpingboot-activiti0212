package com.activiti.common;

public enum ResponseCode {
    SUCCESS("success"),
    ERROR("error"),
    NOLOGIN("nologin"),
    NOALLOW("noallow");


    private final String value;
    ResponseCode(String i) {
        this.value=i;
    }
	public String value() {
		return value;
	}
    
    
}
