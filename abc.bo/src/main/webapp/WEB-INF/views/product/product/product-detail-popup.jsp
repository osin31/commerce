<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	
<script type="text/javascript">
	$(function() {
		<%-- 2020.01.30 : Const.java 에서 내리는 채널별 카테고리 최대 LEVEL --%>
		abc.biz.product.product.detail.MAX_CTGR_LEVEL_ART	= "${Const.MAX_CTGR_LEVEL_ART}";
		abc.biz.product.product.detail.MAX_CTGR_LEVEL_ABC	= "${Const.MAX_CTGR_LEVEL_ABC}";
		abc.biz.product.product.detail.MAX_CTGR_LEVEL_GS	= "${Const.MAX_CTGR_LEVEL_GS}";
		abc.biz.product.product.detail.MAX_CTGR_LEVEL_OTS	= "${Const.MAX_CTGR_LEVEL_OTS}";
		abc.biz.product.product.detail.MAX_CTGR_LEVEL_KIDS	= "${Const.MAX_CTGR_LEVEL_KIDS}";
	});
</script>
	
	<form id="product-form">
		<div class="window-wrap">
			<div class="window-title">
				<h1>상품상세</h1>
			</div>
			<div class="window-content">
	
				<!-- S : tbl-row -->
				<%@include file="/WEB-INF/views/product/product/product-detail-popup-top-wrapper.jsp"%>
				<!-- E : tbl-row -->
	
				<%@include file="/WEB-INF/views/product/product/product-detail-popup-tab-wrapper.jsp"%>
			</div>
		</div>
		<c:if test="${newErpProductYn eq 'Y' }">
			<input type="hidden" name="newErpProductYn" value="${newErpProductYn }"/><%-- 자사 상품 등록 시, I/F 연계데이터를 위한 부가 정보 --%>
		</c:if>
	</form>
	<%-- 색상연계상품 조회 폼 --%>
	<form name="cntc-prdt-setup-form">
		<input type="hidden" name="prdtNo" value="${product.prdtNo }"/>
		<input type="hidden" name="rltnPrdtTypeCode" value="${RLTN_PRDT_TYPE_CODE_LINKED }"/>
	</form>
	<%-- 관련용품 조회 폼 --%>
	<form name="rltn-goods-setup-form">
		<input type="hidden" name="prdtNo" value="${product.prdtNo }"/>
		<input type="hidden" name="rltnPrdtTypeCode" value="${RLTN_PRDT_TYPE_CODE_RELATED }"/>
	</form>
	<%-- 상품 옵션 조회 폼 --%>
	<form name="product-option-form">
		<input type="hidden" name="prdtNo" value="${product.prdtNo }"/>
		<c:if test="${newErpProductYn eq 'Y' }">
			<%-- 자사 상품 등록 시, I/F 연계데이터를 위한 부가 정보 --%>
			<input type="hidden" name="vndrPrdtNoText" value="${vndrPrdtNoText }"/>
			<input type="hidden" name="chnnlNo" value="${chnnlNo }"/>
			<input type="hidden" name="newErpProductYn" value="${newErpProductYn }"/>
		</c:if>
	</form>
	<script type="text/javascript">
		var codes = ${gridCodes };<%-- 판매상태코드 --%>
		var isMmnyPrdt = ${product.mmnyPrdtYn eq 'Y' ? 'true' : 'false' };<%-- 자사상품여부 --%>
	</script>
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.detail.js<%=_DP_REV%>"></script>
</body>
</html>
