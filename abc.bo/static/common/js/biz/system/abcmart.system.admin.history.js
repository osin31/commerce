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

		initSheet.Cfg = {SearchMode:smLazyLoad, Page:pageCount, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [];
		
		var selectValue = $('#adminNo option:selected').val();
		var adminCount = $('#adminNo option').length;
		
		
		if(selectValue != ''){	// 전체가 아닐 때	
			var loginId = $('#adminNo option:selected').text();
			var adminNo = $('#adminNo option:selected').val();
//			console.log("adminNo >>>" + adminNo + "\nloginId >>>" + loginId);
			initSheet.Cols.push( {Header:"URI|URI", 		Type:"Text",	SaveName:"accessUrl",			MinWidth:200,	Align:"Left", 	Edit:0, Sort:0} );
			initSheet.Cols.push( {Header:loginId+"|횟수", 	Type:"Int",		SaveName:adminNo+"Cnt",			MinWidth:100,	Align:"Center", 	Edit:0, Sort:0, Format:"Integer"} );
			initSheet.Cols.push( {Header:loginId+"|건수", 	Type:"Int",		SaveName:adminNo+"ResultCnt",	MinWidth:100,	Align:"Center", 	Edit:0, Sort:0, Format:"Integer"} );
		}else{	// 전체일 때
			initSheet.Cols.push( {Header:"URI|URI", 		Type:"Text",	SaveName:"accessUrl",				MinWidth:300,	Align:"Left", 	Edit:0, Sort:0} );
			for(i=1; i<adminCount; i++){
				var loginId = $('#adminNo option')[i].text;
				var adminNo = $('#adminNo option')[i].value;
//				console.log("adminNo >>>" + adminNo + "\nloginId >>>" + loginId);
				initSheet.Cols.push( {Header:loginId+"|횟수", 	Type:"Int",		SaveName:adminNo+"Cnt",			MinWidth:50,	Align:"Center", 	Edit:0, Sort:0, Format:"Integer"} );
				initSheet.Cols.push( {Header:loginId+"|건수", 	Type:"Int",		SaveName:adminNo+"ResultCnt",	MinWidth:50,	Align:"Center", 	Edit:0, Sort:0, Format:"Integer"} );
			}
		}
		
		// Grid 초기화
		IBS_InitSheet(urlLogSheet, initSheet);
		// Grid 건수 정보 표시
		//urlLogSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//urlLogSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		urlLogSheet.FitColWidth();
		urlLogSheet.SetFrozenCol(1);
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		urlLogSheet.SetExtendLastCol(1);

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
		
		$('#adminNo').on('change', function(event) {
			urlLogSheet.Reset(); //컬럼 초기화
			_urlLog.sheetInit();
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
		
		$('[name=urlLogForm]').find('[name=arrAdminNo]').remove();
		
		if(adminNo == ''){
			$('#adminNo > option').each(function() {
//				console.log($(this).val());
				$('#urlLogForm').append('<input type="hidden" name="arrAdminNo" id="arrAdminNo" value="'+$(this).val()+'" />');
			});
		}else{
			$('#urlLogForm').append('<input type="hidden" name="arrAdminNo" id="arrAdminNo" value="'+adminNo+'" />');			
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
		
		var param = { url : "/system/admin-log-history/read-list"
					//, onePageRow : $('#pageCount').val()
//					, subparam : "adminNo="+adminNo+"&fromDate="+fromDate+"&toDate="+toDate+"&menuNo="+menuNo
					, subparam : FormQueryStringEnc(document.forms.urlLogForm)
				    , sheet : "urlLogSheet"};
		DataSearch(param);
	}

/*	
	urlLogSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var headerRows = urlLogSheet.HeaderRows() ;
		var Rows = urlLogSheet.RowCount() +urlLogSheet.HeaderRows();
		var selectValue = $('#adminNo option:selected').val();
		
		if(selectValue == ''){
			$('#adminNo > option').each(function(i, v) {
//				console.log("v.value >>>" + v.value);
				if(v.value != ''){	// 전체는 건너뜀
					var acceseType  = v.value+"Cnt";
					var acceseType2 = v.value+"ResultCnt";
					var hiddenFlag  = true;
					
					for(j=headerRows; j<Rows; j++){
						if(urlLogSheet.GetCellValue(j, acceseType) > 0){
							hiddenFlag = false;
							urlLogSheet.SetColHidden(acceseType ,  hiddenFlag);
							urlLogSheet.SetColHidden(acceseType2 , hiddenFlag);
							return true; // next loop[admin option loop]
						}
						
						if(j == (Rows-1) ){	// 마지막로우에서 해당 컬럼의 숨김 여부를 세팅[hiddenFlag]
//							console.log("마지막 행 j >>>" + j + "\t\t value >>>" + urlLogSheet.GetCellValue(j, acceseType));
							urlLogSheet.SetColHidden(acceseType ,  hiddenFlag);
							urlLogSheet.SetColHidden(acceseType2 , hiddenFlag);
						}
					}
				}
			});
		
		}
		
	}
*/	

	urlLogSheet_OnRowSearchEnd = function(row){
		var adminCount = $('#adminNo option').length;
		var headerObj = $('#adminNo option');
		
		//접속 로그가 있는 컬럼의 underline으로 링크가 있음을 표시
		$('#adminNo > option').each(function(i, v) {
			var acceseType = v.value+"Cnt";

//			console.log("acceseType >>>" + acceseType);
			if(urlLogSheet.GetCellValue(row, acceseType) > 0){
				urlLogSheet.SetCellFontUnderline(row, acceseType, 1);
			}else{
//				urlLogSheet.SetColHidden(acceseType , true);
			}
		});
	}
	
	
	urlLogSheet_OnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row > 1) {	// 헤더가 병합으로 인해 0~1까지 이므로 1보다 큰것으로 구분한다.
			var colSaveName	= urlLogSheet.ColSaveName(Col);	// 컬럼의 saveName
			var cellValue 	= urlLogSheet.GetCellValue(Row, colSaveName);	// 셀의 값[0,1,2...접속한 횟수]
			var menuNo 		= urlLogSheet.GetCellValue(Row, "menuNo");	// menuNo
			var cntIndex 	= arrLogCell.indexOf(colSaveName);	// 링크가 연결된 '횟수' 항목의 index[0~24]
			var accessType	= colSaveName.substring(0, 3).toUpperCase();
			console.log("colSaveName >>>" + colSaveName + "\ncellValue >>>" + cellValue + "\nindexOf >>>" + arrLogCell.indexOf(colSaveName) + "\naccessType >>>" + accessType);
			
			if (cellValue > 0) {	// 클릭한 셀의 값이 0보다 크면 상세 팝업창으로 연결
				var url = "/system/admin-log-history-detail";
				var params = {}
				params.adminNo 		= colSaveName.substr(0, 10);
				params.accessUrl 		= urlLogSheet.GetCellValue(Row, "accessUrl");
				params.searchType 	= "STATS";
				params.fromDate 	= $('#fromDate').val();
				params.toDate 		= $('#toDate').val();
				
				abc.open.popup({
					winname :	"adminLogHistoryDetail",
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
