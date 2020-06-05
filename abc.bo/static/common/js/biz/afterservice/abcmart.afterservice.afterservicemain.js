/***
 * AS 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _afterservicemain = abc.object.createNestedObject(window,"abc.biz.afterservice.afterservicemain");
	
	/*************************************************************************
	 *								AS 목록/검색
	 *************************************************************************/
	
	/**
	 * AS관리 목록 초기 세팅
	 */
	_afterservicemain.initafterserviceSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("asGrid"), "asSheet", "100%", "370px");
		
		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		// 1. 접수자 구분 코드 추가 필요  -> 접수자가 관리자인지 일반회원인지
		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1,MergeSheet:msHeaderOnly, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:0};	
		initSheet.Cols 		 = [
			  {Header:"",				Type:"CheckBox",SaveName:"downloadCheck",		Width: 40,  Align:"Center", Edit:1, Sort:0}
			, {Header:"사이트",			Type:"Combo",	SaveName:"siteNo",				Width: 100,  Align:"Center", Edit:0, Sort:0}
			, {Header:"진행상태",			Type:"Combo",	SaveName:"asStatCode",			Width: 70,  Align:"Center", Edit:0, Sort:0}
			, {Header:"진행상태",			Type:"Text",	SaveName:"asStatCodeName",	    Width: 80,  Align:"Center", Edit:0, Sort:0}
			, {Header:"A/S번호", 			Type:"Text",	SaveName:"asAcceptNo",		    Width: 120, Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문번호",			Type:"Text",	SaveName:"orderNo",				Width: 120,  Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문배송ID", 		Type:"Text",	SaveName:"dlvyIdText",			Width: 140, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"A/S사유", 			Type:"Combo",	SaveName:"asGbnCode",			Width: 80,  Align:"Center", Edit:0, Sort:0}
			, {Header:"유형",				Type:"Combo",	SaveName:"asRsnCode",			Width: 100, Align:"Center", Edit:0, Sort:0}
			, {Header:"처리내용", 			Type:"Text",	SaveName:"asProcTypeInfo",		Width: 140,  Align:"Left", Edit:0, Sort:0}
			, {Header:"상품코드", 			Type:"Text",	SaveName:"prdtNo",		        Width: 100,  Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"상품명", 			Type:"Text",	SaveName:"prdtName",			Width: 240, Align:"Left",   Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"옵션",				Type:"Text",	SaveName:"optnName",     		Width: 80, Align:"Center", Edit:0, Sort:0}
			, {Header:"결제금액",			Type:"Float",	SaveName:"orderAmt",			Width: 80,  Align:"Right",  Edit:0, Sort:1,  Format:"#,##0 원"}
			, {Header:"접수자",			Type:"Text",	SaveName:"rgsterName",			Width: 120, Align:"Center", Edit:0, Sort:0, Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"접수일시",			Type:"Date",	SaveName:"rgstDtm",				Width: 140, Align:"Center", Edit:0, Sort:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자", 			Type:"Text",	SaveName:"moderName",			Width: 120, Align:"Center", Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"처리일시", 			Type:"Date",	SaveName:"modDtm",				Width: 140, Align:"Center", Edit:0, Sort:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"미처리여부", 		Type:"Text",	SaveName:"unProcYn",			Width: 70,  Align:"Center", Edit:0, Sort:0}
			, {Header:"접수자구분",			Type:"Text",	SaveName:"adminAcceptYn",		Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"회원타입값",			Type:"Text",	SaveName:"memberTypeName",		Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"등록자번호",			Type:"Text",	SaveName:"rgsterNo",			Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"관리자 번호",		Type:"Text",	SaveName:"moderNo",			    Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"진행상태변수",		Type:"Text",	SaveName:"colModify",			Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"상품순번",			Type:"Text",	SaveName:"asAcceptPrdtSeq",		Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"회원번호",			Type:"Text",	SaveName:"memberNo",		    Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"처리유형상세",		Type:"Text",	SaveName:"asProcTypeDtlCode",   Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"사이트번호",			Type:"Text",	SaveName:"orgSiteNo",			Align:"Center", Edit:0, Sort:0, Hidden:1}
		];

		//Grid 초기화
		IBS_InitSheet(asSheet, initSheet);
		// Grid 건수 정보 표시
		asSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		asSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//asSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//asSheet.SetExtendLastCol(1);
		
		asSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보
		asSheet.InitDataCombo(0, "asStatCode",  this.codeCombo.AS_STAT_CODE.text,   this.codeCombo.AS_STAT_CODE.code );		// 진행상태 
		asSheet.InitDataCombo(0, "asGbnCode",  this.codeCombo.AS_GBN_CODE.text,   this.codeCombo.AS_GBN_CODE.code );		// 사유구분 
		asSheet.InitDataCombo(0, "asRsnCode",  this.codeCombo.AS_RSN_CODE.text,   this.codeCombo.AS_RSN_CODE.code );		// 심의구분
	}
	
	/**
	 * 사이트 URL
	 */
	_afterservicemain.ABC_FO_URL = "";
	_afterservicemain.OTS_FO_URL = "";
	
	_afterservicemain.SITE_NO_ART = "";
	_afterservicemain.SITE_NO_OTS = "";
	
	/**
	 * 그리드 Click 이벤트
	 * 그리드에서 상세 팝업 띄우기 주문 상세 , AS상세 , 상품상세 , 접수자 , 관리자 상세 , 재접수 버튼 클릭에 해당하는 이벤트 발생
	 */
	_afterservicemain.asSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			if ( asSheet.ColSaveName(Col) == "asAcceptNo" && Value != "" ) {
				
				var asAcceptNo = asSheet.GetRowData(Row).asAcceptNo;
				var asGbnCode = asSheet.GetRowData(Row).asGbnCode;
				var asAcceptPrdtSeq = asSheet.GetRowData(Row).asAcceptPrdtSeq;
				_afterservicemain.openAfterServiceDetailPop(asAcceptNo, asAcceptPrdtSeq); // AS 상세 팝업
			}
			else if ( asSheet.ColSaveName(Col) == "orderNo" && Value != "" ) {
				var orderNo = asSheet.GetRowData(Row).orderNo;
				abc.orderDetailPopup(asSheet,"orderNo",Row,Col,'orderNo='+orderNo);
				//function(ibsheetObject,saveName,Row,Col,strParam)
			}
			else if ( asSheet.ColSaveName(Col) == "prdtNo" && Value != "" ) {
				
				var param = { prdtNo : asSheet.GetRowData(Row).prdtNo };
				abc.readonlyProductDetailPopup(param);
			}
			else if ( asSheet.ColSaveName(Col) == "prdtName" && Value != "" ) {
				var prdtNo = asSheet.GetRowData(Row).prdtNo;
				var prdtName = asSheet.GetRowData(Row).prdtName;
				
				if( asSheet.GetRowData(Row).orgSiteNo == _afterservicemain.SITE_NO_ART ){
					window.open(_afterservicemain.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(_afterservicemain.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
			}
			else if ( asSheet.ColSaveName(Col) == "rgsterName" && Value != "" ) {

				var adminAcceptYn = asSheet.GetRowData(Row).adminAcceptYn;
				
				if(adminAcceptYn == 'N'){
					// 관리자가 접수여부 : N -> 회원이 접수
					var memberTypeValue = asSheet.GetRowData(Row).memberTypeName;
					var memberNo = asSheet.GetRowData(Row).memberNo;	
					if(memberTypeValue != "비회원" || memberTypeValue != "" || memberTypeValue != null ){
						abc.memberDetailPopup(memberNo);
					}
					else 
					{
						//console.log("non-member");
					}
					
				}else if(adminAcceptYn == 'Y'){
					// 관리자가 접수 : Y
					var rgsterNo = asSheet.GetRowData(Row).rgsterNo;
					abc.adminDetailPopup(rgsterNo);
				}
			}
			else if ( asSheet.ColSaveName(Col) == "moderName" && Value != "" ) {
					var adminAcceptYn = asSheet.GetRowData(Row).adminAcceptYn;
				
					if(adminAcceptYn == 'N'){
						// 관리자가 여부 : N -> 회원이 접수
						var memberTypeValue = asSheet.GetRowData(Row).memberTypeName;
						var memberNo = asSheet.GetRowData(Row).memberNo;	
						if(memberTypeValue != "비회원" || memberTypeValue != "" || memberTypeValue != null ){
							abc.memberDetailPopup(memberNo);
						}
						else 
						{
							//console.log("non-member");
						}
						
					}else if(adminAcceptYn == 'Y'){
						// 관리자가 접수 : Y
						var rgsterNo = asSheet.GetRowData(Row).rgsterNo;
						abc.adminDetailPopup(rgsterNo);
					}
			}
			else  if ( asSheet.ColSaveName(Col) == "asStatCodeName" && Value != "" ) {
				var asAcceptNo = asSheet.GetRowData(Row).asAcceptNo;
				var reAccept = asSheet.GetRowData(Row).colModify;
				var asAcceptPrdtSeq = asSheet.GetRowData(Row).asAcceptPrdtSeq;
				var asProcTypeDtlCode = asSheet.GetRowData(Row).asProcTypeDtlCode;
				//10001 접수취소, 10011 AS완료 ,10012 AS불가 
				if(reAccept == abc.biz.afterservice.constCode.asStatCodeAcceptCancel || reAccept == abc.biz.afterservice.constCode.asStatCodeAsFinish  || reAccept == abc.biz.afterservice.constCode.asStatCodeAsReject ){
					// 재접수 
					_afterservicemain.ajaxReAfterService(asAcceptNo,asAcceptPrdtSeq,reAccept,asProcTypeDtlCode);
				}else{
					return;
				}
			}
		}
	}
	
	_afterservicemain.fromDash = "";
	
	/**
	 * dash 보드로 부터 접속 시
	 */
	_afterservicemain.fromDashboard = function(){
		
		if( !abc.text.allNull(_afterservicemain.fromDash) ){
			if( _afterservicemain.fromDash == "Y" ){
				_afterservicemain.afterserviceDoAction('search');
			}
		}
	}
	
	/**
	 * 심의 수선 선택했을때 상세 유형 선택 
	 */
	$('#asGbnCode').change(function(){
		if(this.value == ""){
			$('#asRsnCode').hide();
		}else{
			$('#asRsnCode').show();
		}
	});
	
	/**
	 * 결제구분 선택
	 */
	_afterservicemain.deviceCodeCheck = function(){
		
		/** 결제수단 변경 함수 */
		$("#chkDeviceCodeModuleAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=deviceCode]").prop("checked",true);
			} else {
				$("input[custom=deviceCode]").prop("checked",false);
			}
		})
		
		$("input[custom=deviceCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=deviceCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#chkDeviceCodeModuleAll").prop("checked",true);					
					}
				} else {
					$("#chkDeviceCodeModuleAll").prop("checked",false);
				}
			})
		})
	}
	
	//페이지선택시
	$("#pageCount").change(function(){
		_afterservicemain.afterserviceDoAction('search');
	});
	
	/**
	 * AS번호 / 주문번호 선택 후, 입력 input
	 */
	_afterservicemain.asOrderNoSelect = function(){
		
		// AS 및 주문번호 selectBox 선택 시
		$("#asOrderNoSelect").change(function(){
			$("#asOrderNoInp").off();
			$("#asOrderNoInp").val("");
			// 클레임번호로 검색할 때
			if($("#asOrderNoSelect option:selected").val() == "asAcceptNo"){
				$("#asOrderNoInp").attr("name", "asAcceptNo").off().on("keydown keyup focusout", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
					
				});
			} 
				// 주문번호로 검색할 때 
			else if($("#asOrderNoSelect option:selected").val() == "orderNo") {
				$("#asOrderNoInp").attr("name", "orderNo").off().on("keydown keyup focusout", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			}
		});
	}
	
	
	/**
	 * 상품 selectBox 선택 후, 입력 input
	 */
	_afterservicemain.prdtInfoSelect = function(){
		
		// 상품 selectBox 선택 시
		$("#prdtInfoSelect").change(function(){
			$("#prdtInfoInp").val("");
			// 상품코드으로 검색할 때
			if($("#prdtInfoSelect option:selected").val() == "prdtNo"){
				$("#prdtInfoInp").attr("name", "prdtNo").off().on("keydown keyup focusout", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			} 
			// 상품명로 검색할 때 
			else if($("#prdtInfoSelect option:selected").val() == "prdtName") {
				$("#prdtInfoInp").attr("name", "prdtName").off();
			} 
			// 스타일코드로 검색할 때
			else if($("#prdtInfoSelect option:selected").val() == "styleInfo") {
				$("#prdtInfoInp").attr("name", "styleInfo").off();
			} 
		});
	}
	
	/**
	 * 주문 selectBox 선택 후, 입력 input
	 */
	_afterservicemain.orderMemberInfoSelect = function(){
		
		// 주문 selectBox 선택 시
		$("#orderMemberInfoSelect").change(function(){
			
			$("#orderMemberInfoInp").val("");
			$("#hdphnBackNoText").hide();
			// 주문자명으로 검색할 때
			if($("#orderMemberInfoSelect option:selected").val() == "buyerName"){
				
				$("#orderMemberInfoInp").attr("name", "buyerName").off();
				$("#orderMemberInfoInp").attr("placeholder", "");
				$("#hdphnBackNoText").show();
				
			} // 수령자로 검색할 때 
			else if($("#orderMemberInfoSelect option:selected").val() == "rcvrName") {
				
				$("#orderMemberInfoInp").attr("name", "rcvrName").off();
				$("#orderMemberInfoInp").attr("placeholder", "");
				
			} // 휴대폰 번호로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "buyerHdphnNoText") {
				
				$("#orderMemberInfoInp").attr("name", "buyerHdphnNoText").off().on("keydown keyup focusout", function() {
					$("#orderMemberInfoInp").val($("#orderMemberInfoInp").val().replace(/[^0-9]/gi,''));
				});
				$("#orderMemberInfoInp").attr("placeholder", "- 없이 전체 입력");
				
			} // 주문자ID로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "loginId") {
				
				$("#orderMemberInfoInp").attr("name", "loginId").off();
				$("#orderMemberInfoInp").attr("placeholder", "");
			}
		});
	}
	
	
	/**
	 * AS 진행상태 checkBox 선택
	 */
	_afterservicemain.asStatCodeCheck = function(){
		
		/** 결제수단 변경 함수 */
		$("#chkAsStatModuleAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=asStatCode]").prop("checked",true);
			} else {
				$("input[custom=asStatCode]").prop("checked",false);
			}
		})
		
		$("input[custom=asStatCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=asStatCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#chkAsStatModuleAll").prop("checked",true);					
					}
				} else {
					$("#chkAsStatModuleAll").prop("checked",false);
				}
			})
		})
	}
	
	/**
	 * 미처리여부  radio 선택시 검색기간 날짜 control 
	 */
	_afterservicemain.unProcYnSelect = function(){
		// 전체
		$("#rdoUnProcYnAll").click(function(){
			$("a[name^=per1Month]").trigger("click");
		});
		//처리 선택시   
		$("#rdoUnProcYnY").click(function(){
			$("a[name^=per1Month]").trigger("click");
		});
		// 미처리 선택시 
		$("#rdoUnProcYnN").click(function(){
			$("#fromDate").val("");
			$("#toDate").val("");
		});
	}
	
	$("#btnSearch").click(function(){
		_afterservicemain.afterserviceDoAction('search');
	});
	
	/**
	 * A/S 목록 Action 관리
	 */
	_afterservicemain.afterserviceDoAction = function(sAction){
		switch(sAction) {
		
		case "search" :
			
			if(!_afterservicemain.searchValidation()){
				return;
			}
			
			var pageCount = $('#pageCount').val();
			// 검색기간 최초 한달 기본
			// 미처리 여부 VAL 확인 날짜 초기화 
			var unProcYn = $('input:radio[name="unProcYn"]:checked').val();
			if(unProcYn != 'N' && unProcYn != '' ){
				$("a[name^=per1Month]").trigger("click");
				$("#fromDate").val("");
				$("#toDate").val("");
			}
			
			// 1년 넘는지 확인 
			var diff = abc.date.dateDiffAbs($("#fromDate").val(),$("#toDate").val());
			
			if(diff > 365){
				alert("rj");
			}
			
			var param = { url : "/afterservice/read-afterservice-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "asSheet" };
		
			DataSearchPaging(param);
			
			//체크박스는 검색을 하면 초기화되지만, 체크시 생성되었던 input은 사라지지 않으므로 
			$("#frmDownload").empty();
			
			break;
		}
	}
	
	$("#btnReset").click(function(){
		_afterservicemain.searchFormReset();
	});
	
	/**
	 * AS관리 검색 form 초기화
	 */
	_afterservicemain.searchFormReset = function() {
		
		$('#frmSearch').each(function(){
			this.reset();
			$("a[name^=per1Month]").trigger("click");
		});
	}
	
	$("#downloadExcel").click(function(){
		_afterservicemain.downloadExcel();
	});
	
	/**
	 * 선택한 AS 목록 엑설로 다운로드
	 */
	_afterservicemain.downloadExcel = function() {
		
		var sRow = asSheet.FindCheckedRow("downloadCheck");
		
		if (sRow <= 0 ){
			alert( "다운로드 할 항목을 선택해 주세요." );
			return false;
			
		} else {
			// 배열 처리 
			for(var i=1; i <= asSheet.RowCount(); i++){
				if(asSheet.GetCellValue(i, "downloadCheck") == "1"){
					console.log(asSheet.GetCellValue(i, "asAcceptNo"));
					
					$("#frmSearch").append("<input type='text' id='asNos' name='asNos.asAcceptNo' value='" + asSheet.GetCellValue(i, "asAcceptNo") + "'>");
				}	
			}

			frmSearch.action = "/afterservice/afterservice/download-afterservice-excel";
			frmSearch.submit();
			
			$("input[name='asNos.asAcceptNo']").remove();
		}
	}
	
	$("#downloadAllExcel").click(function(){
		_afterservicemain.downloadAllExcel();
	});
	
	/**
	 * 한 페이지의 전체 AS 목록 엑설로 다운로드
	 */
	_afterservicemain.downloadAllExcel = function() {
		if(asSheet.RowCount() == 0) {
			alert("다운로드 할 데이타가 없습니다.");
		}else{
			var sortInfo   = asSheet.GetColSortInfo();
			
			if( sortInfo[0] != undefined ){
				var sortColumn = asSheet.ColSaveName(sortInfo[0].Col);
				var sortType   = sortInfo[0].SortOrder;
				$("#sortColumn").val(sortColumn);
				$("#sortType").val(sortType);
			}
			
			frmSearch.action = "/afterservice/afterservice/download-afterservice-all-excel";
			frmSearch.submit();
			
			$("#sortColumn").val("");
			$("#sortType").val("");
		}
	}
	
	/**
	 * 캘린더 버튼 설정(일)
	 */
	$("a[name^=perToday]").click(function(){
		abc.date.days(this);
	});
	
	/**
	 * 캘린더 버튼 설정(주)
	 */
	$("a[name^=perWeek]").click(function(){
		abc.date.week(this);
	});
	
	/**
	 * 캘린더 버튼 설정(월) 
	 */
	$("a[name^=per1Month]").click(function(){
		abc.date.month(this);
	});
	
	/**
	 * 캘린더 버튼 설정(전체)
	 */
	$("a[name^=per1Year]").click(function(){
		abc.date.year(this);
	});
	
	/**
	 *  검색 실행
	 */
	_afterservicemain.searchValidation = function(){
		
		if(!abc.date.searchValidate()){
			$("a[name=per1Month]").trigger("click");
			return false;
		}
		
		// 검색 기간 validate
		var dateDiff = abc.date.dateDiffAbs($("#fromDate").val(), $("#toDate").val());
		
		if(dateDiff > 365) {
			alert("검색 기간은 1년을 넘을 수 없습니다.");
			$("a[name=per1Month]").trigger("click");
			return false;
		}
		
		//상품코드 자릿수 체크 
		if($("#prdtInfoSelect option:selected").val() == "prdtNo"){
			if(abc.text.isLimitLength($("#prdtInfoInp").val(), 10)){
				alert("상품코드 자리수를 초과하였습니다.");
				$("#prdtInfoInp").focus();
				return false;
			}
		}
		//상품코드 자릿수 체크 
		if($("#prdtInfoSelect option:selected").val() == "prdtName"){
			if(abc.text.isLimitLength($("#prdtInfoInp").val(), 100)){
				alert("상품명  최대길이 50자를 초과하였습니다.");
				$("#prdtInfoInp").focus();
				return false;
			}
		}
		//스타일코드 자릿수 체크 
		if($("#prdtInfoSelect option:selected").val() == "styleInfo"){
			if(abc.text.isLimitLength($("#prdtInfoInp").val(), 50)){
				alert("스타일코드  최대길이 25자를 초과하였습니다.");
				$("#prdtInfoInp").focus();
				return false;
			}
		}
		//주문번호  자릿수 체크 
		if($("#asOrderNoSelect option:selected").val() == "orderNo") {
			if(abc.text.isLimitLength($("#asOrderNoInp").val(), 13)){
				alert("주문번호 최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		
		//AS접수번호
		if($("#asOrderNoSelect option:selected").val() == "asAcceptNo") {
			if(abc.text.isLimitLength($("#asOrderNoInp").val(), 13)){
				alert("AS접수번호 최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		
		// loginId 최대깅이
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
				return false;
			}
			if(!abc.text.allNull($("#orderMemberInfoInp").val()) 
					&& abc.text.allNull($("#hdphnBackNoText").val())){
				alert("휴대폰번호 뒷자리를 입력해주세요.");
				return false;
			}
			if(!abc.text.allNull($("#orderMemberInfoInp").val()) 
					&& $("#hdphnBackNoText").val().length < 4){
				alert("휴대폰번호 뒤 4자리를 입력해주세요.");
				return false;
			}
		}
		// rcvrName 최대길이
		if($("#orderMemberInfoSelect option:selected").val() == "rcvrName") {
			if(abc.text.isLimitLength($("#orderMemberInfoInp").val(), 50)){
				alert("수령자명이 최대 자리수를 초과하였습니다.");
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
	_afterservicemain.detailPop = '';
	_afterservicemain.openAfterServiceDetailPop = function(asAcceptNo, asAcceptPrdtSeq){
		var url = "";
		var params = {asAcceptNo : asAcceptNo, asAcceptPrdtSeq:asAcceptPrdtSeq};
		url = "/afterservice/afterservice/read-afterservice-detail-pop"; // AS접수 상세 
		
		_afterservicemain.detailPop = abc.open.popup({
			winname :	"asDetailPop",
			url 	:	url,
			width 	:	1300,
			height	:	950,
			params	:	params
		});
	}
	
	
	/**
	 * 재접수 클릭시 신규 번호 채번하고 기존데이타 
	 */
	
	_afterservicemain.ajaxReAfterService = function(asAcceptNo,asAcceptPrdtSeq,asStatCode,asProcTypeDtlCode){
		// 재접수시 교환 또는 반품 처리 상세 유형이면 
		if(asProcTypeDtlCode == abc.biz.afterservice.constCode.asPocTypeDtlCodeChange || asProcTypeDtlCode == abc.biz.afterservice.constCode.asPocTypeDtlCodeReturn ){
			alert("해당 A/S건은 클레임으로 접수되어 A/S재접수가 불가합니다.");
		}else{
			var url = "/afterservice/afterservice/create-reAfterservice-detail-pop";
			var params = {}
			
			params.asAcceptNo 		=  asAcceptNo;
			params.asAcceptPrdtSeq  =  asAcceptPrdtSeq;
			
			abc.open.popup({
				winname :	"reEntryAccept",
				url 	:	url,
				width 	:	1230,  
				height	:	860,    
				params	:	params
			});
		}
	}
	
	/**
	 * 브랜드 selectBox 선택 시
	 */
	$("#btnBrand").click(function(){
		var options = { callback : "abc.biz.afterservice.afterservicemain.setSelectedBrand", brandName : $('#brandName').val() };
		abc.brandSearchPopup(false, options);
	});
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_afterservicemain.setSelectedBrand = function(data) {
		$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
	
	/**
	 * 처리유형상세 코드가 교환 도는 반품일 경우 (배송비 취소안함 전제) 
	 */
	_afterservicemain.openClaimDetailPop = function(clmNo, clmGbnCode){
		_afterservicemain.detailPop.close();
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};

		if(clmGbnCode == abc.biz.afterservice.constCode.CLM_GBN_CODE_EXCHANGE) { //교환
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
		} else if(clmGbnCode == abc.biz.afterservice.constCode.CLM_GBN_CODE_RETURN) { //반품
			url = "/claim/claim/read-claim-return-detail-pop"; // 클레임 반품 상세
		}
		abc.open.popup({
			winname :	"claimDetailPop",
			url 	:	url,
			width 	:	1260,
			height	:	990,
			params	:	params
		});
	}
		
	/**
	 * AS 상세에서 수선불가 > 교환, 반품일 경우 값을 셋팅 
	 */
	_afterservicemain.data = {};
	
})();