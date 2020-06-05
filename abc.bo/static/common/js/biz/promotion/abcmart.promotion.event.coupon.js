(function() {

	var _eventCoupon = abc.object.createNestedObject(window,"abc.biz.promotion.event.coupon");
	
	/**
	 * 초기화
	 */
	_eventCoupon.init = function(){
		/*_eventCoupon.sheet.init();
		if($('#eventNo').val() != "" && $('[name=eventJoinType]:checked').val() == 'C') {
			$('#benefit2Th').attr('rowspan', 2);
			$('.cpnTrArea').show();
			_eventCoupon.getList();
		} else {
			$('.cpnTrArea').hide();
			$('#eventCouponSheet').hide();
		}*/
		
		_eventCoupon.event();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventCoupon.sheet = {};
	_eventCoupon.sheet.init = function(){
		createIBSheet2(document.getElementById("eventCouponSheet"), "cpnList", "100%", "370px");
		
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header : "", Type:"Status", SaveName:"status", Width: 10, Align:"Center", Edit:1, Sort:0, Hidden:1}
			,{Header : "", Type:"CheckBox", SaveName:"checkCoupon", Width: 10, Align:"Center", 	Edit:1, Sort:0}
			,{Header : "발행수량 제한", Type:"Text", SaveName:"totalIssueLimitCount", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			,{Header : "ID당 발급횟수 제한", Type:"Text", SaveName:"per1psnMaxIssueCount", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"쿠폰번호" , Type:"Text", SaveName:"cpnNo", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"쿠폰명" , Type:"Text", SaveName:"cpnName", Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"쿠폰 유형" , Type:"Text", SaveName:"cpnTypeCodeName", Width: 15,  Align:"Center", Edit:0}
			, {Header : "할인율/금액" , Type:"Text", SaveName:"dscntText", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			, {Header : "발급시작일", Type:"Text", SaveName:"issueStartDtm", Width: 20, Align:"Center", Edit:0}
			, {Header : "발급종료일", Type:"Text", SaveName:"issueEndDtm", Width: 20, Align:"Center", Edit:0}
		];
		
		IBS_InitSheet(cpnList , initSheet);
		cpnList.FitColWidth();
		cpnList.SetExtendLastCol(1);
		
		_eventCoupon.sheet.event();
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventCoupon.sheet.event = function(){
		cpnList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( cpnList.ColSaveName(col) == "cpnName" && value != "" ) {
					window.open("/promotion/coupon/detail?cpnNo=" + cpnList.GetRowData(row).cpnNo);
				}
			}
		}
		
	}
	
	_eventCoupon.event = function(){
		$('#couponDelBtn').on('click', function(e) {
			var seq = '';
			seq = cpnList.FindCheckedRow('checkCoupon');
			if(seq == '') {
				alert('삭제할 쿠폰을 선택해주세요.');
				return;
			} else {
				if(confirm('삭제하시겠습니까?')) {
					cpnList.RowDelete(seq);
				}
			}
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_eventCoupon.getList = function(){
		$.ajax({
			type :"get",
			url : "/promotion/event/coupon/list/read",
			data: {eventNo : $('#eventNo').val()}
		})
		.done(function(d){
			if(d != null && d.length > 0){
				abc.biz.promotion.event.detail.appendCoupon(d);
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			alert("오류가 발생하였습니다");
		});
	}
	
	$(function() {
		_eventCoupon.init();
	});
	
})();