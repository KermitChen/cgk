package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.HmdDao;
import com.dyst.BaseDataMsg.entities.Hmd;
import com.dyst.BaseDataMsg.service.HmdService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Service(value="hmdService")
public class HmdServiceImpl implements HmdService {

	private HmdDao hmdDao;
	
	public HmdDao getHmdDao() {
		return hmdDao;
	}
	@Autowired
	public void setHmdDao(HmdDao hmdDao) {
		this.hmdDao = hmdDao;
	}

	//hql 带分页的多条件语句查询
	public PageResult getPageResult(String hql, List<Object> params,
			int pageNo, int pageSize) {
		return hmdDao.getPageResult(hql, params, pageNo, pageSize);
	}
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return hmdDao.getPageResult(queryHelper, pageNo, pageSize);
	}
	public Hmd getHmdById(Serializable id) {
		return hmdDao.findHmdById(id);
	}

}
