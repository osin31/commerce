<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		<script type="text/javascript">
			$(function() {
				<%-- 관리자 아이디 키 입력 체크 --%>
				$("#loginId").off().on('keyup', function() {
					abc.biz.system.admin.admin.keyCheck(this);
				});

				<%-- 관리자 아이디 중복 확인 --%>
				$("#idCheckBtn").off().on('click', function() {
					abc.biz.system.admin.admin.checkLoginId();
				});

				<%-- 전화번호 키 체크 --%>
				$("#telNoText").off().on('keyup', function() {
					abc.text.validateOnlyNumber(this);
				});

				<%-- 휴대폰 번호 키 체크 --%>
				$("#hdphnNoText").off().on('keyup', function() {
					abc.text.validateOnlyNumber(this);
				});

				<%-- 이메일 변경 시 세팅 --%>
				$("#selEmail").off().on('change', function() {
					var selEmailVal = $(this).val();
					abc.biz.system.admin.admin.changeEmail(selEmailVal);
				});

				$("#saveBtn").off().on('click', function() {
					abc.biz.system.admin.admin.doActionAconnectAdmin("save");
				});

				$("#listBtn").off().on('click', function() {
					location.href = "/system/admin/store?menuNo=" + abc.param.getParams().menuNo;
				});

				<%-- 비밀번호 초기화 --%>
				$("#resetBtn").click(function(){
					abc.biz.system.admin.admin.pswdReset();
				});

				<%-- 오프라인 매장찾기 --%>
				$("#searchStore").click(function(){
					abc.storeSearchPopup('abc.biz.system.admin.admin.aconnectStoreSearchCallback');
				});

				// 이메일 상세정보
				var emailText = '<c:out value="${detailInfo.emailAddrText}"/>';
				abc.biz.system.admin.admin.detailEmailSet(emailText);

			});
		</script>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">A-Connect 사용자 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites">
							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>A-Connect 사용자 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">A-Connect 사용자 관리</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<form id="frmAconnectAdmin" name="frmAconnectAdmin">
				<input type="hidden" name="adminNo" id="adminNo" value="${detailInfo.adminNo}">
				<input type="hidden" id="confirmLoginId" value="">
				<input type="hidden" id="emailAddrText" name="emailAddrText">
				<input type="hidden" id="storeNo" name="storeNo">
				<table class="tbl-row">
					<caption>A-Connect 사용자 관리</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
				<c:if test="${detailInfo.adminNo == null or detailInfo.adminNo eq '' }">
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">사용자ID</span>
							</th>
							<td class="input">
								<span class="ip-text-box">
									<input type="text" id="loginId" name="loginId" class="ui-input" title="사용자 ID 입력" placeholder="영어, 숫자만 입력 가능" maxlength="20">
									<a href="#" id="idCheckBtn" class="btn-sm btn-func">중복확인</a>
								</span>
								<!-- E : ip-text-box -->

								<!-- S : td-text-list -->
								<ul class="td-text-list">
									<li>* 영문(대소문자 구분) 및 숫자 3~20자까지 가능합니다.</li>
									<li>* 특수문자 및 공백은 사용 불가합니다</li>
								</ul>
							</td>
							<th scope="row">
								<span class="th-required">사용자명</span>
							</th>
							<td class="input">
								<input id="adminName" name="adminName" type="text" class="ui-input" title="사용자명 입력" maxlength="20">
							</td>
						</tr>
						<!-- S : A-Connect 사용자 등록페이지 노출 -->
						<tr>
							<th scope="row">
								<span class="th-required">비밀번호</span>
							</th>
							<td class="input">
								<input id="pswdText" name="pswdText" type="password" class="ui-input" title="비밀번호 입력" maxlength="20">
								<ul class="td-text-list">
									<li>* 영문(대소문자 구분), 숫자, 특수문자의 2개 이상 조합 10~20자까지 가능합니다.</li>
									<li>* 동일 문자를 3회 이상 사용할 수 없습니다.</li>
									<li>* 아이디와 동일한 비밀번호를 사용할 수 없습니다.</li>
								</ul>
							</td>
							<th scope="row">
								<span class="th-required">비밀번호 확인</span>
							</th>
							<td class="input">
								<input id="pswdTextConfirm" name="pswdTextConfirm" type="password" class="ui-input" title="비밀번호 입력" maxlength="20">
							</td>
						</tr>
						<tr>
							<th scope="row">전화번호</th>
							<td class="input">
								<input type="text" id="telNoText" name="telNoText" class="ui-input" placeholder="'-' 없이 입력 (예: 01012345678)" title="전화번호 입력" maxlength="15">
							</td>
							<th scope="row">
								<span class="th-required">핸드폰 번호</span>
							</th>
							<td class="input">
								<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="- 없이 입력" maxlength="15">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">메일주소</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : email-box -->
								<span class="email-box small">
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
								<span class="th-required">매장</span>
							</th>
							<td class="input" colspan="3" id="storeTr">
								<!-- DESC : A-Connect 사용자 상세페이지 노출 -->
								<!-- <span class="text">storeName : <span>MS 서울중구점</span>, storeId : <span>0000</span></span> -->
								<a href="javascript:void(0)" id="searchStore" class="btn-sm btn-link">오프라인매장 조회</a>
							</td>
						</tr>
					</tbody>
				</c:if>
				<c:if test="${detailInfo.adminNo != null and detailInfo.adminNo ne '' }">
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">사용자ID</span>
							</th>
							<td>${detailInfo.loginIdDetail}</td>

							<th scope="row">
								<span class="th-required">사용자명</span>
							</th>
							<td class="input">
								<input id="adminName" name="adminName" value="${detailInfo.adminName}" type="text" class="ui-input" title="사용자명 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">비밀번호(교체기간)</th>
							<td class="input">
								<span class="ip-text-box">
									<button type="button" id="resetBtn" class="btn-sm btn-func" id="resetBtn">초기화</button>
									<c:if test="${detailInfo.pswdInitYn ne 'N'}">
									<span class="text">(<fmt:formatDate value="${detailInfo.pswdChngDtm}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>
									</c:if>
								</span>
							</td>
							<th scope="row">비밀번호 초기화 여부</th>
							<td><c:out value="${detailInfo.pswdInitYn}"/></td>
						</tr>
						<tr>
							<th scope="row">전화번호</th>
							<td class="input">
								<input type="text" id="telNoText" name="telNoText" class="ui-input" placeholder="'-' 없이 입력 (예: 01012345678)" title="전화번호 입력" value="<c:out value="${detailInfo.telNoText}"/>">
							</td>
							<th scope="row">
								<span class="th-required">핸드폰 번호</span>
							</th>
							<td class="input">
								<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="- 없이 입력" value="<c:out value="${detailInfo.hdphnNoText}"/>">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">메일주소</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : email-box -->
								<span class="email-box small">
									<span class="email-ip">
										<input type="text" id="emailAddr" name="emailAddr" class="ui-input" title="메일주소 아이디 입력">
										<span class="txt">@</span>
										<input type="text" id="emailAddrDtl" name="emailAddrDtl" class="ui-input mail-domain" title="메일주소 도메인 직접 입력">
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
								<span class="th-required">매장</span>
							</th>
							<td class="input" colspan="3" id="storeTr">
								<span class="text">storeName : <span>${detailInfo.storeName}</span>, storeId : <span>${detailInfo.storeNo}</span></span><a href="javascript:void(0)" id="searchStore" class="btn-sm btn-link">오프라인매장 조회</a>
							</td>
						</tr>
						<tr>
							<th scope="row">등록일시</th>
							<td><fmt:formatDate value="${detailInfo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							<th scope="row">사용여부</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="useYn" id="useY" value="Y"<c:if test="${detailInfo.useYn eq 'Y'}"> checked</c:if>>
											<label for="useY">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="useYn" id="useN" value="N"<c:if test="${detailInfo.useYn eq 'N'}"> checked</c:if>>
											<label for="useN">사용안함</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</c:if>
				</table>
				</form>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
				<c:if test="${detailInfo.adminNo != null and detailInfo.adminNo ne '' }">
					<!-- div class="fl">
						<a href="#" id="deleteBtn" class="btn-normal btn-del">삭제</a>
					</div -->
				</c:if>
					<div class="fr">
						<a href="#" id="listBtn" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="#" id="saveBtn" class="btn-lg btn-save">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->

<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>