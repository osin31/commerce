/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _statsMemberJoin = abc.object.createNestedObject(window,"abc.biz.stats.today.member.join");

	/**
	 * ibsheet 초기화
	 */
	_statsMemberJoin.sheetInit = function(){
		var pageCount = $('#pageCount').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("todayJoinGrid"), "memberJoinSheet", "100%", "429px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, Page:pageCount, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"시간대별 통계|시간대별 통계", 	Type:"Text",	SaveName:"title",			Align:"Center", 	Edit:0}
						, {Header:"전체|전체", 				Type:"Int",		SaveName:"total",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|전체", 			Type:"Int",		SaveName:"totot",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|남", 			Type:"Int",		SaveName:"tomen",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|여", 			Type:"Int",		SaveName:"tofemale",		Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"온라인 회원|알수없음", 		Type:"Int",		SaveName:"toetc",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|전체", 			Type:"Int",		SaveName:"tmtot",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|남", 			Type:"Int",		SaveName:"tmmen",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 회원|여", 			Type:"Int",		SaveName:"tmfemale",		Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|전체", 			Type:"Int",		SaveName:"tmctot",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|남", 			Type:"Int",		SaveName:"tmcmen",			Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						, {Header:"멤버십 전환수|여", 			Type:"Int",		SaveName:"tmcfemale",		Align:"Center", 	Edit:0, Format:"NullInteger", Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(memberJoinSheet, initSheet);
		// Grid 건수 정보 표시
		//memberJoinSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//memberJoinSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		memberJoinSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		memberJoinSheet.SetExtendLastCol(1);

	}

	_statsMemberJoin.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel').on("click", function(){
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

				todayJoinForm.action = "/stats/member/today-join-list-excel";
				todayJoinForm.submit();
			}
			
		});

		/**
		 * 검색
		 */
		$('#todayJoinFormSearch').on("click", function(){
			_statsMemberJoin.getList();
		});

		/**
		 * 초기화
		 */
		$('#todayJoinFormReset').on("click", function(){
			$.form("#todayJoinForm").reset();
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
		
		
	}

	/**
	 * 그리드 데이터 조회
	 */
	_statsMemberJoin.getList = function(){
		var startHour = $('#startHour').val(); 
		var endHour   = $('#endHour').val();
//		console.log("startHour >>>" + startHour + "\tendHour >>>" + endHour);
		
		if(!((startHour == '' && endHour == '')
		 ||(startHour != '' && endHour != ''))){
			alert("시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요");
			return;
		}else{
			if(new Number(startHour) > new Number(endHour)){
				alert("시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요");
				return;
			}
			
			memberJoinSheet.RemoveAll();	// 합계항목이 두 개가 나오는 경우가 있어서 검색 전에 모든 그리드 데이터를 삭제하고 조회{데이터가 나오는 검색 조건으로 검색 후, 데이터가 없는 검색조건으로 조회하면 합계가 두2개[기존의 합계항목+합계가 0인 항목] 생김}
			var param = { url : "/stats/member/today-join-list"
						, subparam : FormQueryStringEnc(document.forms.todayJoinForm)
					    , sheet : "memberJoinSheet"};
			DataSearch(param);
		}
	}
	
	memberJoinSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		if(memberJoinSheet.RowCount() > 1){
			$('#excel').removeClass("disabled");
		}else{
			$('#excel').addClass("disabled");			
		}
		memberJoinSheet.SetRowBackColor(2, '#e2f5ff');
		memberJoinSheet.SetFrozenRows(1);
	}
	
	
	$(function(){
		_statsMemberJoin.sheetInit();
		_statsMemberJoin.event();
	});
})();