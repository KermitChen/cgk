package com.ht.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ht.utils.HttpReqJsonUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		Date date1 = new Date();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tpid", "pic?0dd208581-94e6d11i39*dp=*7pdi=*1s5i8=37b2ied33*6f2543162-8c6395--t=0d2m564d8810i42=_109");
		
		JSONObject jsonResult = HttpReqJsonUtils.http().post("http://localhost:8989/picServer/hkpic/getPicOfBase64ById", JSONObject.toJSONString(params));
//		System.out.println(jsonResult.toJSONString());
		
		Date date2 = new Date();
		double d = (date2.getTime() - date1.getTime());
		System.out.println(d/1000);
	}
}