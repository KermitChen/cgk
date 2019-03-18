package com.dyst.earlyWarning.serviceImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Area;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.BaseDataMsg.entities.Road;
import com.dyst.BaseDataMsg.service.AreaService;
import com.dyst.BaseDataMsg.service.JcdService;
import com.dyst.BaseDataMsg.service.RoadService;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.earlyWarning.dao.EWarningDao;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.earlyWarning.entities.Instruction;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.pushmsg.MyScriptSessionListener;
import com.dyst.utils.pushmsg.PushMessageUtil;
import com.dyst.vehicleQuery.utils.ComUtils;
import com.dyst.workflow.service.WorkflowTraceService;


@Service("eWarningService")
public class EWarningServiceImpl implements EWarningService {
	@Autowired
	private EWarningDao eWarningDao;
	@Autowired
	private DispatchedService dispatchedService;
	@Autowired
	private JcdService jcdService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoadService roadService;
	@Autowired
	private AreaService areaService;
	@Autowired
	protected WorkflowTraceService traceService;
	
	@Override
	public void update(Object entity) {
		eWarningDao.update(entity);
	}

	@Override
	public EWarning findEWarningById(Serializable id) {
		return eWarningDao.findObjectById(id);
	}
	
	@Override
	public EWRecieve findEWRecieveById(Serializable id) {
		return eWarningDao.findEWRecieveById(id);
	}
	
	@Override
	public Instruction findInstructionByQsId(Serializable qsid) {
		return eWarningDao.findInstructionByQsId(qsid);
	}
	
	@Override
	public InstructionSign findInstructionSignById(Serializable id) {
		return eWarningDao.findInstructionSignById(id);
	}

	@Override
	public List findObjects(String hql, List<Object> parameters) {
		return eWarningDao.findObjects(hql, parameters);
	}

	@Override
	public List<EWRecieve> findObjects(QueryHelper queryHelper) {
		return eWarningDao.findObjects(queryHelper);
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return eWarningDao.getPageResult(queryHelper, pageNo, pageSize);
	}
	
