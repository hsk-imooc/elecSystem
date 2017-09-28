package com.rupeng.elec.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecApply;
import com.rupeng.elec.domain.ElecApplyTemplate;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.domain.vo.ApplyBean;
import com.rupeng.elec.service.ElecJBPMService;

public class ElecApplyAction extends ActionSupport implements ModelDriven<ElecApply> {

	/**
	 * 文件上传三个参数
	 */
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	private ElecJBPMService jbpmService;

	ElecApply apply = new ElecApply();

	@Override
	public ElecApply getModel() {
		return apply;
	}

	public void setJbpmService(ElecJBPMService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * 
	 * 首页跳转
	 * 
	 * @return
	 */
	public String home() {
		List<ProcessDefinition> procesDefinitionList = jbpmService.getNewestProcessDefinitions();
		List<ElecApplyTemplate> applyTemplateList = jbpmService.applyTemplateList();
		List<ApplyBean> applyBeanList = new ArrayList<ApplyBean>();

		for (ProcessDefinition processDefinition : procesDefinitionList) {
			String processDefinitionId = processDefinition.getId();
			String processDefinitionKey = processDefinition.getKey();
			String filename = null;
			String path = null;
			String templateId = null;

			for (ElecApplyTemplate applyTemplate : applyTemplateList) {
				if (processDefinitionKey.equals(applyTemplate.getProcessDefinitionKey())) {
					filename = applyTemplate.getFilename();
					path = applyTemplate.getPath();
					templateId = applyTemplate.getTemplateId();
				}
			}

			ApplyBean applyBean = new ApplyBean();
			applyBean.setFilename(filename);
			applyBean.setPath(path);
			applyBean.setProcessDefinitionId(processDefinitionId);
			applyBean.setProcessDefinitionKey(processDefinitionKey);
			applyBean.setTemplateId(templateId);

			applyBeanList.add(applyBean);
		}
		ServletActionContext.getRequest().setAttribute("applyBeanList", applyBeanList);
		return "home";
	}

	/**
	 * 上传申请文件启动流程
	 * 
	 * @return
	 */
	public String start() {
		/**
		 * 上传申请文件
		 */
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload/applyFile");
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		realPath += "/" + UUID.randomUUID().toString() + uploadFileName;

		System.out.println(realPath);
		try {
			FileUtils.copyFile(upload, new File(realPath));
		} catch (IOException e) {
			e.printStackTrace();
		} // 文件复制

		/**
		 * 启动流程实例
		 */
		// 封装ElecApply数据
		ElecUser user = (ElecUser) ServletActionContext.getContext().getSession().get("user");
		String account = user.getAccount();
		String username = user.getUsername();
		String userId = user.getUserId();

		apply.setAccount(account);
		apply.setApplyStatus("applyStatus_ing");
		apply.setApplyTime(new Date());
		apply.setFilename(uploadFileName);
		apply.setPath(realPath);
		apply.setUserId(userId);
		apply.setUsername(username);

		jbpmService.saveApplyAndStartProcessInstance(apply);

		return "start";
	}

	/**
	 * 我的申请查询
	 * 
	 * @return
	 */
	public String applyMy() {
		ElecUser user = (ElecUser) ServletActionContext.getContext().getSession().get("user");

		List<ProcessDefinition> processDefinitionList = jbpmService.getNewestProcessDefinitions();
		ServletActionContext.getRequest().setAttribute("processDefinitionList", processDefinitionList);

		Conditions conditions = new Conditions();
		conditions.addCondition("processDefinitionKey", apply.getProcessDefinitionKey(), Operator.EQUAL);
		conditions.addCondition("applyStatus", apply.getApplyStatus(), Operator.EQUAL);
		conditions.addCondition("userId", user.getUserId(), Operator.EQUAL);
		List<ElecApply> applyList = jbpmService.applyListByConditions(conditions);
		ServletActionContext.getRequest().setAttribute("applyList", applyList);

		return "applyMy";
	}

}
