(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.mass.product");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
		_object.sheet.init();
		_object.sheet.event();
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("mass-product-list"), "list", "100%", "429px");

		var initSheet = {};
		initSheet.Cfg = { SearchMode:smLazyLoad, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		initSheet.Cols = [
			{ Header:"",				Type:"Status",		SaveName:"status",				Width: 10,	Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 5,   Align:"Center",	Edit:1, Sort:0 },
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",					Width: 6,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"사이트번호",			Type:"Text",		SaveName:"siteNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"사이트구분",			Type:"Combo",		SaveName:"siteName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : codes.SITE_NO.text,	ComboCode : codes.SITE_NO.code },
			{ Header:"상품코드",			Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"상품명(국문)",		Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"상품명(영문)",		Type:"Text",		SaveName:"engPrdtName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"브랜드번호",			Type:"Text",		SaveName:"brandNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드",			Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"스타일",			Type:"Text",		SaveName:"styleInfo",			Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"색상코드",			Type:"Text",		SaveName:"prdtColorCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"색상코드",			Type:"Text",		SaveName:"prdtColorCodeName",	Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"표준카테고리번호",		Type:"Text",		SaveName:"stdCtgrNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"표준카테고리",		Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"전시채널",			Type:"Combo",		SaveName:"chnnlNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : codes.CHNNL_NO.text + '|A-RT',	ComboCode : codes.CHNNL_NO.code + '|10000' },
			{ Header:"전시카테고리번호",		Type:"Text",		SaveName:"stdrCtgrNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"전시카테고리",		Type:"Text",		SaveName:"stdrCtgrName",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",				Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"판매상태",			Type:"Combo",		SaveName:"sellStatCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code },
			{ Header:"판매시작일",			Type:"Date",		SaveName:"sellStartDtm",		Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"판매종료일",			Type:"Date",		SaveName:"sellEndDtm",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"정상가",			Type:"Text",		SaveName:"normalAmt",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"판매가",			Type:"Text",		SaveName:"sellAmt",				Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"예약상품여부",		Type:"Combo",		SaveName:"rsvPrdtYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", ComboText : "예약|일반",	ComboCode : "Y|N" },
			{ Header:"품목코드",			Type:"Text",		SaveName:"itemCode",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"제조사",			Type:"Text",		SaveName:"mnftrName",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"제조국/원산지",		Type:"Text",		SaveName:"orgPlaceCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"색상정보",			Type:"Text",		SaveName:"prdtColorInfo",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"임직원할인여부",		Type:"Text",		SaveName:"empDscntYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"무료배송여부",		Type:"Text",		SaveName:"freeDlvyYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"상품아이콘코드",		Type:"Text",		SaveName:"insdMgmtInfoText",	Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"검색가능여부",		Type:"Text",		SaveName:"srchPsbltYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"검색키워드",			Type:"Text",		SaveName:"srchKeyWordText",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"사이즈가이드코드",		Type:"Text",		SaveName:"sizeChartSeq",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"색상코드",			Type:"Text",		SaveName:"prdtColorCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"구매제한여부",		Type:"Text",		SaveName:"buyLimitYn",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"최소구매가능수량",		Type:"Text",		SaveName:"minBuyPsbltQty",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"1일최대구매가능수량",	Type:"Text",		SaveName:"dayMaxBuyPsbltQty",	Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"최대구매가능수량",		Type:"Text",		SaveName:"maxBuyPsbltQty",		Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 },

		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		_object.sheet.list = list;
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function() {
		/**
		 * 정렬 전 파일여부 체크
		 */
		list_OnBeforeSort = function(col) {
			if(abc.text.isBlank($('#excel-file').val())) {
				alert('파일을 업로드해주세요.');
				return false;
			} else if(0 == list.RowCount()) {
				alert('업로드 된 항목이 없습니다.');
				return false;
			}
		}

		/**
		 * 정렬
		 */
		list_OnSort = function(col, sortOrder) {

		}

		/**
		 * 저장 이후
		 */
		list_OnSaveEnd = function(Code, Msg, StCode, StMsg) {
			console.log(Code);
			console.log(Msg);
			console.log(StCode);
			console.log(StMsg);
			if(200 == StCode) {
				alert("상품등록을 성공하였습니다.");
			} else {
				alert("상품등록을 실패하였습니다.");
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		// 찾아보기
		$('#excel-file').off().on('change', function(e) {
			if(!_object.fileValidate(this.files[0])) {
				$('.btn-file-del').click();
				return false;
			}
			$('.subject').text(this.files[0].name);
			$('.btn-file-del').show();

		});

		// 파일 삭제
		$('.btn-file-del').off().on('click', function(e) {
			$('.subject').text('선택된 파일 없음');
			$('.btn-file-del').hide();
			$('#excel-file').val('');
		});

		// 엑셀폼 다운로드
		$('#download-excel').off().on('click', function(e){
			$("#download-form").submit();
		});

		// 파일 업로드
		$('#upload-file').off().on('click', function(e){
			if(!_object.fileValidate(document.getElementById('excel-file').files[0])) {
				return false;
			}

			$.form("#upload-form").submit({
				url : "/product/mass-product/upload/excel",
				type : "POST",
				dataType : "json",
				success : function(data) {

					$('#cnt-total').html(data.excelList.length);
					$('#cnt-error').html( (data.excelList.length - data.normalList.length) );
					$('#cnt-normal').html(data.normalList.length);

					_object.normalList = data.normalList; // 정상 목록

					list.RemoveAll();
					for(var i=0; i<data.normalList.length; i++) {
						var item = data.normalList[i];
						list.SetRowData(list.RowCount() + 1, item, { Add : 1 });
					}

					if(data.errorMsg != "") {
						alert(data.errorMsg);
					}

				},
				error : function(e) {
					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
						_object.reset();
					}
					console.log(e);
				}
			})
		});

		// 일괄등록
		$('#batch-save').off().on('click', function(e){
			if(!_object.fileValidate(document.getElementById('excel-file').files[0])) {
				return false;
			}

			var rowCount = list.RowCount();
			if(rowCount < 1) {
				alert('등록할 상품이 없습니다.');
				return false;
			}else{
				alert(rowCount + '건의 상품 등록을 시도합니다.');
			}

			list.DoSave('/product/mass-product/save', {
				CallBack : function() {
					_object.reset();
				}
				, Sync : 2 // 동기 방식
				, Quest : 0 // 저장 시 메세지 사용 여부 N
			});
			/*
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/mass-product/save",
				data : _object.normalList
				//data : JSON.stringify(_object.normalList)
			})
			.done(function(data){
				alert(rowCount + '건의 상품이 등록되었습니다.');
				_object.reset();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
			 */

		});

		// 선택삭제
		$('#delete').off().on('click', function(e){
			var checkedRows = list.FindCheckedRow("updateCheck");
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }

			for(var i=0; i<checkedRows.split('|').length; i++) {
				delete _object.normalList[checkedRows[i]-1];
			}

			list.RowDelete(checkedRows);
		});
	}

	/**
	 * 초기화
	 */
	_object.reset = function() {
		list.RemoveAll();
		$('.btn-file-del').click();
		$('.cnt.tc-red').text('0');
	}

	/**
	 * 파일 validate
	 */
	_object.fileValidate = function(file) {
		var bool = false;
		if(!file) {
			alert('파일 선택 후 파일 업로드를 진행해주세요.');
			return bool;
		}
		var fileName = file.name;
		var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		if(ext != 'xlsx' && ext != 'xls') {
			alert('등록 가능한 확장자명은 xlsx, xls입니다.');
			return bool;
		}

		bool = true;
		return bool;
	}


	$(function() {
		_object.init();
	});

})();