<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">상품정보 일괄수정</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>상품정보 일괄수정</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">대상 상품 관리</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
<%--
					<label class="title" for="selTermsModule">목록개수</label>
					<select class="ui-sel" id="selTermsModule">
						<option>15개 보기</option>
					</select>
--%>
					<button type="button" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="list">선택 삭제</button>
				</span>
				<!-- DESC : 20190220 수정 // btn-group 영역 삭제 -->
			</div>
			<div class="fr">
				<button type="button" class="btn-sm btn-func" data-button="add-row-from-product" data-sheet-name="list">상품 추가</button>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap" id="product-list">
			<!-- <div style="width:100%; height:429px;">
				ibsheet grid영역(div 삭제 필요)
			</div> -->
		</div>
		<!-- E : ibsheet-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">일괄수정 항목 선택</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<form id="bulk-update-form">
			<input type="hidden" name="auth" value="${auth }"/>
		
			<!-- S : tab-wrap -->
			<div class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item"><a href="#tabContent1" class="tab-link">기본정보</a></li>
					<li class="tab-item"><a href="#tabContent2" class="tab-link">전시정보</a></li>
					<li class="tab-item"><a href="#tabContent3" class="tab-link">재고/옵션정보</a></li>
				</ul>
				<%@include file="/WEB-INF/views/product/bulk-update/bulk-update-tab1-basic.jsp" %>
				<%@include file="/WEB-INF/views/product/bulk-update/bulk-update-tab2-display.jsp" %>
				<%@include file="/WEB-INF/views/product/bulk-update/bulk-update-tab3-stockOption.jsp" %>
			</div>
			<!-- E : tab-wrap -->
		
		</form>

		<!-- S : content-bottom -->
		<div class="content-bottom">
			<a href="javascript:void(0);" class="btn-normal btn-func" data-button="save">일괄수정</a>
		</div>
		<!-- E: content-bottom -->
	</div>
</div>
<!-- E : container -->
<script type="text/javascript">
	var codes = ${gridCodes };<%-- 판매상태 코드 --%>
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.bulkUpdate.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>