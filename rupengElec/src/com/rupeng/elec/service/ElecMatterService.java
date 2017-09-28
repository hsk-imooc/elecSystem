package com.rupeng.elec.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.IElecMatterDao;
import com.rupeng.elec.domain.ElecMatter;

/**
 * 代办事宜service
 * 
 * @author LeeGossHK
 * 
 */
@Transactional
public class ElecMatterService {

	private IElecMatterDao matterDao;

	public void setMatterDao(IElecMatterDao matterDao) {
		this.matterDao = matterDao;
	}

	/**
	 * 业务层添加待办事宜
	 * 
	 * 待办事宜的数据表中始终只有一条数据
	 * 
	 * @param matter
	 */
	public void add(ElecMatter matter) {
		/**
		 * 添加新的待办事宜需要删除之前的记录
		 */
		List<ElecMatter> matters = matterDao.findAll();
		if (matter != null && matters.size() > 0) {
			matterDao.deleteAll(matters);
		}
		// int i = 1 / 0;
		matterDao.addOrUpdate(matter);
	}

	/**
	 * 查询待办事宜
	 * 
	 * @return
	 */
	public ElecMatter findOne() {
		List<ElecMatter> matters = matterDao.findAll();
		if (matters != null && matters.size() > 0) {
			return matters.get(0);
		}
		return null;
	}
}
