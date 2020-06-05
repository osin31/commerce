(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.freeGift");
	
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
		createIBSheet2(document.getElementById("freeGift-list"), "list", "100%", "429px");
		
		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };	
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"", 				Type:"CheckBox",	SaveName:"updateCheck",		Width: 3,	Align:"Center", Edit:1, Sort:0, Cursor:"Pointer", Hidden:1},
			{ Header:"번호",				Type:"Seq",			SaveName:"",				Width: 3,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer" },
			{ Header:"사은품 ID",			Type:"Text",		SaveName:"prdtNo",			Width: 6,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"사은품명",			Type:"Text",		SaveName:"prdtName",		Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer" },
			{ Header:"재고수량",			Type:"Int",			SaveName:"totalStockQty",	Width: 6,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"사용여부",			Type:"Combo",		SaveName:"useYn",			Width: 5,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ComboText : "사용|사용안함",	ComboCode : "Y|N" },
			{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",			Width: 5,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"수정자",			Type:"Text",		SaveName:"moderNo",			Width: 5,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1},
			{ Header:"수정자",			Type:"Text",		SaveName:"moderInfo",		Width: 5,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"수정일시",			Type:"Date",		SaveName:"modDtm",			Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"주문수량",			Type:"Text",		SaveName:"totalOrderQty",	Width: 10,	Align:"Center",	Edit:0, Sort:0, Hidden:1}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		list.SetColFontUnderline('prdtName', 1);
		//list.SetColFontUnderline('moderInfo', 1);
		
		_object.sheet.list = list;
		
		_object.getList();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if(list.ColSaveName(col) == 'prdtName') {
					abc.biz.display.common.setFormParameter.setDetailMove();
					location.href = "/product/freeGift/detail?prdtNo=" + list.GetRowData(row).prdtNo;
//				} else if(list.ColSaveName(col) == 'moderInfo') {
//					// 수정자 정보 새창 출력
//					abc.adminDetailPopup(list.GetRowData(row).moderNo);
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
		});
		
		// 검색 버튼 이벤트
		$("#search").click(function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_object.getList();
		});
		
		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});
		
		$('#save-for-promotion').click(function() {
			var isMultiple = abc.param.getParams().isMultiple; // 다중선택여부
			var chkCnt = list.CheckedRows("updateCheck");
			if(isMultiple == "false") {
				if (chkCnt > 1) {
					alert('1개 이상 선택할 수 없습니다.');
					return;
				}
			}
			
			var checkedRows = _object.sheet.list.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var arr = [];
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				arr.push(_object.sheet.list.GetRowData(row));
			}
			var callback = abc.param.getParams().callback;
			
			var result = null;
			
			if(callback) {
				callback = "window.opener." + callback;
				result = new Function("return " + callback)();
			}
			
			// callback 실행
			if(typeof result === "function") {
				result(arr);
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});
		
		// 사은품 ID 숫자만 입력
		$('input[name="prdtNo"]').off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
	}
	
	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		var url = "/product/freeGift";
		var serializedFormData = $("#search-form").serialize();
		
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list",
				CPage : +abc.biz.display.common.setFormParameter.getPageNum()	
			};
		DataSearchPaging(param);
	}
	
	$(function() {
		_object.init();
	});
	
})();