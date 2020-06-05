(function() {

	var _promotion = abc.object.createNestedObject(window,"abc.biz.promotion.promotion");
	
	/**
	 * 초기화
	 */
	_promotion.init = function(){
		
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
		
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		abc.biz.display.common.checkBoxAll({allId: '#chkDevice00', itemsClass: '[name=deviceCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkChannel00', itemsClass: '[name=chnnlNos]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkMemberTypeAll', itemsClass: '.chkMemberTypeCode'});
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_promotion.sheet.init();
		_promotion.event();
		
		_promotion.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_promotion.sheet = {};
	_promotion.sheet.init = function(){
		createIBSheet2(document.getElementById("promoSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1/*, AutoFitColWidth: 'init'*/};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header : "번호", Type:"Seq", SaveName:"", Width: 50, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"프로모션번호" , Type:"Text", SaveName:"promoNo", Width: 150,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"프로모션명" , Type:"Text", SaveName:"promoName", Width: 350,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"프로모션 유형" , Type:"Text", SaveName:"promoTypeCodeName", Width: 100,  Align:"Center", Edit:0, Sort:0}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 300,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 200,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "회원유형" , Type:"Text", SaveName:"memberTypeCodeName", Width: 250,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "진행상태" , Type:"Combo", SaveName:"promoProgressStatus", Width: 100,  Align:"Center", Edit:0, ComboText : "진행중|대기|종료",	ComboCode : "ing|wait|end", Sort:0}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 100,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "프로모션 시작일", Type:"Date", SaveName:"promoStartDtm", Width: 200, Align:"Center", Edit:0, Format:"yyyy.MM.dd HH:mm"}
			, {Header : "프로모션 종료일", Type:"Date", SaveName:"promoEndDtm", Width: 200, Align:"Center", Edit:0, Format:"yyyy.MM.dd HH:mm"}
			, {Header : "최초 작성자", Type:"Text", SaveName:"rgsterInfo", Width: 200, Align:"Center", Edit:0, Sort:0}
			, {Header : "최초 등록일시", Type:"Date", SaveName:"rgstDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "최종 수정자", Type:"Text", SaveName:"moderInfo", Width: 200, Align:"Center", Edit:0, Sort:0}
			, {Header : "최종 수정일시", Type:"Date", SaveName:"modDtm", Width: 200, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		/*list.FitColWidth();*/
		list.SetExtendLastCol(1);
		
		_promotion.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_promotion.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "promoName" && value != "" ) {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/promotion/promotion/detail?promoNo=" + list.GetRowData(row).promoNo;
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
	_promotion.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			if(!abc.date.searchValidate()){
				return false;
			}
			
			if($('[name=keywordType]').val() == '' && $('[name=keyword]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				return false;
			}
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_promotion.getList();
			return false;
		});
		
		/** 검색조건 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});
		
		$('#pageCount').on('change', function() {
			_promotion.getList();
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_promotion.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/promotion/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_promotion.init();
	});
	
})();