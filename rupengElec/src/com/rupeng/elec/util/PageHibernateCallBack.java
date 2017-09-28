package com.rupeng.elec.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * 封装hibernate回调，用户分页查询
 * 
 * @author LeeGossHK
 * 
 * @param <T>
 */
public class PageHibernateCallBack<T> implements HibernateCallback<List<T>> {

	// 查询的条件语句
	private String hql;
	// 查询的条件语句的参数
	private Object[] values;
	// 当前页中第一条数据在数据库中的索引
	private int firstResult;
	// 每页显示的数据条数
	private int maxResult;

	public PageHibernateCallBack(String hql, Object[] values, int firstResult,
			int maxResult) {
		super();
		this.hql = hql;
		this.values = values;
		this.firstResult = firstResult;
		this.maxResult = maxResult;
	}

	public List<T> doInHibernate(Session session) throws HibernateException,
			SQLException {
		/**
		 * 创建query对象
		 */
		Query query = session.createQuery(hql);
		/**
		 * 设置条件参数
		 */
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		/**
		 * 设置分页的参数
		 */
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		return query.list();
	}

}
