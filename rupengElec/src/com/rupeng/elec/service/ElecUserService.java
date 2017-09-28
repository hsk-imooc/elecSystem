package com.rupeng.elec.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.IElecUserDao;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.util.DataTablesPageBean;

/**
 * 用户service
 * 
 * @author LeeGossHK
 * 
 */
@Transactional
public class ElecUserService {

	private IElecUserDao userDao;

	public void setUserDao(IElecUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 业务层添加用户
	 * 
	 * @param user
	 */
	public void add(ElecUser user) {
		userDao.addOrUpdate(user);
	}

	/**
	 * 业务层检查account唯一性
	 * 
	 * @param account
	 * @return
	 */
	public Boolean checkAccountUnique(String account) {
		Conditions conditions = new Conditions();
		conditions.addCondition("account", account, Operator.EQUAL);
		List<ElecUser> list = userDao.findByConditions(conditions);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 业务层分页查询
	 * 
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param conditions
	 * @return
	 */
	public DataTablesPageBean<ElecUser> page(String sEcho, int iDisplayStart,
			int iDisplayLength, Conditions conditions) {

		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
		List<ElecUser> data = null;

		// 创建pageBean
		DataTablesPageBean<ElecUser> pageBean = new DataTablesPageBean<ElecUser>();

		// 准备pageBean需要封装的数据
		iTotalRecords = userDao.findCountByConditions(conditions);
		iTotalDisplayRecords = iTotalRecords;
		data = userDao.findPageByConditions(iDisplayStart, iDisplayLength,
				conditions);
		for (ElecUser elecUser : data) {
			System.out.println(elecUser);
		}

		// 封装pageBean
		pageBean.setsEcho(sEcho);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setData(data);

		return pageBean;
	}

	/**
	 * 业务层删除用户
	 * 
	 * @param user
	 */
	public void delete(ElecUser user) {
		userDao.delete(user);
	}

	/**
	 * 通过id查找用户
	 * 
	 * @param userId
	 * @return
	 */
	public ElecUser findById(String id) {
		return userDao.findById(id);
	}

	/**
	 * 业务层更新用户
	 * 
	 * @param user
	 */
	public void update(ElecUser user) {
		userDao.addOrUpdate(user);
	}

	/**
	 * 业务层登陆
	 * 
	 * @param conditions
	 * @return
	 */
	public ElecUser login(Conditions conditions) {
		List<ElecUser> list = userDao.findByConditions(conditions);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 条件查询用户
	 * 
	 * @param conditions
	 * @return
	 */
	public List<ElecUser> findByConditions(Conditions conditions) {
		return userDao.findByConditions(conditions);
	}

}
