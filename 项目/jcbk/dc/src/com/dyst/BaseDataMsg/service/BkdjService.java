package com.dyst.BaseDataMsg.service;

import com.dyst.BaseDataMsg.entities.DicDisType;
import com.dyst.base.utils.PageResult;

public interface BkdjService {
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
	 * 添加布控等级
	 * @param type
	 * @throws Exception
	 */
	public void addBkdj(DicDisType type)throws Exception;
	/**
	 * 判断数据库是否存在某字段的某个值
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
	/**
	 * 更新布控等级
	 * @param type
	 * @throws Exception
	 */
	public void updateBkdj(DicDisType type)throws Exception;
}
