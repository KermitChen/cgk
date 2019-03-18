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
 * 接口中常用方法
 */
public class InterUtil {

	/**
	 * @param str
	 *            需要过滤的字符串
	 * @return 过滤后的字符
	 */
	public static String keyWordFilter(String str) {
		// 用#分割关键字
		if("".equals(str) || str == null) {
			return "";
		}
		
		//将字符串转换为小写
		str = str.toLowerCase();
		//定义特殊字符
		StringBuilder model_str = new StringBuilder(); 
		model_str.append("'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|")
					.append("char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like|create|table|from|grant|")
					.append("use|group_concat|column_name|information_schema.columns|table_schema|union|where|")
					.append("order|by|chr|mid|master|truncate|--|//|/|#|=");
		//拆分并过滤
		String model_split_str[] = model_str.toString().split("\\|");
		for(int i=0;i < model_split_str.length;i++) {
			// >=0说明有关键字，否则说明没有关键字
			while(str.indexOf(model_split_str[i]) >= 0){
				str = str.replace(model_split_str[i], "");
			}
		}
		return str;
	}
    
	/**
	 * 获取当前时间前n天的时间
	 * 
	 * @param n
	 * @return
	 */
	public static String getTime(int n) {
		/*
		 * 获取当前时间的前n天的时间
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -n);
		String mDateTime = sdf.format(c.getTime());
		// String strStart=mDateTime.substring(0,19);
		return mDateTime;
	}
	    
	/**
	 * 手机号码校验 是返回true ,不是返回 false;
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return 校验结果
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
	 * 根据字符串数组生成单引号连接的字符串
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
	 * 获取两个时间段之间的日期集合<br>
	 * @param startDate 开始时间 格式 "yyyy-MM-dd HH:mm:ss"<br>
	 * @param endDate   截止时间
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
			// IF起始日期等于截止日期,仅返回起始日期一天
			al.add(startDate);
		} else if (startDate.compareTo(endDate) < 0) {
			// IF起始日期早于截止日期,返回起止日期的每一天
			while (startDate.compareTo(endDate) < 0) {
				al.add(startDate);
				try {
					Long l = sdf.parse(startDate).getTime();
					startDate = sdf.format(l + 3600 * 24 * 1000);// +1天
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			al.add(startDate);
		} else {
			// IF起始日期晚于截止日期,仅返回起始日期一天
			al.add(startDate);
		}
		return al;
	}
	
	/**
	 * 查看某一个图片连接是否存在
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
	 * 获取远程调用webservice的客户端ip地址
	 * @return
	 */
	public static String getWebserviceRemoteAddress() {
		HttpServletRequest request = XFireServletController.getRequest();
		String ip = request.getRemoteAddr();
		return ip;
	}
}
