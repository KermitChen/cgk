package com.dyst.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * 常用的工具类
 * @author cgk
 *
 */
public class CommonUtils {
	/**
	 * @param str
	 *            需要过滤的字符串
	 * @return 过滤后的字符
	 */
	public static String keyWordFilter(String str) {
		// 用#分割关键字
		if (str == null || "".equals(str.trim())) {
			return "";
		}
		
		//转换成小写
		str = str.toLowerCase();
		
		//去除特殊字符
		String model_str = "%#and#exec#insert#select#delete#update#count#*#'#chr#mid#master#truncate#char#declare#;#or#-#+#,#<#># ";
		String model_split_str[] = model_str.split("#");
		for(int i = 0;i < model_split_str.length;i++) {
			if(str.indexOf(model_split_str[i]) >= 0) {// >=0说明有关键字，否则说明没有关键字
				str = str.replace(model_split_str[i], "");
			}
		}
		return str;
	}
	
	/**
	 * 通过HttpServletRequest返回IP地址
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 生成验证码
	 * @param n 验证码位数
	 * @return
	 */
	public static String getCode(int n) {
		Random r = new Random();
		int index = 0;
		String code = "";//验证码
		
		//验证码字符
		String[] codeArr = new String[]{"1","2","3","4","5","6","7","8","9","0",
				                 "A","B","C","D","E","F","G","H","I","J","K",
				                 "L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
				                 "a","b","c","d","e","f","g","h","i","j","k",
				                 "m","n","p","q","r","s","t","u","v","w","x","y","z"};
		
		//取n位，组合成验证码
		for(int i=1;i <= n;i++){
			index = r.nextInt(59);
			code += codeArr[index];
		}
		return code;
	}
	
	/**
	 * 获取beginTime时间点的后n天的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少天后
	 * @return 返回日期字符串
	 */
	public static String getTimeOfAfter_N_Day_String(String pattern, Date beginTime, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Calendar c = Calendar.getInstance();
		c.setTime(beginTime);
		c.add(Calendar.DAY_OF_MONTH, n);
		String mDateTime = sdf.format(c.getTime());
		return mDateTime;
	}
	
	/**
	 * 获取beginTime时间点的后n天的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少天后
	 * @return 返回date类型
	 */
	public static Date getTimeOfAfter_N_Day_Date(String pattern, Date beginTime, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginTime);
		c.add(Calendar.DAY_OF_MONTH, n);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间n小时前后的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少分钟后(正)  多少分钟前(负)
	 * @return 返回date类型
	 */
	public static Date getTimeOf_N_Hour_Date(String pattern, Date beginTime, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginTime);
		c.add(Calendar.HOUR_OF_DAY, n);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间n分钟前后的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少分钟后(正)  多少分钟前(负)
	 * @return 返回date类型
	 */
	public static Date getTimeOf_N_Minute_Date(String pattern, Date beginTime, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginTime);
		c.add(Calendar.MINUTE, n);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间前n天的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少天前
	 * @return 返回日期字符串
	 */
	public static String getTimeOfBefore_N_Day_String(String pattern, Date beginTime, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String mDateTime = "";
		if(beginTime != null){
			Calendar c = Calendar.getInstance();
			c.setTime(beginTime);
			c.add(Calendar.DAY_OF_MONTH, -n);
			mDateTime = sdf.format(c.getTime());
		}
		return mDateTime;
	}
	
	/**
	 * 获取当前时间前n天的时间
	 * @param pattern 格式
	 * @param beginTime 开始时间点
	 * @param n 多少天前
	 * @return 返回date类型
	 */
	public static Date getTimeOfBefore_N_Day_Date(String pattern, Date beginTime, int n) {
		if(beginTime != null){
			Calendar c = Calendar.getInstance();
			c.setTime(beginTime);
			c.add(Calendar.DAY_OF_MONTH, -n);
			return c.getTime();
		}
		return null;
	}
	
