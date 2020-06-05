/***
 * AS 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _afterserviceamount = abc.object.createNestedObject(window,"abc.biz.afterservice.afterserviceamount");
	
	/*************************************************************************
	 *								AS 목록/검색
	 *************************************************************************/
	
	/**
	 * AS관리 목록 초기 세팅
	 */
	_afterserviceamount.initafterserviceAmtSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("asAmtGrid"), "asAmtSheet", "100%", "370px");
		
		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init',MergeSheet:msHeaderOnly, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols 		 = [
			  {Header:"",				Type:"CheckBox",SaveName:"downloadCheck",		Width: 4,  Align:"Center", Edit:1, Sort:0}
			, {Header:"사이트",			Type:"Combo",	SaveName:"siteNo",  			Width: 8,  Align:"Center", Edit:0, Sort:0}
			, {Header:"A/S번호", 			Type:"Text",	SaveName:"asAcceptNo",		    Width: 8,  Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"접수일시",			Type:"Date",	SaveName:"rgstDtm",			    Width: 9,  Align:"Center", Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"주문번호",			Type:"Text",	SaveName:"orderNo",				Width: 8,  Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"브랜드",			Type:"Text",	SaveName:"brandName",			Width: 8,  Align:"Center", Edit:0, Sort:0}
			, {Header:"상품명", 			Type:"Text",	SaveName:"prdtName",			Width: 15, Align:"Left",   Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문자", 			Type:"Text",	SaveName:"rgsterName",			Width: 8,  Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"배송비",			Type:"Float",	SaveName:"addDlvyAmt",			Width: 8,  Align:"Right",  Edit:0, Sort:1,  Format:"#,##0 원"}
			, {Header:"A/S비용", 			Type:"Float",	SaveName:"asAmt",		        Width: 8,  Align:"Right",  Edit:0, Sort:1,  Format:"#,##0 원"}
			, {Header:"합계",				Type:"Float",	SaveName:"totalPymntAmt",  		Width: 8,  Align:"Right",  Edit:0, Sort:1,  Format:"#,##0 원"}
			, {Header:"수급상태", 			Type:"Text",	SaveName:"asStatCodeName",	    Width: 8,  Align:"Center", Edit:0, Sort:0}
			, {Header:"사유구분", 			Type:"Text",	SaveName:"asGbnCode",			Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"상품순번", 			Type:"Text",	SaveName:"asAcceptPrdtSeq",		Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"관리자접수여부", 		Type:"Text",	SaveName:"adminAcceptYn",		Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"상품번호", 			Type:"Text",	SaveName:"prdtNo",				Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"회원번호", 			Type:"Text",	SaveName:"memberNo",			Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"회원타입값",			Type:"Text",	SaveName:"memberTypeName",		Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"사이트",			Type:"Text",	SaveName:"orgSiteNo",  			Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			];

		//Grid 초기화
		IBS_InitSheet(asAmtSheet, initSheet);
		// Grid 건수 정보 표시
		asAmtSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		asAmtSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		asAmtSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		asAmtSheet.SetExtendLastCol(1);
		
		asAmtSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보
	}
	
	/**
	 * 사이트 URL
	 */
	_afterserviceamount.ABC_FO_URL = "";
	_afterserviceamount.OTS_FO_URL = "";
	
	/**
	 * 그리드 Click 이벤트
	 * 그리드에서 상세 팝업 띄우기 주문 상세 , AS상세 , 상품상세 , 접수자 ,클릭에 해당하는 이벤트 발생
	 */
	_afterserviceamount.asAmtSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			if ( asAmtSheet.ColSaveName(Col) == "asAcceptNo" && Value != "" ) {
				
				var asAcceptNo = asAmtSheet.GetRowData(Row).asAcceptNo;
				var asGbnCode = asAmtSheet.GetRowData(Row).asGbnCode;
				var asAcceptPrdtSeq = asAmtSheet.GetRowData(Row).asAcceptPrdtSeq;
				_afterserviceamount.openAfterServiceDetailPop(asAcceptNo,asAcceptPrdtSeq); // AS 상세 팝업
			}
			else if ( asAmtSheet.ColSaveName(Col) == "orderNo" && Value != "" ) {
				var orderNo = asAmtSheet.GetRowData(Row).orderNo;
				abc.orderDetailPopup(asAmtSheet,"orderNo",Row,Col,'orderNo='+orderNo);
				
			}
			else if ( asAmtSheet.ColSaveName(Col) == "prdtName" && Value != "" ) {
				var prdtNo = asAmtSheet.GetRowData(Row).prdtNo;
				var prdtName = asAmtSheet.GetRowData(Row).prdtName;
				
				if( asAmtSheet.GetRowData(Row).orgSiteNo == "10000"){
					window.open(_afterserviceamount.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(_afterserviceamount.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
			}
			else if ( asAmtSheet.ColSaveName(Col) == "rgsterName" && Value != "" ) {
				
				var memberTypeValue = asAmtSheet.GetRowData(Row).memberTypeName;
				var memberNo = asAmtSheet.GetRowData(Row).memberNo;	
				var asAcceptNo = asAmtSheet.GetRowData(Row).asAcceptNo;
				
				if(memberTypeValue == "비회원"){
					alert("접수자 비회원 - 상세팝업");
				}
				else 
				{
					abc.memberDetailPopup(memberNo);
				}
			}
		}
	}
	/**
	 * 주문 selectBox 선택 후, 입력 input
	 */
	_afterserviceamount.orderMemberInfoSelect = function(){
		
		// 주문 selectBox 선택 시
		$("#orderMemberInfoSelect").change(function(){
			
			$("#orderMemberInfoInp").val("");
			$("#hdphnBackNoText").hide();
			// 주문자명으로 검색할 때
			if($("#orderMemberInfoSelect option:selected").val() == "buyerName"){
				
				$("#orderMemberInfoInp").attr("name", "buyerName");
				$("#orderMemberInfoInp").attr("placeholder", "");
				$("#hdphnBackNoText").show();
			} // 수령자로 검색할 때 
			else if($("#orderMemberInfoSelect option:selected").val() == "buyerHdphnNoText") {
				
				$("#orderMemberInfoInp").attr("name", "buyerHdphnNoText");
				$("#orderMemberInfoInp").attr("placeholder", "- 없이 전체 입력");
				
			} // 주문자ID로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "loginId") {
				
				$("#orderMemberInfoInp").attr("name", "loginId");
				$("#orderMemberInfoInp").attr("placeholder", "");
			}
		});
	}
	
	
	//페이지선택시
	$("#pageCount").change(function(){
		_afterserviceamount.afterserviceDoAction("search");
	});
	
	
	$("#btnSearch").click(function(){
		_afterserviceamount.afterserviceDoAction('search');
	});
	
	/**
	 * A/S 목록 Action 관리
	 */
	_afterserviceamount.afterserviceDoAction = function(sAction){
		switch(sAction) {
		
		case "search" :
			
			if(!_afterserviceamount.searchValidation()){
				return;
			}
			
			var pageCount = $('#pageCount').val();
			
			var param = { url : "/afterservice/read-afterservice-amount-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "asAmtSheet" };
		
			DataSearchPaging(param);
			
			//체크박스는 검색을 하면 초기화되지만, 체크시 생성되었던 input은 사라지지 않으므로 
			$("#frmDownload").empty();
			
			break;
		}
	}
	
	$("#btnReset").click(function(){
		_afterserviceamount.searchFormReset();
	});
	
	/**
	 * AS관리 검색 form 초기화
	 */
	_afterserviceamount.searchFormReset = function() {
		
		$('#frmSearch').each(function(){
			this.reset();
			$("a[name^=per1Month]").trigger("click");
		});
	}
	
	$("#downloadExcel").click(function(){
		_afterserviceamount.downloadExcel();
	});
	
	/**
	 * 선택한 AS 목록 엑설로 다운로드
	 */
	_afterserviceamount.downloadExcel = function() {
		
		var sRow = asAmtSheet.FindCheckedRow("downloadCheck");
		
		if (sRow <= 0 ){
			alert( "다운로드 할 항목을 선택해 주세요." );
			return false;
			
		} else {
			// 배열 처리 
			for(var i=1; i <= asAmtSheet.RowCount(); i++){
				if(asAmtSheet.GetCellValue(i, "downloadCheck") == "1"){
					console.log(asAmtSheet.GetCellValue(i, "asAcceptNo"));
					
					$("#frmSearch").append("<input type='text' id='asNos' name='asNos.asAcceptNo' value='" + asAmtSheet.GetCellValue(i, "asAcceptNo") + "'>");
				}	
			}

			frmSearch.action = "/afterservice/afterservice/download-afterserviceamount-excel";
			frmSearch.submit();
			
			$("input[name='asNos.asAcceptNo']").remove();
		}
	}
	
	$("#downloadAllExcel").click(function(){
		_afterserviceamount.downloadAllExcel();
	});
	
	/**
	 * 한 페이지의 전체 AS 목록 엑설로 다운로드
	 */
	_afterserviceamount.downloadAllExcel = function() {
		if(asAmtSheet.RowCount() == 0) {
			alert("다운로드 할 데이타가 없습니다.");
		}else{
			var sortInfo   = asAmtSheet.GetColSortInfo();
			
			if( sortInfo[0] != undefined ){
				var sortColumn = asAmtSheet.ColSaveName(sortInfo[0].Col);
				var sortType   = sortInfo[0].SortOrder;
				$("#sortColumn").val(sortColumn);
				$("#sortType").val(sortType);
			}
			
			frmSearch.action = "/afterservice/afterservice/download-afterserviceamount-all-excel";
			frmSearch.submit();
			
			$("#sortColumn").val("");
			$("#sortType").val("");
		}
	}
	
	/**
	 * 캘린더 버튼 설정(일)
	 */
	$("#perToday").click(function(){
		abc.date.days(this);
	});
	
	/**
	 * 캘린더 버튼 설정(주)
	 */
	$("#perWeek").click(function(){
		abc.date.week(this);
	});
	
	/**
	 * 캘린더 버튼 설정(월) 
	 */
	$("#per1Month").click(function(){
		abc.date.month(this);
	});
	
	/**
	 * 캘린더 버튼 설정(전체)
	 */
	$("#per1Year").click(function(){
		abc.date.year(this);
	});
	
	/**
	 *  검색 실행
	 */
	_afterserviceamount.searchValidation = function(){
		
		if(!abc.date.searchValidate()){
			$("#per1Month").trigger("click");
			return false;
		}
		// 검색 기간 validate
		var dateDiff = abc.date.dateDiffAbs($("#fromDate").val(), $("#toDate").val());
		
		if(dateDiff > 365) {
			alert("검색 기간은 1년을 넘을 수 없습니다.");
			$("#per1Month").trigger("click");
			return false;
		}
		
		//주문번호  자릿수 체크 
		if($("#orderNo").val() == "orderNo") {
			if(abc.text.isLimitLength($("#orderNo").val(), 13)){
				alert("주문번호 최대 자리수를 초과하였습니다.");
				$("#orderNo").focus();
				return false;
			}
		}
			
		// loginId 최대길이
		if($("#orderMemberInfoSelect option:selected").val() == "loginId") {
			if(abc.text.isLimitLength($("#orderMemberInfoInp").val(), 15)){
				alert("로그인 아이디가 최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
			
		// buyerName 최대길이
		if($("#orderMemberInfoSelect option:selected").val() == "buyerName") {
			if(abc.text.isLimitLength($("#orderMemberInfoInp").val(), 50)){
				alert("주문자명이 최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
			if(abc.text.allNull($("#orderMemberInfoInp").val())
					&& !abc.text.allNull($("#hdphnBackNoText").val())){
				alert("주문자명을 입력해주세요.");
				$("#asOrderNoInp").focus();
				return false;
			}
			if(!abc.text.allNull($("#orderMemberInfoInp").val()) 
					&& abc.text.allNull($("#hdphnBackNoText").val())){
				alert("휴대폰번호 뒷자리를 입력해주세요.");
				$("#asOrderNoInp").focus();
				return false;
			}
			if(!abc.text.allNull($("#orderMemberInfoInp").val()) 
					&& $("#hdphnBackNoText").val().length < 4){
				alert("휴대폰번호 뒤 4자리를 입력해주세요.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		
		// 휴대폰 번호 자릿수
		if($("#orderMemberInfoSelect option:selected").val() == "buyerHdphnNoText") {
			if(abc.text.isLimitLength($("#orderMemberInfoInp").val(), 11)){
				alert("휴대폰 번호 자릿수가 맞지 않습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * AS  상세 팝업 호출
	 */
	_afterserviceamount.openAfterServiceDetailPop = function(asAcceptNo,asAcceptPrdtSeq){
		var url = "";
		var params = {asAcceptNo : asAcceptNo, asAcceptPrdtSeq:asAcceptPrdtSeq};
		url = "/afterservice/afterservice/read-afterservice-detail-pop"; // AS접수 상세 
		
		abc.open.popup({
			winname :	"A/S상세",
			url 	:	url,
			width 	:	1300,
			height	:	950,
			params	:	params
		});
	}
	
	/**
	 * 세션의 관리자와 AS 접수/처리의 관리자와 같은지 ajax 요청
	 */
	_afterserviceamount.ajaxCheckAfterServiceAdmin = function(handler,adminNo){
		var result = "N";	
//		$.ajax({
//				type : "post",
//				url  : "/afterservice/afterservice/check-afterservice-admin",
//				data : {handler,adminNo}
//					
//			}).done(function(data){
//				result = data.isConfirm;
//			}).fail(function(e){
//				console.log(e);
//			});
		return result;
	}
	
	/**
	 * 브랜드 selectBox 선택 시
	 */
	$("#btnBrand").click(function(){
		var options = { callback : "abc.biz.afterservice.afterserviceamount.setSelectedBrand", brandName : $('#brandName').val() };
		abc.brandSearchPopup(false, options);
	});
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_afterserviceamount.setSelectedBrand = function(data) {
		$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
})();