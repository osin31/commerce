<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
		<script type="text/javascript">
		$(function() {
			$("input:radio[name=siteNo]").off().on("click", function(f) {
				abc.biz.cmm.msgtemplate.doActionTextMesgSend("siteClick");
			});

			$("#mesgTypeCode").off().on("change", function(f) {
				abc.biz.cmm.msgtemplate.doActionTextMesgSend("mesgTypeChange");
			});

			$("#mesgTmplSeq").off().on("change", function(f) {
				abc.biz.cmm.msgtemplate.doActionTextMesgSend("mesgTmplSeqChange");
			});

			$("#mesgContText").off().on("keyup change", function(f) {
				var sendTypeCode = $("#sendTypeCode").val();
				var str = $("#mesgContText").val();
				abc.biz.cmm.msgtemplate.contTextLengthCheck(sendTypeCode, str);
			});

			$("#sendTextMsgBtn").off().on("click", function(f) {
				abc.biz.cmm.msgtemplate.doActionTextMesgSend("sendTextMsg");
			});
			//숫자만 입력 가능
			$("#sendTelNoText").off().on('input', function(event){
				abc.text.validateOnlyNumber(this);
			});
			$("#recvTelNoText").off().on('input', function(event){
				abc.text.validateOnlyNumber(this);
			});
			
			$("#resetBtn").off().on('click', function(){
				$.form("#frmEmailSend").reset();
				abc.biz.cmm.msgtemplate.doActionTextMesgSend("siteClick");
			});
			
			abc.biz.cmm.msgtemplate.doActionTextMesgSend("siteClick");
			
			if(!abc.text.allNull(abc.param.getParams().siteNo)){
				var siteNo = abc.param.getParams().siteNo;
				if(siteNo == "10000"){
					$("#mesgTitleText").val("[A-RT]");
					$("#sendTelNoText").val("15889667");
				}else{
					$("#mesgTitleText").val("[On the spot]");
					$("#sendTelNoText").val("16440136");
				}
			}else{
				$("#sendTelNoText").val("15889667");
				$("input[name=siteNo]").last().prop("checked", true);
			}
		});
		</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>SMS보내기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fr">
					<div class="btn-group">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<form id="frmEmailSend" name="frmEmailSend">
			<input type="hidden" name="rcvrName" value="${textMsgVo.rcvrName}"/>
			<input type="hidden" name="memberNo" value="${textMsgVo.memberNo}"/>
			<input type="hidden" name="sendTypeCode" id="sendTypeCode" value=""/>
			<input type="hidden" name="checkedSiteNo" value=""/>
			<input type="hidden" name="checkedSendTypeCode" value=""/>
			<table class="tbl-row">
				<caption>SMS보내기</caption>
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
							<c:if test="${null != textMsgVo.siteNo and site.siteNo eq textMsgVo.siteNo}">
								<li>
									<span class="ui-rdo">
										<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}" checked>
										<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
									</span>
								</li>
							</c:if>
							<c:if test="${null == textMsgVo.siteNo}">
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
								<select id="mesgTypeCode" name="mesgTypeCode" class="ui-sel" title="템플릿 유형 선택">
									<option value="">템플릿 유형 선택</option>
								<c:forEach var="mesgTypeCode" items="${mesgTypeCodeList}">
									<option value="${mesgTypeCode.codeDtlNo}">${mesgTypeCode.codeDtlName}</option>
								</c:forEach>
								</select>
								<select id="mesgTmplSeq" name="mesgTmplSeq" class="ui-sel" style="width:150px;" title="메일 템플릿명 선택">
									<option value="">선택</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">받는 휴대폰 번호</span>
						</th>
						<td class="input">
							<input type="text" id="recvTelNoText" name="recvTelNoText" value="${textMsgVo.recvTelNoText}" class="ui-input" title="휴대폰 번호 입력" placeholder="- 없이 입력" maxlength="12">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">보내는 번호</span>
						</th>
						<td class="input">
							<input type="text" id="sendTelNoText" name="sendTelNoText" class="ui-input" value="15889667" title="휴대폰 번호 입력" placeholder="- 없이 입력" maxlength="12">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span>문자 제목</span>
						</th>
						<td class="input">
<!-- 								<span class="text" id="titleTextArea">[A-RT]</span> -->
							<input type="text" id="mesgTitleText" name="mesgTitleText" class="ui-input" value="" title="문자 제목 입력" readonly>
							<span class="text" style="color: red">* LMS 전환 시 제목입력이 가능합니다.</span>
							
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">템플릿 내용</span>
						</th>
						<td class="input">
							<!-- S : msg-wrap -->
							<span class="msg-wrap">
								<span class="msg-box">
									<textarea id="mesgContText" name="mesgContText" class="ui-textarea" title="템플릿 내용 입력"></textarea>
									<span class="text-limit">
										<span class="desc">(</span>
										<input type="text" id="mesgContTextByteLength" name="mesgContTextByteLength" class="ui-input num-unit100" title="입력 글자 Byte">

										<!-- DESC : SMS선택시 공백포함 80 byte, LMS선택시 공백포함 500 byte -->
										<span class="desc">Byte/<span id="mesgContTextMaxByte" class="text">80</span> Byte)</span>
									</span>
								</span>
							</span>
							<!-- E : msg-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="javascript:void(0)" id="sendTextMsgBtn" class="btn-normal btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
<script src="/static/common/js/biz/cmm/abcmart.cmm.msgtemplate.js<%=_DP_REV%>"></script>
</body>
</html>
