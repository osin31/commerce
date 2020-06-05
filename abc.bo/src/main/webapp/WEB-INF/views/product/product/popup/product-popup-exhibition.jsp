<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1 id="title">TITLE</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title" id="name">NAME</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="exhibition-page">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">기획전 전시정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="exhibition-planning">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-link" data-a="close">확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.popup.exhibition.js<%=_DP_REV%>"></script>
</body>
</html>
