package com.dyst.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dyst.dispatched.service.DispatchedService;

public class Tools {
	//判断字符串不为空
	public static boolean isNotEmpty(String s){
		if(s == null || s.trim().length() <= 0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 根据传输参数xh的长度不满足十位时，补足十位 方法：前端补零
	 * 
	 * @param xh
	 * @return
	 */
	public static String getXh(String xh) {
		/*
		 * 根据传入的参数长度，不满十位的在前面补0.返回十位长的字符串。
		 */
		String seq_bkqqs = "";
		if (xh != null) {
			char a[] = xh.toCharArray();
			int lengh = a.length;

			if (lengh < 10) {
				for (int j = 0; j < 10 - lengh; j++) {
					seq_bkqqs = seq_bkqqs + "0";
				}
			}
			seq_bkqqs = seq_bkqqs + xh;

		}
		return seq_bkqqs;
	}
	
	/**
	 * 单号生成方法
	 * 
	 * @param lsbmid
	 * @param zw
	 * @param sDate
	 * @param bkckid
	 * @return
	 * @throws Exception
	 */
	public static String dhsc(String lsbmid, String zw, Date d) throws Exception {
		String zwTemp = zw.substring(0, 1);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String sDate = sdf1.format(d).toString();
		// 生成sql
		String sLshTemp = ""; // 单号流水号
		String sFlag = ""; // 单号头字母
		String sDwdm_4 = "";
		if(lsbmid.length() >= 8){
			sDwdm_4 = lsbmid.substring(4, 8); // 单位4位代码
		}else{
			sDwdm_4 = "0000"; // 单位4位代码
		}
		if ("8".equals(zwTemp))// 分局
		{
			sLshTemp = "fjlsh";
			sFlag = "F";

		}else if("12".equals(zw))// 派出所
		{
			sLshTemp = "jclsh";
			if ("00".equals(lsbmid.substring(lsbmid.length() - 2))) {
				sFlag = "P";
			} else // 卡口
			{
				sFlag = "K";
				sDwdm_4 = lsbmid.substring(4, 6);
				sDwdm_4 = sDwdm_4 + lsbmid.substring(lsbmid.length() - 2);
			}

		} else// 市局或其他
		{
			sLshTemp = "sjlsh";
			sFlag = "S";
		}

		//从web中获取spring容器
		WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		DispatchedService dispatchedService = (DispatchedService) ac.getBean("dispatchedService");

		Integer lsh = 0;// 流水号
		lsh = dispatchedService.getLsh(sLshTemp, sDate);

		// 生成单号
		String _lsh = getLsh("" + lsh);
		_lsh = sFlag + sDwdm_4 + sDate + _lsh; // P0455201102010001

		return _lsh;
	}
	
	/**
	 * 根据传输的xh补足4位时补足4位，前端补零
	 * 
	 * @param xh
	 * @return
	 */
	public static String getLsh(String xh)// 流水号
	{
		/*
		 * 单号生成时调用的方法
		 */
		String seq_bkqqs = "";
		if (xh != null) {
			char a[] = xh.toCharArray();
			int lengh = a.length;

			if (lengh < 4) {
				for (int j = 0; j < 4 - lengh; j++) {
					seq_bkqqs = seq_bkqqs + "0";
				}
			}
			seq_bkqqs = seq_bkqqs + xh;

		}
		return seq_bkqqs;
	}
}
