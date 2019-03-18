package com.dyst.DyMsg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.DyxxXq;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.base.utils.PageResult;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.excel.bean.DyExcelBean;
import com.dyst.utils.excel.bean.DyjgExcelBean;

public interface DySqService {

	//hql  带分页的多条件查询，可以传入 集合作为参数
	public PageResult getPageResult(String hql, Map<String,Object> params,int pageNo, int pageSize) ;
	public PageResult getPageResult2(String string, Map<String, Object> params,
			int pageNo, int i);
	//查询列表
	public List<Object> getList(String hql ,Map<String,Object> params);
	//保存 订阅申请新增
	public void addDysq(Dyxx d, Map<String, Object> variables);
	//保存 订阅申请   详情新增
	public void addDysq(DyxxXq d);
	//保存 订阅 审批 信息
	public void addDysp(Dyxxsp d);
	//更新 订阅信息
	public void updateDyxx(Object d);
	//根据ID查询
	public Dyxxsp findDyxxspById(Serializable id);
	//根据ID查询
	public Dyxx findDyxxById(Serializable id);
	public PageResult findTodoTasks(User user, int pageNo, int i) throws Exception;
	//查询待审批的任务的数量
	public int findToDoTasksCount(User user) ;
	//定时更新订阅条目的状态（，是否超过有效期，是，设置为无效）
	public void updateDyStateByQuartz();
	
	public DyxxXq findDyxxXqById(Serializable id);
	
	public List<Dyxx> findExcelList(String hql,Map<String,Object> params);
	
	public List<Dyjg> findExcelList2(String hql,Map<String,Object> params);
	
	//导出excel
	public void exportExcel(List<DyExcelBean> dyExcelBeanList,ServletOutputStream outputStream ) throws Exception;
	//导出订阅结果excel
	public void exportDyjgExcel(List<DyjgExcelBean> dyjgExcelBeanList,ServletOutputStream outputStream) throws Exception;
}
