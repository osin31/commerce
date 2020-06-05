<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		abc.biz.vendor.inquiry.event();

		// 최초 관리자 메모 로딩
		abc.biz.vendor.inquiry.adminMemoLoad();

	});

</script>
<body class="window-body">
	<div class="window-wrap">
	<form id="vendorForm" name="vendorForm">
	<input type="hidden" name="contactUsSeq" id="contactUsSeq" value="<c:out value="${param.contactUsSeq}"/>">
		<div class="window-title">
			<h1>입점/제휴문의 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">게시물 조회</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>게시글 조회</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">작성자</th>
						<td colspan="3">${inquiryDetail.dispMemberLoginId}/(${inquiryDetail.dispMemberName})</td>
					</tr>
					<tr>
						<th scope="row">휴대폰번호/이메일</th>
						<td colspan="3">${inquiryDetail.dispHdphnNoText}/${inquiryDetail.dispEmailAddrText}</td>
					</tr>
					<tr>
						<th scope="row">문의 사이트</th>
						<td colspan="3">${inquiryDetail.siteName}</td>
					</tr>
					<tr>
						<th scope="row">문의 유형</th>
						<td colspan="3">${inquiryDetail.cnslTypeDtlCodeName}</td>
					</tr>
					<tr>
						<th scope="row">제목</th>
						<td colspan="3">${inquiryDetail.inqryTitleText}</td>
					</tr>
					<tr>
						<th scope="row">내용</th>
						<td class="input" colspan="3">
							<!-- S : 20190308 수정 // 에디터 > textarea로 변경 -->
							<textarea class="ui-textarea" title="내용 입력" readOnly><c:out value="${inquiryDetail.inqryContText}"/></textarea>
							<!-- E : 20190308 수정 // 에디터 > textarea로 변경 -->
						</td>
					</tr>
					<tr>
						<th scope="row">작성자</th>
						<td colspan="3"><fmt:formatDate value="${inquiryDetail.inqryDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-link" id="btnClose">닫기</a>
			</div>
			<!-- E : window-btn-group -->
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 메모</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : manager-msg-wrap -->
			<div class="manager-msg-wrap">
				<div class="msg-box">
					<textarea title="관리자 메모 입력" id="memoText" name="memoText" maxlength="500"></textarea>
				</div>
				<button type="button" class="btn-normal btn-save" id="memoSaveBtn">저장</button>
			</div>
			<!-- E : manager-msg-wrap -->

			<!-- S : msg-list-wrap -->
			<div class="msg-list-wrap" id="adminMemoList"></div>
			<!-- E : msg-list-wrap -->
		</div>
	</form>
	</div>
</body>
<script src="/static/common/js/biz/vendor/abcmart.vendor.inquiry.js<%=_DP_REV%>"></script>
</html>