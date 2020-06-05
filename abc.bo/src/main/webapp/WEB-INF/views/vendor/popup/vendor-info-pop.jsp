<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {

	});
</script>

<body class="window-body">
<input type="hidden" id="popUnYn" value="Y"/>

	<div class="window-wrap">
		<div class="window-title">
			<h1>입점사 상세정보 조회</h1>
		</div>
		<div class="window-content">
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
</body>

</html>