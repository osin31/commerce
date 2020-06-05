/***
 * 클레임 목록/검색 과 관련된 함수 정의.
 */
(function() {

	var _claimmain = abc.object.createNestedObject(window,"abc.biz.claim.claimmain");

	/*************************************************************************
	 *								클레임 목록/검색 변수 선언
	 *************************************************************************/

	_claimmain.SITE_NO_ART	= "";
	_claimmain.SITE_NO_OTS	= "";

	_claimmain.ABC_FO_URL	= "";
	_claimmain.OTS_FO_URL	= "";

	//회원 유형 코드    : 비회원
	_claimmain.MEMBER_TYPE_NONMEMBER	= "";

	//클레임 구분 코드 : 취소
	_claimmain.CLM_GBN_CODE_CANCEL		= "";
	//클레임 구분 코드 : 교환
	_claimmain.CLM_GBN_CODE_EXCHANGE	= "";
	//클레임 구분 코드 : 반품
	_claimmain.CLM_GBN_CODE_RETURN		= "";

	//결제 수단 코드 : 무통장입금(가상계좌)
	_claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT		= "";
	//결제 수단 코드 : 실시간계좌이체
	_claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER	= "";

	// get 파라미터
	_claimmain.getClmGbnCode = "";
	_claimmain.getMmnyPrdtYn = "";

	/*************************************************************************
	 *								클레임 목록/검색
	 *************************************************************************/
	
	
	/**
	 * 클레임 목록 초기 세팅
	 */
	_claimmain.initClaimSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("claimGrid"), "claimSheet", "100%");

		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, SizeMode:0, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols 		 = [
			  {Header:"",				Type:"CheckBox",SaveName:"downloadCheck",		Width:3,	Align:"Center", Edit:1, Sort:0}
			, {Header:"접수처",			Type:"Text",	SaveName:"oflnAcceptYn",		Width:6,	Align:"Center", Edit:0, Sort:0}
			, {Header:"사이트",			Type:"Text",	SaveName:"clmSiteName",			Width:7,	Align:"Center", Edit:0, Sort:0}
			, {Header:"클레임구분",			Type:"Text",	SaveName:"clmGbnCodeName",		Width:5,	Align:"Center", Edit:0, Sort:0}
			, {Header:"클레임상태",			Type:"Text",	SaveName:"clmStatCodeName",		Width:7,	Align:"Center", Edit:0, Sort:0}
			, {Header:"클레임번호", 		Type:"Text",	SaveName:"clmNo",				Width:8,	Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문번호", 			Type:"Text",	SaveName:"orgOrderNo",			Width:8,	Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"상품명",			Type:"Text",	SaveName:"clmPrdtName",			Width:20,	Align:"Left",   Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"접수자구분",			Type:"Text",	SaveName:"adminAcceptYn",		Width:10,	Align:"Center", Edit:0, Sort:0,		Hidden:1}
			, {Header:"회원타입값",			Type:"Text",	SaveName:"memberTypeValue",		Width:10,	Align:"Center", Edit:0, Sort:0,		Hidden:1}
			, {Header:"등록자번호",			Type:"Text",	SaveName:"rgsterNo",			Width:10,	Align:"Center", Edit:0, Sort:0,		Hidden:1}
			, {Header:"접수자", 			Type:"Text",	SaveName:"clmApplicant",		Width:10,	Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer", Hidden:1}
			, {Header:"접수일시", 			Type:"Date",	SaveName:"rgstDtm",				Width:10,	Align:"Center", Edit:0, Sort:1,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"취소(환불)금액",		Type:"Float",	SaveName:"totalRfndAmt",		Width:8,	Align:"Right",  Edit:0, Sort:1, 	Format:"#,##0 원"}
			, {Header:"배송비결제방법",		Type:"Text",	SaveName:"addDlvyAmtPymntType", Width:6,	Align:"Center", Edit:0, Sort:0}
			, {Header:"배송비",			Type:"Float",	SaveName:"addDlvyAmt",			Width:8,	Align:"Right",  Edit:0, Sort:1, 	Format:"#,##0 원"}
			, {Header:"미처리여부",			Type:"Text",	SaveName:"unProcYn",			Width:5,	Align:"Center", Edit:0, Sort:0}
			, {Header:"처리자", 			Type:"Text",	SaveName:"clmHandler",			Width:10,	Align:"Center", Edit:0, Sort:0,		FontBold:1, FontUnderline:1, Cursor:"Pointer", Hidden:1}
			, {Header:"처리일시", 			Type:"Date",	SaveName:"modDtm",				Width:10,	Align:"Center", Edit:0, Sort:1,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"클레임구분코드",		Type:"Text",	SaveName:"clmGbnCode",			Width:10,	Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"처리자번호",			Type:"Text",	SaveName:"moderNo",				Width:10,	Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"멤버타입코드",		Type:"Text",	SaveName:"memberTypeCode",		Width:10,	Align:"Center", Edit:0, Sort:0, 	Hidden:1}
			, {Header:"상품외건갯수",		Type:"Text",	SaveName:"clmPrdtExtraCnt",		Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"회원ID",			Type:"Text",	SaveName:"loginId",				Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"회원명",			Type:"Text",	SaveName:"memberName",			Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"접수자 관리자ID",		Type:"Text",	SaveName:"rgsterAdminId",		Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"접수자 관리자명",		Type:"Text",	SaveName:"rgsterAdminName",		Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"처리자 관리자ID",		Type:"Text",	SaveName:"moderAdminId",		Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"처리자 관리자명",		Type:"Text",	SaveName:"moderAdminName",		Width:10,	Align:"Left",   Edit:0, Hidden:1}
			, {Header:"사이트번호",			Type:"Text",	SaveName:"siteNo",				Width:10,	Align:"Center", Edit:0, Hidden:1}
			, {Header:"상품번호",			Type:"Text",	SaveName:"prdtNo",				Width:10,	Align:"Center", Edit:0, Hidden:1}
		];

		//Grid 초기화
		IBS_InitSheet(claimSheet, initSheet);
		// Grid 건수 정보 표시
		claimSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		claimSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		claimSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		claimSheet.SetExtendLastCol(1);
	}

	/**
	 * 그리드 Click 이벤트
	 */
	_claimmain.claimSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){

		if ( Row != 0 ) {

			if ( claimSheet.ColSaveName(Col) == "clmNo" && Value != "" ) {

				var clmNo = claimSheet.GetRowData(Row).clmNo;
				var clmGbnCode = claimSheet.GetRowData(Row).clmGbnCode;

				_claimmain.openClaimDetailPop(clmNo, clmGbnCode); // 클레임 상세 팝업

			}else if ( claimSheet.ColSaveName(Col) == "orgOrderNo" && Value != "" ) {

				var saveName = "orgOrderNo"; // 원주문번호로 주문상세 팝업 호출
				abc.orderDetailPopup(claimSheet, saveName, Row, Col);

			}else if ( claimSheet.ColSaveName(Col) == "clmPrdtName" && Value != "" ) {

				var prdtNo = claimSheet.GetRowData(Row).prdtNo;
				var siteNo = claimSheet.GetRowData(Row).siteNo;

				if(siteNo == _claimmain.SITE_NO_ART){
					window.open(_claimmain.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(_claimmain.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}

			}else if ( claimSheet.ColSaveName(Col) == "clmApplicant" && Value != "" ) {

				var adminAcceptYn = claimSheet.GetRowData(Row).adminAcceptYn;

				if(adminAcceptYn == 'N'){
					// 관리자가 접수여부 : N -> 회원이 접수
					var memberTypeCode = claimSheet.GetRowData(Row).memberTypeCode;

					if(memberTypeCode != _claimmain.MEMBER_TYPE_NONMEMBER){
						// 비회원이 아니면 회원상세 팝업 호출
						abc.memberDetailPopup(claimSheet.GetRowData(Row).rgsterNo);
					}

				}else if(adminAcceptYn == 'Y'){
					// 관리자가 접수여부 : Y -> 관리자가 접수
					var rgsterNo = claimSheet.GetRowData(Row).rgsterNo;
					abc.adminDetailPopup(rgsterNo);
				}
			}else if ( claimSheet.ColSaveName(Col) == "clmHandler" && Value != "" ) {

				var moderNo = claimSheet.GetRowData(Row).moderNo;
				abc.adminDetailPopup(moderNo);
			}
		}
	}

	_claimmain.claimSheetOnSaveEnd = function(Code, Msg, StCode, StMsg, Response){

		if(StCode == "200"){
			alert("변경된 값으로 저장하였습니다.");
		}else{
			alert("에러가 발생하였습니다.");
		}
		_claimmain.claimDoAction("search");
	}

	$("#btnSearch").click(function(){
		_claimmain.claimDoAction('search');
	});
	
	/**
	 * 클레임 목록 Action 관리
	 */
	_claimmain.claimDoAction = function(sAction){
		switch(sAction) {

		case "search" :

			if( _claimmain.searchValidation() == false ){ return; }
			//console.log(FormQueryStringEnc(document.frmSearch));
			var pageCount = $('#pageCount').val();

			var param = { url : "/claim/claim/read-claim-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "claimSheet" };

			DataSearchPaging(param);

			break;

		}
	}

	/**
	 * DB에서 가져온 데이터 가공
	 */
	_claimmain.claimSheetOnSearchEnd = function() {
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = claimSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;

		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}

		for(var i=pageFirstRow; i<=claimSheet.GetDataLastRow(); i++){
			// 사이트
			/*
			var siteNo = claimSheet.GetRowData(i).siteNo;

			if( siteNo == "10000" ){
				claimSheet.SetRowData(i, {clmSiteName : "통합몰"});
			} else if( siteNo == "10001" ){
				claimSheet.SetRowData(i, {clmSiteName : "OTS"});
			}*/

			// N -> 온라인 / Y -> 오프라인
			var oflnAcceptYn = claimSheet.GetRowData(i).oflnAcceptYn;

			if( oflnAcceptYn == "N" ){
				claimSheet.SetRowData(i, {oflnAcceptYn : "온라인"});
			} else if( oflnAcceptYn == "Y" ){
				claimSheet.SetRowData(i, {oflnAcceptYn : "오프라인"});
			}

			// 상품명 + 외 ~건 표시
			var clmPrdtName = claimSheet.GetRowData(i).clmPrdtName;
			var clmPrdtExtraCnt = claimSheet.GetRowData(i).clmPrdtExtraCnt;

			if( Number(clmPrdtExtraCnt) > 0 ){
				clmPrdtName = clmPrdtName + " 외 " + clmPrdtExtraCnt + "건";
			}

			claimSheet.SetRowData(i, {clmPrdtName : clmPrdtName});

			// 접수자
			/*var adminAcceptYn = claimSheet.GetRowData(i).adminAcceptYn;

			if( adminAcceptYn == "N" ){
				// 접수자가 관리자가 아닌 경우
				var memberTypeCode = claimSheet.GetRowData(i).memberTypeCode;

				if( memberTypeCode == _claimmain.MEMBER_TYPE_NONMEMBER ){
					//비회원인 경우
					claimSheet.SetRowData(i, {clmApplicant : claimSheet.GetRowData(i).memberName});
					claimSheet.SetCellFontUnderline(i,11,0);
				} else {
					//회원인 경우
					claimSheet.SetRowData(i, {clmApplicant : claimSheet.GetRowData(i).loginId + "(" + claimSheet.GetRowData(i).memberName + ")"});
				}
			} else{
				// 접수자가 관리자인 경우
				claimSheet.SetRowData(i, {clmApplicant : claimSheet.GetRowData(i).rgsterAdminId + "(" + claimSheet.GetRowData(i).rgsterAdminName + ")"});
			}*/

			// 추가배송비결제방법
			var addDlvyAmtPymntType = claimSheet.GetRowData(i).addDlvyAmtPymntType;

			if( addDlvyAmtPymntType == "S"){
				// 환불금액 차감
				claimSheet.SetRowData(i, {addDlvyAmtPymntType : "환불금액차감"});

			} else if( addDlvyAmtPymntType == "P"){
				// 선 결제
				claimSheet.SetRowData(i, {addDlvyAmtPymntType : "선 결제"});

			} else if ( addDlvyAmtPymntType == "C"){
				// 무료쿠폰사용
				claimSheet.SetRowData(i, {addDlvyAmtPymntType : "무료쿠폰사용"});

			} else if ( addDlvyAmtPymntType == "F"){
				// 배송비 무료
				claimSheet.SetRowData(i, {addDlvyAmtPymntType : "배송비 무료"});
			}

			// 미처리여부
			var unProcYn = claimSheet.GetRowData(i).unProcYn;

			if( unProcYn == "N"){
				// 처리
				claimSheet.SetRowData(i, {unProcYn : "처리"});

			} else if( unProcYn == "Y"){
				// 미처리
				claimSheet.SetRowData(i, {unProcYn : "미처리"});
			}

			// 처리자
			/*var moderAdminId = claimSheet.GetRowData(i).moderAdminId;

			if( !abc.text.allNull(moderAdminId) ){
				claimSheet.SetRowData(i, {clmHandler : claimSheet.GetRowData(i).moderAdminId + "(" + claimSheet.GetRowData(i).moderAdminName + ")"});
			}*/
		}
	}

	/**
	 * 데쉬보드 링크를 통해 클레임관리 접속
	 */
	_claimmain.fromDashBoard = function(){

		if( !abc.text.allNull(_claimmain.getMmnyPrdtYn) ){
			$("input[name='mmnyPrdtYn']").val(_claimmain.getMmnyPrdtYn);
		}
		if( !abc.text.allNull(_claimmain.getClmGbnCode) ){

			$("select[name='dateGbnType']").val("C");

			if( _claimmain.getClmGbnCode == _claimmain.CLM_GBN_CODE_EXCHANGE ){

				$("#clmPrcsStat").val("E");

				$("input[custum='clmStatCodeE']").prop("checked",true);
				$("input[custum='clmStatCodeR']").prop("checked",false);
				$("input[custum='clmStatCodeC']").prop("checked",false);

				$("#checkExchange").show();
				$("#checkReturn").hide();
				$("#checkCancel").hide();

			} else if( _claimmain.getClmGbnCode == _claimmain.CLM_GBN_CODE_RETURN ){

				$("#clmPrcsStat").val("R");

				$("input[custum='clmStatCodeE']").prop("checked",false);
				$("input[custum='clmStatCodeR']").prop("checked",true);
				$("input[custum='clmStatCodeC']").prop("checked",false);

				$("#checkExchange").hide();
				$("#checkReturn").show();
				$("#checkCancel").hide();
			}

			_claimmain.claimDoAction('search');
		}
	}

	/**
	 * 검색 조건 Validation
	 */
	_claimmain.searchValidation = function(){

		// 클레임·주문번호  validate
		var clmOrderNo = $("#clmOrderNoInp").val();

		if( !abc.text.allNull(clmOrderNo) ){

			// 주문번호/클레임번호로 검색한다면 검색 기간 X
			$(".hasDatepicker").val("");
			// 주문번호/클레임번호로 검색한다면 클레임 유형 구분 X
			$("input[custum='clmStatCodeE']").prop("checked",false);
			$("input[custum='clmStatCodeR']").prop("checked",false);
			$("input[custum='clmStatCodeC']").prop("checked",false);
			$("#checkExchange").hide();
			$("#checkReturn").hide();
			$("#checkCancel").hide();
			
			if( isNaN(clmOrderNo) ){

				alert("클레임·주문번호는 숫자만 입력 가능 합니다.");
				$("#clmOrderNoInp").val("");
				$("#clmOrderNoInp").focus();
				return false;
			}
			if( abc.text.isLimitLength(clmOrderNo, 13) ){

				alert("최대 길이(13)를 초과하였습니다.");
				$("#clmOrderNoInp").focus();
				return false;
			}
		}

		// 상품 validate
		var productSearchKey = $("#prdtInfoSelect option:selected").val();
		var productSearchText = $("#prdtInfoInp").val();

		if( !abc.text.allNull(productSearchText) ){

			if( abc.text.isLimitLength(productSearchText, 100) ){

				alert("최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#prdtInfoInp").focus();
				return false;
			}
		}

		// 주문  validate
		var orderSearchKey = $("#orderMemberInfoSelect option:selected").val();
		var orderSearchText = $("#orderMemberInfoInp").val();

		if( !abc.text.allNull(orderSearchText) ){

			if( abc.text.isLimitLength(orderSearchText, 100) ){

				alert("최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#orderMemberInfoInp").focus();
				return false;
			}

			// 휴대폰번호 validate
			if(orderSearchKey == "buyerHdphnNoText"){

				if( abc.text.isLimitLength(orderSearchText, 11) ){

					alert("최대 길이(11)를 초과하였습니다.");
					$("#orderMemberInfoInp").focus();
					return false;
				}
			}
		}

		// 입점사 validate
		var vndrName =  $("#vndrName").val();

		if( !abc.text.allNull(vndrName) ){
			if( abc.text.isLimitLength(vndrName, 100) ){
				alert("최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#vndrName").focus();
				return false;
			}
		}

		// 브랜드 validate
		var brandName =  $("#brandName").val();

		if( !abc.text.allNull(brandName) ){
			if( abc.text.isLimitLength(brandName, 100) ){
				alert("최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#brandName").focus();
				return false;
			}
		}

		// 검색 기간 validate
		if( abc.text.allNull($("#clmOrderNoInp").val()) && (abc.text.allNull($("#fromDate").val()) || abc.text.allNull($("#toDate").val())) ){
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

		return true;
	}

	$("#btnReset").click(function(){
		_claimmain.searchFormReset();
	});
	
	/**
	 * 클레임 검색 form 초기화
	 */
	_claimmain.searchFormReset = function() {

		$('#frmSearch').each(function(){
			this.reset();
		});

		$("#checkExchange").hide();
		$("#checkReturn").hide();
		$("#checkCancel").hide();
		$("input[custum2='cmmClmStatCode']").prop("checked",false);

		$("#vndrName").val("");
		$("#vndrNo").val("");

		$("#brandName").val("");
		$("#brandNo").val("");

		$("input[custom=pymntMeansCode]").prop("checked",true);
		$("#chkPaymentESCR").prop("checked",true);
		$("#chkPaymentESCR").parent().show();

		$("#chkPaymentNoESCR").prop("checked",true);
		$("#chkPaymentNoESCR").parent().show();

		$("#leftESCR").show();
		$("#rightESCR").show();

		// 기본값 오늘날짜 포함 한달
		$("a[name^=per1Month]").trigger('click');
		
		$("#orderMemberInfoInp").attr("name", "searchBuyerName");
		_claimmain.ordMbInfoSelectChange("buyerName");
	}

	/**
	 * 발송처 checkBox 체크
	 */
	_claimmain.sendPlaceCheck = function(){
		/** 발송처 변경 함수 */
		$("#stockGbnCodeAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=stockGbnCode]").prop("checked",true);
			} else {
				$("input[custom=stockGbnCode]").prop("checked",false);
			}
		});

		$("input[custom=stockGbnCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=stockGbnCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#stockGbnCodeAll").prop("checked",true);
					}
				} else {
					$("#stockGbnCodeAll").prop("checked",false);
				}
			})
		})
	}

	/**
	 * 입점사 선택
	 */
	_claimmain.vendorSelect = function(){
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			var options = { callback : "setSelectedVendors", vndrName : $('[name=vndrName]').val() };
			abc.vndrSearchPopup(false, options);
		});
	}

	/**
	 * 클레임/주문번호 선택 후, 입력 input
	 */
	_claimmain.claimOrderNoInput = function(){

		// 클레임/주문번호 selectBox 선택 시
		$("#claimOrderNoSelect").change(function(){

			// 클레임번호로 검색할 때
			if($("#claimOrderNoSelect option:selected").val() == "clmNo"){

				$("#clmOrderNoInp").attr("name", "clmNo");

			} // 주문번호로 검색할 때
			else if($("#claimOrderNoSelect option:selected").val() == "orgOrderNo") {

				$("#clmOrderNoInp").attr("name", "orgOrderNo");
			}
		});
	}

	/**
	 * 주문 selectBox 선택 후, 입력 input
	 */
	_claimmain.orderMemberInfoSelect = function(){

		// 주문 selectBox 선택 시
		$("#orderMemberInfoSelect").change(function(){

			// 주문자명으로 검색할 때
			if($("#orderMemberInfoSelect option:selected").val() == "buyerName"){

				$("#orderMemberInfoInp").attr("name", "searchBuyerName");
				$("#orderMemberInfoInp").attr("placeholder", "");

			} // 수령자로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "rcvrName") {

				$("#orderMemberInfoInp").attr("name", "searchRcvrName");
				$("#orderMemberInfoInp").attr("placeholder", "");

			} // 휴대폰 번호로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "buyerHdphnNoText") {

				$("#orderMemberInfoInp").attr("name", "searchBuyerHdphnNoText");
				$("#orderMemberInfoInp").attr("placeholder", "- 없이 전체 입력");

			} // 주문자ID로 검색할 때
			else if($("#orderMemberInfoSelect option:selected").val() == "loginId") {

				$("#orderMemberInfoInp").attr("name", "searchLoginId");
				$("#orderMemberInfoInp").attr("placeholder", "");
			}
		});
	}

	/**
	 * 상품 selectBox 선택 후, 입력 input
	 */
	_claimmain.prdtInfoSelect = function(){

		// 상품 selectBox 선택 시
		$("#prdtInfoSelect").change(function(){

			// 상품코드으로 검색할 때
			if($("#prdtInfoSelect option:selected").val() == "prdtNo"){

				$("#prdtInfoInp").attr("name", "prdtNo");

			} // 상품명로 검색할 때
			else if($("#prdtInfoSelect option:selected").val() == "prdtName") {

				$("#prdtInfoInp").attr("name", "prdtName");

			} // 스타일코드로 검색할 때
			else if($("#prdtInfoSelect option:selected").val() == "styleInfo") {

				$("#prdtInfoInp").attr("name", "styleInfo");

			} // 업체상품코드로 검색할 때 - (?)
			else if($("#prdtInfoSelect option:selected").val() == "vndrPrdtNoText") {

				$("#prdtInfoInp").attr("name", "vndrPrdtNoText");
			}
		});
	}

	/**
	 * 브랜드 selectBox 선택 시
	 */
	_claimmain.brandSelect = function(){

		$("#btnBrand").click(function(){
			var options = { callback : "setSelectedBrand", brandName : $('#brandName').val() };
			abc.brandSearchPopup(false, options);
		});
	}

	/**
	 * 기간검색 (주문일/접수일시) 선택 시
	 */
	_claimmain.dateSelect = function(){

		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});

		//캘린더 버튼 설정(1년)
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
	}

	/**
	 * 클레임별 진행 상태 selectBox 선택 시
	 */
	_claimmain.claimSelect = function(){

		$("#clmPrcsStat").change(function(){

			var claimProcessState = $("#clmPrcsStat option:selected").val();

			// 전체
			if(claimProcessState == ""){

				$("input[custum='clmStatCodeE']").prop("checked",false);
				$("input[custum='clmStatCodeR']").prop("checked",false);
				$("input[custum='clmStatCodeC']").prop("checked",false);

				$("#checkExchange").hide();
				$("#checkReturn").hide();
				$("#checkCancel").hide();

			// 교환
			}else if(claimProcessState == "E"){

				$("input[custum='clmStatCodeE']").prop("checked",true);
				$("input[custum='clmStatCodeR']").prop("checked",false);
				$("input[custum='clmStatCodeC']").prop("checked",false);

				$("#checkExchange").show();
				$("#checkReturn").hide();
				$("#checkCancel").hide();

			// 반품
			}else if(claimProcessState == "R"){

				$("input[custum='clmStatCodeE']").prop("checked",false);
				$("input[custum='clmStatCodeR']").prop("checked",true);
				$("input[custum='clmStatCodeC']").prop("checked",false);

				$("#checkExchange").hide();
				$("#checkReturn").show();
				$("#checkCancel").hide();

			// 취소
			}else if(claimProcessState == "C"){

				$("input[custum='clmStatCodeE']").prop("checked",false);
				$("input[custum='clmStatCodeR']").prop("checked",false);
				$("input[custum='clmStatCodeC']").prop("checked",true);

				$("#checkExchange").hide();
				$("#checkReturn").hide();
				$("#checkCancel").show();
			}
		});

		// 교환 - 전체 check
		$("#E_ClmAll").click(function(){

			if($(this).is(":checked")){
				$("input[custum='clmStatCodeE']").prop("checked",true);

			} else {
				$("input[custum='clmStatCodeE']").prop("checked",false);
			}
		});

		// 반품 - 전체 check
		$("#R_ClmAll").click(function(){

			if($(this).is(":checked")){
				$("input[custum='clmStatCodeR']").prop("checked",true);

			} else {
				$("input[custum='clmStatCodeR']").prop("checked",false);
			}
		});

		// 취소 - 전체 check
		$("#C_ClmAll").click(function(){
			if($(this).is(":checked")){
				$("input[custum='clmStatCodeC']").prop("checked",true);

			} else {
				$("input[custum='clmStatCodeC']").prop("checked",false);
			}
		});
	}

	/**
	 * 결제수단 (환불) checkBox 선택 시
	 */
	_claimmain.pymntMeansCodeCheck = function(){

		/** 결제수단 변경 함수 */
		$("#chkPaymentAll").click(function(){

			if($(this).is(":checked")){
				// 결제수단 "전체"가 체크될 경우 -> 모두 체크
				$("input[custom=pymntMeansCode]").prop("checked",true);
				$("#chkPaymentESCR").prop("checked",true);
				$("#chkPaymentNoESCR").prop("checked",true);
			} else {
				// 결제수단 "전체"가 체크 풀릴 경우 -> 모두 체크 품
				$("input[custom=pymntMeansCode]").prop("checked",false);
				$("#chkPaymentESCR").prop("checked",false);
				$("#chkPaymentNoESCR").prop("checked",false);
			}
		});

		$("input[custom=pymntMeansCode]").each(function(){

			$(this).click(function(){

				if($(this).is(":checked")){

					var unChecked = $("input[custom=pymntMeansCode]:not(:checked)");

					if (unChecked.length == 0) {
						$("#chkPaymentAll").prop("checked",true);
					}

					if( $(this).attr("id") == "chkPayment" + _claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT
							|| $(this).attr("id") == "chkPayment" + _claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER ){

						// 결제 수단 "무통장입금", "실시간계좌이체" 중 하나라도 체크된다면 -> 에스크로 적용 "예", "아니요" 모두 체크
						$("#chkPaymentESCR").prop("checked",true);
						$("#chkPaymentNoESCR").prop("checked",true);
					}
				}
				else {
					$("#chkPaymentAll").prop("checked",false);

					if( $(this).attr("id") == "chkPayment" + _claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT
							|| $(this).attr("id") == "chkPayment" + _claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER ){

						if( !$("#chkPayment" + _claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT).is(":checked")
								&& !$("#chkPayment" + _claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER).is(":checked") ){

							// 결제 수단 "무통장입금", "실시간계좌이체" 중 모두 체크가 풀린다면 -> 에스크로 적용 "예", "아니요" 체크 품
							$("#chkPaymentESCR").prop("checked",false);
							$("#chkPaymentNoESCR").prop("checked",false);
						}
					}
				}
			});
		});

		$("input[custom='escrChk']").click(function(){
			var flag = false;

			// 에스크로 적용 "예" "아니요" 중 하나라도 체크한다면
			$("input[custom='escrChk']").each(function(){
				if($(this).is(":checked")){
					flag = true;
				}
			});
			// "무통장입금" "실시간계좌이체" 체크
			if(flag == true){
				$("#chkPayment" + _claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT).prop("checked",true);
				$("#chkPayment" + _claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER).prop("checked",true);
			}
		});
	}

	$("#downloadExcel").click(function(){
		_claimmain.downloadExcel();
	});
	
	/**
	 * 선택한 클레임 목록 엑설로 다운로드
	 */
	_claimmain.downloadExcel = function() {

		var sRow = claimSheet.FindCheckedRow("downloadCheck");

		if (sRow <= 0 ){
			alert( "다운로드 할 항목을 선택해 주세요." );
			return false;

		} else {
			// 배열 처리
			for(var i=1; i <= claimSheet.RowCount(); i++){

				if(claimSheet.GetCellValue(i, "downloadCheck") == "1"){

					$("#frmSearch").append("<input type='hidden' name='clmNos' value='" + claimSheet.GetCellValue(i, "clmNo") + "'>");
				}
			}

			frmSearch.action = "/claim/claim/download-claim-excel";
			frmSearch.submit();

			$("input[name='clmNos']").remove();
		}
	}

	$("#downloadAllExcel").click(function(){
		_claimmain.downloadAllExcel();
	});
	
	/**
	 * 한 페이지의 전체 클레임 목록 엑설로 다운로드
	 */
	_claimmain.downloadAllExcel = function() {

		var sortInfo   = claimSheet.GetColSortInfo();

		if( sortInfo[0] != undefined ){
			var sortColumn = claimSheet.ColSaveName(sortInfo[0].Col);
			var sortType   = sortInfo[0].SortOrder;
			$("#sortColumn").val(sortColumn);
			$("#sortType").val(sortType);
		}

		//console.log($("#frmSearch").serialize());
		frmSearch.action = "/claim/claim/download-claim-all-excel";
		frmSearch.submit();

		$("#sortColumn").val("");
		$("#sortType").val("");
	}
	
	/**
	 * 검색어 변경 이벤트
	 */
	_claimmain.ordMbInfoSelectChange = function(searchType) {
		
		$("#orderMemberInfoInp").val("");
		$("#buyerHdphnBackNo").val("");
		
		if(searchType == "buyerName"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").show();
		}else if(searchType == "rcvrName"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").show();
		}else if(searchType == "buyerHdphnNoText"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").hide();
		}else if(searchType == "loginId"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").hide();
		}
	}

	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/

	/**
	 * 클레임 상세 팝업 호출
	 */
	_claimmain.openClaimDetailPop = function(clmNo, clmGbnCode){

		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};

		if(clmGbnCode == _claimmain.CLM_GBN_CODE_CANCEL) { //취소

			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세

		} else if(clmGbnCode == _claimmain.CLM_GBN_CODE_EXCHANGE) { //교환

			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세

		} else if(clmGbnCode == _claimmain.CLM_GBN_CODE_RETURN) { //반품

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

	/*************************************************************************
	 *								팝업 호출 함수 E
	 *************************************************************************/
})();