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

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="page-count">목록개수</label>
						<select class="ui-sel" id="page-count">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="list">
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
	<script type="text/javascript">
		var commonCode = ${commonCode };
	</script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.popup.history.js<%=_DP_REV%>"></script>
</body>
</html>
