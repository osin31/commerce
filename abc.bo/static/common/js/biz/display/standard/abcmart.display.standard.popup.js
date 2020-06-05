(function() {

	var _popup = abc.object.createNestedObject(window,"abc.biz.display.standard.popup");
	
	/**
	 * 초기화
	 */
	_popup.init = function(){
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		abc.biz.display.common.checkBoxAll({allId: '#chkPopupTypeAll'});
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_popup.sheet.init();
		_popup.event();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_popup.sheet = {};
	_popup.sheet.init = function(){
		createIBSheet2(document.getElementById("popupSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init', "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header:"번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"팝업번호" , Type:"Text", SaveName:"popupSeq", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header:"제목" , Type:"Text", SaveName:"popupTitleText", Width: 30,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"팝업유형", Type:"Combo", SaveName:"popupType",  Align:"Center", Width : 20, Edit:0, ComboText : "일반팝업|이벤트팝업",	ComboCode : "G|E"}
			, {Header:"사이트", Type:"Combo", SaveName:"siteNo",  Align:"Center", Width : 20, Edit:0, ComboText : "통합몰|On The Spot",	ComboCode : "10000|10001"}
			, {Header : "디바이스",	Type : "Text",	SaveName : "deviceCodeName",	Width : 20,	Align : "Center",	Edit : 0}
			, {Header:"팝업타입", Type:"Combo", SaveName:"devicePopupType",  Align:"Center", Width : 20, Edit:0, ComboText : "레이어|새창",	ComboCode : "L|N"}
			, {Header:'미리보기', 		Type:"Button",	 	SaveName:"pcPreview", 			Width: 20,  Align:"Center", Edit:0, DefaultValue:"PC", Sort:0}
			, {Header:'미리보기',		Type:"Button", 		SaveName:"moPreview", 			Width: 20,  Align:"Center", Edit:0, DefaultValue:"MOBILE", Sort:0}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 20,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			, {Header : "전시여부",	Type : "Combo",	SaveName : "dispYn",	Width : 20,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N"}
			, {Header : "우선순위",	Type : "Text",	SaveName : "priorSeq",	Width : 12,	Align : "Center",	Edit : 0}
			, {Header : "특정일전시",	Type : "Combo",	SaveName : "dispDaySetupYn",	Width : 20,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			, {Header : "전시기간",	Type : "Text",	SaveName : "dispDate", Align : "Center",	Edit : 0}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 20, Align:"Center", Edit:0}
			, {Header:"작성일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자", Type:"Text", SaveName:"moderInfo", Width: 20, Align:"Center", Edit:0}
			, {Header:"수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"PC이미지URL", Type:"Text", SaveName:"pcImageUrl", Width: 20, Align:"Center", Hidden:1}
			, {Header:"MO이미지URL", Type:"Text", SaveName:"mobileImageUrl", Width: 20, Align:"Center", Hidden:1}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_popup.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_popup.sheet.event = function(){
		
		list_OnRowSearchEnd = function(row) {
			var data = list.GetRowData(row);
			
			if (data.deviceCodeName == '전체' || data.deviceCodeName == 'PC') {
				list.SetCellEditable(row, 'pcPreview', true);
			}
			if ((data.deviceCodeName == '전체' || data.deviceCodeName == 'Mobile') && data.popupType != 'E') {
				list.SetCellEditable(row, 'moPreview', true);
			}
		}
		
		list_OnButtonClick = function(row, col) {
			var data = list.GetRowData(row);
			
			// 미리보기
			if (list.ColSaveName(col).indexOf('Preview') > -1) {
				
				// 레이어 or 새창 구분 (L,N)
				var devicePopupType = data.devicePopupType;
				// 일반 or 이벤트 구분(G,E)
				var popupType = data.popupType;
				// url
				var url;
				var isMobile;
				if (list.ColSaveName(col).indexOf('pc') > -1) { // PC
					url = data.siteNo == '10000' ? $('input[name=foUrl]').val() : $('input[name=otsUrl]').val();					
				} else { // MOBILE
					url = data.siteNo == '10000' ? $('input[name=moUrl]').val() : $('input[name=otsMoUrl]').val();
					isMobile = true;
				}
				
				abc.open.popup({
					url 	:	url,
					winname :	"preview",
					title 	:	"preview",
					method	: 	"get",
					params	:	{
						isPreview : 'y',
						popupSeq : data.popupSeq,
						popupType : data.popupType,
						devicePopupType : data.devicePopupType,
						viewer : isMobile ? 'mobile' : ''
					}
				});
			}
		}
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "popupTitleText" && value != "" ) {
					
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/display/standard/popup/detail?popupSeq=" + list.GetRowData(row).popupSeq;
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
	_popup.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_popup.getList();
			return false;
		});
		
		/** 검색조건 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});

		$('#selTermsModule').on('change', function(e){
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_popup.getList();
		})
	}
	
	/**
	 * 리스트 조회
	 */
	_popup.getList = function(){

		//검색기간 체크 추가
		var alertSearchDate = "시작일은 종료일 이전 날짜만 가능합니다.";

		var startDate = new Date($('#startYmd')[0].value);
		var endDate = new Date($('#endYmd')[0].value);

		if(startDate > endDate){
			alert(alertSearchDate);
		}
		
		var pageCount = $('#selTermsModule').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/display/standard/popup/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_popup.init();
		$(".calendar-month").click();
		_popup.getList();
	});
	
})();