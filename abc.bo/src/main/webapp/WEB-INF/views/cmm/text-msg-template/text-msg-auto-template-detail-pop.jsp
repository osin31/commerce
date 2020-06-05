<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {
			
			abc.biz.cmm.textmsg.auto.template.failConTextByteLengthCheck($("#failMesgContText").val());
			abc.biz.cmm.textmsg.auto.template.kkoLengthCheck($("#mesgContText").val());
		
			$("#saveBtn").click(function(f) {
				abc.biz.cmm.textmsg.auto.template.doActionTextMsgTemplate("save");
			});

			$("#deleteBtn").click(function(f) {
				abc.biz.cmm.textmsg.auto.template.doActionTextMsgTemplate("delete");
			});
			
			$("#doubleCheckBtn").click(function(f) {
				if($("#originMesgId").val() === $("#mesgId").val()) {
					alert("사용 가능한 코드입니다.");
					return false;
				}
				abc.biz.cmm.textmsg.auto.template.doActionTextMsgTemplate("doubleCheck");
			});

			$("#failMesgContText").off().on("input", function(f) {
				abc.biz.cmm.textmsg.auto.template.failConTextByteLengthCheck($("#failMesgContText").val());
			});
			
			$("#mesgContText").off().on("input", function(f) {
				abc.biz.cmm.textmsg.auto.template.kkoLengthCheck($("#mesgContText").val());
			});
			
			$("#adminPop").off().on('click', function(){
				abc.adminDetailPopup($(this).data("admin"));
			});
			
		});
		</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>자동 발송 문자 템플릿</h1><!-- 190411 수정 | 화면명 수정 -->
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">자동 발송 문자 템플릿 정보</h3><!-- 190411 수정 | 화면명 수정 -->
				</div>
			</div>
			<!-- E : content-header -->

			<!-- s : 190411 수정 | 문자 템플릿 정보 테이블 수정 -->
			<!-- S : tbl-row -->
			<form id="frmTextMsgTemplate" name="frmTextMsgTemplate">
				<input type="hidden" name="mesgTmplSeq"  id="mesgTmplSeq"  value="${cmMessageTemplate.mesgTmplSeq}"/>
				<input type="hidden" name="sendTypeCode" id="sendTypeCode" value="${CommonCode.SEND_TYPE_CODE_SMS}"/>
				<input type="hidden" id="originMesgId" value="${cmMessageTemplate.mesgId}"/>
			<table class="tbl-row">
				<caption>자동 발송 문자 템플릿 정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사이트 구분</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<c:forEach var="site" items="${siteList}">
								<li>
									<span class="ui-rdo">
									<c:choose>
											<c:when test="${cmMessageTemplate.siteNo eq null}">
												<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}"<c:if test="${site.siteNo eq 10000}"> checked</c:if>>
												<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
											</c:when>
											<c:otherwise>
												<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}"<c:if test="${site.siteNo eq cmMessageTemplate.siteNo}"> checked</c:if>>
												<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
						</c:forEach>
								<li>
									<span class="ui-rdo">
										<input id="chkSite99999" name="siteNo" class="ip_chk" type="radio" value="99999"<c:if test="${cmMessageTemplate.siteNo eq '99999'}"> checked</c:if>>
										<label for="chkSite99999" class="ip_chk_lb">공통</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<th scope="row">
							<span class="th-required">발송코드</span>
						</th>
						<td class="input">
							<span class="ip-text-box">
								<c:choose>
									<c:when test="${cmMessageTemplate.mesgTmplSeq ne null }">
										<input type="text" id="mesgId" name="mesgId" class="ui-input" value="${cmMessageTemplate.mesgId}" title="발송코드 입력" maxlength="10">
										<a href="javascript:void(0)" id="doubleCheckBtn" class="btn-sm btn-func">중복확인</a>
									</c:when>
									<c:otherwise>
										<input type="text" id="mesgId" name="mesgId" class="ui-input" title="발송코드 입력" maxlength="10">
										<a href="javascript:void(0)" id="doubleCheckBtn" class="btn-sm btn-func">중복확인</a>	
									</c:otherwise>
								</c:choose>
							</span>	
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 유형</span>
						</th>
						<td class="input">
							<select name="mesgTypeCode" id="mesgTypeCode" class="ui-sel inline" title="템플릿 유형 선택">
								<option value="">선택</option>
								<c:forEach var="mesgTypeCode" items="${mesgTypeCodeList}">
									<option value="${mesgTypeCode.codeDtlNo}" <c:if test="${cmMessageTemplate.mesgTypeCode eq mesgTypeCode.codeDtlNo}"> selected</c:if>>${mesgTypeCode.codeDtlName}</option>
								</c:forEach>
							</select>
						</td>
						<th scope="row">
							<span class="th-required">템플릿 명</span>
						</th>
						<td class="input">
							<input type="text" id="mesgTmplName" name="mesgTmplName" value="<c:out value="${cmMessageTemplate.mesgTmplName}" escapeXml="false"/>" maxlength="100" class="ui-input" title="템플릿명 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">알림톡  메시지<br />(KKO) 내용</span>
						</th>
						<td class="input" colspan="3">
							<span class="msg-wrap">
								<span class="msg-box template-box">
									<textarea class="ui-textarea" id="mesgContText" name="mesgContText" title="템플릿 내용 입력" maxlength="2000"><c:out value="${cmMessageTemplate.mesgContText}" escapeXml="false"/></textarea>
									<span class="text-limit-box">
										<span class="text-limit">
											<span class="value">(<span class="bytes" id="contTextByte">0</span> Byte/2000 Byte)</span>
										</span>
									</span>
									<span class="warning-box">
										<span id="kkoMessage">※ 2000Byte 이내로 작성바랍니다.</span>
									</span>
								</span>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">페일백 메시지<br />(SMS / LMS) 내용</span>
						</th>
						<td class="input" colspan="3">
							<span class="msg-wrap">
								<span class="msg-box template-box">
									<textarea id="failMesgContText" name="failMesgContText" class="ui-textarea" title="템플릿 내용 입력" maxlength="2000">${cmMessageTemplate.failMesgContText}</textarea>
									<span class="text-limit-box">
										<span class="text-limit">
											<span class="name">SMS</span>
											<span class="value">(<span class="bytes" id="smsContTextByte">0</span> Byte/80 Byte)</span>
										</span>
										<span class="text-limit">
											<span class="name">LMS</span>
											<span class="value">(<span class="bytes" id="lmsContTextByte">0</span> Byte/2000 Byte)</span>
										</span>
									</span>
									<span class="warning-box">
										<span id="smsMessage" style="display:none">※ 80 Byte 초과 시 장문메시지로 발송됩니다</span>
										<span id="lmsMesage" style="display:none">※ 2000Byte 이내로 작성바랍니다.</span>
									</span>
								</span>
							</span>
						</td>
					</tr>
					<!-- s : 등록 화면인 경우 미노출 -->
					<tr>
						<th scope="row">
							<span>수정자</span>
						</th>
						<td>
							<a href="javascript:void(0)" id="adminPop" data-admin="${cmMessageTemplate.moderNo}" style="text-decoration: underline;"><c:out value="${cmMessageTemplate.dispModerName}"/></a>
						</td>
						<th scope="row">
							<span>수정일시</span>
						</th>
						<td>
							<fmt:formatDate value="${cmMessageTemplate.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
						</td>
					</tr>
					<!-- e : 등록 화면인 경우 미노출 -->
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->
			<!-- e : 190411 수정 | 문자 템플릿 정보 테이블 수정 -->

			<!-- S : 20190228 추가 // 삭제 버튼 추가 -->
			<!-- S : tbl-desc-wrap -->
			<c:if test="${cmMessageTemplate.mesgTmplSeq != null && cmMessageTemplate.mesgTmplSeq > 0}">
			<div class="tbl-desc-wrap">
				<div class="fl">
					<a href="javascript:void(0)" id="deleteBtn" class="btn-normal btn-del">삭제</a>
				</div>
			</div>
			</c:if>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 20190228 추가 // 삭제 버튼 추가 -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-link">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/cmm/abcmart.cmm.textmsg.auto.template.js<%=_DP_REV%>">
</script>
</body>
</html>