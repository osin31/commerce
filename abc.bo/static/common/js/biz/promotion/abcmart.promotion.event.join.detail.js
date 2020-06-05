(function() {

	var _eventJoinDetail = abc.object.createNestedObject(window,"abc.biz.promotion.event.join.detail");
	
	/**
	 * 초기화
	 */
	_eventJoinDetail.init = function(){
		console.log('init');
		_eventJoinDetail.sheet.init();
		_eventJoinDetail.event();
		_eventJoinDetail.setMonth();
		
		abc.biz.display.common.checkBoxAll({allId: '#chkMemberTypeAll', itemsClass: '[name=memberTypeCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkDevice01', itemsClass: '[name=deviceCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkIssued01', itemsClass: '[name=issueYnArr]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkOutcrop01', itemsClass: '[name=dispYnArr]'});
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		_eventJoinDetail.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventJoinDetail.sheet = {};
	_eventJoinDetail.sheet.init = function(){
		createIBSheet2(document.getElementById("eventJoinSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		var eventTypeCode = $('[name=eventTypeCode]').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init', "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols =  _eventJoinDetail.eventTypeCols(eventTypeCode);
		
		IBS_InitSheet(list, initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		if(eventTypeCode == '10000') {
			if($('#eventJoinType').val() == 'C') list.SetColHidden('addInfo1', 1);
		}
		
		if(eventTypeCode == '10001') {
			var dispRsnCode = abc.biz.promotion.event.join.detail.codeCombo;
			list.InitDataCombo(0 , "unDispRsnCode" , dispRsnCode.UN_DISP_RSN_CODE.text , dispRsnCode.UN_DISP_RSN_CODE.code  );
		}
		
		_eventJoinDetail.sheet.event();
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventJoinDetail.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "dispYnReqName") {
					var modifyDispYn = '';
					var unDispRsnCode = '';
					var unDispRsnText = '';
					if(list.GetRowData(row).dispYn == 'Y') {
						modifyDispYn = 'N';
						unDispRsnCode = list.GetRowData(row).unDispRsnCode;
						unDispRsnText = list.GetRowData(row).unDispRsnText;
						if(unDispRsnCode == '') {
							alert('사유유형을 선택해주세요.');
							return false;
						}
					} else {
						modifyDispYn = 'Y'
					}
					$.ajax({
			            type :'post',
			            data : {
			            	eventAswrSeq : list.GetRowData(row).eventAswrSeq
			            	,dispYn : modifyDispYn
			            	,unDispRsnCode : unDispRsnCode
			            	,unDispRsnText : unDispRsnText
			            },
			            url:'/promotion/event/answer/modify'
			        }).done(function(d){
			        	alert('변경 되었습니다.');
			        	_eventJoinDetail.getList();
			        }).fail(function(e){
			        	alert(e.message);
				    	console.log(e);
			        });
				}
				
				if ( list.ColSaveName(col) == "issueYnReqName") {
					if(list.GetRowData(row).issueYn == 'N' && list.GetRowData(row).benefitGbnCode == '10002') {
						$.ajax({
							type :'post',
							data : {
								eventRuletJoinMemberSeq : list.GetRowData(row).eventRuletJoinMemberSeq
								,issueYn : 'Y'
							},
							url:'/promotion/event/roulette/modify'
						}).done(function(d){
							alert('변경 되었습니다.');
							_eventJoinDetail.getList();
						}).fail(function(e){
							alert(e.message);
							console.log(e);
						});
					}
				}
			}
		}
		
		/*list_OnChange = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {					
			if (row != 0) {
				if (list.ColSaveName(col) == 'unDispRsnCode') {
					$.ajax({
			            type :'post',
			            data : {
			            	eventAswrSeq : list.GetRowData(row).eventAswrSeq
			            	,unDispRsnCode : list.GetRowData(row).unDispRsnCode
			            },
			            url:'/promotion/event/answer/modify'
			        }).done(function(d){
			        	alert('변경 되었습니다.');
			        	_eventJoinDetail.getList();
			        }).fail(function(e){
			        	alert(e.message);
				    	console.log(e);
			        });
				}
			}
		}*/
		
		list_OnMouseMove = function(button, shift, x, y){
		    if(list.ColSaveName(list.MouseCol()) == 'aswrContText') {
		    	list.GetMouseToolTipText();
		    }
		}
		
		list_OnSearchEnd = function(code, msg, stCode, stMsg, responseText) {
			var searchList = list.ExportData({"Type":"json"}).data;
			
			$.each(searchList, function(i, v) {
				list.SetToolTipText(v.seq, 'aswrContText', v.aswrContText);
			});
		}
		
		list_OnRowSearchEnd = function(row) {
			console.log(list.GetRowData(row).benefitGbnCode);
			console.log(list.GetRowData(row).issueYn);
			if(list.GetRowData(row).benefitGbnCode == '10002') {
				if(list.GetRowData(row).issueYn == 'Y') {
					list.SetCellValue(row, "issueYnReqName", "발급완료");
				} else {
					list.SetCellValue(row, "issueYnReqName", '<button type="button" class="GMCellButtonBase GMCellButton">발급처리</button>');
				}
			} else {
				list.SetCellValue(row, "issueYnReqName", "-");
			}
		}
		
	}
	
	/**
	 * 이벤트
	 */
	_eventJoinDetail.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_eventJoinDetail.getList();
			return false;
		});
		
		$('#excelDownload').on('click', function(e){
			$('[name=excelForm]').attr('action', '/promotion/event/join/member/excel/form/download');
			$('[name=excelForm]').submit();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm', false);
			
			_eventJoinDetail.setMonth();
			$("input[type=checkbox]").prop("checked",true);
		});
		
		$('#pageCount').on('change', function() {
			_eventJoinDetail.getList();
		});
	}
	
	/**
	 * 리스트 조회
	 */
	_eventJoinDetail.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/promotion/event/join/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" };
		
		DataSearchPaging(param);
		
	}
	
	_eventJoinDetail.eventTypeCols = function(eventTypeCode) {
		var sheetCols = [];
		switch (eventTypeCode) {
		case "10000":		//일반형
			sheetCols= [
				 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header :"회원ID" , Type:"Text", SaveName:"loginIdMaskNoAuth", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				, {Header :"회원등급" , Type:"Text", SaveName:"memberTypeCodeName",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
				, {Header : "응모일시", Type:"Date", SaveName:"joinDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header : "입력ID/입력번호" , Type:"Text", SaveName:"addInfo1", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "발급쿠폰명" , Type:"Text", SaveName:"cpnName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "입력번호" , Type:"Text", SaveName:"addInfo1", Width: 15,  Align:"Center", Edit:0, FontBold:1}*/
			];
			break;
		case "10001" :	//댓글형
			sheetCols= [
				 {Header : "번호", Type:"Seq", SaveName:"seq", Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header : "댓글 Seq" , Type:"Text", SaveName:"eventAswrSeq", Width: 15,  Align:"Center", Edit:0, FontBold:1, Hidden:1}
				, {Header : "회원ID" , Type:"Text", SaveName:"loginIdMaskNoAuth", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "회원등급" , Type:"Text", SaveName:"memberTypeCodeName",  Width: 25, Align:"Center", Edit:0, FontBold:1}
				, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
				, {Header : "댓글 내용" , Type:"Text", SaveName:"aswrContText",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "응모일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header : "노출여부값", Type:"Text", SaveName:"dispYn", Width: 15, Align:"Center", Edit:0, Hidden:1}
				, {Header : "사유", Type:"Combo", SaveName:"unDispRsnCode", Width: 30, Align:"Center", Edit:1 }
				, {Header : "사유", Type:"Text", SaveName:"unDispRsnText", Width: 30, Align:"Center", Edit:1 }
				, {Header : "사유", Type:"Button", SaveName:"dispYnReqName", Width: 15, Align:"Center", Edit:0, DefalutValue:"노출"}
				, {Header : "최종수정자", Type:"Text", SaveName:"moderInfoNoAuth", Width: 20, Align:"Center", Edit:0}
				, {Header : "최종수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			];
			break;
		case "10002" :	//출석체크
			sheetCols= [
				 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header : "회원ID" , Type:"Text", SaveName:"loginIdMaskNoAuth", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "회원등급" , Type:"Text", SaveName:"memberTypeCodeName", Width: 30,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
				, {Header : "등록일", Type:"Date", SaveName:"atendDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header : "출석일수", Type:"Text", SaveName:"benefitAtendDayCount", Width: 15, Align:"Center", Edit:0}
				, {Header : "제공혜택", Type:"Text", SaveName:"benefitName", Align:"Center", Edit:0}
			];
			break;
		case "10003" :
			sheetCols= [	//드로우
				 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header : "회원ID" , 	Type:"Text", SaveName:"loginIdMaskNoAuth", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "회원등급" ,	Type:"Text", SaveName:"memberTypeCodeName",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "디바이스" ,	Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
				, {Header : "대상상품" , 	Type:"Text", SaveName:"prdtName",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "신청옵션정보",	Type:"Text", SaveName:"optnName", Width: 15, Align:"Center", Edit:0}
				, {Header : "이름", 		Type:"Text", SaveName:"addInfo1MaskNoAuth", Width: 15, Align:"Center", Edit:0}
				, {Header : "휴대폰번호", 	Type:"Text", SaveName:"addInfo2MaskNoAuth", Width: 15, Align:"Center", Edit:0}
				, {Header : "이메일", 		Type:"Text", SaveName:"addInfo3MaskNoAuth", Width: 15, Align:"Center", Edit:0}
				, {Header : "수령정보",	Type:"Text", SaveName:"storeName", Width: 15, Align:"Center", Edit:0}
				, {Header : "등록일", 		Type:"Date", SaveName:"joinDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			];
			break;
		case "10004" :
			sheetCols= [	//룰렛
				 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
				, {Header : "번호", Type:"Seq", SaveName:"eventNo", Width: 10, Align:"Center", 	Edit:0, Sort:0 , Hidden:1}
				, {Header : "룰렛 Seq", Type:"Text", SaveName:"eventRuletJoinMemberSeq", Width: 10, Align:"Center", 	Edit:0, Sort:0 , Hidden:1}
				, {Header : "회원ID" , Type:"Text", SaveName:"loginIdMaskNoAuth", Width: 30,  Align:"Center", Edit:0, FontBold:1}
				, {Header : "회원등급" , Type:"Text", SaveName:"memberTypeCodeName",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
				/*, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
				, {Header : "혜택코드", Type:"Text", SaveName:"benefitGbnCode", Width: 30, Align:"Center", Hidden:1}
				, {Header : "혜택명" , Type:"Text", SaveName:"benefitName",  Align:"Center", Edit:0, FontBold:1}
				, {Header : "응모일시", Type:"Date", SaveName:"joinDtm", Width: 40, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header : "발급여부" , Type:"Text", SaveName:"issueYn",  Align:"Center", Edit:0, Width: 40, Hidden:1}
				, {Header : "발급여부" , Type:"Html", SaveName:"issueYnReqName",  Align:"Center", Width: 40, Edit:0}
				, {Header : "발급일", Type:"Date", SaveName:"issueDtm", Width: 30, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header : "최종수정자", Type:"Text", SaveName:"moderInfoNoAuth", Width: 30, Align:"Center", Edit:0}
				, {Header : "최종수정일시", Type:"Date", SaveName:"modDtm", Width: 30, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			];
			break;
		default :
			console.log("오류입니다.");
		}
		
		return sheetCols;
	} 
	
	_eventJoinDetail.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	_eventJoinDetail.yearCheck = function(compareYear) {
		var yearCheck = true;
		var startYmd = $('#startYmd').val();
		var endYmd = $('#endYmd').val();
		var startDate = '';
		var endDate = '';
		var betweenDate = '';
		if(startYmd != '' && endYmd != '') {
			startYmd = startYmd.split('.');
			endYmd = endYmd.split('.');
		} else {
			return yearCheck;
		}
		
		startDate = new Date(startYmd[0], startYmd[1], startYmd[2]);
		endDate = new Date(endYmd[0], endYmd[1], endYmd[2]);
		betweenDate = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
		
		if(betweenDate < 0) {
			alert('시작일은 종료일 이전 날짜만 가능합니다.');
			yearCheck = false;
		} else if(betweenDate > (compareYear * 365)) {
			alert('조회기간은 최대' + compareYear + '년까지 가능합니다.');
			yearCheck = false;
		}
		
		return yearCheck;
	}
	
	$(function() {
		_eventJoinDetail.init();
	});
	
})();