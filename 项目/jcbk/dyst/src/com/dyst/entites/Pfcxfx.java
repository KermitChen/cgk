package com.dyst.entites;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Pfcxfx {
	/**
	 * �����������ʱ���������Ϣ
	 * ���磺ʱ���Ϊ1��ʱ���[0:00 0:15]->[0:00 0:20]
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
	 * ��������B,����jcdid����ţ����Ҽ����д��ڸ�ָ��jacketed�������ͬ�ģ���count��1
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
     * �жϸ����ļ���id������Ƿ��ڼ����д���	
     * @param jcdid
     * @param index
     * @param list
     * @param flag �ж���index1����index2
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
	 * �����ڵ� Сʱ*60+���� �������ֵ�ڼ����е�����ֵ
	 * @param d ����
	 * @param list �������鼯�ϣ�int[2] ÿ��ʵ�������ʼֵ�ͽ���ֵ��Χ
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int indexTime(Date d, ArrayList<int[]> list){
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(d.getTime());
//	    System.out.println(sdf.format(d));
//	    System.out.println(d.getHours()+"---"+d.getMinutes());
	    int time = d.getHours()*60+d.getMinutes();
//	    System.out.println(time+"ʱ��ֵ");
	    for(int i=0; i<list.size();i++){
	    	int begin = list.get(i)[0];
	    	int end = list.get(i)[1];
	    	if(time>=begin&&time<end){
//	    		System.out.println(begin+"----------"+end);
	    		return i;
	    	}
	    }
//	    System.out.println(+list.get(list.size()-1)[0]+"===="+list.get(list.size()-1)[1]+"------"+d);
	    //����ڼ�����û�ҵ������Ը����ھ��ڼ������һ����Χ����
	    return list.size()-1;
	}
	/**
	 * ���ݸ�����ʼֵ������һ����stepΪ���������顣
	 * @param d ��ʼֵ
	 * @param step ����
	 * @return
	 */
	public ArrayList<int[]> szTime(int d,int step){
	    ArrayList<int[]> list = new ArrayList<int[]>();
	    int [] time = null;
	    while(d<24*60){
			//�����2λ������ڵ���24������ת����ʼ��
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
	