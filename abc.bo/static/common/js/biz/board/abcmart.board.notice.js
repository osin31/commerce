/***
 * 공지사항 관리
 *
 */
(function() {

	var _notice = abc.object.createNestedObject(window,"abc.biz.board.notice");

	/***************************************************************************
	*
	****************************************************************************/

	_notice.noticeGridSheet = function() {

		var initnoticeGridSheet = {};
		var pageCount = $('#pageCount').val();

		initnoticeGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initnoticeGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initnoticeGridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"notcSeq",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} //등록자번호
			,	{Header:"",				Type:"Text",		SaveName:"rgsterNo",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} //등록자번호
			,	{Header:"",				Type:"Text",		SaveName:"moderNo",				Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} //수정자번호
			,	{Header:"번호",			Type:"Text",		SaveName:"topNotcCheck",		Width: 15,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"사이트",			Type:"Text",		SaveName:"siteName",			Width: 40,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"제목",			Type:"Html",		SaveName:"notcTitleText",		Width: 120,		Align:"Left",		Edit:0, 	FontBold:1,	FontUnderline:1,	Cursor:"Pointer"}
			,	{Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",				Width: 40,		Align:"Center",		Edit:0,		ComboText : "전시|전시안함",			ComboCode : "Y|N"}
			,	{Header:"작성자",			Type:"Text",		SaveName:"rgsterDpName",		Width: 50,		Align:"Center",		Edit:0, 	Sort:0		}
			,	{Header:"작성일시",		Type:"Date",		SaveName:"rgstDtm",				Width: 40,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			];

		createIBSheet2(document.getElementById("noticeGrid"), "noticeGridSheet", "100%", "429px");
		IBS_InitSheet(noticeGridSheet, initnoticeGridSheet);
		// Grid 건수 정보 표시
		noticeGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		noticeGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		noticeGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		noticeGridSheet.SetExtendLastCol(1);

	}

	_notice.doAction = function(sAction) {
		var _url = "/board/notice/";
		var _method = "";

		switch(sAction) {
			case "search" : // 목록 조회
				_method = "read-notice-list";
				var _fromDate = $("#fromDate").val(); // 검색 시작일
				var _toDate = $("#toDate").val();     // 검색 종료일
				// 검색어 대상 필드
				var searchKey = $("#searchKey option:selected");

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

				// 페이지리스트 목록 개수
				var pageCount = $("#pageCount").val();

				var param = { url : _url + _method
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.frmSearch)
						, CPage : $('#listPageNum').val()
						, sheet : "noticeGridSheet" };

				DataSearchPaging(param);
				$('#listPageNum').val('1');
				break;
			case "btnNoticeSave" : // 등록  수정 체크
				if($("#saveCheck").val() == "1"){
					if(confirm("등록하시겠습니까?")){
						$("#notcContText").val(CKEDITOR.instances.notcContText.getData());
						if(this.noticeValidate()){
							$("#topNotcYn_N").prop('disabled', false);
							this.createNotice();
						}
					}
				}else{
					if(confirm("수정하시겠습니까?")){
						$("#notcContText").val(CKEDITOR.instances.notcContText.getData());
						if(this.noticeValidate()){
							$("#topNotcYn_N").prop('disabled', false);
							this.doAction("updateNoticeDetail");
						}
					}
				}

				break;
			case "updateNoticeDetail" :		//공지사항 수정
				_method = "update-notice-detail";
				var $form = $("#noticeForm");

				$.ajax({
					type:"post",
					url: _url+_method,
					data: $form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					if (data.result == 1) {
						alert("수정되었습니다.");
						var url = "/board/notice/"+"?menuNo="+abc.param.getParams().menuNo;
						$(location).attr('href', url);
					} else {
						alert(data.Message);
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
				});
				break;
			case "createNoticeForm" :	//공지사항 등록 폼
				_method = "create-notice-form";

				location.href = _url + _method +"?menuNo="+abc.param.getParams().menuNo;
				break;
			case "noticeDelete" :	//공지사항 삭제
				if(confirm("삭제하시겠습니까?")){
					_method = "remove-notice-detail";

					var $form = $("#noticeForm");

					$.ajax({
						type:"post",
						url: _url+_method,
						data: $form.serialize()
					}).done(function(data, textStatus, jqXHR) {
						if (data.result == 1) {
							alert("삭제되었습니다.");
							var url = "/board/notice/"+"?menuNo="+abc.param.getParams().menuNo;
							$(location).attr('href', url);
						} else {
							alert(data.Message);
						}
					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
					});
				}
				break;
			}
	}
	//공지사항 상세 페이지 이동
	_notice.readDetailForm = function(){
		$("#listPageNum").val(noticeGridSheet.GetCurrentPage());
		frmSearch.action = '/board/notice/read-detail-form';
		frmSearch.submit();
	}
	//공지사항 등록
	_notice.createNotice = function(){
		var $form = $("#noticeForm");

		$.ajax({
			type:"post",
			url: "/board/notice/create-notice",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			if (data.result == 1) {
				alert("등록되었습니다.");
				var url = "/board/notice/"+"?menuNo="+abc.param.getParams().menuNo;
				$(location).attr('href', url);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}
	//현재 로우 기록
	_notice.initSet = function(currentRow){
		if(abc.text.allNull(currentRow)){
			$("#currentRow").val("1");
		}else{
			$("#currentRow").val(currentRow);
		}
	}
	//상세 뷰 셋팅
	_notice.noticeDetailInitSet = function(dispYn, topNotcYn){
		if(!(abc.text.allNull(dispYn))){
			$("input:radio[name='dispYn'][value='"+dispYn+"']").prop('checked', true);
			$("input:radio[name='topNotcYn'][value='"+topNotcYn+"']").prop('checked', true);
			$("#saveCheck").val("0");
		}
		_notice.topNoticeArea();
	}
	//초기화 버튼
	_notice.noticeReset = function(dispYn, topNotcYn){
		$("input:radio[name='dispYn'][value='"+dispYn+"']").prop('checked', true);
		$("input:radio[name='topNotcYn'][value='"+topNotcYn+"']").prop('checked', true);

		if($("#saveCheck").val() == "1"){
			CKEDITOR.instances.notcContText.setData("");
		}else{
			CKEDITOR.instances.notcContText.setData(notcContText);
		}
	}

	//검색어 초기화
	_notice.searchValueSet = function(){
		var searchKey = $("#searchKey option:selected").val();

		//검색어 값을 초기화 후 포커스를 이동
//		$("#searchValue").val('');
		$("#searchValue").focus();

		if(searchKey == "notcTitleText"){
			$("#searchValue").off().on('input', function(event){

			});
		}else if(searchKey == "rgsterName"){
			$("#searchValue").off().on('input', function(event){
				abc.text.validateOnlyString(this);
			});
		}
		$("#searchValue").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});
	}
	//목록 버튼
	_notice.pageBack = function(){
		noticeForm.action = '/board/notice';
		noticeForm.submit();
	}
	//상단공지 개수 카운트
	_notice.topNotcCountCheck = function(siteNo){
		if(siteNo == 'ALL'){
			abc.biz.board.notice.topNotcTotalCount();
		}else{
			abc.biz.board.notice.topNotcSiteCount(siteNo);
		}
	}
	//상단공지 공통일경우 개수 카운트
	_notice.topNotcTotalCount = function(){
		$.ajax({
			type :"post",
			url : "/board/notice/read-topnotice-total-count",
			async : false
		}).done(function(data){
			//debugger;
			if(data.result == 1){
				var artCount = data.artCount;
				var otsCount = data.otsCount;


				if($("#orgTopNotcYn").val() == abc.consts.BOOLEAN_TRUE){
					var siteNo = $("#orgSiteNo").val();
					if(siteNo == 'ALL'){
						artCount --;
						otsCount --;
					}else if(siteNo == "10000"){
						artCount --;
					}else{
						otsCount --;
					}
				}
				$("#topNotcARTCount").val(artCount);
				$("#topNotcOTSCount").val(otsCount);

				if(artCount>=3){
					alert("통합몰 상단공지는 3개를 넘을수 없습니다.");
					$("#topNotcYn_N").prop('checked', true);
					return false;
				}
				if(otsCount>=3){
					alert("OTS 상단공지는 3개를 넘을수 없습니다.");
					$("#topNotcYn_N").prop('checked', true);
					return false;
				}
			}else{
				alert("오류가 발생하였습니다."+data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}

	//개별 사이트 상단공지 카운트
	_notice.topNotcSiteCount = function(siteNo){
		var siteNo = {
				"siteNo" : siteNo
		}
		$.ajax({
			type :"post",
			url : "/board/notice/read-topnotice-site-count",
			data : siteNo,
			async : false
		}).done(function(data){
			if(data.result == 1){
				var topCount = data.count;
				if($("#orgTopNotcYn").val() == abc.consts.BOOLEAN_TRUE){
					if($("#orgSiteNo").val() == $("#siteNo").val()){
						topCount --;
					}
				}

				if(topCount>=3){
					alert("상단공지는 3개를 넘을수 없습니다.");
					$("#topNotcYn_N").prop('checked', true);
				}
				$("#topNotcCount").val(topCount);
			}else{
				alert("오류가 발생하였습니다."+data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}
	//전시안함 일경우 상단 공지설정 창 disabled
	_notice.topNoticeArea = function(){
		if($("input:radio[name=dispYn]:checked").val() == "N"){
			$("#topNotcYn_N").prop('checked', true);
			$("#topNotcYn_N").prop('disabled', true);
			$("#topNotcYn_Y").prop('disabled', true);
		}else{
			$("#topNotcYn_N").prop('disabled', false);
			$("#topNotcYn_Y").prop('disabled', false);
		}
	}
	//상단공지 체크
	_notice.topNotcYnCheck;
	//공지사항 validate
	_notice.noticeValidate = function(){
		var textAreaStr = CKEDITOR.instances.notcContText.getData();

		if("" == $("#siteNo").val()){
			alert("공지사이트를 확인해주세요");
			$("#siteNo").focus();

			return false;
		}
		if(abc.text.allNull($("#notcTitleText").val())){
			alert("공지 제목을 확인해주세요.");
			$("#notcTitleText").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#notcTitleText").val(), 100)){
			alert("공지사항 제목은 100byte를 넘기지 못합니다.");
			$("#notcTitleText").focus();

			return false;
		}
		if(abc.text.allNull($("#notcContText").val())){
			alert("내용을 확인해주세요.");
			$("#notcContText").focus();

			return false;
		}
		if(abc.text.isLimitLength(textAreaStr, 4000)){
			CKEDITOR.instances.notcContText.setData(abc.text.substringByte(textAreaStr, 4000));
			alert("내용은 4000byte를 넘을수 없습니다.");

			$("#notcContText").focus();
			return false;
		}
		if($('input[name="topNotcYn"]:checked').val() == abc.consts.BOOLEAN_TRUE){
			_notice.topNotcCountCheck($("#siteNo").val());
			if($("#topNotcCount").val() >= 3){
				$("#topNotcCount").val("");
				return false;
			}
			if($("#topNotcARTCount").val() >= 3){
				$("#topNotcARTCount").val("");
				return false;
			}
			if($("#topNotcOTSCount").val() >= 3){
				$("#topNotcARTCount").val("");
				return false;
			}

		}
		return true;
	}


})();