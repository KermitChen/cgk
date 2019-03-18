package com.dyst.systemmanage.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.dao.SysAnnDao;
import com.dyst.systemmanage.entities.Announcement;
import com.dyst.systemmanage.entities.Message;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.SysAnnService;
import com.dyst.utils.pushmsg.MyScriptSessionListener;
import com.dyst.utils.pushmsg.PushMessageUtil;

@Service(value="sysAnnService")
public class SysAnnServiceImpl implements SysAnnService{

	@Autowired
	private SysAnnDao sysAnnDao;
	/**
	 * 新增一条系统公告
	 */
	@Override
	public void addSysAnn(Announcement a) {
		sysAnnDao.save(a);
	}
	
	/**
	 * ajax分页查询 该页列表
	 */
	@Override
	public PageResult getAnnforPage(String loginName, String fileName,
			String annType, String startTime, String endTime, int pageNo,
			int pageSize) throws Exception {
		
		return sysAnnDao.getAnnforPage(loginName, fileName, annType, startTime, endTime, pageNo, pageSize);
	}

	@Override
	public Announcement getObjectById(Serializable id) {
		return (Announcement) sysAnnDao.getObjectById(Announcement.class, id);
	}

	@Override
	public int update(Announcement a) {
		int temp = 1;
		try {
			sysAnnDao.update(a);
		} catch (Exception e) {
			e.printStackTrace();
			temp = 0;
		}
		return temp;
	}

	@Override
	public int deleteOneById(String id) {
		int flag = 1;
		try {
			Announcement a = (Announcement) sysAnnDao.getObjectById(Announcement.class, Integer.parseInt(id));
			a.setJlzt("0");
			sysAnnDao.update(a);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return flag;
	}

	@Override
	@Transactional
	public int batcheDeleteObj(String[] ids) {
		int index = 1;
		try {
			for(String id :ids){
				if(deleteOneById(id) == 0){
					index = 0;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			index = 0;
		}
		return index;
	}

	@Override
	@Transactional
	public void countUsersHasReadAnn() {
/*		String hql = "select new map(u.loginName as loginName,u.annids as annids) from User u where u.position >= 72";
		List<Object> list = sysAnnDao.getList(hql);
		
		String data = JSON.toJSONString(list);*/
		//查看现在登录用户的数量
		List<String> allScriptSessionIds = MyScriptSessionListener.getAllScriptSessionIds();
		List<String> userIds = new ArrayList<String>();
		for(String user:allScriptSessionIds){
			if(user.length()>7){
				if(user.substring(0, 7).equals("sysAnn@")){
					userIds.add(user);
				}
			}
		}
		if(allScriptSessionIds!=null &&allScriptSessionIds.size()>0){
			PushMessageUtil.sendMessageToMul(userIds, "showMessage", null);
		}
	}
	/**
	 * 查询所有用户待查看 的消息中心的消息
	 */
	@Override
	public List<Message> getEnabledMessage(String hql_myMessage,Map<String,Object> params) {
		List<Message> messages = sysAnnDao.getMessages(hql_myMessage,params);
		return messages;
	}
	/**
	 * 带分页的查询  通知消息
	 */
	@Override
	public PageResult getPageResult(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		return sysAnnDao.getPageResult(hql,params,pageNo,pageSize);
	}	
	/**
	 * 标记 某用户所有的   通知消息为  已读
	 */
	@Override
	public void markUserAllTzzxHasRed(String loginName) {
		sysAnnDao.markUserAllTzzxHasRed(loginName);
	}
	/**
	 * 新增一条  通知消息
	 */
	@Override
	public int saveOneMessage(Message message) {
		int flag = 1;
		try {
			sysAnnDao.save(message);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		return flag;
	}
	
	@Override
	public List<Object> getAllAnnIds(String hql) {
		return sysAnnDao.getList(hql);
	}

	@Override
	public int updateUser(User u) {
		int tmp = 1;
		try {
			sysAnnDao.update(u);
		} catch (Exception e) {
			e.printStackTrace();
			tmp = 0;
		}
		return tmp;
	}

	@Override
	public List<Announcement> getAllAnnIds2(String hql) {
		return sysAnnDao.getList2(hql);
	}

	/**
	 * 系统公告页面，异步加载出，所有的属于该用户的公告信息
	 */
	@Override
	public PageResult getAnnforPageAndNormalUser(String loginName,
			String fileName, String annType, String startTime, String endTime,
			int pageNo, int pageSize, String deptID) throws Exception {
		return sysAnnDao.getAnnforPageAndNormalUser(loginName,
				fileName, annType,startTime,endTime,
				pageNo, pageSize,deptID);
	}
	/**
	 * 根据id查询出一条message消息
	 */
	@Override
	public Message getOneMessgeById(Serializable id) {
		return sysAnnDao.getOneMessageById(id);
	}
}