(function() {

	var _webzine = abc.object.createNestedObject(window,"abc.biz.display.contents.webzine");
	
	/**
	 * 초기화
	 */
	_webzine.init = function(){
     	
     	//일자를 한달로 기본셋팅
		if ($("#startYmd").val() == "" && $("#endYmd").val() == "" ){
			$("#endYmd").datepicker("setDate", "today");
			$("#startYmd").datepicker("setDate", "-1M");
		}
		
		abc.biz.display.common.checkBoxAll({allId: '#check-all-item', itemsClass: '.check-item'});
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_webzine.sheet.init();
		_webzine.event();
		
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_webzine.sheet = {};
	_webzine.sheet.init = function(){
		createIBSheet2(document.getElementById("webzineSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header:"번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"", Type:"Text", SaveName:"wbznSeq", Width: 10, Align:"Center", Edit:0, Hidden:1, Sort:0}
			, {Header:"제목" , Type:"Text", SaveName:"wbznTitleText", Width: 30,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"웹진유형", Type:"Text", SaveName:"wbznTypeName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"전시여부", Type:"Text", SaveName:"dispYnName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"전시시작일", Type:"Date", SaveName:"dispStartYmd", Width: 20, Align:"Center",  Edit:0, Sort:0}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"작성일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수장자", Type:"Text", SaveName:"moderInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_webzine.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_webzine.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "wbznTitleText" && value != "" ) {
					
					abc.biz.display.common.setFormParameter.setDetailMove();
					
					//상세페이지 이동
					var url = "/display/contents/webzine/detail?wbznSeq=";
					location.href = url + list.GetRowData(row).wbznSeq;
				}
			}
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
		
	}
	
	/**
	 * 이벤트
	 */
	_webzine.event = function(){
		
		$(document.forms.searchForm).on('submit', function(){
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_webzine.getList();
			return false;
		});
		
		//초기화
		$('#clear-form').on('click', function(e){
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});
		
		$('#selTermsModule').on('change', function(e){
			_webzine.getList();
		})
	}
	
	/**
	 * 리스트 조회
	 */
	_webzine.getList = function(){
		
		//검색기간 체크 추가
		var alertSearchDate = "시작일은 종료일 이전 날짜만 가능합니다.";

		var startDate = new Date($('#startYmd')[0].value);
		var endDate = new Date($('#endYmd')[0].value);

		if(startDate > endDate){
			alert(alertSearchDate);
			return;
		}
		
		var pageCount = $('#selTermsModule').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/display/contents/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_webzine.init();
		_webzine.getList();
	});
	
})();