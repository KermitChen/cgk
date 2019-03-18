package com.dyst.workflow.listener;


import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.dyst.dispatched.dao.WithdrawDao;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.service.DepartmentService;
import com.dyst.utils.Config;
import com.dyst.utils.IntefaceUtils;

/**
 * 审批完成处理器
 *
 */
@Component
public class AfterWithdrawCompleteProcessor implements ExecutionListener {

	private static final long serialVersionUID = -946080224954572824L;

	public void notify(DelegateExecution delegateExecution) throws Exception {
		//从web中获取spring容器
    	WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
    	DispatchedService dispatchedService = (DispatchedService) ac.getBean("dispatchedService");
    	DepartmentService deptService = (DepartmentService) ac.getBean("deptService");
    	WithdrawDao withdrawDao = (WithdrawDao) ac.getBean("withdrawDao");
		
    	//获取撤控记录
    	String businessKey = delegateExecution.getProcessBusinessKey();
    	Withdraw withdraw = withdrawDao.findObjectById(businessKey);
    	
    	//更新布控信息
    	Dispatched dis = dispatchedService.findDispatchedById(withdraw.getBkid());
        dis.setYwzt("3");//已撤控
        dispatchedService.updateDispatched(dis);
        
        //联动撤控
    	String flag = "0";//撤控生效调用省布控接口   0未调用
    	if("00".equals(dis.getBklb())){
    		if("1".equals(Config.getInstance().getStInterfaceFlag().split(":")[1])){//判断开关
    			flag = IntefaceUtils.sendWithdraw(dis, withdraw);//撤控生效调用省布控接口
    		}
    	}
    	withdraw.setBy1(flag);
    	
    	//更改业务状态
        withdraw.setYwzt("1");//已撤控
        withdraw.setGxsj(new Date());
        withdrawDao.update(withdraw);
		
		//获取布控类别
		String sql = "select d.ID ID,d.NAME NAME,d.SUPERID SUPERID,d.LEVEL LEVEL from DIC_DISPATCHED_TYPE d order by d.SHOW_ORDER asc";
		List<Map> bklbList = dispatchedService.findList(sql, null);
		
		String levelNow = "00";//当前布控类别级别
        String levelBefore = "";
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
        	if("0".equals(d.getBy3())){
				dBest = null;
				break;//说明还有生效的同级布控
			} else if((levelBefore.equals("02") && levelNow.compareTo("02") >= 0 && levelNow.compareTo("10") <= 0)
					|| (levelNow.equals("02") && levelBefore.compareTo("02") >= 0 && levelBefore.compareTo("10") <= 0)
					|| (levelBefore.compareTo(levelNow) == 0)){
        		//平级
			} else if(dBest == null || levelBefore.compareTo(levelNow) < 0){
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
		
		//撤控签收
		if(!StringUtils.isEmpty(withdraw.getTzdw())){
			String[] dws = withdraw.getTzdw().split(";");
			for(String dw: dws){
				DisReceive disReceive = new DisReceive();
				disReceive.setBkckbz("2");
				disReceive.setBkid(withdraw.getCkid());
				disReceive.setTzrjh(withdraw.getCxsqrjh());
				disReceive.setTzr(withdraw.getCxsqr());
				disReceive.setTznr(withdraw.getTznr());
				disReceive.setQrzt("0");
				List<Department> depts = deptService.getDeptByDeptNo(dw);
				if(depts != null && depts.size() > 0){
					disReceive.setQrdwmc(depts.get(0).getDeptName());
				}
				disReceive.setQrdw(dw);
				disReceive.setXfdw(withdraw.getCxsqdw());
				disReceive.setXfdwmc(withdraw.getCxsqdwmc());
				disReceive.setXfsj(new Date());
				disReceive.setHphm(dis.getHphm());
				disReceive.setHpzl(dis.getHpzl());
				disReceive.setBklb(dis.getBklb());
				dispatchedService.save(disReceive);
			}
		}
	}
}