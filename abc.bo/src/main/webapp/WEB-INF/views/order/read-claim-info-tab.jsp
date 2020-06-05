<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">

	$(function() {
		
		<%-- 클레임 목록/검색 변수 선언 --%>
		abc.biz.order.claim.info.tab.CLM_GBN_CODE_CANCEL			= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.order.claim.info.tab.CLM_GBN_CODE_EXCHANGE			= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.order.claim.info.tab.CLM_GBN_CODE_RETURN			= "${CommonCode.CLM_GBN_CODE_RETURN}";
		abc.biz.order.claim.info.tab.PYMNT_STAT_CODE_REFUND_ACCEPT		= "${CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT}";
		abc.biz.order.claim.info.tab.PYMNT_STAT_CODE_REFUND_COMPLETE	= "${CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE}";
		abc.biz.order.claim.info.tab.PYMNT_STAT_CODE_REDEMP_ACCEPT		= "${CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT}";
		abc.biz.order.claim.info.tab.PYMNT_STAT_CODE_REDEMP_COMPLETE	= "${CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE}";
		
		
		abc.biz.order.claim.info.tab.initClaimDiscountCancelInfo();  			// 클레임 할인 취소
		abc.biz.order.claim.info.tab.initClaimRedemptionRefundInfo();  			// 글레임 환수/환불금액관리
		abc.biz.order.claim.info.tab.initclaimInfo();  			// 클레임 내역
		abc.biz.order.claim.info.tab.initafterServicetInfo();  	// A/S내역
	});
	
	<%-- 환수/환불금약관리 그리드 Click 이벤트 --%>
	function claimRedemptionRefundInfoSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.order.claim.info.tab.claimRedemptionRefundInfoSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 환수/환불금약관리 그리드 조회 후 데이터 가공 --%>
	function claimRedemptionRefundInfoSheet_OnSearchEnd(){
		
		abc.biz.order.claim.info.tab.claimRedemptionRefundInfoSheetOnSearchEnd();
	}
	
	<%-- 클레임 그리드 Click 이벤트 --%>
	function claimInfoSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.order.claim.info.tab.claimInfoSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 클레임 그리드 조회 후 데이터 가공 --%>
	function claimInfoSheet_OnSearchEnd(){
		
		abc.biz.order.claim.info.tab.claimInfoSheetOnSearchEnd();
	}
	
	<%-- AS 그리드 Click 이벤트 --%>
	function afterServiceInfoSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.order.claim.info.tab.afterServiceInfoSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
</script>
<!-- S:tab_content -->
<div id="tabContent2" class="tab-content">

	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="orgOrderNo" name="orgOrderNo" value='<c:out value="${param.orgOrderNo}"/>'>
		<input type="hidden" id="orderNo" 	 name="orderNo"    value='<c:out value="${param.orderNo}"/>'>
	</form>
	
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">클레임내역 (교환 | 반품 | 취소)</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="claimInfo" style="width:100%; height:429px;">
		</div>
	</div>
	<!-- E : ibsheet-wrap -->

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">A/S내역</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="afterServicetInfo" style="width:100%; height:429px;">
		</div>
	</div>
	<!-- E : ibsheet-wrap -->
	
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">환불정보</h3>
		</div>
	</div>
	<!-- E : content-header -->
	
		<!-- S : col-wrap -->
	<div class="col-wrap" >
		<div class="col" style="min-width: 45%;">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="title">환불정보</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : tbl-row -->
			<table class="tbl-col">
				<caption>환불정보</caption>
				<colgroup>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" >구분</th>
						<th scope="col">합계</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row" >총 취소금액(①)</th>
						<td class="text-right">
							<br>
							<c:choose>
								<c:when test="${empty totalCancelAmt}">
									<strong>상품금액</strong> <span>0</span>원<br><br>
									<strong>주문배송비</strong> <span>0</span>원<br><br>
								</c:when>
								<c:otherwise>
									<strong>상품금액</strong> <span><fmt:formatNumber value="${totalCancelAmt.cancelPrdtAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									<c:if test="${totalCancelAmt.cancelDlvyAmt ne '0'}">
										<strong>주문배송비</strong> <span><fmt:formatNumber value="${totalCancelAmt.cancelDlvyAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									</c:if>
									<c:if test="${refundPreviousRedempDlvySumAmt ne '0'}">
										<strong>추가배송비</strong> <span><fmt:formatNumber value="${refundPreviousRedempDlvySumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									</c:if>
									<c:if test="${addMultiBuyDifferSumAmt ne '0'}">
										<strong>프로모션</strong> <span><fmt:formatNumber value="${addMultiBuyDifferSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									</c:if>
									<strong style="color:blue;">합계 : <span><fmt:formatNumber value="${totalCancelAmt.cancelPrdtAmt + totalCancelAmt.cancelDlvyAmt + refundPreviousRedempDlvySumAmt + addMultiBuyDifferSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원</strong><br><br>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" >총 추가비용(②)</th>
						<td class="text-right">
							<br>
							<c:choose>
								<c:when test="${empty totalAddAmtList}">
									<strong>추가비용</strong> <span>0</span>원<br><br>
								</c:when>
								<c:otherwise>
									<c:forEach var="list" items="${totalAddAmtList}">
										<strong><c:out value="${list.ocrncRsnCodeName}"/></strong> <span><fmt:formatNumber value="${list.redempAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									</c:forEach>
									<strong style="color:blue;">합계 : <span><fmt:formatNumber value="${totalAddAmtSum}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원</strong><br><br>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" >총 환불금액(①-②)</th>
						<td class="text-right">
							<br>
							<c:choose>
								<c:when test="${empty totalRefundAmtList}">
									<strong>환불금액</strong> <span>0</span>원<br><br>
								</c:when>
								<c:otherwise>
									<c:forEach var="list" items="${totalRefundAmtList}">
										<strong><c:out value="${list.pymntMeansCodeName}"/></strong> <span><fmt:formatNumber value="${list.refundAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원<br><br>
									</c:forEach>
									<strong style="color:blue;">합계 : <span><fmt:formatNumber value="${totalRefundAmtSum}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></span>원</strong><br><br>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 통합맴버심 회원의 경우에만 결제 시 사용한 쿠폰,포인트가 반환 처리 됩니다.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->
		</div>
		<div class="col">
			<!-- S : row-wrap -->
			<div class="row-wrap">
				<div class="row">
					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="title">할인취소내역</span>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="claimDiscountCancelInfo" style="width:100%; height:370px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<div class="row">
					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="title">환수/환불금액관리</span>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="claimRedemptionRefundInfo" style="width:100%; height:370px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
			</div>
			<!-- E : row-wrap -->
		</div>
	</div>
	<!-- E : col-wrap -->
	
</div>
<!-- E:tab_content -->
<script src="/static/common/js/biz/order/abcmart.claim.info.tab.js<%=_DP_REV%>"></script>
