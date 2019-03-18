package com.dyst.dispatched.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.base.utils.QueryHelper;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.earlyWarning.service.EWarningService;
import com.dyst.utils.Tools;
@Component
public class QuartzDispatched{
	@Autowired
	private DispatchedService dispatchedService;
	@Transactional
	public void updateDispatched(){
		QueryHelper queryHelper = new QueryHelper(Dispatched.class, "d");
		queryHelper.addCondition("d.ywzt in (?,?)", "1","7");
		List<Dispatched> list = dispatchedService.findObjects(queryHelper);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = df.format(new Date());
		
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		String levelNow = "00";//当前布控类别级别
		String levelBefore = "";
		
		for(Dispatched dis:list){
			if(dis.getBkjzsj() == null){
				continue;
			}
			if(df.format(dis.getBkjzsj()).compareTo(nowDate)<0){
				dis.setYwzt("5");//已失效
				dispatchedService.updateDispatched(dis);
				List<Dispatched> l = dispatchedService.findDispatchedByHphm(dis.getHphm(), dis.getHpzl(), 0);
				Dispatched dBest = null;
				for(Dispatched d : l){
		        	for(Map bklbMap :bklbList){
						if(bklbMap.get("ID").toString().equals(d.getBklb())){
							levelBefore = bklbMap.get("LEVEL").toString();
						}
						if(dBest != null && bklbMap.get("ID").toString().equals(dBest.getBklb())){
							levelNow = bklbMap.get("LEVEL").toString();
						}
					}
		        	if(d.getBy3().equals("0")){
						dBest = null;
						break;//说明还有生效的同级布控
					}else if((levelBefore.equals("02") && levelNow.compareTo("02") >= 0 && levelNow.compareTo("10") <= 0)
							|| (levelNow.equals("02") && levelBefore.compareTo("02") >= 0 && levelBefore.compareTo("10") <= 0)
							|| (levelBefore.compareTo(levelNow) == 0)){
		        		//平级
					}else if(dBest == null || levelBefore.compareTo(levelNow) < 0){
						dBest = d;//找出目前最高级的布控
					}
		        	d.setBy3("0");
				}
				if(dBest != null){
					for(Dispatched d : l){
			        	for(Map bklbMap :bklbList){
							if(bklbMap.get("ID").toString().equals(d.getBklb())){
								levelBefore = bklbMap.get("LEVEL").toString();
							}
						}
			        	if((levelBefore.equals("02") && levelNow.compareTo("02") >= 0 && levelNow.compareTo("10") <= 0)
								|| (levelNow.equals("02") && levelBefore.compareTo("02") >= 0 && levelBefore.compareTo("10") <= 0)
								|| (levelBefore.compareTo(levelNow) == 0)){
			        		//平级
						}else if(levelNow.compareTo(levelBefore) < 0){
							d.setBy3("1");//屏蔽级别低的布控
						}
			        	dispatchedService.updateDispatched(d);
					}
				}
			}
		}
	}
}
