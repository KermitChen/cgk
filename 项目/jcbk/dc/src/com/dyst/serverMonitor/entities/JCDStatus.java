package com.dyst.serverMonitor.entities;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 地图轨迹点记录表(MAP_TRAIL_POINT)
 * 
 * @version 1.0.0 2016-06-21
 */
@Entity
@Table(name = "JCDStatus")
public class JCDStatus implements java.io.Serializable {
    
	private static final long serialVersionUID = 1L;

	/** ID */
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private int id;  
    
    /** 监测点ID */
    @Column(name = "jcdid", nullable = true, length = 40)
    private String jcdid;
    
    /** 时间 */
    @Column(name = "time", nullable = true)
    private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJcdid() {
		return jcdid;
	}

	public void setJcdid(String jcdid) {
		this.jcdid = jcdid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}