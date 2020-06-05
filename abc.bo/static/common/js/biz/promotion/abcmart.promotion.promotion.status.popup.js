(function() {

	var _promoStatusPopup = abc.object.createNestedObject(window,"abc.biz.promotion.status.popup");
	
	/**
	 * 초기화
	 */
	_promoStatusPopup.init = function(){
		_promoStatusPopup.sheet.init();
		_promoStatusPopup.event();
		
		_promoStatusPopup.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_promoStatusPopup.sheet = {};
	_promoStatusPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("statusSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:false};
		
		initSheet.Cols = [
			{Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"사이트" , Type:"Combo", SaveName:"siteNo", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"결제구분" , Type:"Combo", SaveName:"deviceCode", Width: 20,  Align:"Center", Edit:0}
			, {Header :"주문번호" , Type:"Text", SaveName:"orderNo", Width: 20,  Align:"Center", Edit:0, FontUnderline:1}
			, {Header :"상품명" , Type:"Text", SaveName:"productName", Align:"Left", Edit:0}
			, {Header :"정상가" , Type:"Int", SaveName:"ordNormalAmt", Width: 15,  Align:"Center", Edit:0}
			, {Header :"총결제금액" , Type:"Int", SaveName:"ordPayment", Width: 15,  Align:"Center", Edit:0}
			, {Header :"할인금액" , Type:"Int", SaveName:"ordDscntAmt", Width: 15,  Align:"Center", Edit:0}
			, {Header :"주문상품\n배송상태" , Type:"Combo", SaveName:"orderPrdtStatCode", Width: 15,  Align:"Center", Edit:0}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.InitDataCombo(0, "siteNo",  _promoStatusPopup.siteCombo.text,   _promoStatusPopup.siteCombo.code );		//사이트 콤보
		list.InitDataCombo(0, "deviceCode",  _promoStatusPopup.codeCombo.DEVICE_CODE.text,   _promoStatusPopup.codeCombo.DEVICE_CODE.code );		//디바이스 구분
		list.InitDataCombo(0, "orderPrdtStatCode",  _promoStatusPopup.codeCombo.ORDER_PRDT_STAT_CODE.text,   _promoStatusPopup.codeCombo.ORDER_PRDT_STAT_CODE.code );		 //주문배송상태

		_promoStatusPopup.sheet.event();
		_promoStatusPopup.getList();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_promoStatusPopup.sheet.event = function(){
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "orderNo" && value != "" ) {
					abc.orderDetailPopup(statusSheet,"orderNo",row,col,'orderNo='+value);
				}
			}
		}
		
	}
	
	/**
	 * 이벤트
	 */
	_promoStatusPopup.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_promoStatusPopup.getList();
			return false;
		});
		
		$('#excelDown').on('click', function(e) {
			$('[name=excelForm]').attr('action', '/promotion/promotion/status/excel/download');
			$('[name=excelForm]').submit();
		});
		
		$('#pageCount').on('change', function() {
			_promoStatusPopup.getList();
		});
	}
	
	
	/**
	 * 리스트 조회
	 */
	_promoStatusPopup.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/promotion/status/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" };
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_promoStatusPopup.init();
	});
	
})();