/***
 * 협력게시판관리
 * 
 * ***************************************
 * 협력게시판관리 메뉴 전체에서 사용하는 js
 * 협력게시판 메인, 조회/검색, 질문등록/수정팝업, 답변등록/수정팝업 모두에서 사용
 * ***************************************
 */
// 그리드 Click 이벤트
function vendorCoworkSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if ( Row != 0) {
		if ( vendorCoworkSheet.ColSaveName(Col) == "inqryTitleText") {
			var _url = "";
			var aswrStatCode = vendorCoworkSheet.GetCellValue(Row, "aswrStatCode");
			var aswrSubject = vendorCoworkSheet.GetCellValue(Row, "aswrSubject");
			
			if(aswrStatCode == '10002'){
				_url = "/vendor/cowork/reply-form";
			}else if( ($("#upAuthNo").val() == 'ROLE_10000' && aswrSubject =='ABC-MART') 
					|| ($("#upAuthNo").val() == 'ROLE_30000' && aswrSubject !='ABC-MART')){
				_url = "/vendor/cowork/reply-form";
			}else{
				_url = "/vendor/cowork/update-form";
			}
			
			var _params = {};
			
			_params.corprBoardSeq = vendorCoworkSheet.GetCellValue(Row, "corprBoardSeq");
			abc.open.popup({
				winname :	"vendorCoworkReplyPop",
				url 	:	_url,
				width 	:	1000,
				height	:	900,
				params	:	_params
			});
		}
	}
}



