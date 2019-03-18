package com.dyst.vehicleQuery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.systemmanage.service.UserService;

@Service("bdService")
public class BDService {

	@Autowired
	private UserService userService;
	@Autowired
	private JcdService jcdService;
	
	private static Map<String, String> jcdMap = new HashMap<String, String>();
	private static Map<String, String> cplxMap = new HashMap<String, String>();
	private static Map<String, String> cdMap = new HashMap<String,String>();
	
	public Map<String, String> getCdMap() throws Exception{
		if(cdMap.isEmpty()){
			initCdMap();
		}
		return cdMap;
	}
	public Map<String, String> getJcdMap() throws Exception{
		if(jcdMap.isEmpty()){
			initJcdMap();
		}
		return jcdMap;
	}
	public Map<String, String> getCplxMap() throws Exception{
		if(cplxMap.isEmpty()){
			initCplxMap();
		}
		return cplxMap;
	}
	/**
	 * 初始化监测点map
	 * @throws Exception
	 */
	private void initJcdMap() throws Exception{
		synchronized (jcdMap) {
			if (jcdMap.isEmpty()) {
				jcdMap.clear();
				String hql ="select new map(j.id as id,j.jcdmc as name) from Jcd j";
				List<Object> list4 = jcdService.findObjects(hql, null);
				for (Object object : list4) {
					Map map = (Map) object;
					jcdMap.put((String)map.get("id"), (String)map.get("name"));
				}
			}
		}
	}
	/**
	 * 初始化车牌类型map
	 * @throws Exception
	 */
	private void initCplxMap() throws Exception{
		synchronized (cplxMap) {
			if (cplxMap.isEmpty()) {
				cplxMap.clear();
				List<Dictionary> dicList = userService.getDictionarysByTypeCode("0002");//查出车牌类型
				for(Dictionary d:dicList){
					cplxMap.put(d.getTypeSerialNo(), d.getTypeDesc());
				}
			}
		}
	}
	/**
	 * 初始化车道map
	 * @throws Exception
	 */
	private void initCdMap() throws Exception{
		synchronized (cdMap) {
			if (cdMap.isEmpty()) {
				cdMap.clear();
				List<Dictionary> dicList = userService.getDictionarysByTypeCode("CD");
				for(Dictionary d:dicList){
					cdMap.put(d.getTypeSerialNo(), d.getTypeDesc());
				}
			}
		}
	}
}