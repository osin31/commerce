/***
 * 공지사항 관리
 *
 */
(function() {

	var _faq = abc.object.createNestedObject(window,"abc.biz.board.faq");

	/***************************************************************************
	*
	****************************************************************************/

	_faq.faqGridSheet = function() {

		var initFaqGridSheet = {};
		var pageCount = $('#pageCount').val();

		initFaqGridSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1, MergeSheet:msHeaderOnly};
		initFaqGridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initFaqGridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"faqSeq",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // FAQSEQ
			,	{Header:"",				Type:"Text",		SaveName:"sortSeq",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} // 정렬순서
			,	{Header:"",				Type:"Text",		SaveName:"rgsterNo",		Width: 0,		Align:"Center",		Edit:0,		Hidden:1	} //등록자번호
			,	{Header:"번호",			Type:"Seq",			SaveName:"",				Width: 15,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"TOP10설정여부",	Type:"Combo",		SaveName:"top10SetupYn",	Width: 30,		Align:"Center",		Edit:0,		ComboText : "설정|설정안함",			ComboCode : "Y|N"}
			,	{Header:"TOP10설정여부",	Type:"Combo",		SaveName:"top10Checked",	Width: 30,		Align:"Center",		Edit:0,		ComboText : "전시|전시안함",			ComboCode : "Y|N"}
			,	{Header:"FAQ유형",		Type:"Text",		SaveName:"cnslTypeName",	Width: 60,		Align:"Center",		Edit:0,		Sort:0		}
			,	{Header:"질문",			Type:"Html",		SaveName:"faqTitleText",	Width: 120,		Align:"Left",		Edit:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			Width: 50,		Align:"Center",		Edit:0, 	ComboText : "전시|전시안함",			ComboCode : "Y|N"}
			,	{Header:"작성자",			Type:"Text",		SaveName:"rgsterDpName",	Width: 50,		Align:"Center",		Edit:0, 	Sort:0		}
			,	{Header:"작성일시",		Type:"Date",		SaveName:"rgstDtm",			Width: 0,		Align:"Center",		Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN	}
			];

		createIBSheet2(document.getElementById("faqGrid"), "faqGridSheet", "100%", "429px");
		IBS_InitSheet(faqGridSheet, initFaqGridSheet);
		// Grid 건수 정보 표시
		faqGridSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		faqGridSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		faqGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		faqGridSheet.SetExtendLastCol(1);

	}
	//TOP10 설정화면 그리드
	_faq.top10GridSheet = function() {

		var initTop10GridSheet = {};
		var pageCount = $('#pageCount').val();

		initTop10GridSheet.Cfg = {SearchMode:smServerPaging2, Page:10, DeferredVScroll : 1, MaxSort:1};
		initTop10GridSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initTop10GridSheet.Cols = [
				{Header:"",				Type:"Text",		SaveName:"faqSeq",			Width: 0,		Align:"Center",		Edit:0,		Hidden:1		}
			,	{Header:"상태",			Type:"Status",		SaveName:"status",			Hidden:1,		Edit:1											}	
			,	{Header:"순위",			Type:"Int",			SaveName:"orgTop10SortSeq",	Width: 20,		Align:"Center",		Edit:0,		Sort:0, Hidden:1}
			,	{Header:"선택",			Type:"Radio",		SaveName:"radio",			Width: 20,		Align:"Center",		Edit:1,		Sort:0			}
			,	{Header:"순위",			Type:"Int",			SaveName:"top10SortSeq",	Width: 20,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"FAQ유형",		Type:"Text",		SaveName:"cnslTypeName",	Width: 60,		Align:"Center",		Edit:0						}
			,	{Header:"질문",			Type:"Html",		SaveName:"faqTitleText",	Width: 100,		Align:"Left",		Edit:0, 					}
			];

		createIBSheet2(document.getElementById("top10Grid"), "top10GridSheet", "100%", "600px");
		IBS_InitSheet(top10GridSheet, initTop10GridSheet);
		// Grid 건수 정보 표시
		top10GridSheet.SetCountPosition(3);
		// Grid width 자동 조절
		top10GridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		top10GridSheet.SetExtendLastCol(1);

		abc.biz.board.faq.top10Search();
	}

	_faq.doAction = function(sAction) {
		var _url = "/board/faq/";
		var _method = "";

		switch(sAction) {
			case "search" : // 목록 조회
				_method = "read-faq-list";

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

				// 페이지리스트 목록 개수
				var pageCount = $("#pageCount").val();

				var param = { url : _url + _method
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.frmSearch)
						, CPage : $('#listPageNum').val()
						, sheet : "faqGridSheet" };

				DataSearchPaging(param);
				$('#listPageNum').val('1');
				break;

			case "btnFaqSave" : // 등록
				if($("#saveCheck").val() == "1"){
					if(confirm("등록하시겠습니까?")){
						$("#faqContText").val(CKEDITOR.instances.faqContText.getData());
						if(this.faqValidate()){
							if($("#top10SetupYn_N").prop('disabled')){
								$("#top10SetupYn_N").prop('disabled', false);
							}
							this.createFaq();
						}
					}
				}else{
					if(confirm("수정하시겠습니까?")){  //수정
						$("#faqContText").val(CKEDITOR.instances.faqContText.getData());
						if(this.faqValidate()){
							if($("#top10SetupYn_N").prop('disabled')){
								$("#top10SetupYn_N").prop('disabled', false);
							}
							this.doAction("updateFaqDetail");
						}
					}
				}

				break;
				//수정
			case "updateFaqDetail" :
				_method = "update-faq-detail";
				
				var $form = $("#faqForm");
				
				$.ajax({
					type:"post",
					url: _url+_method,
					data: $form.serialize()
				}).done(function(data, textStatus, jqXHR) {
					if (data.result == 1) {
						alert("수정되었습니다.");
						var url = "/board/faq/"+"?menuNo="+abc.param.getParams().menuNo;
						$(location).attr('href', url);
					} else {
						alert(data.Message);
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
				});
				break;

			case "createFaqForm" : //등록 페이지
				_method = "create-faq-form";

				location.href = _url + _method +"?menuNo="+abc.param.getParams().menuNo;
				break;

			case "faqDelete" : //삭제
				_method = "remove-faq";
				if(confirm("삭제하시겠습니까?")){
					var $form = $("#faqForm");
					
					$.ajax({
						type:"post",
						url: _url+_method,
						data: $form.serialize()
					}).done(function(data, textStatus, jqXHR) {
						if (data.result == 1) {
							alert("삭제되었습니다.");
							var url = "/board/faq/"+"?menuNo="+abc.param.getParams().menuNo;
							$(location).attr('href', url);
						} else {
							alert(data.Message);
						}
					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
					});
				}
				break;
			case "top10ListForm" : //top10리스트 팝업
				_method = "top10-list-form";
				var params = {};
				params = abc.open.popup({
					url : _url+_method,
					width : 500,
					height : 750,
					params : params

				});

				break;
			}
	}
	
	//상세 페이지
	_faq.readDetailForm = function(faqSeq){
		frmSearch.action = "/board/faq/read-detail-form";
		frmSearch.submit();
	}
	
	//FAQ등록
	_faq.createFaq = function(){
		var $form = $("#faqForm");
		
		$.ajax({
			type:"post",
			url: "/board/faq/create-faq",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			if (data.result == 1) {
				alert("등록되었습니다.");
				var url = "/board/faq/"+"?menuNo="+abc.param.getParams().menuNo;
				$(location).attr('href', url);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}
	//검색조건 init
	_faq.searchValueSet = function(){
		var searchKey = $("#searchKey option:selected").val();

		//검색어 값을 초기화 후 포커스를 이동
//		$("#searchValue").val('');
		$("#searchValue").focus();

		if(searchKey == "faqTitleText"){
			$("#searchValue").off().on('input', function(event){
				
			});
		}else if(searchKey == "rgsterName"){
			$("#searchValue").off().on('input', function(event){
				abc.text.validateOnlyString(this);
			});
		}
	}
	//top10그리드 검색
	_faq.top10Search =  function(){
		var param = { url : "/board/faq/read-top10-list"
				, onePageRow : 200
//				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "top10GridSheet" };

		DataSearchPaging(param);
	}
	//상세 init
	_faq.faqDetailInitSet = function(cnslTypeCode, cnslTypeDtlCode, dispYn, top10SetupYn){
		if(!(abc.text.allNull(dispYn))){
			$("#cnslTypeCode").val(cnslTypeCode);
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode, "cnslTypeDtlCode");
			$("input:radio[name='dispYn'][value='"+dispYn+"']").prop('checked', true);
			$("input:radio[name='top10SetupYn'][value='"+top10SetupYn+"']").prop('checked', true);
			$("#cnslTypeDtlCode").val(cnslTypeDtlCode);
			$("#saveCheck").val("0");
		}
		
		if("1" == $("#saveCheck").val()){
			$("#deleteBtnArea").hide();
		}

	}
	
	//목록 버튼
	_faq.pageBack = function(currentRow){
		frmSearch.action = "/board/faq";
		frmSearch.submit();
	}
	
	//폼 초기화
	_faq.faqReload = function(dispYn, top10SetupYn, cnslTypeDtlCode){
		$.form("#faqForm").reset();
		var cnslTypeCode = $("#cnslTypeCode").val();
		
		abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode, "cnslTypeDtlCode");
		
		_faq.top10Area();
		
		if($("#saveCheck").val() == "1"){
			CKEDITOR.instances.faqContText.setData("");
		}else{
			console.log(faqContText)
			CKEDITOR.instances.faqContText.setData(faqContText);
		}
		$("#cnslTypeDtlCode").val(cnslTypeDtlCode);
	}
	
	//전시여부에 따른 top10설정여부 컨트롤
	_faq.top10Area = function(){
		if($("input:radio[name=dispYn]:checked").val() == "N"){
			$("#top10SetupYn_N").prop('checked', true);
			$("#top10SetupYn_N").prop('disabled', true);
			$("#top10SetupYn_Y").prop('disabled', true);
		}else{
			$("#top10SetupYn_N").prop('disabled', false);
			$("#top10SetupYn_Y").prop('disabled', false);
		}
	}
	
	//TOP10 페이지 순위 변동 버튼
	$("#upBtn, #downBtn").on('click', function(){
		if(abc.biz.board.faq.currentTop10Seq !=null){
			var btnParam = '';
			
			if('upBtn' == $(this).attr('id')) {
				btnParam = 'up';
			} else if('downBtn' == $(this).attr('id')) {
				btnParam = 'down';
			}
			_faq.top10ChangSort(btnParam);
		}else{
			alert("FAQ를 선택해주세요");
		}
	});
	
	//TOP10 순위 변동 저장 버튼
	$("#top10SaveBtn").on('click', function(){
		abc.biz.board.faq.updateSort();
	});
	
	//TOP10 취소 
	$("#top10cancel").on('click', function(){
		if(abc.biz.board.faq.currentTop10Seq !=null){
			if(!confirm("삭제하시겠습니까?")){
				return false;
			}
		}else{
			alert("FAQ를 선택해주세요");
		}
		abc.biz.board.faq.updateTop10Cancel();
	});
	
	//TOP10 취소
	_faq.updateTop10Cancel = function(){
		var currentRow = top10GridSheet.GetSelectRow();
		var faqSeq = top10GridSheet.GetCellValue(currentRow, "faqSeq");
		
		var faqData = {
			"faqSeq" : faqSeq ,
		};
		
		$.ajax({
			type:"post",
			url: "/board/faq/update-top10-cancel",
			data: faqData
		}).done(function(data, textStatus, jqXHR) {
			alert("삭제되었습니다.");
			abc.biz.board.faq.top10Search();
			opener.parent.abc.biz.board.faq.doAction("search");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	//TOP10 순서 설정
	_faq.top10ChangSort = function(btnParam){
		var fromRow = top10GridSheet.GetSelectRow();
		var toRow;
		var currentRow;
		
		if(btnParam == 'up'){
			toRow = fromRow - 1;	
		}else if(btnParam == 'down'){
			if(fromRow != top10GridSheet.GetDataLastRow()){
				toRow = fromRow + 1;
			}
		}
		if(toRow > 0){
			if(top10GridSheet.GetRowData(fromRow).faqSeq != '' && top10GridSheet.GetRowData(toRow).faqSeq != ''){
				var tempSeq = top10GridSheet.GetRowData(toRow).top10SortSeq;
				top10GridSheet.SetRowData(toRow, { top10SortSeq : top10GridSheet.GetRowData(fromRow).top10SortSeq});
				top10GridSheet.SetRowData(fromRow, { top10SortSeq : tempSeq});
			}
			
			if(fromRow > toRow){
				top10GridSheet.DataMove(toRow, fromRow);
			}else {
				top10GridSheet.DataMove(fromRow, toRow);
			}
			currentRow = toRow;
			
			top10GridSheet.SetSelectRow(currentRow);
		}else{
			return;
		}
	}
	
	//순위 변동 저장시에 저장
	_faq.updateSort = function() {
		
		var url = "/board/faq/update-sort"
		var param = { "Col" : "status" };
		
		top10GridSheet.DoSave(url, param);
	}
	
	//TOP10 설정 카운트
	_faq.top10CountCheck = function(){
		$.ajax({
			type :"get",
			url : "/board/faq/read-top10-save-count",
			async : false
		}).done(function(data){
			if(data.result == 1){
				if(data.count >= 30){
					alert("TOP10설정은 30개까지만 등록 가능합니다.");
					$("#top10SetupYn_N").prop('checked', true);
				}
				$("#top10Count").val(data.count);
			}else{
				alert("오류가 발생하였습니다.");
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
		
	}
	
	
	//FAQ validate
	_faq.faqValidate = function(){
		var textAreaStr = CKEDITOR.instances.faqContText.getData();

		if(abc.text.allNull($("#cnslTypeCode").val())){
			alert("FAQ 대분류를 확인해주세요");
			$("#cnslTypeCode").focus();

			return false;
		}
		if(abc.text.allNull($("#cnslTypeDtlCode").val())){
			alert("FAQ 소분류를 확인해주세요");
			$("#cnslTypeDtlCode").focus();

			return false;
		}
		if(abc.text.allNull($("#faqTitleText").val())){
			alert("FAQ 제목을 확인해주세요.");
			$("#faqTitleText").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#faqTitleText").val(), 100)){
			alert("FAQ 제목은 100byte를 넘기지 못합니다.");
			$("#faqTitleText").focus();

			return false;
		}

		if(abc.text.allNull($("#faqContText").val())){
			alert("내용을 확인해주세요.");
			$("#faqContText").focus();

			return false;
		}
		if(abc.text.isLimitLength(textAreaStr, 4000)){
			CKEDITOR.instances.faqContText.setData(abc.text.substringByte(textAreaStr, 4000));
			alert("내용은 4000byte를 넘을수 없습니다.");
			
			$("#faqContText").focus();
			return false;
		}
		if($('input[name="top10SetupYn"]:checked').val() == abc.consts.BOOLEAN_TRUE){
			abc.biz.board.faq.top10CountCheck();
			if($("#top10Count").val() >= 30){
				$("#top10Count").val("");
				return false;
			}
		}
		
		return true;
	}
	


})();