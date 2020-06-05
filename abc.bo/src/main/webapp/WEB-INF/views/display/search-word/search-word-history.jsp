<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>검색어 히스토리</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">${srchWordGbnType eq 'S' ? '검색창' : srchWordGbnType eq 'P' ? '인기' : '추천'} 검색어 히스토리 - ${siteName}</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->
			
			<form id="historyForm" name="historyForm">
				<input type="hidden" name="siteNo" value="${siteNo}">
				<input type="hidden" name="srchWordGbnType" value="${srchWordGbnType}">
			</form>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="historySheet"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.search.history.js<%=_DP_REV%>"></script>
</body>
</html>