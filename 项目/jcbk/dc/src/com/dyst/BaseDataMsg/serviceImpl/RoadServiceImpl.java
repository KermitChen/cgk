package com.dyst.BaseDataMsg.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.RoadDao;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;

@Service("roadService")
public class RoadServiceImpl implements RoadService {

	@Autowired
	private RoadDao roadDao;
	
	public void addRoad(Road d) {
		roadDao.save(d);
	}

	public void updateRoad(Road d) {
		roadDao.update(d);
	}

	public void deleteRoad(Serializable id) {
		roadDao.delete(id);
	}

	public Road findDictionayById(Serializable id) {
		return roadDao.findObjectById(id);
	}
	
	//根据道路ID查询道路信息
	public Road findRoadByDlId(String dlid){
		return roadDao.findRoadByDlId(dlid);
	}

	public List<Road> findObjects(String hql, List<Object> parameters) {
		return roadDao.findObjects(hql, parameters);
	}

	public List<Road> findObjects(QueryHelper queryHelper) {
		return roadDao.findObjects(queryHelper);
	}

	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize) {
		return roadDao.getPageResult(queryHelper, pageNo, pageSize);
	}

	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize) {
		
		return roadDao.getPageResult(hql, params,pageNo, pageSize);
	}

	
	public void autoDeal() {
		
	}

}
