package com.dyst.DyMsg.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scala.collection.mutable.HashSet;
import scala.util.parsing.combinator.testing.Str;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.DyMsg.dao.DySqDao;
import com.dyst.DyMsg.entities.Dyjg;
import com.dyst.DyMsg.entities.DyjgBean;
import com.dyst.DyMsg.entities.Dyxx;
import com.dyst.DyMsg.entities.DyxxXq;
import com.dyst.DyMsg.entities.Dyxxsp;
import com.dyst.DyMsg.service.DyssQueryService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.pushmsg.PushMessageUtil;

@Service(value="dyssQueryService")
public class DyssQueryServiceImpl implements DyssQueryService{

	//注入DAO层
	private DySqDao dysqDao;
	public DySqDao getDysqDao() {
		return dysqDao;
	}
	@Autowired
	public void setDysqDao(DySqDao dysqDao) {
		this.dysqDao = dysqDao;
	}
	@Autowired
	private JcdService jcdService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoadService roadService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 发送 订阅 实时查询需要的 过车数据
	 */
	@Override
	@Transactional
	public void sendDyssQueryMsg(String jsonData) {
		//jsonData = "{\"dyid\":1115,\"hphm\":\"京A00023\",\"hpzl\":\"0\",\"tgsj\":\"2016-03-01 00:00:51\",\"jcdid\":\"02010001\",\"cdid\":\"3\",\"tpid\":\"20160312141248650211021761_0_0_0_0_40,2016030100005101205A035812_30\",\"scsj\":\"2016-02-29 23:58:19\"}";
		if(StringUtils.isNotBlank(jsonData)){
			//转换成订阅结果实体
			DyjgBean dyjgBean = JSON.parseObject(jsonData,DyjgBean.class);//来获取订阅id
			Dyjg dyjg = JSON.parseObject(jsonData, Dyjg.class);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				//保存 到数据库
				Dyxx dyxx = (Dyxx) dysqDao.getObjectById(Dyxx.class, Integer.parseInt(dyjgBean.getDyid()));
				dyjg.setDyxx(dyxx);
				dyjg.setLrsj(new Timestamp(new Date().getTime()));
				dyjg.setDylx(dyxx.getDylx());
				dyjg.setDyrjh(dyxx.getJyjh());
				dysqDao.save(dyjg);
				String jsondata = JSON.toJSONString(dyjg);
				//发送实时订阅信息
				PushMessageUtil.sendMessageToOne("Dy@"+dyxx.getJyjh(), jsondata);
				//发送短信
				StringBuilder hpzl_builder = new StringBuilder();
				StringBuilder area_builder = new StringBuilder();
				StringBuilder road_builder = new StringBuilder();
				List<Dictionary> dicList = userService.getDictionarysByTypeCode("0002");
				for(Dictionary d:dicList){
					if(d.getTypeSerialNo().equals(dyjg.getHpzl())){
						hpzl_builder.append(d.getTypeDesc());
						break;
					}
				}
				String hql = "from Jcd where id = ?";
				List<Jcd> jcds = new ArrayList<Jcd>();
				jcds= jcdService.findObject(hql, dyjg.getJcdid());
				Jcd jcd = new Jcd();
				if(jcds.size()>0){
					jcd = jcds.get(0);
					Area area = areaService.findAreaByCqId(jcd.getCqid());
					area_builder.append(area.getAreaname());
					if(StringUtils.isNotBlank(jcd.getDlid())){
						Road road = roadService.findRoadByDlId(jcd.getDlid());
						road_builder.append(road.getRoadName());
					}
				}
				StringBuilder message = new StringBuilder();
				message.append("您好,缉查布控系统订阅车辆报警！号牌号码：").append(dyjg.getHphm())
					.append(",号牌种类：").append(hpzl_builder.toString())
					.append(",监测点").append(jcd.getJcdmc())
					.append("(城区：").append(area_builder.toString())
					.append(",道路：").append(road_builder.toString())
					.append("),通过时间：").append(simpleDateFormat.format(dyjg.getTgsj()))
					.append(",请您及时关注！");
				String[] phones = null ;
				if(dyxx.getDyxxXqs()!=null){
					for(DyxxXq d :dyxx.getDyxxXqs()){
						if(d!=null){
							phones = d.getDxhm2().split(",");
							break;
						}
					}
					if(phones.length>=1){
						for(String phone :phones){
							IntefaceUtils.sendMessage(phone, message.toString());//发送短信
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Map params 查询list
	 */
	public List<Object> getObjects(String hql,Map<String,Object> params){
		return dysqDao.getList(hql, params);
	}

}
