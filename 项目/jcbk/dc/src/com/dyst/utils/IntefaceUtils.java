package com.dyst.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tempuri.ServiceSoap;

import com.dragonsoft.adapter.service.QueryAdapterSend;
import com.dyst.chariotesttube.entities.Driver;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.vehicleQuery.service.PicService;
import com.dyst.ws.InterfaceServicePortType;
import com.ht.jcbk.webservice.InAccess;
import com.sunshine.monitor.system.ws.businessservice.BusinessService;
import com.sunshine.monitor.system.ws.server.AddSoapHeader;
import com.sunshine.monitor.system.ws.server.QueryVehPassrecEntity;
import com.sunshine.monitor.system.ws.server.QueryVehPassrecService;
import com.sunshine.monitor.system.ws.server.VehPassrec;

/**
 * 调用接口的工具类
 * @author cgk
 *
 */
public class IntefaceUtils {

	//	/**
//	 * 发送短信
//	 * @param telephone  手机号码，多个号码以半角逗号隔开
//	 * @param message   短信内容
//	 * @return 
//	 * 	 错误码
//	 *       01:成功！
//	 *       02:失败！
//	 *       03:校验码错误！
//	 *       04:服务类型不存在！
//	 *       05:xml类型参数封装错误！
//	 * 	     01-01:短信接收号码不能为空！
//	 *       01-02:短信内容不能为空！
//	 *       01-03:短信内容长度不能超过300！
//	 *       01-04:其他
//	 */
//	@SuppressWarnings("finally")
//	public static String sendMessage(String telephone, String message){
//    	//调用接口发送短信
//    	String wsdl = Config.getInstance().getMessageWebservice();
//    	Client client = null;
//    	Object[] s = null;
//    	String result = "02";//默认失败
//    	try{
//    		if(telephone != null && !"".equals(telephone.trim()) && message != null && !"".equals(message.trim())){
//    			//封装信息
//    			String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head></head><body><data><phones>" + telephone + "</phones><content>" + message + "</content></data></body></root>";
//    			//调用
//    			client = new Client(new URL(wsdl));
//				s = client.invoke("executes", new String[] {"hello world", "01", str_xml});
//				//解析返回结果
//				result = XmlCreater.xmlToStringOfMsg(s[0].toString());
//        	}
//    	} catch (Exception e) {
//    		e.printStackTrace();
//		} finally{
//			return result;
//		}
//    }
	/**
	 * 发送短信
	 * @param telephone  手机号码，多个号码以半角逗号隔开
	 * @param message   短信内容
	 * @return 
	 * 	 错误码
	 *       01:成功！
	 *       02:失败！
	 *       03:校验码错误！
	 *       04:服务类型不存在！
	 *       05:xml类型参数封装错误！
	 * 	     01-01:短信接收号码不能为空！
	 *       01-02:短信内容不能为空！
	 *       01-03:短信内容长度不能超过300！
	 *       01-04:其他
	 */
	@SuppressWarnings("finally")
	public static String sendMessage(String telephone, String message){
    	//调用接口发送短信
    	String wsdl = Config.getInstance().getMessageWebservice();
    	String result = "02";//默认失败
    	try{
    		if(telephone != null && !"".equals(telephone.trim()) && message != null && !"".equals(message.trim())){
    			//封装信息
    			String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head></head><body><data><phones>" + telephone + "</phones><content>" + message + "</content></data></body></root>";
    			
    			//创建连接
    			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    			factory.setServiceClass(InterfaceServicePortType.class);
    			factory.getOutInterceptors().add(new LoggingOutInterceptor());
    			factory.getInInterceptors().add(new LoggingInInterceptor());
    			factory.setAddress(wsdl);
    			InterfaceServicePortType service = (InterfaceServicePortType) factory.create();
    			
    			//调用服务
    			String resultXml = service.executes("hello world", "01", str_xml);
    			
				//解析返回结果
				result = XmlCreater.xmlToStringOfMsg(resultXml);
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
		} finally{
			return result;
		}
    }
	
//	/**
//	 * 查询深圳机动车接口
//	 * @param cphid 车牌号码
//	 * @param hpzl 车辆类型
//	 * @param userName 查询用户名称
//	 * @param identityCardNum  身份证号
//	 * @param deptName 部门名称
//	 * @throws Exception 
//	 * 
//	 */
//	public static Vehicle queryVehicleOfShenZhen(String cphid, String hpzl, String userName, String identityCardNum, String deptName) throws Exception{
//		Vehicle vehicle = null;
//		//只查深圳的车辆
//		if (cphid != null && cphid.indexOf("粤B") != -1 && cphid.length() > 2 
//				&& identityCardNum != null && !"".equals(identityCardNum.trim()) && userName != null && !"".equals(userName.trim()) 
//				&& deptName != null && !"".equals(deptName.trim())) {
//			Client client = new Client(new URL(Config.getInstance().getCjgWebservice()));
//			Object s[] = client.invoke("MyDoor", new String[] {"sunlight.acsl", cphid.substring(1), hpzl});
//			if (s != null && s.length > 0 && "000000成功".equals(s[s.length-1])) {
//				//封装信息
//				vehicle = new Vehicle();
//				vehicle.setClpp1(s[1].toString());//车辆品牌1
//				vehicle.setClxh(s[2].toString());//车辆型号
//				vehicle.setCllx(s[3].toString());//车辆类型
//				vehicle.setCsys(s[4].toString());//车身颜色
//				vehicle.setClsbdh(s[5].toString());//车辆识别代号
//				vehicle.setFdjh(s[6].toString());//发动机号
//				vehicle.setJdcsyr(s[7].toString());//机动车所有人
//				vehicle.setSfzjzl("A");//身份证件种类
//				vehicle.setSfzh(s[8].toString());//身份证号
//				vehicle.setZzdzxz(s[9].toString());//暂住地址详址
//				vehicle.setDjzzxz(s[9].toString());//登记住址详址
//				vehicle.setLxfs(s[10].toString());//联系方式
//				vehicle.setCcdjrq(s[13].toString());//初次登记日期
//				vehicle.setJdczt(s[17].toString());//机动车状态
//				vehicle.setZzxz("");//住址详址
//				vehicle.setZzdzqh("");//暂住地址区划
//				vehicle.setZzqh("");//暂住区划
//				vehicle.setDjzsbh("");//登记证书编号
//				vehicle.setHphm(cphid);//号牌号码
//				vehicle.setHpzl(hpzl);//号牌种类
//				vehicle.setZzg("");//制造国
//				vehicle.setZzamc("");//制造厂名称
//				vehicle.setGcjk("");//国产/进口
//				vehicle.setClpp2("");//车辆品牌2
//				vehicle.setZzzjlzh("");//暂住证居留证号
//				vehicle.setZrrq("");//转入日期
//				vehicle.setFpjg("");//发牌机关
//				vehicle.setFzjg("");//发证机构
//				vehicle.setFdjxh("");//发动机型号
//				vehicle.setRlzl("");//燃料种类
//				vehicle.setPl("");//排量
//				vehicle.setGl("");//功率
//				vehicle.setCcrq("");//出厂日期
//				vehicle.setZzl("");//总质量
//				vehicle.setHdzzl("");//核定载质量
//				vehicle.setHdzk("");//核定载客
//				vehicle.setZqyzl("");//准牵引总质量
//			}
//		}
//		//返回结果
//		return vehicle;
//	}
	
	/**
	 * 查询深圳机动车接口
	 * @param cphid 车牌号码
	 * @param hpzl 车辆类型
	 * @param userName 查询用户名称
	 * @param identityCardNum  身份证号
	 * @param deptName 部门名称
	 * @throws Exception 
	 * 
	 */
	public static Vehicle queryVehicleOfShenZhen(String cphid, String hpzl, String userName, String identityCardNum, String deptName) throws Exception{
		Vehicle vehicle = null;
		//只查深圳的车辆
		if (cphid != null && cphid.indexOf("粤B") != -1 && cphid.length() > 2 
				&& identityCardNum != null && !"".equals(identityCardNum.trim()) && userName != null && !"".equals(userName.trim()) 
				&& deptName != null && !"".equals(deptName.trim())) {
			//创建连接
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ServiceSoap.class);
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.setAddress(Config.getInstance().getCjgWebservice());
			ServiceSoap service = (ServiceSoap) factory.create();

			//接收返回值变量
			javax.xml.ws.Holder<java.lang.Integer> myDoorResult = new javax.xml.ws.Holder<java.lang.Integer>();
			javax.xml.ws.Holder<java.lang.String> strFactBrand = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strModel = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strVehicleTypeDetail = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strColorDetail = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strBodyNo = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strEngineNo = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strOwnerName = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strCertNo = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strAddr = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strTel = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strZip = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strCity = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strFirstRegTime = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strLatestChkTime = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strChkValidTime = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strIssueCertTime = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strVehicleStatDetail = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strDiscardTime = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strSteerPosDetail = new javax.xml.ws.Holder<java.lang.String>();
			javax.xml.ws.Holder<java.lang.String> strRet = new javax.xml.ws.Holder<java.lang.String>();
			
			//调用服务
			service.myDoor("sunlight.acsl", cphid.substring(1), hpzl, myDoorResult, strFactBrand, strModel, strVehicleTypeDetail, strColorDetail, strBodyNo, strEngineNo, strOwnerName, strCertNo, strAddr, strTel, strZip, strCity, strFirstRegTime, strLatestChkTime, strChkValidTime, strIssueCertTime, strVehicleStatDetail, strDiscardTime, strSteerPosDetail, strRet);
			
			if (strRet != null && "000000成功".equals(strRet.value)) {
				//封装信息
				vehicle = new Vehicle();
				vehicle.setClpp1(strFactBrand.value);//车辆品牌1
				vehicle.setClxh(strModel.value);//车辆型号
				vehicle.setCllx(strVehicleTypeDetail.value);//车辆类型
				vehicle.setCsys(strColorDetail.value);//车身颜色
				vehicle.setClsbdh(strBodyNo.value);//车辆识别代号
				vehicle.setFdjh(strEngineNo.value);//发动机号
				vehicle.setJdcsyr(strOwnerName.value);//机动车所有人
				vehicle.setSfzjzl("A");//身份证件种类
				vehicle.setSfzh(strCertNo.value);//身份证号
				vehicle.setZzdzxz(strAddr.value);//暂住地址详址
				vehicle.setDjzzxz(strAddr.value);//登记住址详址
				vehicle.setLxfs(strTel.value);//联系方式
				vehicle.setCcdjrq(strFirstRegTime.value);//初次登记日期
				vehicle.setJdczt(strVehicleStatDetail.value);//机动车状态
				vehicle.setZzxz("");//住址详址
				vehicle.setZzdzqh("");//暂住地址区划
				vehicle.setZzqh("");//暂住区划
				vehicle.setDjzsbh("");//登记证书编号
				vehicle.setHphm(cphid);//号牌号码
				vehicle.setHpzl(hpzl);//号牌种类
				vehicle.setZzg("");//制造国
				vehicle.setZzamc("");//制造厂名称
				vehicle.setGcjk("");//国产/进口
				vehicle.setClpp2("");//车辆品牌2
				vehicle.setZzzjlzh("");//暂住证居留证号
				vehicle.setZrrq("");//转入日期
				vehicle.setFpjg("");//发牌机关
				vehicle.setFzjg("");//发证机构
				vehicle.setFdjxh("");//发动机型号
				vehicle.setRlzl("");//燃料种类
				vehicle.setPl("");//排量
				vehicle.setGl("");//功率
				vehicle.setCcrq("");//出厂日期
				vehicle.setZzl("");//总质量
				vehicle.setHdzzl("");//核定载质量
				vehicle.setHdzk("");//核定载客
				vehicle.setZqyzl("");//准牵引总质量
			}
		}
		//返回结果
		return vehicle;
	}
	
