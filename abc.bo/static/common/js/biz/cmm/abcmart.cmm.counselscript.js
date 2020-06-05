/***
 * 관리자 상담스크립트관리.
 *
 */
(function() {

	var _counselscript = abc.object.createNestedObject(window,"abc.biz.cmm.counselscript");

	/***************************************************************************
	* 상담스크립트 관리 목록
	***************************************************************************/

	// 상담스크립트 목록 그리드
	_counselscript.initCounselScriptSheet = function() {

		var initCounselScriptSheet = {};
		var pageCount = $('#pageCount').val();

		initCounselScriptSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initCounselScriptSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initCounselScriptSheet.Cols = [
				{Header:"" ,		 	Type:"Status",		SaveName:"status",				Width: 0,		Align:"", 			Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"cnslScriptSeq",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"번호",			Type:"Seq",			SaveName:"",					Width: 10,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상담메뉴",		Type:"Text",		SaveName:"cnslGbnCodeName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상담구분",		Type:"Text",		SaveName:"cnslTypeCodeName",	Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상담유형",		Type:"Text",		SaveName:"cnslTypeDtlCodeName",	Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"스크립트제목",		Type:"Html",		SaveName:"cnslScriptTitleText",	Width: 150,		Align:"Left",		Edit:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"사용여부",		Type:"Combo",		SaveName:"useYn",				Width: 30,		Align:"Center",		Edit:0,		ComboText : "사용|사용안함", ComboCode : "Y|N"}
			,	{Header:"작성자",			Type:"Text",		SaveName:"maskingRgsterName",	Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"작성일시",		Type:"Date",		SaveName:"rgstDtm",				Width: 0,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("counselScriptGrid"), "counselScriptSheet", "100%", "429px");
		IBS_InitSheet(counselScriptSheet, initCounselScriptSheet);
		// Grid 건수 정보 표시
		counselScriptSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		counselScriptSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		counselScriptSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		counselScriptSheet.SetExtendLastCol(1);
		
	}

	// 상담스크립트 action
	_counselscript.doActionCounselScript = function (sAction) {
		var _url = "/cmm/counselscript/";
		var _method				= "";
		switch(sAction) {
			case "search" :	// 목록 조회
				// 검색어 대상 필드
				var searchKey = $("#searchKey option:selected");

				// 검색어
				var searchValue = $("#searchValue");

				// 페이지리스트 목록 개수
				var pageCount = $("#pageCount").val();

				_method = "read-list";

				// 검색어 대상 필드 선택 후 검색어 미 입력
				if(! abc.text.isBlank(searchKey.val()) && abc.text.isBlank(searchValue.val())) {
					alert('검색어를 입력 하세요.');
					$("#searchValue").focus();
					return false;
				}

				// 검색어 대상 필드 선택 후 검색어 미 입력
				if(abc.text.isBlank(searchKey.val()) && ! abc.text.isBlank(searchValue.val())) {
					alert('검색어 구분값을 선택해주세요.');
					$("#searchKey").focus();
					return false;
				}

				var param = { url : _url + _method
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, CPage : $('#listPageNum').val()
					, sheet : "counselScriptSheet" };

				DataSearchPaging(param);
				$("#listPageNum").val("1");
				break;
			case "save" : // 저장, 수정
				var cnslScriptSeq		= $("#cnslScriptSeq");						// 상담스크립트순번
				var cnslGbnCode			= $("#cnslGbnCode option:selected");		// 상담메뉴코드
				var cnslTypeCode		= $("#cnslTypeCode option:selected");		// 상담유형코드
				var cnslTypeDtlCode		= $("#cnslTypeDtlCode option:selected");	// 상담유형상세코드
				var cnslScriptTitleText = $("#cnslScriptTitleText");				// 상담스크립트제목
				var cnslScriptContText	= $("#cnslScriptContText");					// 상담스크립트내용
				var aswrAlertType		= $("#aswrAlertType option:selected");		// 고객알림설정 Type
				var aswrAlertId 		= $("#aswrAlertId");						// 알림 템플릿 아이디

				// 상담스크립트순번 존재 유무로 등록/수정 Method 구분
				if(abc.text.isBlank(cnslScriptSeq.val())) {
					_method = "create";
				} else {
					_method = "update";
				}

				if(abc.text.isBlank(cnslGbnCode.val())) {
					alert("상담메뉴코드를 선택하세요.");
					$("#cnslGbnCode").focus();
					return false;
				}

				if(abc.text.isBlank(cnslTypeCode.val())) {
					alert("상담유형코드를 선택하세요.");
					$("#cnslTypeCode").focus();
					return false;
				}

				if(abc.text.isBlank(cnslTypeDtlCode.val())) {
					alert("상담유형상세코드를 선택하세요.");
					$("#cnslTypeDtlCode").focus();
					return false;
				}

				if(abc.text.isBlank(cnslScriptTitleText.val())) {
					alert("상담스크립트 제목을 입력하세요.");
					$("#cnslScriptTitleText").focus();
					return false;
				}

				if (abc.text.isLimitLength(cnslScriptTitleText.val(), 100)) {
					alert("상담스크립트 제목은 100Byte 까지 입력 가능 합니다.");
					$("#cnslScriptTitleText").focus();
					return false;
				}

				if(abc.text.isBlank(cnslScriptContText.val())) {
					alert("상담스크립트내용을 입력하세요.");
					$("#cnslScriptContText").focus();
					return false;
				}

				if (abc.text.isLimitLength(cnslScriptContText.val(), 4000)) {
					alert("상담스크립트 제목은 4000Byte 까지 입력 가능 합니다.");
					$("#cnslScriptContText").focus();
					return false;
				}

				if(abc.text.isBlank(aswrAlertType.val())) {
					alert("고객알림 설정을 선택하세요.");
					$("#aswrAlertType").focus();
					return false;
				}

				if(abc.text.isBlank(aswrAlertType.val())) {
					alert("고객알림 설정을 선택하세요.");
					$("#aswrAlertType").focus();
					return false;
				}

				if (aswrAlertType.val() != _ASWR_ALERT_TYPE_NON) {
					var message = "";
					var sucessYn = true;
					if (abc.text.isBlank(aswrAlertId.val())) {
						message = (aswrAlertType.val() == _ASWR_ALERT_TYPE_SMS) ? "문자 템플릿 ID를 입력하세요." : "이메일 템플릿 ID를 입력하세요.";

						sucessYn = false;
					} else if (abc.text.isLimitLength(aswrAlertId.val(), 10)) {
						message = ((aswrAlertType.val() == _ASWR_ALERT_TYPE_SMS) ? "문자 템플릿 ID" : "이메일 템플릿 ID") + "는 10Byte까지 입력 가능합니다.";
						sucessYn = false;
					}
					if (! sucessYn) {
						alert(message);
						aswrAlertId.focus();
						return sucessYn;
					}
				}

				$.ajax({
					type:"post",
					url: _url + _method,
					data: $("#frmCounselScript").serialize()
				}).done(function(data, textStatus, jqXHR) {
					var _cnslScriptSeq = "";

					if (data.code == abc.consts.BOOLEAN_TRUE) {
						_cnslScriptSeq = data.cnslScriptSeq;
						alert(data.Message);
						_url += "read-detail";

						frmSearch.action = "/cmm/counselscript?menuNo="+abc.param.getParams().menuNo;
						frmSearch.submit();
						
/*						$("#menuNo").val(abc.param.getParams().menuNo);
						$("#cnslScriptSeq1").val(_cnslScriptSeq);
						$("#frmSearch").attr("action", _url);
						$("#frmSearch")[0].submit();*/
					} else {
						alert(data.Message);
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("오류가 발생했습니다.");
					console.log(jqXHR.responseJSON);
				});

				break;
		}
	}
	
//	$("#searchKey").change(function(){
//		$("#searchValue").val("");
//	});
	
	_counselscript.initSearchForm = function(){
		$('#cnslTypeCode').hide();
		$('#cnslTypeDtlCode').hide();
	}

})();