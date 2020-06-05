/***
 * 관리자 메시징 관리
 *
 */
(function() {

	var _msgtemplate = abc.object.createNestedObject(window,"abc.biz.cmm.msgtemplate");

	var _SEND_TYPE_CODE_SMS = "10000";
	var _SEND_TYPE_CODE_LMS = "10001";

	var _SMS_TEXT_MAX_BYTE = "80"; 	// sms 전송 가능 바이트
	var _LMS_TEXT_MAX_BYTE = "2000";// lmss 전송 가능 바이트
//
	/***************************************************************************
	* 메일키 생성 관리 목록
	***************************************************************************/

	// 메일키 생성 목록 그리드
	_msgtemplate.initEmailKeySheet = function() {
		var initEmailKeySheet = {};
		var pageCount = $('#pageCount').val();

		initEmailKeySheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initEmailKeySheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initEmailKeySheet.Cols = [
				{Header:"" ,		 	Type:"Status",		SaveName:"status",					Width: 0,		Align:"", 			Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"emailTmplSeq",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"moderNo",					Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"번호",			Type:"Seq",			SaveName:"",						Width: 10,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",				Width: 20,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"메일제목",		Type:"Text",		SaveName:"emailTitleText",			Width: 100,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"	}
			,	{Header:"수정자",			Type:"Text",		SaveName:"maskingDispModerName",	Width: 30,		Align:"Center",		Edit:0,		Sort:0, 		}
			,	{Header:"발송일시",		Type:"Date",		SaveName:"sendDtm",					Width: 40,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"수정일시",		Type:"Date",		SaveName:"modDtm",					Width: 40,		Align:"Center",		Edit:0,		Sort:1,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("emailKeyGrid"), "emailKeySheet", "100%", "429px");
		IBS_InitSheet(emailKeySheet, initEmailKeySheet);
		// Grid 건수 정보 표시
		emailKeySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		emailKeySheet.SetPagingPosition(2);
		// Grid width 자동 조절
		emailKeySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		emailKeySheet.SetExtendLastCol(1);
	}

	_msgtemplate.doActionEmailKey = function (sAction) {
		var _url = "/cmm/msg/email-key/";
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
			
			if(Number(abc.text.validateStringSignRemove(_fromDate)) > Number(abc.text.validateStringSignRemove(_toDate))){
				alert("검색시작일은 종료일보다 미래 일 수 없습니다.\n검색기간을 재설정 해 주십시오.");
				$("#fromDate").focus();
				return false;
			}

			if(!abc.date.searchValidate()){
				return false;
			}

			// 페이지리스트 목록 개수
			var pageCount = $("#pageCount").val();

			var param = { url : _url + _method
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "emailKeySheet" };

			DataSearchPaging(param);

			break;
		case "save" : // 저장
			var _form = $("#frmEmailKey");
			var _emailTitleText = $("#emailTitleText"); // 이메일 제목
			var _emailContText = $("#emailContText");  // 내용
			var _sendDay = $("#sendDay"); // 발송일
			var _sendHour = $("#sendHour  option:selected").val(); // 발송시간
			var _sendMinute = $("#sendMinute  option:selected").val(); // 발송분

			var _emailTmplSeq = $("#emailTmplSeq").val(); // 이메일 순번

			_method = "create";

			if (! $("input:radio[name=siteNo]").is(":checked")) {
				alert("사이트를 선택하세요.");
				return false;
			}

			if (abc.text.isBlank(_emailTitleText.val())) {
				alert("메일제목을 입력하세요.");
				_emailTitleText.focus();
				return false;
			}

			if (abc.text.isLimitLength(_emailTitleText.val(), 100)) {
				alert("메일제목은 100Byte 까지 입력 가능 합니다.");
				_emailTitleText.focus();
				return false;
			}
			
			if (abc.text.isBlank(_sendDay.val())) {
				alert("발송일시를 선택하세요.");
				_sendDay.focus();
				return false;
			}
			
			if (abc.text.isBlank(_sendHour) || abc.text.isBlank(_sendMinute)) {
				alert("발송시간을 선택 하세요.");
				$("#sendHour").focus();
				return false;
			}

			_emailContText.val(CKEDITOR.instances.emailContText.getData());

			if (abc.text.isBlank(_emailContText.val())) {
				alert("내용을 입력하세요.");
				_emailContText.focus();
				return false;
			}

			if (! abc.text.isBlank(_emailTmplSeq)) {
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
						opener.parent.abc.biz.cmm.msgtemplate.doActionEmailKey("search");
						window.self.close();
					}
				}

			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert(_errorMsg);
				console.log(jqXHR.responseJSON);
			});

			break;
		}
	}

	/***************************************************************************
	* 이메일 보내기 공통 팝업
	***************************************************************************/

	_msgtemplate.doActionEmailSend = function (sAction) {
		var _form = $("#frmEmailSend");
		var _url = "/noacl";
		switch(sAction) {
			// 사이트 선택
			case "siteClick" :
				var checkedSiteNo = $("input:hidden[name=checkedSiteNo]"); // 이전 선택 사이트 번호
				var checkSiteNo = $("input:radio[name=siteNo]:checked");   // 선택 사이트 번호

				if (!abc.text.isBlank(checkedSiteNo.val()) && checkedSiteNo.val() != checkSiteNo.val()) {
					$("#emailTypeCode").change();
				}

				$("input:hidden[name=checkedSiteNo]").val(checkSiteNo);

				break;
			// 템플릿 유형 코드 선택
			case "emailTypeChange" :
				if (! $("input:radio[name=siteNo]").is(":checked")) {
					alert("사이트를 선택하세요.");
					$("#emailTypeCode option:eq(0)").prop("selected", true);
					return false;
				}

				$("#emailTitleText").val("");	// 템플릿 유형 변경시 제목, 내용 초기화
				CKEDITOR.instances.emailContText.setData("");
				
				abc.biz.cmm.msgtemplate.tmplchange(_form, _url);

				break;
			// 메일 템플릿 선택
			case "emailTmplSeqChange" :
				$.ajax({
					url: _url + "/email-tmpl-read-list",
					dataType : "json",
					data: _form.serialize(),
					async:false
				}).done(function(data, textStatus, jqXHR) {
					CKEDITOR.instances.emailContText.setData(data[0].emailContText);
					$("#emailTitleText").val(data[0].emailTitleText);
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("오류가 발생했습니다.");
					console.log(jqXHR.responseJSON);
				});

				break;
			// 메일 전송
			case "emailSend" :
				var _rcvrEmailAddrText = $("#rcvrEmailAddrText");
				var _emailTitleText = $("#emailTitleText");
				var _emailContText = $("#emailContText");

				if (! $("input:radio[name=siteNo]").is(":checked")) {
					alert("사이트를 선택하세요.");
					return false;
				}

				if (abc.text.isBlank(_rcvrEmailAddrText.val())) {
					alert("받는 메일 주소를 입력하세요.")
					_rcvrEmailAddrText.focus();
					return false;
				}

				if (! abc.text.isEmail(_rcvrEmailAddrText.val())) {
					alert("메일 형식이 잘못되었습니다.")
					_rcvrEmailAddrText.focus();
					return false;
				}

				if (abc.text.isBlank(_emailTitleText.val())) {
					alert("메일 제목을 입력하세요.")
					$("#emailTitleText").focus();
					return false;
				}

				if (abc.text.isLimitLength(_emailTitleText.val(), 100)) {
					alert("메일제목은 100Byte 까지 입력 가능 합니다.");
					_emailTitleText.focus();
					return false;
				}

				_emailContText.val(CKEDITOR.instances.emailContText.getData());

				if (abc.text.isBlank(_emailContText.val())) {
					alert("내용을 입력하세요.");
					return false;
				}

				$.ajax({
					url 	: _url + "/email-send",
					method	: "post",
					data	: _form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					alert(data.Message);
					if (data.Code == abc.consts.BOOLEAN_TRUE) {
						if (opener != null) {
							window.self.close();
						}
					}

				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert(_errorMsg);
					console.log(jqXHR.responseJSON);
				});

				break;
		}
	}

	_msgtemplate.tmplchange = function(_form, _url) {
		var _targetSelectBox = "emailTmplSeq";
		$.ajax({
			url: _url + "/email-tmpl-read-list",
			dataType : "json",
			data: _form.serialize(),
			async:false
		}).done(function(data, textStatus, jqXHR) {
			var _len = $("#" + _targetSelectBox + " option").length;

			for(var idx = _len -1; idx > 0; idx --) {
				$("#" + _targetSelectBox + " option:eq(" + idx + ")").remove();
			}

			$.each(data, function() {
				$("#" + _targetSelectBox).append("<option value='" + this.emailTmplSeq + "'>" + this.emailTmplName + "</option>");
			});

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			$("#emailTypeCode option:eq(0)").prop("selected", true);
			console.log(jqXHR.responseJSON);
		});
	}

	/***************************************************************************
	* 문자 보내기 공통 팝업
	***************************************************************************/
	_msgtemplate.doActionTextMesgSend = function (sAction) {
		var _form = $("#frmEmailSend");
		var _url = "/noacl";

		switch(sAction) {
			// 사이트 선택
			case "siteClick" :
				var checkedSiteNo = $("input:hidden[name=checkedSiteNo]"); // 이전 선택 사이트 번호
				var checkSiteNo = $("input:radio[name=siteNo]:checked");   // 선택 사이트 번호
//				var radioId =checkSiteNo.attr("id");
//				var mesgTitleText = "[" + $("label[for='"+radioId+"']").text() + "]";
//				if(checkSiteNo.val() == '10000'|| checkSiteNo.val() == '99999'){
//					mesgTitleText = '[A-RT]';
//				}
				
//				$("#titleTextArea").text(mesgTitleText);

				if (!abc.text.isBlank(checkedSiteNo.val()) && checkedSiteNo.val() != checkSiteNo.val()) {
//					if ($("input:radio[name=sendTypeCode]").is(":checked")) {
						$("#mesgTypeCode").change();
//					}
					
					if(checkSiteNo.val() == '10000'){
						$("#mesgTitleText").val("[A-RT]");
						$("#sendTelNoText").val("15889667");
					}else if(checkSiteNo.val() == '10001'){
						$("#mesgTitleText").val("[On the spot]");
						$("#sendTelNoText").val("16440136");
					}else{
						$("#mesgTitleText").val("");
						$("#sendTelNoText").val("15889667");
					}
					
				}

				$("input:hidden[name=checkedSiteNo]").val(checkSiteNo.val());

				break;
			// 발송 방식 선택
//			case "sendTypeCheck" :
//				var checkedSendType = $("input:hidden[name=checkedSendTypeCode]").val(); // 이전 선택 발송방식
//				var checkSendType = $("input:radio[name=sendTypeCode]:checked").val(); 	// 선택 발송방식
//
//				if(_SEND_TYPE_CODE_SMS == checkSendType) {
//					$("#mesgContTextMaxByte").text(_SMS_TEXT_MAX_BYTE);
//					$("#mesgContTextByteLength").val("0");
//					$("#mesgTitleText").attr("readonly", true);
//				} else if (_SEND_TYPE_CODE_LMS) {
//					$("#mesgContTextMaxByte").text(_LMS_TEXT_MAX_BYTE);
//					$("#mesgContTextByteLength").val("0");
//					$("#mesgTitleText").attr("readonly", false);
//				}
//
//				if (checkedSendType != "" && checkedSendType != checkSendType) {
//					if ($("input:radio[name=siteNo]").is(":checked")) {
//						$("#mesgTypeCode").change();
//					}
//				}
//
//				$("input:hidden[name=checkedSendTypeCode]").val(checkSendType);
//
//				break;
			// 템플릿 유형 코드 선택
			case "mesgTypeChange" :
				if (! $("input:radio[name=siteNo]").is(":checked")) {
					alert("사이트를 선택하세요.");
					$("#mesgTypeCode option:eq(0)").prop("selected", true);
					return false;
				}

				var _targetSelectBox = "mesgTmplSeq";

//				$("#mesgContText").val("");

				$.ajax({
					url: _url + "/text-msg-tmpl-read-list" ,
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

				break;
			// 문자 템플릿 선택
			case "mesgTmplSeqChange" :
				$.ajax({
					url: _url + "/text-msg-tmpl-read-list",
					dataType : "json",
					data: _form.serialize(),
					async:false
				}).done(function(data, textStatus, jqXHR) {
					$("#sendTypeCode").val(data[0].sendTypeCode);
					if (data[0].sendTypeCode == _SEND_TYPE_CODE_LMS) {
						$("#mesgContTextMaxByte").text(_LMS_TEXT_MAX_BYTE);
						$("#mesgTitleText").attr("readonly", false);
					} else {
						$("#mesgContTextMaxByte").text(_SMS_TEXT_MAX_BYTE);
						$("#mesgTitleText").attr("readonly", true);
					}

					if (!abc.text.isBlank(data[0].mesgContText)) {
						$("#mesgContText").val(data[0].mesgContText);
						$("#mesgContTextByteLength").val(data[0].mesgContText.uniLength());
					}

				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("오류가 발생했습니다.");
					console.log(jqXHR.responseJSON);
				});

				break;

			// 문자메시지 전송
			case "sendTextMsg" :
				var _recvTelNoText = $("#recvTelNoText");
				var _sendTelNoText = $("#sendTelNoText");
				var _mesgTitleText = $("#mesgTitleText");
				var _mesgContText = $("#mesgContText");

				if (! $("input:radio[name=siteNo]").is(":checked")) {
					alert("사이트를 선택하세요.");
					return false;
				}

				if (abc.text.isBlank(_recvTelNoText.val())) {
					alert("받는 휴대폰 번호를 입력하세요.")
					_recvTelNoText.focus();
					return false;
				}

				if (! abc.text.isPhoneNum(_recvTelNoText.val())) {
					alert("휴대폰 번호 형식이 잘못되었습니다.")
					_recvTelNoText.focus();
					return false;
				}

				if (abc.text.isBlank(_sendTelNoText.val())) {
					alert("보내는 번호를 입력하세요.")
					_sendTelNoText.focus();
					return false;
				}

//				if (! abc.text.isPhoneNum(_sendTelNoText.val())) {
//					alert("전화번호 형식이 잘못되었습니다.")
//					_sendTelNoText.focus();
//					return false;
//				}

				if (_SEND_TYPE_CODE_LMS == $("#sendTypeCode").val()) {
					if (abc.text.isBlank(_mesgTitleText.val())) {
						alert("문자 제목을 입력하세요.")
						_mesgTitleText.focus();
						return false;
					}

					if (abc.text.isLimitLength(_mesgTitleText.val(), 100)) {
						alert("문자 제목은 100Byte 까지 입력 가능 합니다.");
						_mesgTitleText.focus();
						return false;
					}
				}

				if (abc.text.isBlank(_mesgContText.val())) {
					alert("내용을 입력하세요.");
					_mesgContText.focus();
					return false;
				}

				if (abc.text.isLimitLength(_mesgTitleText.val(), $("#mesgContTextMaxByte").text())) {
					alert("")
				}

				$.ajax({
					url 	: _url + "/text-mesg-send",
					method	: "post",
					data	: _form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					alert(data.Message);
					if (data.Code == abc.consts.BOOLEAN_TRUE) {
							window.self.close();
					}

				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert(_errorMsg);
					console.log(jqXHR.responseJSON);
				});

				break;
		}
	}

	// 문자 템플릿 글자수를 체크한다.
	_msgtemplate.conTextByteLengthCheck = function (str) {
		var strByteLength = str.uniLength();

		$("#smsContTextByte").text(strByteLength);
		$("#lmsContTextByte").text(strByteLength);

		if (strByteLength <= _SMS_TEXT_MAX_BYTE) {
			$("#smsMessage").hide();
			$("#lmsMesage").hide();
			$("#sendTypeCode").val(_SEND_TYPE_CODE_SMS);
		} else if (_SMS_TEXT_MAX_BYTE < strByteLength && strByteLength <= _LMS_TEXT_MAX_BYTE) {
			$("#smsMessage").show();
			$("#lmsMesage").hide();
			$("#sendTypeCode").val(_SEND_TYPE_CODE_LMS);
		} else if (strByteLength > _LMS_TEXT_MAX_BYTE) {
			$("#smsMessage").show();
			$("#lmsMesage").show();
		//	$("#mesgContText").val(abc.text.substringByte(str, _LMS_TEXT_MAX_BYTE));
		//	alert("문자 템플릿은 2,000Byte까지만 입력 가능 합니다.");
		//	return false;
		}
	}

	// 템플릿 문자 내용 길이 체크
	_msgtemplate.contTextLengthCheck = function (sendTypeCode, str) {
		sendTypeCode = abc.text.isBlank(sendTypeCode) ? _SEND_TYPE_CODE_SMS : sendTypeCode;
		var strByteLength = str.uniLength();

		$("#mesgContTextByteLength").val(strByteLength);

		if (strByteLength <= _SMS_TEXT_MAX_BYTE) {
			$("#sendTypeCode").val(_SEND_TYPE_CODE_SMS);
			$("#mesgContTextMaxByte").text(_SMS_TEXT_MAX_BYTE);
			$("#mesgTitleText").attr("readonly", true);
		} else if (_SMS_TEXT_MAX_BYTE < strByteLength && strByteLength <= _LMS_TEXT_MAX_BYTE) {
			if (sendTypeCode != _SEND_TYPE_CODE_LMS) {
				$("#sendTypeCode").val(_SEND_TYPE_CODE_LMS);
				$("#mesgContTextMaxByte").text(_LMS_TEXT_MAX_BYTE);
				$("#mesgTitleText").attr("readonly", false);
			}
		} else if (strByteLength > _LMS_TEXT_MAX_BYTE) {
			alert(_LMS_TEXT_MAX_BYTE + "Byte 까지만 입력 가능 합니다.");
			$("#mesgContText").val(abc.text.substringByte(str, _LMS_TEXT_MAX_BYTE));
			$("#mesgContTextByteLength").val($("#mesgContText").val().uniLength());
		}
	}
	
})();