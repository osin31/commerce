(function() {

	var _brand = abc.object.createNestedObject(window,"abc.biz.display.aconnect.brand");
	
	/**
	 * 초기화
	 */
	_brand.init = function(){
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_brand.initBrandSheet();
		_brand.event();
		
		_brand.doAction('search');
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_brand.initBrandSheet = function() {
		
		var initBrandSheet = {};
		var pageCount = $('#pageCount').val();
		
		initBrandSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initBrandSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initBrandSheet.Cols = [
//			{Header:'', 				Type:'CheckBox',SaveName:'cheked',			Width:10,	Edit:0,	Align:'Center'}
		    {Header:'상태', 				Type:'Status',	SaveName:'status',			Width:10,	Edit:0,	Align:'Center', Hidden:1}
		  , {Header:'번호', 				Type:'Seq',		SaveName:'',				Width:10,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'영문브랜드명',			Type:'Text',	SaveName:'brandEnName',		Width:50,	Edit:0,	Align:'Center', FontUnderline:1, Cursor:'Pointer'}
		  , {Header:'국문브랜드명',			Type:'Text',	SaveName:'brandName',		Width:50,	Edit:0,	Align:'Center', FontUnderline:1, Cursor:'Pointer'}
		  , {Header:'브랜드id',			Type:'Text',	SaveName:'brandNo',			Width:30,	Edit:0,	Align:'Center', FontUnderline:1, Cursor:'Pointer', Sort:0}
		  , {Header:'베스트여부',			Type:'Text',	SaveName:'',				Width:30,	Edit:0,	Align:'Center', Sort:0, Hidden:1}
		  , {Header:'사용여부',				Type:'Combo',	SaveName:'useYn',			Width:30,	Edit:0,	Align:'Center', ComboCode : "Y|N", ComboText:"사용|사용안함", Sort:0}
		  , {Header:'A-Connect\n전시여부',	Type:'Combo',	SaveName:'aconnectDispYn',	Width:30,	Edit:1,	Align:'Center', ComboCode : "Y|N", ComboText:"전시|전시안함", Sort:0}
		  , {Header:'수정자',			Type:'Text',	SaveName:'moderInfo',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'수정일시',			Type:'Date',	SaveName:'modDtm',			Width:40,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById('brandSheet'), 'brandList', '100%', '370px');
		IBS_InitSheet(brandList, initBrandSheet);
		
		brandList.SetCountPosition(3);
		brandList.SetPagingPosition(2);
		brandList.FitColWidth();
		brandList.SetExtendLastCol(1);	
	}
	
	/**
	 * 메뉴 grid action
	 */
	_brand.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var param = { url : '/display/a-connect/brand/list'
						, sheet : 'brandList'
						, onePageRow : $("#pageCount").val()
						, subparam : FormQueryStringEnc(document.forms.searchForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_brand.event = function(){
		
		brandList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			
			if (row > 0 && (brandList.ColSaveName(col) == 'brandName' || brandList.ColSaveName(col) == 'brandEnName' || brandList.ColSaveName(col) == 'brandNo')) {
				var data = brandList.GetRowData(row);
				abc.biz.display.common.setFormParameter.setDetailMove();
				location.href = '/display/a-connect/brand/detail?brandNo=' + data.brandNo;
			}
		}
		
		brandList_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(brandList.GetCurrentPage());
			}
		}
		
		$('#searchBtn').on('click', function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_brand.doAction('search');
		});
		
		$('#saveBtn').on('click', function() {
			
			var url = '/display/a-connect/brand/modify';
			var param = { 'Col' : 'status', 'CallBack' : function() {
				_brand.doAction('search');
			} };
			
			brandList.DoSave(url, param);
		});
		
		// 검색조건 초기화
		$('#clearBtn').on('click', function() {
			$('#searchForm')[0].reset();
		});
		
		// 엔터키 검색
		$('input[name=searchWord]').keydown(function(key) {
			if (key.keyCode == 13) {
				_brand.doAction('search');
			}
		});
	}
	
	$(function() {
		_brand.init();
	});
	
})();