package com.rupeng.elec.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rupeng.elec.domain.ElecText;
import com.rupeng.elec.service.ElecTextService;

/**
 * 测试用action
 * 
 * @author LeeGossHK
 * 
 */
public class ElecTextAction extends ActionSupport implements
		ModelDriven<ElecText> {

	private ElecTextService textService;

	public void setTextService(ElecTextService textService) {
		this.textService = textService;
	}

	private ElecText text = new ElecText();

	public ElecText getModel() {
		return text;
	}

	public String add() {
		textService.add(text);
		return NONE;
	}

}
