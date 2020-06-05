(function() {

 

	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.logis.popup");
																													  
 	_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		var pageCount ="";
		var initDataListSheet = {};
	
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 		Type:"Status",				SaveName:"status",					Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"", 				Type:"CheckBox",			SaveName:"checkedRows",						Width: 40,		 Align:"Center"  , Hidden:1}
				, {Header:"택배사코드",			Type:"Text",				SaveName:"codeDtlNo",				Width: 150,		 Align:"Center",	 Edit:0 } 
				, {Header:"택배사명",			Type:"Text",				SaveName:"codeDtlName",				Width: 45,		 Align:"Center",	Edit:0 }
		];

		// Grid 초기화
		IBS_InitSheet(dataListSheet, initDataListSheet);
		
		// Grid 건수 정보 표시
		dataListSheet.SetCountPosition(0);
		// Grid 페이지 네비게이션 버튼 표시
		dataListSheet.SetPagingPosition(0);
		// Grid width 자동 조절
		//dataListSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dataListSheet.SetExtendLastCol(1); 

		//dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
	}	 
	

	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
				_deliveryJsObject.doActionJs("search");
		}; //end init


 
	 $("#popupClose").click(function(){
		 self.close();
	 }) ;	//end #popupCancel
   

/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/vendor/delivery-order-vendor/logisPopup/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;
	 		} //end switch

		} //end fucntion

})();

 