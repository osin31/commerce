<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
	});
	
</script>
<body class="window-body">
<input type="hidden" id="orderNo" 	name="orderNo" value="${orderDtail.orderNo}">
<input type="hidden" id="dlvyTypeCode" name="dlvyTypeCode" value="${orderDtail.dlvyTypeCode}">
<input type="hidden" id="orderPrdtSeq" name="orderPrdtSeq" value="${dlvyInfo.orderPrdtSeq}">
<input type="hidden" id="orderDlvyHistSeq" name="orderDlvyHistSeq" value="${dlvyInfo.orderDlvyHistSeq}">
<input type="hidden" id="dlvyStatCode" name="dlvyStatCode" value="${dlvyInfo.dlvyStatCode}">
<input type="hidden" id="dlvyIdText" name="dlvyIdText" value="${dlvyInfo.dlvyIdText}">
<input type="hidden" id="orgStockGbnCode" name="orgStockGbnCode" value="${dlvyInfo.stockGbnCode}">
	<div class="window-wrap">
		<div class="window-title">
			<h1>재배송 상세 정보 입력</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">[주문번호 <span>${orderDtail.orderNo}</span>] 재배송 상세 정보 입력</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">재배송정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>재배송정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">배송처</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select class="ui-sel" id="stockGbnCode"" name="stockGbnCode"" title="배송처 선택">
									<option value="">선택</option>
									<c:forEach items="${STOCK_GBN_CODE}" var="stockGbnCode">
										<option value="${stockGbnCode.codeDtlNo}" >${stockGbnCode.codeDtlName}</option>
									</c:forEach>
								</select>
								<!-- S : 20190305 수정 // search dropdown 단일검색 수정
								<span class="ip-search-box" id="storeInfoView" name="storeInfoView" style="display:none;" >
									<input type="text" class="ui-input" title="검색어 입력" name="storeName" id="storeName" readonly  value="">
									<input type="hidden" name="storeNo" id="storeNo" value="" >
									<a href="javascript:void(0);" class="btn-search" id="searchStore"><i class="ico-search"><span class="offscreen">검색</span></i></a>
								</span>
								: 20190305 수정 // search dropdown 단일검색 수정 -->
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 온라인 : 온라인 물류, 스마트 : 스마트 물류 , 매장 </li>
					<li>* 발송처가 동일한 모든 상품들이 일괄 처리 됩니다.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-link"  id="reDeliverySave" >확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/delivery/abcmart.delivery.redelivery.js<%=_DP_REV%>"></script>