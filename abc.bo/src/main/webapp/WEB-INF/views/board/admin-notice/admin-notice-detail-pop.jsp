<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {

		$("#btnConfirm").click(function(){
			window.close();
		});
		
		$("#closePopupNotTodayBtn").click(function(){
			abc.biz.board.adminnotice.closePopupNotToday($(this).attr("data"));
		});

		// 파일 다운도르
		$("li[name^=attachFileName]").off().on('click', function() {
			abc.biz.board.adminnotice.fileDownLoad(this);
		});
	});
</script>

<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>공지사항</h1>
		</div>
		<div class="window-content">
			<div class="content-header">
				<div class="fl">
					<h3 class="notice-title">${bdAdminNotice.notcTitleText}</h3>
				</div>
			</div>
			<div class="notice-view">
				<div class="notice-header">
					<div class="fl">
						<span class="notice-author"><c:out value="${bdAdminNotice.dispRegstName}"/></span>
					</div>
					<div class="fr">
						<div class="notice-item-info">
							<span class="notice-view-cnt">조회수<span>${bdAdminNotice.hitCount}</span></span>
							<span class="notice-reg-date">등록일<span><fmt:formatDate value="${bdAdminNotice.rgstDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></span></span>
						</div>
					</div>
				</div>
				<div class="notice-body-wrap">
					${bdAdminNotice.notcContText}
				</div>
			</div>

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<c:if test="${isMainPopup}">
					<a href="#" class="btn-normal btn-del" id="closePopupNotTodayBtn" data="${bdAdminNotice.adminNotcSeq}">오늘 하루 보지 않기</a>
				</c:if>
				<a href="#" class="btn-normal btn-link" id="btnConfirm">확인</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>

	<script src="/static/common/js/biz/board/abcmart.board.adminnotice.js<%=_DP_REV%>">
	</script>
</body>