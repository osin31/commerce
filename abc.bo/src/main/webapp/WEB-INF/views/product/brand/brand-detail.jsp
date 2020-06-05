<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">브랜드 정보 상세</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites">
						<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home">
								<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>상품관리</li>
							<li>상품기준 정보관리</li>
							<li>브랜드 정보관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : tab-wrap -->
			<div class="tab-wrap" id="brandInfoTab">
			<input type="hidden" id="brandInfoStatus" value="<c:out value='${brandInfoStatus}' default='C'/>">
				<ul class="tabs">
					<li class="tab-item"><a href="#tabBrandInfo" class="tab-link">기본정보</a></li>
					<li class="tab-item"><a href="#tabBrandStlye" class="tab-link">브랜드 스타일</a></li>
					<li class="tab-item"><a href="#tabBrandProductDetail" class="tab-link">브랜드 상품상세</a></li>
					<li class="tab-item"><a href="#tabBrandPageArt" class="tab-link">브랜드 페이지(통합몰)</a></li>
					<li class="tab-item"><a href="#tabBrandPageOts" class="tab-link">브랜드 페이지(OTS)</a></li>
				</ul>

				<%@include file="/WEB-INF/views/product/brand/detail/brand-basic-info.jsp" %>

				<%@include file="/WEB-INF/views/product/brand/detail/brand-style.jsp" %>

				<%@include file="/WEB-INF/views/product/brand/detail/brand-product-detail.jsp" %>

				<%@include file="/WEB-INF/views/product/brand/detail/brand-page-art.jsp" %>

				<%@include file="/WEB-INF/views/product/brand/detail/brand-page-ots.jsp" %>

			</div>
			<!-- E : tab-wrap -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<!-- 수정페이지에 삭제버튼 노출
					<div class="fl">
						<a href="#" class="btn-normal btn-del">삭제</a>
					</div>
					-->
					<div class="fr">
						<a href="javascript:history.go(-1);" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->
				<a href="#" class="btn-lg btn-save" id="save">저장</a>
			</div>
			<!-- E : content-bottom -->

		</div>
	</div>
	<!-- E : container -->

<template id="file-upload-tmpl">
	<div>
		<span class="file-wrap">
			<span class="btn-box">
			<form id="form-brand-upload">
				<input type="file" id="fileUpload" name="fileUpload" title="첨부파일 추가" data-to="upload"/>
				<label for="fileUpload">찾아보기</label>
			</form>
			</span>
		</span>
	</div>
</template>
<template id="btn-prdt-mng">
	<div>
		<button type="button" class="btn productSearchPop" data-brand-no="" data-sort-seq="">
			<span class="btn-box">대상 상품관리</span>
		</button>
	</div>
</template>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.brand.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>