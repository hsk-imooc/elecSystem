package com.rupeng.elec.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.IElecFunctionDao;
import com.rupeng.elec.domain.ElecFunction;

/**
 * 权限service
 * 
 * @author LeeGossHK
 * 
 */
@Transactional
public class ElecFunctionService {

	private IElecFunctionDao functionDao;

	public void setFunctionDao(IElecFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	/**
	 * 获得所有权限列表
	 * 
	 * @return
	 */
	public List<ElecFunction> list() {
		return functionDao.findAll();
	}

	/**
	 * 通过多个id组成的字符串查找
	 * 
	 * @param functions
	 * @return
	 */
	public List<ElecFunction> findByIds(String functions) {
		String[] functionIds = functions.split(",");
		ArrayList<ElecFunction> functionList = new ArrayList<ElecFunction>();

		for (String functionId : functionIds) {
			if (functionId != null && functionId.trim().length() > 0) {
				ElecFunction function = functionDao.findById(functionId);
				functionList.add(function);
			}
		}
		return functionList;
	}

}
