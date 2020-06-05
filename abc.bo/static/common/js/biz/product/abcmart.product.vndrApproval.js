(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.vndrApproval");

	/**
	 * 초기화
	 */
	_object.init = function() {
		
		abc.biz.display.common.searchFormCalendarBtnEvent('input[name=periodStartDate]', 'input[name=periodEndDate]', '.area-calendar-btn-group');
		$(".calendar-month").click();
		
		_object.event();
		_object.sheet.init();
		_object.sheet.event();
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("vndrApproval-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		// 입점상품승인관리 – 목록 정렬 항목 : 상품코드, 업체상품코드, 상품명, 입점사명, 등록일시, 승인처리일시
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 4,   Align:"Center", Edit:1, Sort:0},
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",					Width: 4,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"상품코드",			Type:"Text",		SaveName:"prdtNo",				Width: 10,	Align:"Center",	Edit:0, Sort:1, Cursor:"Pointer" },
			{ Header:"업체상품코드",		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"상품명",			Type:"Text",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"승인구분",			Type:"Combo",		SaveName:"prdtAprvReqCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.codeCombo.PRDT_APRV_REQ_CODE.text, ComboCode:_object.codeCombo.PRDT_APRV_REQ_CODE.code },
			{ Header:"승인상태",			Type:"Combo",		SaveName:"aprvStatCode",		Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", ComboText:_object.codeCombo.APRV_STAT_CODE.text, ComboCode:_object.codeCombo.APRV_STAT_CODE.code },
			{ Header:"브랜드",			Type:"Text",		SaveName:"brandName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"채널구분",			Type:"Text",		SaveName:"chnnlName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"표준카테고리",		Type:"Text",		SaveName:"stdCtgrName",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"입점사번호",			Type:"Text",		SaveName:"vndrNo",				Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"입점사명",			Type:"Text",		SaveName:"vndrName",			Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer" },
			{ Header:"등록자번호",			Type:"Text",		SaveName:"rgsterNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"등록자",			Type:"Text",		SaveName:"rgsterInfo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer" },
			{ Header:"등록일시",			Type:"Date",		SaveName:"rgstDtm",				Width: 10,	Align:"Center",	Edit:0, Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"승인자번호",			Type:"Text",		SaveName:"aprverNo",			Width: 10,	Align:"Center",	Edit:0, Sort:0,	Cursor:"Pointer", Hidden:1 },
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
		list.SetColFontUnderline('vndrName', 1);

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
					var item = list.GetRowData(row);
					abc.open.popup({
						url 	:	"/product/vndrApproval/detail",
						winname :	"registration",
						method	: 	"get",
						title 	:	"prdt-vndr-aprv",
						width 	:	1480,
						height	:	970,
						params	:	{
							  	  prdtNo : item.prdtNo
								, vndrPrdtNoText : item.vndrPrdtNoText
								, chnnlNo : item.chnnlNo
								, siteNo : item.siteNo
							}
					});
				} else if(list.ColSaveName(col) == 'prdtName') {
					abc.productFrontUrl(list.GetRowData(row).prdtNo);
				} else if(list.ColSaveName(col) == 'prdtAprvReqCode') {
					var item = list.GetRowData(row);
					if(item.prdtAprvReqCode != '10001') {
						return false;
					}
					abc.open.popup({
						url 	:	"/product/product/history",
						winname :	"productChangeHistory",
						method	: 	"get",
						title 	:	"상품정보 변경 이력 조회",
						width 	:	1020,
						height	:	640,
						params	:	{ "name" : "상품정보 변경 이력", "type" : "product", "prdtNo" : item.prdtNo }
					});
				} else if(list.ColSaveName(col) == 'vndrName') {
					// 입점사 정보조회 새창 출력
					abc.vendorInfoPop(list.GetRowData(row).vndrNo);
				}
			}
		}

		list_OnRowSearchEnd = function(row) {
			var item = list.GetRowData(row);
			// 승인요청 상태인 상품만 체크가능
			if(item.aprvStatCode != '10001') {
				list.SetCellEditable(item.seq, 'updateCheck', 0);
			}
			// 상품승인요청코드가 정보변경인 경우만 밑줄
			if(item.prdtAprvReqCode == '10001') {
				list.SetCellFontUnderline(item.seq, 'prdtAprvReqCode', 1);
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

		// 입점상품승인관리(BO) 화면에서는 임시저장은 볼 수 없다.
		$('#aprv-stat-code-10000').closest('li').remove();

		// 승인상태
		var option = {
              allId: '#aprv-stat-code-all'
            , itemsClass: '.aprv-stat-code-all'
		}
		abc.biz.display.common.checkBoxAll(option);

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			$('#search-form')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		//브랜드 찾기
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.product.vndrApproval.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 선택상품 일괄승인
		$("#batch-approval").click(function() {
			// 클릭 시  승인처리 Alert 생성하고 승인완료상태로 변경함
			// Alert 메시지 :  승인완료되었습니다.
			var checkedRows = abc.biz.product.vndrApproval.sheet.list.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var vndrApprovalList = [];
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				var item = abc.biz.product.vndrApproval.sheet.list.GetRowData(row);
				item.aprvStatCode = abc.biz.product.vndrApproval.APRV_STAT_CODE_CONFIRM;
				vndrApprovalList.push(item);
			}
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/vndrApproval/batchModify",
				data : $.paramObject(vndrApprovalList)
			})
			.done(function(data){
				alert('승인완료 처리되었습니다.');
				abc.biz.product.vndrApproval.getList();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		});

		// 선택상품 일괄반려
		$("#batch-return").click(function() {
			abc.open.popup({
				url 	:	"/product/vndrApproval/batchReturn" ,
				winname :	"registration",
				method	: 	"post",
				title 	:	"prdt-batch-return",
				width 	:	1000,
				height	:	350,
			});
		});

		// 기간 제어
		/*$(".term-date-wrap .btn-group .btn-func").click(function(e) {
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
		});*/

		// 입점사 조회
		$('#search-vndr').click(function() {
			abc.vndrSearchPopup(false, "callbackVndr");
		});

		// 시작기간~끝기간 설정
		abc.biz.product.utils.setSearchStartAndEndDate($('input[name="periodStartDate"]'), $('input[name="periodEndDate"]'));
		$('a[name="per1Month"]').click();

		// 상품코드 20건까지 검색 제한
		abc.biz.product.utils.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);
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
		var url = "/product/vndrApproval/";
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

/**
 * 입점사 찾기 팝업 콜백함수
 */
function callbackVndr(data) {
	$('#vndr-name').val(data.arrayVndrName[0]);
	$('#vndr-no').val(data.arrayVndrNo[0]);
}