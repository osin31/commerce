<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript">
	// 판매상태 코드
	var codes = ${gridCodes };
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품승인</h1>
		</div>
		<div class="window-content">
			<form id="product-form">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">승인상태 :
							<c:forEach items="${aprvStatCodeList }" var="item">
								<c:if test="${item.codeDtlNo eq product.aprvStatCode }">
									${item.codeDtlName }
								</c:if>
							</c:forEach>
						</h3>
						<!--
						<h3 class="content-title">판매사이트 / 온라인 상품 코드  :
							<c:forEach items="${channelList }" var="item">
								<c:if test="${item.chnnlNo eq product.chnnlNo }">
									${item.chnnlName }
								</c:if>
							</c:forEach>
							/ ${product.prdtNo }
						</h3>
						 -->
						<input type="hidden" name="prdtNo" value="${product.prdtNo }">
						<input type="hidden" name="aprvStatCode" id="aprv-stat-code" value="${product.aprvStatCode }">
						<input type="hidden" name="rgsterNo" value="${product.rgsterNo }">
						<input type="hidden" name="rgstDtm" value="${product.rgstDtm }">
					</div>
					<c:if test="${product.aprvStatCode eq '10001' }"> <!-- 승인요청상태인 상품만 보여주기 -->
						<div class="fr">
							<a href="javascript:void(0);" id="approval" class="btn-sm btn-func">승인완료</a>
							<a href="javascript:void(0);" id="return-on" class="btn-sm btn-del">승인반려</a>
						</div>
					</c:if>
				</div>
				<!-- E : content-header -->

				<!-- S : 반려처리일 경우, 노출되는 영역 -->
				<!-- S : tbl-row -->
				<table class="tbl-row" id="return-table" ${product.aprvStatCode ne '10003' ? 'style="display:none;"' : '' };">
					<caption>승인반려</caption>
					<colgroup>
						<col style="width: 150px">
						<col>
						<c:if test="${product.aprvStatCode ne '10003' }">
							<col style="width: 120px">
						</c:if>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">반려사유</th>
							<td class="input">
								<textarea class="ui-textarea" title="반려사유 입력" name="returnRsnText">${product.productApprovalHistory[0].returnRsnText }</textarea>
							</td>
							<c:if test="${product.aprvStatCode ne '10003' }">
								<td class="input" >
									<a href="#" id="return" class="btn-sm btn-save">저장</a>
									<a href="#" id="return-off" class="btn-sm btn-del">취소</a>
								</td>
							</c:if>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
			</form>
			<!-- E : 반려처리일 경우, 노출되는 영역 -->

			<!-- S : 20190322 수정 // 코드영역 수정 -->
			<%--
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>상품상세 정보</caption>
				<colgroup>
					<col style="width: 200px">
					<col style="width: 205px">
					<col style="width: 205px">
					<col>
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="th-lborder">채널구분</th>
						<td class="text-center" colspan="2">A-RT</td>
						<td class="text-center">OTS</td>
						<td class="text-center">조회중인 상품 복사</td>
					</tr>
					<tr>
						<th scope="row">온라인 상품코드</th>
						<td class="text-center td-spot">
							<button type="button" class="link">${product.prdtNo }</button>
						</td>
						<td class="text-center td-spot">
							<c:forEach items="${sellStatCodeList }" var="item" varStatus="status">
								<c:if test="${item.codeDtlNo eq product.sellStatCode}">
									${item.codeDtlName }
								</c:if>
							</c:forEach>
							&nbsp;/&nbsp;
							<c:choose>
								<c:when test="${product.dispYn eq 'Y' }">
								전시
								</c:when>
								<c:otherwise>
								미전시
								</c:otherwise>
							</c:choose>
						</td>
						<td class="text-center">등록불가</td>
						<td class="text-center">
							<button type="button" class="link">복사등록</button>
						</td>
					</tr>
				</tbody>
			</table>
			--%>
			<!-- S : tbl-row -->
			<%@include file="../product/product-detail-popup-top-wrapper.jsp"%>
			<!-- E : tbl-row -->

			<%@include file="../product/product-detail-popup-tab-wrapper.jsp"%>
		</div>
	</div>
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
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.detail.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.vndrApproval.detail.js<%=_DP_REV%>"></script>
</body>