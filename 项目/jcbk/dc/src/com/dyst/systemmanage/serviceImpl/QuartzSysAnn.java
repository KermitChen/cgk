package com.dyst.systemmanage.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dyst.systemmanage.dao.SysAnnDao;
import com.dyst.utils.pushmsg.MyScriptSessionListener;
import com.dyst.utils.pushmsg.PushMessageUtil;

@Component
public class QuartzSysAnn {
	@Autowired
	private SysAnnDao sysAnnDao;
	@Transactional
	public void countUsersHasReadAnn(){
//		String hql = "select new map(u.loginName as loginName,u.annids as annids) from User u where u.position >= 72";
//		List<Object> list = sysAnnDao.getList(hql);
//		
//		String data = JSON.toJSONString(list);
//		//查看现在登录用户的数量
//		List<String> allScriptSessionIds = MyScriptSessionListener.getAllScriptSessionIds();
//		List<String> userIds = new ArrayList<String>();
//		for(String user:allScriptSessionIds){
//			if(user.substring(0, 7).equals("sysAnn@")){
//				userIds.add(user);
//			}
//		}
//		if(allScriptSessionIds!=null &&allScriptSessionIds.size()>0){
//			PushMessageUtil.sendMessageToMul(userIds, "showMessage", data);
//		}
	}
}
