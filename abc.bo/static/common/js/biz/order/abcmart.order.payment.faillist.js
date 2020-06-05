/**
 * 결제 실패 목록
 */
(function(){
	
	var _faillist = abc.object.createNestedObject(window,"abc.biz.order.payment.faillist");
	
	_faillist.init = function() {
		// 검색 
		$("#searchBtn").click(function(){
			abc.biz.order.payment.faillist.searchValidate();
		});
		
		//검색 초기화
		$("#searchReset").click(function(){
			abc.biz.order.payment.faillist.searchReset();
		});
		
		$("#termsBtn > a").each(function(index){
			$(this).click(function(){
				abc.biz.order.payment.faillist.setSearchDate($(this).attr("name"))
			})
		})
		
		$("#chkPaymentAll").click(function(){
			if($(this).is(":checked")){
				$("input[name=chkPaymentModule]").prop("checked",true);
				$("#escroDiv").show();
				$("#chkEscroTrue").prop("checked",true);				
				$("#chkEscroFalse").prop("checked",true);
			} else {
				$("input[name=chkPaymentModule]").prop("checked",false);
				$("#escroDiv").hide();
				$("#chkEscroTrue").prop("checked",false);
				$("#chkEscroFalse").prop("checked",false);
			}
		})
		
		$("input[name=chkPaymentModule]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[name=chkPaymentModule]:not(:checked)");
					if (unChecked.length == 0) 
						$("#chkPaymentAll").prop("checked",true);					
					if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){						
						$("#escroDiv").show();
						$("#chkEscroTrue").prop("checked",true);				
						$("#chkEscroFalse").prop("checked",true);
					}
				} else {
					$("#chkPaymentAll").prop("checked",false);
					if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){
						if(!$("#chkPayment10001").is(":checked") && !$("#chkPayment10002").is(":checked")){
							$("#escroDiv").hide();
							$("#chkEscroTrue").prop("checked",false);
							$("#chkEscroFalse").prop("checked",false);
						}
					}
				}
			})
		})
		
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		
		initSheet.Cols = [
			  {Header:"" ,		 	Type:"Status",	SaveName:"orderStatCode",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"번호", 		Type:"Seq",		SaveName:"",					Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"사이트", 		Type:"Combo",	SaveName:"siteNo",				Width: 10, Align:"Center", 	Sort:0,	Edit:0}			
			, {Header:"결제구분", 		Type:"Text",	SaveName:"deviceCodeName",		Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"주문번호", 		Type:"Text",	SaveName:"orderNo",				Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"", 			Type:"Text",	SaveName:"memberNo",			Width: 10, Align:"", 		Edit:0,	Hidden:1}
			, {Header:"주문자", 		Type:"Text",	SaveName:"buyerName",			Width: 10, Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0,	Edit:0}
			, {Header:"결제수단", 		Type:"Text",	SaveName:"pymntMeansCodeName",	Width: 20, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"에스크로결제", 	Type:"Text",	SaveName:"escrApplyYn",			Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"결제금액", 		Type:"Float",	SaveName:"pymntAmt",			Width: 20, Align:"Center", 	Sort:1,	"Format": "#,##0 원", Edit:0}
			, {Header:"오류메세지", 	Type:"Text",	SaveName:"rspnsMesgText",		Width: 20, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"결제일시", 		Type:"Text",	SaveName:"rgstDtm",			Width: 20, Align:"Center", 	Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
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
		listSheet.SetExtendLastCol(1);
			
		var toDate = new Date(); 
		
		
		$('#fromDate').datepicker( "option", "maxDate", toDate);
		
		$('#toDate').datepicker( "option", "maxDate", toDate)
			.on("change", function(){
				$('#fromDate').datepicker("option", "maxDate", _faillist.getDate(this));
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
		 * 결제수단
		 */
		if($("#orderNo").val() == "" && !$("input[name='chkPaymentModule'").is(':checked')){
			alert("결제수단을 선택 해 주시기 바랍니다.");
			return;
		}
		
		
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
		
		var param = { url : "/order/payment-fail-list/search"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.listForm)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);
	}
	
	_faillist.searchReset = function(){
		$("#siteNoAll").prop("checked", true);
		$("#deviceCodeAll").prop("checked", true);
		$("#orderSearchKey").val("buyerName");
		$("#orderSearchText").val("");
		$("#orderNo").val("");
		_faillist.setSearchDate();
	}
	
})();