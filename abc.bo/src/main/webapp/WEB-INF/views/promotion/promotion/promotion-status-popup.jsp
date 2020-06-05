<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>


<script type="text/javascript">
	$(function() { 
		abc.biz.promotion.status.popup.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.promotion.status.popup.siteCombo = ${siteCombo}; //stie 코드 값
  	});
</script>

<body class="window-body">
<form id="searchForm">
	<div class="window-wrap">
		<div class="window-title">
			<h1>프로모션 현황</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">프로모션 현황</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>프로모션 현황</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">총 주문 건수</th>
						<td>${empty prPromotion.totalOrdCount ? 0 : prPromotion.totalOrdCount}건</td>
						<th scope="row">총 주문 수량</th>
						<td>${empty prPromotion.totalOrdQty ? 0 : prPromotion.totalOrdQty}개</td>
						<th scope="row">총 주문 금액</th>
						<fmt:formatNumber var="formatOrdPayment" value="${prPromotion.totalOrdPayment}" pattern="#,###"/>
						<td>${empty prPromotion.totalOrdPayment ? 0 : formatOrdPayment}원</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" id="excelDown" class="btn-sm btn-func">엑셀 다운로드</a>
					</div>
				</div>
				<input type="hidden" name="promoNo" value="${promoNo}" />
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="statusSheet">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</form>

<form name="excelForm" method="post">
	<input type="hidden" name="promoNo" value="${promoNo}" />
</form>
</body>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.promotion.status.popup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp" %>