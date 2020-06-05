/***
 * 주문관리 > 주문상세 > 재배송처리
 * 
 */
(function() {

	var _reDelivery = abc.object.createNestedObject(window,"abc.biz.re.delivery"); 
	
	$("#stockGbnCode").change(function(){
		var value = $(this).val();
		if (value == '10002') { // 매장 ( 오프라인 매장) 
			$("#storeInfoView").show();
		}else{
			$("#storeInfoView").hide();
		}
	});
	
	$("#searchStore").click(function(){
		// 주문 배송 유형 확인 
		// 매장 픽업시 각 채널 확인 필요 함 
		abc.storeSearchPopup('abc.biz.re.delivery.setStoreInfo' , false);
	});
	
	
	$("#reDeliverySave").click(function(){
		// 배송상태 
		var url = "/delivery/re-delivery-save" ; 
		var dlvyStatCode = $("#dlvyStatCode").val();
		
		var orderNo = $("#orderNo").val();
		var orderPrdtSeq = $("#orderPrdtSeq").val();
		var stockGbnCode = $("#stockGbnCode").val();
		var storeNo = $("#storeNo").val();
		var orderDlvyHistSeq = $("#orderDlvyHistSeq").val();
		var dlvyIdText = $("#dlvyIdText").val();
		var orgStockGbnCode = $("#orgStockGbnCode").val();
		
		//var dlvyDscntcRsnCode = ibsheetObject.GetCellValue(row, "dlvyDscntcRsnCode");
		//var orderPrdtStatCode = ibsheetObject.GetCellValue(row, "orderPrdtStatCode");
		if(stockGbnCode == "" ) {
		  alert("배송처를 선택해주세요");
		  return;
		}
		
		if(!confirm('동일 배송 ID에 속한 모든 상품들이 일괄처리 됩니다. 재배송 처리 하시겠습니까?')){
			return;
		}
		
		/*
		 매장선택은 필요 없음
		if ( stockGbnCode == "10002" ) {
			if ($("#storeNo").val() == "") {
				alert("오프라인 매장일 경우 매장을 선택하셔야 합니다.");
				return; 
			}
		}
		*/
		
		if (dlvyStatCode == "10000" || dlvyStatCode == "10001") { // 결제완료 , 상품준비중 
			
		}else {
			alert("상품준비중일 경우에만 재배송처리가 가능합니다.");
			return; 
		}
		
		console.log ("stockGbnCode" , stockGbnCode);
		var param = {
				  orderNo 	 : orderNo
				, orderPrdtSeq : orderPrdtSeq 
				, stockGbnCode : stockGbnCode  // 변경될 재고구분코드 
				, storeNo : storeNo 
				, orderDlvyHistSeq : orderDlvyHistSeq
				, dlvyIdText : dlvyIdText
				, orgStockGbnCode : orgStockGbnCode
		};
		
		$.ajax({
			type 	: "post"
			,data 	: param
			,url	: url
		})
		.done(function(data){
			if ( data.result == "Y"){
				alert("재배송 처리 되었습니다.");
				opener.abc.biz.order.info.tab.ABCProductDoAction("search"); // 조회실행
				window.close();
			}
		})
		.fail(function(e){
			console.log("e :" + e);
		});
		
	});
	
	// 매장 선택 return 
	_reDelivery.setStoreInfo = function(data){
		
		if ( data.length > 1  ) {
			alert("1개이상의 매장이 선택되었습니다.");
			return;
		}
		
		$("#storeNo").val( data[0].storeNo ); // 매장 번호  
		$("#storeName").val(data[0].storeName); // 매장 명 
		
	}
	
})();	