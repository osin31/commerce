<%@page import="org.springframework.http.HttpStatus"%>
<%@page import="org.apache.logging.log4j.status.StatusConsoleListener"%>
<%@page import="org.springframework.http.MediaType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%-- <%@include file="/WEB-INF/views/common/menu.jsp"%> --%>

<!-- 에러페이지입니다. -->
<%-- ${message}<br> --%>
<%-- ${status}<br> --%>
<%-- ${error}<br> --%>
<%-- ${exception}<br> --%>
<%-- ${trace}<br> --%>
	<%
		Exception e = (Exception)request.getAttribute("exception");
	%>
	<script>
		$(document).ready(function(){
			$(".wrap").addClass("error");
		});
	</script>
	<!-- S : error 페이지 -->
	<div class="wrap error">
		<div class="error-wrap">
			<h1 class="error-title"><%=HttpStatus.valueOf(response.getStatus())%></h1>
			<div class="error-code" align="center"> 
<%-- 				<span class="code-num"><%=e%></span> --%>
			</div>
			<div class="error-btn">
			<a href="/" class="btn-lg btn-link">첫 화면으로</a>
			</div>
		</div>
	</div>
	<!-- E : error 페이지 -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>
