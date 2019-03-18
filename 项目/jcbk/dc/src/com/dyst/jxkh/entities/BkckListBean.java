package com.dyst.jxkh.entities;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BkckListBean {

	private List<BkckEntity> list;
	private Map<String,String> total;
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	int bktz_zs = 0;
	int bktz_asqs = 0;
	int bktz_csqs = 0;
	int bktz_wqs = 0;
	String bktz_zqsl;
	String bktz_asqsl;
	
	int cktz_zs = 0;
	int cktz_asqs = 0;
	int cktz_csqs = 0;
	int cktz_wqs = 0;
	String cktz_zqsl;
	String cktz_asqsl;
	
	public BkckListBean() {
	}

	public BkckListBean(List<BkckEntity> list) {
		this.list = list;
		this.total = initMap();
	}

	private Map<String, String> initMap() {
		percentFormat.setMinimumFractionDigits(2);
		total = new HashMap<String, String>();
		for(BkckEntity b:list){
			bktz_zs += Integer.parseInt(b.getBktzzs());
			bktz_asqs += Integer.parseInt(b.getBktz_asqs());
			bktz_csqs += Integer.parseInt(b.getBktz_csqs());
			bktz_wqs += Integer.parseInt(b.getBktz_wqs());
			
			cktz_zs += Integer.parseInt(b.getCktzzs());
			cktz_asqs += Integer.parseInt(b.getCktz_asqs());
			cktz_csqs += Integer.parseInt(b.getCktz_csqs());
			cktz_wqs += Integer.parseInt(b.getCktz_wqs());
		}
		if(bktz_zs == 0){
			bktz_zqsl = percentFormat.format(0);
		} else{
			bktz_zqsl = percentFormat.format((double)(bktz_asqs + bktz_csqs)/(bktz_zs*1.0));
		}
		if(bktz_zs == 0 || bktz_asqs == 0){
			bktz_asqsl = percentFormat.format(0);
		} else{
			bktz_asqsl = percentFormat.format((double)bktz_asqs/(bktz_zs*1.0));
		}
		
		if(cktz_zs == 0){
			cktz_zqsl = percentFormat.format(0);
		} else{
			cktz_zqsl = percentFormat.format((double)(cktz_asqs+cktz_csqs)/(cktz_zs*1.0));
		}
		if(cktz_zs == 0 || cktz_asqs == 0){
			cktz_asqsl = percentFormat.format(0);
		} else{
			cktz_asqsl = percentFormat.format((double)cktz_asqs/(cktz_zs*1.0));
		}
		
		total.put("bktz_zs", Integer.toString(bktz_zs));
		total.put("bktz_asqs", Integer.toString(bktz_asqs));
		total.put("bktz_csqs", Integer.toString(bktz_csqs));
		total.put("bktz_wqs", Integer.toString(bktz_wqs));
		total.put("bktz_zqsl", bktz_zqsl);
		total.put("bktz_asqsl", bktz_asqsl);
		total.put("cktz_zs", Integer.toString(cktz_zs));
		total.put("cktz_asqs", Integer.toString(cktz_asqs));
		total.put("cktz_csqs", Integer.toString(cktz_csqs));
		total.put("cktz_wqs", Integer.toString(cktz_wqs));
		total.put("cktz_zqsl", cktz_zqsl);
		total.put("cktz_asqsl", cktz_asqsl);
		return total;
	}

	public List<BkckEntity> getList() {
		return list;
	}

	public void setList(List<BkckEntity> list) {
		this.list = list;
	}

	public Map<String, String> getTotal() {
		return total;
	}

	public void setTotal(Map<String, String> total) {
		this.total = total;
	}
}