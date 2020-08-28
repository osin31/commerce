<%@page import="kr.co.abcmart.constant.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- S : header-container -->
<div class="header-container js-header-container">
	<c:choose>
		<c:when test="${pageContext.request.serverName eq 'localbo.a-rt.com'}">
				<h1 class="logo" style="background-color: #FF6347">
		</c:when>
		<c:when test="${pageContext.request.serverName eq 'devbo.a-rt.com'}">
				<h1 class="logo" style="background-color: #FF6347">
		</c:when>
		<c:otherwise>
			<h1 class="logo">
		</c:otherwise>
	</c:choose>
	
		<a href="/">
			<c:choose>
				<c:when test="${pageContext.request.serverName eq Const.LOGIN_DOMAIN_BO}">
					<img src="/static/images/common/logo.png" alt="A-RT BACK OFFICE">
				</c:when>
				<c:otherwise>
					<img src="/static/images/common/logo_po.png" alt="TOP PARTNER OFFICE">
				</c:otherwise>
			</c:choose>
		</a>
	</h1>
	<!-- S : login-info -->
	<div class="login-info">
	<sec:authorize access="isAuthenticated()">
		<div class="user"><a href="javascript:useInfoDetailView('${userDetails.adminNo}')" class="name"><c:out value="${userDetails.username}"/></a>님</div>
		<div class="info"><span class="title">최종로그인</span> <span class="desc"><fmt:formatDate value="${userDetails.lastLoginDtm}" pattern="<%= Const.DEFAULT_DATETIME_PATTERN%>" /></span></div>
		<div class="info"><span class="title">최종IP</span> <span class="desc"><c:out value="${userDetails.lastLoginIpText}"/></span></div>
		<a href="javascript:void(0)" onclick="abc.logout()" class="btn-logout"><i class="ico ico-logout"></i>로그아웃</a>
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
		<a href="/system/login" class="btn-logout"><i class="ico ico-logout"></i>로그인</a>
	</sec:authorize>
	</div>
	<!-- E : login-info -->

	<!-- S : gnb-wrap -->
	<div class="gnb-wrap" id="gnbWrapArea"></div>
	<!-- E : gnb-wrap -->
</div>
<!-- E : header-container -->

<!-- S : lnb-container -->
<div class="lnb-container js-lnb-container">
	<!-- S : favorites-wrap -->
	<div class="favorites-wrap js-favorites-wrap">
		<span class="title">즐겨찾기</span>
		<div id="favoritesWrapArea"></div>
	</div>
	<!-- E : favorites-wrap -->

	<!-- S : lnb-wrap -->
	<div class="lnb-wrap js-lnb-wrap" id="lnbWrapArea"></div>
	<!-- E : lnb-wrap -->
</div>
<!-- E : lnb-container -->

<button type="button" class="btn-lnb-toggle js-btn-lnb-toggle"><span class="offscreen">메뉴닫기</span></button>

