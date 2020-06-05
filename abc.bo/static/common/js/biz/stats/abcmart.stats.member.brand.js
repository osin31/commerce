/***
 * 사이트 관리.
 * 
 */
(function() {

	var _brand = abc.object.createNestedObject(window,"abc.biz.stats.member.brand");
	
	/*************************************************************************
	 *					회원 탈퇴사유 통계
	 *************************************************************************/
	
	_brand.initBrandSheet = function() {
		// Grid 객체 생성
		createIBSheet2(document.getElementById("brandGrid"), "brandSheet");
		var pageCount = $('#pageCount').val();
		var initBrandSheet = {};
		initBrandSheet.Cfg = {SearchMode : smLazyLoad,	Page : pageCount,MaxSort : 1, AutoFitColWidth: "init|search|resize|rowrestoreData"};
		initBrandSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initBrandSheet.Cols = [
				  {Header : "",		Type : "Status",SaveName : "status",		Width : 0,	Align : "",			Edit : 0,	Hidden : 1}
				, {Header : "NO",	Type : "Text",	SaveName : "noSeq",			Width : 12,	Align : "Center",	Edit : 0,	Sort : 0}
				, {Header : "브랜드명",Type : "Text",	SaveName : "brandName",		Width : 70,	Align : "Center",	Edit : 0,	Sort : 0}
				, {Header : "등록수",	Type : "Text",	SaveName : "brandCount",	Width : 70,	Align : "Center",	Edit : 0,	Sort : 1}
				];
		// Grid 초기화
		IBS_InitSheet(brandSheet, initBrandSheet);
		// Grid 건수 정보 표시
		brandSheet.SetCountPosition(3);
		// Grid width 자동 조절
		brandSheet.FitColWidth();
		// Grid width 자동 조절 
		brandSheet.FitColWidth("10|45|45");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		brandSheet.SetExtendLastCol(1);
		
		abc.biz.stats.member.brand.brandSearch();
	}
	
	
	//검색
	_brand.brandSearch = function(){
		// 페이지리스트 목록 개수
		
		var param = { 
			  url : "/stats/member/read-interest-brand"
			, sheet : "brandSheet" 
		};
		DataSearch(param);
	}
	
	//엑셀 다운로드 버튼 
	$("#excelBtn").click(function(){
		abc.biz.stats.member.brand.excelDownload();
	});
	
	// 엑셀 다운로드
	_brand.excelDownload = function(){
		if(brandSheet.GetTotalRows() === 0) {
			alert("엑셀 다운로드는 조회 후에 이용 가능합니다..");
				return false;
		}

		$("#brandForm")
		.prop("action","/stats/member/interest-brand-excel")
		.prop("method", "post")
		.submit();
		
	}
	
	
	
})();