	/**
	 * 查询全国机动车接口
	 * @param cphid 车牌号码
	 * @param hpzl 车辆类型
	 * @param userName 查询用户姓名
	 * @param identityCardNum  身份证号
	 * @param deptName 部门名称
	 * @throws Exception 
	 * 
	 */
	public static Vehicle queryVehicleOfNationwide(String cphid, String hpzl, String userName, String identityCardNum, String deptName) throws Exception{
		QueryAdapterSend adpter = null;
		Vehicle vehicle = null;
		if (cphid != null && !"".equals(cphid.trim()) && hpzl != null && !"".equals(hpzl.trim()) 
				&& identityCardNum != null && !"".equals(identityCardNum.trim()) && userName != null && !"".equals(userName.trim()) 
				&& deptName != null && !"".equals(deptName.trim())) {
			String contion = "hphm='" + cphid + "' and hpzl='" + hpzl + "'";//全国的有例子
			//查询
			adpter = new QueryAdapterSend();
			String strRet = adpter.sendQuery("QueryQGCL", contion, identityCardNum, userName, deptName);
			//转换成对象
			vehicle = XmlCreater.xmlToObjOfVehicle(strRet);
		}
		return vehicle;
	}
	
	/**
	 * 查询机动车接口
	 * @param hphm 车牌号码
	 * @param hpzl 车辆类型
	 * @param userName 查询用户姓名
	 * @param identityCardNum  身份证号
	 * @param deptName 部门名称
	 * @throws Exception 
	 * 
	 */
	public static Vehicle queryVehicle(String hphm, String hpzl, String userName, String identityCardNum, String deptName) throws Exception{
		Vehicle vehicle = null;
		if(hphm != null && !"".equals(hphm.trim()) && hpzl != null && !"".equals(hpzl.trim())
				&& userName != null && !"".equals(userName.trim()) && identityCardNum != null && !"".equals(identityCardNum.trim())
				&& deptName != null && !"".equals(deptName.trim())){
			//获取结果
			if (hphm.indexOf("粤B") != -1 && hphm.length() >= 5) {//查深圳本地
				vehicle = IntefaceUtils.queryVehicleOfShenZhen(hphm, hpzl, userName, identityCardNum, deptName);
			} else if(hphm.indexOf("粤Z") != -1 && hphm.length() >= 5){//港澳车
				vehicle = IntefaceUtils.getVehicleOfGac(hphm, "", userName, identityCardNum, deptName);
			} else if(hphm.length() >= 5) {//查询全国车架管接口
				vehicle = IntefaceUtils.queryVehicleOfNationwide(hphm, hpzl, userName, identityCardNum, deptName);
			}
		}
		return vehicle;
	}
	
