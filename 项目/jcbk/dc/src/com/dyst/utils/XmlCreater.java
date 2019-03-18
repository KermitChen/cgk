package com.dyst.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.*;

import com.dyst.chariotesttube.entities.Driver;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.Ya;
import com.dyst.vehicleQuery.dto.RoamOrbit;
import com.sunshine.monitor.system.ws.server.VehPassrec;

/**
 * 解析xml
 * @author cgk
 * 方法：
 *      1.解析短信返回结果，返回错误编码
 * 		2.将xml格式的机动车信息转换成对象
 *      3.将xml格式的驾驶员信息转换成对象
 *      4.将xml格式的漫游轨迹转换成对象列表
 */

public class XmlCreater {
	/**
	 * 解析短信返回结果，返回错误编码
	 * @param xml
	 * @return String
	 * @throws Exception 
	 * 错误码
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
	public static String xmlToStringOfMsg(String xml) throws Exception{
		Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
		Element root = document.getRootElement();
		//Element head = (Element) root.selectNodes("head").get(0);
		Element body = (Element) root.selectNodes("body").get(0);
		String code = body.element("code").getText();
		//String message = body.element("message").getText();
		return code;
	}
	
	/**
	 * 将xml格式的机动车信息转换成对象
	 * @param xml
	 * @return Vehicle
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static Vehicle xmlToObjOfVehicle(String xml) throws Exception{
		Vehicle vehicle = null;
		if(xml != null && !"".equals(xml.trim())){
			Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
			Element root = document.getRootElement();
			Element Method = (Element) root.selectNodes("Method").get(0);
			Element Items = (Element) Method.selectNodes("Items").get(0);
			Element Item = ((Element) Items.selectNodes("Item").get(0));
			Element value = ((Element) Item.selectNodes("Value").get(0));
			List Row = (List) value.selectNodes("Row");// 内容节点
			
			if (Row.size() >= 3) {//总共三行
				//获取数据
				List data = ((Element) Row.get(2)).selectNodes("Data");// 数据集//第三行数据
				
				//封装数据
				vehicle = new Vehicle();
				vehicle.setZzxz(((Element) data.get(0)).getText());//住址详址
				vehicle.setSfzh(((Element) data.get(1)).getText());//身份证号
				vehicle.setZzdzqh(((Element) data.get(2)).getText());//暂住地址区划
				vehicle.setZzdzxz(((Element) data.get(3)).getText());//暂住地址详址
				vehicle.setSfzjzl(((Element) data.get(4)).getText());//身份证件种类
				vehicle.setCllx(((Element) data.get(5)).getText());//车辆类型
				vehicle.setFdjh(((Element) data.get(6)).getText());//发动机号
				vehicle.setCsys(((Element) data.get(7)).getText());//车身颜色
				vehicle.setZzqh(((Element) data.get(8)).getText());//暂住区划
				vehicle.setDjzsbh(((Element) data.get(9)).getText());//登记证书编号
				vehicle.setHphm(((Element) data.get(10)).getText());//号牌号码
				vehicle.setHpzl(((Element) data.get(11)).getText());//号牌种类
				vehicle.setZzg(((Element) data.get(12)).getText());//制造国
				vehicle.setZzamc(((Element) data.get(13)).getText());//制造厂名称
				vehicle.setGcjk(((Element) data.get(14)).getText());//国产/进口
				vehicle.setClpp1(((Element) data.get(15)).getText());//车辆品牌1
				vehicle.setClpp2(((Element) data.get(16)).getText());//车辆品牌2
				vehicle.setClxh(((Element) data.get(17)).getText());//车辆型号
				vehicle.setClsbdh(((Element) data.get(18)).getText());//车辆识别代号
				vehicle.setJdcsyr(((Element) data.get(19)).getText());//机动车所有人
				vehicle.setZzzjlzh(((Element) data.get(20)).getText());//暂住证居留证号
				vehicle.setCcdjrq(((Element) data.get(21)).getText());//初次登记日期
				vehicle.setZrrq(((Element) data.get(22)).getText());//转入日期
				vehicle.setFpjg(((Element) data.get(23)).getText());//发牌机关
				vehicle.setJdczt(((Element) data.get(24)).getText());//机动车状态
				vehicle.setFzjg(((Element) data.get(25)).getText());//发证机构
				vehicle.setDjzzxz(((Element) data.get(26)).getText());//登记住址详址
				vehicle.setLxfs(((Element) data.get(27)).getText());//联系方式
				vehicle.setFdjxh(((Element) data.get(28)).getText());//发动机型号
				vehicle.setRlzl(((Element) data.get(29)).getText());//燃料种类
				vehicle.setPl(((Element) data.get(30)).getText());//排量
				vehicle.setGl(((Element) data.get(31)).getText());//功率
				vehicle.setCcrq(((Element) data.get(32)).getText());//出厂日期
				vehicle.setZzl(((Element) data.get(33)).getText());//总质量
				vehicle.setHdzzl(((Element) data.get(34)).getText());//核定载质量
				vehicle.setHdzk(((Element) data.get(35)).getText());//核定载客
				vehicle.setZqyzl(((Element) data.get(36)).getText());//准牵引总质量
			}
		}
		return vehicle;
	}
	
	/**
	 * 将xml格式的港澳车机动车信息转换成对象
	 * @param xml
	 * @return Vehicle
	 * @throws Exception 
	 */
	public static Vehicle gacxmlToObjOfVehicle(String xml) throws Exception{
		Vehicle vehicle = null;
		Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
		Element root = document.getRootElement();
		
		Element head = (Element) root.selectNodes("head").get(0);
		String code = head.element("code").getText();
//		String message = head.element("message").getText();
		String rownum = head.element("rownum").getText();
		//获取结果
		if (code != null && "1".equals(code.trim()) && rownum != null && Integer.parseInt(rownum) > 0) {//总共三行
			Element body = (Element) root.selectNodes("body").get(0);
			Element gac = ((Element) body.selectNodes("gac").get(0));
			
			//封装数据
			vehicle = new Vehicle();
			vehicle.setZzxz(((Element) gac.selectNodes("lxdz").get(0)).getText());//住址详址
			vehicle.setSfzh("");//身份证号
			vehicle.setZzdzqh(((Element) gac.selectNodes("zsxzqh").get(0)).getText());//暂住地址区划
			vehicle.setZzdzxz("");//暂住地址详址
			vehicle.setSfzjzl("");//身份证件种类
			vehicle.setCllx(((Element) gac.selectNodes("cllx").get(0)).getText());//车辆类型
			vehicle.setFdjh(((Element) gac.selectNodes("fdjh").get(0)).getText());//发动机号
			vehicle.setCsys(((Element) gac.selectNodes("csys").get(0)).getText());//车身颜色
			vehicle.setZzqh(((Element) gac.selectNodes("zsxzqh").get(0)).getText());//暂住区划
			vehicle.setDjzsbh("");//登记证书编号
			vehicle.setHphm(((Element) gac.selectNodes("ndhp").get(0)).getText().replace("粤Z.", "粤Z") + "/" + ((Element) gac.selectNodes("gahp").get(0)).getText());//号牌号码
			vehicle.setHpzl("");//号牌种类
			vehicle.setZzg("");//制造国
			vehicle.setZzamc("");//制造厂名称
			vehicle.setGcjk("");//国产/进口
			vehicle.setClpp1(((Element) gac.selectNodes("cpxh").get(0)).getText());//车辆品牌1
			vehicle.setClpp2("");//车辆品牌2
			vehicle.setClxh("");//车辆型号
			vehicle.setClsbdh(((Element) gac.selectNodes("clsbdh").get(0)).getText());//车辆识别代号
			vehicle.setJdcsyr(((Element) gac.selectNodes("czdjmc").get(0)).getText());//机动车所有人
			vehicle.setZzzjlzh("");//暂住证居留证号
			vehicle.setCcdjrq(((Element) gac.selectNodes("djrq").get(0)).getText());//初次登记日期
			vehicle.setZrrq("");//转入日期
			vehicle.setFpjg(((Element) gac.selectNodes("zsxzqh").get(0)).getText());//发牌机关
			vehicle.setJdczt("");//机动车状态
			vehicle.setFzjg(((Element) gac.selectNodes("fzjg").get(0)).getText());//发证机构
			vehicle.setDjzzxz(((Element) gac.selectNodes("lxdz").get(0)).getText());//登记住址详址
			vehicle.setLxfs(((Element) gac.selectNodes("lxdh").get(0)).getText());//联系方式
			vehicle.setFdjxh("");//发动机型号
			vehicle.setRlzl("");//燃料种类
			vehicle.setPl("");//排量
			vehicle.setGl("");//功率
			vehicle.setCcrq(((Element) gac.selectNodes("ccnf").get(0)).getText());//出厂日期
			vehicle.setZzl("");//总质量
			vehicle.setHdzzl("");//核定载质量
			vehicle.setHdzk("");//核定载客
			vehicle.setZqyzl("");//准牵引总质量
		}
		return vehicle;
	}
	
