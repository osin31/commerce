<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	// 판매상태 코드
	var codes = ${gridCodes };
</script>
<body class="window-body">
	<form id="product-form">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품승인</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<c:forEach items="${aprvStatCodeList }" var="item">
						<c:if test="${item.codeDtlNo eq product.aprvStatCode }">
							<h3 class="content-title">승인상태 : ${item.codeDtlName }</h3>
							<input type="hidden" name="aprvStatCode" id="aprv-stat-code" value="${product.aprvStatCode }">
						</c:if>
					</c:forEach>
				</div>
			</div>
			<!-- E : content-header -->
			<c:if test="${product.aprvStatCode eq '10003'}">
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>승인반려</caption>
				<colgroup>
					<col style="width: 150px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">반려사유</th>
						<td class="input">
							<textarea class="ui-textarea" title="반려사유 입력">${product.productApprovalHistory[0].returnRsnText }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			</c:if>

			<!-- S : 20190322 수정 // 코드영역 수정 -->

			<!-- S : tbl-row -->
			<%@include file="/WEB-INF/views/product/product/product-detail-popup-top-wrapper.jsp"%>
			<!-- E : tbl-row -->

			<%@include file="/WEB-INF/views/product/product/product-detail-popup-tab-wrapper.jsp"%>
		</div>
	</div>
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
	var isMmnyPrdt = ${product.mmnyPrdtYn eq 'Y' ? 'true' : 'false' };<%-- 자사상품여부 --%>
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.detail.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.approval.detail.js<%=_DP_REV%>"></script>
</body>