	/**
	 * 将数值以道号分割的值加上单引号
	 * @param value 原值
	 * @return
	 */
	public static String changeString(String value){
		String str = "";
		if(value != null && !"".equals(value)){
			String[] arrStr = value.split(",");
			for(int i = 0;i < arrStr.length;i++){
				str += "'" + arrStr[i] + "',";
			}
			
			//去掉最后一个道号
			if(str.length() > 1){
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}
	
	/**
	 * MD5加密
	 * @param str 需要加密的字符串
	 * @throws Exception
	 */
	public static String md5(String str) throws Exception{
	    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes());
        byte[] digest =  messageDigest.digest();
        return byte2hex(digest).toLowerCase();
	}
	
	/**
     * byte数组转十六进制字符串
     * @param b
     */
	public static String byte2hex(byte[] b){
        StringBuilder buf = new StringBuilder();
        for (byte c : b) {
            buf.append(String.format("%02x", c).toUpperCase());
        }
        return buf.toString();
    }
	
	/**
	 * 判断身份证号是否正确
	 * @param identityCard
	 * @return
	 */
	public static boolean isIdentityCard(String identityCard){
		String reg = "^\\d{15}(\\d{2}[0-9Xx])?$";
		if(identityCard.matches(reg)){
			char code = getCheckCode(identityCard);
			int len = identityCard.length() == 18 ? 17:14;
			return identityCard.charAt(len) == code ? true : false;
		}
		return false;
	}
	
	/**
	 * 计算身份证校验码，最后一位
	 * @param cardStr
	 * @return
	 */
	public static char getCheckCode(String cardStr){//获取校验码方法
		int[] systemNumber = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};//系数
		char[] checkCode = {'1','0','X','9','8','7','6','5','4','3','2'};//校验码
		int sum =0;
		for(int i = 0; i < cardStr.length() - 1;i++){//计算前17位数字和系数相乘之和
			sum +=(cardStr.charAt(i)-'0')*systemNumber[i];
		}
		
		int remainder = sum % 11;//对11取余数
		return checkCode[remainder];//返回校验码
	}
	
