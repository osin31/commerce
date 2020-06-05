/***
 * 클레임 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _bankda = abc.object.createNestedObject(window,"abc.biz.claim.bankda");
	
	_bankda.init = function() {
		// 검색 
		$("#searchBtn").click(function(){
			abc.biz.claim.bankda.searchValidate();
		});
		
		//검색 초기화
		$("#searchReset").click(function(){
			abc.biz.claim.bankda.searchReset();
		});
		
		$("#termsBtn > a").each(function(index){
			$(this).click(function(){
				abc.biz.claim.bankda.setSearchDate($(this).attr("name"))
			})
		})
		
		$("#processBtn").click(function(){
			abc.biz.claim.bankda.updateConfirm();
		})
		
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		
		initSheet.Cols = [
			  {Header:"",			Type:"CheckBox",	SaveName:"updateCheck",	Width: 7,  Align:"Center", Edit:1, Sort:0}
			, {Header:"", 			Type:"Text",		SaveName:"bkcode",		Width: 10, Align:"", Edit:0, Hidden:1}
			, {Header:"일시", 		Type:"Date",		SaveName:"bkdate", 		Width: 30, Align:"Center", Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0, Edit:0}
			, {Header:"은행명", 		Type:"Combo",		SaveName:"acctno",		Width: 15, Align:"Center", ComboText:"기업은행|국민은행|우리은행|하나은행|우체국|국민(ART)|국민은행(OTS)", ComboCode:"13706230701011|26880104120581|1005901419208|39891001170504|01450601004780|38500104017427|37530104004127", Sort:0, Edit:0}
			, {Header:"", 			Type:"Text",		SaveName:"bkjukyo",		Width: 10, Align:"", Edit:0, Hidden:1}
			, {Header:"", 			Type:"Text",		SaveName:"bkcontent",	Width: 10, Align:"", Edit:0, Hidden:1}
			, {Header:"", 			Type:"Text",		SaveName:"bketc",		Width: 10, Align:"", Edit:0, Hidden:1}
			, {Header:"거래내역", 		Type:"Text",		SaveName:"acctHistory",	Width: 50, Align:"Center", Sort:0, Edit:0}
			, {Header:"입금확인구분", 	Type:"Combo",		SaveName:"depositconfirm", Width: 20, Align:"Center", ComboText:"확인|대기", ComboCode:"Y|N", Sort:0, Edit:0}
			, {Header:"입금정보", 		Type:"Html",		SaveName:"depositinfo",	Width: 20, Align:"Center"}
			, {Header:"입금구분", 		Type:"Text",		SaveName:"depositgubun",Width: 20, Align:"Center", Sort:0, Edit:0}
			, {Header:"완료일시", 		Type:"Date",		SaveName:"depositdtm", 	Width: 30, Align:"Center", Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0, Edit:0}
			, {Header:"입금액", 		Type:"Float",		SaveName:"bkinput",		Width: 20, Align:"Center", Format: "#,##0원", Sort:0, Edit:0}
			, {Header:"출금액", 		Type:"Float",		SaveName:"bkoutput",	Width: 20, Align:"Center", Format: "#,##0원", Sort:0, Edit:0}
			, {Header:"잔액", 		Type:"Float",		SaveName:"bkjango",		Width: 20, Align:"Center", Format: "#,##0원", Sort:1, Edit:0}
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
	
	_bankda.setSearchDate = function(term){
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
	
	_bankda.searchValidate = function(){
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
		
		_bankda.search();
	}
	
	_bankda.search = function(){
		var pageCount = $('#pageCount').val();
		
		var param = { url : "/claim/bankda/list/search"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.frmSearch)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);		
		_bankda.todayInfo();
	}
	
	_bankda.todayInfo = function(){
		$.ajax({
			type :"post",
			url : "/claim/bankda/todayInfo",
			data: { "acctno" : $("#acctno").val()}
		}).done(function(result){
			var todayInput = new Number();
			var todayOutput = new Number();
			var jango = new Number();
			var pattern = "#,##0.####원";
			if(result.todayInfo != null){
				var todayInfo = result.todayInfo;
				if(todayInfo.todayInput != null)
					todayInput = new Number(todayInfo.todayInput);
				if(todayInfo.todayOutput != null)
					todayOutput = new Number(todayInfo.todayOutput);
				if(todayInfo.jango != null)
					jango = new Number(todayInfo.jango);
			}
			$("#todayInput").html(todayInput.format(pattern));
			$("#todayOutput").html(todayOutput.format(pattern));
			$("#jango").html(jango.format(pattern));
		})
		.fail(function(e){
			var todayInput = new Number();
			var todayOutput = new Number();
			var jango = new Number();
			var pattern = "#,##0.####원";
			
			$("#todayInput").html(todayInput.format(pattern));
			$("#todayOutput").html(todayOutput.format(pattern));
			$("#jango").html(jango.format(pattern));
		});
	}
	
	_bankda.searchReset = function(){
		$("#acctno").val("26880104120581");
		$("#bkjukyo").val("");
		$("#siteNoAll").prop("checked", true);
		_bankda.setSearchDate();
	}
	
	_bankda.popupDeposit = function(_row) {
		var url = "/claim/bankda/deposit/popup";
		var params = {}
		params.bkcode = listSheet.GetCellValue(_row, "bkcode");
		params.callback = "abc.biz.claim.bankda.saveDeposit";
		
		abc.open.popup({
			winname :	"bankdaRegistPop",
			url 	:	url,
			width 	:	500,
			height	:	250,
			params	:	params
		});
	}
	
	_bankda.saveDeposit = function(_data){
		$.ajax({
			type :"post",
			url : "/claim/bankda/update",
			data : _data
		})
		.done(function(result){
			_bankda.search();
		})
		.fail(function(e){
			console.log(e);
		});
	}
	
	_bankda.updateConfirm = function(){
		var processSel = $("#processSel").val();
		var sRow = listSheet.FindCheckedRow("updateCheck").split("|");

		if(processSel == null || processSel == '' || sRow == ""){
			alert("선택 항목이 없습니다.");
			return false;
		}
		
		var params = new Array();
		console.log(sRow);
		if(processSel == "confirm"){
			if(confirm("선택하신 항목을 입금확인 완료 처리 하시겠습니까?")){
				sRow.forEach(function(v){
					params.push({
						bkcode : listSheet.GetRowData(v).bkcode,
						depositconfirm : "Y"
					}); 
				});
			}
		}
			
		if(processSel == "cancel"){
			if(confirm("선택하신 항목을 입금확인 취소 처리 하시겠습니까?")){
				sRow.forEach(function(v){
					params.push({
						bkcode : listSheet.GetRowData(v).bkcode,
						depositconfirm : "N"
					}); 
				});
			}
		}

		if(params.length > 0){
			$.ajax({
				type :"post",
				url : "/claim/bankda/update",
				contentType: 'application/json',
				data : params
			})
			.done(function(result){
				alert("처리되었습니다.");
				_bankda.search();
			})
			.fail(function(e){
				console.log(e);
			});
		}
	}
})();