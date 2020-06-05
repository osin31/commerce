<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {

	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title"><c:out value="${detailInfo.loginId}"/>(<c:out value="${detailInfo.adminName}"/>) 관리자</h2>
				</div>
				<div class="fr">
					<span class="guide-text">최종 로그인 : <fmt:formatDate value="${userDetails.lastLoginDtm}" pattern="yyyy-MM-dd HH:mm:ss"/> 최종 로그인 IP <c:out value="${userDetails.lastLoginIpText}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">기본정보</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>기본정보</caption>
				<colgroup>
					<col style="width: 135px;">
					<col>
					<col style="width: 135px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">권한그룹명</span>
						</th>
						<td><c:out value="${detailInfo.authName}"/></td>
						<th scope="row">권한적용 시스템</th>
						<td><c:out value="${detailInfo.authApplySystemType}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 id</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.loginId}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 이름</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.adminName}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이메일</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.emailAddrText}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.hdphnNoText}"/></td>
					</tr>
					<tr>
						<th scope="row">사용여부</th>
						<td colspan="3"><c:out value="${detailInfo.useYn}"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

		</div>
	</div>
</body>

<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>