<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>수동 발송 이메일  템플릿</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">수동 발송 이메일  템플릿 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="frmEmailTemplate" name="frmEmailTemplate">
			<input type="hidden" name="emailTmplSeq" id="emailTmplSeq"  value="${cmEmailTemplate.emailTmplSeq}"/>
			<table class="tbl-row">
				<caption>이메일  템플릿 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사이트 구분</span>
						</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
						<c:forEach var="site" items="${siteList}">
								<li>
									<span class="ui-rdo">
										<c:choose>
											<c:when test="${cmEmailTemplate.siteNo eq null }">
												<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}"<c:if test="${site.siteNo eq '10000'}"> checked</c:if>>
												<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
											</c:when>
											<c:otherwise>
												<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}"<c:if test="${site.siteNo eq cmEmailTemplate.siteNo}"> checked</c:if>>
												<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
						</c:forEach>
								<li>
									<span class="ui-rdo">
										<input id="chkSite99999" name="siteNo" class="ip_chk" type="radio" value="99999"<c:if test="${cmEmailTemplate.siteNo eq '99999'}"> checked</c:if>>
										<label for="chkSite99999" class="ip_chk_lb">공통</label>
									</span>
								</li>
							</ul>
						</td>
						
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 유형</span>
						</th>
						<td class="input">
							<select id="emailTypeCode" name="emailTypeCode" class="ui-sel" title="유형 선택">
								<option value="">선택</option>
								<c:forEach var="emailTypeCode" items="${emailTypeCodeList}">
									<option value="${emailTypeCode.codeDtlNo}"<c:if test="${cmEmailTemplate.emailTypeCode eq emailTypeCode.codeDtlNo}"> selected</c:if>>${emailTypeCode.codeDtlName}</option>
								</c:forEach>
							</select>
						</td>
						<th scope="row">
							<span class="th-required">템플릿 명</span>
						</th>
						<td class="input">
							<input type="text" id="emailTmplName" name="emailTmplName" value="${cmEmailTemplate.emailTmplName}" maxlength="100" class="ui-input" title="템플릿명 입력">
						</td>
						
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">메일제목</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" id="emailTitleText" name="emailTitleText" value="${cmEmailTemplate.emailTitleText}" maxlength="100" class="ui-input" title="메일제목 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">내용</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea rows="10" cols="100" name="emailContText" id="emailContText" class="w100" title="내용 입력">${cmEmailTemplate.emailContText}</textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span>수정자</span>
						</th>
						<td>
							<a href="javascript:void(0)" id="moderId" name="${cmEmailTemplate.moderNo}" style="text-decoration: underline;"><c:out value="${cmEmailTemplate.dispModerName}"/></a>
						</td>
						<th scope="row">수정일시</th>
						<td><fmt:formatDate value="${cmEmailTemplate.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->
			<c:if test="${cmEmailTemplate.emailTmplSeq ne null }">
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<!-- S : 20190228 추가 // 삭제 버튼 추가 -->
					<div class="fl">
						<a href="javascript:void(0)" id="deleteBtn" class="btn-normal btn-del">삭제</a>
					</div>
					<!-- E : 20190228 추가 // 삭제 버튼 추가 -->
				</div>
				<!-- E : tbl-desc-wrap -->
			</c:if>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-link">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/cmm/abcmart.cmm.email.manual.template.js<%=_DP_REV%>"></script>

</body>
</html>