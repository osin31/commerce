/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _loginMember = abc.object.createNestedObject(window,"abc.biz.stats.member.login");

	/**
	 * ibsheet 초기화
	 */
	_loginMember.sheetInit = function(){
		// Grid 객체 생성
		createIBSheet2(document.getElementById("loginGrid"), "memberLoginSheet", "100%", "385px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"기간별 통계|일별 통계", 				Type:"Html",	SaveName:"dateInfo",				MinWidth:130,	Align:"Center", 	Edit:0}
						, {Header:"전체|남", 							Type:"Int",		SaveName:"manMemberCount",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"전체|여", 							Type:"Int",		SaveName:"womanMemberCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"전체|알수없음", 						Type:"Int",		SaveName:"etcMemberCount",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|0~1시", 			Type:"Int",		SaveName:"statusField0Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|1~2시", 			Type:"Int",		SaveName:"statusField1Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|2~3시", 			Type:"Int",		SaveName:"statusField2Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|3~4시", 			Type:"Int",		SaveName:"statusField3Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|4~5시", 			Type:"Int",		SaveName:"statusField4Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|5~6시", 			Type:"Int",		SaveName:"statusField5Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|6~7시", 			Type:"Int",		SaveName:"statusField6Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|7~8시", 			Type:"Int",		SaveName:"statusField7Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|8~9시", 			Type:"Int",		SaveName:"statusField8Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|9~10시", 			Type:"Int",		SaveName:"statusField9Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|10~11시", 			Type:"Int",		SaveName:"statusField10Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|11~12시", 			Type:"Int",		SaveName:"statusField11Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|12~13시", 			Type:"Int",		SaveName:"statusField12Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|13~14시", 			Type:"Int",		SaveName:"statusField13Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|14~15시", 			Type:"Int",		SaveName:"statusField14Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|15~16시", 			Type:"Int",		SaveName:"statusField15Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|16~17시", 			Type:"Int",		SaveName:"statusField16Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|17~18시", 			Type:"Int",		SaveName:"statusField17Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|18~19시", 			Type:"Int",		SaveName:"statusField18Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|19~20시", 			Type:"Int",		SaveName:"statusField19Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|20~21시", 			Type:"Int",		SaveName:"statusField20Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|21~22시", 			Type:"Int",		SaveName:"statusField21Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|22~23시", 			Type:"Int",		SaveName:"statusField22Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"시간대별 로그인 현황|23~24시", 			Type:"Int",		SaveName:"statusField23Count",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(memberLoginSheet, initSheet);
		// Grid 건수 정보 표시
		//memberLoginSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//memberLoginSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberLoginSheet.FitColWidth();
		memberLoginSheet.SetFrozenCol(4);
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
//		memberLoginSheet.SetExtendLastCol(1);

	}

	_loginMember.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel').on("click", function(){
			loginForm.action = "/stats/member/login-list-excel";
			loginForm.submit();
		});

		/**
		 * 검색
		 */
		$('#loginFormSearch').on("click", function(){
			_loginMember.getList();
		});

		/**
		 * 초기화
		 */
		$('#loginFormReset').on("click", function(){
			$.form("#loginForm").reset();
			
			$('#chkSnsTypeAll').attr("disabled", true);
			$("[id^=chkSnsType0]").each(function(){
				$(this).attr("disabled", true);
			});
			
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

		/**
		 * 디바이스 전체 체크하기 컨트롤
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
		 * SNS구분 체크하기 컨트롤
		 */
		$('#chkSnsTypeAll').on("click", function(){
			var checkFlag 	= $(this).is(":checked");
			
			$("[id^=chkSnsType0]").each(function(){
				$(this).prop('checked', checkFlag);
			});
		});

		/**
		 * SNS구분 체크박스 컨트롤
		 */
		$('[id^=chkSnsType0]').on("click", function(){
			var checkFlag 	= true;

			$("[id^=chkSnsType0]").each(function(){
				checkFlag = $(this).is(":checked");
				if(!checkFlag){
					return false;
				}
			});

			$('#chkSnsTypeAll').prop('checked', checkFlag);
		});
		
		/**
		 * SNS 계정 로그인 여부 클릭 시
		 * SNS 구분 체크박스 컨트롤
		 */
		$('#snsLoginYn').on("click", function(){
			var checkFlag = $(this).is(":checked");
			
			$('#chkSnsTypeAll').prop('checked', checkFlag);
			$('#chkSnsTypeAll').attr("disabled", !checkFlag);
			$("[id^=chkSnsType0]").each(function(){
				$(this).prop("checked", checkFlag);
				$(this).attr("disabled", !checkFlag);
			});
		});
		
	}

	/**
	 * 그리드 데이터 조회
	 */
	_loginMember.getList = function(){
		var isSnsLoginYn = $('#snsLoginYn').is(":checked");
		var isSns 		 = false;
		var periodType   = $('#periodType').val();
		var fromDate 	 = $('#fromDate').val();
		var toDate 		 = $('#toDate').val();
		var dateDiff 	 = abc.date.dateDiffAbs(fromDate, toDate);
		
		if(periodType == "day" && dateDiff > 366){
			alert("조회기간은 최대 1년까지 선택 가능합니다.");
			return;
		}else{
			if(isSnsLoginYn){	// SNS 계정 로그인 여부를 체크한 경우 'SNS 구분' 항목 체크여부를 검사
				$("[id^=chkSnsType0]").each(function(){
					isSns = $(this).is(":checked");
					
					if(isSns){	// 'SNS 구분' 항목을 하나라도 체크했다면 loop를 멈춤
						return false;
					}
				});
				
				if(!isSns){	// 'SNS 구분' 항목 체크를 한개도 안했다면 alert
					alert("SNS 구분을 선택하세요");
					return;
				}
			}
			
			memberLoginSheet.RemoveAll();	// 합계항목이 두 개가 나오는 경우가 있어서 검색 전에 모든 그리드 데이터를 삭제하고 조회{데이터가 나오는 검색 조건으로 검색 후, 데이터가 없는 검색조건으로 조회하면 합계가 두2개[기존의 합계항목+합계가 0인 항목] 생김}
	
			var param = { url : "/stats/member/login-list"
						, subparam : FormQueryStringEnc(document.forms.loginForm)
					    , sheet : "memberLoginSheet"};
			DataSearch(param);
		}
	}
	
	memberLoginSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var periodType  = $('#periodType').val();
		
		// 검색 기간 타입에 따른 헤더 텍스트 변경
		if(periodType == 'day'){
				memberLoginSheet.SetCellText(1, 0, '일별 통계');
		}else if(periodType == 'month'){
				memberLoginSheet.SetCellText(1, 0, '월별 통계');
		}else if(periodType == 'year'){
				memberLoginSheet.SetCellText(1, 0, '년별 통계');
		}

		memberLoginSheet.SetRowBackColor(2, '#e2f5ff');
		memberLoginSheet.SetFrozenRows(1);
		
		if(memberLoginSheet.RowCount() > 1){
			$('#excel').removeClass("disabled");
		}else{
			$('#excel').addClass("disabled");
		}
	}
	
	$(function(){
		_loginMember.sheetInit();
		_loginMember.event();
	});
})();

$(document).ready(function(){
	$('#periodDateWeek').get(0).click();
});