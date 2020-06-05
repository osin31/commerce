(function() {

	var _couponProduct = abc.object.createNestedObject(window,"abc.biz.promotion.coupon.product");

	/**
	 * 초기화
	 */
	_couponProduct.init = function(){
		console.log('init');
		_couponProduct.sheet.init();
		_couponProduct.event();
		_couponProduct.sheet.event();
		if($('#cpnNo').val() != ""){
			_couponProduct.getPrdtList();
		}
	}

	/**
	 *  IBSheet 초기화
	 */
	_couponProduct.sheet = {};
	_couponProduct.sheet.init = function(){
		var productCodeCombo = abc.biz.promotion.coupon.product.codeCombo;

		createIBSheet2(document.getElementById("product1Sheet"), "product1list", "100%", "370px");

		var product1Sheet = {};
		var pageCount = $('#pageCountTagetProduct').val();

		product1Sheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		product1Sheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};

		product1Sheet.Cols = [
			{Header : "", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header : "", Type:"Text", SaveName:"prdtType", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header : "", Type:"CheckBox", SaveName:"checkProduct", Width: 10, Align:"Center", 	Edit:1}
			, {Header : "", Type:"Text", SaveName:"vndrNo", Width: 10, Align:"Center", 	Edit:1, Hidden:1}
			, {Header:"온라인상품코드", Type:"Text", SaveName:"prdtNo", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight: 60	}
			, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 25, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer", Align:"Left"}
			, {Header:"자사(입점사)", Type:"Combo", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0,ComboText : "자사|입점",	ComboCode : "Y|N"}
			, {Header:"전시채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
			, {Header:"카테고리", Type:"Text", SaveName:"stdCtgrName", Width: 10, Align:"Center", Edit:0}
			, { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }

			, { Header:"온라인재고",		Type:"Int",			SaveName:"stockAiQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"스마트재고",		Type:"Int",			SaveName:"stockAwQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"매장재고",		Type:"Int",			SaveName:"stockAsQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"입점재고",		Type:"Int",			SaveName:"stockVdQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }

			, {Header:"주문가능수량", Type:"Text", SaveName:"orderPsbltQty", Width: 10, Align:"Center", Edit:0}
			, {Header:"옵션가용률", Type:"Text", SaveName:"availabilityRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"정상가", Type:"Text", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"판매가", Type:"Text", SaveName:"sellAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"할인율", Type:"Int", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"useYnQty", Type:"Int", SaveName:"useYnQty", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"totalOptionCount", Type:"Int", SaveName:"totalOptionCount", Width: 10, Align:"Center", Edit:0, Hidden:1}

		];

		IBS_InitSheet(product1list , product1Sheet);
		product1list.FitColWidth();
		product1list.SetExtendLastCol(1);
		// Grid 건수 정보 표시
		product1list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		product1list.SetPagingPosition(2);
		product1list.InitDataCombo(0 , "sellStatCode" , productCodeCombo.SELL_STAT_CODE.text , productCodeCombo.SELL_STAT_CODE.code  );

		createIBSheet2(document.getElementById("product2Sheet"), "product2list", "100%", "370px");

		var product2Sheet = {};

		pageCount = $('#pageCountLessProduct').val();

		product2Sheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		product2Sheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};

		product2Sheet.Cols = [
			{Header : "", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header : "", Type:"Text", SaveName:"prdtType", Width: 10, Align:"Center", Edit:1, Hidden:1}
			, {Header : "", Type:"CheckBox", SaveName:"checkProduct", Width: 10, Align:"Center", 	Edit:1}
			, {Header : "", Type:"Text", SaveName:"vndrNo", Width: 10, Align:"Center", 	Edit:1, Hidden:1}
			, {Header:"온라인상품코드", Type:"Text", SaveName:"prdtNo", Width: 10, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight: 60	}
			, {Header:"상품명", Type:"Text", SaveName:"prdtName", Width: 25, Align:"Center", Edit:0, FontUnderline:1, Cursor:"Pointer", Align:"Left"}
			, {Header:"자사(입점사)", Type:"Combo", SaveName:"mmnyPrdtYn", Width: 10, Align:"Center", Edit:0,ComboText : "자사|입점",	ComboCode : "Y|N"}
			, {Header:"전시채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0}
			, {Header:"카테고리", Type:"Text", SaveName:"stdCtgrName", Width: 10, Align:"Center", Edit:0}
			, { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }

			, { Header:"온라인재고",		Type:"Int",			SaveName:"stockAiQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"스마트재고",		Type:"Int",			SaveName:"stockAwQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"매장재고",		Type:"Int",			SaveName:"stockAsQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }
			, { Header:"입점재고",		Type:"Int",			SaveName:"stockVdQty",					Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" }

			, {Header:"주문가능수량", Type:"Text", SaveName:"orderPsbltQty", Width: 10, Align:"Center", Edit:0}
			, {Header:"옵션가용률", Type:"Text", SaveName:"availabilityRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"정상가", Type:"Text", SaveName:"normalAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"판매가", Type:"Text", SaveName:"sellAmt", Width: 10, Align:"Center", Edit:0}
			, {Header:"할인율", Type:"Int", SaveName:"onlnDscntRate", Width: 10, Align:"Center", Edit:0}
			, {Header:"useYnQty", Type:"Int", SaveName:"useYnQty", Width: 10, Align:"Center", Edit:0, Hidden:1}
			, {Header:"totalOptionCount", Type:"Int", SaveName:"totalOptionCount", Width: 10, Align:"Center", Edit:0, Hidden:1}
		];

		IBS_InitSheet(product2list , product2Sheet);
		product2list.FitColWidth();
		product2list.SetExtendLastCol(1);
		// Grid 건수 정보 표시
		product2list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		product2list.SetPagingPosition(2);
		product2list.InitDataCombo(0 , "sellStatCode" , productCodeCombo.SELL_STAT_CODE.text , productCodeCombo.SELL_STAT_CODE.code  );
	}

	/**
	 * IBSheet 이벤트
	 */
	_couponProduct.sheet.event = function(){
		product1list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( (product1list.ColSaveName(col) == "prdtName" || product1list.ColSaveName(col) == "prdtNo") && value != "" ) {
					abc.readonlyProductDetailPopup(product1list.GetRowData(row));
				}
			}
		}

		product2list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( (product2list.ColSaveName(col) == "prdtName" || product2list.ColSaveName(col) == "prdtNo") && value != "" ) {
					abc.readonlyProductDetailPopup(product2list.GetRowData(row));
				}
			}
		}

		product1list_OnRowSearchEnd = function(row) {
			var d = product1list.GetRowData(row);

			product1list.SetRowData(row, { availabilityRate : Math.ceil(d.useYnQty / d.totalOptionCount * 100)});
			product1list.SetRowData(row, { normalAmt : abc.text.isMakeComma(d.normalAmt) + '원'});
			product1list.SetRowData(row, { sellAmt : abc.text.isMakeComma(d.sellAmt) + '원'});
		}

		product2list_OnRowSearchEnd = function(row) {
			var d = product2list.GetRowData(row);

			product2list.SetRowData(row, { availabilityRate : Math.ceil(d.useYnQty / d.totalOptionCount * 100)});
			product2list.SetRowData(row, { normalAmt : abc.text.isMakeComma(d.normalAmt) + '원'});
			product2list.SetRowData(row, { sellAmt : abc.text.isMakeComma(d.sellAmt) + '원'});
		}

	}

	/**
	 * 이벤트
	 */
	_couponProduct.event = function(){
		$('.productSearchPop').on('click', function(e) {
			var listType = $(this).data('type');
			var cpnApplyType = $('[name=cpnApplyType]:checked').val();
			var callback = '';
			abc.biz.promotion.coupon.detail.beforeCpnApplyType = cpnApplyType;
			if(listType == 'product1list') {
				callback = 'abc.biz.promotion.coupon.product.appendProduct1';
			}else{
				callback = 'abc.biz.promotion.coupon.product.appendProduct2';
			}
			abc.productSearchPopup(true, callback);
		});

		$("#pageCountTagetProduct").on('change', _couponProduct.getPrdtList);
		$("#pageCountLessProduct").on('change', _couponProduct.getPrdtList);

		$('.productDelBtn').on('click', function(e) {
				var listType = $(this).data('type');
				var list = '';
				var deleteList = '';
				var seq = '';

				if(listType == 'product1list') {
					deleteList = product1list;
				} else {
					deleteList = product2list;
				}

				var seq = deleteList.FindCheckedRow('checkProduct');

				if(seq == '') {
					alert('대상 상품을 선택해주세요.');
					return;
				}

				if(! confirm('삭제하시겠습니까?')) {
					return;
				}

				var deletePrdtNo = [];

				var deleteSeqs = [];

				deleteSeqs = seq.split("|");
				// 배열 처리
				for(var i = 0; i <= deleteSeqs.length; i++){
					if(deleteList.GetCellValue(deleteSeqs[i], "checkProduct") == "1"){
						deletePrdtNo.push(deleteList.GetCellValue(deleteSeqs[i], "prdtNo"));
					}
				}

				var param = {
						prdtNoList   : deletePrdtNo,
						cpnNo        : $("#cpnNo").val()
				};

				$.ajax({
					type :"post",
					url : "/promotion/coupon/product/coupon-product-delete",
					async: false,
					data: param
				})
				.done(function(data){
					var beforeRow = deleteList.GetTotalRows();
					deleteList.RowDelete(seq);

					var vndrNoArr = new Array();

					$.each(data.vndrNoAddr, function(i, vndrNo) {
						vndrNoArr.push(vndrNo)
					});

					$.each(deleteList.ExportData({"Type":"json"}).data, function(i, v) {
						if(v.mmnyPrdtYn != '자사' && v.mmnyPrdtYn != 'Y') {
							var isVndrNo = false;
							$.each(vndrNoArr, function(i, vndrNo) {
								if (vndrNo == v.vndrNo) {
									isVndrNo = true;
									return false;
								}
							});
							if (! isVndrNo) {
								vndrNoArr.push(v.vndrNo);
							}
						}
					});

					abc.biz.promotion.coupon.vendor.getBasicInfoList(vndrNoArr, $("#cpnNo").val());

/*					if($('[name=cpnApplyType]:checked').val() == 'P') vendorlist.RemoveAll();
					_couponProduct.appendVendor(vndrNoArr);*/

//					_couponProduct.getPrdtList();

					alert("선택 상품을 삭제 처리 했습니다.");
					
					if(deleteList.SearchRows() == 0 ){
						_couponProduct.getPrdtList();
					}else{
						deleteList.SetTotalRows(Number(beforeRow - deleteSeqs.length));
					}
				})
				.fail(function(jqXHR, textStatus, errorThrown){
					console.log(jqXHR.responseJSON.message);
					alert("선택 상품을 삭제 처리 하지 못 했습니다.");
					return false;
				});

			//}
		});
	}

	_couponProduct.appendProduct1 = function(d) {
		var vndrNoArr = new Array();
		$.each(d, function(i, v) {
			//vndrNoArr.push(v.vndrNo);
			_couponProduct.appendProductSheet(v, 'product1list');
		});

		$.each(product1list.ExportData({"Type":"json"}).data, function(i, v) {
			if(v.mmnyPrdtYn != '자사' && v.mmnyPrdtYn != 'Y') {
				vndrNoArr.push(v.vndrNo);
			}
		});

		_couponProduct.appendVendor(vndrNoArr);
	}

	_couponProduct.appendProduct2 = function(d) {
		var vndrNoArr = new Array();
		$.each(d, function(i, v) {
			//vndrNoArr.push(v.vndrNo);
			_couponProduct.appendProductSheet(v, 'product2list');
		});

		$.each(product2list.ExportData({"Type":"json"}).data, function(i, v) {
			if(v.mmnyPrdtYn != '자사' && v.mmnyPrdtYn != 'Y') {
				vndrNoArr.push(v.vndrNo);
			}
		});

		_couponProduct.appendVendor(vndrNoArr);
	}

	_couponProduct.appendVendor = function(vndrNoArr) {
		var cpnNo = $("#cpnNo").val();
		// 기존 등록 상품 중 입점 업체 정보를 조회한다.
		if (! abc.text.isNull($("#cpnNo").val())) {

			param = {
				cpnNo : $("#cpnNo").val()
			};

			$.ajax({
				type :"post",
				url : "/promotion/coupon/vendor/list/read",
				async: false,
				data: param
			})
			.done(function(data){
				$.each(data, function(i, vndrShareRate) {
					vndrNoArr.push(vndrShareRate.vndrNo)
				});
			})
			.fail(function(jqXHR, textStatus, errorThrown){
				console.log(jqXHR.responseJSON.message);
				return false;
			});
		}
		if($('[name=cpnApplyType]:checked').val() == 'P' && vndrNoArr.length > 0) abc.biz.promotion.coupon.vendor.getBasicInfoList(vndrNoArr, cpnNo);
	}

	_couponProduct.duplCheck = function(prdtNo, sheetName) {
		var list = '';
		var duplCheck = true;
		if(sheetName == 'product1list') {
			list = product1list.ExportData({"Type":"json"}).data;
		} else {
			list = product2list.ExportData({"Type":"json"}).data;
		}

		$.each(list, function(i, v) {
			if(v.prdtNo == prdtNo) {
				duplCheck = false;
				return false;
			}
		});

		return duplCheck;
	}

	_couponProduct.appendProductSheet = function(d, sheetName) {
		var duplCheck = _couponProduct.duplCheck(d.prdtNo,  sheetName);

		if(duplCheck) {
			var sheet = eval(sheetName);	// 시트 객체 획득
			var insertRowNumber = "";
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
			var prdtType = 'T';
			if(sheetName == 'product2list') prdtType = 'L';
			insertRowNumber = sheet.RowCount() + 1
			initialRowData = {
				"seq" : insertRowNumber
				,"prdtType" : prdtType
				,"prdtNo"		: d.prdtNo
				,"vndrNo" : d.vndrNo
				,"titleImageUrl" : d.titleImageUrl
				,"prdtName"	: d.prdtName
				,"mmnyPrdtYn"	: d.mmnyPrdtYn == 'Y' ? '자사' : '입점사'
				,"chnnlName"	: d.chnnlName
				,"stdCtgrName"	: d.stdCtgrName
				,"sellStatCode"	: d.sellStatCode

				,"stockAiQty"	: d.stockAiQty
				,"stockAwQty"	: d.stockAwQty
				,"stockAsQty"	: d.stockAsQty
				,"stockVdQty"	: d.stockVdQty

				,"orderPsbltQty"	: d.orderPsbltQty
//				,"availabilityRate"	: d.availabilityRate
				,"availabilityRate"	: Math.ceil(d.useYnQty / d.totalOptionCount * 100)
				,"normalAmt"	: abc.text.isMakeComma(d.normalAmt)
				,"sellAmt"	: abc.text.isMakeComma(d.sellAmt)
				,"onlnDscntRate"	: d.onlnDscntRate
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
		}
	}

	_couponProduct.appendProductSheetByGetList = function(d, sheetName) {
		var duplCheck = _couponProduct.duplCheck(d.prdt_no,  sheetName);
		if(duplCheck) {
			var sheet = eval(sheetName);	// 시트 객체 획득
			var insertRowNumber = "";
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
			var prdtType = 'T';
			if(sheetName == 'product2list') prdtType = 'L';
			insertRowNumber = sheet.RowCount() + 1
			initialRowData = {
				"seq" : insertRowNumber
				,"prdtType" : prdtType
				,"prdtNo"		: d.prdtNo
				,"vndrNo" : d.vndrNo
				,"titleImageUrl" : d.titleImageUrl
				,"prdtName"	: d.prdtName
				,"mmnyPrdtYn"	: d.mmnyPrdtYn == 'Y' ? '자사' : '입점사'
				,"chnnlName"	: d.chnnlName
				,"stdCtgrName"	: d.stdrCtgrName
				,"sellStatCode"	: d.sellStatCode

				,"stockAiQty"	: d.stockAiQty
				,"stockAwQty"	: d.stockAwQty
				,"stockAsQty"	: d.stockAsQty
				,"stockVdQty"	: d.stockVdQty

				,"orderPsbltQty"	: d.orderPsbltQty
//				,"availabilityRate"	: d.orderPsbltQty
				,"availabilityRate"	: Math.ceil(d.useYnQty / d.totalOptionCount * 100)
				,"normalAmt"	: abc.text.isMakeComma(d.normalAmt) + '원'
				,"sellAmt"	: abc.text.isMakeComma(d.sellAmt) + '원'
				,"onlnDscntRate"	: d.onlnDscntRate
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
		}
	}

	/**
	 * 리스트 조회
	 */
	_couponProduct.getPrdtList = function(){
//		var form = $.form(document.forms.frm);
//		form.submit({
//			'type': 'POST',
//			'url': '/promotion/coupon/product/list/read',
//			'success': function(d) {
//				if(d.Data != null && d.Data.length > 0){
//					$.each(d.Data, function(i, v) {
//						if(v.prdtType == 'T')  _couponProduct.appendProductSheetByGetList(v, 'product1list');
//						else _couponProduct.appendProductSheetByGetList(v, 'product2list');
//					});
//				}
//			},
//			'error': function(e){
//				alert(e.message);
//				console.log(e);
//			}
//		});
		var cpnApplyType = $('[name=cpnApplyType]:checked').val();
		abc.biz.promotion.coupon.detail.beforeCpnApplyType = cpnApplyType;
		var sheetName = "";
		var pageCount = 100;
		if(cpnApplyType == "P"){
			$("#prdtType").val("T");
			sheetName = "product1list";
			pageCount = $("#pageCountTagetProduct").val();
		}else{
			$("#prdtType").val("L");
			sheetName = "product2list";
			pageCount = $("#pageCountLessProduct").val();
		}

		var param = { url : '/promotion/coupon/product/list/read'
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.frm)
			, CPage : 1
			, sheet : sheetName };

		DataSearchPaging(param);
	}

	$(function() {
		_couponProduct.init();
	});

})();