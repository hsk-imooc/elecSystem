<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>修改用户</title>
		<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/validate/validate.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/validate/validateMessage_zh_CN.js"></script>
	</head>
<script type="text/javascript">
//客户端表单项有效性检查
$(document).ready(function() {
	$("#form1").validate( {
		rules : {
			account : "required",
			username : "required",
			units : "required",
			email : 'email',
			password:{
				required:true,
				minlength:3,
				maxlength:15
			},
			repassword:{
				required:true,
				minlength:3,
				maxlength:15,
				equalTo:'#password'
			}
		},
		submitHandler : function(form) {
			form.submit();
			alert('请求已提交');
			opener.location.reload();
			window.close();
		}
	});
});
</script>

	<body>
		<br>
		<form id="form1" action="${pageContext.request.contextPath }/system/userAction_update.action" method="post">
			<input type="hidden" name="userId" value="<s:property value="%{model.userId}"/>">
			<table cellSpacing="1" cellPadding="5" width="620px" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" colSpan="4" background="${pageContext.request.contextPath }/images/b-info.gif">
						<font face="宋体" size="2"><strong>修改用户</strong></font>
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">登录账号：<font color="#FF0000">*</font></td>
					<td class="ta_01" bgColor="#ffffff">
						<input id='account' name="account" type="text"  value="<s:property value="%{model.account}"/>" readonly="readonly"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">用户姓名：<font color="#FF0000">*</font></td>
					<td class="ta_01" bgColor="#ffffff">
						<input id="username" name="username" type="text"  value="<s:property value="%{model.username}"/>" />
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:radio name="gender" list="%{#application.dict.dictMap.gender}" value="%{model.gender}"></s:radio>
						<!-- 
							<input type="radio" name="gender"    <s:if test='%{model.gender=="gender_male"}'>checked="checked"</s:if>  value="gender_male"/>男
							<input type="radio" name="gender"  <s:if test='%{model.gender=="gender_female"}'>checked="checked"</s:if> value="gender_female"/>女
						 -->
						<!-- 
						<s:elseif test='%{model.gender}=="gender_female"'>
							<input type="radio" name="gender"     value="gender_male"/>男
							<input type="radio" name="gender"  checked="checked"  value="gender_female"/>女
						</s:elseif>
						<s:else>
							<input type="radio" name="gender"     value="gender_male"/>男
							<input type="radio" name="gender"   value="gender_female"/>女
						</s:else>
						 -->
						
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">所属单位：<font color="#FF0000">*</font></td>
					<td class="ta_01" bgColor="#ffffff">
						<s:select name="units" list="%{#application.dict.dictMap.units}" value="%{model.units}"></s:select>
						<!-- 
						<select name="units" style="width:155px">
							<option value="1">北京</option>
							<option value="2" selected>深圳</option>
							<option value="3">厦门</option>
							<option value="4">上海</option>
							<option value="5">广州</option>
						</select>
						 -->
					</td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input id="password" name="password" type="password"  value="<s:property value="%{model.password}"/>"/>
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">确认密码：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="repassword" type="password"  value="<s:property value="%{model.password}"/>"/>
					</td>
				</tr>

				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">出生日期：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="birthday" type="text"  value="<s:property value="%{model.birthday}"/>" onclick="WdatePicker()" />
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">联系地址：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="address" type="text"  value="<s:property value="%{model.address}"/>" />
					</td>
				</tr>

				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">联系电话：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="homeTel" type="text"  value="<s:property value="%{model.homeTel}"/>"/>
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="phone" type="text"  value="<s:property value="%{model.phone}"/>"/>
					</td>
				</tr>

				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">电子邮箱：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="email" type="text"  value="<s:property value="%{model.email}"/>" />
					</td>
					<td align="center" bgColor="#f5fafe" class="ta_01">是否在职：</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:radio name="isDuty" list="%{#application.dict.dictMap.isDuty}" value="%{model.isDuty}"></s:radio>
						<!-- 
						<select name="isDuty"  style="width:155px">
							<option value="1" selected>是</option>
							<option value="2">否</option>
						</select>
						 -->
					</td>
				</tr>

				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">入职日期：</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="onDutyDate" type="text"  value="<s:property value="%{model.onDutyDate}"/>" onclick="WdatePicker()">
					</td>
					<td align="center" bgColor="#ffffff" class="ta_01"></td>
					<td class="ta_01" bgColor="#ffffff"></td>
				</tr>

				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：
					</td>
					<td class="ta_01" bgColor="#ffffff" colSpan="3">
						<textarea name="comment" style="WIDTH: 95%" rows="4" cols="52"><s:property value="%{model.comment}"/></textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colSpan="4" class="sep1"></td>
				</tr>
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center" bgColor="#f5fafe" colSpan="4">
						<input type="submit" value="保存" style="font-size: 12px; color: black;" /><font face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						<input style="font-size: 12px; color: black;" type="button" value="关闭" name="Reset1" onClick="window.close()">
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>
