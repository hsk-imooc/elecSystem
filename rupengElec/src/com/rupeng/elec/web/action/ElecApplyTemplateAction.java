package com.rupeng.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rupeng.elec.domain.ElecApplyTemplate;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.service.ElecJBPMService;

/**
 * 申请模板action
 * 
 * @author LeeGossHK
 *
 */
public class ElecApplyTemplateAction extends ActionSupport implements ModelDriven<ElecApplyTemplate> {

	private InputStream inputStream;

	/**
	 * 文件上传三个参数
	 */
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	private ElecJBPMService jbpmService;

	private ElecApplyTemplate applyTemplate = new ElecApplyTemplate();

	@Override
	public ElecApplyTemplate getModel() {
		return applyTemplate;
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

	public void setApplyTemplate(ElecApplyTemplate applyTemplate) {
		this.applyTemplate = applyTemplate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	public String home() {
		List<ElecApplyTemplate> applyTemplateList = jbpmService.applyTemplateList();
		ServletActionContext.getRequest().setAttribute("applyTemplateList", applyTemplateList);
		return "home";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	public String addPage() {
		List<ProcessDefinition> processDefinitions = jbpmService.getNewestProcessDefinitions();
		ServletActionContext.getRequest().setAttribute("processDefinitions", processDefinitions);
		return "addPage";
	}

	/**
	 * 上传模板的方法
	 * 
	 * @return
	 */
	public String uploadTemplate() {
		/**
		 * 1、获得上传文件目录的绝对路径
		 */
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload/applyTemplate");
		realPath += "/" + UUID.randomUUID().toString() + uploadFileName;
		System.out.println(realPath.length());
		File file = new File(realPath);

		/**
		 * 2、复制文件
		 */
		try {
			FileUtils.copyFile(upload, file);
		} catch (IOException e) {
			throw new RuntimeException("文件上传失败" + e);
		}
		/**
		 * 封装数据
		 */
		applyTemplate.setFilename(uploadFileName);
		applyTemplate.setPath(realPath);

		jbpmService.uploadTemplate(applyTemplate);
		return "uploadTemplate";
	}

	/**
	 * 下载申请模板的方法
	 * 
	 * @return
	 */
	public String download() {

		applyTemplate = jbpmService.findApplyTemplateById(applyTemplate.getTemplateId());
		String path = applyTemplate.getPath();

		try {
			inputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return "download";
	}

	/**
	 * 删除申请模板的方法
	 * 
	 * @return
	 */
	public String delete() {
		jbpmService.deleteApplyTemplateById(applyTemplate.getTemplateId());
		return "delete";
	}

	/**
	 * 获得文件的mimeType类型
	 * 
	 * @return
	 */
	public String getContentType() {
		applyTemplate = jbpmService.findApplyTemplateById(applyTemplate.getTemplateId());
		String filename = applyTemplate.getFilename();
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		return mimeType;
	}

	/**
	 * 获得文件名
	 * 
	 * @return
	 */
	public String getDownloadFileName() {

		ElecUser user = (ElecUser) ServletActionContext.getContext().getSession().get("user");
		String account = user.getAccount();

		applyTemplate = jbpmService.findApplyTemplateById(applyTemplate.getTemplateId());
		String filename = applyTemplate.getFilename();
		int index = filename.lastIndexOf(".");
		filename = filename.substring(0, index) + "_" + account + filename.substring(index);

		System.out.println(filename);
		try {
			filename = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return filename;
	}
}
