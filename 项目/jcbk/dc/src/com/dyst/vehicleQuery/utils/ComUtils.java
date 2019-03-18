package com.dyst.vehicleQuery.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.apache.commons.lang.StringUtils;
import com.dyst.advancedAnalytics.dto.RequestParameters;
import com.dyst.chariotesttube.entities.Vehicle;
import com.dyst.kafka.service.KafkaService;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.IntefaceUtils;
import com.dyst.vehicleQuery.dto.ReqArgs;

public class ComUtils {
	/**
	 * 查询机动车信息
	 */
	public static Vehicle getVehicleInfo(User user, String hphm, String hpzl, String ip, KafkaService kafkaService) throws Exception {
		Vehicle vehicle = null;
		try {
			if (user.getIdentityCard() != null || !"".equals(user.getIdentityCard().trim())) {
				// 获取参数
				if (hphm != null && !"".equals(hphm.trim()) && hpzl != null && !"".equals(hpzl.trim())) {
					//记录日志
					String log = joinStr("#", user.getLoginName(), user.getUserName(), ip, "车辆轨迹查询", "查询机动车信息", "hphm:" + hphm + ",hpzl:" + hpzl, 
							new JDateTime().toString("YYYY-MM-DD hh:mm:ss"), user.getDeptId(), user.getDeptName(), "1");
					// 向kafka发送消息
					kafkaService.sendMessage("business_log", log, "");
					// 获取结果
					vehicle = IntefaceUtils.queryVehicle(hphm, hpzl, user.getUserName(), user.getIdentityCard(), user.getDeptId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/*
		 *vehicle = new Vehicle(); vehicle.setClpp1("宝马");
		 *vehicle.setClxh(String.valueOf((int)(Math.random()*100)));
		 *vehicle.setFdjh("123456"); vehicle.setClsbdh("78945613");
		 *vehicle.setCsys("红色"); vehicle.setJdczt("无状态");
		 *vehicle.setJdcsyr("张三"); vehicle.setLxfs("1888888888");
		 *vehicle.setSfzh("1565656556"); vehicle.setDjzzxz("北京市海淀区中关村科技发展大厦");
		 */
		return vehicle;
	}

	/**
	 * 根据车牌类型获取相对应的号牌种类
	 * 
	 * @param cplx
	 * @return
	 * @throws Exception
	 */
	public static String cplxToHpzl(String cplx) throws Exception {
		String temp = "";
		if (cplx == null || "".equals(cplx) || !StringUtils.isNumeric(cplx)) {
			temp = "";
			return temp;
		}

		// 转换
		int i = Integer.parseInt(cplx);
		switch (i) {
		case 0:
			temp = "02";
			break;

		case 1:
			temp = "06";
			break;

		case 2:
			temp = "01";
			break;

		case 3:
			temp = "01";
			break;

		case 4:
			temp = "01";
			break;

		case 5:
			temp = "23";
			break;

		case 6:
			temp = "23";
			break;

		case 7:
			temp = "01";
			break;

		case 8:
			temp = "02";
			break;

		case 9:
			temp = "02";
			break;
		}
		return temp;
	}
	/**
	 * 号牌种类转换号牌颜色
	 * @param hpzl
	 * @return
	 */
	public static String hpzlToCplx(String hpzl) {
		if (hpzl != null && "02".equals(hpzl.trim())) {
			return "0";
		} else if (hpzl != null && "03".equals(hpzl.trim())
				&& "04".equals(hpzl.trim()) && "05".equals(hpzl.trim())
				&& "06".equals(hpzl.trim()) && "26".equals(hpzl.trim())
				&& "27".equals(hpzl.trim())) {
			return "1";
		} else if (hpzl != null && "01".equals(hpzl.trim())
				&& "13".equals(hpzl.trim()) && "14".equals(hpzl.trim())
				&& "15".equals(hpzl.trim()) && "16".equals(hpzl.trim())) {
			return "2";
		} else if (hpzl != null && "20".equals(hpzl.trim())
				&& "21".equals(hpzl.trim()) && "22".equals(hpzl.trim()) 
				&& "23".equals(hpzl.trim()) && "31".equals(hpzl.trim())
				 && "32".equals(hpzl.trim())) {
			return "3";
		} else if(hpzl != null && "51".equals(hpzl.trim()) 
				&& "52".equals(hpzl.trim())){//绿牌
			return "14";
		} else {
			return "13";//其他
		}
	}

	/**
	 * 根据查询条件确定查询类型 01:已识别轨迹查询 03:未识别车辆查询 04:车牌号模糊查询 05:车牌前缀查询 11:不分已识别和未识别
	 * 
	 * @param args
	 * @return
	 */
	public static String ensureBusFlag(ReqArgs args) {
		String busFlag = "01";
		if (args.getHphm() != null
				&& !"".equals(args.getHphm())
				&& (args.getHphm().contains("?") || args.getHphm()
						.contains("*"))) {
			return "04";
		}
		if (args.getUnrecognizedFlag() == null
				|| "".equals(args.getUnrecognizedFlag())
				|| "11".equals(args.getUnrecognizedFlag())) {
			return "11";
		}
		if (args.getUnrecognizedFlag() != null
				&& !"".equals(args.getUnrecognizedFlag())
				&& "0".equals(args.getUnrecognizedFlag())) {
			return "03";
		}
		return busFlag;
	}

	/**
	 * 将参数放入map中
	 * 
	 * @param args
	 * @return
	 */
	public static void putInMap(ReqArgs args, Map<String, Object> map) {
		Field[] fields = args.getClass().getDeclaredFields();
		for (Field field : fields) {
			Method method = null;
			Object value = null;
			String name = field.getName();
			String upperName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			try {
				method = args.getClass().getMethod("get" + upperName);
				value = method.invoke(args);
				if (value != null) {
					map.put(name, value);
				} else {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将参数放入map中
	 * 
	 * @param parms
	 * @return
	 */
	public static void putInMap(RequestParameters parms, Map<String, Object> map) {
		Field[] fields = parms.getClass().getDeclaredFields();
		for (Field field : fields) {
			Method method = null;
			Object value = null;
			String name = field.getName();
			String upperName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			try {
				method = parms.getClass().getMethod("get" + upperName);
				value = method.invoke(parms);
				if (value != null) {
					map.put(name, value);
				} else {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按指定的分隔符拼接字符串
	 * 
	 * @param splitStr
	 * @param strs
	 * @return
	 */
	private static String joinStr(String splitStr, String... strs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append(splitStr);
		}
		return sb.substring(0, sb.length() - 1);
	}
}