	/**
	 * 将xml格式的驾驶员信息转换成对象
	 * @param xml
	 * @return List<Driver>
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static List<Driver> xmlToObjOfDriver(String xml) throws Exception{
		Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
		Element root = document.getRootElement();
		Element Method = (Element) root.selectNodes("Method").get(0);
		Element Items = (Element) Method.selectNodes("Items").get(0);
		Element Item = ((Element) Items.selectNodes("Item").get(0));
		Element value = ((Element) Item.selectNodes("Value").get(0));
		List Row = (List) value.selectNodes("Row");// 内容节点
		List<Driver> driverList = null;
		
		if (Row.size() >= 3) {//总共三行
			//获取数据
			Driver driver = null;
			driverList = new ArrayList<Driver>();
			for(int i=2;i < Row.size();i++){
				List data = ((Element) Row.get(i)).selectNodes("Data");// 数据集//第三行数据
				
				//封装
				driver = new Driver();
				driver.setId(i-1);
				driver.setXm(((Element) data.get(0)).getText());//姓名
				driver.setXb(((Element) data.get(1)).getText());//性别
				driver.setCsrq(((Element) data.get(2)).getText());//出生日期
				driver.setJszzl(((Element) data.get(3)).getText());//驾驶证种类
				driver.setJszh(((Element) data.get(4)).getText());//驾驶证号
				driver.setDabh(((Element) data.get(5)).getText());//档案编号
				driver.setDjzzqh(((Element) data.get(6)).getText());//登记住址区划
				driver.setDjzzxz(((Element) data.get(7)).getText());//登记住址详细地址
				driver.setXzzqh(((Element) data.get(8)).getText());//现住址区划
				driver.setXzzxz(((Element) data.get(9)).getText());//现住址详细地址
				driver.setLszzqh(((Element) data.get(10)).getText());
				driver.setLszzxz(((Element) data.get(11)).getText());
				driver.setCclzrq(((Element) data.get(12)).getText());
				driver.setZjcx(((Element) data.get(13)).getText());
				driver.setJszyxqsrq(((Element) data.get(14)).getText());//驾驶证有效起始日期
				driver.setJszyxzzrq(((Element) data.get(15)).getText());//驾驶证有效终止日期
				driver.setFzjg(((Element) data.get(16)).getText());//发证机关
				driver.setJszzt(((Element) data.get(17)).getText());//驾驶证状态
				driver.setJszfzrq(((Element) data.get(18)).getText());//驾驶证发证日期
				driver.setSfzjzl(((Element) data.get(19)).getText());//身份证件种类
				driver.setZjbh(((Element) data.get(20)).getText());//证件编号
				
				//加入list
				driverList.add(driver);
			}
		}
		return driverList;
	}
	
	/**
	 * 将xml格式的漫游轨迹转换成对象列表
	 * @param xml
	 * @return List<RoamOrbit>
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static List<RoamOrbit> xmlToObjOfRoamOrbit(String xml) throws Exception{
		List<RoamOrbit> listM = new ArrayList<RoamOrbit>();
		Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
		Element root = document.getRootElement();
		Element head = (Element) root.selectNodes("head").get(0);
		Element body = (Element) root.selectNodes("body").get(0);//实体数据
		String count = ((Element) head.selectNodes("count").get(0)).getText();
		if(Integer.parseInt(count) > 0){
			List data = (List) body.selectNodes("data");
			RoamOrbit roamOrbit = null;
			for(int i=0;i < data.size();i++){
				Element e = (Element)data.get(i);
				
				//封装
				roamOrbit = new RoamOrbit();
				roamOrbit.setHphm( ((Element)e.selectNodes("hphm").get(0)).getText() );//
				roamOrbit.setHpys( ((Element)e.selectNodes("hpys").get(0)).getText() );//
				roamOrbit.setGcsj( ((Element)e.selectNodes("gcsj").get(0)).getText() );//
				roamOrbit.setKdbh( ((Element)e.selectNodes("kdbh").get(0)).getText() );//
				roamOrbit.setKdmc( ((Element)e.selectNodes("kdmc").get(0)).getText() );//
				roamOrbit.setCdbh( ((Element)e.selectNodes("cdbh").get(0)).getText() );//
				roamOrbit.setHpzl( ((Element)e.selectNodes("hpzl").get(0)).getText() );//
				roamOrbit.setGcxh( ((Element)e.selectNodes("gcxh").get(0)).getText() );//
				roamOrbit.setXzqh( ((Element)e.selectNodes("xzqh").get(0)).getText() );//行政区号
				roamOrbit.setTp1( ((Element)e.selectNodes("tp1").get(0)).getText() );//
				roamOrbit.setTp2( ((Element)e.selectNodes("tp2").get(0)).getText() );//
				roamOrbit.setSbbh("");//
				roamOrbit.setSbmc("");//
//				m.setSbbh( ((Element)e.selectNodes("sbbh").get(0)).getText() );//
//				m.setSbmc( ((Element)e.selectNodes("sbmc").get(0)).getText() );//
//				m.setFxbh( ((Element)e.selectNodes("fxbh").get(0)).getText() );//
//				m.setFxmc( ((Element)e.selectNodes("fxmc").get(0)).getText() );//
//				m.setClsd( ((Element)e.selectNodes("clsd").get(0)).getText() );//
				roamOrbit.setFxbh("");//
				roamOrbit.setFxmc("");//
				roamOrbit.setClsd("");//
				roamOrbit.setCllx("");//
				roamOrbit.setTp3("");//
				roamOrbit.setWfbj("");//
				roamOrbit.setRksj("");//
				roamOrbit.setClxs("");//
				roamOrbit.setCwhphm("");//
				roamOrbit.setCwhpys("");//
				roamOrbit.setXszt("");//
				roamOrbit.setClwx("");//
				roamOrbit.setCsys("");//
				
				listM.add(roamOrbit);
			}
		}
		return listM;
	} 
	
	/**
	 * 将xml格式的漫游轨迹转换成对象列表
	 * @param xml
	 * @return List<RoamOrbit>
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static List<VehPassrec> oldRoamOrbitToObject(String city, String xml) throws Exception{
		List<VehPassrec> listM = new ArrayList<VehPassrec>();
		VehPassrec vehPassrec = null;
		
		Document document = (Document) DocumentHelper.parseText(xml);// 字符串转换为document文件。
		Element root = document.getRootElement();
		Element head = (Element) root.selectNodes("head").get(0);
		Element body = (Element) root.selectNodes("body").get(0);//实体数据
		String count = ((Element) head.selectNodes("count").get(0)).getText();
		
		if(Integer.parseInt(count) > 0){
			List data = (List) body.selectNodes("data");
			for(int i=0;i < data.size();i++){
				vehPassrec = new VehPassrec();
				Element e = (Element)data.get(i);
//				vehPassrec.setCity( ((Element)e.selectNodes("xzqh").get(0)).getText() );//行政区号
				vehPassrec.setCity( city );//行政区号
				vehPassrec.setGcxh( ((Element)e.selectNodes("gcxh").get(0)).getText() );//
				vehPassrec.setSbbh( ((Element)e.selectNodes("sbbh").get(0)).getText() );//
				vehPassrec.setSbmc( ((Element)e.selectNodes("sbmc").get(0)).getText() );//
				vehPassrec.setKdbh( ((Element)e.selectNodes("kdbh").get(0)).getText() );//
				vehPassrec.setKdmc( ((Element)e.selectNodes("kdmc").get(0)).getText() );//
				vehPassrec.setFxbh( ((Element)e.selectNodes("fxbh").get(0)).getText() );//
				vehPassrec.setFxmc( ((Element)e.selectNodes("fxmc").get(0)).getText() );//
				vehPassrec.setHpzl( ((Element)e.selectNodes("hpzl").get(0)).getText() );//
				vehPassrec.setHpzlmc( ((Element)e.selectNodes("hpzl").get(0)).getText() );//
				vehPassrec.setHphm( ((Element)e.selectNodes("hphm").get(0)).getText() );//
				vehPassrec.setGcsj( ((Element)e.selectNodes("gcsj").get(0)).getText() );//
				String clsd = ((Element)e.selectNodes("clsd").get(0)).getText();
				vehPassrec.setClsd( clsd != null && !"".equals(clsd)?Long.parseLong(clsd):0 );//
				vehPassrec.setHpys( ((Element)e.selectNodes("hpys").get(0)).getText() );//
				vehPassrec.setHpysmc( ((Element)e.selectNodes("hpys").get(0)).getText() );//
				vehPassrec.setCllx( ((Element)e.selectNodes("cllx").get(0)).getText() );//
				vehPassrec.setTp1( ((Element)e.selectNodes("tp1").get(0)).getText() );//
				vehPassrec.setTp2( ((Element)e.selectNodes("tp2").get(0)).getText() );//
				vehPassrec.setTp3("");//
				vehPassrec.setWfbj("");//
				vehPassrec.setRksj( ((Element)e.selectNodes("rksj").get(0)).getText() );//
				vehPassrec.setCwhphm("");//
				vehPassrec.setCwhpys("");//
				vehPassrec.setXszt("");//
				vehPassrec.setClwx("");//
				vehPassrec.setCsys("");//
				vehPassrec.setCdbh( ((Element)e.selectNodes("cdbh").get(0)).getText() );//
				
				listM.add(vehPassrec);
			}
		}

		return listM;
	}
	
	/**
	 * 预案生成xml并保存
	 */
	@SuppressWarnings("finally")
	public static String yaToXml(String trStr, String ipStr, String cphmStr, String hpzlStr, String tgsjStr, 
			String jcdidStr, String jcdmcStr, String jdStr, String wdStr, String cdidStr, List<Ya> yaList){
		/* 生成xml文件 */
		Document doc = DocumentHelper.createDocument();// 创建文档
		doc.setXMLEncoding("gb2312");// 设置文档编码方式

		// 创建头节点 MSG;
		Element msg = doc.addElement("MSG");

		// 添加MSG的 HD子节点及HD的子节点
		Element hd = msg.addElement("HD");
		Element tr = hd.addElement("TR");
		Element ip = hd.addElement("IP");
		
		// 添加MSG的 BD子节点及BD的子节点
		Element bd = msg.addElement("BD");
		Element cphm = bd.addElement("CPHM");
		Element hpzl = bd.addElement("HPZL");
		Element tgsj = bd.addElement("TGSJ");
		Element jcdid = bd.addElement("JCDID");
		Element jcdmc = bd.addElement("JCDMC");
		Element jd = bd.addElement("JD");
		Element wd = bd.addElement("WD");
		Element cdid = bd.addElement("CDID");
		//预案
		Element cjya = bd.addElement("CJYA");
	    
		//添加 内容
	    tr.setText(trStr);
	    ip.setText(ipStr);
	    
	    //添加body
	    cphm.setText(cphmStr);
	    hpzl.setText(hpzlStr);
	    tgsj.setText(tgsjStr);
	    jcdid.setText(jcdidStr);
	    jcdmc.setText(jcdmcStr);
	    jd.setText(jdStr);
	    wd.setText(wdStr);
	    cdid.setText(cdidStr);
	    
	    //添加预案
	    for(int i=0;i < yaList.size();i++){
	    	Ya ya = yaList.get(i);
	    	//预案元素
	    	Element yaElement = cjya.addElement("YA");
	    	Element kdbh = yaElement.addElement("KDBH");
	    	Element kdmc = yaElement.addElement("KDMC");
	    	Element kdjd = yaElement.addElement("JD");
	    	Element kdwd = yaElement.addElement("WD");
	    	Element yjsj = yaElement.addElement("YJSJ");
	    	Element czya = yaElement.addElement("CZYA");
	    	Element zbry = yaElement.addElement("ZBRY");
	    	Element lxfs = yaElement.addElement("LXFS");
	    	//预案内容
	    	kdbh.setText(ya.getKdbh());
	    	kdmc.setText(ya.getKdmc());
	    	kdjd.setText(ya.getJd());
	    	kdwd.setText(ya.getWd());
	    	yjsj.setText(ya.getYjsj());
	    	czya.setText(ya.getCzya());
	    	zbry.setText(ya.getZbry());
	    	lxfs.setText(ya.getLxfs());
	    }

		//保存xml文件
	    XMLWriter writer = null;
	    File myFile = null;
	    String filePath = null;
		try {
			//创建文件
			myFile = new File(Config.getInstance().getLogFolder() + File.separator + "yaxx_xml_" + new Date().getTime() + ".xml");
			
			//写文件
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			writer = new XMLWriter(new FileOutputStream(myFile), format);
			writer.write(doc);
			writer.flush();
			writer.close();
			
			//获取文件路径
			filePath = myFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return filePath;
		}
	}
	
