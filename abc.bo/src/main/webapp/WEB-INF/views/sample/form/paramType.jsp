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

	<form action="/sample/form/paramType" method="post">
		<input type="hidden" name="param" value="파라메터"/>
		<input type="hidden" name="number" value="12346934"/>
		<input type="hidden" name="price" value="4,000"/>
		<input type="hidden" name="small" value="3,000.10"/>
	
	
	
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
		<input type="submit" />
	</form>
	
	<button id="param"> ajax param</button>
	<button id="arrayparam"> array param</button>
<script>

$(document).ready(function(){
	

	$("#param").click(function(){
		
		$.ajax({
			type :"post",
			data : {
				prdNo : "100002010",
				prdNm : "나이키 신발"
			},
	        url:'/sample/form/objectParam',
	        success:function(data){
	            $('#time').append(data);
	        }
	    })
	});
   
	$("#arrayparam").click(function(){
		
		$.ajax({
			type :"post",
			data : [
				{prdNo : "100002010"},
				{prdNm : "나이키 신발"}
			],
	        url:'/sample/form/objectParam',
	        success:function(data){
	            $('#time').append(data);
	        }
	    })
		
	});

	
});
</script>
</body>
</html>