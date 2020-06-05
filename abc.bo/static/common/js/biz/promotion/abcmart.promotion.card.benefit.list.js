(function() {

	var _benefit = abc.object.createNestedObject(window,"abc.biz.product.benefit");
	
	/**
	 * 초기화
	 */
	_benefit.init = function(){
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');

		abc.biz.display.common.setFormParameter.setForm($(document.forms.cardBenefitForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		this.initCardBenefitSheet();
		this.event();
		
	}
	
	/**
	 *  IBSheet 초기화(카드사 혜택 목록)
	 */
	_benefit.initCardBenefitSheet = function() {
		createIBSheet2(document.getElementById("cardBenefitSheet"), "list", "100%", "370px");
		var pageCount = $('#pageCount').val();
		
		var initCardBenefitSheet = {};
		initCardBenefitSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initCardBenefitSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initCardBenefitSheet.Cols = [
				{Header:"번호", 			Type:"Seq",		SaveName:"seq",				Width:20,	Align:"Center", Sort:0}				
				, {Header:"상태",			Type:"Status",	SaveName:"status",			Width:20,	Align:"Center",	Hidden:1}
				, {Header:"전시여부",		Type:"Combo",	SaveName:"dispYn",			Width:50,	Align:"Center",	Edit:0, ComboText:"전시|전시안함",	ComboCode : "Y|N", Sort:0}
				, {Header:"무이자할부 행사명",	Type:"Text",	SaveName:"cardBenefitName",	Width:150,	Align:"Center",	Edit:0, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"전시기간",		Type:"Text",	SaveName:"dispYmd",			Width:100,	Align:"Center",	Edit:0, Sort:0}
				, {Header:'최종수정일시',	Type:"Date",	SaveName:"modDtm",			Width:100,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"카드혜택순번",	Type:"Text",	SaveName:"cardBenefitSeq",	Width:100,	Align:"Center",	Edit:0,	Hidden:1}
		];
		
		IBS_InitSheet(list, initCardBenefitSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_benefit.doActionCardBenefit('search');
	}
	
	/**
	 * 메뉴 grid action
	 */
	_benefit.doActionCardBenefit = function(sAction){
		switch (sAction) {
		case "search":			
			var pageCount = $("#pageCount").val();
			var param = { url : "/promotion/card-benefit/read/list"
						, sheet : "list"
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.cardBenefitForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_benefit.event = function() {
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			
			if(row != 0 && col == 3) {
				abc.biz.display.common.setFormParameter.setDetailMove();
				var seq = list.GetRowData(row).cardBenefitSeq;
				
				location.href = "/promotion/card-benefit/detail?cardBenefitSeq=" + seq;
			}
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
		
		$("#pageCount").on("change", function() {
			
			_benefit.doActionCardBenefit("search");
		});
		
		$("#searchBtn").on("click", function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			if ($('#startYmd').val() == '' || $('#endYmd').val() == '') {
				alert('기간을 모두 입력해주세요.');
				return false;
			} 
			_benefit.doActionCardBenefit("search");
		});
		
		$("#refreshBtn").on("click", function() {
			$('#cardBenefitForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화			
		});
		
		$(document.forms.cardBenefitForm).on('submit', function(e){
			e.preventDefault();
			_benefit.doActionCardBenefit('search');
		});
	}	
	
	$(function() {
		
		_benefit.init();
		$(".calendar-month").click();
	});
	
})();