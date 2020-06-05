(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product");
	var _common = abc.biz.product.utils;

	_object.resource = {
			sheet : {
				header : {
					list : [
						{ Header:"",							Type:"CheckBox",	SaveName:"updateCheck",					Width: 10,  Align:"Center",	Edit:1, Sort:0},
						{ Header:"번호",							Type:"Seq",			SaveName:"seq",							Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"",							Type:"Status",		SaveName:"status",						Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"번호",							Type:"Int",			SaveName:"",							Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"사이트번호",					Type:"Text",		SaveName:"siteNo",						Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"사이트이름",					Type:"Text",		SaveName:"siteName",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"채널번호",						Type:"Text",		SaveName:"chnnlNo",						Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"자사/입점",						Type:"Combo",		SaveName:"mmnyPrdtYn",					Width: 15,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "자사|입점",					ComboCode : "Y|N" },
						{ Header:"업체상품코드",					Type:"Text",		SaveName:"vndrPrdtNoText",				Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"등급코드",						Type:"Combo",		SaveName:"dispFlagText",				Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer", ComboText:tierFlagCodeCombo.addInfo1, ComboCode:tierFlagCodeCombo.name },
						{ Header:"전시채널",						Type:"Text",		SaveName:"chnnlName",					Width: 20,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
						{ Header:"상품코드",						Type:"Text",		SaveName:"prdtNo",						Width: 25,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
						{ Header:"상품이미지",					Type:"Text",		SaveName:"titleImageName",				Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"상품이미지",					Type:"Text",		SaveName:"titleImagePathText",			Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"상품이미지",					Type:"Image",		SaveName:"titleImageUrl",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
						{ Header:"상품명",						Type:"Html",		SaveName:"prdtName",					Width: 40,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
						{ Header:"브랜드",						Type:"Text",		SaveName:"brandName",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
						{ Header:"판매상태",						Type:"Combo",		SaveName:"sellStatCode",				Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code },
						{ Header:"전시여부",						Type:"Combo",		SaveName:"dispYn",						Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
						{ Header:"온라인재고",					Type:"Int",			SaveName:"stockAiQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"스마트재고",					Type:"Int",			SaveName:"stockAwQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"매장재고",						Type:"Int",			SaveName:"stockAsQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"입점재고",						Type:"Int",			SaveName:"stockVdQty",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"주문가능수량",					Type:"Int",			SaveName:"avaiableStockQty",			Width: 25,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"주문가능수량",					Type:"Int",			SaveName:"orderPsbltQty",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"정상가",						Type:"Text",		SaveName:"normalAmt",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"판매가",						Type:"Text",		SaveName:"sellAmt",						Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"판매가",						Type:"Text",		SaveName:"displayProductPrice",			Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"할인율",						Type:"Int",			SaveName:"displayDiscountRate",			Width: 15,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"오프라인판매가",					Type:"Text",		SaveName:"erpSellAmt",					Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"온라인판매가",					Type:"Text",		SaveName:"onlnSellAmt",					Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"표준카테고리",					Type:"Text",		SaveName:"stdCtgrName",					Width: 20,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
						{ Header:"전시카테고리(기준)",				Type:"Text",		SaveName:"stdrCtgrName",				Width: 20,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
						{ Header:"기간계할인율/할인가",				Type:"Text",		SaveName:"erpDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"온라인할인율/할인가",				Type:"Text",		SaveName:"onlnDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"임직원할인율/할인가",				Type:"Text",		SaveName:"empDscntText",				Width: 30,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
						{ Header:"기간계할인율",					Type:"Text",		SaveName:"erpDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"온라인할인율",					Type:"Text",		SaveName:"onlnDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
						{ Header:"임직원할인율",					Type:"Text",		SaveName:"empDscntRate",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"판매중인옵션갯수",				Type:"Int",			SaveName:"useYnQty",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"재고통합여부",					Type:"Text",		SaveName:"stockIntgrYn",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"전체옵션갯수",					Type:"Int",			SaveName:"totalOptionCount",			Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"가용율(%)",						Type:"Int",			SaveName:"availabilityRate",			Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"기본수수료율",					Type:"Int",			SaveName:"dfltCmsnRate",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"등록업체명",					Type:"Text",		SaveName:"vndrName",					Width: 20,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
						{ Header:"예약상품여부",					Type:"Combo",		SaveName:"rsvPrdtYn",					Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "예약|일반",					ComboCode : "Y|N" },
						{ Header:"입점사번호",					Type:"Text",		SaveName:"vndrNo",						Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"스타일정보",					Type:"Text",		SaveName:"styleInfo",					Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"적용프로모션정보",				Type:"Text",		SaveName:"appliedPromotionInfo",		Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"적용다족구매프로모션갯수",			Type:"Int",			SaveName:"promotionCountDiscountMultiShoues",		Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"중복프로모션번호",				Type:"Text",		SaveName:"duplPromoNo",					Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
						{ Header:"상품색상정보",					Type:"Text",		SaveName:"prdtColorInfo",				Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 }
					]
				}
			}
		}

	_object.initPrev = function() {
		// 파라미터로 넘어온 정보를 검색창에 설정
		var CONTINUE_PARAMETER = ".title.isMultiple.callback.searchItemKeyword.promoType.";	// 검색조건 설정에 해당하지 않는 파라미터
		var parameters = _common.parameter.getParameters();
		var keys = Object.keys(parameters);
		var values = Object.keys(parameters).map(function(i) { return parameters[i]; } );
		for(var i=0 ; i<keys.length ; i++) {
			var key = keys[i];
			var value = decodeURIComponent(values[i]);

			switch(keys[i]) {
			case "isHideSearch" :
				// 임의파라미터. 검색창 숨김 여부. 아직사용안함.
//				var isHideSearch = _common.parameter.getParameter("isHideSearch");
				break;
			case "searchItemType" :
				// 임의파라미터. 검색유형
				$("#search-keyword-type").find("option[value='" + value + "']").prop("selected", true);
				$("#search-keyword").val(decodeURIComponent(_common.parameter.getParameter("searchItemKeyword")));
				break;
			case "searchPromoInfo" :
				// 임의파라미터. 프로모션정보조회
				var $param = $("<input>").attr({ type : "hidden" , name : "searchPromoInfo" , value : value });
				$("#search-form").append($param);
				break;
			default :
				if(CONTINUE_PARAMETER.indexOf("." + key + ".") > -1) {
					// 임의파리미터 중 다중파라미터를 사용하는 경우, 처리안함
					continue;
				}
				var element = $("[name='" + key + "']");
				switch(element.attr("type")) {
				case "hidden" :
					element.val(value);
					break;
				case "text" :
					element.val(value);
					break;
				case "textarea" :
					element.text(value);
					break;
				case "checkbox" :
					element.prop("checked", false);
					var splitedValues = value.split(",");
					if(splitedValues.length > 0) {
						for(var j=0 ; j<splitedValues.length ; j++) {
							element.filter("[value='" + splitedValues[j] + "']").prop("checked", true);
						}
					}
					break;
				case "radio" :
					element.filter("[value='" + value + "']").prop("checked", true);
					break;
				default :
					console.log("알 수 없는 유형입니다.(key : " + key + ") element.attr(\"type\") : " + element.attr("type"));
				}
			}
		}
	}

	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');
		_object.initPrev();
		_object.sheet.init();
		_object.sheet.event();
		_object.event();
		_object.initView();
		_object.initPost();
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
		}

		_object.getList();
	}

	_object.sheet.event = function(){
		/**
		 * IBSheet 이벤트
		 */
		var isMultiple = _common.parameter.isMultiple();	// 다중선택여부

		// 전체 선택 체크박스 제어
		list_OnBeforeCheckAll = function(row, col) {
			if(!isMultiple) {
				return false;
			}
		}

		// 체크박스 선택 제어
		list_OnBeforeCheck = function(row, col) {
			if(!isMultiple && col == 4) {
				// 체크박스 모두 해제
				var productList = list.ExportData({ Type : "json" }).data;
				for(var i=0 ; i<productList.length ; i++) {
					list.SetRowData(i, { checked : 0 });
				}
//				list.CheckAll(col, 0); // 오류 발생(Uncaught RangeError: Maximum call stack size exceeded)으로 인한 주석처리
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
						dscntRate += _common.number.unit.won(_common.number.comma(calculated));	// 천단위 콤마 및 금액단위 입력
						var modifiedRowData = {};
						modifiedRowData[textFields[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
						
					} else if( discountRateFields[i] == 'onlnDscntRate' ){
						
						var onlnSellAmt = list.GetRowData(row)["onlnSellAmt"];
						dscntRate += "% / ";
						dscntRate += _common.number.unit.won(_common.number.comma(onlnSellAmt));	// 천단위 콤마 및 금액단위 입력
						var modifiedRowData = {};
						modifiedRowData[textFields[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
						
					} else if( discountRateFields[i] == 'erpDscntRate' ){
						
						var erpSellAmt = list.GetRowData(row)["erpSellAmt"];
						dscntRate += "% / ";
						dscntRate += _common.number.unit.won(_common.number.comma(erpSellAmt));	// 천단위 콤마 및 금액단위 입력
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
				modifiedRowData[headers[i]] = _common.number.unit.won(_common.number.comma(price));
				list.SetRowData(row, modifiedRowData);
			}

			// 가용율 계산
			if(list.GetRowData(row).mmnyPrdtYn == abc.consts.BOOLEAN_FALSE){
				list.SetRowData(row, { "availabilityRate" : 0 });
			}else{
				list.SetRowData(row, { "availabilityRate" : Math.ceil(list.GetRowData(row).useYnQty / list.GetRowData(row).totalOptionCount) });
			}
		}

//		// 선택이벤트
//		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
//			if(row != 0 && col != 0) {
//				if(list.ColSaveName(col) === "prdtNo") {
//					// 상품번호 클릭 시, 상품수정 팝업 띄움
//					var param = {
//						prdtNo			: list.GetRowData(row).prdtNo,			// 상품번호
//						siteNo			: list.GetRowData(row).siteNo,			// 사이트번호
//						chnnlNo			: list.GetRowData(row).chnnlNo,			// 채널번호
//						vndrPrdtNoText	: list.GetRowData(row).vndrPrdtNoText	// 입점사관리번호
//					};
//					abc.open.popup({
//						url 	:	"/product/product/detail",
//						winname :	"product-detail-from-popup",
//						method	: 	"get",
//						title 	:	"product-detail-from-popup",
//						width 	:	1480,
//						height	:	970,
//						params	:	param
//					});
//				} else if(list.ColSaveName(col) === "prdtName") {
//					// 상품명 클릭 시, 프론트 상품상세 띄움
//					abc.productFrontUrl(list.GetRowData(row).prdtNo, true);
//				}
//			}
//		}

		_common.grid.util.decorateUnderline(list, ["prdtNo", "prdtName"]);	// 내용 밑줄
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
		});

		// 검색영역 자사/입점 토글 이벤트
		$("[name='mmnyPrdtYn']").click(function(e) {
			if($(this).val() === "Y" ) {
				$("#vndr-no").val("");	// 자사 선택 시 입점업체번호 초기화
				$("#vndr-name").val("");	// 자사 선택 시 입점업체명 초기화
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
			case "add-product" :
				var result = [];
				var productList = list.ExportData({ Type : "json" }).data;
				if(productList.length > 0) {
					for(var i=0 ; i<productList.length ; i++) {
						if(productList[i].updateCheck == 1) {
							result.push(productList[i]);
						}
					}
				} else {
					alert("조회된 데이터가 없습니다.");
					return;
				}

				if(!_common.parameter.isMultiple()) {
					if(result.length > 1) {
						alert('하나의 상품만 등록이 가능합니다.');
						return ;
					}
				}

				// callback 실행
				var callback = _common.parameter.runCallback("callback");
				if(typeof callback === "function") {
					if(result.length > 0) {
						callback(result);
						self.close();
					} else {
						alert("선택된 데이터가 없습니다.");
					}
				} else {
					alert("callback 함수를 찾을 수 없습니다.");
				}
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
				e.preventDefault();
				var options = { callback : "callbackFindVendor" };
				_object.popup("find-vendor", options);
				break;
			case "find-brand" :
				// 브랜드 찾기
				e.preventDefault();
				var options = { callback : "abc.biz.product.product.callback.findBrand", brandName : $('[name=brandName]').val() };
				window.abc.brandSearchPopup(false, options);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
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
				$("#chnnl-no").hide();
				$("#chnnl-no").val("");
				$("#site-no").show();
				break;
			default :
				$("#site-no").hide();
				$("#site-no").val("");
				$("#chnnl-no").hide();
				$("#chnnl-no").val("");
			}
		});

		//판매상태 체크별 event
		$("input:checkbox[name='sellStatCodes']").not("#sell-stat-code-all").click(function(){
			var length = $("[name='sellStatCodes']:checked").not('#sell-stat-code-all').length;
			console.log(length);
			if(length == 4){
				$("#sell-stat-code-all").prop("checked", true);
			} else {
				$("#sell-stat-code-all").prop("checked", false);
			}
		});

		//판매상태 전체체크 클릭시 event
		$("#sell-stat-code-all").click(function(){
			if($("#sell-stat-code-all").is(":checked")){
				$("[name='sellStatCodes']").prop("checked", true);
			}else{
				$("[name='sellStatCodes']").prop("checked", false);
			}
		});

		$("[name=radioRestockModule]").click(function(){
			if($("#radioRestock01").is(":checked")){
				$("[name=searchDate]").attr('disabled', true);
				$("[name=prdtRestock]").attr('disabled', true);
			} else {
				$("[name=searchDate]").attr('disabled', false);
				$("[name=prdtRestock]").attr('disabled', false);

			}
		});
		
		// 상품코드 20건까지 검색 제한
		_common.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);
	}

	/**
	 * 초기 화면 설정
	 */
	_object.initView = function() {
		$("#site-no").hide();
		$("#chnnl-no").hide();
		$("#searchNew").click();
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
	 * 목록 조회
	 */
	_object.getList = function() {
		var url = "/product/product";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	_object.popup = function(type, param) {
		switch(type) {
		case "find-vendor" :
			window.abc.vndrSearchPopup(false, param.callback);
			break;
		case "find-brand" :
			window.abc.brandSearchPopup(false, param);
			break;
		default :
			console.log("지원하지 않는 형식입니다. " + type);
		}
	}

	_object.initPost = function() {
		/**
		 * 초기화 후처리
		 */

		// 콜백함수가 없는 경우, callback에 사용되는 기능 비활성화
		if(_common.isEmpty(_common.parameter.getParameter("callback"))) {
			$("[data-button='add-product']").remove();	// 선택 상품 추가 버튼 삭제
			list.SetColEditable("updateCheck", 0);		// 그리드 내 체크박스 비활성화
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
	console.log(data);
	$("#vndr-no").val(data.arrayVndrNo[0]);
	$("#vndr-name").val(data.arrayVndrName[0]);
}