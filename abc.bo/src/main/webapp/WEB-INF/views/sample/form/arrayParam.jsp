<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

</head>
<body>폼 전송 <br>

	<form action="" method="post">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="prdNo" value="112344010"/>
		<input type="hidden" name="prdNm" value="아디다스 신발 "/>		
		<input type="hidden" name="item" value="A10001"/>
		<input type="hidden" name="item" value="A10002"/>
		
		<input type="hidden" name="paramArr" value="파라메터1"/>
		<input type="hidden" name="paramArr" value="파라메터2"/>
		<input type="hidden" name="paramArr" value="파라메터3"/>
		<input type="hidden" name="paramArr" value="파라메터4"/>
		<input type="hidden" name="numberArr" value="12346934"/>
		<input type="hidden" name="numberArr" value="12346934"/>
		<input type="hidden" name="numberArr" value="12346934"/>
		<input type="hidden" name="priceArr" value="3,000"/>
		<input type="hidden" name="priceArr" value="3,000"/>
		<input type="hidden" name="priceArr" value="3,000"/>
		<input type="hidden" name="smallArr" value="4,000.10"/>
		<input type="hidden" name="smallArr" value="4,000.10"/>
		<input type="hidden" name="smallArr" value="3,000.10"/>
		
	</form>
	<button id="genericArrayParam"> generic Array param</button>
	<button id="createArrayParam"> array param</button>
	<button id="mapArrayParam"> map array param</button>
	
</body>

<script>

$(document).ready(function(){
	

	$("#genericArrayParam").click(function(){
		$("form").attr("action","/sample/form/genericArrayParam").submit();
	});

	$("#createArrayParam").click(function(){
		$("form").attr("action","/sample/form/createArrayParam").submit();
	});
	
	$("#mapArrayParam").click(function(){
		$("form").attr("action","/sample/form/mapArrayParam").submit();
	});
});
</script>
</html>