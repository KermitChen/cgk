package com.dyst.oracle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;

import com.dyst.utils.Config;
import com.dyst.utils.InterUtil;

/**
 * SQL语句生成类
 * @author Administrator
 *
 */
public class CreateSql {
	/**
	 * 根据参数生成SQL语句  1张识别表方式
	 * @param kssj   开始时间<br>
	 * @param jssj   截止时间<br>
	 * @param hpzl   号牌种类<br>
	 * @param cphid  车牌号ID<br>
	 * @param cplx   车牌类型<br>
	 * @param gcxh   过车序号<br>
	 * @param jcdid  监测点id<br>
	 * @param hmdCphm  红名单车牌号码<br>
	 * @param bussiness 业务类型<br>
	 * @param flag      标志状态，0:查询记录；1:查询总数
	 * @return 生成的SQL语句
	 */
	public static String getSqlByCon(String kssj, String jssj,
			String hpzl, String cphid, String cplx, String gcxh, String jcdid,
			String cd, String cb, String sd, String hmdCphm, String bussiness, String flag, String sort) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		
		Config config = Config.getInstance();
		String qz1 = config.getQz1();//图片调用前缀
		String qz2 = config.getQz2();//图片调用前缀
        String selectTable = "sb";

        //获取天
        Set<String> al = InterUtil.process(kssj, jssj);
        String day = "";
		//组装
		for(String d : al){
			try {
				day += sdf2.format(sdf1.parse(d))+",";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(day.length() > 0){
			day = day.substring(0, day.length() - 1);
		}
        
        //创建查询语句
		StringBuffer strSql = new StringBuffer("select cphm1,cplx1,tgsj,scsj,tpzs,tpid1,tpid2,tpid3,tpid4,tpid5,jcdid,cdid,cb,sd,rownum rn from " + selectTable + " where 1=1 and fqh in (" + day + ")");
		
		if("1".equals(flag)){//查询记录总数
			strSql = new StringBuffer("select count(1) as count from " + selectTable + " where 1=1 and fqh in (" + day + ")");
		}
		
		//添加条件
		if (cphid != null && !"".equals(cphid.trim())) {
			//号牌号码判断,只有已识别车辆查询、模糊查询和总数查询才返回添加车牌号条件
			String strhphm[] = cphid.split(",");
			if("01".equals(bussiness.trim())){//已识别车轨迹查询接口
				if(strhphm.length == 1){
					strSql.append(" and cphm1 = '" + cphid + "'");
				} else{
					strSql.append(" and cphm1 in (" + InterUtil.getStr(strhphm) + ")");
				}
			} else if("04".equals(bussiness.trim())){
				//车辆模糊查询，只能一个车牌号,采用*代表任意字符，？代表单个字符;在Oracle数据库中需要转换成%和_
				strSql.append(" and cphm1 like '" + cphid.replace("*", "%").replace("?", "_") + "'");
			} else if("05".equals(bussiness.trim())){//前缀查询
				if(cphid.startsWith("!")){//非前缀查询
					strSql.append(" and cphm1 not like '" + (cphid.length() > 1? cphid.substring(1):"") + "%'");
				} else{
					if(cphid.subSequence(0, 1).equals(qz1)){
						strSql.append(" and cphm1 like '" + cphid + "%' and cphm1 not like '" + qz2 + "%'");
					} else{
						strSql.append(" and cphm1 like '" + cphid + "%'");
					}
				}
			}
		}
		
		//监测点条件
		if (jcdid != null && !"".equals(jcdid.trim())) {// 监测点ID判断
			String strjcdid[] = jcdid.split(",");// 多个车牌号
			if (strjcdid.length == 1) {
				strSql.append(" and jcdid = '" + jcdid + "'");
			} else {
				strSql.append(" and (jcdid in ('00000000')");
				
				String[] aa = new String[500];
				int j = 0;
				for(int k = 0;k < strjcdid.length;k++){
					aa[j] = strjcdid[k];
					if((j+1) == 500){
						strSql.append(" or jcdid in (" + InterUtil.getStr(aa) + ")");
						aa = new String[500];
						j = 0;
					} else{
						j++;
					}
					
					if(j > 0 && k == strjcdid.length - 1){
						aa = Arrays.copyOf(aa, j);
						strSql.append(" or jcdid in (" + InterUtil.getStr(aa) + ")");
					}
				}
				strSql.append(" )");
			}
		}
		//车牌类型条件
		if (cplx != null && !"".equals(cplx.trim())) {
			String strcplx[] = cplx.split(",");
			if (strcplx.length == 1) {
				strSql.append(" and cplx1 = '"+cplx+"'");
			} else {// 多个车牌号
				strSql.append(" and cplx1 in ("+InterUtil.getStr(strcplx)+")");
			}
		}
		
		//图片id条件
		if (gcxh != null && !"".equals(gcxh.trim())) {
			String strgcxh[] = gcxh.split(",");
			if (strgcxh.length == 1) {
				strSql.append(" and tpid1 = '" + gcxh + "'");
			} else {// 多个图片id
				strSql.append(" and tpid1 in ("+InterUtil.getStr(strgcxh)+")");
			}
		}
		
		//车道
		if (cd != null && !"".equals(cd.trim())) {
			String strcd[] = cd.split(",");
			if (strcd.length == 1) {
				strSql.append(" and cdid = '" + cd + "'");
			} else {// 多个车道
				strSql.append(" and cdid in (" + InterUtil.getStr(strcd) + ")");
			}
		}
		
		//车标
		if (cb != null && !"".equals(cb.trim())) {
			strSql.append(" and cb like '%" + cb.trim() + "%'");
		}
		
		//速度
		if (sd != null && !"".equals(sd.trim()) && !",".equals(sd.trim())) {
			String strsd[] = sd.split(",");
			if(strsd.length == 1){
				strSql.append(" and sd >= " + strsd[0]);
			} else if(strsd.length > 1){
				if("".equals(strsd[0])){
					strSql.append(" and sd <= " + strsd[1]);
				} else{
					strSql.append(" and sd >= " + strsd[0] + " and sd <= " + strsd[1]);
				}
			}
		}
		
		//识别时间条件
		if (kssj != null && !"".equals(kssj.trim()) && jssj != null
				&& !"".equals(jssj.trim())) {
			// 开始和截止时间必填
			strSql.append(" and " + (sort != null && "scsj".equals(sort.trim().toLowerCase())?"scsj":"tgsj") + " between to_date('" + kssj + "','yyyy-MM-dd HH24:mi:ss') and to_date('" + jssj + "','yyyy-MM-dd HH24:mi:ss')");
		}
		
		//未识别车辆查询,添加是否完成标志位0
        if("03".equals(bussiness.trim())){//未识别查询
        	strSql.append(" and qpsfwc = '0'");
        } else if("11".equals(bussiness.trim())){//不区分已识别和未识别
        	
        } else{
        	strSql.append(" and qpsfwc != '0'");//已识别查询
        }
		return strSql.toString();		
	}
}