package com.dyst.earlyWarning.daoimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dyst.base.daoImpl.BaseDaoImpl;
import com.dyst.base.utils.PageResult;
import com.dyst.base.utils.QueryHelper;
import com.dyst.earlyWarning.dao.EWarningDao;
import com.dyst.earlyWarning.entities.EWRecieve;
import com.dyst.earlyWarning.entities.EWarning;
import com.dyst.earlyWarning.entities.Instruction;
import com.dyst.earlyWarning.entities.InstructionSign;

@Repository("eWarningDao")
public class EWarningDaoImpl extends BaseDaoImpl implements EWarningDao {
	// 根据ID 查询预警信息
	public EWarning findObjectById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM EWarning where bjxh = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (EWarning) query.uniqueResult();
	}

	// 根据ID 查询预警签收
	public EWRecieve findEWRecieveById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM EWRecieve where qsid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (EWRecieve) query.uniqueResult();
	}
	
	// 根据ID 查询指令签收
	public Instruction findInstructionByQsId(Serializable qsid) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM Instruction where qsid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, qsid);
		return (Instruction) query.uniqueResult();
	}
	
	// 根据ID 查询指令签收
	public InstructionSign findInstructionSignById(Serializable id) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "FROM InstructionSign where id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		return (InstructionSign) query.uniqueResult();
	}

	// 多条件语句查询
	public List findObjects(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	// 查询预警签收
	public List<EWRecieve> findEWRecieve(String hql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	// 使用 QueryHelper 多条件查询
	public List<EWRecieve> findObjects(QueryHelper queryHelper) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	// 带分页的多条件查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		if (pageNo < 1)
			pageNo = 1;

		query.setFirstResult((pageNo - 1) * pageSize);// 设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		// 获取总记录数
		Query queryCount = session.createQuery(queryHelper.getQueryCountHql());
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long) queryCount.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

	// 带分页的多条件查询
	public PageResult getPageResult(String hql, List<Object> parameters, int pageNo, int pageSize) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		if (pageNo < 1)
			pageNo = 1;

		long totalCount = new Long(query.list().size());
		query.setFirstResult((pageNo - 1) * pageSize);// 设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

	// 多条件语句查询
	public List<Map> findList(String sql, List<Object> parameters) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
}
