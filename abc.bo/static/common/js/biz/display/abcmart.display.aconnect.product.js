(function() {

	var _product = abc.object.createNestedObject(window,"abc.biz.display.aconnect.product");
	
	/**
	 * 초기화
	 */
	_product.init = function(){
		
		_product.event();
		_product.initProductSheet();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_product.initProductSheet = function() {
		
		var initProductSheet = {};
		var pageCount = $('#pageCount').val();
		
		initProductSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initProductSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initProductSheet.Cols = [
		    {Header:'상태', 				Type:'Status',	SaveName:'status',			Width:10,	Edit:0,	Align:'Center', Hidden:1}
		  , {Header:'번호', 				Type:'Seq',		SaveName:'',				Width:10,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'상품코드',				Type:'Text',	SaveName:'prdtNo',			Width:50,	Edit:0,	Align:'Center', FontUnderline:1, Cursor:'Pointer'}
		  , {Header:'상품이미지',			Type:'Image',	SaveName:'titleImageUrl',	Width:30,	Edit:0, Align:'Center', ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png", Sort:0}
		  , {Header:'상품명',				Type:'Html',	SaveName:'prdtName',		Width:50,	Edit:0,	Align:'Left', FontUnderline:1, Cursor:'Pointer'}
		  , {Header:'카테고리',				Type:'Text',	SaveName:'stdCtgrName',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'스타일코드',			Type:'Text',	SaveName:'styleInfo',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'승인상태',				Type:'Combo',	SaveName:'aprvStatCode',	Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'전시여부',				Type:'Combo',	SaveName:'dispYn',			Width:30,	Edit:0,	Align:'Center', ComboText:"전시|전시안함", ComboCode : "Y|N", Sort:0}
		  , {Header:'A-Connect\n전시여부',	Type:'Combo',	SaveName:'aconnectDispYn',	Width:30,	Edit:1,	Align:'Center', ComboText:"전시|전시안함", ComboCode : "Y|N", Sort:0}
		  , {Header:'수정자',			Type:'Text',	SaveName:'moderInfo',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'수정일시',			Type:'Date',	SaveName:'modDtm',			Width:40,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
		];
		
		createIBSheet2(document.getElementById('productSheet'), 'productList', '100%', '370px');		
		
		IBS_InitSheet(productList, initProductSheet);
		
		productList.SetCountPosition(3);
		productList.SetPagingPosition(2);
		productList.FitColWidth();
		//productList.SetExtendLastCol(1);
		
		productList.InitDataCombo(0, "aprvStatCode", this.codeCombo.APRV_STAT_CODE.text, this.codeCombo.APRV_STAT_CODE.code);
	}
	
	/**
	 * 메뉴 grid action
	 */
	_product.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var param = { url : '/display/a-connect/product/list'
						, sheet : 'productList'
						, onePageRow : $("#pageCount").val()
						, subparam : FormQueryStringEnc(document.forms.searchForm)
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_product.event = function(){
		
		productList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			
			if (row != 0 && (productList.ColSaveName(col) == 'prdtName' || productList.ColSaveName(col) == 'prdtNo')) {
				
				var data = productList.GetRowData(row);
				
				var pop = abc.open.popup({
					url 	:	'/display/a-connect/product/detail',
					winname :	'a-connect-product-detail',
					method	: 	'get',
					title 	:	'a-connect-product-detail',
					width 	:	855,
					height	:	945,
					params	:	{ 'prdtNo' : data.prdtNo }
				});
			}
		}
		
		$('#searchBtn').on('click', function() {
			
			_product.doAction('search');
		});
		
		$('#pageCount').on('change', function() {
			
			_product.doAction('search');
		});
		
		$('#saveBtn').on('click', function() {
			
			var url = '/display/a-connect/product/modify';
			var param = { 'Col' : 'status', 'CallBack' : function() {
				_product.doAction('search');
			} };
			
			productList.DoSave(url, param);
		});
		
		$('.clear-form').on('click', function() {
			
			$('#searchForm')[0].reset();
		});
		
		// 엔터키 검색
		$('input[name=searchItemKeyword]').keydown(function(key) {
			if (key.keyCode == 13) {
				_product.doAction('search');
			}
		});
	}
	
	/** callback */
	_product.callback = function(data) {
		
		_product.doAction('search');
	}
	
	$(function() {
		_product.init();
		_product.doAction('search');
	});
	
})();