	/**
	 * 预警生成xml并保存
	 */
	@SuppressWarnings("finally")
	public static String yjToXml(String trStr, String ipStr, EWRecieve eWRecieve) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/* 生成xml文件 */
		Document doc = DocumentHelper.createDocument();// 创建文档
		doc.setXMLEncoding("gb2312");// 设置文档编码方式

		// 创建头节点 MSG;
		Element msg = doc.addElement("MSG");

		// 添加MSG的 HD子节点及HD的子节点
		Element hd = msg.addElement("HD");
		Element tr = hd.addElement("TR");
		Element ip = hd.addElement("IP");

		// 添加 内容
		tr.setText(trStr);
		ip.setText(ipStr);

		// 添加MSG的 HD子节点及HD的子节点
		Element bd = msg.addElement("BD");
		Element bkid = bd.addElement("BKID");
		Element cphm = bd.addElement("CPHM");
		Element cpys = bd.addElement("CPYS");
		Element tgsj = bd.addElement("TGSJ");
		Element jcdid = bd.addElement("JCDID");
		Element jcdmc = bd.addElement("JCDMC");
		Element cdid = bd.addElement("CDID");
		Element bkyy = bd.addElement("BKYY");
		Element bkya = bd.addElement("BKYA");
		Element bkr = bd.addElement("BKR");
		Element sldw = bd.addElement("SLDW");
		Element slr = bd.addElement("SLR");
		Element sldh = bd.addElement("SLDH");
		Element sxms = bd.addElement("SXMS");
		Element bkqssj = bd.addElement("BKQSSJ");
		Element bkzzsj = bd.addElement("BKZZSJ");
		Element jqbh = bd.addElement("JQBH");

