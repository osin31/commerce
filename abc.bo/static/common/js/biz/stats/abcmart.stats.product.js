(function() {
	var _object = abc.object.createNestedObject(window,"abc.biz.stats.product");

	_object.sheet = {};

	/**
	 * 페이지 초기화
	 */
	_object.init = function(){
		_object.event();
		_object.sheet.init();
		_object.sheet.event();
	}

	_object.event = function(){
		/**
		 * 검색버튼 이벤트
		 */
		$("#search").on("click", function() {
			_object.getList();
		});

		/**
		 * 찾기버튼 이벤트
		 */
		$("[data-button-popup]").on("click", function(e) {
			var type = $(this).data("button-popup");
			switch(type) {
			case "find-vendor" :
				// 입점사 찾기
				var options = { callback : "abc.biz.stats.product.callbackFindVendor" };
				window.abc.vndrSearchPopup(false, options.callback);
				break;
			case "find-brand" :
				// 브랜드 찾기
				e.preventDefault();
				var options = { callback : "abc.biz.stats.product.callbackFindBrand" };
				window.abc.brandSearchPopup(false, options);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){

			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
				return false;
			}

			$('#search-form').prop('action', '/stats/etc/download-productStatsExcel').prop('method', 'post').submit();
		});

		//초기화
		$("#resetBtn").on("click", function() {
			$("#search-form")[0].reset();
		});
	}

	/**
	 * 시트 초기화
	 */
	_object.sheet.init = function(){
		createIBSheet2(document.getElementById("product-stat-list"), "list", "100%", "492px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1, MergeSheet: msHeaderOnly, SumPosition:0 };
		initSheet.HeaderMode = { Sort:0, ColMove:0, ColResize:1 };

		initSheet.Cols = [
			{ Header:"상태",						Type:"Status",	SaveName:"status",		Width: 10,	Align:"Center",		Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"업체별 통계|업체별 통계",			Type:"Text",	SaveName:"vndrName", 	Width: 20,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"합계|합계",					Type:"Int",		SaveName:"totalCount", 	Width: 14,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"상태별 상품 수|판매대기",			Type:"Int",		SaveName:"sellWait", 	Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"상태별 상품 수|판매중",			Type:"Int",		SaveName:"sellSelling", Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"상태별 상품 수|일시품절",			Type:"Int",		SaveName:"sellPause", 	Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"상태별 상품 수|판매중지",			Type:"Int",		SaveName:"sellStop", 	Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"승인상태별 상품 수|임시저장",		Type:"Int",		SaveName:"stgTemp", 	Width: 12,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"승인상태별 상품 수|승인요청",		Type:"Int",		SaveName:"stgRequest", 	Width: 12,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"승인상태별 상품 수|승인대기",		Type:"Int",		SaveName:"stgWait", 	Width: 12,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"승인상태별 상품 수|승인반려",		Type:"Int",		SaveName:"stgReturn", 	Width: 12,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"승인상태별 상품 수|승인완료",		Type:"Int",		SaveName:"stgComplete", Width: 12,	Align:"Center",		Edit:0, Cursor:"Pointer" },
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		_object.getList();
	};

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnSearchEnd = function(code, msg) {
		    // 엑셀 다운로드 버튼 제어
			if(list.GetTotalRows() == 0) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		}
	}

	/**
	 * 리스트 조회
	 */
	_object.getList = function() {
		var toDay = new Date();
		$(".tc-red").text("* 기준일시 : "+toDay.format('yyyy-MM-dd hh:mm:ss'));

		var url = "/stats/etc/product-list";
		var serializedFormData = $("#search-form").serialize();
		var pageCount = $("#pageCount").val();
		var param = {
			url : url
			, onePageRow : pageCount
			, subparam : serializedFormData
			, sheet : "list" };

		DataSearchPaging(param);
	}

	/**
	 * 입점사 찾기 callback 이벤트
	 */
	_object.callbackFindVendor = function(data) {
		$("#vndr-no").val(data.arrayVndrNo[0]);
	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
	}

	$(function() {
		_object.init();
	});
})();