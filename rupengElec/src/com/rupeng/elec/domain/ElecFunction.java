package com.rupeng.elec.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限javabean
 * 
 * @author LeeGossHK
 * 
 */
public class ElecFunction {

	private String functionId;
	private String functionName;
	private String path;// 权限对应的访问路径
	private String groups;// 权限对应的分组

	// 一个权限可以对应多个用户
	Set<ElecUser> users = new HashSet<ElecUser>();

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Set<ElecUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ElecUser> users) {
		this.users = users;
	}

}
