<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {

		abc.biz.order.info.tab.codeCombo = ${codeCombo};
		<%-- 주문 상세  초기 세팅. --%>
		abc.biz.order.info.tab.initOrderABCProductGrid();  		// abc 자사 상품
		abc.biz.order.info.tab.initOrderCompanyProductGrid(); 	// 입점업체 상품
		abc.biz.order.info.tab.initOrderDiscountInfoGrid(); 	// 입점업체 상품
		abc.biz.order.info.tab.initOrderPaymentInfoGrid(); 		// 입점업체 상품

		// 관리자 메모 글자수 체크

		$("#dlvyMemoTextArea").keyup(function(e){
			var content = $(this).val();
			abc.biz.order.info.tab.stringLengthCheck(content);
		});

		abc.biz.order.info.tab.stringLengthCheck($("#dlvyMemoTextArea").val());

		//abc.biz.order.info.tab.orderDetail = ${orderDetail};

	});

	// 자사 상품  영역
	function orderABCProductSheet_OnRowSearchEnd(row){
		var prdtNameHtml = "";
		var dlvyTypeCode = $("#dlvyTypeCode").val(); // 배송 타입 코드
		var orderPrdtStatCode 			= orderABCProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderPrdtStatCodeName 		= orderABCProductSheet.GetCellValue(row, "orderPrdtStatCodeName");
		var deliveryConfirmRgstDtm 		= orderABCProductSheet.GetCellValue(row, "buyDcsnDtm");
		var dlvyDscntcYn 				= orderABCProductSheet.GetCellValue(row, "dlvyDscntcYn");
		var salesCnclGbnType 			= orderABCProductSheet.GetCellValue(row, "salesCnclGbnType");
		var prdtName 					= orderABCProductSheet.GetCellValue(row, "prdtName");
		var giftPrdtNo 					= orderABCProductSheet.GetCellValue(row, "giftPrdtNo");
		var giftPrdtName 				= orderABCProductSheet.GetCellValue(row, "giftPrdtName");
		var stockGbnCode 				= orderABCProductSheet.GetCellValue(row, "stockGbnCode");
		var prdtTypeCode 				= orderABCProductSheet.GetCellValue(row, "prdtTypeCode");
		var imageUrl 					= orderABCProductSheet.GetCellValue(row, "imageUrl");
		var clmGbnCode 					= orderABCProductSheet.GetCellValue(row, "clmGbnCode");
		var prdtAsAcceptFlag 			= orderABCProductSheet.GetCellValue(row, "prdtAsAcceptFlag");
		var brandName 					= orderABCProductSheet.GetCellValue(row, "brandName");
		var rsvPrdtYn					= orderABCProductSheet.GetCellValue(row, "rsvPrdtYn");
		var clmNo						= orderABCProductSheet.GetCellValue(row, "clmNo");
		var memberTypeCode 	= $("#memberTypeCode").val(); // 회원구분코드

		deliveryConfirmRgstDtm 			= new Date(deliveryConfirmRgstDtm).format("yyyy-MM-dd hh:mm:ss");
		var prdFullPath = "";
		// 교환 매출건 row 색 처리
		if (salesCnclGbnType == abc.order.consts.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE) { // 교환 매출 건
			orderABCProductSheet.SetRowBackColor(row,"#E0ECF8");
		}
		// 취소완료건 매출건 row 색 처리
		if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE) { // 상품 취소건
			orderABCProductSheet.SetRowBackColor(row,"#F8C0C0"); // 2020.03.10 취소상품 색상이 알아보기어렵다 하여 변경
		}


		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY) {
			if(imageUrl != ""){
				prdFullPath = imageUrl;
			}else{
				prdFullPath = "";
			}
		}else{
			prdFullPath = "";
		}

		orderABCProductSheet.SetCellValue(row, "productImg", prdFullPath);

		// 상품명 html ( 사은품명 노출 )
		prdtNameHtml+= "["+brandName+"]<br>";
		prdtNameHtml+= prdtName;
		if (giftPrdtNo != "" ) {
			prdtNameHtml+= "<p> └사은품 :" + giftPrdtName +"</p>"
		}

		orderABCProductSheet.SetCellValue(row, "prdtName", prdtNameHtml);

		// 옵션변경  -- 상품준비중 이전  ORDER_PRDT_STAT_CODE 10002 결제 완료 일경우에만 변경 가능   자사 상품의 경우 AI 온라인 배송의 경우에만
		// 배송비 상품 제외
		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY  // 배송비 상품 제외
				&& orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE // 결제 완료
				&& stockGbnCode == abc.order.consts.STOCK_GBN_CODE_AI // 발송처 AI
				&& dlvyTypeCode == "10000" // 배송유형 택배
				) {
			// 옵션변경 노출
		}else{
			orderABCProductSheet.SetMergeCell(row, 7, 1 , 2 );
		}

		var linkInfo = "";
		var btnInfo = "";

		if ( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM ) {

			linkInfo = '<u style="display: block;">'+deliveryConfirmRgstDtm+'</u>' ;
			linkInfo = linkInfo + '<b style="display: block; margin-top: 7px;"><u onclick="abc.biz.order.info.tab.productHistory('+row+');">'+orderPrdtStatCodeName+'</u>';
			// 클레임이 없을 경우에만 as 신청 가능
			if (clmGbnCode == "") {
				if (prdtAsAcceptFlag != "Y" && memberTypeCode != '10002') { //진행중인 as건이 없을 경우 --비회원이 아닌경우
					btnInfo = '<button class="GMCellButtonBase GMCellButton" type="button" style="margin-left: 7px;" onclick=abc.biz.order.info.tab.asAccept('+row+');>A/S신청</button></b>';
				}
			}
			orderABCProductSheet.SetCellValue(row, "orderPrdtStatCodeName", linkInfo + btnInfo);
		}else {

			linkInfo = '<b><u onclick="abc.biz.order.info.tab.productHistory('+row+');">'+orderPrdtStatCodeName+'</u></b>';
			orderABCProductSheet.SetCellValue(row, "orderPrdtStatCodeName", linkInfo + btnInfo);
		}
		
		/*
		배송중단 재배송  , 결제 완료 , 상품준비중 일경우 노출    ORDER_PRDT_STAT_CODE
		as-is 배송중단 주문접수(입금대기) , 상품준비중
		to-be 	배송중단  버튼 노출 기준  배송중단 N && 입금대기  , 결제 완료 , 상품준비중
				재배송 버튼 노출 기준  배송중단 Y && 입금대기  , 결제 완료 , 상품준비중
		*/
		/*
		 * 2020.03.20 : 배송중단  버튼 노출 기준 추가 배송중단 N && 입금대기 , 결제 완료 , 상품준비중에 추가로
		 * 매장픽업 주문일경우 상품출고,픽업준비완료 에도 배송중단처리 가능
		 */
		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY  
				&& (
						   orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_STAND_BY // 입금대기
						|| orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE // 결제 완료
						|| orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION // 상품준비중
						|| ( orderPrdtStatCode == "10004" && dlvyTypeCode == "10002" ) // 상품출고 (매장픽업주문)
						|| ( orderPrdtStatCode == "10006" && dlvyTypeCode == "10002" ) // 픽업준비완료 (매장픽업주문)
				   )
		   ) { 
			if (dlvyDscntcYn == "Y") { // 배송중단여부
				orderABCProductSheet.SetCellEditable(row,"dlvyDscntcRsnCode", 0);
				orderABCProductSheet.SetCellEditable(row,"dlvyDscntcBtn", 0);
				orderABCProductSheet.SetCellEditable(row,"reDeliveryBtn", 1);
			}else {
				orderABCProductSheet.SetCellEditable(row,"dlvyDscntcRsnCode", 1);
				orderABCProductSheet.SetCellEditable(row,"dlvyDscntcBtn", 1);
				orderABCProductSheet.SetCellEditable(row,"reDeliveryBtn", 0);

			}
		}else {
			orderABCProductSheet.SetCellEditable(row,"dlvyDscntcRsnCode", 0);
			orderABCProductSheet.SetCellEditable(row,"dlvyDscntcBtn", 0);
			orderABCProductSheet.SetCellEditable(row,"reDeliveryBtn", 0);
		}

		// 배송비 상품 체크 박스 비활성화 처리
		if ( prdtTypeCode == abc.order.consts.PRDT_TYPE_CODE_DELIVERY ) {
			orderABCProductSheet.SetCellEditable(row,"updateCheck", 0);
			orderABCProductSheet.SetCellFontUnderline( row ,"prdtName" , 0 ) ; // 상품명 링크 제거
			orderABCProductSheet.SetCellFontUnderline( row ,"prdtNo" , 0 ) ; // 상품코드 링크 제거
		}

		// 2020.03.25 추가 매장픽업주문의 픽업가능일자가 꽂히면 보여주기
		var pickupPsbltYmd = orderABCProductSheet.GetCellValue(row, "pickupPsbltYmd");
		if ( orderPrdtStatCode == "10006" && !abc.text.allNull(pickupPsbltYmd) ){
			var orderPrdtStatCodeName = orderABCProductSheet.GetCellValue(row, "orderPrdtStatCodeName");
			pickupPsbltYmd = pickupPsbltYmd.substring(0,4) + '.' + pickupPsbltYmd.substring(4,6) + '.' + pickupPsbltYmd.substring(6,8);
			orderPrdtStatCodeName = orderPrdtStatCodeName + "<br>(" + pickupPsbltYmd + "까지 픽업가능)";
			orderABCProductSheet.SetCellValue(row, "orderPrdtStatCodeName", orderPrdtStatCodeName);
		}
		
		if(rsvPrdtYn == abc.consts.BOOLEAN_TRUE){
			orderABCProductSheet.SetCellEditable(row,"optnChange", 0);
		}
		
		if( abc.text.allNull(clmNo) ){
			orderABCProductSheet.SetCellValue(row, "clmGbnCode", "");
		}
		
		orderABCProductSheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}

	// 결제정보 영역
	function orderABCProductSheet_OnButtonClick(row, col) {
		//alert("[" + row + "," + col + "] 셀의 버튼 클릭");
// 		console.log("[" + row + "," + col + "] 셀의 버튼 클릭");
		
		// 옵션 변경
		if( orderABCProductSheet.ColSaveName(col)  == "optnChange"){
			abc.biz.order.info.tab.optionChange( orderABCProductSheet , row , col, 'abc' ) ;
		}
		// 배송중단 처리
		if( orderABCProductSheet.ColSaveName(col)  == "dlvyDscntcBtn"){
			//var  dlvyDscntcRsnCode = orderABCProductSheet.GetCellValue(row, "dlvyDscntcRsnCode");
			abc.biz.order.info.tab.deliveryStop( orderABCProductSheet , row , col , '') ;
		}
		// 재배송 처리
		if( orderABCProductSheet.ColSaveName(col)  == "reDeliveryBtn"){
			abc.biz.order.info.tab.reDelivery( orderABCProductSheet , row , col , '') ;
		}

	}

	<%-- 자사 상품 그리드 Click 이벤트 --%>
	function orderABCProductSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

		if ( Row != 0) {
			if ( orderABCProductSheet.ColSaveName(Col) == "prdtNo" && Value != "" ) {
				var saveName = "prdtNo";
				var param = { prdtNo : orderABCProductSheet.GetCellValue(Row, "prdtNo") };
				abc.readonlyProductDetailPopup(param);
			}

			if ( orderABCProductSheet.ColSaveName(Col) == "prdtName" && Value != "" ) {
				var saveName = "prdtName";
				var siteNo = orderABCProductSheet.GetCellValue(Row, "siteNo");
				var prdtNo = orderABCProductSheet.GetCellValue(Row, "prdtNo");
				var prdtTypeCode = orderABCProductSheet.GetCellValue(Row, "prdtTypeCode");

				if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY ) {
					abc.productFrontUrl(prdtNo);
				}
			}

			if ( orderABCProductSheet.ColSaveName(Col) == "clmGbnCode" && Value != "" ) {
				var saveName = "clmGbnCode";
				
				// 2020.04.10 : 클레임 클릭시 클레임 상세로 바로 넘기도록 수정
				var clmNo = orderABCProductSheet.GetCellValue(Row, "clmNo");
				var clmGbnCode = orderABCProductSheet.GetCellValue(Row, "clmGbnCode");
				
				abc.biz.order.info.tab.openClaimDetailPop(clmNo, clmGbnCode);
			}
		}
	}

	function orderABCProductSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {

		if (Code == "Y"){
			alert("미수령기간갱신되었습니다.");
		}else {
			alert(Msg);
		}
		abc.biz.order.info.tab.ABCProductDoAction("search"); // 조회실행
	}


	<%-- 입점 업체 상품 그리드 Click 이벤트 --%>
	function orderCompanyProductSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

		if ( Row != 0) {
			if ( orderCompanyProductSheet.ColSaveName(Col) == "prdtNo" && Value != "" ) {
				var saveName = "prdtNo";
				var param = { prdtNo : orderCompanyProductSheet.GetCellValue(Row, "prdtNo") };
				abc.readonlyProductDetailPopup(param);
			}

			if ( orderCompanyProductSheet.ColSaveName(Col) == "prdtName" && Value != "" ) {
				var saveName = "prdtName";
				var siteNo = orderCompanyProductSheet.GetCellValue(Row, "siteNo");
				var prdtNo = orderCompanyProductSheet.GetCellValue(Row, "prdtNo");
				var prdtTypeCode = orderCompanyProductSheet.GetCellValue(Row, "prdtTypeCode");

				if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY ) {
					abc.productFrontUrl(prdtNo);
				}

			}

			if ( orderCompanyProductSheet.ColSaveName(Col) == "vndrName" && Value != "" ) {
				var saveName = "vndrName";
				abc.vendorInfoPop( orderCompanyProductSheet.GetCellValue(Row, "vndrNo") );
			}
			if ( orderCompanyProductSheet.ColSaveName(Col) == "sellCnclRsnCode" && Value != "" ) {
				var saveName = "sellCnclRsnCode";

				var dlvyIdText=[];
				var orderPrdtSeq =[];

				var orderNo = orderCompanyProductSheet.GetCellValue(Row, "orderNo");
				var selectDlvyIdText = orderCompanyProductSheet.GetCellValue(Row, "dlvyIdText");
				var selectOrderPrdtSeq = orderCompanyProductSheet.GetCellValue(Row, "orderPrdtSeq");

				dlvyIdText[0] = selectDlvyIdText;
				orderPrdtSeq[0] = selectOrderPrdtSeq;
				var params = {};  //popup창 params
				var url = "/delivery/vendor/delivery-order-vendor/cancel-popup/read";

				console.log ( "orderNo " , orderNo);
				console.log ( "dlvyIdText " , dlvyIdText);
				console.log ( "orderPrdtSeq " , orderPrdtSeq);

				params.orderNo = orderNo;  // params 주문번호
				params.dlvyIdText = dlvyIdText;   // params 배송아이디
				params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
				params.cancelPopupType = "R";  // 팝업 호출 구분 C:등록 R:읽기

				abc.open.popup({
					winname :	"DeliveryCancelPopup",
					url 	:	url,
					method	: 	"post",
					width 	:	1240,
					height	:	880,
					params	:	params

				});
			}
			if ( orderCompanyProductSheet.ColSaveName(Col) == "clmGbnCode" && Value != "" ) {
				var saveName = "clmGbnCode";
				
				// 2020.04.10 : 클레임 클릭시 클레임 상세로 바로 넘기도록 수정
				var clmNo = orderCompanyProductSheet.GetCellValue(Row, "clmNo");
				var clmGbnCode = orderCompanyProductSheet.GetCellValue(Row, "clmGbnCode");
				
				abc.biz.order.info.tab.openClaimDetailPop(clmNo, clmGbnCode);
			}
		}
	}

	// 입점사 상품 상품  영역
	function orderCompanyProductSheet_OnRowSearchEnd(row){

		var prdtNameHtml = "";
		var orderPrdtStatCode 			= orderCompanyProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderPrdtStatCodeName 		= orderCompanyProductSheet.GetCellValue(row, "orderPrdtStatCodeName");
		var deliveryConfirmRgstDtm 		= orderCompanyProductSheet.GetCellValue(row, "buyDcsnDtm"); // 구매확정
		var dlvyDscntcYn 				= orderCompanyProductSheet.GetCellValue(row, "dlvyDscntcYn");
		var prdtName 					= orderCompanyProductSheet.GetCellValue(row, "prdtName");
		var giftPrdtNo 					= orderCompanyProductSheet.GetCellValue(row, "giftPrdtNo");
		var giftPrdtName 				= orderCompanyProductSheet.GetCellValue(row, "giftPrdtName");
		var prdtTypeCode 				= orderCompanyProductSheet.GetCellValue(row, "prdtTypeCode");
		deliveryConfirmRgstDtm 			= new Date(deliveryConfirmRgstDtm).format("yyyy-MM-dd hh:mm:ss");
		var imageUrl 					= orderCompanyProductSheet.GetCellValue(row, "imageUrl");
		var salesCnclGbnType 			= orderCompanyProductSheet.GetCellValue(row, "salesCnclGbnType");
		var clmGbnCode 					= orderCompanyProductSheet.GetCellValue(row, "clmGbnCode");
		var prdtAsAcceptFlag 			= orderCompanyProductSheet.GetCellValue(row, "prdtAsAcceptFlag");
		var brandName 					= orderCompanyProductSheet.GetCellValue(row, "brandName");
		var rsvPrdtYn					= orderCompanyProductSheet.GetCellValue(row, "rsvPrdtYn");
		var clmNo						= orderCompanyProductSheet.GetCellValue(row, "clmNo");
		var prdFullPath = "";
		var memberTypeCode 	= $("#memberTypeCode").val(); // 회원구분코드


		// 교환 매출건 row 색 처리
		if (salesCnclGbnType == abc.order.consts.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE) { // 교환 매출 건
			orderCompanyProductSheet.SetRowBackColor(row,"#E0ECF8");
		}
		// 취소완료건 매출건 row 색 처리
		if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE) { // 상품 취소건
			orderCompanyProductSheet.SetRowBackColor(row,"#F8C0C0");
		}

		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY) {
			if(imageUrl != ""){
				prdFullPath = imageUrl;
			}else{
				prdFullPath = "";
			}
		}else{
			prdFullPath = "";
		}

		orderCompanyProductSheet.SetCellValue(row, "productImg", prdFullPath);

		// 옵션변경  -- 상품준비중 이전  ORDER_PRDT_STAT_CODE 10002 결제 완료 일경우에만 변경 가능
		// 배송비 상품 제외
		if ( prdtTypeCode != abc.order.consts.PRDT_TYPE_CODE_DELIVERY
				&& orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE
				) {
		}else{
			orderCompanyProductSheet.SetMergeCell(row, 10, 1 , 2);
		}

		// 상품명 html ( 사은품명 노출 )
		prdtNameHtml+= "["+brandName+"]<br>";
		prdtNameHtml+= prdtName;
		if (giftPrdtNo != "" ) {
			prdtNameHtml+= "<p> └사은품 :" + giftPrdtName +"</p>"
		}

		orderCompanyProductSheet.SetCellValue(row, "prdtName", prdtNameHtml);

		var linkInfo = "";
		var btnInfo = "";

		if ( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM ) {
			linkInfo = '<u style="display: block;">'+deliveryConfirmRgstDtm+'</u>' ;
			linkInfo = linkInfo + '<b style="display: block; margin-top: 7px;"><u onclick="abc.biz.order.info.tab.productHistoryCompany('+row+');">'+orderPrdtStatCodeName+'</u>';
			// 클레임이 없을 경우에만 as 신청 가능
			if (clmGbnCode == "") {
				if (prdtAsAcceptFlag != "Y" && memberTypeCode != '10002') { //진행중인 as건이 없을 경우
					btnInfo = '<button class="GMCellButtonBase GMCellButton" type="button" style="margin-left: 7px;" onclick=abc.biz.order.info.tab.asAccept('+row+');>A/S신청</button></b>';
				}
			}
			orderCompanyProductSheet.SetCellValue(row, "orderPrdtStatCodeName", linkInfo + btnInfo);
		}else {

			linkInfo = '<b><u onclick="abc.biz.order.info.tab.productHistoryCompany('+row+');">'+orderPrdtStatCodeName+'</u></b>';
			orderCompanyProductSheet.SetCellValue(row, "orderPrdtStatCodeName", linkInfo + btnInfo);
		}

		// 배송비 상품 체크 박스 비활성화 처리
		if ( prdtTypeCode == abc.order.consts.PRDT_TYPE_CODE_DELIVERY ) {
			orderCompanyProductSheet.SetCellEditable(row,"updateCheck", 0);
			orderCompanyProductSheet.SetCellFontUnderline( row ,"prdtName" , 0 ) ;
			orderCompanyProductSheet.SetCellFontUnderline( row ,"prdtNo" , 0 ) ; // 상품코드 링크 제거
		}

		if(rsvPrdtYn == abc.consts.BOOLEAN_TRUE){
			orderCompanyProductSheet.SetCellEditable(row,"optnChange", 0);
		}
		
		if( abc.text.allNull(clmNo) ){
			orderCompanyProductSheet.SetCellValue(row, "clmGbnCode", "");
		}
		
		orderCompanyProductSheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}

	// 입점업체 상품 그리드 버튼 영역
	function orderCompanyProductSheet_OnButtonClick(row, col) {

		if (col == 9) {
			abc.biz.order.info.tab.optionChange( orderCompanyProductSheet , row , col , 'company') ;
		}

	}


	// 할인정보 영역
	function orderDiscountInfoSheet_OnRowSearchEnd(row){

		var discountId 		= orderDiscountInfoSheet.GetCellValue(row, "discountId");
		var discountTxt 	= orderDiscountInfoSheet.GetCellValue(row, "discountTxt");
		var discountStartDt = orderDiscountInfoSheet.GetCellValue(row, "discountStartDt");
		var discountEndDt 	= orderDiscountInfoSheet.GetCellValue(row, "discountEndDt");
		var discountGbn 	= orderDiscountInfoSheet.GetCellValue(row, "discountGbn");

		//discountStartDt = abc.date.formatDate(discountStartDt);
		//discountEndDt = abc.date.formatDate(discountEndDt);

		var txt = "";

		if (discountGbn == abc.order.consts.DISCOUNT_GBN_COUPON ){
			discountGbn = "쿠폰";
			txt = "유효기간 :";
		}else if (discountGbn == abc.order.consts.DISCOUNT_GBN_PROMOTION ){
			discountGbn = "프로모션";
			txt = "기간 :";
		}

		var charHtml = "";

		charHtml += "<p>&nbsp;["+discountId+"] " +discountTxt+"</p>";
		charHtml += "<p>&nbsp;&nbsp;&nbsp;"+ txt +"&nbsp;&nbsp;" +discountStartDt+"&nbsp;&nbsp;-&nbsp;&nbsp;" +discountEndDt+"</p>";
		orderDiscountInfoSheet.SetCellValue(row, "discountGbn", discountGbn);
		orderDiscountInfoSheet.SetCellValue(row, "discountTxt", charHtml);

		orderDiscountInfoSheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}

	// 결제정보 영역
	function orderPaymentInfoSheet_OnRowSearchEnd(row){
		var pymntMeansCode 		= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCode");
		var pymntMeansCodeName 	= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCodeName");
		var instmtTermCount 	= orderPaymentInfoSheet.GetCellValue(row, "instmtTermCount"); // 할부 개월수
		var pymntOrganName 		= orderPaymentInfoSheet.GetCellValue(row, "pymntOrganName"); // 결제기관명
		var pymntCardInfo		 = orderPaymentInfoSheet.GetCellValue(row, "cardType"); // 결제기관명
		var cardType;
		if(pymntCardInfo == ''){
			cardType = "";
		}else if(pymntCardInfo == 'N'){
			cardType = "개인";
		}else{
			cardType = "법인";
		}
		//var pymntBankAccountInfo = "10203040506070"; // 계좌번호 ( 확인 필요 )

		var pymntStatCode 		= orderPaymentInfoSheet.GetCellValue(row, "pymntStatCode"); // 결제 상태
		var pymntDtm 			= orderPaymentInfoSheet.GetCellValue(row, "pymntDtm"); 		// 결제 승인날짜
		var mainPymntMeansYn 	= orderPaymentInfoSheet.GetCellValue(row, "mainPymntMeansYn"); // 주결제 여부
		var cashRcptIssueYn 	= orderPaymentInfoSheet.GetCellValue(row, "cashRcptIssueYn"); // 현금영수증 여부
		var pymntMeansCnt 		= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCnt");
		var changeCnt 			= orderPaymentInfoSheet.GetCellValue(row, "changeCnt");
		var vendorPrdtCnt 		= orderPaymentInfoSheet.GetCellValue(row, "vendorPrdtCnt");
		var claimCnt 			= orderPaymentInfoSheet.GetCellValue(row, "claimCnt");
		var pymntMeansChngPsbltYn 	= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansChngPsbltYn");
		var pymntMeansChngDtm 	= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansChngDtm");
		var rcptDealNoText 		= orderPaymentInfoSheet.GetCellValue(row, "rcptDealNoText"); // 기프트카드 현금 영수증 번호
		var pymntOrganNoText 		= orderPaymentInfoSheet.GetCellValue(row, "pymntOrganNoText"); //

		var orderStatCode			= $("#orderStatCode").val();

		/* 결제 정보 pymntInfo
			카드 		: [개인/법인] 카드사명 - 개월수
			무통장 	: 은행명 발급 계좌 번호
			실시간	: 은행명 계좌 번호
			네이버페이 ,  카카오페이  , 모바일상품권 , 포인트
			결제내역 조회 버튼 -- 네이버 페이의 경우에만 pymntMeansCode : 10004
		*/

		var pymntMeansChng = "";
		if( pymntMeansChngPsbltYn == "Y" && pymntMeansChngDtm != ""  ){
			pymntMeansChng = "[결제수단변경]";
// 			console.log("pymntMeansChng ??? " + pymntMeansChng);
		}

		var payTxtInfo = ""; // 결제내역조회  text
		var payBtnInfo = ""; // 결제내역조회 버튼
		if ( pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_CARD}') { // 카드
			if(instmtTermCount == '0'){
				payTxtInfo = "["+cardType+"] "+ pymntOrganName;
			}else{
				payTxtInfo = "["+cardType+"] "+ pymntOrganName + " - " + instmtTermCount + "개월" ;
			}
		}else if (pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}'
				|| pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}'
					|| pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT}') { // 무통장 , 실시간 , 기프트카드
			// 계좌번호 안들어옴.
			payTxtInfo = pymntOrganName;
			if (pymntOrganNoText != "") {
				payTxtInfo += "<p>&nbsp;" +pymntOrganNoText+"</p>";
			}
		}else { // 그외
			if ( pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_NAVER_PAY}'  || pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_KAKAO_PAY}' ){ // 네이버 , 카카오페이
				payBtnInfo = '<br/><button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.paymentHistory('+row+');>결제내역조회</button>';
			}
			payTxtInfo = pymntMeansCodeName+payBtnInfo ;
		}
		//payBtnInfo = '<br/><button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.paymentHistory('+row+');>결제내역조회</button>';
		//payTxtInfo = payTxtInfo+payBtnInfo;
		orderPaymentInfoSheet.SetCellValue(row, "pymntInfo", payTxtInfo);

		/*
			신용카드 10000  , 계좌이체 10002 ( 다른 결제 방식 카카오 , 네이버 , 모바일 의 경우 ) PYMNT_MEANS_CODE
			주결제수단만 제어 여부 확인 필요 (개발 이후 ) mainPymntMeansYn
			결제 상태값 제어 확인 pymntStatCode
			결제수단이 신용/실시간 , ABC상품 , 클레임 접수건수 0 , 변경이 3건 미만  , 결제수단변경이 N 일때만 노촐
		*/
		var BtnInfo = ""; // 결제수단변경 버튼
		pymntDtm = new Date(pymntDtm).format("yyyy-MM-dd hh:mm:ss");
		if(pymntMeansCnt == 0 && vendorPrdtCnt == 0 && claimCnt == 0 &&  changeCnt <= 2 &&  pymntMeansChngPsbltYn == 'N'){
			if ((pymntMeansCode == '${CommonCode.PYMNT_MEANS_CODE_CARD}' ||  pymntMeansCode == '${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}') && orderStatCode == '${CommonCode.ORDER_STAT_CODE_COMPLETE}') {
				BtnInfo = '<br/><button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.paymentChage('+row+');>결제수단변경</button>';
				orderPaymentInfoSheet.SetCellValue(row, "pymntDtm", pymntDtm + BtnInfo);
			}else {
				orderPaymentInfoSheet.SetCellValue(row, "pymntDtm", pymntDtm);
			}
		}else{
			orderPaymentInfoSheet.SetCellValue(row, "pymntDtm", pymntDtm);
		}
		var cashInfo = "";
		// 현금영수증 발급 조회 버튼
