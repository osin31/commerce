/***
 * 대량구매 문의관리
 *
 */
(function() {

	var _bulkbuy = abc.object.createNestedObject(window,"abc.biz.board.bulkbuy");

	/***************************************************************************
	* 대량구매 문의관리 목록
	***************************************************************************/

	_bulkbuy.bulkBuyGridSheet = function() {

		var initbulkBuyGridSheet = {};
		var pageCount = $('#pageCount').val();

		initbulkBuyGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initbulkBuyGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initbulkBuyGridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"blkBuyInqrySeq",	Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 1:1 문의번호
			,	{Header:"",				Type:"Text",		SaveName:"memberNo",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1		} // 회원번호
			,	{Header:"번호",			Type:"Seq",			SaveName:"",				Width: 10,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"제목",			Type:"Html",		SaveName:"inqryTitleText",	Width: 120,		Align:"Left",		Edit:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"문의사이트",		Type:"Text",		SaveName:"siteName",		Width: 30,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"담당자",			Type:"Text",		SaveName:"mngrDpName",		Width: 50,		Align:"Center",		Edit:0, 	Sort:0,		FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"문의일시",		Type:"Text",		SaveName:"inqryDtm",		Width: 40,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			];

		createIBSheet2(document.getElementById("bulkBuyGrid"), "bulkBuyGridSheet", "100%", "429px");
		IBS_InitSheet(bulkBuyGridSheet, initbulkBuyGridSheet);
		// Grid 건수 정보 표시
		bulkBuyGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		bulkBuyGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		bulkBuyGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		bulkBuyGridSheet.SetExtendLastCol(1);
		
	}


	_bulkbuy.doAction = function(sAction) {
		var _url = "/board/bulkbuy-inquiry/";
		var _method = "";

		switch(sAction) {
		case "search" : // 목록 조회
			_method = "read-list";

			// 기간검색 유형
			var _fromDate = $("#fromDate").val(); // 검색 시작일
			var _toDate = $("#toDate").val();     // 검색 종료일
			
			if(!abc.text.isDate(abc.text.validateStringSignRemove(_fromDate))){
				alert("시작일자를 확인해주세요");
				$("#fromDate").focus();
				
				return false;
			}
			
			if(!abc.text.isDate(abc.text.validateStringSignRemove(_toDate))){
				alert("종료일자를 확인해주세요");
				$("#toDate").focus();
				
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
					, sheet : "bulkBuyGridSheet" };

			DataSearchPaging(param);

			break;
		case "memoSave" : //관리자 메모 저장
			_method = "create-bulkbuy-memo";

			$.ajax({
				type:"post",
				url: _url + _method,
				data: $("#frmAdminMemo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				console.log(data);
				var addArea;
				var rgstDtm = new Date(data.rgstDtm);
				addArea  = "<li id='adminMemoArea"+data.blkBuyMemoSeq+"'>";
				addArea +=  	"<span class='msg-list-info'>";
				addArea +=  		"<span class='user-info'>";
				addArea +=  			"<span class='user-id'>"+data.loginId+"</span>";
				addArea +=  			"<span class='user-name'>("+data.adminName+")</span>";
				addArea +=  		"</span>";
				addArea +=  		"<span class='regist-date-wrap'>";
				addArea +=  			"<span class='regist-date'>"+rgstDtm.format()+"</span>";
				addArea +=  			"<a href='javascript:void(0)' value='"+data.blkBuyMemoSeq+"'  rgsterno='"+data.rgsterNo+"' name='memoDeleteBtn' class='btn-msg-list-del'><i class='ico ico-del'><span class='offscreen'>메모 삭제</span></i></a>";
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

			break;

		}
	}

	//답변보류 체크박스
	_bulkbuy.holdAswrCheck = function(){
		if($("#aswrHoldCheck").prop("checked")){
			$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_HOLD_ASWR);
		}else{
			$("#aswrStatCode").val(abc.consts.ASWR_STAT_CODE_COM_ASWR);
		}
	}
	//초기화 버튼
	_bulkbuy.resetButton = function(){
		$.form("#frmSearch").reset();
	}

	//관리자 메모 삭제
	_bulkbuy.removeAdminMemo = function(blkBuyInqrySeq,blkBuyMemoSeq){
		var _url = "/board/bulkbuy-inquiry/";
		var _method = "remove-bulkbuy-memo";

		$.ajax({
			type:"post",
			url: _url + _method,
			data: {
				"blkBuyInqrySeq" : blkBuyInqrySeq,
				"blkBuyMemoSeq" : blkBuyMemoSeq
			}
		}).done(function(data, textStatus, jqXHR) {
			alert("삭제되었습니다.");
			$("#adminMemoArea"+blkBuyMemoSeq).remove();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}

	//단체주문 문의 검색어 초기화
	_bulkbuy.searchValueSet = function(){
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

	// 대시보드에서 더보기 클릭시 사이트 구분 검색조회 전체(1), 통합몰(2), OTS(3)
	_bulkbuy.fromDashSearch = function(fromDash, tabIdx) {
		if(tabIdx == 5) { 			// 통합몰
			$("#siteNo1").prop("checked", true);
		}else if(tabIdx == 6) { 	// OTS
			$("#siteNo2").prop("checked", true);
		}
		
		$("a[name^=per1Month]").trigger("click");
		
		_bulkbuy.doAction("search");
	}

})();