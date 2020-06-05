<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sample form</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>폼 전송 <br>

	<form action="/sample/form/objectParam" method="post">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="submit" />
	</form>
	<button id="genericParam"> generic param</button>
	<button id="createParam"> create param</button>
	<button id="mapParam"> map param</button>
	
<script>
$(document).ready(function(){
	

	$("#genericParam").click(function(){
		$("form").attr("action","/sample/form/genericParam").submit();
	});

	$("#createParam").click(function(){
		$("form").attr("action","/sample/form/createParam").submit();
	});
	
	$("#mapParam").click(function(){
		$("form").attr("action","/sample/form/mapParam").submit();
	});
});
</script>
</body>
</html>