package com.dyst.oracle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import com.dyst.entites.Jjhomd;

public class JjhomdOracle {
	public static List<Jjhomd> jjhomdList = new ArrayList<Jjhomd>();
	
	/**
	 * 加载一级红名单
	 */
	public static void getJjhomds(){
		//连接数据库
		DBConnectionManager dbCon = null;
		Connection connection = null;
		QueryRunner qr = new QueryRunner();
		try {
			dbCon = DBConnectionManager.getInstance();
			connection = dbCon.getConnection("db");
			//执行查询
			String sql = "select id, cphid, cplx from JJHOMD where jlzt = '002' and zt = '1'";
			jjhomdList = (List<Jjhomd>)qr.query(connection, sql, new BeanListHandler<Jjhomd>(Jjhomd.class));
			System.out.println("加载红名单长度:" + jjhomdList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				dbCon.freeConnection("db", connection);
			}
		}
	}
	
	/**
	 *隐藏一级红名单 
	 */
	@SuppressWarnings("finally")
	public static boolean hideJjhomd(String cphm, String cplx){
		boolean hideFlag = false;//是否隐藏红名单 true隐藏
		Jjhomd jjhomd = null;
		try {
			cplx = cplxToHpzl(cplx);
			//判断是否隐藏
			if(cphm != null && !"".equals(cphm.trim())
					&& cplx != null && !"".equals(cplx.trim())){
				for(int j = 0;j < jjhomdList.size();j++){
					jjhomd = jjhomdList.get(j);
					if(jjhomd != null && jjhomd.getCphid() != null && !"".equals(jjhomd.getCphid().trim())
						&& jjhomd.getCplx() != null && !"".equals(jjhomd.getCplx().trim())){
						//如果车牌号码及车牌颜色匹配，则需要隐藏，然后退出循环
						if(jjhomd.getCphid().trim().equals(cphm.trim())
							&& jjhomd.getCplx().trim().equals(cplx.trim())){
							hideFlag = true;//是否隐藏红名单 true隐藏
							break;
						}
					}
				}
			} else {
				hideFlag = false;//是否隐藏红名单 true隐藏
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return hideFlag;
		}
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
}