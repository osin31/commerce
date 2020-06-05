(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product");
	var _common = abc.biz.product.utils;
	var _searchType = "old";

	_object.resource = {
		sheet : {
			header : {
				list : [
					{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",					Width: 10,	Align:"Center",	Edit:1, Sort:0},
					{ Header:"번호",				Type:"Seq",			SaveName:"seq",							Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"",				Type:"Status",		SaveName:"status",						Width: 0,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",			SaveName:"",							Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트번호",			Type:"Text",		SaveName:"siteNo",						Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트이름",			Type:"Text",		SaveName:"siteName",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"채널번호",			Type:"Text",		SaveName:"chnnlNo",						Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"자사/입점",			Type:"Combo",		SaveName:"mmnyPrdtYn",					Width: 15,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer", ComboText : "자사|입점",						ComboCode : "Y|N" },
					{ Header:"업체상품코드",			Type:"Html",		SaveName:"vndrPrdtNoText",				Width: 20,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
					{ Header:"등급코드",			Type:"Combo",		SaveName:"dispFlagText",				Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer", ComboText:tierFlagCodeCombo.addInfo1, ComboCode:tierFlagCodeCombo.name },
					{ Header:"전시채널",			Type:"Text",		SaveName:"chnnlName",					Width: 20,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
					{ Header:"상품코드",			Type:"Text",		SaveName:"prdtNo",						Width: 20,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
					{ Header:"상품이미지",			Type:"Text",		SaveName:"titleImageName",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"상품이미지",			Type:"Text",		SaveName:"titleImagePathText",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"상품이미지",			Type:"Image",		SaveName:"titleImageUrl",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
					{ Header:"상품명",				Type:"Html",		SaveName:"prdtName",					Width: 30,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
					{ Header:"브랜드",				Type:"Text",		SaveName:"brandName",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
					{ Header:"스타일",				Type:"Text",		SaveName:"styleInfo",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer"},
					{ Header:"판매상태",			Type:"Combo",		SaveName:"sellStatCode",				Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code },
					{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",						Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
					{ Header:"온라인재고",			Type:"Int",			SaveName:"stockAiQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"스마트재고",			Type:"Int",			SaveName:"stockAwQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"매장재고",			Type:"Int",			SaveName:"stockAsQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"입점재고",			Type:"Int",			SaveName:"stockVdQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"주문가능수량",			Type:"Int",			SaveName:"avaiableStockQty",			Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"가용율",				Type:"Int",			SaveName:"availabilityRate",			Width: 20,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:0 },
					{ Header:"주문가능수량",			Type:"Int",			SaveName:"orderPsbltQty",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"정상가",				Type:"Text",		SaveName:"normalAmt",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"판매가",				Type:"Text",		SaveName:"sellAmt",						Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"판매가",				Type:"Text",		SaveName:"displayProductPrice",			Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"할인율",				Type:"Int",			SaveName:"displayDiscountRate",			Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"입점사수수료율",			Type:"Int",			SaveName:"vndrCmsnRate",			Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"오프라인판매가",		Type:"Text",		SaveName:"erpSellAmt",					Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"온라인판매가",			Type:"Text",		SaveName:"onlnSellAmt",					Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"표준카테고리",			Type:"Text",		SaveName:"stdCtgrName",					Width: 45,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
					{ Header:"전시카테고리(기준)",	Type:"Text",		SaveName:"stdrCtgrName",				Width: 45,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
					{ Header:"기간계\n할인율/할인가",	Type:"Text",		SaveName:"erpDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"온라인\n할인율/할인가",	Type:"Text",		SaveName:"onlnDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"임직원\n할인율/할인가",	Type:"Text",		SaveName:"empDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
					{ Header:"기간계\n할인율",		Type:"Text",		SaveName:"erpDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"온라인\n할인율",		Type:"Text",		SaveName:"onlnDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"임직원\n할인율",		Type:"Text",		SaveName:"empDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"판매중인옵션갯수",		Type:"Int",			SaveName:"useYnQty",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"재고통합여부",			Type:"Text",		SaveName:"stockIntgrYn",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"전체옵션갯수",			Type:"Int",			SaveName:"totalOptionCount",			Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"기본수수료율",			Type:"Int",			SaveName:"dfltCmsnRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"적용프로모션정보",		Type:"Text",		SaveName:"appliedPromotionInfo",		Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"적용다족구매프로모션갯수",	Type:"Int",			SaveName:"promotionCountDiscountMultiShoues",		Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"중복프로모션번호",		Type:"Text",		SaveName:"duplPromoNo",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"등록업체명",			Type:"Text",		SaveName:"vndrName",					Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"예약상품여부",			Type:"Combo",		SaveName:"rsvPrdtYn",					Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "예약|일반",						ComboCode : "Y|N" },
					{ Header:"승인일",				Type:"Date",		SaveName:"aprverDtm",					Width: 25,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
					{ Header:"최종입고일",			Type:"Date",		SaveName:"lastWrhsDay",					Width: 25,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" }
				]
			}
		}
	}

	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');
		_object.sheet.init();
		_object.sheet.event();
		_object.event();
		_object.initView();
		abc.biz.display.common.checkBoxAll({allId: '#sell-stat-code-all', itemsClass: '.sellStatCodes'});
		abc.biz.display.common.checkBoxAll({allId: '#tierFlagCodeAll', itemsClass: '.tierFlagCode'});
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		_common.grid.create(_object.sheet, "product-list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.list);
		list.SetFrozenCol(list.SaveNameCol("stdCtgrName"));	// 틀고정
		_common.grid.util.decorateUnderline(list, ["prdtNo", "titleImageUrl", "prdtName"]);	// 내용 밑줄

		if(userAuthority === "B") {
			list.SetColHidden(list.SaveNameCol("stockAiQty"), 0);	// AI 재고 보이기
			list.SetColHidden(list.SaveNameCol("stockAwQty"), 0);	// AW 재고 보이기
			list.SetColHidden(list.SaveNameCol("stockAsQty"), 0);	// AS 재고 보이기
			list.SetColHidden(list.SaveNameCol("stockVdQty"), 0);	// VD 재고 보이기
		} else {
			list.SetHeaderMode();
			list.SetColHidden(list.SaveNameCol("stockVdQty"), 0);	// VD 재고 보이기
			list.SetColHidden(list.SaveNameCol("empDscntRate"), 1);	// 임직원할인율
			list.SetColHidden(list.SaveNameCol("empDscntText"), 1);	// 임직원할인가
		}

		_object.getList();
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		// 선택이벤트
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			if(row != 0 && col != 0) {
				if(list.ColSaveName(col) === "prdtNo" || list.ColSaveName(col) === "titleImageUrl") {
					// 상품번호/상품이미지 클릭 시, 상품수정 팝업 띄움
					var param = {
						prdtNo			: list.GetRowData(row).prdtNo,			// 상품번호
						siteNo			: list.GetRowData(row).siteNo,			// 사이트번호
						chnnlNo			: list.GetRowData(row).chnnlNo,			// 채널번호
						vndrPrdtNoText	: list.GetRowData(row).vndrPrdtNoText	// 입점사관리번호
					};
					abc.open.popup({
						url 	:	"/product/product/detail",
						winname :	"product-detail",
						method	: 	"get",
						title 	:	"product detail",
						width 	:	1480,
						height	:	970,
						params	:	param
					});
				} else if(list.ColSaveName(col) === "prdtName") {
					// 상품명 클릭 시, 프론트 상품상세 띄움
					abc.productFrontUrl(list.GetRowData(row).prdtNo);
				}
			}
		}

		list_OnRowSearchEnd = function(row) {
			/**
			 * 2020.02.13
			 * 할인율 정보를 기반으로 정상가에서 할인금액 계산 및 입력 (상품가격이력 테이블에 저장안되는 임직원만)
			 */
			var discountRateFields = ["erpDscntRate", "onlnDscntRate", "empDscntRate"];		// 할인율 필드
			var textFields = ["erpDscntText", "onlnDscntText", "empDscntText"];				// 복합정보 입력 필드

			
			for(var i=0 ; i<discountRateFields.length ; i++) {
				var dscntRate = list.GetRowData(row)[discountRateFields[i]];
				var normalAmt = list.GetRowData(row)["normalAmt"];

				if(dscntRate !== "") {
					
					if( discountRateFields[i] == 'empDscntRate' ){
						
						var discountPrice = 0.00;
						discountPrice = normalAmt * (dscntRate / 100);
						var calculated = normalAmt - discountPrice;
						dscntRate += "% / ";
						dscntRate += _common.number.setDecoratePrice(calculated);	// 천단위 콤마 및 금액단위 입력
						var modifiedRowData = {};
						modifiedRowData[textFields[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
						
					} else if( discountRateFields[i] == 'onlnDscntRate' ){
						
						var onlnSellAmt = list.GetRowData(row)["onlnSellAmt"];
						dscntRate += "% / ";
						dscntRate += _common.number.setDecoratePrice(onlnSellAmt);	// 천단위 콤마 및 금액단위 입력
						var modifiedRowData = {};
						modifiedRowData[textFields[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
						
					} else if( discountRateFields[i] == 'erpDscntRate' ){
						
						var erpSellAmt = list.GetRowData(row)["erpSellAmt"];
						dscntRate += "% / ";
						dscntRate += _common.number.setDecoratePrice(erpSellAmt);	// 천단위 콤마 및 금액단위 입력
						var modifiedRowData = {};
						modifiedRowData[textFields[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
					}
				}
			}

			// 단위 "원" 입력
			headers = ["normalAmt", "sellAmt", "displayProductPrice"];
			for(var i=0 ; i<headers.length ; i++) {
				var price = list.GetRowData(row)[headers[i]];
				var modifiedRowData = {};
				modifiedRowData[headers[i]] = _common.number.setDecoratePrice(price);
				list.SetRowData(row, modifiedRowData);
			}

			// 가용율 계산
			if(list.GetRowData(row).mmnyPrdtYn == abc.consts.BOOLEAN_FALSE){
				list.SetRowData(row, { "availabilityRate" : 0 });
			}else{
				var rate = Math.ceil(list.GetRowData(row).useYnQty / list.GetRowData(row).totalOptionCount * 100);
				if(isNaN(rate)){
					list.SetRowData(row, { "availabilityRate" :  0});
				}else{
					list.SetRowData(row, { "availabilityRate" :  rate});
				}
			}
		}

		$("[name=radioRestockModule]").click(function(){
			if($("#radioRestock01").is(":checked")){
				$("[name=searchDate]").attr('disabled', true);
				$("[name=prdtRestock]").attr('disabled', true);
			} else {
				$("[name=searchDate]").attr('disabled', false);
				$("[name=prdtRestock]").attr('disabled', false);

			}
		});

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		// 전시카테고리 이벤트 설정
		abc.biz.display.common.setDpCategoryEvent();

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			_common.initFormData("search-form");
			$("#site-no").hide();	// 사이트검색 숨김
			$("#chnnl-no").hide();	// 채널검색 숨김
			$("#search-form").find("input[type='checkbox']").prop("checked", "checked");	// 체크박스 초기화 시 전체선택되어야 하므로 추가
			$("#brand-name").val('');
			$("#vndr-name").val('');
			$('#prdt-keyword').val('');
		});

		// 검색영역 자사/입점 선택 이벤트
		$("#mmny-prdt-yn").change(function() {
			switch($(this).val()) {
			case "Y" :
				// 자사 선택
				$("#site-no").hide();
				$("#site-no").val("");
				$("#chnnl-no").show();
				break;
			case "N" :
				// 입점 선택
				$("#chnnl-no").show();
				$("#chnnl-no").val("");
				$("#site-no").hide();
				break;
			default :
				$("#site-no").hide();
				$("#site-no").val("");
				$("#chnnl-no").hide();
				$("#chnnl-no").val("");
			}

			// 입점사명 제어
			if('Y' == $(this).val()) {
				$("#vndr-name").val('');
			    $("#vndr-name").prop('readonly', true);
			    $("#search-vndr").addClass("disabled");
			} else {
				$("#search-vndr").removeClass("disabled");
				$("#vndr-name").removeAttr("readonly");
			}
		});

		// 검색 버튼 이벤트
		$("#search, #searchNew").click(function() {
			// 전시카테고리 검색값 검증
			if(_common.isNotEmpty($(".dp-category-area>.chnnl-no").val())) {
			    var message = "";
			    $.each($(".dp-category-area").find("select:enabled").find("option:selected"), function(i, v) {
			        if(_common.isEmpty(message)) {
			            if(_common.isEmpty($(v).val())) {
			                if($(v).attr("name") != "ctgrNo") {
			                    if($(v).closest("select").hasClass("1depth")) {
			                        message = "대카테고리";
			                    } else if($(v).closest("select").hasClass("2depth")) {
			                        message = "중카테고리";
			                    } else if($(v).closest("select").hasClass("3depth")) {
			                        message = "소카테고리";
			                    }
			                } else {
			                    message = "세카테고리";
			                }
			            }
			        }
			    });
			    /*if(_common.isNotEmpty(message)) {
			        alert("전시카테고리 중 " + message + "를 선택해 주세요.");
			        return false;
			    }*/
			}

			// 판매가 범위 검증
			if(_common.isNotEmpty($("[name='sellPriceStartWith']").val()) && _common.isNotEmpty($("[name='sellPriceEndWith']").val())) {
				if($("[name='sellPriceStartWith']").val() > $("[name='sellPriceEndWith']").val()) {
					alert("판매가 검색 시작금액이 종료금액보다 큽니다.");
					return false;
				}
			}

			// 추가검색 사용여부 설정
			if($(".tbl-search").eq(1).is(":visible")) {
				$("#use-advanced-search").val("Y");
			} else {
				$("#use-advanced-search").val("N");
			}

			if (this.id == "searchNew") {
				_searchType = "new";
			} else {
				_searchType = "old";
			}

			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		// 표준카테고리 이벤트
		abc.biz.display.common.setStdCategoryEvent();

		$("[data-button]").click(function(e) {
			var type = $(this).data("button");
			switch(type) {
			case "download-excel" :
//				list.Down2Excel({"FileName":"test", "SheetName":"testSheet"});
				var $rowsPerPage = $("<input>").attr({ "type" : "hidden", "name" : "rowsPerPage" }).val($("#page-count").val());
				var $pageNum = $("<input>").attr({ "type" : "hidden", "name" : "pageNum" }).val(list.GetCurrentPage());
				$("#search-form").attr("action", "/product/product/download/excel");
				$("#search-form").attr("method", "POST");
				$("#search-form").append($rowsPerPage);
				$("#search-form").append($pageNum);
				$("#search-form").submit();
				$("#search-form").find("[name='rowsPerPage']").remove();
				$("#search-form").find("[name='pageNum']").remove();
				break;
			case "download-excel-new" :
//				list.Down2Excel({"FileName":"test", "SheetName":"testSheet"});
				var $rowsPerPage = $("<input>").attr({ "type" : "hidden", "name" : "rowsPerPage" }).val($("#page-count").val());
				var $pageNum = $("<input>").attr({ "type" : "hidden", "name" : "pageNum" }).val(list.GetCurrentPage());
				$("#search-form").attr("action", "/product/product/download/excelNew");
				$("#search-form").attr("method", "POST");
				$("#search-form").append($rowsPerPage);
				$("#search-form").append($pageNum);
				$("#search-form").submit();
				$("#search-form").find("[name='rowsPerPage']").remove();
				$("#search-form").find("[name='pageNum']").remove();
				break;
			case "download-excel-sku" :
//				list.Down2Excel({"FileName":"test", "SheetName":"testSheet"});
				var $rowsPerPage = $("<input>").attr({ "type" : "hidden", "name" : "rowsPerPage" }).val($("#page-count").val());
				var $pageNum = $("<input>").attr({ "type" : "hidden", "name" : "pageNum" }).val(list.GetCurrentPage());
				$("#search-form").attr("action", "/product/product/download/excel/sku/prodlist");
				$("#search-form").attr("method", "POST");
				$("#search-form").append($rowsPerPage);
				$("#search-form").append($pageNum);
				$("#search-form").submit();
				$("#search-form").find("[name='rowsPerPage']").remove();
				$("#search-form").find("[name='pageNum']").remove();
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			}
		});

		// 기간 제어
		$("[data-button-period]").click(function(e) {
			var type = $(this).data("button-period");
			switch(type) {
			case "today" :
				abc.date.days(this);
				break;
			case "week" :
				abc.date.week(this);
				break;
			case "month" :
				abc.date.month(this);
				break;
			case "year" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		$("[data-button-popup]").click(function(e) {
			var type = $(this).data("button-popup");
			switch(type) {
			case "find-vendor" :
				// 입점사 찾기
				var options = { callback : "abc.biz.product.product.callback.findVendor" , vndrName : $('[name=vndrName]').val()};
				_object.popup("find-vendor", options);
				break;
			case "find-brand" :
				// 브랜드 찾기
				e.preventDefault();
				var options = { callback : "abc.biz.product.product.callback.findBrand", brandName : $('[name=brandName]').val() };
				_object.popup("find-brand", options);
				break;
			case "registry" :
				// 상품등록
				e.preventDefault();
				_object.popup("registry", { auth : "P", type : "registry" });
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		$("#brand-name").keydown(function(e){
			if(e.keyCode == 13)  $("#search").click();
		});

		// 상품코드 100건까지 검색 제한
		_common.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);
	}

	/**
	 * 초기 화면 설정
	 */
	_object.initView = function() {
		$("#site-no").hide();
		$("#chnnl-no").hide();
	}

	_object.callback = {};

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callback.findBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandName);
	}

	/**
	 * 입점사 찾기 callback 이벤트
	 */
	_object.callback.findVendor= function(data) {
//		$("#vndr-no").val(data.arrayVndrNo[0]);
		$("[data-item-search='vndr-no']").val(data.arrayVndrNo[0]);
		$("#vndr-name").val(data.arrayVndrName[0]);
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {

		var url = "/product/product/" + (_searchType == "old" ? "" : "productSearchNew");
		var serializedFormData = $("#search-form").serialize() + "&prdtListLimit=100";
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	/**
	 * 팝업
	 */
	_object.popup = function(type, param) {
		switch(type) {
		case "registry" :
			abc.open.popup({
					url 	:	"/product/product/detail",
					winname :	"registration",
					method	: 	"get",
					title 	:	encodeURIComponent("상품 등록"),
					width 	:	1480,
					height	:	980,
					params	:	param
				});
			break;
		case "find-vendor" :
			window.abc.vndrSearchPopup(false, param);
			break;
		case "find-brand" :
			window.abc.brandSearchPopup(false, param);
			break;
		default :
			console.log("지원하지 않는 형식입니다. " + type);
		}
	}

	$(function() {
		_object.init();
	});

})();

/**
 * 입점사 찾기 callback 이벤트
 */
function callbackFindVendor(data) {
//	$("#vndr-no").val(data.arrayVndrNo[0]);
	$("[data-item-search='vndr-no']").val(data.arrayVndrNo[0]);
	$("#vndr-name").val(data.arrayVndrName[0]);
}
