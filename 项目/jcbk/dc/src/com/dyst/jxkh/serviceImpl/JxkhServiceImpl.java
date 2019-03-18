package com.dyst.jxkh.serviceImpl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.dispatched.entities.DisReceive;
import com.dyst.jxkh.dao.JxkhDao;
import com.dyst.jxkh.entities.BkckEntity;
import com.dyst.jxkh.entities.CzfkEntity2;
import com.dyst.jxkh.entities.YjqsEntity2;
import com.dyst.jxkh.entities.YjqsEntity22;
import com.dyst.jxkh.entities.ZlqsEntity2;
import com.dyst.jxkh.service.JxkhService;
import com.dyst.systemmanage.entities.Department;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.excel.ExportExcelUtil;
import com.dyst.utils.excel.bean.bktj.BkckExcelBean;
import com.dyst.utils.excel.bean.bktj.BktjExcelBean;
import com.dyst.utils.excel.bean.yj.YjqsExcelBean;
import com.dyst.utils.excel.bean.yj.YjtjExcelBean;

@Service(value="jxkjService")
public class JxkhServiceImpl implements JxkhService{
	
	@Autowired
	private JxkhDao jxkhDao;
	
	/**
	 * ==============================布控统计查询=================================
	 * @throws ParseException 
	 */
	//布控统计页面点击查询按钮，执行的查询方法
	@SuppressWarnings("unchecked")
	public Map<String, List> getBktj(String startTime, String endTime, String deptId) throws Exception{
		//存放结果
		Map<String, List> msp = new HashMap<String, List>();
		
		//本市布控数
		StringBuffer sql1 = new StringBuffer();
		sql1.append(" select BKDL, BJLX, count(*) as NUM from BKSQ where XXLY='0' and BY4='0' ");
		Map<String,Object> params1 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql1.append(" and BKSJ >= :startTime ");
			params1.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql1.append(" and BKSJ <= :endTime ");
			params1.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql1.append(" group by BKDL, BJLX ");
		List list_bdbk = jxkhDao.getObjects(sql1.toString(), params1);
		msp.put("bdbk", list_bdbk);
		
		//110布控数
		StringBuffer sql2 = new StringBuffer();
		sql2.append(" select BKDL, BJLX, count(*) as NUM from BKSQ where XXLY='0' and BY4='1' ");
		Map<String,Object> params2 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql2.append(" and BKSJ >= :startTime ");
			params2.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql2.append(" and BKSJ <= :endTime ");
			params2.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql2.append(" group by BKDL, BJLX ");
		List list_110bk = jxkhDao.getObjects(sql2.toString(), params2);
		msp.put("110bk", list_110bk);
		
		//联动布控数
		StringBuffer sql3 = new StringBuffer();
		sql3.append(" select BKDL, BJLX, count(*) as NUM from BKSQ where XXLY='2' ");
		Map<String,Object> params3 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql3.append(" and BKSJ >= :startTime ");
			params3.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql3.append(" and BKSJ <= :endTime ");
			params3.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql3.append(" group by BKDL, BJLX ");
		List list_ldbk = jxkhDao.getObjects(sql3.toString(), params3);
		msp.put("ldbk", list_ldbk);
		
		return msp;
	}
	
