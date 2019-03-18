package com.dyst.utils.excel.bean.yj;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dyst.jxkh.entities.YjqsEntity22;

public class YjqsExcelBean {

	private List<YjqsEntity22> list;
	
	NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	private String startTime;
	
	private String endTime;
	
	private String deptName;
	
	private int yjqs_yjzs = 0;
	private int yjqs_asqs = 0;
	private int yjqs_csqs = 0;
	private int yjqs_wqs = 0;
	private String yjqs_zqsl;
	private String yjqs_asqsl;
	
	private int zlqs_zlzs = 0;
	private int zlqs_asqs =0;
	private int zlqs_csqs = 0;
	private int zlqs_wqs = 0;
	private String zlqs_zqsl;
	private String zlqs_asqsl;
	
	private int czfk_fkzs = 0;
	private int czfk_asfk = 0;
	private int czfk_csfk =0;
	private int czfk_wfk = 0;
	private String czfk_zfkl;
	private String czfk_asfkl;
	
	private Map<String,String> total ;

	
	public YjqsExcelBean() {
	}
	
	public YjqsExcelBean(List<YjqsEntity22> list) {
		this.list = list;
		this.total = initMap();
	}

	public List<YjqsEntity22> getList() {
		return list;
	}

	public void setList(List<YjqsEntity22> list) {
		this.list = list;
	}



	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Map<String, String> getTotal() {
		return total;
	}

	public void setTotal(Map<String, String> total) {
		this.total = total;
	}

	private Map<String, String> initMap(){
		percentFormat.setMinimumFractionDigits(2);
		total = new HashMap<String, String>();
		for(YjqsEntity22 y:list){
			yjqs_yjzs +=y.getYjqsEntity().getYjqs_qszs();
			yjqs_asqs +=y.getYjqsEntity().getYjqs_asqs();
			yjqs_csqs +=y.getYjqsEntity().getYjqs_csqs();
			yjqs_wqs +=y.getYjqsEntity().getYjqs_wqs();
			
			zlqs_zlzs +=y.getZlqsEntity().getZlqs_zlzs();
			zlqs_asqs +=y.getZlqsEntity().getZlqs_asqs();
			zlqs_csqs +=y.getZlqsEntity().getZlqs_csqs();
			zlqs_wqs +=y.getZlqsEntity().getZlqs_wqs();
			
			czfk_fkzs +=y.getCzfkEntity().getCzfk_fkzs();
			czfk_asfk +=y.getCzfkEntity().getCzfk_asfk();
			czfk_csfk +=y.getCzfkEntity().getCzfk_csfk();
			czfk_wfk +=y.getCzfkEntity().getCzfk_wfk();
		}
		
		if(yjqs_yjzs==0){
			yjqs_zqsl = percentFormat.format(0);
		}else{
			yjqs_zqsl = percentFormat.format((double)(yjqs_asqs + yjqs_csqs) / (yjqs_yjzs*1.0));
		}
		if(yjqs_asqs==0||yjqs_yjzs==0){
			yjqs_asqsl = percentFormat.format(0);
		}else{
			yjqs_asqsl = percentFormat.format((double)yjqs_asqs / (yjqs_yjzs*1.0));
		}
		total.put("yjqs_yjzs", Integer.toString(yjqs_yjzs));
		total.put("yjqs_asqs", Integer.toString(yjqs_asqs));
		total.put("yjqs_csqs", Integer.toString(yjqs_csqs));
		total.put("yjqs_wqs", Integer.toString(yjqs_wqs));
		total.put("yjqs_zqsl", yjqs_zqsl);
		total.put("yjqs_asqsl", yjqs_asqsl);
		
		if(zlqs_zlzs==0){
			zlqs_zqsl = percentFormat.format(0);
		}else{
			zlqs_zqsl = percentFormat.format((double)(zlqs_asqs+zlqs_csqs) / (zlqs_zlzs*1.0));
		}
		if(zlqs_asqs==0||zlqs_zlzs==0){
			zlqs_asqsl = percentFormat.format(0);
		} else{
			zlqs_asqsl = percentFormat.format((double)zlqs_asqs / (zlqs_zlzs*1.0));
		}
		total.put("zlqs_zlzs", Integer.toString(zlqs_zlzs));
		total.put("zlqs_asqs", Integer.toString(zlqs_asqs));
		total.put("zlqs_csqs", Integer.toString(zlqs_csqs));
		total.put("zlqs_wqs", Integer.toString(zlqs_wqs));
		total.put("zlqs_zqsl", zlqs_zqsl);
		total.put("zlqs_asqsl", zlqs_asqsl);
		
		if(czfk_fkzs==0){
			czfk_zfkl = percentFormat.format(0);
		}else{
			czfk_zfkl = percentFormat.format((double)(czfk_asfk + czfk_csfk) / (czfk_fkzs*1.0));
		}
		if(czfk_asfk==0||czfk_fkzs==0){
			czfk_asfkl = percentFormat.format(0);
		}else{
			czfk_asfkl = percentFormat.format((double)czfk_asfk / (czfk_fkzs*1.0));
		}
		total.put("czfk_fkzs", Integer.toString(czfk_fkzs));
		total.put("czfk_asfk", Integer.toString(czfk_asfk));
		total.put("czfk_csfk", Integer.toString(czfk_csfk));
		total.put("czfk_wfk", Integer.toString(czfk_wfk));
		total.put("czfk_zfkl", czfk_zfkl);
		total.put("czfk_asfkl", czfk_asfkl);
		
		return total;
	}
}