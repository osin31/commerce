<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 조건날짜 관리 목록 초기 세팅. --%>
		abc.biz.cmm.dayscondtn.initDaysSheet();
		
		<%-- 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.cmm.dayscondtn.daysDoAction('save');
		});
		
		<%-- 초기화 --%>
		$("#resetBtn").click(function(){
			abc.biz.cmm.dayscondtn.daysDoAction('search');
		});
		
		<%-- 목록개수 변경 시 --%>
		$("#pageCount").change(function(){
			abc.biz.cmm.dayscondtn.daysDoAction('search');
		});
		
	});
	
	<%-- 그리드 값 체크--%>
	function daysSheet_OnEditValidation(Row, Col, Value) {
		if(daysSheet.ColSaveName(Col) == "condtnTermValue"){
			if(Value == ""){
				alert("조건 기간 값은 필수입니다.");
				daysSheet.ValidateFail(1); 
			}
		}
	}
	
	<%-- DataSave End 이벤트 --%>
	function daysSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		if(Code >= 0){
			alert(Msg);
			abc.biz.cmm.dayscondtn.daysDoAction('search');
		}else{
			alert(Msg);
		}
	}
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">조건 날짜 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>기본설정</li>
						<li>조건 날짜 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->


		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">날짜 조건 설정</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
			</div>
			<div class="fr">
				<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
				<a href="#" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
				<!-- E : 20190114 수정 -->
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="dasyGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->

		<!-- S : content-bottom -->
		<div class="content-bottom">
			<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
			<a href="#" id="saveBtn" class="btn-lg btn-save">저장</a>
			<!-- E : 20190114 수정 -->
		</div>
		<!-- E : content-bottom -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/cmm/abcmart.cmm.dayscondtn.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>