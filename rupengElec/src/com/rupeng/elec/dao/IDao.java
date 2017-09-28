package com.rupeng.elec.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.rupeng.elec.dao.util.Conditions;

/**
 * 顶层接口
 * 
 * @author LeeGossHK
 * 
 */
public interface IDao<T> {

	/**
	 * 添加和修改
	 * 
	 * @param bean
	 */
	void addOrUpdate(T bean);

	/**
	 * 批量添加和修改
	 * 
	 * @param beans
	 */
	void addOrUpdateAll(Collection<T> beans);

	/**
	 * 通过id查找
	 * 
	 * @param id
	 * @return
	 */
	T findById(Serializable id);

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	List<T> findAll();

	List<T> findByConditions(Conditions conditions);

	/**
	 * 通过id删除
	 * 
	 * @param id
	 */
	void deleteById(Serializable id);

	/**
	 * 通过id批量产出
	 * 
	 * @param ids
	 */
	void deleteAllByIds(Serializable... ids);

	/**
	 * 通过对象删除
	 * 
	 * @param bean
	 */
	void delete(T bean);

	/**
	 * 通过对象批量删除
	 * 
	 * @param beans
	 */
	void deleteAll(Collection<T> beans);

	/**
	 * 通过条件查询数量
	 * 
	 * @param conditions
	 * @return
	 */
	int findCountByConditions(Conditions conditions);

	/**
	 * 条件分页查询
	 * 
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param conditions
	 * @return
	 */
	List<T> findPageByConditions(int iDisplayStart, int iDisplayLength,
			Conditions conditions);

	/**
	 * 通过多个id查找
	 * 
	 * @param ids
	 * @return
	 */
	List<T> findByIds(Serializable... ids);
}
