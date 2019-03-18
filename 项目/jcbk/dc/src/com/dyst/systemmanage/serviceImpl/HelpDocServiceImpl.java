package com.dyst.systemmanage.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dyst.base.dao.BaseDao;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.HelpDocDao;
import com.dyst.systemmanage.entities.Wjjlb;
import com.dyst.systemmanage.service.HelpDocService;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：帮助文档业务层实现类，主要实现方法：
 *       1.根据条件分页查询帮助文档
 *       2.删除帮助文档
 *       3.获取帮助文档
 *       4.新增帮助文档
 *       5.更新帮助文档
 */
@Service("helpDocService")
public class HelpDocServiceImpl implements HelpDocService{
	@Resource
	private HelpDocDao helpDocDao;
	public HelpDocDao getHelpDocDao() {
		return helpDocDao;
	}
	public void setHelpDocDao(HelpDocDao helpDocDao) {
		this.helpDocDao = helpDocDao;
	}
	
	@Resource 
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

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
	public PageResult getWjjlbForPage(String loginName, String userName,
			String fileName, String startTime, String endTime, int pageNo,
			int pageSize) throws Exception {
		return helpDocDao.getWjjlbForPage(loginName, userName, fileName, startTime, endTime, pageNo, pageSize);
	}
	
	/**
	 * 删除帮助文档
	 * @param ids 帮助文档id
	 * 
	 */
	public void deleteWjjlb(String ids) throws Exception {
		helpDocDao.deleteWjjlb(ids);
	}
	
	/**
	 * 获取帮助文档
	 * @param id 信息编号
	 * @return Wjjlb信息
	 */
	public Wjjlb getWjjlb(int id) throws Exception{
		return (Wjjlb)baseDao.getObjectById(Wjjlb.class, id);
	}
	
	/**
	 * 新增帮助文档
	 * @param Wjjlb 对象
	 */
	public void addWjjlb(Wjjlb wjjlb) throws Exception {
		baseDao.save(wjjlb);
	}
	
	/**
	 * 更新帮助文档
	 * @param id 帮助文档id
	 * @param fileName
	 * @param remark
	 */
	public void updateWjjlb(int id, String fileName, String remark) throws Exception{
		helpDocDao.updateWjjlb(id, fileName, remark);
	}
}