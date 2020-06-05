(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.brand");

	/**
	 * 초기화
	 */
	_object.init = function() {

		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();

		_object.sheet.init();
		_object.sheet.event();
		_object.event();
	}

	_object.sheet = {};

	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("product-brand-list"), "list", "100%", "370px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:0, ColMove:0, ColResize:1 };

		initSheet.Cols = [
			{ Header:"상태",			Type:"Status",	SaveName:"status",				Width: 10,	Align:"Center",		Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"번호",			Type:"Seq",		SaveName:"",					Width: 10,	Align:"Center",		Edit:0,	Cursor:"Pointer" },
			{ Header:"사이트 구분",	Type:"Combo",	SaveName:"siteNo",				Width: 8,	Align:"Center",		Edit:0, Cursor:"Pointer", ComboText : siteGubunText,	ComboCode : siteGubunCode },
			{ Header:"브랜드ID",		Type:"Text",	SaveName:"brandNo",				Width: 5,	Align:"Center",		Edit:0, Cursor:"Pointer", FontUnderline:1 },
			{ Header:"영문브랜드명",	Type:"Text",	SaveName:"brandEnName",			Width: 15,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"국문브랜드명",	Type:"Text",	SaveName:"brandName",			Width: 15,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"사용여부",		Type:"Combo",	SaveName:"useYn",				Width: 5,	Align:"Center",		Edit:0, Cursor:"Pointer", ComboText : "예|아니오", ComboCode : "Y|N" },
			{ Header:"수정자",		Type:"Text",	SaveName:"moderInfo",			Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"수정일시",		Type:"Date",	SaveName:"modDtm",				Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN }
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		_object.sheet.list = list;

		_object.getList();
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if (row !== 0 && col === list.SaveNameCol('brandNo')) {
				abc.biz.display.common.setFormParameter.setDetailMove();
				// 영문브랜드명을 선택한 경우, 상세정보보기로 이동
				var url = "/product/brand/detail?brandNo=" + list.GetRowData(row).brandNo;
				location.href = url;
			}
		}

		list_OnRowSearchEnd = function(row) {
			// 수정자 정보가 있는 경우, "수정자아이디(수정자이름)" 으로 출력되도록 작업
			if(list.GetRowData(row).moderId) {
				var moderInfo = "";
				moderInfo += list.GetRowData(row).moderId
				moderInfo += "(" + list.GetRowData(row).moderName + ")";
				list.SetRowData(row, { moderInfo : moderInfo });
			}
		}

		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function(){
		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		$("#search").click(function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_object.getList();
		});

		$("#pageCount").on("change", function(event) {
			_object.getList();
		});

		// 검색조건 초기화
		$('#clearBtn').on('click', function() {
			$('#search-form')[0].reset();
		});
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		var url = "/product/brand/brand";
		var serializedFormData = $("#search-form").serialize();
		var pageCount = $("#pageCount").val();
		var param = {
			url : url
			, onePageRow : pageCount
			//, subparam : FormQueryStringEnc(document.forms.searchForm)
			, subparam : serializedFormData
			, sheet : "list"
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};

		DataSearchPaging(param);
	}

	$(function() {
		_object.init();
	});

})();