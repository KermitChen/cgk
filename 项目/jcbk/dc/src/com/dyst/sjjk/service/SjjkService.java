package com.dyst.sjjk.service;

import java.util.List;
import java.util.Map;

import com.dyst.sjjk.entities.TjEnitity;
import com.dyst.sjjk.entities.YwtjEnitity;
import com.dyst.systemmanage.entities.Department;

public interface SjjkService {
	/**
	 * 往前端页面发送实时过车数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void sendGcToPage(String message, boolean updateFlag) throws Exception;
	
	/**
	 * 根据条件获取已识别、未识别数据量
	 * @param jcdid   监测点Id
	 * @param cd      车道
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 */
	public List<TjEnitity> getYsbWsbData(String jcdid, String cd, String startTime, String endTime, Map<String, String> jcdMap);
	
	/**
	 * 根据hql及参数查询总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getCountByHql(String hql, Map<String, Object> params) throws Exception;
	
	/**
	 * 根据条件获取数据传输情况
	 * @param jcdid   监测点Id
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 * @param csbz 实时传输标准      大于该值则视为超时传输
	 */
	public List<TjEnitity> getSjcsqkData(String jcdid, String startTime, String endTime, String csbz, Map<String, String> jcdMap);
	
	/**
	 * 根据条件获取业务处置情况
	 * @param startTime    起始时间
	 * @param endTime      截止时间
	 * @param khbm 
	 */
	public List<YwtjEnitity> getYwczqk(String startTime, String endTime, String khbm, List<Department> deptList);
}