/***
 * 사이트 관리.
 * 
 */
(function() {

	var _store = abc.object.createNestedObject(window,"abc.biz.stats.member.store");
	
	/*************************************************************************
	 *					회원 탈퇴사유 통계
	 *************************************************************************/
	
	_store.initStoreSheet = function() {
		// Grid 객체 생성
		createIBSheet2(document.getElementById("storeGrid"), "storeSheet");
		var pageCount = $('#pageCount').val();
		var initStoreSheet = {};
		initStoreSheet.Cfg = {SearchMode : smLazyLoad,	Page : pageCount,MaxSort : 1, AutoFitColWidth: "init|search|resize|rowrestoreData"};
		initStoreSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initStoreSheet.Cols = [
				  {Header : "",		Type : "Status",SaveName : "status",		Width : 0,	Align : "",			Edit : 0,	Hidden : 1}
				, {Header : "NO",	Type : "Text",	SaveName : "noSeq",			Width : 12,	Align : "Center",	Edit : 0,	Sort : 0}
				, {Header : "매장명",	Type : "Text",	SaveName : "storeName",		Width : 70,	Align : "Center",	Edit : 0,	Sort : 0}
				, {Header : "등록수",	Type : "Text",	SaveName : "storeCount",	Width : 70,	Align : "Center",	Edit : 0,	Sort : 1}
				];
		// Grid 초기화
		IBS_InitSheet(storeSheet, initStoreSheet);
		// Grid 건수 정보 표시
		storeSheet.SetCountPosition(3);
		// Grid width 자동 조절
		storeSheet.FitColWidth();
		// Grid width 자동 조절 
		storeSheet.FitColWidth("10|45|45");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		storeSheet.SetExtendLastCol(1);
		
		abc.biz.stats.member.store.storeSearch();
	}
	
	
	//검색
	_store.storeSearch = function(){
		// 페이지리스트 목록 개수
		
		var param = { 
			  url : "/stats/member/read-interest-store"
			, sheet : "storeSheet" 
		};
		DataSearch(param);
	}
	
	//엑셀 다운로드 버튼 
	$("#excelBtn").click(function(){
		abc.biz.stats.member.store.excelDownload();
	});
	
	// 엑셀 다운로드
	_store.excelDownload = function(){
		if(storeSheet.GetTotalRows() === 0) {
			alert("엑셀 다운로드는 조회 후에 이용 가능합니다.");
				return false;
		}

		$("#storeForm")
		.prop("action","/stats/member/interest-store-excel")
		.prop("method", "post")
		.submit();
		
	}
	
	
	
})();