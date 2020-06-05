<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {
			
			$("#saveBtn").click(function(f) {
				abc.biz.cmm.textmsg.manual.template.doActionTextMsgTemplate("save");
			});

			$("#deleteBtn").click(function(f) {
				abc.biz.cmm.textmsg.manual.template.doActionTextMsgTemplate("delete");
			});

			$("#mesgContText").off().on("keyup change", function(f) {
				var sendTypeCode = $("#sendTypeCode").val();
				var str = $("#mesgContText").val();
				abc.biz.cmm.textmsg.manual.template.conTextByteLengthCheck(str);
			});
			
			//작성자 수정자 클릭시 관리자 상세 호출
			$("#adminPop").off().on('click', function(){
				abc.adminDetailPopup($(this).data("admin"));
			});

			$("#mesgContText").trigger("keyup");
		});
		</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>문자 템플릿</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">문자 템플릿 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="frmTextMsgTemplate" name="frmTextMsgTemplate">
			<input type="hidden" name="mesgTmplSeq"  id="mesgTmplSeq"  value="${cmMessageTemplate.mesgTmplSeq}"/>
			<input type="hidden" name="sendTypeCode" id="sendTypeCode" value="${CommonCode.SEND_TYPE_CODE_SMS}"/>

			<table class="tbl-row">
				<caption>문자 템플릿 정보</caption>
				<colgroup>
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
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 유형</span>
						</th>
						<td class="input" colspan="3">
							<select id="mesgTypeCode" name="mesgTypeCode" class="ui-sel" title="유형 선택">
								<option value="">선택</option>
								<c:forEach var="mesgTypeCode" items="${mesgTypeCodeList}">
									<option value="${mesgTypeCode.codeDtlNo}"<c:if test="${cmMessageTemplate.mesgTypeCode eq mesgTypeCode.codeDtlNo}"> selected</c:if>>${mesgTypeCode.codeDtlName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 명</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" id="mesgTmplName" name="mesgTmplName" value="${cmMessageTemplate.mesgTmplName}" maxlength="100" class="ui-input" title="템플릿명 입력">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">내용</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : msg-wrap -->
							<span class="msg-wrap">
								<span class="msg-box template-box">
									<textarea id="mesgContText" name="mesgContText" class="ui-textarea" title="템플릿 내용 입력" maxlength="2000">${cmMessageTemplate.mesgContText}</textarea>
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
							<!-- E : msg-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span>수정자</span>
						</th>
						<td><a href="javascript:void(0)" id="adminPop" data-admin="${cmMessageTemplate.moderNo}" style="text-decoration: underline;"><c:out value="${cmMessageTemplate.dispModerName}"/></a></td>
						<th scope="row">수정일시</th>
						<td><fmt:formatDate value="${cmMessageTemplate.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

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
<script src="/static/common/js/biz/cmm/abcmart.cmm.textmsg.manual.template.js<%=_DP_REV%>">
</script>
</body>
</html>