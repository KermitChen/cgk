package com.dyst.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config {

	private static Config config = new Config();

	private Config() {
		init();// ��ʼ�������ļ���Ϣ
	}

	public static Config getInstance() {
		return config;
	}

	public static Config getConfig() {
		return config;
	}

	// ��ѯN��ǰ�����ݣ�Ĭ��Ϊ0
	public String logFolder = "/";
    private String sysFlag;//ϵͳ��ʶ
    private String getJcdTime;//��ʱ���ؼ���

	// oracle ������Ϣ
	private String user;
	private String pwd;
	private String url;
	private String driver;
	private String dbMaxCon;
	private String dbInit;
	private String dbtimeOut;

	// ͼƬ����
	public String numThread = "1";// ͼƬÿ��ͬ����ѯ��¼��
	
	//�ӿڲ�ѯ�쳣�������
	public String errorCode01;
	public String errorCode02;
	public String errorCode03;
	public String errorCode04;
	public String errorCode05;
	public String errorCode06;
	public String errorCode07;
	public String errorCode08;
	public String errorCode09;
	public String errorCode10;
	public String errorCode11;
	public String errorCode12;
	public String errorCode13;
	public String errorCode14;
	public String errorCode15;
    
	private void init() {
		SAXReader reader = new SAXReader();
		InputStream in = Config.class.getResourceAsStream("/config.xml");
		Document document = null;
		try {
			document = (Document) reader.read(in);
			Element root = document.getRootElement();
			
			logFolder = root.element("logFolder").getText();// ��־�ļ����Ŀ¼
			sysFlag = root.element("sysFlag").getText().trim();

			// ���ݿ�������Ϣ
			user = root.element("user").getText();
			pwd = root.element("pwd").getText();
			url = root.element("url").getText();
			driver = root.element("driver").getText();
			dbMaxCon = root.element("dbMaxCon").getText();
			dbInit = root.element("dbInit").getText();
			dbtimeOut = root.element("dbtimeOut").getText();

			// ͼƬ����������Ϣ
			numThread = root.element("threadNum").getText();// �̳߳ض��̲߳�����
			getJcdTime = root.element("getJcdTime").getText();// ��ʱ���ؼ���

			// �����붨��
			errorCode01 = root.element("errorCode01").getText();
			errorCode02 = root.element("errorCode02").getText();
			errorCode03 = root.element("errorCode03").getText();
			errorCode04 = root.element("errorCode04").getText();
			errorCode05 = root.element("errorCode05").getText();
			errorCode06 = root.element("errorCode06").getText();
			errorCode07 = root.element("errorCode07").getText();
			errorCode08 = root.element("errorCode08").getText();
			errorCode09 = root.element("errorCode09").getText();
			errorCode10 = root.element("errorCode10").getText();
			errorCode11 = root.element("errorCode11").getText();
			errorCode12 = root.element("errorCode12").getText();
			errorCode13 = root.element("errorCode13").getText();
			errorCode14 = root.element("errorCode14").getText();
			errorCode15 = root.element("errorCode15").getText();
		} catch (DocumentException e) {
			StringUtil.writerTXT(logFolder, "���������ļ�·���Ƿ���ȷ��" + e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			StringUtil.writerTXT(logFolder, "XML�ļ���������" + e);
		}
	}

	public String getErrorCode01() {
		return errorCode01;
	}

	public String getErrorCode02() {
		return errorCode02;
	}

	public String getErrorCode03() {
		return errorCode03;
	}

	public String getErrorCode04() {
		return errorCode04;
	}

	public String getErrorCode05() {
		return errorCode05;
	}

	public String getErrorCode06() {
		return errorCode06;
	}

	public String getErrorCode07() {
		return errorCode07;
	}

	public String getErrorCode08() {
		return errorCode08;
	}

	public String getErrorCode09() {
		return errorCode09;
	}

	public String getErrorCode10() {
		return errorCode10;
	}

	public String getErrorCode11() {
		return errorCode11;
	}

	public String getErrorCode12() {
		return errorCode12;
	}

	public String getErrorCode13() {
		return errorCode13;
	}

	public void setErrorCode13(String errorCode13) {
		this.errorCode13 = errorCode13;
	}

	public String getErrorCode14() {
		return errorCode14;
	}

	public void setErrorCode14(String errorCode14) {
		this.errorCode14 = errorCode14;
	}

	public String getErrorCode15() {
		return errorCode15;
	}

	public void setErrorCode15(String errorCode15) {
		this.errorCode15 = errorCode15;
	}

	public String getDbtimeOut() {
		return dbtimeOut;
	}

	public String getDbMaxCon() {
		return dbMaxCon;
	}

	public void setDbMaxCon(String dbMaxCon) {
		this.dbMaxCon = dbMaxCon;
	}

	public String getDbInit() {
		return dbInit;
	}

	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUrl() {
		return url;
	}

	public String getDriver() {
		return driver;
	}

	public String getNumThread() {
		return numThread;
	}

	public String getLogFolder() {
		return logFolder;
	}

	public String getGetJcdTime() {
		return getJcdTime;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
}