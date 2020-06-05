<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품검색</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">상품정보 검색</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<%@include file="/WEB-INF/views/product/product/product-list-search.jsp" %>

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">상품 목록</h3>
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
				<div class="fr">
					<button type="button" class="btn-sm btn-func" data-button="add-product">선택 상품 추가</button>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="product-list">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	<!-- E : container -->
	<script type="text/javascript">
		// 판매상태 코드
		var codes = ${gridCodes };
		var userAuthority = "${userAuthority}";
		var tierFlagCodeCombo = ${tierFlagCodeCombo};
	</script>
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.popup.find.js<%=_DP_REV%>"></script>
</body>
</html>
