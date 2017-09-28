package com.rupeng.elec.domain;

/**
 * 
 * 
 * @author LeeGossHK
 *
 *         create table elecApplyTemplate( templateId varchar(100),#模板id
 *         filename varchar(100),#模板文件名 path text,#模板文件真实路径 processDefinitionKey
 *         varchar(100)#模板表关联的流程定义的key );
 */
public class ElecApplyTemplate {

	private String templateId;
	private String filename;
	private String path;
	private String processDefinitionKey;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
