<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		if('${memberInfo.leaveYn}' != abc.consts.BOOLEAN_FALSE){
			if('${memberInfo.leaveYn}' == abc.consts.BOOLEAN_TRUE){
				alert("탈퇴 신청 회원입니다. \r탈퇴 신청날짜 : ${memberInfo.leaveDtm}");
			}else{
				alert("탈퇴처리된 회원입니다.");
			}
			window.close();
		}
		
		<%-- 초기값 세팅 --%>
		abc.biz.member.member.info.otsVipYn = "${memberInfo.otsVipYn}";
		abc.biz.member.member.info.smsRecvYn = "${memberInfo.smsRecvYn}";
		abc.biz.member.member.info.emailRecvYn = "${memberInfo.emailRecvYn}";
		abc.biz.member.member.info.resellerDoubtYn = "${memberInfo.resellerDoubtYn}";
		abc.biz.member.member.info.nightSmsRecvYn = "${memberInfo.nightSmsRecvYn}";
		abc.biz.member.member.info.blackListCode = "${memberInfo.blackListYn}";
		abc.biz.member.member.info.blackListTypeCode = "${memberInfo.blackListTypeCode}";
		abc.biz.member.member.info.bankCode = "${memberInfo.bankCode}";
		
		<%-- 초기화면 셋팅 --%>
		abc.biz.member.member.info.initSetData();
		
		<%-- 회원이력보기 팝업 --%>
		$("#historyBtn").click(function(){
			abc.biz.member.member.info.memberHistoryPop();
		});
		
		<%--회원탈퇴 --%>
		$("#leaveBtn").click(function(){
			abc.biz.member.member.info.memberLeave(); 
		});
		
		<%-- 임직원 인증 초기화 --%>
		$("#empCrtfcResetBtn").click(function(){
			abc.biz.member.member.info.empCrtfcReset();
		});
		
		<%-- 생일 입력(숫자만) --%>
		$("#birthYmd").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 기념일 입력(숫자만) --%>
		$("#annvrYmd").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 휴대폰번호 입력(숫자만) --%>
		$("#hdphnNoText").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 인증번호 입력(숫자만) --%>
		$("#crtfcNoText").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 우편번호 입력(숫자만) --%>
		$("#postCodeText").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 신발사이즈 입력(숫자만) --%>
		$("#shoesSize").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 계좌번호 입력(숫자만) --%>
		$("#acntNoText").off().on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 비밀번호 초기화 --%>
		$("#pswdResetBtn").click(function(){
			abc.biz.member.member.info.pswdReset();
		});
		
		<%-- 인증번호 발송 --%>
		$("#certBtn").click(function(){
			abc.biz.member.member.info.certificationSet();
		});
		
		<%-- 인증번호 인증 --%>
		$("#certConfirmBtn").click(function(){
			abc.biz.member.member.info.certificationConfirm();
		});
		
		<%-- 포인트 사용 비밀번호 초기화 --%>
		$("#pointResetBtn").click(function(){
			abc.biz.member.member.info.pointReset();
		});
		
		<%-- 우편번호 찾기 --%>
		$("#postSearchBtn").click(function(){
			abc.postPopup(abc.biz.member.member.info.setPostCode);
		});

		<%-- 계좌인증  --%>
		$("#accountBtn").click(function(){
			abc.biz.member.member.info.accountAuth();
		});
		
		<%-- 배송주소록 등록내역 --%>
		$("#deliveryAddrBtn").click(function(){
			abc.biz.member.member.info.searchDeliveryPop();
		});
		
		<%-- 블랙리스트 체크 설정 --%>
		$("#blackListYn").change(function(){
			abc.biz.member.member.info.blackListCheckSet();	
		});
		
		<%-- 블랙리스트 저장 --%>
		$("#blackSaveBtn").click(function(){
			abc.biz.member.member.info.memberInfoAction('blackSave');
		})
		
		<%-- 관리자 메모 글자수 체크 --%>
		$("#memoText").keyup(function(e){
			var content = $(this).val();
			abc.biz.member.member.info.stringLengthCheck(content);
		});
		
		<%-- 회원 관리자 메모 저장 --%>
		$("#memoSaveBtn").click(function(){
			abc.biz.member.member.info.memberInfoAction('memoSave');
		});
		
		<%-- 회원 관리자 메모 삭제 --%>
		$(document).on("click", ".btn-msg-list-del", function(event){
			var idx = $(this).parent().parent().parent("li").index();
			var classNm = $(this).parent().parent().attr("class");
			
			abc.biz.member.member.info.removeMemo(idx, classNm);
		});
		
		<%-- 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.member.member.info.memberInfoAction('save');
		});
		
		// 회원활동정보영역 링크 클릭시 탭이동
		$("a[name=tabLink]").off().on("click", function() {
			var tabId = $(this).data("tabid");
			var searchKey1 = $(this).data("keyid");
			var searchKey2 = $(this).data("keyid2");
			
			abc.biz.member.memberDetail.fromInfo = [searchKey1, searchKey2];
			
			$("#"+tabId).trigger("click");
			return false;
		});
		
		//계좌 초기화
		$("#accountRemove").click(function(){
			abc.biz.member.member.info.accountRemove();
		});

});
</script>


