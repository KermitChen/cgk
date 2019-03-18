package com.dyst.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于标识需要记录日志的方法
 * @author Administrator
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
	/**
	 * 模块名称
	 * @return
	 */
	public String moduleName() default "无描述信息";
	/**
	 * 操作内容
	 * @return
	 */
	public String operationName() default "无描述信息";
	
	/**
	 * 操作类型
	 * @return
	 */
	public String operateType() default "1";
}