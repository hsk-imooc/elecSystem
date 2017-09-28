package com.rupeng.elec.domain.vo;

import java.util.Set;

/**
 * 封装待我审批页面携带数据
 * 
 * @author LeeGossHK
 *
 */
public class TaskApply {

	private String taskId;
	private String taskName;

	private String processDefinitionKey;
	private String processDefinitionId;

	private String userId;
	private String username;
	private String account;

	private String filename;
	private String path;
	private String applyId;

	private Set<String> outcomes;

	public Set<String> getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(Set<String> outcomes) {
		this.outcomes = outcomes;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

}
