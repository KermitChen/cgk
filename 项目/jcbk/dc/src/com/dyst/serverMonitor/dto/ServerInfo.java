package com.dyst.serverMonitor.dto;

public class ServerInfo {

	private String disk_usedPecent;
	private String memFree;
	private String memTOtal;
	private String cpuModel;
	private String disk_size;
	private String memBuffers;
	private String disk_free;
	private String cpu_num;
	private String memPecent;
	private String disk_used;
	private String cpuRate;
	private String host;
	private long queryTime;
	private String memUsed;
	private String status;
	public ServerInfo() {
		super();
	}
	public ServerInfo(String disk_usedPecent, String memFree, String memTOtal,
			String cpuModel, String disk_size, String memBuffers,
			String disk_free, String cpu_num, String memPecent,
			String disk_used, String cpuRate, String host, long queryTime,
			String memUsed, String status) {
		super();
		this.disk_usedPecent = disk_usedPecent;
		this.memFree = memFree;
		this.memTOtal = memTOtal;
		this.cpuModel = cpuModel;
		this.disk_size = disk_size;
		this.memBuffers = memBuffers;
		this.disk_free = disk_free;
		this.cpu_num = cpu_num;
		this.memPecent = memPecent;
		this.disk_used = disk_used;
		this.cpuRate = cpuRate;
		this.host = host;
		this.queryTime = queryTime;
		this.memUsed = memUsed;
		this.status = status;
	}
	public String getDisk_usedPecent() {
		return disk_usedPecent;
	}
	public void setDisk_usedPecent(String disk_usedPecent) {
		this.disk_usedPecent = disk_usedPecent;
	}
	public String getMemFree() {
		return memFree;
	}
	public void setMemFree(String memFree) {
		this.memFree = memFree;
	}
	public String getMemTOtal() {
		return memTOtal;
	}
	public void setMemTOtal(String memTOtal) {
		this.memTOtal = memTOtal;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	public String getDisk_size() {
		return disk_size;
	}
	public void setDisk_size(String disk_size) {
		this.disk_size = disk_size;
	}
	public String getMemBuffers() {
		return memBuffers;
	}
	public void setMemBuffers(String memBuffers) {
		this.memBuffers = memBuffers;
	}
	public String getDisk_free() {
		return disk_free;
	}
	public void setDisk_free(String disk_free) {
		this.disk_free = disk_free;
	}
	public String getCpu_num() {
		return cpu_num;
	}
	public void setCpu_num(String cpu_num) {
		this.cpu_num = cpu_num;
	}
	public String getMemPecent() {
		return memPecent;
	}
	public void setMemPecent(String memPecent) {
		this.memPecent = memPecent;
	}
	public String getDisk_used() {
		return disk_used;
	}
	public void setDisk_used(String disk_used) {
		this.disk_used = disk_used;
	}
	public String getCpuRate() {
		return cpuRate;
	}
	public void setCpuRate(String cpuRate) {
		this.cpuRate = cpuRate;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public long getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}
	public String getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(String memUsed) {
		this.memUsed = memUsed;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
