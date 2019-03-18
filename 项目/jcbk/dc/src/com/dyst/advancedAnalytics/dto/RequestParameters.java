package com.dyst.advancedAnalytics.dto;


public class RequestParameters {

	private String reqFlag="";//请求标记
	private String resFlag="";//查询标记
	private String businessFlag="";//业务标记
	private String loginName="";//用户名
	private String responseTopic="";//结果接收主题名
	private String showStyle="";
	
	private String hphm="";//号牌号码
	private String kssj="";//开始时间
	private String jssj="";//结束时间
	private String cplx="";//车牌类型
	private String sjld="";//时间粒度
	private String jcdid="";//监测点id
	private String txyz="";
	
	private String dwConGroup="";//多维碰撞条件
	
	private String cqid="";//城区id
	private String hssj="";//回溯时间
	
	private String gccs="";//过车次数
	private String cxlb="";
	private String time1="";
	private String time2="";
	private String cxrq="";
	private String year="";
	private String month="";
	
	private String ljsj="";//落脚时间
	private float zybl;//昼夜比例
	
	
	public String getReqFlag() {
		return reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag.trim();
	}

	public String getResFlag() {
		return resFlag;
	}

	public void setResFlag(String resFlag) {
		this.resFlag = resFlag.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName.trim();
	}

	public String getResponseTopic() {
		return responseTopic;
	}

	public void setResponseTopic(String responseTopic) {
		this.responseTopic = responseTopic.trim();
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm.trim();
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj.trim();
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj.trim();
	}

	public String getCplx() {
		return cplx;
	}

	public void setCplx(String cplx) {
		this.cplx = cplx.trim();
	}

	public String getSjld() {
		return sjld;
	}

	public void setSjld(String sjld) {
		this.sjld = sjld.trim();
	}
	public String getShowStyle() {
		return showStyle;
	}

	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}

	public String getDwConGroup() {
		return dwConGroup;
	}

	public void setDwConGroup(String dwConGroup) {
		this.dwConGroup = dwConGroup.trim();
	}


	public String getBusinessFlag() {
		return businessFlag;
	}

	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag.trim();
	}

	public String getCqid() {
		return cqid;
	}

	public void setCqid(String cqid) {
		this.cqid = cqid.trim();
	}

	public String getHssj() {
		return hssj;
	}

	public void setHssj(String hssj) {
		this.hssj = hssj.trim();
	}
	
	public String getGccs() {
		return gccs;
	}

	public void setGccs(String gccs) {
		this.gccs = gccs.trim();
	}
	
	public String getJcdid() {
		return jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid.trim();
	}
	
	public String getCxlb() {
		return cxlb;
	}

	public void setCxlb(String cxlb) {
		this.cxlb = cxlb.trim();
	}
	
	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}
	
	public String getCxrq() {
		return cxrq;
	}

	public void setCxrq(String cxrq) {
		this.cxrq = cxrq.trim();
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year.trim();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month.trim();
	}

	public String getLjsj() {
		return ljsj;
	}

	public void setLjsj(String ljsj) {
		this.ljsj = ljsj.trim();
	}
	
	public String getTxyz() {
		return txyz;
	}

	public void setTxyz(String txyz) {
		this.txyz = txyz.trim();
	}

	public float getZybl() {
		return zybl;
	}

	public void setZybl(float zybl) {
		this.zybl = zybl;
	}

	@Override
	public String toString() {
		return "RequestParameters [reqFlag=" + reqFlag + ", resFlag=" + resFlag
				+ ", businessFlag=" + businessFlag + ", loginName=" + loginName
				+ ", responseTopic=" + responseTopic + ", showStyle="
				+ showStyle + ", hphm=" + hphm + ", kssj=" + kssj + ", jssj="
				+ jssj + ", cplx=" + cplx + ", sjld=" + sjld + ", jcdid="
				+ jcdid + ", txyz=" + txyz + ", dwConGroup=" + dwConGroup
				+ ", cqid=" + cqid + ", hssj=" + hssj + ", gccs=" + gccs
				+ ", cxlb=" + cxlb + ", time1=" + time1 + ", time2=" + time2
				+ ", cxrq=" + cxrq + ", year=" + year + ", month=" + month
				+ ", ljsj=" + ljsj + ", zybl=" + zybl + "]";
	}
	
}
