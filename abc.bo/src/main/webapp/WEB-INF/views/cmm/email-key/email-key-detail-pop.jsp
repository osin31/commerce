<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {
			$("#saveBtn").click(function(f) {
				abc.biz.cmm.msgtemplate.doActionEmailKey("save");
			});
			
			<%-- CKEDITOR 셋팅--%>
			CKEDITOR.replace("emailContText", {
				width:"100%",
			});
			
			//작성자 수정자 클릭시 관리자 상세 호출
			$("#adminPopTag").off().on('click', function(){
				abc.adminDetailPopup($(this).data("admin"));
			});
			
			if(abc.text.allNull($("#emailTmplSeq").val())){
				$("#chkSite10000").attr("checked", true);
			}
			
			$("#previewBtn").off().on('click', function() {
				abc.open.popup({
					url 	:	"/cmm/msg/email-key/read-preview-pop",
					winname :	"emailKeyPreviewPop",
					method	: 	"post",
					title 	:	"emailKeyPrivew",
					width 	:	1200,
					height	:	900,
					params	: 	{emailContText : CKEDITOR.instances.emailContText.getData()}
				});
			});
			
		});
		</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>광고 이메일</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">광고 이메일 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="frmEmailKey" name="frmEmailKey">
			<input type="hidden" name="emailTmplSeq" id="emailTmplSeq" value="${cmEmailTemplate.emailTmplSeq}"/>
			<input type="hidden" name="emailKeyText" value="${cmEmailTemplate.emailKeyText}"/>
			<table class="tbl-row">
				<caption>광고 이메일 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사이트</span>
						</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
						<c:forEach var="site" items="${siteList}">
								<li>
									<span class="ui-rdo">
										<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}"<c:if test="${site.siteNo eq cmEmailTemplate.siteNo}"> checked</c:if>>
										<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
									</span>
								</li>
						</c:forEach>
							</ul>
						</td>
					</tr>
					<%-- <tr>
						<th scope="row">메일 키 정보</th>
						<td colspan="1">${cmEmailTemplate.emailKeyText}</td>
						<td colspan="2">※메일키는 최초등록시 자동생성됩니다.</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">메일키 URL</span>
						</th>
						<td colspan="3">
							<span>서비스URL?key=${cmEmailTemplate.emailKeyText}</span>
						</td>
					</tr> --%>
					<tr>
						<th scope="row">
							<span class="th-required">메일제목</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" id="emailTitleText" name="emailTitleText" value="${cmEmailTemplate.emailTitleText}" maxlength="50" class="ui-input" title="메일제목 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">발송일시</span>
						</th>
						<td class="input" colspan="3">
						<span class="term-date-wrap">
							<span class="date-box">
							<input type="text" id="sendDay" name="sendDay" value="${cmEmailTemplate.sendDay}" data-role="datepicker" class="ui-cal js-ui-cal" title="발송일 선택">
							</span>
							<select name="sendHour" id="sendHour" class="ui-sel" title="시작시  선택">
								<option value="">선택</option>
							<c:forEach var="idx" begin="0" end="23" step="1">
								<option value="<fmt:formatNumber pattern="00" value="${idx}" />"<c:if test="${idx eq cmEmailTemplate.sendHour}"> selected</c:if>><fmt:formatNumber pattern="00" value="${idx}" />시</option>
							</c:forEach>
							</select>
							<select name="sendMinute" id="sendMinute" class="ui-sel" title="시작분 선택">
								<option value="">선택</option>
							<c:forEach var="idx" begin="00" end="50" step="10">
								<option value="<fmt:formatNumber pattern="00" value="${idx}" />"<c:if test="${idx eq cmEmailTemplate.sendMinute}"> selected</c:if>><fmt:formatNumber pattern="00" value="${idx}" />분</option>
							</c:forEach>
							</select>
						</span>
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
							<span>수정자 </span>
						</th>
						<td>
							<c:if test="${not empty cmEmailTemplate.dispModerName}">
								<a href="javascript:void(0)" id="adminPopTag" data-admin="${cmEmailTemplate.moderNo}" style="text-decoration: underline;"><c:out value="${cmEmailTemplate.dispModerName}"/></a>
							</c:if>
						</td>
						<th scope="row">수정일시</th>
						<td><fmt:formatDate value="${cmEmailTemplate.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="previewBtn" class="btn-normal btn-link">미리보기</a>
				<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-link">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/cmm/abcmart.cmm.msgtemplate.js<%=_DP_REV%>">
</script>
</body>
</html>