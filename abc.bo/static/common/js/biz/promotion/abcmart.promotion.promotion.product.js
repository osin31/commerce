(function() {
	var _promotionProduct = abc.object.createNestedObject(window,"abc.biz.promotion.promotion.product");	
	var _promotionAlert = '';
	
	/**
	 * 초기화
	 */
	_promotionProduct.init = function(){
		console.log('init');
		//_promotionProduct.sheet.init();
		_promotionProduct.event();
		_promotionProduct.sheet.event();
		if($('#promoNo').val() != "") {
			_promotionProduct.getList();
		}
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_promotionProduct.sheet = {};
	_promotionProduct.sheet.init = function(){
		createIBSheet2(document.getElementById("product1Sheet"), "product1list", "100%", "370px");
		
		var product1Sheet = {};

		product1Sheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		product1Sheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		
		product1Sheet.Cols = [
			{Header : "", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header : "", Type:"Text", SaveName:"prdtType", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header : "", Type:"CheckBox", SaveName:"checkProduct", Width: 10, Align:"Center", 	Edit:1}
			, {Header:"상품코드", Type:"Text", SaveName:"prdtNo", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"업체상품코드", Type:"Text", SaveName:"vndrPrdtNoText", Width: 10, Align:"Center", Edit:0}
			, {Header:"업체코드", Type:"Text", SaveName:"vndrNo", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight: 60}
			, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 40, Align:"Left", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"자사(입점사)", Type:"Combo", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0,ComboText : "자사|입점",	ComboCode : "Y|N"}
			, {Header:"전시채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
			, {Header:"카테고리",	Type:"Text", SaveName:"stdCtgrName", Width: 10, Align:"Center", Edit:0}
			, { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
			
			, { Header:"온라인재고",		Type:"Int",			SaveName:"stockAiQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"스마트재고",		Type:"Int",			SaveName:"stockAwQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"매장재고",		Type:"Int",			SaveName:"stockAsQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"입점재고",		Type:"Int",			SaveName:"stockVdQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			
			, {Header:"주문가능수량", Type:"Text", SaveName:"avaiableStockQty", Width: 10, Align:"Center", Edit:0}
			, {Header:"가용률", Type:"Text", SaveName:"availabilityRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"정상가", Type:"Text", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"판매가", Type:"Text", SaveName:"displayProductPrice", Width: 10, Align:"Center", Edit:0}
			, {Header:"할인율", Type:"Int", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"잔여행사수량", Type:"Int", SaveName:"eventLimitQty", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"행사수량", Type:"Int", SaveName:"maxEventLimitQty", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header:"중복 프로모션", Type:"Text", SaveName:"duplPromoNo", Width: 10, Align:"Center", Edit:0}
			, {Header:"기본 수수료율", Type:"Int", SaveName:"dfltCmsnRate", Width: 10, Align:"Center", Edit:0}	 
			, {Header:"프로모션 수수료율(%)", Type:"Int", SaveName:"cmsnRate", Width: 10, Align:"Center", Edit:1}
			, {Header:"상품삭제체크", Type:"Text", SaveName:"productDelCheck", Width: 10, Align:"Center", Edit:1, Hidden:1}
		];
		
		IBS_InitSheet(product1list , product1Sheet);
		/*product1list.SetCountPosition(3);*/
		/*product1list.SetPagingPosition(2);*/
		product1list.FitColWidth();
		product1list.SetExtendLastCol(1);
		
		var productCodeCombo = abc.biz.promotion.promotion.product.codeCombo;
		
		product1list.InitDataCombo(0 , "sellStatCode" , productCodeCombo.SELL_STAT_CODE.text , productCodeCombo.SELL_STAT_CODE.code  );
		
		/*createIBSheet2(document.getElementById("product2Sheet"), "product2list", "100%", "370px");
		
		var product2Sheet = {};
		
		product2Sheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		product2Sheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		product2Sheet.Cols = [
			{Header : "", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Edit:1, Sort:0, Hidden:1}
			, {Header : "", Type:"Text", SaveName:"prdtType", Width: 10, Align:"Center", Edit:1, Sort:0, Hidden:1}
			, {Header : "", Type:"CheckBox", SaveName:"checkProduct", Width: 10, Align:"Center", 	Edit:1, Sort:0}
			, {Header:"온라인상품코드", Type:"Text", SaveName:"prdtNo", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0}
			, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"자사(입점사)", Type:"Combo", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0,ComboText : "자사|입점",	ComboCode : "Y|N"}
			, {Header:"전시채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
			, {Header:"카테고리", Type:"Text", SaveName:"stdCtgrName", Width: 10, Align:"Center", Edit:0}
			, { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",	Width: 10,	Align:"Center" }
			, {Header:"주문가능수량", Type:"Text", SaveName:"avaiableStockQty", Width: 10, Align:"Center", Edit:0}
			, {Header:"옵션가용률", Type:"Text", SaveName:"availabilityRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"정상가", Type:"Int", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"판매가", Type:"Int", SaveName:"sellAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"할인율", Type:"Int", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
		];
		
		IBS_InitSheet(product2list , product2Sheet);
		product2list.SetCountPosition(3);
		product2list.SetPagingPosition(2);
		product2list.FitColWidth();
		product2list.SetExtendLastCol(1);
		
		product2list.InitDataCombo(0 , "sellStatCode" , productCodeCombo.SELL_STAT_CODE.text , productCodeCombo.SELL_STAT_CODE.code  );*/
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_promotionProduct.sheet.event = function(){
		product1list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( product1list.ColSaveName(col) == "prdtName" && value != "" ) {
					abc.productFrontUrl(product1list.GetRowData(row).prdtNo, true);

				}
				
				if ( product1list.ColSaveName(col) == "prdtNo" && value != "" ) {
					abc.readonlyProductDetailPopup(product1list.GetRowData(row));
				}
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_promotionProduct.event = function(){
		$('.productSearchPop').on('click', function(e) {
			var listType = $(this).data('type');
			var callback = '';
			if(listType == 'product1list') callback = 'abc.biz.promotion.promotion.product.appendProduct1';
			//else callback = 'abc.biz.promotion.promotion.product.appendProduct2';
			var promoNo = $('[name=promoNo]').val();
			
			var params = {
				'promoType' : 'promoType'
				,'searchPromoInfo' : promoNo == '' ? '0' : promoNo
			}
			
			_promotionAlert = abc.productSearchPopup(true, callback, false, params);
		});
		
		$('.productDelBtn').on('click', function(e) {
			var seq = '';
			seq = product1list.FindCheckedRow('checkProduct');
			var seqArr = seq.split('|');
			/*var delSeqArr = new Array();*/
			
			/*$.each(seqArr, function(i, v){
				if(product1list.GetRowData(v).productDelCheck == 'Y') {
					delSeqArr.push(v);
				}
			});
			
			if(seq == '') {
				alert('삭제할 상품을 선택해주세요.');
				return false;
			}
			
			if(seqArr.length != delSeqArr.length) {
				alert('진행중 또는 종료된 프로모션 상품은 삭제할 수 없습니다.');
				return false;
			}*/
			
			if(confirm('삭제하시겠습니까?')) {
				/*var delSeq = '';
				$.each(delSeqArr, function(i, v){
					if (i != delSeqArr.length - 1) delSeq += (v + '|');
					else delSeq += v;
				});
				product1list.RowDelete(delSeq);*/
				//2019.10.11 위 주석들은 진행중인 프로모션 상품은 지울 수 없는 validation임 (진행중인 프로모션 관계없이 지워달라고 요청이 와서 주석처리함)
				product1list.RowDelete(seq);
			}
		});
		
		$('#cmsnRateBtn').on('click', function(e) {
			var cmsnRate = $('#cmsnRateVal').val();
			var list = '';
			
			if(cmsnRate == '') {
				alert('수수료율을 입력해주세요.');
				$('#cmsnRateVal').focus();
				return ;
			}
			
			if(product1list.FindCheckedRow('checkProduct') == '') {
				alert('적용할 상품을 선택해주세요.');
				return ;
			}
			
			if(!$.isNumeric(cmsnRate)) {
				alert('수수료율은 숫자만 입력이 가능합니다.');
				return ;
			}
			
			list = product1list.ExportData({"Type":"json"}).data;
			
			for(var i = 1; i < list.length + 1; i++ ) {
				if(list[i - 1].checkProduct == 1) product1list.SetCellValue(i, 'cmsnRate', cmsnRate);
			}
		});
		
		$('#eventLimitQtyBtn').on('click', function(e) {
			var eventLimitQty = $('#eventLimitQtyVal').val();
			var list = '';
			
			if(eventLimitQty == '') {
				alert('행사수량을 입력해주세요.');
				$('#eventLimitQtyVal').focus();
				return ;
			}
			
			if(product1list.FindCheckedRow('checkProduct') == '') {
				alert('적용할 상품을 선택해주세요.');
				return ;
			}
			
			if(!$.isNumeric(eventLimitQty)) {
				alert('행사수량은 숫자만 입력이 가능합니다.');
				return ;
			}
			
			list = product1list.ExportData({"Type":"json"}).data;
			
			for(var i = 1; i < list.length + 1; i++ ) {
				if(list[i - 1].checkProduct == 1) product1list.SetCellValue(i, 'maxEventLimitQty', eventLimitQty);
			}
		});
	}
	
	_promotionProduct.changeColumn = function(promoTypeCode) {
		 if(promoTypeCode == '10004') {		//타임특가
			product1list.SetColHidden(product1list.SaveNameCol('eventLimitQty'), 0);
			product1list.SetColHidden(product1list.SaveNameCol('maxEventLimitQty'), 0);
			/*product1list.SetColHidden(product1list.SaveNameCol('rate'), 1);
			product1list.SetColHidden(product1list.SaveNameCol('cmsnRate'), 1);*/
		} else {
			product1list.SetColHidden(product1list.SaveNameCol('eventLimitQty'), 1);
			product1list.SetColHidden(product1list.SaveNameCol('maxEventLimitQty'), 1);
			/*product1list.SetColHidden(product1list.SaveNameCol('rate'), 0);
			product1list.SetColHidden(product1list.SaveNameCol('cmsnRate'), 0);*/
		}
		 
		 if(promoTypeCode == '10005') {
			 product1list.SetColHidden(product1list.SaveNameCol('cmsnRate'), 1); 
		 } else {
			 product1list.SetColHidden(product1list.SaveNameCol('cmsnRate'), 0);
		 }
	}
	
	_promotionProduct.appendProduct1 = function(d) {
		var sellStatCodeCheckCount = 0;
		$.each(d, function(i, v) {
			if(v.sellStatCode != '10001') {
				sellStatCodeCheckCount += 1;	
			} else {
				_promotionProduct.appendProductSheet(v, 'product1list');
			}
		});
		
		if(d != null) $('#productTitleArea').text('대상 상품 목록(' + d.length + ')');
		if(sellStatCodeCheckCount != 0) {
			_promotionAlert.alert('판매중인 상품만 등록됩니다.(' + sellStatCodeCheckCount + '건 제외)');
		}
	}
	
	_promotionProduct.appendProduct2 = function(d) {
		$.each(d, function(i, v) {
			_promotionProduct.appendProductSheet(v, 'product2list');
		});
	}
	
	_promotionProduct.duplCheck = function(prdtNo, sheetName) {
		var list = '';
		var duplCheck = true;
		if(sheetName == 'product1list') {
			list = product1list.ExportData({"Type":"json"}).data;
		} 
		/*else {
			list = product2list.ExportData({"Type":"json"}).data;
		}*/
		
		$.each(list, function(i, v) {
			if(v.prdtNo == prdtNo) {
				duplCheck = false;
				return false;
			}
		});
		
		return duplCheck;
	}
	
	_promotionProduct.vendorMultiBuyCheck = function() {
		var list = product1list.ExportData({"Type":"json"}).data;
		var compareList = product1list.ExportData({"Type":"json"}).data;
		var vendorMultiBuyCheck = false;
		
		$.each(list, function(i, v) {
			$.each(compareList, function(j, x) {
				if(v.mmnyPrdtYn != x.mmnyPrdtYn) {
					vendorMultiBuyCheck = true;
					return false;
				}
				
				if(v.mmnyPrdtYn == 'N' && v.vndrNo != x.vndrNo) {
					vendorMultiBuyCheck = true;
					return false;
				} 
			});
			
			if(vendorMultiBuyCheck) return false;
		});
		
		return vendorMultiBuyCheck;
	}
	
	/*_promotionProduct.duplPromoProduct = function(prdtNo) {
		var duplPromoNo = '';
		$.ajax({
            type :'post',
            data : {
            	prdtNo : prdtNo
            	,promoNo : $('[name=promoNo]').val()
            },
            url:'/promotion/promotion/duplProduct',
            async: false
        }).done(function(d){
        	if(d.duplPromoNo != null) duplPromoNo = d.duplPromoNo;
        }).fail(function(e){
	    	console.log(e);
        });
		
		return duplPromoNo;
	}*/
	
	_promotionProduct.appendProductSheet = function(d, sheetName) {
		var duplCheck = _promotionProduct.duplCheck(d.prdtNo,  sheetName);
		//var duplPromoNo = _promotionProduct.duplPromoProduct(d.prdtNo); //임시로 ajax 로 추후 상품 쪽 쿼리 요청
		
		if(duplCheck) {
			var sheet = eval(sheetName);	// 시트 객체 획득
			var insertRowNumber = "";
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
			var prdtType = 'T';
			//if(sheetName == 'product2list') prdtType = 'L'; 
			insertRowNumber = sheet.RowCount() + 1
			initialRowData = {
					"seq" : insertRowNumber
					,"prdtType" : prdtType
					,"prdtNo"		: d.prdtNo
					,"vndrPrdtNoText"		: d.vndrPrdtNoText
					,"titleImageUrl" : d.titleImageUrl
					,"prdtName"	: d.prdtName
					,"mmnyPrdtYn"	: d.mmnyPrdtYn
					,"chnnlName"	: d.chnnlName
					,"stdCtgrName"	: d.stdCtgrName
					,"sellStatCode"	: d.sellStatCode
					
					,"stockAiQty"	: d.stockAiQty
					,"stockAwQty"	: d.stockAwQty
					,"stockAsQty"	: d.stockAsQty
					,"stockVdQty"	: d.stockVdQty
					
					,"avaiableStockQty"	: d.avaiableStockQty
//					,"availabilityRate"	: d.availabilityRate
					,"availabilityRate"	: Math.ceil(d.useYnQty / d.totalOptionCount * 100)
					,"normalAmt"	: abc.text.isMakeComma(d.normalAmt)
					,"displayProductPrice"	: abc.text.isMakeComma(d.displayProductPrice)
					,"onlnDscntRate"	: d.onlnDscntRate
					,"maxEventLimitQty"	: d.maxEventLimitQty
					,"cmsnRate"	: 0
					,"duplPromoNo" : d.duplPromoNo
					,"dfltCmsnRate" : d.dfltCmsnRate
					,"vndrNo" : d.vndrNo
					,"productDelCheck" : 'Y'
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
			if(d.duplPromoNo != '') sheet.SetRowFontColor(insertRowNumber, "#FF0000");
		}
	}
	
	_promotionProduct.appendProductSheetByGetList = function(d, sheetName) {
		console.log(d);
		var duplCheck = _promotionProduct.duplCheck(d.prdtNo,  sheetName);
		if(duplCheck) {
			var sheet = eval(sheetName);	// 시트 객체 획득
			var insertRowNumber = "";
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
			var prdtType = 'T';
			var promoProgressStatus = $('[name=promoProgressStatus]').val();
			
			//if(sheetName == 'product2list') prdtType = 'L'; 
			insertRowNumber = sheet.RowCount() + 1
			initialRowData = {
				"seq" : insertRowNumber
				,"prdtType" : prdtType
				,"prdtNo"		: d.prdtNo
				,"vndrPrdtNoText"		: d.vndrPrdtNoText
				,"titleImageUrl" : d.titleImageUrl
				,"prdtName"	: d.prdtName
				,"mmnyPrdtYn"	: d.mmnyPrdtYn
				,"chnnlName"	: d.chnnlName
				,"stdCtgrName"	: d.stdrCtgrName
				,"sellStatCode"	: d.sellStatCode
				
				,"stockAiQty"	: d.stockAiQty
				,"stockAwQty"	: d.stockAwQty
				,"stockAsQty"	: d.stockAsQty
				,"stockVdQty"	: d.stockVdQty
				
				,"avaiableStockQty"	: d.avaiableStockQty
				,"availabilityRate"	: Math.ceil(d.useYnQty / d.totalOptionCount * 100)
				,"normalAmt"	: abc.text.isMakeComma(d.normalAmt) + '원'
				,"displayProductPrice"	: abc.text.isMakeComma(d.displayProductPrice) + '원'
				,"onlnDscntRate"	: d.onlnDscntRate
				,"eventLimitQty"	: d.eventLimitQty
				,"maxEventLimitQty"	: d.maxEventLimitQty
				,"cmsnRate"	: d.cmsnRate
				,"duplPromoNo" : d.duplPromoNo
				,"dfltCmsnRate" : d.dfltCmsnRate
				,"vndrNo" : d.vndrNo
				,"productDelCheck" : promoProgressStatus == 'none' || promoProgressStatus == 'wait' ? 'Y' : 'N' 
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
			if(d.duplPromoNo != '' && d.duplPromoNo != undefined) sheet.SetRowFontColor(insertRowNumber, "#FF0000");
		}
	}
	
	/**
	 * 리스트 조회 (상품 서비스 요청 후 수정)
	 */
	_promotionProduct.getList = function(){
		var form = $.form(document.forms.frm);
		form.submit({
			'type': 'POST',
			'url': '/product/product/mapping/promotion/target',
			'success': function(d) {
				if(d.Data != null && d.Data.length > 0){
					$.each(d.Data, function(i, v) {
						if(v.prdtType == 'T')  _promotionProduct.appendProductSheetByGetList(v, 'product1list');
						//else _promotionProduct.appendProductSheetByGetList(v, 'product2list');
					});
					$('#productTitleArea').text('대상 상품 목록(' + d.Data.length + ')');
				}
			},
			'error': function(e){
				alert(e.message);
				console.log(e);
			}
		});
	}
	
	$(function() {
		_promotionProduct.init();
	});
	
})();