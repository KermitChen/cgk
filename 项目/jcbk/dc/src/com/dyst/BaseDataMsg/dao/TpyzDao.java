package com.dyst.BaseDataMsg.dao;

import java.util.List;

import com.dyst.BaseDataMsg.entities.Tpyz;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface TpyzDao extends BaseDao{

	/**
	 * 删除所有数据，插如前使用
	 * @return
	 * @throws Exception
	 */
	public abstract int deleteAll()throws Exception;
	/**
	 * 插入数据
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public abstract void upData(List<Tpyz> list)throws Exception;
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract PageResult getTpyzForPage(int pageNo,int pageSize,String jcdid)throws Exception;
}
