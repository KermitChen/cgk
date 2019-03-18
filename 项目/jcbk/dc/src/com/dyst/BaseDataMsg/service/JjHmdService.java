package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.activiti.engine.runtime.ProcessInstance;

import com.dyst.BaseDataMsg.entities.Jjhomd;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.utils.excel.bean.JjhomdExcelBean;

public interface JjHmdService {

	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);

	public Jjhomd getHmdById(Serializable id);
	//根据号牌号码模糊查询
	public List wildFindHmd(String hphm) throws Exception;
	//查询该车是否是生效红名单
	public Boolean isHmdByHphm(String hphm,String hpzl);
	//保存新增的 红名单
	public void save(Jjhomd hmd);
	//更新红名单
	public void update(Jjhomd hmd);
	
	//保存红名单信息，并启动工作流
	public ProcessInstance saveHomdAndStartFlow(Jjhomd jjhomd,Map<String, Object> variables);
	
	//撤销红名单，并启动工作流
	public ProcessInstance revokeHomdAndStartFlow(Jjhomd jjhomd,Map<String, Object> variables);
	//工作流分页
	public PageResult getPageResult2(String hql, Map<String, Object> params,
			int pageNo, int pageSize);

	public void exportExcel(List<JjhomdExcelBean> excelBeanList,ServletOutputStream outputStream) throws Exception;
	
	//根据hql语句查询列表
	List<Jjhomd> findObjects(String hql, Map<String, Object> params);
	
	//根据hql语句查询列表
	List<Jjhomd> findObjects(String hql);
}
