<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		<%-- 화면 초기 세팅 --%>
		abc.biz.system.admin.admin.AdminDetailInitSet();
		
		<%-- 권한그룹명 변경 시 권한적용시스템 설정 --%>
		$("#authNo").change(function(){
			var authNo = $(this).val();
			abc.biz.system.admin.admin.authGroupChange(authNo);
		});
		
		<%-- 관리자 아이디 키 입력 체크 --%>
		$("#loginId").keyup(function(){
			abc.biz.system.admin.admin.keyCheck(this);
		});
		
		<%-- 관리자 아이디 중복 확인 --%>
		$("#idCheckBtn").click(function(){
			abc.biz.system.admin.admin.checkLoginId();
		});
		
		<%-- 휴대폰 번호 키 체크 --%>
		$("#hdphnNoText").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 접근허용 아이피 추가 --%>
		$("#ipAddBtn").click(function(){
			abc.biz.system.admin.admin.addAccessIpText();
		});
		
		<%-- 접근허용 아이피 삭제 --%>
		$(document).on("click", ".btn-item-del", function(event){
			var idx = $(this).parent().index();
			var itemList = $(".item-list > li").eq(idx);
			itemList.remove();
		});
		
		<%-- 이메일 변경 시 세팅 --%>
		$("#selEmail").change(function(){
			var selEmailVal = $(this).val();
			abc.biz.system.admin.admin.changeEmail(selEmailVal);
		});
		
		<%-- 관리자 저장 --%>
		$("#saveBtn").click(function(){
			abc.biz.system.admin.admin.joinAdmin();
		});
		
	});
</script>

<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 등록</h1>
		</div>
		<form id="adminForm" name="adminForm" method="post" onsubmit="return false;">
		<input type="hidden" id="confirmLoginId" value="">
		<input type="hidden" id="emailAddrText" name="emailAddrText">
		<input type="hidden" id="viewType" name="viewType" value="C">
		
		<div class="window-content">
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
						<td class="input">
							<select id="authNo" name="authNo" class="ui-sel" title="권한그룹명 선택">
								<option value="">선택</option>
								<c:forEach var="authGroup" items="${authGroup}">
									<c:if test="${authGroup.authApplySystemType ne 'P'}">
										<option value="${authGroup.authNo}">${authGroup.authName}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<th scope="row">권한적용 시스템</th>
						<td id="authSystemType"></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 id</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" id="loginId" name="loginId" class="ui-input" title="관리자 ID 입력" placeholder="영어, 숫자만 입력 가능" maxlength="20">
								<a href="#" id="idCheckBtn" class="btn-sm btn-func">중복확인</a>
							</span>
							<!-- E : ip-text-box -->

							<!-- S : td-text-list -->
							<ul class="td-text-list">
								<li>* 영문 및 숫자 3~20자까지 가능합니다.</li>
								<li>* 특수문자 및 공백은 사용 불가합니다</li>
							</ul>
							<!-- E : td-text-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">관리자 이름</span>
						</th>
						<td class="input" colspan="3">
							<input type="text" id="adminName" name="adminName" class="ui-input" title="관리자 이름 입력" maxlength="20">
						</td>
					</tr>
					<!-- <tr>
						<th scope="row">
							<span class="th-required">비밀번호</span>
						</th>
						<td class="input" colspan="3">
							<input type="password" id="pswdText" name="pswdText" class="ui-input" title="비밀번호 입력" maxlength="20">

							S : td-text-list
							<ul class="td-text-list">
								<li>* 영문(대소문자 구분), 숫자, 특수문자의 2개 이상 조합 10~20자까지 가능합니다.</li>
								<li>* 동일 문자를 3회 이상 사용할 수 없습니다.</li>
								<li>* 아이디와 동일한 비밀번호를 사용할 수 없습니다.</li>
							</ul>
							E : td-text-list
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">비밀번호 확인</span>
						</th>
						<td class="input" colspan="3">
							<input type="password" id="pswdTextConfirm" name="pswdTextConfirm" class="ui-input" title="비밀번호 입력" maxlength="20">
						</td>
					</tr> -->
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
									<input type="text" id="hdphnNoText" name="hdphnNoText" class="ui-input" title="휴대폰번호 입력" placeholder="- 없이 입력" maxlength="15">

									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="batchErrorAlertYn" name="batchErrorAlertYn" class="ip_chk" type="checkbox" value="Y">
										<label for="batchErrorAlertYn" class="ip_chk_lb">배치에러 SMS발송</label>
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
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="memberInfoMgmtY" name="memberInfoMgmtYn" type="radio" value="Y">
										<label for="memberInfoMgmtY">가능</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="memberInfoMgmtN" name="memberInfoMgmtYn" type="radio" value="N" checked="checked">
										<label for="memberInfoMgmtN">불가능</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					<tr id="accessIpArea">
						<th scope="row">
							<span class="th-required">접근 허용 아이피</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" id="accessIp" class="ui-input" title="접근 허용 아이피 입력">
								<a href="#" id="ipAddBtn" class="btn-sm btn-func">추가</a>
							</span>
							<!-- E : ip-text-box -->

							<!-- S : item-list -->
							<ul class="item-list">
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">관리자 계정</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>관리자 계정</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사용여부</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="useY" name="useYn" type="radio"  value="Y" checked="checked">
										<label for="useY">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="useN" name="useYn" type="radio" value="N">
										<label for="useN">사용안함</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- S : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="saveBtn" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
		
		</form>
	</div>
</body>

<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>