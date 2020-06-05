<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sample Batch form</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>배치 업무 등록 <br>

	<form id="addJobForm" name="addJobForm" action="/sample/batch/addScheduleJob">
		프로젝트 명 : <input type="text" id="schedName" name="schedName" /><br>
	
		업무 그룹 : 
				<select id="jobGroup" name="jobGroup">
				</select><br><br>
		업무 명: <input type="text" id="jobName" name="jobName" /><br>
		업무 설명: <input type="text" id="jobDescription" name="jobDescription" /><br>
		
		
	</form>
	<button id="addJob">배치 업무 등록</button>

<script>
$(document).ready(function(){
	
	//샘플용입니다.DB에서 공통 코드 테이블에서 읽어오든 다른 테이블을 만들어 읽어오든 해야 합니다.
	var bizCode = {
			"BC-001" : "회원",
			"BC-002" : "전시",
			"BC-003" : "상품",
			"BC-004" : "주문",
			"BC-005" : "시스템"
	};
	
	//jobGroup select option 설정
	var $select = $("#jobGroup");
	var selectHtml = "";
	for ( var key in bizCode) {
		selectHtml += '<option value="'+key+'">'+bizCode[key]+'</option>';
	}
	
	$select.html(selectHtml);
	
	$("#addJob").on("click",function(){
		var addJobReq = $.ajax({
			url: "/sample/batch/addScheduleJob",
				method: "POST",
				data : {
					schedName : $("#schedName").val(),
					jobGroup : $("#jobGroup").val(),
					jobName : $("#jobName").val()
				},
				dataType: "json"
			});
		
			addJobReq.done(function( d ) {
				console.log( d );
			});
			 
			addJobReq.fail(function( jqXHR, textStatus ) {
				alert( "Request failed: " + textStatus );
			});
		
	});


   
});
</script>
</body>
</html>