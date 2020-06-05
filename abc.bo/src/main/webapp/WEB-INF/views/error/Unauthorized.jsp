<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%-- <%@include file="/WEB-INF/views/common/menu.jsp"%> --%>

<!-- 에러페이지입니다. -->
<%-- ${message}<br> --%>
<%-- ${status}<br> --%>
<%-- ${error}<br> --%>
<%-- ${exception}<br> --%>
<%-- ${trace}<br> --%>
	<script>
		$(".wrap").addClass("error");
	</script>
	<!-- S : error 페이지 -->
	<div class="error-wrap">
		<h1 class="error-title">Unauthorized - 접근 권한이 없습니다.</h1>

		<div class="error-code" align="center"> 
			<span class="code-title">상태 코드: 401 </span>
			<span class="code-num">${status}</span>
		</div>

<!-- 		<div class="error-desc"> -->
<%-- 			<pre>${exception}</pre> --%>
<!-- 		</div> -->

		<div class="error-btn">
			<a href="/" class="btn-lg btn-link">첫 화면으로</a>
		</div>
	</div>
	<!-- E : error 페이지 -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>
