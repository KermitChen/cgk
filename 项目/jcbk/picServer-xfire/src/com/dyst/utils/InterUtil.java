package com.dyst.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.xfire.transport.http.XFireServletController;

/*
 * �ӿ��г��÷���
 */
public class InterUtil {

	/**
	 * @param str
	 *            ��Ҫ���˵��ַ���
	 * @return ���˺���ַ�
	 */
	public static String keyWordFilter(String str) {
		// ��#�ָ�ؼ���
		if("".equals(str) || str == null) {
			return "";
		}
		
		//���ַ���ת��ΪСд
		str = str.toLowerCase();
		//���������ַ�
		StringBuilder model_str = new StringBuilder(); 
		model_str.append("'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|")
					.append("char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like|create|table|from|grant|")
					.append("use|group_concat|column_name|information_schema.columns|table_schema|union|where|")
					.append("order|by|chr|mid|master|truncate|--|//|/|#|=");
		//��ֲ�����
		String model_split_str[] = model_str.toString().split("\\|");
		for(int i=0;i < model_split_str.length;i++) {
			// >=0˵���йؼ��֣�����˵��û�йؼ���
			while(str.indexOf(model_split_str[i]) >= 0){
				str = str.replace(model_split_str[i], "");
			}
		}
		return str;
	}
    
	/**
	 * ��ȡ��ǰʱ��ǰn���ʱ��
	 * 
	 * @param n
	 * @return
	 */
	public static String getTime(int n) {
		/*
		 * ��ȡ��ǰʱ���ǰn���ʱ��
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -n);
		String mDateTime = sdf.format(c.getTime());
		// String strStart=mDateTime.substring(0,19);
		return mDateTime;
	}
	    
	/**
	 * �ֻ�����У�� �Ƿ���true ,���Ƿ��� false;
	 * 
	 * @param mobiles
	 *            �ֻ�����
	 * @return У����
	 */
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null || "".equals(mobiles.trim())) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		// logger.info(m.matches()+"---");
		return m.matches();
	}
	
	/**
	 * �����ַ����������ɵ��������ӵ��ַ���
	 * 
	 * @param strs
	 * @return
	 */
	public static String getStr(String[] strs) {
		StringBuffer strbuf = new StringBuffer();
		int len = strs.length;
		for (int i = 0; i < strs.length; i++) {
			if (i == len - 1) {
				strbuf.append("'" + strs[i] + "'");
			} else {
				strbuf.append("'" + strs[i] + "',");
			}
		}
		return strbuf.toString();
	}
	
	/**
	 * ��ȡ����ʱ���֮������ڼ���<br>
	 * @param startDate ��ʼʱ�� ��ʽ "yyyy-MM-dd HH:mm:ss"<br>
	 * @param endDate   ��ֹʱ��
	 * @return
	 */
	public static Set<String> process(String startDate, String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = sdf.format(format.parse(startDate));
			endDate = sdf.format(format.parse(endDate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Set<String> al = new HashSet<String>();
		if (startDate.equals(endDate)) {
			// IF��ʼ���ڵ��ڽ�ֹ����,��������ʼ����һ��
			al.add(startDate);
		} else if (startDate.compareTo(endDate) < 0) {
			// IF��ʼ�������ڽ�ֹ����,������ֹ���ڵ�ÿһ��
			while (startDate.compareTo(endDate) < 0) {
				al.add(startDate);
				try {
					Long l = sdf.parse(startDate).getTime();
					startDate = sdf.format(l + 3600 * 24 * 1000);// +1��
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			al.add(startDate);
		} else {
			// IF��ʼ�������ڽ�ֹ����,��������ʼ����һ��
			al.add(startDate);
		}
		return al;
	}
	
	/**
	 * �鿴ĳһ��ͼƬ�����Ƿ����
	 * @param pName
	 * @return
	 */
	public static boolean exists(String pName){
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(pName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode()==HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * ��ȡԶ�̵���webservice�Ŀͻ���ip��ַ
	 * @return
	 */
	public static String getWebserviceRemoteAddress() {
		HttpServletRequest request = XFireServletController.getRequest();
		String ip = request.getRemoteAddr();
		return ip;
	}
}
