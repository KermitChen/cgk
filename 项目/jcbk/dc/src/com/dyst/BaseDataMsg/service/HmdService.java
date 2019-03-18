package com.dyst.BaseDataMsg.service;

import java.io.Serializable;
import java.util.List;

import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

public interface HmdService {
	
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize);
	
	public PageResult getPageResult(QueryHelper queryHelper,int pageNo,int pageSize);

	public Hmd getHmdById(Serializable id);
}
