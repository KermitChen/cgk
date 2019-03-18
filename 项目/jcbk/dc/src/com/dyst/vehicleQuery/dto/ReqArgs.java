package com.dyst.vehicleQuery.dto;
/**
 * 用于接收请求参数的实体类
 * @author Administrator
 *
 */
public class ReqArgs {

	private String kssj ="";//开始时间
	private String jssj ="";//结束时间
	private String hpzl ="";//号牌种类
	private String hphm ="";//号牌号码
	private String cplx ="";//车牌类型
	private String tpid ="";//过车序号
	private String jcdid ="";//监测点id
	private String jcdid1 ="";
	private String cd ="";//车道
	private String cb ="";//车标
	private String sd1 ="";
	private String sd2 ="";
	private String sd ="";//速度
	private String hmdCphm ="";//红名单车牌号码
	private String unrecognizedFlag ="";//是否已识别flag
	private String isFilterHmd ="";
	private String isHiddenDiv = "";
	private String showStyle = "0";
	private String cxlb= "";
	private String year;
	private String month;
	private String cxrq;
	
	private int pagesize;//页面显示记录数
	private int from = 0;//起始记录数
	private String sort = "";//排序字段
	private String sortType = "";//排序类型 降序，升序
	
	private String showPathDiv = "";
	
	public ReqArgs(){}
	
	public ReqArgs(String kssj, String jssj, String hphm, int pagesize,
			int from, String sort, String sortType) {
		super();
		this.kssj = kssj.trim();
		this.jssj = jssj.trim();
		this.hphm = hphm.trim();
		this.pagesize = pagesize;
		this.from = from;
		this.sort = sort;
		this.sortType = sortType;
	}
	
	public ReqArgs(String kssj, String jssj, String hphm, String jcdid,
			int pagesize, int from, String sort, String sortType) {
		super();
		this.kssj = kssj.trim();
		this.jssj = jssj.trim();
		this.hphm = hphm.trim();
		this.jcdid = jcdid.trim();
		this.pagesize = pagesize;
		this.from = from;
		this.sort = sort.trim();
		this.sortType = sortType.trim();
	}
	
	public String getJcdid1() {
		return jcdid1;
	}

	public void setJcdid1(String jcdid1) {
		this.jcdid1 = jcdid1;
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
	public String getHpzl() {
		return hpzl;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl.trim();
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm.trim();
	}
	public String getCplx() {
		return cplx;
	}
	public void setCplx(String cplx) {
		this.cplx = cplx.trim();
	}
	public String getTpid() {
		return tpid;
	}
	public void setTpid(String tpid) {
		this.tpid = tpid.trim();
	}
	public String getJcdid() {
		return jcdid;
	}
	public void setJcdid(String jcdid) {
		this.jcdid = jcdid.trim();
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd.trim();
	}
	public String getCb() {
		return cb;
	}
	public void setCb(String cb) {
		this.cb = cb.trim();
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd.trim();
	}
	public String getHmdCphm() {
		return hmdCphm;
	}
	public void setHmdCphm(String hmdCphm) {
		this.hmdCphm = hmdCphm.trim();
	}
	
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort.trim();
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType.trim();
	}
	public String getUnrecognizedFlag() {
		return unrecognizedFlag;
	}
	public void setUnrecognizedFlag(String unrecognizedFlag) {
		this.unrecognizedFlag = unrecognizedFlag.trim();
	}
	public String getIsFilterHmd() {
		return isFilterHmd;
	}
	public void setIsFilterHmd(String isFilterHmd) {
		this.isFilterHmd = isFilterHmd.trim();
	}
	
	public String getIsHiddenDiv() {
		return isHiddenDiv;
	}
	public void setIsHiddenDiv(String isHiddenDiv) {
		this.isHiddenDiv = isHiddenDiv.trim();
	}
	public String getShowStyle() {
		return showStyle;
	}
	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle.trim();
	}
	public String getCxlb() {
		return cxlb;
	}
	public void setCxlb(String cxlb) {
		this.cxlb = cxlb.trim();
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
	
	public String getCxrq() {
		return cxrq;
	}
	public void setCxrq(String cxrq) {
		this.cxrq = cxrq.trim();
	}
	
	public String getSd1() {
		return sd1;
	}

	public void setSd1(String sd1) {
		this.sd1 = sd1.trim();
	}

	public String getSd2() {
		return sd2;
	}

	public void setSd2(String sd2) {
		this.sd2 = sd2.trim();
	}

	public String getShowPathDiv() {
		return showPathDiv;
	}

	public void setShowPathDiv(String showPathDiv) {
		this.showPathDiv = showPathDiv;
	}

	@Override
	public String toString() {
		return "ReqArgs [kssj=" + kssj + ", jssj=" + jssj + ", hpzl=" + hpzl
				+ ", hphm=" + hphm + ", cplx=" + cplx + ", tpid=" + tpid
				+ ", jcdid=" + jcdid + ", cd=" + cd + ", cb=" + cb + ", sd1="
				+ sd1 + ", sd2=" + sd2 + ", sd=" + sd + ", hmdCphm=" + hmdCphm
				+ ", unrecognizedFlag=" + unrecognizedFlag + ", isFilterHmd="
				+ isFilterHmd + ", isHiddenDiv=" + isHiddenDiv + ", showStyle="
				+ showStyle + ", cxlb=" + cxlb + ", year=" + year + ", month="
				+ month + ", cxrq=" + cxrq 
				 + ", pagesize=" + pagesize
				+ ", from=" + from + ", sort=" + sort + ", sortType="
				+ sortType + "]";
	}

}
