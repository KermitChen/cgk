package com.dyst.workflow.listener;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.dispatched.entities.DisApproveRecord;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.Message;
import com.dyst.systemmanage.entities.User;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.systemmanage.service.SysAnnService;
import com.dyst.systemmanage.service.UserService;
import com.dyst.utils.Config;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.ReadConfig;
import com.dyst.utils.Tools;

/**
 * 审批完成处理器
 *
 */
@Component
public class AfterDispatchedCompleteProcessor implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	DispatchedService dispatchedService = (DispatchedService) ac.getBean("dispatchedService");
    	DepartmentService deptService = (DepartmentService) ac.getBean("deptService");
    	SysAnnService sysAnnService = (SysAnnService) ac.getBean("sysAnnService");
    	UserService userService = (UserService) ac.getBean("userService");
		//更改业务状态
    	String businessKey = delegateExecution.getProcessBusinessKey();
        Dispatched dispatched = dispatchedService.findDispatchedById(businessKey);
        dispatched.setYwzt("1");//布控中
        dispatched.setGxsj(new Date());
        
        //获取布控类别
      	String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
      	List<Map> bklbList = dispatchedService.findList(sql, null);
      	String url = ReadConfig.getPropertiesValue("activiti", "dispatchedDetailUrl");
      	
	    //读取字典表
	    String hpzl = "";
	    String bkdl = "";
	    String bklb = "";
	    List<Dictionary> dicList = userService.getDictionarysByTypeCode("HPZL,BKDL");
	    for(Dictionary dictionary : dicList){
	      	if(dictionary.getTypeCode().equals("HPZL") && dictionary.getTypeSerialNo().equals(dispatched.getHpzl())){
	      		hpzl = dictionary.getTypeDesc();
	      	}else if(dictionary.getTypeCode().equals("BKDL") && dictionary.getTypeSerialNo().equals(dispatched.getBkdl())){
	      		bkdl = dictionary.getTypeDesc();
	      	}
	    }
	    for(Map bklbMap :bklbList){
	     	if(bklbMap.get("ID").toString().equals(dispatched.getBklb())){
	      		bklb = bklbMap.get("NAME").toString();
	      	}
	    }
        
        
        List<Dispatched> list = dispatchedService.findDispatchedByHphm(dispatched.getHphm(), dispatched.getHpzl(), 0);
        String levelNow = "";//当前布控类别级别
        String levelBefore = "";
        for(Dispatched d : list){
        	for(Map bklbMap :bklbList){
				if(bklbMap.get("ID").toString().equals(d.getBklb())){
//					bklb = bklbMap.get("NAME").toString();
					levelBefore = bklbMap.get("LEVEL").toString();
				}
				if(StringUtils.isEmpty(levelNow) && bklbMap.get("ID").toString().equals(dispatched.getBklb())){
					levelNow = bklbMap.get("LEVEL").toString();
				}
			}
        	if((levelBefore.equals("02") && levelNow.compareTo("02") >= 0 && levelNow.compareTo("10") <= 0)
					|| (levelNow.equals("02") && levelBefore.compareTo("02") >= 0 && levelBefore.compareTo("10") <= 0)
					|| (levelBefore.compareTo(levelNow) == 0)){
        		//平级
			}else if(levelNow.compareTo(levelBefore) < 0){
				d.setBy3("1");//屏蔽级别低的布控
				dispatchedService.updateDispatched(d);
				String messageContent = "您好,稽查布控系统您的布控信息已被更高级别布控屏蔽,最新布控信息为：号牌号码："+dispatched.getHphm()+",号牌种类："+hpzl+",布控大类："+bkdl+",布控类别："+bklb
						+",布控人："+dispatched.getBkr()+",布控单位名称："+dispatched.getBkjgmc();
				//站内信
				Message message = new Message();
				message.setContent(messageContent);
				message.setRecid(d.getBkrjh());
				message.setTopic("您的布控暂时已被屏蔽");
				message.setUrl(url+"?rowId="+dispatched.getBkid()+"&taskId=&conPath=findDispatched");
				sysAnnService.saveOneMessage(message);
			}
		}
		if("00".equals(dispatched.getBklb())){//联动布控
			dispatched.setBkxh("440300000000"+Tools.getXh(dispatched.getBkid().toString()));
			String flag = "0";//布控生效调用省布控接口  0未调用
			if("1".equals(Config.getInstance().getStInterfaceFlag().split(":")[0])){//判断开关
				flag = IntefaceUtils.sendDispatched(dispatched);
			}
			dispatched.setBy5(flag);
			
		}
        dispatchedService.updateDispatched(dispatched);
        
        //布控签收
		if(!StringUtils.isEmpty(dispatched.getTzdw())){
			String[] dws = dispatched.getTzdw().split(";");
			for(String dw: dws){
				DisReceive disReceive = new DisReceive();
				disReceive.setBkckbz("1");
				disReceive.setBkid(dispatched.getBkid());
				disReceive.setTzrjh(dispatched.getBkrjh());
				disReceive.setTzr(dispatched.getBkr());
				disReceive.setTznr(dispatched.getTznr());
				disReceive.setQrzt("0");
				List<Department> depts = deptService.getDeptByDeptNo(dw);
				if(depts != null && depts.size() > 0){
					disReceive.setQrdwmc(depts.get(0).getDeptName());
				}
				disReceive.setQrdw(dw);
				disReceive.setXfdw(dispatched.getBkjg());
				disReceive.setXfdwmc(dispatched.getBkjgmc());
				disReceive.setXfsj(new Date());
				disReceive.setHphm(dispatched.getHphm());
				disReceive.setHpzl(dispatched.getHpzl());
				disReceive.setBklb(dispatched.getBklb());
				dispatchedService.save(disReceive);
			}
		}
		
		//审批记录,给审批过的人发送短信和站内信
		if(!"1".equals(dispatched.getZjbk())){//直接布控只有警员确认，暂不发送信息
			List<DisApproveRecord> commentList = dispatchedService.findApproveRecord(dispatched.getBkid(), "1");
			
			List<String> czrjh = new ArrayList<String>(); 
			for(DisApproveRecord approve : commentList){
				if(czrjh.contains(approve.getCzrjh())){
					continue;//同一人多次审批
				}
				czrjh.add(approve.getCzrjh());
				String messageContent = "您好,稽查布控系统有一条您审批过的布控已生效,号牌号码："+dispatched.getHphm()+",号牌种类："+hpzl+",布控大类："+bkdl+",布控类别："+bklb
						+",布控人："+dispatched.getBkr()+",布控单位名称："+dispatched.getBkjgmc();
				//站内信
				Message message = new Message();
				message.setContent(messageContent);
				message.setRecid(approve.getCzrjh());
				message.setTopic("审批的布控已生效");
				message.setUrl(url+"?rowId="+dispatched.getBkid()+"&taskId=&conPath=findDispatched");
				sysAnnService.saveOneMessage(message);
				//短信
				User user = userService.getUser(approve.getCzrjh());
				if(user != null && !StringUtils.isEmpty(user.getTelPhone())){
					IntefaceUtils.sendMessage(user.getTelPhone(), messageContent);//发送短信
				}
			}
		}
	}
}