	@Override
	public PageResult getPageResult(String hql, List<Object> parameters, int pageNo, int pageSize){
		return eWarningDao.getPageResult(hql, parameters, pageNo, pageSize);
	}
	@Override
	@SuppressWarnings("rawtypes")
	public List<Map> findList(String sql, List<Object> parameters) {
		return eWarningDao.findList(sql, parameters);
	}
	
	
	/**
	 * DWR推送
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void sendDWRMsg(String jsonData){
		try {
			/*信息保存开始*/
//			jsonData = "{\"bkid\":136,\"hphm\":\"京A00020\",\"hpzl\":\"0\",\"tgsj\":\"2016-03-01 00:00:51\",\"jcdid\":\"02010001\",\"cdid\":\"3\",\"tpid\":\"2016030100005101205A035811_30,2016030100005101205A035812_30\",\"scsj\":\"2016-02-29 23:58:19\"}";
			EWarning eWarning = JSON.parseObject(jsonData, EWarning.class);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String hql = "from Jcd where id = ?";
			List<Jcd> jcdList = jcdService.findObject(hql, eWarning.getJcdid());
			Boolean isEW = true;
			Date bjsj = new Date();
			if(jcdList.size() > 0){
				Jcd jcd = jcdList.get(0);
				if(!"2".equals(jcd.getJcdxz())){//停车场不预警
					//查询城区和道路
					String areaName = "";
					Area area = null;
					if(jcd.getCqid() != null && !"".equals(jcd.getCqid().trim())){
						area = areaService.findAreaByCqId(jcd.getCqid());
						if(area != null && area.getAreaname() != null && !"".equals(area.getAreaname().trim())){
							areaName = area.getAreaname();
						}
					}
					String roadName = "";
					Road road = null;
					if(jcd.getDlid() != null && !"".equals(jcd.getDlid().trim())){
						road = roadService.findRoadByDlId(jcd.getDlid());
						if(road != null && road.getRoadName() != null && !"".equals(road.getRoadName().trim())){
							roadName = road.getRoadName();
						}
					}
					
					//设置监测点名称
					eWarning.setJcdmc((!"".equals(areaName + roadName)?jcd.getJcdmc() + "（" + areaName + roadName + "）":jcd.getJcdmc()));
					
					Dispatched dispatched = dispatchedService.findDispatchedById(eWarning.getBkid());
					if("02".equals(dispatched.getBklb())){//分局布控
						isEW = false;
						//获取当前布控的区域
						String areaNo = "00";//深圳市区
						String areaNo1 = traceService.findKeyById("branchToArea", dispatched.getFjbh());
						if(areaNo1 != null){
							areaNo = areaNo1;
						}
						if(jcd.getCqid() != null && areaNo.equals(jcd.getCqid().trim())){
							isEW = true;
						}
					}
					
					if(isEW){//是否预警
						//保存预警信息
						eWarning.setCplx(eWarning.getHpzl());
						eWarning.setHpzl(ComUtils.cplxToHpzl(eWarning.getCplx()));
						eWarning.setBjsj(bjsj);
						eWarning.setXxly("0");
						int ewId = (Integer) eWarningDao.save(eWarning);
						
						//分发预警，获取预警接收单位
						String sql1 = "";
						if("1".equals(dispatched.getBkdl())){
							if("02".equals(dispatched.getBklb())){//分局布控
								sql1 = "select dept_no, dept_name, system_no from DEPARTMENT where dept_no='" + dispatched.getFjbh() + "010000'";
							} else{
								sql1 = "select dept_no, dept_name, system_no from DEPARTMENT where jxkh='1'";
							}
						} else {//不是涉案类只发给本部门，并且不是省厅布控的(xxly=2)
							if(dispatched.getLskhbm() != null && !"".equals(dispatched.getLskhbm().trim()) 
									&& dispatched.getXxly() != null && !"2".equals(dispatched.getXxly().trim())){
								sql1 = "select dept_no, dept_name, system_no from DEPARTMENT where dept_no='" + dispatched.getLskhbm().trim() + "'";
							}
						}
						
						if(sql1 != null && !"".equals(sql1.trim())){
							List<Map> depts = dispatchedService.findList(sql1, null);
							//获取当前在实时预警页面的人员
							List<String> list = MyScriptSessionListener.getAllScriptSessionIds();
							for(Map dept : depts){
								EWRecieve eWRecieve = new EWRecieve();
								eWRecieve.setBjxh(ewId);
								eWRecieve.setBkid(dispatched.getBkid());
								eWRecieve.setHphm(eWarning.getHphm());
								eWRecieve.setHpzl(eWarning.getHpzl());
								eWRecieve.setCplx(eWarning.getCplx());
								eWRecieve.setJcdid(eWarning.getJcdid());
								eWRecieve.setJcdmc(eWarning.getJcdmc());
								eWRecieve.setJd(jcd.getJd());
								eWRecieve.setWd(jcd.getWd());
								eWRecieve.setCdid(eWarning.getCdid());
								eWRecieve.setTpid(eWarning.getTpid());
								eWRecieve.setSd(eWarning.getSd());
								eWRecieve.setTgsj(eWarning.getTgsj());
								eWRecieve.setScsj(eWarning.getScsj());
								eWRecieve.setBjdl(dispatched.getBkdl());
								eWRecieve.setBjlx(dispatched.getBjlx());
								eWRecieve.setQrzt("0");//默认没确认
								eWRecieve.setBjbm(dept.get("dept_no") != null?dept.get("dept_no").toString():"");
								eWRecieve.setBjbmmc(dept.get("dept_name").toString() != null?dept.get("dept_name").toString():"");
								eWRecieve.setBjsj(bjsj);
								eWRecieve.setGxsj(bjsj);
								eWRecieve.setXxly("0");
								eWRecieve.setBy1("0");//未下发指令
								eWarningDao.save(eWRecieve);
								/*信息保存结束*/
							}
							
							//发送实时预警
							List<String> userIds = new ArrayList<String>();
							for(String userId : list){
								if(userId == null || !"".equals(userId.trim()) || userId.trim().indexOf("@") != -1){//不是实时预警
									continue;
								}
								
								User user = userService.getUser(userId);
								if(user != null){
									for(Map dept : depts){
										if(user.getLskhbm() != null && user.getLskhbm().equals(dept.get("dept_no"))){
											userIds.add(userId);//添加该用户到推送名单
											break;
										}
									}
								}
							}
							PushMessageUtil.sendMessageToMul(userIds, "showMessage",JSON.toJSONString("新预警"));//推送
						}
						
						//发送短信
						String hpzl = "";
						List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL");
						for(Dictionary dictionary : dicList){
							if("HPZL".equals(dictionary.getTypeCode()) && dictionary.getTypeSerialNo().equals(dispatched.getHpzl())){
								hpzl = dictionary.getTypeDesc();
							}
						}
						String message = "您好，缉查布控系统布控车辆报警！号牌号码：" + eWarning.getHphm() + "，号牌种类：" + hpzl + "，监测点：" + eWarning.getJcdmc() + "(" + areaName+roadName + ")，行驶车道：" + eWarning.getCdid() + "，通过时间：" + simpleDateFormat.format(eWarning.getTgsj()) + "，请您及时关注！";
						if(!StringUtils.isEmpty(dispatched.getDxjshm())){
							String[] phones = dispatched.getDxjshm().split(",");
							for(String phone : phones){
								IntefaceUtils.sendMessage(phone, message);//发送短信
							}
						}
					}
					
					//推送到实时预警监控页面，所有预警监控
					//组装数据            bkid;bjxh;hphm;hpzl;cplx;jcdid;jcdmc;cdid;tpid;sd;tgsj;scsj;bjsj;bjdl;bjlx
					StringBuilder sbd =  new StringBuilder();
					sbd.append(eWarning.getBkid()).append(";").append(eWarning.getBjxh()).append(";").append(eWarning.getHphm()).append(";");
					sbd.append(eWarning.getHpzl()).append(";").append(eWarning.getCplx()).append(";").append(eWarning.getJcdid()).append(";");
					sbd.append(eWarning.getJcdmc()).append(";").append(eWarning.getCdid()).append(";").append(eWarning.getTpid()).append(";");
					sbd.append(eWarning.getSd()).append(";").append(simpleDateFormat.format(eWarning.getTgsj())).append(";").append(simpleDateFormat.format(eWarning.getScsj())).append(";");
					sbd.append(simpleDateFormat.format(eWarning.getBjsj())).append(";").append(dispatched.getBkdl()).append(";").append(dispatched.getBjlx());
					
					//获取当前在实时预警监控页面的人员
					List<String> yjPeopleList = MyScriptSessionListener.getAllScriptSessionIds();
					List<String> userIds = new ArrayList<String>();
					for(String userId : yjPeopleList){
						if(userId != null && !"".equals(userId) && userId.startsWith("yjsjjk@")){//给实时预警监控人员推送数据
							userIds.add(userId);//添加该用户到推送名单
						}
					}
					//开始推送
					if(userIds != null && userIds.size() > 0){
						PushMessageUtil.sendMessageToMul(userIds, "showMessage", sbd.toString());//推送
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Serializable save(Object entity) {
		return eWarningDao.save(entity);
	}
}