<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>角色权限管理</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<script type="text/javascript">
	function selectRole() {
		var roleValue = $('#roles').val();
		//如果角色下拉框为空，则将所有的权限复选框置为空
		if (!roleValue) {
			$('[name="functions"]').each(function(index, domEle) {
				$(domEle).attr('checked', null);
			});
		} else {
			//如果角色下拉框不为空，则发送ajax查出对应的所有的权限id字符串
			$.post(
					'${pageContext.request.contextPath}/system/authAction_selectRole.action?'
							+ Math.random(), {
						'roleId' : roleValue
					}, function(data) {
						$('[name="functions"]').each(function(index, domEle) {
							var value = $(domEle).val();
							var index2 = data.indexOf(value);
							if (index2 > -1) {
								$(domEle).attr('checked', 'checked');
							} else {
								$(domEle).attr('checked', null);
							}
						});
					});
		}
	}
	//修改角色的权限列表
	function updateRole() {
		var roleValue = $('#roles').val();
		var functions = '';
		$('[name="functions"]').each(function(index,domEle){
			if($(domEle).attr('checked')){
				functions+=$(domEle).val()+',';
			}
		});
		
		var url='${pageContext.request.contextPath}/system/authAction_updateRole.action?'+Math.random();
		$.post(url,{'roleId':roleValue,'functions':functions},function(data){
			alert(data);
			location.reload();
		});
	}
	
	var roleArray = <s:property value="%{#request.roleListJson}" escapeHtml="false"/>;
	
	//根据用户名模糊查询
	function queryUser(){
		var username=$('#username').val();
		//alert(username);
		if(!username){
			return;		
		}else{
			//发送ajax模糊查询
			$('#userRoleTable').empty();
			var url = '${pageContext.request.contextPath}/system/authAction_queryUser.action?'+Math.random();
			$.post(url,{'username':username},function(data){
				for(var i in data){
					var html="";
					html +='<tr><td>'+data[i].username+'</td>';
					var action='${pageContext.request.contextPath}/system/authAction_updateUser.action?'+Math.random();
					html +='<td><form id="form'+i+'" action="'+action+'" method="post">';
					for(var j in roleArray){
						html+='<input type="checkbox" name="roles" value="'+roleArray[j].roleId+'"';
						var roles = data[i].roles;
						if(roles){
							for(var k in roles){
								if(roles[k].roleId==roleArray[j].roleId){
									html+='checked="checked"';
									break;
								}
							}
						}
						
						html+='/>'+roleArray[j].roleName;
					}
					
					html+='<input type="hidden" name="userId" value="'+data[i].userId+'"/>';
					html+='<input type="button" value="保存修改" onclick="updateUser('+i+')"/></form></td>';
					html+='</tr>';
					$('#userRoleTable').append(html);
				}
			});
		}
	}
	//修改用户的角色列表，发送ajax请求
	function updateUser(i){
		var formRoles = $('#form'+i).serialize();
		var url = '${pageContext.request.contextPath}/system/authAction_updateUser.action?'+formRoles;
		$.post(url,{'random':Math.random()},function(data){
			alert(data);
		});
	}
</script>
</head>

<body>
	<s:if test=""></s:if>
	<div align="center">
		<div align="center"
			style="width: 90%;background-color: #f5fafe;height: 100%">

			<fieldset
				style="width:100%; border : 1px solid #73C8F9;text-align:left;color:#023726;font-size: 12px;min-height: 40%">
				<legend align="left">
					权限分配
					<s:select id="roles" name="roleId" list="%{#request.roleList}"
						listKey="roleId" listValue="roleName" emptyOption="true"
						onchange="selectRole()"></s:select>

				</legend>
				<legend align="right">
					<button onclick="updateRole()">保存修改</button>
				</legend>

				<table>
					<s:iterator value="%{#request.groupsMap}" var="entry">
						<tr>
							<td><s:property value="%{#entry.key}" />
							</td>
							<td><s:checkboxlist id="%{#entry.key}" name="functions"
									list="%{#entry.value}" listKey="functionId"
									listValue="functionName"></s:checkboxlist>
							</td>
						</tr>
					</s:iterator>
				</table>
			</fieldset>

			<br />

			<fieldset
				style="width:100%; border : 1px solid #73C8F9;text-align:left;color:#023726;font-size: 12px;min-height: 40%">
				<legend align="left">
					角色分配 <input id="username" type="text" size="15" name="username" />
					<button onclick="queryUser()">查询用户名</button>
				</legend>

				<table id="userRoleTable" cellSpacing="0" cellPadding="0" border="0">
				</table>
			</fieldset>
		</div>
	</div>
</body>
</HTML>
