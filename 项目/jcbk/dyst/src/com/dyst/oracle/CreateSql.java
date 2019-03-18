package com.dyst.oracle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;

import com.dyst.utils.Config;
import com.dyst.utils.InterUtil;

/**
 * SQL���������
 * @author Administrator
 *
 */
public class CreateSql {
	/**
	 * ���ݲ�������SQL���  1��ʶ���ʽ
	 * @param kssj   ��ʼʱ��<br>
	 * @param jssj   ��ֹʱ��<br>
	 * @param hpzl   ��������<br>
	 * @param cphid  ���ƺ�ID<br>
	 * @param cplx   ��������<br>
	 * @param gcxh   �������<br>
	 * @param jcdid  ����id<br>
	 * @param hmdCphm  ���������ƺ���<br>
	 * @param bussiness ҵ������<br>
	 * @param flag      ��־״̬��0:��ѯ��¼��1:��ѯ����
	 * @return ���ɵ�SQL���
	 */
	public static String getSqlByCon(String kssj, String jssj,
			String hpzl, String cphid, String cplx, String gcxh, String jcdid,
			String cd, String cb, String sd, String hmdCphm, String bussiness, String flag, String sort) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		
		Config config = Config.getInstance();
		String qz1 = config.getQz1();//ͼƬ����ǰ׺
		String qz2 = config.getQz2();//ͼƬ����ǰ׺
        String selectTable = "sb";

        //��ȡ��
        Set<String> al = InterUtil.process(kssj, jssj);
        String day = "";
		//��װ
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
        
        //������ѯ���
		StringBuffer strSql = new StringBuffer("select cphm1,cplx1,tgsj,scsj,tpzs,tpid1,tpid2,tpid3,tpid4,tpid5,jcdid,cdid,cb,sd,rownum rn from " + selectTable + " where 1=1 and fqh in (" + day + ")");
		
		if("1".equals(flag)){//��ѯ��¼����
			strSql = new StringBuffer("select count(1) as count from " + selectTable + " where 1=1 and fqh in (" + day + ")");
		}
		
		//�������
		if (cphid != null && !"".equals(cphid.trim())) {
			//���ƺ����ж�,ֻ����ʶ������ѯ��ģ����ѯ��������ѯ�ŷ�����ӳ��ƺ�����
			String strhphm[] = cphid.split(",");
			if("01".equals(bussiness.trim())){//��ʶ�𳵹켣��ѯ�ӿ�
				if(strhphm.length == 1){
					strSql.append(" and cphm1 = '" + cphid + "'");
				} else{
					strSql.append(" and cphm1 in (" + InterUtil.getStr(strhphm) + ")");
				}
			} else if("04".equals(bussiness.trim())){
				//����ģ����ѯ��ֻ��һ�����ƺ�,����*���������ַ������������ַ�;��Oracle���ݿ�����Ҫת����%��_
				strSql.append(" and cphm1 like '" + cphid.replace("*", "%").replace("?", "_") + "'");
			} else if("05".equals(bussiness.trim())){//ǰ׺��ѯ
				if(cphid.startsWith("!")){//��ǰ׺��ѯ
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
		
		//��������
		if (jcdid != null && !"".equals(jcdid.trim())) {// ����ID�ж�
			String strjcdid[] = jcdid.split(",");// ������ƺ�
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
		//������������
		if (cplx != null && !"".equals(cplx.trim())) {
			String strcplx[] = cplx.split(",");
			if (strcplx.length == 1) {
				strSql.append(" and cplx1 = '"+cplx+"'");
			} else {// ������ƺ�
				strSql.append(" and cplx1 in ("+InterUtil.getStr(strcplx)+")");
			}
		}
		
		//ͼƬid����
		if (gcxh != null && !"".equals(gcxh.trim())) {
			String strgcxh[] = gcxh.split(",");
			if (strgcxh.length == 1) {
				strSql.append(" and tpid1 = '" + gcxh + "'");
			} else {// ���ͼƬid
				strSql.append(" and tpid1 in ("+InterUtil.getStr(strgcxh)+")");
			}
		}
		
		//����
		if (cd != null && !"".equals(cd.trim())) {
			String strcd[] = cd.split(",");
			if (strcd.length == 1) {
				strSql.append(" and cdid = '" + cd + "'");
			} else {// �������
				strSql.append(" and cdid in (" + InterUtil.getStr(strcd) + ")");
			}
		}
		
		//����
		if (cb != null && !"".equals(cb.trim())) {
			strSql.append(" and cb like '%" + cb.trim() + "%'");
		}
		
		//�ٶ�
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
		
		//ʶ��ʱ������
		if (kssj != null && !"".equals(kssj.trim()) && jssj != null
				&& !"".equals(jssj.trim())) {
			// ��ʼ�ͽ�ֹʱ�����
			strSql.append(" and " + (sort != null && "scsj".equals(sort.trim().toLowerCase())?"scsj":"tgsj") + " between to_date('" + kssj + "','yyyy-MM-dd HH24:mi:ss') and to_date('" + jssj + "','yyyy-MM-dd HH24:mi:ss')");
		}
		
		//δʶ������ѯ,����Ƿ���ɱ�־λ0
        if("03".equals(bussiness.trim())){//δʶ���ѯ
        	strSql.append(" and qpsfwc = '0'");
        } else if("11".equals(bussiness.trim())){//��������ʶ���δʶ��
        	
        } else{
        	strSql.append(" and qpsfwc != '0'");//��ʶ���ѯ
        }
		return strSql.toString();		
	}
}