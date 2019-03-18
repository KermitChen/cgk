package com.dyst.advancedAnalytics.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.advancedAnalytics.entities.FalseHphmExcelBean;
import com.dyst.base.utils.PageResult;

public interface FalseService {

	/**
	 * 分页查询假牌车辆
	 * @param kssj
	 * @param jssj
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFalseForPage(String hphm, String kssj, String jssj, int pageNo, int pageSize) throws Exception;
	/**
	 *  查询列表方法
	 */
	public List<FalseCphm> getList(String hql ,Map<String,Object> params);
	/**
	 * 根据id获取假牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FalseCphm getFalseById(int id) throws Exception;
	/**
	 * 根据号牌号码模糊查询
	 * @param hphm
	 * @return
	 * @throws Exception
	 */
	public List findByHphm(String hphm)throws Exception;
	/**
	 * 更新记录
	 * @param falsePlate
	 * @throws Exception
	 */
	public void updateObject(FalseCphm falseCphm)throws Exception;
	/**
	 * 判断是否到达标记的个数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String decideIsDelete(int jpid)throws Exception;
	/**
	 * 删除假牌记录和标记记录
	 * @param jpid
	 * @throws Exception
	 */
	public void deleteCphmAndFlag(int jpid)throws Exception;
	/**
	 * 查询标记删除的记录
	 * @param jpid
	 * @return
	 * @throws Exception
	 */
	public List findDeleteData(int jpid)throws Exception;
	/**
	 * 查询某用户是否已标记删除词条记录
	 * @param jpid
	 * @param pno
	 * @return
	 * @throws Exception
	 */
	public List searchIsMark(int jpid,String pno)throws Exception;
	/**
	 * 标记删除
	 * @param jpid
	 * @param pno
	 * @param pname
	 * @param reason
	 * @param realPlate
	 * @throws Exception
	 */
	public void markDelete(Integer jpid, String pno, String pname,String reason, String realPlate)throws Exception;
	/**
	 * 导出excel
	 * @param excelBeanList
	 * @param outputStream
	 */
	public void excelExportForFakeHphm(List<FalseHphmExcelBean> excelBeanList,
			ServletOutputStream outputStream);
}
