package com.rupeng.elec.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.opensymphony.xwork2.ActionSupport;
import com.rupeng.elec.service.ElecJBPMService;

/**
 * 流程定义action
 * 
 * @author LeeGossHK
 *
 */
public class ElecProcessDefinitionAction extends ActionSupport {

	/**
	 * 注入流程定义service
	 */
	private ElecJBPMService jbpmService;
	/**
	 * 文件上传三个参数
	 */
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	/**
	 * 接收流程定义id
	 */
	private String id;

	private InputStream inputStream;

	/**
	 * @param jbpmService
	 */
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

	public void setId(String id) {
		/**
		 * 需要处理乱码问题
		 */
		try {
			id = new String(id.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		this.id = id;
	}

	/**
	 * 返回流程定义图片的输入流
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 显示主页面的方法
	 * 
	 * @return
	 */
	public String home() {
		List<ProcessDefinition> newestProcessDefinitions = jbpmService.getNewestProcessDefinitions();
		ServletActionContext.getRequest().setAttribute("processDefinitions", newestProcessDefinitions);

		return "home";
	}

	/**
	 * 显示上传流程定义文件的页面的方法
	 * 
	 * @return
	 */
	public String addPage() {
		return "addPage";
	}

	/**
	 * 部署流程定义的方法
	 * 
	 * @return
	 */
	public String deployment() {

		jbpmService.deployment(upload);
		return "deployment";
	}

	/**
	 * 删除部署的流程定义
	 * 
	 * @return
	 */
	public String delete() {

		jbpmService.deleteProcessDefinitionById(id);
		return "delete";
	}

	/**
	 * 查看流程定义图片的方法
	 * 
	 * @return
	 */
	public String showProcessImage() {

		inputStream = jbpmService.showProcessImage(id);
		return "showProcessImage";
	}
}
