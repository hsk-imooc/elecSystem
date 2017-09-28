package com.rupeng.elec.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rupeng.elec.dao.IDao;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.WhereAndValues;
import com.rupeng.elec.util.PageHibernateCallBack;

/**
 * 基本dao实现类
 * 
 * @author LeeGossHK
 * 
 */
public class BaseDao<T> implements IDao<T> {

	private HibernateTemplate hibernateTemplate;
	private Class beanClass;

	{
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		beanClass = (Class) actualTypeArguments[0];
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 添加和修改
	 */
	public void addOrUpdate(T bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	/**
	 * 通过id查找
	 */
	public T findById(Serializable id) {
		return (T) hibernateTemplate.get(beanClass, id);
	}

	/**
	 * 查找所有
	 */
	public List<T> findAll() {
		String hql = "from " + beanClass.getName();
		return hibernateTemplate.find(hql);
	}

	/**
	 * 根据条件查询
	 */
	public List<T> findByConditions(Conditions conditions) {
		WhereAndValues wv = conditions.createWhereAndValues();
		String hql = "from " + beanClass.getName() + wv.getWhere() + conditions.createOrderByString();
		Object[] values = wv.getValues();
		return hibernateTemplate.find(hql, values);
	}

	/**
	 * 批量添加和修改
	 */
	public void addOrUpdateAll(Collection<T> beans) {

		if (beans != null) {
			hibernateTemplate.saveOrUpdateAll(beans);
		}
	}

	/**
	 * 通过id删除
	 */
	public void deleteById(Serializable id) {
		T bean = findById(id);
		if (bean != null) {
			delete(bean);
		}
	}

	/**
	 * 通过id批量删除
	 */
	public void deleteAllByIds(Serializable... ids) {
		if (ids != null) {
			for (Serializable id : ids) {
				deleteById(id);
			}
		}
	}

	/**
	 * 通过对象删除
	 */
	public void delete(T bean) {
		hibernateTemplate.delete(bean);
	}

	/**
	 * 通过对象批量删除
	 */
	public void deleteAll(Collection<T> beans) {
		hibernateTemplate.deleteAll(beans);
	}

	/**
	 * 通过条件查询数量
	 */
	public int findCountByConditions(Conditions conditions) {

		WhereAndValues wv = conditions.createWhereAndValues();
		String where = wv.getWhere();
		Object[] values = wv.getValues();

		String hql = "select count(*) from " + beanClass.getName() + where;
		List<Long> list = hibernateTemplate.find(hql, values);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * 条件分页查询
	 */
	public List<T> findPageByConditions(int iDisplayStart, int iDisplayLength, Conditions conditions) {
		WhereAndValues wv = conditions.createWhereAndValues();

		String hql = "from " + beanClass.getName() + wv.getWhere();
		Object[] values = wv.getValues();

		System.out.println(hql);
		System.out.println(values);
		int firstResult = iDisplayStart;
		int maxResult = iDisplayLength;

		return hibernateTemplate.executeFind(new PageHibernateCallBack<T>(hql, values, firstResult, maxResult));
	}

	/**
	 * 通过多个id查找
	 */
	public List<T> findByIds(Serializable... ids) {
		if (ids != null) {
			List<T> list = new ArrayList<T>();
			for (Serializable id : ids) {
				T bean = findById(id);
				if (bean != null) {
					list.add(bean);
				}
			}
			return list;
		}
		return null;
	}

}
