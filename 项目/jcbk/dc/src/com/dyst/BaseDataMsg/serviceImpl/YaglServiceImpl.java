package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.YaglDao;
import com.dyst.BaseDataMsg.entities.Yacs;
import com.dyst.BaseDataMsg.service.YaglService;
import com.dyst.base.utils.PageResult;

@Service(value="yaglService")
public class YaglServiceImpl implements YaglService{

	//注入预案管理dao
	private YaglDao yaglDao;
	public YaglDao getYaglDao() {
		return yaglDao;
	}
	@Autowired
	public void setYaglDao(YaglDao yaglDao) {
		this.yaglDao = yaglDao;
	}

	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,
			int pageNo, int pageSize) {
		return yaglDao.getPageResult(hql, params, pageNo, pageSize);
	}
	
	//添加预案参数信息
	public int addYa(Yacs y) {
		String yadj = y.getYadj();
		String bjlx = y.getBjlx();
		String hql = "from Yacs where 1=1";
		List<Yacs> list = getList(hql,null);
		int flag = 0;
		if(list!=null&&list.size()>=1){
			for(Yacs yacs:list){
				if(yacs.getYadj().equals(yadj)&&yacs.getBjlx().equals(bjlx)){
					flag = 1;
				}
			}
		}
		if(flag==1){
			return 0;
		}else{
			yaglDao.save(y);
			return 1;
		}
	}
	/**
	 * 根据hql查询列表
	 */
	@Override
	public List<Yacs> getList(String hql, Map<String, Object> params) {
		return yaglDao.getList(hql,params);
	}
	
	//删除一条预案信息
	public void deleteYa(Serializable id) {
		yaglDao.deleteYa(id);
	}

	//根据id查询一条预案参数信息
	public Yacs findObjectById(Serializable id) {
		return yaglDao.findObjectById(id);
	}
	//根据hql查询预案参数列表
	public List<Object> findObjects(String hql, List<Object> parameters) {
		return yaglDao.findObjects(hql, parameters);
	}
	//更新预案一条预案参数
	public int udpateYa(Yacs y) {
		int flag = 0;
		try {
			yaglDao.updateYacs(y);
			flag = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	//根据预案种类和预案等级查询预案
	public Yacs findYacsByType(String bklb,String bjlx) {
		return yaglDao.findYacsByType(bklb,bjlx);
	}

	

}
