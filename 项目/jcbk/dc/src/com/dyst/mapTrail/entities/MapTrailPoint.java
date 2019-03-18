package com.dyst.mapTrail.entities;
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
@Table(name = "MAP_TRAIL_POINT")
public class MapTrailPoint implements java.io.Serializable {
	 /** 版本号 */
    private static final long serialVersionUID = -1064988331068487745L;
    
    /** 2个监测点ID组合（逗号隔开） */
    @Id
    @Column(name = "jcdids", unique = true, nullable = false, length = 50)
    private String jcdids;  
    
    /** 2个检测点的距离（米） */
    @Column(name = "len", nullable = true, length = 10)
    private String len;
    
    /** 坐标点集合 */
    @Column(name = "coordinate", nullable = true, length = 2147483647)
    private String coordinate;
    //起始和结束的经纬度
    @Transient
    private double startLng;
    @Transient
    private double startLat;
    @Transient
    private double endLng;
    @Transient
    private double endLat;
    @Transient
    private String startJcdmc;
    @Transient
    private String endJcdmc;
    
    /**
     * 获取2个监测点ID组合（逗号隔开）
     * 
     * @return 2个监测点ID组合（逗号隔开）
     */
     public String getJcdids() {
        return this.jcdids;
     }
     
    /**
     * 设置2个监测点ID组合（逗号隔开）
     * 
     * @param jcdids
     *          2个监测点ID组合（逗号隔开）
     */
     public void setJcdids(String jcdids) {
        this.jcdids = jcdids;
     }
    
    /**
     * 获取2个检测点的距离（米）
     * 
     * @return 2个检测点的距离（米）
     */
     public String getLen() {
        return this.len;
     }
     
    /**
     * 设置2个检测点的距离（米）
     * 
     * @param length
     *          2个检测点的距离（米）
     */
     public void setLen(String len) {
        this.len = len;
     }
    
    /**
     * 获取坐标点集合
     * 
     * @return 坐标点集合
     */
     public String getCoordinate() {
        return this.coordinate;
     }
     
    /**
     * 设置坐标点集合
     * 
     * @param coordinate
     *          坐标点集合
     */
     public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
     }

	public double getStartLng() {
		return startLng;
	}

	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}

	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getEndLng() {
		return endLng;
	}

	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}

	public double getEndLat() {
		return endLat;
	}

	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	public String getStartJcdmc() {
		return startJcdmc;
	}

	public void setStartJcdmc(String startJcdmc) {
		this.startJcdmc = startJcdmc;
	}

	public String getEndJcdmc() {
		return endJcdmc;
	}

	public void setEndJcdmc(String endJcdmc) {
		this.endJcdmc = endJcdmc;
	}

}