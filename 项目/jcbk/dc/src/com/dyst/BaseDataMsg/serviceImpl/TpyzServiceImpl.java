package com.dyst.BaseDataMsg.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.dao.JcdDao;
import com.dyst.BaseDataMsg.dao.TpyzDao;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.entities.Tpyz;
import com.dyst.BaseDataMsg.service.TpyzService;
import com.dyst.base.utils.PageResult;
@Service("tpyzService")
public class TpyzServiceImpl implements TpyzService{
	
	@Autowired
	private JcdDao jcdDao;
	@Autowired
	private TpyzDao tpyzDao;
	
	private static Logger logger = LoggerFactory.getLogger(TpyzServiceImpl.class);
	/**
	 * 更新套牌阈值
	 * 没有经度 纬度，在规定距离之内的，在同一条路上的都不计算
	 */
	@Override
	public void upTpyz(double distance, double speed) throws Exception {
		logger.info("开始计算套牌阈值,用户输入距离:{},用户输入速度:{}", distance, speed);
		List<Tpyz> tpyzList = null;
		try {
			tpyzList = new ArrayList<Tpyz>();
			//套牌修正值
//			int tpxzzMinYz = Config.getInstance().getTpxzzMinYz();//最小阈值
//			int tpxzzMaxYz = Config.getInstance().getTpxzzMaxYz();//最大阈值
			//获取监测点
			List<Jcd> li = new ArrayList<Jcd>();
			List<Jcd> list = jcdDao.findAllJcd();			
			li = list;
			
			//公里/小时	转成		米/秒
			double f = new BigDecimal(Double.valueOf(speed)).divide(new BigDecimal("3.6"), 10, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal b = new BigDecimal(f);
			int f1 = (int) b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
			long distanceMi = (long)(distance *1000);
			Jcd t1 = null;
			Jcd t2 = null;
			long beginTime = System.currentTimeMillis();
			for(int i=0;i < list.size();i++){
				t1 = (Jcd)list.get(i);	
				if(t1.getJd() != null && t1.getJd() != 0 && t1.getWd() != null && t1.getWd() != 0
						&& "1".equals(t1.getJcdxz())){
					for(int j=i;j < li.size();j++){
						t2 = (Jcd)li.get(j);
						//若两监测点在同一路段,则不进行计算
						if(StringUtils.isNotEmpty(t1.getDlid()) && StringUtils.isNotEmpty(t2.getDlid()) && t1.getDlid().equals(t2.getDlid())){
							continue;
						}
						if(t2.getJd() != null && t2.getJd() != 0 && t2.getWd() != null && t2.getWd() != 0
								&& "1".equals(t2.getJcdxz())){	
							long n = (long)distanceByLngLat(t1.getJd(), t1.getWd(), t2.getJd(), t2.getWd());//距离 m
							//小于规定的距离，则过滤掉
							if(n <= distanceMi){
								continue;
							}
							//计算阈值
							int yz = ((int)n/f1);
							//如果小于规定阈值范围最小值，则改为最小值
//							if(yz < tpxzzMinYz){
//								yz = tpxzzMinYz;
//							}
							//如果大于规定阈值范围最大值，则改为最大值
//							if(yz > tpxzzMaxYz){
//								yz = tpxzzMaxYz;
//							}
							tpyzList.add(new Tpyz(t1.getId(), t2.getId(), yz, (int)n, (int)speed, "1"));
						}
					}
				}
			}
			logger.info("计算完成！耗时:{}ms", System.currentTimeMillis()-beginTime);
			beginTime = System.currentTimeMillis();
			if (tpyzList != null && tpyzList.size() > 0) {
				tpyzDao.deleteAll();
				tpyzDao.upData(tpyzList);
			}
			logger.info("插入数据库完成！耗时:{}ms,插入记录数：{}", System.currentTimeMillis()-beginTime,tpyzList.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据经纬度，获取两点间的距离
	 * 
	 * @author jzl
	 * @param lng1 经度
	 * @param lat1 纬度
	 * @param lng2
	 * @param lat2
	 * @return
	 *
	 */
	private static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) { 
		
	    double radLat1 = lat1 * Math.PI / 180;
	    double radLat2 = lat2 * Math.PI / 180;
	    double a = radLat1 - radLat2;
	    double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
	    
	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
	            * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
	    s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
	    s = Math.round(s * 10000) / 10000; //s=米
	    return s;
	}

	@Override
	public PageResult getTpyzForPage(int pageNo, int pageSize,String jcdid) throws Exception {
		return tpyzDao.getTpyzForPage(pageNo, pageSize,jcdid);
	}
}
