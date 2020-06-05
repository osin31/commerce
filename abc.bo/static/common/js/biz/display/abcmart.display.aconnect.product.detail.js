(function() {

	var _productDetail = abc.object.createNestedObject(window,"abc.biz.display.aconnect.product.detail");
	
	/**
	 * 초기화
	 */
	_productDetail.init = function(){
		
		_productDetail.initProductSheet();
		_productDetail.event();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_productDetail.initProductSheet = function() {	
		
		var initProductSheet = {};
		var pageCount = $('#pageCount').val();
		
		initProductSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initProductSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initProductSheet.Cols = [
//			{Header:'', 		Type:'CheckBox',SaveName:'cheked',				Width:10,	Edit:0,	Align:'Center'}
		    {Header:'번호', 		Type:'Seq',		SaveName:'seq',					Width:10,	Edit:0,	Align:'Center'}
		  , {Header:'변경구분',		Type:'Text',	SaveName:'',					Width:15,	Edit:0}
		  , {Header:'구분',		Type:'Text',	SaveName:'chngFieldName',		Width:15,	Edit:0}
		  , {Header:'내용',		Type:'Text',	SaveName:'chngFieldValue',		Width:40,	Edit:0}
		  , {Header:'변경전필드값',	Type:'Text',	SaveName:'chngBeforeFieldValue',Width:40,	Edit:0, Hidden:1}
		  , {Header:'변경후필드값',	Type:'Text',	SaveName:'chngAfterFieldValue',	Width:40,	Edit:0, Hidden:1}
		  , {Header:'최종 수정자',	Type:'Text',	SaveName:'rgsterInfo',			Width:20,	Edit:0,	Align:'Center'}
		  , {Header:'최종 수정일시',	Type:'Date',	SaveName:'rgstDtm',				Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById('historySheet'), 'productList', '100%', '370px');
		IBS_InitSheet(productList, initProductSheet);
		
		productList.SetCountPosition(3);
		productList.SetPagingPosition(2);
		productList.FitColWidth();
		productList.SetExtendLastCol(1);
		
		_productDetail.doAction('search');
	}
	
	/**
	 * 메뉴 grid action
	 */
	_productDetail.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var param = { url : '/product/product/history/product'
						, sheet : 'productList'
						, onePageRow : $('#pageCount').val()
						, subparam : FormQueryStringEnc(document.forms.historyForm)
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_productDetail.event = function(){
		
		productList_OnRowSearchEnd = function(row) {
			
			var data = productList.GetRowData(row);
			
			var str = data.chngBeforeFieldValue + ' > ' + data.chngAfterFieldValue
			
			productList.SetRowData(row, {
				chngFieldValue : str
			});
		}
		
		$('#saveBtn').on('click', function() {
			
			var url = '/display/a-connect/product/modify';
			var form = $.form(document.forms.historyForm);
			
			form.submit({
				url : url,
				method : "POST",
				valid	: function($f){
					return true;
				},
				success : function() {
					
					alert('저장되었습니다.');
					
					// callback
					var cb = 'abc.biz.display.aconnect.product.callback';
					var opFunc = new Function('return opener.'+cb)();
					opFunc();
					window.close();
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});
		});
		
		$('#pageCount').on('change', function() {
			
			_productDetail.doAction('search');
		});
		
	}
	
	$(function() {
		_productDetail.init();
	});
	
})();