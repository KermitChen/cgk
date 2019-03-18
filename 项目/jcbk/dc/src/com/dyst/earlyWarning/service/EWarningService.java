package com.dyst.earlyWarning.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.earlyWarning.entities.Instruction;
import com.dyst.earlyWarning.entities.InstructionSign;

public interface EWarningService {
	//更新
	public void update(Object d);
	//保存
	public Serializable save(Object d);
	//根据ID查询
	public EWarning findEWarningById(Serializable id);
	//根据ID 查询预警签收
	public EWRecieve findEWRecieveById(Serializable id);
	//根据ID 查询指令
	public Instruction findInstructionByQsId(Serializable qsid);
	//根据ID 查询指令签收
	public InstructionSign findInstructionSignById(Serializable id);
	//多条件语句查询
	public List findObjects(String hql, List<Object> parameters);
	//利用queryHelper，多条件语句查询
	public 	List<EWRecieve> findObjects(QueryHelper queryHelper);
	//根据条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	// 带分页的多条件查询
	public PageResult getPageResult(String hql, List<Object> parameters, int pageNo, int pageSize);
	//多条件语句查询
	public List<Map> findList(String sql, List<Object> parameters);
	//DWR推送
	public void sendDWRMsg(String jsonData);
}