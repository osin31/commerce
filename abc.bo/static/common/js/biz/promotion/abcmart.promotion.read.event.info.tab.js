(function() {

	var _readInfoEventTab = abc.object.createNestedObject(window,"abc.biz.promotion.read.event.info.tab");
	
	/**
	 * 초기화
	 */
	_readInfoEventTab.init = function(){
		console.log('init');
		_readInfoEventTab.sheet.init();
		_readInfoEventTab.sheet.event();
		_readInfoEventTab.event();
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		abc.biz.display.common.checkBoxAll({allId: '#chkChannel00', itemsClass: '[name=chnnlNos]'});
		abc.biz.display.common.checkBoxAll({allId: '#checkProgress01', itemsClass: '[name=eventProgressStatusArr]'});
		
		if(!$('#checkProgress01').is(':checked')) {
			$('#checkProgress01').trigger('click');
		}
			
		_readInfoEventTab.setMonth();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_readInfoEventTab.sheet = {};
	_readInfoEventTab.sheet.init = function(){
		if(typeof list != 'undefined'){
			list.Reset();
			list.DisposeSheet(1);
		}
		
		createIBSheet2(document.getElementById("eventSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"이벤트 번호" , Type:"Text", SaveName:"eventNo", Width: 15,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"이벤트명" , Type:"Text", SaveName:"eventName", Width: 50,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"이벤트 유형" , Type:"Text", SaveName:"eventTypeCodeName", Width: 15,  Align:"Center", Edit:0, Sort:0}
			/*, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1, Sort:0}*/
			, {Header : "대상 회원" , Type:"Text", SaveName:"memberTypeCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			/*, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}*/
			/*, {Header : "전시여부",	Type : "Combo",	SaveName : "dispYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N", Sort:0}*/
			, {Header : "진행상태" , Type:"Combo", SaveName:"eventProgressStatus", Width: 15,  Align:"Center", Edit:0, ComboText : "임시저장|진행중|대기|종료",	ComboCode : "temp|ing|wait|end", Sort:0}
			, {Header : "당첨자 발표" , Type:"Text", SaveName:"przwrPblcName", Width: 15,  Align:"Center", Edit:0, Sort:0}
			, {Header : "결과보기" , Type:"Button", SaveName:"resultName", Width: 15,  Align:"Center", Edit:0, DefaultValue:"", Sort:0} //추후 FO URL 추가
			/*, {Header : "미리보기" , Type:"Button", SaveName:"", Width: 15,  Align:"Center", Edit:0, DefaultValue:"PC", Sort:0}	 //추후 FO URL 추가
			, {Header : "미리보기" , Type:"Button", SaveName:"", Width: 15,  Align:"Center", Edit:0, DefaultValue:"MOBILE", Sort:0} //추후 MO URL 추가
*/			, {Header : "이벤트 시작일", Type:"Date", SaveName:"eventStartDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "이벤트 종료일", Type:"Date", SaveName:"eventEndDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "이벤트 참여일", Type:"Date", SaveName:"eventJoinDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "", Type:"Int", SaveName:"resultCount", Width: 20, Align:"Center", Edit:0, Hidden:1}
			/*, {Header : "작성자", Type:"Text", SaveName:"rgsterName", Width: 20, Align:"Center", Edit:0, Sort:0}
			, {Header : "작성일", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "수정자", Type:"Text", SaveName:"moderName", Width: 20, Align:"Center", Edit:0, Sort:0}
			, {Header : "수정일", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}*/
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_readInfoEventTab.sheet.list = list;
		_readInfoEventTab.getList();
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_readInfoEventTab.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					var eventNo = list.GetRowData(row).eventNo;
					//상세페이지 이동
					//location.href = "/promotion/event/detail?eventNo=" + list.GetRowData(row).eventNo;
					$.ajax({
						url				: "/promotion/event/front/url",
						method			: "GET",
						data			: {
							"deviceCode" : "10000"
						}
					}).done(function(data) {		
						if(data && data.success === true) {
							console.log(data.data);
							window.open(data.data + '?eventNo=' + eventNo, "eventFront");
						} else {
							alert("프론트 URL 조회에 실패하였습니다.");
							console.log(data);
						}
					}).fail(function(data) {
						alert("프론트 URL 조회에 실패하였습니다.");
						console.log(data);
					});
					
				}
				
				if ( list.ColSaveName(col) == "resultName" && value != "") {
					//페이지 이동
				}
			}
		}
		
	}
	
	/**
	 * 이벤트
	 */
	_readInfoEventTab.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			if($('[name=keywordType]').val() == '' && $('[name=keyword]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				return false;
			}
			_readInfoEventTab.getList();
			return false;
		});
		
		$('.inputCalendar').on('change', function(){
			_readInfoEventTab.yearCheck(1);
			$(this).focus();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm', false);
			
			_readInfoEventTab.setMonth();
			$("input[type=checkbox]").prop("checked",true);
		});
		
		// 페이지별 갯수 변경 이벤트
		$("#pageCount").on('change', function() {
			_readInfoEventTab.getList();
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_readInfoEventTab.getList = function(){
		if(_readInfoEventTab.yearCheck(1)) {
			var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
			var param = { url : "/promotion/event/member/history/list/read"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.forms.searchForm)
				, sheet : "list" };
			
			DataSearchPaging(param);
		}
	}
	
	_readInfoEventTab.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	_readInfoEventTab.yearCheck = function(compareYear) {
		var yearCheck = true;
		var startYmd = $('#startYmd').val();
		var endYmd = $('#endYmd').val();
		var startDate = '';
		var endDate = '';
		var betweenDate = '';
		if(startYmd != '' && endYmd != '') {
			startYmd = startYmd.split('.');
			endYmd = endYmd.split('.');
		} else {
			return yearCheck;
		}
		
		startDate = new Date(startYmd[0], startYmd[1], startYmd[2]);
		endDate = new Date(endYmd[0], endYmd[1], endYmd[2]);
		betweenDate = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
		
		if(betweenDate < 0) {
			alert('시작일은 종료일 이전 날짜만 가능합니다.');
			yearCheck = false;
		} else if(betweenDate > (compareYear * 365)) {
			alert('조회기간은 최대' + compareYear + '년까지 가능합니다.');
			yearCheck = false;
		}
		
		return yearCheck;
	}
	
	$(function() {
		_readInfoEventTab.init();
	});
	
})();