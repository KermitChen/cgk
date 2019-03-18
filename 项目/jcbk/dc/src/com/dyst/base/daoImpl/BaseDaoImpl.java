package com.dyst.base.daoImpl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.dyst.base.dao.BaseDao;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.utils.IntefaceUtils;

/**
 * 持久层基类
 *     主要实现方法：
 * 		   1.获取数据库时间
 *         2.添加数据
 *         3.更新数据
 *         4.删除数据
 *         5.根据Id获取数据
 */
@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {
	/**
	 * 注入 sessionFactory对象便于操作
	 */
	@Resource
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 获取数据库时间
	 * @param pattern 格式字符串
	 * @throws ParseException 
	 */
	public Date getSysDate(String pattern) throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		 Session session = getSessionFactory().getCurrentSession();
		 Query query = session.createSQLQuery(" select now() as sysdate ").addScalar("sysdate");
		 Date date = (Date)query.list().get(0); 
		 session.flush();
		 return sdf.parse(sdf.format(date));
	}
	
	/***************************************************************************
	 * 添加数据
	 **************************************************************************/
	public Serializable save(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(entity);
		session.flush();
		return id;
	}

	/***************************************************************************
	 * 更新数据
	 **************************************************************************/
	public void update(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
		session.flush();
	}

	/***************************************************************************
	 * 删除数据
	 **************************************************************************/
	public void delete(Object entity){
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
		session.flush();
	}
	
	/***************************************************************************
	 * 根据Id获取数据
	 **************************************************************************/
	public Object getObjectById(Class<?> entityClass, Serializable id){
		Session session = getSessionFactory().getCurrentSession();
		return session.get(entityClass, id);
	}
	
	
}