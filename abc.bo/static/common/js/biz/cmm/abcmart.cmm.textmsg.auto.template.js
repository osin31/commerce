/**
 * 프로모션 메시징관리 자동 문자템플릿 관리
 */
(function() {

	var _msgtemplate = abc.object.createNestedObject(window,"abc.biz.cmm.textmsg.auto.template");
	
	var _SEND_TYPE_CODE_SMS = "10000";
	var _SEND_TYPE_CODE_LMS = "10001";

	var _SMS_TEXT_MAX_BYTE = "80"; 	// sms 전송 가능 바이트
	var _LMS_TEXT_MAX_BYTE = "2000";// lms 전송 가능 바이트
	
	var _isDoubleCheck = false;		// 중복확인 버튼 클릭여부
	var _mesgIdCheck = "";			// 중복확인 후 수정 여부
	
	/***************************************************************************
	* 문자템플릿 관리 목록
	***************************************************************************/

	// 문자템플릿 목록 그리드
	_msgtemplate.initTextMsgTemplateSheet = function() {
		var initTextMsgTemplateSheet = {};
		var pageCount = $('#pageCount').val();

		initTextMsgTemplateSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initTextMsgTemplateSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initTextMsgTemplateSheet.Cols = [
				{Header:"" ,		 	Type:"Status",		SaveName:"status",					Width: 0,		Align:"", 			Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"mesgTmplSeq",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"moderNo",					Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"번호",			Type:"Seq",			SaveName:"",						Width: 15,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",				Width: 25,		Align:"Center",		Edit:0,		Sort:0			}
//			,	{Header:"발송방식",		Type:"Text",		SaveName:"sendTypeCodeName",		Width: 20,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"발송코드",		Type:"Text",		SaveName:"mesgId",					Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"템플릿유형",		Type:"Text",		SaveName:"mesgTypeCodeName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"템플릿명",		Type:"Html",		SaveName:"mesgTmplName",			Width: 50,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"	}
			,	{Header:"내용",			Type:"Html",		SaveName:"mesgContText",			Width: 200,		Align:"Left",		Edit:0,		Sort:0			}
			,	{Header:"수정자",			Type:"Text",		SaveName:"maskingDispModerName",	Width: 30,		Align:"Center",		Edit:0,		Sort:0, 		}
			,	{Header:"수정일시",		Type:"Date",		SaveName:"modDtm",					Width: 0,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("textMsgTemplateGrid"), "textMsgTemplateSheet", "100%", "429px");
		IBS_InitSheet(textMsgTemplateSheet, initTextMsgTemplateSheet);
		// Grid 건수 정보 표시
		textMsgTemplateSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		textMsgTemplateSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		textMsgTemplateSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		textMsgTemplateSheet.SetExtendLastCol(1);
	}

	// 문자 메시지 템플릿 관리 Action 처리
	_msgtemplate.doActionTextMsgTemplate = function (sAction) {
		var _url = "/cmm/msg/text-template/auto/";
		var _method	= "";
		
		switch(sAction) {
			case "search" :	// 목록 조회
				_method = "read-list";

				var _searchDateKey = $("#searchDateKey option:selected").val();
				var _fromDate = $("#fromDate").val(); // 검색 시작일
				var _toDate = $("#toDate").val();     // 검색 종료일

				if (! abc.text.isBlank(_searchDateKey)) {
					if (abc.text.allNull(_fromDate) || abc.text.allNull(_toDate)) {
						alert("기간검색을 선택하세요.");
						return false;
					}
				}

				if(!abc.date.searchValidate()){
					return false;
				}

				// 페이지리스트 목록 개수
				var pageCount = $("#pageCount").val();

				var param = { url : _url + _method
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "textMsgTemplateSheet" };

				DataSearchPaging(param);

				break;
			case "save" : // 저장
				var _form = $("#frmTextMsgTemplate");
				var _siteNo = $("input:radio[name=siteNo]:checked");
				var _mesgTypeCode = $("#mesgTypeCode option:selected"); // 문자 템플릿 유형
				var _mesgTmplName = $("#mesgTmplName"); // 템플릿 명
				var _mesgContText = $("#mesgContText");  // kko내용
				var _failMesgContText = $('#failMesgContText'); //페일백 메시지
				var _mesgId = $("#mesgId"); // 발송코드
			
				var _mesgTmplSeq = $("#mesgTmplSeq").val(); // 문자템플릿 순번
			
				_method = "create";

				if (abc.text.isBlank(_siteNo.val())) {
					alert("사이트를 선택하세요.");
					_siteNo.focus();
					return false;
				}
				
				if (abc.text.isBlank(_mesgTypeCode.val())) {
					alert("템플릿 유형을 선택하세요.");
					_mesgTypeCode.focus();
					return false;
				}

				if (abc.text.isBlank(_mesgTmplName.val())) {
					alert("템플릿 명을 입력하세요.");
					_mesgTmplName.focus();
					return false;
				}

				if (abc.text.isLimitLength(_mesgTmplName.val(), 100)) {
					alert("템플릿 명은 100Byte 까지 입력 가능 합니다.");
					_mesgTmplName.focus();
					return false;
				}

				if (abc.text.isBlank(_mesgContText.val())) {
					alert("알림톡 메시지 내용을 입력하세요.");
					_mesgContText.focus();
					return false;
				}
				
				if (abc.text.isBlank(_failMesgContText.val())) {
					alert("페일백 메시지 내용을 입력하세요.");
					_failMesgContText.focus();
					return false;
				}

				if (abc.text.isLimitLength(_mesgContText.val(), _LMS_TEXT_MAX_BYTE)) {
					alert("내용은 " + _LMS_TEXT_MAX_BYTE + "Byte 까지 입력 가능 합니다.");
					_mesgContText.focus();
					return false;
				}
				
				if (abc.text.isLimitLength(_failMesgContText.val(), _LMS_TEXT_MAX_BYTE)) {
					alert("내용은 " + _LMS_TEXT_MAX_BYTE + "Byte 까지 입력 가능 합니다.");
					_failMesgContText.focus();
					return false;
				}
				
				// 발송코드 입력확인
				if (abc.text.isBlank(_mesgId.val())) {
					alert("발송코드를 입력하세요.");
					_mesgId.focus();
					return false;
				}
				
				// 중복확인 체크
				if(!_mesgId.prop("readonly")) {
					if(_mesgId.val() !== $("#originMesgId").val()) {
						
						if(!_isDoubleCheck) {
							alert("중복확인 버튼을 클릭 해주세요.");
							_mesgId.focus();
							_isDoubleCheck=false;
							return false;
						}
						
						if(_mesgIdCheck != _mesgId.val()) {
							alert("중복확인 버튼을 다시 해주세요.");
							_mesgIdCheck="";
							_mesgId.focus();
							return false;
						}
					}
					
					
				}

				if (! abc.text.isBlank(_mesgTmplSeq)) {
					_method = "modify";
				}

				$.ajax({
						url 	: _url + _method,
						method	: "post",
						data	: _form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					alert(data.Message);

					if (data.code == abc.consts.BOOLEAN_TRUE) {
						if (opener != null) {
							opener.parent.abc.biz.cmm.textmsg.auto.template.doActionTextMsgTemplate("search");
							window.self.close();
						}
					}

				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert(_errorMsg);
					console.log(jqXHR.responseJSON);
				});
			
				break;
				
			case "doubleCheck" : // 중복확인
				var _mesgId = $("#mesgId");
				
				_method = "double-check";
				
				if(abc.text.isBlank(_mesgId.val())) {
					alert("발송코드를 입력해주세요.");
					_mesgId.focus();
					return false;
				}
				
				var _mesgIdValue = {
					"mesgId" : _mesgId.val()	
				}
				
				$.ajax({
					url		: _url + _method,
					method	: "get",
					data 	: _mesgIdValue,
				}).done(function(data, textStatus, jqXHR) {
					if(data === 0) {	// 동일한 mesgId 개수
						_isDoubleCheck = true;
						_mesgIdCheck = _mesgId.val();
						alert("사용 가능한 코드입니다.");
					}else {
						_isDoubleCheck = false;
						alert("발송코드가 이미 존재합니다.");
					}
					
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("중복확인 검사에 실패하였습니다.\n" + jqXHR);
				});
				break;

			case "delete" : // 삭제
				var _form = $("#frmTextMsgTemplate");
				_method = "delete";
				if (confirm("문자 템를릿을 삭제 하시겠습니까")) {
					$.ajax({
							url 	: _url + _method,
							method	: "post",
							data	: _form.serialize()
					}).done(function(data, textStatus, jqXHR) {
						alert(data.Message);

						if (data.code == abc.consts.BOOLEAN_TRUE) {
							if (opener != null) {
								opener.parent.abc.biz.cmm.textmsg.auto.template.doActionTextMsgTemplate("search");
								window.self.close();
							}
						}

					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert(_errorMsg);
						console.log(jqXHR.responseJSON);
					});
				}
				break;
		}
	}

	// 페일백 메시지 길이 체크
	_msgtemplate.failConTextByteLengthCheck = function (str) {
		var strByteLength = str.uniLength();

		$("#smsContTextByte").text(strByteLength);
		$("#lmsContTextByte").text(strByteLength);

		if (strByteLength <= _SMS_TEXT_MAX_BYTE) {
			$("#smsMessage").hide();
			$("#lmsMesage").hide();
			$("#sendTypeCode").val(_SEND_TYPE_CODE_SMS);
		} else if (_SMS_TEXT_MAX_BYTE < strByteLength && strByteLength <= _LMS_TEXT_MAX_BYTE) {
			$("#smsMessage").show();
			$("#lmsMesage").show();
			$("#sendTypeCode").val(_SEND_TYPE_CODE_LMS);
		} else if (strByteLength > _LMS_TEXT_MAX_BYTE) {
			var subStr = abc.text.substringByte(str, _LMS_TEXT_MAX_BYTE);
			strByteLength = subStr.uniLength();
			
			$("#smsContTextByte").text(strByteLength);
			$("#lmsContTextByte").text(strByteLength);

			$("#smsMessage").show();
			$("#lmsMesage").show();
			
			$("#failMesgContText").val(subStr);
			alert("문자 템플릿은 2,000Byte까지만 입력 가능 합니다.");
			return false;
		}
	}
	
	// 알림톡(kko) 메시지 길이 체크
	_msgtemplate.kkoLengthCheck = function(str){
		var contLength = str.uniLength();
		$('#contTextByte').text(contLength);
		
		if (contLength <= _SMS_TEXT_MAX_BYTE) {
			$("#kkoMessage").hide();

		} else if (_SMS_TEXT_MAX_BYTE < contLength && contLength <= _LMS_TEXT_MAX_BYTE) {
			$("#kkoMessage").show();

		} else if (contLength > _LMS_TEXT_MAX_BYTE) {
			var subStr = abc.text.substringByte(str, _LMS_TEXT_MAX_BYTE);
			$("#contTextByte").text(subStr.uniLength());

			$("#kkoMessage").show();
			
			$("#mesgContText").val(subStr);
			alert("문자 템플릿은 2,000Byte까지만 입력 가능 합니다.");
			return false;
		}
	};

	// 템플릿 문자 내용 길이 체크
//	_msgtemplate.contTextLengthCheck = function (sendTypeCode, str) {
//		sendTypeCode = abc.text.isBlank(sendTypeCode) ? _SEND_TYPE_CODE_SMS : sendTypeCode;
//		var strByteLength = str.uniLength();
//
//		$("#mesgContTextByteLength").val(strByteLength);
//
//		if (strByteLength <= _SMS_TEXT_MAX_BYTE) {
//			$("#sendTypeCode").val(_SEND_TYPE_CODE_SMS);
//			$("#mesgContTextMaxByte").text(_SMS_TEXT_MAX_BYTE);
//			$("#mesgTitleText").attr("readonly", true);
//		} else if (_SMS_TEXT_MAX_BYTE < strByteLength && strByteLength <= _LMS_TEXT_MAX_BYTE) {
//			if (sendTypeCode != _SEND_TYPE_CODE_LMS) {
//				if (confirm(_SMS_TEXT_MAX_BYTE + "Byte 이상 입력 하시면, LMS로 전송 됩니다. LMS로 전송 하시겠습니까?")) {
//					$("#sendTypeCode").val(_SEND_TYPE_CODE_LMS);
//					$("#mesgContTextMaxByte").text(_LMS_TEXT_MAX_BYTE);
//					$("#mesgTitleText").attr("readonly", false);
//				} else {
//					$("#failMesgContText").val(abc.text.substringByte(str, _SMS_TEXT_MAX_BYTE));
//					$("#mesgTitleText").attr("readonly", true);
//				}
//			}
//		} else if (strByteLength > _LMS_TEXT_MAX_BYTE) {
//			alert(_LMS_TEXT_MAX_BYTE + "자 까지만 입력 가능 합니다.");
//			$("#failMesgContText").val(abc.text.substringByte(str, _LMS_TEXT_MAX_BYTE));
//		}
//	}
	
	// 자동 문자 템플릿명 변경 선택
	_msgtemplate.msgTmplchange = function(_form, _url) {
		var _targetSelectBox = "mesgTmplSeq";
		var method = "/text-msg-auto-tmpl-read-list";
		
		$.ajax({
			url: _url + method ,
			dataType : "json",
			data: _form.serialize(),
			async:false
		}).done(function(data, textStatus, jqXHR) {
			var _len = $("#" + _targetSelectBox + " option").length;

			for(var idx = _len -1; idx > 0; idx --) {
				$("#" + _targetSelectBox + " option:eq(" + idx + ")").remove();
			}

			$.each(data, function() {
				$("#" + _targetSelectBox).append("<option value='" + this.mesgTmplSeq + "'>" + this.mesgTmplName + "</option>");
			});

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			$("#mesgTypeCode option:eq(0)").prop("selected", true);
			console.log(jqXHR.responseJSON);
		});
	};
	
	// 문자 템플릿 목록 엑셀 다운로드
	$("#textMsgExcelDown").click(function(){
		
		if(textMsgTemplateSheet.GetTotalRows() === 0) {
			alert("다운로드 할 목록이 없습니다.");
				return false;
		}
			
		$("#frmSearch")
			.prop("action","/cmm/msg/text-template/auto/excel-down")
			.prop("method", "post")
			.submit();
			
	});
	
})();