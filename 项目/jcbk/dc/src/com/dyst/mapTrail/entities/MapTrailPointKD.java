package com.dyst.mapTrail.entities;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;


/**
 * 地图轨迹点记录表(MAP_TRAIL_POINT)
 * 
 * @version 1.0.0 2016-06-21
 */
@Entity
@Table(name = "MAP_TRAIL_KD")
public class MapTrailPointKD implements java.io.Serializable {
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
}