	/**
	 * 查询全国驾驶员接口
	 * @param jszh   驾驶证号
	 * @param userName 查询用户名称
	 * @param identityCardNum  身份证号
	 * @param deptName 部门名称
	 * 
	 */
	public static List<Driver> queryDriverOfNationwide(String jszh, String userName, String identityCardNum, String deptName) throws Exception{
		QueryAdapterSend adpter = null;
		List<Driver> driverList = null;
		if (jszh != null && !"".equals(jszh.trim()) 
				&& identityCardNum != null && !"".equals(identityCardNum.trim()) && userName != null && !"".equals(userName.trim()) 
				&& deptName != null && !"".equals(deptName.trim())) {
			String contion = "JSZH='" + jszh + "'";// 全国的有例子
			//查询
			adpter = new QueryAdapterSend();
			String strRet = adpter.sendQuery("QueryQGJSY", contion, identityCardNum, userName, deptName);
			//转换成对象
			driverList = XmlCreater.xmlToObjOfDriver(strRet);
		}
		return driverList;
	}
	
	/**
	 * 根据查询条件查询全省漫游车辆
	 * @param hphm
	 * @param hpzl
	 * @param kssj
	 * @param jzsj
	 * @param address
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	public static List<VehPassrec> queryRoamOrbits(String hphm, String hpzl, String kssj, String jzsj, String address) throws Exception{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(QueryVehPassrecService.class);
		
		//header
		AddSoapHeader ash = new AddSoapHeader();
		List list = new ArrayList();
		list.add(ash);
		factory.setOutInterceptors(list);
		//factory.getOutInterceptors().add(new LoggingOutInterceptor());
		//factory.getInInterceptors().add(new LoggingInInterceptor());
		
		//地址
		factory.setAddress(address);
		
		//条件
		QueryVehPassrecEntity queryConditions = new QueryVehPassrecEntity();
		queryConditions.setHphm(hphm);
		queryConditions.setHpzl(hpzl);
		queryConditions.setKssj(kssj);
		queryConditions.setJssj(jzsj);
		
		//调取
		QueryVehPassrecService service = (QueryVehPassrecService) factory.create();
		CommonUtils.setCxfTimeOut(service, 150, 150);//超时设置
		return service.queryVehPassrec(queryConditions);
	}
	
	/**
	 * 根据查询条件查询全省漫游车辆
	 * @param hphm
	 * @param hpzl
	 * @param kssj
	 * @param jzsj
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public static List<VehPassrec> queryRoamOrbitsOfOld(String hphm, String hpzl, String kssj, String jzsj, String city, String address) throws Exception{
		//结果集合
		List<VehPassrec> vehList = null;
		
		//封装
		String str_xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><head><systemtype>01</systemtype >" 
						+ "<businesstype>01</businesstype></head><body><data><kssj>"
						+ kssj + "</kssj><jssj>" + jzsj + "</jssj><gcxh></gcxh><kdbh></kdbh>"
						+ "<fxbh></fxbh><hpzl>" + hpzl + "</hpzl><hphm>" + hphm + "</hphm><hpys></hpys><wpc>0</wpc></data></body></root>";
		
		// 连接
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(InAccess.class);
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		
		factory.setAddress(address);
		InAccess service = (InAccess) factory.create();
		// 调用服务
		String result = service.executes("01", "01", "hello,world", str_xml);
		//解析结果
		vehList = XmlCreater.oldRoamOrbitToObject(city, result);
		//返回结果
		return vehList;
	}
	
	/**
	 * 港澳车
	 */
	public static Vehicle getVehicleOfGac(String szhphm, String gahphm, String userName, String identityCardNum, String deptName) throws Exception{
		Vehicle vehicle = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		JaxWsDynamicClientFactory jwcxf = JaxWsDynamicClientFactory.newInstance();
		org.apache.cxf.endpoint.Client client = jwcxf.createClient(Config.getInstance().getGacjgWebservice());
		Thread.currentThread().setContextClassLoader(cl);
		//封装参数
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><QueryCondition><ndhp>" + szhphm.replace("粤Z", "粤Z.") + "</ndhp><gahp>" + gahphm + "</gahp></QueryCondition></root>";
		//调用方法
		Object[] objects = client.invoke("queryObjectOut", new Object[]{"11", "D8CD1556D7295DB8C00F58333AADAE929D4434EEDD678C650891CC07DBDC68E9", "11C01", xml});
		//解析
		vehicle = XmlCreater.gacxmlToObjOfVehicle(objects[0].toString());
	    //返回
		return vehicle;
	}
	
