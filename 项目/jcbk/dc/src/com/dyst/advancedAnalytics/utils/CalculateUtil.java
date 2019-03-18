package com.dyst.advancedAnalytics.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dyst.advancedAnalytics.dto.BsResult;
import com.dyst.advancedAnalytics.entities.ResSb;

public class CalculateUtil {
	/**
	 * 计算轨迹
	 * @param list	过车记录集合
	 * @param interval 多长时间认为是连续
	 * @param txyz 同行阈值
	 * @return
	 * @throws Exception
	 */
	public static List matchPath(List<ResSb> list,long interval,int txyz) throws Exception{
		List resList = new ArrayList();
		boolean flag = false;
		int indexTemp = 0;
		for (int i = 0; i < list.size()-1; i++) {//遍历过车记录集合
			ResSb b1 = list.get(i);
			ResSb b2 = list.get(i+1);
			//两条记录时间间隔超过设置的值
			if(!compareDate(b1.getTgsj(), b2.getTgsj(), interval)){
				flag = true;
			}
			if(i == list.size()-2 && flag){
				if(i+1-indexTemp >= txyz){//轨迹长度大于阈值长度
					List<ResSb> tempList = list.subList(indexTemp, i+1);
					resList.add(tempList);
				}
				break;
			}else if(i == list.size()-2 && !flag){
				if(i+2-indexTemp >= txyz){//轨迹长度大于阈值长度
					List<ResSb> tempList = list.subList(indexTemp, i+2);
					resList.add(tempList);
				}
				break; 
			}
			if(flag){
				if(i+1-indexTemp >= txyz){//轨迹长度大于阈值长度
					List<ResSb> tempList = list.subList(indexTemp, i+1);
					resList.add(tempList);
				}
				indexTemp = i+1;
				flag = false;
			}
		}
		return resList;
	}
	/**
	 * 计算同行监测点个数
	 * @param list
	 * @param interval 多长时间认为是连续
	 * @param txyz 同行阈值
	 * @return
	 * @throws Exception
	 */
	public static int countJcd(List<ResSb> list,long interval,int txyz) throws Exception{
		int result = 0;
		if(list.size() >= txyz){//集合长度小于同行阈值，排除
			boolean flag = false;
			int indexTemp = 0;
			for (int i = 0; i < list.size()-1; i++) {
				ResSb b1 = list.get(i);
				ResSb b2 = list.get(i+1);
				if(!compareDate(b1.getTgsj(), b2.getTgsj(), interval)){//判断两过车记录是否相连
					flag = true;
				}
				if(i == list.size()-2 && flag){
					if(i+1-indexTemp >= txyz){//轨迹长度大于阈值长度
						result += i+1-indexTemp;
					}
					break;
				}else if(i == list.size()-2 && !flag){
					if(i+2-indexTemp >= txyz){//轨迹长度大于阈值长度
						result += i+2-indexTemp;
					}
					break; 
				}
				if(flag){//判断相连失败
					if(i+1-indexTemp >= txyz){//轨迹长度大于阈值长度
						result += i+1-indexTemp;
					}
					indexTemp = i+1;
					flag = false;
				}
			}
		}
		return result;
	}
	/**
	 * 按照车牌号码进行分组并按时间排序
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, List> groupByCphm(List list){
		//存放车牌号和过车记录的map容器
		Map<String, List> map = new HashMap<String, List>();
		ResSb temp = null;
		for (int i = 0; i < list.size(); i++) {
			temp = (ResSb) list.get(i);
			if(map.containsKey(temp.getCphm1())){//map中已存在对应车牌号码
				map.get(temp.getCphm1()).add(temp);
			}else{//创建list放入map
				List li = new ArrayList();
				li.add(temp);
				map.put(temp.getCphm1(), li);
			}
		}
		return map;
	}
	/**
	 * 按时间大小顺序插入数据
	 * @param list
	 * @param sb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static void intertDataAndOrder(List list,ResSb sb){
		ResSb temp = (ResSb) list.get(0);
		if(sb.getTgsj().before(temp.getTgsj())){//小于第一个元素
			list.add(0,sb);
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			temp = (ResSb) list.get(i);
			if(sb.getTgsj().after(temp.getTgsj())){
				list.add(i+1, sb);
				break;
			}
		}
	}
	
	/**
	 * 比较两条过车记录的时间是否超过某个阈值
	 * @param d1
	 * @param d2
	 * @param interval
	 * @return false 大于；true 小于
	 */
	private static boolean compareDate(Date d1,Date d2,long interval){
		if(d2.getTime() - d1.getTime() > interval){
			return false;
		}
		return true;
	}
	/**
	 * 计算两个时间的天数差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int betweenDays(Date begin,Date end){
		long l1 = begin.getTime();
		long l2 = end.getTime();
		return (int) ((l2-l1)/(24*60*60*1000))+1;
	}
}
