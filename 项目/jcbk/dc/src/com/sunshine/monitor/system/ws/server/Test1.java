package com.sunshine.monitor.system.ws.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.sunshine.monitor.system.ws.server.AddSoapHeader;
import com.sunshine.monitor.system.ws.server.QueryVehPassrecEntity;
import com.sunshine.monitor.system.ws.server.QueryVehPassrecService;
import com.sunshine.monitor.system.ws.server.VehPassrec;

/**
 * 全省漫游接口调用方式
 *
 */
public class Test1 {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			AddSoapHeader ash = new AddSoapHeader();
			
			List list = new ArrayList();
			list.add(ash);
			
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(QueryVehPassrecService.class);
			factory.setOutInterceptors(list);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress("http://10.43.64.181:9080/jcbk/service/QueryVehPassrecServer");
			QueryVehPassrecService service = (QueryVehPassrecService) factory.create();
			
			QueryVehPassrecEntity queryConditions = new QueryVehPassrecEntity();
			queryConditions.setHphm("");
			queryConditions.setHpzl("");
			queryConditions.setKssj("2016-07-06 00:00:00");
			queryConditions.setJssj("2016-07-06 10:00:00");
			List<VehPassrec> temp = service.queryVehPassrec(queryConditions);
			for(int i=0;i < temp.size();i++){
				VehPassrec veh = temp.get(i);
				
				System.out.println("gcxh:" + veh.getGcxh());
				System.out.println("sbbh:" + veh.getSbbh());
				System.out.println("kdbh:" + veh.getKdbh());
				System.out.println("fxbh:" + veh.getFxbh());
				System.out.println("hpzl:" + veh.getHpzl());
				System.out.println("hphm:" + veh.getHphm());
				System.out.println("gcsj:" + veh.getGcsj());
				System.out.println("clsd:" + veh.getClsd());
				System.out.println("hpys:" + veh.getHpys());
				System.out.println("cllx:" + veh.getCllx());
				System.out.println("tp1:" + veh.getTp1());
				System.out.println("tp2:" + veh.getTp2());
				System.out.println("tp3:" + veh.getTp3());
				System.out.println("wfbj:" + veh.getWfbj());
				System.out.println("by1:" + veh.getBy1());
				System.out.println("rksj:" + veh.getRksj());
				System.out.println("clxs:" + veh.getClxs());
				System.out.println("cwhphm:" + veh.getCwhphm());
				System.out.println("cwhpys:" + veh.getCwhpys());
				System.out.println("hpyz:" + veh.getHpyz());
				System.out.println("xszt:" + veh.getXszt());
				System.out.println("byzd:" + veh.getByzd());
				System.out.println("clpp:" + veh.getClpp());
				System.out.println("clwx:" + veh.getClwx());
				System.out.println("csys:" + veh.getCsys());
				System.out.println("cdbh:" + veh.getCdbh());
				System.out.println("kssj:" + veh.getKssj());
				System.out.println("jssj:" + veh.getJssj());
				System.out.println("kssd:" + veh.getKssd());
				System.out.println("jssd:" + veh.getJssd());
				System.out.println("glbm:" + veh.getGlbm());
				System.out.println("hpzlmc:" + veh.getHpzlmc());
				System.out.println("sbmc:" + veh.getSbmc());
				System.out.println("kdmc:" + veh.getKdmc());
				System.out.println("fxmc:" + veh.getFxmc());
				System.out.println("hpysmc:" + veh.getHpysmc());
				System.out.println("cllxmc:" + veh.getCllxmc());
				System.out.println("wfbjmc:" + veh.getWfbjmc());
				System.out.println("cwhpysmc:" + veh.getCwhpysmc());
				System.out.println("hpyzmc:" + veh.getHpyzmc());
				System.out.println("cwhpysmc:" + veh.getCwhpysmc());
				System.out.println("csysmc:" + veh.getCsysmc());
				System.out.println("xsztmc:" + veh.getXsztmc());
				System.out.println("wpc:" + veh.getWpc());
				System.out.println("city:" + veh.getCity());
				System.out.println("datasource:" + veh.getDatasource());
				System.out.println("ll:" + veh.getLl());
				System.out.println("kkjd:" + veh.getKkjd());
				System.out.println("kkwd:" + veh.getKkwd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
