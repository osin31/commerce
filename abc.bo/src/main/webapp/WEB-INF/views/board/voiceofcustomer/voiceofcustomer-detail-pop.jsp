<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		//상담유형 상세코드 변경 및 답변상태에 따라 답변보류 영역 조정
		abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();

		// 상담유형 코드 변경
		$("#aswrCnslTypeCode").off().on('change', function(f) {
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", "${cnslTypeCode.get(0).getCodeDtlNo()}", "aswrCnslTypeDtlCode");
			abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
		});

		//상담스크립트 제목 조회
		$("#aswrCnslTypeDtlCode").off().on('change', function(f) {
			var _cnslTypeCode = $("#aswrCnslTypeCode").val();		// 상담유형코드
			var _cnslTypeDtlCode = $("#aswrCnslTypeDtlCode").val();	// 상담분류코드

			abc.getCounselScriptTitle(_cnslTypeCode, _cnslTypeDtlCode, "cnslScriptTitleText");
		});

		// 답변 내용 조회
		$("#cnslScriptTitleText").off().on('change', function(f) {
			var _cnslScriptSeq = $("#cnslScriptTitleText").val();	// 상담분류코드

			abc.getCounselScript(_cnslScriptSeq, "aswrContText");
		});
		
		//회원정보 클릭시 회원상세
		$("#memberPop").click(function(){
			var memberNo = $(this).attr("value");
			abc.memberDetailPopup(memberNo);
		});

		// 저장 버튼
		$("#saveBtn").off().on('click', function() {
			if($("#leaveYn").val() == abc.consts.BOOLEAN_FALSE){
				abc.biz.board.inquiry.doActionCounselScript("voiceOfCustomerSave");
			}else{
				abc.biz.board.inquiry.doActionCounselScript("saveVocLeave");
			}
		});

		// 관리자 메모 저장 버튼
		$("#memoSaveBtn").off().on('click', function() {
			if(!(abc.text.allNull($("#adminMemoText").val()))){
				if(confirm("저장하시겠습니까?")){
					abc.biz.board.inquiry.doActionCounselScript("memoSave");
				}
			}else{
				alert("관리자 메모를 입력해주세요");
				$("#adminMemoText").focus();
			}
		});

		//관리자메모 삭제 버튼
		$(document).on("click", ".btn-msg-list-del", function(event){
			var rgsterNo = $(this).attr('rgsterno');
			if($("#adminNo").val() == rgsterNo){
				if(confirm("삭제 하시겠습니까?")){
					var cnslMemoSeq = $(this).attr("value");
					var memberCnslSeq = $("#memberCnslSeq").val();
					
	 				abc.biz.board.inquiry.removeAdminMemo(memberCnslSeq,cnslMemoSeq);
				}
			}else{
				alert("메모삭제는 작성자만 가능합니다.");
			}
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminPopTag").off().on('click', function(){
			abc.adminDetailPopup($(this).data("admin"));
		});
		
		//초기화 버튼
		$("#resetBtn").click(function(){
			$.form("#frmInquiry").reset();
			abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#aswrCnslTypeCode").val(), "aswrCnslTypeDtlCode");
			
			var cnslTypeDtlCode = '${bdMemberCounsel.cnslTypeDtlCode}';
			$("#aswrCnslTypeDtlCode").val(cnslTypeDtlCode);
		});
		
		//상담 스크립트 있을시에
		if("" != '${counselScript.cnslScriptSeq}'){
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#aswrCnslTypeCode").val(), "aswrCnslTypeDtlCode");
			abc.biz.board.inquiry.detailAswrCnslTypeCodeSet();
			$("#aswrCnslTypeDtlCode").val('${counselScript.cnslTypeDtlCode}');
			
			abc.getCounselScriptTitle($("#aswrCnslTypeCode").val(), $("#aswrCnslTypeDtlCode").val(), "cnslScriptTitleText");
			var cnslScriptSeq = '${counselScript.cnslScriptSeq}'
			$("#cnslScriptTitleText").val(cnslScriptSeq);
		}
		
		if('${memberInfo.leaveYn}' != abc.consts.BOOLEAN_FALSE){
			$("#saveBtn").text("저장(탈퇴회원)");
		}
		
	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>고객의 소리 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">고객의 소리 상세</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->
			<!-- S : col-wrap -->
			<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
			<form id="frmInquiry" name="frmInquiry"  method="post" onsubmit="return false;">
			<input type="hidden" id="memberCnslSeq" name="memberCnslSeq" value="${bdMemberCounsel.memberCnslSeq}"/>
			<input type="hidden" id="cnslScriptSeq" name="cnslScriptSeq" value="${bdMemberCounsel.cnslScriptSeq}"/>
			<input type="hidden" id="cnslGbnCode" value="${bdMemberCounsel.cnslGbnCode}"/>
			<input type="hidden" id="vndrAssignYn" name="vndrAssignYn" value="${Const.BOOLEAN_FALSE}"/>
			<input type="hidden" id="dispYn" name="dispYn" value="${Const.BOOLEAN_TRUE}"/>
			<input type="hidden" id="siteNo" name="siteNo" value="${bdMemberCounsel.siteNo}"/>
			<input type="hidden" id="memberNo" name="memberNo" value="${bdMemberCounsel.memberNo}"/>
			<input type="hidden" id="checkAswrDtm" name="checkAswrDtm" value="${bdMemberCounsel.aswrDtm}"/>
			<input type="hidden" id="leaveYn" value="${memberInfo.leaveYn}"/>
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
								<th scope="row">회원</th>
								<td><a href="javascript:void(0)" id="memberPop" class="link" value="${bdMemberCounsel.memberNo}"><c:out value="${bdMemberCounsel.memberDetailInfo}"/></a> / <c:out value="${memberInfo.memberTypeCodeName}"/></td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호 / 이메일</th>
								<td><c:out value="${memberInfo.detailHdphnNoText}"/> / <c:out value="${memberInfo.detailEmailAddrText}"/></td>
							</tr>
							<tr>
								<th scope="row">문의사이트</th>
								<td><c:out value="${bdMemberCounsel.siteName}"/></td>
							</tr>
							<tr>
								<th scope="row">문의유형</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" title="문의유형 선택">
											<option value="">문의유형</option>
											<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}"<c:if test="${cnslTypeCode.codeDtlNo eq bdMemberCounsel.cnslTypeCode}"> selected</c:if>>${cnslTypeCode.codeDtlName}</option>
											</c:forEach>
										</select>
										<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" title="문의구분 선택">
											<option value="">문의구분</option>
											<c:forEach var="cnslTypeDtlCode" items="${cnslTypeDtlCode}">
												<option value="${cnslTypeDtlCode.codeDtlNo}"<c:if test="${cnslTypeDtlCode.codeDtlNo eq bdMemberCounsel.cnslTypeDtlCode}"> selected</c:if>>${cnslTypeDtlCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td><c:out value="${bdMemberCounsel.inqryTitleText}" escapeXml="false"/></td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea id="inqryContText" name="inqryContText" class="ui-textarea" title="답변내용 입력"><c:out value="${bdMemberCounsel.inqryContText}" escapeXml="false"/></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">문의일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.inqryDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
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
								<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
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
							<!-- S : 20190308 수정 // 기존 답변상태 tr 위치 변경 -->
							<tr>
								<th scope="row">답변상태</th>
								<td id="aswrStatCodeTd" name="aswrStatCodeTd"><c:out value="${bdMemberCounsel.aswrStatName}"/></td>
								<input type="hidden" id="aswrStatCode" name="aswrStatCode" value="${bdMemberCounsel.aswrStatCode}"/>
							</tr>
							<!-- E : 20190308 수정 // 기존 답변상태 tr 위치 변경 -->
							<tr>
								<th scope="row">답변 스크립트 선택</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select id="aswrCnslTypeCode" name="aswrCnslTypeCode" class="ui-sel" title="상담유형 선택">
											<option value="etc">직접입력</option>
											<c:forEach var="aswrCnslTypeCode" items="${aswrCnslTypeCode}">
												<option value="${aswrCnslTypeCode.codeDtlNo}"<c:if test="${aswrCnslTypeCode.codeDtlNo eq bdMemberCounsel.aswrCnslTypeCode}"> selected</c:if>>${aswrCnslTypeCode.codeDtlName}</option>
											</c:forEach>
										</select>
										<select id="aswrCnslTypeDtlCode" name="aswrCnslTypeDtlCode" class="ui-sel" title="상담분류 선택">
											<option value="">상담분류</option>
											<c:forEach var="aswrCnslTypeDtlCode" items="${aswrCnslTypeDtlCode}">
												<option value="${aswrCnslTypeDtlCode.codeDtlNo}"<c:if test="${aswrCnslTypeDtlCode.codeDtlNo eq bdMemberCounsel.aswrCnslTypeDtlCode}"> selected</c:if>>${aswrCnslTypeDtlCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">답변내용</th>
								<td class="input">
									<select id="cnslScriptTitleText" name="cnslScriptTitleText" class="ui-sel" title="스크립트제목 선택">
										<option value="">스크립트제목</option>
									</select>
									<textarea id="aswrContText" name="aswrContText" class="ui-textarea" title="답변내용 입력" maxlength="2000">${bdMemberCounsel.aswrContText}</textarea>
								</td>
							</tr>
							<tr>
								<th scope="row">답변보류</th>
								<td class="input">
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="aswrHoldCheck" name="aswrHoldCheck" type="checkbox">
										<label for="aswrHoldCheck">답변보류</label>
									</span>
								</td>
							</tr>
							<!-- E : 20190308 추가 // 담당업체이관, 답변보류 영역 추가 -->
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0)" class="adminPopTag" data-admin="${bdMemberCounsel.aswrNo}" style="text-decoration: underline;"><c:out value="${bdMemberCounsel.getAswrDetailInfo()}"/></a></td>
							</tr>
							<tr>
								<th scope="row">답변일시</th>
								<td><fmt:formatDate value="${bdMemberCounsel.aswrDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
			</div>
			</form>
			<!-- E : col-wrap -->
			<!-- E : 20190130 수정 // 기획 수정으로 상세, 답변 화면 2분할 수정 -->

			<!-- S : window-btn-group -->
			<!-- S : window-btn-group -->
			<form id="frmAdminMemo" name="frmAdminMemo">
			<div class="window-btn-group">
				<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->

			<!-- S : content-header -->
			<input type="hidden" name="memberCnslSeq" value="${bdMemberCounsel.memberCnslSeq}"/>
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관리자 메모</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : manager-msg-wrap -->
				<div class="manager-msg-wrap">
					<div class="msg-box">
						<textarea id="adminMemoText" name="memoText" title="관리자 메모 입력" maxlength="500"></textarea>
					</div>
					<button id="memoSaveBtn" type="button" class="btn-normal btn-save">저장</button>
				</div>
				<!-- E : manager-msg-wrap -->
				<c:if test="${bdMemberCounselMemo != null}">
				<!-- S : msg-list-wrap -->
				<div class="msg-list-wrap">
					<div class="wrapper"></div>
					<ul id="memoAddArea" class="msg-list">
					<c:forEach var="bdMemberCounselMemo" items="${bdMemberCounselMemo}" varStatus="idx">
						<li id="adminMemoArea${bdMemberCounselMemo.cnslMemoSeq}">
							<span class="msg-list-info">
								<span class="user-info">
									<span class="user-id">${bdMemberCounselMemo.rgsterDpName}</span>
								</span>
								<span class="regist-date-wrap">
									<span class="regist-date"><fmt:formatDate value="${bdMemberCounselMemo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
									<a href="#" value="${bdMemberCounselMemo.cnslMemoSeq}" rgsterno="${bdMemberCounselMemo.rgsterNo}" name="memoDeleteBtn" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
								</span>
							</span>
							<p class="msg-desc">
								${bdMemberCounselMemo.memoText}
							</p>
						</li>
					</c:forEach>
					</ul>
				</div>
				</c:if>
				<!-- E : msg-list-wrap -->
			</form>

		</div>
	</div>
</body>
<script src="/static/common/js/biz/board/abcmart.board.inquiry.js<%=_DP_REV%>">
</script>