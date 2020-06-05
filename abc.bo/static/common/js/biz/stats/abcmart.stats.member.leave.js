/***
 * 사이트 관리.
 * 
 */
(function() {

	var _leave = abc.object.createNestedObject(window,"abc.biz.stats.member.leave");
	
	/*************************************************************************
	 *					회원 탈퇴사유 통계
	 *************************************************************************/
	
	_leave.initLeaveSheet = function() {
		// Grid 객체 생성
		createIBSheet2(document.getElementById("leaveGrid"), "leaveSheet");
		var pageCount = $('#pageCount').val();
		var initLeaveSheet = {};
		initLeaveSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount,MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initLeaveSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initLeaveSheet.Cols = [
			  {Header : "기간별통계|일별통계",						Type : "Text",	SaveName : "totalDayC",	Width : 20,	Align : "Center",	Edit : 0,	Sort : 1}
			, {Header : "전체|전체",							Type : "Int",	SaveName : "total",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "상품(사이즈, 종류)이\n 다양하지 않아서|상품(사이즈, 종류)이\n 다양하지 않아서", Type : "Int",	SaveName : "type0", Width : 25,	Align : "Center", Edit : 0,	Sort : 0}
			, {Header : "방문 빈도가\n낮아서|방문 빈도가\n낮아서",		Type : "Int",	SaveName : "type1",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "상품가격이\n비싸서|상품가격이\n비싸서",		Type : "Int",	SaveName : "type2",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "개인 정보\n유출우려|개인 정보\n유출우려",		Type : "Int",	SaveName : "type3",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "쇼핑몰의\n신뢰도 부족|쇼핑몰의\n신뢰도 부족",	Type : "Int",	SaveName : "type4",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "사이트\n쇼핑기능이 불편|사이트\n쇼핑기능이 불편",	Type : "Int",	SaveName : "type5",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "고객서비스\n불만족|고객서비스\n불만족",		Type : "Int",	SaveName : "type6",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "배송서비스\n불만족|배송서비스\n불만족",		Type : "Int",	SaveName : "type7",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "교환/환불/반품\n불만족|교환/환불/반품\n불만족",	Type : "Int",	SaveName : "type8",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "기타|기타",							Type : "Int",	SaveName : "type9",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "휴먼회원탈퇴|휴먼회원탈퇴",					Type : "Int",	SaveName : "type10",	Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			, {Header : "강제탈퇴처리|강제탈퇴처리",					Type : "Int",	SaveName : "type11",	Width : 20,	Align : "Center",	Edit : 0,	Sort : 0}
			];
		// Grid 초기화
		IBS_InitSheet(leaveSheet, initLeaveSheet);
		// Grid 건수 정보 표시
		leaveSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시 
		leaveSheet.SetPagingPosition(2);
		// Grid width 자동 조절 
		leaveSheet.FitColWidth("7|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		leaveSheet.SetExtendLastCol(2);
		leaveSheet.SetFrozenCol(2);
		
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
		$("#leaveForm")[0].reset();
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
	 * 기간 타입 select box 변경 시
	 */
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
		}else if(val == abc.consts.SEARCH_DATE_TYPE_abc.consts.SEARCH_DATE_TYPE_YEAR){
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
	
	//select box 변동시에 
//	_leave.selectBoxSet = function(){
//		var selectValue = $("#totalDayType").val();
//		
//		if(selectValue == abc.consts.SEARCH_DATE_TYPE_DAY){
//			$("[id^=searchMonth]").hide();
//			$("[id^=searchDay]").show();
//		}else{
//			$("[id^=searchDay]").hide();
//			$("[id^=searchMonth]").show();
//			abc.biz.stats.member.leave.monthYearSelectBox(selectValue);
//		}
//	}
	
	//년월 select Box 생성
//	_leave.monthYearSelectBox = function(value){
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
//			for(var i = 1 ; i <= nmon ; i++){
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
//			for(var sy = nyear-1 ; sy <= nyear ; sy++) {
//				for(var i = 1 ; i <= 12 ; i ++){
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
	_leave.gridText = function(value){
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
	
	//검색
	_leave.leaveSearch = function(){
		var searchType = $("#periodType").val();
		var fromDate;
		var toDate;
		if(searchType == abc.consts.SEARCH_DATE_TYPE_MONTH){
			fromDate = new Date($("#periodMonthStart")[0].value);
			toDate = new Date($("#periodMonthEnd")[0].value);
		}else if(searchType == abc.consts.SEARCH_DATE_TYPE_YEAR){
			fromDate = new Date($("#periodYearStart")[0].value);
			toDate = new Date($("#periodYearEnd")[0].value);
		}else{
			fromDate = new Date($('#fromDate')[0].value);
			toDate = new Date($('#toDate')[0].value);
		}
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
		var param = { url : "/stats/member/leave/read-member-leave"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.leaveForm)
				, sheet : "leaveSheet" };
		DataSearchPaging(param);
	}
	
	//합계값 가져오기
	/*_leave.totalSet = function(){
		var $form = $("#leaveForm");
		
		$.ajax({
			type:"post",
			url: "/stats/member/leave/read-total",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			console.log(data);
			if (data.result) {
				leaveSheet.ShowFooterRow([{
					"totalDayC": data.totalData.totalDayC ,	"totalDayC#FontColor": "Black",
					"total":  data.totalData.total ,		"total#FontColor": "Black",		"tota1#Type": "Int",
					"type0":  data.totalData.type0 ,		"type0#FontColor": "Black", 	"type0#Type": "Int",
					"type1":  data.totalData.type1 ,		"type1#FontColor": "Black",		"type1#Type": "Int",
					"type2":  data.totalData.type2 ,		"type2#FontColor": "Black",		"type2#Type": "Int",
					"type3":  data.totalData.type3 ,		"type3#FontColor": "Black",		"type3#Type": "Int",
					"type4":  data.totalData.type4 ,		"type4#FontColor": "Black",		"type4#Type": "Int",
					"type5":  data.totalData.type5 ,		"type5#FontColor": "Black",		"type5#Type": "Int",
					"type6":  data.totalData.type6 ,		"type6#FontColor": "Black",		"type6#Type": "Int",
					"type7":  data.totalData.type7 ,		"type7#FontColor": "Black",		"type7#Type": "Int",
					"type8":  data.totalData.type8 ,		"type8#FontColor": "Black",		"type8#Type": "Int",
					"type9":  data.totalData.type9 ,		"type9#FontColor": "Black",		"type9#Type": "Int",
					"type10": data.totalData.type10 ,		"type10#FontColor": "Black", 	"type10#Type": "Int",
					"type11": data.totalData.type11 ,		"type11#FontColor": "Black", 	"type11#Type": "Int"
				}]);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}*/
	
	// 엑셀 다운로드
	_leave.excelDownload = function(){
		if(leaveSheet.GetTotalRows() === 0) {
			alert("엑셀 다운로드는 조회 후에 이용 가능합니다..");
				return false;
		}

		$("#leaveForm")
		.prop("action","/stats/member/leave/download-excel")
		.prop("method", "post")
		.submit();
		
	}
	
	
	
})();