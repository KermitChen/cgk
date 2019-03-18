package com.dyst.oracle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.dyst.entites.HkCloudConfig;

public class JcdOracle {
//	public static List<Jcd> jcdList = new ArrayList<Jcd>();
	public static List<HkCloudConfig> hkCloudConfigList = new ArrayList<HkCloudConfig>();
	
//	/**
//	 * 加载监测点对应的图片存放路径
//	 */
//	public static void getJcds(){
//		//连接数据库
//		DBConnectionManager dbCon = null;
//		Connection connection = null;
//		QueryRunner qr = new QueryRunner();
//		try {
//			dbCon = DBConnectionManager.getInstance();
//			connection = dbCon.getConnection("db");
//			
//			//执行查询
//			String sql = "select ID, TPCFLJ from JCD where TPCFLJ is not null";
//			jcdList = (List<Jcd>)qr.query(connection, sql, new BeanListHandler<Jcd>(Jcd.class));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(connection != null){
//				dbCon.freeConnection("db", connection);
//			}
//		}
//	}
	
	/**
	 * 加载海康云配置
	 */
	public static void getHkCloudConfig(){
		//连接数据库
		DBConnectionManager dbCon = null;
		Connection connection = null;
		QueryRunner qr = new QueryRunner();
		try {
			dbCon = DBConnectionManager.getInstance();
			connection = dbCon.getConnection("db");
			
			//执行查询
			String sql = " select id, ip, port from HK_CLOUD_CONFIG ";
			hkCloudConfigList = (List<HkCloudConfig>)qr.query(connection, sql, new BeanListHandler<HkCloudConfig>(HkCloudConfig.class));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				dbCon.freeConnection("db", connection);
			}
		}
	}
	
//	/**
//	 * 根据监测点id返回图片存放路径
//	 */
//	public static String getTpcflj(String jcdid){
//		if(jcdid == null || "".equals(jcdid)){
//			return "";
//		}
//
//		for(int i = 0;i < jcdList.size();i++){
//			Jcd jcd = jcdList.get(i);
//			if(jcdid.equals(jcd.getId())){
//				return jcd.getTpcflj();
//			}
//		}
//		return "";
//	}
	
	/**
	 * 根据图片服务id返回图片存放路径
	 */
	public static String getTpcfljById(String id) {
		if(id == null || "".equals(id)){
			return "";
		}

		HkCloudConfig hkCloudConfig = null;
		for(int i=0;i < hkCloudConfigList.size();i++){
			hkCloudConfig = hkCloudConfigList.get(i);
			if(id.equals(hkCloudConfig.getId())){
				return "http://" + hkCloudConfig.getIp() + ":" + hkCloudConfig.getPort() + "/";
			}
		}
		return "";
	}
}