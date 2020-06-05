<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 그리드 세팅 --%>
		abc.biz.member.delivery.initMemberDeliverySheet();
		
		$("#pageCount").change(function(){
			abc.biz.member.delivery.doMemberDeliveryAction('search');
		});
	});
	
</script>

<body class="window-body">
	<div class="window-wrap">
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
			<input type="hidden" name="memberNo" value="${member.memberNo}">
		</form>
		<div class="window-title">
			<h1>배송 주소록 등록내역</h1>
		</div>
		<div class="window-content">
			<div class="user-info-wrap">
				<div class="user-info">
					<div class="info-box">
						<!-- S : 190121 추가 | 회원이 블랙리스트 포함인 경우만 추가 -->
						<c:if test="${member.blackListYn eq 'Y'}">
						<span class="user-blacklist">블랙리스트</span>
						</c:if>
						<!-- E : 190121 추가 | 회원이 블랙리스트 포함인 경우만 추가 -->
						<span class="user-grade"><c:out value="${member.mbshpGradeCodeName}"/></span>
						<span class="user-id"><c:out value="${member.loginId}"/></span>
						<span class="user-name"><c:out value="${member.memberName}"/></span><span>님</span>
						<span class="user-membership">(멤버십: <c:out value="${member.mbshpCardNo}"/>)</span>
					</div>
				</div>
			</div>

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">배송 주소록 내역</h3>
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

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="deliveryGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->


		</div>
	</div>
</body>

<script src="/static/common/js/biz/member/abcmart.member.delivery.js<%=_DP_REV%>">
</script>