<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {
			var contText = $("#content").text().replace(/&amp;/g, '&')
			.replace(/&lt;/g, '<')
			.replace(/&gt;/g, '>')
			.replace(/&quot;/g, '"');
			$("#content").empty().append(contText);
		});
		</script>
<body id="content">
	<c:out value="${cmEmailTemplate.emailContText}" />
</body>
</html>