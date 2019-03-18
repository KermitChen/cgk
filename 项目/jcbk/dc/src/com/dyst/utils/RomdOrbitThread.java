package com.dyst.utils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import com.sunshine.monitor.system.ws.server.VehPassrec;

/**
 * 漫游轨迹查询线程类
 * @author cgk
 */
public class RomdOrbitThread extends Thread {
	private CountDownLatch threadsSignal;//线程数
	private String cphid;//车牌号码
	private String hpzl;//车牌种类
	private String startTime;//起始时间
	private String endTime;//截至时间
	private String address;//访问地址
	private List<String> failfureList;//查询出错的城市
	private List<VehPassrec> roamOrbitList;//结果存放集合
	
	//构造方法
	public RomdOrbitThread(CountDownLatch threadsSignal, String cphid, String hpzl, 
			String startTime, String endTime, String address, List<String> failfureList, List<VehPassrec> roamOrbitList) {
		this.threadsSignal = threadsSignal;//线程数
		this.cphid = cphid;
		this.hpzl = hpzl;
		this.startTime = startTime;
		this.endTime = endTime;
		this.address = address;
		this.failfureList = failfureList;
		this.roamOrbitList = roamOrbitList;
	}
	
	@Override
	public void run() {
		//拆分       城市|地址
		String[] addressArr = address.split("\\|");
		try {
			//调取接口方法
			List<VehPassrec> list = null;
			if(addressArr[1] != null && addressArr[1].indexOf("QueryVehPassrecServer") != -1){
				list = IntefaceUtils.queryRoamOrbits(cphid, hpzl, startTime, endTime, addressArr[1]);//新接口
			} else if(addressArr[1] != null && addressArr[1].indexOf("InAccess") != -1){
				list = IntefaceUtils.queryRoamOrbitsOfOld(cphid, hpzl, startTime, endTime, addressArr[0], addressArr[1]);//旧接口
			}
			//判断结果是否为空，并加入集合
			if(list != null && list.size() > 0){
				roamOrbitList.addAll(list);
			}
			
			// 线程计数器减1,执行完操作
			threadsSignal.countDown();
		} catch (Exception e) {
			failfureList.add(addressArr[0]);
			threadsSignal.countDown();// 线程计数器减1,执行完操作
			e.printStackTrace();
		}
	}
}