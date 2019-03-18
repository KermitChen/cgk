package com.dyst.kafka;

import com.dyst.entites.BusinessLog;
import com.dyst.entites.ErrorLog;

public class ParseUtil {

	/**
	 * 解析业务日志
	 * @param str
	 * @param splitStr 分隔符
	 * @return
	 */
	public static BusinessLog parseBusinessLog(String str, String splitStr){
		BusinessLog log = null;
		try {
			String[] arr = str.split(splitStr);
			if (arr != null && arr.length >= 7) {
				log = new BusinessLog(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], "", "");
			} else if(arr != null && arr.length >= 9){
				log = new BusinessLog(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}
	/**
	 * 解析错误日志
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static ErrorLog parseErrorLog(String str,String splitStr){
		ErrorLog log = null;
		try {
			String[] arr = str.split(splitStr);
			if(arr != null && arr.length >= 8){
				log = new ErrorLog(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}
}