	/**
	 * 写文件，换行写 pathDir:文件存放路径 fileName:文件名字 content:行内容 changeName:大于一百兆改文件名字
	 */
	@SuppressWarnings("finally")
	public static Boolean writeFile(String pathDir, String fileName, String content, boolean changeName, boolean addTime) {
		boolean writeSuccess = true;
		OutputStreamWriter out = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder sb = new StringBuilder();
		try {
			// 判断路径是否存在，不存在，则创建
			File fileFolder = new File(pathDir);
			if (!fileFolder.getParentFile().exists()) {
				fileFolder.getParentFile().mkdirs();
			}
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}

			// 写文件
			out = new OutputStreamWriter(new BufferedOutputStream(
					new FileOutputStream(pathDir + File.separator + fileName,
							true)), "UTF-8");
			out.write("\n");// 换行
			// 加时间戳
			if (addTime) {
				sb.append(dateFormat.format(new Date())).append(":");
			}
			sb.append(content);//内容
			out.write(sb.toString());
			out.flush();
			out.close();
			out = null;

			// 大于一百兆，则改名
			if (changeName) {
				File file = new File(pathDir + File.separator + fileName);
				if ((file.length() / 1048576.0) > 50.0) {// 如果大于50兆，则改名
					file.renameTo(new File(pathDir + File.separator + dateFormat.format(new Date()) + fileName));
				}
			}
		} catch (Exception e) {
			writeSuccess = false;
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return writeSuccess;
		}
	}
	
	/**
	 * 记录操作日志
	 * @param loginName 登录用户名
	 * @param userName  登录用户姓名
	 * @param deptNo    隶属部门编号
	 * @param deptName  隶属部门名称
	 * @param ip   客户端ip地址
	 * @param operateTime  操作时间
	 * @param operateContent  执行的操作，如查询用户信息，增加用户信息
	 * @param operateResult   操作结果  成功1     或    失败0
	 */
	public static void recordOperationLog(String loginName, String userName, String deptNo, String deptName, 
			String ip, Date operateTime, String operateContent, String operateResult){
		try {
			//保存信息
			
		} catch (Exception e) {
			//记录错误信息
			
			e.printStackTrace();
		} finally{
			
		}
	}
	
	/**
	 * 记录错误日志
	 * @param loginName 登录用户名
	 * @param operateContent  执行的操作，如查询用户信息，增加用户信息
	 * @param Exception 异常信息
	 */
	public static void recordErrorLog(String loginName, String operateContent, Exception error){
		try {
			//保存信息
			
		} catch (Exception e) {
			//记录错误信息
			
			e.printStackTrace();
		} finally{
			
		}
	}
	
	/**
	 * 两个时间的分钟及秒数
	 * @param one
	 * @param two
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getDistanceTime(Date one, Date two) {
		if (one == null || two == null) {
			return "";
		}
		
		long min = 0;
		long sec = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			// day = diff / (24 * 60 * 60 * 1000);
			// hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000))); // - day * 24 * 60 - hour * 60
			sec = (diff / 1000 - min * 60); // -day*24*60*60-hour*60*60
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return min + "分" + sec + "秒"; // day + "天" + hour + "小时" +
		}
	}
	
	/**
	 * 两个时间的天数、小时、分钟及秒数
	 * @param one
	 * @param two
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getDistanceHour(Date one, Date two) {
		long day = 0;
		long hour = 0;
		if (one == null || two == null) {
			return "";
		}
		long min = 0;
		long sec = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - min * 60 - day * 24 * 60 * 60 - hour * 60 * 60); //
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return day + "天" + hour + "小时" + min + "分" + sec + "秒";
		}
	}
	
	/**
	 * 号牌种类转换号牌颜色（号牌颜色为国标）
	 * 0:白色       
	 * 1:黄色       
	 * 2:蓝色       
     * 3:黑色       
     * 4:绿色    
     * 9:其他颜色 
	 * @param hpzl
	 * @return
	 */
	public static String hpzlToCplxOfGb(String hpzl) {
		if (hpzl != null && "02".equals(hpzl.trim())) {//小型车  蓝牌
			return "2";
		} else if (hpzl != null && "03".equals(hpzl.trim())
				&& "04".equals(hpzl.trim()) && "05".equals(hpzl.trim())
				&& "06".equals(hpzl.trim()) && "26".equals(hpzl.trim())
				&& "27".equals(hpzl.trim())) {//黑牌车
			return "3";
		} else if (hpzl != null && "01".equals(hpzl.trim())
				&& "13".equals(hpzl.trim()) && "14".equals(hpzl.trim())
				&& "15".equals(hpzl.trim()) && "16".equals(hpzl.trim())) {//大型车  黄牌
			return "1";
		} else if (hpzl != null && "20".equals(hpzl.trim())
				&& "21".equals(hpzl.trim()) && "22".equals(hpzl.trim()) 
				&& "23".equals(hpzl.trim()) && "31".equals(hpzl.trim())
				 && "32".equals(hpzl.trim())) {//白牌
			return "0";
		} else if(hpzl != null && "51".equals(hpzl.trim()) && "52".equals(hpzl.trim())){//绿牌
			return "4";
		} else {
			return "9";
		}
	}
	
	/**
	 * cxf超时设置
	 * @param service
	 * @param connectionTimeout
	 * @param receiveTimeout
	 */
	public static void setCxfTimeOut(Object service, long connectionTimeout, long receiveTimeout){
		org.apache.cxf.endpoint.Client proxy = ClientProxy.getClient(service);
		HTTPConduit conduit = (HTTPConduit)proxy.getConduit();
		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(connectionTimeout*1000);
		policy.setReceiveTimeout(receiveTimeout*1000);
		conduit.setClient(policy);
	}
}