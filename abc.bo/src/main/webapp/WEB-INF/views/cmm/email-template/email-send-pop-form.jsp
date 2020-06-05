<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {

			<%-- CKEDITOR 셋팅--%>
			CKEDITOR.replace("emailContText", {
				width:"100%",
				height:"100%"
			});

			$("input:radio[name=siteNo]").off().on("click", function(f) {
				abc.biz.cmm.msgtemplate.doActionEmailSend("siteClick");
			});

			$("#emailTypeCode").off().on("change", function(f) {
				abc.biz.cmm.msgtemplate.doActionEmailSend("emailTypeChange");
			});

			$("#emailTmplSeq").off().on("change", function(f) {
				abc.biz.cmm.msgtemplate.doActionEmailSend("emailTmplSeqChange");
			});

			$("#sendMailBtn").off().on("click", function(f) {
				abc.biz.cmm.msgtemplate.doActionEmailSend("emailSend");
			});
			
			$("#resetBtn").off().on('click', function(){
				$.form("#frmEmailSend").reset();
				CKEDITOR.instances.emailContText.setData("");
			});
			abc.biz.cmm.msgtemplate.doActionEmailSend("siteClick");

		});

		</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>메일보내기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fr">
					<div class="btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="javascript:void(0);" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="frmEmailSend" name="frmEmailSend">
			<input type="hidden" name="rcvrName" value="${commonEmailPopupVo.rcvrName}"/>
			<input type="hidden" name="rcvrMemberNo" value="${commonEmailPopupVo.rcvrMemberNo}"/>
			<input type="hidden" name="checkedSiteNo" value=""/>
			<table class="tbl-row">
				<caption>메일보내기</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사이트</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
						<c:forEach var="site" items="${siteList}">
							<c:if test="${null != commonEmailPopupVo.siteNo and site.siteNo eq commonEmailPopupVo.siteNo}">
								<li>
									<span class="ui-rdo">
										<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}" checked>
										<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
									</span>
								</li>
							</c:if>
							<c:if test="${null == commonEmailPopupVo.siteNo}">
								<li>
									<span class="ui-rdo">
										<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}" <c:if test="${site.siteNo eq '10000'}">checked</c:if> >
										<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
									</span>
								</li>
							</c:if>
						</c:forEach>
								<li>
									<span class="ui-rdo">
										<input id="chkSite99999" name="siteNo" class="ip_chk" type="radio" value="99999">
										<label for="chkSite99999" class="ip_chk_lb">공통</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">템플릿 유형</th>
						<td class="input">
							<span class="ip-text-box">
								<select id="emailTypeCode" name="emailTypeCode" class="ui-sel" title="메일 유형 선택">
									<option value="">선택</option>
								<c:forEach var="emailTypeCode" items="${emailTypeCodeList}">
									<option value="${emailTypeCode.codeDtlNo}">${emailTypeCode.codeDtlName}</option>
								</c:forEach>
									</select>
								<select id="emailTmplSeq" name="emailTmplSeq" class="ui-sel" style="width:150px;" title="메일 템플릿명 선택">
									<option value="">선택</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">받는 메일 주소</span>
						</th>
						<td class="input">
							<input type="text" id="rcvrEmailAddrText" name="rcvrEmailAddrText" value="${commonEmailPopupVo.rcvrEmailAddrText}" class="ui-input" title="메일 주소 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">메일 제목</span>
						</th>
						<td class="input">
							<input type="text" id="emailTitleText" name="emailTitleText" class="ui-input" title="메일제목 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 내용</span>
						</th>
						<td class="input">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea rows="10" cols="100" name="emailContText" id="emailContText" class="w100" title="내용 입력"></textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" id="sendMailBtn" class="btn-normal btn-link">메일 발송</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/cmm/abcmart.cmm.msgtemplate.js<%=_DP_REV%>"></script>
</body>
</html>