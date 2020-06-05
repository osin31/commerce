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
	<a href="/sample/transaction/transaction" >기본 트랜잭션 </a><br>
	<a href="/sample/transaction/insertRollback" >insert rollback - db insert 중 쿼리 에러 발생</a><br>
	<a href="/sample/transaction/throwsErrorRollback" >insert throw rollback - db insert 중 throw new Exception이 발생 하였을 경우</a><br>
	
<script>

</script>
</body>
</html>