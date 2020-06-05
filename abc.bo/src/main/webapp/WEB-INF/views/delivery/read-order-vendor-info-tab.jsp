<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		
		abc.biz.order.vendor.info.tab.codeCombo = ${codeCombo};
		<%-- 주문 상세  초기 세팅. --%>
		abc.biz.order.vendor.info.tab.initOrderCompanyProductGrid(); 	// 입점업체 상품 
		
		
	});

	<%-- 입점 업체 상품 그리드 Click 이벤트 --%>
	function orderCompanyProductSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		if ( Row != 0) {
			// BO 상품 상세 팝업
			if ( orderCompanyProductSheet.ColSaveName(Col) == "prdtNo" && Value != "" ) {
				var saveName = "prdtNo";
				var param = { prdtNo : orderCompanyProductSheet.GetCellValue(Row, "prdtNo") };
				abc.readonlyProductDetailPopup(param);
			}
			// FO 상품 상세 페이지
			if ( orderCompanyProductSheet.ColSaveName(Col) == "prdtName" && Value != "" ) {
				var saveName = "prdtName";
				var siteNo = orderCompanyProductSheet.GetCellValue(Row, "siteNo");
				var prdtNo = orderCompanyProductSheet.GetCellValue(Row, "prdtNo");
				var prdtTypeCode = orderCompanyProductSheet.GetCellValue(Row, "prdtTypeCode");

				if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY ) {
					abc.productFrontUrl(prdtNo);
				}
			}

		}
	}
	
	// 입점사 상품 상품  영역
	function orderCompanyProductSheet_OnRowSearchEnd(row){
		var prdtNameHtml = "";
		var orderPrdtStatCode 			= orderCompanyProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderPrdtStatCodeName 		= orderCompanyProductSheet.GetCellValue(row, "orderPrdtStatCodeName");
		var deliveryConfirmRgstDtm 		= orderCompanyProductSheet.GetCellValue(row, "deliveryConfirmRgstDtm");
		var dlvyDscntcYn 				= orderCompanyProductSheet.GetCellValue(row, "dlvyDscntcYn");
		var prdtName 					= orderCompanyProductSheet.GetCellValue(row, "prdtName");
		var giftPrdtNo 					= orderCompanyProductSheet.GetCellValue(row, "giftPrdtNo");
		var giftPrdtName 				= orderCompanyProductSheet.GetCellValue(row, "giftPrdtName");
		var prdtTypeCode 				= orderCompanyProductSheet.GetCellValue(row, "prdtTypeCode");
		var salesCnclGbnType 			= orderCompanyProductSheet.GetCellValue(row, "salesCnclGbnType");
		var imageUrl 					= orderCompanyProductSheet.GetCellValue(row, "imageUrl");
		var prdFullPath = "";
		deliveryConfirmRgstDtm 			= new Date(deliveryConfirmRgstDtm).format("yyyy-MM-dd hh:mm:ss");
		
		// 교환 매출건 row 색 처리
		if (salesCnclGbnType == abc.order.consts.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE) { // 교환 매출 건
			orderCompanyProductSheet.SetRowBackColor(row,"#E0ECF8");
		}
		// 취소완료건 매출건 row 색 처리
		if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE) { // 상품 취소건
			orderCompanyProductSheet.SetRowBackColor(row,"#F8C0C0");
		}
		
		// 상품명 html ( 사은품명 노출 )
		prdtNameHtml+= prdtName;
		if (giftPrdtNo != "" ) {
			prdtNameHtml+= "<p> └사은품 :" + giftPrdtName +"</p>"
		}
		orderCompanyProductSheet.SetCellValue(row, "prdtName", prdtNameHtml);
		
		// 옵션변경  -- 상품준비중 이전  ORDER_PRDT_STAT_CODE 10002 결제 완료 일경우에만 변경 가능   
		// 배송비 상품 제외 
