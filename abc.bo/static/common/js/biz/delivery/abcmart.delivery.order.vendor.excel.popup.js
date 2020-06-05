(function() {


	var alertExcelUploadConfirm				= "엑셀 업로드 후 저장 처리 하지않은 데잍가 존재합니다. \n\n  다시 업로드시 기존 데이타는  유지 되지 않습니다. \n\n  업로드를 진행 하시겠습니까?";
	var alertConfirmSave = "주문을 배송처리 하시겠습니까?";
	var alertUploadSaveSuccess  = "엑셀 업로드 데이타를 저장 처리 하였습니다."
	var alertUploadSaveFail  = "서버 통신에 실패했습니다."


	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.excel.popup");
																													  
 	_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "300px");
		var pageCount ="";
		var initDataListSheet = {};
	
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 				Type:"Status",				SaveName:"status",				Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"", 						Type:"CheckBox",			SaveName:"checkedRows",			Width: 40,		 Align:"Center"  , Hidden:1}
				, {Header:"순번",						Type:"Seq",					SaveName:"seqNumber",			Width: 80,		Align:"Center"}
				, {Header:"주문번호\n(문자)",			Type:"Text",				SaveName:"orderNo",				Width: 150,		 Align:"Center",	 Edit:0 } 
				, {Header:"주문순번\n(숫자)",			Type:"Text",				SaveName:"orderPrdtSeq",		Width: 80,		 Align:"Center",	Edit:0 }
				, {Header:"업체상품코드\n(문자)",		Type:"Text",				SaveName:"vndrPrdtNoText",		Width: 150,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"상품코드\n(문자)", 			Type:"Text",				SaveName:"prdtNo",				Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"배송ID\n(문자)",			Type:"Text",				SaveName:"dlvyIdText",	  		Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"택배사\n(택배사코드 참조)",		Type:"Combo",				SaveName:"logisVndrCode",		Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"운송장번호\n(문자)",			Type:"Text",				SaveName:"waybilNoText",		Width: 150,		 Align:"Center", 	Edit:0, Format:"Number"}
				, {Header:"upload 결과",				Type:"Text",				SaveName:"uploadResultMsg",		Width: 150,		 Align:"Center", 	Edit:0}
		];

		// Grid 초기화
		IBS_InitSheet(dataListSheet, initDataListSheet);
		
		// Grid 건수 정보 표시
		dataListSheet.SetCountPosition(0);
		// Grid 페이지 네비게이션 버튼 표시
		dataListSheet.SetPagingPosition(0);
		// Grid width 자동 조절
		dataListSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dataListSheet.SetExtendLastCol(1); 

		dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
	}	 
	

	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
		//저장하기 버튼  hidden
		$("#popupSave").hide();
	}; //end init

 
	//양식 다운로드
	  $("#uploadExcelDown").click(function(){
			//location.href ="/static/common/ibsheet/excelUploadSample.xlsx";
		location.href ="/delivery/vendor/delivery-order-vendor/delivery-excel-sample-down";
		 //location.href ="/noacl/defaultContentTypedownload?downLoadFileName=excelUploadSample.xlsx&atchFilePathText=/static/common/ibsheet/";
 
	 }) ;	

	/*
 	 취소
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel

	 $("#popupClose").click(function(){
		 self.close();
	 }) ;	//end #popupCancel
 

   
	 /*
	 엑셀 업로드 데이타 저장 처리
	*/
	 $("#popupLoad").click(function(){
		 	var totalRows = dataListSheet.GetTotalRows();  //업로드 전체 건수
			var callPopupType = $('#callPopupType').val();
			
			//다시 엑셀 업로드 진행시
			if(totalRows >0 &&  callPopupType =="C"){
					if (!confirm(alertExcelUploadConfirm)) {
						return;
					}
			}

          //엑셀 업로드시 매치 유형 선택 (기본값: 헤더 타이틀 기준 매치)
		var params = { Mode : "HeaderMatch",  StartRow: "1" ,  FileExt: "xls|xlsx"} ;
		 dataListSheet.LoadExcel(params); 

	 }) //end #pupupSave

  /*
	 엑셀 업로드 데이타 저장 처리
	*/
	 $("#popupSave").click(function(){
  
		var totalRows = dataListSheet.GetTotalRows();  //업로드 전체 건수
		  for (idx=1; idx<totalRows ; idx++){   
			  var logisVndrCode  = dataListSheet.GetCellValue( idx , "logisVndrCode").trim(); // 택배사
			  var waybilNoText  = dataListSheet.GetCellValue( idx , "waybilNoText").trim(); // 송장번호
			  var dlvyIdText  = dataListSheet.GetCellValue( idx , "dlvyIdText").trim(); // 배송 아이디

				//송장번호 택배사 체크
				if ( abc.text.allNull( waybilNoText) ||  abc.text.allNull( logisVndrCode)  ) {
						var alertNullValue  = "필수 입력값을 선택/입력해주세요.(송장번호/택배사)" ;
						alert( alertNullValue +"\n 순번 : [ "+(idx)+" ]");
						return;
				}

				//송장번호 숫자 체크
				if (isNaN( waybilNoText )) {
						var alertNullValueOnlyNum  = "송장번호는 숫자만 입력 가능 합니다."
						alert( alertNullValueOnlyNum+"\n 순번 : [ "+(idx)+" ]");
						return;
				}

				//송장번호 Length체크
				if (  waybilNoText.length < 10 || waybilNoText.length > 30  ) {
						var alertNullValueLength  = "송장번호는 [숫자만  최소 10자리 이상  30자리 이하]만 가능 합니다." ;
						alert( alertNullValueLength +"\n 순번 : [ "+(idx)+" ]");
						return;
				}
				 
		  }
 
		//조회 	mySheet.DoSearch("multilinedata.json");
		if( !confirm( alertConfirmSave )){
			return;
		} 
		
		//버튼 Hidden
		 $("#popupSave").hide();

		//전송 데이타 셋팅
		var param = dataListSheet.GetSaveString();
	
		 var request   =
			  $.ajax({
				 type :"post",
				url : "/delivery/vendor/delivery-order-vendor/excel-upload-save",
				 data : param
			  })
			  request.done(function(data){
					var resultYn = data.result ;  //결과 값 처리
					var failCnt = data.failCnt ;  //실패건수
					var successCnt = data.successCnt ;  //성공건수
					//성공 처리시
					if( resultYn == "Y" ){
						$("#popupClose").show();
						$("#popupLoad").hide();
						$("#popupCancel").hide();
						$('#failCnt').html(failCnt); //실패건수
						$('#successCnt').html(successCnt); //성공건수

						//그리드 수정 불가 초기화
						dataListSheet.SetColEditable("logisVndrCode",0);    // 택배사 비활성화
						dataListSheet.SetColEditable("waybilNoText",0);   //송장번호 비활성화
						
						//메세지 색상 적용
						dataListSheet.SetColBackColor("uploadResultMsg", "#ffff00"); 

						 alert( alertUploadSaveSuccess );
						 dataListSheet.RemoveAll();  //Grid Reset 
						 dataListSheet.LoadSaveData(data);  //Grid 데이타 적용
					}else{
						  $("#popupSave").show(); //실패시 보여주기
						 alert( alertUploadSaveFail );
					}
			  })
			  request.fail(function(e){
				  $("#popupSave").show(); //실패시 보여주기
				 alert( alertUploadSaveFail );
			  }); 
	 }) //end #pupupSave 

})();

 