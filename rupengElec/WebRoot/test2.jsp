<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/dataTables/css/jquery.dataTables.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath }/script/dataTables/fnReloadAjax.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#table1').DataTable(
			{
				oLanguage: {
			       "sLengthMenu": "每页显示 _MENU_条",
			       "sZeroRecords": "没有找到符合条件的数据",
			       "sProcessing": "&lt;img src=’./loading.gif’ /&gt;",
			       "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
			       "sInfoEmpty": "有木记录",
			       "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
			       "sSearch": "搜索：",
			       "oPaginate": {
			              "sFirst": "首页",
			              "sPrevious": "前一页",
			              "sNext": "后一页",
			              "sLast": "尾页"
			       }
				},
				
				"bServerSide": true,  //开启服务器支持
				"sAjaxSource": "${pageContext.request.contextPath}/system/userAction_test.action",  //ajax请求的url
				//向服务器发送请求时调用,用来添加额外的请求参数
				"fnServerParams": function ( aoData ) {
							aoData.push( { "name": "username", "value": "aa"} );	
				},
				//需要从服务器请求数据时调用
				"fnServerData": function ( sSource, aoData, fnCallback ) {
 							$.ajax( {
 									"dataType": 'json',
 									"type": "POST",
 									"url": sSource,
 									"data": aoData,
 									"success": fnCallback
 							} );
 				 },
 				 
 				 "columns": [
           				 { "data": "account" },//第1列为 json对象的account属性
           				 { "data": "username" },
          				  { "data": "gender" },
           				 { "data": "contactTel" },
         				   { "data": "isDuty" },
        			    { "data": "account" }
     			   ],
			}
		);
	});
</script>
</head>
<body>
	
	<table id="table1" border="1">
		<thead>
			<tr>
				<td>学号</td>
				<td>姓名</td>
				<td>性别</td>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
		
	</table>
</body>
</html>