<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>브랜드 검색</h1>
		</div>
		<div class="window-content">
	
			<%@include file="brand-popup-find-search.jsp"%>
	
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">브랜드 목록</h3>
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
				<div id="product-brand-list">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save" id="save">저장</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var siteGubunText = "${siteGubunText }";
		var siteGubunCode = "${siteGubunCode }";
	</script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.brand.popup.find.js<%=_DP_REV%>"></script>
</body>
</html>