	/**
	 * 发送xml文件给PGIS
	 * @param ip 部署PGIS系统的机器IP
	 * @param port PGIS系统socket端口号
	 * @param file 需要发送的文件
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendXmlToPGIS(String ip, int port, File file) {
		String result = "1";
		try{
			//文件长度，限制文件长度
			byte[] mybytearray = new byte [(int)file.length()];
	        if((int)file.length() > 10000) {
	        	result = "0";
	  		} else {
	  			//与Pgis建立socket连接
			    Socket sock = new Socket(ip, port); 
			    try{
			    	//先测试能否与远端建立连接
			    	sock.sendUrgentData(0xFF);//判断是否连接上
			    	
			    	// 先发送包长度
					OutputStream os = sock.getOutputStream();
					byte[] len_buf = new byte[4];
					len_buf = intToByteArray((int) file.length() + 4);
					os.write(len_buf);

					// 再发送 包内容
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					bis.read(mybytearray, 0, mybytearray.length);
					os.write(mybytearray, 0, mybytearray.length);

					//关闭
					os.flush();
					sock.close();
			    } catch(Exception e){
			    	result = "0";
			    }
	  		}
		} catch(Exception e){
			result = "0";
	    } finally{
	    	return result;
	    }
	}
	
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}
	
	/**
	 * 给省厅发送联动布控信息
	 * @param dispatched
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendDispatched(Dispatched dispatched) {
		String result = "1";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
			bean.setAddress(Config.getInstance().getStInterface());
			bean.setServiceClass(BusinessService.class);
			bean.getInInterceptors().add(new LoggingInInterceptor());
			bean.getInInterceptors().add(new LoggingOutInterceptor());
			BusinessService businessService = (BusinessService) bean.create();
			
			//封装数据准备 - 间隔符改为 ','
			String bkfw = dispatched.getBkfw()!=null ? dispatched.getBkfw().replace(";", ","):"";
			//封装
			StringBuffer str = new StringBuffer("<Parameters>");
			str.append("<InfoSet>").
			append("<Field>").append("<Name>").append("BKXH").append("</Name>").append("<Value>").append(dispatched.getBkxh() != null ? dispatched.getBkxh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPHM").append("</Name>").append("<Value>").append(dispatched.getHphm() != null ? dispatched.getHphm().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPZL").append("</Name>").append("<Value>").append(dispatched.getHpzl() != null ? dispatched.getHpzl().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKDL").append("</Name>").append("<Value>").append(dispatched.getBkdl() != null ? dispatched.getBkdl().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKLB").append("</Name>").append("<Value>").append(dispatched.getBjlx() != null ? dispatched.getBjlx().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SFJTP").append("</Name>").append("<Value>").append(((dispatched.getSfjtp()!=null) && (dispatched.getSfjtp()!="")) ? dispatched.getSfjtp().trim():"0").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKQSSJ").append("</Name>").append("<Value>").append(dispatched.getBkqssj() != null ? dateFormat.format(dispatched.getBkqssj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKJZSJ").append("</Name>").append("<Value>").append(dispatched.getBkjzsj() != null ? dateFormat.format(dispatched.getBkjzsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("JYAQ").append("</Name>").append("<Value>").append(dispatched.getJyaq() != null ? dispatched.getJyaq().trim():"").append("</Value>").append("</Field>").
			//原先 bkfwlx - 2联动   dispatched.getBkfwlx() != null ? dispatched.getBkfwlx().trim():""
			append("<Field>").append("<Name>").append("BKFWLX").append("</Name>").append("<Value>").append("2").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKFW").append("</Name>").append("<Value>").append(bkfw).append("</Value>").append("</Field>").
			//原先 bkjb - 2二级   ((dispatched.getBkjb()!=null) && (dispatched.getBkjb()!="")) ? dispatched.getBkjb().trim():"2"
			append("<Field>").append("<Name>").append("BKJB").append("</Name>").append("<Value>").append("2").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SQSB").append("</Name>").append("<Value>").append(dispatched.getSqsb() != null ? dispatched.getSqsb().trim():"").append("</Value>").append("</Field>").
			//原先 bjya - ((dispatched.getBjya()!=null) && (dispatched.getBjya()!="")) ? dispatched.getBjya().trim():("1".equals(dispatched.getBkdl())?"1":"0")
			append("<Field>").append("<Name>").append("BJYA").append("</Name>").append("<Value>").append("1").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJFS").append("</Name>").append("<Value>").append(dispatched.getBjfs() != null ? dispatched.getBjfs().trim():("1".equals(dispatched.getBkdl())?(dispatched.getDxjshm() != null ? "1101":"1100"):(dispatched.getDxjshm() != null ? "0001":"0000"))).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("DXJSHM").append("</Name>").append("<Value>").append(dispatched.getDxjshm() != null ? dispatched.getDxjshm().trim():"").append("</Value>").append("</Field>").
			//原先 dispatched.getLar() != null ? dispatched.getLar().trim():""
			append("<Field>").append("<Name>").append("LAR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			//原先 dispatched.getLadw() != null ? dispatched.getLadw().trim():""
			append("<Field>").append("<Name>").append("LADW").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			//原先 dispatched.getLadwlxdh() != null ? dispatched.getLadwlxdh().trim():""
			append("<Field>").append("<Name>").append("LADWLXDH").append("</Name>").append("<Value>").append("075584450516").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLPP").append("</Name>").append("<Value>").append(dispatched.getClpp() != null ? dispatched.getClpp().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPYS").append("</Name>").append("<Value>").append(dispatched.getHpzl() != null ? CommonUtils.hpzlToCplxOfGb(dispatched.getHpzl().trim()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLXH").append("</Name>").append("<Value>").append(dispatched.getClxh() != null ? dispatched.getClxh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLLX").append("</Name>").append("<Value>").append(dispatched.getCllx() != null ? dispatched.getCllx().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CSYS").append("</Name>").append("<Value>").append(dispatched.getCsys() != null ? dispatched.getCsys().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLSBDH").append("</Name>").append("<Value>").append(dispatched.getClsbdh() != null ? dispatched.getClsbdh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("FDJH").append("</Name>").append("<Value>").append(dispatched.getFdjh() != null ? dispatched.getFdjh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLTZ").append("</Name>").append("<Value>").append(dispatched.getCltz() != null ? dispatched.getCltz().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLSYR").append("</Name>").append("<Value>").append(dispatched.getClsyr() != null ? dispatched.getClsyr().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SYRLXDH").append("</Name>").append("<Value>").append(dispatched.getSyrlxdh() != null ? dispatched.getSyrlxdh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SYRXXDZ").append("</Name>").append("<Value>").append(dispatched.getSyrxxdz() != null ? dispatched.getSyrxxdz().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKRMC").append("</Name>").append("<Value>").append("深圳联动布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKJG").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKJGMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			//原先  dispatched.getBkjglxdh()
			append("<Field>").append("<Name>").append("BKJGLXDH").append("</Name>").append("<Value>").append((dispatched.getBkjglxdh()!=null)&&(dispatched.getBkjglxdh()!="") ? dispatched.getBkjglxdh():"075584450516").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKSJ").append("</Name>").append("<Value>").append(dispatched.getBksj() != null ? dateFormat.format(dispatched.getBksj()):"").append("</Value>").append("</Field>").
			
			append("<Field>").append("<Name>").append("CZR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRMC").append("</Name>").append("<Value>").append("深圳市联动布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRDW").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRDWMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZSJ").append("</Name>").append("<Value>").append(dateFormat.format(new Date())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("MS").append("</Name>").append("<Value>").append("同意布控！").append("</Value>").append("</Field>").
			
			append("<Field>").append("<Name>").append("BKAJJB").append("</Name>").append("<Value>").append("1").append("</Value>").append("</Field>").
			append("</InfoSet>").
			append("</Parameters>");
			
			//调用 
			String returnStr = businessService.execute("05", "01", "c700aa07-678a-473e-aeb9-67c630e33ed7", str.toString());
			
			//解析
			Document document = (Document) DocumentHelper.parseText(returnStr);// 字符串转换为document文件。
			Element resultSet = document.getRootElement();
			Element returnValue = (Element) resultSet.selectNodes("ReturnValue").get(0);
			result = returnValue.getText();
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	/**
	 * 给省厅发送联动撤控信息
	 * @param dispatched
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendWithdraw(Dispatched dispatched, Withdraw withdraw) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = "1";
		try {
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
			bean.setAddress(Config.getInstance().getStInterface());
			bean.setServiceClass(BusinessService.class);
			bean.getInInterceptors().add(new LoggingInInterceptor());
			bean.getInInterceptors().add(new LoggingOutInterceptor());
			BusinessService businessService = (BusinessService) bean.create();
			
			//封装
			StringBuffer str = new StringBuffer("<Parameters>");
			str.append("<InfoSet>").
			append("<Field>").append("<Name>").append("BKXH").append("</Name>").append("<Value>").append(dispatched.getBkxh() != null ? dispatched.getBkxh().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQRMC").append("</Name>").append("<Value>").append("深圳联动布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQDW").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQDWMC").append("</Name>").append("<Value>").append("深圳公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CXSQSJ").append("</Name>").append("<Value>").append(withdraw.getCxsqsj() != null ? dateFormat.format(withdraw.getCxsqsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CKYYDM").append("</Name>").append("<Value>").append(withdraw.getCkyydm() != null ? withdraw.getCkyydm().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CKYYMS").append("</Name>").append("<Value>").append(withdraw.getCkyyms() != null ? withdraw.getCkyyms().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKSJ").append("</Name>").append("<Value>").append(dispatched.getBksj() != null ? dateFormat.format(dispatched.getBksj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRMC").append("</Name>").append("<Value>").append("深圳联动布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRDW").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZRDWMC").append("</Name>").append("<Value>").append("深圳公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZSJ").append("</Name>").append("<Value>").append(dateFormat.format(new Date())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("MS").append("</Name>").append("<Value>").append("同意撤控！").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKAJJB").append("</Name>").append("<Value>").append("1").append("</Value>").append("</Field>").
			append("</InfoSet>").
			append("</Parameters>");
			
			//调用
			String returnStr = businessService.execute( "05", "02", "c700aa07-678a-473e-aeb9-67c630e33ed7", str.toString());
			
			//解析
			Document document = (Document) DocumentHelper.parseText(returnStr);// 字符串转换为document文件。
			Element resultSet = document.getRootElement();
			Element returnValue = (Element) resultSet.selectNodes("ReturnValue").get(0);
			result = returnValue.getText();
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	/**
	 * 给省厅发送预警签收信息
	 * @param dispatched
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendEWRecieve(EWRecieve eWRecieve) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = "1";
		try {
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
			bean.setAddress(Config.getInstance().getStInterface());
			bean.setServiceClass(BusinessService.class);
			bean.getInInterceptors().add(new LoggingInInterceptor());
			bean.getInInterceptors().add(new LoggingOutInterceptor());
			BusinessService businessService = (BusinessService) bean.create();
			
			//解析图片
			String tpid1 = "";
			String tpid2 = "";
			List<String> tpidList = new ArrayList<String>();
			if(eWRecieve.getTpid() != null && !"".equals(eWRecieve.getTpid().trim())){
				String[] pic = eWRecieve.getTpid().split(",");
				if(pic.length >= 1){
					tpid1 = pic[0];
					tpidList.add(tpid1);
				}
				if(pic.length > 1){
					tpid2 = pic[1];
					tpidList.add(tpid2);
				}
			}
			
			//调用图片
			List<String> urlList = PicService.getPicUrl(tpidList);
			
			//封装
			StringBuffer str = new StringBuffer("<Parameters>");
			str.append("<RequestCode>").append("1").append("</RequestCode>").
			append("<InfoSet>").
			append("<Field>").append("<Name>").append("BJXH").append("</Name>").append("<Value>").append("440300000000" + Tools.getXh(eWRecieve.getQsid().toString())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDL").append("</Name>").append("<Value>").append(eWRecieve.getBjdl() != null ? eWRecieve.getBjdl().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJLX").append("</Name>").append("<Value>").append(eWRecieve.getBjlx() != null ? eWRecieve.getBjlx().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKXH").append("</Name>").append("<Value>").append(eWRecieve.getDispatched() != null ? (eWRecieve.getDispatched().getBkxh() != null ? eWRecieve.getDispatched().getBkxh().trim():""):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJSJ").append("</Name>").append("<Value>").append(eWRecieve.getBjsj() != null ? dateFormat.format(eWRecieve.getBjsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWDM").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWLXDH").append("</Name>").append("<Value>").append("110").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPHM").append("</Name>").append("<Value>").append(eWRecieve.getHphm() != null ? eWRecieve.getHphm().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPZL").append("</Name>").append("<Value>").append(eWRecieve.getHpzl() != null ? eWRecieve.getHpzl().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("GCXH").append("</Name>").append("<Value>").append("440300000000" + Tools.getXh(eWRecieve.getQsid().toString())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("GCSJ").append("</Name>").append("<Value>").append(eWRecieve.getTgsj() != null ? dateFormat.format(eWRecieve.getTgsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("KDBH").append("</Name>").append("<Value>").append(eWRecieve.getJcdid() != null ? eWRecieve.getJcdid().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("KDMC").append("</Name>").append("<Value>").append(eWRecieve.getJcdmc() != null ? eWRecieve.getJcdmc().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("FXBH").append("</Name>").append("<Value>").append(eWRecieve.getJcdid() != null ? eWRecieve.getJcdid().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("FXMC").append("</Name>").append("<Value>").append(eWRecieve.getJcdmc() != null ? eWRecieve.getJcdmc().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLLX").append("</Name>").append("<Value>").append(eWRecieve.getDispatched() != null ? (eWRecieve.getDispatched().getCllx() != null ? eWRecieve.getDispatched().getCllx().trim():""):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLSD").append("</Name>").append("<Value>").append("0").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPYS").append("</Name>").append("<Value>").append(eWRecieve.getHpzl() != null ? CommonUtils.hpzlToCplxOfGb(eWRecieve.getHpzl().trim()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CWHPHM").append("</Name>").append("<Value>").append("").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CWHPYS").append("</Name>").append("<Value>").append("").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("HPYZ").append("</Name>").append("<Value>").append("1").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CDBH").append("</Name>").append("<Value>").append(eWRecieve.getCdid() != null ? eWRecieve.getCdid().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SBBH").append("</Name>").append("<Value>").append(eWRecieve.getJcdid() != null ? eWRecieve.getJcdid().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLWX").append("</Name>").append("<Value>").append("").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CSYS").append("</Name>").append("<Value>").append(eWRecieve.getDispatched() != null ? (eWRecieve.getDispatched().getCsys() != null ? eWRecieve.getDispatched().getCsys().trim():""):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("TP1").append("</Name>").append("<Value>").append(urlList != null && urlList.size() >= 1 ? urlList.get(0):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("TP2").append("</Name>").append("<Value>").append(urlList != null && urlList.size() > 1 ? urlList.get(1):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("TP3").append("</Name>").append("<Value>").append("").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRR").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRRMC").append("</Name>").append("<Value>").append("深圳市布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRDWDM").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRDWDMMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRDWLXDH").append("</Name>").append("<Value>").append(eWRecieve.getQrdwlxdh() != null ? eWRecieve.getQrdwlxdh().trim():"075584450516").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRSJ").append("</Name>").append("<Value>").append(eWRecieve.getQrsj() != null ? dateFormat.format(eWRecieve.getQrsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRZT").append("</Name>").append("<Value>").append(eWRecieve.getQrzt() != null ? eWRecieve.getQrzt().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("QRJG").append("</Name>").append("<Value>").append(eWRecieve.getQrjg() != null ? eWRecieve.getQrjg().trim():"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("JYLJTJ").append("</Name>").append("<Value>").append("0").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("XXLY").append("</Name>").append("<Value>").append("2").append("</Value>").append("</Field>").
			append("</InfoSet>").
			append("</Parameters>");
			
			//调用
			String returnStr = businessService.feedBack(str.toString());
			
			//解析
			Document document = (Document) DocumentHelper.parseText(returnStr);// 字符串转换为document文件。
			Element resultSet = document.getRootElement();
			Element returnValue = (Element) resultSet.selectNodes("ReturnValue").get(0);
			result = returnValue.getText();
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	/**
	 * 给省厅发送预警反馈信息
	 * @param dispatched
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendInstructionSignForFk(InstructionSign instructionSign) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = "1";
		try {
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
			bean.setAddress(Config.getInstance().getStInterface());
			bean.setServiceClass(BusinessService.class);
			bean.getInInterceptors().add(new LoggingInInterceptor());
			bean.getInInterceptors().add(new LoggingOutInterceptor());
			BusinessService businessService = (BusinessService) bean.create();
			
			//数据
			EWRecieve eWRecieve = instructionSign.getInstruction().getEwrecieve();
			
			//封装
			StringBuffer str = new StringBuffer("<Parameters>");
			str.append("<RequestCode>").append("2").append("</RequestCode>").
			append("<InfoSet>").
			append("<Field>").append("<Name>").append("FKBH").append("</Name>").append("<Value>").append("440300000000" + Tools.getXh(instructionSign.getId().toString())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BKXH").append("</Name>").append("<Value>").append(eWRecieve.getDispatched() != null ? (eWRecieve.getDispatched().getBkxh() != null ? eWRecieve.getDispatched().getBkxh().trim():""):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJXH").append("</Name>").append("<Value>").append("440300000000" + Tools.getXh(eWRecieve.getQsid().toString())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("GCXH").append("</Name>").append("<Value>").append("440300000000" + Tools.getXh(eWRecieve.getQsid().toString())).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("LRRJH").append("</Name>").append("<Value>").append("sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("LRRMC").append("</Name>").append("<Value>").append("深圳联动布控人员").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("LRDWDM").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("LRDWMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("LRSJ").append("</Name>").append("<Value>").append(instructionSign.getFksj() != null ? dateFormat.format(instructionSign.getFksj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("DDR").append("</Name>").append("<Value>").append(instructionSign.getDdr() != null ? instructionSign.getDdr().trim():"sz0000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("XBR").append("</Name>").append("<Value>").append(instructionSign.getXbr() != null ? instructionSign.getXbr().trim():"sz0001").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("SFLJ").append("</Name>").append("<Value>").append(instructionSign.getSflj() != null ? instructionSign.getSflj().trim():"0").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CZJG").append("</Name>").append("<Value>").append(instructionSign.getCzjg() != null ? instructionSign.getCzjg().trim():"1").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("WLJDYY").append("</Name>").append("<Value>").append(instructionSign.getWljdyy() != null ? instructionSign.getWljdyy().trim():"9").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("CLGC").append("</Name>").append("<Value>").append(instructionSign.getFknr().trim()).append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJSJ").append("</Name>").append("<Value>").append(eWRecieve.getBjsj() != null ? dateFormat.format(eWRecieve.getBjsj()):"").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWDM").append("</Name>").append("<Value>").append("440300000000").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWMC").append("</Name>").append("<Value>").append("深圳市公安局").append("</Value>").append("</Field>").
			append("<Field>").append("<Name>").append("BJDWLXDH").append("</Name>").append("<Value>").append("110").append("</Value>").append("</Field>").
			append("</InfoSet>").
			append("</Parameters>");
			
			//调用
			String returnStr = businessService.feedBack(str.toString());
			
			//解析
			Document document = (Document) DocumentHelper.parseText(returnStr);// 字符串转换为document文件。
			Element resultSet = document.getRootElement();
			Element returnValue = (Element) resultSet.selectNodes("ReturnValue").get(0);
			result = returnValue.getText();
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(sendXmlToPGIS("10.42.127.70", 8900, new File("D:\\xml\\yaxx_snd_gb2312.xml")));;
//			System.out.println(sendMessage("15218722860", "短信调用测试！"));
//			System.out.println(getPic2("20160722020042002050180311_40"));
//			String res = getPic2("20160312091053023060670631_40");
//			System.out.println(res);
//			Vehicle vehicle = queryVehicleOfNationwide("粤B17B30", "02", "于峰", "420500197408110017", "440300010103");
//			System.out.println(vehicle.getLxfs() + vehicle.getHphm() + "," + vehicle.getHpzl() + "," + vehicle.getSfzh() + "," + vehicle.getFdjh() + "," + vehicle.getClsbdh());
//			queryDriverOfNationwide("420500197408110017", "于峰", "420500197408110017", "440300010103");
//			Vehicle vehicle2 = queryVehicleOfShenZhen("粤B123456", "02", "于峰", "420500197408110017", "440300010103");
//			System.out.println(vehicle2.getHphm() + "," + vehicle2.getHpzl() + "," + vehicle2.getSfzh());
//			getVehicleOfGac("粤Z.DR97港", "02", "于峰", "420500197408110017", "440300010103");
//			System.out.println("粤ZBW31港".replace("粤Z", "粤Z."));
			List<VehPassrec> ss = IntefaceUtils.queryRoamOrbits("", "", "2017-09-24 00:00:00", "2017-09-24 12:00:00", "http://10.42.127.70:9080/jcbk/service/QueryVehPassrecServer");//新接口
			for(int a=0; a < ss.size();a++){
				VehPassrec veh = ss.get(a);
				System.out.println(veh.getHphm()  +  "==" + veh.getTp1() + "==" + veh.getTp2());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}