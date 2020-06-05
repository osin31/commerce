/***
 * 회원관리 상세 회원쿠폰 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								포인트 탭
	 *************************************************************************/
	var _memberPoint = abc.object.createNestedObject(window,"abc.biz.member.member.point");
	
	_memberPoint.memberNo;
	_memberPoint.safeKey;
	_memberPoint.safeKeySeq;
	
	/**
	 * 그리드 초기 세팅
	 */
	_memberPoint.initPointSheet = function() {
		
		if(typeof pointSheet != 'undefined'){
			pointSheet.Reset();
		}
		//
		createIBSheet2(document.getElementById("pointGrid"), "pointSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 			Type:"Status",		SaveName:"status",			Width: 0,  	Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"순번", 				Type:"Text",		SaveName:"pointSeq",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"구분", 				Type:"Text",		SaveName:"saveType",		Width: 30, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"적립/사용일", 			Type:"Date",		SaveName:"saleDate",		Width: 60, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATE_PATTERN}
			, {Header:"경로", 				Type:"Text",		SaveName:"storeCd",			Width: 40, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"적립", 				Type:"Int",			SaveName:"savePoint",		Width: 40, 	Align:"Center", 	Edit:0, Sort:0, Format: "#,### Point"}
			, {Header:"종류", 				Type:"Text",		SaveName:"pointGubun",		Width: 60, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"적립/차감 내용", 			Type:"Text",		SaveName:"saveGubun",		Width: 80, 	Align:"Center", 	Edit:0, Sort:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(pointSheet , initSheet);
		// Grid 건수 정보 표시
		pointSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		pointSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		pointSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		pointSheet.SetExtendLastCol(1);
		
		//pointSheet.InitDataCombo(0 , "storeCd" , "온라인|오프라인" , "0072|8800");
	}
	
	/**
	 * 회원 목록 Action 관리
	 */
	_memberPoint.memberPointDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var pageCount = $("#pageCount").val();
				$("#frmSearch").append("<input type='hidden' name='safeKey' value='"+ _memberPoint.safeKey +"'>");
				$("#frmSearch").append("<input type='hidden' name='safeKeySeq' value='"+ _memberPoint.safeKeySeq +"'>");
				
				var param = { url : "/member/read-member-point-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "pointSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	/**
	 * 포인트 관리자 적립/사용 팝업 호출
	 */
	_memberPoint.savePop = function(){
		var url = "/member/member-point-save-pop";
		var params = {}
		params.memberNo = _memberPoint.memberNo;

		abc.open.popup({
			winname :	"pointSavePop",
			url 	:	url,
			width 	:	800,
			height	:	385,
			params	:	params
		});
	}
	
	/**
	 * 포인트 사후 적립 팝업 호출
	 */
	_memberPoint.expostSavePop = function(){
		var url = "/member/member-point-expost-pop";
		var params = {}
		params.memberNo = _memberPoint.memberNo;

		abc.open.popup({
			winname :	"pointSavePop",
			url 	:	url,
			width 	:	1000,
			height	:	960,
			params	:	params
		});
	}
	
	/*************************************************************************
	 *								포인트 적립 
	 *************************************************************************/
	
	/**
	 * 적립/사용 내용 입력 글자수 체크
	 */
	_memberPoint.comemtLengthCheck = function(obj) {
		if(obj.length <= 20){
			$('#comentCounter').html(obj.length);
		}else{
			alert("적립/사용 내용은 20자를 초과할 수 없습니다.");
			$("#saveUseContText").val(obj.substring(0, 20));
			return false;
		}
	}
	
	/**
	 * 관리자 코멘트 입력 글자수 체크
	 */
	_memberPoint.adminMemoLengthCheck = function(obj) {
		if(obj.length <= 30){
			$('#adminMemoCounter').html(obj.length);
		}else{
			alert("관리자 코멘트는 30자를 초과할 수 없습니다.");
			$("#saveUseContText").val(obj.substring(0, 30));
			return false;
		}
	}
	
	_memberPoint.savePoint = function(){
		var saveTypeVal = $("input[name=saveType]:checked").val();
		var validateCode = $("#validateCode option:selected").val();
		var pointVal = $("#point").val();
		var saveUseContText = $("#saveUseContText").val();
		var etcSavedCode = $("#etcSavedCode").val();
		
		if(!$("input[name=saveType]").is(":checked")){
			alert("포인트 적립/사용 구분을 선택해주세요.");
			return;
		}
		
		if(abc.text.allNull(pointVal)){
			alert("적립/사용 포인트를 입력해주세요.");
			$("#changeAmount").focus();
			return;
		}
		
		if(pointVal <= 0){
			alert("0포인트를 입력할 수 없습니다.");
			$("#changeAmount").focus();
			return;
		}
		
		if(abc.text.allNull(saveUseContText)){
			alert("적립/사용 내용을 입력해주세요.");
			$("#saveUseContText").focus();
			return;
		}
		
		if(validateCode == "1"){
			if(abc.text.allNull(etcSavedCode)){
				alert("통합맴버십 코드를 입력해주세요.");
				$("#etcSavedCode").focus();
				return;
			}
		}
		
		$("#savePointForm").append("<input type='hidden' name='memberNo' value='"+ _memberPoint.memberNo +"'>");
		
		if(confirm("저장하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/member/update-member-save-point",
				data : $("#savePointForm").serialize(),
				async : false
			}).done(function(data){
				if(data.code){
					alert("저장되었습니다.");
					opener.parent.abc.memberDetailTabReload();
					self.close();
				}else{
					alert("저장에 실패하였습니다.");
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.responseJSON.message);
			});
		}
	}
	
	/*************************************************************************
	 *								사후 적립 팝업
	 *************************************************************************/
	
	/**
	 * 사후적립 그리드 세팅
	 */
	_memberPoint.initPointExpostSheet = function() {
		
		if(typeof expostSheet != 'undefined'){
			expostSheet.Reset();
			expostSheet.DisposeSheet(1);
		}
		//
		createIBSheet2(document.getElementById("expostGrid"), "expostSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",			Width: 0,  	Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"orderNo",			Width: 40, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"구매일시", 			Type:"Date",		SaveName:"orderDtm",		Width: 40, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"주문상품", 			Type:"Text",		SaveName:"orderPrdtInfo",	Width: 80, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제금액", 			Type:"Int",			SaveName:"pymntAmt",		Width: 35, 	Align:"Center", 	Edit:0, Sort:0}
			/*, {Header:"적립포인트", 		Type:"Int",			SaveName:"savePoint",		Width: 35, 	Align:"Center", 	Edit:0, Sort:0}*/
			, {Header:"취소여부", 			Type:"Text",		SaveName:"orderCancelYn",	Width: 20, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"적립일시", 			Type:"Html",		SaveName:"savepointInfo",	Width: 40, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"적립일시", 			Type:"Date",		SaveName:"savepointDtm",	Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"적립여부", 			Type:"Text",		SaveName:"savepointYn",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"인증번호", 			Type:"Text",		SaveName:"crtfcNoText",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(expostSheet , initSheet);
		// Grid 건수 정보 표시
		expostSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		expostSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		expostSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		expostSheet.SetExtendLastCol(1);
		
		_memberPoint.memberExpostDoAction("search");
	}
	
	/**
	 * 사후적립 화면 세팅
	 */
	_memberPoint.initExpost = function(){
		//온라인회원일 경우 검색 조건 및 버튼 숨김 처리
		$(".tbl-row").find("tr").eq(1).hide();
		$(".tbl-row").find("tr").eq(2).hide();
		
		$("#searchBtn").hide();
	}
	
	/**
	 * 적립/사용 내용
	 */
	_memberPoint.changeMemberType = function(memberType){
		if(memberType == abc.consts.MEMBER_TYPE_ONLINE){
			expostSheet.RemoveAll();
			$(".tbl-row").find("tr").eq(1).hide();
			$(".tbl-row").find("tr").eq(2).hide();
			$("#searchBtn").hide();
			
			_memberPoint.memberExpostDoAction('search');
		}else{
			expostSheet.RemoveAll();
			$(".tbl-row").find("tr").eq(1).show();
			$(".tbl-row").find("tr").eq(2).show();
			
			$("#searchBtn").show();
		}
	}
	
	/**
	 * 비회원 구매 조회
	 */
	_memberPoint.searchNonMemberPurchaseList = function() {
		var orderNo = $("#orderNo").val();
		var crtfcNoText = $("#crtfcNoText").val();
		var orderDtm = $("#orderDtm").val();
		
		if(abc.text.allNull(orderNo)){
			alert("주문번호를 입력해 주세요.");
			$("#orderNo").focus();
			return false;
		}
		
		if(abc.text.allNull(crtfcNoText)){
			alert("인증번호 입력해 주세요.");
			$("#crtfcNoText").focus();
			return false;
		}
		
		if(abc.text.allNull(orderDtm)){
			alert("구매일자를 선택해주세요.");
			return false;
		}
		
		_memberPoint.memberExpostDoAction("search");
	}
	
	/**
	 * 포인트 사후적립 조회
	 */
	_memberPoint.memberExpostDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				
				var pageCount = $("#pageCount").val();
				
				var param = { url : "/member/read-member-purchase-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "expostSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	/**
	 * 온라인회원, 비회원 구매 사후적립
	 */
	_memberPoint.pointSave = function(row){
		
		var param = {
				  memberNo 	 : $("#memberNo").val()
				, onlnBuyYn : abc.consts.BOOLEAN_TRUE 
				, memberTypeCode : $("input[name='memberTypeCode']:checked").val()
				, buyNoText : expostSheet.GetCellValue(row, "orderNo")
				, buyYmd : expostSheet.GetCellValue(row, "orderDtm")
				, pymntAmt : expostSheet.GetCellValue(row, "pymntAmt")
				, crtfcNoText : expostSheet.GetCellValue(row, "crtfcNoText")
		};
		console.log(param);
		
		$.ajax({
			type :"post",
			url : "/member/member-create-expost-point",
			async: false, 
			data: param
		})
		.done(function(data){
			if(data.resultCode == abc.consts.BOOLEAN_FALSE){
				alert(data.resultMsg);
			}else{
				alert(data.resultMsg);
			}
		})
		.fail(function(e){
			alert("처리중 오류가 발생하였습니다.");
			console.log("e :" + e);
		});
	}
	
	_memberPoint.offlinePointSave = function() {
		var orderNo = $("#buyNoText").val();
		var orderDtm = $("#buyYmd").val();
		var pymntAmt = $("#pymntAmt").val();
		var crtfcNoText = $("#offCrtfcNoText").val();
		var storeNo = $("#storeNo").val();
		
		if(abc.text.allNull(orderNo)){
			alert("주문(거래)번호를 입력해주세요.");
			$("#buyNoText").focus();
			return false;
		}
		
		if(abc.text.allNull(crtfcNoText)){
			alert("인증번호를 입력해주세요.");
			$("#offCrtfcNoText").focus();
			return false;
		}
		
		if(abc.text.allNull(orderDtm)){
			alert("구매일자를 선택해주세요.");
			return false;
		}
		
		if(abc.text.allNull(pymntAmt)){
			alert("결제금액을 입력해주세요.");
			$("#pymntAmt").focus();
			return false;
		}
		
		if(abc.text.allNull(storeNo)){
			alert("매장을 선택해주세요.");
			$("#storeNm").focus();
			return false;
		}
		
		var param = {
				  memberNo 	 : $("#memberNo").val()
				, onlnBuyYn : abc.consts.BOOLEAN_FALSE  
				, buyNoText : orderNo
				, buyYmd : orderDtm
				, pymntAmt : pymntAmt
				, crtfcNoText : crtfcNoText
				, storeNo : storeNo
		};
		console.log(param);
		
		$.ajax({
			type :"post",
			url : "/member/member-create-expost-point",
			async: false, 
			data: param
		})
		.done(function(data){
			if(data.resultCode == abc.consts.BOOLEAN_FALSE){
				alert(data.resultMsg);
			}else{
				alert(data.resultMsg);
			}
		})
		.fail(function(e){
			alert("처리중 오류가 발생하였습니다.");
			console.log("e :" + e);
		});
	}
	
	/***
	 * 매장찾기 콜백
	 */
	_memberPoint.setStoreCallBack = function(obj) {
		var storeCode= obj[0].storeIdText;
		var storeName= obj[0].storeName;
		
		$("#storeNo").val(storeCode);
		$("#storeNm").val(storeName);
	}
})();