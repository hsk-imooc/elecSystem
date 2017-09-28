<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>用户管理</title>		
		<link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/dataTables/css/jquery.dataTables.css">
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/dataTables/js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/dataTables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/util.js"></script>
		<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/dataTables/fnReloadAjax.js"></script></head>
	<style type="text/css">
	.tableClass tr{
		text-align: center;
    
	}
	.tableClass tr td{
		line-height: 5px;	
		background-color: #f5fafe;
		border-top:gray 1px solid;
		border-left:gray 1px solid;
	}
	.tableClass thead tr td{
		font-weight:bold;
		line-height:0px;
		border-bottom:none;
		background-image: url('${pageContext.request.contextPath }/images/tablehead.jpg');
	}
	.tableClass  img{
		margin-top: -6px;
		margin-bottom: -6px;
	}

	
</style>
<script type="text/javascript">
var options = {
		"oLanguage":{
			"sUrl":"/rupengElec/script/dataTables/zh_CN.txt"
		},
		
        "columns": [
            { "data": "account" },
            { "data": "username" },
            { "data": "gender" },
            { "data": "homeTel" },
            { "data": "isDuty" },
            { "data": "account" }
        ],
        "bPaginate": true, //翻页功能
		"bLengthChange": false, //改变每页显示数据数量
		"bFilter": false, //过滤功能
		"bInfo": true,//页脚信息
		"bSort": false, //排序功能
 		"bStateSave": true,//状态保存开关,使用cookie记录当前页码,即是页面关闭,重新打开时还是上次的页码
 		
 		"bServerSide": true,  //开启服务器支持
 		//ajax请求的url
		"sAjaxSource": "${pageContext.request.contextPath}/system/userAction_page.action",  
		
		//向服务器发送请求时调用,用来添加额外的请求参数
		"fnServerParams": function ( aoData ) {
				var value=$('#username').val();
					aoData.push( { "name": "username", "value": value} );	
		},
		
		//需要向服务器请求数据时调用
		"fnServerData": function ( sSource, aoData, fnCallback ) {
 					$.ajax( {
 							"dataType": 'json',
 							"type": "POST",
 							"url": sSource,
 							"data": aoData,
 							"success": fnCallback
 					} );
 		 },
 		 
 		 //当显示一行数据的时候调用,为最后一列添加删除和修改超链接
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
				var deleteUrl='${pageContext.request.contextPath}/system/userAction_delete.action';
				var updateUrl='${pageContext.request.contextPath}/system/userAction_userEdit.action';
				var value='<a href="'+deleteUrl+'?userId='+aData.userId+'">删除</>&nbsp<a href="'+updateUrl+'?userId='+aData.userId+'">修改</a>';
				$(':last-child', nRow).html(value);
				nRow.onmouseover=function(){
					$('td' , this).css('background-color', 'white');
				}
				nRow.onmouseout=function(){
					$('td' , this).css('background-color','#F5FAFE');	
				}
				return nRow;
		}
	}
	
var pageTables;
$(document).ready(function() {
   pageTables =  $('#table').dataTable( options );
});


function exportExcel(){
	$("#exportForm").attr("action","${pageContext.request.contextPath}/system/userAction_exportExecl.action") ;
	$("#exportForm").submit();
}

function exportChart(){
	$("#exportForm").attr("action","${pageContext.request.contextPath}/system/statistic.PNG") ;
	$("#exportForm").submit();
}

function query(){
	pageTables.fnReloadAjax();
}
</script>
		
	<body >
	<div align="center" >
	<div style="width:90%;background-color: #f5fafe;">
		<div style="height: 10px;"></div>
		<div style="font: bold 12pt 宋体 ;background-image:url('${pageContext.request.contextPath}/images/b-info.gif')" align="center">用户信息管理</div>
		<div align="left">
			<form id="exportForm"  method="post">
				姓名<input name="username" type="text" id="username" />
			</form>
		</div>
		<div style="height: 10px"></div>
		<div>
			<div style="float: left;width:80px;background-image: url('${pageContext.request.contextPath }/images/cotNavGround.gif')">
				<img src="${pageContext.request.contextPath }/images/yin.gif" width="15" />用户列表
			</div>
			<div align="right" style="float: right;">
				<button  onclick="query()" >查询</button>
				<button  onclick="exportExcel()" >导出报表</button>
				<button  onclick="exportChart()" >统计各单位人数</button>
				
				<button onclick="openWindow('${pageContext.request.contextPath }/system/userAction_addPage.action','800','400')">添加用户</button>
			</div>
			<div style="clear: both;"></div>
		</div>
		<table id="table"  class="tableClass"   >
			<thead style="width: 100%;">
					<tr>
								<td>Name</td>
								<td>用户姓名</td>
								<td>性别</td>
								<td>联系电话</td>
								<td>是否在职</td>
								<td>操作</td>
					</tr>
				</thead>
		</table>
		
	</div>
	</div>
	<div id="tt"></div>
	</body>
</html>
