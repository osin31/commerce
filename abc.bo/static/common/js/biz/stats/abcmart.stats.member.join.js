/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _statsMemberJoin = abc.object.createNestedObject(window,"abc.biz.stats.member.join");

	/**
	 * ibsheet 초기화
	 */
	_statsMemberJoin.sheetInit1 = function(){
		var pageCount1 = $('#pageCount1').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("todayJoinGrid"), "memberJoinSheet", "100%", "250px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet1 = {};

		initSheet1.Cfg = {SearchMode:smServerPaging2, Page:pageCount1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet1.HeaderMode = {};
		initSheet1.Cols = [
						  {Header:"기간별 통계|일별 통계", 		Type:"Text",	SaveName:"dateInfo",			MinWidth:130,	Align:"Center", 	Edit:0}
						, {Header:"전체|전체", 				Type:"Int",		SaveName:"totalCnt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|전체", 			Type:"Int",		SaveName:"statusField4Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|남", 			Type:"Int",		SaveName:"statusField1Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|여", 			Type:"Int",		SaveName:"statusField2Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|알수없음", 		Type:"Int",		SaveName:"statusField3Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|전체", 			Type:"Int",		SaveName:"statusField7Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|남", 			Type:"Int",		SaveName:"statusField5Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|여", 			Type:"Int",		SaveName:"statusField6Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|전체", 			Type:"Int",		SaveName:"statusField10Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|남", 			Type:"Int",		SaveName:"statusField8Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|여", 			Type:"Int",		SaveName:"statusField9Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(memberJoinSheet, initSheet1);
		// Grid 건수 정보 표시
		memberJoinSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		memberJoinSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberJoinSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberJoinSheet.SetExtendLastCol(1);

	}

	/**
	 * ibsheet 초기화
	 */
	_statsMemberJoin.sheetInit2 = function(){
		var pageCount2 = $('#pageCount2').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("todayAgeJoinGrid"), "memberAgeJoinSheet", "100%", "250px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet2 = {};
		
		initSheet2.Cfg = {SearchMode:smServerPaging2, Page:pageCount2, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet2.HeaderMode = {};
		initSheet2.Cols = [
						  {Header:"기간별 통계|일별 통계", 	Type:"Text",	SaveName:"dateInfo",			MinWidth:130,	Align:"Center", 	Edit:0}
						, {Header:"전체|남", 				Type:"Int",		SaveName:"totMale",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"전체|여", 				Type:"Int",		SaveName:"totFemale",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"10대|남", 				Type:"Int",		SaveName:"statusField1Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"10대|여", 				Type:"Int",		SaveName:"statusField2Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"20대|남", 				Type:"Int",		SaveName:"statusField3Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"20대|여", 				Type:"Int",		SaveName:"statusField4Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"30대|남", 				Type:"Int",		SaveName:"statusField5Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"30대|여", 				Type:"Int",		SaveName:"statusField6Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"40대|남", 				Type:"Int",		SaveName:"statusField7Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"40대|여", 				Type:"Int",		SaveName:"statusField8Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"50대|남", 				Type:"Int",		SaveName:"statusField9Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"50대|여", 				Type:"Int",		SaveName:"statusField10Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"60대|남", 				Type:"Int",		SaveName:"statusField11Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"60대|여", 				Type:"Int",		SaveName:"statusField12Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"기타|남", 				Type:"Int",		SaveName:"statusField13Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"기타|여", 				Type:"Int",		SaveName:"statusField14Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"탈퇴|남", 				Type:"Int",		SaveName:"statusField15Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"탈퇴|여", 				Type:"Int",		SaveName:"statusField16Count",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
			];
		// Grid 초기화
		IBS_InitSheet(memberAgeJoinSheet, initSheet2);
		// Grid 건수 정보 표시
		memberAgeJoinSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		memberAgeJoinSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberAgeJoinSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberAgeJoinSheet.SetExtendLastCol(1);
		memberAgeJoinSheet.SetFrozenCol(1);
		
	}

	_statsMemberJoin.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel1, #excel2').on("click", function(){
			var startHour = $('#startHour').val(); 
			var endHour   = $('#endHour').val();
			
			if(!((startHour == '' && endHour == '')
			 ||(startHour != '' && endHour != ''))){
				alert("시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요");
				return;
			}else{
				if(new Number(startHour) > new Number(endHour)){
					alert("시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요");
					return;
				}

				gForm.action = "/stats/member/member-join-list-excel";
				gForm.submit();
			}
			
		});
		
		$('#memberType1, #memberType2').on("click", function(){
			if(this.id == 'memberType1'){
				$('#memberType').val('1');
			}else{
				$('#memberType').val('2');
			}
		});

		/**
		 * 검색
		 */
		$('#gFormSearch').on("click", function(){
			_statsMemberJoin.getList();
		});

		/**
		 * 초기화
		 */
		$('#gFormReset').on("click", function(){
			$.form("#gForm").reset();
			$('#startDayArea').show();
			$('#dashArea').show();
			$('#endDayArea').show();
			$('#periodButtonArea').show();
			$('#periodMonthStart').hide();
			$('#periodDashMonth').hide();
			$('#periodMonthEnd').hide();
			$('#periodYearStart').hide();
			$('#periodDashYear').hide();
			$('#periodYearEnd').hide();
			$('#periodDateWeek').get(0).click();
		});

		/**
		 * 전체 체크하기 컨트롤
		 */
		$('#chkdeviceTypeAll').on("click", function(){
			var checkFlag 	= $(this).is(":checked");
			
			$("[id^=chkdeviceType0]").each(function(){
				$(this).prop('checked', checkFlag);
			});
		});

		/**
		 * 디바이스 체크박스 컨트롤
		 */
		$('[id^=chkdeviceType0]').on("click", function(){
			var checkFlag 	= true;

			$("[id^=chkdeviceType0]").each(function(){
				checkFlag = $(this).is(":checked");
				if(!checkFlag){
					return false;
				}
			});
			$('#chkdeviceTypeAll').prop('checked', checkFlag);
		});
		
		

		/**
		 * 일별 기간 버튼 선택 시 컨트롤
		 */
		$('[id^=periodDate]').on("click", function(){
			var val = this.id;
			
			if(val == 'periodDateYesterday'){
				abc.date.yesterday(this);
			}else if(val == 'periodDateWeek'){
				abc.date.statWeek(this);
			}else if(val == 'periodDateMonth'){
				abc.date.statMonth(this);
			}
		});

		/**
		 * 기간 타입 select box 변경 시
		 */
		$("#periodType").on("change", function() {
			var val = this.value;

			if(val == 'day'){
				$('#startDayArea').show();
				$('#dashArea').show();
				$('#endDayArea').show();
				$('#periodButtonArea').show();
				$('#periodMonthStart').hide();
				$('#periodDashMonth').hide();
				$('#periodMonthEnd').hide();
				$('#periodYearStart').hide();
				$('#periodDashYear').hide();
				$('#periodYearEnd').hide();
			}else if(val == 'month'){
				$('#startDayArea').hide();
				$('#dashArea').hide();
				$('#endDayArea').hide();
				$('#periodButtonArea').hide();
				$('#periodMonthStart').show();
				$('#periodDashMonth').show();
				$('#periodMonthEnd').show();
				$('#periodYearStart').hide();
				$('#periodDashYear').hide();
				$('#periodYearEnd').hide();
			}else if(val == 'year'){
				$('#startDayArea').hide();
				$('#dashArea').hide();
				$('#endDayArea').hide();
				$('#periodButtonArea').hide();
				$('#periodMonthStart').hide();
				$('#periodDashMonth').hide();
				$('#periodMonthEnd').hide();
				$('#periodYearStart').show();
				$('#periodDashYear').show();
				$('#periodYearEnd').show();
			}
		});
		
		$('#pageCount1, #pageCount2').on('change', function(event) {
			var memberType 	= $('#memberType').val();
			
			if(memberType == '1'){
				memberJoinSheet.SetPageCount(this.value);	// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}else{
				memberAgeJoinSheet.SetPageCount(this.value);	// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}
			
			_statsMemberJoin.getList();
		});
	}

	/**
	 * 그리드 데이터 조회
	 */
	_statsMemberJoin.getList = function(){
		var memberType 	= $('#memberType').val();
		var sheet      	= "memberJoinSheet"; 
		var periodType 	= $('#periodType').val();
		var fromDate 	= $('#fromDate').val();
		var toDate 		= $('#toDate').val();
		var dateDiff 	= abc.date.dateDiffAbs(fromDate, toDate);
		
		if(memberType == '1'){
			sheet = "memberJoinSheet";
		}else{
			sheet = "memberAgeJoinSheet";
		}
		
		
		if(periodType == "day" && dateDiff > 366){
			alert("조회기간은 최대 1년까지 선택 가능합니다.");
			return;
		}else{
			var param = { url : "/stats/member/member-join-list"
						, onePageRow : $('#pageCount'+memberType).val()
						, subparam : FormQueryStringEnc(document.gForm)
						, sheet : sheet
						};
			
			DataSearchPaging(param);
		}
	}
	
	/**
	 * 회원 타입별 검색 후 첫번째 '합계' 행을 고정
	 */
	memberJoinSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= memberJoinSheet.GetFrozenRows();
		var currentPage = memberJoinSheet.GetCurrentPage();
		var periodType  = $('#periodType').val();
		
		// 검색 기간 타입에 따른 헤더 텍스트 변경
		if(periodType == 'day'){
				memberJoinSheet.SetCellText(1, 0, '일별 통계');
		}else if(periodType == 'month'){
				memberJoinSheet.SetCellText(1, 0, '월별 통계');
		}else if(periodType == 'year'){
				memberJoinSheet.SetCellText(1, 0, '년별 통계');
		}

		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=memberJoinSheet.SearchRows(); i++){
				var saveName = memberJoinSheet.GetCellValue(i, "dateInfo");
				if(saveName == "합계"){
					memberJoinSheet.RowDelete(i, 0);		
				}
			}
		}
		if(memberJoinSheet.RowCount() > 1){
			$('#excel1').removeClass("disabled");
		}else{
			$('#excel1').addClass("disabled");
		}
		
		memberJoinSheet.SetRowBackColor(2, '#e2f5ff');
//		memberJoinSheet.SetCellFont('FontBold', 3,2,4,5,1);
		memberJoinSheet.SetFrozenRows(1);
	}

	/**
	 * 연령별 검색 후 첫번째 '합계' 행을 고정
	 */
	memberAgeJoinSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= memberAgeJoinSheet.GetFrozenRows();
		var currentPage = memberAgeJoinSheet.GetCurrentPage();
		var periodType  = $('#periodType').val();
		//console.log("memberAgeJoinSheet  SearchRows >>>" + memberAgeJoinSheet.SearchRows() + "\ncurrentPage" + memberAgeJoinSheet.GetCurrentPage() + "\nperiodType >>>" + periodType);
		
		// 검색 기간 타입에 따른 헤더 텍스트 변경
		if(periodType == 'day'){
			memberAgeJoinSheet.SetCellText(1, 0, '일별 통계');
		}else if(periodType == 'month'){
			memberAgeJoinSheet.SetCellText(1, 0, '월별 통계');
		}else if(periodType == 'year'){
			memberAgeJoinSheet.SetCellText(1, 0, '년별 통계');
		}

		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=memberAgeJoinSheet.SearchRows(); i++){
				var saveName = memberAgeJoinSheet.GetCellValue(i, "dateInfo");
				if(saveName == "합계"){
					memberAgeJoinSheet.RowDelete(i, 0);		
				}
			}
		}
		
		if(memberAgeJoinSheet.RowCount() > 1){
			$('#excel2').removeClass("disabled");
		}else{
			$('#excel2').addClass("disabled");
		}
		memberAgeJoinSheet.SetRowBackColor(2, '#e2f5ff');
		memberAgeJoinSheet.SetFrozenRows(1);
	}

	/**
	 * 즉시 실행 함수
	 */
	$(function(){
		_statsMemberJoin.sheetInit1();
		_statsMemberJoin.sheetInit2();
		_statsMemberJoin.event();
	});
})();

$(document).ready(function(){
	$('#periodDateWeek').get(0).click();
});
