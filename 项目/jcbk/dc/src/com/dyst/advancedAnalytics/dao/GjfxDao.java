package com.dyst.advancedAnalytics.dao;


import java.util.List;

import com.dyst.advancedAnalytics.entities.ResFootHold;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface GjfxDao extends BaseDao {
	/**
	 * 通用（分页查询某一计算结果表）
	 * @param tableName 表名
	 * @param resFlag 查询标志
	 * @param pageNo 页码
	 * @param pageSize 单页数量
	 * @return
	 */
	public PageResult getResForPage(String tableName,String resFlag,String orderName,String orderType,int pageNo,int pageSize) throws Exception;
	/**
	 * 分页获取频繁过车
	 * @param tableName 表名
	 * @param resFlag 查询标志
	 * @param pageNo 页码
	 * @param pageSize 单页数量
	 * @return
	 */
	public PageResult getPfgcResForPage(String resFlag,int pageNo,int pageSize) throws Exception;
	/**
	 * 查询落脚点分析统计结果
	 * @param resFlag
	 * @return
	 */
	public List<ResFootHold> getFootHoldRes(String resFlag);
	/**
	 * 查询出行规律结果
	 * @param resFlag
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult getCxglResForPage(String resFlag,int pageNo,int pageSize) throws Exception;
	/**
	 * 查询计算结果中的所有车牌号
	 * @param resFlag
	 * @return
	 * @throws Exception
	 */
	public List getBsCphm(String resFlag) throws Exception;
	/**
	 * 根据车牌号和查询标记查询一个车的过车记录
	 * @param resFlag
	 * @param hphm
	 * @return
	 * @throws Exception
	 */
	public List getBsSb(String resFlag,String hphm)throws Exception;
	/**
	 * 查询布控车辆
	 * @param cphm
	 * @return
	 * @throws Exception
	 */
	public List getBkCphm(String cphm)throws Exception;
}
