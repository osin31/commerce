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

	<form action="/sample/form/xssParam" method="post">
		<input type="hidden" name="prdNo" value="100002010"/>
		<input type="hidden" name="prdNm" value="나이키 신발 "/>
		<input type="hidden" name="item" value="A10001"/>
		<input type="submit" />
	</form>
	<button id="xssParam"> xss param</button>
	<button id="createParam"> create param</button>
	
<script>

var param = {
		html : "<a>window.alert('ㅎㅎㅎㅎㅎ')</a>",
		html2 : "<html><body><a>window.alert('가나다라마바사')</a></body></html>",
		htmlArr : ["<html><body><a>window.alert('12345')</a></body></html>","<html><body><script>window.alert('asdfasdf')</a></body></html>"]
	};
	
$(document).ready(function(){
	

	$("#xssParam").click(function(){
		$.ajax({
			type :"post",
			data : param,
	        url:"/sample/form/xssParam",
	        success:function(data){
	            $('#time').append(data);
	        }
	    })
	});

	$("#createParam").click(function(){
		$("form").attr("action","/sample/form/createParam").submit();
	});
});
</script>
</body>
</html>