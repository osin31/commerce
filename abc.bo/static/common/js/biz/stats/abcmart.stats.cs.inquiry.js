(function() { // 사이트 관리.

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.cs.inquiry");

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
		createIBSheet2(document.getElementById("reviewGrid"), "list", "100%", "429px");

		// Grid 객체 생성
		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
				{Header:"기간별 통계|일별 통계",		Type:"Text",			SaveName:"inqryDtm",		Width: 100,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|합계",				Type:"Text",			SaveName:"totalCount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"답변상태|답변 보류",		Type:"Text",			SaveName:"aswrReady",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"답변상태|미답변",			Type:"Text",			SaveName:"aswrNo",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"답변상태|답변 완료",		Type:"Text",			SaveName:"aswrSuccess",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"상품 문의 유형 Data",		Type:"Text",			SaveName:"cnslList",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
		];

		for(var i in cnslTypeDtlCodeList) {
			var item = cnslTypeDtlCodeList[i];
			initSheet.Cols.push( {Header:"상품 문의 유형|" + item.codeDtlName, Type:"Text", SaveName:"cnsl" + item.codeDtlNo, Width: 100, Align:"Right", Edit:0, Sort:0 } );
		}

		// Grid 초기화
		IBS_InitSheet(list, initSheet);
		// Grid 건수 정보 표시
		list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		list.SetPagingPosition(2);
		// Grid width 자동 조절
		list.FitColWidth("7|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		list.SetExtendLastCol(2);

		_object.sheet.list = list;
	}


	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnRowSearchEnd = function(row) {
			var cnsl = list.GetRowData(row).cnslList.split('|');

			for(var i = 1; i <= cnsl.length; i++){
				var item = "cnsl" + 1000 + i;
				list.SetCellValue(row, item, cnsl[i-1]);
			}
		}

		list_OnSearchEnd = function(code, msg) {
		    if (code == 0) {
				list.SetFrozenRows(1);
		    }
		    // 엑셀 다운로드 버튼 제어
			if(list.GetTotalRows() == 0) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function(){

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 입점사 찾기
		$('#search-vndr').click(function() {
			abc.vndrSearchPopup(false, "callbackVndr");
		});

		// 초기화
		$("#clear").off().on("click", function(e) {
			$("#form")[0].reset();
			$('a[name="perWeek"]').click();
			$('#choice-period option[value="date"]').change();
		});

		//검색조회.
		$('#search').off().on('click', function(){
			_object.getList();
		});

		// 상품코드 구분 이벤트
		$('select[name="mmnyPrdtYn"]').off().on('change', function(e) {
			if('' == $(this).val()) { // 전체 선택한 경우
				$('select[name="chnnlNo"]').val('');
				$('select[name="chnnlNo"]').hide();
			} else {
				$('select[name="chnnlNo"]').show(); // 채널 목록 노출
				if('V' == $(this).val()) {
					$('[data-vndr-use-yn="N"]').hide();
				} else {
					$('[data-vndr-use-yn="N"]').show();
				}
			}
		});

		// 기간별 선택 시 이벤트
		$('#choice-period').on("change", function(e){
			var value = this.value;
			_object.setPeriod(value); // input 날짜 이벤트

			switch (value) {
			case 'date':
				value = '일별 통계';
				break;
			case 'month':
				value = '월별 통계';
				break;
			case 'year':
				value = '년별 통계';
				break;
			default:
				break;
			}
			list.SetCellText(1, 'inqryDtm', value);
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").off().on("change",function(){
			_object.getList();
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
					return false;
			}
			$('#form').prop('action', '/stats/cs/download-inquiryExcel').prop('method', 'post').submit();
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perYesterday" :
				abc.date.yesterday(this);
				break;
			case "perWeek" :
				abc.date.statWeek(this);
				break;
			case "per1Month" :
				abc.date.statMonth(this);
				break;
			case "perYear" :
				abc.date.statYear(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 기본 날짜 세팅
		$('a[name="perWeek"]').click();
		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));

		// 체크박스 제어
		abc.biz.display.common.checkBoxAll({allId: '#device-code-all', itemsClass: '.device-code'});

	}

	/**
	 * 목록 조회
	 */
	_object.getList = function(){
		if (!abc.date.searchValidate()) {
			return false;
		}

		DataSearchPaging({
			  url : "/stats/cs/inquiry-stats-search"
			, onePageRow : $("#page-count").val()
			, subparam : $("#form").serialize()
			, sheet : "list"
		});
	}

	/**
	 * 기간별 제어
	 */
	_object.setPeriod = function(type) {
		$('.term-date-wrap').children().not('#choice-period').hide();
		$('.' + type).show();
		switch (type) {
		case 'date':
			break;
		case 'month':
			$('select.month').off().on('change', function(e) {
				var value = $(this).val();
				if($(this).prop('id').includes('from')) {
					$('input[name="fromDate"]').val(value + '.01');
				} else {
					var date = new Date(value);
					date = new Date(date.getFullYear(), date.getMonth() + 1, 0);
					$('input[name="toDate"]').val(value + '.' + date.getDate());
				}
			});
			$('select.month').change();
			break;
		case 'year':
			$('select.year').off().on('change', function(e) {
				var value = $(this).val();
				if($(this).prop('id').includes('from')) {
					$('input[name="fromDate"]').val(value + '.01.01');
				} else {
					$('input[name="toDate"]').val(value + '.12.31');
				}
			});
			$('select.year').change();
			break;
		default:
			break;
		}
	}

	$(function() {
		_object.init();
	});

})();

function callbackVndr(data) {
	$('#vndr-name').val(data.arrayVndrName[0]);
	$('#vndr-no').val(data.arrayVndrNo[0]);
}