	/**
	 * 导出    布控统计  Excel
	 * @throws Exception 
	 */
	@Override
	public void excelExportForBktj(User user, BktjExcelBean bean,ServletOutputStream outputStream) throws Exception {
		ExportExcelUtil.excelExportForBktj(user, bean, outputStream);
	}
	
	
	/**
	 * 布控撤控统计查询，查询出列表
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BkckEntity> getBkck(String startTime, String endTime,
			String deptId, List<Department> khbmList) throws Exception {
		//百分比转换
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);
		
		//最后结果列表
		List<BkckEntity> list = new ArrayList<BkckEntity>();
		
		//加载预警统计时间  10分钟 过期
		String bkqsTime = "10";
		String ckqsTime = "10";
		String hql_jxkh = "select d.TYPE_SERIAL_NO, d.TYPE_DESC from DICTIONARY d where d.TYPE_CODE='JXKHTIME' and d.DELETE_FLAG != '1'";
		List<Object> jxkhList = jxkhDao.getObjects(hql_jxkh, null);
		for(Object o:jxkhList){
			if(o instanceof Object[]){
				if("BKQS".equals(((Object[])o)[0].toString())){
					bkqsTime = ((Object[])o)[1].toString();
				} else if("CKQS".equals(((Object[])o)[0].toString())){
					ckqsTime = ((Object[])o)[1].toString();
				}
			}
		}
		
		//布控通知总数
		List<DisReceive> list1 = new ArrayList<DisReceive>();
		Map<String,Object> params1 = new HashMap<String, Object>();
		StringBuilder hql1 =new StringBuilder();
		hql1.append("from DisReceive d where 1=1");
		if(StringUtils.isNotBlank(startTime)){
			hql1.append(" and d.xfsj >=:startTime");
			params1.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			hql1.append(" and d.xfsj <=:endTime");
			params1.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		hql1.append(" order by d.qrdw asc");
		list1 = jxkhDao.getDisReceive(hql1.toString(), params1);
		
		for(Department d:khbmList){
			//判断是否有指定部门，如指定，则必须为指定部门，否则跳过
			if(deptId != null && !"".equals(deptId.trim()) && !deptId.trim().equals(d.getDeptNo())){
				continue;
			}
			
			BkckEntity b = new BkckEntity();
			b.setDeptID(d.getDeptNo());
			b.setDeptName(d.getDeptName());
			//布控通知总数
			int temp = 0;
			int temp_asqs = 0;
			int temp_csqs = 0;
			int temp_wqs = 0;
			
			int temp_cktz = 0;
			int temp_cktz_asqs = 0;
			int temp_cktz_csqs = 0;
			int temp_cktz_wqs = 0;
			if(list1 != null && list1.size() >= 1){
				for(DisReceive dis:list1){
					//布控
					if("1".equals(dis.getBkckbz())){
						if(d.getDeptNo().equals(dis.getQrdw())){
							temp += 1;
							
							//及时签收数
							if("1".equals(dis.getQrzt()) && Math.abs((dis.getQrsj().getTime() - dis.getXfsj().getTime())/(1000*60*1.0)) <= Double.parseDouble(bkqsTime)){
								temp_asqs += 1;
							}
							//未及时签收
							if("1".equals(dis.getQrzt()) && Math.abs((dis.getQrsj().getTime() - dis.getXfsj().getTime())/(1000*60*1.0)) > Double.parseDouble(bkqsTime)){
								temp_csqs += 1;
							}
							//未签收数
							if("0".equals(dis.getQrzt())){
								temp_wqs += 1;
							}
						}
					}
					
					//撤控
					if("2".equals(dis.getBkckbz())){
						if(d.getDeptNo().equals(dis.getQrdw())){
							temp_cktz += 1;
							
							//及时签收数
							if("1".equals(dis.getQrzt()) && Math.abs((dis.getQrsj().getTime() - dis.getXfsj().getTime())/(1000*60*1.0)) <= Double.parseDouble(ckqsTime)){
								temp_cktz_asqs += 1;
							}
							//未及时签收
							if("1".equals(dis.getQrzt()) && Math.abs((dis.getQrsj().getTime() - dis.getXfsj().getTime())/(1000*60*1.0)) > Double.parseDouble(ckqsTime)){
								temp_cktz_csqs += 1;
							}
							//未签收数
							if("0".equals(dis.getQrzt())){
								temp_cktz_wqs += 1;
							}
						}
					}
				}
			}
			b.setBktzzs(Integer.toString(temp));
			b.setBktz_asqs(Integer.toString(temp_asqs));
			b.setBktz_csqs(Integer.toString(temp_csqs));
			b.setBktz_wqs(Integer.toString(temp_wqs));
			if(temp == 0){
				b.setBktz_zqsl(percentFormat.format(0));
			} else{
				b.setBktz_zqsl(percentFormat.format((double)(temp_asqs+temp_csqs)/(temp*1.0)));
			}
			if(temp == 0 || temp_asqs == 0){
				b.setBktz_asqsl(percentFormat.format(0));
			} else{
				b.setBktz_asqsl(percentFormat.format((double)temp_asqs/(temp*1.0)));
			}
			
			b.setCktzzs(Integer.toString(temp_cktz));
			b.setCktz_asqs(Integer.toString(temp_cktz_asqs));
			b.setCktz_csqs(Integer.toString(temp_cktz_csqs));
			b.setCktz_wqs(Integer.toString(temp_cktz_wqs));
			if(temp_cktz == 0){
				b.setCktz_zqsl(percentFormat.format(0));
			} else{
				b.setCktz_zqsl(percentFormat.format((double)(temp_cktz_asqs+temp_cktz_csqs)/(temp_cktz*1.0)));
			}
			if(temp_cktz == 0 || temp_cktz_asqs == 0){
				b.setCktz_asqsl(percentFormat.format(0));
			} else{
				b.setCktz_asqsl(percentFormat.format((double)temp_cktz_asqs/(temp_cktz*1.0)));
			}
			list.add(b);
		}
		return list;
	}

	/**
	 * 导出 布控撤控excel
	 */
	@Override
	public void excelExportForBkck(BkckExcelBean excelBean,
			ServletOutputStream outputStream) {
		ExportExcelUtil.excelExportForBkck(excelBean,outputStream);
	}
	
	
	/**
	 * 预警签收统计查询
	 */
	@SuppressWarnings("unchecked")
	public List<YjqsEntity22> getYjqs(String startTime, String endTime, String dwmc, List<Department> khbmList) throws Exception{
		List<YjqsEntity22> list = new ArrayList<YjqsEntity22>();
		
		//加载预警统计时间  2分钟 过期
		String yjqsTime = "2";
		String zlqsTime = "10";
		String zlfkTime = "24";
		String hql_jxkh = "select d.TYPE_SERIAL_NO, d.TYPE_DESC from DICTIONARY d where d.TYPE_CODE='JXKHTIME' and d.DELETE_FLAG != '1'";
		List<Object> jxkhList = jxkhDao.getObjects(hql_jxkh, null);
		for(Object o:jxkhList){
			if(o instanceof Object[]){
				if("YJQS".equals(((Object[])o)[0].toString())){
					yjqsTime = ((Object[])o)[1].toString();
				} else if("ZLQS".equals(((Object[])o)[0].toString())){
					zlqsTime = ((Object[])o)[1].toString();
				} else if("ZLFK".equals(((Object[])o)[0].toString())){
					zlfkTime = ((Object[])o)[1].toString();
				}
			}
		}
		
		//预警签收总数
		StringBuffer sql1 = new StringBuffer();
		Map<String,Object> params1 = new HashMap<String,Object>();
		sql1.append("select BJBM, count(*) as SUM from YJQS where 1=1 ");
		if(StringUtils.isNotBlank(startTime)){
			sql1.append(" and BJSJ >= :startTime");
			params1.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql1.append(" and BJSJ <= :endTime");
			params1.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(dwmc)){
			sql1.append(" and BJBM = :dwmc");
			params1.put("dwmc", dwmc.trim());
		}
		sql1.append(" group by BJBM order by BJBM asc");
		List<Object> list1 = jxkhDao.getObjects(sql1.toString(), params1);
		
		//查询的部门存在的情况下
		if(dwmc != null && !"".equals(dwmc.trim())){
			Department dept = null;
			for(Department d:khbmList){
				if(d.getDeptNo() != null && dwmc.trim().equals(d.getDeptNo().trim())){
					dept = d;
					break;
				}
			}
			if(dept != null){
				YjqsEntity22 y = new YjqsEntity22();//预警表单  条目实体
				y.setDeptID(dept.getDeptNo());
				y.setDeptName(dept.getDeptName());
				
				YjqsEntity2 yjqsEntity = new YjqsEntity2();
				yjqsEntity.setYjqs_qszs(0);
				for(int i=0;i < list1.size();i++){
					Object[] objArr = (Object[])list1.get(i);
					if(objArr[0] != null && objArr[0].toString().trim().equals(dept.getDeptNo().trim())){
						yjqsEntity.setYjqs_qszs(Integer.parseInt(((Object[])objArr)[1].toString()));
						break;
					}
				}
				
				y.setYjqsEntity(yjqsEntity);
				list.add(y);
			}
		} else {
			for(Department d:khbmList){
				YjqsEntity22 y = new YjqsEntity22();//预警表单  条目实体
				y.setDeptID(d.getDeptNo());
				y.setDeptName(d.getDeptName());
				
				YjqsEntity2 yjqsEntity = new YjqsEntity2();
				yjqsEntity.setYjqs_qszs(0);
				for(int i=0;i < list1.size();i++){
					Object[] objArr = (Object[])list1.get(i);
					if(objArr[0] != null && objArr[0].toString().trim().equals(d.getDeptNo().trim())){
						yjqsEntity.setYjqs_qszs(Integer.parseInt(((Object[])objArr)[1].toString()));
						break;
					}
				}
				
				y.setYjqsEntity(yjqsEntity);
				list.add(y);
			}
		}
		
		//预警签收  按时签收
		StringBuffer sql2 = new StringBuffer();
		Map<String,Object> params2 = new HashMap<String,Object>();
		sql2.append("select BJBM, count(*) as SUM from YJQS where 1=1 and QRZT != '0' and TIMESTAMPDIFF(SECOND, BJSJ, QRSJ) <= " + (Integer.parseInt(yjqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql2.append(" and BJSJ >= :startTime");
			params2.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql2.append(" and BJSJ <= :endTime");
			params2.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql2.append(" group by BJBM order by BJBM asc ");
		List<Object> list_Jsyjqs = jxkhDao.getObjects(sql2.toString(), params2);
		
		//预警超时签收数
		StringBuffer sql3 = new StringBuffer();
		Map<String,Object> params3 = new HashMap<String,Object>();
		sql3.append("select BJBM, count(*) as SUM from YJQS where 1=1 and QRZT != '0' and TIMESTAMPDIFF(SECOND, BJSJ, QRSJ) > " + (Integer.parseInt(yjqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql3.append(" and BJSJ >= :startTime");
			params3.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql3.append(" and BJSJ <= :endTime");
			params3.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql3.append(" group by BJBM order by BJBM asc ");
		List<Object> list_Csyjqs = jxkhDao.getObjects(sql3.toString(), params3);
		
		//预警未签收数
		StringBuffer sql4 = new StringBuffer();
		Map<String,Object> params4 = new HashMap<String,Object>();
		sql4.append("select BJBM, count(*) as SUM from YJQS where 1=1 and QRZT = '0'");
		if(StringUtils.isNotBlank(startTime)){
			sql4.append(" and BJSJ >= :startTime");
			params4.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql4.append(" and BJSJ <= :endTime");
			params4.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql4.append(" group by BJBM order by BJBM asc ");
		List<Object> list_yj_wqs = jxkhDao.getObjects(sql4.toString(), params4);
		
		//预警指令签收总数
		StringBuffer sql5 = new StringBuffer();
		sql5.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where 1=1 ");
		Map<String,Object> params5 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql5.append(" and ZLSJ >= :startTime");
			params5.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql5.append(" and ZLSJ <= :endTime");
			params5.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql5.append(" group by ZLBM order by ZLBM asc");
		List<Object> list2 = jxkhDao.getObjects(sql5.toString(), params5);
		
		//指令按时签收
		StringBuffer sql6 = new StringBuffer();
		Map<String, Object> params6 = new HashMap<String, Object>();
		sql6.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where QSZT = '1' and TIMESTAMPDIFF(SECOND, ZLSJ, QSSJ) <= "+ (Integer.parseInt(zlqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql6.append(" and ZLSJ >= :startTime");
			params6.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql6.append(" and ZLSJ <= :endTime");
			params6.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql6.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_zl_asqs = jxkhDao.getObjects(sql6.toString(), params6);
		
		//指令..超时签收
		StringBuffer sql7 = new StringBuffer();
		Map<String,Object> params7 = new HashMap<String,Object>();
		sql7.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where QSZT = '1' and TIMESTAMPDIFF(SECOND, ZLSJ, QSSJ) > "+ (Integer.parseInt(zlqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql7.append(" and ZLSJ >= :startTime");
			params7.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql7.append(" and ZLSJ <= :endTime");
			params7.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql7.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_zl_wasqs = jxkhDao.getObjects(sql7.toString(), params7);
		
		//指令未签收
		StringBuffer sql8 = new StringBuffer();
		Map<String,Object> params8 = new HashMap<String,Object>();
		sql8.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where QSZT = '0' ");
		if(StringUtils.isNotBlank(startTime)){
			sql8.append(" and ZLSJ >= :startTime");
			params8.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql8.append(" and ZLSJ <= :endTime");
			params8.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql8.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_zl_wqs = jxkhDao.getObjects(sql8.toString(), params8);
		
		//处置反馈
		StringBuffer sql9 = new StringBuffer();
		sql9.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where 1=1 ");
		Map<String,Object> params9 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql9.append(" and ZLSJ >= :startTime");
			params9.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql9.append(" and ZLSJ <= :endTime");
			params9.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql9.append(" group by ZLBM order by ZLBM asc");
		List<Object> list3 = jxkhDao.getObjects(sql9.toString(), params9);
		
		//按时 反馈
		StringBuffer sql10 = new StringBuffer();
		Map<String,Object> params10 = new HashMap<String,Object>();
		sql10.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where FKZT = '1' and TIMESTAMPDIFF(SECOND, ZLSJ, FKSJ) <= " + (Integer.parseInt(zlfkTime) * 60 * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql10.append(" and ZLSJ >= :startTime");
			params10.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql10.append(" and ZLSJ <= :endTime");
			params10.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql10.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_asfk = jxkhDao.getObjects(sql10.toString(), params10);
		
		//未按时反馈
		StringBuffer sql11 = new StringBuffer();
		Map<String,Object> params11 = new HashMap<String,Object>();
		sql11.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where FKZT = '1' and TIMESTAMPDIFF(SECOND, ZLSJ, FKSJ) > " + (Integer.parseInt(zlfkTime) * 60 * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql11.append(" and ZLSJ >= :startTime");
			params11.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql11.append(" and ZLSJ <= :endTime");
			params11.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql11.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_wasfk = jxkhDao.getObjects(sql11.toString(), params11);
		
		//未反馈
		StringBuffer sql12 = new StringBuffer();
		Map<String,Object> params12 = new HashMap<String,Object>();
		sql12.append("select ZLBM, count(*) as SUM from INSTRUCTION_SIGN where FKZT = '0' ");
		if(StringUtils.isNotBlank(startTime)){
			sql12.append(" and ZLSJ >= :startTime");
			params12.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql12.append(" and ZLSJ <= :endTime");
			params12.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sql12.append(" group by ZLBM order by ZLBM asc");
		List<Object> list_wfk = jxkhDao.getObjects(sql12.toString(), params12);
		
		for(YjqsEntity22 y2:list){
			String deptID = y2.getDeptID();
			
			//预警
			YjqsEntity2 y = y2.getYjqsEntity();
			y.setYjqs_asqs(0);
			y.setYjqs_csqs(0);
			y.setYjqs_wqs(0);
			
			for(int i=0;i < list_Jsyjqs.size();i++){//及时
				Object[] objArr = (Object[])list_Jsyjqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					y.setYjqs_asqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_Csyjqs.size();i++){//超时
				Object[] objArr = (Object[])list_Csyjqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					y.setYjqs_csqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_yj_wqs.size();i++){//未签收
				Object[] objArr = (Object[])list_yj_wqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					y.setYjqs_wqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			//指令
			ZlqsEntity2 z = new ZlqsEntity2();
			z.setZlqs_zlzs(0);
			z.setZlqs_asqs(0);
			z.setZlqs_csqs(0);
			z.setZlqs_wqs(0);
			
			for(int i=0;i < list2.size();i++){//总数
				Object[] objArr = (Object[])list2.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					z.setZlqs_zlzs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_zl_asqs.size();i++){//按时签收
				Object[] objArr = (Object[])list_zl_asqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					z.setZlqs_asqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_zl_wasqs.size();i++){//超时签收
				Object[] objArr = (Object[])list_zl_wasqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					z.setZlqs_csqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_zl_wqs.size();i++){//未签收
				Object[] objArr = (Object[])list_zl_wqs.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					z.setZlqs_wqs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			y2.setZlqsEntity(z);
			
			//指令反馈
			CzfkEntity2 c = new CzfkEntity2();
			c.setCzfk_fkzs(0);
			c.setCzfk_asfk(0);
			c.setCzfk_csfk(0);
			c.setCzfk_wfk(0);
			
			for(int i=0;i < list3.size();i++){//总数
				Object[] objArr = (Object[])list3.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					c.setCzfk_fkzs(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_asfk.size();i++){//按时反馈
				Object[] objArr = (Object[])list_asfk.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					c.setCzfk_asfk(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_wasfk.size();i++){//超时反馈
				Object[] objArr = (Object[])list_wasfk.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					c.setCzfk_csfk(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			
			for(int i=0;i < list_wfk.size();i++){//未反馈
				Object[] objArr = (Object[])list_wfk.get(i);
				if(objArr[0] != null && objArr[0].toString().trim().equals(deptID.trim())){
					c.setCzfk_wfk(Integer.parseInt(((Object[])objArr)[1].toString()));
					break;
				}
			}
			y2.setCzfkEntity(c);
		}
		
		return list;
	}
	
	/**
	 * 导出    预警签收   Excel
	 */
	@Override
	public void excelExportForYjqs(YjqsExcelBean bean,
			ServletOutputStream outputStream) throws Exception {
		ExportExcelUtil.exportExcelForYjqs(bean,outputStream);
	}
	
	/*=====================================预警统计查询============================*/
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List> getYjtj(String startTime, String endTime, String Check_tjbm) throws Exception {
		//存放结果
		Map<String, List> msp = new HashMap<String, List>();
		
		//加载预警统计时间  2分钟 过期
		String yjqsTime = "2";
//		String zlqsTime = "10";
		String zlfkTime = "24";
		String hql_jxkh = "select d.TYPE_SERIAL_NO, d.TYPE_DESC from DICTIONARY d where d.TYPE_CODE='JXKHTIME' and d.DELETE_FLAG != '1'";
		List<Object> jxkhList = jxkhDao.getObjects(hql_jxkh, null);
		for(Object o:jxkhList){
			if(o instanceof Object[]){
				if("YJQS".equals(((Object[])o)[0].toString())){
					yjqsTime = ((Object[])o)[1].toString();
				} else if("ZLQS".equals(((Object[])o)[0].toString())){
//					zlqsTime = ((Object[])o)[1].toString();
				} else if("ZLFK".equals(((Object[])o)[0].toString())){
					zlfkTime = ((Object[])o)[1].toString();
				}
			}
		}
		
		//给类别加载详情1..报警总数
		StringBuffer sql1 = new StringBuffer();
		sql1.append(" select BJDL, BJLX, count(*) as SUM from YJQS where 1=1 ");
		Map<String,Object> params1 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql1.append(" and BJSJ >= :startTime");
			params1.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql1.append(" and BJSJ <= :endTime");
			params1.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql1.append(" and BJBM = :deptID");
			params1.put("deptID", Check_tjbm);
		}
		sql1.append(" group by BJDL, BJLX ");
		List<Object> list_bjzs = jxkhDao.getObjects(sql1.toString(), params1);
		msp.put("bjzs", list_bjzs);
		
		//给类别添加详情..及时签收数
		StringBuffer sql2 = new StringBuffer();
		Map<String,Object> params2 = new HashMap<String, Object>();
		sql2.append("select BJDL, BJLX, count(*) as SUM from YJQS where QRZT != '0' and TIMESTAMPDIFF(SECOND, BJSJ, QRSJ) <= " + (Integer.parseInt(yjqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql2.append(" and BJSJ >= :startTime");
			params2.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql2.append(" and BJSJ <=:endTime");
			params2.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql2.append(" and BJBM = :deptID");
			params2.put("deptID", Check_tjbm);
		}
		sql2.append(" group by BJDL, BJLX ");
		List<Object> list_bjjsqs = jxkhDao.getObjects(sql2.toString(), params2);
		msp.put("bjjsqs", list_bjjsqs);
		
		//给类别添加详情..超时签收数
		StringBuffer sql3 = new StringBuffer();
		Map<String,Object> params3 = new HashMap<String, Object>();
		sql3.append("select BJDL, BJLX, count(*) as SUM from YJQS where QRZT != '0' and TIMESTAMPDIFF(SECOND, BJSJ, QRSJ) > " + (Integer.parseInt(yjqsTime) * 60));
		if(StringUtils.isNotBlank(startTime)){
			sql3.append(" and BJSJ >= :startTime");
			params3.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql3.append(" and BJSJ <=:endTime");
			params3.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql3.append(" and BJBM = :deptID");
			params3.put("deptID", Check_tjbm);
		}
		sql3.append(" group by BJDL, BJLX ");
		List<Object> list_bjcsqs = jxkhDao.getObjects(sql3.toString(), params3);
		msp.put("bjcsqs", list_bjcsqs);
		
		//给类别添加详情..未签收数
//		StringBuffer sql33 = new StringBuffer();
//		Map<String,Object> params33 = new HashMap<String, Object>();
//		sql33.append("select BJDL, BJLX, count(*) as SUM from YJQS where QRZT = '0' ");
//		if(StringUtils.isNotBlank(startTime)){
//			sql33.append(" and BJSJ >= :startTime");
//			params33.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
//		}
//		if(StringUtils.isNotBlank(endTime)){
//			sql33.append(" and BJSJ <=:endTime");
//			params33.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
//		}
//		if(StringUtils.isNotBlank(Check_tjbm)){
//			sql33.append(" and BJBM = :deptID");
//			params33.put("deptID", Check_tjbm);
//		}
//		sql33.append(" group by BJDL, BJLX ");
//		List<Object> list_bjwqs = jxkhDao.getObjects(sql33.toString(), params33);
//		msp.put("bjwqs", list_bjwqs);
		
		//给类别添加详情..有效数
		StringBuffer sql4 = new StringBuffer();
		sql4.append(" select BJDL, BJLX, count(*) as SUM from YJQS where QRZT not in('0', '2') ");
		Map<String,Object> params4 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql4.append(" and BJSJ >= :startTime");
			params4.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql4.append(" and BJSJ <=:endTime");
			params4.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql4.append(" and BJBM = :deptID");
			params4.put("deptID", Check_tjbm);
		}
		sql4.append(" group by BJDL, BJLX ");
		List<Object> list_bjyxs = jxkhDao.getObjects(sql4.toString(), params4);
		msp.put("bjyxs", list_bjyxs);
		
		//给类别添加详情..无效数
		StringBuffer sql5 = new StringBuffer();
		sql5.append(" select BJDL, BJLX, count(*) as SUM from YJQS where QRZT = '2' ");
		Map<String,Object> params5 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql5.append(" and BJSJ >= :startTime");//上传时间
			params5.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql5.append(" and BJSJ <=:endTime");
			params5.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql5.append(" and BJBM = :deptID");
			params5.put("deptID", Check_tjbm);
		}
		sql5.append(" group by BJDL, BJLX ");
		List<Object> list_bjwxs = jxkhDao.getObjects(sql5.toString(), params5);
		msp.put("bjwxs", list_bjwxs);

		//给类别添加详情..已下达拦截指令数
		StringBuffer sql6 = new StringBuffer();
		sql6.append(" select yj.BJDL, yj.BJLX, count(*) as SUM from YJQS yj, INSTRUCTION ins, INSTRUCTION_SIGN in_sign where yj.QSID=ins.QSID and ins.ID=in_sign.ZLID and in_sign.ZLBM in(select DEPT_NO from DEPARTMENT where JXKH='1') ");
		Map<String,Object> params6 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql6.append(" and yj.BJSJ >= :startTime");
			params6.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql6.append(" and yj.BJSJ <= :endTime");
			params6.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}		
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql6.append(" and yj.BJBM = :deptID");
			params6.put("deptID", Check_tjbm);
		}
		sql6.append(" group by yj.BJDL, yj.BJLX ");
		List<Object> list_zlzs = jxkhDao.getObjects(sql6.toString(), params6);
		msp.put("zlzs", list_zlzs);
		
		//添加详情..拦截指令及时反馈数
		StringBuffer sql7 = new StringBuffer();
		sql7.append(" select yj.BJDL, yj.BJLX, count(*) as SUM from YJQS yj, INSTRUCTION ins, INSTRUCTION_SIGN in_sign where yj.QSID=ins.QSID and ins.ID=in_sign.ZLID and in_sign.ZLBM in(select DEPT_NO from DEPARTMENT where JXKH='1') and in_sign.FKZT='1' and TIMESTAMPDIFF(SECOND, in_sign.ZLSJ, in_sign.FKSJ) <= " + (Integer.parseInt(zlfkTime) * 60 * 60));
		Map<String,Object> params7 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql7.append(" and yj.BJSJ >= :startTime");
			params7.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql7.append(" and yj.BJSJ <= :endTime");
			params7.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql7.append(" and yj.BJBM = :deptID");
			params7.put("deptID", Check_tjbm);
		}
		sql7.append(" group by yj.BJDL, yj.BJLX ");
		List<Object> list_zljsfks = jxkhDao.getObjects(sql7.toString(), params7);
		msp.put("zljsfks", list_zljsfks);
		
		//添加详情..拦截指令未及时反馈数
		StringBuffer sql8 = new StringBuffer();
		sql8.append(" select yj.BJDL, yj.BJLX, count(*) as SUM from YJQS yj, INSTRUCTION ins, INSTRUCTION_SIGN in_sign where yj.QSID=ins.QSID and ins.ID=in_sign.ZLID and in_sign.ZLBM in(select DEPT_NO from DEPARTMENT where JXKH='1') and in_sign.FKZT='1' and TIMESTAMPDIFF(SECOND, in_sign.ZLSJ, in_sign.FKSJ) > " + (Integer.parseInt(zlfkTime) * 60 * 60));
		Map<String,Object> params8 = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(startTime)){
			sql8.append(" and yj.BJSJ >= :startTime");
			params8.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql8.append(" and yj.BJSJ <= :endTime");
			params8.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(Check_tjbm)){
			sql8.append(" and ins.ZLXFBM = :deptID");
			params8.put("deptID", Check_tjbm);
		}
		sql8.append(" group by yj.BJDL, yj.BJLX ");
		List<Object> list_zlwjsfks = jxkhDao.getObjects(sql8.toString(), params8);
		msp.put("zlwjsfks", list_zlwjsfks);
		
//		//添加详情..拦截指令未反馈数
//		StringBuffer sql9 = new StringBuffer();
//		sql9.append(" select yj.BJDL, yj.BJLX, count(*) as SUM from YJQS yj, INSTRUCTION ins, INSTRUCTION_SIGN in_sign where yj.QSID=ins.QSID and ins.ID=in_sign.ZLID and in_sign.FKZT='0' ");
//		Map<String,Object> params9 = new HashMap<String,Object>();
//		if(StringUtils.isNotBlank(startTime)){
//			sql9.append(" and yj.BJSJ >= :startTime");
//			params9.put("startTime", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
//		}
//		if(StringUtils.isNotBlank(endTime)){
//			sql9.append(" and yj.BJSJ <= :endTime");
//			params9.put("endTime", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
//		}
//		if(StringUtils.isNotBlank(Check_tjbm)){
//			sql9.append(" and ins.ZLXFBM = :deptID");
//			params9.put("deptID", Check_tjbm);
//		}
//		sql9.append(" group by yj.BJDL, yj.BJLX ");
//		List<Object> list_zlwfks = jxkhDao.getObjects(sql9.toString(), params9);
//		msp.put("zlwfks", list_zlwfks); 
		 
		return msp;
	}

	//===========导出     预警统计   Excel=========================
	@Override
	public void excelExportForYjtj(User user, YjtjExcelBean bean, ServletOutputStream outputStream) throws Exception {
		ExportExcelUtil.excelExportForYjtj(user, bean, outputStream);
	}
	
	
	/**
	 * 应用成效统计查询
	 * @throws Exception 
	 */
	public void excelExportForYycx(User user, String kssj, String jssj, List<Dictionary> dicList, List listSs, List listSh, List listwf, ServletOutputStream outputStream) throws Exception{
		ExportExcelUtil.excelExportForYycx(user, kssj, jssj, dicList, listSs, listSh, listwf, outputStream);
	}
	
	public List<Department> getDeptList(String hql, Map<String, Object> params) {
		return jxkhDao.getDeptList(hql, params);
	}
}