/***
 * 사이트 관리.
 * 
 */
(function() {

	var _point = abc.object.createNestedObject(window,"abc.biz.stats.member.point");
	
	/*************************************************************************
	 *					회원 탈퇴사유 통계
	 *************************************************************************/
	
	_point.initPointSheet = function() {
		// Grid 객체 생성
		createIBSheet2(document.getElementById("pointGrid"), "pointSheet");
		var pageCount = $('#pageCount').val();
		var initPointSheet = {};
		initPointSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount,MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initPointSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initPointSheet.Cols = [
			  {Header : "기간별통계|일별통계",				Type : "Text",	SaveName : "totalDayC",	MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 1}
			, {Header : "전체|적립",					Type : "Int",	SaveName : "totalPlus",	MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "전체|차감",					Type : "Int",	SaveName : "totalMinus",MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "상품구매적립|상품구매적립",			Type : "Int",	SaveName : "type0", 	MinWidth : 25,	Align : "Center", 	Edit : 0,	Sort : 0}
			, {Header : "첫구매적립|첫구매적립",			Type : "Int",	SaveName : "type1",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "기념일구매적립|기념일구매적립",		Type : "Int",	SaveName : "type2",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "회원가입적립|회원가입적립",			Type : "Int",	SaveName : "type3",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "상품후기적립|상품후기적립",			Type : "Int",	SaveName : "type4",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "이벤트적립|이벤트적립",			Type : "Int",	SaveName : "type5",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "구매사용차감|구매사용차감",			Type : "Int",	SaveName : "type9",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "고객응대|적립",					Type : "Int",	SaveName : "type8",		MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "고객응대|차감",					Type : "Int",	SaveName : "type11",	MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "기타|적립",					Type : "Int",	SaveName : "type12",	MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "기타|차감",					Type : "Int",	SaveName : "type13",	MinWidth : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			];
		// Grid 초기화
		IBS_InitSheet(pointSheet, initPointSheet);
		// Grid 건수 정보 표시
		pointSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시 
		pointSheet.SetPagingPosition(2);
		// Grid width 자동 조절 
		pointSheet.FitColWidth("6|6|6|6|6|6|6|6|6|6|6|6|6|6|6|6|6");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		pointSheet.SetExtendLastCol(2);
		pointSheet.SetFrozenCol(3);
		
	}
	
	//오늘부터 과거만 선택가능
	$("#fromDate").datepicker({
		constrainInput: false ,
		showOn: "both" ,
		maxDate : "-1"
	});
	//오늘부터 과거만 선택가능
	$("#toDate").datepicker({
		constrainInput: false ,
		showOn: "both" ,
		maxDate : "-1"
	});
	
	// 초기화
	$("#resetBtn").click(function(f) {
		$("#pointForm")[0].reset();
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
	
	$("#periodType").on("change", function() {
		var val = this.value;

		if(val == abc.consts.SEARCH_DATE_TYPE_DAY){
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
		}else if(val == abc.consts.SEARCH_DATE_TYPE_MONTH){
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
		}else if(val == abc.consts.SEARCH_DATE_TYPE_YEAR){
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
	
	//검색
	_point.pointSearch = function(){
		var searchType = $("#periodType").val();
		var fromDate = new Date($('#fromDate')[0].value);
		var toDate = new Date($('#toDate')[0].value);
		if(searchType == abc.consts.SEARCH_DATE_TYPE_DAY){
			if (abc.date.dateDiff($("#fromDate").val(), $("#toDate").val()) > 365) {
				alert("조회기간은 최대 1년까지 선택 가능합니다."); 
				$("#fromDate").focus();
				return false;
			}
			
			if(!abc.text.isDate(abc.text.validateStringSignRemove($("#fromDate").val()))){
				alert("시작일자를 확인해주세요");
				$("#fromDate").focus();
				
				return false;
			}
			
			if(!abc.text.isDate(abc.text.validateStringSignRemove($("#toDate").val()))){
				alert("종료일자를 확인해주세요");
				$("#toDate").focus();
				
				return false;
			}
			
			if(fromDate > toDate){
				alert("검색시작일은 종료일보다 미래 일 수 없습니다.\n검색기간을 재설정 해 주십시오.");
				return false;
			}
			
			if(abc.date.dateDiff(toDate, new Date()) <=0){
				alert("전일 기준의 데이터만 조회 가능합니다.\n종료일자를 확인해주세요.");
				$("#toDate").focus();
				
				return false;
			}
		}
		
		
		// 페이지리스트 목록 개수
		var pageCount = $("#pageCount").val();
		var param = { url : "/stats/member/read-point-status"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.pointForm)
				, sheet : "pointSheet" };
		DataSearchPaging(param);
	}
	
	
	// 엑셀 다운로드
	_point.excelDownload = function(){
		if(pointSheet.GetTotalRows() === 0) {
			alert("엑셀 다운로드는 조회 후에 이용 가능합니다..");
				return false;
		}

		$("#pointForm")
		.prop("action","/stats/member/point-download-excel")
		.prop("method", "post")
		.submit();
		
	}
	
	//셀렉트 박스 영역 조정
//	_point.selectBoxSet = function(){
//		var selectValue = $("#totalDayType").val();
//		
//		if(selectValue == abc.consts.SEARCH_DATE_TYPE_DAY){
//			$("[id^=searchMonth]").hide();
//			$("[id^=searchDay]").show();
//		}else{
//			$("[id^=searchDay]").hide();
//			$("[id^=searchMonth]").show();
//			abc.biz.stats.member.point.monthYearSelectBox(selectValue);
//		}
//	}
	
	//날짜 선택 박스
//	_point.monthYearSelectBox = function(value){
//		$('#searchMonth1').find("option").remove();
//		$('#searchMonth3').find("option").remove();
//		
//		var now = new Date();
//		var nyear = now.getFullYear();
//		var nmon = now.getMonth()+1;
//		var lastDate;
//		var lastDay;
//		
//		if(value == abc.consts.SEARCH_DATE_TYPE_MONTH){
//			for(var i = nmon ; i >= 1 ; i--){
//				lastDate = new Date(nyear, i, 1);
//				lastDate.setDate(lastDate.getDate() - 1);
//				lastDay = lastDate.getDate();
//				if( i < 10 ){
//					$('#searchMonth1').append('<option value="' + nyear+ ".0" + i + ".01"+ '">' + nyear + ".0"+ i + '</option>');
//					$('#searchMonth3').append('<option value="' + nyear+ ".0" + i + "." + lastDay +'">' + nyear + ".0"+ i + '</option>');
//				}else{
//					$('#searchMonth1').append('<option value="' + nyear + "." + i + ".01"+ '">' + nyear + "."+ i + '</option>');
//					$('#searchMonth3').append('<option value="' + nyear + "." + i + "." + lastDay + '">' + nyear + "."+ i + '</option>');
//				}
//			}
//			for(var sy = nyear-1 ; sy > 2010 ; sy--) {
//				for(var i = 12 ; i >=1 ; i --){
//					lastDate = new Date(sy, i, 1);
//					lastDate.setDate(lastDate.getDate() - 1);
//					lastDay = lastDate.getDate();
//					if( i < 10 ){
//						$('#searchMonth1').append('<option value="' + sy + "." + i + ".01"+ '">' + sy+ ".0"+ i + '</option>');
//						$('#searchMonth3').append('<option value="' + sy + "." + i + "." + lastDay +'">' + sy+ ".0"+ i + '</option>');
//					}else{
//						$('#searchMonth1').append('<option value="' + sy + "." + i + ".01"+ '">' + sy+ "."+ i + '</option>');
//						$('#searchMonth3').append('<option value="' + sy + "." + i + "." + lastDay + '">' + sy+ "."+ i + '</option>');
//					}
//				}
//			}
//		}else{
//			for(var sy = nyear ; sy > 2010 ; sy--) {
//				$('#searchMonth1').append('<option value="' + sy + ".01.01" + '">' + sy+ '</option>');
//				$('#searchMonth3').append('<option value="' + sy + ".12.31" + '">' + sy+ '</option>');
//			}
//		}
//	}
	
	
	//그리드 text변경
	_point.gridText = function(value){
		var gridText;
		if(value == abc.consts.SEARCH_DATE_TYPE_MONTH){
			gridText = '월별통계';
		}else if(value == abc.consts.SEARCH_DATE_TYPE_YEAR){
			gridText = '년별통계';
		}else{
			gridText = '일별통계';
		}
		
		return gridText;
	}
	
	
})();