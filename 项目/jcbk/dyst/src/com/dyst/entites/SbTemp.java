package com.dyst.entites;

@SuppressWarnings("serial")
public class SbTemp implements java.io.Serializable ,Comparable<SbTemp>{

	private String cphm1;
	private int count;
	private int[] sequence;
	private double propability;
	private String tpid1;
	public String getCphm1() {
		return cphm1;
	}
	public void setCphm1(String cphm1) {
		this.cphm1 = cphm1;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int[] getSequence() {
		return sequence;
	}
	public void setSequence(int[] sequence) {
		this.sequence = sequence;
	}
	public double getPropability() {
		return propability;
	}
	public void setPropability(double propability) {
		this.propability = propability;
	}
	public String getTpid1() {
		return tpid1;
	}
	public void setTpid1(String tpid1) {
		this.tpid1 = tpid1;
	}
	public int compareTo(SbTemp o) {
		if(this.count>o.getCount()){
			return -1;
		}else if(this.count==o.getCount()){
			return 0;
		}else{
			return 1;
		}
	}


}