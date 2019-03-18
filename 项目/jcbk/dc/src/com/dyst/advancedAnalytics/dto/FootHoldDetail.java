package com.dyst.advancedAnalytics.dto;

public class FootHoldDetail {

	private String timeBucket;
	private String jcdid;
	private int times;
	private int total;
	
	
	public FootHoldDetail() {
		super();
	}
	public FootHoldDetail(String timeBucket, String jcdid, int times, int total) {
		super();
		this.timeBucket = timeBucket;
		this.jcdid = jcdid;
		this.times = times;
		this.total = total;
	}
	public String getTimeBucket() {
		return timeBucket;
	}
	public void setTimeBucket(String timeBucket) {
		this.timeBucket = timeBucket;
	}
	public String getJcdid() {
		return jcdid;
	}
	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
