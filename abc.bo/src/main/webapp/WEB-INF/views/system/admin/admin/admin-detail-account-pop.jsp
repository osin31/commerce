<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		// 관리자 상세 초기 탭페이지 설정 및 데이터 세팅
		abc.biz.system.admin.admin.initAdminDetailData();

		// 이메일 상세정보
		var emailText = '<c:out value="${detailInfo.emailAddrText}"/>';
		abc.biz.system.admin.admin.detailEmailSet(emailText);

		// 저장
		$("#saveBtn").click(function(){
			abc.biz.system.admin.admin.changeDetailInfo();
		})

		<%-- 휴대폰 번호 키 체크 --%>
		$("#hdphnNoText").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 이메일 변경 시 세팅 --%>
		$("#selEmail").change(function(){
			var selEmailVal = $(this).val();
			abc.biz.system.admin.admin.changeEmail(selEmailVal);
		});
	});
</script>

<body class="window-body">
	<form id="adminDetailForm" name="adminDetailForm" method="post" onsubmit="return false;">
	<input type="hidden" id="adminNo" name="adminNo" value='<c:out value="${detailInfo.adminNo}"/>'>
	<input type="hidden" id="loginId" value='<c:out value="${detailInfo.loginId}"/>'>
	<input type="hidden" id="batchErrorVal" value='<c:out value="${detailInfo.batchErrorAlertYn}"/>'>
	<input type="hidden" id="emailAddrText" name="emailAddrText">

	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 수정</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title"><c:out value="${detailInfo.loginId}"/>(<c:out value="${detailInfo.adminName}"/>) 관리자</h2>
				</div>
				<div class="fr">
					<span class="guide-text">최종 로그인 : <fmt:formatDate value="${userDetails.lastLoginDtm}" pattern="yyyy-MM-dd HH:mm:ss"/> 최종 로그인 IP <c:out value="${userDetails.lastLoginIpText}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">기본정보</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>기본정보</caption>
				<colgroup>
					<col style="width: 135px;">
					<col>
					<col style="width: 135px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">권한그룹명</span>
						</th>
						<td><c:out value="${detailInfo.authName}"/></td>
						<th scope="row">권한적용 시스템</th>
						<td><c:out value="${detailInfo.authApplySystemType}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 id</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.loginId}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 이름</span>
						</th>
						<td colspan="3"><c:out value="${detailInfo.adminName}"/></td>
					</tr>
					<tr>
						<th scope="row">현재비밀번호</th>
						<td class="input" colspan="3">
							<input id="oldPswdText" type="password" class="ui-input" title="비밀번호 입력" maxlength="20">
						</td>
					</tr>
					<tr>
						<th scope="row">새 비밀번호</th>
						<td class="input" colspan="3">
							<input id="pswdText" name="pswdText" type="password" class="ui-input" title="비밀번호 입력" maxlength="20">
						</td>
					</tr>
					<tr>
						<th scope="row">새 비밀번호 확인</th>
						<td class="input" colspan="3">
							<input id="pswdTextConfirm" name="pswdTextConfirm"  type="password" class="ui-input" title="비밀번호 입력" maxlength="20">

							<ul class="td-text-list">
								<li>* 영문(대소문자 구분), 숫자, 특수문자의 2개 이상 조합 10~20자까지 가능합니다.</li>
								<li>* 동일 문자를 3회 이상 사용할 수 없습니다.</li>
								<li>* 아이디와 동일한 비밀번호를 사용할 수 없습니다.</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이메일</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : email-box -->
							<span class="email-box">
								<span class="email-ip">
									<input type="text" id="emailAddr" name="emailAddr" class="ui-input" title="메일주소 아이디 입력" maxlength="50">
									<span class="txt">@</span>
									<input type="text" id="emailAddrDtl" name="emailAddrDtl" class="ui-input mail-domain" title="메일주소 도메인 직접 입력" maxlength="50">
								</span>
								<select id="selEmail" name="selEmail" class="ui-sel domain-sel" title="메일주소 도메인 선택">
									<c:forEach var="emailSiteCode" items="${emailSiteCode}">
										<option value="<c:if test="${emailSiteCode.codeDtlName ne '직접입력'}">${emailSiteCode.codeDtlName}</c:if>">${emailSiteCode.codeDtlName}</option>
									</c:forEach>
								</select>
							</span>
							<!-- E : email-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="- 없이 입력" value="<c:out value="${detailInfo.hdphnNoText}"/>" maxlength="15">

									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="batchErrorAlertYn" name="batchErrorAlertYn" type="checkbox" value="Y" disabled="disabled">
										<label for="batchErrorAlertYn">배치에러 SMS발송</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">개인정보 취급여부</span>
						</th>
						<td colspan="3">
							<c:choose>
								<c:when test="${detailInfo.memberInfoMgmtYn eq Const.BOOLEAN_TRUE}">
									<c:out value="가능"/>
								</c:when>
								<c:otherwise>
									<c:out value="불가능"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">접근 허용 아이피</span>
						</th>
						<td colspan="3">
							<c:forEach var="accessIpData" items="${accessIpData}" varStatus="status">
								<c:choose>
									<c:when test="${status.index == 0}">
										<c:out value="${accessIpData.accessIpText}"/>
									</c:when>
									<c:otherwise>
										, <c:out value="${accessIpData.accessIpText}"/>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- DESC : 20180121 삭제 // 수정/상세페이지 삭제버튼 삭제 -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="#" id="saveBtn" class="btn-normal btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	</form>
</body>


<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>