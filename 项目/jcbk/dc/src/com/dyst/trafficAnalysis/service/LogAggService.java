package com.dyst.trafficAnalysis.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface LogAggService {

	public List<JSONObject> tjBusinessLog(String kssj,String jssj,String ip,String operator,String operateName,String tjWord)throws Exception;
}
