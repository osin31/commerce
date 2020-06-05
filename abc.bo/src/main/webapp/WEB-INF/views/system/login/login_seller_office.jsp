<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="kr.co.abcmart.constant.Const" var="Const" />
<un:useConstants className="kr.co.abcmart.constant.CommonCode" var="CommonCode" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>3TOP PARTNER OFFICE
	</title>
<%@include file="/WEB-INF/views/common/java.jsp" %>
<%@include file="/WEB-INF/views/common/static.jsp" %>
	<script>
	$(function() {
		if(!abc.text.isNull(opener)){
			opener.window.top.location.href = "/";
			window.close();
		}
		if('${expired}' == 'Y'){
			alert("새로 로그인 되어 현재 로그인 계정은 로그아웃 됩니다.");
		}

		cLoginId = $.cookie('cLoginId');
		if (cLoginId != undefined) {
			$('#chkSaveID').prop('checked',true);
			$('#username').val(cLoginId);
			$('#password').focus();
		}

		$("#reqBtn").click(function(e){
			abc.poLoginCrtfcNo();
		});

		$("#login").click(function(e){
			abc.poLogin();
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
	</script>
</head>
<body>
	<!-- s: login || 판매원용 로그인 -->
	<div class="wrap login">
		<div class="login-wrap">
			<div class="left-wrap bg-seller-office">
				<div class="offscreen">
					<h1>3Top PARTNER OFFICE</h1>
					<div>3TOP 파트너오피스 회원님 반갑습니다. 로그인해 주세요.</div> <!--190702 수정 // 셀러오피스 > 파트너오피스 명칭 변경-->
					<div>Copyright © 3TOP korea co,ltd. all rights reserved.</div>
				</div>
			</div>
			<div class="right-wrap">
				<form id="loginForm">
				<input type="hidden" name="returnUrl" id="returnUrl" value="${returnUrl}"/>
				<h2><img src="/static/images/common/title_seller_office.gif" alt="for seller Office"></h2>
				<div class="desc">사용하시는 아이디로 로그인 후 접속하셔야 <br />관리자 시스템 이용이 가능합니다. </div>
				<div class="login-form-wrap">
					<div class="login-input"><input type="text" placeholder="ID" class="ui-input" name="username" id="username"></div>
					<div class="login-input"><input type="password" placeholder="Password" class="ui-input" name="password" id="password"></div>
					<div class="id-save">
						<span class="ui-chk">
							<input id="chkSaveID" class="ip_chk" type="checkbox" >
							<label for="chkSaveID" class="ip_chk_lb">아이디 저장</label>
						</span>
					</div>
					<!-- s : 190820 수정 | 휴대폰 인증로그인 UI 수정 -->
					<div class="phone-cert-box">
						<button type="button" id="reqBtn" class="btn-login">휴대폰 인증 후 로그인</button>

						<div class="cert-box">
							<input type="text" class="ui-input" id="crtfcNoText" name="crtfcNoText" placeholder="인증번호 6자리를 입력해주세요." title="인증번호 입력" readonly="readonly">
							<span class="count" id="countDown"></span>
							<a href="javascript:void(0);" id="login" class="btn-confirm">확인</a>
						</div>
					</div>
					<!-- e : 190820 수정 | 휴대폰 인증로그인 UI 수정 -->
				</div>
				</form>
			</div>
		</div>
	</div>
	<!-- e: login || 판매원용 로그인 -->
</body>
</html>
