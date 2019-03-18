package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.AreaDao;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Service("areaService")
public class AreaServiceImpl implements AreaService {
	
	//注入dao
	@Autowired
	private AreaDao areaDao;

	public void addArea(Area area) {
		area.setDeleteFlag("0");
		areaDao.save(area);
	}

	public void updateArea(Area area) {
		areaDao.update(area);
	}

	public void deleteArea(Serializable id) {
		areaDao.delete(id);
	}

	public Area findAreaById(Serializable id) {
		return areaDao.findObjectById(id);
	}
	
	//根据城区ID查询城区信息
	public Area findAreaByCqId(String cqid){
		return areaDao.findAreaByCqId(cqid);
	}

	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return areaDao.getPageResult(queryHelper, pageNo, pageSize);
	}

	public Area findAreaByName(String areaName) {
		return areaDao.findAreaByName(areaName);
	}

	public List<Area> findAreasById(Serializable id) {
		return areaDao.findAreasById(id);
	}

	public List<Area> findObjects(String hql, List<Object> parameters) {
		return areaDao.findObjects(hql, parameters);
	}

}
