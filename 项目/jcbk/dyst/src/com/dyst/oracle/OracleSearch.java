package com.dyst.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.dyst.entites.Sbnew;
import com.dyst.utils.InterUtil;
/**
 * ʵ��Oralce��ѯ
 * @author likui
 */
public class OracleSearch {	
	/**
	 *��ѯ����1
	 * ��������: ��ѯ�ض��������͵���ʷ�켣<br>
	 * sql:��ѯ���   <br>
	 * from ��ʼ��ѯ��¼<br>
	 * pagsize:ÿҳ��ʾ��¼��<br>
	 * sort : �����ֶ�<br>
	 * sortType ��������<br>
	 * @throws SQLException <br>
	 * @return ʶ���¼�����б�
	 */	
	@SuppressWarnings("finally")
	public static List<Sbnew> TDCPGJCX(String sql, int from, int pagsize, String sort, String sortType) throws SQLException{
		//���������ַ�  ==============
//		sql = InterUtil.keyWordFilter(sql);
//		sort = InterUtil.keyWordFilter(sort);
//		sortType = InterUtil.keyWordFilter(sortType);
		
		//�������ݿ�
		DBConnectionManager dbCon = DBConnectionManager.getInstance();
		Connection connection = dbCon.getConnection("db");
		String orderStr="";//�����ֶ�������
		//ִ�в�ѯ
	    List<Sbnew> listTxsj = new ArrayList<Sbnew>();
		try {
			QueryRunner qr = new QueryRunner();
			//oracle���� ��ҳ��ѯ��ͬʱ���򣬳���ʹ����Ƕ��һ��Ĳ�ѯ��ʽ. ����������ʱЧ�ʽϵ�
			//��һ�ֽ����������������ֶν����������������������Ȱ����ֶ�����Ȼ����rownum,��������
			//��ʶ���ʱ��tpid1������������ͨ��ʱ��Ϊ�������У����ڴ�ʹ��tpid1��������
			if(!"".equals(sort.trim()) && !"".equals(sortType)){//��������sql
				orderStr = " order by tpid1 " + sortType ;
			}
			
			if(pagsize != 0){//�����ʼ��ѯ��¼��ÿҳ��ʾ��¼����Ϊ0����ҳ��ѯ
				sql = "SELECT * FROM (" + sql + " and ROWNUM <= " + (from + pagsize) + orderStr +") WHERE rn > " + from;
			} else{
				sql += orderStr;
			}

			listTxsj = (List<Sbnew>)qr.query(connection, sql, new BeanListHandler<Sbnew>(Sbnew.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Oracle���ݿ��ѯ�쳣");
		}finally{
			if(connection!=null){
				dbCon.freeConnection("db", connection);
			}
			
			//��ҳ��ѯ
            return listTxsj;
		}
	}
	
	/**
	 * ��������: ����ָ��sql��䷵�ز�ѯ��¼����
	 * @throws SQLException 
	 */	
	@SuppressWarnings("finally")
	public static Integer getTDCPGJCXCount(String sql) throws SQLException{	
		//�������ݿ�
		DBConnectionManager dbCon = DBConnectionManager.getInstance();
		Connection connection = dbCon.getConnection("db");
		Long count = null;
		try {
			QueryRunner qr = new QueryRunner();
			count = qr.query(connection, sql, new ResultSetHandler<Long>(){
				public Long handle(ResultSet rs) throws SQLException {
					if(rs.next()){
						Long len = rs.getLong(1);//����rs.getLong("count")
						return len;
					}
					return 0L;
				}
			});
		} catch (Exception e) {
			throw new SQLException("Oracle���ݿ��ѯ�쳣");
		}finally{
			if(connection != null){
				dbCon.freeConnection("db", connection);//�黹���ӳ�����
			}
			if(count == null){
				return 0;
			}
			return count.intValue();
		}
	}
	
	/**
	 * ����Oracleʶ���¼
	 * tpid1 ͼƬid
	 * cphm1 �޸ĺ�ĳ��ƺ���
	 * cplx1 �޸ĺ�ĳ�������
	 */
	@SuppressWarnings("finally")
	public static String updateOracleSb(String tpid1, String cphm1, String cplx1) throws Exception {
		DBConnectionManager dbCon = DBConnectionManager.getInstance();
		Connection connection = dbCon.getConnection("db");
		Statement st = null;
		String sql = "";
		String result = "1";
		try {
			//���ƺ��뼰ͼƬid����Ϊ��
			if(tpid1 != null && !"".equals(tpid1.trim()) && cphm1 != null && !"".equals(cphm1.trim())){
				//�����������null������Ϊ���ַ���
				if(cplx1 == null){
					cplx1 = "";
				}
				
				//�����ַ�����
				cphm1 = InterUtil.keyWordFilter(cphm1);
				cplx1 = InterUtil.keyWordFilter(cplx1);
				tpid1 = InterUtil.keyWordFilter(tpid1);
				
				//�±�ṹ
				sql = " update SB set cphm1 = '" + cphm1 + "', cplx1 = '" + cplx1 + "' where tpid1 = '" + tpid1 + "'";
				
				st = connection.createStatement();// �������ݿ�
			    st.execute(sql); // ���¼�¼
			} else{
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			throw new Exception(""+e.getMessage());
		} finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){
				dbCon.freeConnection("db", connection);
			}
			
			return result;
		}
	}
}