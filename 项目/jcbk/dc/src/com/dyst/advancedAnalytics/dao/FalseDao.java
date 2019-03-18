package com.dyst.advancedAnalytics.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dyst.advancedAnalytics.entities.FalseCphm;
import com.dyst.advancedAnalytics.entities.FalseDelete;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface FalseDao extends BaseDao{
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
	 * 查询标记删除个数
	 * @return
	 * @throws Exception
	 */
	public long getDeleteNum(int jpid)throws Exception;
	/**
	 * 查询标记删除的记录
	 * @param jpid
	 * @return
	 * @throws Exception
	 */
	public List findDeleteData(int jpid)throws Exception;
	/**
	 * 标记删除表添加记录
	 * @param jpid
	 * @param pno
	 * @param pName
	 * @throws Exception
	 */
	public void markDelete(Integer jpid, String pno, String pname,String reason, String realPlate)throws Exception;
	/**
	 * 删除假牌记录和标记记录
	 * @param jpid
	 * @throws Exception
	 */
	public void deleteCphmAndFlag(int jpid)throws Exception;
	/**
	 * 查询某用户是否已标记删除词条记录
	 * @param jpid
	 * @param pno
	 * @return
	 * @throws Exception
	 */
	public List searchIsMark(int jpid,String pno)throws Exception;
	/**
	 * 查询列表
	 */
	public List<FalseCphm> getList(String hql, Map<String, Object> params);
}
