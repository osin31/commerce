/***
 * 회원관리 상세 회원정보 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								회원 주문정보 탭
	 *************************************************************************/
	var _memberOrder = abc.object.createNestedObject(window,"abc.biz.member.member.order");

//	$("#dlvyTypeCode").change(function(){
//		var value = $(this).val();
//		if (value == '10002' || value == '99999') {
//			abc.biz.member.member.order.getChannelList(value);
//			$("#chnnlNo").show();
//		}else{
//			$("#chnnlNo").hide();
//		}
//	});
	
	/** 주문취소여부 변경 함수 */ 
	$("#chkCancelAll").click(function(){
		if($(this).is(":checked")){
			$("input[name=chkCancel]").prop("checked",true);
		} else {
			$("input[name=chkCancel]").prop("checked",false);
		}
	});
	
	$("input[name=chkCancel]").each(function(){
		$(this).click(function(){
			if($(this).is(":checked")){
				var unChecked = $("input[name=chkCancel]:not(:checked)");
				if (unChecked.length == 0) {
					$("#chkCancelAll").prop("checked",true);					
				}
			} else {
				$("#chkCancelAll").prop("checked",false);
			}
		})
	});
	
	/**
	 * 주문목록 그리드 초기 세팅
	 */
	_memberOrder.initOrderSheet = function() {
		
		if(typeof onlineSheet != 'undefined'){
			onlineSheet.Reset();
			onlineSheet.DisposeSheet(1);
		}
		
		if(typeof offlineSheet != 'undefined'){
			offlineSheet.Reset();
			offlineSheet.DisposeSheet(1);
		}
		//
		createIBSheet2(document.getElementById("onlineGrid"), "onlineSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		//var pageCount = $('#pageCount').val();
		var pageCount = "15";
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 , MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 			Type:"Status",		SaveName:"status",			Width: 0,  Align:"", 		Edit:0, Hidden:1, Sort:0}
			, {Header:"번호|번호", 			Type:"Seq",			SaveName:"",				Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"임직원여부|임직원여부", 	Type:"Text",		SaveName:"empYn",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사이트|사이트", 			Type:"Combo",		SaveName:"siteNo",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문유형|주문유형", 		Type:"Combo",		SaveName:"rsvOrderYn",		Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문번호|주문번호", 		Type:"Text",		SaveName:"orderNo",			Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"상품명|상품명", 			Type:"Text",		SaveName:"orderPrdtInfo",	Width: 25, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문일시|주문일시", 		Type:"Date",		SaveName:"orderDtm",		Width: 25, Align:"Center", 	Edit:0, Sort:0 , Format:"YmdHms"}
			, {Header:"주문금액합계|주문금액합계", 	Type:"Float",		SaveName:"pymntAmt",		Width: 20, Align:"Right", 	Edit:0, Sort:0, Format: "#,##0원"}
			, {Header:"입금(결제)완료일|입금(결제)완료일", Type:"Text",	SaveName:"pymntDtm",		Width: 20, Align:"Center", 	Edit:0, Sort:0, Format:"YmdHms"}
			, {Header:"결제상태|결제상태", 		Type:"Combo",		SaveName:"orderStatCode",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문취소여부|주문취소여부", 	Type:"Combo",		SaveName:"orderCancelFlag",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문배송유형|주문배송유형", 	Type:"Combo",		SaveName:"dlvyTypeCode",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"유선상담|유선상담", 		Type:"Button",		SaveName:"inquiryCnt",		Width: 20, Align:"Center", 	Edit:1, Sort:0, DefaultValue:"유선접수"}
			, {Header:"클레임|교환", 			Type:"Text",		SaveName:"claimExchangeCnt",Width: 10, Align:"Right", 	Edit:0, Sort:0}
			, {Header:"클레임|반품", 			Type:"Text",		SaveName:"claimReturnCnt",	Width: 10, Align:"Right", 	Edit:0, Sort:0}
			, {Header:"클레임|A/S", 			Type:"Text",		SaveName:"claimASCnt",		Width: 10, Align:"Right", 	Edit:0, Sort:0}
			, {Header:"총주문결제건수|총주문결제건수", Type:"Text",		SaveName:"orderCnt",		Width: 10, Align:"Right", 	Edit:0, Hidden:1}
			, {Header:"총주문결제금액|총주문결제금액", Type:"Text",		SaveName:"orderAmt",		Width: 10, Align:"Right", 	Edit:0, Hidden:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(onlineSheet , initSheet);
		// Grid 건수 정보 표시
		onlineSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		onlineSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		onlineSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		onlineSheet.SetExtendLastCol(1);
		
		onlineSheet.InitDataCombo(0 , "siteNo" , this.siteCombo.text , this.siteCombo.code  );
		onlineSheet.InitDataCombo(0 , "rsvOrderYn" , "예약|일반" , "Y|N"  ); 
		onlineSheet.InitDataCombo(0 , "orderStatCode" , this.codeCombo.ORDER_STAT_CODE.text , this.codeCombo.ORDER_STAT_CODE.code  );
		onlineSheet.InitDataCombo(0 , "dlvyTypeCode" , this.codeCombo.DLVY_TYPE_CODE.text , this.codeCombo.DLVY_TYPE_CODE.code  );
		onlineSheet.InitDataCombo(0 , "orderCancelFlag" , "전체|부분|-" , "total|part|"   );
		
		createIBSheet2(document.getElementById("offlineGrid"), "offlineSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",		Width: 0,  Align:"", 		Edit:0, Hidden:1, Sort:0}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",			Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"매장명", 			Type:"Text",		SaveName:"storeName",	Width: 30, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"거래번호", 			Type:"Text",		SaveName:"dealNo",		Width: 30, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"상품명", 			Type:"Text",		SaveName:"prdtName",	Width: 30, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"거래일시", 			Type:"Text",		SaveName:"saleDate",	Width: 25, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"총결제금액", 		Type:"Float",		SaveName:"salePrice",	Width: 25, Align:"Right", 	Edit:0, Sort:0, Format: "#,##0원"}
			, {Header:"주문상태", 			Type:"Combo",		SaveName:"returnFlag",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"총주문결제건수", 		Type:"Text",		SaveName:"orderCnt",	Width: 10, Align:"Right", 	Edit:0, Sort:0, Hidden:1 }
			, {Header:"총주문결제금액", 		Type:"Text",		SaveName:"orderAmt",	Width: 10, Align:"Right", 	Edit:0, Sort:0, Hidden:1 }
		];
		
		// Grid 초기화
		IBS_InitSheet(offlineSheet , initSheet);
		// Grid 건수 정보 표시
		offlineSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		offlineSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		offlineSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		offlineSheet.SetExtendLastCol(1);
		
		offlineSheet.InitDataCombo(0 , "returnFlag" , "주문|취소" , "0|1"  ); 
	}
	
	/**
	 * 주문화면 초기 세팅
	 */
	_memberOrder.orderViewinit = function() {
		$("#offlineGrid").hide();
	}
	
	/**
	 * 구분 변경 시 그리드 Show/Hide
	 */
	_memberOrder.orderChange = function(disivionType) {
		if(disivionType == "online"){
			$("#onlineGrid").show();
			$("#offlineGrid").hide();
			// 사이트 
			$("input[name=siteNo]").removeAttr("disabled");
			// 주문배송유형
			$("select[name=dlvyTypeCode]").prop("disabled",false);
			// 임직원 여부 활성화 
			$("#empYn").prop("disabled", false);
			$("a[name^=perYear]").trigger("click");
			// 기간검색 주문일 , 결제완료일
			var select = $("#orderDateKey");
			select.children("[value='pymntDtm']").remove();
			select.children("[value='orderDtm']").remove();
			select.append(new Option("주문일", "pymntDtm"));
			select.append(new Option("결제확인일", "pymntDtm"));
			// 주문건 , 결제 금액 표시 부분  초기화
			abc.biz.member.member.order.orderDoAction('onLineSearch');
		}else if ( disivionType == "offline"){
			$("#onlineGrid").hide();
			$("#offlineGrid").show();
			// 사이트 --> 채널
			$("input[name=siteNo]").attr("disabled",true);
			$('input[name="siteNo"]:radio[value=""]').prop('checked',true);
			// 주문배송유형 -- > 없음 
			$("select[name=dlvyTypeCode]").prop("disabled",true);
			// 임직원 여부 비활성화 
			$("#empYn").prop("disabled", true); 
			$("a[name^=perToday]").trigger("click");
			// 기간검색 주문일 , 결제완료일 --> 결제완료일 제외 
			var select = $("#orderDateKey");
			select.children("[value='pymntDtm']").remove();
			select.children("[value='orderDtm']").remove();
			select.append(new Option("거래일자", "orderDtm"));
			// 주문건 , 결제 금액 표시 부분  초기화
			$('#orderCnt').text("");
			$('#orderAmt').text(""); 
			abc.biz.member.member.order.orderDoAction('offLineSearch');
		}
	}
	
	/**
	 * 주문 목록 Action 관리
	 */
	_memberOrder.orderDoAction = function(sAction){
		var pageCount = 15;
		
		
		var fromDate = Number(abc.text.validateStringSignRemove($("#fromDate").val()));;
		var toDate = Number(abc.text.validateStringSignRemove($("#toDate").val()));
		
		if(!abc.text.isDate(abc.text.validateStringSignRemove($("#fromDate").val()))){
			alert("시작일자를 확인해주세요");
			$("#fromDate").focus();
			
			return false;
		}
		
		if(!abc.text.isDate(abc.text.validateStringSignRemove($("#toDate").val()))){
			alert("종료일자를 확인해주세요");
			$("#toDate").focus();
			
			return false;
		}
		
		if(fromDate > toDate){
			alert("검색시작일은 종료일보다 미래 일 수 없습니다.\n검색기간을 재설정 해 주십시오.");
			$("#fromDate").focus();
			return false;
		}
		
		switch(sAction) {
			// 조회 
			case "onLineSearch" :
				var param = { url : "/order/member/onlineOrderList"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "onlineSheet" };
				
				DataSearchPaging(param);
				
				break;
			case "offLineSearch" :
				var param = { url : "/order/member/offlineOrderList"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "offlineSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
//	/**
//	 * 주문배송유형 선택 -- multi select 채널정보 
//	 */
//	_memberOrder.getChannelList = function(value) {
//		$.ajax({
//			type:"post",
//			url: "/order/channel-list/"
//		}).done(function(data, textStatus, jqXHR) {
//			abc.biz.member.member.order.setChannelList(data);
//		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
//			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
//		});
//	}
//	
//	
//	/**
//	 * 주문배송유형 선택 -- multi select 채널정보 
//	 */
//	_memberOrder.setChannelList = function(data) {
//		var select = $("#chnnlNo");
//		select.find("option").remove();
//		
//		var all = "";		
//		$.each(data, function(index, item) {
//			select.append(new Option(item.chnnlName, item.chnnlNo));
//			all += (index == 0) ? item.chnnlNo : ","+item.chnnlNo;
//		}); 
//		
//		select.prepend(new Option("전체", "" , false, true));
//	}
	
	_memberOrder.onlineSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		if ( Row != 0 ) {
			if (onlineSheet.ColSaveName(Col) == "orderNo") {
				var orderNo = onlineSheet.GetRowData(Row).orderNo;
				abc.orderDetailPopup(onlineSheet,"orderNo",Row,Col,'orderNo='+orderNo);
			}
		}
	}
	
})();