<!-- S : col-wrap -->
<form id="memberDetailForm" name="memberDetailForm">
<input type="hidden" id="memberNo" name="memberNo" value="${memberInfo.memberNo}">
<input type="hidden" id="mbshpGradeCode" name="mbshpGradeCode" value="${memberInfo.mbshpGradeCode}">
<input type="hidden" id="emailAddrText" name="emailAddrText" value="${memberInfo.emailAddrText}">
<input type="hidden" id="acntCrtfcYn" name="acntCrtfcYn" value="${Const.BOOLEAN_FALSE}">
<input type="hidden" id="hiddenMemberName" name="hiddenMemberName" value="${memberInfo.memberName}">
<div class="col-wrap col2 user-info-default">
	<!-- S : col -->
	<div class="col">
		<!-- S : row-wrap -->
		<div class="row-wrap">
			<!-- S : 회원정보 > 임직원정보인 경우에만 노출 -->
			<c:if test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP and memberInfo.empYn eq 'Y'}">
			<div class="row">
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">임직원정보</h3>
					</div>
				</div>

				<table class="tbl-row">
					<caption>임직원정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">사번</th>
							<td><c:out value="${empInfo.empCd}"/></td>
							<th scope="row">이번 달 잔여한도</th>
							<td>
								<span class="td-box-both">
									<span class="fl"><span class="text"><fmt:formatNumber value="${empInfo.balance}" pattern="#,###"/>원</span></span>
									<span class="fr"><span class="text">*매월 1일 초기화</span></span>
								</span>
							</td>
						</tr>
						<tr>
							<th scope="row">부서/직책</th>
							<td><c:out value="${empInfo.deptCd}"/> / <c:out value="${empInfo.jikChaek}"/></td>
							<th scope="row">입사일 / 퇴사일</th>
							<td><c:out value="${empInfo.retdt}"/> / <c:out value="${empInfo.entDt}"/></td>
						</tr>
						
					</tbody>
				</table>
			</div>
			</c:if>
			<!-- E : 회원정보 > 임직원정보인 경우에만 노출 -->

			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">회원 기본정보</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<c:if test="${memberInfo.leaveYn eq Const.BOOLEAN_FALSE}">
								<a href="javascript:void(0)" id="leaveBtn" class="btn-sm btn-link">회원 탈퇴</a>
							</c:if>
							<a href="javascript:void(0)" id="historyBtn" class="btn-sm btn-link">회원이력 보기</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>회원 기본정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<c:choose>
							<c:when test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_ONLINE}">
								<tr>
									<th scope="row">구분</th>
									<td colspan="3">-</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th scope="row">구분</th>
									<td colspan="3">
										<c:if test="${memberInfo.localYn eq 'Y'}">
											<c:out value="내국인"/>
										</c:if>
										<c:if test="${memberInfo.localYn eq 'N'}">
											<c:out value="외국인"/>
										</c:if>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<!-- 9/16: 맴버십/온라인 동일하게 변경(기획팀요청) -->
						<tr>
							<th scope="row">회원 ID</th>
							<td><c:out value="${memberInfo.detailLoginId}"/> / <c:out value="${memberInfo.memberNo}"/></td>
							<th scope="row">회원예외유형</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input type="checkbox" name="otsVipYn" id="otsVipYn">
											<label for="otsVipYn">OTS VIP</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input type="checkbox" name="resellerDoubtYn" id="resellerDoubtYn">
											<label for="resellerDoubtYn">리셀러의심</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">이름</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" id="memberName" name="memberName" class="ui-input name" title="이름 입력" value="${memberInfo.memberName}" maxlength="50">
									<span class="text">*이름 변경시 휴대폰번호 인증 필수</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
							<th scope="row">성별</th>
							<c:choose>
								<c:when test="${memberInfo.genderType eq 'M'}">
									<td><c:out value="남자"/></td>
								</c:when>
								<c:when test="${memberInfo.genderType eq 'F'}">
									<td><c:out value="여자"/></td>
								</c:when>
								<c:otherwise>
									<td>-</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<c:choose>
							<c:when test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_ONLINE}">
								<!-- S : 온라인회원일 경우, 생년월일/기념일 영역 -->
								<tr>
									<th scope="row">생년월일</th>
									<td>-</td>
									<th scope="row">기념일</th>
									<td class="input">
										<!-- S : anniversary-box -->
										<span class="anniversary-box">
											<input type="text" id="annvrName" name="annvrName" class="ui-input" title="기념일명 입력" placeholder="기념일명 15자 이내 " value="${memberInfo.annvrName}" maxlength="15">
											<input type="text" id="annvrYmd" name="annvrYmd" class="ui-input" title="기념일 입력" placeholder="yyyy.mm.dd" value="<fmt:formatDate value="${memberInfo.annvrYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>"  maxlength="8"/>
										</span>
										<!-- E : anniversary-box -->
									</td>
								</tr>
								<!-- E : 온라인회원일 경우, 생년월일/기념일 영역 -->
							</c:when>
							<c:otherwise>
								<!-- S : 회원공통, 통합멤버십, 임직원일 경우 생년월일/기념일 영역 -->
								<tr>
									<th scope="row">생년월일</th>
									<td>
										<input type="hidden" id="birthYmd" name="birthYmd" value="<fmt:formatDate value="${memberInfo.birthYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>">
										<fmt:formatDate value="${memberInfo.birthYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>
									</td>
									<th scope="row">기념일</th>
									<td class="input">
										<span class="anniversary-box">
											<input type="text" id="annvrName" name="annvrName" class="ui-input" title="기념일명 입력" placeholder="기념일명 15자 이내 " value="${memberInfo.annvrName}" maxlength="15">
											<input type="text" id="annvrYmd" name="annvrYmd" class="ui-input" title="기념일 입력" placeholder="yyyy.mm.dd" value="<fmt:formatDate value="${memberInfo.annvrYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>" maxlength="8">
										</span>
									</td>
								</tr>
								<!-- E : 회원공통, 통합멤버십, 임직원일 경우 생년월일/기념일 영역  -->
							</c:otherwise>
						</c:choose>
						<tr>
							<th scope="row">비밀번호</th>
							<td colspan="3" class="input">
								<button type="button" id="pswdResetBtn" class="btn-sm btn-func">비밀번호 초기화</button>
								<c:if test="${memberInfo.pswdInitYn eq 'N'}">
								<span class="text">(비밀번호 초기화 여부 : N)</span>
								</c:if>
								<c:if test="${memberInfo.pswdInitYn eq 'Y'}">
								<span class="text">(비밀번호 초기화 여부 : Y, 비밀번호 변경기한 : <fmt:formatDate value="${memberInfo.pswdChngDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>)</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">임직원인증</th>
							<td class="input" colspan="3">
								<c:if test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}">
								<a href="javascript:void(0)" id="empCrtfcResetBtn" class="btn-sm btn-func">인증 초기화</a>
								<c:choose>
									<c:when test="${memberInfo.empCrtfcInitDtm eq null}">
										<span class="text">(실패 횟수 : <c:out value="${memberInfo.empCrtfcFailCount}"/>  / 초기화 여부 : <c:out value="${Const.BOOLEAN_FALSE}"/> )</span>
									</c:when>
									<c:otherwise>
										<span class="text">(실패 횟수 : <c:out value="${memberInfo.empCrtfcFailCount}"/>  / 초기화 여부 : <c:out value="${Const.BOOLEAN_TRUE}"/>  최근 초기화일: <fmt:formatDate value="${memberInfo.empCrtfcInitDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>)</span>
									</c:otherwise>
								</c:choose>
								</c:if>
								<c:if test="${memberInfo.memberTypeCode ne CommonCode.MEMBER_TYPE_MEMBERSHIP}">
									<span class="text">-</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">이메일</span>
							</th>
							<td colspan="3"><c:out value="${memberInfo.detailEmailAddrText}"/></td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">휴대폰 번호</span>
							</th>
							<td colspan="3" class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" class="ui-input phone-number" id="hdphnNoText" name="hdphnNoText" placeholder="01012345678" title="휴대폰 번호 입력" value="${memberInfo.hdphnNoText}" maxlength="11">
									<button id="certBtn" type="button" class="btn-sm btn-func">인증번호 발송</button>
								</span>
								<!-- E : ip-text-box -->

								<!-- S : 인증번호 발송 버튼 클릭시 노출되는 영역 -->
								<!-- S : ip-text-box -->
								<span id="certArea" class="ip-text-box">
									<input type="text" id="crtfcNoText" class="ui-input num-unit100000" title="인증번호 입력" maxlength="6">
									<input type="hidden" id="certConfirmYn" value="${Const.BOOLEAN_FALSE}">
									<button id="certConfirmBtn" type="button" class="btn-sm btn-func">인증번호 확인</button>
								</span>
								<!-- E : ip-text-box -->
								<!-- E : 인증번호 발송 버튼 클릭시 노출되는 영역 -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span>주소</span>
							</th>
							<td colspan="3">
								<!-- S : address-box -->
								<span class="address-box small">
									<span class="zip-code-wrap">
										<input type="text" class="ui-input" id="postCodeText" name="postCodeText" title="우편번호 입력" value="${deliveryInfo.postCodeText}" readonly="readonly" maxlength="5">
										<button type="button" id="postSearchBtn" class="btn-sm btn-link">우편번호 찾기</button>
									</span>
									<span class="address-wrap">
										<input type="text" class="ui-input" id="postAddrText" name="postAddrText" placeholder="시/군/구 동" title="시/군/구 동 입력" value="${deliveryInfo.postAddrText}" readonly>
										<input type="text" class="ui-input" id="dtlAddrText" name="dtlAddrText" placeholder="상세주소" title="상세주소 입력" value="${deliveryInfo.dtlAddrText}" maxlength="100">
									</span>
								</span>
								<!-- E : address-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">마케팅 활용동의</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="smsRecvYn" name="smsRecvYn" type="checkbox">
											<label for="smsRecvYn">SMS</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<input id="emailRecvYn" name="emailRecvYn" type="checkbox">
											<label for="emailRecvYn">이메일</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">야간 마케팅 수신 동의<br />(21시~08시)</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="nightSmsRecvYn" name="nightSmsRecvYn" type="checkbox">
											<label for="nightSmsRecvYn">SMS</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
			</div>
			<!-- E : row -->

			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">회원 부가정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>회원 부가정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">환불계좌</th>
							<td class="input">
								<!-- S : refund-account-box -->
								<span class="refund-account-box">
									<input type="hidden" id="addInfoRefund" name="arrayMemberAddInfo.addInfoTypeCode">
									<select class="ui-sel" id="bankCode" name="bankCode" title="은행/증권사 선택">
										<option value="">은행/증권사 선택</option>
										<c:forEach var="bankCode" items="${codeList.BANK_CODE}" varStatus="status">
											<c:if test="${bankCode.addInfo4 ne null and bankCode.addInfo4 ne ''}">
												<option value="${bankCode.codeDtlNo}" id="${bankCode.addInfo4}">${bankCode.codeDtlName}</option>
												<c:if test="${memberInfo.acntCrtfcYn eq 'Y'}">
													<script>
														$('#bankCode').val('<c:out value="${memberInfo.bankCode}"/>');
													</script>
												</c:if>
											</c:if>	
										</c:forEach>
									</select>
									<span class="refund-input">
										<input type="text" id="acntNoText" name="acntNoText" class="ui-input account-number" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력" value="${memberInfo.acntNoText}" maxlength="100">
										<!-- S : 20190121 추가 // 예금주명 input 추가 -->
										<input type="text" id="acntHldrName" name="acntHldrName" class="ui-input account-holder" placeholder="예금주명" title="예금주명 입력" value="${memberInfo.acntHldrName}" maxlength="50">
										<!-- E : 20190121 추가 // 예금주명 input 추가 -->
										<button type="button" id="accountBtn" class="btn-sm btn-func">계좌인증</button>
										<button type="button" id="accountRemove" class="btn-sm btn-func">계좌초기화</button>
									</span>
								</span>
								<!-- E : refund-account-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">신발 사이즈</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" class="ui-input num-unit100" id="shoesSize" name="shoesSize" title="신발 사이즈 입력" value="${memberInfo.shoesSize}" maxlength="3">
									<span class="text">mm</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">단골매장</th>
							<td><c:out value="${memberInfo.interestStore}"/></td>
						</tr>
						<tr>
							<th scope="row">관심 브랜드</th>
							<td><c:out value="${memberInfo.interestBrand}"/></td>
						</tr>
					</tbody>
					
				</table>
				<c:if test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}">
					<span style="opacity: 0.0;">
						<c:out value="hiddenKey: + ${memberInfo.safeKey}"/><br>
						<c:out value="hiddenSeq: + ${memberInfo.safeKeySeq}"/>
					</span>
				</c:if>
				<!-- E : tbl-row -->
			</div>
			<!-- E : row -->
		</div>
		<!-- E : row-wrap -->
	</div>
	<!-- E : col -->
	<!-- S : col -->
	<div class="col">
		<!-- S : row-wrap -->
		<div class="row-wrap">
			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">회원 활동정보</h3>
					</div>
					<div class="fr">
						<span class="guide-text">※표시항목은 최근 2개월 기준</span>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>회원 활동정보</caption>
					<colgroup>
						<col style="width:20%">
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">구분</th>
							<th scope="col">온라인 접수</th>
							<th scope="col">유선상담</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">※문의내역</th>
							<td>미답변 <a href="#" name="tabLink" data-tabid="tabInquiryMove" data-keyid="radioConfirm1" class="link"><c:out value="${counselData.onlineUnasCnt}"/></a> 건 / 답변보류 <a href="#" data-tabid="tabInquiryMove" data-keyid="radioConfirm2" name="tabLink" class="link"><c:out value="${counselData.onlineHoldCnt}"/></a>건</td>
							<td>답변보류 <a href="#" name="tabLink" data-tabid="tabInquiryMove" data-keyid="radioConfirm2" data-keyid2="radioQuestionType3" class="link"><c:out value="${counselData.offlineUnasCnt}"/></a> 건</td>
						</tr>
						<tr>
							<th scope="row">※상품Q&amp;A</th>
							<td colspan="2">미답변 <a href="#" name="tabLink" data-tabid="tabProdInquiryMove" data-keyid="aswr-stat-code-10000" class="link"><c:out value="${prodAswrData.unAswrCount}"/></a> 건 / 답변보류 <a href="#" name="tabLink" data-tabid="tabProdInquiryMove" data-keyid="aswr-stat-code-10001" class="link"><c:out value="${prodAswrData.hdAswrCount}"/></a>건</td>
						</tr>
						<tr>
							<th scope="row">※상품후기</th>
							<td colspan="2">미답변 <a href="#" name="tabLink" data-tabid="tabProdReviewMove" data-keyid="radioAnswer03" class="link"><c:out value="${productReviewCount}"/> </a> 건</td>
						</tr>
						<tr>
							<th scope="row">보유쿠폰</th>
							<td colspan="2">사용가능 <a href="#" name="tabLink" data-tabid="tabCouponMove" data-keyid="chkUse03" class="link"><c:out value="${memberInfo.couponCnt}"/></a> 건</td>
						</tr>
						<tr>
							<th scope="row">보유포인트</th>
							<td class="input" colspan="2">
								<c:choose>
									<c:when test="${memberInfo.memberTypeCode eq CommonCode.MEMBER_TYPE_ONLINE}">
										<span class="text">0 Point</span>
										<button type="button" class="btn-sm btn-func" disabled="disabled">포인트 사용 비밀번호 초기화</button>
									</c:when>
									<c:otherwise>
										<c:if test="${empty ifResut}">
											<span class="text"><a href="#" name="tabLink" data-tabid="tabPointMove" class="link"><fmt:formatNumber value="${pointInfo.possPoint + pointInfo.eventPoint}" pattern="#,###"/></a>&nbsp;Point</span>
											<button type="button" id="pointResetBtn" class="btn-sm btn-func">포인트 사용 비밀번호 초기화</button>
										</c:if>
										<c:if test="${not empty ifResut}">
											<span class="text" style="color: red;"><c:out value="${ifResut}"/></span>
										</c:if>
									</c:otherwise>
								</c:choose>
<%-- 								<span class="text"><a href="#" class="link"><fmt:formatNumber value="${pointInfo.accessPoint + pointInfo.eventPoint}" pattern="#,###"/></a>&nbsp;Point</span> --%>
<!-- 								DESC : 온라인회원의 경우에만, disabled="disabled" 속성 추가 -->
<%-- 								<c:if test="${memberInfo.memberTypeCode eq '10000'}"> --%>
								
<%-- 								</c:if> --%>
<%-- 								<c:if test="${memberInfo.memberTypeCode ne '10000'}"> --%>
<%-- 								</c:if> --%>
							</td>
						</tr>
						<tr>
							<th scope="row">※이벤트 내역</th>
							<td colspan="2">
								당첨 <a href="#" name="tabLink"  data-tabid="tabEventMove" class="link"><c:out value="${eventData.winCount}"/></a>건 (참여 <a href="#" name="tabLink" data-tabid="tabEventMove" class="link"><c:out value="${eventData.joinCount}"/></a>건)
							</td>
						</tr>
						<tr>
							<th scope="row">가입일시
								<c:if test="${not empty memberInfo.mbshpCnvrtDtm }">
									<br><br>
									전환일시
								</c:if>
							</th>
							<td colspan="2">
								<fmt:formatDate value="${memberInfo.joinDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
								<c:if test="${not empty memberInfo.mbshpCnvrtDtm }">
									<br><br>
									<fmt:formatDate value="${memberInfo.mbshpCnvrtDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">가입유형</th>
							<td colspan="2"><c:out value="${memberInfo.memberTypeCodeName}"/></td>
						</tr>
						<tr>
							<th scope="row">연동 SNS</th>
							<td colspan="2"><c:out value="${memberInfo.memberSns}"/></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-col -->
			</div>
			<!-- E : row -->

			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">회원 구매활동정보 </h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>회원 구매활동정보</caption>
					<colgroup>
						<col style="width:130px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">배송 주소록</th>
							<td class="input">
								<!-- DESC : html/member/BO-MB-01005.html 파일 확인 해주세요 -->
								<a href="#" id="deliveryAddrBtn" class="btn-sm btn-link">배송주소록 등록내역</a>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>회원 구매활동정보</caption>
					<colgroup>
						<col style="width:130px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">최초 주문정보</th>
							<td><fmt:formatDate value="${memberInfo.firstOrderDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">최신 주문정보</th>
							<td>
								<fmt:formatDate value="${memberInfo.lastOrderDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
								&nbsp;<a href="#" class="link"></a>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
			</div>
			<!-- E : row -->

			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">블랙리스트</h3>
					</div>
					<div class="fr">
						<!-- S : opt-group -->
						<span class="opt-group">
							<span class="ui-chk">
								<input id="blackListYn" name="blackListYn" type="checkbox">
								<label for="blackListYn">블랙리스트 설정</label>
							</span>
							<!-- DESC : 블랙리스트 설정 활성화시 selectbox disabled="disabled" 속성 삭제해주세요 -->
							<select class="ui-sel inline" id="blackListTypeCode" title="블랙리스트 유형 선택" disabled="disabled">
								<option value="">유형선택</option>
								<c:forEach var="blackListCode" items="${codeList.BLACK_LIST_TYPE_CODE}" varStatus="status">
									<option value="${blackListCode.codeDtlNo}">${blackListCode.codeDtlName}</option>
								</c:forEach>
							</select>
							<a href="#" id="blackSaveBtn" class="btn-sm btn-save">저장</a>
						</span>
						<!-- E : opt-group -->
					</div>
				</div>
				<!-- E : content-header -->
			</div>
			<!-- E : row -->

			<!-- S : row -->
			<div class="row">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관리자 메모</h3>
					</div>
					<div class="fr">
						<span class="text-limit">(<span id="counter" class="state-num">0</span>/400자)</span>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : manager-msg-wrap -->
				<div class="manager-msg-wrap">
					<div class="msg-box">
						<textarea id="memoText" title="관리자 메모 입력"></textarea>
					</div>
					<button type="button" id="memoSaveBtn" class="btn-normal btn-save">저장</button>
				</div>
				<!-- E : manager-msg-wrap -->

				<!-- S : msg-list-wrap -->
				<div class="msg-list-wrap">
					<ul id="memoArea" class="msg-list">
						<c:forEach var="memoList" items="${memoInfo}" varStatus="idx">
							<li>
								<span class="msg-list-info">
									<span class="user-info">
										<span class="user-id">${memoList.memberMemoSeq} / <c:out value="${memoList.dpRgsterName}"/></span>
									</span>
									<span class="regist-date-wrap">
										<span class="regist-date"><fmt:formatDate value="${memoList.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
										<input type="hidden" value="${memoList.memberMemoSeq}">
										<a href="javascript:void(0)" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
									</span>
								</span>
								<p class="msg-desc">
									${memoList.memoText}
								</p>
							</li>
						</c:forEach>
					</ul>
				</div>
				<!-- E : msg-list-wrap -->
			</div>
			<!-- E : row -->
		</div>
		<!-- E : row-wrap -->
	</div>
	<!-- E : col -->
</div>
<!-- E : col-wrap -->
</form>
<!-- S : tbl-desc-wrap -->
<!-- <div class="tbl-desc-wrap">
	<div class="fl">
		<a href="#" class="btn-normal btn-del">삭제</a>
	</div>
</div> -->
<!-- E : tbl-desc-wrap -->

<!-- S : window-btn-group -->
<div class="window-btn-group">
	<a href="javascript:void(0)" id="saveBtn" class="btn-normal btn-save">저장</a>
</div>
<!-- E : window-btn-group -->

<script src="/static/common/js/biz/member/abcmart.member.member.info.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/member/abcmart.member.member.js<%=_DP_REV%>"></script>
