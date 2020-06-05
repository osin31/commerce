(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product.popup.history");
	
	_object.resource = {
		type : "",
		title : "",
		name : "",
		prdtNo : "",
		mmnyPrdtYn : "",
		sheet : {
			header : {
				priceHistory : [
					{ Header:"",				Type:"Status",	SaveName:"status",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",		SaveName:"prdtPriceHistSeq",	Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"적용기간(시작)",		Type:"Date",	SaveName:"applyStartDtm",		Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"적용기간(종료)",		Type:"Date",	SaveName:"applyEndDtm",			Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"정상가",			Type:"Text",	SaveName:"normalAmt",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"판매가",			Type:"Text",	SaveName:"sellAmt",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"오프라인할인율(%)",	Type:"Text",	SaveName:"erpDscntRate",		Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:0 },
					{ Header:"온라인할인율(%)",	Type:"Text",	SaveName:"onlnDscntRate",		Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"임직원할인율(%)",	Type:"Text",	SaveName:"empDscntRate",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"등록자번호",		Type:"Text",	SaveName:"rgsterNo",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"등록자ID",			Type:"Text",	SaveName:"rgsterId",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"등록자ID",			Type:"Text",	SaveName:"rgsterName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"등록자",			Type:"Text",	SaveName:"rgsterInfo",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"등록일시",			Type:"Date",	SaveName:"rgstDtm",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자번호",		Type:"Text",	SaveName:"moderNo",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자ID",			Type:"Text",	SaveName:"moderId",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자ID",			Type:"Text",	SaveName:"moderName",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자",			Type:"Text",	SaveName:"moderInfo",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"수정일시",			Type:"Date",	SaveName:"modDtm",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				],
				productHistory : [
					{ Header:"",				Type:"Status",		SaveName:"status",					Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",			SaveName:"chngHistSeq",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"변경컬럼",			Type:"Text",		SaveName:"chngField",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"변경항목",			Type:"Text",		SaveName:"chngFieldName",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"변경 전 정보",		Type:"Text",		SaveName:"chngBeforeFieldValue",	Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"변경 후 정보",		Type:"Text",		SaveName:"chngAfterFieldValue",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"수정자번호",		Type:"Text",		SaveName:"rgsterNo",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자ID",			Type:"Text",		SaveName:"rgsterId",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자ID",			Type:"Text",		SaveName:"rgsterName",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"수정자",			Type:"Text",		SaveName:"rgsterInfo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"수정일시",			Type:"Date",		SaveName:"rgstDtm",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				],
				approvalHistoryBo : [
					{ Header:"",				Type:"Status",		SaveName:"status",					Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"변경이력번호",		Type:"Int",			SaveName:"prdtAprvHistSeq",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인자번호",		Type:"Text",		SaveName:"aprverNo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인자아이디",		Type:"Text",		SaveName:"aprverId",				Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"승인자이름",		Type:"Text",		SaveName:"aprverName",				Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"승인자정보",		Type:"Text",		SaveName:"aprverInfo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인일시",			Type:"Date",		SaveName:"aprverDtm",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				],
				approvalHistoryPo : [
					{ Header:"",				Type:"Status",		SaveName:"status",					Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",			SaveName:"prdtAprvHistSeq",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인구분",			Type:"Combo",		SaveName:"prdtAprvReqCode",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : commonCode.PRDT_APRV_REQ_CODE.text,	ComboCode : commonCode.PRDT_APRV_REQ_CODE.code },
					{ Header:"승인상태",			Type:"Combo",		SaveName:"prdtAprvStatCode",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : commonCode.PRDT_APRV_STAT_CODE.text,	ComboCode : commonCode.PRDT_APRV_STAT_CODE.code },
					{ Header:"승인요청자번호",		Type:"Text",		SaveName:"reqtrNo",					Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인요청자ID",		Type:"Text",		SaveName:"reqtrId",					Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인요청자명",		Type:"Text",		SaveName:"reqtrName",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인요청자",		Type:"Text",		SaveName:"reqtrInfo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인요청일시",		Type:"Date",		SaveName:"reqDtm",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인자번호",		Type:"Text",		SaveName:"aprverNo",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인자ID",			Type:"Text",		SaveName:"aprverId",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인자명",			Type:"Text",		SaveName:"aprverName",				Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"승인자",			Type:"Text",		SaveName:"aprverInfo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"승인상태 변경일시",	Type:"Date",		SaveName:"aprverDtm",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				]
			}
		}
	}
	
	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');
		
		_object.resource.type = abc.biz.product.utils.parameter.getDecodedParameter("type");
		_object.resource.title = abc.biz.product.utils.parameter.getDecodedParameter("title");
		_object.resource.name = abc.biz.product.utils.parameter.getDecodedParameter("name");
		_object.resource.prdtNo = abc.biz.product.utils.parameter.getDecodedParameter("prdtNo");
		_object.resource.mmnyPrdtYn = abc.biz.product.utils.parameter.getDecodedParameter("mmnyPrdtYn");
		$("#title").text(_object.resource.title);	// 타이틀 설정
		$("#name").text(_object.resource.name);	// 이름 설정
		
		_object.sheet.init();
		_object.sheet.event();
		_object.event();
	}
	
	_object.sheet = {};
	_object.sheet.init = function() {
		switch(_object.resource.type) {
		case "price":
			if(_object.resource.mmnyPrdtYn != undefined && _object.resource.mmnyPrdtYn === "N") {
				for(var i=0 ; i<_object.resource.sheet.header.priceHistory.length ; i++) {
					if(_object.resource.sheet.header.priceHistory[i].SaveName === "erpDscntRate") {
						_object.resource.sheet.header.priceHistory[i].Hidden = 1;	// 자사상품이 아닌 경우, 오프라인할인율 컬럼 숨김
						break;
					}
				}
			}
			abc.biz.product.utils.grid.create(_object.sheet, "list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.priceHistory);
			break;
		case "product":
			abc.biz.product.utils.grid.create(_object.sheet, "list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.productHistory);
			break;
		case "approval-bo":
			abc.biz.product.utils.grid.create(_object.sheet, "list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.approvalHistoryBo);
			break;
		case "approval-po":
			abc.biz.product.utils.grid.create(_object.sheet, "list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.approvalHistoryPo);
			break;
		default :
			console.log("알 수 없는 유형입니다. " + _object.resource.type);
			//window.self.close();
		}
		
		_object.getList(_object.resource.type);
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){
		list_OnRowSearchEnd = function(row) {
			switch(_object.resource.type) {
			case "price":
				// 가격이력
				// 할인율 정보를 기반으로 정상가에서 할인금액 계산 및 입력
				var headers = ["erpDscntRate", "onlnDscntRate", "empDscntRate"];
				for(var i=0 ; i<headers.length ; i++) {
					var dscntRate = list.GetRowData(row)[headers[i]];
					var normalAmt = list.GetRowData(row)["normalAmt"];
					
					if(dscntRate !== "") {
						var discountPrice = normalAmt * (dscntRate / 100);
						var calculated = normalAmt - discountPrice;
						dscntRate += "% / ";
						dscntRate += abc.biz.product.utils.number.unit.won(abc.biz.product.utils.number.comma(calculated));
						var modifiedRowData = {};
						modifiedRowData[headers[i]] = dscntRate;
						list.SetRowData(row, modifiedRowData);
					}
				}
				// 단위 "원" 입력
				headers = ["normalAmt", "sellAmt"];
				for(var i=0 ; i<headers.length ; i++) {
					var price = list.GetRowData(row)[headers[i]];
					if(abc.biz.product.utils.isNotEmpty(price)) {
						var modifiedRowData = {};
						modifiedRowData[headers[i]] = abc.biz.product.utils.number.unit.won(abc.biz.product.utils.number.comma(price));
						list.SetRowData(row, modifiedRowData);
					}
				}
				abc.biz.product.utils.grid.setRgsterInfo(list, row);	// 등록자이름 설정
				abc.biz.product.utils.grid.setModerInfo(list, row);	// 수정자이름 설정
				break;
			case "product":
				// 변경이력
				var chngField = list.GetRowData(row).chngField;	// 변경필드 컬럼명
				var postfixText = "";	// 공통코드 접미사
				var codeIndex = -1;	// 변경필드 내 공통코드 접미사 시작위치
				
				// 공통코드 변환 작업
				postfixText = "_CODE";	// 공통코드 접미사
				textIndex = chngField.lastIndexOf(postfixText);	// 변경필드 내 공통코드 접미사 시작위치
				if(textIndex > 0 && (textIndex === chngField.length - postfixText.length)) {
					// 코드인덱스가 있고 마지막으로 끝나는 경우, 공통코드값 설정
					if(commonCode[chngField] != undefined) {
						var codeList = commonCode[chngField].code.split("|");	// 공통코드목록 배열화
						var textList = commonCode[chngField].text.split("|");	// 공통코드값목록 배열화
						var beforeFieldValue = list.GetRowData(row).chngBeforeFieldValue;	// 변경전 필드값 (공통코드)
						var afterFieldValue = list.GetRowData(row).chngAfterFieldValue;	// 변경후 필드값 (공통코드)
						var isBreak = 0;
						
						for(var i=0 ; i<codeList.length ; i++) {
							if(beforeFieldValue === codeList[i]) {
								// 변경전 필드값 변경
								list.SetCellValue(row, "chngBeforeFieldValue", textList[i]);
								isBreak++;
							}
							if(afterFieldValue === codeList[i]) {
								// 변경후 필드값 변경
								list.SetCellValue(row, "chngAfterFieldValue", textList[i]);
								isBreak++;
							}
							if(isBreak === 2) {
								// 모두 변환한 경우, 반복문 탈출
								break;
							}
						}
					}
				}
				
				// 금액 변환작업
				postfixText = "_AMT";	// 공통코드 접미사
				textIndex = chngField.lastIndexOf(postfixText);	// 변경필드 내 공통코드 접미사 시작위치
				if(textIndex > 0 && (textIndex === chngField.length - postfixText.length)) {
					var beforeFieldValue = list.GetRowData(row).chngBeforeFieldValue;	// 변경전 필드값 (숫자)
					var afterFieldValue = list.GetRowData(row).chngAfterFieldValue;	// 변경후 필드값 (숫자)
					list.SetCellValue(row, "chngBeforeFieldValue", abc.biz.product.utils.number.unit.won(abc.biz.product.utils.number.comma(beforeFieldValue)));
					list.SetCellValue(row, "chngAfterFieldValue", abc.biz.product.utils.number.unit.won(abc.biz.product.utils.number.comma(afterFieldValue)));
				}
				
				abc.biz.product.utils.grid.setRgsterInfo(list, row);	// 등록자이름 설정
				
				//일괄수정일 경우 빈칸 대신 - 로 표현
				if(chngField == "BULK_UPDATE"){
					list.SetCellValue(row, "chngBeforeFieldValue", "-");
					list.SetCellValue(row, "chngAfterFieldValue", "-");
				} 
				break;
			case "approval-bo":
				// 승인이력 (BO권한)
				//list_OnRowSearchEnd = function(row) {
					abc.biz.product.utils.grid.setUserInfo(list, row, "aprverInfo", "aprverId", "aprverName");	// 승인자명 설정
				//}
				break;
			case "approval-po":
				// 승인이력 (PO권한)
				//list_OnRowSearchEnd = function(row) {
					abc.biz.product.utils.grid.setUserInfo(list, row, "reqtrInfo", "reqtrId", "reqtrName");	// 승인요청자명 설정
					abc.biz.product.utils.grid.setUserInfo(list, row, "aprverInfo", "aprverId", "aprverName");	// 승인자명 설정
				//}
				break;
			default :
				console.log("유형을 추가해야 합니다. " + _object.resource.type);
			}
		}
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList(_object.resource.type);
		});
		
		// 확인버튼
		$("[data-a]").click(function(e) {
			var type = $(this).data("a");
			switch(type) {
			case "close":
				// 확인
				window.self.close();
				break;
			default:
				console.log("알 수 없는 유형입니다. " + type);
			}
		});
	}
	
	/**
	 * 목록 조회
	 */
	_object.getList = function(type) {
		var param = null;
		
		switch(type) {
		case "price" :
			// 상품가격이력 조회
			param = {
				url : "/product/product/history/price",
				onePageRow : $("#page-count").val(),
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "list"
			};
			break;
		case "product" :
			// 상품변경이력 조회
			param = {
				url : "/product/product/history/product",
				onePageRow : $("#page-count").val(),
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "list"
			};
			break;
		case "approval-bo" :
			// 상품승인이력 조회
			param = {
				url : "/product/product/history/approval",
				onePageRow : $("#page-count").val(),
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "list"
			};
			break;
		case "approval-po" :
			// 상품승인이력 조회
			param = {
				url : "/product/product/history/approval",
				onePageRow : $("#page-count").val(),
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "list"
			};
			break;
		default :
			console.log("지원하지 않는 유형입니다. " + type);
			return;
		}
		DataSearchPaging(param);
	}
	
	$(function() {
		_object.init();
	});
	
})();