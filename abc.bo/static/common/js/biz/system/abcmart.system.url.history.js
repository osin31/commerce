/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _urlLog = abc.object.createNestedObject(window,"abc.biz.url.log.history");

	/**
	 * ibsheet 초기화
	 */
	_urlLog.sheetInit = function(){
		var pageCount = $('#pageCount').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("urlLogGrid"), "urlLogSheet", "100%", "529px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"번호", 		Type:"Seq",			SaveName:"Seq",					Align:"Center", 	Edit:0, Sort:0}
						, {Header:"관리자ID", 	Type:"Text",		SaveName:"loginId",				Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접근경로", 		Type:"Text",		SaveName:"accessUrl",			Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접근 파라미터", 	Type:"Text",		SaveName:"accessParamtrText",	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접속 아이피", 	Type:"Text",		SaveName:"accessIpText",		Align:"Center", 	Edit:0, Sort:0}
						, {Header:"성공여부", 		Type:"Text",		SaveName:"successYn",			Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실패사유", 		Type:"Text",		SaveName:"failRsnText",			Align:"Center", 	Edit:0, Sort:0}
						, {Header:"등록일시", 		Type:"Text",		SaveName:"rgstDtm",				Align:"Center", 	Edit:0, Sort:0, Format:"YmdHms"}
						];
		// Grid 초기화
		IBS_InitSheet(urlLogSheet, initSheet);
		// Grid 건수 정보 표시
		urlLogSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		urlLogSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		urlLogSheet.FitColWidth("5|12|12|20|15|12|12|12");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		urlLogSheet.SetExtendLastCol(1);

	}

	_urlLog.event = function(){
		// enter 검색
		$(".search-wrap")
		.find(".ui-input, .js-ui-cal")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.off().on("keyup", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
		    }
		});
		
		/**
		 * 검색
		 */
		$('#searchBtn').on("click", function(){
			_urlLog.getList();
		});

		/**
		 * 초기화
		 */
		$('#resetBtn').on("click", function(){
			$.form("#urlLogForm").reset();
		});
		
		$('#pageCount').on('change', function(event) {
			urlLogSheet.SetPageCount(this.value);	// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			_urlLog.getList()
		});
	}

	/**
	 * 그리드 데이터 조회
	 */
	_urlLog.getList = function(){
		var param = { url : "/system/url-log-history/read-list"
					, onePageRow : $('#pageCount').val()
					, subparam : FormQueryStringEnc(document.forms.urlLogForm)
				    , sheet : "urlLogSheet"};
		DataSearchPaging(param);
	}
	
	urlLogSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
//		if(urlLogSheet.RowCount() > 1){
//			$('#excel').removeClass("disabled");
//		}else{
//			$('#excel').addClass("disabled");			
//		}
//		urlLogSheet.SetRowBackColor(2, '#e2f5ff');
//		urlLogSheet.SetFrozenRows(2);
	}

	$(function(){
		_urlLog.sheetInit();
		_urlLog.event();
	});
})();