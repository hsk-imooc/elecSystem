package com.rupeng.elec.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecFunction;
import com.rupeng.elec.domain.ElecRole;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.service.ElecFunctionService;
import com.rupeng.elec.service.ElecRoleService;
import com.rupeng.elec.service.ElecUserService;

public class AuthAction extends ActionSupport {

	private String userId;
	private String[] roles;

	private String roleId;
	private String functions;
	private String username;

	private ElecUserService userService;
	private ElecRoleService roleService;
	private ElecFunctionService functionService;

	public void setUserService(ElecUserService userService) {
		this.userService = userService;
	}

	public void setRoleService(ElecRoleService roleService) {
		this.roleService = roleService;
	}

	public void setFunctionService(ElecFunctionService functionService) {
		this.functionService = functionService;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	/**
	 * 显示角色主页面
	 * 
	 * @return
	 */
	public String roleHome() {
		// 1 准备角色数据
		List<ElecRole> roleList = roleService.list();
		ServletActionContext.getRequest().setAttribute("roleList", roleList);
		// 2 准备权限数据
		List<ElecFunction> functionList = functionService.list();

		Map<String, List<ElecFunction>> groupsMap = new HashMap<String, List<ElecFunction>>();
		for (ElecFunction function : functionList) {
			String groups = function.getGroups();
			List<ElecFunction> tempList = groupsMap.get(groups);
			if (tempList == null) {
				tempList = new ArrayList<ElecFunction>();
			}
			tempList.add(function);
			groupsMap.put(groups, tempList);
		}

		ServletActionContext.getRequest().setAttribute("groupsMap", groupsMap);
		Gson json = new Gson();
		String roleListJson = json.toJson(roleList);
		ServletActionContext.getRequest().setAttribute("roleListJson",
				roleListJson);

		return "roleHome";
	}

	/**
	 * 选择角色
	 * 
	 * @return
	 * @throws IOException
	 */
	public String selectRole() throws IOException {
		ElecRole role = roleService.findById(roleId);
		Set<ElecFunction> functions = role.getFunctions();

		StringBuilder s = new StringBuilder(",");
		for (ElecFunction function : functions) {
			s.append(function.getFunctionId()).append(",");
		}

		ServletActionContext.getResponse().getWriter().write(s.toString());
		return NONE;
	}

	/**
	 * 更新角色的权限列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateRole() throws IOException {
		// 通过id获得role
		ElecRole role = roleService.findById(roleId);
		// 获得该id的role的所有权限
		Set<ElecFunction> functionSet = role.getFunctions();
		// 清空该id下的所有权限
		functionSet.clear();

		// 通过function的id字符串查出对应的function
		List<ElecFunction> functionList = functionService.findByIds(functions);
		functionSet = new HashSet<ElecFunction>(functionList);
		role.setFunctions(functionSet);

		// 更新role
		roleService.update(role);

		ServletActionContext.getResponse().setContentType(
				"text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write("保存成功");

		return NONE;
	}

	/**
	 * 前台ajax根据用户名模糊查询
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryUser() throws IOException {
		Conditions conditions = new Conditions();
		conditions.addCondition("isDelete", false, Operator.EQUAL);
		conditions.addCondition("username", username, Operator.LIKE);

		List<ElecUser> userList = userService.findByConditions(conditions);

		Gson gson = new Gson();
		String userListJson = gson.toJson(userList);

		ServletActionContext.getResponse().setContentType(
				"text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(userListJson);
		// System.out.println(userListJson);

		return NONE;
	}

	/**
	 * 更新用户的角色列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateUser() throws IOException {
		// System.out.println(Arrays.toString(roles) + "----------" + userId);
		ElecUser user = userService.findById(userId);
		Set userRoles = user.getRoles();
		userRoles.clear();

		List<ElecRole> roleList = roleService.findByIds(roles);
		userRoles = new HashSet<ElecRole>(roleList);
		user.setRoles(userRoles);

		userService.update(user);

		ServletActionContext.getResponse().setContentType(
				"text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write("保存成功");

		return NONE;
	}
}
