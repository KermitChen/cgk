package com.dyst.base.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.dyst.base.dao.BaseDao2;
import com.dyst.base.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	protected BaseDao2<T> baseDao;
	
	@Override
	public List<T> findObjects(String hql, Map<String, Object> params) {
		return baseDao.findObjects(hql, params);
	}

}
