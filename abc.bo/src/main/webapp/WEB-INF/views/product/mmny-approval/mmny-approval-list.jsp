<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script>
$(function() {
	abc.biz.product.mmnyApproval.codeCombo = ${codeCombo};
	abc.biz.product.mmnyApproval.tierFlagCodeCombo = ${tierFlagCodeCombo};
});
</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">자사상품 승인관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>상품승인관리</li>
						<li>자사상품 승인관리</li>
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

		<%@include file="mmny-approval-list-search.jsp" %>

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
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="mmnyApproval-list"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.mmnyApproval.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>