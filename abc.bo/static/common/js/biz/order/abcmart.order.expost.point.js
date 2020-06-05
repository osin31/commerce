/**
 * 포인트 사후적립 목록
 */
(function(){
	
	var _list = abc.object.createNestedObject(window,"abc.biz.order.expost.list");
	
	_list.init = function() {
		// 검색 
		$("#searchBtn").click(function(){
			abc.biz.order.expost.list.searchValidate();
		});
		
		//검색 초기화
		$("#searchReset").click(function(){
			abc.biz.order.expost.list.searchReset();
		});
		
		//검색기간
		$("#termsBtn > a").each(function(index){
			$(this).click(function(){
				abc.biz.order.expost.list.setSearchDate($(this).attr("name"))
			})
		})
		
		//검색 조건 
		$("#orderSearchKey").on('change', function(){
			if(this.value == null || this.value == '')
				$("#orderSearchText").attr("readOnly", true);
			else
				$("#orderSearchText").removeAttr("readOnly");
		})	
		
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		
		initSheet.Cols = [
			  {Header:"번호", 		Type:"Seq",		SaveName:"",				Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			 , {Header:"" ,		 	Type:"Text",	SaveName:"memberNo",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"onlnBuyYn",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"buyNoText",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"적립구분", 		Type:"Combo",	SaveName:"siteNo",			Width: 10, Align:"Center", 	Sort:0,	Edit:0}			
			, {Header:"적립채널", 		Type:"Combo",	SaveName:"adminRgstYn",		Width: 10, Align:"Center", 	ComboText:"FO|BO", ComboCode:"N|Y", Sort:0,	Edit:0}
			, {Header:"주문일시", 		Type:"Text",	SaveName:"orderDtm",		Width: 20, Align:"Center", 	Sort:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
			, {Header:"주문(거래)번호",	Type:"Text",	SaveName:"orderNo",			Width: 20, Align:"Center",	Sort:1, Edit:0}
			, {Header:"상품명", 		Type:"Text",	SaveName:"orderPrdtInfo",	Width: 30, Align:"Center", 	Sort:0, Edit:0}
			, {Header:"결제금액", 		Type:"Float",	SaveName:"pymntAmt",		Width: 20, Align:"Center", 	Sort:1,	"Format": "#,##0원", Edit:0}
			, {Header:"적립포인트", 	Type:"Float",	SaveName:"saveAmt",			Width: 20, Align:"Center", 	Sort:1,	"Format": "#,##0Point", Edit:0}
			, {Header:"주문자", 		Type:"Text",	SaveName:"buyerName",		Width: 20, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"" ,		 	Type:"Text",	SaveName:"saveLoginId",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"적립ID", 		Type:"Text",	SaveName:"saveMemberName",	Width: 20, Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0,	Edit:0}
			, {Header:"" ,		 	Type:"Text",	SaveName:"adminNo",			Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"adminLoginId",	Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"adminName",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"rgsterMemberNo",	Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"rgsterLoginId",	Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"" ,		 	Type:"Text",	SaveName:"rgsterName",		Width: 0,  Align:"", 		Edit:0,	Hidden:1}
			, {Header:"처리자ID",		Type:"Text",	SaveName:"manager",			Width: 20, Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0,	Edit:0}
			, {Header:"적립일시", 		Type:"Text",	SaveName:"saveDtm",			Width: 20, Align:"Center", 	Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
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
				$('#fromDate').datepicker("option", "maxDate", _list.getDate(this));
			});	
		
		this.setSearchDate();
	}
	
	_list.setSearchDate = function(term){
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
	
	_list.getDate = function(element) {
		var date;
		try {
			date = $.datepicker.parseDate("yy.mm.dd", element.value );
		} catch( error ) {
			date = null;
		}
		return date;
	}
	
	_list.searchValidate = function(){

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
		
		_list.search();
	}
	
	_list.search = function(){
		var pageCount = $('#pageCount').val();
		
		var param = { url : "/order/expost-point-list/search"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.listForm)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);
	}
	
	_list.searchReset = function(){
		$("#onlnBuyYnAll").prop("checked", true);
		$("#adminRgstYnAll").prop("checked", true)
		$("#orderSearchKey").val("buyerName").prop('selected', true);
		$("#orderSearchText").val("");
		$("#orderNo").val("");
		_list.setSearchDate();
	}
	
})();