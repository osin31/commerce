(function() {
	var _eventProduct = abc.object.createNestedObject(window,"abc.biz.promotion.event.product");
	var _eventAlert = '';
	
	/**
	 * 초기화
	 */
	_eventProduct.init = function(){
		console.log('init');
		//_eventProduct.sheet.init();
		_eventProduct.event();
		_eventProduct.sheet.event();
		//if($('#eventNo').val() != '' && $('[name=eventTypeCode]').val() == '10003') abc.biz.promotion.event.product.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventProduct.sheet = {};
	_eventProduct.sheet.init = function(){
		createIBSheet2(document.getElementById("productSheet"), "productList", "100%", "370px");
		var productSheet = {};
		
		productSheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		productSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		
		productSheet.Cols = [
			{Header : "", Type:"Status", SaveName:"status", Width: 10, Align:"Center", Edit:1, Hidden:1}
			/*, {Header : "", Type:"CheckBox", SaveName:"checkProduct", Width: 5, Align:"Center", 	Edit:1}*/
			, {Header:"노출순서", Type:"Int", SaveName:"sortSeq", Width: 10, Align:"Center", Edit:1, DefaultValue:1, Hidden:1}
			, {Header:"상품코드", Type:"Text", SaveName:"prdtNo", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight: 60}
			, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"자사(입점사)", Type:"Text", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0,ComboText : "자사|입점",	ComboCode : "Y|N"}
			, {Header:"전시채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
			, {Header:"카테고리", Type:"Text", SaveName:"stdCtgrName", Width: 10, Align:"Center", Edit:0}
			, { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
			/*, {Header:"쿠폰유무", Type:"Text", SaveName:"", Width: 10, Align:"Center", Edit:0}*/
			, {Header:"정상가", Type:"Text", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"판매가", Type:"Text", SaveName:"sellAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"할인율(%)", Type:"Text", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
		];
		
		IBS_InitSheet(productList , productSheet);
		productList.FitColWidth();
		productList.SetExtendLastCol(1);
		productList.InitDataCombo(0 , "sellStatCode" , codeCombo.SELL_STAT_CODE.text , codeCombo.SELL_STAT_CODE.code  );
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventProduct.sheet.event = function(){
		productList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( productList.ColSaveName(col) == "prdtName" && value != "" ) {
					abc.productFrontUrl(productList.GetRowData(row).prdtNo, true);

				}
				
				if ( productList.ColSaveName(col) == "prdtNo" && value != "" ) {
					abc.readonlyProductDetailPopup(productList.GetRowData(row));
				}
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_eventProduct.event = function(){
		$('.productSearchPop').on('click', function(e) {
			var drawJoinCheckCnt =$('[name=drawJoinCheckCnt]').val();
			if(drawJoinCheckCnt > 0) {
				alert('응모자가 있는 경우 상품을 변경할 수 없습니다.');
				return false;
			}
			
			var _params = {
				"sellStatCodes" : "10000"
			}
			
			_eventAlert = abc.productSearchPopup(false, 'abc.biz.promotion.event.product.appendProduct', '', _params);
		});
		
		$('#productDelBtn').on('click', function(e) {
			var drawJoinCheckCnt =$('[name=drawJoinCheckCnt]').val();
			if(drawJoinCheckCnt > 0) {
				alert('응모자가 있는 경우 상품을 변경할 수 없습니다.');
				return false;
			}
			
			productList.RemoveAll();
			
			/*var seq = '';
			seq = productList.FindCheckedRow('checkProduct');
			if(seq == '') {
				alert('삭제할 상품을 선택해주세요.');
				return;
			} else {
				if(confirm('삭제하시겠습니까?')) {
					productList.RowDelete(seq);
				}
			}*/
		});
	}
	
	_eventProduct.duplCheck = function(prdtNo) {
		var prdtList = productList.ExportData({"Type":"json"}).data;
		var duplCheck = true;
		
		$.each(prdtList, function(i, v) {
			if(v.prdtNo == prdtNo) {
				duplCheck = false;
				return false;
			}
		});
		
		return duplCheck;
	}
	
	_eventProduct.confirmCheck = function(dataLength) {
		if(dataLength == 0) return true;
		else {
			if(_eventAlert.confirm('상품을 변경하시겠습니까?')){
				return true;
			} else {
				return false;
			}
		}
	}
	
	_eventProduct.appendProduct = function(d) {
		var prdtList = productList.ExportData({"Type":"json"}).data;
		var confirmCheck = _eventProduct.confirmCheck(prdtList.length);
		if(confirmCheck){
			productList.RemoveAll();
			$.each(d, function(i, v) {
				var duplCheck = _eventProduct.duplCheck(d.prdtNo);
				if(v.sellStatCode != '10000') {
					_eventAlert.alert('판매 대기중인 상품만 등록이 가능합니다.');
					return false;
				}
				
				if(duplCheck) {
					var sheet = eval('productList');	// 시트 객체 획득
					var insertRowNumber = "";
					var initialRowData = {};	// 추가될 행 데이터
					var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
					insertRowNumber = sheet.RowCount() + 1
					initialRowData = {
						"prdtNo"		: v.prdtNo
						,"titleImageUrl" : v.titleImageUrl
						,"prdtName"	: v.prdtName
						,"mmnyPrdtYn"	: v.mmnyPrdtYn == 'Y' ? '자사' : '입점사'
						,"chnnlName"	: v.chnnlName
						,"stdCtgrName"	: v.stdCtgrName
						,"sellStatCode"	: v.sellStatCode
						,"availabilityRate"	: v.orderPsbltQty
						,"normalAmt"	: v.normalAmt
						,"sellAmt"	: v.sellAmt
						,"onlnDscntRate"	: v.onlnDscntRate + '%'
					};
					sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
				}
			});
		}
	}
	
	_eventProduct.appendProductByGetList = function(d) {
		$.each(d, function(i, v) {
			var sheet = eval('productList');	// 시트 객체 획득
			var insertRowNumber = "";
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
			insertRowNumber = sheet.RowCount() + 1
			initialRowData = {
				"prdtNo"		: v.prdtNo
				,"titleImageUrl" : v.titleImageUrl
				,"prdtName"	: v.prdtName
				,"mmnyPrdtYn"	: v.mmnyPrdtYn
				,"chnnlName"	: v.chnnlName
				,"stdCtgrName"	: v.stdrCtgrName
				,"sellStatCode"	: v.sellStatCode
				,"orderPsbltQty"	: v.orderPsbltQty
				,"availabilityRate"	: v.orderPsbltQty
				,"normalAmt"	: abc.text.isMakeComma(v.normalAmt) + '원'
				,"sellAmt"	: abc.text.isMakeComma(v.sellAmt) + '원'
				,"onlnDscntRate"	: v.onlnDscntRate + '%'
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_eventProduct.getList = function(){
		var form = $.form(document.forms.frm);
		form.submit({
			'type': 'POST',
			'url': '/promotion/event/product/list/read',
			'success': function(d) {
				_eventProduct.appendProductByGetList(d.Data);
			},
			'error': function(e){
				alert(e.message);
				console.log(e);
			}
		});
	}
	
	_eventProduct.drawDuplCheckCount = function(prdtNo, eventNo) {
		var drawDuplCheckCount = false;
		$.ajax({
            type :'get',
            data : {prdtNo : prdtNo, eventNo : eventNo},
            async : false,
            url:'/promotion/event/draw/duplCheck'
        }).done(function(d){
        	drawDuplCheckCount = d;
        }).fail(function(e){
        	alert(e.message);
	    	console.log(e);
        });
		
		return drawDuplCheckCount;
	}
	
	$(function() {
		_eventProduct.init();
	});
	
})();