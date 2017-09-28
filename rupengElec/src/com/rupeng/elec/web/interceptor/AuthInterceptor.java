package com.rupeng.elec.web.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.rupeng.elec.domain.ElecFunction;
import com.rupeng.elec.domain.ElecRole;
import com.rupeng.elec.domain.ElecUser;

/**
 * 检查权限的interceptor
 * 
 * @author LeeGossHK
 * 
 */
public class AuthInterceptor extends MethodFilterInterceptor {

	// 定义白名单，白名单中的action不会被拦截
	private List<String> whiteList;

	@Override
	public void init() {
		whiteList = new ArrayList<String>();
		whiteList.add("menuAction_alermJX");
		whiteList.add("menuAction_alermSB");
		whiteList.add("menuAction_alermXZ");
		whiteList.add("menuAction_alermYS");
		whiteList.add("menuAction_alermZD");
		whiteList.add("menuAction_home");
		whiteList.add("menuAction_index");
		whiteList.add("menuAction_left");
		whiteList.add("menuAction_loading");
		whiteList.add("menuAction_title");
		whiteList.add("matterAction_home");
		whiteList.add("matterAction_alermZD");
		whiteList.add("matterAction_alermSB");
		whiteList.add("userAction_home");
		whiteList.add("userAction_login");
		whiteList.add("menuAction_login");
		whiteList.add("checkCodeAction");

		System.out.println("authInterceptor拦截器初始化了。。。。。！！！！！");
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		boolean mark = true;
		if (mark) {
			return invocation.invoke();
		}
		String actionName = invocation.getProxy().getActionName();
		// 白名单直接放行
		if (whiteList.contains(actionName)) {
			return invocation.invoke();
		}
		// 取出session中的user
		ElecUser user = (ElecUser) invocation.getInvocationContext().getSession().get("user");
		// user不为空则进行权限判断
		if (user != null) {
			Set<ElecRole> roles = user.getRoles();
			if (roles != null) {
				for (ElecRole role : roles) {
					Set<ElecFunction> functions = role.getFunctions();
					if (functions != null) {
						for (ElecFunction function : functions) {
							String functionName = function.getFunctionName();
							if (functionName.equals(actionName)) {
								return invocation.invoke();
							}
						}
					}
				}
			}
		}
		return "authError";
	}

}