// 		console.log("pymntMeansCode " , pymntMeansCode ) ;
// 		console.log("cashRcptIssueYn " , cashRcptIssueYn ) ;
		if (pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}' || pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}'){
			if (cashRcptIssueYn == 'Y') {
				cashInfo = '<button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.cashReceipte('+row+');>조회</button>';
			}else {
				cashInfo = "미발급";
			}
		}else if (pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_NAVER_PAY}'){ // 네이버 페이 현금영수증 발급여부와 상관없이 노출 처리
			cashInfo = '<button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.cashReceipte('+row+');>조회</button>';
		}else if (pymntMeansCode ==  '${CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT}'){ // 기프트카드
			if (rcptDealNoText != "") {
				cashInfo = '<button class="GMCellButtonBase GMCellButton" type="button" style="margin-top: 7px;" onclick=abc.biz.order.info.tab.cashReceipte('+row+');>조회</button>';
			}
		}else{
			cashInfo = "-";
		}
		orderPaymentInfoSheet.SetCellValue(row, "cashRcptIssueYn", cashInfo);

		orderDiscountInfoSheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}

	// 결제정보 영역
	function orderPaymentInfoSheet_OnButtonClick(Row, Col) {
		//alert("[" + Row + "," + Col + "] 셀의 버튼 클릭");
// 		console.log("[" + Row + "," + Col + "] 셀의 버튼 클릭");
	}


	function rtnOptionChange (args){
		abc.biz.order.info.tab.optionChangeSave(args);
	}

	//편의점찾기 return;
	setCVSResult = function(data){
		abc.biz.order.info.tab.setCvsInfo(data);
	}


