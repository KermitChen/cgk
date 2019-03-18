package com.dyst.DyMsg.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装多个订阅详情list
 * @author liuqiang
 *
 */
public class Bean {

	private List<DyxxXq> xqList = new ArrayList<DyxxXq>();
	
	public Bean(){
		
	}

	public List<DyxxXq> getXqList() {
		return xqList;
	}

	public void setXqList(List<DyxxXq> xqList) {
		this.xqList = xqList;
	}
	
}
