(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.approval");

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
		createIBSheet2(document.getElementById("approval-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		// 상품승인대상관리 – 목록 정렬 항목 : 상품코드, 업체상품코드, 상품명, 입점사명, 등록일시, 승인처리일시
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 4,   Align:"Center",	Edit:1, Sort:0 },
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",					Width: 4,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"상품코드",			Type:"Text",		SaveName:"prdtNo",				Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"업체상품코드",		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"상품명",			Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"승인구분",			Type:"Combo",		SaveName:"prdtAprvReqCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.codeCombo.PRDT_APRV_REQ_CODE.text, ComboCode:_object.codeCombo.PRDT_APRV_REQ_CODE.code  },
			{ Header:"승인상태",			Type:"Combo",		SaveName:"aprvStatCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.codeCombo.APRV_STAT_CODE.text, ComboCode:_object.codeCombo.APRV_STAT_CODE.code },
			{ Header:"브랜드",			Type:"Text",		SaveName:"brandNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드",			Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"채널구분",			Type:"Text",		SaveName:"chnnlName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"표준카테고리",		Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"반려사유",			Type:"Text",		SaveName:"returnRsnText",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"등록자번호",			Type:"Text",		SaveName:"rgsterNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"입점사명",			Type:"Text",		SaveName:"vndrName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"등록자",			Type:"Text",		SaveName:"rgsterInfo",			Width: 10,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer" },
			{ Header:"등록일시",			Type:"Date",		SaveName:"rgstDtm",				Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"수정자",			Type:"Text",		SaveName:"moderInfo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"수정일시",			Type:"Date",		SaveName:"modDtm",				Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
//			{ Header:"승인처리자",			Type:"Text",		SaveName:"aprverInfo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
//			{ Header:"승인처리일시",		Type:"Date",		SaveName:"aprverDtm",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"채널 번호",			Type:"Text",		SaveName:"chnnlNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1  },
			{ Header:"사이트 번호",			Type:"Text",		SaveName:"siteNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1  },
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.SetColFontUnderline('prdtNo', 1);
		list.SetColFontUnderline('prdtName', 1);

		_object.sheet.list = list;

		_object.getList();

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if(list.ColSaveName(col) == 'prdtNo') {
					var product = list.GetRowData(row);
					var params = {
						  prdtNo : product.prdtNo
						, vndrPrdtNoText : product.vndrPrdtNoText
						, chnnlNo : product.chnnlNo
						, siteNo : product.siteNo
					};
					abc.open.popup({
						url 	:	"/product/approval/detail",
						winname :	"registration",
						method	: 	"get",
						title 	:	"prdt-aprv-mng(PO)",
						width 	:	1480,
						height	:	970,
						params	:	params
					});
				} else if(list.ColSaveName(col) == 'prdtName') {
					abc.productFrontUrl(list.GetRowData(row).prdtNo);
				}
			}
		}

		list_OnRowSearchEnd = function(row) {
			var item = list.GetRowData(row);
			// 승인요청 상태 체크박스 선택 불가
			if(item.aprvStatCode == abc.biz.product.approval.APRV_STAT_CODE_REQUEST) {
				_object.sheet.list.SetCellEditable(item.seq, 'updateCheck', 0);
			} else if(item.aprvStatCode == abc.biz.product.approval.APRV_STAT_CODE_REJECT) {
				_object.sheet.list.SetToolTipText(item.seq, 'aprvStatCode', item.returnRsnText);
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 상품승인대상관리(PO) 화면에서는 승인완료는 볼 수 없다.
		$('#aprv-stat-code-10004').closest('li').remove();

		// 승인상태
		var option = {
              allId: '#aprv-stat-code-all'
            , itemsClass: '.aprv-stat-code-all'
		}
		abc.biz.display.common.checkBoxAll(option);

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			abc.biz.product.utils.initFormData("search-form");
			$('input[name="searchKeywordType"]').val('prdt-name');
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.product.approval.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perToday" :
				abc.date.days(this);
				break;
			case "perWeek" :
				abc.date.week(this);
				break;
			case "per1Month" :
				abc.date.month(this);
				break;
			case "perYear" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 선택 상품 삭제
		$("#delete").click(function() {
			var checkedRows = abc.biz.product.approval.sheet.list.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var approvalList = [];
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				var item = abc.biz.product.approval.sheet.list.GetRowData(row);
				if(item.aprvStatCode == abc.biz.product.approval.APRV_STAT_CODE_CONFIRM) {
					alert(item.seq + "번째 항목은 승인완료 상태이므로 삭제가 불가합니다.");
					abc.biz.product.approval.sheet.list.SetCellValue(item.seq,'updateCheck',0);
					return false;
				} else if(item.aprvStatCode == abc.biz.product.approval.APRV_STAT_CODE_REQUEST) {
					alert(item.seq + "번째 항목은 승인요청 상태이므로 삭제가 불가합니다.");
					abc.biz.product.approval.sheet.list.SetCellValue(item.seq,'updateCheck',0);
					return false;
				}
				approvalList.push(item);
			}

			if(!confirm('선택한 상품을 삭제하시겠습니까? (Y/N)')) { return false; }

			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/approval/delete",
				data : $.paramObject(approvalList)
			})
			.done(function(data){
				alert('상품이 삭제되었습니다.');
				abc.biz.product.approval.getList();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		});

		// 선택 상품 승인요청
		$("#approval").click(function() {
			var checkedRows = abc.biz.product.approval.sheet.list.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var approvalList = [];
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				var item = abc.biz.product.approval.sheet.list.GetRowData(row);
				if(item.aprvStatCode == abc.biz.product.approval.APRV_STAT_CODE_CONFIRM) {
					alert(item.seq + "번째 항목은 승인완료 상태이므로 요청 불가합니다.");
					abc.biz.product.approval.sheet.list.SetCellValue(item.seq,'updateCheck',0);
					return false;
				}
				item.aprvStatCode = abc.biz.product.approval.APRV_STAT_CODE_REQUEST;
				approvalList.push(item);
			}

			if(!confirm('선택한 상품을  승인요청 하시겠습니까? (Y/N)')) { return false; }

			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/approval/modify",
				data : $.paramObject(approvalList)
			})
			.done(function(data){
				alert('승인요청 상태로 저장됩니다. 관리자 승인 후 판매/전시 가능합니다.');
				abc.biz.product.approval.getList();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		});

		// 시작기간~끝기간 설정
		abc.biz.product.utils.setSearchStartAndEndDate($('input[name="periodStartDate"]'), $('input[name="periodEndDate"]'));
		// $('a[name="per1Month"]').click();

		// 상품코드 20건까지 검색 제한
		abc.biz.product.utils.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);

		// 상품등록 팝업 버튼
		$("[data-button-popup='registry']").click(function() {
			abc.open.popup({
				url 	:	"/product/product/detail",
				winname :	"registration",
				method	: 	"get",
				title 	:	encodeURIComponent("상품 등록"),
				width 	:	1480,
				height	:	980,
				params	:	{ auth : "P", type : "registry" }
			});
		});

	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandName);
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		if (!abc.date.searchValidate()) {
			return false;
		}
		var url = "/product/approval";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	$(function() {
		_object.init();
	});

})();