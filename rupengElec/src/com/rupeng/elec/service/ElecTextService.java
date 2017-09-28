package com.rupeng.elec.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.impl.ElecTextDaoImpl;
import com.rupeng.elec.domain.ElecText;

@Transactional
public class ElecTextService {

	private ElecTextDaoImpl textDao;

	public void setTextDao(ElecTextDaoImpl textDao) {
		this.textDao = textDao;
	}

	public void add(ElecText text) {
		textDao.addOrUpdate(text);
	}

	public ElecText findById(Serializable id) {
		return textDao.findById(id);
	}

}
