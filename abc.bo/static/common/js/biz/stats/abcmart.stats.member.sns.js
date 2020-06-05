/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _statsMemberSns = abc.object.createNestedObject(window,"abc.biz.stats.member.sns");

	/**
	 * ibsheet 초기화
	 */
	_statsMemberSns.sheetInit = function(){
		var pageCount = $('#pageCount').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("todaySnsGrid"), "memberSnsSheet", "100%", "250px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"기간별 통계|일별 통계", 	Type:"Text",	SaveName:"dateInfo",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"전체|전체", 			Type:"Int",		SaveName:"totalMemberCount",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"네이버|전체", 			Type:"Int",		SaveName:"naverTotalCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"네이버|남", 			Type:"Int",		SaveName:"naverManCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"네이버|여", 			Type:"Int",		SaveName:"naverWomanCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"네이버|알수없음", 		Type:"Int",		SaveName:"naverEtcCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"페이스북|전체", 			Type:"Int",		SaveName:"facebookTotalCount",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"페이스북|남", 			Type:"Int",		SaveName:"facebookManCount",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"페이스북|여", 			Type:"Int",		SaveName:"facebookWomanCount",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"페이스북|알수없음", 		Type:"Int",		SaveName:"facebookEtcCount",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"카카오톡|전체", 			Type:"Int",		SaveName:"kkoTotalCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"카카오톡|남", 			Type:"Int",		SaveName:"kkoManCount",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"카카오톡|여", 			Type:"Int",		SaveName:"kkoWomanCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"카카오톡|알수없음", 		Type:"Int",		SaveName:"kkoEtcCount",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(memberSnsSheet, initSheet);
		// Grid 건수 정보 표시
		memberSnsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		memberSnsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberSnsSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberSnsSheet.SetExtendLastCol(1);
		memberSnsSheet.SetFrozenCol(2);

	}

	_statsMemberSns.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel').on("click", function(){
			snsForm.action = "/stats/member/sns-connect-list-excel";
			snsForm.submit();
		});

		/**
		 * 검색
		 */
		$('#snsFormSearch').on("click", function(){
			_statsMemberSns.getList();
		});

		/**
		 * 초기화
		 */
		$('#snsFormReset').on("click", function(){
			$.form("#snsForm").reset();
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
		
		$('#pageCount').on('change', function(event) {
			memberSnsSheet.SetPageCount(this.value);	// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			_statsMemberSns.getList();
		});
	}

	/**
	 * 그리드 데이터 조회
	 */
	_statsMemberSns.getList = function(){
		var periodType 	= $('#periodType').val();
		var fromDate 	= $('#fromDate').val();
		var toDate 		= $('#toDate').val();
		var dateDiff 	= abc.date.dateDiffAbs(fromDate, toDate);
		
		if(periodType == "day" && dateDiff > 366){
			alert("조회기간은 최대 1년까지 선택 가능합니다.");
			return;
		}else{
			var param = { url : "/stats/member/sns-connect-list"
						, onePageRow : $('#pageCount').val()
						, subparam : FormQueryStringEnc(document.snsForm)
						, sheet : "memberSnsSheet"
						};
			
			DataSearchPaging(param);
		}
	}
	
	/**
	 * 회원 타입별 검색 후 첫번째 '합계' 행을 고정
	 */
	memberSnsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= memberSnsSheet.GetFrozenRows();
		var currentPage = memberSnsSheet.GetCurrentPage();
		var periodType  = $('#periodType').val();
		
		// 검색 기간 타입에 따른 헤더 텍스트 변경
		if(periodType == 'day'){
			memberSnsSheet.SetCellText(1, 0, '일별 통계');
		}else if(periodType == 'month'){
			memberSnsSheet.SetCellText(1, 0, '월별 통계');
		}else if(periodType == 'year'){
			memberSnsSheet.SetCellText(1, 0, '년별 통계');
		}

		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=memberSnsSheet.SearchRows(); i++){
				var saveName = memberSnsSheet.GetCellValue(i, "dateInfo");
				if(saveName == "합계"){
					memberSnsSheet.RowDelete(i, 0);		
				}
			}
		}
		memberSnsSheet.SetRowBackColor(2, '#e2f5ff');
//		memberSnsSheet.SetCellFont('FontBold', 3,2,4,5,1);
		memberSnsSheet.SetFrozenRows(1);
		
		if(memberSnsSheet.RowCount() > 1){
			$('#excel').removeClass("disabled");
		}else{
			$('#excel').addClass("disabled");
		}
	}

	/**
	 * 즉시 실행 함수
	 */
	$(function(){
		_statsMemberSns.sheetInit();
		_statsMemberSns.event();
	});
})();

$(document).ready(function(){
	$('#periodDateWeek').get(0).click();
});
