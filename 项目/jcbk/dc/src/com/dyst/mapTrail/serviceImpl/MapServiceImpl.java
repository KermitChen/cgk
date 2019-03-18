package com.dyst.mapTrail.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.mapTrail.dao.MapDao;
import com.dyst.mapTrail.entities.MapTrailPoint;
import com.dyst.mapTrail.entities.MapTrailPointKD;
import com.dyst.mapTrail.service.MapService;


@Service("mapService")
public class MapServiceImpl implements MapService {
	@Autowired
	private MapDao mapDao;
	@Autowired
	private JcdService jcdService;
	
//	@Override
//	public void clearAll() {
//		mapDao.clearAll();
//	}

	@Override
	public List<Map> findList(String hql, List<Object> parameters) {
		return mapDao.findList(hql, parameters);
	}

//	@Override
//	public void save(Object entity) {
//		mapDao.save(entity);
//	}

	@Override
	public List<MapTrailPoint> findMapTrailList(String hql,
			List<Object> parameters) {
		return mapDao.findMapTrailList(hql, parameters);
	}

	@Override
	public MapTrailPoint findMapTrailById(String start, String end) {
		MapTrailPoint mapTrailPoint = mapDao.findMapTrailById(start, end);
//		if(mapTrailPoint != null){
//			Jcd startJcd = jcdService.findJcdById(start);
//			mapTrailPoint.setStartLng(startJcd.getJd());
//			mapTrailPoint.setStartLat(startJcd.getWd());
//			mapTrailPoint.setStartJcdmc(startJcd.getJcdmc());
//			Jcd endJcd = jcdService.findJcdById(end);
//			mapTrailPoint.setEndLng(endJcd.getJd());
//			mapTrailPoint.setEndLat(endJcd.getWd());
//			mapTrailPoint.setEndJcdmc(endJcd.getJcdmc());
//		}
		return mapTrailPoint;
	}
	@Override
	public MapTrailPointKD findMapTrailKDById(String start, String end) {
		MapTrailPointKD mapTrailPoint = mapDao.findMapTrailKDById(start, end);
		return mapTrailPoint;
	}
}
