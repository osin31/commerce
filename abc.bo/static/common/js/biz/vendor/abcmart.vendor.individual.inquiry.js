/***
 * 1:1 문의관리
 *
 */
(function() {

	var _inquiry = abc.object.createNestedObject(window,"abc.biz.vendor.individual.inquiry");

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
				{Header:"",				Type:"Text",		SaveName:"memberCnslSeq",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 1:1 문의번호
			,	{Header:"",				Type:"Text",		SaveName:"checkAtchFile",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 첨부파일 유무
			,	{Header:"",				Type:"Text",		SaveName:"vndrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 담당업체코드
			,	{Header:"",				Type:"Text",		SaveName:"memberNo",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 회원번호
			,	{Header:"",				Type:"Text",		SaveName:"aswrNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 답변 관리자 번호
			,	{Header:"",				Type:"Text",		SaveName:"cnslGbnCode",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 상담구분코드
			,	{Header:"번호",			Type:"Seq",			SaveName:"",					Width: 10,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"답변상태",		Type:"Text",		SaveName:"aswrStatName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"문의유형",		Type:"Text",		SaveName:"cnslTypeName",		Width: 60,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"제목",			Type:"Html",		SaveName:"inqryTitleText",		Width: 120,		Align:"Left",		Edit:0, 	Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",			Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"회원",			Type:"Text",		SaveName:"memberInfo",			Width: 50,		Align:"Center",		Edit:0, 	Sort:0			}
			,	{Header:"문의일시",		Type:"Text",		SaveName:"inqryDtm",			Width: 40,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"이관일시",		Type:"Date",		SaveName:"vndrAssignDtm",		Width: 50,		Align:"Center",		Edit:0, 	Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			,	{Header:"답변자",			Type:"Text",		SaveName:"aswrInfo",			Width: 30,		Align:"Center",		Edit:0,		Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"최종답변일시",		Type:"Date",		SaveName:"aswrDtm",				Width: 0,		Align:"Center",		Edit:0,		Sort:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
		];

		createIBSheet2(document.getElementById("inquiryGrid"), "inquiryGridSheet", "100%", "429px");
		IBS_InitSheet(inquiryGridSheet, initInquiryGridSheet);
		// Grid 건수 정보 표시
		inquiryGridSheet.SetCountPosition(3);2
		// Grid 페이지 네비게이션 버튼 표시
		inquiryGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		inquiryGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		inquiryGridSheet.SetExtendLastCol(1);
	}

	_inquiry.doActionCounselScript = function(sAction) {
		var _url = "/vendor/individual-inquiry/";
		var _method = "";

		switch(sAction) {
		case "search" : // 목록 조회
			_method = "read-list";

			// 기간검색 유형
			var _searchDateKey = $("#searchDateKey").val();
			var _fromDate = $("#fromDate").val(); // 검색 시작일
			var _toDate = $("#toDate").val();     // 검색 종료일
			
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
				alert('검색 유형을 선택  하세요.');
				$("#searchKey").focus();
				return false;
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

			var aswrContText		= $("#aswrContText");							// 답변 내용
			var memberCnslSeq		= $("#memberCnslSeq");
			var aswrHoldCheck 		= $("#aswrHoldCheck");
			
			this.holdAswrCheck();

			if(abc.text.isBlank(aswrContText.val()) && !$("#aswrHoldCheck").prop("checked") ) {
				alert("답변 내용을 적어주세요.");
				aswrContText.focus();
				return false;
			}
			
			// 답변 완료일경우 체크
			if (typeof aswrStatCode != "undefined" && aswrStatCode === abc.consts.ASWR_STAT_CODE_COM_ASWR) {
				if(abc.text.isBlank(aswrContText.val())) {
					alert("답변내용을 입력하세요.");
					$("#inqryContText").focus();
					return false;
				}
			}
			
			var formData = new FormData($('#frmInquiry')[0]);

			$.ajax({
				processData: false,
	            contentType: false,
				type:"post",
				url: _url + _method,
				data: formData
			}).done(function(data, textStatus, jqXHR) {
				if (data.code == abc.consts.BOOLEAN_TRUE) {
					alert('수정 되었습니다.');
					opener.parent.abc.biz.vendor.individual.inquiry.doActionCounselScript("search");
					window.close();
				} else {
					alert(data.message);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다.\n" + jqXHR.responseJSON.message);
			});
			break;
			
		case "memoSave" :  //관리자 메모 저장
			_method = "create-adminmemo";

			$.ajax({
				type:"post",
				url: _url + _method,
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
				addArea +=  			"<a href='#' value='"+data.cnslMemoSeq+"' rgsterno='"+ data.rgsterNo +"' name='memoDeleteBtn' class='btn-msg-list-del'><i class='ico ico-del'><span class='offscreen'>메모 삭제</span></i></a>";
				addArea +=  		"</span>";
				addArea +=  	"</span>";
				addArea +=  	"<p class='msg-desc'>"+data.memoText+"</p>";
				addArea +=  "</li>";
				$("#memoAddArea").prepend(addArea);
				$("#adminMemoText").val("");
				
				alert(data.message);

			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다.\n"+jqXHR.responseJSON.message);
			});

			break;
		}
	}

	//답변보류 체크박스
	_inquiry.holdAswrCheck = function(){
		if($("#aswrHoldCheck").prop("checked")){
			$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_HOLD_ASWR);
		}else{
			if($("#chkEscalation").prop("checked")){
				$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_NO_ASWR);
			}else{
				$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_COM_ASWR);
			}
		}
	}

	//초기화 버튼
	_inquiry.resetButton = function(){
		$.form("#frmSearch").reset();
		$("#cnslGbnCode_on").prop('checked', true);
		
		$("a[name^=per1Month]").trigger("click");
	}

	//관리자 메모 삭제
	_inquiry.removeAdminMemo = function(memberCnslSeq,cnslMemoSeq){
		var _url = "/vendor/individual-inquiry/";
		var _method = "remove-adminmemo";

		$.ajax({
			type:"post",
			url: _url + _method,
			data: {
				"memberCnslSeq" : memberCnslSeq,
				"cnslMemoSeq" : cnslMemoSeq
			}
		}).done(function(data, textStatus, jqXHR) {
			alert("삭제되었습니다.");
			$("#adminMemoArea"+cnslMemoSeq).remove();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.\n" + jqXHR);
		});
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
		$("#searchValue").val('');
		$("#searchValue").focus();

		if(searchKey == "inqryTitleText"){
			$("#searchValue").removeAttr("onkeyup");
		}else if(searchKey == "memberName"){
			$("#searchValue").attr("onkeyup", "window.abc.text.validateOnlyString(this)");
		}else if(searchKey == "rgsterName"){
			$("#searchValue").attr("onkeyup", "window.abc.text.validateOnlyString(this)");
		}else{
			$("#searchValue").attr("onkeyup", "window.abc.text.validateOnlyAlphabetNum(this)");
		}
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

	//첨부파일 다운로드
	_inquiry.fileDownLoad = function(e, check){
		var downLoadFileName =""; // 다운로드 파일명
		var atchFilePathText = ""; // 저장 디렉토리 + 실제 저장된 파일명
		if(check == 'A'){
			downLoadFileName = $(e).text();
			atchFilePathText = $(e).prev('[name=aswrAtchFilePathText]').val();
		}else{
			downLoadFileName = $(e).children('input[name=inquiryDownLoadFileName]').val();
			atchFilePathText = $(e).children('input[name=inquiryAtchFilePathText]').val();
			
		}
		atchFilePathText = atchFilePathText.split("\\").join("/");

		location.href = "/noacl/defaultContentTypedownload?downLoadFileName=" + downLoadFileName + "&atchFilePathText=" + atchFilePathText;
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
	_inquiry.fromDashSearch = function(fromDash) {
		if(abc.consts.BOOLEAN_TRUE === fromDash) {
			$('#'+abc.consts.ASWR_STAT_CODE_HOLD_ASWR).prop('checked', true);
			_inquiry.doActionCounselScript("search");
		}
	};

})();