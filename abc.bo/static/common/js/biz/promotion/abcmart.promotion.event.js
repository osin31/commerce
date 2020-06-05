(function() {

	var _event = abc.object.createNestedObject(window,"abc.biz.promotion.event");
	
	/**
	 * 초기화
	 */
	_event.init = function(){
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		abc.biz.display.common.checkBoxAll({allId: '#chkChannel00', itemsClass: '[name=chnnlNos]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkMemberTypeAll', itemsClass: '.chkMemberTypeCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkDevice00', itemsClass: '[name=deviceCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#checkProgress01', itemsClass: '[name=eventProgressStatusArr]'});
		
		_event.setMonth();
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();

		_event.sheet.init();
		_event.event();
		
		_event.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_event.sheet = {};
	_event.sheet.init = function(){
		createIBSheet2(document.getElementById("eventSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, /*AutoFitColWidth: 'init',*/ "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header : "번호", 			Type:"Seq", 	SaveName:"", 					Width: 50, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"이벤트 번호" , 		Type:"Text", 	SaveName:"eventNo", 			Width: 100,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"이벤트명" , 		Type:"Html", 	SaveName:"eventName", 			Width: 330,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"이벤트유형코드" ,	Type:"Text", 	SaveName:"eventTypeCode", 		Width: 110,  Align:"Center", Edit:0, Sort:0, Hidden:1}
			, {Header :"유형" , 			Type:"Text", 	SaveName:"eventTypeCodeName", 	Width: 110,  Align:"Center", Edit:0, Sort:0}
			, {Header : "디바이스" , 		Type:"Text", 	SaveName:"deviceCodeName", 		Width: 110,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "대상 회원" , 		Type:"Text", 	SaveName:"memberTypeCodeName", 	Width: 200,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "채널" , 			Type:"Text", 	SaveName:"chnnlName", 			Width: 280,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header : "사용여부",		Type : "Combo",	SaveName : "useYn",				Width : 100,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			//, {Header : "A-RT전시여부",	Type : "Combo",	SaveName : "artDispYn",			Width : 100,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "전시여부",		Type : "Combo",	SaveName : "dispYn",			Width : 100,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N", Sort:0}
			, {Header : "진행상태" , 		Type:"Combo", 	SaveName:"eventProgressStatus", Width: 100,  Align:"Center", Edit:0, ComboText : "임시저장|진행중|대기|종료",	ComboCode : "temp|ing|wait|end", Sort:0}
			, {Header : "당첨발표여부" , 	Type:"Text", 	SaveName:"przwrPblcName", 		Width: 100,  Align:"Center", Edit:0, FontUnderline:1, Sort:0}
			, {Header : "당첨자발표일", 	Type:"Date", 	SaveName:"przwrPblcTodoYmd", 	Width: 130, Align:"Center", Edit:0, Format:"yyyy.MM.dd", Sort:0}
			, {Header : "미리보기" , 		Type:"Button", 	SaveName:"pcPreview", 			Width: 50,  Align:"Center", Edit:0, DefaultValue:"PC", Sort:0}
			, {Header : "미리보기" , 		Type:"Button", 	SaveName:"moPreview", 			Width: 80,  Align:"Center", Edit:0, DefaultValue:"MOBILE", Sort:0}
			, {Header : "시작일", 		Type:"Date", 	SaveName:"eventStartDtm", 		Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "종료일", 		Type:"Date", 	SaveName:"eventEndDtm", 		Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "작성자", 		Type:"Text", 	SaveName:"rgsterInfo", 			Width: 130, Align:"Center", Edit:0, Sort:0}
			, {Header : "작성일", 		Type:"Date", 	SaveName:"rgstDtm", 			Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "수정자", 		Type:"Text", 	SaveName:"moderInfo", 			Width: 130, Align:"Center", Edit:0, Sort:0}
			, {Header : "수정일", 		Type:"Date", 	SaveName:"modDtm", 				Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		/*list.FitColWidth();*/
		list.SetExtendLastCol(1);
		
		_event.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_event.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/promotion/event/detail?eventNo=" + list.GetRowData(row).eventNo;
				}
				
				if ( list.ColSaveName(col) == "przwrPblcName" && value != "-") {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//당첨자 페이지 이동
					//이벤트 유형코드 추가
					location.href = "/promotion/event/result/detail?eventNo=" + list.GetRowData(row).eventNo + '&eventName=' + list.GetRowData(row).eventName + '&eventTypeCode=' + list.GetRowData(row).eventTypeCode;
				}
				
				if ( list.ColSaveName(col) == "pcPreview" || list.ColSaveName(col) == "moPreview") {
					var viewer = value == "PC" ? "pc" : "mobile";
					var eventNo = list.GetRowData(row).eventNo;
					$.ajax({
						url				: "/promotion/event/front/url",
						method			: "GET",
						data			: {
							"eventNo" : eventNo,
							"deviceCode" : value == "PC" ? "10000" : "10001"
						}
					}).done(function(data) {		
						if(data && data.success === true) {
							if( value == "PC"){
								window.open(data.data + "?eventNo=" + eventNo + '&viewer=' + viewer + '&adminView=Y', "eventFront");
							}else{
								var param = {
									eventNo		: eventNo,
									adminView	: abc.consts.BOOLEAN_TRUE,
									viewer 		: viewer
								};
								
								abc.open.popup({
									url 	:	data.data,
									winname :	"preview",
									title 	:	"preview",
									method	: 	"get",
									width	:	490,
									height	:	1060,
									params	:	param
								});
							}
						} else {
							alert("URL 조회에 실패하였습니다.");
							console.log(data);
						}
					}).fail(function(data) {
						alert("URL 조회에 실패하였습니다.");
						console.log(data);
					});
				}
			}
		}
		
		list_OnRowSearchEnd = function(row) {
			var data = list.GetRowData(row);
			
			if (data.deviceCodeName != '') {
				if (data.deviceCodeName.indexOf('PC') > -1) { // PC
					list.SetCellEditable(row, 'pcPreview', true);
				}
				if (data.deviceCodeName.indexOf('Mobile') > -1 || data.deviceCodeName.indexOf('APP') > -1) { // MOBILE
					list.SetCellEditable(row, 'moPreview', true);
				}
				if ( !abc.text.allNull(data.chnnlName) ) {
					list.SetRowData(row, {chnnlName : data.chnnlName.replace(/,/gi, ', ')});
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
	_event.event = function(){
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
			_event.getList();
			return false;
		});
		
		$('[name=przwrPblcYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('[name=answerYns]').prop('disabled', false);
				$('[name=answerYns]').prop('checked', true);
			} else {
				$('[name=answerYns]').prop('checked', false);
				$('[name=answerYns]').prop('disabled', true);
			}
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('#rdoWin01').trigger('change');			
			_event.setMonth();
		});
		
		$('#pageCount').on('change', function() {
			_event.getList();
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_event.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/event/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()};
		
		DataSearchPaging(param);
	}
	
	_event.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	$(function() {
		_event.init();
	});
	
})();