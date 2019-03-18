package com.dyst.utils.excel.bean;

import com.dyst.BaseDataMsg.entities.Jjhomd;

public class JjhomdExcelBean {

	private Jjhomd jjhomd;//红名单
	
	private String jlzt;//记录状态
	
	private String cllx;//车辆类型
	
	private String spjg;//审批结果
	
	private String dqjd;//当前节点
	
	public JjhomdExcelBean() {
	}

	public JjhomdExcelBean(Jjhomd jjhomd, String jlzt, String cllx, String spjg,String dqjd) {
		super();
		this.jjhomd = jjhomd;
		this.jlzt = jlzt;
		this.cllx = cllx;
		this.spjg = spjg;
		this.dqjd = dqjd;
	}

	public String getJlzt() throws Exception {

		return jlzt;
	}

	public String getCllx() throws Exception {
	
		return cllx;
	}

	public String getSpjg() throws Exception {
		
		return spjg;
	}

	public void setSpjg(String spjg) throws Exception {
		
		this.spjg = spjg;
	}

	public void setCllx(String cllx) throws Exception {
		
		this.cllx = cllx;
	}


	public void setJlzt(String jlzt) throws Exception {
		
		this.jlzt = jlzt;
	}

	public Jjhomd getJjhomd() {
		return jjhomd;
	}

	public void setJjhomd(Jjhomd jjhomd) {
		this.jjhomd = jjhomd;
	}
	
	
	public String getDqjd() {
		return dqjd;
	}

	public void setDqjd(String dqjd) {
		this.dqjd = dqjd;
	}


	class A{
		private String typeSerialNo;
		private String typeDesc;
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
	}
	
}
