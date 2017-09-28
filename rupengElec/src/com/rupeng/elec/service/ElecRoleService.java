package com.rupeng.elec.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.IElecRoleDao;
import com.rupeng.elec.domain.ElecRole;

/**
 * 角色service
 * 
 * @author LeeGossHK
 * 
 */
@Transactional
public class ElecRoleService {

	private IElecRoleDao roleDao;

	public void setRoleDao(IElecRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 获得所有角色列表
	 * 
	 * @return
	 */
	public List<ElecRole> list() {
		return roleDao.findAll();
	}

	/**
	 * 通过id查找角色
	 * 
	 * @param roleId
	 * @return
	 */
	public ElecRole findById(String id) {
		return roleDao.findById(id);
	}

	/**
	 * 更新role
	 * 
	 * @param role
	 */
	public void update(ElecRole role) {
		roleDao.addOrUpdate(role);
	}

	/**
	 * 通过多个id查找角色
	 * 
	 * @param roles
	 * @return
	 */
	public List<ElecRole> findByIds(String[] roles) {
		return roleDao.findByIds(roles);
	}
}
