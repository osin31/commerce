<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("onlinePolicyGrid"), "onlinePoliceSheet");
		<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initSheet = {};
		pageCount = ('<c:out value="${param.pageCount}"/>' == '') ? $('#pageCount').val() : '<c:out value="${param.pageCount}"/>' ;
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"상태",			Type:"Status",		SaveName:"status", 			Width:10,	Hidden:1}
			, {Header:"번호", 		Type:"Seq", 		SaveName:"", 				Width: 5, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"plcySeq",	Type:"Text",		SaveName:"plcySeq",			Width:5, 	Align:"Center", 	Edit:0, Hidden:1, Sort:0}
			, {Header:"정책시작일",	Type:"Text",		SaveName:"plcyApplyYmd",	Width:15, 	Align:"Center", 	Edit:0, Format:"Ymd", FontUnderline:1, Sort:0, Cursor:"Pointer"}
			, {Header:"작성자",		Type:"Text",		SaveName:"adminInfo",		Width:10, 	Align:"Left", 		Edit:0, Sort:0}
			, {Header:"작성일시",		Type:"Date",		SaveName:"rgstDtm",			Width:10, 	Align:"Center", 	Edit:0, Format:"YmdHms"}
		];
		
		<%-- Grid 초기화 --%>
		IBS_InitSheet(onlinePoliceSheet , initSheet);
		<%-- Grid 건수 정보 표시 --%>
		onlinePoliceSheet.SetCountPosition(3);
		<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		onlinePoliceSheet.SetPagingPosition(2);
		<%-- Grid width 자동 조절 --%>
		onlinePoliceSheet.FitColWidth();
		<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		onlinePoliceSheet.SetExtendLastCol(1);
		
		$('#pageCount').val(pageCount);	// 그리드 page per list 세팅
		
		doAction("search");	// 온라인 회원 정책 그리드 조회
		
		$('#pageCount').on('change', function(event) {
			doAction("search");
		});
		
		<%-- 온라인 정책 추가 --%>
		$("#regOnlinePolicy").click(function(f) {
			abc.biz.system.onlinepolicy.setOnlinePolicyData();
		});
	});
	
	function doAction(sAction){
		switch(sAction) {
			case "search" :	<%-- 조회 --%>
				var param = { url : "/cmm/policy/read-list"
					, onePageRow  : $('#pageCount').val()
					, subparam    : FormQueryStringEnc(document.onlinePolicyForm)
					, sheet       : "onlinePoliceSheet" };
				DataSearchPaging(param);
			break;
		}
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function onlinePoliceSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			<%-- 번호 클릭시  --%>
			if(onlinePoliceSheet.ColSaveName(Col) == "plcyApplyYmd") {
				var plcySeq = onlinePoliceSheet.GetCellValue(Row, "plcySeq");
				$('#plcySeq').val(plcySeq);
				
				onlinePolicyForm.action = "/cmm/policy/online-policy-pop";
				onlinePolicyForm.submit();
			}
		}
	}
	
</script>
	<!-- S : container -->
	<form id="onlinePolicyForm" name="onlinePolicyForm" method="get" onsubmit="return false;">
	<input type="hidden" id="plcySeq" name="plcySeq" value="">
	
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">혜택 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>기본설정</li>
							<li>회원 정책 설정</li>
							<li>온라인 회원 혜택</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->


			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">혜택 관리 변경 이력</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount" name="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
				<div class="fr">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0);" class="btn-sm btn-link" id="regOnlinePolicy">등록</a>
					<!-- E : 20190114 수정 -->
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="onlinePolicyGrid" style="width:100%; height:729px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	</form>
	<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.onlinepolicy.js<%=_DP_REV%>"></script>	
<%@include file="/WEB-INF/views/common/footer.jsp"%>	