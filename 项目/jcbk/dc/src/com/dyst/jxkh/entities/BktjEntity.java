package com.dyst.jxkh.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BktjEntity {

	//布控统计实体..布控类型（涉案类，交通违法类，管控类）
	private String typeDesc;
	//布控统计实体..布控类型id
	private String typeSerialNo;
	
	private Integer total;
	
	private List<BktjItemEntity> list= new ArrayList<BktjItemEntity>();
	
	private Map<String,Integer> subTotalList;

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeSerialNo() {
		return typeSerialNo;
	}

	public void setTypeSerialNo(String typeSerialNo) {
		this.typeSerialNo = typeSerialNo;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<BktjItemEntity> getList() {
		return list;
	}

	public void setList(List<BktjItemEntity> list) {
		this.list = list;
	}

	public Map<String, Integer> getSubTotalList() {
		subTotalList = new HashMap<String, Integer>();
		int bdbk = 0;
		int bjfw = 0;
		int ldbk = 0;
		int subtotal =0;
		for(BktjItemEntity b:list){
			bdbk +=b.getBdbk();
			bjfw +=b.getBjfwt();
			ldbk +=b.getLdbk();
			subtotal +=b.getSubtotal();
		}
		subTotalList.put("bdbk", bdbk);
		subTotalList.put("bjfw", bjfw);
		subTotalList.put("ldbk", ldbk);
		subTotalList.put("subtotal", subtotal);
		return subTotalList;
	}

	public void setSubTotalList(Map<String, Integer> subTotalList) {
		this.subTotalList = subTotalList;
	}
	
}