(function() {
	var _vendorCowork = abc.object.createNestedObject(window,"abc.biz.vendor.cowork");

	_vendorCowork.initVendorCoworkSheet = function() {
		createIBSheet2(document.getElementById("vendorCoworkGrid"), "vendorCoworkSheet", "100%", "370px");
		var initVendorCoworkSheet = {};
		var pageCount = $('#pageCount').val();
		
		initVendorCoworkSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initVendorCoworkSheet.Cols = [
				  {Header:"", 				Type:"CheckBox",	SaveName:"checkBoxCowork",		Width: 10, Align:"Center",  Sort:0}
				, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 15, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"번호", 			Type:"Text",		SaveName:"corprBoardSeq",		Width: 0,  Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"답변여부", 			Type:"Text",		SaveName:"aswrStatName",		Width: 30, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"답변여부코드", 		Type:"Text",		SaveName:"aswrStatCode",		Width: 40, Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"제목", 			Type:"Text",		SaveName:"inqryTitleText",		Width: 50, Align:"Left", 	Edit:0, FontUnderline:1, Cursor:"Pointer", Sort:0}
				, {Header:"문의주체",			Type:"Text",		SaveName:"inqrySubject",		Width: 40, Align:"Center",	Edit:0, Sort:0}
				, {Header:"최종수정자", 		Type:"Text",		SaveName:"maskingInqryInfo",	Width: 35, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"작성자", 			Type:"Text",		SaveName:"inqrerNo",			Width: 0,  Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"최종수정일시", 		Type:"Date",		SaveName:"inqryDtm",			Width: 30, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"답변주체",			Type:"Text",		SaveName:"aswrSubject",			Width: 40, Align:"Center",	Edit:0, Sort:0}
				, {Header:"업체번호",			Type:"Text",		SaveName:"vndrNo",				Width: 45, Align:"Center",	Edit:0, Sort:0, Hidden:1}
				, {Header:"최종답변자", 		Type:"Text",		SaveName:"maskingAswrInfo",		Width: 35, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"최종답변일시", 		Type:"Date",		SaveName:"aswrDtm",				Width: 30, Align:"Center", 	Edit:0, Sort:0}
		];

		
		// Grid 초기화
		IBS_InitSheet(vendorCoworkSheet, initVendorCoworkSheet);
		// Grid 건수 정보 표시
		vendorCoworkSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		vendorCoworkSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		vendorCoworkSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorCoworkSheet.SetExtendLastCol(1);
		
	}
	
	/**
	 * Action관리
	 */
	_vendorCowork.doActionVendorCowork = function(sAction){
		var corprBoardSeq = $("#corprBoardSeq").val();

		switch(sAction) {
			// 조회 
			case "search" :
				
				if(!_vendorCowork.searchValidation()){
					return;
				}
				
				var pageCount = $('#pageCount').val();

				var param = { url : "/vendor/cowork/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.vendorForm)
					, sheet : "vendorCoworkSheet" };
				
				DataSearchPaging(param);

				break;
			
			case "memoSave" :
				if (!confirm('저장하시겠습니까?')) {
					return;
				}
				var memoText = $("#memoText").val();
				var url = "/vendor/cowork/create-memo";

				if(abc.text.allNull(memoText)){
					alert("메모를 입력하세요.");
					$("#memoText").focus();
					return false;
				}
				
				$.ajax({
					type :"post",
					url : url,
					data : {"corprBoardSeq" : corprBoardSeq, "memoText" : memoText}
				})
				.done(function(data){
					if(data.result == abc.consts.BOOLEAN_TRUE){
						alert("저장되었습니다.");
						_vendorCowork.adminMemoLoad();
						$("#memoText").val("");
					} else {
						alert("저장에 실패하였습니다.");
					}
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});				
				break;
				
			case "coworkSave" :  // 협력게시판 질문글 등록/수정
				if (!confirm('저장하시겠습니까?')) {
					return;
				}
				if($("#inqryGbnType").val() != abc.consts.INQRY_GBN_TYPE_VENDOR
						&& $("#vndrNo").val() == ''
					){
					alert("대상업체를 선택하세요");
					abc.vndrSearchPopup(false, "setSelectedVendors");
					return;
				}
				
				if ($("#inqryTitleText").val() == null || $("#inqryTitleText").val() == "") {
					alert("제목을 입력해주세요.");
					$("#inqryTitleText").focus();
					return false;
				}
				
				$("#inqryContText").val(CKEDITOR.instances.inqryContText.getData());
				
				if($("#inqryContText").val() == null || $("#inqryContText").val() == ""){
					alert("내용을 입력해주세요.");
					$("#inqryContText").focus();
					return false;
				}

		        var formData = new FormData($('#vendorForm')[0]);

				$.ajax({
					processData: false,
		            contentType: false,
					type :"post",
					url : "/vendor/cowork/save",
					data: formData
				})
				.done(function(data){
					alert("저장되었습니다.");
					
					if(!abc.text.allNull(opener.vendorCoworkSheet)) {
						opener.abc.biz.vendor.cowork.doActionVendorCowork('search');
					}
					
					window.close();
				})
				.fail(function(e){
					alert("저장에 실패했습니다.");
				});

				break;
				
			case "coworkReplySave" :  // 협력게시판 답변글 등록/수정
				if (!confirm('저장하시겠습니까?')) {
					return;
				}
				var aswrContText = $("#aswrContText").val();
				var url = "/vendor/cowork/update-cowork";

				if(abc.text.isBlank(aswrContText)) {
					alert("답변 내용을 작성하세요.");
					$("#aswrContText").focus();
					return false;
				}

		        var formData = new FormData($('#vendorForm')[0]);
				$.ajax({
					processData: false,
		            contentType: false,
					type :"post",
					url : url,
					data : formData
				})
				.done(function(data){
					if(data.result == abc.consts.BOOLEAN_TRUE){
						alert("저장되었습니다.");
						
						if(!abc.text.allNull(opener.vendorCoworkSheet)) {
							opener.abc.biz.vendor.cowork.doActionVendorCowork('search');
						}
						
						window.close();
					} else {
						alert("저장에 실패하였습니다.");
					}
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});
				
				break;

			case "coworkDelete" :  // 협력게시판 글 삭제(displayYn 으로 처리)
				var checkRows = vendorCoworkSheet.FindCheckedRow("checkBoxCowork"); 
				var arrRow = checkRows.split("|"); 
				var corprBoardSeqArr = [];
				for (idx=0; idx<arrRow.length; idx++){   
					var inqrerNo =  vendorCoworkSheet.GetCellValue( arrRow[idx], "inqrerNo");
					if(inqrerNo != $("#adminNo").val()) {
						alert("작성자 자신의 글만 삭제할 수 있습니다.");
						return false;
					}
					corprBoardSeqArr.push(vendorCoworkSheet.GetCellValue( arrRow[idx], "corprBoardSeq"));
				}



				if(confirm("정말 삭제하시겠습니까?")){
					$.ajax({
						type :"post",
						url : "/vendor/cowork/delete-cowork",
						data : {
							  "corprBoardSeqArr" : corprBoardSeqArr
						}
					})
					.done(function(data){
						alert("삭제되었습니다.");
						_vendorCowork.doActionVendorCowork("search");
					})
					.fail(function(e){
						alert("실패하였습니다.");
					});
				}
				break;
		}
	}
	
	_vendorCowork.searchValidation = function(sAction){
		var searchKey = $("#searchKey option:selected").val();
		var searchValue = $("#searchValue").val();
		var inqryGbnType = $("#inqryGbnType option:selected").val();
		var vndrNo = $("#vndrNo").val();
		
		
		var searchDateKey = $("#searchDateKey option:selected").val();
		
		if(abc.text.allNull(searchKey) && !abc.text.allNull(searchValue)){
			alert("검색어 구분값을 선택해주세요.");
			$("#searchKey").focus();
			return false;
		}else if(!abc.text.allNull(searchKey) && abc.text.allNull(searchValue)){
			alert("검색어를 입력하세요");
			$("#searchValue").focus();
			return false;
		}

		if(!abc.date.searchValidate()){
			return false;
		}
		if(abc.text.allNull(searchDateKey) && !abc.text.allNull($("#fromDate").val())){
			alert("기간검색을 선택하세요");
			$("#searchDateKey").focus();
			return false;
		}
		if(!abc.text.allNull(searchDateKey) && abc.text.allNull($("#fromDate").val())){
			alert("기간을 선택하세요");
			$("#fromDate").focus();
			return false;
		}
		
		if(inqryGbnType == abc.consts.INQRY_GBN_TYPE_VENDOR && vndrNo == ''){
			alert("입점업체를 선택하세요");
			abc.vndrSearchPopup(false, "setSelectedVendors");
			return false;
		}
		
		return true;
	}
	
	// 화면 이벤트 
	_vendorCowork.event = function(){
		
		// 협력게시판 검색
		$("#vendorCoworkSerch").click(function(){
			_vendorCowork.doActionVendorCowork("search");
		});
		
		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		// 캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		/**
		 * 캘린더 버튼 설정(전체)
		 */
		$("a[name^=per1Year]").click(function(){
			abc.date.year(this);
		});
		
		$("a[name^=per1Month]").trigger("click");
		
		// 검색어 초기화
//		$("#searchKey").change(function(){
//			$("#searchValue").val("");
//		});

		$("#memoSaveBtn").click(function(){  // 관리자 메모 저장
			_vendorCowork.doActionVendorCowork("memoSave");
		});

		$("#coworkSaveBtn").click(function(){
			_vendorCowork.doActionVendorCowork("coworkSave");
		});

		$("#coworkReplySaveBtn").click(function(){
			_vendorCowork.doActionVendorCowork("coworkReplySave");
		});

		$("#counselScript").change(function(f) {  // 상담 스크립트
 			var counselScript = $("#counselScript option:selected");		// 상담유형코드
 			$("#aswrContText").val(counselScript.val());
		});

		$("#vendorCoworkRegist").click(function(){  // 협력게시판 등록 팝업
			var url = "/vendor/cowork/create-form";
			abc.open.popup({
				winname :	"coworkCreatePop",
				url 	:	url,
				width 	:	900,
				height	:	800
			});
		});

		// 입점업체 찾기 팝업
		$("#searchVendor").click(function(){
			abc.vndrSearchPopup(false, "setSelectedVendors");
		});

		// 작성자 구분 선택
		$("[name=inqryGbn]").change(function(){
			_vendorCowork.chgInqryGbn();
		});

		// 작성자 글 삭제
		$("#coworkDeleteBtn").click(function(){
			_vendorCowork.doActionVendorCowork("coworkDelete");
		});

		//초기화 버튼
		$("#resetButton").click(function(){
			$('form')[0].reset();
			CKEDITOR.instances.inqryContText.setData("");
			$("a[name^=per1Month]").trigger("click");
		});
		
		// 협력게시판 이메일 발송
		$("#sendCoworkEmail").click(function(){
			var chkList = vendorCoworkSheet.FindCheckedRow("checkBoxCowork").split("|");
			if(abc.text.allNull(chkList[0])){
				alert("선택된 입점사가 없습니다.");
				return;
			}

			var vendorInfoList = new Array();
			for(var i in chkList){
				if(vendorCoworkSheet.GetRowData(chkList[i]).aswrSubject == 'ABC-MART'){
					alert("답변주체에  ABC-MART가 선택되어있습니다.");
					return false;
				}
				vendorInfoList.push({
					vndrNo  : vendorCoworkSheet.GetRowData(chkList[i]).vndrNo
				})
			}

			abc.sendVendorEmailPop(vendorInfoList);
		});
		
		// 협렵게시판 sms발송
		$("#sendCoworkSms").click(function(){
			var chkList = vendorCoworkSheet.FindCheckedRow("checkBoxCowork").split("|");
			if(abc.text.allNull(chkList[0])){
				alert("선택된 입점사가 없습니다.");
				return;
			}

			var vendorInfoList = new Array();
			for(var i in chkList){
				if(vendorCoworkSheet.GetRowData(chkList[i]).aswrSubject == 'ABC-MART'){
					alert("답변주체에  ABC-MART가 선택되어있습니다.");
					return false;
				}
				vendorInfoList.push({
					vndrNo  : vendorCoworkSheet.GetRowData(chkList[i]).vndrNo
				})
			}

			abc.sendVendorSmsPop(vendorInfoList);
		});
		
	}
	
	_vendorCowork.chgInqryGbn = function(){

		$("#vndrName").hide();
		$("#vndrNo").val("");
		$("#vndrName").val("");

	}
	
	_vendorCowork.setVndrName = function(vndrNo){
		$.ajax({
			type :"get",
			url : "/vendor/cowork/read-vendor-detail",
			data : {"vndrNo" : vndrNo},
			dataType : "json"
		})
		.done(function(data){
			$("#vndrNo").val(data.vndrNo);
			$("#vndrName").val(data.vndrName);
		})
		.fail(function(e){
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	_vendorCowork.adminMemoLoad = function(){
		var url = "/vendor/cowork/read-memo";
		var corprBoardSeq = $("#corprBoardSeq").val();
		$.ajax({
			type :"post",
			url : url,
			data : {"corprBoardSeq" : corprBoardSeq},
			dataType : "html"
		})
		.done(function(data){
			$("#adminMemoList").html(data);
		})
		.fail(function(e){
			alert("--------------fail ");
		});
	}

	// 대시보드에서 더보기 클릭시 미답변 검색조회
	_vendorCowork.fromDashSearch = function(fromDash, tabIdx) {
		if(abc.consts.BOOLEAN_TRUE === fromDash) {
			$("#aswrStatCode1").prop("checked", true);
				
			if(tabIdx == 2) { 			// 자사
				$("#inqryGbnQ").prop("checked", true);
			}else if(tabIdx == 3) { 	// 입점
				$("#inqryGbnA").prop("checked", true);
			}
			
			_vendorCowork.doActionVendorCowork("search");
		}
	}
	
})();

function delMemo(corprBoardSeq, corprBoardMemoSeq){
	if(confirm("삭제하시겠습니까?")){
		$.ajax({
			type :"post",
			url : "/vendor/cowork/delete-memo",
			data : {"corprBoardSeq" : corprBoardSeq, "corprBoardMemoSeq" : corprBoardMemoSeq},
		}).done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				abc.biz.vendor.cowork.adminMemoLoad();
				$("#memoText").val("");
			} else {
				alert("삭제에 실패하였습니다.");
			}
		})
		.fail(function(e){
			alert("삭제에 실패하였습니다.");
		});
	}
}

function setSelectedVendors(arg){
	$("#vndrName").show();
	$("#searchVendor").show();
	$("#vndrName").val(arg.arrayVndrName);
	$("#vndrNo").val(arg.arrayVndrNo);
}