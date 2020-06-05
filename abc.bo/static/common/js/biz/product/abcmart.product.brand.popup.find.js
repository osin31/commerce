(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.brand");

	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');

		$('[name=brandName]').val(_object.parameter.getParameter("brandName") != null ? decodeURI(_object.parameter.getParameter("brandName")) : "");

		_object.sheet.init();
		_object.sheet.event();
		_object.event();
	}

	_object.sheet = {};

	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("product-brand-list"), "list", "100%", "370px");

		var initSheet = {};
		var pageCount = $('#page-count').val();

		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:0, ColMove:0, ColResize:1 };
		initSheet.Cols = [
			{ Header:"상태",			Type:"Status",		SaveName:"status",				Width: 0,	Align:"Center",		Edit:0,	Cursor:"Pointer",	Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 5,	Align:"Center",		Edit:1, Cursor:"Pointer" },
			{ Header:"번호",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",		Edit:0, Cursor:"Pointer" },
//			{ Header:"브랜드 구분",		Type:"Combo",		SaveName:"onlnPrvuseBrandYn",	Width: 7,	Align:"Center",		Edit:0, Cursor:"Pointer",	ComboText : "온라인|공통",	ComboCode : "Y|N" },
			{ Header:"사이트",		Type:"Combo",		SaveName:"siteNo",				Width: 7,	Align:"Center",		Edit:0, Cursor:"Pointer", ComboText : siteGubunText,	ComboCode : siteGubunCode },
			{ Header:"브랜드ID",		Type:"Text",		SaveName:"brandNo",				Width: 5,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"국문브랜드명",	Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"영문브랜드명",	Type:"Text",		SaveName:"brandEnName",			Width: 10,	Align:"Center",		Edit:0, Cursor:"Pointer" },
//			{ Header:"사용여부",		Type:"Combo",		SaveName:"useYn",				Width: 5,	Align:"Center",		Edit:0, Cursor:"Pointer",	ComboText : "예|아니오",	ComboCode : "Y|N" }
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
	_object.sheet.event = function() {
		var isMultiple = _object.parameter.isMultiple();	// 다중선택여부
		console.log("다중선택여부값 : " + isMultiple);

		// 전체 선택 체크박스 제어
		list_OnBeforeCheckAll = function(row, col) {
			if(!isMultiple) {
				return false;
			}
		}

		// 체크박스 선택 제어
		list_OnBeforeCheck = function(row, col) {
			if(!isMultiple && list.ColSaveName(col) == 'checked') {
				// 체크박스 모두 해제
				var checkedRows = list.FindCheckedRow("checked").split('|');
				for(var i in checkedRows) {
					var currentRow = checkedRows[i];
					list.SetRowData(currentRow, { checked : 0 });
				}
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function(){

		console.log(document.forms.searchForm);
		$(document.forms.searchForm).on('submit', function(){
			_object.getList();
			return false;
		});

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			console.log("검색 영역을 초기화합니다.");
			abc.biz.product.utils.initFormData("searchForm");
		});

		// 검색
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		$("#save").click(function() {
			var result = [];
			var brandList = list.ExportData({ Type : "json" }).data;

			if(brandList.length > 0) {
				for(var i=0 ; i<brandList.length ; i++) {
					if(brandList[i].checked == 1) {
						result.push(brandList[i]);
					}
				}
			} else {
				alert("조회된 데이터가 없습니다.");
				return;
			}

			// callback 실행
			var callback = _object.parameter.getCallback();
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

		$("#page-count").on('change',function(){
			_object.getList();
		});

	}

	_object.parameter = {};

	/**
	 * 다중선택여부 값 반환
	 */
	_object.parameter.isMultiple = function() {
		var multiple = _object.parameter.getParameter("multiple");
		return multiple === "true" ? true : false;
	}

	/**
	 * 콜백함수 반환
	 */
	_object.parameter.getCallback = function() {
		var callback = _object.parameter.getParameter("callback");
		var result = null;

		if(callback) {
			callback = "window.opener." + callback;
			result = new Function("return " + callback)();
		}
		return result;
	}

	/**
	 * QueryString에서 해당 key에 대한 값을 반환합니다.
	 */
	_object.parameter.getParameter = function(findParamName) {
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
	 * 목록 조회
	 */
	_object.getList = function() {
		var url = "/noacl/brand/find";
		var serializedFormData = $("#searchForm").serialize();
		var pageCount = $("#page-count").val();
		var param = {
			url : url
			, onePageRow : pageCount
			, subparam : serializedFormData
			, sheet : "list" };
		console.log(param);
		DataSearchPaging(param);
	}

	$(function() {
		_object.init();
	});

})();