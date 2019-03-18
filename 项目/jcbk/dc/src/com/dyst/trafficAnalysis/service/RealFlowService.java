package com.dyst.trafficAnalysis.service;

import java.util.Map;


public interface RealFlowService {

	//初始化车流量图表
	public Map<String, Object> getChartData(String beginTime,String endTime,String jcd,String interval) throws Exception;
}
