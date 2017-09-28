package com.rupeng.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.domain.vo.ApproveInfo;
import com.rupeng.elec.domain.vo.TaskApply;
import com.rupeng.elec.service.ElecJBPMService;

public class ElecTaskAction extends ActionSupport {

	private Boolean isAgree;
	private String outcome;
	private String comment;

	private InputStream inputStream;

	private String taskId;
	private ElecJBPMService jbpmService;

	public void setJbpmService(ElecJBPMService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 待我审批------我的任务列表
	 * 
	 * @return
	 */
	public String taskMy() {
		ElecUser user = (ElecUser) ServletActionContext.getContext().getSession().get("user");

		List<TaskApply> taskApplyList = jbpmService.taskApplyList(user);

		ServletActionContext.getRequest().setAttribute("taskApplyList", taskApplyList);
		return "taskMy";
	}

	/**
	 * 显示审批页面
	 * 
	 * @return
	 */
	public String taskApprove() {
		TaskApply taskApply = jbpmService.getTaskApplyByTaskId(taskId);

		ServletActionContext.getRequest().setAttribute("taskApply", taskApply);
		return "taskApprove";
	}

	/**
	 * 下载申请文件
	 * 
	 * @return
	 */
	public String downloadApplyFile() {
		TaskApply taskApply = jbpmService.getTaskApplyByTaskId(taskId);
		String path = taskApply.getPath();

		try {
			inputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return "downloadApplyFile";
	}

	/**
	 * 审批任务
	 * 
	 * @return
	 */
	public String approve() {
		ElecUser user = (ElecUser) ServletActionContext.getContext().getSession().get("user");
		jbpmService.approve(user, taskId, isAgree, outcome, comment);
		return "approve";
	}

	/**
	 * 查看流程信息的方法
	 * 
	 * @return
	 */
	public String approveInfo() {
		List<ApproveInfo> approveInfoList = jbpmService.approveInfoList(taskId);
		ServletActionContext.getRequest().setAttribute("approveInfoList", approveInfoList);
		return "approveInfo";
	}

	public String getContentType() {
		TaskApply taskApply = jbpmService.getTaskApplyByTaskId(taskId);
		String filename = taskApply.getFilename();

		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);

		return mimeType;
	}

	public String getDownloadFileName() {
		TaskApply taskApply = jbpmService.getTaskApplyByTaskId(taskId);
		String filename = taskApply.getFilename();

		try {
			return URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
