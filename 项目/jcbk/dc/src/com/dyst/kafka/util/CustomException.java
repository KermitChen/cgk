package com.dyst.kafka.util;
/**
 * 自定义异常类
 * @author Administrator
 *
 */
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomException() {
	}
	
	public CustomException(String message){
		super(message);
	}
	
	public CustomException(String message,Exception e){
		super(message, e);
	}
}
