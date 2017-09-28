package com.rupeng.elec.domain;

import java.util.Date;

/**
 * #申请信息表 create table elecApply( applyId varchar(100) primary key,#申请信息的逻辑id
 * userId varchar(100),#申请人信息 username varchar(50), account varchar(50),
 * processDefinitionId varchar(100),#记录提交申请时对应的特定的流程定义，保证提交申请和审批申请时使用的是同一个流程定义
 * processDefinitionKey varchar(50), applyTime date,#申请时间 applyStatus
 * varchar(50), filename varchar(100),#申请文件名称 path text#申请文件的路径 );
 * 
 * @author LeeGossHK
 *
 */
public class ElecApply {

	// 申请信息的逻辑id
	private String applyId;
	// 申请人信息
	private String userId;
	private String username;
	private String account;

	// 申请的流程定义相关信息
	private String processDefinitionId;
	private String processDefinitionKey;

	// 申请时间
	private Date applyTime;
	// 申请状态：审批中；申请通过；申请未通过
	private String applyStatus;

	// 申请文件的文件名
	private String filename;
	// 上传的申请文件保存的真是路径
	private String path;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
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

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
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

}
