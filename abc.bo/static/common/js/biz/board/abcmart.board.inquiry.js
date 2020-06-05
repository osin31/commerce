/***
 * 1:1 문의관리
 *
 */
(function() {

	var _inquiry = abc.object.createNestedObject(window,"abc.biz.board.inquiry");

	/***************************************************************************
	*  1:1 문의관리 목록
	***************************************************************************/

	// 1:1문의 목록 그리드
	_inquiry.inquiryGridSheet = function() {

		var initInquiryGridSheet = {};
		var pageCount = $('#pageCount').val();

		initInquiryGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initInquiryGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initInquiryGridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"memberCnslSeq",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 1:1 문의번호
			,	{Header:"",				Type:"Text",		SaveName:"checkAtchFile",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 첨부파일 유무
			,	{Header:"",				Type:"Text",		SaveName:"vndrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 담당업체코드
			,	{Header:"",				Type:"Text",		SaveName:"memberNo",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 회원번호
			,	{Header:"",				Type:"Text",		SaveName:"aswrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 답변 관리자 번호
			,	{Header:"",				Type:"Text",		SaveName:"cnslGbnCode",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 상담구분코드
			,	{Header:"",				Type:"Text",		SaveName:"vndrGbnType",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 업체구분
			,	{Header:"번호",			Type:"Seq",			SaveName:"",					Width: 10,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"답변상태",		Type:"Text",		SaveName:"aswrStatName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"문의유형",		Type:"Text",		SaveName:"cnslTypeName",		Width: 60,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"제목",			Type:"Html",		SaveName:"inqryTitleText",		Width: 120,		Align:"Left",		Edit:0, 	Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",			Width: 30,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"담당업체",		Type:"Text",		SaveName:"vndrName",			Width: 50,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"회원",			Type:"Text",		SaveName:"memberInfo",			Width: 50,		Align:"Center",		Edit:0, 	Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"문의일시",		Type:"Text",		SaveName:"inqryDtm",			Width: 40,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"답변자",			Type:"Text",		SaveName:"aswrInfo",			Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"답변일시",		Type:"Date",		SaveName:"aswrDtm",				Width: 0,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("inquiryGrid"), "inquiryGridSheet", "100%", "429px");
		IBS_InitSheet(inquiryGridSheet, initInquiryGridSheet);
		// Grid 건수 정보 표시
		inquiryGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		inquiryGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		inquiryGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		inquiryGridSheet.SetExtendLastCol(1);
		
		$("#searchCnslTypeDtlCode").hide();
	}
	//고객의소리 그리드
	_inquiry.voiceOfCustomerGridSheet = function() {

		var initVoiceOfCustomerGridSheet = {};
		var pageCount = $('#pageCount').val();

		initVoiceOfCustomerGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initVoiceOfCustomerGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initVoiceOfCustomerGridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"memberCnslSeq",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 1:1 문의번호
			,	{Header:"",				Type:"Text",		SaveName:"vndrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 담당업체코드
			,	{Header:"",				Type:"Text",		SaveName:"memberNo",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 회원번호
			,	{Header:"",				Type:"Text",		SaveName:"aswrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 답변 관리자 번호
			,	{Header:"번호",			Type:"Seq",			SaveName:"",					Width: 10,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"답변상태",		Type:"Text",		SaveName:"aswrStatName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"유형",			Type:"Text",		SaveName:"cnslTypeName",		Width: 60,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"제목",			Type:"Html",		SaveName:"inqryTitleText",		Width: 120,		Align:"Left",		Edit:0, 	Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"문의사이트",		Type:"Text",		SaveName:"siteName",			Width: 30,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"회원",			Type:"Text",		SaveName:"memberInfo",			Width: 50,		Align:"Center",		Edit:0, 	Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"문의일시",		Type:"Text",		SaveName:"inqryDtm",			Width: 40,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"답변자",			Type:"Text",		SaveName:"aswrInfo",			Width: 30,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"답변일시",		Type:"Date",		SaveName:"aswrDtm",				Width: 0,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("voiceOfCustomerGrid"), "voiceOfCustomerGridSheet", "100%", "429px");
		IBS_InitSheet(voiceOfCustomerGridSheet, initVoiceOfCustomerGridSheet);
		// Grid 건수 정보 표시
		voiceOfCustomerGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		voiceOfCustomerGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		voiceOfCustomerGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		voiceOfCustomerGridSheet.SetExtendLastCol(1);
		
	}


	_inquiry.doActionCounselScript = function(sAction) {
		var _url = "/board/inquiry/";
		var _method = "";

		switch(sAction) {
		case "search" : // 목록 조회
			_method = "read-list";

			// 기간검색 유형
			var _searchDateKey = $("#searchDateKey").val();
			var fromDate = new Date($('#fromDate')[0].value);
			var toDate = new Date($('#toDate')[0].value);
			var searchYear = new Date(toDate);
			searchYear.setFullYear(toDate.getFullYear() - 1);
			
			if(!(abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()))){
				if(abc.text.allNull($("#searchDateKey").val())){
					alert("기간검색 구분값을 선택해주세요.");
					$("#searchDateKey").focus();
					return false;
				}
			}
			
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
			if(fromDate < searchYear) {
				alert("검색기간은 1년을 넘을 수 없습니다.\n검색기간을 재설정 해 주십시오.");
				return false;
			}

			var _searchChageVndr = $("#searchChageVndr").val(); // 담당업체
			var _searchChageVndrValue = $("#searchChageVndrValue"); // 업체 검색어

			// 검색어 대상 필드
			var searchKey = $("#searchKey option:selected");

			// 검색어
			var searchValue = $("#searchValue");

			if (_searchChageVndr == "vndrNm" && abc.text.isBlank(_searchChageVndrValue.val())) {
				alert("검색할 업체명을 입력 하세요.");
				_searchChageVndrValue.focus();
				return false;
			}

			// 검색어 대상 필드 선택 후 검색어 미 입력
			if(! abc.text.isBlank(searchKey.val()) && abc.text.isBlank(searchValue.val())) {
				alert('검색어를 입력 하세요.');
				searchValue.focus();
				return false;
			}

			// 검색어 대상 필드 선택 후 검색어 미 입력
			if(abc.text.isBlank(searchKey.val()) && ! abc.text.isBlank(searchValue.val())) {
				alert('검색어 구분값을 선택해주세요.');
				$("#searchKey").focus();
				return false;
			}
			if($("#searchKey").val() == "memberName"){
				if(abc.text.allNull($("#searchValue").val())
						&& !abc.text.allNull($("#hdphnBackNoText").val())){
					alert("회원명을 입력해주세요.");
					return false;
				}
				if(!abc.text.allNull($("#searchValue").val()) 
						&& abc.text.allNull($("#hdphnBackNoText").val())){
					alert("휴대폰번호 뒷자리를 입력해주세요.");
					return false;
				}
				if(!abc.text.allNull($("#searchValue").val()) 
						&& $("#hdphnBackNoText").val().length < 4){
					alert("휴대폰번호 뒤 4자리를 입력해주세요.");
					return false;
				}
			}

			// 페이지리스트 목록 개수
			var pageCount = $("#pageCount").val();

			var param = { url : _url + _method
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "inquiryGridSheet" };

			DataSearchPaging(param);
			
			break;
		case "save" : // 수정
			_method = "update";
			
			var aswrCnslTypeCode	= $("#aswrCnslTypeCode option:selected");		// 상담유형 코드
			var aswrCnslTypeDtlCode	= $("#aswrCnslTypeDtlCode option:selected");	// 상담구분 코드

			var cnslScriptTitleText = $("#cnslScriptTitleText option:selected");	// 답변 제목 선택 유무.
			var aswrContText		= $("#aswrContText");							// 답변 내용
			var memberCnslSeq		= $("#memberCnslSeq");

			if("" == $("#vndrAsnrNo").val()){
				this.escalationCheck();
				this.holdAswrCheck();
			}else{
				if(!($("#aswrStatCode").val() == abc.consts.ASWR_STAT_CODE_COM_ASWR)){
					alert("입점사에서 처리중입니다.");
					return false;
				}
			}
			var aswrStatCode = $("#aswrStatCode").val();
			$("#cnslScriptSeq").val(cnslScriptTitleText.val());

			// 답변 완료일경우 체크
			if (typeof aswrStatCode != "undefined" && aswrStatCode == "10002") {
				if(abc.text.isBlank(aswrCnslTypeCode.val())) {
					alert("상담유형 코드를 선택하세요.");
					$("#aswrCnslTypeCode").focus();
					return false;
				}
				if($("#aswrCnslTypeCode").val() !="etc"){
					if(abc.text.isBlank(aswrCnslTypeDtlCode.val())) {
						alert("상담구분 코드를 선택하세요.");
						$("#aswrCnslTypeDtlCode").focus();
						return false;
					}
					
					if(abc.text.isBlank(cnslScriptTitleText.val())) {
						alert("스크립트 제목을 선택하세요.");
						$("#cnslScriptTitleText").focus();
						return false;
					}
				}
				if(abc.text.isBlank(aswrContText.val())) {
					alert("답변내욜 입력하세요.");
					$("#aswrContText").focus();
					return false;
				}
			}
			
			var formData = new FormData($('#frmInquiry')[0]);
			
			$.ajax({
				type:"post",
				url: _url + _method,
				processData: false,
				contentType: false,
				data: formData
			}).done(function(data, textStatus, jqXHR) {
				if (data.code == abc.consts.BOOLEAN_TRUE) {
					alert("수정되었습니다.");
					if(!abc.text.allNull(abc.param.getParams().cnslGbnCode)){
						opener.parent.abc.biz.board.inquiry.doActionCounselScript("search");
					}else{
						opener.parent.abc.memberDetailTabReload();
					}
					window.close();
				} else {
					alert(data.Message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});

			break;
		case "memoSave" :  //관리자 메모 저장
			if($("#cnslGbnCode").val() == abc.consts.CNSL_GBN_CODE_VOC){
				_url = "/board/voiceofcustomer/";
			}
			_method = "create-adminmemo";

			$.ajax({
				type:"post",
				url: _url + _method,
				data: $("#frmAdminMemo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				if(data.code == abc.consts.BOOLEAN_TRUE){
					var addArea;
					var rgstDtm = new Date(data.rgstDtm);
					addArea  = "<li id='adminMemoArea"+data.cnslMemoSeq+"'>";
					addArea +=  	"<span class='msg-list-info'>";
					addArea +=  		"<span class='user-info'>";
					addArea +=  			"<span class='user-id'>"+data.rgsterDpName+"</span>";
					addArea +=  		"</span>";
					addArea +=  		"<span class='regist-date-wrap'>";
					addArea +=  			"<span class='regist-date'>"+rgstDtm.format()+"</span>";
					addArea +=  			"<a href='#' value='"+data.cnslMemoSeq+"' rgsterno='"+ data.rgsterNo +"' name='memoDeleteBtn' class='btn-msg-list-del'><i class='ico ico-del'><span class='offscreen'>메모 삭제</span></i></a>";
					addArea +=  		"</span>";
					addArea +=  	"</span>";
					addArea +=  	"<p class='msg-desc'>"+data.memoText+"</p>";
					addArea +=  "</li>";
					$("#memoAddArea").prepend(addArea);
					$("#adminMemoText").val("");
					alert("저장되었습니다.");
				}else{
					alert(data.Message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다.");
				console.log(jqXHR.responseJSON);
			});

			break;
		case "voiceOfCustomerSearch" : // 목록 조회
			_url = "/board/voiceofcustomer/"
			_method = "read-list";

			// 기간검색 유형
			var _searchDateKey = $("#searchDateKey").val();
			var fromDate = new Date($('#fromDate')[0].value);
			var toDate = new Date($('#toDate')[0].value);
			var searchYear = new Date(toDate);
			searchYear.setFullYear(toDate.getFullYear() - 1)
			
			if(!(abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()))){
				if(abc.text.allNull($("#searchDateKey").val())){
					alert("기간검색 구분값을 선택해주세요.");
					$("#searchDateKey").focus();
					return false;
				}
			}
			
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
				return false;
			}
			if(fromDate < searchYear) {
				alert("검색기간은 1년을 넘을 수 없습니다.\n검색기간을 재설정 해 주십시오.");
				return false;
			}
			
			// 검색어 대상 필드
			var searchKey = $("#searchKey option:selected");

			// 검색어
			var searchValue = $("#searchValue");


			// 검색어 대상 필드 선택 후 검색어 미 입력
			if(! abc.text.isBlank(searchKey.val()) && abc.text.isBlank(searchValue.val())) {
				alert('검색어를 입력 하세요.');
				searchValue.focus();
				return false;
			}

			// 검색어 대상 필드 선택 후 검색어 미 입력
			if(abc.text.isBlank(searchKey.val()) && ! abc.text.isBlank(searchValue.val())) {
				alert('검색어 구분값을 선택해주세요.');
				$("#searchKey").focus();
				return false;
			}
			
			if($("#searchKey").val() == "memberName"){
				if(abc.text.allNull($("#searchValue").val())
						&& !abc.text.allNull($("#hdphnBackNoText").val())){
					alert("회원명을 입력해주세요.");
					return false;
				}
				if(!abc.text.allNull($("#searchValue").val()) 
						&& abc.text.allNull($("#hdphnBackNoText").val())){
					alert("휴대폰번호 뒷자리를 입력해주세요.");
					return false;
				}
				if(!abc.text.allNull($("#searchValue").val()) 
						&& $("#hdphnBackNoText").val().length < 4){
					alert("휴대폰번호 뒤 4자리를 입력해주세요.");
					return false;
				}
			}

			// 페이지리스트 목록 개수
			var pageCount = $("#pageCount").val();

			var param = { url : _url + _method
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "voiceOfCustomerGridSheet" };

			DataSearchPaging(param);

			break;
		case "voiceOfCustomerSave" : // 수정
			_url = "/board/voiceofcustomer/"
			_method = "update";

			var aswrCnslTypeCode	= $("#aswrCnslTypeCode option:selected");		// 상담유형 코드
			var aswrCnslTypeDtlCode	= $("#aswrCnslTypeDtlCode option:selected");	// 상담구분 코드
			var cnslScriptTitleText = $("#cnslScriptTitleText option:selected");	// 답변 제목 선택 유무.
			var aswrContText		= $("#aswrContText");							// 답변 내용
			var memberCnslSeq		= $("#memberCnslSeq");

			this.holdAswrCheck();
			var aswrStatCode		= $("#aswrStatCode").val();	// 답변 상태

			$("#cnslScriptSeq").val(cnslScriptTitleText.val());
			
			// 답변 완료일경우 체크
			if (typeof aswrStatCode != "undefined" && aswrStatCode == "10002") {
				if(abc.text.isBlank(aswrCnslTypeCode.val())) {
					alert("상담유형 코드를 선택하세요.");
					$("#aswrCnslTypeCode").focus();
					return false;
				}
				if($("#aswrCnslTypeCode").val() !="etc"){
					if(abc.text.isBlank(aswrCnslTypeDtlCode.val())) {
						alert("상담구분 코드를 선택하세요.");
						$("#aswrCnslTypeDtlCode").focus();
						return false;
					}

					if(abc.text.isBlank(cnslScriptTitleText.val())) {
						alert("스크립트 제목을 선택하세요.");
						$("#cnslScriptTitleText").focus();
						return false;
					}
				}

				if(abc.text.isBlank(aswrContText.val())) {
					alert("답변내욜 입력하세요.");
					$("#inqryContText").focus();
					return false;
				}
			}
			
			var $form = $("#frmInquiry");

			$.ajax({
				type:"post",
				url: _url + _method,
				data: $form.serialize()
			}).done(function(data, textStatus, jqXHR) {
				if (data.code == abc.consts.BOOLEAN_TRUE) {
					alert("수정되었습니다.");
					if(!abc.text.allNull(abc.param.getParams().cnslGbnCode)){
						opener.parent.abc.biz.board.inquiry.doActionCounselScript("voiceOfCustomerSearch");
					}else{
						opener.parent.abc.biz.member.member.inquiry.memberInquiryDoAction("search")
					}
					window.close();
				} else {
					alert(data.Message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
			break;
		case "saveInquiryLeave" : // 탈퇴회원 1:1문의 저장
			this.holdAswrCheck();
			
			var formData = new FormData($('#frmInquiry')[0]);
			
			$.ajax({
				type:"post",
				url: "/board/inquiry/save-inquiry-leave",
				processData: false,
				contentType: false,
				data: formData
			}).done(function(data, textStatus, jqXHR) {
				if (data.code == abc.consts.BOOLEAN_TRUE) {
					alert("수정되었습니다.");
					if(!abc.text.allNull(abc.param.getParams().cnslGbnCode)){
						opener.parent.abc.biz.board.inquiry.doActionCounselScript("search");
					}else{
						opener.parent.abc.memberDetailTabReload();
					}
					window.close();
				} else {
					alert(data.Message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});

			break;
		case "saveVocLeave" : // 탈퇴회원 고객의소리 문의 저장
			this.holdAswrCheck();
			
			var $form = $("#frmInquiry");

			$.ajax({
				type:"post",
				url: "/board/voiceofcustomer/save-voc-leave",
				data: $form.serialize()
			}).done(function(data, textStatus, jqXHR) {
				if (data.code == abc.consts.BOOLEAN_TRUE) {
					alert("수정되었습니다.");
					if(!abc.text.allNull(abc.param.getParams().cnslGbnCode)){
						opener.parent.abc.biz.board.inquiry.doActionCounselScript("voiceOfCustomerSearch");
					}else{
						opener.parent.abc.biz.member.member.inquiry.memberInquiryDoAction("search")
					}
					window.close();
				} else {
					alert(data.Message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
			break;
		}
	}

	//답변보류 체크박스
	_inquiry.holdAswrCheck = function(){
		if($("#aswrHoldCheck").prop("checked")){
			$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_HOLD_ASWR);
		}else{
			$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_COM_ASWR);
		}
	}
	//이관 체크
	_inquiry.escalationCheck = function(){
		if($("#chkEscalation").prop("checked")){
			$("#vndrAssignYn").val(abc.consts.BOOLEAN_TRUE);
		}else{
			$("#vndrAssignYn").val(abc.consts.BOOLEAN_FALSE);
		}
	}
	
	
	//초기화 버튼
	_inquiry.resetButton = function(){
		$.form("#frmSearch").reset();
		$("#cnslGbnCode_on").prop('checked', true);
	}

	//관리자 메모 삭제
	_inquiry.removeAdminMemo = function(memberCnslSeq,cnslMemoSeq){
		var _url = "";
		if($("#cnslGbnCode").val() == abc.consts.CNSL_GBN_CODE_INQRY){
			_url = "/board/inquiry/"; 
		}else{
			_url = "/board/voiceofcustomer/"
		}
		var _method = "remove-adminmemo";

		$.ajax({
			type:"post",
			url: _url + _method,
			data: {
				"memberCnslSeq" : memberCnslSeq,
				"cnslMemoSeq" : cnslMemoSeq
			}
		}).done(function(data, textStatus, jqXHR) {
			if(data.result == 1){
				alert("삭제되었습니다.");
				$("#adminMemoArea"+cnslMemoSeq).remove();
			}else{
				alert("오류가 발생했습니다.");
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}
	
	
	//AS팝업
	_inquiry.asPopUp = function(asAcceptSeq, asAcceptPrdtSeq){
		var url = "/afterservice/afterservice/read-afterservice-detail-pop";
		var params = {};

		params.asAcceptNo = asAcceptSeq,
		params.asAcceptPrdtSeq = asAcceptPrdtSeq,

		abc.open.popup({
			winname :	"AS상세팝업",
			url 	:	url,
			width 	:	0,
			height	:	1170,
			params	:	params
		});
	}
	
	//상담유형 상세코드 변경 및 답변상태에 따라 답변보류 영역 조정
	_inquiry.detailAswrCnslTypeCodeSet = function() {
		if($("#aswrCnslTypeCode option:selected").val()=="etc"){
			$("#aswrCnslTypeDtlCode").hide();
			$("#cnslScriptSeq").val("");
			$("#cnslScriptTitleText").val("");
			var titleIndex = 0;
			
			$("#cnslScriptTitleText").find("option").each(function(){
				if(titleIndex > 0){
					$(this).remove();
				}
				titleIndex++;
			});
		}else{
			$("#aswrCnslTypeDtlCode").show();
		}
		if($("#aswrStatCode").val() == abc.consts.ASWR_STAT_CODE_COM_ASWR){
			$("#chkEscalation").prop("disabled", true);
			$("#aswrHoldCheck").prop("disabled", true);
		}
	}
	
	//이관 상태에 따라 이관영역 조정
	_inquiry.initEscalationCheck = function(vndrPrdtNoText){
		if('' == $("#vndrNo").val()){
			$("#chkEscalation").prop("disabled", true);
		}
		if($("#mmnyPrdtYn").val() == abc.consts.BOOLEAN_TRUE){
			$("#chkEscalation").prop("disabled", true);
		}
		if("" != $("#vndrAsnrNo").val()){
			$("#chkEscalation").prop("disabled", true);
		}
	}
	
	
	_inquiry.inquiryExcelDown = function(){
		if(inquiryGridSheet.SearchRows() == 0){
			alert("다운 가능한 검색 목록이 존재하지 않습니다.");
			return false;
		}
		
		$("#frmSearch")
		.prop("action","/board/inquiry/download-excel")
		.prop("method", "post")
		.submit();
	}
	
	_inquiry.vocExcelDown = function(){
		if(voiceOfCustomerGridSheet.SearchRows() == 0){
			alert("다운 가능한 검색 목록이 존재하지 않습니다.");
			return false;
		}
		
		$("#frmSearch")
		.prop("action","/board/voiceofcustomer/download-excel")
		.prop("method", "post")
		.submit();
	}
	
	/**
	 * 첨부파일 관련
	 * */
	_inquiry.checkFileUpload = false;
	_inquiry.aswrFileArray = new Array();
	_inquiry.MaxAswrFileCnt = 0;
	_inquiry.aswrFileCnt = 0;

	_inquiry.inqryFileArray = new Array();
	_inquiry.MaxInqryFileCnt = 0;
	_inquiry.inqryFileCnt = 0;

	//파일 validate
	_inquiry.fileValidate = function(aswrFileId, checkExtStr, maxSize) {
		var fileId = "#" + aswrFileId;

		//파일정보
		var fileObj= $(fileId)[0].files[0];
		var img_all_names, ext, size;
		
		if(fileObj != null || fileObj != undefined){
			img_all_names = fileObj['name'].split('.');
			ext = img_all_names[img_all_names.length -1].toLowerCase();//확장자
			size = fileObj['size'];//파일사이즈
		}
		
		if(!(checkExtStr.indexOf(ext) > -1)) {
			alert(checkExtStr + ' 해당 확장자의 파일만 업로드 가능합니다.');
			$(fileId).replaceWith( $(fileId).clone(true) ); //초기화
			$(fileId).val("");
			$("#aswrFileLi" + aswrFileId).remove();
			_inquiry.aswrAtchCnt--; // validate 불통과라 -1

			return false;
		}
		if(size >= parseInt(1048576 * maxSize)) {
			alert(maxSize + "MB 이하의 파일만 가능합니다.");
			$(fileId).replaceWith( $(fileId).clone(true) ); //초기화
			$(fileId).val("");
			$("#aswrFileLi" + aswrFileId).remove();
			_inquiry.aswrAtchCnt--; // validate 불통과라 -1

			return false;
		}

		return true;
	}
	//검색어 초기화와 포커스 이동
	_inquiry.searchValueSet = function(){
		var searchKey = $("#searchKey option:selected").val();

		//검색어 값을 초기화 후 포커스를 이동
//		$("#searchValue").val('');
		$("#searchValue").focus();
		$("#hdphnBackNoText").hide();
		if(searchKey == "inqryTitleText"){
			$("#searchValue").off().on('input', function(event){
				
			});
		}else if(searchKey == "memberName"){
			$("#searchValue").off().on('input', function(event){
				abc.text.validateOnlyString(this);
			});
			$("#hdphnBackNoText").show();
		}else if(searchKey == "rgsterName"){
			$("#searchValue").off().on('input', function(event){
				abc.text.validateOnlyString(this);
			});
		}else{
			$("#searchValue").off().on('input', function(event){
				abc.text.validateOnlyAlphabetNum(this);
			});
		}
		$("#searchValue").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});
	}


	//첨부파일 화면에 그리는 메소드
	_inquiry.drawAswrFile = function(aswrFileId) {
		var fileId = "#" + aswrFileId;
	    var fileNum = fileId.substr(9,9);
		var atchFileName = $(fileId)[0].files[0].name;

		var fileArea;
		fileArea +=  "<li>";
		fileArea += 	"<input type='hidden' name='atchFileGbnType' value='A'>"
		fileArea += 	"<input type='hidden' name='atchFileName' value='" + atchFileName + "'>"
		fileArea += 	"<span class='subject' name='fileName'>" + atchFileName + "</span>";
		fileArea += 	"<button type='button' class='btn-file-del' id='removeAswrFile" + fileNum + "'>";
		fileArea +=			"<span class='ico ico-fdel'><span class='offscreen'>첨부파일 삭제</span></span>";
		fileArea +=		"</button>";
		fileArea += "</li>";

		$("#aswrFileArea").append(fileArea);
	}


	/**
	 * 첨부파일 제거 메소드
	 * */
	_inquiry.removeAswrFile = function(el, memberCnslSeq) {
		_inquiry.checkFileUpload = true;

		var removeAswrFileSeq = $(el).parent().attr('id');

		$("#aswrFileLi" + _inquiry.aswrAtchCnt).show();
		$("#aswrFileLi" + (Number(_inquiry.aswrAtchCnt) + 1)).hide();

		if(removeAswrFileSeq != 0 && removeAswrFileSeq != null && removeAswrFileSeq != undefined){
			var html = "<input type='hidden' name='removeAtchFileSeq' value='" + removeAswrFileSeq + "'>";
			$(".col-wrap").prepend(html);
		}

		$(el).parent().remove();
		//var replaceNum = $(el).attr('id').substr(14, 10);
		var atchFileSeq = $(el).attr('value');

		if(Number(_inquiry.aswrAtchCnt) > 0) {
			for(var i=1; i<= _inquiry.aswrAtchCnt; i++) {
				$("#removeAtchFile" + _inquiry.aswrAtchCnt).attr("id", "removeAswrFile" + _inquiry.aswrAtchCnt-i);
			}

			_inquiry.aswrAtchCnt--;
		}

		$("#aswrFile" + atchFileSeq).replaceWith( $("#aswrFile" + atchFileSeq).clone(true) ); //초기화
		$("#aswrFile" + atchFileSeq).val("");
	}

	// 대시보드에서 더보기 클릭으로 왔을때 미답변 검색
	_inquiry.fromDashSearch = function(fromDash, tabIdx, forVoice) {
		$("#searchAswrStatCode").children('option[value="' + abc.consts.ASWR_STAT_CODE_NO_ASWR + '"]').prop("selected", true);
		
		if(tabIdx == 2) {
			$("#searchChageVndr").children('option[value="cs"]').prop("selected", true);
		}else if(tabIdx == 3) {
			$("#searchChageVndr").children('option[value="vndr"]').prop("selected", true);
		}
		
		if(forVoice == undefined){
			_inquiry.doActionCounselScript("search");
		} else if('voiceCustomer') {
			_inquiry.doActionCounselScript("voiceOfCustomerSearch");
		}
	}





})();