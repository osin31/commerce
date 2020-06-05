<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="kr.co.abcmart.common.config.Config"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<%
	String _NCP_CLIENT_ID = Config.getString("naver.map.key","");
%>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>오프라인 매장 지도 검색</h1>
		</div>
		<div class="window-content">
			<!-- S : white-box -->
			<form id="searchForm" name="searchForm">
			<div class="white-box">				
				<!-- S : ip-text-box -->				
				<span class="ip-text-box">
					<input type="text" class="ui-input" placeholder="매장명을 입력해주세요" title="매장명 입력" name="searchStoreName">
					<button type="submit" class="btn-sm btn-func" id="searchBtn">검색</button>
				</span>
				<!-- E : ip-text-box -->

				<!-- S : ip-text-box -->
				<span class="ip-text-box">
					<span class="text">X Point:</span>
					<input type="text" class="ui-input num-unit100" title="X좌표 입력" id="xPoint" value="${data.pointX}">
					<span class="text">Y Point:</span>
					<input type="text" class="ui-input num-unit100" title="Y좌표 입력" id="yPoint" value="${data.pointY}">
					<a href="#" class="btn-sm btn-func" id="saveBtn">입력</a>
				</span>
				<!-- E : ip-text-box -->
			</div>
			</form>
			<!-- E : white-box -->

			<!-- S : map-wrap -->
			<div class="map-wrap">
				<div style="height: 500px;" id="map"></div>
			</div>
			<!-- E : map-wrap -->
		</div>
	</div>
	
	<%-- 네이버지도 --%>
	<script type="text/javascript" src="//openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=<%=_NCP_CLIENT_ID %>&submodules=geocoder"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.store.map.js<%=_DP_REV%>"></script>
</body>
</html>