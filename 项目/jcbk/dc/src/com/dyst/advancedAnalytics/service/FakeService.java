package com.dyst.advancedAnalytics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.dyst.advancedAnalytics.entities.FakeHphmExcelBean;
import com.dyst.advancedAnalytics.entities.FakePlate;
import com.dyst.base.utils.PageResult;

public interface FakeService {

	/**
	 * 分页查询套牌车辆信息
	 * @param jcdid	监测点id
	 * @param kssj 开始时间
	 * @param jssj 结束时间
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getFakeForPage(String hphm,String jcdid, String kssj, String jssj, int pageNo, int pageSize) throws Exception;
	/**
	 * 假牌 导出excel
	 * @param outputStream 
	 * @param excelBeanList 
	 */
	public void excelExportForFakeHphm(List<FakeHphmExcelBean> excelBeanList, ServletOutputStream outputStream);
	/**
	 * 根据查询条件，查询出所有的假牌信息
	 */
	public List<FakePlate> getList(String hql,Map<String,Object> params);
	/**
	 * 根据id查询套牌车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FakePlate getFakeById(int id) throws Exception;
	/**
	 * 根据车牌号码模糊查询
	 * @param hphm
	 * @return
	 * @throws Exception
	 */
	public List findByHphm(String hphm)throws Exception;
	/**
	 * 判断是否到达标记的个数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String decideIsDelete(int tpid)throws Exception;
	/**
	 * 删除套牌记录和标记记录
	 * @param tpid
	 * @throws Exception
	 */
	public void deleteCphmAndFlag(int tpid)throws Exception;
	/**
	 * 查询标记删除的记录
	 * @param tpid
	 * @return
	 * @throws Exception
	 */
	public List findDeleteData(int tpid)throws Exception;
	/**
	 * 标记删除
	 * @param tpid
	 * @param pno
	 * @param pname
	 * @param reason
	 * @param realPlate
	 * @throws Exception
	 */
	public void markData(Integer tpid, String pno, String pname,String reason, String realPlate)throws Exception;
}