		bkid.setText(eWRecieve.getEwarning().getDispatched().getBkid().toString());
		cphm.setText(eWRecieve.getEwarning().getHphm());
		cpys.setText(eWRecieve.getEwarning().getHpzl());
		tgsj.setText(formatter.format(eWRecieve.getEwarning().getTgsj()));
		jcdid.setText(eWRecieve.getEwarning().getJcdid());
		jcdmc.setText(eWRecieve.getEwarning().getJcdmc());
		cdid.setText(eWRecieve.getEwarning().getCdid());
		bkyy.setText("");
		bkya.setText("");
		bkr.setText(eWRecieve.getEwarning().getDispatched().getBkrjh());
		sldw.setText(eWRecieve.getEwarning().getDispatched().getBkjg());
		slr.setText(eWRecieve.getEwarning().getDispatched().getBkr());
		sldh.setText(eWRecieve.getEwarning().getDispatched().getBkjglxdh());
		sxms.setText("");
		bkqssj.setText(formatter.format(eWRecieve.getEwarning().getDispatched().getBkqssj()));
		bkzzsj.setText(formatter.format(eWRecieve.getEwarning().getDispatched().getBkjzsj()));
		jqbh.setText("");

		// 保存xml文件
		XMLWriter writer = null;
		File myFile = null;
		String filePath = null;
		try {
			// 创建文件
			myFile = new File(Config.getInstance().getLogFolder() + File.separator + "yjxx_xml_" + new Date().getTime() + ".xml");
			//创建父文件夹
			if (!myFile.getParentFile().exists()) {
				myFile.getParentFile().mkdirs();
			}

			// 写文件
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			writer = new XMLWriter(new FileOutputStream(myFile), format);
			writer.write(doc);
			writer.flush();
			writer.close();

			// 获取文件路径
			filePath = myFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return filePath;
		}
	}
}