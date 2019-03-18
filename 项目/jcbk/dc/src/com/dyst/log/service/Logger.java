package com.dyst.log.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import com.dyst.kafka.util.KafkaProPoolFactory;

public class Logger {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final String BUSINESS_TOPIC= "business_log";//业务日志topic
	private static final String ERROR_TOPIC= "error_log";//错误日志topic
	/**
	 * 写入kafka业务日志
	 * @param operator	操作人
	 * @param ip	ip地址
	 * @param moduleName	模块名称		eg:部门信息管理
	 * @param operateName	操作内容		eg:新增用户
	 * @param args	传入参数
	 */
	@Deprecated
	public static void businessLog(String operator,String ip,String moduleName,String operateName,String args){
		String message = joinStr("#",operator,ip,moduleName,operateName,args,sdf.format(new Date()));
		KafkaProPoolFactory pool = null;
		Producer<String, String> producer = null;
		try {
			pool = KafkaProPoolFactory.getInstance();
			producer = pool.getConnection();
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(BUSINESS_TOPIC, message);
			producer.send(data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				pool.releaseConnection(producer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 写入kafka业务日志
	 * @param operator 操作人
	 * @param ip ip
	 * @param moduleName 模块名称
	 * @param operateName 操作内容
	 * @param args 参数
	 * @param ex 异常信息
	 */
	@Deprecated
	public static void errorLog(String operator,String ip,String moduleName,String operateName,String args,Exception ex){
		Writer w = null;
		KafkaProPoolFactory pool = null;
		Producer<String, String> producer = null;
		try {
			w = new StringWriter();
			ex.printStackTrace(new PrintWriter(w));
			String message = joinStr("#",operator,ip,moduleName,operateName,args,sdf.format(new Date()),w.toString());
			pool = KafkaProPoolFactory.getInstance();
			producer = pool.getConnection();
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(ERROR_TOPIC, message);
			producer.send(data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				pool.releaseConnection(producer);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 按指定的分隔符拼接字符串
	 * @param splitStr
	 * @param strs
	 * @return
	 */
	private static String joinStr(String splitStr,String... strs){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append(splitStr);
		}
		return sb.substring(0, sb.length()-1);
	}
}
