package com.rupeng.elec.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.transaction.annotation.Transactional;

import com.rupeng.elec.dao.IElecApplyDao;
import com.rupeng.elec.dao.IElecApplyTemplateDao;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecApply;
import com.rupeng.elec.domain.ElecApplyTemplate;
import com.rupeng.elec.domain.ElecRole;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.domain.vo.ApproveInfo;
import com.rupeng.elec.domain.vo.TaskApply;

/**
 * jbpm相关service
 * 
 * @author LeeGossHK
 *
 */
@Transactional
public class ElecJBPMService {

	private IElecApplyDao applyDao;
	/**
	 * 注入申请模板dao
	 */
	private IElecApplyTemplateDao applyTemplateDao;
	/**
	 * 注入jbpm流程引擎对象
	 */
	private ProcessEngine processEngine;

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public void setApplyTemplateDao(IElecApplyTemplateDao applyTemplateDao) {
		this.applyTemplateDao = applyTemplateDao;
	}

	public void setApplyDao(IElecApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	/**
	 * 业务层部署流程定义
	 * 
	 * @param upload
	 */
	public void deployment(File upload) {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		NewDeployment newDeployment = repositoryService.createDeployment();
		try {
			newDeployment.addResourcesFromZipInputStream(new ZipInputStream(new FileInputStream(upload)));
			newDeployment.deploy();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("部署流程定义失败：" + e);
		}

	}

	/**
	 * 获得最新版本的processDefinition列表
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getNewestProcessDefinitions() {

		ProcessDefinitionQuery processDefinitionQuery = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		List<ProcessDefinition> list = processDefinitionQuery.list();

		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		if (list != null && list.size() > 0) {
			for (ProcessDefinition processDefinition : list) {
				String key = processDefinition.getKey();
				int version = processDefinition.getVersion();

				ProcessDefinition processDefinition2 = map.get(key);
				if (processDefinition2 != null) {
					int version2 = processDefinition2.getVersion();
					if (version2 < version) {
						map.put(key, processDefinition);
					}
				} else {
					map.put(key, processDefinition);
				}
			}
			return new ArrayList<ProcessDefinition>(map.values());
		}
		return new ArrayList<ProcessDefinition>();
	}

	/**
	 * 通过id删除processDefinition
	 * 
	 * @param id
	 */
	public void deleteProcessDefinitionById(String id) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id)
				.uniqueResult();
		repositoryService.deleteDeployment(processDefinition.getDeploymentId());
	}

	/**
	 * 业务层获得流程定义图片的输入流
	 * 
	 * @param id
	 * @return
	 */
	public InputStream showProcessImage(String id) {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id)
				.uniqueResult();

		String deploymentId = processDefinition.getDeploymentId();
		String imageResourceName = processDefinition.getImageResourceName();

		return repositoryService.getResourceAsStream(deploymentId, imageResourceName);
	}

	/**
	 * 业务层上传申请模板
	 * 
	 * @param applyTemplate
	 */
	public void uploadTemplate(ElecApplyTemplate applyTemplate) {
		Conditions conditions = new Conditions();
		conditions.addCondition("processDefinitionKey", applyTemplate.getProcessDefinitionKey(), Operator.EQUAL);
		List<ElecApplyTemplate> list = applyTemplateDao.findByConditions(conditions);
		if (list != null && list.size() > 0) {
			applyTemplateDao.deleteAll(list);
		}
		applyTemplateDao.addOrUpdate(applyTemplate);
	}

	/**
	 * 获得所有申请模板列表
	 * 
	 * @return
	 */
	public List<ElecApplyTemplate> applyTemplateList() {
		return applyTemplateDao.findAll();
	}

	/**
	 * 通过id查找applyTemplate
	 * 
	 * @param templateId
	 * @return
	 */
	public ElecApplyTemplate findApplyTemplateById(String templateId) {
		return applyTemplateDao.findById(templateId);
	}

	/**
	 * 通过id删除applyTemplate
	 * 
	 * @param templateId
	 */
	public void deleteApplyTemplateById(String templateId) {
		applyTemplateDao.deleteById(templateId);
	}

	/**
	 * 保存申请信息并启动流程实例
	 * 
	 * @param apply
	 */
	public void saveApplyAndStartProcessInstance(ElecApply apply) {
		// 保存申请信息
		applyDao.addOrUpdate(apply);

		// 启动流程实例并完成第一个任务
		String processDefinitionId = apply.getProcessDefinitionId();
		String processDefinitionKey = apply.getProcessDefinitionKey();
		String account = apply.getAccount();
		Map<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("account", account);
		varMap.put("apply", apply);

		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstance processInstance = executionService.startProcessInstanceById(processDefinitionId, varMap);// 启动流程实例

		TaskService taskService = processEngine.getTaskService();

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).uniqueResult();

		System.out.println(task.getName());
		System.out.println(task.getId());
		System.out.println(taskService.getOutcomes(task.getId()));
		taskService.completeTask(task.getId());// 完成第一个任务
	}

	/**
	 * 获得所有申请信息列表
	 * 
	 * @return
	 */
	public List<ElecApply> applyList() {
		return applyDao.findAll();
	}

	/**
	 * 条件查询apply申请信息
	 * 
	 * @return
	 */
	public List<ElecApply> applyListByConditions(Conditions conditions) {
		return applyDao.findByConditions(conditions);
	}

	/**
	 * 获得待我审批的任务列表
	 * 
	 * @param user
	 * @return
	 */
	public List<TaskApply> taskApplyList(ElecUser user) {
		List<TaskApply> taskApplyList = new ArrayList<TaskApply>();
		// 获得user的角色
		Set<ElecRole> roles = user.getRoles();
		// 通过角色查到对应的任务
		for (ElecRole elecRole : roles) {
			String roleName = elecRole.getRoleName();
			List<Task> tasks = processEngine.getTaskService().findPersonalTasks(roleName);
			// 通过任务查到和该任务相关联的申请信息
			for (Task task : tasks) {
				ElecApply apply = (ElecApply) processEngine.getTaskService().getVariable(task.getId(), "apply");

				TaskApply taskApply = new TaskApply();
				taskApply.setAccount(user.getAccount());
				taskApply.setApplyId(apply.getApplyId());
				taskApply.setFilename(apply.getFilename());
				taskApply.setPath(apply.getPath());
				taskApply.setProcessDefinitionId(apply.getProcessDefinitionId());
				taskApply.setProcessDefinitionKey(apply.getProcessDefinitionKey());
				taskApply.setTaskId(task.getId());
				taskApply.setTaskName(task.getName());
				taskApply.setUserId(apply.getUserId());
				taskApply.setUsername(apply.getUsername());

				taskApplyList.add(taskApply);
			}
		}
		return taskApplyList;
	}

	/**
	 * 显示审批页面
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskApply getTaskApplyByTaskId(String taskId) {

		Task task = processEngine.getTaskService().getTask(taskId);
		ElecApply apply = (ElecApply) processEngine.getTaskService().getVariable(taskId, "apply");
		Set<String> outcomes = processEngine.getTaskService().getOutcomes(taskId);

		TaskApply taskApply = new TaskApply();
		taskApply.setTaskId(task.getId());
		taskApply.setTaskName(task.getName());
		taskApply.setFilename(apply.getFilename());
		taskApply.setPath(apply.getPath());
		taskApply.setOutcomes(outcomes);
		taskApply.setApplyId(apply.getApplyId());
		taskApply.setAccount(apply.getAccount());
		taskApply.setUserId(apply.getUserId());
		taskApply.setUsername(apply.getUsername());
		taskApply.setProcessDefinitionId(apply.getProcessDefinitionId());
		taskApply.setProcessDefinitionKey(apply.getProcessDefinitionKey());

		return taskApply;
	}

	/**
	 * 业务层完成审批操作
	 * 
	 * @param taskId
	 * @param isAgree
	 * @param outcome
	 * @param comment
	 */
	public void approve(ElecUser user, String taskId, Boolean isAgree, String outcome, String comment) {

		ElecApply apply = (ElecApply) processEngine.getTaskService().getVariable(taskId, "apply");
		String executionId = processEngine.getTaskService().getTask(taskId).getExecutionId();// 执行id
		Execution execution = processEngine.getExecutionService().findExecutionById(executionId);// 执行对象
		String processInstanceId = execution.getProcessInstance().getId();// 流程实例id
		if (isAgree) {
			/**
			 * 同意：根据连线完成任务任务
			 */
			// approveInfo对象封装的是上一次审批的信息，因为该次审批之前可能会存在多次审批
			// 即每一次审批任务对应着一个审批的信息，所以需要用map通过每次审批任务的id将该次审批的信息保存起来
			// 该map是通过流程变量的形式记录的
			Map<String, ApproveInfo> approveInfoMap = (Map<String, ApproveInfo>) processEngine.getTaskService()
					.getVariable(taskId, "approveInfoMap");
			if (approveInfoMap == null) {
				approveInfoMap = new HashMap<String, ApproveInfo>();
			}
			ApproveInfo approveInfo = new ApproveInfo();
			approveInfo.setAccount(user.getAccount());
			approveInfo.setComment(comment);
			approveInfo.setTaskId(taskId);
			approveInfo.setTaskName(processEngine.getTaskService().getTask(taskId).getName());
			approveInfo.setUserId(user.getUserId());
			approveInfo.setUsername(user.getUsername());

			approveInfoMap.put(taskId, approveInfo);

			Map<String, Object> varMap = new HashMap<String, Object>();
			varMap.put("comment", comment);
			varMap.put("approveInfoMap", approveInfoMap);
			processEngine.getTaskService().completeTask(taskId, outcome, varMap);

			ProcessInstance processInstance = processEngine.getExecutionService()
					.findProcessInstanceById(processInstanceId);
			if (processInstance == null) {
				apply.setApplyStatus("applyStatus_pass");
				applyDao.addOrUpdate(apply);
			}

		} else {
			/**
			 * 不同意：直接结束掉整个流程
			 */
			processEngine.getExecutionService().endProcessInstance(processInstanceId, ProcessInstance.STATE_ENDED);
			apply.setApplyStatus("applyStatus_fail");
			applyDao.addOrUpdate(apply);

		}
	}

	/**
	 * 业务层查看审批的流程信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<ApproveInfo> approveInfoList(String taskId) {
		Map<String, ApproveInfo> approveInfoMap = (Map<String, ApproveInfo>) processEngine.getTaskService()
				.getVariable(taskId, "approveInfoMap");
		if (approveInfoMap != null) {
			return new ArrayList<ApproveInfo>(approveInfoMap.values());
		}
		return null;
	}

}
