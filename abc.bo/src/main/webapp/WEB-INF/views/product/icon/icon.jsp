<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function(){
	abc.biz.product.icon.siteCombo = ${siteCombo};
	abc.biz.product.icon.codeCombo = ${codeCombo};
})
</script>
<style>
.icon-image-del {
	display: inline-block;
    background: url(/static/images/common/sprite.png);
    background-repeat: no-repeat;
    vertical-align: top;
    width: 17px;
    height: 17px;
    background-position: -45px -1px;
    font-size: 0;
}
</style>

		<!-- S : container -->
		<div class="container">
			<input type="hidden" name="siteCombo" value="${siteCombo}">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상품아이콘 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>상품관리</li>
								<li>상품기준 정보관리</li>
								<li>상품아이콘 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품아이콘 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="#" class="btn-sm btn-func" id="addIconBtn">아이콘 추가</a>
						<a href="#" class="btn-sm btn-save" id="saveIconBtn">변경 정보 저장</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="iconSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
				
				<!-- 대상 상품 목록 -->
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">대상 상품 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="prdtPageCount">목록개수</label>
							<select class="ui-sel" id="prdtPageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr"></div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="prdtSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->				
			</div>	
		</div>
		<!-- E : container -->
		
		<template id="icon-image-upload-tmpl">
			<div>
				<form>
				<span class="file-wrap">
					<ul class="file-list">
						<li>
							<img class="iconImage" alt="" src="" style="width: 32px; height: 32px;">
							<span class="btn-box">
								<input type="file" id="inputFile" class="inputFile" title="첨부파일 추가" name="imageFile">
								<input type="hidden" name="iconPathText">
								<label for="inputFile">찾아보기</label>
							</span>
						</li>
						<li>
							<a href="#" class="subject" id="imageName">파일명.exe</a>
							<button type="button" class="btn-file-del icon-image-del">
								<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
							</button>
						</li>
					</ul>
				</span>
				</form>
			</div>
		</template>

<script src="/static/common/js/biz/product/abcmart.product.icon.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>