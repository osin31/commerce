(function() { // 입점사 수수료 변경이력 조회

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.etc.commission");

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
		createIBSheet2(document.getElementById("cmsn-stat-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
				{Header:"사이트",			Type:"Text",			SaveName:"siteName",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"입점사명",		Type:"Text",			SaveName:"vndrName",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"브랜드",			Type:"Text",			SaveName:"brandName",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"표준카테고리(대)",	Type:"Text",			SaveName:"stdCtgr",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품코드",		Type:"Text",			SaveName:"pdCode",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"온라인상품코드",	Type:"Text",			SaveName:"onPdCode",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품명",			Type:"Text",			SaveName:"pdName",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품이미지",		Type:"Image",			SaveName:"prdtImageUrl",Width: 30,		Align:"Center",		Edit:0,		Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" }
			,	{Header:"판매상태",		Type:"Text",			SaveName:"sellYn",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"전시여부",		Type:"Text",			SaveName:"dispYn",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"변경전 수수료",		Type:"Text",			SaveName:"beforeRate",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"변경후 수수료",		Type:"Text",			SaveName:"afterRate",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"수수료 유형",		Type:"Text",			SaveName:"rateType",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"수정일시",		Type:"Date",			SaveName:"rgstDate",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Format:"YmdHms" }
			,	{Header:"수정자",			Type:"Text",			SaveName:"rgsterName",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
		];

		// Grid 초기화
		IBS_InitSheet(list, initSheet);
		// Grid 건수 정보 표시
		list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		list.SetPagingPosition(2);
		// Grid width 자동 조절
		list.FitColWidth("5|5|7|7|6|7|10|9|5|5|7|7|7|10|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		list.SetExtendLastCol(2);
		list.SetDataRowMerge(1);

		_object.sheet.list = list;
	}

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
	 * 이벤트 바인딩
	 */
	_object.event = function(){

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 초기화
		$("#clear").off().on("click", function(e) {
			$("#form")[0].reset();
			$('a[name="perWeek"]').click();
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").on("change",function(){
			_object.getList();
		});

		// 입점사 찾기
		$('#search-vndr').click(function() {
			abc.vndrSearchPopup(false, "callbackVndr");
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
				return false;
			}
			$('#form').prop('action', '/stats/etc/download-cmsnStatsExcel').prop('method', 'post').submit();
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perYesterday" :
				abc.date.yesterday(this);
				break;
			case "perWeek" :
				abc.date.statWeek(this);
				break;
			case "per1Month" :
				abc.date.statMonth(this);
				break;
			case "perYear" :
				abc.date.statYear(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.stats.etc.commission.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 기본 날짜 세팅
		$('a[name="perWeek"]').click();
		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));
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
	_object.getList = function(){
		if(!$("#vndr-name").val()) {
			alert('입점사를 선택해주세요.')
			return;
		}
		if (!abc.date.searchValidate()) {
			return false;
		}

		DataSearchPaging({
			  url : "/stats/etc/cmsn-list"
			, onePageRow : $("#page-count").val()
			, subparam : $("#form").serialize()
			, sheet : "list"
		});
	}

	$(function() {
		_object.init();
	});

})();

function callbackVndr(data) {
	$('#vndr-name').val(data.arrayVndrName[0]);
	$('#vndr-no').val(data.arrayVndrNo[0]);
}