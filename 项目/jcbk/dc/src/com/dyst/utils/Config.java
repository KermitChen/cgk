package com.dyst.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 配置文件类
 * @author cgk
 *
 */
public class Config {
	private static Config config = new Config();

	private Config() {
		init();//初始化配置文件信息
	}
	
	public static Config getInstance() {
		return config;
	}
	
	private String sysTitle;//系统名称
	private String logFolder = "/";
	
	private String messageWebservice;//短信接口
	private String dlyzmFlag;//使用登录验证码标志
	
	private String sbPicWebservice;// 图片轨迹接口
	private String dzys1;//ip切换
	private int maxOrder;//最大排序
	
	private String cjgWebservice;//深圳本地车架管接口
	private String gacjgWebservice;//港澳车架管接口
	private String updateUserDeptFlag;//是否启用实时更新用户部门信息
	private String updateDynamicService;//是否动态勤务数据
	private String updateDynamicServiceTime;//更新时间范围（以当前时间为准，更新前N分钟的数据）   单位：分钟
	private String helpDocDir;//帮助文档存放路径
	private String helpDocPath;//帮助文档访问服务名
	
	private String stInterface;//省厅接口地址
	private String stInterfaceFlag;//省厅接口开关（0关   1开）     结构定义        布控开关:撤控开关
	
	/*=========@liuqiang============*/
	private String sysAnnDir;//系统公告存放路径
	private String sysAnnPath;//系统公告访问服务名
	
	private String authURL;//pki网关服务地址
	private String appId;//pki应用标示
	private String attrType;//获取属性列表控制项none：不获取属性；all 获取全部属性 ；portion 获取指定属性
	private String attributes;//当配置文件中attrType 值为portion时 获取查询指定属性   当且仅当attrType为portion时有效
	private String isReadWarn;//是否读取实时预警
	private String earlyWaringTopicName;//预警的topic名称
	private String isReadSubscription;
	private String subscriptionTopicName;//订阅的topic名称
	private String isReadVehicleData;
	private String realVehicleDataTopicName;//实时过车数据的topic名称
	private int defaultTxyz;
	private long interval;
	
	private String bugPicDir;
	private String bugPicPath;
	
	/**
	 * 获取配置文件中的配置项
	 */
	private void init() {
		SAXReader reader = new SAXReader();
		InputStream in = Config.class.getResourceAsStream("/config/config.xml");
		Document document = null;
		try {
			document = (Document) reader.read(in);
			Element root = document.getRootElement();
			this.sysTitle = root.element("sysTitle").getText().trim();
			this.logFolder = root.element("logFolder").getText().trim();
			this.messageWebservice = root.element("messageWebservice").getText().trim();
			this.dlyzmFlag = root.element("dlyzmFlag").getText().trim();
			this.sbPicWebservice = root.element("sbPicWebservice").getText().trim();
			this.dzys1 = root.element("dzys1").getText().trim();
			this.maxOrder = Integer.valueOf(root.element("maxOrder").getText().trim());
			this.cjgWebservice = root.element("cjgWebservice").getText().trim();
			this.gacjgWebservice = root.element("gacjgWebservice").getText().trim();
			this.helpDocDir = root.element("helpDocDir").getText().trim();
			this.helpDocPath = root.element("helpDocPath").getText().trim();
			this.stInterface = root.element("stInterface").getText().trim();//省厅接口地址
			this.stInterfaceFlag = root.element("stInterfaceFlag").getText().trim();//省厅接口开关（0关   1开）     结构定义        布控开关:撤控开关
			
			this.sysAnnDir = root.element("sysAnnDocDir").getText().trim();
			this.sysAnnPath = root.element("sysAnnouncement").getText().trim();
			
			this.authURL = root.element("authURL").getText().trim();
			this.appId = root.element("appId").getText().trim();
			this.attrType = root.element("attrType").getText().trim();
			this.attributes = root.element("attributes").getText().trim();
			this.updateUserDeptFlag = root.element("updateUserDeptFlag").getText().trim();
			this.updateDynamicService = root.element("updateDynamicService").getText().trim();
			this.updateDynamicServiceTime = root.element("updateDynamicServiceTime").getText().trim();
			this.isReadWarn = root.element("isReadWarn").getText().trim();
			this.earlyWaringTopicName = root.element("earlyWaringTopicName").getText().trim();
			this.subscriptionTopicName = root.element("subscriptionTopicName").getText().trim();
			this.isReadSubscription = root.element("isReadSubscription").getText().trim();
			
			this.isReadVehicleData = root.element("isReadVehicleData").getText().trim();
			this.realVehicleDataTopicName = root.element("realVehicleDataTopicName").getText().trim();
			
			this.defaultTxyz = Integer.parseInt(root.element("defaultTxyz").getText().trim());
			this.interval = Long.parseLong(root.element("interval").getText().trim());
			this.bugPicDir = root.element("bugPicDir").getText().trim();
			this.bugPicPath = root.element("bugPicPath").getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMessageWebservice() {
		return messageWebservice;
	}

	public String getDlyzmFlag() {
		return dlyzmFlag;
	}

	public String getGacjgWebservice() {
		return gacjgWebservice;
	}

	public String getSysTitle() {
		return sysTitle;
	}
	public String getSbPicWebservice(){
		return sbPicWebservice;
	}
	public String getDzys1(){
		return dzys1;
	}

	public String getCjgWebservice() {
		return cjgWebservice;
	}
	
	public int getMaxOrder(){
		return maxOrder;
	}

	public String getUpdateUserDeptFlag() {
		return updateUserDeptFlag;
	}

	public String getEarlyWaringTopicName() {
		return earlyWaringTopicName;
	}


	public String getSubscriptionTopicName() {
		return subscriptionTopicName;
	}

	public String getHelpDocDir() {
		return helpDocDir;
	}

	public String getHelpDocPath() {
		return helpDocPath;
	}

	public static Config getConfig() {
		return config;
	}

	public String getSysAnnDir() {
		return sysAnnDir;
	}

	public String getSysAnnPath() {
		return sysAnnPath;
	}

	public String getAuthURL() {
		return authURL;
	}

	public String getAppId() {
		return appId;
	}

	public String getAttrType() {
		return attrType;
	}

	public String getAttributes() {
		return attributes;
	}

	public String getIsReadWarn() {
		return isReadWarn;
	}


	public String getIsReadSubscription() {
		return isReadSubscription;
	}

	public String getUpdateDynamicService() {
		return updateDynamicService;
	}

	public String getUpdateDynamicServiceTime() {
		return updateDynamicServiceTime;
	}

	public int getDefaultTxyz() {
		return defaultTxyz;
	}

	public long getInterval() {
		return interval;
	}

	public String getLogFolder() {
		return logFolder;
	}

	public String getBugPicDir() {
		return bugPicDir;
	}

	public String getBugPicPath() {
		return bugPicPath;
	}

	public String getStInterface() {
		return stInterface;
	}

	public String getStInterfaceFlag() {
		return stInterfaceFlag;
	}

	public String getIsReadVehicleData() {
		return isReadVehicleData;
	}

	public String getRealVehicleDataTopicName() {
		return realVehicleDataTopicName;
	}
}