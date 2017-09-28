package com.rupeng.elec.domain;

import java.util.Set;

/**
 * 角色javabean
 * 
 * @author LeeGossHK
 * 
 */
public class ElecRole {

	private String roleId;
	private String roleName;

	// 一个角色对应多个权限
	private Set<ElecFunction> functions;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<ElecFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<ElecFunction> functions) {
		this.functions = functions;
	}

}
