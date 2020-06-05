<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function() {

		if(!abc.text.allNull('${userDetails.loginId}') && abc.consts.ADMIN_ID.indexOf('${userDetails.loginId}') == -1){
			if( (abc.date.dateDiff('${userDetails.pswdChngDtm}', new Date()) >= (parseInt('${userDetails.condtnTermValue}') - 1))
					|| ('${userDetails.pswdInitYn}' != 'N')){
				abc.biz.system.pwdchange.popupopen = "Y";
				$("#changePasswordBtn").trigger('click');
			}
		}

		$("#pswdChangeBtn").click(function(e){
			abc.biz.system.pwdchange.pswdChangeBtn();
		});

		var capscount = 0;
		$("#password").keypress(function(e){
			var shiftKey = false;
			shiftKey = e.shiftKey;
			if (capscount < 1
				&& ((e.keyCode >= 65 && e.keyCode <= 90) && !shiftKey)
						|| ((e.keyCode >= 97 && e.keyCode <= 122) && shiftKey)) {
				capscount++;
				alert("CapsLock 키가 눌려있습니다");
			}
		});
	});

	$(document).on('keydown',function(evt) {
		if (evt.keyCode == 27) {
			if(abc.biz.system.pwdchange.popupopen == "Y"){
				$("#changePasswordBtn").trigger('click');
			}
		}
	});
</script>

	<button type="button" id="changePasswordBtn" class="btn-sm btn-func btn-dialog" data-target="#dialogChangePasswordInfo" style="display: none;"></button>

	<div id="dialogChangePasswordInfo" class="ui-dialog-contents" data-show-close="false" title="비밀번호 변경 안내">
	<form id="pswdChangeForm" name="pswdChangeForm">
	<input type="hidden" id="adminNo" name="adminNo" value='<c:out value="${userDetails.adminNo}"/>'>
	<input type="hidden" id="loginId" value='<c:out value="${userDetails.loginId}"/>'>
	<input type="hidden" id="pswdInitYn" name="pswdInitYn" value='<c:out value="${userDetails.pswdInitYn}"/>'>
		<p class="dialog-desc">
			<!--관리자 시스템 or 파트너 시스템 -->
			<c:choose>
				<c:when test="${userDetails.authApplySystemType == 'P'}">
					<span class="spot">파트너 시스템</span>
				</c:when>
				<c:otherwise>
					<span class="spot">관리자 시스템</span>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${userDetails.pswdInitYn != 'N'}">
					의 원활한 이용을 위하여 임시로 발급된 비밀번호를 입력 후 <br />
					변경을 원하시는 비밀번호로 변경하여 주세요.
				</c:when>
				<c:otherwise>
					의 원활한 이용을 위하여 비밀번호를 변경하여 주세요.<br />
					비밀번호를 변경하시지 않을 경우 해당 계정은 잠김 처리되어 로그인이 불가합니다.
				</c:otherwise>
			</c:choose>
		</p>
		<table class="tbl-row">
			<caption>시스템 공지 등록</caption>
			<colgroup>
				<col style="width: 130px;">
				<col>
			</colgroup>
			<tbody>
			<tr>
				<th scope="row">
					<c:choose>
						<c:when test="${userDetails.pswdInitYn != 'N'}">
							<span class="th-required">임시 비밀번호</span>
						</c:when>
						<c:otherwise>
							<span class="th-required">현재 비밀번호</span>
						</c:otherwise>
					</c:choose>
				</th>
				<td class="input">
					<input type="password" id="oldPswdText" class="ui-input" title="현재 비밀번호 입력">
				</td>
			</tr>
			<tr>
				<th scope="row" class="th-required">
					<span class="th-required">새 비밀번호</span>
				</th>
				<td class="input">
					<input type="password" id="pswdText" name="pswdText" class="ui-input" title="새 비밀번호 입력">
				</td>
			</tr>
			<tr>
				<th scope="row" class="th-required">
					<span class="th-required">새 비밀번호 확인</span>
				</th>
				<td class="input">
					<input type="password" id="pswdTextConfirm" name="pswdTextConfirm" class="ui-input" title="새 비밀번호 확인 입력">
					<!-- S : td-text-list -->
					<ul class="td-text-list">
						<li>* 영문(대소문자 구분), 숫자, 특수문자의 2개 이상 조합<br />10~20자까지 가능합니다.</li>
						<li>* 동일 문자를 3회 이상 사용할 수 없습니다.</li>
						<li>* 아이디와 동일한 비밀번호를 사용할 수 없습니다.</li>
						<li>* 이전 비밀번호는 사용하실 수 없습니다.</li>
					</ul>
					<!-- E : td-text-list -->
				</td>
			</tr>
			</tbody>
		</table>

		<div class="dialog-footer">
			<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
			<button type="button" id="pswdChangeBtn" class="btn-lg btn-save">저장</button>
			<!-- E : 20190114 수정 -->
		</div>
	</form>
	</div>

<script src="/static/common/js/biz/system/abcmart.system.loginpasswordchange.js<%=_DP_REV%>"></script>