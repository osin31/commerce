<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
		<script type="text/javascript">
		var _ASWR_ALERT_TYPE_SMS = "M"; // 답변알림 유형 문자 TYPE
		var _ASWR_ALERT_TYPE_MAIL = "E"; // 답변알림 유형 메일 TYPE
		var _ASWR_ALERT_TYPE_NON = "N"; // 답변알림 유형 발송안함 TYPE

		$(function() {
			//초기화버튼
			$("#resetBtn").click(function(f) {
				$.form("#frmCounselScript").reset();
				var cnslGbnCode = $("#cnslGbnCode option:selected");	// 상담메뉴 코드
				if (cnslGbnCode.val() == "${CommonCode.CNSL_GNB_CODE_PRODUCT_REVIEW}") {
					$("#spanPointPymnt").show();
				} else {
					$("#spanPointPymnt").hide();
				}
			});
			
			//목록 버튼
			$("#listBtn").click(function(f) {
				frmSearch.action = "/cmm/counselscript";
				frmSearch.submit();
			});
			
			// 저장/수정 버튼 클릭
			$("#saveBtn").click(function(f) {
				abc.biz.cmm.counselscript.doActionCounselScript("save");
			});
			
			if(abc.text.isBlank($("#cnslScriptSeq").val())) {
				$("#rgsterTd").text("");
				$("#moderTd").text("");
			}
			// 상담메뉴 코드 변경
			$("#cnslGbnCode").change(function(f) {
				var cnslGbnCode = $("#cnslGbnCode option:selected");		// 상담메뉴 코드
				abc.setLowerCodeList("CNSL_TYPE_CODE", cnslGbnCode.val(), "cnslTypeCode");
				if (cnslGbnCode.val() == "${CommonCode.CNSL_GNB_CODE_PRODUCT_REVIEW}") {
					$("#spanPointPymnt").show();
				} else {
					$("#spanPointPymnt").hide();
				}

				$("#cnslTypeCode").trigger("change");
			});

			// 상담 구분 코드 변경
			$("#cnslTypeCode").change(function(f) {
				var cnslTypeCode = $("#cnslTypeCode option:selected");		// 상담유형코드
				abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
			});

			// 고객알림 설정 변경
			$("#aswrAlertType").change(function(f) {
				var aswrAlertType = $("#aswrAlertType option:selected");	// 고객알림 설정 Type
				var aswrAlertId = $("#aswrAlertId");

				//console.log("aswrAlertType value :" + aswrAlertType.val());
				//console.log("smsAswrAlertIdHidden value :" + $("#smsAswrAlertIdHidden").val());
				//console.log("mailAswrAlertIdHidden value :" + $("#mailAswrAlertIdHidden").val());
				if (aswrAlertType.val() == _ASWR_ALERT_TYPE_SMS) {
					aswrAlertId.show();
					aswrAlertId.val($("#smsAswrAlertIdHidden").val());
					aswrAlertId.attr("placeholder", "문자 템플릿 ID를 입력하세요.");
				} else if (aswrAlertType.val() == _ASWR_ALERT_TYPE_MAIL) {
					aswrAlertId.show();
					aswrAlertId.val($("#mailAswrAlertIdHidden").val());
					aswrAlertId.attr("placeholder", "이메일 템플릿 ID를 입력하세요.");
				} else {
					aswrAlertId.hide();
				}

			});
			
			//작성자 수정자 클릭시 관리자 상세 호출
			$(".adminPopTag").off().on('click', function(){
				abc.adminDetailPopup($(this).data("admin"));
			});

			$("#aswrAlertId").keyup(function() {
				$("#aswrAlertIdKeyInYN").val("Y");
				if ($("#aswrAlertType option:selected").val() == _ASWR_ALERT_TYPE_SMS) {
					$("#smsAswrAlertIdHidden").val($("#aswrAlertId").val());
				} else  if ($("#aswrAlertType option:selected").val() == _ASWR_ALERT_TYPE_MAIL) {
					$("#mailAswrAlertIdHidden").val($("#aswrAlertId").val());
				}
			});

			$("#aswrAlertType").trigger("change");

			$("#aswrAlertId").val("${cmCounselScript.aswrAlertId}");
		});
		</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상담스크립트관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>고객응대관리</li>
								<li>상담스크립트관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상담스크립트 <c:if test="${cmCounselScript.cnslScriptSeq eq null}">등록</c:if><c:if test="${cmCounselScript.cnslScriptSeq ne null}">상세</c:if></h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="#" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<form id="frmCounselScript" name="frmCounselScript">
				<input type="hidden" id="cnslScriptSeq" name="cnslScriptSeq" value="${cmCounselScript.cnslScriptSeq}"/>
				<input type="hidden" id="aswrAlertIdKeyInYN" value="N"/>
				<input type="hidden" id="mailAswrAlertIdHidden" value="<c:if test="${cmCounselScript.aswrAlertType eq CommonCode.ASWR_ALERT_TYPE_EMAIL}">${cmCounselScript.aswrAlertId}</c:if>"/>
				<input type="hidden" id="smsAswrAlertIdHidden" value="<c:if test="${cmCounselScript.aswrAlertType eq CommonCode.ASWR_ALERT_TYPE_SMS}">${cmCounselScript.aswrAlertId}</c:if>"/>
				<table class="tbl-row">
					<caption>상담스크립트 등록</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">상담유형</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="cnslGbnCode" name="cnslGbnCode" class="ui-sel" style="width:150px;" title="메뉴 선택"<c:if test="${cmCounselScript.cnslScriptSeq ne null and cmCounselScript.cnslScriptSeq ne ''}"> disabled</c:if>>
										<option value="">선택</option>
										<c:forEach var="cnslGbnCode" items="${cnslGbnCode}">
											<c:if test="${cnslGbnCode.addInfo1 eq Const.BOOLEAN_TRUE}">
												<option value="${cnslGbnCode.codeDtlNo}"<c:if test="${cnslGbnCode.codeDtlNo eq cmCounselScript.cnslGbnCode}"> selected</c:if>>${cnslGbnCode.codeDtlName}</option>
											</c:if>
										</c:forEach>
									</select>
									<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" style="width:150px;" title="상담유형 대분류 선택"<c:if test="${cmCounselScript.cnslScriptSeq ne null and cmCounselScript.cnslScriptSeq ne ''}"> disabled</c:if>>
										<option value="">선택</option>
										<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}"<c:if test="${cnslTypeCode.codeDtlNo eq cmCounselScript.cnslTypeCode}"> selected</c:if>>${cnslTypeCode.codeDtlName}</option>
										</c:forEach>
									</select>
									<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" style="width:150px;" title="상담유형 소분류 선택"<c:if test="${cmCounselScript.cnslScriptSeq ne null and cmCounselScript.cnslScriptSeq ne ''}"> disabled</c:if>>
										<option value="">선택</option>
										<c:forEach var="cnslTypeDtlCode" items="${cnslTypeDtlCode}">
											<option value="${cnslTypeDtlCode.codeDtlNo}"<c:if test="${cnslTypeDtlCode.codeDtlNo eq cmCounselScript.cnslTypeDtlCode}"> selected</c:if>>${cnslTypeDtlCode.codeDtlName}</option>
										</c:forEach>
									</select>
									<span class="ui-chk" id="spanPointPymnt" style="<c:if test="${cmCounselScript.cnslGbnCode ne CommonCode.CNSL_GNB_CODE_PRODUCT_REVIEW}">display:none;</c:if>">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="pointPayYn" name="pointPayYn" value="Y" type="checkbox"<c:if test="${cmCounselScript.pointPayYn eq 'Y'}"> checked</c:if>>
										<label for="pointPayYn">포인트지급</label>
									</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">스크립트제목</span>
							</th>
							<td colspan="3" class="input">
								<input type="text" name="cnslScriptTitleText" id="cnslScriptTitleText" value="${cmCounselScript.cnslScriptTitleText}" class="ui-input" title="제목 입력" maxlength="50">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">내용</span>
							</th>
							<td colspan="3" class="input">
								<textarea name="cnslScriptContText" id="cnslScriptContText" class="ui-textarea" title="내용 입력" maxlength="2000">${cmCounselScript.cnslScriptContText}</textarea>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">고객알림설정</span>
							</th>
							<td class="input" colspan="3">
								<span class="ip-text-box">
									<select id="aswrAlertType" name="aswrAlertType" class="ui-sel" style="width:150px;" title="알림유형 선택">
										<option value="N"<c:if test="${cmCounselScript.aswrAlertType eq CommonCode.ASWR_ALERT_TYPE_NON}"> selected</c:if>>발송안함</option>
										<option value="M"<c:if test="${cmCounselScript.aswrAlertType eq CommonCode.ASWR_ALERT_TYPE_SMS}"> selected</c:if>>SMS발송</option>
										<option value="E"<c:if test="${cmCounselScript.aswrAlertType eq CommonCode.ASWR_ALERT_TYPE_EMAIL}"> selected</c:if>>이메일발송</option>
									</select>
									<input type="text" name="aswrAlertId" id="aswrAlertId" value="${cmCounselScript.aswrAlertId}" class="ui-input" title="제목 입력" placeholder="" style="display:none;width: 200px;" maxlength="10">
								</span>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input" colspan="3">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useY" name="useYn" type="radio" value="Y"<c:if test="${cmCounselScript.useYn eq 'Y' or cmCounselScript.useYn eq null}"> checked</c:if>>
											<label for="useY">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useN" name="useYn" type="radio" value="N"<c:if test="${cmCounselScript.useYn eq 'N'}"> checked</c:if>>
											<label for="useN">사용안함</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">작성자</th>
							<td id="rgsterTd"><a href="javascript:void(0)" class="adminPopTag" data-admin="${cmCounselScript.rgsterNo}" style="text-decoration: underline;"><c:out value="${cmCounselScript.authRgsterMasking}"/></a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${cmCounselScript.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td id="moderTd"><a href="javascript:void(0)" class="adminPopTag" data-admin="${cmCounselScript.moderNo}" style="text-decoration: underline;"><c:out value="${cmCounselScript.authModerMasking}"/></a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${cmCounselScript.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				</form>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fr">
						<a href="javascript:void(0);" class="btn-normal btn-link" id="listBtn">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" class="btn-lg btn-save" id="saveBtn">저장</a>
				</div>
				<!-- E : content-bottom -->

				<form id="frmSearch" name="frmSearch" method="get">
				<input type="hidden" name="menuNo" 			value="${param.menuNo}"/>
				<input type="hidden" name="cnslScriptSeq" 	value="${param.cnslScriptSeq}"/>
				<input type="hidden" name="cnslGbnCode" 	value="${param.cnslGbnCode}"/>
				<input type="hidden" name="cnslTypeCode" 	value="${param.cnslTypeCode}"/>
				<input type="hidden" name="cnslTypeDtlCode"	value="${param.cnslTypeDtlCode}"/>
				<input type="hidden" name="useYn" 			value="${param.useYn}"/>
				<input type="hidden" name="searchKey" 		value="${param.searchKey}"/>
				<input type="hidden" name="searchValue" 	value="${param.searchValue}"/>
				<input type="hidden" name="pageCount" 		value="${param.pageCount}"/>
				<input type="hidden" name="listPageNum" 	value="${param.listPageNum}"/>
				</form>

			</div>
		</div>
		<!-- E : container -->

<script src="/static/common/js/biz/cmm/abcmart.cmm.counselscript.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>