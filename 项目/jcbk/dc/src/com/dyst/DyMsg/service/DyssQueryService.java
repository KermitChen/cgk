package com.dyst.DyMsg.service;

import java.util.List;
import java.util.Map;

public interface DyssQueryService {

	public void sendDyssQueryMsg(String jsonData);
	
	public List<Object> getObjects(String hql,Map<String,Object> params);
}
