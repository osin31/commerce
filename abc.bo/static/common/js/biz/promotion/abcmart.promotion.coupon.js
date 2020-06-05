(function() {

	var _coupon = abc.object.createNestedObject(window,"abc.biz.promotion.coupon");

	/**
	 * 초기화
	 */
	_coupon.init = function(){

		var today = new Date();
		today.setMonth(today.getMonth()-1);

		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));

		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		abc.biz.display.common.checkBoxAll({allId: '#chkSite01', itemsClass: '[name=cpnUseGbnTypes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkDevice01', itemsClass: '[name=deviceCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkChannel01', itemsClass: '[name=chnnlNos]'});

		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();

		_coupon.sheet.init();
		_coupon.event();


		_coupon.getList();
	}

	/**
	 *  IBSheet 초기화
	 */
	_coupon.sheet = {};
	_coupon.sheet.init = function(){
		createIBSheet2(document.getElementById("couponSheet"), "list", "100%", "370px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1/*, AutoFitColWidth: 'init'*/};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};

		initSheet.Cols = [
			 {Header : "번호", Type:"Seq", SaveName:"", Width: 50, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"쿠폰번호" , Type:"Text", SaveName:"cpnNo", Width: 150,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"쿠폰명" , Type:"Html", SaveName:"cpnName", Width: 350,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"쿠폰 유형" , Type:"Text", SaveName:"cpnTypeCodeName", Width: 150,  Align:"Center", Edit:0, Sort:0}
			, {Header : "쿠폰 분류",	Type : "Combo",	SaveName : "normalCpnYn",	Width : 150,	Align : "Center",	Edit : 0,	ComboText : "일반쿠폰|플러스쿠폰",	ComboCode : "Y|N", Sort:0}
			, {Header : "쿠폰 속성",	Type : "Combo",	SaveName : "cpnUseGbnType",	Width : 100,	Align : "Center",	Edit : 0,	ComboText : "행사_일반|행사_다운로드|CS|eDM",	ComboCode : "E|F|C|D", Sort:0}
			, {Header : "할인율/금액" , Type:"Text", SaveName:"dscntText", Width: 150,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "사용처",	Type : "Combo",	SaveName : "usePlaceGbnType",	Width : 200,	Align : "Center",	Edit : 0,	ComboText : "온라인|오프라인|온라인 및 오프라인",	ComboCode : "O|F|A", Sort:0}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 200,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 300,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "쿠폰존 전시여부",	Type : "Combo",	SaveName : "cpnZoneDispYn",	Width : 100,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "전시여부",	Type : "Combo",	SaveName : "dispYn",	Width : 100,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 100,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "유효기간",	Type : "Text",	SaveName : "validDate", Width: 240, Align : "Center",	Edit : 0, Sort:0}
			, {Header : "발급시작일", Type:"Date", SaveName:"issueStartDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "발급종료일", Type:"Date", SaveName:"issueEndDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "작성자", Type:"Text", SaveName:"rgsterInfo", Width: 200, Align:"Center", Edit:0, Sort:0}
			, {Header : "작성일시", Type:"Date", SaveName:"rgstDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "수정자", Type:"Text", SaveName:"moderInfo", Width: 200, Align:"Center", Edit:0, Sort:0}
			, {Header : "수정일시", Type:"Date", SaveName:"modDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		/*list.FitColWidth();*/
		list.SetExtendLastCol(1);

		_coupon.sheet.event();

	}

	/**
	 * IBSheet 이벤트
	 */
	_coupon.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "cpnName" && value != "" ) {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/promotion/coupon/detail?cpnNo=" + list.GetRowData(row).cpnNo;
				}
			}
		}

		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}

	}

	/**
	 * 이벤트
	 */
	_coupon.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			if(!abc.date.searchValidate()){
				return false;
			}

			if($('[name=keywordType]').val() == '' && $('[name=keyword]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				return false;
			}
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_coupon.getList();
			return false;
		});

		/** 검색조건 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});

		$('#pageCount').on('change', function() {
			_coupon.getList();
		});
	}

	/**
	 * 리스트 조회
	 */
	_coupon.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/coupon/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list"
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};

		DataSearchPaging(param);

	}

	$(function() {
		_coupon.init();
	});

})();