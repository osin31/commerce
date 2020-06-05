/***
 * 입점문의관리
 *
 */
// 그리드 Click 이벤트
function vendorInquirySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if ( Row != 0) {
		if ( vendorInquirySheet.ColSaveName(Col) == "maskingRsterInfo" && Value != "" ) {
			abc.memberDetailPopup(vendorInquirySheet.GetCellValue(Row, "memberNo"));
			return;
		}

		if ( vendorInquirySheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
			var _url = "/vendor/inquiry/read-detail-pop";

			var _params = {};
			_params.contactUsSeq = vendorInquirySheet.GetCellValue(Row, "contactUsSeq");

			abc.open.popup({
				winname :	"vendorInquiryDetailPop",
				url 	:	_url,
				width 	:	700,
				height	:	840,
				params	:	_params
			});

			return;
		}
	}
}



(function() {

	var _vendorInquiry = abc.object.createNestedObject(window,"abc.biz.vendor.inquiry");

	_vendorInquiry.initVendorInquirySheet = function() {
		createIBSheet2(document.getElementById("vendorInquiryGrid"), "vendorInquirySheet", "100%", "370px");
		var initVendorInquirySheet = {};
		var pageCount = $('#pageCount').val();

		initVendorInquirySheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initVendorInquirySheet.Cols = [
				  {Header:"" ,		 		Type:"Status",		SaveName:"status",					Width:  0,  Align:"", 		Edit:0, Hidden:1}
				, {Header:"", 				Type:"CheckBox",	SaveName:"",						Width: 10, Align:"Center", 	Sort:0}
				, {Header:"번호", 			Type:"Seq",			SaveName:"",						Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"번호", 			Type:"Text",		SaveName:"contactUsSeq",			Width:  0,  Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"사이트 ",			Type:"Text",		SaveName:"siteName",				Width: 20, Align:"Center",	Edit:0, Sort:0}
				, {Header:"문의유형",			Type:"Text",		SaveName:"cnslTypeDtlCodeName",		Width: 20, Align:"Center",	Edit:0, Sort:0}
				, {Header:"제목", 			Type:"Text",		SaveName:"inqryTitleText",			Width: 70, Align:"Left", 	Edit:0, Sort:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"작성자", 			Type:"Text",		SaveName:"maskingRsterInfo",		Width: 20, Align:"Center", 	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"회원번호", 			Type:"Text",		SaveName:"memberNo",				Width:  0, Align:"Center", 	Edit:0, Hidden:1, Sort:0}
				, {Header:"연락처", 			Type:"Text",		SaveName:"maskingHdphnNoText",		Width: 25, Align:"Center", 	Edit:0}
				, {Header:"이메일", 			Type:"Text",		SaveName:"maskingEmailAddrText",	Width: 30, Align:"Center", 	Edit:0}
				, {Header:"작성일시", 			Type:"Date",		SaveName:"inqryDtm",				Width: 30, Align:"Center", 	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		// Grid 초기화
		IBS_InitSheet(vendorInquirySheet, initVendorInquirySheet);
		// Grid 건수 정보 표시
		vendorInquirySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		vendorInquirySheet.SetPagingPosition(2);
		// Grid width 자동 조절
		vendorInquirySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorInquirySheet.SetExtendLastCol(1);

	}

	/**
	 * 관리자 목록 Action관리
	 */
	_vendorInquiry.doActionVendorInquiry = function(sAction){
		var contactUsSeq = $("#contactUsSeq").val();

		switch(sAction) {
			// 조회
			case "search" :

				if(!_vendorInquiry.searchValidation()){
					return;
				}

				var pageCount = $('#pageCount').val();

				var param = { url : "/vendor/inquiry/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.vendorForm)
					, sheet : "vendorInquirySheet" };

				DataSearchPaging(param);

				break;

			case "memoSave" :
				var memoText = $("#memoText").val();
				var url = "/vendor/inquiry/create-memo";

				if(abc.text.allNull(memoText)){
					alert("메모를 입력하세요.");
					$("#memoText").focus();
					return false;
				}

				$.ajax({
					type :"post",
					url : url,
					data : {"contactUsSeq" : contactUsSeq, "memoText" : memoText}
				})
				.done(function(data){
					if(data.result == abc.consts.BOOLEAN_TRUE){
						alert("저장되었습니다.");
						_vendorInquiry.adminMemoLoad();
						$("#memoText").val("");
					} else {
						alert("저장에 실패하였습니다.");
					}
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});
				break;

			case "inquirySave" :
				var aswrStatCode = $("input[name=aswrStatCode]:checked").val();
				var aswrContText = $("#aswrContText").val();
				var url = "/vendor/inquiry/update-inquiry";

				if(abc.text.isBlank(aswrContText)) {
					alert("답변 내용을 작성하세요.");
					$("#aswrContText").focus();
					return false;
				}

				if (typeof aswrStatCode == "undefined") {
					alert("답변 상태를 선택하세요.");
					return false;
				}

				$.ajax({
					type :"post",
					url : url,
					data : {
						    "aswrContText" : aswrContText
						  , "aswrStatCode" : aswrStatCode
						  , "contactUsSeq" : contactUsSeq
					}
				})
				.done(function(data){
					if(data.result == abc.consts.BOOLEAN_TRUE){
						alert("저장되었습니다.");
					} else {
						alert("저장에 실패하였습니다.");
					}
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});

				break;

			case "inquiryDelete" :
				if(confirm("삭제하시겠습니까?")){
					$.ajax({
						type :"post",
						url : "/vendor/inquiry/delete-inquiry",
						data : {
							  "contactUsSeq" : contactUsSeq
						}
					})
					.done(function(data){
						alert("삭제하였습니다.");
						opener.abc.biz.vendor.inquiry.doActionVendorInquiry("search");
						window.close();

					})
					.fail(function(e){
						alert("실패하였습니다.");
					});
				}
				break;
		}
	}

	_vendorInquiry.searchValidation = function(sAction){
		var searchKey = $("#searchKey option:selected").val();
		var searchValue = $("#searchValue").val();

		var searchDateKey = "inqryDtm"; // 작성일

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

		return true;
	}

	// 화면 이벤트
	_vendorInquiry.event = function(){

		// 초기화
		$("#vendorFormReset").click(function(){
			$('#vendorForm')[0].reset();
			$("a[name^=per1Month]").trigger("click");
		});

		// 입점문의 검색
		$("#vendorInquirySerch").click(function(){
			_vendorInquiry.doActionVendorInquiry("search");
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

		// 캘린더 버튼 설정(년)
		$("a[name^=perYearMonth]").click(function(){
			abc.date.year(this);
		});

		// 검색어 초기화
//		$("#searchKey").change(function(){
//			$("#searchValue").val("");
//		});

		$("#memoSaveBtn").click(function(){
			_vendorInquiry.doActionVendorInquiry("memoSave");
		});

		$("#btnClose").click(function() {
			window.close();
		});

		$("a[name^=per1Month]").trigger("click");
	}


	_vendorInquiry.adminMemoLoad = function(){
		var url = "/vendor/inquiry/read-memo";
		var contactUsSeq = $("#contactUsSeq").val();

		$.ajax({
			type :"post",
			url : url,
			data : {"contactUsSeq" : contactUsSeq},
			dataType : "html"
		})
		.done(function(data){
			$("#adminMemoList").html(data);
		})
		.fail(function(e){
			alert("--------------fail ");
		});
	}
	
	// 대시보드에서 더보기 클릭시 사이트 구분 검색조회 전체(1), 통합몰(2), OTS(3)
	_vendorInquiry.fromDashSearch = function(fromDash, tabIdx) {
		if(abc.consts.BOOLEAN_TRUE === fromDash) {
			if(tabIdx == 5) { 			// 통합몰
				$("#siteNo1").prop("checked", true);
			}else if(tabIdx == 6) { 	// OTS
				$("#siteNo2").prop("checked", true);
			}
			
			$("a[name^=per1Month]").trigger("click");
			
			_vendorInquiry.doActionVendorInquiry("search");
		}
	}

})();

function delMemo(contactUsSeq, contactUsMemoSeq){
	if(confirm("삭제하시겠습니까?")){
		$.ajax({
			type :"post",
			url : "/vendor/inquiry/delete-memo",
			data : {"contactUsSeq" : contactUsSeq, "contactUsMemoSeq" : contactUsMemoSeq},
		}).done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				abc.biz.vendor.inquiry.adminMemoLoad();
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