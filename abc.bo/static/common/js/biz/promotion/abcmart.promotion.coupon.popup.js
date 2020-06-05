(function() {

	var _couponPopup = abc.object.createNestedObject(window,"abc.biz.promotion.coupon.popup");

	/**
	 * 초기화
	 */
	_couponPopup.init = function(){
		console.log('init');
		_couponPopup.sheet.init();
		_couponPopup.event();

		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');

		var today = new Date();
		today.setMonth(today.getMonth()-1);

		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));

		_couponPopup.getList();
	}

	/**
	 *  IBSheet 초기화
	 */
	_couponPopup.sheet = {};
	_couponPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("couponSheet"), "list", "100%", "370px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:false};

		initSheet.Cols = [
			 {Header : "선택", Type:"CheckBox", SaveName:"checkCpn", Width: 10, Align:"Center", 	Edit:1, Sort:0}
			, {Header :"쿠폰번호" , Type:"Text", SaveName:"cpnNo", Width: 15,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"쿠폰명" , Type:"Text", SaveName:"cpnName", Width: 60,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Align:"Left"}
			, {Header :"쿠폰 유형" , Type:"Text", SaveName:"cpnTypeCodeName", Width: 15,  Align:"Center", Edit:0, Sort:0}
			, {Header : "쿠폰 분류",	Type : "Combo",	SaveName : "normalCpnYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "일반쿠폰|플러스쿠폰",	ComboCode : "Y|N", Sort:0}
			, {Header : "쿠폰 속성",	Type : "Combo",	SaveName : "cpnUseGbnType",	Width : 20,	Align : "Center",	Edit : 0,	ComboText : "행사_일반|행사_다운로드|CS|eDM",	ComboCode : "E|F|C|D", Sort:0}
			, {Header : "할인율/금액" , Type:"Text", SaveName:"dscntText", Width: 30,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "사용처",	Type : "Combo",	SaveName : "usePlaceGbnType",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "온라인|오프라인|온라인 및 오프라인",	ComboCode : "O|F|A", Sort:0}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "전시여부",	Type : "Combo",	SaveName : "dispYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "유효기간",	Type : "Text",	SaveName : "validDate", Align : "Center",	Edit : 0, Sort:0}
			, {Header : "발급시작일", Type:"Date", SaveName:"issueStartDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "발급종료일", Type:"Date", SaveName:"issueEndDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "작성자", Type:"Text", SaveName:"rgsterName", Width: 20, Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header : "작성일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
			, {Header : "수정자", Type:"Text", SaveName:"moderName", Width: 20, Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header : "수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
			, {Header : "총발급건수", Type:"Text", SaveName:"totalIssueCount", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "총발급제한수", Type:"Text", SaveName:"totalIssueLimitCount", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "총발급제한수여부", Type:"Text", SaveName:"totalIssueLimitYn", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "유효기간 타입", Type:"Text", SaveName:"validTermGbnType", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "유효기간 종료일", Type:"Text", SaveName:"validEndDtm", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "1인당 발급제한 여부", Type:"Text", SaveName:"per1psnIssueLimitYn", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "1인당 최대 발급 수", Type:"Text", SaveName:"per1psnMaxIssueCount", Width: 20, Align:"Center", Edit:0, Hidden:1}
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		_couponPopup.sheet.event();

	}

	/**
	 * IBSheet 이벤트
	 */
	_couponPopup.sheet.event = function(){
		/*list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "checkCpn" && value != "" ) {

				}
			}
		}*/

	}

	/**
	 * 이벤트
	 */
	_couponPopup.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_couponPopup.getList();
			return false;
		});

		$('#save-coupon').on('click', function(e) {
			var chkCnt = list.CheckedRows("checkCpn");
			if(!_couponPopup.parameter.isMultiple()) {
				if (chkCnt > 1) {
					alert('1개 이상 선택할 수 없습니다.');
					return;
				}
			}

			var result = [];
			var couponList = list.ExportData({ Type : "json" }).data;

			if(couponList.length > 0) {
				for(var i=0 ; i<couponList.length ; i++) {
					if(couponList[i].checkCpn == 1) {
						result.push(couponList[i]);
					}
				}
			} else {
				alert("조회된 데이터가 없습니다.");
				return;
			}

			if(!_couponPopup.parameter.isMultiple()) {
				if(result[0].validTermGbnType == 'T') {
					var date = new Date();
					var compareDate = result[0].validEndDtm - date.getTime();
					compareDate = Math.floor(compareDate / (1000 * 60 * 60 * 24));
					if(compareDate > 0 && compareDate < 3) {
						if(!confirm('해당 쿠폰은 ' + compareDate + '일 남았습니다. 그대로 진행하시겠습니까?')) return;
					} else if(compareDate < 0) {
						alert('해당 쿠폰은 유효기간이 지났습니다.');
						return;
					}
				}
			}

			// callback 실행
			var callback = _couponPopup.parameter.getCallback();
			if(typeof callback === "function") {
				if(result.length > 0) {
					callback(result);
					self.close();
				} else {
					alert("선택된 데이터가 없습니다.");
				}
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});

		$('#chkSite01').on('click', function(e) {
			if($(this).is(':checked')) {
				$('[name=cpnUseGbnTypes]').prop('checked', true);
			} else {
				$('[name=cpnUseGbnTypes]').prop('checked', false);
			}
		});

		$('[name=cpnUseGbnTypes]').on('click', function(e) {
			if($('[name=cpnUseGbnTypes]').length == $('[name=cpnUseGbnTypes]:checked').length) {
				$('#chkSite01').prop('checked', true);
			} else {
				$('#chkSite01').prop('checked', false);
			}
		});

		$('#chkDevice01').on('click', function(e) {
			if($(this).is(':checked')) {
				$('[name=deviceCodes]').prop('checked', true);
			} else {
				$('[name=deviceCodes]').prop('checked', false);
			}
		});

		$('[name=deviceCodes]').on('click', function(e) {
			if($('[name=deviceCodes]').length == $('[name=deviceCodes]:checked').length) {
				$('#chkDevice01').prop('checked', true);
			} else {
				$('#chkDevice01').prop('checked', false);
			}
		});

		$('#chkChannel01').on('click', function(e) {
			if($(this).is(':checked')) {
				$('[name=chnnlNos]').prop('checked', true);
			} else {
				$('[name=chnnlNos]').prop('checked', false);
			}
		});

		$('[name=chnnlNos]').on('click', function(e) {
			if($('[name=chnnlNos]').length == $('[name=chnnlNos]:checked').length) {
				$('#chkChannel01').prop('checked', true);
			} else {
				$('#chkChannel01').prop('checked', false);
			}
		});

		$('#pageCount').on('change', function() {
			_couponPopup.getList();
		});

		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm', false);
		});
	}

	_couponPopup.parameter = {};

	/**
	 * 다중선택여부 값 반환
	 */
	_couponPopup.parameter.isMultiple = function() {
		var multiple = _couponPopup.parameter.getParameter("multiple");
		return multiple === "true" ? true : false;
	}

	/**
	 * 콜백함수 반환
	 */
	_couponPopup.parameter.getCallback = function() {
		var callback = _couponPopup.parameter.getParameter("callback");
		var result = null;

		var form = $.form(document.forms.frm);

		if(callback) {
			callback = "window.opener." + callback;
			result = new Function("return " + callback)();
		}
		return result;
	}

	/**
	 * QueryString에서 해당 key에 대한 값을 반환합니다.
	 */
	_couponPopup.parameter.getParameter = function(findParamName) {
		var queryString = window.location.search.slice(1);
		var queryArray = queryString.split("&");
		var result = null;

		for(var i=0 ; i<queryArray.length ; i++) {
			var query = queryArray[i].split("=");
			if(query.length === 2) {
				if(query[0] === findParamName) {
					result = query[1];
					break;
				}
			} else {
				console.log("파라미터 key/value 형태가 올바르지 않습니다. " + queryArray[i]);
			}
		}
		return result;
	}


	/**
	 * 리스트 조회
	 */
	_couponPopup.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/coupon/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" };

		DataSearchPaging(param);

	}

	$(function() {
		_couponPopup.init();
	});

})();