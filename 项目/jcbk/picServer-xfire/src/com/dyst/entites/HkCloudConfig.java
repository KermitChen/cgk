package com.dyst.entites;

/**
 * Jcd entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class HkCloudConfig implements java.io.Serializable {
	private String id;
	private String ip;
	private String port;

	// Constructors

	/** default constructor */
	public HkCloudConfig() {
	}

	/** minimal constructor */
	public HkCloudConfig(String id, String ip, String port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}