// 		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY && orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE) {
// 			orderCompanyProductSheet.SetMergeCell(row, 7, 1 , 2 );
// 		}
	}
	
</script>
<div>
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">[<c:out value="${orderDtail.dlvyTypeCodeName}"/>] 주문상품</h3> 
		</div>
	</div>
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="orderNo" name="orderNo" value='<c:out value="${param.orderNo}"/>'>
		<input type="hidden" id="memberNo" name="memberNo" value="${orderDtail.memberNo}">
		<input type="hidden" id="mmnyPrdtYn" name="mmnyPrdtYn" value=""> <!-- 그리드 조회시 상품 분기 조회  자사 Y 업체 N -->
		<input type="hidden" id="dlvyTypeCode" name="dlvyTypeCode" title="배송유형코드" value="${orderDtail.dlvyTypeCode}" >
		<input type="hidden" id="orderStatCode" name="orderStatCode" title="주문상태코드" value="${orderDtail.orderStatCode}" >
	</form>
	<!-- E : content-header -->



	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div  id="orderCompanyProductGrid" style="width:100%; height:429px;">
		</div>
	</div>
	<!-- E : ibsheet-wrap -->

	
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">배송정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : col-wrap -->
	<div class="col-wrap">
		<div class="col">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="title">주문자정보</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>주문자정보</caption>
				<colgroup>
					<col style="width: 120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">주문자명</th>
						<td><c:out value="${orderDtail.buyerName}" default=""/></td>
					</tr>
					<tr>
						<th scope="row">휴대폰번호</th>
						<td><c:out value="${orderDtail.buyerHdphnNoText}" default=""/></td>
					</tr>
					<tr>
						<th scope="row">이메일</th>
						<td><c:out value="${orderDtail.buyerEmailAddrText}" default=""/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
		</div>
		<div class="col">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="title">수령자정보</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>수령자 정보</caption>
				<colgroup>
					<col style="width: 120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">수령자명</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="수령자명 입력" id="rcvrName" name="rcvrName" value="${orderDtail.rcvrName}" readonly>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="휴대폰번호 입력" id="rcvrHdphnNoText" name="rcvrHdphnNoText" value="${orderDtail.rcvrHdphnNoText}" readonly>
							<input type="hidden"id="rcvrTelNoText" name="rcvrTelNoText" value="${orderDtail.rcvrTelNoText}" >
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">주소</span>
						</th>
						<td class="input">
							<span class="address-box">
								<span class="zip-code-wrap">
									<input type="text" class="ui-input" title="우편번호 입력" id="rcvrPostCodeText" name="rcvrPostCodeText" value="${orderDtail.rcvrPostCodeText}" readonly>
								</span>
								<span class="address-wrap">
									<input type="text" class="ui-input" id="rcvrPostAddrText" name="rcvrPostAddrText" placeholder="시/군/구 동" title="시/군/구 동 입력" value="${orderDtail.rcvrPostAddrText}" readonly>
									<input type="text" class="ui-input" id="rcvrDtlAddrText" name="rcvrDtlAddrText" placeholder="상세주소" title="상세주소 입력" value="${orderDtail.rcvrDtlAddrText}" readonly >
								</span>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">배송 시 요청사항</th>
						<td class="input">
							<!-- S : msg-wrap -->
							<span class="msg-wrap">
								<span class="msg-box ip-size-lg">
									<textarea class="ui-textarea" placeholder="배송 메시지는 40자 이내로 입력해 주세요." title="배송 메세지 입력" value="${orderDtail.dlvyMemoText}" readonly></textarea>
									<span class="text-limit">
										<span class="desc">(<span>0</span>/40 자)</span>
									</span>
								</span>
							</span>
							<!-- E : msg-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
		</div>
	</div>
	<!-- E : col-wrap -->

	</div>
	<!-- E : col-wrap -->
</div>
<!-- E:tab_content -->
<script src="/static/common/js/biz/order/abcmart.order.vendor.info.tab.js<%=_DP_REV%>"></script>
