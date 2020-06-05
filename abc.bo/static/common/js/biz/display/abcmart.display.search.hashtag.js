(function() {

	var _hashTag = abc.object.createNestedObject(window,"abc.biz.display.search.hashtag");

	_hashTag.searchFormInit = function() {
		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});

		// 캘린더 버튼 설정(1년)
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});

		//기간검색 세팅
		$("a[name^=per1Month]").trigger("click");

		// 해쉬태그 검색
		$("#hashTagSearch").click(function(){
			_hashTag.doActionHashTag("search");
		});

		// 초기화
		$("#hashTagReset").click(function(){
			$('#hashTagSearchForm')[0].reset();
			$("a[name^=per1Month]").trigger("click");
		});

		// 해쉬태그 등록
		$("#hashTagCreateForm").click(function() {
			var url = "/display/hashtag/read-detail?menuNo="+abc.param.getParams().menuNo;
			var param = FormQueryStringEnc(document.hashTagSearchForm);

			$(location).attr('href', url + "&" + param);
		});
	}

	/**
	 * 해쉬태그 목록 그리드 초기 세팅
	 */
	_hashTag.initHashTagSheet = function() {
		var initHashTagSheet = {};
		var pageCount = $('#pageCount').val();

		initHashTagSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initHashTagSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initHashTagSheet.Cols = [
				{Header:"", 				Type:"CheckBox",	SaveName:"",						Width: 20, 		Align:"Center",  				Hidden:1		}
			,	{Header:"", 				Type:"Text",		SaveName:"hshtgSeq",				Width: 20, 		Align:"Center",  				Hidden:1		}
			,	{Header:"순번",				Type:"Seq",			SaveName:"",						Width: 20,		Align:"Center",		Edit:0,		Sort:0 			}
			,	{Header:"전시채널",			Type:"Text",		SaveName:"siteName",				Width: 50,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"해시태그명",			Type:"Text",		SaveName:"hshtgName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	Cursor:"Pointer"}
			,	{Header:"사용여부",			Type:"Combo",		SaveName:"useYn",					Width: 30,		Align:"Center",		Edit:0,		Sort:0,		ComboText : "사용|사용안함",		ComboCode : "Y|N"}
			,	{Header:"사용기간",			Type:"Text",		SaveName:"dispTerm",				Width: 100,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상품개수",			Type:"Int",			SaveName:"prdtCnt",					Width: 40,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"수정자",				Type:"Text",		SaveName:"maskingModerName",		Width: 50,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"수정일시",			Type:"Date",		SaveName:"modDtm",					Width: 0,		Align:"Center",		Edit:0,		Sort:0 			}
		];

		createIBSheet2(document.getElementById("hashTagGrid"), "hashTagSheet", "100%", "429px");
		IBS_InitSheet(hashTagSheet, initHashTagSheet);

		// Grid 건수 정보 표시
		hashTagSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		hashTagSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		hashTagSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		hashTagSheet.SetExtendLastCol(1);
	}

	_hashTag.hashTagCreateInit = function() {
		$("#hashTagPrdtSearch").click(function() {
			abc.productSearchPopup(true, "abc.biz.display.search.hashtag.hashTagProductSheetAppend");
		});

		$("#hashTagProdtDel").click(function() {
			abc.biz.display.search.hashtag.hashTagProductSheetDelete();
		});

		$("#hashTagSave").click(function() {
			abc.biz.display.search.hashtag.doActionHashTag("save");
		});

		$("#hashTagList").click(function() {
			$(location).attr('href', "/display/hashtag?menuNo="+abc.param.getParams().menuNo);
		});
	}

	/**
	 * 해쉬태그 등록 페이지 Sheet상품 추가 콜백 함수
	 */
	_hashTag.hashTagProductSheetAppend = function(data) {
		var sheet = hashTagProductSheet; // 해쉬태그 상품 목록 그리드
		var list = sheet.ExportData({"Type": "json", "Cols" : "prdtNo"}).data; // 해쉬태그 상품 목록에 추가된 상품 번호 리스트

		console.log(data);

		if (! abc.text.allNull(list)) { // 해쉬태그 상품 목록에 상품정보가 있을 경우
			$.each(data, function(idx, item) {
				if(! _hashTag.sheetProductDupCheck(list, item.prdtNo)) { // 추가 상품 중복 체크
					_hashTag.sheetDataAppend(sheet, item);
				};
			});
		} else { // 해쉬태그 상품 목록에 상품 정보가 없을 경우
			var sheetRowNo = 0;
			var rowOption = { Add : 1 };
			$.each(data, function(idx, item) {
				_hashTag.sheetDataAppend(sheet, item);
			});

		}
	}

	/**
	 * 추가 상품 중복 체크
	 */
	_hashTag.sheetProductDupCheck = function(sheetPrdtNoList, prdtNo) {
		var dupYn = false;
		$.each(sheetPrdtNoList, function(idx, sheetPrdt) {
			if (sheetPrdt.prdtNo == prdtNo) {
				dupYn = true;
				return;
			}
		})
		return dupYn;
	}

	/**
	 * 해쉬태그 상품 그리드에 상품 추가
	 */
	_hashTag.sheetDataAppend = function(sheet, item) {
		var sheetRowNo = sheet.RowCount() + 1;
		var rowOption = { Add : 1 };
		sheet.SetRowData(sheetRowNo, item, rowOption);
/*		var product = {
				"chnnlName"       : item.chnnlName      ,
				"prdtNo"       	  : item.prdtNo	        ,
				"titleImageUrl"   : item.titleImageUrl  ,
				"prdtName"     	  : item.prdtName       ,
				"brandName"    	  : item.brandName      ,
				"sellStatCode"	  : item.sellStatCode   ,
				"dispYn"       	  : item.dispYn	        ,
				"stdCtgrName"  	  : item.stdCtgrName    ,
				"stdrCtgrName"	  : item.stdrCtgrName   ,
				"vndrName"     	  : item.vndrName
		};
		sheet.SetRowData(sheetRowNo, product, rowOption);*/
	}

	/**
	 * 그리드에서 선택 항목 삭제 처리
	 */
	_hashTag.hashTagProductSheetDelete = function() {
		if (! confirm("선택한 상품을 하시겠습니까?")) {
			return;
		}

		var sheet = hashTagProductSheet; // 해쉬태그 상품 목록 그리드
		var checkedRows = sheet.FindCheckedRow("checkYN"); // '|'로 구분된 해쉬태그 상품 목록에 추가된 상품 번호 리스트

		if (abc.text.allNull(checkedRows)) {
			alert("삭제할 상품을 선택하세요.");
			return;
		} else {
			sheet.RowDelete(checkedRows);
		}
	}

	/**
	 * 해쉬태그 상세 화면 상품 목록 그리드
	 */
	_hashTag.initHashTagProductSheet = function() {
		var productSellStateCode = abc.biz.display.search.hashtag.sellStateCode;
		var initHashTagProductSheet = {};
		var pageCount = $('#pageCountProduct').val();

		initHashTagProductSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initHashTagProductSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initHashTagProductSheet.Cols = [
				{Header:"", 				Type:"CheckBox",	SaveName:"checkYN",					Width: 20, 		Align:"Center",  				Sort:0			}
			,	{Header:"",					Type:"Status",		SaveName:"status",					Width: 0,		Align:"Center",		Edit:0, 	Hidden:1 		}
			,	{Header:"", 				Type:"Text",		SaveName:"hshtgSeq",				Width: 20, 		Align:"Center",  				Hidden:1		}
			,	{Header:"순번",				Type:"Seq",			SaveName:"",						Width: 20,		Align:"Center",		Edit:0,		Sort:0 			}
			,	{Header:"전시채널",			Type:"Text",		SaveName:"chnnlName",				Width: 40,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상품코드",			Type:"Text",		SaveName:"prdtNo",					Width: 50,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"상품이미지",			Type:"Image",		SaveName:"titleImageUrl",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, 	ImgWidth: 60,	ImgHeight: 60	}
			,	{Header:"상품명",				Type:"Text",		SaveName:"prdtName",				Width: 100,		Align:"Left",		Edit:0,		Sort:0, 	FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"브랜드",				Type:"Text",		SaveName:"brandName",				Width: 40,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"판매상태",			Type:"Combo",		SaveName:"sellStatCode",			Width: 50,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",					Width: 0,		Align:"Center",		Edit:0,		Sort:0,		ComboText : "전시|미전시|",		ComboCode : "Y|N|"}
			,	{Header:"표준카테고리",			Type:"Text",		SaveName:"stdCtgrName",				Width: 0,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"전시카테고리",			Type:"Text",		SaveName:"stdrCtgrName",			Width: 0,		Align:"Center",		Edit:0,		Sort:0			}
			,	{Header:"등록업체명",			Type:"Text",		SaveName:"vndrName",				Width: 0,		Align:"Center",		Edit:0,		Sort:0			}
		];

		createIBSheet2(document.getElementById("hashTagProductGrid"), "hashTagProductSheet", "100%", "430px");
		IBS_InitSheet(hashTagProductSheet, initHashTagProductSheet);
		hashTagProductSheet.InitDataCombo(0 , "sellStatCode" , productSellStateCode.SELL_STAT_CODE.text , productSellStateCode.SELL_STAT_CODE.code  );
		// Grid 건수 정보 표시
		hashTagProductSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		hashTagProductSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		hashTagProductSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		hashTagProductSheet.SetExtendLastCol(1);
	}

	_hashTag.doActionHashTag = function(sAction) {
		var _url = "/display/hashtag/";
		switch(sAction) {
			case "search" :
				if(!abc.date.searchValidate()){
					return false;
				}

				if(abc.text.allNull($("#fromDate").val()) || abc.text.allNull($("#toDate").val()) ){
					alert("기간을 선택하세요");
					$("#fromDate").focus();
					return false;
				}

				var pageCount = $('#pageCount').val();

				var param = { url : _url + "read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.hashTagSearchForm)
					, sheet : "hashTagSheet" };

				DataSearchPaging(param);

				break;
			case "save" :
				var sheet = hashTagProductSheet; // 해쉬태그 상품 목록 그리드
				var list = sheet.ExportData({"Type": "json", "Cols" : "prdtNo"}).data; // 해쉬태그 상품 목록에 추가된 상품 번호 리스트
				var hshtgSeq = $("input[name='hshtgSeq']").val(); // 해쉬태그 순번
				var prdtNoArr = [];
				var notValidPrdtNoArr = [];
				var method = abc.text.allNull(hshtgSeq) ? "create" : "modify";

				if(abc.text.allNull(($("input[name='siteNo']:checked").val()))) {
					alert("사이트 구분을 선택하세요.");
					return;
				};

				if(abc.text.allNull(($("input[name='useYn']:checked").val()))) {
					alert("사용여부를 선택하세요.");
					return;
				};

				if(abc.text.allNull(($("input[name='useYn']:checked").val()))) {
					alert("사용여부를 선택하세요.");
					return;
				};

				if(abc.text.allNull(($("input[name='hshtgName']").val()))) {
					alert("해쉬태그명을 입력하세요.");
					$("input[name='hshtgName']").focus();
					return;
				};

				if (abc.text.isLimitLength($("input[name='hshtgName']").val(), 50)) {
					alert("해쉬태그명은 한글(2Byte)포함 최대 50자리까지 입력 가능 합니다.");
					$("input[name='hshtgName']").focus();
					return;
				}

				if(abc.text.allNull(($("input[name='dispStartYmd']").val()))) {
					alert("사용 시작일자를 선택하세요.");
					$("input[name='dispStartYmd']").focus();
					return;
				};

				if(abc.text.allNull(($("input[name='dispEndYmd']").val()))) {
					alert("사용 종료일자를 선택하세요.");
					$("input[name='dispEndYmd']").focus();
					return;
				};

				if(!abc.date.searchValidate()){
					return false;
				}

				$.each(list, function(idx, prdt) {
					if ($.isNumeric(prdt.prdtNo)) {
						prdtNoArr.push(prdt.prdtNo);
					} else {
						notValidPrdtNoArr.push(prdt.prdtNo);
					}
				});

				var confirmMsg = "";
				if (notValidPrdtNoArr != null && notValidPrdtNoArr.length > 0) {
					confirmMsg = "상품 코드는 숫자만 입력 가능 합니다.\n";

					$.each(notValidPrdtNoArr, function(idx, notVaildPrdtNo) {
						confirmMsg += "상품 코드 : " + notVaildPrdtNo + "\n";
					});

					confirmMsg += "위 상품 코드를 제외 하고 "
				}

				confirmMsg += "등록(수정)된 내용을 저장하시겠습니까?";

				if (prdtNoArr == null || prdtNoArr.length <= 0) {
					alert("등록 상품 정보가 없습니다.");
					return;
				}

				$("input[name='prdtNoArr']").val(prdtNoArr);

				if(confirm(confirmMsg)) {
					$.ajax({
						tyle : "post",
						url  : _url + method,
						data : $("#hashTagForm").serialize()
					}).done(function(data) {
						//alert("저장 되었습니다.");
						var hshtgSeq = data.hshtgSeq;
						var message  = data.Message;
						if (data.code == abc.consts.BOOLEAN_TRUE) {
							alert(message);
							$(location).attr('href', _url + "read-detail" + "?menuNo="+abc.param.getParams().menuNo + "&hshtgSeq=" + hshtgSeq);
						} else {
							alert(message);
						}
					}).fail(function(e) {
						alert("등록(수정)된 내용을 적용하지 못했습니다.");
					});
				}

				break;
			case "productSearch" :
				if (abc.text.allNull($("input[name='hshtgSeq']").val())) {
					return false;
				}
				var pageCount = $('#pageCountProduct').val();
				
				var param = { url : _url + "product/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.hashTagForm)
					, sheet : "hashTagProductSheet" };

				DataSearchPaging(param);

				break;

			case "excelUpLoadProductSearch" :
				var prdtNoArr = [];
				var sheet = hashTagProductSheet; // 해쉬태그 상품 목록 그리드
				var list = sheet.ExportData({"Type": "json", "Cols" : "prdtNo"}).data; // 해쉬태그 상품 목록에 추가된 상품 번호 리스트

				$.each(list, function(idx, prdt) {
					prdtNoArr.push(prdt.prdtNo);
				});

				$("input[name='prdtNoArr']").val(prdtNoArr);

				var param = { url : _url + "product/excelupload-read-list"
						, subparam : FormQueryStringEnc(document.hashTagForm)
						, sheet : "hashTagProductSheet" };

				DataSearchPaging(param);

				break;


		}
	}
	
	//페이지선택시
	$("#pageCountProduct").change(function(){
		_hashTag.doActionHashTag('productSearch');
	});

})();