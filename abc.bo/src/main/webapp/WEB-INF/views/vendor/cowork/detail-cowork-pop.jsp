<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		if(!abc.text.allNull(abc.param.getParams().vndrNo)){
			abc.biz.vendor.cowork.setVndrName(abc.param.getParams().vndrNo);
		}

		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("inqryContText", {
			width:"100%",
			height:"100%"
		});

		abc.biz.vendor.cowork.event();
	});

</script>
<body class="window-body">
	<div class="window-wrap">
	<form id="vendorForm" name="vendorForm" method="post" action="/vendor/cowork/create" enctype="multipart/form-data">
		<div class="window-title">
			<h1>게시글 등록</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">게시글 등록</h2>
				</div>
				<div class="fr">
					<div class="btn-group">
						<a href="javascript:void(0);" id="resetButton" class="btn-sm btn-func" id="btnReset"><i class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>게시글 등록</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<c:if test="${userDetails.inqryGbnType ne 'V'}">
						<tr>
							<th scope="row">대상업체</th>
							<td class="input" colspan="3">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" class="ui-input" title="입점업체 입력" id="vndrName" name="vndrName" value="<c:out value="${coworkDetail.vndrName}"/>" readOnly>
									<a href="javascript:void(0);" id="searchVendor" class="btn-sm btn-link">입점업체 찾기</a>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
					</c:if>
					<tr>
						<th scope="row">
							<span class="th-required">제목</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" class="ui-input" title="제목 입력" id="inqryTitleText" name="inqryTitleText" maxlength="50" value="<c:out value="${coworkDetail.inqryTitleText}"/>">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">내용</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea rows="20" cols="100" name="inqryContText" id="inqryContText" class="w100">${coworkDetail.inqryContText}</textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">작성자</th>
						<td>
							<c:if test="${not empty coworkDetail.inqryName}">
								<c:out value="${coworkDetail.inqryName }" />
							</c:if>
							<c:if test="${empty coworkDetail.inqryName}">
								<c:out value="${userDetails.loginId }" />(<c:out value="${userDetails.username }" />)
							</c:if>
						</td>
						<th scope="row">작성일시</th>
						<td> <fmt:formatDate value="${coworkDetail.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
					<tr>
						<th scope="row">수정자</th>
						<td>
							<c:if test="${isUpdateMode eq 'true' and coworkDetail.modDtm != null}">
								<c:out value="${userDetails.loginId }" />(<c:out value="${userDetails.username }" />)
							</c:if>
						</td>
						<th scope="row">수정일시</th>
						<td> <fmt:formatDate value="${coworkDetail.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:abc.biz.vendor.cowork.doActionVendorCowork('coworkSave');" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
		<input type="hidden" id="inqryGbnType" name="inqryGbnType" value="<c:out value="${userDetails.inqryGbnType}" />" />
		<input type="hidden" id="vndrNo" name="vndrNo" value="<c:out value="${coworkDetail.vndrNo}" />">
		<input type="hidden" id="corprBoardSeq" name="corprBoardSeq" value="<c:out value="${param.corprBoardSeq}" />">
		<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
	</form>
	</div>
</body>
<script src="/static/common/js/biz/vendor/abcmart.vendor.cowork.js<%=_DP_REV%>"></script>
