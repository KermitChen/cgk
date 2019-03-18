package com.dyst.advancedAnalytics.utils;

public class StaticUtil {

	//高级分析中发送请求的kafka的topic名
	public static final String KAFKA_REQUEST_TOPIC_NAME = "cal_req";
	
	//高级分析中请求的业务标记
	public static final String BS_BUS_FLAG = "01";
	public static final String DWPZ_BUS_FLAG = "02";
	public static final String PFGC_BUS_FLAG = "05";
	
	//高级分析中接收结果的topic和数据库表名
	public static final String BS_TOPIC_NAME = "bs_res";
	public static final String DWPZ_TOPIC_NAME = "dwpz_res";
	public static final String PFGC_TOPIC_NAME = "pfgc_res";
}
