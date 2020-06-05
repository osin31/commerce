(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.bulkUpdate");
	
	_object.resource = {
		isRunningNetwork : false,
		limitProductCount : 50,
		popup : {
			product : null,
			goods : null
		},
		sheet : {
			header : {
				list : [
					{ Header:"",						Type:"CheckBox",	SaveName:"checked",						Width: 6,	Align:"Center",	Edit:1, Sort:0},
					{ Header:"번호",						Type:"Seq",			SaveName:"seq",							Width: 6,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"",						Type:"Status",		SaveName:"status",						Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",						Type:"Int",			SaveName:"",							Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트번호",					Type:"Text",		SaveName:"siteNo",						Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트이름",					Type:"Text",		SaveName:"siteName",					Width: 0,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"채널번호",					Type:"Text",		SaveName:"chnnlNo",						Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"자사/입점",					Type:"Combo",		SaveName:"mmnyPrdtYn",					Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", ComboText : "자사|입점",					ComboCode : "Y|N" },
					{ Header:"업체상품코드",				Type:"Text",		SaveName:"vndrPrdtNoText",				Width: 18,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시채널",					Type:"Text",		SaveName:"chnnlName",					Width: 20,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"상품코드",					Type:"Text",		SaveName:"prdtNo",						Width: 20,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"상품이미지",					Type:"Text",		SaveName:"titleImageName",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"상품이미지",					Type:"Text",		SaveName:"titleImagePathText",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"상품이미지",					Type:"Image",		SaveName:"titleImageUrl",				Width: 30,	Align:"Center",	Edit:0,	Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
					{ Header:"상품명",					Type:"Text",		SaveName:"prdtName",					Width: 30,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"브랜드",					Type:"Text",		SaveName:"brandName",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
					{ Header:"판매상태",					Type:"Combo",		SaveName:"sellStatCode",				Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code },
					{ Header:"전시여부",					Type:"Combo",		SaveName:"dispYn",						Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" },
					{ Header:"정상가",					Type:"Text",		SaveName:"normalAmt",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"판매가",					Type:"Text",		SaveName:"sellAmt",						Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					// -- 여기 기준으로 고정이라는데..확인필요.
					//{ Header:"표준카테고리",				Type:"Text",		SaveName:"stdCtgrName",					Width: 15,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					//{ Header:"전시카테고리(기준)",			Type:"Text",		SaveName:"stdrCtgrName",				Width: 20,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					//{ Header:"등록업체명",				Type:"Text",		SaveName:"vndrName",					Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					//{ Header:"예약상품여부",				Type:"Combo",		SaveName:"rsvPrdtYn",					Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", ComboText : "예약|일반",					ComboCode : "Y|N" }
				],
				rltnGoodsSetupList : [
					{ Header:"",			Type:"Status",		SaveName:"status",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",			Type:"Seq",			SaveName:"seq",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"관련상품순번",	Type:"Int",			SaveName:"rltnPrdtTypeCode",	Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"",			Type:"CheckBox",	SaveName:"checked",				Width: 5,	Align:"Center",	Edit:1,	Cursor:"Pointer" },
					{ Header:"상품코드",		Type:"Text",		SaveName:"rltnPrdtNo",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"상품명",		Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"브랜드",		Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"표준카테고리",	Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"전시채널",		Type:"Text",		SaveName:"chnnlName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시카테고리",	Type:"Text",		SaveName:"stdrCtgrName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
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
		
		$("[data-toggle-target]").hide();
	}
	
	_object.sheet = {};
	_object.sheet.init = function() {
		console.log("시트 초기화를 시작합니다.");
		var pageCount = 50;
		abc.biz.product.utils.grid.create(_object.sheet, "product-list", "list", "100%", "429px", pageCount, _object.resource.sheet.header.list);
		abc.biz.product.utils.grid.create(_object.sheet, "rltn-goods-setup-list", "rltnGoodsSetupList", "100%", "429px", pageCount, _object.resource.sheet.header.rltnGoodsSetupList);
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){
		console.log("시트 이벤트 바인딩을 시작합니다.");
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		console.log("이벤트 바인딩을 시작합니다.");
		
		// 전시카테고리 이벤트 설정
		abc.biz.display.common.setDpCategoryEvent();
		
		$("[data-toggle]").click(function() {
			// 대상을 찾아 보이기 설정
			var value = $(this).data("toggle");
			var selector = "[data-toggle-target='" + value + "']";
			$(selector).show();
		});
		
		$("[data-clear]").click(function() {
			// 대상을 찾아 값 초기화
			var value = $(this).data("clear");
			var selector = "[data-clear-target='" + value + "']";
			var selectedElement = $(selector);
			
			if(selectedElement.length > 0) {
				$.each(selectedElement, function(i, v) {
					switch(v.tagName.toLowerCase()) {
					case "input" :
						switch($(v).attr("type").toLowerCase()) {
						case "text" :
							$(v).val("");
							break;
						case "number" :
							$(v).val("0");
							break;
						case "checkbox" :
							$(v).prop("checked", false);
							break;
						default :
							console.log("알 수 없는 유형입니다. $(v).attr(\"type\") : " + $(v).attr("type"));
						}
						break;
					case "select" :
						$(v).find("option:first").prop("selected", true);
						break;
					default :
						console.log("알 수 없는 유형입니다. v.tagName : " + $(v).tagName);
					}
				});
			}
			
			// 추가적으로 그리드제거항목이 있는 경우 제거
			var sheetName = $(this).data("clear-sheet-name");
			if(abc.biz.product.utils.isNotEmpty(sheetName)) {
				abc.biz.product.utils.grid.util.removeAll(sheetName);
			}
			
			// 전시카테고리 항목 초기화
			var clearDisplayCategory = $(this).data("clear-display-category");
			if(abc.biz.product.utils.isNotEmpty(clearDisplayCategory)) {
				$("#display-category-area").find("tr").not("[data-empty='display-category']").remove();	// 전시카테고리 모두 지운 후 없음출력
				$("[data-empty='display-category']").show();	// 전시카테고리 없음 메시지 출력
			}
			
			// 대상 토글항목 숨기기
			selector = "[data-toggle-target='" + value + "']";
			$(selector).hide();
		});
		
		$("[data-button]").click(function(e) {
			var type = $(this).data("button");
			switch(type) {
			case "clear" :	// 초기화
//				abc.biz.product.utils.initFormData("bulk-update-form");
				$("input[type='radio']").prop("selected", false);
				$("input[type='checkbox']").prop("checked", false);
				$("input[type='text']").val("");
				$.each($("select"), function(i, v) {
					$(v).find("option:first").prop("selected", true);
				});
				
				$("#display-category-area").find("tr").not("[data-empty='display-category']").remove();	// 전시카테고리 모두 지운 후 없음출력
				$("[data-empty='display-category']").show();	// 전시카테고리 없음 메시지 출력
				
				abc.biz.product.utils.grid.util.removeAll("rltnGoodsSetupList");	// 관련용품 모두 제거
				
				$("td").find("input[type='radio']:first").prop("checked", true);
				
				$("[data-toggle-target]").hide();
				break;
			case "add-display-category" :	// 전시 카테고리 추가
					
				if( abc.text.allNull($("select[name='ctgrNo']").val()) ){
					alert("전시 카테고리를 선택해주세요.");
					return;
				}
				
				console.log("ctgrNo : ");
				console.log( $("select[name='ctgrNo']").val() );
				
				/**
				 * 2020.05.14 상품 전시 카테고리 validate 변경
				 */
				var depthName = "";
				var categorySelectYn = true;
				var categoryChnnlNo = $(".chnnl-no").val();
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
				
				var selectOptn = $("select[name='ctgrNo']").val();
				var classArray = $("select[name='ctgrNo']").attr('class').split(' ');
				depthName = classArray[2];
				var ctgrLevel = Number(depthName.substring(0,1));
				console.log("ctgrLevel : " + ctgrLevel);
				console.log("maxCtgrLevel : " + maxCtgrLevel);
				if(ctgrLevel < maxCtgrLevel){
					categorySelectYn = false;
				}
				
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
					
					// 데이터 없음 메시지 숨기기
					$("[data-empty='display-category']").hide();
					
					// 전시 카테고리 값 초기화
					$(".dp-category-select").attr("selected", "selected");
					
					var target = $(this).data("target");
					var param = {
						siteNo	: $("#display-category-channel>option:selected").data("site-no"),
						chnnlNo	: $("#display-category-channel").val(),
						ctgrNo	: $("select[name='ctgrNo']").val()
					};
					
					console.log("param : ");
					console.log(param);
					
					$.ajax({
						//url				: "/display/category/list/category",
						url				: "/noacl/display/display-category/list",
						method			: "POST",
						data			: param
					}).done(function(data) {
						
						//console.log(data);
						
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
						if($("#display-category-area").find("tr").not("[data-empty='display-category']").length === 1) {
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
			case "remove-display-category" :	// 전시 카테고리 선택삭제
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
				
				if($(selector).find("tr").not("[data-empty='display-category']").length === 0) {
					$("[data-empty='display-category']").show();	// 등록된 전시카테고리가 없는 경우, 없음 메시지 노출
				}
				
				break;
			case "remove-rows" :	// 선택삭제
				var sheetName = $(this).data("sheet-name");
				abc.biz.product.utils.grid.util.removeRows(sheetName);
				break;
			case "add-row-from-product" :	// 상품선택팝업으로부터 행 추가
				var sheetName = $(this).data("sheet-name");
				switch(sheetName) {
				case "list" :
					_object.resource.popup.product = window.abc.productSearchPopup(true, "abc.biz.product.bulkUpdate.callback.addProduct.list");
					break;
				case "rltnGoodsSetupList" :
					_object.resource.popup.goods = window.abc.productSearchPopup(true, "abc.biz.product.bulkUpdate.callback.addProduct.rltnGoodsSetupList");
					break;
				default :
					console.log("알 수 없는 시트이름 입니다. " + sheetName);
				}
				break;
			case "save" :	// 일괄 수정 저장
				_object.regist.run();
				break;
			default :
				console.log("잘못된 유형입니다. " + type);
				break;
			}
		});
		
		$("[data-checkbox]").click(function(e) {
			var type = $(this).data("checkbox");
			switch(type) {
			case "check-all" :
				// 전체선택
				var selector = "[data-target=" + $(this).data("target") + "]";
				var isChecked = $(this).is(":checked");
				$(selector).attr("checked", isChecked);
				break;
			default :
				console.log("잘못된 유형입니다. " + type);
				break;
			}
		});
	}
	
	_object.callback = {
		addProduct : {
			list : function(data) {
				console.log("팝업으로부터 대상상품정보를 추가합니다.");
				var rowData = null;
				for(var i=0 ; i<data.length ; i++) {
					list.SetRowData(0, data[i], { Add : 1 });
				}

				if(_object.resource.limitProductCount < list.RowCount()) {
					_object.resource.popup.product.alert("최대 50개까지만 등록됩니다.");
					do {
						list.RowDelete(1);
					} while(_object.resource.limitProductCount != list.RowCount());
				}
			},
			rltnGoodsSetupList : function(data) {
				console.log("팝업으로부터 관련용품 상품정보를 추가합니다.");
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
					rltnGoodsSetupList.SetRowData(0, rowData, { Add : 1 });
				}
			}
		}
	};
	
	/**
	 * 팝업
	 */
	_object.popup = function(type, param) {
		switch(type) {
		case "add-product" :
			window.abc.productSearchPopup(true, abc.biz.product.bulkUpdate.callback.addProduct);
			break;
		default :
			console.log("알 수 없는 유형입니다. " + type);
		}
	}
	
	/**
	 * 등록처리
	 */
	_object.regist = {
		additionalParam : {},
		validate : function() {
			var notValid = false;
			var notValidMessage = "";
			
			if($("td").find("input[type='radio']:first").length >= 0) {
				// 변경안함 갯수 체크(변경사항 유무 확인)
				var items = $("td").find("input[type='radio']:first");
				var checkedItemCount = 0;
				$.each(items, function(i, v) {
					if($(v).is(":checked")) {
						checkedItemCount++;
					}
				});
				if(items.length === checkedItemCount) {
					notValid = true;
					notValidMessage = "변경사항이 없습니다.";
				}
			}
			if(!notValid && _object.regist.additionalParam.products.length === 0) {
				// 상품선택여부 확인 및 반환
				notValid = true;
				notValidMessage = "대상 상품이 없습니다.";
			}
			if(!notValid && $("#sell-period-change").is(":checked")) {
				// 판매기간 변경을 선택한 경우
				if(abc.biz.product.utils.isNotEmpty($("#sell-start-dtm-date").val()) && abc.biz.product.utils.isNotEmpty($("#sell-end-dtm-date").val())) {
					// 판매기간 생성(시작일자, 종료일자)
					$("#sell-start-dtm").val($("#sell-start-dtm-date").val().replace(/\./g, "-") + " " + $("#sell-start-dtm-hour").val() + ":" + $("#sell-start-dtm-minute").val() + ":00");
					$("#sell-end-dtm").val($("#sell-end-dtm-date").val().replace(/\./g, "-") + " " + $("#sell-end-dtm-hour").val() + ":" + $("#sell-end-dtm-minute").val() + ":59");
				} else {
					notValid = true;
					notValidMessage = "판매기간을 입력 해 주세요.";
				}
			}
			if(!notValid && $("#rsv-prdt-yn-y").is(":checked")) {
				// 예약상품인 경우 출고일 체크
				if(abc.biz.product.utils.isEmpty($("#rsv-dlvy-ymd").val())) {
					notValid = true;
					notValidMessage = "예상출고일을 선택 해 주세요.";
				}
				
			}
			if(!notValid && $("#display-category-no-change").is(":checked")) {
				// 전시카테고리 변경인 경우 항목체크
				if($("#display-category-area").find("tr").not("[data-empty='display-category']").length === 0) {
					notValid = true;
					notValidMessage = "전시 카테고리를 등록 해 주세요.";
				}
			}
			if(!notValid && $("#stock-intgr-yn-n").is(":checked")) {
				// 재고미통합이 체크된 경우 사유선택여부 확인
				if($("#stock-un-intgr-rsn-code").val() === "") {
					notValid = true;
					notValidMessage = "재고 미통합 사유를 선택 해 주세요.";
				}
			}
			
			if(notValid) {
				alert(notValidMessage);
			}
			
			return !notValid;
		},
		run : function() {
			// 네트워크 사용 플래그 검사
			if(_object.resource.isRunningNetwork) {
				alert("저장중입니다.\n잠시만 기다려 주세요.");
				return false;
			}
			
			_object.resource.isRunningNetwork = true;	// 네트워크 사용 플래그 설정
			
			// 그리드 데이터 생성
			_object.regist.additionalParam = {};
			_object.regist.additionalParam = {
				"products" : list.ExportData({ "Type" : "json" }).data,
				"relationProducts" : rltnGoodsSetupList.ExportData({ "Type" : "json" }).data
			};
			
			// 체크박스로 된 YN값 설정
			if($("#price-exception-change").is(":checked")) {
				abc.biz.product.utils.setNyIfSourceChecked("emp-dscnt-yn-n", "emp-dscnt-yn");						// 임직원 할인 제외
				abc.biz.product.utils.setYnIfSourceChecked("dprc-except-yn-y", "dprc-except-yn");					// 감가제외
			}
			if($("#delivery-exception-chnage").is(":checked")) {
				abc.biz.product.utils.setNyIfSourceChecked("store-pickup-psblt-yn-n", "store-pickup-psblt-yn");		// 매장픽업불가
//				abc.biz.product.utils.setYnIfSourceChecked("order-mnfct-yn-y", "order-mnfct-yn");					// 주문제작
			}
			abc.biz.product.utils.setZeroIfSourceChecked("buy-limit-yn-n", "min-buy-psblt-qty");					// 최소구매수량
			abc.biz.product.utils.setZeroIfSourceChecked("buy-limit-yn-n", "max-buy-psblt-qty");					// 최대 구매수량
			abc.biz.product.utils.setZeroIfSourceChecked("buy-limit-yn-n", "day-max-buy-psblt-qty");				// 1일 최대 구매수량
	
			if(!_object.regist.validate()) {
				// 검증 미통과
				_object.resource.isRunningNetwork = false;	// 네트워크 사용 플래그 해제
				return;
			}
			
			var option = {
					url			: "/product/product/update/bulk",
					type		: "POST",
					dataType	: "json",
					enctype		: "multipart/form-data",
					data		: $.paramObject(_object.regist.additionalParam),
					success		: function(data) {
						console.log(data);
						if(data) {
							alert("대상상품 " + _object.regist.additionalParam.products.length + "건의 상품정보가 일괄수정되었습니다");
							location.reload();
						}
					},
					error		: function(e) {
						if(e != null && e.message != null && e.message != "") {
							alert(e.message);
						}
						console.log(e);
					},
					complete	: function() {
						_object.resource.isRunningNetwork = false;	// 네트워크 사용 플래그 초기화
					}
				};
			$.form("#bulk-update-form").submit(option);
		}
	}
	
	$(function() {
		_object.init();
	});
	
})();
