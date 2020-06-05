/***
 * 주문관리 > 배송관리 >  미출고 현황 
 * 
 */
(function() {

   // define Alert Msg
	var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
	var alertCheckBoxFail ="최소한 한개 이상의 검색 조건은 체크를 해야 검색이 가능 합니다.";

	// param 정보 선언
	var param = abc.param.getParams();
	var vendorYn = "";	// 입점업체 구분 전역 변수
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.not.main");

	_deliveryJsObject.SITE_NO_ART = "";
	_deliveryJsObject.SITE_NO_OTS = "";
	
	_deliveryJsObject.ABC_FO_URL = "";
	_deliveryJsObject.OTS_FO_URL = "";
	
	_deliveryJsObject.NON_MEMBER_NO = "";
	
	_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();

		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 		Type:"Status",	SaveName:"status",				Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"사이트", 			Type:"Combo",	SaveName:"siteNo",				Width: 100,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "" }
				, {Header:"사이트번호", 		Type:"Text",	SaveName:"orgSiteNo",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1}
				, {Header:"주문일시", 			Type:"Text",	SaveName:"orderDtm",			Width: 150,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"주문번호",			Type:"Text",	SaveName:"orderNo",				Width: 120,		 Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"주문순번",			Type:"Text",	SaveName:"orderPrdtSeq",		Width: 60,		 Align:"Center",	Edit:0 }
				, {Header:"상품코드", 			Type:"Text",	SaveName:"prdtNo",				Width: 150,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"상품명", 			Type:"Text",	SaveName:"prdtName",			Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"옵션", 			Type:"Text",	SaveName:"optnName",			Width: 80,		 Align:"Center", 	Edit:0}
				, {Header:"결제금액", 			Type:"Int",		SaveName:"orderAmt",			Width: 80,		 Align:"Right", 	Edit:0, Format:"#,##0 원"}
				, {Header:"배송유형", 			Type:"Combo",	SaveName:"dlvyTypeCode",		Width: 80,		 Align:"Center", 	Edit:0}
				, {Header:"주문배송상태", 		Type:"Combo",	SaveName:"dlvyStatCode",		Width: 80,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"배송ID",			Type:"Text",	SaveName:"dlvyIdText",			Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"택배사",			Type:"Combo",	SaveName:"logisVndrCode",		Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"운송장번호",			Type:"Text",	SaveName:"waybilNoText",		Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"발송처",		Type:"Text",	SaveName:"stockGbnCodeName",		Width: 170,	 Align:"Center", Edit:0}
				, {Header:"매장픽업\n택배전환사유",Type:"Combo",	SaveName:"logisCnvrtRsnCode",	Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"주문자",			Type:"Text",	SaveName:"buyerName",			Width: 80,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문자ID",			Type:"Text",	SaveName:"buyerId",				Width: 80,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"주문자번호",			Type:"Text",	SaveName:"memberNo",			Width: 80,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"수령자",			Type:"Text",	SaveName:"rcvrName",			Width: 80,		 Align:"Center", 	Edit:0}
				, {Header:"주소기본",			Type:"Text",	SaveName:"rcvrPostAddrText",	Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"주소상세",			Type:"Text",	SaveName:"rcvrDtlAddrText",		Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
		];
 
		// Grid 초기화
		IBS_InitSheet(dataListSheet, initDataListSheet);
		
		// Grid 건수 정보 표시
		dataListSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		dataListSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//dataListSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dataListSheet.SetExtendLastCol(1);

       dataListSheet.InitDataCombo(0, "dlvyTypeCode",  this.codeCombo.DLVY_TYPE_CODE.text,   this.codeCombo.DLVY_TYPE_CODE.code );		 //배송유형콤보코드
	   dataListSheet.InitDataCombo(0, "dlvyStatCode",  this.codeCombo.DLVY_STAT_CODE.text,   this.codeCombo.DLVY_STAT_CODE.code );		 //주문배송상태
       dataListSheet.InitDataCombo(0, "stockGbnCode",  this.codeCombo.STOCK_GBN_CODE.text,   this.codeCombo.STOCK_GBN_CODE.code );		 //발송처
       dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
       dataListSheet.InitDataCombo(0, "logisCnvrtRsnCode",  this.codeCombo.LOGIS_CNVRT_RSN_CODE.text,   this.codeCombo.LOGIS_CNVRT_RSN_CODE.code );		 //택배전화 사유 코드
	   
	   dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo.text,   this.siteCombo.code );		//사이트 콤보

	   	//입점업체 구분
		vendorYn =  $("#vndrGbnType").val();
		if( vendorYn =="Y" ){
			dataListSheet.SetColHidden(dataListSheet.SaveNameCol('logisCnvrtRsnCode'), 1);
			dataListSheet.SetColHidden(dataListSheet.SaveNameCol('stockGbnCode'), 1);
		} 
	}	 

	_deliveryJsObject.dataListSheetOnSearchEnd = function(){
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = dataListSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;
		
		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}
		
		for(var i=pageFirstRow; i<=dataListSheet.GetDataLastRow(); i++){
			
			var memberNo = dataListSheet.GetRowData(i).memberNo;
			
			if( memberNo != _deliveryJsObject.NON_MEMBER_NO){
				dataListSheet.SetRowData(i, {buyerName : dataListSheet.GetRowData(i).buyerName + "(" + dataListSheet.GetRowData(i).buyerId + ")"});
			}else{
				dataListSheet.SetCellFontUnderline(i,17,0);
			}
		}
	}
	
	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
		var vndrNo = $('input[name=vndrNo]').val();
		var vndrGbnType = $('input[name=vndrGbnType]').val();
		 
 
	}

	// 화면 이벤트 
	_deliveryJsObject.event = function(){
		
		// 초기화
		$("#gFormReset").click(function(){
			$('#gForm')[0].reset();
			_deliveryJsObject.init();
		});
		
		//  검색
		$("#gFormSearch").click(function(){
			//검색 Validate
			if( _deliveryJsObject.searchValidate()){
				_deliveryJsObject.doActionJs("search");
			}
		});

		//페이지선택시
		$("#pageCount").change(function(){
			_deliveryJsObject.doActionJs("search");
		});
 

		//사이트구분 체크 
		$("input[name='siteNoArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='siteNoArr']").prop("checked", this.checked);
			} else {
				$("input[name='siteNoArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=siteNo]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='siteNoArr']:eq(0)").prop("checked", true);
			}

		});

		//업체 찾기 팝업
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			abc.vndrSearchPopup(false, "setSelectedVendors"); 
		});

		// 매장찾기 팝업
		$("#searchStorePopUp").click(function(){ 
    		abc.storeSearchPopup('abc.biz.delivery.order.not.main.setStoreInfo', false);
		}); 

		//브랜드 찾기 팝업
		$("#serchBrandPop").click(function(){
			abc.brandSearchPopup(false, "setSelectedBrand");
		});

		//배송유형 체크 
		$("input[name='dlvyTypeCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='dlvyTypeCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='dlvyTypeCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=dlvyTypeCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='dlvyTypeCodeArr']:eq(0)").prop("checked", true);
			}

		});
		
		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
	}
	
	/**
	 * 2020.05.22 주문 검색어 선택 이벤트
	 */
	_deliveryJsObject.ordMbInfoSelectChange = function(searchOrderKey) {
		
		$("#searchOrderValue").val("");
		$("#buyerHdphnBackNo").val("");
		
		if(searchOrderKey == "buyerName"){
			$("#buyerHdphnBackNo").show();
		}else if(searchOrderKey == "rcvrName"){
			$("#buyerHdphnBackNo").show();
		}else if(searchOrderKey == "orderNo"){
			$("#buyerHdphnBackNo").hide();
		}else if(searchOrderKey == "loginId"){
			$("#buyerHdphnBackNo").hide();
		}
	}
	
	// 매장찾기Set
	_deliveryJsObject.setStoreInfo = function(data){
		  
		if ( data.length > 1  ) {
			alert("1개이상의 매장이 선택되었습니다.");
			return;
		} 
		$("#storeNo").val( data[0].storeNo ); // 매장 번호  
		$("#storeName").val(data[0].storeName); // 매장 명 
		 	
	}
	
	/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/delivery-order-not/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;
		}
	}


   /**
	 * 검색조건 Validate 체크
	 */
	_deliveryJsObject.searchValidate = function(){
 	 		
		/**
		 * 주문자 정보
		 */
	 	 var searchOrderValue = $("#searchOrderValue").val();

		 if (!abc.text.allNull( searchOrderValue)) {
				 switch ($("#searchOrderKey").val()) {
					case "orderNo":
						 if (isNaN( searchOrderValue )) {
								alert( alertOrderNo );
								$("#searchOrderValue").val("");
								return false;
							}
						break;
				}
		} //end  if (!abc.text.allNull( searchOrderValue)) {
		 
		 //검색 체크박스 체크
		var checkBoxLength = $("input[type=checkbox]:checked").length;
		if( checkBoxLength < 1 ){
			alert( alertCheckBoxFail );
			return false;
		}

		return true;

	} //end _deliveryJsObject.searchValidate

	/**
	 * 한 페이지의 전체 클레임 목록 엑설로 다운로드
	 */
	_deliveryJsObject.downloadAllExcel = function() {
		
		gForm.action = "/delivery/delivery-order-not/download-all-excel";
		gForm.submit();
	}
	
	 //그리드 Click 이벤트 
	dataListSheet_OnClick = function(Row, Col, Value) {
		var vndrNo 		= $('input[name=vndrNo]').val();
		var vndrGbnType = $('input[name=vndrGbnType]').val();
		/* 
		/입점사 로그인인경우
		if( vndrGbnType =="Y"){
			return;
		}
		*/
		
		if ( Row != 0) {
			//파라미터값셋팅
			var params = {};
				params.orderStatCode = dataListSheet.GetCellValue(Row, "orderStatCode");

			//주문상세
			if ( dataListSheet.ColSaveName(Col) == "orderNo") {
				if( vendorYn =="Y" ){
					abc.orderVendorDetailPopup(dataListSheet,"orderNo",Row,Col,"");
				}else{
					abc.orderDetailPopup(dataListSheet,"orderNo",Row,Col,"");
				}
			}
			
			//상품코드
			if( dataListSheet.ColSaveName(Col) == "prdtNo" ) {
				var prdtNo = dataListSheet.GetCellValue(Row, "prdtNo");
				var param  = { prdtNo : prdtNo };
				abc.readonlyProductDetailPopup(param);
			}
			
			//상품명
			if ( dataListSheet.ColSaveName(Col) == "prdtName"   ) {
				var prdtNo = dataListSheet.GetRowData(Row).prdtNo;
				var siteNo = dataListSheet.GetRowData(Row).orgSiteNo;
				
				if(siteNo == abc.biz.delivery.order.not.main.SITE_NO_ART){
					window.open(abc.biz.delivery.order.not.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.delivery.order.not.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
			}
			
			//주문배송상태
			if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
				var orderNo      =  dataListSheet.GetCellValue(Row, "orderNo");
				var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			 
				abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
			}
			
			//주문자
			if ( dataListSheet.ColSaveName(Col) == "buyerName") {
				var memberNo =  dataListSheet.GetCellValue(Row, "memberNo");
				if( memberNo != abc.biz.delivery.order.not.main.NON_MEMBER_NO){
					abc.memberDetailPopup(memberNo);
				}
			}
		}
	} // end 그리드 Click 이벤트
	
})();	 //end  (function() {


 //end function 그리드 Click 이벤트 
