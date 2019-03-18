package com.dyst.entites;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Pfcxfx {
	/**
	 * 根据序号生成时间段描述信息
	 * 例如：时间戳为1，时间段[0:00 0:15]->[0:00 0:20]
	 * @param index
	 * @return
	 */
	public String getDecsByIndex(int index,int step,int start){
		String desc = "["+(index*step-start)/60+":"+(index*step-start)%60+" "
		  +((index*step-start)+step)/60+":"+((index*step-start)+step)%60+"]->[" +
		  "["+(index*step-start)/60+":"+(index*step-start)%60+" "
		  +((index*step-start)+20)/60+":"+((index*step-start)+20)%60+"]";
		return desc;
	}
	
	/**
	 * 生成数组B,根据jcdid和序号，查找集合中存在跟指定jacketed和序号相同的，则count加1
	 * @param jcdid
	 * @param index
	 * @param list
	 * @param flag
	 * @return
	 */
	public PfTemp generateSB(String jcdid,int index,List<PfTemp> list,String flag){
		PfTemp p = new PfTemp();
		int count=0;
		if("1".equals(flag)){
			p.setJcdid(jcdid);
			p.setIndex1(index);
			for(PfTemp pt : list){
				if(jcdid.equals(pt.getJcdid())&&index==pt.getIndex1()){
					count++;
				}
			}
			p.setCount(count);
		}else if("2".equals(flag)){
			p.setJcdid(jcdid);
			p.setIndex2(index);
			for(PfTemp pt : list){
				if(jcdid.equals(pt.getJcdid())&&index==pt.getIndex2()){
					count++;
				}
			}
			p.setCount(count);
		}
		return p;
	} 
	
    /**
     * 判断给定的监测点id和序号是否在集合中存在	
     * @param jcdid
     * @param index
     * @param list
     * @param flag 判断是index1还是index2
     * @return
     */
	public boolean existSB(String jcdid ,int index ,List<PfTemp>  list,String flag){
		if("1".equals(flag)){
			for(PfTemp p :list){
				if(p.getJcdid().equals(jcdid)&&p.getIndex1()==index){
					return true;
				}
			}
		}else if("2".equals(flag)){
			for(PfTemp p :list){
				if(p.getJcdid().equals(jcdid)&&p.getIndex2()==index){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 把日期的 小时*60+分钟 ，计算该值在集合中的索引值
	 * @param d 日期
	 * @param list 日期数组集合，int[2] 每个实体包括开始值和结束值范围
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int indexTime(Date d, ArrayList<int[]> list){
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(d.getTime());
//	    System.out.println(sdf.format(d));
//	    System.out.println(d.getHours()+"---"+d.getMinutes());
	    int time = d.getHours()*60+d.getMinutes();
//	    System.out.println(time+"时间值");
	    for(int i=0; i<list.size();i++){
	    	int begin = list.get(i)[0];
	    	int end = list.get(i)[1];
	    	if(time>=begin&&time<end){
//	    		System.out.println(begin+"----------"+end);
	    		return i;
	    	}
	    }
//	    System.out.println(+list.get(list.size()-1)[0]+"===="+list.get(list.size()-1)[1]+"------"+d);
	    //如果在集合中没找到，所以该日期就在集合最后一个范围里面
	    return list.size()-1;
	}
	/**
	 * 根据给定初始值，生成一个以step为步长的数组。
	 * @param d 初始值
	 * @param step 步长
	 * @return
	 */
	public ArrayList<int[]> szTime(int d,int step){
	    ArrayList<int[]> list = new ArrayList<int[]>();
	    int [] time = null;
	    while(d<24*60){
			//如果第2位数组大于等于24，则跳转至开始出
		    if((d+step)/60>=24){
		    	time = new int[]{d,(d+step)%60};	  		
		    }else{
		    	time = new int[]{d,d+step};		    	
		    }
          	d += step;
          	list.add(time);
	    }
		return list;
	}
}
	