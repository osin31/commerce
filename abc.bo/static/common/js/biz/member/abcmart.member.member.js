/***
 * 회원관리 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								회원 관리 목록
	 *************************************************************************/
	var _member = abc.object.createNestedObject(window,"abc.biz.member.member");
	
	/**
	 * 회원 목록 화면 초기 세팅
	 */
	_member.initSetObj = function() {
		$("#loginId").hide();
		$("#memberNo").hide();
		$("#selEmail").val("").prop("selected", true);
		$("#emailAddrDtl").val("");
		$("#emailAddrDtl").attr("readonly", false);
		$("#emailAddrDtl").attr("disabled", false);
	}
	
	/**
	 * 초기화 시 일부 엘리먼트 초기화 세팅
	 */
	_member.formResetInit = function() {
		$("#loginId").hide();
		$("#selEmail").val("").prop("selected", true);
		$("#emailAddrDtl").val("");
		$("#emailAddrDtl").attr("readonly", false);
		$("#emailAddrDtl").attr("disabled", false);
		$("#memberName").show();
		$("#hdphnBackNoText").show();
		$("#loginId").hide();
		$("#memberName").val("");
		$("#hdphnBackNoText").val("");
		$("#loginId").val("");
	}
	
	/**
	 * 회원 목록 그리드 초기 세팅
	 */
	_member.initMemberSheet = function() {
		//
		createIBSheet2(document.getElementById("memberGrid"), "memberSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1, Sort:0}
			, {Header:"회원번호", 			Type:"Text",		SaveName:"memberNo",			Width: 10, Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"블랙리스트", 		Type:"Text",		SaveName:"blackListYn",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"회원상태", 			Type:"Text",		SaveName:"memberState",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"회원", 			Type:"Text",		SaveName:"gridMemberName",		Width: 25, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"성별", 			Type:"Text",		SaveName:"genderType",			Width: 25, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"이메일", 			Type:"Text",		SaveName:"gridEmailAddrText",	Width: 30, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"휴대폰번호", 		Type:"Text",		SaveName:"gridHdphnNoText",		Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"가입유형", 			Type:"Text",		SaveName:"memberTypeCodeName",	Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"최종로그인일시", 		Type:"Date",		SaveName:"lastLoginDtm",		Width: 20, Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"회원가입일시", 		Type:"Date",		SaveName:"joinDtm",				Width: 30, Align:"Center", 	Edit:0, Format:"YmdHms"}
		];
		
		// Grid 초기화
		IBS_InitSheet(memberSheet , initSheet);
		// Grid 건수 정보 표시
		memberSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		memberSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberSheet.SetExtendLastCol(1);
	}
	
	/** 회원유형 변경 함수  */
	$("#chkMemberTypeAll").click(function(){
		if($(this).is(":checked")){
			$("input[custom=memberTypeCode]").prop("checked",true);
			$("#chkMemberTypeERP").prop("checked",true);
			$("input[custom=mbshpGradeCode]").prop("checked",true);
		} else {
			$("input[custom=memberTypeCode]").prop("checked",false);
			$("#chkMemberTypeERP").prop("checked",false);
			$("input[custom=mbshpGradeCode]").prop("checked",false);
		}
	});
	
	$("input[custom=memberTypeCode]").each(function(){
		$(this).click(function(){
			if($(this).is(":checked")){
				var unChecked = $("input[custom=memberTypeCode]:not(:checked)");
				var unChecked2 = $("#chkMemberTypeERP:not(:checked)");

				if (unChecked.length == 0 && unChecked2.length == 0 ) {
					$("#chkMemberTypeAll").prop("checked",true);
				}
				if($(this).attr("id") == "chkMemberType10001" ){
					$("input[custom=mbshpGradeCode]").prop("checked",true);
				}
			} else {
				$("#chkMemberTypeAll").prop("checked",false);
				if($(this).attr("id") == "chkMemberType10001" ){
					if(!$("#chkMemberType10001").is(":checked")){

						$("input[custom=mbshpGradeCode]").prop("checked",false);
					}else {
						$("input[custom=mbshpGradeCode]").prop("checked",true);
					}
				}
			}
		})
	});

	$("input[custom=mbshpGradeCode]").each(function(){
		$(this).click(function(){
			if($(this).is(":checked")){
				var unChecked = $("input[custom=mbshpGradeCode]:not(:checked)");

				//if (unChecked.length == 0) {
					$("#chkMemberType10001").prop("checked",true);
				//}
			} else {
				var unChecked = $("input[custom=mbshpGradeCode]:not(:checked)");

			}
		})
	});
	
	/**
	 * 회원검색 조건 콤보박스 이벤트
	 */
	_member.memberSearchTypeChange = function(searchType) {
		if(searchType == "01"){
			$("#memberName").show();
			$("#hdphnBackNoText").show();
			$("#loginId").hide();
			$("#memberName").val("");
			$("#hdphnBackNoText").val("");
			$("#loginId").val("");
			$("#memberNo").hide();
			$("#memberNo").val("");
		}else if(searchType == "02"){
			$("#loginId").show();
			$("#memberName").hide();
			$("#hdphnBackNoText").hide();
			$("#memberName").val("");
			$("#hdphnBackNoText").val("");
			$("#loginId").val("");
			$("#memberNo").hide();
			$("#memberNo").val("");
		}else{
			$("#memberNo").show();
			$("#memberName").hide();
			$("#hdphnBackNoText").hide();
			$("#memberName").val("");
			$("#hdphnBackNoText").val("");
			$("#loginId").hide();
			$("#loginId").val("");
		}
	}
	
	/**
	 * 회원 목록 Action 관리
	 */
	_member.memberDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var memberName = $("#memberName").val();
				var loginId = $("#loginId").val();
				var searchType = $("#memberSearchType").val();
				var hdphnBackNo = $("#hdphnBackNoText").val();
				var emailAddr = $("#emailAddr").val();
				var emailAddrDtl = $("#emailAddrDtl").val();
				var fromDate = $("#fromDate").val();
				var toDate = $("#toDate").val();
				var memberNo = $("#memberNo").val();
				
				if(searchType == "03"){
					if(abc.text.isBlank(memberNo) ){
						alert("회원번호를 입력해주세요.");
						$("#memberNo").focus();
						return false;
					}
				}else if(abc.text.isBlank(memberName) && abc.text.isBlank(hdphnBackNo) && abc.text.isBlank(loginId) && abc.text.isBlank(emailAddr) && abc.text.isBlank(emailAddrDtl)){
					alert("[회원이름, 휴대폰번호 뒤 4자리] 또는  \n[이메일 주소] 또는 \n[회원 ID]를 입력해주세요.");
					return false;
				}
				
				if(searchType == "01" && abc.text.isBlank(emailAddr) && abc.text.isBlank(emailAddrDtl)){
					if(abc.text.isBlank(memberName)){
						alert("회원이름, 휴대폰번호 뒤 4자리를 입력해주세요.");
						$("#memberName").focus();
						return false;
					}
					
					if(hdphnBackNo.length != 4){
						alert("회원이름, 휴대폰번호 뒤 4자리를 입력해주세요.");
						$("#hdphnBackNoText").focus();
						return false;
					}
				}else if(searchType == "02" && abc.text.isBlank(emailAddr) && abc.text.isBlank(emailAddrDtl)){
					if(abc.text.isBlank(loginId)){
						alert("회원 ID를 입력해주세요.");
						$("#loginId").focus();
						return false;
					}
				}else if(searchType == "03"){
					if(abc.text.isBlank(memberNo)){
						alert("회원번호를 입력해주세요.");
						$("#memberNo").focus();
						return false;
					}
				}
				
				if(!abc.text.isBlank(emailAddr)){
					if(abc.text.isBlank(emailAddrDtl)){
						alert("이메일 주소를 확인해세요.");
						$("#emailAddrDtl").focus();
						return false;
					}
				}
				
				if(!abc.text.isBlank(emailAddrDtl)){
					if(abc.text.isBlank(emailAddr)){
						alert("이메일 주소를 확인해세요.");
						$("#emailAddr").focus();
						return false;
					}
				}
				
				// 기간검색 체크
				if(!abc.date.searchValidate()){
					return false;
				}
				
				$("#emailAddrText").val("");
				
				var pageCount = $("#pageCount").val();
				var emailAddrText = "";
				var emailAddr = $("#emailAddr").val();
				var emailAddrDtl = $("#emailAddrDtl").val();
				
				if(emailAddr != ""){
					emailAddrText = emailAddr + "@" + emailAddrDtl;
					$("#emailAddrText").val(emailAddrText);
				}
				
				var param = { url : "/member/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "memberSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	/**
	 * 이메일 변경 시 세팅
	 */
	_member.changeEmail = function(selEmailVal){

		if(selEmailVal == ""){
			$("#emailAddrDtl").val("");
			$("#emailAddrDtl").attr("readonly", false);
			$("#emailAddrDtl").attr("disabled", false);
			$("#emailAddrDtl").focus();
		}else{
			$("#emailAddrDtl").val(selEmailVal);
			$("#emailAddrDtl").attr("readonly", true);
			$("#emailAddrDtl").attr("disabled", false);
		}
	}
	
	/*************************************************************************
	 *								회원 관리 상세
	 *************************************************************************/
	var _memberDetail = abc.object.createNestedObject(window,"abc.biz.member.memberDetail");
	
	_memberDetail.fromInfo = [];
	
	/**
	 * 회원 상세 탭 변경 이벤트
	 */
	_memberDetail.detailTabChange = function(ui){
		ui.oldPanel.empty();
		abc.biz.member.memberDetail.detailTabLoad(ui.newPanel.attr("id"));
	}
	
	/**
	 * 회원 상세 탭 로드 이벤트
	 */
	_memberDetail.detailTabLoad = function(tabId){
		
		var url;
		var memberNo = $("#memberNo").val();
		var safeKey = $("#safeKey").val();
		var safeKeySeq = $("#safeKeySeq").val();
		var memberTypeCode = $("#memberTypeCode").val();
		
		if(tabId == "tabMember"){
			// 회원정보
			url = "/member/read-member-info-tab";
		} else if(tabId == "tabOrder"){
			// 주문내역
			url = "/member/read-order-info-tab";
		} else if(tabId == "tabGiftCardPurchase"){
			// 기프트카드 구매내역 
			url = "/member/giftcard/read-giftcard-info-tab";
		} else if(tabId == "tabInquiry"){
			// 문의내역
			url = "/member/read-inquiry-info-tab";
		} else if(tabId == "tabProdInquiry"){
			// 상품 Q&A
			url = "/member/read-prod-inquiry-info-tab";
		} else if(tabId == "tabProdReview"){
			// 상품후기
			url = "/member/read-prod-review-info-tab";
		} else if(tabId == "tabCoupon"){
			// 쿠폰관리
			url = "/member/read-coupon-info-tab";
		} else if(tabId == "tabPoint"){
			// 포인트 관리
			url = "/member/read-point-info-tab";
		} else if(tabId == "tabEvent"){
			// 이벤트 내역
			url = "/member/read-event-info-tab";
		}
		
		
		$.ajax({
			type :"post",
			url : url,
			data : {"memberNo" : memberNo, "safeKey" : safeKey, "memberTypeCode" : memberTypeCode, "safeKeySeq" : safeKeySeq},
			dataType : "html",
			async : false
		})
		.done(function(data){
			$("#" + tabId).html(data);
			$(".ui-cal").each(function(){
				abc.namespace.front.backOffice.setDatepicker(this);
			});
			
			$(".btn-search-toggle").each(function(){
				abc.namespace.front.backOffice.setSearchZoneViewToggleBtn(this);
			});
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			var responseJSON = JSON.parse(jqXHR.responseText);
			alert(responseJSON.message);
		});
	}
	
	// 링크 클릭시 탭이동 및 검색조건 세팅, 검색
	_memberDetail.moveTab = function(iptType) {
		var fromInfo = abc.biz.member.memberDetail.fromInfo;
		var fromInfoSize = fromInfo.length;
		
		if(fromInfoSize > 0) {
			for(var i=0; i<fromInfoSize; i++) {
				if(iptType === "radio") {
						$("#"+fromInfo[i]).prop("checked", true);
						
				} else if(iptType === "checkbox") {
					var boxName = $("#"+fromInfo[i]).attr("name");
					var boxes = $("input[name=" + boxName + "]");
					$("input[name="+boxName+"]").closest("ul").find("input").eq(0).trigger("click");
					$("#"+fromInfo[i]).prop("checked", true);
				}
			}
			
			abc.biz.member.memberDetail.fromInfo = [];
			
			var _Today = new Date();
			var inputDate;
			var toDateVal = $("#toDate").val();

			if(abc.text.allNull(toDateVal)){
				$("#toDate").val($.datepicker.formatDate($("#toDate").datepicker("option", "dateFormat"), _Today));
				_Today.setMonth(_Today.getMonth() - 2);
				$("#fromDate").val($.datepicker.formatDate($("#fromDate").datepicker("option", "dateFormat"), _Today));
			}else{
				abc.text.validateStringSignRemove(toDateVal);
				inputDate = abc.date.changeDate(toDateVal);
				inputDate.setMonth(inputDate.getMonth() - 2);
				$("#fromDate").val($.datepicker.formatDate($("#fromDate").datepicker("option", "dateFormat"), inputDate));
			}
			
			$("#searchBtn, #search").trigger("click");
		}
		
	}
	
})();