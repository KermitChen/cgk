package com.dyst.advancedAnalytics.dao;

import java.util.List;
import java.util.Map;

import com.dyst.advancedAnalytics.entities.FakePlate;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface FakeDao extends BaseDao{
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
	 * 查询标记删除个数
	 * @return
	 * @throws Exception
	 */
	public long getDeleteNum(int tpid)throws Exception;
	/**
	 * 查询标记删除的记录
	 * @param tpid
	 * @return
	 * @throws Exception
	 */
	public List findDeleteData(int tpid)throws Exception;
	/**
	 * 删除套牌记录和标记记录
	 * @param tpid
	 * @throws Exception
	 */
	public void deleteCphmAndFlag(int tpid)throws Exception;
	
	/**
	 * 根据列表查询
	 */
	public List<FakePlate> getList(String hql, Map<String, Object> params);
}
