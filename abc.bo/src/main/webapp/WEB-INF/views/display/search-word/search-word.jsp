<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">검색어 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites">
							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시 관리</li>
								<li>검색어 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : tab-wrap -->
				<div class="tab-wrap">
					<ul class="tabs">
						<li class="tab-item"><a href="#tabContent1" class="tab-link" id="baseTab">검색창 검색어 관리</a></li>
						<li class="tab-item"><a href="#tabContent2" class="tab-link" id="hotTab">인기 검색어 관리</a></li>
						<li class="tab-item"><a href="#tabContent3" class="tab-link" id="suggestionTab">추천 검색어 관리</a></li>
						<li class="tab-item"><a href="#tabContent4" class="tab-link" id="channelTab">채널 검색어 관리</a></li>
					</ul>
					
					<!-- tabContent1 -->
					<%@ include file="tab/search-word-base.jsp" %>
					<!-- tabContent2 -->
					<%@ include file="tab/search-word-hot.jsp" %>
					<!-- tabContent3 -->
					<%@ include file="tab/search-word-suggestion.jsp" %>
					<!-- tabContent4 -->
					<%@ include file="tab/search-word-channel.jsp" %>
				</div>
				<!-- E : tab-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.search.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>