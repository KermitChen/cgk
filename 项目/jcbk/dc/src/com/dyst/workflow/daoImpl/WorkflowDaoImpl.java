package com.dyst.workflow.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.workflow.dao.WorkflowDao;

@Repository("workflowDao")
public class WorkflowDaoImpl extends BaseDaoImpl implements WorkflowDao {
	
	//根据typeCode和serialNo 查询值
	public String findKeyById(String typeCode,String id) {
		String sql="select TYPE_DESC NAME from DICTIONARY where TYPE_CODE=? and TYPE_SERIAL_NO=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter(0, typeCode);
		query.setParameter(1, id);
		return (String) query.uniqueResult();
	}
	
}
