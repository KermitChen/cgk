package com.dyst.BaseDataMsg.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.JcdDao;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.utils.BaiduapiOffline;

@Service("jcdService")
public class JcdServiceImpl implements JcdService {

	//注入dao
	@Autowired
	private JcdDao jcdDao;
	
	/**
	 * 启用停用
	 * @param jcdId 监测点ID
	 * @param qybz  变更后的状态值
	 */
	public void qyty(String jcdId, String qybz) throws Exception{
		jcdDao.qyty(jcdId, qybz);
	}
	
	public PageResult getPageResult(String hql, List<Object> params,int pageNo, int pageSize) {
		return jcdDao.getPageResult(hql, params, pageNo, pageSize);
	}
	//利用queryHelper 带分页的多条件语句查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return jcdDao.getPageResult(queryHelper, pageNo, pageSize);
	}
	//查询出所有监测点信息
	public List<Object> findObjects(String hql, List<Object> params) {
		return jcdDao.findObjects(hql, params);
	}
	//根据id查询出一条记录
	public List<Jcd> findObject(String hql, String id) {
		return jcdDao.findObject(hql, id);
	}
	public PageResult getPageResult(String hql, Map<String, Object> params,
			int pageNo, int pageSize) {
		return jcdDao.getPageResult(hql, params, pageNo, pageSize);
	}
	@Override
	public List<Object> findObjects2(String hql, Map<String, Object> params) {
		return jcdDao.findObjects2(hql, params);
	}
	@Override
	public List<Jcd> findAllJcd() {
		//转换为百度坐标
		List<Jcd> list = jcdDao.findAllJcd();
		List<Jcd> l = new ArrayList();
		for(Jcd j : list){
//			double[] bdGis = MapConvertor.wgs2bd(j.getJd(), j.getWd());//这个目前精确度欠缺
			double[] bdGis = BaiduapiOffline.transform(j.getWd(),j.getJd());
			j.setJd(bdGis[0]);
			j.setWd(bdGis[1]);
			l.add(j);
		}
		return l;
	}
	//根据ID查询监测点，并转换坐标
	@Override
	public Jcd findJcdById(String id) {
		//转换为百度坐标
		Jcd jcd = jcdDao.findJcdById(id);
		if(jcd.getJd() != null && jcd.getJd() != 0){
	//		double[] bdGis = MapConvertor.wgs2bd(j.getJd(), j.getWd());//这个目前精确度欠缺
			double[] bdGis = BaiduapiOffline.transform(jcd.getWd(),jcd.getJd());
			jcd.setJd(bdGis[0]);
			jcd.setWd(bdGis[1]);
		}
		return jcd;
	}
	
	//根据条件查询监测点
	@Override
	public List findJcds(String hql, Map<String, Object> params){
		return jcdDao.findJcds(hql,params);
	}
	
	/**
	 * 根据hql及参数查询总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getCountByHql(String hql, Map<String, Object> params) throws Exception{
		return jcdDao.getCountByHql(hql, params);
	}
}