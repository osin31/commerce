<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<script type="text/javascript">
			$(document).ready(function(){
				// sms 발송 팝업 호출
				$("#sendSmsBtn").off().on("click", function(f) {
					var _params = {
							siteNo : "",
							memberNo : "MB00000002",
							recvTelNoText : "",
							rcvrName : ""
					};
					abc.sendSmsPopup(_params);
				});


				// Email 발송 팝업 호출
				$("#sendEmailBtn").off().on("click", function(f) {
					var _params = {
							siteNo : "",
							rcvrMemberNo : "MB00000002",
							rcvrEmailAddrText : "",
							rcvrName : ""
					};
					abc.sendMailPopup(_params);
				});

				// 입점사 검색
				$("#searchVndrBtn").off().on("click", function(f) {
					console.log("vndrMultySelectYn =============]" + $("input:checkbox[id=vndrMultySelectYn]").is(":checked"));
					var isMultiSelect = $("#vndrMultySelectYn").is(":checked");
					abc.vndrSearchPopup(isMultiSelect, "vndrCheck");
				});

				// 우편번호 검색
				$("#searchPostNumBtn").off().on("click", function(f) {
					abc.postPopup(postNumCheck);
				});

				// 관리자 검색
				$("#searchAdminBtn").off().on("click", function(f) {
					abc.adminSearchPopup("adminSearchCheck");
				});
			});

			// 임점사 검색 CallBack
			function vndrCheck(_param) {
				$.each(_param, function(key, value) {
					console.log("key [" + key + "] : value [" + value + "]");
				});
			}

			// 우편번호 검색 CallBack
			function postNumCheck(_param) {
				$.each(_param, function(key, value) {
					console.log("key [" + key + "] : value [" + value + "]");
				});
			}

			// 관리자 검색 CallBack
			function adminSearchCheck(_param) {
				$.each(_param, function(key, value) {
					console.log("key [" + key + "] : value [" + value + "]");
				});
			}
		</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">공통 팝업 호출 샘플</h2>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">

					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>이메일 템플릿 검색</caption>
							<colgroup>
								<col style="width: 100px;">
								<col style="width: 79px">
								<col style="width: 79px">
								<col>
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">SMS보내기</th>
									<td class="input">
										<button type="button" id="sendSmsBtn" class="btn-sm btn-func btn-dialog">SMS 보내기</button>
									</td>
									<td></td>
									<td class="input"><textarea class="ui-textarea">
	/* ***************************************************************************
	 *  17. 문자메시지 발송 팝업
	 *  siteNo : 사이트 구분을 해서 전송 할 경우 필수
	 *  memberNo : 비회원일 경우  Const.NON_MEMBER_NO 에 정의된 회원 번호
	 *  recvTelNoText, rcvrName : 비 회원일 경우 필수
	 *  _param {
	 *  	siteNo : 사이트 번호,
	 *  	memberNo : 회원번호,
	 *  	recvTelNoText : 받는 휴대폰 번호
	 *  	rcvrName : 수신자 이름
	 *  }
	 *************************************************************************** */
									</textarea></td>
									<td class="input"><textarea class="ui-textarea">
			var _params = {
					siteNo : "",
					memberNo : "MB000000010",
					recvTelNoText : "",
					rcvrName : ""
			};
			abc.sendSmsPopup(_params);
									</textarea></td>
								</tr>
								<tr>
									<th scope="row">이메일보내기</th>
									<td class="input">
										<button type="button" id="sendEmailBtn" class="btn-sm btn-func btn-dialog">mail보내기</button>
									</td>
									<td></td>
									<td class="input"><textarea class="ui-textarea">
	/* ***************************************************************************
	 *  16. 메일 발송 팝업
	 *  siteNo : 사이트 구분을 해서 전송 할 경우 필수
	 *  rcvrMemberNo : 비회원일 경우  Const.NON_MEMBER_NO 에 정의된 회원 번호
	 *  rcvrEmailAddrText, rcvrName : 비 회원일 경우 필수
	 *  _param {
	 *  	siteNo : 사이트 번호,
	 *  	rcvrMemberNo : 회원번호,
	 *  	rcvrEmailAddrText : 수신자 메일 주소
	 *  	rcvrName : 수신자 이름
	 *  }
	 *************************************************************************** */
									</textarea></td>
									<td class="input"><textarea class="ui-textarea">
	var _params = {
			siteNo : "",
			rcvrMemberNo : "MB000000010",
			rcvrEmailAddrText : "",
			rcvrName : ""
	};
	abc.sendMailPopup(_params);
									</textarea></td>
								</tr>
								<tr>
									<th scope="row">입점사 검색</th>
									<td class="input">
										<button type="button" id="searchVndrBtn" class="btn-sm btn-func btn-dialog">입점사 검색</button>
									</td>
									<td><input type="checkbox" id="vndrMultySelectYn" value="Y"/> 입점사 다중 선택 여부</td>
									<td class="input"><textarea class="ui-textarea">
	/* ***************************************************************************
	 *  12. 입점사 검색 Pop
		    isMultiSelect : true(여러 업체 선택), false(1개 업체만 선택)
		    callbackFunction : opener 바닥창으로 콜백해줄 function 명
	 *************************************************************************** */

									</textarea></td>
									<td class="input"><textarea class="ui-textarea">
	var isMultiSelect = $("#vndrMultySelectYn").is(":checked");
	abc.vndrSearchPopup(isMultiSelect, "vndrCheck");

	// Callback 함수 사용법
	function vndrCheck(_param) {
		$.each(_param, function(key, value) {
			console.log("key [" + key + "] : value [" + value + "]");
		});
	}
									</textarea></td>
								</tr>
								<tr>
									<th scope="row">우펀변호 검색</th>
									<td class="input">
										<button type="button" id="searchPostNumBtn" class="btn-sm btn-func btn-dialog">우펀변호 검색</button>
									</td>
									<td></td>
									<td class="input"><textarea class="ui-textarea">
	/* ***************************************************************************
	 *  14. 우편번호 찾기 popup
	 *  콮백 함수는 함수 이름으로 작성 해야함.
	 *************************************************************************** */
									</textarea></td>
									<td class="input"><textarea class="ui-textarea">
	abc.postPopup(postNumCheck);

	// Callback 함수 사용법
	function postNumCheck(_param) {
		$.each(_param, function(key, value) {
			console.log("key [" + key + "] : value [" + value + "]");
		});
	}
									</textarea></td>
								</tr>
								<tr>
									<th scope="row">관리자 검색</th>
									<td class="input">
										<button type="button" id="searchAdminBtn" class="btn-sm btn-func btn-dialog">관리자 검색</button>
									</td>
									<td></td>
									<td class="input"><textarea class="ui-textarea">
	/* ***************************************************************************
	 *  15. 관리자 찾기 팝업
	 *************************************************************************** */
									</textarea></td>
									<td class="input"><textarea class="ui-textarea">
	abc.adminSearchPopup(adminSearchCheck);

	// Callback 함수 사용법
	function adminSearchCheck(_param) {
		$.each(_param, function(key, value) {
			console.log("key [" + key + "] : value [" + value + "]");
		});
	}
									</textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!-- E : search-wrap -->

			</div>
		</div>
		<!-- E : container -->

<!-- S : 비밀번호 변경 레이어 -->
<%@include file="/WEB-INF/views/system/login/password-change.jsp" %>
<!-- E : 비밀번호 변경 레이어 -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>
