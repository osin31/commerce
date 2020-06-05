/***
 * 환불 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _refundmain = abc.object.createNestedObject(window,"abc.biz.claim.refundmain");
	
	/*************************************************************************
	 *								환불 목록/검색 변수 선언
	 *************************************************************************/
	
	//회원 유형 코드    : 비회원 
	_refundmain.MEMBER_TYPE_NONMEMBER	= "";
	//클레임 구분 코드 : 취소
	_refundmain.CLM_GBN_CODE_CANCEL		= "";
	//클레임 구분 코드 : 교환
	_refundmain.CLM_GBN_CODE_EXCHANGE	= "";
	//클레임 구분 코드 : 반품
	_refundmain.CLM_GBN_CODE_RETURN		= "";	
	//결제 상태 코드: 환불완료
	_refundmain.PYMNT_STAT_CODE_REFUND_COMPLETE = "";
	
	_refundmain.SITE_NO_ART		= "";	
	_refundmain.SITE_NO_OTS		= "";	
	
	_refundmain.SYSTEM_ADMIN_NO		= "";	
	
	/*************************************************************************
	 *								환불 목록/검색
	 *************************************************************************/
	
	/**
	 * 환불 목록 초기 세팅
	 */
	_refundmain.initRefundSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("refundGrid"), "refundSheet", "100%", "370px");
		
		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols 		 = [
				  {Header:"상태",				Type:"Status",	SaveName:"Status", 				Width:10,  Hidden:1}
				, {Header:"환불/환수구분",		Type:"Text",	SaveName:"redempRfndGbnType",	Width: 8,  Align:"Center", Edit:0, Sort:0, Hidden:1}
				, {Header:"",				Type:"CheckBox",SaveName:"downloadCheck",		Width: 5,  Align:"Center", Edit:1, Sort:0}
				, {Header:"클레임구분",			Type:"Text",	SaveName:"clmGbnCodeName",		Width: 9,  Align:"Center", Edit:0, Sort:0}
				, {Header:"클레임구분코드",		Type:"Text",	SaveName:"clmGbnCode",			Width: 6,  Align:"Center", Edit:0, Sort:0, Hidden:1}
				, {Header:"사이트",			Type:"Text",	SaveName:"clmSiteName",			Width: 100, Align:"Center", Edit:0, Sort:0}
				, {Header:"발생사유", 			Type:"Text",	SaveName:"ocrncRsnCodeName",    Width: 16, Align:"Center", Edit:0, Sort:0}
				, {Header:"클레임번호", 		Type:"Text",	SaveName:"clmNo",				Width: 12, Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문번호", 			Type:"Text",	SaveName:"orgOrderNo",			Width: 12, Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문자번호",			Type:"Text",	SaveName:"memberNo",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"주문자", 			Type:"Text",	SaveName:"orderMember",			Width: 15, Align:"Center", Edit:0, Sort:0,	FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"결제수단", 			Type:"Text",	SaveName:"pymntMeansCodeStuff",	Width: 15, Align:"Left",   Edit:0, Sort:0}
				, {Header:"환불금액",			Type:"Float",	SaveName:"pymntAmt",			Width: 13, Align:"Right",  Edit:0, Sort:1, Format:"#,##0 원"}
				, {Header:"지급상태코드",		Type:"Text",	SaveName:"pymntStatCode",		Width: 4,  Align:"Center", Edit:0, Sort:0, Hidden:1}
				, {Header:"지급상태",			Type:"Text",	SaveName:"pymntStatCodeName",	Width: 10, Align:"Center", Edit:0, Sort:0}
				, {Header:"처리불가여부",		Type:"Text",	SaveName:"procImpsbltYn",		Width: 8,  Align:"Center", Edit:0, Sort:0}
				, {Header:"처리불가사유",		Type:"Text",	SaveName:"procImpsbltRsnText",	Width: 8,  Align:"Center", Edit:0, Sort:0}
				, {Header:"환불은행명",			Type:"Text",	SaveName:"bankCodeName",		Width: 10, Align:"Center", Edit:0, Sort:0}
				, {Header:"환불계좌",			Type:"Text",	SaveName:"clmAcnt",				Width: 15, Align:"Center", Edit:0, Sort:0}
				, {Header:"예금주",			Type:"Text",	SaveName:"acntHldrName",		Width: 8,  Align:"Center", Edit:0, Sort:0}
				, {Header:"환불금액 발생일시", 	Type:"Date",	SaveName:"rgstDtm",				Width: 13, Align:"Center", Edit:0, Sort:1,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"처리자", 			Type:"Text",	SaveName:"clmHandler",			Width: 13, Align:"Center", Edit:0, Sort:0,	 FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"처리일시", 			Type:"Date",	SaveName:"modDtm",				Width: 13, Align:"Center", Edit:0, Sort:1,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"처리자번호",			Type:"Text",	SaveName:"moderNo",						   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"멤버타입코드",		Type:"Text",	SaveName:"memberTypeCode",				   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"처리자번호",			Type:"Text",	SaveName:"redempRfndOpetrNo",			   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"처리일시", 			Type:"Date",	SaveName:"redempRfndOpetrDtm",	Width: 13, Align:"Center", Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
				, {Header:"로그인ID",			Type:"Text",	SaveName:"loginId",			    		   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"회원명",			Type:"Text",	SaveName:"memberName",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"주문자명",			Type:"Text",	SaveName:"buyerName",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"은행명",			Type:"Text",	SaveName:"bankCodeName",				   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"계좌번호",			Type:"Text",	SaveName:"acntNoText",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"관리자ID",			Type:"Text",	SaveName:"adminId",			   			   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"관리자 명",			Type:"Text",	SaveName:"adminName",					   Align:"Center", Edit:0, Sort:0, 	Hidden:1}
				, {Header:"사이트번호",			Type:"Text",	SaveName:"siteNo",						   Align:"Center", Edit:0, Hidden:1}
				, {Header:"클레임결제순번",		Type:"Text",	SaveName:"clmPymntSeq",					   Align:"Center", Edit:0, Hidden:1}
				, {Header:"발생사유코드",		Type:"Text",	SaveName:"ocrncRsnCode",				   Align:"Center", Edit:0, Hidden:1}
		];

		//Grid 초기화
		IBS_InitSheet(refundSheet, initSheet);
		// Grid 건수 정보 표시
		refundSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		refundSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//refundSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//refundSheet.SetExtendLastCol(1);
		
		refundSheet.SetColWidth("downloadCheck", 40);
		refundSheet.SetColWidth("clmGbnCodeName", 80);
		refundSheet.SetColWidth("clmSiteName", 80);
		refundSheet.SetColWidth("ocrncRsnCodeName", 80);
		refundSheet.SetColWidth("clmNo", 120);
		refundSheet.SetColWidth("orgOrderNo", 120);
		refundSheet.SetColWidth("orderMember", 100);
		refundSheet.SetColWidth("pymntMeansCodeStuff", 200);
		refundSheet.SetColWidth("pymntAmt", 100);
		refundSheet.SetColWidth("pymntStatCodeName", 80);
		refundSheet.SetColWidth("procImpsbltYn", 80);
		refundSheet.SetColWidth("procImpsbltRsnText", 200);
		refundSheet.SetColWidth("bankCodeName", 80);
		refundSheet.SetColWidth("clmAcnt", 100);
		refundSheet.SetColWidth("rgstDtm", 140);
		refundSheet.SetColWidth("acntHldrName", 60);
		refundSheet.SetColWidth("clmHandler", 100);
		refundSheet.SetColWidth("modDtm", 140);

	}
	
	/**
	 * 그리드 Click 이벤트
	 */
	_refundmain.refundSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0) {
			
			if ( refundSheet.ColSaveName(Col) == "orgOrderNo" && Value != "" ) {

				var saveName = "orgOrderNo";
				abc.orderDetailPopup(refundSheet, saveName, Row, Col);
			}
			else if ( refundSheet.ColSaveName(Col) == "clmNo" && Value != ""  ) {
				
				var clmNo = refundSheet.GetRowData(Row).clmNo;
				var clmGbnCode = refundSheet.GetRowData(Row).clmGbnCode;
				// 클레임 상세 팝업 호출
				_refundmain.openClaimDetailPop(clmNo, clmGbnCode);
			}
			else if ( refundSheet.ColSaveName(Col) == "orderMember" && Value != ""  ) {
				
				var memberTypeCode = refundSheet.GetRowData(Row).memberTypeCode;
				
				if(memberTypeCode != _refundmain.MEMBER_TYPE_NONMEMBER){
					// 비회원이 아니면 회원상세 팝업 호출
					abc.memberDetailPopup(refundSheet.GetRowData(Row).memberNo);
				}
			}
			else if ( refundSheet.ColSaveName(Col) == "clmHandler" && Value != ""  ) {

				var moderNo = refundSheet.GetRowData(Row).moderNo;
				
				//if( moderNo != _refundmain.SYSTEM_ADMIN_NO ){
					abc.adminDetailPopup(moderNo);
				//}
			}
		}
	}
	
	/**
	 * 그리드 조회 후 데이터 가공
	 */
	_refundmain.refundSheetOnSearchEnd = function(){
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = refundSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;
		
		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}
		
		for(var i=pageFirstRow; i<=refundSheet.GetDataLastRow(); i++){
			
			var siteNo = refundSheet.GetRowData(i).siteNo;
			
			/*
			if( siteNo == _refundmain.SITE_NO_ART ){
				refundSheet.SetRowData(i, {clmSiteName : "통합몰"});
			} else if( siteNo == _refundmain.SITE_NO_OTS ){
				refundSheet.SetRowData(i, {clmSiteName : "OTS"});
			}*/
			
			var memberTypeCode = refundSheet.GetRowData(i).memberTypeCode;
			
			if( memberTypeCode == _refundmain.MEMBER_TYPE_NONMEMBER ){
				//비회원인 경우
				refundSheet.SetRowData(i, {orderMember : refundSheet.GetRowData(i).buyerName});
				refundSheet.SetCellFontUnderline(i,"orderMember",0);
			} else {
				//회원인 경우
				refundSheet.SetRowData(i, {orderMember : refundSheet.GetRowData(i).loginId + "(" + refundSheet.GetRowData(i).memberName + ")"});
			}
			
			refundSheet.SetRowData(i, {clmAcnt : refundSheet.GetRowData(i).acntNoText});
			
			var adminId = refundSheet.GetRowData(i).adminId;
			var moderNo = refundSheet.GetRowData(i).moderNo;
			
			if( !abc.text.allNull(adminId) ){
				/*if( moderNo == _refundmain.SYSTEM_ADMIN_NO ){
					//refundSheet.SetCellFontUnderline(i,18,0);
					refundSheet.SetRowData(i, {clmHandler : refundSheet.GetRowData(i).adminName});
				} else{
					refundSheet.SetRowData(i, {clmHandler : refundSheet.GetRowData(i).adminId + "(" + refundSheet.GetRowData(i).adminName + ")"});
				}*/
				refundSheet.SetRowData(i, {clmHandler : refundSheet.GetRowData(i).adminId + "(" + refundSheet.GetRowData(i).adminName + ")"});
			}
		}
	}
	
	_refundmain.refundSheetOnSaveEnd = function(Code, Msg, StCode, StMsg, Response){
		
		if(StCode == "200")
			alert("변경된 값으로 저장하였습니다.");
		else
			alert("에러가 발생하였습니다.");
		
		_refundmain.refundDoAction("search");
	}
	
	$("#btnSearch").click(function(){
		_refundmain.refundDoAction("search");
	});
	
	$("#btnProcImpsblt").click(function(){
		_refundmain.refundDoAction("update");
	});
	
	/**
	 * 환불 목록 Action 관리
	 */
	_refundmain.refundDoAction = function(sAction){
		switch(sAction) {
		
		case "search" :
			
			if( _refundmain.searchValidation() == false ){ return; }
			
			var pageCount = $('#pageCount').val();
			//console.log("검색 : " + FormQueryStringEnc(document.frmSearch));
			var param = { url : "/claim/refund/read-refund-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "refundSheet" };
			
			DataSearchPaging(param);
			
			break;
			
		case "update" :
			var checkCnt = refundSheet.FindCheckedRow("downloadCheck");
			
			if(checkCnt < 1){
				alert("선택 항목이 없습니다.");
				return false;
			}
			
			var url = "";

			if($("#selProcImpsblt option:selected").val() == ''){

				alert("처리불가와 처리완료 중 선택해주세요.");
				return;
			}else if($("#selProcImpsblt option:selected").val() == 'Y'){
				
				for(var i=1; i <= refundSheet.RowCount(); i++){
					if(refundSheet.GetCellValue(i, "downloadCheck") == "1"){
						if( !abc.text.allNull(refundSheet.GetCellValue(i, "procImpsbltYn")) 
								&& refundSheet.GetCellValue(i, "procImpsbltYn") ==  "Y"){
							// 처리불가여부  "Y" 일때
							alert("이미 처리불가된 클레임(" + refundSheet.GetCellValue(i, "clmNo") + ")입니다.");
							return false;
						}
						if( !abc.text.allNull(refundSheet.GetCellValue(i, "pymntStatCode"))
								&& refundSheet.GetCellValue(i, "pymntStatCode") ==  _refundmain.PYMNT_STAT_CODE_REFUND_COMPLETE){
							// 결제상태코드가 "환불완료" 일때
							alert("이미 입금완료(환불완료)된 클레임(" + refundSheet.GetCellValue(i, "clmNo") + ")입니다.");
							return false;
						}
					}
				}
				
				if ( abc.text.allNull($("#procImpsbltRsnText").val()) ){
					
					alert('사유를 적어주세요.');
					$("#procImpsbltRsnText").focus();
					return;
				}
				else{
					if( $("#procImpsbltRsnText").val().length > 20 ){
						
						alert("처리불가사유는 20자를 넘길 수 없습니다.");
						var text = $("#procImpsbltRsnText").val().substring(0,20);
						$("#procImpsbltRsnText").val( text );
						$("#procImpsbltRsnText").focus();
						return;
					}
				}
				
				if (!confirm('선택하신 항목을 처리불가 처리 하시겠습니까?')) return;
				url = "/claim/refund/update-process-impossible";

			}else if($("#selProcImpsblt option:selected").val() == 'N'){
				
				for(var i=1; i <= refundSheet.RowCount(); i++){
					if(refundSheet.GetCellValue(i, "downloadCheck") == "1"){
						if( !abc.text.allNull(refundSheet.GetCellValue(i, "pymntStatCode")) 
								&& refundSheet.GetCellValue(i, "pymntStatCode") ==  _refundmain.PYMNT_STAT_CODE_REFUND_COMPLETE ){
							// 결제상태코드가 "환불완료" 일때
							alert("이미 입금완료(환불완료)된 클레임(" + refundSheet.GetCellValue(i, "clmNo") + ")입니다.");
							return false;
						}
						/*if( !abc.text.allNull(refundSheet.GetCellValue(i, "procImpsbltYn")) ){
							if( refundSheet.GetCellValue(i, "procImpsbltYn") ==  "Y" ){
								alert("이미 처리불가된 클레임(" + refundSheet.GetCellValue(i, "clmNo") + ")입니다.");
								return false;
							}
						}*/
						if( abc.text.allNull(refundSheet.GetCellValue(i, "clmAcnt")) ){
							// 해당 클레임에 환불계좌가 존재하지 않을때
							alert("클레임(" + refundSheet.GetCellValue(i, "clmNo") + ")에 환불계좌가 존재하지 않습니다.");
							return false;
						}
					}
				}
				
				if (!confirm('선택하신 항목을 입금확인 완료 처리 하시겠습니까?')) return;
				url = "/claim/refund/update-process-complete";
			}
			
			/**
			 * 처리불가/처리완료에 넘겨주는 값
			 * clmNo : 클레임번호
			 * clmGbnCode : 클레임구분코드
			 * clmPymntSeq : 클레임결제순번
			 * ocrncRsnCode : 발생사유코드
			 * procImpsbltRsnText : 처리불가사유
			 */
			for(var i=1; i <= refundSheet.RowCount(); i++){
				if(refundSheet.GetCellValue(i, "downloadCheck") == "1"){
					$("#updateForm").append("<input type='hidden' name='clmNo' value='" + refundSheet.GetCellValue(i, "clmNo") + "'>");
					$("#updateForm").append("<input type='hidden' name='clmGbnCode' value='" + refundSheet.GetCellValue(i, "clmGbnCode") + "'>");
					$("#updateForm").append("<input type='hidden' name='clmPymntSeq' value='" + refundSheet.GetCellValue(i, "clmPymntSeq") + "'>");
					$("#updateForm").append("<input type='hidden' name='ocrncRsnCode' value='" + refundSheet.GetCellValue(i, "ocrncRsnCode") + "'>");
					$("#updateForm").append("<input type='hidden' name='procImpsbltRsnText' value='" + $("#procImpsbltRsnText").val() + "'>");
				}	
			}
			
			$.ajax({
				type :"post",
				url  : url,
				data : $("#updateForm").serialize(),
			}).done(function(data){
				
				alert("변경된 값으로 저장하였습니다.");
				_refundmain.refundDoAction("search");
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다.");
				//console.log(jqXHR.responseJSON);
			});
			
			$("#updateForm").empty();
			
			break;
		}
	}
	
	/**
	 * 검색 조건 Validation
	 */
	_refundmain.searchValidation = function(){
		
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
		_refundmain.searchFormReset();
	});
	
	/**
	 * 환불 검색 form 초기화
	 */
	_refundmain.searchFormReset = function() {
		
		$('#frmSearch').each(function(){
			this.reset();
		});
		
		// 기본값 오늘날짜 포함 한달
		$("a[name^=per1Month]").trigger('click');
	}
	
	/**
	 * 주문 selectBox 선택 후 입력
	 */
	_refundmain.orderSelect = function(){
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
	_refundmain.dateSelect = function(){
		
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
		_refundmain.downloadExcel();
	});
	
	/**
	 * 선택한 환불 목록 엑설로 다운로드
	 */
	_refundmain.downloadExcel = function() {
		
		var sRow = refundSheet.FindCheckedRow("downloadCheck");
		
		if (sRow <= 0 ){
			alert( "다운로드 할 항목을 선택해 주세요." );
			return false;
			
		} else {
			// 배열 처리 
			for(var i=1; i <= refundSheet.RowCount(); i++){
				
				if(refundSheet.GetCellValue(i, "downloadCheck") == "1"){
					
					$("#claimForm").append("<input type='hidden' id='clmNos' name='clmNos.clmNo' value='" + refundSheet.GetCellValue(i, "clmNo") + "'>");
				}	
			}

			claimForm.action = "/claim/refund/download-refund-redemp-excel";
			claimForm.submit();
			
			$("input[name='clmNos.clmNo']").remove();
		}
	}
	
	$("#downloadAllExcel").click(function(){
		_refundmain.downloadAllExcel();
	});
	
	/**
	 * 한 페이지의 전체 환불 목록 엑설로 다운로드
	 */
	_refundmain.downloadAllExcel = function() {
		
		var sortInfo   = refundSheet.GetColSortInfo();
		
		if( sortInfo[0] != undefined ){
			var sortColumn = refundSheet.ColSaveName(sortInfo[0].Col);
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
	
	/**
	 * 처리불가/처리완료 선택 시
	 */
	_refundmain.procImpsblt = function(){
		
		$("#selProcImpsblt").change(function(){
			
			if($("#selProcImpsblt option:selected").val() == ''){
				$("#procImpsbltRsnText").hide();
				$("#procImpsbltRsnText").val("");
			}
			else if($("#selProcImpsblt option:selected").val() == 'Y'){
				$("#procImpsbltRsnText").show();
				$("#procImpsbltRsnText").val("");
			}
			else if($("#selProcImpsblt option:selected").val() == 'N'){
				$("#procImpsbltRsnText").hide();
				$("#procImpsbltRsnText").val("");
			}
		});
	}
	
	
	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_refundmain.openClaimDetailPop = function(clmNo, clmGbnCode){
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};

		if(clmGbnCode == _refundmain.CLM_GBN_CODE_CANCEL) { //취소
			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세
		} else if(clmGbnCode == _refundmain.CLM_GBN_CODE_EXCHANGE) { //교환
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
		} else if(clmGbnCode == _refundmain.CLM_GBN_CODE_RETURN) { //반품
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