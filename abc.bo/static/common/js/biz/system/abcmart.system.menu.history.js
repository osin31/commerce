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
		createIBSheet2(document.getElementById("urlLogGrid"), "urlLogSheet", "100%", "529px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"메뉴 ID|메뉴 ID", 		Type:"Text",		SaveName:"menuNo",				MinWidth:50,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"메뉴 이름|메뉴 이름", 		Type:"Text",		SaveName:"menuName",			MinWidth:150,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"메뉴 경로|메뉴 경로", 		Type:"Text",		SaveName:"menuUrl",				MinWidth:150,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"C01|횟수", 			Type:"Text",		SaveName:"c01Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"C01|건수",				Type:"Text",		SaveName:"c01ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"C01|메뉴명",			Type:"Text",		SaveName:"c01Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"C02|횟수",				Type:"Text",		SaveName:"c02Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}    
						, {Header:"C02|건수",				Type:"Text",		SaveName:"c02ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"C02|메뉴명",			Type:"Text",		SaveName:"c02Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"C03|횟수",				Type:"Text",		SaveName:"c03Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"C03|건수",				Type:"Text",		SaveName:"c03ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"C03|메뉴명",			Type:"Text",		SaveName:"c03Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R01|횟수",				Type:"Text",		SaveName:"r01Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R01|건수",				Type:"Text",		SaveName:"r01ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R01|메뉴명",			Type:"Text",		SaveName:"r01Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R02|횟수",				Type:"Text",		SaveName:"r02Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R02|건수",				Type:"Text",		SaveName:"r02ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R02|메뉴명",			Type:"Text",		SaveName:"r02Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R03|횟수",				Type:"Text",		SaveName:"r03Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R03|건수",				Type:"Text",		SaveName:"r03ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R03|메뉴명",			Type:"Text",		SaveName:"r03Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R04|횟수",				Type:"Text",		SaveName:"r04Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R04|건수",				Type:"Text",		SaveName:"r04ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R04|메뉴명",			Type:"Text",		SaveName:"r04Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R05|횟수",				Type:"Text",		SaveName:"r05Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R05|건수",				Type:"Text",		SaveName:"r05ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R05|메뉴명",			Type:"Text",		SaveName:"r05Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R06|횟수",				Type:"Text",		SaveName:"r06Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R06|건수",				Type:"Text",		SaveName:"r06ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R06|메뉴명",			Type:"Text",		SaveName:"r06Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R07|횟수",				Type:"Text",		SaveName:"r07Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R07|건수",				Type:"Text",		SaveName:"r07ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R07|메뉴명",			Type:"Text",		SaveName:"r07Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R08|횟수",				Type:"Text",		SaveName:"r08Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R08|건수",				Type:"Text",		SaveName:"r08ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R08|메뉴명",			Type:"Text",		SaveName:"r08Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R09|횟수",				Type:"Text",		SaveName:"r09Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R09|건수",				Type:"Text",		SaveName:"r09ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R09|메뉴명",			Type:"Text",		SaveName:"r09Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R10|횟수",				Type:"Text",		SaveName:"r10Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R10|건수",				Type:"Text",		SaveName:"r10ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R10|메뉴명",			Type:"Text",		SaveName:"r10Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R11|횟수",				Type:"Text",		SaveName:"r11Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R11|건수",				Type:"Text",		SaveName:"r11ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R11|메뉴명",			Type:"Text",		SaveName:"r11Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"R12|횟수",				Type:"Text",		SaveName:"r12Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"R12|건수",				Type:"Text",		SaveName:"r12ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"R12|메뉴명",			Type:"Text",		SaveName:"r12Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U01|횟수",				Type:"Text",		SaveName:"u01Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U01|건수",				Type:"Text",		SaveName:"u01ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U01|메뉴명",			Type:"Text",		SaveName:"u01Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U02|횟수",				Type:"Text",		SaveName:"u02Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U02|건수",				Type:"Text",		SaveName:"u02ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U02|메뉴명",			Type:"Text",		SaveName:"u02Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U03|횟수",				Type:"Text",		SaveName:"u03Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U03|건수",				Type:"Text",		SaveName:"u03ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U03|메뉴명",			Type:"Text",		SaveName:"u03Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U04|횟수",				Type:"Text",		SaveName:"u04Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U04|건수",				Type:"Text",		SaveName:"u04ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U04|메뉴명",			Type:"Text",		SaveName:"u04Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U05|횟수",				Type:"Text",		SaveName:"u05Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U05|건수",				Type:"Text",		SaveName:"u05ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U05|메뉴명",			Type:"Text",		SaveName:"u05Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U06|횟수",				Type:"Text",		SaveName:"u06Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U06|건수",				Type:"Text",		SaveName:"u06ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U06|메뉴명",			Type:"Text",		SaveName:"u06Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"U07|횟수",				Type:"Text",		SaveName:"u07Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"U07|건수",				Type:"Text",		SaveName:"u07ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"U07|메뉴명",			Type:"Text",		SaveName:"u07Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"D01|횟수",				Type:"Text",		SaveName:"d01Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"D01|건수",				Type:"Text",		SaveName:"d01ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"D01|메뉴명",			Type:"Text",		SaveName:"d01Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"D02|횟수",				Type:"Text",		SaveName:"d02Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"D02|건수",				Type:"Text",		SaveName:"d02ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"D02|메뉴명",			Type:"Text",		SaveName:"d02Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						, {Header:"D03|횟수",				Type:"Text",		SaveName:"d03Cnt",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Cursor:"Pointer"}
						, {Header:"D03|건수",				Type:"Text",		SaveName:"d03ResultCnt",		MinWidth:40,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"D03|메뉴명",			Type:"Text",		SaveName:"d03Name",				MinWidth:40,	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
						];
		// Grid 초기화
		IBS_InitSheet(urlLogSheet, initSheet);
		// Grid 건수 정보 표시
		urlLogSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		urlLogSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		urlLogSheet.FitColWidth();
		urlLogSheet.SetFrozenCol(3);
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
		
		$('#menuNo1').on('change', function() {
			var menuNo 				= $(this).val();
			var dispYn 				= "Y";
			var useYn 				= "Y";
			
			var param = {'upMenuNo' : menuNo, 'dispYn' : dispYn,'useYn':useYn};
			
			if(menuNo == '') {
				$('#menuNo2').hide();
				$('#menuNo2').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
				return;
			}
			
			$.ajax({
				type : 'post',
				url : '/system/menu',
				data : param
			}).done(function(data) {
				var dataLength = data.length;
				var level = 0;	// 선택한 메뉴의 하위 메뉴가 몇 개가 있는지의 카운트
				
				if(dataLength > 0){
					level = data[0].level;
					// 2뎁스에 메뉴를 걸어놓은 메뉴의 경우 하위의 select box를 보여주지 않음. 
					// 리스트 조회 시 up_menu_no를 조건으로 조회하기 때문에 3뎁스 메뉴까지 걸린 경우 2뎁스 셀렉트박스까지 선택하게 해주고, 
					// 2뎁스에서 메뉴를 건 경우는 1뎁스의 메뉴까지 선택하게 해줘야 하위 메뉴에 대한 접근 로그를 검색할 수 있다
					if(level == 0){
						$('#menuNo2').hide();
						$('#menuNo2').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
					}else{
						$('#menuNo2').show();
					}
				}else{
					$('#menuNo2').hide();
					$('#menuNo2').html($('<option>').val('').text('-- 전체 --')).prop('selected', true);
				}
				
				$('#level').val(level);
				
				$.each(data, function(i,v) {
					var $option = $('<option>').val(v.menuNo).text(v.menuName);
					$('#menuNo2').append($option);
				});				
			}).fail(function(e) {
				console.log("e :" + e);
			})	
		});
	}

	/**
	 * 그리드 데이터 조회
	 */
	_urlLog.getList = function(){
		var level 	= $('#level').val();
		var menuNo1 = $('#menuNo1').val();
		var menuNo2 = $('#menuNo2').val();
		
		var adminNo = $('#adminNo').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var menuNo = "";
		
		if(menuNo1 == ''){
			alert("메뉴 카테고리를 선택하세요");
			$('#menuNo1').focus();
			return;
		}
		
		if(level == 0){
			menuNo = $('#menuNo1').val();
		}else{
			if(menuNo2 == ''){
				menuNo = $('#menuNo1').val();
//				alert("메뉴 카테고리를 선택하세요");
//				$('#menuNo2').focus();
//				return;
			}else{
				menuNo = $('#menuNo2').val();
			}
		}
		
		var param = { url : "/system/menu-log-history/read-list"
					, onePageRow : $('#pageCount').val()
					, subparam : "adminNo="+adminNo+"&fromDate="+fromDate+"&toDate="+toDate+"&menuNo="+menuNo
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
//			alert('urlLogSheet_OnClick >>>' + urlLogSheet.ColSaveName(Col));
			var colSaveName	= urlLogSheet.ColSaveName(Col);	// 컬럼의 saveName
			var cellValue 	= urlLogSheet.GetCellValue(Row, colSaveName);	// 셀의 값[0,1,2...접속한 횟수]
			var menuNo 		= urlLogSheet.GetCellValue(Row, "menuNo");	// menuNo
			var cntIndex 	= arrLogCell.indexOf(colSaveName);	// 링크가 연결된 '횟수' 항목의 index[0~24]
			var accessType	= colSaveName.substring(0, 3).toUpperCase();
//			console.log("colSaveName >>>" + colSaveName + "\ncellValue >>>" + cellValue + "\nindexOf >>>" + arrLogCell.indexOf(colSaveName) + "\naccessType >>>" + accessType);
			
			if (cellValue > 0 && cntIndex >= 0) {	// 클릭한 셀의 값이 0보다 크고 배열[arrLogCell]에 정한 항목명[횟수 항목] 경우만 상세 팝업창으로 연결
				var url = "/system/menu-log-history-detail";
				var params = {}
				params.menuNo 		= menuNo;
				params.adminNo 		= $('#adminNo').val();
				params.accessType 	= accessType;
				params.menuNo1 		= $('#menuNo1').val();
				params.menuNo2 		= $('#menuNo2').val();
				params.fromDate 	= $('#fromDate').val();
				params.toDate 		= $('#toDate').val();
				
				abc.open.popup({
					winname :	"menuLogHistoryDetail",
					url 	:	url,
					width 	:	1480,
					height	:	700,
					method	: 	'post',
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
	$("a[name^=per1Month]").trigger('click');
});
