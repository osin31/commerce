(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.giftCard");

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
		createIBSheet2(document.getElementById("giftCard-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",				Width: 4,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"기프트카드번호",		Type:"Int",			SaveName:"giftCardNo",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"카드유형",			Type:"Combo",		SaveName:"giftCardTypeCode",Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.codeCombo.GIFT_CARD_TYPE_CODE.text, ComboCode:_object.codeCombo.GIFT_CARD_TYPE_CODE.code },
			{ Header:"카드구분",			Type:"Combo",		SaveName:"giftCardGbnType",	Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:"권종형|충전형", ComboCode:"0|1" },
			{ Header:"판매가",			Type:"Text",		SaveName:"formatSellAmt",	Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"유효기간",			Type:"Combo",		SaveName:"validTermType",	Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", ComboText:"3개월|1년|3년|5년", ComboCode:"1|2|3|4" },
			{ Header:"기프트카드명",		Type:"Text",		SaveName:"giftCardName",	Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"이미지코드",			Type:"Text",		SaveName:"mgmtNoText",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"이미지URL",			Type:"Text",		SaveName:"imageUrl",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"이미지",			Type:"Text",		SaveName:"imageName",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"사용여부",			Type:"Combo",		SaveName:"useYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:"사용|사용안함", ComboCode:"Y|N" },
			{ Header:"작성자번호",			Type:"Text",		SaveName:"rgsterNo",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"작성자",			Type:"Text",		SaveName:"rgsterInfo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"작성일시",			Type:"Date",		SaveName:"rgstDtm",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"수정자번호",			Type:"Text",		SaveName:"moderNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"수정자",			Type:"Text",		SaveName:"moderInfo",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"수정일시",			Type:"Date",		SaveName:"modDtm",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		list.SetColFontUnderline('giftCardName', 1);
		list.SetColFontUnderline('imageName', 1);

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
			if(list.ColSaveName(col) == 'giftCardName') {
				var options = {
					winname :	"registration",
					method	: 	"get",
					url 	:	"/product/giftCard/detail" ,
					title 	:	"기프트카드 상세정보 ",
					width 	:	600,
					height	:	650,
					params	:	{ giftCardNo : list.GetRowData(row).giftCardNo }
				};
				abc.open.popup(options);
			}
		}

		list_OnRowSearchEnd = function(row) {
			_object.setToolTipAfterInit(list.GetRowData(row));
		}

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		/*
		// 승인상태
		var option = {
              allId: '#gift-card-type-code-all'
            , itemsClass: '.gift-card-type-code'
		}
		abc.biz.display.common.checkBoxAll(option);
		*/

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 검색영역 초기화
		$("#clear").off().on("click", function(e) {
			abc.biz.product.utils.initFormData("search-form");
			$(".calendar-month").click(); // 한달 클릭
			$('select option[value="Y"]').prop('selected', true);
		});

		// 검색 버튼 이벤트
		$("#search").off().on('click', function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").off().on('change', function() {
			_object.getList();
		});

		// 등록
		$('#rgst').click(function(e){
			var options = {
				winname :	"registration",
				method	: 	"get",
				url 	:	"/product/giftCard/detail" ,
				title 	:	"기프트카드 상세정보 ",
				width 	:	600,
				height	:	650,
			};
			abc.open.popup(options);
		});

		// 시작기간~끝기간 설정
		abc.biz.display.common.searchFormCalendarBtnEvent('input[name=periodStartDate]', 'input[name=periodEndDate]', '.area-calendar-btn-group');
		$(".calendar-month").click(); // 한달 클릭

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
		var param = {
				url : "/product/giftCard",
				onePageRow : $("#page-count").val(),
				subparam : $("#search-form").serialize(),
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	/**
	 * IBsheet 툴팁 설정 row가 1부터 시작
	 */
	_object.setToolTipAfterInit = function(item) {
		// 상품후기이미지 설정
		if(abc.biz.product.utils.isNotEmpty(item.imageUrl)) {
			var imageTag = "<img src=" + item.imageUrl + " alt=" + item.imageName + " width=\"200\" height=\"200\"/>";
			_object.sheet.list.SetToolTipText(item.seq, 'imageName', imageTag);
		}
	}

	$(function() {
		_object.init();
	});

})();