package com.dyst.jxkh.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YjtjEntity {

	//布控大类
	private String typeSerialNo;
	private String typeDesc;
	//布控大类下的布控小类
	private List<BklbEntity> bklbList;
	//小计
	private Map<String, Integer> subTotalList;
	
	public YjtjEntity() {
		super();
	}

	public String getTypeSerialNo() {
		return typeSerialNo;
	}
	public void setTypeSerialNo(String typeSerialNo) {
		this.typeSerialNo = typeSerialNo;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public List<BklbEntity> getBklbList() {
		return bklbList;
	}

	public void setBklbList(List<BklbEntity> bklbList) {
		this.bklbList = bklbList;
	}

	public Map<String, Integer> getSubTotalList() {
		subTotalList = new HashMap<String, Integer>();
		
		int bjzs = 0;
		int bjjsqs = 0;
		int bjcsqs = 0;
		int bjwqs = 0;
		int bjyxs = 0;
		int bjwxs = 0;
		
		int zlzs = 0;
		int zljsfks = 0;
		int zlwjsfks = 0;
		int zlwfks = 0;
		
		for(BklbEntity b:bklbList){
			bjzs += b.getBjzs();
			bjjsqs += b.getBjjsqs();
			bjcsqs += b.getBjcsqs();
			bjwqs += b.getBjwqs();
			bjyxs += b.getBjyxs();
			bjwxs += b.getBjwxs();
			
			zlzs += b.getZlzs();
			zljsfks += b.getZljsfks();
			zlwjsfks += b.getZlwjsfks();
			zlwfks += b.getZlwfks();
		}
		
		subTotalList.put("xj_bjzs", bjzs);
		subTotalList.put("xj_bjjsqs", bjjsqs);
		subTotalList.put("xj_bjcsqs",bjcsqs);
		subTotalList.put("xj_bjwqs",bjwqs);
		subTotalList.put("xj_bjyxs",bjyxs);
		subTotalList.put("xj_bjwxs",bjwxs);
		subTotalList.put("xj_zlzs",zlzs);
		subTotalList.put("xj_zljsfks",zljsfks);
		subTotalList.put("xj_zlwjsfks",zlwjsfks);
		subTotalList.put("xj_zlwfks",zlwfks);
		return subTotalList;
	}
	
	public void setSubTotalList(Map<String, Integer> subTotalList) {
		this.subTotalList = subTotalList;
	}
}