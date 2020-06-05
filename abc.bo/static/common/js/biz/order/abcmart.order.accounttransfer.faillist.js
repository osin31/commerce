/**
 * 무통장입금 실패 목록
 */
(function(){
	
	var _faillist = abc.object.createNestedObject(window,"abc.biz.order.accounttransfer.faillist");
	
	_faillist.init = function() {
		// 검색
		$("#searchBtn").click(function(){
			abc.biz.order.accounttransfer.faillist.searchValidate();
		});
		
		// 검색 초기화
		$("#searchReset").click(function(){
			abc.biz.order.accounttransfer.faillist.searchReset();
		});
		
		$("#termsBtn > a").each(function(index){
			$(this).click(function(){
				abc.biz.order.accounttransfer.faillist.setSearchDate($(this).attr("name"))
			})
		})
		
		$("#refundBtn").click(function(){			
			abc.biz.order.accounttransfer.faillist.refundComplete();
		})
		
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header:"",				Type:"CheckBox",	SaveName:"updateCheck",		Width: 7, 	Align:"Center",		Edit:1, Sort:0}
			, {Header:"NO", 		Type:"Seq",			SaveName:"",				Width: 10, 	Align:"Center", 	Sort:0, Edit:0}
			, {Header:"사이트", 		Type:"Combo",		SaveName:"siteNo",			Width: 10, 	Align:"Center", 	Sort:0, Edit:0}			
			, {Header:"주문일시", 		Type:"Date",		SaveName:"orderDtm",		Width: 20, 	Align:"Center", 	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:1, Edit:0}
			, {Header:"주문번호", 		Type:"Text",		SaveName:"orderNo",			Width: 20, 	Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0, Edit:0}
			, {Header:"", 			Type:"Text",		SaveName:"orderPymntSeq",	Width: 20, 	Align:"Center", 	Hidden:1}
			, {Header:"상품명", 		Type:"Text",		SaveName:"orderPrdtInfo",	Width: 0,  	Align:"Center", 	Sort:0, Edit:0}
			, {Header:"" ,		 	Type:"Text",		SaveName:"memberNo",		Width: 0,  	Align:"", 			Edit:0,	Hidden:1}
			, {Header:"주문자", 		Type:"Text",		SaveName:"buyerName",		Width: 20, 	Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0, Edit:0}
			, {Header:"총 결제금액", 	Type:"Float",		SaveName:"pymntAmt",		Width: 20, 	Align:"Center", 	"Format": "#,##0원", Sort:1,	Edit:0}
			, {Header:"환불처리여부", 	Type:"Combo",		SaveName:"procYn",			Width: 20, 	Align:"Center", 	ComboText:"처리|미처리", ComboCode:"Y|N", Sort:0, Edit:0}
			, {Header:"", 			Type:"Text",		SaveName:"pymntOrganNoText",Width: 0,  	Align:"", 			Edit:0,	Hidden:1}
			, {Header:"환불계좌정보", 	Type:"Button",		SaveName:"refundAccount",	Width: 20, 	Align:"Center", 	Sort:0, Edit:1}
			, {Header:"처리일시", 		Type:"Date",		SaveName:"modDtm",			Width: 20, 	Align:"Center", 	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:1, Edit:0}
			, {Header:"" ,		 	Type:"Text",		SaveName:"moderNo",			Width: 0,  	Align:"", 			Edit:0,	Hidden:1}
			, {Header:"처리자", 		Type:"Text",		SaveName:"moderName",		Width: 20, 	Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0, Edit:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(listSheet , initSheet);
		// Grid 건수 정보 표시
		listSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		listSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		listSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		// listSheet.SetExtendLastCol(1);

		var toDate = new Date(); 
		
		$('#toDate').datepicker( "option", "maxDate", toDate);
		
		$('#toDate').datepicker( "option", "maxDate", toDate)
			.on("change", function(){
				$('#toDate').datepicker("option", "maxDate", _faillist.getDate(this));
			});	
		
		this.setSearchDate();
	}
	
	_faillist.setSearchDate = function(term){
		var toDate = new Date();
		var weekAgo = new Date();
		var monthAgo = new Date();
		var yearAgo = new Date();
		
		weekAgo.setDate(toDate.getDate() - 7);
		monthAgo.setMonth(toDate.getMonth()-1);
		yearAgo.setFullYear(toDate.getFullYear()-1);
		
		switch (term) {		
			case "perToday":
				$('#toDate').datepicker("setDate", toDate);				
				$('#fromDate').datepicker("option", "maxDate", toDate).datepicker("setDate", toDate);
				break;
			case "perWeek":
				$('#toDate').datepicker("setDate", toDate);			
				$('#fromDate').datepicker( "option", "maxDate", toDate).datepicker("setDate", weekAgo);
				break;
			case "perMonth":
				$('#toDate').datepicker("setDate", toDate);			
				$('#fromDate').datepicker( "option", "maxDate", toDate).datepicker("setDate", monthAgo);
				break;
			case "perYear":
				$('#perYear').datepicker("setDate", toDate);			
				$('#fromDate').datepicker( "option", "maxDate", toDate).datepicker("setDate", yearAgo);
				break;
			default:
				$('#toDate').datepicker("setDate", toDate);			
				$('#fromDate').datepicker( "option", "maxDate", toDate).datepicker("setDate", monthAgo);
				break;
		}
	}
	
	_faillist.getDate = function(element) {
		var date;
		try {
			date = $.datepicker.parseDate("yy.mm.dd", element.value );
		} catch( error ) {
			date = null;
		}
		return date;
	}
	
	_faillist.searchValidate = function(){

		/**
		 * 주문자 정보
		 */
		switch ($("#orderSearchKey").val()) {
			case "buyerName":
				console.log("validate orderSearchKey buyerName")
				break;		
			case "recieverName":
				console.log("validate orderSearchKey recieverName")
				break;
			case "buyerId":
				console.log("validate orderSearchKey buyerId")
				break;
			case "hdphnNo":
				console.log("validate orderSearchKey hdphnNo")
				break;
			default:
				break;
		}
		

		/**
		 * 주문번호
		 */
		
		
		/**
		 * 검색 기간
		 */
		var fromDate = new Date($('#fromDate')[0].value);
		var toDate = new Date($('#toDate')[0].value);
		var searchYear = new Date(toDate);
		searchYear.setFullYear(toDate.getFullYear() - 1)
		
		if(fromDate > toDate){
			alert("검색시작일은 종료일보다 미래 일 수 없습니다.\n검색기간을 재설정 해 주십시오.");
			return;
		} else if(fromDate < searchYear) {
			alert("검색기간은 1년을 넘을 수 없습니다.\n검색기간을 재설정 해 주십시오.");
			return;
		}
		
		
		_faillist.search();
	}
	
	_faillist.search = function(){
		var pageCount = $('#pageCount').val();
		
		var param = { url : "/order/account-transfer-fail-list/search"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.listForm)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);
	}
	
	_faillist.searchReset = function(){
		$("#siteNoAll").prop("checked", true);
		$("#orderSearchKey").val("buyerName");
		$("#orderSearchText").val("");
		$("#orderNo").val("");
		$("#procYnAll").prop("checked", true);
		$("#searchRefundAccountAll").prop("checked", true);
		$("#searchDateType").val("orderDtm");
		_faillist.setSearchDate();
	}
	
	_faillist.popupRefundInfo = function(params) {
		var url = "/order/account-transfer-fail-list/popRefundInfo";
		params.callback = "abc.biz.order.accounttransfer.faillist.registRefundInfo";
		abc.open.popup({
			winname :	"refundAccount",
			url 	:	url,
			width 	:	500,
			height	:	300,
			params	:	params
		});
	}
	
	_faillist.registRefundInfo = function(refundInfo) {
		$.ajax({
			type :"post",
			url : "/order/account-transfer-fail-list/registRefundInfo",
			data : refundInfo
		})
		.done(function(result){
			_faillist.search();
		})
		.fail(function(e){
			console.log(e);
		});
	}
	
	_faillist.refundComplete = function(){
		var sRow = listSheet.FindCheckedRow("updateCheck").split("|");
		
		if(sRow == ""){
			alert("상품을 선택하시기 바랍니다.");
			return false;
		}
		
		if(!confirm("선택하신 "+sRow.length+"건을 환불처리 완료하시겠습니까?")){
			return false;
		}
		var params = new Array();
		var alreadyProcCnt = 0;
		var updateProcDate = true;
		if(sRow.length > 0){	
			//validation check를 위한 loop
			for(var row of sRow) {				
				if(listSheet.GetRowData(row).pymntOrganNoText == null || listSheet.GetRowData(row).pymntOrganNoText == ""){
					alert('환불계좌 정보 미입력 건이 있습니다. \n환불 계좌 정보 입력 후 환불처리가 가능합니다.');
					listSheet.SetCellValue(row, "updateCheck", "0");
					return false;
				}
				if(listSheet.GetRowData(row).procYn == "Y"){
					alreadyProcCnt++;					
				}
			}
			
			if(alreadyProcCnt > 0) {
				if(!confirm('이미 환불 완료 처리된 건이 '+alreadyProcCnt+'건 있습니다. \n현재 일자 기준으로 처리일을 변경하시겠습니까?')){
					updateProcDate = false;
				}
			}
			
			for(var row of sRow) {
				var isUpdate = true;
				if(listSheet.GetRowData(row).procYn == "Y"){
					isUpdate = updateProcDate;
				}
				params.push({
					orderNo : listSheet.GetRowData(row).orderNo,
					orderPymntSeq : listSheet.GetRowData(row).orderPymntSeq,
					updateProcDate : isUpdate
				}); 
			}
		}
		
		if(params.length > 0){
			$.ajax({
				type :"post",
				url : "/order/account-transfer-fail-list/refundComplete",
				contentType: 'application/json',
				data : params
			})
			.done(function(result){
				_faillist.search();
			})
			.fail(function(e){
				console.log(e);
			});
		}
	}
})();