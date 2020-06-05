(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.mmnyApproval");

	/**
	 * 초기화
	 */
	_object.init = function() {

		abc.biz.display.common.searchFormCalendarBtnEvent('input[name=periodStartDate]', 'input[name=periodEndDate]', '.area-calendar-btn-group');
		$(".calendar-month").click();

		_object.event();
		_object.sheet.init();
		_object.sheet.event();
		abc.biz.display.common.checkBoxAll({allId: '#tierFlagCodeAll', itemsClass: '.tierFlagCode'});

	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("mmnyApproval-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		// 자사상품승인관리 – 목록 정렬 항목 : 상품코드, 채널구분, 브랜드명, 상품명, ERP등록일시
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",		Width: 4,   Align:"Center",	Edit:1, Sort:0},
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",				Width: 4,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"상품코드",			Type:"Text",		SaveName:"sangPumCd",		Width: 8,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"채널구분",			Type:"Combo",		SaveName:"chnnlNo",			Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer", ComboText:_object.codeCombo.text, ComboCode:_object.codeCombo.code },
			{ Header:"매장등급코드",		Type:"Combo",		SaveName:"tierFlagCode",	Width: 6,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.tierFlagCodeCombo.addInfo1, ComboCode:_object.tierFlagCodeCombo.name },
			{ Header:"브랜드명",			Type:"Text",		SaveName:"brandNm",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"상품명",			Type:"Text",		SaveName:"sangPumNm",		Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"ABC-MART 재고",		Type:"Text",		SaveName:"abcStockQty",		Width: 10,	Align:"Right",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"GRAND STAGE 재고",	Type:"Text",		SaveName:"gsStockQty",		Width: 10,	Align:"Right",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"ABC-KIDS 재고",		Type:"Text",		SaveName:"kidsStockQty",	Width: 10,	Align:"Right",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"ON THE SPOT 재고",	Type:"Text",		SaveName:"otsStockQty",		Width: 10,	Align:"Right",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"ERP 등록일시",		Type:"Date",		SaveName:"regDate",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		list.SetColFontUnderline('sangPumCd', 1);

		_object.sheet.list = list;

		_object.getList();

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if(list.ColSaveName(col) == 'sangPumCd') {

					$.ajax({
						url : "/product/product/check/online/registered",
						method : "GET",
						data : { "chnnlNo" : list.GetRowData(row).chnnlNo,"vndrPrdtNoText" : list.GetRowData(row).sangPumCd }
					}).done(function(data) {
						if (! abc.text.isBlank(data.result)) {
							alert("해당 상품은 온라인 상품 번호 [" + data.result.prdtNo+ "]로 기 등록된 상품 입니다.");
							_object.getList();
						} else {
							var param = list.GetRowData(row);
							param.vndrPrdtNoText = param.sangPumCd;

							abc.open.popup({
								url 	:	"/product/product/detail" ,
								winname :	"registration",
								method	: 	"get",
								title 	:	"prdt-mmny-aprv",
								width 	:	1480,
								height	:	970,
								params	:	param
							});
						}
					}).fail(function(data) {
						console.log(data);
					});

				}
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
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		//브랜드 찾기
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.product.mmnyApproval.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 기간 제어
		/*$(".term-date-wrap .btn-group .btn-func").click(function(e) {
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
			}
		});*/

		// 시작기간~끝기간 설정
		abc.biz.product.utils.setSearchStartAndEndDate($('input[name="periodStartDate"]'), $('input[name="periodEndDate"]'));
		$('a[name="per1Month"]').click();

		// 상품코드 20건까지 검색 제한
		abc.biz.product.utils.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 20);

	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandEnName);
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		if (!abc.date.searchValidate()) {
			return false;
		}
		var url = "/product/mmnyApproval/";
		var serializedFormData = $("#search-form").serialize() + "&prdtListLimit=20";
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