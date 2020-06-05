<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("inqryContText", {
			 width:"100%"
			,height:"100%"
			,readOnly : true
			,toolbarCanCollapse : true
			,toolbarStartupExpanded : false
		});

		// 최초 관리자 메모 로딩
		abc.biz.vendor.cowork.adminMemoLoad();

		abc.biz.vendor.cowork.event();
	});	

</script>
<body class="window-body">
	<div class="window-wrap">
	<form id="vendorForm" name="vendorForm" method="post" action="/vendor/cowork/create" enctype="multipart/form-data">
		<div class="window-title">
			<h1>답변등록</h1>
		</div>
		<div class="window-content">
			<!-- S : col-wrap -->
			<div class="col-wrap">
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">게시물 조회</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>게시물 조회</caption>
						<colgroup>
							<col style="width: 140px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">제목</th>
								<td><c:out value="${coworkDetail.inqryTitleText}"/></td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td>
									<textarea rows="10" cols="100" name="inqryContText" id="inqryContText" class="w100">${coworkDetail.inqryContText}</textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">문의주체</th>
								<td><c:out value="${coworkDetail.inqrySubject}"/></td>
							</tr>
							<tr>
								<th scope="row">작성자</th>
								<td><c:out value="${coworkDetail.inqryName}"/></td>
							</tr>
							<tr>
								<th scope="row">최종작성일시</th>
								<td><fmt:formatDate value="${coworkDetail.inqryDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">답변관리</h3>
						</div>
						<div class="fr">
							<div class="btn-group">
								<a href="javascript:void(0);" id="resetButton" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>답변관리</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">답변내용</th>
								<td class="input">
									<textarea class="ui-textarea" title="답변내용 입력" name="aswrContText" id="aswrContText"><c:out value="${coworkDetail.aswrContText}"/></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">답변주체</th>
								<td><c:out value="${coworkDetail.aswrSubject}"/></td>
							</tr>
							<tr>
								<th scope="row">답변자</th>
								<td><c:out value="${coworkDetail.aswrName}"/></td>
							</tr>
							<tr>
								<th scope="row">최종답변일시</th>
								<td><fmt:formatDate value="${coworkDetail.aswrDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
			</div>
			<!-- E : col-wrap -->

			<!-- S : tbl-desc-wrap -->
			<!-- <div class="tbl-desc-wrap">
				<div class="fl">
					<a href="javascript:void(0);" class="btn-normal btn-del" id="coworkDeleteBtn">삭제</a>
				</div>
			</div> -->
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<c:if test="${coworkDetail.aswrStatCode != '10002'}">
					<a href="javascript:void(0);" class="btn-normal btn-save" id="coworkReplySaveBtn">저장</a>
				</c:if>
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
 		<input type="hidden" name="corprBoardSeq" id="corprBoardSeq" value="<c:out value="${param.corprBoardSeq}"/>">
		<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
		<input type="hidden" id="inqrerNo" value="<c:out value="${coworkDetail.inqrerNo}" />">
		<input type="hidden" name="aswrStatCode" value="10002">
	</form>
	</div>
</body>

<script src="/static/common/js/biz/vendor/abcmart.vendor.cowork.js<%=_DP_REV%>"></script>
