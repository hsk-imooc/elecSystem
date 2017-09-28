<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/util.js"></script>
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
	</head>
	<style type="text/css">
	 tr{
		text-align: center;
	}
	td{
		border-bottom:1 gray solid;
		background-color: #f5fafe;
		
	}
	thead tr td{
		font-weight:bold;
		background-image: url('${pageContext.request.contextPath }/images/tablehead.jpg');
	}

	
</style>

	<body >
	<div align="center" >
	<div style="width:90%;background-color: #f5fafe;">
		<form action="${pageContext.request.contextPath}/workflow/applyAction_applyMy.action" >
		<div style="height: 10px;"></div>
		<div style="font: bold 12pt 宋体 ;background-image:url('${pageContext.request.contextPath}/images/b-info.gif')" align="center">我的申请查询</div>
		<div align="left">
				流程定义
				<s:select name="processDefinitionKey" list="%{#request.processDefinitionList}" listKey="key" listValue="key" emptyOption="true"></s:select>
				<!-- 
				<select>
					<option >员工请假申请流程</option>
					<option >设备购买申请流程</option>
					<option >设备报销申请流程</option>
				</select>
				 -->
				申请状态
				<s:select name="applyStatus" list="%{#application.dict.dictMap.applyStatus}" emptyOption="true"></s:select>
				<!-- 
				<select>
					<option >审核中</option>
					<option >审核通过</option>
					<option >审核未通过</option>
				</select>
				 -->
		</div>
		<div align="right"><input type="submit" value="查询"/></div>
		<div style="height: 10px"></div>
		<div style="height: 10px"></div>
		</form>
		<div>
			<div style="float: left;width:130px;background-image: url('${pageContext.request.contextPath }/images/cotNavGround.gif')">
				<img src="${pageContext.request.contextPath }/images/yin.gif" width="15" />我的申请列表
			</div>
			<div align="right" style="float: right;">
				<input  type="button" value="刷新"  onclick="location.reload()" />
			</div>
			<div style="clear: both;"></div>
			<div align="left">
			</div>
		</div>
		<table id="table"   style="width: 100%"  cellspacing="0"  border="0">
			<thead style="height: 20px;">
					<tr>
								<td>工作流程名称</td>
								<td>申请时间</td>
								<td>申请状态</td>
					</tr>
					
			</thead>
				<s:iterator value="%{#request.applyList}" var="apply">
					<tr>
						<td><s:property value="%{#apply.processDefinitionKey}"/></td>
						<td><s:property value="%{#apply.applyTime}"/></td>
						<td><s:property value="%{#application.dict.itemMap[#apply.applyStatus]}"/></td>
					</tr>
				</s:iterator>
						<!-- 
						<tr>
							<td>员工请假申请流程</td>
							<td>2014-10-01</td>
							<td>审核通过</td>
						</tr>
							<tr>
							<td>员工请假申请流程</td>
							<td>2015-03-01</td>
							<td>审核中</td>
						</tr>
						 -->
		</table>
		
	</div>
	</div>
	<div id="tt"></div>
	</body>
</html>