</script>
<div>
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">
				<c:choose>
					<c:when test="${logisCnvrtYCnt > 0 }">
						[택배전환]
					</c:when>
					<c:otherwise>
						[<c:out value="${orderDtail.dlvyTypeCodeName}"/>]
					</c:otherwise>
				</c:choose>
				 주문상품
			</h3>
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

	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<span class="opt-group">
				<span class="title">자사 상품</span>
				<label class="opt-desc" for="makeClaimSelect">선택한 상품</label>
				<select class="ui-sel" id="makeClaimSelect"  name="makeClaimSelect" title="상품 공급 유형 선택">
					<option value="cancel">주문취소 접수</option>
					<option value="return">반품접수</option>
					<option value="exchange">교환접수</option>
					<option value="lose">분실처리</option><!-- 190208 수정 : 옵션값 '분실임의처리'로 수정 -->
					<option value="dlvyChange">택배전환</option>
					<c:if test="${orderDtail.dlvyTypeCode ==  CommonCode.DLVY_TYPE_CODE_STORE_PICKUP}">
						<option value="dlvyDate">미수령기간갱신</option>
					</c:if>
					<!-- 2020.05.07 : 비회원 주문만 노출되는 AS반품접수 -->
					<c:if test="${orderDtail.memberNo == Const.NON_MEMBER_NO}">
						<option value="asReturn">AS반품접수</option>
					</c:if>
				</select>
				<button type="button" class="btn-sm btn-func" id="makeClaim" >일괄적용</button>
			<u onclick="" ></u>
			</span>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : ibsheet-wrap -->
	<form id="ABCProductGrid" name="ABCProductGrid" method="post" onsubmit="return false;">
	<div class="ibsheet-wrap">
		<div id="orderABCProductGrid" style="width:100%; height:429px;">
		</div>
	</div>
	</form>
	<!-- E : ibsheet-wrap -->

	<!-- S : tbl-controller -->
	<div class="tbl-controller" style="margin-top: 20px">
		<div class="fl">
			<span class="opt-group">
				<span class="title">입점 상품</span>
				<label class="opt-desc" for="makeClaimSelectForCompany">선택한 상품</label>
				<select class="ui-sel" id="makeClaimSelectForCompany"  title="상품 공급 유형 선택">
					<option value="cancel">주문취소 접수</option>
					<option value="return">반품접수</option>
					<option value="exchange">교환접수</option>
					<!--<option value="lose">분실처리</option>--><!-- 190208 수정 : 옵션값 '분실임의처리'로 수정 , 20190830 입점 업체 상품 분실처리 제외 -->
				</select>
				<button type="button" class="btn-sm btn-func" id="makeClaimForCompany" >일괄적용</button>
			</span>
		</div>
	</div>
	<!-- E : tbl-controller -->

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
				<div class="fr">
					<span class="ui-chk">
						<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
						<input id="buyerSameCheck" name="buyerSameCheck" type="checkbox">
						<label for="buyerSameCheck">주문자 정보와 동일</label>
					</span>
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
							<input type="text" class="ui-input" title="수령자명 입력" id="rcvrName" name="rcvrName" value="${orderDtail.rcvrName}" >
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="휴대폰번호 입력" id="rcvrHdphnNoText" name="rcvrHdphnNoText" value="${orderDtail.rcvrHdphnNoText}" >
							<input type="hidden"id="rcvrTelNoText" name="rcvrTelNoText" value="${orderDtail.rcvrTelNoText}" >
						</td>
					</tr>
					<c:choose>
						<c:when test="${orderDtail.dlvyTypeCode == CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS}"> <!-- 일반택배 -->
							<tr>
								<th scope="row">
									<span class="th-required">주소</span>
								</th>
								<td class="input">
									<span class="address-box">
										<span class="zip-code-wrap">
											<input type="text" class="ui-input" title="우편번호 입력" id="rcvrPostCodeText" name="rcvrPostCodeText" value="${orderDtail.rcvrPostCodeText}">
											<button type="button" class="btn-sm btn-link" id="postSearchBtn">우편번호 찾기</button>
										</span>
										<span class="address-wrap">

											<input type="text" class="ui-input" id="rcvrPostAddrText" name="rcvrPostAddrText" placeholder="시/군/구 동" title="시/군/구 동 입력" value="${orderDtail.rcvrPostAddrText}" readonly>
											<input type="text" class="ui-input" id="rcvrDtlAddrText" name="rcvrDtlAddrText" placeholder="상세주소" title="상세주소 입력" value="${orderDtail.rcvrDtlAddrText}" >
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
											<textarea class="ui-textarea" id="dlvyMemoTextArea" placeholder="배송 메시지는 40자 이내로 입력해 주세요." title="배송 메세지 입력"  >${orderDtail.dlvyMemoText}</textarea>
											<span class="text-limit">
												<span class="desc">(<span id="dlvyMemoCounter">0</span>/40 자)</span>
											</span>
										</span>
									</span>
									<!-- E : msg-wrap -->
								</td>
							</tr>
						</c:when>
						<c:when test="${orderDtail.dlvyTypeCode == CommonCode.DLVY_TYPE_CODE_CONVENIENCE_PICKUP}"> <!-- 편의점 픽업	 -->
							<!-- S : 배송유형 > 편의점픽업인 경우 노출되는 영역 -->
							<tr>
								<th scope="row">수령지점</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<c:forEach var="cvnstrCodeInfo" items="${codeList.CVNSTR_CODE}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="cvnstrCode" id="cvnstrCode${status.count}" value="${cvnstrCodeInfo.codeDtlNo}" <c:if test="${cvsInfo.cvnstrCode eq cvnstrCodeInfo.codeDtlNo}"> checked</c:if>>
													<label for="cvnstrCode${status.count}"><c:out value="${cvnstrCodeInfo.codeDtlName}"/></label>
												</span>
											</li>
										</c:forEach>
										<li>
											<input type="text" class="ui-input" id="cvsName" name="cvsName" title="편의점명 입력" value="${cvsInfo.cvnstrName}" disabled="disabled">
											<a href="javascript:void(0);" class="btn-sm btn-link" id="cvsSearchBtn">편의점 찾기</a>
										</li>
									</ul>
									<!-- E : ip-box-list -->
									<!-- S : td-text-list -->
									<ul class="td-text-list">
										<li> <span style="text" id="cvsAddrInfo"> (${cvsInfo.postCodeText}) ${cvsInfo.postAddrText} ${cvsInfo.dtlAddrText} </span> </li>
									</ul>
									<input type="hidden" name="cvnstrCode" id="cvnstrCode" value="${cvsInfo.cvnstrCode}">		<!-- ho_code: "01" gs25 ,  ho_code: "02" cu ,  codePany: "GS25"   -->
									<input type="hidden" name="cvnstrNoText" id="cvnstrNoText" value="${cvsInfo.cvnstrNoText}">		<!-- store_code: "0O882" -->
									<input type="hidden" name="cvnstrName" id="cvnstrName" value="${cvsInfo.cvnstrName}">		<!--  codeName: "GS25강남본점"-->
									<input type="hidden" name="telNoText" id="telNoText" value="${cvsInfo.telNoText}">			<!--  codeTel: "025553047"-->
									<input type="hidden" name="postCodeText" id="postCodeText" value="${cvsInfo.postCodeText}"><!-- post_no: "135080"  -->
									<input type="hidden" name="postAddrText" id="postAddrText" value="${cvsInfo.postAddrText}"><!-- store_address1: "서울 강남구 역삼동" -->
									<input type="hidden" name="dtlAddrText" id="dtlAddrText" value="${cvsInfo.dtlAddrText}"><!-- store_address2: "825-1번지" -->
									<input type="hidden" name="arvlStoreCodeText" id="arvlStoreCodeText" value="${cvsInfo.arvlStoreCodeText}"><!-- code_f: "1J13-0" -->
									<input type="hidden" name="arvlStoreBrcdText" id="arvlStoreBrcdText" value="${cvsInfo.arvlStoreBrcdText}"><!--  dd_zone: "1J13"-->
									<input type="hidden" name="dongNameCodeText" id="dongNameCodeText" value="${cvsInfo.dongNameCodeText}"><!-- dd_bag: "1J1" -->
									<input type="hidden" name="arvlDongName" id="arvlDongName" value="${cvsInfo.arvlDongName}"><!-- eupmyeon: "역삼동" -->
									<input type="hidden" name="dlvyPlaceYn" id="dlvyPlaceYn" value="Y">
									<!-- E : td-text-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">지점정보</th>
								<td>연락처 : <span style="text" id="cvsTelInfo">${cvsInfo.telNoText}</span></td>
							</tr>
							<!-- E : 배송유형 > 편의점픽업인 경우 노출되는 영역 -->
						</c:when>
						<c:when test="${orderDtail.dlvyTypeCode ==  CommonCode.DLVY_TYPE_CODE_STORE_PICKUP}"> <!-- 매장픽업	 -->
							<!-- S : 배송유형 > 매장픽업인 경우 노출되는 영역 -->
							<tr>
								<th scope="row">수령지점</th>
								<td class="input">
								<input type="hidden" name="storeNo" id="storeNo" value="${orderDtail.storeNo}">
								<input type="hidden" name="storeName" id="storeName" value="${orderDtail.storeName}">
								<input type="hidden" name="storeAddInfo" id="storeAddInfo" value="${orderDtail.storeAddInfo}">
									<a href="javascript:void(0);" class="btn-sm btn-link"  id="abcmartSearchBtn">매장찾기</a>
									<!-- S : td-text-list -->
									<ul class="td-text-list">
										<li><span class="tc-bold" id="storeNameView">${storeInfo.storeName} </span> <span class="tc-bold" id="storeAddr"> ${storeInfo.postAddrText} ${storeInfo.dtlAddrText}</span>   </li>
									</ul>
									<!-- E : td-text-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">지점정보</th>
								<td>영업시간<span class="tc-bold" id="storeBizTime"> ${storeInfo.businessStartTime} ~ ${storeInfo.businessEndTime}  </span> / 전화 <span class="tc-bold" id="storeTel"> ${storeInfo.telNoText}</span></td>
							</tr>
							<c:if test="${logisCnvrtYCnt > 0 }">
								<tr>
									<th scope="row">택배전환</th>
									<td class="input">
										<a href="javascript:void(0);" id="pickupDlvyChangeInfo" class="btn-sm btn-link">보기</a>
									</td>
								</tr>
							</c:if>
							<!-- E : 배송유형 > 매장픽업인 경우 노출되는 영역 -->
						</c:when>
					</c:choose>

				</tbody>
			</table>
			<!-- E : tbl-row -->
			<!-- S : 20190213 수정 // 확인 버튼 추가 -->
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-func" id="rcvrInfoUdpate" >확인</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 20190213 수정 // 확인 버튼 추가 -->
		</div>
	</div>
	<!-- E : col-wrap -->

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">결제 및 할인정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : col-wrap -->
	<div class="col-wrap">
		<div class="col" style="min-width: 45%;">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="title">총 결제금액</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : tbl-row -->
			<table class="tbl-col">
				<caption>총 결제금액</caption>
				<colgroup>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" colspan="2">구분</th>
						<th scope="col">합계</th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<th scope="row" colspan="2">총 정상가</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalNormalAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">①총 판매가</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalSellAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="rowgroup" rowspan="3">총 할인금액</th>
						<th scope="row">프로모션 할인</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalPromoDscntAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row">쿠폰할인</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalCpnDscntAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row">임직원 할인금액</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalEmpDscntAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr class="row-sum">
						<th scope="row" colspan="2">②소계</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalPromoDscntAmt + orderDtail.totalCpnDscntAmt + orderDtail.totalEmpDscntAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="rowgroup" rowspan="2">포인트</th>
						<th scope="row">포인트</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.pointUseAmt - orderDtail.eventPointUseAmt}" pattern="#,###.##" type="number" /> Point</td>
					</tr>
					<tr>
						<th scope="row">이벤트포인트</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.eventPointUseAmt}" pattern="#,###.##" type="number" /> Point</td>
					</tr>
					<tr class="row-sum">
						<th scope="row" colspan="2">③소계</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.pointUseAmt}" pattern="#,###.##" type="number" /> Point</td>
					</tr>
					<tr>
						<th scope="rowgroup" rowspan="2">배송비</th>
						<th scope="row">자사</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.mmnyDlvyAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row">입점사</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.totalVndrDlvyAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr class="row-sum">
						<th scope="row" colspan="2">④소계</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.mmnyDlvyAmt + orderDtail.totalVndrDlvyAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th scope="row" colspan="2">⑤결제금액=(①-②-③)+④</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.pymntAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">⑥총 취소금액</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.cnclAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">총 결제 금액=⑤-⑥</th>
						<td class="text-right"><fmt:formatNumber value="${orderDtail.pymntAmt - orderDtail.cnclAmt}" pattern="#,###.##" type="number" />원</td>
					</tr>
				</tfoot>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 판매가는 정상가에서 즉시할인 또는 타임특가 할인 금액이 적용된 금액 입니다.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->
		</div>
		<div class="col" style="min-width: 45%;">
			<!-- S : row-wrap -->
			<div class="row-wrap">
				<div class="row">
					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="title">할인정보</span>
						</div>
						<div class="fr">
						
							<%-- 
								 2020.05.13 :  CS 고객센터는 이 두아이디만 쿠폰발급 버튼 노출
								  	- csmizie109 / cs_j0202j70
							--%>
							<c:set var="substringNoCpnBtnLoinId" value="${fn:substring(noCpnBtnLoinId,0,2)}" />
							<c:choose>
								<c:when test="${substringNoCpnBtnLoinId eq 'cs'}">
									<c:if test="${noCpnBtnLoinId eq 'csmizie109' or noCpnBtnLoinId eq 'cs_j0202j70'}">
										<a href="javascript:void(0);" class="btn-sm btn-link" id="couponIssue">쿠폰지급</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" class="btn-sm btn-link" id="couponIssue">쿠폰지급</a>
								</c:otherwise>
							</c:choose>
							
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="orderDiscountInfoGrid" style="width:100%; height:300px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<div class="row">
					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="title">결제정보 상세</span>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="orderPaymentInfoGrid" style="width:100%; height:300px;">

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
<script src="/static/common/js/biz/order/abcmart.order.info.tab.js<%=_DP_REV%>"></script>
