<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<input type="hidden" id="upAuthNo" value="<c:out value="${userDetails.adminAuthorities[0].upAuthNo}"/>"><%-- 상위권한 --%>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<c:choose>
					<c:when test="${vendorInfo.status == 'U'}">
						<h2 class="page-title">입점사 정보상세</h2>
					</c:when>
					<c:otherwise>
						<h2 class="page-title">입점사 정보등록</h2>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>입점관리</li>
						<li>입점사 정보등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : tab-wrap -->
		<div class="tab-wrap" id="tabVendorDetail">
			<ul class="tabs">
				<li class="tab-item"><a href="#tabContent1" class="tab-link">입점사 기본정보</a></li>
				<!-- DESC : 입점사 기본정보 입력시, 부가정보탭 비활성화. tab-link영역 tab-disabled 클래스 추가 해주세요 -->
				<li class="tab-item"><a href="#tabContent2" id="vendorAddInfo" class="tab-link tab-disabled">입점사 부가정보</a></li>
			</ul>

			<%@include file="/WEB-INF/views/vendor/info/create-form-vendor-baseinfotab.jsp" %>
			<div id="tabContent2" class="tab-content"></div>

		</div>
		<!-- E : tab-wrap -->

	</div>
</div>
<!-- E : container -->


<%@include file="/WEB-INF/views/common/footer.jsp"%>