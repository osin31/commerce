/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _statsMemberTodayLogin = abc.object.createNestedObject(window,"abc.biz.stats.member.today.login");

	/**
	 * ibsheet 초기화
	 */
	_statsMemberTodayLogin.sheetInit = function(){
		// Grid 객체 생성
		createIBSheet2(document.getElementById("MemberTodayLogingGrid"), "memberLoginSheet", "100%", "429px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"시간대별 통계|시간대별 통계", 	Type:"Text",	SaveName:"dtmTitle",		Align:"Center", 	Edit:0}
						, {Header:"전체|전체", 				Type:"Int",		SaveName:"total",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|전체", 			Type:"Int",		SaveName:"totot",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|남", 			Type:"Int",		SaveName:"tomen",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|여", 			Type:"Int",		SaveName:"tofemale",		Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|알수없음", 		Type:"Int",		SaveName:"toetc",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|전체", 			Type:"Int",		SaveName:"tmtot",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|남", 			Type:"Int",		SaveName:"tmmen",		Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|여", 			Type:"Int",		SaveName:"tmfemale",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(memberLoginSheet, initSheet);
		// Grid 건수 정보 표시
		//memberLoginSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//memberLoginSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberLoginSheet.FitColWidth("12|11|11|11|11|11|11|11|11");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberLoginSheet.SetExtendLastCol(1);

	}

	_statsMemberTodayLogin.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel').on("click", function(){
			todayLoginForm.action = "/stats/member/today-login-list-excel";
			todayLoginForm.submit();
			
		});

		/**
		 * 검색
		 */
		$('#todayLoginFormSearch').on("click", function(){
			_statsMemberTodayLogin.getList();
		});

		/**
		 * 초기화
		 */
		$('#todayLoginFormReset').on("click", function(){
			$.form("#todayLoginForm").reset();
			
			$('#chkSnsTypeAll').attr("disabled", true);
			$("[id^=chkSnsType0]").each(function(){
				$(this).attr("disabled", true);
			});
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
	_statsMemberTodayLogin.getList = function(){
		var isSnsLoginYn = $('#snsLoginYn').is(":checked");
		var isSns = false;
		
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
		
		var param = { url : "/stats/member/today-login-list"
					, subparam : FormQueryStringEnc(document.forms.todayLoginForm)
				    , sheet : "memberLoginSheet"};
		DataSearch(param);
	}

	memberLoginSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		if(memberLoginSheet.RowCount() > 1){
			$('#excel').removeClass("disabled");
		}else{
			$('#excel').addClass("disabled");
		}
		memberLoginSheet.SetRowBackColor(2, '#e2f5ff');
		memberLoginSheet.SetFrozenRows(1);
	}
	
	$(function(){
		_statsMemberTodayLogin.sheetInit();
		_statsMemberTodayLogin.event();
	});
})();