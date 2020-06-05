(function() {

	var _eventWinPopup = abc.object.createNestedObject(window,"abc.biz.promotion.event.win.popup");
	
	/**
	 * 초기화
	 */
	_eventWinPopup.init = function(){
		console.log('init');
		_eventWinPopup.sheet.init();
		_eventWinPopup.event();
		_eventWinPopup.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventWinPopup.sheet = {};
	_eventWinPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("eventWinSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = 100;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"이벤트 번호" , Type:"Text", SaveName:"eventNo", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"이벤트명" , Type:"Text", SaveName:"eventName", Width: 20,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"유형" , Type:"Text", SaveName:"eventTypeCodeName", Width: 15,  Align:"Center", Edit:0}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			
			, {Header : "대상상품" , Type:"Text", SaveName:"prdtName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "신청옵션정보" , Type:"Text", SaveName:"optnName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "이름" , Type:"Text", SaveName:"addInfo1MaskNoAuth", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "휴대폰번호" , Type:"Text", SaveName:"addInfo2MaskNoAuth", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "이메일" , Type:"Text", SaveName:"addInfo3MaskNoAuth", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "수령정보" , Type:"Text", SaveName:"storeName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "주문번호" , Type:"Text", SaveName:"orderNo", Width: 20,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header : "수령상태" , Type:"Text", SaveName:"orderPrdtStatCodeName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "등록일", Type:"Date", SaveName:"joinDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_eventWinPopup.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventWinPopup.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					//상세페이지 이동
					window.open("/promotion/event/detail?eventNo=" + list.GetRowData(row).eventNo);
					//location.href = "/promotion/event/result/detail?eventNo=" + list.GetRowData(row).eventNo;
				}
				
				if ( list.ColSaveName(col) == "orderNo" && value != "" ) {
					var url = "/order/read-detail-pop";
					var params = {}
					params.orderNo = list.GetRowData(row).orderNo;
					
					abc.open.popup({
						winname :	"주문 상세 팝업",
						url 	:	url,
						width 	:	1240,
						height	:	990,
						params	:	params
					});
				}
				/*if ( list.ColSaveName(col) == "winCount" && value != "" ) {
					var params = {};
					params.eventNo = list.GetRowData(row).eventNo
					_eventWinPopup.eventWinPopup(params);
				}*/
			}
		}
		

	}
	
	/**
	 * 이벤트
	 */
	_eventWinPopup.event = function(){
		//검색
	/*	$(document.forms.searchForm).on('submit', function(e){
			_eventWinPopup.getList();
			return false;
		});*/
		
		$('#resultBenefit').on('change', function(e){
			_eventWinPopup.getList();
			if(abc.text.allNull($("#resultBenefit option:selected").val())){
				var winCount = 0;
				$("#resultBenefit option").each(function(){ 
					if(!abc.text.allNull($(this).data('winCount'))){
						winCount = winCount + parseInt($(this).data('winCount')); 
					}
				});
				
				$('#winCountArea').text(winCount);
			}else{
				$('#winCountArea').text($("#resultBenefit option:selected").data("winCount"));
			}
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_eventWinPopup.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/event/result/member/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			,callback : function() {
			//	$('#winCountArea').text(list.RowCount());
			}};
		
		DataSearchPaging(param);
	}
	
	$(function() {
		_eventWinPopup.init();
	});
	
})();