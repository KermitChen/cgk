package com.dyst.BaseDataMsg.dao;

import com.dyst.BaseDataMsg.entities.DicDisType;
import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

public interface BkdjDao extends BaseDao{

	/**
	 * 分页查询布控等级
	 * @param bkdl 布控大类
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageResult findByPage(String bkdl,int pageNo,int pageSize)throws Exception;
	/**
	 * 判断某字段某个值是否已存在
	 * @param showOrder
	 * @param level
	 * @param id
	 * @param notId
	 * @return
	 * @throws Exception
	 */
	public long decideIsHave(String showOrder,String level,String id,String notId)throws Exception;
	/**
	 * 删除布控等级
	 * @param id
	 * @throws Exception
	 */
	public void deleteBkdj(String id)throws Exception;
	/**
	 * 根据id获取布控等级
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DicDisType getBkdjById(String id)throws Exception;
}
