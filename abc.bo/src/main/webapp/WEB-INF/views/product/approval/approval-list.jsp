<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
	$(function() {

		<%-- 승인상태코드 변수 선언 --%>
		abc.biz.product.approval.APRV_STAT_CODE_TEMPORARY	= "${CommonCode.APRV_STAT_CODE_TEMPORARY }"; //임시저장
		abc.biz.product.approval.APRV_STAT_CODE_REQUEST		= "${CommonCode.APRV_STAT_CODE_REQUEST }"; 	//승인요청
		abc.biz.product.approval.APRV_STAT_CODE_STANDBY		= "${CommonCode.APRV_STAT_CODE_STANDBY }";	//승인대기
		abc.biz.product.approval.APRV_STAT_CODE_REJECT		= "${CommonCode.APRV_STAT_CODE_REJECT }";	//승인반려
		abc.biz.product.approval.APRV_STAT_CODE_CONFIRM		= "${CommonCode.APRV_STAT_CODE_CONFIRM }";	//승인완료
		//IBSheet 콤보 코드
		abc.biz.product.approval.codeCombo = ${codeCombo };
	});
</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">상품승인대상내역</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품승인관리</li>
						<li>상품승인대상내역</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">승인대상 상품 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<%@include file="approval-list-search.jsp" %>
		
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">승인대상 상품 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<label class="title" for="page-count">목록개수</label>
				<select class="ui-sel" id="page-count">
					<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
				</select>
				</span>
				<!-- E : opt-group -->
				<div class="btn-group">
					<button type="button" id="delete" class="btn-sm btn-del">선택 상품 삭제</button>
					<button type="button" id="approval" class="btn-sm btn-func">선택 상품 승인요청</button>
				</div>
			</div>
			<div class="fr">
				<c:if test="${Const.AUTH_APPLY_SYSTEM_TYPE_BO ne userAuthority }">
					<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="registry">상품 등록</a>
				</c:if>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="approval-list">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.approval.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>			
