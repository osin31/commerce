<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>sample index page <br>

form  :<br>
	<a href="/sample/form/arrayParam.jspa"/>배열 파라메터 전송</a><br>
	<a href="/sample/form/objectParam.jspa"/>일반 파라메터 전송</a><br>
	<a href="/sample/form/paramType.jspa"/>파라메터 primitive 타입으로 값 가져오기</a><br>
	<a href="/sample/form/xssParam.jspa"/>파라메터 xss처리</a><br>
	
file  :<br>
	<a href="/sample/file/upload.jspa"/>업로드</a><br>
	<a href="/sample/file/download.jspa"/>다운로드</a><br>
	
tx  :<br>
<a href="/sample/tx/tx.jspa"/>트랜잭션</a><br>

배치  :<br>
<a href="/sample/batch/batch"/>트랜잭션</a><br>
</body>
</html>