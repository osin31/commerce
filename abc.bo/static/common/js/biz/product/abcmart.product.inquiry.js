(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.inquiry");

	/**
	 * 초기화
	 */
	_object.init = function() {

		_object.event();
		_object.sheet.init();
		_object.sheet.event();
		// 회웑벙보 탭에서 이동시 세탕
		if(abc.biz.member) {
			abc.biz.member.memberDetail.moveTab("radio");
		}
	}

	_object.sheet = {};
	_object.sheet.init = function() {

		if(typeof productInquirySheet != 'undefined'){
			productInquirySheet.Reset();
			productInquirySheet.DisposeSheet(1);
		}

		createIBSheet2(document.getElementById("inquiry-list"), "productInquirySheet", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		// BO 상품Q&A 관리 – 목록 정렬 항목 : 온라인상품코드, 상품명, 제목, 자사/입점, 작성일시, 답변일시
		initSheet.Cols = [
			{ Header:"상태",					Type:"Status",		SaveName:"status",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"번호",					Type:"Seq",			SaveName:"seq",				Width: 6,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"상품문의순번",			Type:"Int",			SaveName:"prdtInqrySeq",	Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"답변상태",				Type:"Combo",		SaveName:"aswrStatCode",	Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", ComboText: _object.codeCombo.ASWR_STAT_CODE.text,	ComboCode: _object.codeCombo.ASWR_STAT_CODE.code },
			{ Header:"문의유형",				Type:"Combo",		SaveName:"cnslTypeCode",	Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", ComboText: _object.codeCombo.CNSL_TYPE_CODE.text,	ComboCode: _object.codeCombo.CNSL_TYPE_CODE.code },
			{ Header:"전시여부",				Type:"Combo",		SaveName:"dispYn",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", ComboText:"전시|전시안함", ComboCode: "Y|N" },
			{ Header:"온라인상품코드",			Type:"Text",		SaveName:"prdtNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"스타일",				Type:"Text",		SaveName:"styleInfo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"상품이미지대체텍스트",		Type:"Text",		SaveName:"productAltrnText",Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지",				Type:"Image",		SaveName:"productImageUrl",	Width: 30,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
			{ Header:"상품명",				Type:"Html",		SaveName:"prdtName",		Width: 30,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"제목",					Type:"Text",		SaveName:"inqryTitleText",	Width: 30,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"자사/입점",				Type:"Text",		SaveName:"vndrName",		Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"작성자번호",				Type:"Text",		SaveName:"memberNo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"작성자",				Type:"Text",		SaveName:"memberInfo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"작성일시",				Type:"Date",		SaveName:"inqryDtm",		Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"답변자번호",				Type:"Text",		SaveName:"aswrNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"답변자",				Type:"Text",		SaveName:"aswrInfo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"답변일시",				Type:"Date",		SaveName:"aswrDtm",			Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"ERP상품코드",			Type:"Text",		SaveName:"vndrPrdtNoText",	Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1  },
			{ Header:"채널 번호",				Type:"Text",		SaveName:"chnnlNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1  },
			{ Header:"사이트 번호",				Type:"Text",		SaveName:"siteNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1  },
			{ Header:"입점사번호",				Type:"Text",		SaveName:"vndrNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1  },
			{ Header:"자사입점구분",			Type:"Text",		SaveName:"mmnyPrdtYn",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1  }
		];

		IBS_InitSheet(productInquirySheet , initSheet);
		productInquirySheet.SetCountPosition(3);
		productInquirySheet.SetPagingPosition(2);
		productInquirySheet.FitColWidth();
		productInquirySheet.SetExtendLastCol(1);

		productInquirySheet.SetColFontUnderline('prdtNo', 1);
		productInquirySheet.SetColFontUnderline('prdtName', 1);
		productInquirySheet.SetColFontUnderline('inqryTitleText', 1);

		if(abc.biz.product.inquiry.gubun == "B") {
			//productInquirySheet.SetColFontUnderline('memberInfo', 1);
			//productInquirySheet.SetColFontUnderline('aswrInfo', 1);
		} else {
			productInquirySheet.SetColHidden('vndrName', 1);
		}

		_object.sheet.list = productInquirySheet;

		_object.getList();

	}



	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		productInquirySheet_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if(productInquirySheet.ColSaveName(col) == 'prdtNo') {
					// 상품상세(BO) 새창 출력
					abc.readonlyProductDetailPopup(productInquirySheet.GetRowData(row));
				} else if(productInquirySheet.ColSaveName(col) == 'prdtName') {
					// 상품상세(FO) 새창 출력
					abc.productFrontUrl(productInquirySheet.GetRowData(row).prdtNo);
				} else if(productInquirySheet.ColSaveName(col) == 'inqryTitleText') {
					// Q&A 답변관리 새창 풀력
					abc.open.popup({
						url 	:	"/product/inquiry/detail" ,
						winname :	"registration",
						title 	:	"prdt-inquiry-detail ",
						width 	:	1250,
						height	:	820,
						params	:	{ prdtInqrySeq : productInquirySheet.GetRowData(row).prdtInqrySeq }
					});
				} else if(productInquirySheet.ColSaveName(col) == 'vndrName') {
					if(productInquirySheet.GetRowData(row).mmnyPrdtYn == "N") {
						// 입점사 정보조회 새창 출력
						abc.vendorInfoPop(productInquirySheet.GetRowData(row).vndrNo);
					}
//				} else if(productInquirySheet.ColSaveName(col) == 'memberInfo') {
//					if(abc.biz.product.inquiry.gubun == "P") { return false; }
//					// 회원정보 새창 출력
//					abc.memberDetailPopup(productInquirySheet.GetRowData(row).memberNo);
//				} else if(productInquirySheet.ColSaveName(col) == 'aswrInfo') {
//					if(abc.biz.product.inquiry.gubun == "P") { return false; }
//					// 관리자 정보 새창 출력
//					var aswrNo = productInquirySheet.GetRowData(row).aswrNo;
//					if(aswrNo) {
//						abc.adminDetailPopup(aswrNo);
//					}
				}
			}
		}

		productInquirySheet_OnSearchEnd = function(code, msg, stCode, stMsg, responseText) {
			var list = productInquirySheet.ExportData({"Type":"json"}).data;
			_object.setUnderlineAfterInit(list);
			productInquirySheet.FitSize(1, 1);
			productInquirySheet.SetColWidth('prdtName', 150);
			productInquirySheet.SetColWidth('inqryTitleText', 200);
			productInquirySheet.SetColWidth('inqryDtm', 150);
			productInquirySheet.SetColWidth('aswrDtm', 150);
		}

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		// 입점사 조회
		$('#search-vndr').click(function() {
			var options = { callback : "callbackVndr", vndrName : $('[name=vndrName]').val() };
			abc.vndrSearchPopup(false, options);
		});

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			abc.biz.product.utils.initFormData("search-form");
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.product.inquiry.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		$("#brand-name").keydown(function(e){
			if(e.keyCode == 13)  $("#search").click();
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perToday" :
				abc.date.days(this);
				break;
			case "perWeek" :
				abc.date.week(this);
				break;
			case "per1Month" :
				abc.date.month(this);
				break;
			case "perYear" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 상담유형 선택 시
		$("#cnsl-type-code").off().on('change', function(f) {
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $(this).val(), "cnsl-type-dtl-code");
		});

		// 상담분류 선택 시
		$("#cnsl-type-dtl-code").off().on('change', function(f) {
			abc.getCounselScriptTitle($("#cnsl-type-code").val(), $(this).val(), "counsel-script-list");
		});

		if(abc.biz.product.inquiry.gubun == "P") {
			$('.vndr-tr').hide();
		}

		// 시작기간~끝기간 설정
		abc.biz.product.utils.setSearchStartAndEndDate($('input[name="periodStartDate"]'), $('input[name="periodEndDate"]'));
		$('a[name="per1Month"]').click();

		// 상품코드 20건까지 검색 제한
		abc.biz.product.utils.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);

		// 자사/입점 제어
		$('[name=mmnyPrdtYn]').off().on('click', function(e) {
		   if($(this).val() == "N"){
			   $("#search-vndr").removeClass("disabled");
			   $("#vndr-name").removeAttr("readonly");
		   } else {
			   $("#vndr-name").val('');
			   $("#vndr-name").prop('readonly', true);
			   $("#search-vndr").addClass("disabled");
		   }
		});
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		//회원명일경우 걸러내야함
		$("select[name=searchMemberType]").change(function(){
			if($("select[name=searchMemberType]").val() == "member-name"){
				$("#hdphnBackNoText").show(); 
			}else{
				$("#hdphnBackNoText").hide();
			}
		});


	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandName);
	}




	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		if (!abc.date.searchValidate()) {
			return false;
		}
		if($("select[name=searchMemberType]").val() == "member-name"){
			if(abc.text.allNull($("input[name=searchMember]").val())
					&& !abc.text.allNull($("#hdphnBackNoText").val())){
				alert("회원명을 입력해주세요.");
				return false;
			}
			if(!abc.text.allNull($("input[name=searchMember]").val()) 
					&& abc.text.allNull($("#hdphnBackNoText").val())){
				alert("휴대폰번호 뒷자리를 입력해주세요.");
				return false;
			}
			if(!abc.text.allNull($("input[name=searchMember]").val()) 
					&& $("#hdphnBackNoText").val().length < 4){
				alert("휴대폰번호 뒤 4자리를 입력해주세요.");
				return false;
			}
		}
		var url = "/product/inquiry";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "productInquirySheet"
			};
		DataSearchPaging(param);
	}

	/**
	 * 자사/입점 밑줄 이벤트
	 */
	_object.setUnderlineAfterInit = function(list) {
		for(var i=0; i<list.length; i++) {
			var item = list[i];
			if(item.mmnyPrdtYn == "N") {
				_object.sheet.list.SetCellFontUnderline(item.seq, "vndrName", 1) ;
			} else {
				_object.sheet.list.SetCellText(item.seq, 'vndrName', '자사');
			}
		}
	}


	$(function() {
		_object.init();
	});

})();


/**
 * 입점사 찾기 팝업 콜백함수
 */
function callbackVndr(data) {
	$('#vndr-name').val(data.arrayVndrName[0]);
	$('#vndr-no').val(data.arrayVndrNo[0]);
}