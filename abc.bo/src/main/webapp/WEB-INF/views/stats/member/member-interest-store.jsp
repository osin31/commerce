<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		abc.biz.stats.member.store.initStoreSheet();
	});	

	function storeSheet_OnSearchEnd(Code, Msg){
		storeSheet.SetFrozenRows(1);
		
		if(storeSheet.SearchRows() > 0){
			$('#excelBtn').removeClass('disabled');
		} else {
			$('#excelBtn').addClass('disabled');
		}
	}
	
	function storeSheet_OnSort(){
		//console.log("RowCount>>>>>"+storeSheet.RowCount());
		var length = storeSheet.RowCount();
		for(i=0; i<length; i++){
			storeSheet.SetCellText((i+2), 1, (i+1));
		}
	}
	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">단골 매장 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>기타통계/현황 </li>
						<li>단골 매장 현황</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->
		
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li class="tc-red">* 기준일시 : <fmt:formatDate value="${currentTime}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/> </li>
				<li>* 현일 기준 조회 정보는 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
			</ul>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
		<form id="storeForm" name="storeForm" />
			<div class="fl">
				<h3 class="content-title">단골 매장 목록</h3>
			</div>
			<!-- s : 190626 수정 // 버튼 위치 수정 -->
			<div class="fr">
				<a href="javascript:void(0)" id="excelBtn" class="btn-sm btn-func disabled">엑셀 다운로드</a>
			</div>
			<!-- e : 190626 수정 // 버튼 위치 수정 -->
		</div>
		<!-- E : content-header -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="storeGrid" style="width:100%; height:429px;">
				
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/stats/abcmart.stats.member.store.js<%=_DP_REV%>">
	
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>