/***
 * 프로모션 메시징관리 자동 발송 이메일 템플릿 관리
 * 
 */

(function() {
	/***************************************************************************
	* 이메일 템플릿 관리 목록	(자동)
	***************************************************************************/
	
	var _emailtemplate = abc.object.createNestedObject(window,"abc.biz.cmm.email.auto.template");
	
	var _isDoubleCheck = false;		// 중복확인 버튼 클릭여부
	var _emailIdCheck = "";			// 중복확인 후 수정 여부
	
	// 이메일 템플릿 시작	
	_emailtemplate.init = function() {
		var gridContainer = $("#emailTemplateGrid");
		
		// gird영역이 존재여부 체크 (main과 detail 구분에 따라 생성여부)
		if(Object.keys(gridContainer).length !== 0) {		// main.jsp 일때
			_emailtemplate.initEmailAutoTemplateSheet();	// 그리드 생성
			_emailtemplate.mainEvent();						// 메인화면 이벤트 핸들러 생성
			_emailtemplate.moveDetail();					// 상세 이동 핸들러 생성
			_emailtemplate.triggerSearch();
			
		}else {												// detail.jsp 일때
			_emailtemplate.detailEvent();					// 상세화면 이벤트 핸들러 생성

			CKEDITOR.replace("emailContText", {				// CKEDITOR 셋팅
				width:"100%",
				height:"400px"
			});
		}
	}
	
	// 이메일 템플릿 목록 그리드
	_emailtemplate.initEmailAutoTemplateSheet = function() {
		var initEmailTemplateSheet = {};
		var pageCount = $('#pageCount').val();

		initEmailTemplateSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initEmailTemplateSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initEmailTemplateSheet.Cols = [
				{Header:"" ,		 	Type:"Status",		SaveName:"status",					Width: 0,		Align:"", 			Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"emailTmplSeq",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"",				Type:"Text",		SaveName:"moderNo",					Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"번호",			Type:"Seq",			SaveName:"",						Width: 15,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",				Width: 20,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"이메일 ID",		Type:"Text",		SaveName:"emailId",					Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"템플릿유형",		Type:"Text",		SaveName:"emailTypeCodeName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"템플릿명",		Type:"Html",		SaveName:"emailTmplName",			Width: 50,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"	}
			,	{Header:"메일제목",		Type:"Html",		SaveName:"emailTitleText",			Width: 100,		Align:"Left",		Edit:0,		Sort:0			}
			,	{Header:"수정자",			Type:"Text",		SaveName:"maskingDispModerName",	Width: 25,		Align:"Center",		Edit:0,		Sort:0, 		}
			,	{Header:"수정일시",		Type:"Date",		SaveName:"modDtm",					Width: 0,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("emailTemplateGrid"), "emailTemplateSheet", "100%", "429px");
		IBS_InitSheet(emailTemplateSheet, initEmailTemplateSheet);
		// Grid 건수 정보 표시
		emailTemplateSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		emailTemplateSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		emailTemplateSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		emailTemplateSheet.SetExtendLastCol(1);
	}

	// 메일 템플릿 관리 Action 처리
	_emailtemplate.doActionEmailAutoTemplate = function (sAction) {
		var _url = "/cmm/msg/email-template/auto/";
		var _method	= "";
		
		switch(sAction) {
			case "search" :	// 목록 조회
				_method = "read-list";

				var _fromDate = $("#fromDate").val(); // 검색 시작일
				var _toDate = $("#toDate").val();     // 검색 종료일
				
				if (abc.text.allNull(_fromDate) || abc.text.allNull(_toDate)) {
					alert("기간검색을 선택하세요.");
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
					, sheet : "emailTemplateSheet" };

				DataSearchPaging(param);
				break;
				
			case "save" : // 저장
				var _form = $("#frmEmailTemplate");
				var _emailTypeCode = $("#emailTypeCode option:selected").val(); // 문자 템플릿 유형
				var _emailTmplName = $("#emailTmplName"); // 템플릿 명
				var _emailId = $("#emailId"); // 이메일 ID
				var _emailTitleText = $("#emailTitleText"); // 이메일 제목
				var _emailContText = $("#emailContText");  // 내용
								
				var _emailTmplSeq = $("#emailTmplSeq").val(); // 문자템플릿 순번

				_method = "create";

				if (abc.text.isBlank(_emailTypeCode)) {
					alert("유형을 선택하세요.");
					$("#emailTypeCode").focus();
					return false;
				}

				if (abc.text.isBlank(_emailTmplName.val())) {
					alert("템플릿 명을 입력하세요.");
					_emailTmplName.focus();
					return false;
				}

				if (abc.text.isLimitLength(_emailTmplName.val(), 100)) {
					alert("템플릿 명은 100Byte 까지 입력 가능 합니다.");
					_emailTmplName.focus();
					return false;
				}
				
				if (abc.text.isBlank(_emailId.val())) {
					alert("이메일 ID를 입력하세요.");
					_emailId.focus();
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

				_emailContText.val(CKEDITOR.instances.emailContText.getData());
				
				if (abc.text.isBlank(_emailContText.val())) {
					alert("내용을 입력하세요.");
					_emailContText.focus();
					return false;
				}
				
				// 중복확인 체크
				if(!_emailId.prop("readonly")) {
					if(!_isDoubleCheck) {
						alert("중복확인 버튼을 클릭 해주세요.");
						_emailId.focus();
						return false;
					}
					
					if(_emailIdCheck != _emailId.val()) {
						alert("중복확인 버튼을 다시 해주세요.");
						_emailIdCheck="";
						_emailId.focus();
						return false;
					}
				}
				
				if (!abc.text.isBlank(_emailTmplSeq)) {
					_method = "modify";
				}
				
				$.ajax({
						url 	: _url + _method,
						method	: "post",
						data	: _form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					alert(data.message);

					if (data.code == abc.consts.BOOLEAN_TRUE) {
						if (opener != null) {
							opener.parent.abc.biz.cmm.email.auto.template.doActionEmailAutoTemplate("search");
							window.self.close();
						}
					}

				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("실패하였습니다.\n" + jqXHR.responseJSON.message);
					
				});
				break;

			case "doubleCheck" : // 중복확인
				var _emailId = $("#emailId");
				_method = "double-check";
				
				if(abc.text.isBlank(_emailId.val())) {
					alert("이메일 ID를 입력해주세요.");
					_emailId.focus();
					return false;
				}
				
				var _emailIdValue = {
					"emailId" : _emailId.val()	
				}
				
				$.ajax({
					url		: _url + _method,
					method	: "get",
					data 	: _emailIdValue,
				}).done(function(data, textStatus, jqXHR) {
					if(data === 0) {	// 동일한 emailId 개수
						_isDoubleCheck = true;
						_emailIdCheck = _emailId.val();
						alert("사용 가능한 ID입니다.");
					}else {
						_isDoubleCheck = false;
						alert("이메일 ID가 이미 존재합니다.");
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("중복확인 검사에 실패하였습니다.\n" + jqXHR);
				});
				break;
				
			case "delete" : // 삭제
				var _form = $("#frmEmailTemplate");
				_method = "delete";
				if (confirm("등록된 템플릿을 삭제하시겠습니까?")) {
					$.ajax({
							url 	: _url + _method,
							method	: "post",
							data	: _form.serialize()
					}).done(function(data, textStatus, jqXHR) {
						alert(data.message);

						if (data.code == abc.consts.BOOLEAN_TRUE) {
							if (opener != null) {
								opener.parent.abc.biz.cmm.email.auto.template.doActionEmailAutoTemplate("search");
								window.self.close();
							}
						}

					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("삭제에 실패하였습니다.\n" + jqXHR.responseJSON.message);
					});
				}
				break;
		}
	}
	
	// 이메일 템플릿 메인 화면 이벤트
	_emailtemplate.mainEvent = function() {
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});

		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
	
		// 목록 조회
		$("#searchBtn").click(function(f) {
			_emailtemplate.doActionEmailAutoTemplate("search");
		});

		// 유형 변경
		$("#emailTypeCode").off().on("change", function(f) {
			$("#emailTmplSeq").val(null);	// 템플릿 유형 변경시 이전 tmplSeq 초기화
			_emailtemplate.tmplchange($("#frmSearch"), "/noacl/auto");
		});

		$("#pageCount").change(function(f) {
			_emailtemplate.doActionEmailAutoTemplate("search");
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			$.form("#frmSearch").reset();
			$("a[name^=per1Month]").trigger("click");
		});

		// 이메일 템플릿 등록 폼 호출
		$("#rgstBtn").click(function(f) {
			abc.open.popup({
				url 	:	"/cmm/msg/email-template/auto/read-detail-pop",
				winname :	"emailTemplatepop",
				metho	: 	"get",
				title 	:	"emailTemplate",
				width 	:	890,
				height	:	890,
				params	: 	{}
			});
		});

		// 날짜 기본 설정
		$("a[name^=per1Month]").trigger("click");
		
		// enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
		    }
		});
		
		// 사이트 구분 변경시 템플릿 유형 초기화
		$("input[name=siteNo]").on("change", function() {
			$("#emailTypeCode").val("");
			_emailtemplate.tmplchange($("#frmSearch"), "/noacl");
		});
	}
	
	// 이메일 템플릿 상세 화면 이벤트
	_emailtemplate.detailEvent = function() {
		// 유형 변경
		$("#emailTypeCode").off().on("change", function(f) {
			_emailtemplate.tmplchange($("#frmSearch"), "/noacl/auto");
		});

		$("#saveBtn").click(function(f) {
			_emailtemplate.doActionEmailAutoTemplate("save");
		});

		$("#doubleCheckBtn").click(function(f) {
			_emailtemplate.doActionEmailAutoTemplate("doubleCheck");
		});
		
		// 관리자 클릭시 상세팝업
		$("#moderId").off().on("click", function() {
			abc.adminDetailPopup($(this).attr("name"));
		});

	}

	//이메일 템플릿 상세 페이지 이동
	_emailtemplate.moveDetail = function() {
		emailTemplateSheet_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {
			if ( Row != 0) {
				if ( emailTemplateSheet.ColSaveName(Col) == "emailTmplName" && Value != "" ) {
					abc.open.popup({
						url 	:	"/cmm/msg/email-template/auto/read-detail-pop",
						winname :	"emailTemplatepop",
						metho	: 	"get",
						title 	:	"emailTemplate",
						width 	:	890,
						height	:	890,
						params	: 	{emailTmplSeq : emailTemplateSheet.GetCellValue(Row, "emailTmplSeq")}
					});
				}
			}
		}
	}
	
	// 템플릿 유형 변경
	_emailtemplate.tmplchange = function(_form, _url) {
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
			$("#emailTypeCode option:eq(0)").prop("selected", true);
		});
	}
	
	// 템플릿 목록 진입시 검색 트리거
	_emailtemplate.triggerSearch = function() {
		$("#searchBtn").trigger("click");
	}
	
	// 이메일 템플릿 시작 실행
	$(function() {
		_emailtemplate.init();
	});
})();

