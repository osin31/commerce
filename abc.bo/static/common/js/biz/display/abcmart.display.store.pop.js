(function() {

	var _store = abc.object.createNestedObject(window,"abc.biz.display.store");

	/**
	 * 초기화
	 */
	_store.init = function() {

		_store.event();
		_store.initSheet();

		abc.biz.display.common.checkBoxAll({allId: '#chkServiceAll', itemsClass: '.storeServiceCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkStoreAll', itemsClass: '.storeTypeCode'});
	}

	/**
	 *  IBSheet 초기화
	 */
	_store.initSheet = function() {

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:'번호', 				Type:'Seq',		SaveName:'',					Width:10,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'',					Type:'CheckBox',SaveName:'checkbox',			Width:10,	Edit:1,	Align:'Center'}
			  , {Header:'전시여부',				Type:'Combo',	SaveName:'dispYn',				Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오",	ComboCode : "Y|N"}
			  , {Header:'지역구분',				Type:'Text',	SaveName:'areaName',			Width:25,	Edit:0,	Align:'Center'}
			  , {Header:'매장형태', 			Type:'Combo',	SaveName:'storeTypeCode',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'매장명',		 		Type:'Text',	SaveName:'storeName',			Width:30,	Edit:0,	Align:'Center'}
			  , {Header:'매장ID', 			Type:'Text',	SaveName:'storeIdText',			Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'매장 제공 서비스', 		Type:'Text',	SaveName:'storeServiceArr',		Width:80,	Edit:0,	Align:'Center'}
			  , {Header:'매장픽업\n가능여부', 		Type:'Combo',	SaveName:'pickupPsbltYn',		Width:35,	Edit:0,	Align:'Center',	ComboText:"가능|불가능",ComboCode : "Y|N"}
			  , {Header:'임직원가 구매\n가능여부',	Type:'Combo',	SaveName:'empPriceBuyPsbltYn',	Width:40,	Edit:0,	Align:'Center',	ComboText:"가능|불가능",ComboCode : "Y|N"}
			  , {Header:'주소',				Type:'Text',	SaveName:'fullAddr',			Width:85,	Edit:0,	Align:'Center'}
			  , {Header:'매장번호', 			Type:'Text',	SaveName:'storeNo',				Hidden:1,	Edit:0}
			  , {Header:'영업시작시간', 			Type:'Text',	SaveName:'businessStartTime',	Hidden:1,	Edit:0}
			  , {Header:'영업종료시간', 			Type:'Text',	SaveName:'businessEndTime',		Hidden:1,	Edit:0}
			  , {Header:'전화번호', 			Type:'Text',	SaveName:'TelNoText',			Hidden:1,	Edit:0}
			  , {Header:'우편번호', 			Type:'Text',	SaveName:'postCodeText',		Hidden:1,	Edit:0}
			  , {Header:'우편주소', 			Type:'Text',	SaveName:'postAddrText',		Hidden:1,	Edit:0}
			  , {Header:'상세주소', 			Type:'Text',	SaveName:'dtlAddrText',			Hidden:1,	Edit:0}
		];

		createIBSheet2(document.getElementById("storeSheet"), "list", "100%", "370px");
		IBS_InitSheet(list, initSheet);

		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.InitDataCombo(0, "storeTypeCode", this.codeCombo.STORE_TYPE_CODE.text, this.codeCombo.STORE_TYPE_CODE.code);

		_store.doAction('search');
	}

	/**
	 * grid action
	 */
	_store.doAction = function(sAction){

		switch (sAction) {
		case 'search':

			var pageCount = $("#pageCount").val();
			var param = { url : '/display/store/list'
						, sheet : 'list'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.searchForm)
			};
			DataSearchPaging(param);
			break;
		}
	}

	/**
	 * 이벤트
	 */
	_store.event = function(){

		var isMultiple = abc.param.getParams().isMultiple;

		// 전체 선택 체크박스 제어
		list_OnBeforeCheckAll = function(row, col) {
			if(isMultiple != 'true') {
				return false;
			}
		}

		// 체크박스 선택 제어
		list_OnBeforeCheck = function(row, col) {
			if(isMultiple != 'true' && list.ColSaveName(col) == 'checkbox') {
				// 체크박스 모두 해제
				var checkedRows = list.FindCheckedRow("checkbox").split('|');
				for(var i in checkedRows) {
					var currentRow = checkedRows[i];
					list.SetRowData(currentRow, { checkbox : 0 });
				}
			}
		}

		// 검색
		$('#searchBtn').on('click', function() {
			_store.doAction('search');
		});

		$("#searchWord").off('keypress').on('keypress',function(e){
			if(e.keyCode == 13){
				_store.doAction('search');
				e.preventDefault();
			}
		});

		// 목록개수
		$('#pageCount').on('change', function() {
			_store.doAction('search');
		});

		// 초기화
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm', false);
		});

		// 추가
		$("#storeSelectBtn").on('click', function() {

			var result = [];
			var storeList = list.ExportData({ Type : "json" }).data;

			if(storeList.length > 0) {
				for(var i=0 ; i<storeList.length ; i++) {
					if(storeList[i].checkbox == 1) {
						result.push(storeList[i]);
					}
				}
			} else {
				alert("추가할 매장을 선택해주세요.");
				return;
			}

			// callback 실행
			var callback = _store.parameter.getCallback();
			if(typeof callback === "function") {
				if(result.length > 0) {
					alert(result.length + '개의 매장이 대상에 추가되었습니다.');
					callback(result);
					self.close();
				} else {
					alert("선택된 데이터가 없습니다.");
				}
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});
	}

	_store.parameter = {};

	/**
	 * 콜백함수 반환
	 */
	_store.parameter.getCallback = function() {
		var callback = _store.parameter.getParameter("callback");
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
	_store.parameter.getParameter = function(findParamName) {
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

	$(function() {
		_store.init();
	});

})();