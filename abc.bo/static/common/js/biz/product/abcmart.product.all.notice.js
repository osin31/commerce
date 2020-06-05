(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.all.notice");

	/**
	 * 초기화
	 */
	_object.init = function() {

		_object.event();
		_object.sheet.init();
		_object.sheet.event();

	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("all-notice-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",				Width: 3,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",					Width: 3,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"업체번호",			Type:"Text",		SaveName:"vndrNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"업체상품전체공지순번",	Type:"Int",			SaveName:"vndrPrdtAllNotcSeq",	Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"자사/입점",			Type:"Combo",		SaveName:"vndrGbnType",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "자사|입점", ComboCode : "C|V" },
			{ Header:"업체명",			Type:"Text",		SaveName:"vndrName",			Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"제목",				Type:"Text",		SaveName:"allNotcTitleText",	Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"공지시작일시",		Type:"Date",		SaveName:"allNotcStartYmd",		Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:'yyyy.MM.dd' },
			{ Header:"공지종료일시",		Type:"Date",		SaveName:"allNotcEndYmd",		Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:'yyyy.MM.dd' },
			{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함", ComboCode : "Y|N" },
			{ Header:"등록자",			Type:"Text",		SaveName:"rgsterInfo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"등록일시",			Type:"Date",		SaveName:"rgstDtm",				Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.SetColFontUnderline('allNotcTitleText', 1);

		_object.sheet.list = list;

		_object.getList();

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if(row < 1) {
				return false;
			}
			if(list.ColSaveName(col) == 'allNotcTitleText') {
				var _allNotice = list.GetRowData(row);
				location.href = "/product/allNotice/detail?vndrPrdtAllNotcSeq="+_allNotice.vndrPrdtAllNotcSeq+"&vndrNo="+_allNotice.vndrNo;
			}
		}

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			$('#search-form')[0].reset();
			$(".calendar-month").click(); // 한달 클릭
			$('input[name="vndrGbnType"]:enabled').eq(0).prop('checked',true);
			$('input[name="vndrGbnType"][checked]').change();
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		// 자사/입점 구분
		$('input[name="vndrGbnType"]').change(function(e) {
			if($(this).val() == "V") {
				$('#vndr-name').parent().show();
			} else {
				$('#vndr-name').val('');
				$('#vndr-no').val('');
				$('#vndr-name').parent().hide();
			}
		});

		// 입점사 조회
		$('#search-vndr').click(function() {
			abc.vndrSearchPopup(false, "callbackVndr");
		});

		// 시작기간~끝기간 설정
		abc.biz.display.common.searchFormCalendarBtnEvent('input[name=periodStartDate]', 'input[name=periodEndDate]', '.area-calendar-btn-group');
		$(".calendar-month").click();

	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		if (!abc.date.searchValidate()) {
			return false;
		}
		var url = "/product/allNotice";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
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