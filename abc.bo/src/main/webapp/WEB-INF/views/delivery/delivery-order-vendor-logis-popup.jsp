<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
		$(function() { 
		
		// 목록 그리드 초기화
		abc.biz.delivery.order.vendor.logis.popup.initDataListSheet();

		//초기화
		abc.biz.delivery.order.vendor.logis.popup.init();

		});

 
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>택배사</h1>
		</div>
		<div class="window-content">
		<form id="gForm" name="gForm">
			<!-- S : 엑셀 업로드 완료 팝업 -->
			<!-- DESC : 완료 팝업인 경우 노출되는 영역. 임시 div 삭제 필요 -->
			<div style="margin-top: 20px;"> 
				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid"></div>
				</div>
				<input type="hidden" name="param" value="">
				<!-- E : ibsheet-wrap -->

				<!-- S : window-btn-group -->
				<div class="window-btn-group">
					<a href="javascript:void(0)" id="popupClose" name="popupClose" class="btn-normal btn-del">닫기</a>
				</div>
				<!-- E : window-btn-group -->
			</div>
			<!-- E : 엑셀 업로드 완료 팝업 -->
		</div>

		</form>
	</div>
</body>
</html>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.logis.popup.js<%=_DP_REV%>"></script>