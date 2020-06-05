(function() {

	var _info = abc.object.createNestedObject(window,"abc.biz.product.info");
	
	/**
	 * 초기화
	 */
	_info.init = function(){
		

		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		this.initInfoSheet();
		this.event();
	}
	
	/**
	 *  IBSheet 초기화(상품아이콘 목록)
	 */
	_info.initInfoSheet = function() {
		createIBSheet2(document.getElementById("infoSheet"), "list", "100%", "370px");
		var pageCount = $('#pageCount').val();
		
		var initInfoSheet = {};
		initInfoSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initInfoSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initInfoSheet.Cols = [
				{Header:"노출 우선순위",		Type:"Int",		SaveName:"sortSeq",			Width:20,	Align:"Center",	Edit:1, KeyField:true, Sort:0}
			  , {Header:"상태",			Type:"Status",	SaveName:"status",			Width:100,	Align:"Center",	Hidden:1,	Edit:1}
			  , {Header:"품목명",			Type:"Combo",	SaveName:"itemCode",		Width:50,	Align:"Center",	Edit:0, Sort:0}
			  , {Header:"항목명",			Type:"Text",	SaveName:"infoNotcName",	Width:80,	Align:"Left",	Edit:0, FontUnderline:1, Cursor:"Pointer"}
			  , {Header:"사용여부",			Type:"Combo",	SaveName:"useYn",			Width:40,	Align:"Center",	Edit:0, ComboText:"사용|사용안함",	ComboCode : "Y|N", Sort:0}
			  , {Header:"필수여부",			Type:"Combo",	SaveName:"reqYn",			Width:40,	Align:"Center",	Edit:0, ComboText:"예|아니오",		ComboCode : "Y|N", Sort:0}
			  , {Header:"최종수정자",		Type:"Text",	SaveName:"moderInfo",		Width:30,	Align:"Center",	Edit:0, Sort:0}
			  , {Header:"최종수정일시",		Type:"Date",	SaveName:"modDtm",			Width:60,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"상품정보고시순번",	Type:"Text",	SaveName:"prdtInfoNotcSeq",	Width:100,	Align:"Center",	Hidden:1,	Edit:0}
			  , {Header:"정보고시기본값",		Type:"Text",	SaveName:"infoNotcDfltValue",Width:60,	Align:"Center",	Hidden:1,	Edit:0}
			  , {Header:"등록자이름",		Type:"Text",	SaveName:"rgsterInfo",		Width:100,	Align:"Center",	Hidden:1,	Edit:1}
			  , {Header:"등록자번호",		Type:"Text",	SaveName:"rgsterNo",		Width:100,	Align:"Center",	Hidden:1,	Edit:1}
			  , {Header:"등록일시",			Type:"Date",	SaveName:"rgstDtm",			Width:100,	Align:"Center",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"수정자번호",		Type:"Text",	SaveName:"moderNo",			Width:100,	Align:"Center",	Hidden:1,	Edit:1}
		];
		
		IBS_InitSheet(list, initInfoSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		list.InitDataCombo(0, "itemCode", this.codeCombo.ITEM_CODE.text, this.codeCombo.ITEM_CODE.code);
		
		_info.doActionInfo('search');
	}	
	
	/**
	 * 메뉴 grid action
	 */
	_info.doActionInfo = function(sAction){
		switch (sAction) {
		case "search":
			var pageCount = $("#pageCount").val();
			var param = { url : "/product/info-notice/list/read"
						, sheet : "list"
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.infoForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()
			};
			DataSearchPaging(param);
			$("input[name=selectedItemCode]").val($("#itemCode").val());
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_info.event = function() {
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
						
			if(row != 0 && col == 3) {
				abc.biz.display.common.setFormParameter.setDetailMove();
				var seq = list.GetRowData(row).prdtInfoNotcSeq;
				location.href = "/product/info-notice/detail?prdtInfoNotcSeq=" + seq;
			}
		}

		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
		
		// 검색
		$("#searchBtn").on("click", function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_info.doActionInfo("search");
		});
		
		// 초기화
		$("#refreshBtn").on("click", function() {
			$('#infoForm')[0].reset();			
		});
		
		// 목록개수
		$("#pageCount").on("change", function() {
			
			_info.doActionInfo("search");
		});
		
		// 변경 적용
		$("#saveBtn").on("click", function() {
						
			var url = '/product/info-notice/save';
			var param = { 'Col' : 'status', 'CallBack' : function() {
				_info.doActionInfo("search");
			} };
			list.DoSave(url, param);
		});
		
		// 등록
		$("#registBtn").on("click", function() {
			
			var itemCode = $("input[name=selectedItemCode]").val();
			location.href = "/product/info-notice/detail?itemCode=" + itemCode;
		});
	}	
	
	$(function() {
		
		_info.init();
	});
	
})();