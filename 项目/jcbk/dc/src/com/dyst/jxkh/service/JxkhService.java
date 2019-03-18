package com.dyst.jxkh.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.jxkh.entities.BkckEntity;
import com.dyst.jxkh.entities.YjqsEntity22;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.excel.bean.bktj.BkckExcelBean;
import com.dyst.utils.excel.bean.bktj.BktjExcelBean;
import com.dyst.utils.excel.bean.yj.YjqsExcelBean;
import com.dyst.utils.excel.bean.yj.YjtjExcelBean;

public interface JxkhService {
	/**
	 * 布控统计
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List> getBktj(String startTime, String endTime, String deptId) throws Exception;
	public void excelExportForBktj(User user, BktjExcelBean bean,ServletOutputStream outputStream) throws Exception;
	
	/**
	 * 布控撤控统计查询
	 * @throws Exception 
	 */
	public List<BkckEntity> getBkck(String startTime,String endTime,String deptId, List<Department> khbmList) throws Exception;
	public void excelExportForBkck(BkckExcelBean excelBean, ServletOutputStream outputStream);
	
	/**
	 * 预警签收统计查询
	 * @throws Exception 
	 */
	public List<YjqsEntity22> getYjqs(String startTime, String endTime, String dwmc, List<Department> khbmList) throws Exception;
	public void excelExportForYjqs(YjqsExcelBean bean,ServletOutputStream outputStream) throws Exception;
	
	/**
	 * 应用成效统计查询
	 * @throws Exception 
	 */
	public void excelExportForYycx(User user, String kssj, String jssj, List<Dictionary> dicList, List listSs, List listSh, List listwf, ServletOutputStream outputStream) throws Exception;

	
	/**
	 * 查询预警统计表
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List> getYjtj(String startTime,String endTime,String deptId) throws Exception;
	public void excelExportForYjtj(User user, YjtjExcelBean bean,ServletOutputStream outputStream) throws Exception;
	
	//查询列表
	public List<Department> getDeptList(String hql ,Map<String,Object> params);
}