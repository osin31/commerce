<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script>
$(function() {
	abc.biz.product.review.codeCombo = ${codeCombo};
});
</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">상품후기관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>상품후기관리</li>
						<li>포토후기</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<%@include file="review-list-search.jsp" %>
		
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">상품 후기 목록</h3>
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
					<select class="ui-sel" title="지급 선택" name="batchPointPayYn">
						<option value="">지급 선택</option>
						<!-- <option value="Y">지급</option> -->
						<option value="G">일반포인트 지급</option>
						<option value="P">포토포인트 지급</option>
						<!-- <option value="N">미지급</option> -->
						<option value="C">지급취소</option>
					</select>
				</span>
				<!-- E : opt-group -->
				<span class="btn-group">
				<a href="javascript:void(0);" id="batch-point" class="btn-sm btn-func">포인트 지급상태 일괄변경</a>
				<a href="javascript:void(0);" id="batch-cnfrm" class="btn-sm btn-func">선택 일괄확인</a>
				<a href="javascript:void(0);" id="batch-aswr" class="btn-sm btn-link">선택 일괄답변</a>
				</span>
			</div>
			<div class="fr">
				<a href="javascript:void(0);" id="change-save" class="btn-sm btn-save">변경 저장</a>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="review-list">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.review.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>