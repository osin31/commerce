<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">

	$(function() {
		<%-- 팝업창 닫기 --%>
		$("#btnConfirm").click(function(){
			window.close();
		});
	});

</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>미리보기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">${bdPopup.popupTitleText}</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : preview-box -->
			<div class="preview-box">
				<div><img src="${bdPopup.imageSrc}" /></div>
			</div>
			<!-- E : preview-box -->
		</div>
	</div>
</body>
