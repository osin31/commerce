/***
 * 환수 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _redemptionmain = abc.object.createNestedObject(window,"abc.biz.claim.redemptionmain");
	
	/*************************************************************************
	 *								환수 목록/검색 변수 선언
	 *************************************************************************/
	
	//회원 유형 코드    : 비회원 
	_redemptionmain.MEMBER_TYPE_NONMEMBER	= "";
	//클레임 구분 코드 : 취소
	_redemptionmain.CLM_GBN_CODE_CANCEL		= "";
	//클레임 구분 코드 : 교환
	_redemptionmain.CLM_GBN_CODE_EXCHANGE	= "";
	//클레임 구분 코드 : 반품
	_redemptionmain.CLM_GBN_CODE_RETURN		= "";	
	//결제상태코드 : 환수접수
	_redemptionmain.PYMNT_STAT_CODE_REDEMP_ACCEPT		= "";	
	//결제상태코드 : 환수완료
	_redemptionmain.PYMNT_STAT_CODE_REDEMP_COMPLETE		= "";	
	
	/*************************************************************************
	 *								환수 목록/검색
	 *************************************************************************/
	
	/**
	 * 환수 목록 초기 세팅
	 */
	_redemptionmain.initRedemptionSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("redemptionGrid"), "redemptionSheet", "100%", "370px");
		
		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init', MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols 		 = [
			  {Header:"상태",				Type:"Status",	SaveName:"Status", 				Width:10,  Hidden:1}
			, {Header:"환불/환수구분",		Type:"Text",	SaveName:"redempRfndGbnType",	Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",SaveName:"downloadCheck",		Width: 5,  Align:"Center", Edit:1, Sort:0}
			, {Header:"클레임구분",			Type:"Text",	SaveName:"clmGbnCodeName",		Width: 9,  Align:"Center", Edit:0, Sort:0}
			, {Header:"클레임구분코드",		Type:"Text",	SaveName:"clmGbnCode",			Width: 6,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"사이트",			Type:"Text",	SaveName:"clmSiteName",			Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"발생사유", 			Type:"Text",	SaveName:"ocrncRsnCodeName",    Width: 16, Align:"Center", Edit:0, Sort:0}
			, {Header:"클레임번호", 		Type:"Text",	SaveName:"clmNo",				Width: 12, Align:"Center", Edit:0, Sort:0,FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문번호", 			Type:"Text",	SaveName:"orgOrderNo",			Width: 12, Align:"Center", Edit:0, Sort:0,FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문자번호",			Type:"Text",	SaveName:"memberNo",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"주문자", 			Type:"Text",	SaveName:"orderMember",			Width: 15, Align:"Center", Edit:0, Sort:0,FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"환수금액",			Type:"Float",	SaveName:"pymntAmt",			Width: 13, Align:"Right",  Edit:0, Sort:1, Format:"#,##0 원", FontColor:"#FF0000"}
			, {Header:"수급상태코드",		Type:"Text",	SaveName:"pymntStatCode",		Width: 4,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header:"수급상태",			Type:"Text",	SaveName:"pymntStatCodeName",	Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"임의처리여부",		Type:"Text",	SaveName:"redempAmtRndmProcYn",	Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"환수은행명",			Type:"Text",	SaveName:"bankCodeName",		Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"환수계좌",			Type:"Text",	SaveName:"clmAcnt",				Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"예금주",			Type:"Text",	SaveName:"acntHldrName",		Width: 8,  Align:"Center", Edit:0, Sort:0}
			, {Header:"환수금액 발생일시", 	Type:"Date",	SaveName:"rgstDtm",				Width: 13, Align:"Center", Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"가상계좌 소멸일시", 	Type:"Date",	SaveName:"vrtlAcntExprDtm",		Width: 13, Align:"Center", Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
			, {Header:"처리자", 			Type:"Text",	SaveName:"clmHandler",			Width: 13, Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"처리일시", 			Type:"Date",	SaveName:"modDtm",				Width: 13, Align:"Center", Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자번호",			Type:"Text",	SaveName:"moderNo",						   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"멤버타입코드",		Type:"Text",	SaveName:"memberTypeCode",				   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"처리자번호",			Type:"Text",	SaveName:"redempRfndOpetrNo",			   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"처리일시", 			Type:"Date",	SaveName:"redempRfndOpetrDtm",	Width: 13, Align:"Center", Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
			, {Header:"로그인ID",			Type:"Text",	SaveName:"loginId",			   			   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"회원명",			Type:"Text",	SaveName:"memberName",			   		   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"주문자명",			Type:"Text",	SaveName:"buyerName",			   		   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"은행명",			Type:"Text",	SaveName:"bankCodeName",			   	   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"계좌번호",			Type:"Text",	SaveName:"acntNoText",			   		   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"관리자ID",			Type:"Text",	SaveName:"adminId",			   			   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"관리자 명",			Type:"Text",	SaveName:"adminName",			   		   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"사이트번호",			Type:"Text",	SaveName:"siteNo",						   Align:"Center", Edit:0, Hidden:1}
			, {Header:"디비 현재시각",		Type:"Text",	SaveName:"strNowTime",					   Align:"Center", Edit:0, Hidden:1}
		];

		//Grid 초기화
		IBS_InitSheet(redemptionSheet, initSheet);
		// Grid 건수 정보 표시
		redemptionSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		redemptionSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		redemptionSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		redemptionSheet.SetExtendLastCol(1);
	}

	/**
	 * 그리드 Click 이벤트
	 */
	_redemptionmain.redemptionSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0) {
			
			if ( redemptionSheet.ColSaveName(Col) == "orgOrderNo" && Value != "" ) {

				var saveName = "orgOrderNo"; // 원주문번호로 주문상세 팝업 호출
				abc.orderDetailPopup(redemptionSheet, saveName, Row, Col);
			}
			else if ( redemptionSheet.ColSaveName(Col) == "clmNo" && Value != ""  ) {
				
				var clmNo = redemptionSheet.GetRowData(Row).clmNo;
				var clmGbnCode = redemptionSheet.GetRowData(Row).clmGbnCode;
				// 클레임 상세 팝업 호출
				_redemptionmain.openClaimDetailPop(clmNo, clmGbnCode);
			}
			else if ( redemptionSheet.ColSaveName(Col) == "orderMember" && Value != ""  ) {
				
				var memberTypeCode = redemptionSheet.GetRowData(Row).memberTypeCode;
				
				if(memberTypeCode != _redemptionmain.MEMBER_TYPE_NONMEMBER){
					// 비회원이 아니면 회원상세 팝업 호출
					abc.memberDetailPopup(redemptionSheet.GetRowData(Row).memberNo);
				}
			}
			else if ( redemptionSheet.ColSaveName(Col) == "clmHandler" && Value != ""  ) {
				
				var moderNo = redemptionSheet.GetRowData(Row).moderNo;
				abc.adminDetailPopup(moderNo);
			}
		}
	}
	
	_redemptionmain.redemptionSheetOnSaveEnd = function(Code, Msg, StCode, StMsg, Response){
		
		if(StCode == "200")
			alert("변경된 값으로 저장하였습니다.");
		else
			alert("에러가 발생하였습니다.");
		
		_redemptionmain.redemptionDoAction("search");
	}
	
	$("#btnSearch").click(function(){
		_redemptionmain.redemptionDoAction("search");
	});
	
	/**
	 * 환수 목록 Action 관리
	 */
	_redemptionmain.redemptionDoAction = function(sAction){
		switch(sAction) {
		
		case "search" :
			
			if( _redemptionmain.searchValidation() == false ){ return; }
			
			var pageCount = $('#pageCount').val();
			//console.log(FormQueryStringEnc(document.frmSearch));
			var param = { url : "/claim/redemption/read-redemption-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "redemptionSheet" };
			
			DataSearchPaging(param);
			
			break;
			
/*		case "update" : // 현제 삭제된 기능
			var checkCnt = redemptionSheet.FindCheckedRow("downloadCheck");
			
			if(checkCnt < 1){
				alert("선택 항목이 없습니다.");
				return false;
			}
			
			if ( abc.text.allNull($("#procImpsbltRsnText").val()) ){
				
				alert('처리불가 사유를 적어주세요.');
				$("#procImpsbltRsnText").focus();
				return;
			} 
			else{
				if( $("#procImpsbltRsnText").val().length > 100 ){
					
					alert("100자를 넘길 수 없습니다.");
					var text = $("#procImpsbltRsnText").val().substring(0,100);
					$("#procImpsbltRsnText").val( text );
					$("#procImpsbltRsnText").focus();
					return;
				}
			}
			
			if (!confirm('선택하신 항목을 처리불가 처리 하시겠습니까?')) return;
			
			var param = { url : "/claim/refund/update-process-impossible"
					, subparam:FormQueryStringEnc(document.claimForm)
					, sheet:"redemptionSheet" };
			
			DataSave(param);
				
			break;*/
		}
	}
	
	_redemptionmain.redemptionSheetOnSearchEnd = function(){
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = redemptionSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;
		
		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}
		
		for(var i=pageFirstRow; i<=redemptionSheet.GetDataLastRow(); i++){
			// 사이트
			/*
			var siteNo = redemptionSheet.GetRowData(i).siteNo;
			if( siteNo == "10000" ){
				redemptionSheet.SetRowData(i, {clmSiteName : "통합몰"});
			} else if( siteNo == "10001" ){
				redemptionSheet.SetRowData(i, {clmSiteName : "OTS"});
			}*/
			
			// 접수자
			var memberTypeCode = redemptionSheet.GetRowData(i).memberTypeCode;
			if( memberTypeCode == _redemptionmain.MEMBER_TYPE_NONMEMBER ){
				//비회원인 경우
				redemptionSheet.SetRowData(i, {orderMember : redemptionSheet.GetRowData(i).buyerName});
				redemptionSheet.SetCellFontUnderline(i,"orderMember",0);
			} else {
				//회원인 경우
				redemptionSheet.SetRowData(i, {orderMember : redemptionSheet.GetRowData(i).loginId + "(" + redemptionSheet.GetRowData(i).memberName + ")"});
			}
			redemptionSheet.SetRowData(i, {clmAcnt : redemptionSheet.GetRowData(i).acntNoText});

			// 입금대기,입금완료,입금기한만료
			var rgstDtm = redemptionSheet.GetRowData(i).rgstDtm;
			var vrtlAcntExprDtm = redemptionSheet.GetRowData(i).vrtlAcntExprDtm;
			var pymntStatCode = redemptionSheet.GetRowData(i).pymntStatCode;
			var strNowTime = redemptionSheet.GetRowData(i).strNowTime;
			if( !abc.text.allNull(vrtlAcntExprDtm) && pymntStatCode == _redemptionmain.PYMNT_STAT_CODE_REDEMP_ACCEPT ){
				// 가상계좌만료일이 null이 아니고 환수접수(입금대기) 상태일때
				vrtlAcntExprDtm = vrtlAcntExprDtm.substring(0,8);
				// 입금기한이 지났다면
				if( abc.date.compareDate( strNowTime, vrtlAcntExprDtm ) > 0 ){
					redemptionSheet.SetRowData(i, {pymntStatCodeName : "입금기한만료"});
				}
			}
			
			// 임의처리여부
			var redempAmtRndmProcYn = redemptionSheet.GetRowData(i).redempAmtRndmProcYn;
			if( redempAmtRndmProcYn == "N" ){
				redemptionSheet.SetRowData(i, {redempAmtRndmProcYn : "임의처리안함"});
			} else if( redempAmtRndmProcYn == "Y" ){
				redemptionSheet.SetRowData(i, {redempAmtRndmProcYn : "임의처리"});
			}
			
			// 처리자
			var adminId = redemptionSheet.GetRowData(i).adminId;
			if( !abc.text.allNull(adminId) ){
				redemptionSheet.SetRowData(i, {clmHandler : redemptionSheet.GetRowData(i).adminId + "(" + redemptionSheet.GetRowData(i).adminName + ")"});
			}
		}
	}
	
	/**
	 * 검색 조건 Validation
	 */
	_redemptionmain.searchValidation = function(){
		
		// 주문 validate
		var orderSearchKey = $("#orderSelect option:selected").val();
		var orderSearchText = $("#orderInp").val();
		
		if( !abc.text.allNull(orderSearchText) ){
			
			if( abc.text.isLimitLength(orderSearchText, 100) ){
				
				alert("최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#orderInp").focus();
				return false;
			}
			
			// 휴대폰번호 validate
			if(orderSearchKey == "buyerHdphnNoText"){
				
				// 휴대폰번호 validate
				if(orderSearchKey == "buyerHdphnNoText"){
					
					if( abc.text.isLimitLength(orderSearchText, 11) ){
						
						alert("최대 길이(11)를 초과하였습니다."); 
						$("#orderInp").focus();
						return false;
					}
				}
			}
		} 
		
		// 주문번호 validate
		var orgOrderNo = $("input[name='orgOrderNo']").val();
		
		if( !abc.text.allNull( orgOrderNo ) ){
		
			if( abc.text.isLimitLength(orgOrderNo, 13) ){
				
				alert("최대 길이(13)를 초과하였습니다."); 
				$("input[name='orgOrderNo']").focus();
				return false;
			}
			
			// 주문번호로 검색한다면 검색 기간 X
			$(".hasDatepicker").val("");
		}
		
		// 검색 기간 validate
		if( abc.text.allNull($("input[name='orgOrderNo']").val()) && (abc.text.allNull($("#fromDate").val()) || abc.text.allNull($("#toDate").val())) ){
			alert("검색 기간을 입력해주세요.");
			
			if( abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()) ){
				$("#fromDate").focus();
			} else if( abc.text.allNull($("#fromDate").val()) && !abc.text.allNull($("#toDate").val()) ){
				$("#fromDate").focus();
			} else if( !abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()) ){
				$("#toDate").focus();
			}
			
			return false;
		}
		
		if( abc.date.searchValidate() == false ){
			return false;
		}
		
		var dateDiff = abc.date.dateDiffAbs($("#fromDate").val(), $("#toDate").val());
		
		if(dateDiff > 365) {
			
			alert("검색 기간은 1년을 넘을 수 없습니다.");
			$("#fromDate").focus();
			return false;
		}
	}
	
	$("#btnReset").click(function(){
		_redemptionmain.searchFormReset();
	});
	
	/**
	 * 환수 검색 form 초기화
	 */
	_redemptionmain.searchFormReset = function() {
		
		$('#frmSearch').each(function(){
			this.reset();
		});
		
		// 기본값 오늘날짜 포함 한달
		$("a[name^=per1Month]").trigger('click');
	}
	
	/**
	 * 주문 selectBox 선택 후 입력
	 */
	_redemptionmain.orderSelect = function(){
		// 주문 selectBox 선택 시
		$("#orderSelect").change(function(){ 
			
			$("#buyerHdphnBackNo").val(""); // 휴대폰 뒷자리 초기화
			
			// 주문자명로 검색할 때
			if($("#orderSelect option:selected").val() == "buyerName"){
				
				$("#orderInp").attr("name", "searchBuyerName");
				$("#orderInp").attr("placeholder", "");
				$("#buyerHdphnBackNo").show();
				
			}// 주문자 아이디로 검색할 때
			else if($("#orderSelect option:selected").val() == "loginId"){
				
				$("#orderInp").attr("name", "searchLoginId");
				$("#orderInp").attr("placeholder", "");
				$("#buyerHdphnBackNo").hide();
				
			}// 휴대폰번호로 검색할 때
			else if($("#orderSelect option:selected").val() == "buyerHdphnNoText"){
				
				$("#orderInp").attr("name", "searchBuyerHdphnNoText");
				$("#orderInp").attr("placeholder", "- 없이 전체 입력");
				$("#buyerHdphnBackNo").hide();
			}
		});
	}
	
	/**
	 * 기간검색 (환불금액 발생일시/처리일) 선택 시
	 */
	_redemptionmain.dateSelect = function(){
		
		//캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		//캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		//캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		//캘린더 버튼 설정(1년)
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
	}
	
	$("#downloadExcel").click(function(){
		_redemptionmain.downloadExcel();
	});
	
	/**
	 * 선택한 환수 목록 엑설로 다운로드
	 */
	_redemptionmain.downloadExcel = function() {
		
		var sRow = redemptionSheet.FindCheckedRow("downloadCheck");
		
		if (sRow <= 0 ){
			alert( "다운로드 할 항목을 선택해 주세요." );
			return false;
			
		} else {
			// 배열 처리 
			for(var i=1; i <= redemptionSheet.RowCount(); i++){
				
				if(redemptionSheet.GetCellValue(i, "downloadCheck") == "1"){
					
					$("#claimForm").append("<input type='hidden' id='clmNos' name='clmNos.clmNo' value='" + redemptionSheet.GetCellValue(i, "clmNo") + "'>");
				}	
			}

			claimForm.action = "/claim/refund/download-refund-redemp-excel";
			claimForm.submit();
			
			$("input[name='clmNos.clmNo']").remove();
		}
	}
	
	$("#downloadAllExcel").click(function(){
		_redemptionmain.downloadAllExcel();
	});
	
	/**
	 * 한 페이지의 전체 환수 목록 엑설로 다운로드
	 */
	_redemptionmain.downloadAllExcel = function() {
		
		var sortInfo   = redemptionSheet.GetColSortInfo();
		
		if( sortInfo[0] != undefined ){
			var sortColumn = redemptionSheet.ColSaveName(sortInfo[0].Col);
			var sortType   = sortInfo[0].SortOrder;
			$("#sortColumn").val(sortColumn);
			$("#sortType").val(sortType);
		}
		
		//console.log($("#frmSearch").serialize());
		frmSearch.action = "/claim/refund/download-refund-redemp-all-excel";
		frmSearch.submit();
		
		$("#sortColumn").val("");
		$("#sortType").val("");
	}
	
	
	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_redemptionmain.openClaimDetailPop = function(clmNo, clmGbnCode){
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};
		
		if(clmGbnCode == _redemptionmain.CLM_GBN_CODE_CANCEL) { //취소
			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세
		} else if(clmGbnCode == _redemptionmain.CLM_GBN_CODE_EXCHANGE) { //교환
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
		} else if(clmGbnCode == _redemptionmain.CLM_GBN_CODE_RETURN) { //반품
			url = "/claim/claim/read-claim-return-detail-pop"; // 클레임 반품 상세
		}
		
		abc.open.popup({
			winname :	"claimDetailPop",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	}
	
	/*************************************************************************
	 *								팝업 호출 함수 E
	*************************************************************************/
	
})();