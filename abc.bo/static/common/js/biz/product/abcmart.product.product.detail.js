(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product.detail");
	var _common = abc.biz.product.utils;

	/**
	 * 2020.01.30 : 채널별 카테고리 최대 LEVEL
	 */
	_object.MAX_CTGR_LEVEL_ART = "";
	_object.MAX_CTGR_LEVEL_ABC = "";
	_object.MAX_CTGR_LEVEL_GS = "";
	_object.MAX_CTGR_LEVEL_OTS = "";
	_object.MAX_CTGR_LEVEL_KIDS = "";

	_object.resource = {
		isRunningNetwork : false,	// 네트워크사용플래그
		isInitializing : false,		// 상품추가버튼에 의한 초기화 진행여부
		popup : {				// 팝업 객체 저장공간
			findBrand : null		// 브랜드 찾기 팝업
		},
		view3d : {
			brand : [						// 3D VIEW를 사용하는 브랜드 목록
				{ "brandNo" : "000050" }	// 나이키
			]
		},
		search : {
			keyword : {
				maxLength : 100	// 검색어 최대입력갯수
			}
		},
		sheet : {	// IBSheet 헤더정보
			header : {
				cntcPrdtSetupList : [
					{ Header:"",			Type:"Status",		SaveName:"status",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"관련상품순번",		Type:"Int",			SaveName:"rltnPrdtTypeCode",	Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 5,	Align:"Center",	Edit:1,	Cursor:"Pointer" },
					{ Header:"상품코드",		Type:"Text",		SaveName:"rltnPrdtNo",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"상품명",			Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"브랜드",			Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"표준카테고리",		Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"전시채널",		Type:"Text",		SaveName:"chnnlName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시카테고리",		Type:"Text",		SaveName:"stdrCtgrName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 }
				],
				rltnGoodsSetupList : [
					{ Header:"",			Type:"Status",		SaveName:"status",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"관련상품순번",		Type:"Int",			SaveName:"rltnPrdtTypeCode",	Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 5,	Align:"Center",	Edit:1,	Cursor:"Pointer" },
					{ Header:"상품코드",		Type:"Text",		SaveName:"rltnPrdtNo",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"상품명",			Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"브랜드",			Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"표준카테고리",		Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"전시채널",		Type:"Text",		SaveName:"chnnlName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시카테고리",		Type:"Text",		SaveName:"stdrCtgrName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				],
				prdtOptnListBo : [
					{ Header:"",			Type:"Status",		SaveName:"status",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"seq",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"업체상품번호",		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"자사상품여부",		Type:"Text",		SaveName:"mmnyPrdtYn",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0},
					{ Header:"옵션ID",		Type:"Text",		SaveName:"prdtOptnNo",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0},
					{ Header:"옵션명",			Type:"Text",		SaveName:"optnName",			Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"추가금액",		Type:"Int",			SaveName:"optnAddAmt",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",				Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"온라인\n마감재고",	Type:"Int",			SaveName:"stockAiQty",			Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"스마트\n재고",	Type:"Int",			SaveName:"stockAwQty",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"매장\n재고",		Type:"Int",			SaveName:"stockAsQty",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"온라인\n주문수량",	Type:"Int",			SaveName:"orderAiCount",		Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"스마트\n주문수량",	Type:"Int",			SaveName:"orderAwCount",		Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"매장\n주문수량",	Type:"Int",			SaveName:"orderAsCount",		Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"입점\n주문수량",	Type:"Int",			SaveName:"orderVdCount",		Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"온라인\n주문가능수량",Type:"Int",			SaveName:"availableStockAiQty",	Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", Sort:0 },
					{ Header:"스마트\n주문가능수량",			Type:"Int",			SaveName:"availableStockAwQty",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"매장\n주문가능수량",			Type:"Int",			SaveName:"availableStockAsQty",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"입점",			Type:"Int",			SaveName:"availableStockVdQty",	Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"전체주문\n가능수량",		Type:"Int",			SaveName:"orderPsbltQty",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"사방넷재고량",		Type:"Int",			SaveName:"stockSbQty",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"총주문수량",		Type:"Int",			SaveName:"totalOrderQty",		Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"총재고수량",		Type:"Int",			SaveName:"totalStockQty",		Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"품절사유",		Type:"Text",		SaveName:"soldOutRsnText",		Width: 30,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
					{ Header:"판매상태",		Type:"Text",		SaveName:"sellStatCodeName",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"전시여부",		Type:"Combo",		SaveName:"useYn",				Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0 }
				],
				prdtOptnListPo : [
					{ Header:"",			Type:"Status",		SaveName:"status",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"seq",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"상품옵션번호",		Type:"Text",		SaveName:"prdtOptnNo",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"자사상품여부",		Type:"Text",		SaveName:"mmnyPrdtYn",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
					{ Header:"사이즈",			Type:"Text",		SaveName:"optnName",			Width: 20,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
//					{ Header:"사이즈",			Type:"Text",		SaveName:"addOptn1Text",		Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer" },
					{ Header:"추가옵션",		Type:"Text",		SaveName:"addOptn2Text",		Width: 20,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",				Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"재고",			Type:"Int",			SaveName:"stockVdQty",			Width: 10,	Align:"Center",	Edit:1, Cursor:"Pointer", Sort:0 },
					{ Header:"총주문수량",		Type:"Int",			SaveName:"totalOrderQty",		Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", Sort:0 },
					{ Header:"주문가능수량",		Type:"Int",			SaveName:"orderPsbltQty",		Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", Sort:0 },
					{ Header:"옵션추가금액",		Type:"Int",			SaveName:"optnAddAmt",			Width: 20,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
					{ Header:"옵션판매상태",		Type:"Combo",		SaveName:"sellStatCode",		Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code, Sort:0 },
					{ Header:"품절사유",		Type:"Text",		SaveName:"soldOutRsnText",		Width: 30,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
					{ Header:"관리번호",		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 20,	Align:"Center",	Edit:1,	Cursor:"Pointer", Sort:0 },
					{ Header:"판매상태",		Type:"Text",		SaveName:"sellStatCodeName",	Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Sort:0 },
					{ Header:"전시여부",		Type:"Combo",		SaveName:"useYn",				Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0 }
				]
			}
		},
		prdtSafeItem : [	// 상품정보고시 중 취급주의사항 및 제품안전 인증정보
			{
				id						: "10000",
				name					: "공급자 적합성 확인 대상 품목",
				text					: "본 제품은 공급자 적합성 확인 대상 품목이며 관련 적합성 인증을 필하였을을 확인합니다.",
				safeCrtfcNoText			: "",
				safeCrtfcImageName		: "auth_mark.jpg",
				safeCrtfcImagePathText 	: "/static/images/auth/auth_mark.jpg",
				safeCrtfcImageUrl 		: "/static/images/auth/auth_mark.jpg"
			},
			{
				id						: "10001",
				name					: "안전 확인 대상 품목",
				text					: "본 제품은 안전 확인 대상 품목이며 관련 확인 인증을 필하였음을 확인합니다.",
				safeCrtfcNoText			: "",
				safeCrtfcImageName 		: "auth_mark.jpg",
				safeCrtfcImagePathText 	: "/static/images/auth/auth_mark.jpg",
				safeCrtfcImageUrl 		: "/static/images/auth/auth_mark.jpg"
			},
			{
				id						: "10002",
				name					: "안전 인증 대상 품목",
				text					: "본 제품은 안전 인증 대상 품목이며 관련 확인 인증을 필하였음을 확인합니다.",
				safeCrtfcNoText			: "",
				safeCrtfcImageName 		: "auth_mark.jpg",
				safeCrtfcImagePathText 	: "/static/images/auth/auth_mark.jpg",
				safeCrtfcImageUrl 		: "/static/images/auth/auth_mark.jpg"
			}
		]
	}

	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');
		_object.editor.init();
		_object.sheet.init();
		_object.sheet.event();
		_object.event();
		_object.initView();
		_object.initData();
		_object.callback.approval();	// 승인관리에서 사용됨
	}

	_object.editor = {
		init : function() {
			/**
			 * 에디터 초기화
			 */

			// 에디터 초기화 (상세설명-PC, 상세설명-MOBILE)
			_common.editor.createWithUpload(["prdt-dtl-info-pc", "prdt-dtl-info-mobile"], ["/product/product/editer/upload", "/product/product/editer/upload"]);
		}
	}

	_object.sheet = {
		init : function() {
			/**
			 * 그리드 초기화
			 */
			_common.grid.create(_object.sheet, "cntc-prdt-setup-list", "cntcPrdtSetupList", "100%", "429px", null, _object.resource.sheet.header.cntcPrdtSetupList);	// 색상연계상품목록 그리드 초기화
			_common.grid.create(_object.sheet, "rltn-goods-setup-list", "rltnGoodsSetupList", "100%", "429px", null, _object.resource.sheet.header.rltnGoodsSetupList);	// 관련용품목록 그리드 초기화
			if(isMmnyPrdt) {
				_common.grid.create(_object.sheet, "prdt-optn-list", "prdtOptnList", "100%", "429px", null, _object.resource.sheet.header.prdtOptnListBo);	// 상품옵션목록 그리드 초기화
			} else {
				_common.grid.create(_object.sheet, "prdt-optn-list", "prdtOptnList", "100%", "429px", null, _object.resource.sheet.header.prdtOptnListPo);	// 상품옵션목록 그리드 초기화
			}

			_object.getList("cntcPrdtSetupList");	// 색상연계상품목록 조회
			_object.getList("rltnGoodsSetupList");	// 관련용품목록 조회
			_object.getList("prdtOptnList");		// 상품옵션목록 조회

			prdtOptnList.Editable = true;
		},
		event : function() {
			/**
			 * IBSheet 이벤트
			 */

			if(isMmnyPrdt) {
				// 자사

				prdtOptnList_OnRowSearchEnd = function(row) {

					var stockAiQty = prdtOptnList.GetCellValue(row, "stockAiQty");	// 온라인 재고량
					var stockAwQty = prdtOptnList.GetCellValue(row, "stockAwQty");	// 스마트 재고량
					var stockAsQty = prdtOptnList.GetCellValue(row, "stockAsQty");	// 매장 재고량

					var orderAiCount = prdtOptnList.GetCellValue(row, "orderAiCount");	// 온라인 주문수량
					var orderAwCount = prdtOptnList.GetCellValue(row, "orderAwCount");	// 스마트 주문수량
					var orderAsCount = prdtOptnList.GetCellValue(row, "orderAsCount");	// 매장 주문수량

					if(_common.isEmpty(stockAiQty)) { stockAiQty = 0; }
					if(_common.isEmpty(stockAwQty)) { stockAwQty = 0; }
					if(_common.isEmpty(stockAsQty)) { stockAsQty = 0; }

					if(_common.isEmpty(orderAiCount)) { orderAiCount = 0; }
					if(_common.isEmpty(orderAwCount)) { orderAwCount = 0; }
					if(_common.isEmpty(orderAsCount)) { orderAsCount = 0; }

					prdtOptnList.SetCellValue(row, "availableStockAiQty", stockAiQty - orderAiCount);
					prdtOptnList.SetCellValue(row, "availableStockAwQty", stockAwQty - orderAwCount);
					prdtOptnList.SetCellValue(row, "availableStockAsQty", stockAsQty - orderAsCount);
				}

				prdtOptnList_OnAfterEdit = function(row, col) {
					/**
					 * 셀 수정 후 이벤트
					 */
					if("stockAiQty" === prdtOptnList.CellSaveName(row, col)) {
						// 재고를 수정한 경우, 주문가능수량 재설정
						var totalStockQty = 0;
						totalStockQty += prdtOptnList.GetRowData(row).stockAiQty;		// 온라인재고수량
						totalStockQty += prdtOptnList.GetRowData(row).stockAwQty;		// 스마트재고수량
						totalStockQty += prdtOptnList.GetRowData(row).stockAsQty;		// 매장재고수량
						var totalOrderQty = prdtOptnList.GetRowData(row).totalOrderQty;		// 총주문수량
						var orderPsbltQty = totalStockQty - totalOrderQty;					// 주문가능수량
						prdtOptnList.SetRowData(row, { "orderPsbltQty" : orderPsbltQty });
					}
				}
			} else {
				//옵션 그리드 클릭 시
				prdtOptnList_OnClick = function(row, col) {
					var checkedRowCnt = 0;
					if (row != 0) {
						// 목록에서 체크된 부분이 없을 경우 상단에 위치한 전체 선택을 해제
						if (prdtOptnList.ColSaveName(col) == "checked") {
							checkedRowCnt = prdtOptnList.CheckedRows("checked");

							if(checkedRowCnt == 0){
								prdtOptnList.ClearHeaderCheck();
							}
						}
					}
				}

				// 입점
				prdtOptnList_OnAfterEdit = function(row, col) {
					/**
					 * 셀 수정 후 이벤트
					 */
					if("stockVdQty" === prdtOptnList.CellSaveName(row, col)) {
						// 재고를 수정한 경우, 주문가능수량 재설정
						var totalStockQty = prdtOptnList.GetRowData(row).stockVdQty;		// 재고수량
						var totalOrderQty = prdtOptnList.GetRowData(row).totalOrderQty;		// 총주문수량
						var orderPsbltQty = totalStockQty - totalOrderQty;					// 주문가능수량
						prdtOptnList.SetRowData(row, { "orderPsbltQty" : orderPsbltQty });
					}
				}
			}
		}
	}

	_object.event = function() {
		/**
		 * 이벤트 바인딩
		 */

		$(".anchor-tab-wrap").click(function(e) {
			// 자사상품 신규등록 시 채널선택유도
			if(location.pathname === "/product/product/detail") {
				// 상품상세 팝업인 경우
				if(!_object.resource.isInitializing && $("[data-channel-area]").filter("td.td-spot").length === 0) {
					e.preventDefault();
					alert("먼저 등록하시려는 채널에서 \"상품추가\"를 선택해주세요.");
					return;
				}
			}
		});

//		$("[name='addOptnSetupYn']").change(function() {
//			// 추가옵션여부에 대한 옵션 행추가 버튼 보이기/숨기기 이벤트
//			switch($(this).val()) {
//			case "Y" :
//				$("[data-toggle='addOptnSetupYn']").show();
//				break;
//			case "N" :
//				$("[data-toggle='addOptnSetupYn']").hide();
//				break;
//			default :
//				console.log("알 수 없는 유형입니다. " + $(this).val());
//			}
//		});

		// 전시카테고리 이벤트 설정
		// 2020-02-02 자사 상품도 표준 카테고리에 속한 전시 카테고리만 노출 되어야 함.
		abc.biz.display.common.setDpCategoryEvent();

		// 사이즈가이드 이벤트 설정
		_object.sizeChart.event();

		// 취급주의사항 이벤트 설정
		_object.precaution.event();

		// 색상정보연계 이벤트
		$("[name='cntcPrdtSetupYn']").click(function(e) {
			var value = $(this).val();
			if(value === "Y") {
				$("[data-hidden='cntc-prdt-setup-yn']").show();
				$("[data-rowspan='cntc-prdt-setup-yn']").attr("rowspan", "2");
			} else {
				$("[data-hidden='cntc-prdt-setup-yn']").hide();
				$("[data-rowspan='cntc-prdt-setup-yn']").removeAttr("rowspan");
				cntcPrdtSetupList.RemoveAll();	// 시트 비우기
			}
		});

		// 관련용품 설정 이벤트
		$("[name='rltnGoodsSetupYn']").click(function(e) {
			var value = $(this).val();
			if(value === "Y") {
				$("[data-hidden='rltn-goods-setup-yn']").show();
				$("[data-rowspan='rltn-goods-setup-yn']").attr("rowspan", "2");
			} else {
				$("[data-hidden='rltn-goods-setup-yn']").hide();
				$("[data-rowspan='rltn-goods-setup-yn']").removeAttr("rowspan");
				rltnGoodsSetupList.RemoveAll();	// 시트 비우기
			}
		});

		// ERP 상품코드 조회 이벤트
		$("#search-vndr-prdt-no-text").click(function(e) {
			e.preventDefault();
			var searchVndrPrdtNoText = $("[name=searchVndrPrdtNoText]").val();
			if($.isNumeric(searchVndrPrdtNoText)) {
			location.href = "/product/product/detail?vndrPrdtNoText=" + searchVndrPrdtNoText;
			} else {
				alert("ERP 상품코드가 올바르지 않습니다. (" + searchVndrPrdtNoText + ")");
			}
		});

		// 에디터 데이터 설정
		if(_common.isNotEmpty(document.getElementById("prdt-dtl-info-pc"))) {
			$("#prdt-dtl-info-pc").text(CKEDITOR.instances["prdt-dtl-info-pc"].getData());
		}
		if(_common.isNotEmpty(document.getElementById("prdt-dtl-info-mobile"))) {
			$("#prdt-dtl-info-mobile").text(CKEDITOR.instances["prdt-dtl-info-mobile"].getData());
		}

		// 대표이미지 첨부 이벤트
		_common.processImage( { file: "#image-file", name: "#image-name", type : "I" } );	// 상품이미지 대표이미지
		if($("input[id^='image-file-']").length > 0) {
			$.each($("input[id^='image-file-']"), function(i, v) {
				var me = $(v);
				var selector = "#" + me.attr("id");
				_common.processImage( { file: selector, name: selector } );	// 상품 추가이미지
			});
		}
		_common.processImage( { file: "#product-image-upload-file-video",			name: "#productRelationFile.uploadFileVideo",	type : "M" } );	// 상품이미지 동영상등록 동영상
		_common.processImage( { file: "#product-image-video-upload-image",		name: "#productRelationFile.uploadFileImage",	type : "I" } );	// 상품이미지 동영상등록 대체이미지
		_common.processImage( { file: "#product-description-upload-file-video",	name: "#productRelationFile.uploadFileVideo",	type : "M" } );	// 상품상세 동영상등록 동영상
		_common.processImage( { file: "#product-description-video-upload-image",	name: "#productRelationFile.uploadFileImage",	type : "I", allow : "jpg, jpeg, png, gif, bmp" } );	// 상품상세 동영상등록 대체이미지

		$("[data-toggle]").click(function(e) {
			var type = $(this).data("toggle");
			switch(type) {
			case "product-image-upload-y" :
				$(this).closest("td").find("input[name='productRelationFile.uploadYn']").val("Y");
				$("[data-toggle-item='product-image-upload-y']").show();	// 상품이미지 직접업로드
				$("[data-toggle-item='product-image-upload-n']").hide();	// 상품이미지 URL입력
				$("input[type='text'][data-toggle-item='product-image-upload-n']").val("");	// 기존 입력데이터 삭제
				break;
			case "product-image-upload-n" :
				$(this).closest("td").find("input[name='productRelationFile.uploadYn']").val("N");
				$("[data-toggle-item='product-image-upload-y']").hide();	// 상품이미지 직접업로드
				$("[data-toggle-item='product-image-upload-n']").show();	// 상품이미지 URL입력
				$("input[type='text'][data-toggle-item='product-image-upload-n']").val("");	// 기존 입력데이터 삭제
				break;
			case "product-description-upload-y" :
				$(this).closest("td").find("input[name='productRelationFile.uploadYn']").val("Y");
				$("[data-toggle-item='product-description-upload-y']").show();	// 상품설명 직접업로드
				$("[data-toggle-item='product-description-upload-n']").hide();	// 상품설명 URL입력
				$("input[type='text'][data-toggle-item='product-description-upload-n']").val("");	// 기존 입력데이터 삭제
				break;
			case "product-description-upload-n" :
				$(this).closest("td").find("input[name='productRelationFile.uploadYn']").val("N");
				$("[data-toggle-item='product-description-upload-y']").hide();	// 상품설명 직접업로드
				$("[data-toggle-item='product-description-upload-n']").show();	// 상품설명 URL입력
				$("input[type='text'][data-toggle-item='product-description-upload-n']").val("");	// 기존 입력데이터 삭제
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			}
		});

		// 버튼 이벤트
		$(document).on("click", "[data-button]", function(e) {
			var type = $(this).data("button");
			switch(type) {
			case "save" :
				// 저장
				_object.regist.run();
				break;
			case "temporary" :
				// 임시저장
				$("#type").val("temporary");
				_object.regist.run();
				break;
			case "remove-elements" :
				// 전시 카테고리 선택삭제
				var target = $(this).data("target");
				var selector = "#" + target;
				var removeRowElements = $(selector).find("input[type='checkbox']:checked").closest("tr");

				var flag = true;
				$.each(removeRowElements, function(i, v) {
					if($(v).find("[name='stdrCtgrNo']").is(":checked")) {
						alert("기준 전시카테고리는 삭제할 수 없습니다.");
						flag = false;
					}
				});

				if(flag) {
					$(selector).find("input[type='checkbox']:checked").closest("tr").remove();
				}

				break;
			case "add-element" :
				// 전시 카테고리 추가
				//var selectedLastDisplayCategoryNo = $(".dp-category-select").not(":disabled").last().val();
				//if(_common.isNotEmpty(selectedLastDisplayCategoryNo)) {

				/**
				 * 2020.01.30 상품 전시 카테고리 validate 변경
				 */
				var depthName = "";
				var categorySelectYn = true;
				var categoryChnnlNo = $("#display-category-channel").val();
				var maxCtgrLevel = 0;

				// Const.java 에서 내린 채널별 카테고리 MAX LEVEL
				if( categoryChnnlNo == abc.consts.SITE_CHNN_ART ){

					maxCtgrLevel = _object.MAX_CTGR_LEVEL_ART;

				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_ABC ){

					maxCtgrLevel = _object.MAX_CTGR_LEVEL_ABC;

				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_GS ){

					maxCtgrLevel = _object.MAX_CTGR_LEVEL_GS;

				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_OTS ){

					maxCtgrLevel = _object.MAX_CTGR_LEVEL_OTS;

				} else if( categoryChnnlNo == abc.consts.SITE_CHNN_KIDS ){

					maxCtgrLevel = _object.MAX_CTGR_LEVEL_KIDS; // 미정
				}

				$(".dp-category-select").each(function(i,v){
					var selectOptn = $(v).val();
					if(abc.text.isBlank(selectOptn)){
						var classArray = $(v).attr('class').split(' ');
						depthName = classArray[2];
						var ctgrLevel = Number(depthName.substring(0,1));
						if(ctgrLevel <= maxCtgrLevel){
							categorySelectYn = false;
							return false;
						}
					}
				});
				console.log("depthName : " + depthName);
				console.log("categorySelectYn : " + categorySelectYn);
				if(categorySelectYn == false) {
					alert("전시 카테고리를 선택해주세요.");
					$("." + depthName).focus();
					return;
				}

				if(categorySelectYn == true) {

					// 등록된 전시카테고리와 중복 비교
					if($("#display-category-area").find("tr").find("[name='displayCategoryList.ctgrNo'][value='" + $("select[name='ctgrNo']").val() + "']").length > 0) {
						alert("이미 등록되어 있는 전시 카테고리입니다.");
						return;
					}

					// 데이터 없음 메시지 제거
					$("#display-category-area").find("[data-empty='display-category']").remove();

					// 전시 카테고리 값 초기화
					$(".dp-category-select").attr("selected", "selected");

					var target = $(this).data("target");
					var param = {
						siteNo	: isMmnyPrdt === true ? $("#display-category-channel>option:selected").data("site-no") : $("#display-category-channel").data("site-no"),
						chnnlNo	: $("#display-category-channel").val(),
						ctgrNo	: $("select[name='ctgrNo']").val(),
						mmnyPrdtYn : $("#mmny-prdt-yn").val(), // 2019-12-30 업체 상품 여부 추가
						stdCtgrNo :  $("select[name='stdCtgrNo']").val(), // 2019-12-30 업체 상품 여부 추가
					};

					$.ajax({
						url				: "/noacl/display/display-category/list",
						method			: "POST",
						data			: param
					}).done(function(data) {
						var template = $("#display-category-template").clone();

						template = $(template.html());
						template = $(template);

						template.find("#display-category-checkbox").attr("id", "display-category-checkbox" + data.ctgrNo);	// 체크박스 아이디 변경
						template.find("label[for='display-category-checkbox']").attr("for", "display-category-checkbox" + data.ctgrNo);	// 레에블 아이디 변경

						template.find("[data-template=display-category-channel-name]").text(data.chnnlName);	// 채널이름
						template.find("[data-template=display-category-category-path]").text(data.ctgrNamePath);	// 카테고리 경로
						template.find("[data-template=display-category-ctgr-no]").val(data.ctgrNo);	// 카테고리 번호

						template.find("[data-template=display-category-stdr-ctgr-no-radio]").attr("id", "stdr-ctgr-no" + data.ctgrNo);	// 기준 카테고리 설정정보 중 라디오
						template.find("[data-template=display-category-stdr-ctgr-no-radio]").attr("value", data.ctgrNo);	// 기준 카테고리 설정정보 중 라디오
						template.find("[data-template=display-category-stdr-ctgr-no-label]").attr("for", "stdr-ctgr-no" + data.ctgrNo);	// 기준 카테고리 설정정보 중 레이블

						$("#" + target).append(template);	// 템플릿을 카테고리영역에 추가

						// 첫 데이터인 경우, 추가처리
						if($("#display-category-area").find("tr").length === 1) {
							$("#display-category-area").find("tr").find("[data-template='display-category-stdr-ctgr-no-radio']").attr("checked", true);	// 체크상태 설정
						}

					}).fail(function(data) {
						console.log("선택된 전시 카테고리 조회 실패");
						console.log(data);
					});
				} else {
					alert("전시 카테고리를 선택해 주세요.");
				}
				break;
			case "remove-rows" :
				// 선택삭제
				var sheetName = $(this).data("sheet-name");
				_common.grid.util.removeRows(sheetName);
				break;
			case "add-row" :
				// 행추가
				var sheetName = $(this).data("sheet-name");
				var rowData = null;
				if(isMmnyPrdt === "Y") {
					// 자사. 해당경우 없음.
					rowData = { "mmnyPrdtYn" : "Y" };
				} else {
					// 입점
					rowData = { "mmnyPrdtYn" : "N", "stockVdQty" : 0, "totalOrderQty" : 0, "orderPsbltQty" : 0, "optnAddAmt" : 0, "sortSeq" : 0 };
				}
				_common.grid.util.addRow(sheetName, rowData);
				break;
			case "add-row-from-product" :
				// 상품선택팝업으로부터 행 추가
				var sheetName = $(this).data("sheet-name");
				switch(sheetName) {
				case "cntcPrdtSetupList" :
					// 색상연계상품
					var param = { "searchItemType" : "style-info", "searchItemKeyword" : $("[name='styleInfo']").val() };
					window.abc.productSearchPopup(true, "abc.biz.product.product.detail.callback.addProduct.cntcPrdtSetupList", true, param);
					break;
				case "rltnGoodsSetupList" :
					// 관련용품
					window.abc.productSearchPopup(true, "abc.biz.product.product.detail.callback.addProduct.rltnGoodsSetupList");
					break;
				default :
					console.log("알 수 없는 시트이름 입니다. " + sheetName);
				}
				break;
			case "href" :
				// 이동 링크
				var uri = $(this).data("uri");
				location.href = uri;
				break;
			case "add-product" :
				// 현재 상품정보를 채널변경하여 등록
				_object.resource.isInitializing = true;	// 초기화중 플래그 설정

				var siteNo = $(this).data("site-no");
				var chnnlNo = $(this).data("chnnl-no");
				var chnnlName = $(this).data("chnnl-name");
//				var vndrNo = $(this).data("vndr-no");

				// 상품정보 내 사이트/채널정보 설정
				$("#type").val("save");
				$("input[name='prdtNo']").val("");
				$("input[name='siteNo']").val(siteNo);
				$("input[name='chnnlNo']").val(chnnlNo);
//				$("#vndr-no").val("");	// 업체번호 지움. 2019-10-23.입점정보제공고시조회를 위해 주석처리.

				// 상품옵션조회 인자값(그리드용) 재설정
				$("[name='product-option-form']").find("[name='chnnlNo']").val(chnnlNo);

				// 전시카테고리 설정 작업
				if(isMmnyPrdt === true) {
					$("#display-category-channel").find("[data-removeable='true']").remove();
					var newOption = $("<option>");
					newOption.attr("data-removeable", "true");
					newOption.val(chnnlNo);
					newOption.text(chnnlName);
					$("#display-category-channel").append(newOption);

					//ots의 경우 카테고리 채널 첫번째 art disabled 처리
					if(siteNo == '10001'){
						$("#display-category-channel option").eq(0).hide();
						$("#display-category-channel option").eq(1).prop('selected', true);
					}else{
						$("#display-category-channel option").eq(0).show();
						$("#display-category-channel option").eq(0).prop('selected', true);
					}

				} else {
					$("#display-category-channel").attr({ "data-site-no" : siteNo, "value" : chnnlNo });
				}

				// 전시카테고리용 채널조회 변경

				// 등록된 전시채널 초기화
				$("#display-category-area>tr").remove();
				$("#display-category-area").append($("<tr data-empty=\"display-category\"><td class=\"text-center\" colspan=\"4\"><span>등록된 전시 카테고리가 없습니다.</span></td></tr>"));

				// 자사상품인 경우 옵션정보 중간계 재조회
				if(_common.isNotEmpty($("input[name='newErpProductYn']").val()) && "Y" === $("input[name='newErpProductYn']").val()) {
					_object.getList("prdtOptnList");
					// 전시카테고리 이벤트 설정
				}

				// 전시카테고리 이벤트 설정
				if ("Y" === $("input[name='mmnyPrdtYn']").val()) {
					$('.dp-category-select.chnnl-no').trigger('change');
				}
				// 첨부이미지 초기화
				$.each($("[name='productRelationFile.uploadFileImage']"), function(i, v) {
					var me =$(v);
					me.closest("div.file-wrap").find("input[type='hidden'][name='productRelationFile.uploadFileNameImage']").val("");
					me.closest("div.file-wrap").find("a").text("");
					me.closest("div.file-wrap").find(".btn-file-del").hide();
					me.closest("div.file-wrap").find("input[type='text'][name='productRelationFile.altrnText']").val("");
					me.closest("div.file-wrap").find("img").remove();
				});
				// 상품메인이미지 대체텍스트 설정
				$("#product-rep-img-area").find("input[type='text'][name='productRelationFile.altrnText']").val("MAIN");
				// 3D/동영상 내용 초기화
				$("[name='productRelationFile.imageName']").val("");
				$("[name='productRelationFile.linkInfo']").val("");
				$("[name='productRelationFile.movieUrl']").val("");
				// 동영상등록 라디오버튼 초기화
				$("#product-image-upload-yn-n").trigger("click");
				$("#product-description-upload-yn-n").trigger("click");
				// 추가이미지 제거
				$('#append-image-tmpl').find("tr").remove();
				// 정렬순서 초기화
				$("#tab-image").find("input[name='productRelationFile.sortSeq']").val("");
				$("#tab-description").find("input[name='productRelationFile.sortSeq']").val("");
				// 상품메인이미지 정렬순서 1로 설정
				$("#product-rep-img-area").find("input[name='productRelationFile.sortSeq']").val("1");

				// 브랜드 비주얼 재조회
				_object.brand.visual.get();

				// 그리드 상태 I로 일괄 변경
				_common.grid.util.updateCellValue("cntcPrdtSetupList", "status", "I");
				_common.grid.util.updateCellValue("rltnGoodsSetupList", "status", "I");
				_common.grid.util.updateCellValue("prdtOptnList", "status", "I");

				_object.promotion.init();	// 적용 프로모션 조회
				_object.coupon.init();	// 적용 쿠폰 조회

				$("[data-channel-area]").closest("td").removeClass("td-spot");
				$(this).closest("td").addClass("td-spot");

				_object.resource.isInitializing = false;	// 초기화중 플래그 해제
				break;
			case "return-self" :
				// 상품 추가를 누른 뒤 현재채널상품으로 다시 돌아온 경우
				location.reload();
			case "regist-by-copy" :
				// PO 복사등록
				$("#type").val("save");	// 모드
				$("input[name='prdtNo']").val("");	// 상품번호
				$("input[name='vndrPrdtNoText']").val("");	// 상품코드

				// 첨부이미지 초기화
				$.each($("[name='productRelationFile.uploadFileImage']"), function(i, v) {
					$(v).closest("div.file-wrap").find("input[type='hidden'][name='productRelationFile.uploadFileNameImage']").val("");
					$(v).closest("div.file-wrap").find("a").text("");
					$(v).closest("div.file-wrap").find(".btn-file-del").hide();
					$(v).closest("div.file-wrap").find("input[type='text'][name='productRelationFile.altrnText']").val("");
					$(v).closest("div.file-wrap").find("img").remove();
				});
				// 상품메인이미지 대체텍스트 설정
				$("#product-rep-img-area").find("input[type='text'][name='productRelationFile.altrnText']").val("MAIN");
				// 3D/동영상 내용 초기화
				$("[name='productRelationFile.imageName']").val("");
				$("[name='productRelationFile.linkInfo']").val("");
				$("[name='productRelationFile.movieUrl']").val("");
				// 동영상등록 라디오버튼 초기화
				$("#product-image-upload-yn-n").trigger("click");
				$("#product-description-upload-yn-n").trigger("click");
				// 추가이미지 제거
				$('#append-image-tmpl').find("tr").remove();
				// 정렬순서 초기화
				$("#tab-image").find("input[name='productRelationFile.sortSeq']").val("");
				$("#tab-description").find("input[name='productRelationFile.sortSeq']").val("");
				// 상품메인이미지 정렬순서 1로 설정
				$("#product-rep-img-area").find("input[name='productRelationFile.sortSeq']").val("1");

				// 그리드 상태 I로 일괄 변경
				_common.grid.util.updateCellValue("cntcPrdtSetupList", "status", "I");
				_common.grid.util.updateCellValue("rltnGoodsSetupList", "status", "I");
				_common.grid.util.updateCellValue("prdtOptnList", "status", "I");

				// 브랜드 비주얼 재조회
				_object.brand.visual.get();

				_object.promotion.init();	// 적용 프로모션 조회
				_object.coupon.init();	// 적용 쿠폰 조회

				break;
			case "async-save-admin-memo":
				// 관리자 메모 저장 이벤트
				_object.memo.saveMemo();
				break;
			case "remove-memo" :
				// 관리자 메모 삭제
				var prdtMemoSeq = $(this).data("prdt-memo-seq");
				if(prdtMemoSeq == null || prdtMemoSeq == "") return false;
				var cfm = confirm("삭제 하시겠습니까?");
				removeMemoElement = $(this).parents("li").eq(0);
				if(cfm) _object.memo.removeMemo(prdtMemoSeq, removeMemoElement);
				break;
			case "close" :
				window.self.close();
				break;
			case "batch-update-disp-yn" :
				// 재고/옵션정보 중 옵션 전시여부 일괄설정
				var source = $(this).data("source");	// 일괄설정할 전시유형 값
				var checkedRows = prdtOptnList.FindCheckedRow("checked").split('|');	// 체크된

				if(checkedRows[0] === "") {
					// 선택된 상품옵션 없음
					alert("상품 옵션을 선택해주세요.");
					$("[data-button-source='" + source + "']").focus();

				} else {
					// 선택된 상품옵션 있음
					switch($("[data-button-source='" + source + "']").val()) {
					case "Y" :
						// 전시함으로 일괄설정하는 경우
						for(var i=0; i<checkedRows.length; i++) {
							prdtOptnList.SetCellValue(checkedRows[i], "useYn", "Y");
						}
						break;
					case "N" :
						// 전시안함으로 일괄설정하는 경우
						for(var i=0; i<checkedRows.length; i++) {
							prdtOptnList.SetCellValue(checkedRows[i], "useYn", "N");
						}
						break;
					default :
						// 전시유형이 올바르지 않은 경우
						alert("전시유형을 선택해 주세요.");
						$("[data-button-source='" + source + "']").focus();
					}
					break;
				}
			case "batch-update-sell-stat" :
					// 재고/옵션정보 중 옵션 판매상태 일괄설정
					var source = $(this).data("source");	// 일괄설정할 전시유형 값
					var checkedRows = prdtOptnList.FindCheckedRow("checked").split('|');	// 체크된

					if(checkedRows[0] === "") {
						// 선택된 상품옵션 없음
						alert("상품 옵션을 선택해주세요.");
						$("[data-button-source='" + source + "']").focus();

					} else {
						// 선택된 상품옵션 있음
						switch($("[data-button-source='" + source + "']").val()) {
						case "10000" :
							// 판매대기로 일괄설정하는 경우
							for(var i=0; i<checkedRows.length; i++) {
								prdtOptnList.SetCellValue(checkedRows[i], "sellStatCode", "10000");
							}
							break;
						case "10001" :
							// 판매중으로 일괄설정하는 경우
							for(var i=0; i<checkedRows.length; i++) {
								prdtOptnList.SetCellValue(checkedRows[i], "sellStatCode", "10001");
							}
							break;
						case "10002" :
							// 일시품절로 일괄설정하는 경우
							for(var i=0; i<checkedRows.length; i++) {
								prdtOptnList.SetCellValue(checkedRows[i], "sellStatCode", "10002");
							}
							break;
						case "10003" :
							// 판매종료로 일괄설정하는 경우
							for(var i=0; i<checkedRows.length; i++) {
								prdtOptnList.SetCellValue(checkedRows[i], "sellStatCode", "10003");
							}
							break;
						default :
							// 전시유형이 올바르지 않은 경우
							alert("판매상태를 선택해주세요.");
							$("[data-button-source='" + source + "']").focus();
						}
						break;
					}
			default :
				console.log("잘못된 유형입니다. " + type);
				break;
			}
		});

		// 팝업 버튼 이벤트
		$("[data-button-popup]").click(function(e) {
			e.preventDefault();
			var type = $(this).data("button-popup");
			switch(type) {
			case "find-brand" :
				// 브랜드 찾기 팝업
				_object.resource.popup.brand = window.abc.brandSearchPopup(false, "abc.biz.product.product.detail.callback.findBrand");
				break;
			case "search-price-history" :
				// 가격변경이력조회
				if($("#prdt-no").val() !== "undefined") {
					abc.open.popup({
						url 	:	"/product/product/history",
						winname :	"productPriceHistory",
						method	: 	"get",
						title 	:	encodeURIComponent("가격변경 이력 조회"),
						width 	:	1020,
						height	:	640,
						params	:	{ "name" : "가격변경 이력 목록", "type" : "price", "prdtNo" : $("#prdt-no").val(), "mmnyPrdtYn" : $("#mmny-prdt-yn").val() }
					});
				} else {
					console.log("조회된 상품이 없습니다.");
				}
				break;
			case "search-approvement-history" :
				// 승인이력조회
				if($("#prdt-no").val() !== "undefined") {
					abc.open.popup({
						url 	:	"/product/product/history",
						winname :	"productApprovalHistory",
						method	: 	"get",
						title 	:	encodeURIComponent("승인 이력 조회"),
						width 	:	1020,
						height	:	640,
						params	:	{ "name" : "상품승인 이력", "type" : isMmnyPrdt === true ? "approval-bo" : "approval-po", "prdtNo" : $("#prdt-no").val() }
					});
				} else {
					console.log("조회된 상품이 없습니다.");
				}
				break;
			case "search-change-history" :
				// 변경이력조회
				if($("#prdt-no").val() !== "undefined") {
					abc.open.popup({
						url 	:	"/product/product/history",
						winname :	"productChangeHistory",
						method	: 	"get",
						title 	:	encodeURIComponent("상품정보 변경 이력 조회"),
						width 	:	1020,
						height	:	640,
						params	:	{ "name" : "상품정보 변경 이력", "type" : "product", "prdtNo" : $("#prdt-no").val() }
					});
				} else {
					console.log("조회된 상품이 없습니다.");
				}
				break;
			case "search-display-area" :
				// 상품전시영역확인
				if($("#prdt-no").val() !== "undefined") {
					abc.open.popup({
						url 	:	"/product/product/exhibition",
						winname :	"productExhibition",
						method	: 	"get",
						title 	:	encodeURIComponent("상품전시영역 확인"),
						width 	:	1020,
						height	:	900,
						params	:	{ "name" : "상품전시 페이지 정보", "prdtNo" : $("#prdt-no").val() }
					});
				} else {
					console.log("조회된 상품이 없습니다.");
				}
				break;
			case "preview-size-chart" :
				// 사이즈가이드 미리보기
				var selectedData = $("#size-chart-list").find("option:selected").not("[data-removeable='false']").data();
				console.log(selectedData);
				if(_common.isNotEmpty(selectedData)) {
					var popup = window.open("about:blank", "preview-size-chart", "width=500,height=500");
					popup.document.write("<html><title>미리보기</title><style>body{ margin:0; padding:0; } img{ display:block; margin:auto; }</style><body><img src='" + selectedData.imageUrl + "' alt='" + selectedData.altrnText + "' onClick='javascript:self.close();'/></body></html>")
				} else {
					alert("사이즈가이드를 선택하세요.");
				}
				break;
			default :
				console.log("잘못된 유형입니다. " + type);
			}
		});

		// 체크박스 이벤트
		$(document).on("click", "[data-checkbox]", function(e) {
			var type = $(this).data("checkbox");
			switch(type) {
			case "check-all" :
				// 전체선택
				var selector = "[data-target=" + $(this).data("target") + "]";
				var isChecked = $(this).is(":checked");
				$(selector).prop("checked", isChecked);

				$(selector).each(function(i,v){
					if($(v).prop("checked")) {
						$(v).closest("tr").find("[name='productAddInfo.prdtAddInfo']").val("상품정보 별도표시");
					} else {
						$(v).closest("tr").find("[name='productAddInfo.prdtAddInfo']").val($(v).closest("tr").find("[name='productAddInfo.prdtAddInfo']").attr("placeholder"));
					}
				});

				break;
			case "product-add-info-display-category" :
				var cnt = 0;
				var selector = "[data-target=" + $(this).data("target") + "]";

				// 체크박스 체크해제된 수 체크
				$(selector).each(function(i,v){
					if($(this).prop("checked")) {
						cnt++;
					}
				});
				// 전체 체크 박스 해제
				if(cnt == 1){
					$("#display-category-checkbox-all").prop("checked", false);
				}

				break;
			case "product-add-info-display-separate" :
				var cnt = 0;
				var selector = "[data-target=" + $(this).data("target") + "]";

				if($(this).prop("checked")) {
					$(this).closest("tr").find("[name='productAddInfo.prdtAddInfo']").val("상품정보 별도표시");
				} else {
					$(this).closest("tr").find("[name='productAddInfo.prdtAddInfo']").val($(this).closest("tr").find("[name='productAddInfo.prdtAddInfo']").attr("placeholder"));
				}

				// 체크박스 체크해제된 수 체크
				$(selector).each(function(i,v){
					if($(this).prop("checked")) {
						cnt++;
					}
				});
				// 전체 체크 박스 해제
				if(cnt == 1){
					$("#display-separate-all").prop("checked", false);
				}

				break;
			default :
				console.log("잘못된 유형입니다. " + type);
				break;
			}
		});

		// 체크박스 이벤트
		$("[data-radio]").click(function(e) {
			var type = $(this).data("radio");
			switch(type) {
			case "buy-limit" :
				// 구매수량 제한여부
				if($(this).val() === "Y") {
					$("#min-buy-psblt-qty").val("1");	// 최소 구매수량 기본값 설정
					$("#max-buy-psblt-qty").val("1");	// 최대 구매수량 기본값 설정
					$("#day-max-buy-psblt-qty").val("1");	// 1일 최대 구매수량 기본값 설정
					$("#product-buy-limit-yn-n").show();
				} else {
					$("#product-buy-limit-yn-n").hide();
				}
				break;
			case "stock-management" :
				// PO 재고관리여부 토글에 따른 옵션재고 잠금 설정
				if($(this).val() ==="Y") {
					prdtOptnList.SetColEditable("stockVdQty", 1);
				} else {
					prdtOptnList.SetColEditable("stockVdQty", 0);
				}
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			break;
			}
		});

		// 상품 가격 변경에 따른 할인금액 계산
		$("[data-focusout]").focusout(function() {
			var type = $(this).data("focusout");
			switch(type) {
			case "online-discount-rate" :
				$(this).val(_common.number.setDecoratePrice($(this).val()));	// 입력된 값 콤마 및 원단위 재설정
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			}
		});

		/**
		 * 출시일 여부 체크상태에 따른 이벤트
		 */
		$('#relis-todo-yn-y').click(function(e) {
			if($(this).is(":checked")) {
				// 출시일 체크된 경우
				$("[data-toggle='relis-todo-dtm']").show();
			} else {
				$("[data-toggle='relis-todo-dtm']").hide();
				$('#relis-todo-dtm-date').val('');
				$('#relis-todo-dtm-hour').find("option:first").attr("selected", true);
				$('#relis-todo-dtm-minute').find("option:first").attr("selected", true);
			}
		});

		/**
		 * 예약상품 여부 체크상태에 따른 이벤트
		 */
		$("#rsv-prdt-yn-y").click(function(e) {
			if($(this).is(":checked")) {
				alert("예약상품의 경우 재고는 온라인재고만 관리됩니다.\n재고연동여부를 다시 확인해주세요.");

				// 예약상품 체크된 경우
				$("[data-toggle='rsv-dlvy-ymd']").show();

				// 재고연동여부 연동안함으로 강제설정
				if($("#mmny-prdt-yn").val() === "Y") {
					$("#stock-intgr-yn-n").attr("checked", true);
					$("#stock-un-intgr-rsn-code").find("[value='10006']").attr("selected", true);
					$("[name='stockIntgrYn']").attr("onclick", "return false;");
					$("#stock-un-intgr-rsn-code").attr({ "onfocus" : "this.initialSelect = this.selectedIndex;", "onchange" : "this.selectedIndex = this.initialSelect;" });
				}
			} else {
				$("#rsv-dlvy-ymd").val("");
				$("[data-toggle='rsv-dlvy-ymd']").hide();

				// 재고연동여부 연동안함 해제
				if($("#mmny-prdt-yn").val() === "Y") {
					$("[name='stockIntgrYn']").removeAttr("onclick");
					$("#stock-un-intgr-rsn-code").removeAttr("onfocus").removeAttr("onchange");
					$("[name='stockIntgrYn'][value='" + $("#default-stock-intgr-yn").val() + "']").attr("checked", true);
					$("#stock-un-intgr-rsn-code").val($("#default-stock-un-untgr-rsn-code").val());
				}
			}
		});

		// 예약상품 예상출고일 값 오늘 이후 설정 가능
		$("#rsv-dlvy-ymd").change(function(e) {
			if(abc.biz.product.utils.isNotEmpty($(this).val())) {
				var rsvDlvyDate = new Date($(this).val());
				var todayDate = new Date();
				todayDate.setHours(0);
				todayDate.setMinutes(0);
				todayDate.setSeconds(0);
				todayDate.setMilliseconds(0);
				if(todayDate.getTime() > rsvDlvyDate.getTime()) {
					// 예상출고일이 오늘 이전인 경우
					alert("예상출고일은 과거날짜를 입력할 수 없습니다.");
					$(this).val(todayDate.format("yyyy.MM.dd"));
				}
			}
		});

		// 상품정보고시 이벤트
		_object.noticeInfo.event();

		// 제품안전인증정보 이벤트
		_object.prdtSafeType.event();

		/**
		 * 가격란 포커스 시 숫자로 설정
		 */
		$("[data-focusout='online-discount-rate']").focus(function() {
			$(this).val(_common.number.setUndecoratePrice($(this).val()));
		});

		// 상품이미지탭 내 추가이미지 추가 이벤트
		$('#add-image-tmpl').on('click', function(){

			var tmpl = document.templateOneRoot("#image-tmpl");
			var addImageCount = $('.add-image-layer').length;

			if(addImageCount > 8) {
				alert('최대 9개까지 등록 가능합니다.');
				return false;
			}
			addImageCount++;
			tmpl.find("span[data-add-image-number='no']").text(addImageCount); // 추가이미지 번호 설정
			tmpl.find("input[name='productRelationFile.prdtRltnFileSeq']").val(addImageCount + 10);	// 추가이미지 순번 설정
			tmpl.find('#image-checkbox').attr('id', 'image-checkbox'+addImageCount).next().attr('for', 'image-checkbox'+addImageCount);
			tmpl.find('#image-file-').attr('id', 'image-file-'+addImageCount).next().attr('for', 'image-file-'+addImageCount);
			tmpl.find("[name='productRelationFile.altrnText']").val("SUB " + addImageCount);	// 대체텍스트 기본값 설정

			// 상품이미지탭 내 정렬순번 검증
			var nextSortSeq = 0;
			$.each($("#tab-image").find("[name='productRelationFile.sortSeq']"), function(i, v) {
				var sortSeq = $(v).val();
				if(sortSeq != undefined && sortSeq != "" && $.isNumeric(sortSeq)) {
					if(parseInt(sortSeq) > nextSortSeq) {
						nextSortSeq = parseInt(sortSeq);
					}
				}
			});
			tmpl.find("[name='productRelationFile.sortSeq']").val(++nextSortSeq);	// 정렬순서 자동기입

			$('#append-image-tmpl').append(tmpl);

			//initImage('#image-file-'+addImageCount);
			abc.biz.product.utils.processImage( { file: "#image-file-" + addImageCount, name: "#image-name-" + addImageCount, type : "I" } );
		})

		// 상품이미지탭 내 추가이미지 삭제 이벤트
		$('#remove-image-tmpl').on('click', function(){
			var imageLayer = $('[id^=image-checkbox]:checked').parents('.add-image-layer');

			if(imageLayer.length == 0){
				alert('삭제할 이미지를 선택해주세요.');
				return false;
			}

			var cfm = confirm('삭제하시겠습니까?');
			if(cfm)	{
				imageLayer.remove();

				// ID 및 번호 재산정
				var root = $("#append-image-tmpl>tr");
				var imageStartIdx = 1;
				var imageStartSeq = 11;

				$.each(root, function(i, v) {
					$(v).find("span[data-add-image-number='no']").text(imageStartIdx++);
					$(v).find("input[name='productRelationFile.prdtRltnFileSeq']").val(imageStartSeq++);
				});
			}
		});

		// 판매상태 이벤트
		$('input[name="sellStatCode"]').off().on('click', function(e) {
			if('10000' == $(this).val()) {
				// 판매대기
				$('#relis-todo-yn-y').prop('disabled', false);
			} else {
				// 판매상태 변경 시 display 처리
				if($('#relis-todo-yn-y').is(":checked")) {
					$('#relis-todo-yn-y').click();
				}
				$('#relis-todo-yn-y').prop('disabled', true);
			}
		});

		// 재고연동여부에 따른 재고미통합 사유코드 제어
		$("[name='stockIntgrYn']").click(function() {
			if($(this).attr("onclick") === undefined) {
				// readonly 효과를 위한 onclick 이므로 onclick속성이 없는 경우에만 제어
				switch($(this).val()) {
				case "Y" :
					$("#stock-un-intgr-rsn-code").hide();
					$("#stock-un-intgr-rsn-code").val("");
					break;
				case "N" :
					$("#stock-un-intgr-rsn-code").show();
					break;
				}
			} else {
			}
		});
	}

	_object.initView = function() {
		/**
		 * 화면 초기화
		 */

		$("[data-init=hidden]").hide();	// 숨김요소 설정

		// 색상연계상품 설정여부에 대한 row통합 제어
		if($("[name='cntcPrdtSetupYn']:checked").val() === "Y") {
			$("[data-rowspan='cntc-prdt-setup-yn']").attr("rowspan", 2);
		} else {
			$("[data-rowspan='cntc-prdt-setup-yn']").removeAttr("rowspan");
			$("[data-hidden='cntc-prdt-setup-yn']").hide();
		}

		// 관련용품 설정여부에 대한 row통합 제어
		if($("[name='rltnGoodsSetupYn']:checked").val() === "Y") {
			$("[data-rowspan='rltn-goods-setup-yn']").attr("rowspan", 2);
		} else {
			$("[data-rowspan='rltn-goods-setup-yn']").removeAttr("rowspan");
			$("[data-hidden='rltn-goods-setup-yn']").hide();
		}

		// 사이즈가이드 초기설정값에 따른 표시설정
		if($("[name='sizeChartDispYn']:checked").val() === "Y") {
			$("[data-hidden='size-chart-disp-yn']").show();
		} else {
			$("[data-hidden='size-chart-disp-yn']").hide();
		}

		// 효과이미지 중 동영상업로드 유형 첨부에 따른 초기화
		if($("input[name='productImageUploadYn']:checked").val() === "Y") {
			// 상품이미지 동영상업로드에서, URL업로드와 직접업로드 중 직접업로드가 선택된 경우
			$("#product-image-upload-yn").val("Y");
			$("[data-toggle-item='product-image-upload-y']").show();	// 상품이미지 직접업로드
			$("[data-toggle-item='product-image-upload-n']").hide();	// 상품이미지 URL입력
		} else {
			$("#product-image-upload-yn").val("N");
			$("[data-toggle-item='product-image-upload-y']").hide();	// 상품이미지 직접업로드
			$("[data-toggle-item='product-image-upload-n']").show();	// 상품이미지 URL입력
		}
		if($("input[name='productDescriptionUploadYn']:checked").val() === "Y") {
			// 상품상세 동영상업로드에서, URL업로드와 직접업로드 중 직접업로드가 선택된 경우
			$("#product-description-upload-yn").val("Y");
			$("[data-toggle-item='product-description-upload-y']").show();	// 상품설명 직접업로드
			$("[data-toggle-item='product-description-upload-n']").hide();	// 상품설명 URL입력
		} else {
			$("#product-description-upload-yn").val("N");
			$("[data-toggle-item='product-description-upload-y']").hide();	// 상품설명 직접업로드
			$("[data-toggle-item='product-description-upload-n']").show();	// 상품설명 URL입력
		}

		// 예약상품여부에 따른 예상출고일 초기화
		if($("#rsv-prdt-yn-y").is(":checked")) {
			$("[data-toggle='rsv-dlvy-ymd']").show();
		} else {
			$("[data-toggle='rsv-dlvy-ymd']").hide();
		}

		// 출시일여부에 따른 출시일 초기화
		if($('#relis-todo-yn-y').is(":checked")) {
			$("[data-toggle='relis-todo-dtm']").show();
		} else {
			$("[data-toggle='relis-todo-dtm']").hide();
		}

		// 판매상태에 따른 출시일 여부 제어
		if(!$('input[name="sellStatCode"][value="10000"]').is(":checked")) {
			$('#relis-todo-yn-y').prop('disabled', true);
		}

		// PO 재고관리여부 토글에 따른 옵션재고 잠금 설정
		if(!isMmnyPrdt) {
			if($("input[name='stockMgmtYn']:checked").val() ==="Y") {
				prdtOptnList.SetColEditable("stockVdAmt", 1);
			} else {
				prdtOptnList.SetColEditable("stockVdAmt", 0);
			}
		}

		// 구매수량제한여부 초기 토글
		if($("input[name='buyLimitYn']:checked").val() === "Y") {
			$("#product-buy-limit-yn-n").show();	// 최소/최대/1일 구매수량 제한 보이기
		} else {
			$("#product-buy-limit-yn-n").hide();	// 최소/최대/1일 구매수량 제한 숨기기
		}

		_object.brand.visual.get();					// 브랜드 비주얼 조회
		_object.brand.validate.available3dView();	// 3d view 사용가능여부 검사

		_object.vendor.get();

		// 예약상품여부에 따른 숨김설정
		if($("#mmny-prdt-yn").val() === "Y") {
			if($("#rsv-prdt-yn-y").is(":checked")) {
				$("#stock-un-intgr-rsn-code").show();

				// 재고연동여부 연동안함으로 강제설정
				$("[name='stockIntgrYn']").attr("onclick", "return false;");
				$("#stock-un-intgr-rsn-code").attr({ "onfocus" : "this.initialSelect = this.selectedIndex;", "onchange" : "this.selectedIndex = this.initialSelect;" });
			} else if($("#stock-intgr-yn-y").is(":checked")) {
				$("#stock-un-intgr-rsn-code").hide();
			}
		}

	}

	_object.initData = function() {
		/**
		 * 초기 데이터 설정
		 */

		// 표준 카테고리 기존 데이터를 이용한 설정 이벤트
//		if(_common.isNotEmpty($("#std-ctgr-no").val())) {
			_common.selectStandardCategory($("#std-ctgr-no").val());
//		} else {
//			abc.biz.display.common.setStdCategoryEvent();
//		}

		// 사이즈가이드 목록 선택
		_object.sizeChart.search(true);

		// 상품정보고시
		_object.noticeInfo.init();

		// 제품안전인증정보
		_object.prdtSafeType.init();

		// 취급주의사항 조회. 표카 이벤트에서 초기화됨.
//		_object.precaution.init();

		_object.promotion.init();		// 적용 프로모션 조회
		_object.coupon.init();			// 적용 쿠폰 조회
	}

	_object.sizeChart = {
		/**
		 * 사이즈가이드
		 */

		data : {},
		parameter : {
			stdCtgrNo	: "",
			themeType	: "",
			brandNo		: ""
		},
		gather : function() {
			_object.sizeChart.parameter.stdCtgrNo = $("[name='stdCtgrNo']").val() === "" ? $("#std-ctgr-no").val() : $("[name='stdCtgrNo']").val();
			_object.sizeChart.parameter.themeType = $("[name='genderGbnCode']:checked").val();
			_object.sizeChart.parameter.brandNo = $("#brand-no").val();

			switch(_object.sizeChart.parameter.themeType) {
			case "10000" :	// 남성
				_object.sizeChart.parameter.themeType = "M";
				break;
			case "10001" :	// 여성
				_object.sizeChart.parameter.themeType = "F";
				break;
			case "10002" :	// KIDS
				_object.sizeChart.parameter.themeType = "C";
				break;
			case "10003" :	// 공통
				_object.sizeChart.parameter.themeType = "A";
				break;
			default :
				console.log("알 수 없는 유형입니다. themeType : " + _object.sizeChart.parameter.themeType);
			}
		},
		search : function(isInitial) {
			// 사이즈가이드 검색
			if($("#size-chart-disp-yn-y").is(":checked")) {
				_object.sizeChart.gather();
				_object.sizeChart.get();
			}
		},
		get : function() {
			$.ajax({
				url : "/noacl/product/sizechart/search",
				method : "GET",
				data : _object.sizeChart.parameter
			}).done(function(data) {
				_object.sizeChart.data = data;
				$("#size-chart-list").find("[data-removeable!='false']").remove();
				for(var i=0 ; i<data.length ; i++) {
					var option = $("<option>");
					option.val(data[i].sizeChartSeq);
					option.text(data[i].sizeChartName);
					option.attr( {
						"data-size-chart-seq" : data[i].sizeChartSeq,
						"data-image-url" : data[i].imageUrl,
						"data-altrn-text" : data[i].altrnText
					});

					if(/*isInitial && */_common.isNotEmpty($("#size-chart-seq").val())) {
						// 진입 초기 조회 시, 기존 값이 있는 경우 선택 설정
						if($("#size-chart-seq").val() == data[i].sizeChartSeq) {
							option.attr("selected", "selected");
						}
					}

					$("#size-chart-list").append(option);
				}
			}).fail(function(data) {
				console.log("사이즈가이드 조회 실패");
				console.log(data);
			});
		},
		enable : function() {
			$("[data-hidden='size-chart-disp-yn']").show();
			_object.sizeChart.search(false);
		},
		disable : function() {
			$("[data-hidden='size-chart-disp-yn']").hide();
			$("[name='sizeChartDispYn'][value='N']").prop("checked", true);
			$("#size-chart-list").val("");	// 선택 값 초기화
			$("#size-chart-seq").val("");	// 설정 값 초기화
		},
		event : function() {
			// 표준카테고리/브랜드 변경에 따른 사이즈가이드 초기화 이벤트
			$(document).on("change paste", "#std-ctgr-no-depth-1, [name='genderGbnCode'], #brand-no", function(e) {
				if($("[name='sizeChartDispYn'][value='Y']").is(":checked")) {
					// 사이즈가이드를 사용하는 경우
					if(_object.resource.popup.brand != null && _object.resource.popup.brand != undefined) {
						_object.resource.popup.brand.alert("사이즈 가이드가 초기화됩니다.");
						_object.resource.popup.brand = null;
					} else {
						alert("사이즈 가이드가 초기화됩니다.");
					}
					_object.sizeChart.disable();
				}
				_object.brand.visual.get();
				_object.brand.validate.available3dView();
				_object.vendor.get();
			});

			// 사이즈가이드 이벤트
			$("[name='sizeChartDispYn']").click(function(e) {
				if($(this).val() === "Y") {
					_object.sizeChart.enable();
				} else {
					_object.sizeChart.disable();
				}
			});
		}
	};

	/**
	 * 상품정보고시
	 */
	_object.noticeInfo = {
		init : function() {
			/**
			 * 상품정보고시 초기화
			 */
			if($("#prdt-no").val() === "") {
				$("select[name=itemCode]").change();
			}

			// 품목명 선택
			$.ajax({
				type : "POST",
				url : "/product/product/notice/info/list/itemCode"
			}).done(function(data) {

				var defaultValue = $("select[name=itemCode]").data("default-value");

				$.each(data, function(i,v) {
					$option = $("<option>").text(v.codeDtlName).val(v.codeDtlNo);

					if(defaultValue == v.codeDtlNo) {
						$option.attr("selected", "selected");
					}

					$("select[name=itemCode]").append($option);
				});
				
				// 제공고시가 존재하지 않을경우 해당 품목코드로 임의생성되게 수정
				if(!abc.text.allNull($("#item-code").data("default-value")) 
						&& $("#add-info-area").find(".info-notice-row").length == 0) {
					$("#item-code").trigger("change");
				}
			}).fail(function(e) {
				console.log("e :" + e);
			});

//			// 확인대상 품목
//			$.ajax({
//				type : "POST",
//				url : "/product/product/notice/info/list/prdtSafeTypeCode"
//			}).done(function(data) {
//				console.log(data);
//				$.each(data, function(i,v) {
//					$option = $("<option>").text(v.codeDtlName).val(v.codeDtlNo);
//					$("select.prdtSafeTypeCode").append($option);
//				});
//			}).fail(function(e) {
//				console.log("e :" + e);
//			});
		},
		event : function() {///// 표카 변경 이벤트
			$("select[name=itemCode]").on("change", function() {
				itemCode = $("select[name=itemCode] option:selected").val();
				param = { "itemCode" : itemCode, "mmnyPrdtYn" : $("#mmny-prdt-yn").val(), "vndrNo" : $("#vndr-no").val() };

				$.ajax({
					type : "POST",
					url : "/noacl/product/info-notice/list/prdtInfoNotcCode",
					data : param
				}).done(function(data) {
					console.log(data);
					$(".info-notice-row").remove();
					
					$.each(data, function(i,v) {
						if($("#chnnl-no").val() == '10003'){
							var tmpl = document.templateOneRoot("#info-notice-tmpl");
							
							tmpl.find("[name='productAddInfo.infoNotcName']").html(v.infoNotcName);
							tmpl.find("[name='productAddInfo.prdtAddInfoRltnSeq']").val(v.prdtInfoNotcSeq);
							tmpl.find("#prdt-info-notc-seq-").attr("id", "prdt-info-notc-seq-"+i);
							tmpl.find("label").attr("for", "prdt-info-notc-seq-"+i);
							tmpl.find("[name='productAddInfo.prdtAddInfo']").attr({ "placeholder" : v.infoNotcDfltValue, "value" : v.infoNotcDfltValue, "data-required" : v.reqYn });
							tmpl.find("[name='productAddInfo.sortSeq']").val(v.sortSeq);
							tmpl.find("[name='productAddInfo.reqYn']").val(v.reqYn);
							tmpl.find("[name='productAddInfo.dispYn']").val("N");	// 초기 별도표시여부 체크 안함
							
							if (v.reqYn == "Y"){
								tmpl.find("#reqYnArea").html("필수");
							}
							
							$("#addInfo").before(tmpl);
						}else{
							 if(v.prdtInfoNotcCode != "10005"){
								var tmpl = document.templateOneRoot("#info-notice-tmpl");
									
								tmpl.find("[name='productAddInfo.infoNotcName']").html(v.infoNotcName);
								tmpl.find("[name='productAddInfo.prdtAddInfoRltnSeq']").val(v.prdtInfoNotcSeq);
								tmpl.find("#prdt-info-notc-seq-").attr("id", "prdt-info-notc-seq-"+i);
								tmpl.find("label").attr("for", "prdt-info-notc-seq-"+i);
								tmpl.find("[name='productAddInfo.prdtAddInfo']").attr({ "placeholder" : v.infoNotcDfltValue, "value" : v.infoNotcDfltValue, "data-required" : v.reqYn });
								tmpl.find("[name='productAddInfo.sortSeq']").val(v.sortSeq);
								tmpl.find("[name='productAddInfo.reqYn']").val(v.reqYn);
								tmpl.find("[name='productAddInfo.dispYn']").val("N");	// 초기 별도표시여부 체크 안함
								
								if (v.reqYn == "Y"){
									tmpl.find("#reqYnArea").html("필수");
								}
								
								$("#addInfo").before(tmpl);
							 }
						}
					});
				}).fail(function(e) {
					console.log("e :" + e);
				});
			});
		},
		callback : {
			initByStdCtgrChange : function(me) {
				/**
				 * 표준카테고리 변경에 따른 품목명 초기화 이벤트
				 */
				$("#item-code").val(me.find(":selected").data("item-code"));
				$("#item-code").trigger("change");
			}
		}
	}

	/**
	 * 취급주의사항
	 */
	_object.precaution = {
		data : [],
		init : function() {
			/**
			 * 취급주의사항 초기화
			 */
			// 소재 및 취급주의사항 항목 조회
			$.ajax({
				url				: "/noacl/product/category/standard/handling-precaution",
				method			: "GET",
				data			: { "stdCtgrNo" : $("#std-ctgr-no-depth-1").val() }
			}).done(function(data) {
				_object.precaution.data = data;

				var defaultValue = $("#prdt-materl-title-text").data("default-value");
				var option = null;
				$.each(_object.precaution.data, function(i, v) {
					option = $("<option>");
					option.attr("value", v.handlPrecauSeq);
					option.text(v.titleText);

					if(_common.isNotEmpty(defaultValue) && defaultValue == v.handlPrecauSeq) {
						option.attr("selected", true);
					}

					$("#prdt-materl-title-text").append(option);
					
					// 제공고시가 존재하지 않을경우 해당 품목코드로 임의생성되게 수정
					if(!abc.text.allNull($("#item-code").data("default-value")) 
							&& $("#add-info-area").find(".info-notice-row").length == 0) {
						$("#item-code").trigger("change");
					}
					
				});
			}).fail(function(data) {
				console.log("상품정보고시 취급주의사항 정보 조회 실패");
				console.log(data);
			});
		},
		reload : function() {
			/**
			 * 취급시주의사항 데이터 갱신
			 */
			_object.precaution.init();
		},
		event : function() {
			/**
			 * 소재 변경 이벤트
			 */
			$("#prdt-materl-title-text").change(function() {
				var value = $(this).val();
				if(_common.isNotEmpty(value)) {
					$.each(_object.precaution.data, function(i, v) {
						if(value == v.handlPrecauSeq) {
							$("#prdt-materl-cont-text").val(v.contText);
						}
					});
				} else {
					$("#prdt-materl-cont-text").val("");
				}
			});

			// 표준카테고리 변경에 따른 초기화 이벤트
			$("select[name='stdCtgrNo']").change(function(e) {
				//alert("===");
//				if ("N" === $("input[name='mmnyPrdtYn']").val()) {
//					if (!abc.text.isBlank($("select[name='stdCtgrNo']").val())) {
						$('.dp-category-select.chnnl-no').trigger('change');
//					} else {
						//$(".dp-category-select").attr("selected", "selected");
//					}
//				}
//				alert("상품정보고시관리탭 취급시 주의사항 중 소재가 초기화됩니다.");
				_object.precaution.reload();
			});
		}
	};

	_object.prdtSafeType = {
		/**
		 * 제품안전인증정보
		 */
		init : function() {
			/**
			 * 초기화
			 */
			$.ajax({
				type : "POST",
				url : "/product/product/notice/info/list/prdtSafeTypeCode"
			}).done(function(data) {
				$.each(data, function(i,v) {
					$option = $("<option>").text(v.codeDtlName).val(v.codeDtlNo);
					$("select.prdtSafeTypeCode").append($option);
				});
			}).fail(function(e) {
				console.log("e :" + e);
			});
		},
		event : function() {
			/**
			 * 이벤트 설정
			 */
			$(document).on("change", ".prdtSafeTypeCode, select[data-event-change='prdt-safe-type-code']", function() {
				console.log($(this).val());
				var target = $(this).parents("tr").next();
				var $text = target.find(".prdtAddInfo");
				var $certificationNumber = target.next().find(".safeCrtfcNoText");
				var $certificationMark = target.next().next().find(".safeCrtfcImageUrl");
				var $certificationIdHidden = target.next().next().find("input[name='productAddInfo.prdtSafeTypeCode']");
				var $certificationMarkHidden = target.next().next().find("input[name='productAddInfo.safeCrtfcImageUrl']");

				if ($(this).val() == "") {
					$text.prop("disabled", true);
					$certificationNumber.prop("disabled", true);
				} else {
					$text.prop("disabled", false);
					$certificationNumber.prop("disabled", false);
				}

				for(var i=0 ; i<_object.resource.prdtSafeItem.length ; i++) {
					if($(this).val() == _object.resource.prdtSafeItem[i].id) {
						$text.val(_object.resource.prdtSafeItem[i].text);
						$certificationNumber.val(_object.resource.prdtSafeItem[i].safeCrtfcNoText);
						$certificationMark.attr("src", _object.resource.prdtSafeItem[i].safeCrtfcImageUrl);
						$certificationIdHidden.attr("value", _object.resource.prdtSafeItem[i].id);
						$certificationMarkHidden.attr("value", _object.resource.prdtSafeItem[i].safeCrtfcImageUrl);
						break;
					}
				}
			});

			// 인증정보 추가
			$("#addSafeCrtfc").on("click", function() {
				var idx = 1;

				tmpl = $("#safety-tmpl").clone();
				tmpl = $(tmpl.html());
				tmpl = $(tmpl);

				tmpl.addClass("safety" + idx++);

				$.ajax({
					type : "POST",
					url : "/product/product/notice/info/list/prdtSafeTypeCode"
				}).done(function(data) {

					$.each(data, function(i,v) {
						$option = $("<option>").text(v.codeDtlName).val(v.codeDtlNo);

						tmpl.find("select.prdtSafeTypeCode").append($option);
					});
				}).fail(function(e) {
					console.log("e :" + e);
				});

				$("#add-info-area").append(tmpl);
			});

			// 인증정보 삭제
			$("body").on("click", ".safety-remove-btn", function() {
				var me = $(this).closest("tr");
				me.next().next().next().remove();
				me.next().next().remove();
				me.next().remove();
				me.remove();
			});
		}
	}

	_object.memo = {
		/**
		 * 관리자 메모
		 */
		saveMemo : function() {
			/**
			 * 저장
			 */
			if(_common.isNotEmpty($("#prdt-no").val())) {
				$.ajax({
					dataType : "json",
					type :"POST",
					url : "/product/product/memo/save",
					data : { "memoText" : $('#memoText').val(), "prdtNo" : $("#prdt-no").val() }
				})
				.done(function(data){
					alert("저장되었습니다.");
					_object.memo.appendMemo(data);
					$("#memoText").val("");
				})
				.fail(function(e){
					console.log(e);
					alert(e.responseJSON.message);
				});
			} else {
				alert("상품을 등록한 이후에 메모를 남길 수 있습니다.");
			}
		},
		appendMemo : function(data) {
			/**
			 * 추가
			 */
			var tmpl = document.templateSeveralRoot('#product-memo-row-tmpl');
			tmpl.find(".user-id").html('<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup("' + data.rgsterNo + '")">' + data.rgsterInfo + '</a>');
			//tmpl.find(".user-name").text("(" + data.rgsterName + ")");
			tmpl.find(".btn-msg-list-del").attr({ "data-prdt-memo-seq" : data.prdtMemoSeq });
			var rgstDtm = new Date(data.rgstDtm);
			tmpl.find(".regist-date").text(rgstDtm.format("yyyy-mm-dd HH:mm:ss"));
			tmpl.find(".msg-desc").text(data.memoText);

			var adminNo = tmpl.find(".btn-msg-list-del").data("loginAdminNo");

			if(adminNo != data.rgsterNo){
				tmpl.find(".btn-msg-list-del").hide();
			}else{
				tmpl.find(".btn-msg-list-del").data("prdtMemoSeq", data.prdtMemoSeq);
			}

			$("#append-product-memo").prepend(tmpl);
		},
		removeMemo : function(prdtMemoSeq, removeMemoElement) {
			/**
			 * 삭제
			 */
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/product/memo/remove",
				data : { "prdtMemoSeq" : prdtMemoSeq, "prdtNo" : $("#prdt-no").val() }
			})
			.done(function(data){
				alert("삭제되었습니다.");
				removeMemoElement.remove();
			})
			.fail(function(e){
				console.log(e);
			});
		}
	};

	_object.callback = {
		/**
		 * callback 목록
		 */
		findBrand : function(data) {
			/**
			 * 브랜드 찾기
			 */
			if(data) {
				$("#brand-no").val(data[0].brandNo).change();
				$("#brand-name").val(data[0].brandName);
				$("#brand-no").change();
			}
		},
		addProduct : {
			cntcPrdtSetupList : function(data) {
				var rowData = null;
				for(var i=0 ; i<data.length ; i++) {
					rowData = {
						"rltnPrdtTypeCode" : "10000",
						"rltnPrdtNo" : data[i].prdtNo,
						"prdtName" : data[i].prdtName,
						"brandNo" : data[i].brandNo,
						"brandName" : data[i].brandName,
						"stdCtgrName" : data[i].stdCtgrName,
						"channlNo" : data[i].chnnlNo,
						"chnnlName" : data[i].chnnlName,
						"stdrCtgrName" : data[i].stdrCtgrName,
						"siteNo" : data[i].siteNo
					};
					//같은 사이트 번호와 다른 상품번호일때만 로우 쌓이게 수정
					if(!_common.findDuplicateInSheet(cntcPrdtSetupList, 'rltnPrdtNo', rowData, 'rltnPrdtNo', $('#prdt-no').val())
							&& data[i].siteNo == $("#site-no").val() && data[i].prdtNo != $('#prdt-no').val()) {
						cntcPrdtSetupList.SetRowData(0, rowData, { Add : 1 });
					}
				}
			},
			rltnGoodsSetupList : function(data) {
				var rowData = null;
				for(var i=0 ; i<data.length ; i++) {
					rowData = {
						"rltnPrdtTypeCode" : "10001",
						"rltnPrdtNo" : data[i].prdtNo,
						"prdtName" : data[i].prdtName,
						"brandNo" : data[i].brandNo,
						"brandName" : data[i].brandName,
						"stdCtgrName" : data[i].stdCtgrName,
						"channlNo" : data[i].chnnlNo,
						"chnnlName" : data[i].chnnlName,
						"stdrCtgrName" : data[i].stdrCtgrName
					};
					if(!_common.findDuplicateInSheet(rltnGoodsSetupList, 'rltnPrdtNo', rowData, 'rltnPrdtNo', $('#prdt-no').val())) {
						rltnGoodsSetupList.SetRowData(0, rowData, { Add : 1 });
					}
				}
			}
		},
		approval : function() {
			/**
			 * 승인관리 init 호출할 예정함수
			 */
		}
	};

	_object.getList = function(type) {
		/**
		 * 목록 조회
		 */
		var param = null;
		var onePageRow = 1000000;

		if(type === "prdtOptnList" && _common.parameter.getParameter("auth") === "P" && _common.parameter.getParameter("type") === "registry") {
			// 입점사 신규등록으로 접속한 경우, 기본옵션 1개 추가
			var rowData = { optnName : "기본", "mmnyPrdtYn" : "N", "stockVdQty" : 0, "totalOrderQty" : 0, "orderPsbltQty" : 0, "optnAddAmt" : 0, "sortSeq" : 1 };
			_common.grid.util.addRow(prdtOptnList, rowData);
			return false;
		}
		if(_common.isEmpty($("#prdt-no").val())) {
			if($("[name='newErpProductYn']").val() !== "Y") {
				// 자사상품 신규등록이 아닌 경우, 목록 조회 전부 안함
				return false;
			}
		}

		switch(type) {
		case "cntcPrdtSetupList" :
			// 색상연계상품 목록 조회
			param = {
					url : "/product/product/relation",
					onePageRow : onePageRow,
					subparam : FormQueryStringEnc(document.forms["cntc-prdt-setup-form"]),
					sheet : "cntcPrdtSetupList",
					callback : function() {
						$(".window-content").scrollTop(0);	// IBsheet가 여러개일 경우, 페이지 로딩 후 스크롤이 하단으로 떨어지는 경우가 있어 상단으로 고정
					}
			};
			break;
		case "rltnGoodsSetupList" :
			// 관련용품 목록 조회
			param = {
				url : "/product/product/relation",
				onePageRow : onePageRow,
				subparam : FormQueryStringEnc(document.forms["rltn-goods-setup-form"]),
				sheet : "rltnGoodsSetupList",
				callback : function() {
					$(".window-content").scrollTop(0);	// IBsheet가 여러개일 경우, 페이지 로딩 후 스크롤이 하단으로 떨어지는 경우가 있어 상단으로 고정
				}
			};
			break;
		case "prdtOptnList" :
			param = {
				url : "/product/product/option",
				onePageRow : onePageRow,
				subparam : FormQueryStringEnc(document.forms["product-option-form"]),
				sheet : "prdtOptnList",
				callback : function() {
					$(".window-content").scrollTop(0);	// IBsheet가 여러개일 경우, 페이지 로딩 후 스크롤이 하단으로 떨어지는 경우가 있어 상단으로 고정
				}
			};
			break;
		default :
			console.log("지원하지 않는 유형입니다. " + type);
			return;
		}
		DataSearchPaging(param);
	}

	_object.brand = {
		/**
		 * 브랜드
		 */
		visual : {
			get : function() {
				if($("#prdt-no").val() != "" && $("#brand-no").val() != "") {
					$.ajax({
						url		: "/noacl/brand/visual",
						method	: "GET",
						data	: { "prdtNo" : $("#prdt-no").val(), "brandNo" : $("#brand-no").val() }
					}).done(function(data) {
						if(data) {
							console.log("브랜드 비주얼 조회 성공");
							_object.brand.visual.appear(data);
							console.log(data);
						} else {
							_object.brand.visual.disappear();
						}
					}).fail(function(data) {
						console.log("브랜드 비주얼 조회 실패");
						_object.brand.visual.disappear();
						console.log(data);
					});
				} else {
					_object.brand.visual.disappear();
				}
			},
			appear : function(data) {
				$("[data-brand-visual='exist']").find("[data-brand='image']").css("background-image", "url('" + data.imageUrl + "')");
				$("[data-brand='title']").text(data.titleText);
				$("[data-brand='image-name']").text(data.imageName);
				$("[data-brand='display-status']").text(data.dispYn === "Y" ? "전시 " : "전시안함");
				$("[data-brand-visual='none']").hide();
				$("[data-brand-visual='exist']").show();
			},
			disappear : function() {
				$("[data-brand-visual='exist']").hide();
				$("[data-brand-visual='none']").show();
			}
		},
		validate : {
			available3dView : function() {
				var available3dView = false;
				for(var i=0 ; i<_object.resource.view3d.brand.length ; i++) {
					if($("#brand-no").val() === _object.resource.view3d.brand[i].brandNo) {
						available3dView = true;
						break;
					}
				}
				if(available3dView) {
					$("[data-product-property='3d-view']").find("input").prop("disabled", false);
					$("[data-product-property='3d-view']").show();
				} else {
					$("[data-product-property='3d-view']").hide();
					$("[data-product-property='3d-view']").find("input").prop("disabled", true);
				}
			}
		}
	}

	_object.vendor = {
		/**
		 * 입점사 정보
		 */
		get : function() {
			if($("#mmny-prdt-yn").val() === "N") {
				var param = {
					"vndrNo"	: $("#vndr-no").val(),
					"stdCtgrNo"	: $("#std-ctgr-no").val(),
					"brandNo"	: $("#brand-no").val()
				};

				var isContinue = true;
				for(var item in param) {
					if(param[item] === undefined || param[item] === "") {
						isContinue = false;
						break;
					}
				}

				if(isContinue) {
					$.ajax({
						url				: "/product/product/information/vendor",
						method			: "GET",
						data			: param
					}).done(function(data) {
						console.log("입점사 정보 조회 성공");
						if(data) {
							if(data.cmsnRate != undefined) {
								_object.vendor.commitionFee.appear(data.cmsnRate);
							} else {
								_object.vendor.commitionFee.disappear();
							}
							if(data.empDscntRate != undefined) {
								_object.vendor.empDscntRate.appear(data.empDscntRate);
							} else {
								_object.vendor.empDscntRate.disappear();
							}
						}
					}).fail(function(data) {
						console.log("입점사 정보 조회 실패");
						_object.vendor.commitionFee.disappear();
						_object.vendor.empDscntRate.disappear();
					});
				}
			}
		},
		commitionFee : {	// 수수료율
			appear : function(rate) {
				$("[data-product-price='commition-rate']").text(rate);
			},
			disappear : function() {
				$("[data-product-price='commition-rate']").text("0");
			}
		},
		empDscntRate : {
			appear : function(rate) {
				var normalAmt = _common.number.getNumberOnly($("#normal-amt").val());
				var sellAmt = _common.number.getDiscountPrice(normalAmt, rate);
				var displayText = rate + "% / " + _common.number.setDecoratePrice(sellAmt);
				$("[data-product-price='employee-discount-rate']").text(displayText);
				$("#emp-dscnt-rate").val(rate);
			},
			disappear : function() {
				var normalAmt = $("#normal-amt").val();
				var displayText = "0% / " + _common.number.setDecoratePrice(normalAmt);
				$("[data-product-price='employee-discount-rate']").text(displayText);
			}
		}
	}

	/**
	 * 상품에 적용된 프로모션
	 */
	_object.promotion = {
		init : function() {
			_object.promotion.getList();
		},
		getList : function() {
			if($("#prdt-no").val() != "") {
				$.ajax({
					url				: "/noacl/promotion/target/product/promotion/list",
					method			: "GET",
					data			: { prdtNo : $("#prdt-no").val() }
				}).done(function(data) {
					_object.promotion.appear(data);
				}).fail(function(data) {
					_object.promotion.disappear(data);
				});
			} else {
				console.log("상품 번호가 없습니다.");
			}
		},
		appear : function(data) {
			var result = [];
			$.each(data, function(i, v) {
				var text = "";
				result.push(text.concat(v.promoName, "(", v.promoNo, ")"));
			});
			$("[data-promotion='applying']").text(result.join(", "));
		},
		disappear : function(data) {
			$("[data-promotion='applying']").empty();
		}
	}

	/**
	 * 상품에 적용된 쿠폰
	 */
	_object.coupon = {
		init : function() {
			_object.coupon.getList();
		},
		getList : function() {
			if($("#prdt-no").val() != "") {
				$.ajax({
					url				: "/noacl/coupon/target/product/coupon/list",
					method			: "GET",
					data			: { prdtNo : $("#prdt-no").val() }
				}).done(function(data) {
					_object.coupon.appear(data);
				}).fail(function(data) {
					_object.coupon.disappear(data);
				});
			} else {
				console.log("상품 번호가 없습니다.");
			}
		},
		appear : function(data) {
			var result = [];
			$.each(data, function(i, v) {
				result.push(v.cpnNo);
			});
			$("[data-coupon='applying']").text(result.join(", "));
		},
		disappear : function(data) {
			$("[data-coupon='applying']").empty();
		}
	}

	_object.regist = {
		/**
		 * 등록처리
		 */
		setPreRunning : function() {
			/**
			 * 통신 전 잠금설정
			 */
			// 에디터 데이터 설정
			_common.editor.setData(["prdt-dtl-info-pc", "prdt-dtl-info-mobile"]);

			// 체크박스로 된 YN값 설정
			_common.setYnIfSourceChecked("rsv-prdt-yn-y", "rsv-prdt-yn");						// 예약상품여부
			_common.setYnIfSourceChecked("relis-todo-yn-y", "relis-todo-yn");					// 출시일여부
			_common.setNyIfSourceChecked("emp-dscnt-yn-n", "emp-dscnt-yn");						// 임직원 할인 제외
			_common.setYnIfSourceChecked("dprc-except-yn-y", "dprc-except-yn");					// 감가 제외
			_common.setYnIfSourceChecked("order-mnfct-yn-y", "order-mnfct-yn");					// 주문제작
			_common.setNyIfSourceChecked("store-pickup-psblt-yn-n", "store-pickup-psblt-yn");	// 매장픽업불가

			_common.setZeroIfSourceChecked("buy-limit-yn-n", "min-buy-psblt-qty");				// 최소구매수량
			_common.setZeroIfSourceChecked("buy-limit-yn-n", "max-buy-psblt-qty");				// 최대 구매수량
			_common.setZeroIfSourceChecked("buy-limit-yn-n", "day-max-buy-psblt-qty");			// 1일 최대 구매수량

			$("#size-chart-seq").val($("#size-chart-list").val());	// 사이즈가이드 선택박스 값 이동

			// 판매기간 생성(시작일자, 종료일자)
			$("#sell-start-dtm").val($("#sell-start-dtm-date").val().replace(/\./g, "-") + " " + $("#sell-start-dtm-hour").val() + ":" + $("#sell-start-dtm-minute").val() + ":00");
			$("#sell-end-dtm").val($("#sell-end-dtm-date").val().replace(/\./g, "-") + " " + $("#sell-end-dtm-hour").val() + ":" + $("#sell-end-dtm-minute").val() + ":59");

			// 출시(예정일) 생성
			if($('#relis-todo-yn-y').is(":checked")) {
				$("#relis-todo-dtm").val($("#relis-todo-dtm-date").val().replace(/\./g, "-") + " " + $("#relis-todo-dtm-hour").val() + ":" + $("#relis-todo-dtm-minute").val() + ":00");
			}

			// 정상가 가격 꾸밈 해제
			_common.number.setUndecoratePriceBySelector("#normal-amt");
			// 판매가 가격 꾸밈 해제
			_common.number.setUndecoratePriceBySelector("#onln-sell-amt");

			// 불필요 파라미터 비활성화
			$("input[name='productImageUploadYn']").attr("disabled", true);
			$("input[name='productDescriptionUploadYn']").attr("disabled", true);

			_object.resource.isRunningNetwork = true;	// 네트워크 사용 플래그 설정
		},
		setPostRunning : function() {
			/**
			 * 통신 후 잠금 해제
			 */
			// 불필요 파라미터 활성화
			$("input[name='productImageUploadYn']").attr("disabled", false);
			$("input[name='productDescriptionUploadYn']").attr("disabled", false);


			// 정상가 가격 꾸밈 설정
			_common.number.setDecoratePriceBySelector("#normal-amt");
			// 판매가 가격 꾸밈 설정
			_common.number.setDecoratePriceBySelector("#onln-sell-amt");

			_object.resource.isRunningNetwork = false;	// 네트워크 사용 플래그 해제
		},
		checkBeforeRunning : function() {
			/**
			 * 통신 전 사용조건 확인
			 */
			var isOk = true;
			if(_object.resource.isRunningNetwork) {
				alert("저장중입니다.\n잠시만 기다려 주세요.");
				isOk = false;
			}
			return isOk;
		},
		validate : function() {
			/**
			 * 통신 전 검증
			 */
			var isValid = true;
			var sellStartDtm = _common.date.convertDate("yyyy.mm.dd hh:mm", $("#sell-start-dtm-date").val().concat(" ", $("#sell-start-dtm-hour").val(), ":", $("#sell-start-dtm-minute").val()));
			var sellEndDtm = _common.date.convertDate("yyyy.mm.dd hh:mm", $("#sell-end-dtm-date").val().concat(" ", $("#sell-end-dtm-hour").val(), ":", $("#sell-end-dtm-minute").val()));
			var sellDtmGap = (sellEndDtm.getTime() - sellStartDtm.getTime()) / 1000 / 60 / 60 / 24;
			var todayDtm = new Date();

			if(sellDtmGap < 0) {
				alert("시작일은 종료일 이전 날짜만 가능합니다.");
				return isValid = false;
			}

			if($('[name=sellStatCode][value=10001]').is(':checked')) {
				if(!(todayDtm >= sellStartDtm && todayDtm <= sellEndDtm))  {
					alert("현재 판매가능한 기간이 아닙니다. 판매상태를 확인해주세요.");
					return isValid = false;
				}
			}

			// 출시일 검증
			if($('#relis-todo-yn-y').is(":checked")) {
//				if(!$('[name=sellStatCode][value=10000]').is(':checked')) {
//					alert("판매대기 상태의 상품만 출시일 등록이 가능합니다.");
//					return isValid = false;
//				}

				if(abc.text.isBlank($("#relis-todo-dtm").val())) {
					alert('출시(예정)일을 입력해주세요.');
					return isValid = false;
				} else {
					var relisTodoDtm = new Date($("#relis-todo-dtm").val());
					var relisTodoDtmGap = (sellEndDtm.getTime() - relisTodoDtm.getTime()) / 1000 / 60 / 60 / 24;
					if(relisTodoDtmGap < 0) {
						alert('판매기간, 출시(예정)일을  다시 한번 확인해주세요.');
						return isValid = false;
					}
				}
			}

			// 검색키워드
			if(_common.isNotEmpty($("[name='srchKeyWordText']").val())) {
				var totalLength = abc.text.isLimitLength($("[name='srchKeyWordText']").val(),2000);
				//alert("dddd"+totalLength);
				if(!totalLength){
					var refinedKeywords = [];	// 정제된 검색어 저장공간
					var unrefinedKeywords = $("[name='srchKeyWordText']").val().trim().split(",");	// 입력된 검색어 공백 제거 후 배열로 변환

					for(var i=0 ; i<unrefinedKeywords.length ; i++) {
						var keyword = unrefinedKeywords[i].trim();

						if(_common.isNotEmpty(keyword)) {
							refinedKeywords.push(keyword);
							if(refinedKeywords.length > _object.resource.search.keyword.maxLength) {
								// 검색어 입력제한갯수가 넘은 경우
								$("[name='srchKeyWordText']").focus();
								alert("검색 키워드는 100개까지 가능합니다.");
								return isValid = false;
							}
						}
					}
					$("[name='srchKeyWordText']").val(refinedKeywords.join(","));	// 검색키워드를 정제된 검색어로 재설정
				}else{
					alert("2000byte 초과하였습니다.");
					$("[name='srchKeyWordText']").focus();
					return isValid = false;
				}
			}

			// 정보제공고시
//			var noticeInfo = $(".info-notice-row");
//			if(isValid && noticeInfo.length > 0) {
//				$.each(noticeInfo, function(i, v) {
//					var me = $(v);
//					me.find("[name='productAddInfo.dispYn']").val(me.find("[id^='prdt-info-notc-seq-']").prop("checked") ? "Y" : "N");
//
//					if(isValid && (me.find("[name='productAddInfo.reqYn']").val() === "Y" && me.find("[name='productAddInfo.prdtAddInfo']").val() === "")) {
//						alert("상품정보제공고시 중 " + me.find("[name='productAddInfo.infoNotcName']").text() + "은(는) 필수입니다.");
//						me.find("[name='productAddInfo.prdtAddInfo']").focus();
//						return isValid = false;
//					}
//				});
//			}

			return isValid;
		},
		run : function() {
			/**
			 * 통신 수행
			 */

			if(!_object.regist.checkBeforeRunning()) {
				// 통신 수행 불가능 상태(이미 통신중임)
				_object.regist.setPostRunning();
				return false;
			}

			_object.regist.setPreRunning();	// 통신 전 설정

			if(!_object.regist.validate()) {
				// 검증 실패
				_object.regist.setPostRunning();
				return false;
			}

			//정상가, 판매가 수정되었는지 판단
			_object.regist.priceChangeYn();

			// 그리드 데이터 생성
			var additionalParam = {
				"productOption"			: prdtOptnList.ExportData({ "Type" : "json" }).data,
				"cntcPrdtSetupList"		: cntcPrdtSetupList.ExportData({ "Type" : "json" }).data,
				"rltnGoodsSetupList"	: rltnGoodsSetupList.ExportData({ "Type" : "json" }).data
			};

			var option = {
				url			: "/product/product/save",
				type		: "POST",
				dataType	: "json",
				enctype		: "multipart/form-data",
				data		: $.paramObject(additionalParam),
				success		: function(data) {
					//2020-02-14 현업 박유진씨 요청으로 상품 수정 후 리로드 하지 않는 방식으로 변경
					//window.opener.location.reload();	// 부모창 목록 재조회
					if(data != undefined && data != null && data.resultMessage != undefined && data.resultMessage != null && data.resultMessage != "") {
						alert(data.resultMessage);
					} else {
						alert("저장되었습니다.");
					}
					self.close();
				},
				error		: function(e) {
					// 검증실패요소 포커스. 요소가 여러개인 경우, 첫번쨰 요소에 포커스를 위치함.
					var $el;
					if(e.fieldName == "productColor"){
						$el = $("[name='productColor.prdtColorCode']:not(:disabled)").not("[type='hidden']");
					}else{
						$el = $("[name='" + e.fieldName + "']:not(:disabled)").not("[type='hidden']");
					}

					var isErrorForEmpty = false;
					var isErrorForLength = false;

					isErrorForEmpty = (e.message.indexOf("필수 입니다.") > -1) ? true : false;
					isErrorForLength = (e.message.indexOf("초과되었습니다.") > -1) ? true : false;
					isErrorForLength = isErrorForLength || (e.message.indexOf("필수 입니다.") > -1) ? true : false;
					isErrorForLength = isErrorForLength || (e.message.indexOf("입력하세요.") > -1) ? true : false;
					isErrorForLength = isErrorForLength || (e.message.indexOf("이하여야 합니다.") > -1) ? true : false;

					var isFocus = false;
					var focusElement = null;

					for(var i=0 ; i<$el.length ; i++) {
						if(isErrorForEmpty) {
							if($($el[i]).val() === "") {
								focusElement = $el[i];
								break;
							}
						}

						if(isErrorForLength) {
							if($($el[i]).val() != "") {
								focusElement = $el[i];
								break;
							}
						}

					}

					if(e.fieldName === "productAddInfo.prdtAddInfo" && e.message.indexOf("상품정보고시에서") > -1 ) {
						// 상품정보제공고시부분 검증에 실패한 경우, 결과메시지에서 항목명을 재지정함
						focusElement = $(focusElement).closest("tbody").find("input[name='productAddInfo.prdtAddInfo'][data-notice-type='information'][data-required='Y']").filter(function() { return $(this).val() == "" }).first();
						var itemName = focusElement.closest("tr").find("[name='productAddInfo.infoNotcName']").text();
						e.message = e.message.replace("상품정보고시에서", "상품정보고시에서 " + itemName + " 항목명에 대한");
					}

					if(focusElement != null) {
						$(focusElement).focus();
					}

					// ckeditor가 적용된 곳에 focus를 주기 위해 설정
					if(e.fieldName == "productDetail.divider"){
						var ckeditor = CKEDITOR.instances['prdt-dtl-info-pc'];
						ckeditor.focus();
					}


					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
					}
				},
				complete	: function() {
					_object.regist.setPostRunning();
				}
			};

			$.form("#product-form").submit(option);
		}, priceChangeYn : function(){
			var changeStatus = false;
			if($("#org-normal-amt").val() != $("#normal-amt").val()){
				changeStatus = true;
			}
			if($("#onln-sell-amt").val() != $("#org-onln-sell-amt").val()){
				changeStatus = true;
			}

			if(changeStatus){
				$("#orgPriceChangeYn").val(abc.consts.BOOLEAN_TRUE);
			}else{
				$("#orgPriceChangeYn").val(abc.consts.BOOLEAN_FALSE);
			}
		}

	}

	$(function() {
		_object.init();
	});

})();