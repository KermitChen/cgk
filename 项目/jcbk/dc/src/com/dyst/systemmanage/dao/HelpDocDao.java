package com.dyst.systemmanage.dao;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：帮助文档持久层接口，主要接口方法：
 *       1.根据条件分页查询帮助文档
 *       2.删除帮助文档
 *       3.更新帮助文档 
 */
public interface HelpDocDao extends BaseDao{
	/**
	 * 根据条件分页查询帮助文档
	 * @param loginName 用户名
	 * @param userName 姓名
	 * @param fileName 
	 * @param startTime  起始时间
	 * @param endTime  截至时间
	 * @param pageNo  第几页
	 * @param pageSize 每页条数
	 * @return PageResult pageResult
	 */
	public PageResult getWjjlbForPage(String loginName, String userName, String fileName, 
			String startTime, String endTime, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 删除帮助文档
	 * @param ids 帮助文档id
	 * 
	 */
	public void deleteWjjlb(String ids) throws Exception;
	
	/**
	 * 更新帮助文档
	 * @param id 帮助文档id
	 * @param fileName
	 * @param remark
	 */
	public void updateWjjlb(int id, String fileName, String remark) throws Exception;
}