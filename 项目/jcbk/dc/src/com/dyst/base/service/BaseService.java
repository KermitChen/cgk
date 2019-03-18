package com.dyst.base.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	public List<T> findObjects(String hql,Map<String,Object> params);
}
