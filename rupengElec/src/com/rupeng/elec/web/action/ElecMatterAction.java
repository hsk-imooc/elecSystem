package com.rupeng.elec.web.action;

import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rupeng.elec.domain.ElecMatter;
import com.rupeng.elec.service.ElecMatterService;

/**
 * 待办事宜action
 * 
 * @author LeeGossHK
 * 
 */
public class ElecMatterAction extends ActionSupport implements
		ModelDriven<ElecMatter> {

	private ElecMatterService matterService;
	private ElecMatter matter = new ElecMatter();

	public ElecMatter getModel() {
		return matter;
	}

	public void setMatterService(ElecMatterService matterService) {
		this.matterService = matterService;
	}

	/**
	 * 代办事宜首页
	 * 
	 * @return
	 */
	public String home() {
		matter = matterService.findOne();
		return "home";
	}

	/**
	 * 添加待办事宜的方法
	 * 
	 * @return
	 */
	public String add() {
		matter.setCreateDate(new Date());
		matterService.add(matter);
		return "addMatterSuccess";
	}

	/**
	 * loading页面加载站点运行情况
	 * 
	 * @return
	 */
	public String alermZD() {
		matter = matterService.findOne();
		return "alermZD";
	}

	/**
	 * loading页面加载设备运行情况
	 * 
	 * @return
	 */
	public String alermSB() {
		matter = matterService.findOne();
		return "alermSB";
	}
}
