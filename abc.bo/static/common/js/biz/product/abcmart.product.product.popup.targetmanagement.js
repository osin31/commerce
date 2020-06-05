(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product.popup.targetmanagement");
	var _common = abc.biz.product.utils;

	var initSheet = {};
	var noMsg = false;

	_object.resource = {
		option : {}
	}

	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');

		_object.resource.option = {
			name						: decodeURIComponent(abc.biz.product.utils.parameter.getParameter("name")),
			count						: abc.biz.product.utils.parameter.getParameter("count"),
			searchFormId				: abc.biz.product.utils.parameter.getParameter("searchFormId"),
			searchUrl					: abc.biz.product.utils.parameter.getParameter("searchUrl"),
			saveAdditionalParamFormId	: abc.biz.product.utils.parameter.getParameter("saveAdditionalParamFormId"),
			saveUrl						: abc.biz.product.utils.parameter.getParameter("saveUrl"),
		    returnFn					: abc.biz.product.utils.parameter.getParameter("returnFn"),
		    returnFnVal					: abc.biz.product.utils.parameter.getParameter("returnFnVal"),
			isHideSortSeq				: abc.biz.product.utils.parameter.getParameter("isHideSortSeq") === "true" ? true : false
		};

		$("td[data-area='name']").text(_object.resource.option.name);
		//$("td[data-area='count']").text(_object.resource.option.count);

		_object.sheet.init();
		_object.sheet.event();
		_object.event();
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		//abc.biz.product.utils.grid.create(_object.sheet, "product-list", "list", "100%", "429px", $('#page-count').val(), _object.resource.sheet.header.list);
		//list.Editable = true;
		//list.SetColHidden("sortSeq", _object.resource.option.isHideSortSeq);	// 인자값 sortSeq에 따른 노출순서 보이기/숨기기 처리

		initSheet.Cfg = {SearchMode:smServerPaging2, Sort:1, ColMove:0, ColResize:1};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initSheet.Cols = [
			{ Header:"",				Type:"Status",		SaveName:"status",						Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"checked",						Width: 5,	Align:"Center",	Edit:1, Cursor:"Pointer" },
//			{ Header:"",				Type:"DelCheck",	SaveName:"checked",						Width: 5,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"노출순서",			Type:"Int",			SaveName:"sortSeq",						Width: 5,	Align:"Center",	Edit:1, Cursor:"Pointer", Hidden:1 },
			{ Header:"온라인상품코드",		Type:"Text",		SaveName:"prdtNo",						Width: 5,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"브랜드",				Type:"Text",		SaveName:"brandName",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer"},
			{ Header:"상품명",				Type:"Text",		SaveName:"prdtName",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer"},
			{ Header:"상품코드",			Type:"Text",		SaveName:"vndrPrdtNoText",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지",			Type:"Text",		SaveName:"titleImageName",				Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지",			Type:"Text",		SaveName:"titleImagePathText",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지",			Type:"Image",		SaveName:"titleImageUrl",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
			{ Header:"표준카테고리",			Type:"Text",		SaveName:"stdCtgrName",					Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"전시채널",			Type:"Text",		SaveName:"chnnlName",					Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"판매상태",			Type:"Combo",		SaveName:"sellStatCode",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code },
			{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",						Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" }
		];

		createIBSheet2(document.getElementById("product-list"), "list", "100%", "429px");
		IBS_InitSheet(list , initSheet);

		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.Editable = true;
		list.SetColHidden("sortSeq", _object.resource.option.isHideSortSeq);	// 인자값 sortSeq에 따른 노출순서 보이기/숨기기 처리

		list.SetColFontUnderline("prdtNo", 1);			// 내용 밑줄

		_object.getList();
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){
//		list_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response){
//			var response = null;
//			var scFlag = false;
//			if(Response) {
//				response = JSON.parse(Response);
//				scFlag = response.success;
//				Msg = response.message;
//			}
//
//			console.log("list_OnSaveEnd > " + scFlag + " / " + Msg);
//			if(Msg != null && Msg != "") {
//				alert(Msg);
//			}
//			
//			if(scFlag) {
//				
//				/*
//				switch(btnEvent) {
//				case "remove-rows" :
//					alert("수정되었습니다.");
//					break;
//				case "save" :
//					alert("저장되었습니다.");
//					break;
//				}
//				*/
////				// callback 실행
////				var callback = abc.biz.product.utils.parameter.runCallback("returnFn");
////				if(typeof callback === "function") {
////					callback(_object.resource.option.returnFnVal);
////				}
//				//self.close();
//			} else {
//
//				if(noMsg) {
//					noMsg = false;
//				} else {
//					alert(Msg);
//				}
//
//			}
//			// callback 실행. 브랜드스타일에서 바닥페이지 조회를 하지 않아 위치가 변경됨.
//			var callback = abc.biz.product.utils.parameter.runCallback("returnFn");
//			if(typeof callback === "function") {
//				callback(_object.resource.option.returnFnVal);
//			}
//			
//		}

		// 선택이벤트
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			if(row != 0 && col != 0) {
				if(list.ColSaveName(col) === "prdtNo") {
					// 상품번호 클릭 시, 상품수정 팝업 띄움
					var param = {
						prdtNo	: list.GetRowData(row).prdtNo			// 상품번호
					};
					abc.open.popup({
						url 	:	"/product/product/detail",
						winname :	"product-detail-from-popup",
						method	: 	"get",
						title 	:	"product-detail-from-popup",
						width 	:	1480,
						height	:	970,
						params	:	param
					});
				} else if(list.ColSaveName(col) === "prdtName") {
					// 상품명 클릭 시, 프론트 상품상세 띄움
					abc.productFrontUrl(list.GetRowData(row).prdtNo, true);
				}
			}
		}

		// 조회 후
		list_OnSearchEnd = function(code, msg, stCode, stMsg, responseText) {
			$("td[data-area='count']").text(list.GetTotalRows());
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		$("[data-button]").click(function(e) {
			var type = $(this).data("button");

			switch(type) {
			case "add-products" :
				// 상품추가
				var data = {};
				var srchData = $(window.opener.document.forms[_object.resource.option.searchFormId]).serializeArray();
				$.each(srchData, function(i, o) {
					data[o.name] = o.value;
		        });
				window.abc.productSearchPopup(true, "abc.biz.product.product.popup.targetmanagement.callback.addProduct", false, data);
				break;
			case "remove-rows" :
				// 선택삭제
				var newRows = [];
				var checkedRows = list.FindCheckedRow("checked").split('|');
				for(var i=0; i<checkedRows.length; i++) {
					var row = checkedRows[i];
					var item = list.GetRowData(row);
					if(item.status === "I") {
						newRows.push(row);
					} else if(item.status === "U" || item.status === "R") {
						list.SetCellValue(row, "status", "D");
					}
				}

				if(newRows.length) { // 상품추가한 항목은 삭제
					list.RowDelete(newRows.join('|'));
				}

				var url = _object.resource.option.saveUrl;
				var param = {"Col" : "status"};
				if(abc.biz.product.utils.isNotEmpty(_object.resource.option.saveAdditionalParamFormId)) {
					// 저장 시 추가전송 파라미터가 있는 경우, 이를 설정함
					param["Param"] = $(window.opener.document.forms[_object.resource.option.saveAdditionalParamFormId]).serialize();
				}
				list.DoSave(url, param);
				break;
			case "save" :
				// 저장 전 점검
				var isValid = true;

				if(!_object.resource.option.isHideSortSeq) {
					// 정렬순서를 숨기지 않은 경우, 정렬순서 입력여부를 확인
					var data = list.ExportData({ "Type" : "json"});
					if(data && data.data) {
						$.each(data.data, function(i, v) {
							if(abc.biz.product.utils.isEmpty(v.sortSeq)) {
								isValid = false;
							}
						});
					}
				}

				if(!isValid) {
					alert("입력되지 않은 노출순서가 있습니다.");
					return;
				}

				// 저장
				var url = _object.resource.option.saveUrl;
				/*
				var param = {
					"Col" : "status",
					"CallBack" : function() {
						alert("저장되었습니다.");
						// callback 실행
						var callback = abc.biz.product.utils.parameter.runCallback("returnFn");
						if(typeof callback === "function") {
							callback(_object.resource.option.returnFnVal);
						}
						//self.close();
					}
				};
				*/
				var param = {"Col" : "status"};
				if(abc.biz.product.utils.isNotEmpty(_object.resource.option.saveAdditionalParamFormId)) {
					// 저장 시 추가전송 파라미터가 있는 경우, 이를 설정함
					param["Param"] = $(window.opener.document.forms[_object.resource.option.saveAdditionalParamFormId]).serialize();
				}
				param["CallBack"] = function(d) {
					console.log(d);
					alert(d.Message);
					
					// callback 실행. 브랜드스타일에서 바닥페이지 조회를 하지 않아 위치가 변경됨.
					var callback = abc.biz.product.utils.parameter.runCallback("returnFn");
					if(typeof callback === "function") {
						callback(_object.resource.option.returnFnVal);
					}
					location.reload();
				};

				list.DoSave(url, param);
				
				//_object.getList();
//				location.reload();
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			}
		});
	}

	/**
	 * callback 목록
	 */
	_object.callback = {
		addProduct :  function(data) {
			var rowData = null;
			for(var i=0 ; i<data.length ; i++) {
				rowData = {
					"prdtNo"				: data[i].prdtNo,
					"prdtName"				: data[i].prdtName,
					"vndrPrdtNoText"		: data[i].vndrPrdtNoText,
					"titleImageName"		: data[i].titleImageName,
					"titleImagePathText"	: data[i].titleImagePathText,
					"titleImageUrl"			: data[i].titleImageUrl,
					"titleImage"			: "",
					"stdCtgrName"			: data[i].stdCtgrName,
					"chnnlName"				: data[i].chnnlName,
					"sellStatCode"			: data[i].sellStatCode,
					"dispYn"				: data[i].dispYn,
					"sortSeq"				: list.RowCount() + 1
				};
				if(abc.biz.product.utils.isNotEmpty(data[i].titleImageUrl)) {
					// 이미지가 있는 경우, 이미지 설정
					var imageTag = "<img src=\"{URL}\" alt=\"{ALTERNATIVE_TEXT}\" width=\"20\" height=\"20\"/>";
					imageTag = imageTag.replace("{URL}", data[i].titleImageUrl);
					imageTag = imageTag.replace("{ALTERNATIVE_TEXT}", data[i].titleImageAltrnText);
					rowData["titleImage"] = imageTag;
				}
				_common.grid.util.addRow(list, rowData);
			}

			noMsg = true;
//			var url = _object.resource.option.saveUrl;
//			var param = {"Col" : "status", "Quest" : 0};
//			if(abc.biz.product.utils.isNotEmpty(_object.resource.option.saveAdditionalParamFormId)) {
//				// 저장 시 추가전송 파라미터가 있는 경우, 이를 설정함
//				param["Param"] = $(window.opener.document.forms[_object.resource.option.saveAdditionalParamFormId]).serialize();
//			}
//			list.DoSave(url, param);
		}
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		var param = {
				url : _object.resource.option.searchUrl,
				onePageRow : $("#page-count").val(),
				subparam : FormQueryStringEnc(window.opener.document.forms[_object.resource.option.searchFormId]),
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	$(function() {
		_object.init();
	});

})();
