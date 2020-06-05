/***
 * 회원관리 상세 회원쿠폰 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								문의 내역 탭
	 *************************************************************************/
	var _memberInquiry = abc.object.createNestedObject(window,"abc.biz.member.member.inquiry");
	
	_memberInquiry.memberNo;
	_memberInquiry.viewType;
	_memberInquiry.cnslTypeCode;
	_memberInquiry.cnslTypeDtlCode;
	_memberInquiry.aswrStatCode;
	_memberInquiry.siteNo;
	_memberInquiry.orderNo;
	_memberInquiry.vndrNo;
	_memberInquiry.asAcceptNo;
	_memberInquiry.asPrdtNo;
	
	$("#orderNo").change(function(){
		var value = $(this).val();
		abc.biz.member.member.inquiry.getOrderProductList(value);
	});
	
	$("#prdtInfo").change(function(){
		var value = $(this).val();
		console.log("value  == " + value );
		abc.biz.member.member.inquiry.getOrderProductInfo(value);
	});
	
	$("#asAcceptNo").change(function(){
		
		$("#asPrdtNo").remove();
		$("#prdtNo").val("");
		
		var value = $(this).val();
		$("#spanAs").append("<a href='javascript:void(0)' class='link' name='prdtNo' id='asPrdtNo'>"+value+"</a>");
		$("#prdtNo").val(value);
		
		$("#asPrdtNo").click(function(){
			var param = { prdtNo : $(this).text() };
			abc.readonlyProductDetailPopup(param);
		});
	});
	
	_memberInquiry.getAsPrdt = function(prdtNo) {
		var param = { prdtNo : prdtNo };
		abc.readonlyProductDetailPopup(param);
	}
	
	/**
	 * 그리드 초기 세팅
	 */
	_memberInquiry.initInquirySheet = function() {
		
		if(typeof inquirySheet != 'undefined'){
			inquirySheet.Reset();
		}
		//
		createIBSheet2(document.getElementById("inquiryGrid"), "inquirySheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 	Type:"Status",		SaveName:"status",				Width: 0,  	Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"시퀀스", 		Type:"Text",		SaveName:"memberCnslSeq",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"회원ID", 		Type:"Text",		SaveName:"memberNo",			Width: 0, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"",			Type:"Text",		SaveName:"checkAtchFile",		Width: 0,	Align:"Center",		Edit:0,	Hidden:1		} // 첨부파일 유무
			, {Header:"",			Type:"Text",		SaveName:"vndrNo",				Width: 0,	Align:"Center",		Edit:0,	Hidden:1		} // 담당업체코드
			, {Header:"",			Type:"Text",		SaveName:"aswrNo",				Width: 0,	Align:"Center",		Edit:0,	Hidden:1		} // 답변 관리자 번호
			, {Header:"",			Type:"Text",		SaveName:"vndrGbnType",			Width: 0,	Align:"Center",		Edit:0,	Hidden:1		} // 업체구분
			, {Header:"번호", 		Type:"Seq",			SaveName:"",					Width: 20, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"답변상태", 		Type:"Text",		SaveName:"aswrStatName",		Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사이트", 		Type:"Text",		SaveName:"siteName",			Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"분류", 		Type:"Combo",		SaveName:"cnslGbnCode",			Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"유형", 		Type:"Text",		SaveName:"cnslTypeName",		Width: 50, 	Align:"Left", 		Edit:0, Sort:0}
			, {Header:"제목", 		Type:"Html",		SaveName:"inqryTitleText",		Width: 100, Align:"Left", 		Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"등록일시", 		Type:"Date",		SaveName:"inqryDtm",			Width: 50, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"담당업체", 		Type:"Text",		SaveName:"vndrName",			Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"답변자", 		Type:"Text",		SaveName:"aswrDetailInfo",		Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"최종답변일시", 	Type:"Date",		SaveName:"aswrDtm",				Width: 50, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		// Grid 초기화
		IBS_InitSheet(inquirySheet , initSheet);
		// Grid 건수 정보 표시
		inquirySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		inquirySheet.SetPagingPosition(2);
		// Grid width 자동 조절
		inquirySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		inquirySheet.SetExtendLastCol(1);
		
		inquirySheet.InitDataCombo(0 , "cnslGbnCode" , this.codeCombo.CNSL_GBN_CODE.text , this.codeCombo.CNSL_GBN_CODE.code);
	}
	
	/**
	 * 회원 목록 Action 관리
	 */
	_memberInquiry.memberInquiryDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				
				var fromDate = Number(abc.text.validateStringSignRemove($("#fromDate").val()));
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
				
				var _searchChageVndr = $("#searchChageVndr").val(); // 담당업체
				var _searchChageVndrValue = $("#searchChageVndrValue"); // 업체 검색어
				
				if (_searchChageVndr == "vndrNm" && abc.text.isBlank(_searchChageVndrValue.val())) {
					alert("검색할 업체명을 입력 하세요.");
					_searchChageVndrValue.focus();
					return false;
				}

				var pageCount = $("#pageCount").val();
				$("#frmSearch").append("<input type='hidden' name='memberNo' value='"+ _memberInquiry.memberNo +"'>");
				
				var param = { url : "/member/board/inquiry/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "inquirySheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	/**
	 * 분류에 선택에 따른 유형 변경
	 */
	_memberInquiry.cnslGbnCodeChange = function(){
		var cnslGbnVal = $("input[name=cnslGbnCode]:checked").val();
		
		if((cnslGbnVal == '10000' || cnslGbnVal == '10002')){
			$("select[name='searchCnslTypeCode'] option[value='10013']").hide();
			$("#searchCnslTypeCode").val("").prop("selected", true).trigger("change");
			$("#searchCnslTypeCode").prop("disabled", false);
		}else if(cnslGbnVal == '10001'){
			$("select[name='searchCnslTypeCode'] option[value='10013']").show();
			$("#searchCnslTypeCode").val("10013").prop("selected", true).trigger("change");
			$("#searchCnslTypeCode").prop("disabled", true);
		}else{
			$("select[name='searchCnslTypeCode'] option[value='10013']").show();
			$("#searchCnslTypeCode").val("").prop("selected", true).trigger("change");
			$("#searchCnslTypeCode").prop("disabled", false);
		}
	}
	
	/**
	 * 회원 유선상담 등록 팝업 호출
	 */
	_memberInquiry.registMemberCounsel = function(){
		var url = "/member/member-counsel-pop";
		var params = {}
		params.memberNo = _memberInquiry.memberNo;
		params.viewType = abc.consts.CRUD_C;

		abc.open.popup({
			winname :	"counselPop",
			url 	:	url,
			width 	:	1270,
			height	:	820,
			params	:	params
		});
	}
	
	/*************************************************************************
	 *								유선상담 등록 팝업
	 *************************************************************************/
	
	/**
	 * 회원 유선상담 등록 팝업 화면 세팅
	 */
	_memberInquiry.memberCounselinit = function(){
		//유선상담 등록 팝업일 경우 문의 일자, 답변자, 최종답변일시 를 노출시키지 않음.
		if(_memberInquiry.viewType == abc.consts.CRUD_C){
			$("#thDtmTitle").hide();
			$("#tdDtmValue").hide();
			$("#tdCnslValue").attr("colspan", 3);
			$("#trAswr").hide();
			
			//관리자 메모 히든처리
			$("#memoHeader").hide();
			$(".manager-msg-wrap").hide();
			$(".msg-list-wrap").hide();
			
			$("input:radio[name='siteNo'][value='10000']").prop("checked", true);
			// 답변상태를 답변완료로 디폴트 체크
			$("input:radio[name='aswrStatCode'][value='10002']").prop("checked", true);
			$("#orderArea").hide();
			$("#asArea").hide();
			
			// 업체
			$("#vendorlink").empty();
			$("#vndrNo").val("");
			
			// AS
			$("#asAcceptNo").val("");
			$("#asPrdtNo").text("");
			$("#prdtNo").val("");
			
		}else{
			$("input:radio[name='siteNo'][value='"+_memberInquiry.siteNo+"']").prop("checked", true);
			$("#cnslTypeCode").val(_memberInquiry.cnslTypeCode).prop("selected", true).trigger("change");
			$("input:radio[name='aswrStatCode'][value='"+_memberInquiry.aswrStatCode+"']").prop("checked", true);
			$("#cnslTypeDtlCode").val(_memberInquiry.cnslTypeDtlCode).prop("selected", true);
			
			if(_memberInquiry.cnslTypeCode == "10004"){ // 상담유형 : 주문/결제/취소
				_memberInquiry.getOrderProductList( $("#orderNo").val() );
				_memberInquiry.getOrderProductInfo( $("#prdtInfo").val() );
			}
			if(_memberInquiry.cnslTypeCode == "10005"){
				$("#vndrNo").val( _memberInquiry.vndrNo );
			}
			
			// AS
			$("#asAcceptNo").val( _memberInquiry.asAcceptNo );
			$("#asPrdtNo").val( _memberInquiry.asPrdtNo );
			$("#prdtNo").val( _memberInquiry.asPrdtNo );
		}
	}
	
	_memberInquiry.currentUrl;
	/**
	 * 유선상담 등록
	 */
	_memberInquiry.saveCounsel = function() {
		var _url;
		var cnslTypeCode =  $("#cnslTypeCode option:selected").val();
		var cnslTypeDtlCode =  $("#cnslTypeDtlCode option:selected").val();
		
		if((abc.text.allNull(cnslTypeCode) || abc.text.allNull(cnslTypeDtlCode))){
			alert("유형선택은 필수 입니다.");
			if(abc.text.allNull(cnslTypeCode)){
				$("#cnslTypeCode").focus();
			}else if(abc.text.allNull(cnslTypeDtlCode)){
				$("#cnslTypeDtlCode").focus();
			}
			return false;
		}
		
		$("#viewType").val(_memberInquiry.viewType);
		$("#inqryTitleText").val("유선상담 접수건 입니다.");
		
		var $form = $('#counselRegiForm');

		//화면 타입(등록/상세)에 따라 url 세팅
		if(_memberInquiry.viewType == abc.consts.CRUD_C){
			_url = "/member/create-member-counsel";
		}else{
			_url = "/member/update-member-counsel";
		}
		
		
		if(confirm("저장하시겠습니까?")){
			$.ajax({
				type:"post",
				url: _url,
				data: $form.serialize()
			})
			.done(function(data){
				if(data.code == abc.consts.BOOLEAN_TRUE){
					console.log(opener.parent.window.location.pathname);
					alert("저장되었습니다.");
					var openerUrl = opener.window.location.pathname;
					if(openerUrl == "/member/member-detail-pop"){
						opener.parent.abc.memberDetailTabReload();
					}else if(openerUrl == "/board/inquiry/"){
						opener.parent.abc.biz.board.inquiry.doActionCounselScript("search");
					}
				}
				window.self.close();
			})
			.fail(function(e){
				alert("저장에 실패하였습니다.");
			});
		}
	}
	
	/**
	 * 관리자 메모등록
	 */
	_memberInquiry.saveMemo = function() {
		var memberCnslSeq = $("#memberCnslSeq").val();
		
		$.ajax({
			type:"post",
			url: "/member/create-member-admin-memo",
			data: $("#frmAdminMemo").serialize()
		}).done(function(data, textStatus, jqXHR) {
			var addArea;
			var rgstDtm = new Date(data.rgstDtm);
			addArea  = "<li id='adminMemoArea"+data.cnslMemoSeq+"'>";
			addArea +=  	"<span class='msg-list-info'>";
			addArea +=  		"<span class='user-info'>";
			addArea +=  			"<span class='user-id'>"+data.rgsterDpName+"</span>";
			addArea +=  		"</span>";
			addArea +=  		"<span class='regist-date-wrap'>";
			addArea +=  			"<span class='regist-date'>"+rgstDtm.format()+"</span>";
			addArea +=  			"<a href='#' value='"+data.cnslMemoSeq+"' name='memoDeleteBtn' class='btn-msg-list-del'><i class='ico ico-del'><span class='offscreen'>메모 삭제</span></i></a>";
			addArea +=  		"</span>";
			addArea +=  	"</span>";
			addArea +=  	"<p class='msg-desc'>"+data.memoText+"</p>";
			addArea +=  "</li>";
			$("#memoAddArea").prepend(addArea);
			$("#adminMemoText").val("");
			alert("저장되었습니다.");
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			console.log(jqXHR.responseJSON);
		});
	}
	
	/**
	 * 관리자 메모삭제
	 */
	_memberInquiry.removeAdminMemo = function(memberCnslSeq,cnslMemoSeq){
		$.ajax({
			type:"post",
			url: "/member/remove-member-admin-memo",
			data: {
				"memberCnslSeq" : memberCnslSeq,
				"cnslMemoSeq" : cnslMemoSeq
			}
		}).done(function(data, textStatus, jqXHR) {
			alert("삭제되었습니다.");
			$("#adminMemoArea"+cnslMemoSeq).remove();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			console.log(jqXHR.responseJSON);
		});
		
	}
	
	/**
	 * 주문배송유형 선택 -- multi select 채널정보 
	 */
	_memberInquiry.getOrderProductList = function(value) {
		$.ajax({
			type:"post",
			url: "/order/product-list/", 
			data: {
				"orderNo" : value
			}	
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.member.member.inquiry.setOrderProductList(data);
			abc.biz.member.member.inquiry.getOrderProductInfo( $("#prdtInfo").val() );
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 주문배송유형 선택 -- multi select 채널정보 
	 */
	_memberInquiry.setOrderProductList = function(data) {
		console.log(data)
		var select = $("#prdtInfo");
		select.find("option").remove();
		
		var all = "";		
		$.each(data, function(index, item) {
			select.append(new Option(item.prdtName+" (옵션: "+item.optnName+")", item.prdtNo+"|"+item.vndrNo+"|"+item.vndrName ));
			all += (index == 0) ? item.prdtNo : ","+item.prdtNo;
		}); 
		
	}
	
	_memberInquiry.getOrderProductInfo = function(data) {
		
		// 초기화 먼저
		$("#vendorlink").empty();
		$("#vndrNo").val("");
		
		var splitValue = data.split("|");
		
		var vendorNo = splitValue[1];
		var vendorName = splitValue[2];
		
		console.log("vendorNo" + vendorNo);
		console.log("vendorName" + vendorName);
		var addArea  = "<a href='#' value='"+vendorNo+"' name='"+vendorNo+"' >["+vendorNo+"] "+vendorName+"</a>";
		$("#vendorlink").prepend(addArea);
		
		$("#vndrNo").val(vendorNo);
	}
	
	//문의유형에 따른 주문번호,AS접수번호 영역 노출
	_memberInquiry.orderAsAreaSet = function(cnslAddInfo) {
		if(cnslAddInfo == "O"){
			$("#orderArea").show();
			$("#asArea").hide();
		}else if(cnslAddInfo == "A"){
			$("#orderArea").hide();
			$("#asArea").show();
		}else{
			$("#orderArea").hide();
			$("#asArea").hide();
		}
		
	}
	
	//초기화
	_memberInquiry.frmReset = function() {
		$.form("#counselRegiForm").reset();
		abc.biz.member.member.inquiry.memberCounselinit();
		if(_memberInquiry.viewType == abc.consts.CRUD_C){
			$("#cnslTypeDtlCode").find("option").each(function(i, v){
				if(i > 0){
					$(this).remove();
				} 
			});
		}
	}
	
	
	
})();