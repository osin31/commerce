/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _urlLog = abc.object.createNestedObject(window,"abc.biz.url.log.history");
	// accessType을 배열에 정의
	var arrLogCell = ["c01Cnt", "c02Cnt", "c03Cnt", "r01Cnt", "r02Cnt"
		            , "r03Cnt", "r04Cnt", "r05Cnt", "r06Cnt", "r07Cnt"
		            , "r08Cnt", "r09Cnt", "r10Cnt", "r11Cnt", "r12Cnt"
		            , "u01Cnt", "u02Cnt", "u03Cnt", "u04Cnt", "u05Cnt"
		            , "u06Cnt", "u07Cnt", "d01Cnt", "d02Cnt", "d03Cnt"
		            ];

	/**
	 * ibsheet 초기화
	 */
	_urlLog.sheetInit = function(){
		var pageCount = $('#pageCount').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("urlLogGrid"), "urlLogSheet", "100%", "300px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"번호", 			Type:"Text",		SaveName:"menuAccessHist",		MinWidth:50,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"관리자ID", 		Type:"Text",		SaveName:"loginId",				MinWidth:50,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"권한ID", 			Type:"Text",		SaveName:"authName",			MinWidth:80,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접근경로",			Type:"Text",		SaveName:"accessUrl",			MinWidth:80,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접근 파라미터",		Type:"Text",		SaveName:"accessParamtrText",	MinWidth:100,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"접속IP",			Type:"Text",		SaveName:"accessIpText",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}    
						, {Header:"성공 여부",			Type:"Text",		SaveName:"successYn",			MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"성공사유",			Type:"Text",		SaveName:"successRsnText",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"결과개수",			Type:"Text",		SaveName:"rsltCount",			MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"결과 DESC",		Type:"Text",		SaveName:"rsltDescText",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"등록일시",			Type:"Text",		SaveName:"rgstDtm",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Format:"YmdHms"}
						];
		// Grid 초기화
		IBS_InitSheet(urlLogSheet, initSheet);
		// Grid 건수 정보 표시
		urlLogSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		urlLogSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		urlLogSheet.FitColWidth();
//		urlLogSheet.SetFrozenCol(3);
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//urlLogSheet.SetExtendLastCol(1);

	}

	_urlLog.event = function(){
		// enter 검색
		$(".search-wrap")
		.find(".ui-input, .js-ui-cal")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.on("keyup", function(e) {
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
			$("a[name^=per1Month]").trigger('click');
			$('select#menuNo1').val('').trigger('change');
		});
		
		/** 캘린더 버튼 설정(일) */
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		/** 캘린더 버튼 설정(주) */
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		/** 캘린더 버튼 설정(월) */
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		/** 캘린더 버튼 설정(1년) */
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		$('#pageCount').on('change', function(event) {
			urlLogSheet.SetPageCount(this.value);	// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			_urlLog.getList()
		});
		
		$('#menuNo1, #menuNo2, #menuNo3').on('change', function() {
			var menuNo 	= $(this).val();
			var dispYn 	= "Y";
			var useYn 	= "Y";
			var name	= this.name; 
			var target  = "";
			$('#menuNo').val(menuNo);
			
			if(name == "menuNo3"){
				return;
			}else{
				if(name == "menuNo1"){
					$('#menuNo2').hide();
					$('#menuNo2').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
					$('#menuNo3').hide();
					$('#menuNo3').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
					target = $('#menuNo2');
				}else if(name == "menuNo2"){
					$('#menuNo3').hide();
					$('#menuNo3').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
					target = $('#menuNo3');
				}

				var param = {'upMenuNo' : menuNo, 'dispYn' : dispYn,'useYn':useYn};

				$.ajax({
					type : 'post',
					url : '/system/menu',
					data : param
				}).done(function(data) {
					var dataLength = data.length;
					
					if(dataLength > 0){
						$(target).show();
					}
					
					$.each(data, function(i,v) {
						var $option = $('<option>').val(v.menuNo).text(v.menuName);
						$(target).append($option);
					});				
				}).fail(function(e) {
					console.log("e :" + e);
				})
			}
		});
	}

	/**
	 * 그리드 데이터 조회
	 */
	_urlLog.getList = function(){
		var menuNo1 = $('#menuNo1').val();
		var menuNo2 = $('#menuNo2').val();
		
		var adminNo = $('#adminNo').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var menuNo = "";
		
		var param = { url : "/system/admin-log-history-detail/read-list"
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
//		alert("searchEnd" + urlLogSheet.GetDataLastRow());
	}

	urlLogSheet_OnRowSearchEnd = function(row){
		//접속 로그가 있는 컬럼의 underline으로 링크가 있음을 표시
		for(i=0; i<arrLogCell.length; i++){
			var acceseType = arrLogCell[i];
			if(urlLogSheet.GetCellValue(row, acceseType) > 0){
				urlLogSheet.SetCellFontUnderline(row, acceseType, 1);
			}
		}
	}
	
	
	urlLogSheet_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row > 1) {	// 헤더가 병합으로 인해 0~1까지 이므로 1보다 큰것으로 구분한다.
			var colSaveName	= urlLogSheet.ColSaveName(Col);	// 컬럼의 saveName
			var cellValue 	= urlLogSheet.GetCellValue(Row, colSaveName);	// 셀의 값[0,1,2...접속한 횟수]
			var cntIndex 	= arrLogCell.indexOf(colSaveName);	// 링크가 연결된 '횟수' 항목의 index[0~24]
			
			if (cellValue > 0 && cntIndex >= 0) {
				var url = "/system/menu-log-history-detail";
				var params = {}
				params.memberNo = urlLogSheet.GetCellValue(Row, "memberNo");
				
				abc.open.popup({
					winname :	"menuLogHistoryDetail",
					url 	:	url,
					width 	:	1480,
					height	:	985,
					params	:	params
				});
			}
		}
	}
	
	$(function(){
		_urlLog.sheetInit();
		_urlLog.event();
	});
})();

$(document).ready(function(){
	//$("a[name^=per1Month]").trigger('click');
	
	abc.biz.url.log.history.getList();	
	
});
