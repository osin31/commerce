if(!window.abc){
	window.abc = {};
}
/***
 * Common(업무 공통) 함수 정의.
 *
  *  작성되는 스크립트의 내용을 아래와 같이 작성 하여 공유 한다.
 ********************************************************************************
		1. 로그인 처리.
		1-1. po 로그인시 인증번호 발송
		1-2. po 로그인(파라미터 세팅후 로그인)
		2. 로그인 loginValid.
	    3. 로그인 중복체크
	    4. 로그아웃 호출
	    5. 비밀번호 체크(관리자, 메인공통 사용)
		6. 비밀번호 확인 체크(관리자, 메인공통 사용)
		7. 선택한 코드의 하위 코드를 조회 한다.
		8. 선택한 상담유형의 상담스크립 제목을 조회한다.
		9. 선택한 상담 스트립트의 내용을 조회한다.
		10. 주문 상세 팝업
		11. 관리자 상세 정보 popup
		12. 입점사 검색 Pop
		13. 회원  상세 정보 popup
		14. 우편번호 찾기 popup : http://postcode.map.daum.net/guide
		15. 관리자 찾기 popup
		16. 메일 발송 팝업
		17. 문자 메시지 발송 팝업
		18. 유선 상담 등록 팝엄
		19. 입점업체 기본정보 팝업
		20. 입점업체 sms보내기 팝업
		21. 브랜드 찾기 팝업
		22. 상품 찾기 팝업
		23. 입점업체 문의등록 팝업
		24. 일괄답변 팝업
		25. 오프라인 매장 찾기 팝업
		26. 입점업체 이메일보내기 팝업
		27. 업체배송관리 > 업체 주문관리 상세
		28. 쿠폰 검색 팝업
		29. 대상상품관리 팝업
		30. 상품상세 보기 팝업
		31. 대시보드 관리자 공지 팝업
		32. ID를 이용한 관리자 상세 팝업
		33. input[type=number] 일 경우 숫자만 입력 받는 이벤트
		34. 영수증 관련 팝업 호출
		35. 인기상품정보조회 팝업
		36. 옵션변경 Pop
		37. 주문배송 상태 팝업 호출
		38. 상품 FO 미리보기 팝업 호출
		39. 배송정보조회 팝업 호출
		40. 회원상세 화면 탭 리로드
 ********************************************************************************
 */
(function() {

	var _common = window.abc;


	/* ***************************************************************************
	 * 1. 로그인 처리.
	 *************************************************************************** */
	_common.login = function(){

		if(!_common.loginValid()){
			return;
		}

		$.ajax({
			type :"post",
			url : "/login/login-processing",
			data: $("#loginForm").serialize()
		})
		.done(function(data){
			console.log("login : " + data);
			if(data.status == 200){
				if($("#chkSaveID").is(":checked")){
					$.cookie('cLoginId',$('#username').val(),{expires:90});
				}else {
					$.removeCookie('cLoginId');
				}
				if (abc.text.isBlank($('#returnUrl').val())) {
					location.href = '/';
				}else {
					location.href = $('#returnUrl').val();
				}
			}
		})
		.fail(function(e){
			if(e.responseJSON.message == 'dupLoginId'){
				var msg = "로그인 된 계정이 있습니다.\n이전 로그인 계정은 로그아웃 됩니다.\n새로 로그인하시겠습니까?";
				if(confirm(msg)){
					_common.forceLogin();
				}else{
					$("input[name=password]").val("");
				}
			}else if(e.responseJSON.message == '10007'){
				alert("정확한 인증번호 6자리를 입력해주세요.");
			}
		});

	}

	/* ***************************************************************************
	 * 1-1 po 로그인시 인증번호 발송
	 *************************************************************************** */
	_common.validTime = 180;
	// 인증 상태 (0:최초, 1:인증요청중, 2:인증시간만료)
	_common.certifyStatus = 0;

	_common.poLoginCrtfcNo = function(){

		if (_common.certifyStatus == 1) {
		//	alert("인증 요청이 진행중 입니다.");
		//	return;
		}else if(_common.certifyStatus == 2){
			_common.certifyStatus = 0;
		}

		if(!_common.loginValid()){
			return;
		}

		$.ajax({
			type :"post",
			url : "/noacl/getPoLoginCrtfcNo",
			data: $("#loginForm").serialize()
		})
		.done(function(data){
			_common.certifyStatus = 1;
			$('#username').prop('readonly', true);
			$('#password').prop('readonly', true);
			$("#crtfcNoText").prop('readonly', false);
			alert("인증번호가 발송되었습니다.");
			$("#crtfcNoText").val(data);
			$("#reqBtn").text("인증번호 재전송");
			window.abc.countdown($("#countDown"), _common.validTime);
		})
		.fail(function(e){
			console.log("e : " + JSON.stringify(e));
			if(!abc.text.allNull(e.responseJSON.message)){
				alert(e.responseJSON.message);
			}else{
				alert("로그인시 에러가 발생하였습니다.");
			}
			$("input[name=password]").val("");
		});

	}

	// 인증 시간 카운트
	_common.countdown = function($element, duration) {
		_common.certifyStatus = 1
		var timer = duration, minutes, seconds;

		clearInterval(_common.interval);

		_common.interval = setInterval(function() {
			minutes = parseInt(timer / 60, 10);
			seconds = parseInt(timer % 60, 10);

			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;

			var text = minutes + ":" + seconds;
			$element.text(text);

			if (--timer < 0) {
				timer = duration;
				_common.certifyStatus = 2;
				$('#username').prop('readonly', false);
				$('#password').prop('readonly', false);
				$("#crtfcNoText").val("");
				$("#crtfcNoText").prop('readonly', true);
				clearInterval(_common.interval);
				$element.text('');
				alert('제한시간이 만료되었습니다.');
			}
		}, 1000);
	}

	/* ***************************************************************************
	 * 1-2 po 로그인(파라미터 세팅후 로그인)
	 *************************************************************************** */
	_common.poLogin = function(){

		if(!_common.loginValid()){
			return;
		}

		if (_common.certifyStatus == 0) {
			alert("인증 요청후 로그인해주세요.");
			return;
		}
		if (_common.certifyStatus == 2) {
			alert("인증시간이 만료되었습니다.");
			return;
		}

		if(abc.text.isBlank($("input[name=crtfcNoText]").val())){
			alert("인증번호 6자리를 입력해 주세요.");
			$("input[name=crtfcNoText]").focus();
			return;
		}

		$.ajax({
			type :"post",
			url : "/noacl/setPoLoginParameter",
			data: $("#loginForm").serialize()
		})
		.done(function(data){
			_common.login();
		})
		.fail(function(e){
			alert("오류가 발생하였습니다")
		});

	}

	/* ***************************************************************************
	 * 2. 로그인 loginValid.
		***************************************************************************	 */
	_common.loginValid = function(){
		if(abc.text.isBlank($("input[name=username]").val())){
			alert("아이디를 입력해 주십시요.")
			$("input[name=username]").focus();
			return false;
		}else if(abc.text.isBlank($("input[name=password]").val())){
			alert("비밀번호를 입력해 주십시요.")
			$("input[name=password]").focus();
			return false;
		}
		return true;
	}

	/* ***************************************************************************
	 * 3. 중복로그인 로그인 시도
	 *************************************************************************** */
	_common.forceLogin = function(){

		$.ajax({
			type :"POST",
			async : false,
			url : "/noacl/setForceLogin",
			data : $("#loginForm").serialize()
		})
		.done(function(data){
			_common.login();
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
			$("input[name=password]").val("");
			alert("로그인시 에러가 발생하였습니다.")
		});
	}

	/* ***************************************************************************
	 * 4.로그아웃 호출
	 *************************************************************************** */
	_common.logout = function(){

		$.ajax({
			type :"POST",
			async : false,
			url : "/noacl/expire-user-session",
			data : {}
		})
		.done(function(data){
			console.log("data :" + JSON.stringify(data));
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
		})
		.always(function(){
			location.href="/login/logout-processing";
		});

	}

	/* ***************************************************************************
	 * 5. 비밀번호 체크(관리자, 메인공통 사용)
	************************************************************************** */
	_common.checkPassword = function(){
		var isValidPwd;
		var isResult;
		var isBoolean = true;
		var adminNo = $("#adminNo").val();
		var pswdText = $.trim($("#pswdText").val());
		var oldPswdText = $.trim($("#oldPswdText").val());

		// 현재 비밀번호 확인
		if(!abc.text.allNull(adminNo)){

			if(abc.text.allNull(oldPswdText)){
				alert("현재 비밀번호를 입력해야 합니다.");
				$("#oldPswdText").focus();
				return false;
			}

			$.ajax({
				type :"post",
				url : "/noacl/read-checkPswd",
				async : false,
				data : {"adminNo" : adminNo, "pswdText" : oldPswdText},
			})
			.done(function(data){
				if(!data.result){
					alert("현재 비밀번호가 일치하지 않습니다.");
					$("#oldPswdText").focus();
					isBoolean = false;
				}
			})
			.fail(function(e){
				alert("현재 비밀번호 조회에 실패하였습니다.");
				$("#oldPswdText").focus();
				isBoolean = false;
			});

			if(!isBoolean){
				return false;
			}
		}

		if(abc.text.allNull(pswdText)){
			alert("비밀번호를 입력해야 합니다.");
			$("#pswdText").focus();
			return false;
		}

		if(!abc.text.allNull(pswdText)){
			isValidPwd = abc.text.validatePassword(pswdText);
			if(isValidPwd != null){
				alert(isValidPwd);
				$("#pswdText").focus();
				return false;
			}
		}

		// 비밀번호 금지어 체크
		$.ajax({
			type :"post",
			url : "/noacl/read-forbiddenPswd",
			async : false,
			data : {"pswdText" : pswdText},
		})
		.done(function(data){
			if(data.result.first){
				alert("비밀번호 금지어 ["+ data.result.second +"]는 사용할 수 없습니다.");
				$("#pswdText").focus();
				isResult = false;
			}else{
				isResult = true;
			}
		})
		.fail(function(e){
			alert("비밀번호 금지어 조회에 실패하였습니다.");
			$("#pswdText").focus();
			isResult = false;
		});

		if(isResult){
			return true;
		}else{
			return false;
		}
	}

	/* **************************************************************************
	 * 6. 비밀번호 확인 체크(관리자, 메인공통 사용 - 관리자 비밀번호를 조회 체크하는 로직)
	 **************************************************************************  */
	_common.checkConfirmPassword = function(){
		var isBoolean;

		var adminNo = $("#adminNo").val();
		var pswdText = $("#pswdText").val();
		var pswdTextConfirm = $("#pswdTextConfirm").val();

		if(pswdTextConfirm == ""){
			alert("비밀번호 확인을 입력해 주세요.");
			$("#pswdTextConfirm").focus();
			return false;
		}

		if(pswdTextConfirm != ""){
			if(pswdText != pswdTextConfirm){
				alert("비밀번호가 일치하지 않습니다.");
				$("#pswdTextConfirm").focus();
				return false;
			}
		}

		$.ajax({
			type :"post",
			url : "/noacl/read-checkPswd",
			async : false,
			data : {"adminNo" : adminNo, "pswdText" : pswdText},
		})
		.done(function(data){
			if(data.result){
				alert("이전 비밀번호는 사용할 수 없습니다.");
				$("#pswdText").focus();
				isBoolean = false;
			}else{
				isBoolean = true;
			}
		})
		.fail(function(e){
			alert("오류가 발생하였습니다. 관리자에 문의하세요.");
			$("#pswdText").focus();
			isBoolean = false;
		});

		return isBoolean;
	}

	/* **************************************************************************
	 * 6-1. A-Connect 관리자 비밀번호 확인 체크
	 **************************************************************************  */
	_common.connectCheckConfirmPassword = function(){
		var pswdText = $("#pswdText").val();
		var pswdTextConfirm = $("#pswdTextConfirm").val();

		if(pswdTextConfirm == ""){
			alert("비밀번호 확인을 입력해 주세요.");
			$("#pswdTextConfirm").focus();
			return false;
		}

		if(pswdTextConfirm != ""){
			if(pswdText != pswdTextConfirm){
				alert("비밀번호가 일치하지 않습니다.");
				$("#pswdTextConfirm").focus();
				return false;
			}else{
				return true;
			}
		}
	}

	/* **************************************************************************
	  7. 선택한 코드의 하위 코드를 조회 한다.
		 _codeField : 코드 그룹 코드
		 _codeDtlNo : 상위 코드 선택 상세 코드 번호
		 _targetSelectBox : 상세 코드를 노출할 select Box
		 _selectedVal : 선택 값
	 ************************************************************************* */
	_common.setLowerCodeList = function(_codeField, _codeDtlNo, _targetSelectBox, _selectedVal) {
		var _data = "codeField=" + _codeField + "&addInfo1=" + _codeDtlNo;

		$.ajax({
			url: "/noacl/code-detail-list",
			dataType : "json",
			data: _data,
			async:false
		}).done(function(data, textStatus, jqXHR) {

			var _len = $("#" + _targetSelectBox + " option").length;

			for(var idx = _len -1; idx > 0; idx --) {
				$("#" + _targetSelectBox + " option:eq(" + idx + ")").remove();
			}

			if (! abc.text.isBlank(_codeDtlNo)) {
				$.each(data, function() {
					if (_selectedVal != this.codeDtlNo) {
						$("#" + _targetSelectBox).append("<option value='" + this.codeDtlNo + "'>" + this.codeDtlName + "</option>");
					} else {
						$("#" + _targetSelectBox).append("<option value='" + this.codeDtlNo + "' selected>" + this.codeDtlName + "</option>");
					}
				});
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			console.log(jqXHR.responseJSON);
		});
	}

	/* **************************************************************************
	   8. 선택한 상담유형의 상담스크립 제목을 조회한다.
		 _cnslTypeCode : 상담유형코드
		 _cnslTypeDtlCode : 상담유형상세코드
		 _targetSelectBox : 상담스크립 제목울 노출할 select Box 이름
	************************************************************************** */
	_common.getCounselScriptTitle = function(_cnslTypeCode, _cnslTypeDtlCode, _targetSelectBox) {
		var _data = "cnslTypeCode=" + _cnslTypeCode + "&cnslTypeDtlCode=" + _cnslTypeDtlCode;
		$.ajax({
			url: "/noacl/counselscript/read-cnslscriptcontitle",
			dataType : "json",
			data: _data,
			async: false
		}).done(function(data, textStatus, jqXHR) {
			var _len = $("#" + _targetSelectBox + " option").length;

			for(var idx = _len -1; idx > 0; idx --) {
				$("#" + _targetSelectBox + " option:eq(" + idx + ")").remove();
			}
			$.each(data, function() {
				$("#" + _targetSelectBox).append("<option value='" + this.cnslScriptSeq + "'>" + this.cnslScriptTitleText + "</option>");
			});
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			console.log(jqXHR.responseJSON);
		});
	}

	/* ***************************************************************************
	 *  9. 선택한 상담 스트립트의 내용을 조회한다.
		  _cnslScriptSeq : 상담스크립트 번호
		  _targetTextArea : 상담스크립트 내용을 노출할 TextArea
	 *************************************************************************** */
	_common.getCounselScript = function(_cnslScriptSeq, _targetTextArea) {
		var _data = "cnslScriptSeq=" + _cnslScriptSeq;

		$.ajax({
			url: "/noacl/counselscript/read-cnslscriptconttext",
			dataType : "json",
			data: _data
		}).done(function(data, textStatus, jqXHR) {
			if (data.cmCounselScript != null) {
				$("#" + _targetTextArea).val(data.cmCounselScript.cnslScriptContText);
			} else {
				$("#" + _targetTextArea).val("");
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.");
			console.log(jqXHR.responseJSON);
		});
	}


	/* ***************************************************************************
	 *  10. 주문 상세 팝업
		   ibsheetObject :  ibsheet Name
			saveName : Grid Save Name
		   Row : row
		   Col : col
		   strParam  : 예시- aaa=aaa1&bbb=bbb2 or null
	 *************************************************************************** */
	_common.orderDetailPopup = function(ibsheetObject,saveName,Row,Col,strParam){

		var url = "/order/read-detail-pop";
		var params = {}

		//파라미터 파싱
		if( !abc.text.allNull( strParam)){
			 params = abc.param.getParams("?"+strParam);
		}else{
			params.orderNo =  ibsheetObject.GetCellValue(Row, saveName);
		}

		abc.open.popup({
			winname :	"주문 상세 팝업",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  11. 관리자 상세 정보 popup
		    ibsheetObject : ibsheet Name
			saveName : Grid SaveName
		    Row : row
		    Col : col
		    strParam  : 예시- aaa=aaa1&bbb=bbb2 or null
	 *************************************************************************** */
	_common.adminDetailPopup = function(adminNo){

		var url = "/noacl/admin-detail-noauth-pop";
		var params = {};
		params.adminNo = adminNo;

		abc.open.popup({
			winname :	"adminDetailPop",
			url 	:	url,
			width 	:	830,
			height	:	500,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  12. 입점사 검색 Pop
	 *  _param {
	 *  	multiple	: 다중선택여부.	ex) true
	 *  	callback	: 콜백함수명.		ex) "abc.biz.vendor.serch.callback"
	 *  }
	 *************************************************************************** */
	_common.vndrSearchPopup = function(multiple, callback) {

		var url = "/noacl/vendor-search-pop";
		var _params = {
				"multiple" : multiple,
				"callback" : callback
			}
		abc.open.popup({
			url 	:	url,
			winname :	"find-vendor",
			title 	:	"find-vendor",
			method	: 	"get",
			width 	:	1000,
			height	:	842,
			params	:	_params
		});

	}

	/* ***************************************************************************
	 *  13. 회원  상세 정보 popup
		    ibsheetObject : ibsheet Name
			saveName : Grid SaveName
		    Row : row
		    Col : col
		    strParam  : 예시- aaa=aaa1&bbb=bbb2 or null
	 *************************************************************************** */
	_common.memberDetailPopup = function(memberNo){

		var url = "/member/member-detail-pop";
		var params = {};
		params.memberNo = memberNo;

		abc.open.popup({
			winname :	"memberDetailPop",
			url 	:	url,
			width 	:	1480,
			height	:	985,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  14. 우편번호 찾기 popup
	 *************************************************************************** */
	_common.postPopup = function(callback) {
		daum.postcode.load(function(){
	        new daum.Postcode({
	            oncomplete: function(data) {
		            var addr = '';
		            // 지번주소를 선택하더라도 도로명 주소를 반환하도록 수정.
		            if (data.roadAddress != "") {
		                addr = data.roadAddress;
		            } else {
		            	addr = data.autoRoadAddress;
		            }

		            var result = {
		            	 postAddress : addr
		            	,postCode : data.zonecode
		            	,buildingCode : data.buildingCode // 건물번호는 필요시 사용한다. (건물에 부여한 25자리 고유번호)
		            };
					callback(result);
	            }
	        }).open();
	    });
	}


	/* ***************************************************************************
	 *  15. 관리자 찾기 팝업
	 *************************************************************************** */
	_common.adminSearchPopup = function(callbackFunction) {
		if(typeof callbackFunction != "string"){
			alert("콜백함수명이 문자열이 아닙니다");
			return;
		}

		var url = "/noacl/read-list-pop-form";
		var _params = {}
		_params.callbackFunction = callbackFunction;

		abc.open.popup({
			winname :	"adminSearch",
			url 	:	url,
			width 	:	800,
			height	:	880,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  16. 메일 발송 팝업
	 *  siteNo : 사이트 구분을 해서 전송 할 경우 필수
	 *  rcvrMemberNo : 비회원일 경우  Const.NON_MEMBER_NO 에 정의된 회원 번호
	 *  rcvrEmailAddrText, rcvrName : 비 회원일 경우 필수
	 *  _param {
	 *  	siteNo : 사이트 번호,
	 *  	rcvrMemberNo : 수신자 회원번호,
	 *  	rcvrEmailAddrText : 수신자 메일 주소
	 *  	rcvrName : 수신자 이름
	 *  }
	 *************************************************************************** */
	_common.sendMailPopup = function(_params) {
		var url = "/noacl/email-send-pop-form";

		if (typeof _params != "object") {
			alert("메일 대상자 정보가 정확 하지 않습니다.");
			return false;
		}

		if(abc.text.isBlank(_params.rcvrMemberNo)) {
			alert("회원 번호는 필수 입니다.")
			return false;
		}

		if (_params.rcvrMemberNo == abc.consts.NON_MEMBER_NO || abc.text.isBlank(_params.rcvrMemberNo)) { // 비회원 일 경우 메일 주소
			if (typeof _params.rcvrEmailAddrText == "undefined" || abc.text.isBlank(_params.rcvrEmailAddrText)) {
				alert("비회원일 경우 수신자 메일 주소는 필수 입니다.");
				return false;
			}

			if (typeof _params.rcvrName == "undefined" || abc.text.isBlank(_params.rcvrName)) {
				alert("비회원일 경우 수신자 이름은 필수 입니다.");
				return false;
			}
		}

		abc.open.popup({
			winname :	"emailSendForm",
			url 	:	url,
			width 	:	1000,
			height	:	800,
			params	:	_params
		});
	}

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

	_common.sendSmsPopup = function(_params) {
		var url = "/noacl/text-msg-send-pop-form";

		console.log("_params.memberNo : " + _params.memberNo);

		if (typeof _params != "object") {
			alert("문자 대상자 정보가 정확 하지 않습니다.");
			return false;
		}

		if(abc.text.isBlank(_params.memberNo)) {
			alert("회원 번호는 필수 입니다.")
			return false;
		}

		if (_params.memberNo == abc.consts.NON_MEMBER_NO || abc.text.isBlank(_params.memberNo)) { // 비회원 일 경우 메일 주소
			if (typeof _params.recvTelNoText == "undefined" || abc.text.isBlank(_params.recvTelNoText)) {
				alert("비회원일 경우 받는 휴대폰 번호	는 필수 입니다.");
				return false;
			}

			if (typeof _params.rcvrName == "undefined" || abc.text.isBlank(_params.rcvrName)) {
				alert("비회원일 경우 수신자 이름은 필수 입니다.");
				return false;
			}
		}

		abc.open.popup({
			winname :	"smsSendForm",
			url 	:	url,
			width 	:	600,
			height	:	700,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  18. 유선상담 등록 팝업
	 *  _param {
	 *  	memberNo : 회원번호
	 *  }
	 *************************************************************************** */
	_common.registMemberCounsel = function(memberNo){
		var url = "/noacl/member-counsel-pop";
		var params = {}
		params.memberNo = memberNo;
		params.viewType = abc.consts.CRUD_C;

		abc.open.popup({
			winname :	"counselPop",
			url 	:	url,
			width 	:	1270,
			height	:	820,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  19. 입점업체 기본정보 팝업
	 *  _param {
	 *  	vndrNo : 업체번호
	 *  }
	 *************************************************************************** */
	_common.vendorInfoPop = function(vndrNo){
		if(abc.text.isBlank(vndrNo)) {
			alert("업체 번호는 필수 입니다.")
			return false;
		}
		var url = "/noacl/vendor-info-pop";
		var params = {}
		params.vndrNo = vndrNo;

		abc.open.popup({
			winname :	"vendorInfoPop",
			url 	:	url,
			width 	:	1270,
			height	:	820,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  20. 입점업체 sms보내기
	 *  _param {
	 *  	obj : 업체번호 , 업체명
	 *  }
	 *************************************************************************** */
	_common.sendVendorSmsPop = function(obj){
		if(abc.text.isBlank(obj)) {
			alert("업체 번호는 필수 입니다.")
			return false;
		}
		var url = "/vendor/info/sms-send-pop";

		abc.open.popup({
			winname :	"smsVendorPop",
			url 	:	url,
			width 	:	600,
			height	:	600,
			params	:	obj
		});
	}

	/* ***************************************************************************
	 *  21. 브랜드 찾기 팝업
	 *  _param {
	 *  	isMultiple	: 다중선택여부.	ex) true
	 *  	callback	: 콜백함수명.		ex) "abc.biz.product.brand.callback"
	 *  }
	 *************************************************************************** */
	_common.brandSearchPopup = function(isMultiple, callback) {

		var url = "/noacl/brand/find";
		var _params = {
			"isMultiple" : isMultiple,
			"callback" : callback
		}

		var popupBrand = abc.open.popup({
			url 	:	url,
			winname :	"find-brand",
			title 	:	"find-brand",
			method	: 	"get",
			width 	:	645,
			height	:	750,
			params	:	_params
		});
		return popupBrand;
	}

	/* ***************************************************************************
	 *  22. 상품 찾기 팝업
	 *  _param {
	 *  	isMultiple		: 다중선택여부.		ex) true
	 *  	callback		: 콜백함수명.			ex) "abc.biz.product.brand.callback"
	 *  	isHideSearch	: 검색영역 숨김여부		ex) false
	 *  	param			: 추가파라미터			ex) { searchItemType : "style-info", searchItemKeyword : "1234" }
	 *  }
	 *************************************************************************** */
	_common.productSearchPopup = function(isMultiple, callback, isHideSearch, param) {

		var window = null;
		var url = "/noacl/product/find";
		var _params = {
				"isMultiple"		: isMultiple,
				"callback"			: callback,
				"isHideSearch"		: isHideSearch != undefined ? isHideSearch : false
		}

		if(param != undefined && param != null && param != {}) {
			$.extend(_params, param);
		}

		window = abc.open.popup({
			url 	:	url,
			winname :	"find-product",
			title 	:	"find-product",
			method	: 	"get",
			width 	:	1400,
			height	:	1000,
			params	:	_params
		});

		return window;
	}


	/* ***************************************************************************
	 *  23. 입점업체 문의 등록 팝업
	 *  _param {
	 *  		vndrNo : 업체번호
	 *  }
	 *************************************************************************** */
	_common.vendorInquiryPopup = function(vndrNo) {

		var url = "/noacl/cowork-create-form";
		var _params = {
				"vndrNo" : vndrNo
		}

		abc.open.popup({
			url 	:	url,
			winname :	"vendorInquiry",
			title 	:	"vendorInquiry",
			method	: 	"get",
			width 	:	1000,
			height	:	900,
			params	:	_params
		});
	}


	/* ***************************************************************************
	 *  24. 일괄답변 팝업
	 *  _param {
	 *  	callback	: 콜백함수명.		ex) "abc.biz.product.review.callback"
	 *  }
	 *  해당 팝업객체를 호출한 곳에서 받아줘야 callback함수안에서 alert제어가 가능하다.
	 *************************************************************************** */
	_common.batchAswrPopup = function(callback) {

		var url = "/noacl/review/aswr/";
		var _params = {
				"callback" : callback
		}

		var win = abc.open.popup({
			url 	:	url,
			winname :	"aswr-review",
			title 	:	"aswr-review",
			method	: 	"get",
			width 	:	645,
			height	:	480,
			params	:	_params
		});

		return win;

	}

	/* ***************************************************************************
	 *  25. 오프라인 매장 찾기 팝업
	 *  _param {
	 *  	callback	: 콜백함수명.	ex) "storeCallback"
	 *  	isMultiple	: 다중선택여부.	ex) true, false
	 *  }
	 *************************************************************************** */
	_common.storeSearchPopup = function(callback, isMultiple) {

		var url = "/noacl/store/find";
		var _params = {
				"callback" : callback,
				"isMultiple" : isMultiple
		}

		abc.open.popup({
			url 	:	url,
			winname :	"find-store",
			title 	:	"find-store",
			method	: 	"get",
			width 	:	1240,
			height	:	950,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  26. 입점업체 이메일보내기 팝업
	 *  _param {
	 *  	obj : 업체번호 , 업체명
	 *  }
	 *************************************************************************** */
	_common.sendVendorEmailPop = function(obj){
		if(abc.text.isBlank(obj)) {
			alert("업체 번호는 필수 입니다.")
			return false;
		}
		var url = "/vendor/info/email-send-pop";

		abc.open.popup({
			winname :	"emailVendorPop",
			url 	:	url,
			width 	:	800,
			height	:	600,
			params	:	obj
		});
	}

	/* ***************************************************************************
	 *  27. 업체 주문  상세 팝업
		ibsheetObject :  ibsheet Name
		saveName : Grid Save Name
		Row : row
		Col : col
		strParam  : 예시- vendor=aaa1
	 *************************************************************************** */
	_common.orderVendorDetailPopup = function(ibsheetObject,saveName,Row,Col,strParam){

		var url = "/delivery/vendor/order/read-order-vendor-detail-pop";
		var params = {}

		//파라미터 파싱
		if( !abc.text.allNull( strParam)){
			 params = abc.param.getParams("?"+strParam);
		}else{
			params.orderNo =  ibsheetObject.GetCellValue(Row, saveName);
		}

		abc.open.popup({
			winname :	"업체 주문 상세 팝업",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  28. 쿠폰 검색 팝업
	 *  _param {
	 *  	callback	: 콜백함수명
	 *  }
	 *************************************************************************** */
	_common.couponSearchPopup = function(isMultiple, callback) {

		var url = "/promotion/coupon/popup";
		var _params = {
			"multiple" : isMultiple,
			"callback" : callback
		}

		abc.open.popup({
			url 	:	url,
			winname :	"find-coupon",
			title 	:	"find-coupon",
			method	: 	"get",
			width 	:	1240,
			height	:	950,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  29. 대상상품관리 팝업
	 *  _param = {
	 *	    name						: "브랜드 스타일",										// 상품관리명
	 *	    count						: 0,												// 대상상품전체갯수
	 *	    searchFormId				: "target-product-search-form",						// 조회 조건 FORM ID
	 *	    searchUrl					: "/noacl/product/brand/management/product/target",	// 조회 URI. 공통유틸에서 POST를 이용하여 통신함
	 *	    saveAdditionalParamFormId	: "target-product-save-additional-parameter-form",	// 저장 시 전달 할 파라미터가 설정된 FORM ID
	 *	    saveUrl						: "/noacl/product/brand/management/product/target",	// 저장 URI. API에서 POST를 이용하여 통신함
	 *	    isHideSortSeq				: true												// 노출순서 숨김 여부
	 *	}
	 *************************************************************************** */
	_common.targetProductManagementPopup = function(_param) {

		var url = "/noacl/product/management/product/target";

		abc.open.popup({
			url 	:	url,
			winname :	"target-product-management",
			title 	:	"target-product-management",
			method	: 	"get",
			width 	:	1400,
			height	:	715,
			params	:	_param
		});
	}

	/* ***************************************************************************
	 *  30. 상품상세 보기 팝업
	 *  _param = {
	 *	    prdtNo			: "2000000022",	// (필수) 상품번호
	 *	    siteNo			: "10000",		// (선택) 사이트번호
	 *	    chnnlNo			: "10000",		// (선택) 채널번호
	 *	    vndrPrdtNoText	: "1000PRDT01"	// (선택) 내부관리번호
	 *************************************************************************** */
	_common.readonlyProductDetailPopup = function(_param) {

		var url = "/noacl/product/detail";

		abc.open.popup({
			url 	:	url,
			winname :	"readonly-product-detail",
			title 	:	"readonly-product-detail",
			method	: 	"get",
			width 	:	1480,
			height	:	970,
			params	:	_param
		});
	}

	/* ***************************************************************************
	 *  31. BO, PO 대시보드 관리자 공지 팝업
	 *  args = array [관리자공지일련번호]
	 *************************************************************************** */
	_common.mainPopup = function(args) {
		var _url = "/bo/dashboard/read-detail-pop";
		var _width = 500;
		var array = Array.isArray(args) ? args : [];
		var index = 0;

		array.forEach(function (element) {
			var _winname = "adminNoticeMainPop"+element;
			var cookie = abc.cookie.getCookie(_winname);
			if (!cookie) {
				var _params = {
					mainPopup: true,
					adminNotcSeq: element
				};

				abc.open.popup({
					winname: _winname,
					url: _url,
					width: _width,
					height: 600,
					top: 10,
					left: 10 + index * _width,
					params:	_params
				});
				index += 1;
			}
		});
	}

	/* ***************************************************************************
	 *  32. BO 관리자 상세 팝업 공통 링크[id:linkAdmin]
	 *************************************************************************** */
	_common.link = function(){
		//관리자 링크
		$('#linkAdmin').on('click', function() {
			var adminNo = this.value;
			if(adminNo != ''){
				_common.adminDetailPopup(adminNo);
			}
			return false;
		});
	}

	$(function() {
		_common.link();
	});

	/* ***************************************************************************
	 *  33. input[type=number] 일 경우 숫자만 입력 받는 이벤트
	 *************************************************************************** */
	_common.inputTypeNumberOnlyNumber = function(){

		$('[type=number]').on('keypress', function(e){
        	var key = window.event ? event.keyCode : event.which;
            if (event.keyCode === 8 || event.keyCode === 46) {
                return true;
            } else if ( key < 48 || key > 57 ) {
                return false;
            } else {
                return true;
            }
        });
	}

	$(function() {
		_common.inputTypeNumberOnlyNumber();
	});


	/* 실결제시 : "https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=" */
    /* 테스트시 : "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=" */
	/* ***************************************************************************
	 *  34. 매출전표 - 영수증 호출 팝업
	 *      tno			:  거래번호
	 *	    orderNo			: 주문번호
	 *	    amount			: 결제금액
	 *	    billType	: 전표 구분 - billType
	 *								 card_bill - 카드결제, acnt_bill - 계좌이체
	 *								vcnt_bill - 가상계좌 , mcash_bill - 휴대폰결제
	 *     callType :  AS - AS결제 호출, ORDDER - 주문 , GIFT - 기프트카드
	 *************************************************************************** */
	_common.receiptView = function( tno, orderNo, amount , billType , callType ) {
        receiptWin = "https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=";
        receiptWin += tno + "&";
        receiptWin += "order_no=" + ordr_idxx + "&";
        receiptWin += "trade_mony=" + amount ;

        window.open(receiptWin, "", "width=455, height=815");
    }

	/* ***************************************************************************
	 *  35. 인기상품조회 팝업
	 *  _param {
	 *  	isMultiple	: 다중선택여부.	ex) true
	 *  	callback	: 콜백함수명.		ex) "abc.biz.product.brand.callback"
	 *  }
	 *************************************************************************** */
	_common.productPopularSearchPopup = function(isMultiple, callback) {

		var _params = {
				"isMultiple" : isMultiple,
				"callback" : callback
		}

		abc.open.popup({
			url 	:	"/display/contents/popup/corner-product-popular-pop",
			winname :	"corner-product-popular-pop",
			title 	:	"corner-product-popular-pop",
			method	: 	"get",
			width 	:	1400,
			height	:	1000,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  36. 옵션변경 Pop
		    orderNo : 주문번호 , orderPrdtSeq : 주문상품번호
		    callbackFunction : opener 바닥창으로 콜백해줄 function 명 (String)
		    strParam : 추가 변수
	 *************************************************************************** */
	_common.optionChangePopup = function(orderNo , orderPrdtSeq , callbackFunction , strParam , layerType) {
		if(typeof callbackFunction != "string"){
			alert("콜백함수명이 문자열이 아닙니다");
			return;
		}
		var url = "/order/option-change";
		var _params = {};
		_params.orderNo = orderNo;
		_params.orderPrdtSeq = orderPrdtSeq;
		_params.callbackFunction = callbackFunction;
		_params.strParam = strParam;
		_params.layerType = layerType;

		abc.open.popup({
			winname :	"옵션변경",
			url 	:	url,
			width 	:	640,
			height	:	745,
			params	:	_params
		});
	}

	/* ***************************************************************************
	 *  37.  주문배송 상태 팝업 호출
		    orderNo : 주문번호 , orderPrdtSeq : 주문상품번호 , orderPrdtStatCode : 주문상품 상태(미사용)

	 *************************************************************************** */
	_common.orderProductHistoryPopup = function(orderNo, orderPrdtSeq,orderPrdtStatCode){

		var url = "/order/delivery-history";
		var params = {}

		params.orderNo 		=  orderNo;
		params.orderPrdtSeq =  orderPrdtSeq;

		abc.open.popup({
			winname :	"상품 배송 이력",
			url 	:	url,
			width 	:	800,
			height	:	670,
			params	:	params
		});
	}

	/* ***************************************************************************
	 *  38. 상품 FO 미리보기 팝업 호출
		    prdtNo : 상품번호, isPopup : 팝업여부

	 *************************************************************************** */
	_common.productFrontUrl = function(prdtNo, isPopup) {
		// 프론트 상품상세 열기
		$.ajax({
			url				: "/product/product/front/url",
			method			: "GET",
			data			: { "prdtNo" : prdtNo }
		}).done(function(data) {
			if(data && data.success === true) {
				if(isPopup) {
					// 팝업 열기
					abc.open.popup({
						url			: data.data.split("?")[0],
						winname		: "product-front",
						method		: "get",
						title		: "product-front",
						width		: 1800,
						height		: 1000,
						params		: { prdtNo : data.data.split("=")[1] }
					});
				} else {
					// 새창 열기
					window.open(data.data, "productFront");
				}
			} else {
				alert("프론트 URL 조회에 실패하였습니다.");
				console.log(data);
			}
		}).fail(function(data) {
			alert("프론트 URL 조회에 실패하였습니다.");
			console.log(data);
		});
	}

	/* ***************************************************************************
	 *  39. 배송정보 조회 팝업 호출

	 *************************************************************************** */
	_common.dlvyInfoInquiry = function(code, invoice) {
		if(code == abc.consts.DLVY_CODE_CJ) {
			var url = abc.consts.CJ_DOORTODOOR_DLVY_URL + invoice;
			var option = 'width=500, height=800';

			window.open(url, '_blank', option);

		} else {
			$.ajax({
				url : '/mypage/get-sweettracker-key',
				method : 'post'
			}).done(function(data){
				var _params = {
						t_key		: data.t_key,
						t_code		: code,
						t_invoice 	: invoice
				};

				abc.open.popup({
					url      :   abc.consts.SWEETTRACKER_URL,
					method   :   'post',
					width    :   500,
					height   :   800,
					params   :   _params
				});
			}).fail(function(jqXHR, textStatus, errorThrown){
				abc.errorMessage();
			});
		}

	}

	/* ***************************************************************************
	 *  40. 회원상세 화면 탭 리로드

	 *************************************************************************** */
	_common.memberDetailTabReload = function(tabId){

		var url;
		var tabIndex = $("#tabID").tabs('option', 'active');
		var memberNo = $("#memberNo").val();
		var safeKey = $("#safeKey").val();
		var safeKeySeq = $("#safeKeySeq").val();
		var memberTypeCode = $("#memberTypeCode").val();
		var tabId = $(".tab-wrap ul li a").eq(tabIndex).attr("href").replace("#","");

		if(tabId == "tabMember"){
			// 회원정보
			url = "/member/read-member-info-tab";
		} else if(tabId == "tabOrder"){
			// 주문내역
			url = "/member/read-order-info-tab";
		} else if(tabId == "tabGiftCardPurchase"){
			// 기프트카드 구매내역
			url = "/member/giftcard/read-giftcard-info-tab";
		} else if(tabId == "tabInquiry"){
			// 문의내역
			url = "/member/read-inquiry-info-tab";
		} else if(tabId == "tabProdInquiry"){
			// 상품 Q&A
			url = "/member/read-prod-inquiry-info-tab";
		} else if(tabId == "tabProdReview"){
			// 상품후기
			url = "/member/read-prod-review-info-tab";
		} else if(tabId == "tabCoupon"){
			// 쿠폰관리
			url = "/member/read-coupon-info-tab";
		} else if(tabId == "tabPoint"){
			// 포인트 관리
			url = "/member/read-point-info-tab";
		} else if(tabId == "tabEvent"){
			// 이벤트 내역
			url = "/member/read-event-info-tab";
		}

		$.ajax({
			type :"post",
			url : url,
			data : {"memberNo" : memberNo, "safeKey" : safeKey, "memberTypeCode" : memberTypeCode, "safeKeySeq" : safeKeySeq},
			dataType : "html",
			async : false
		})
		.done(function(data){
			$("#" + tabId).html(data);
			$(".ui-cal").each(function(){
				abc.namespace.front.backOffice.setDatepicker(this);
			});

			$(".btn-search-toggle").each(function(){
				abc.namespace.front.backOffice.setSearchZoneViewToggleBtn(this);
			});
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			var responseJSON = JSON.parse(jqXHR.responseText);
			alert(responseJSON.message);
		});
	}
	
	
	/* ***************************************************************************
	 *  41. 상품상세 보기 팝업 - 수정가능
	 *  _param = {
	 *	    prdtNo			: "2000000022",	// (필수) 상품번호
	 *	    siteNo			: "10000",		// (선택) 사이트번호
	 *	    chnnlNo			: "10000",		// (선택) 채널번호
	 *	    vndrPrdtNoText	: "1000PRDT01"	// (선택) 내부관리번호
	 *************************************************************************** */
	_common.modProductDetailPopup = function(prdtNo,siteNo,chnnlNo,vndrPrdtNoText) {

		if(abc.text.allNull(prdtNo)){
			alert("상품번호는 필수입니다.");
			return;
		}
		
		if(abc.text.allNull(siteNo)){
			alert("siteNo는 필수입니다.");
			return;
		}
		
		if(abc.text.allNull(chnnlNo)){
			alert("chnnlNo는 필수입니다.");
			return;
		}
		
		if(abc.text.allNull(vndrPrdtNoText)){
			alert("내부 상품코드는 필수입니다.");
			return;
		}
		
		var param = {
				prdtNo			: prdtNo,			// 상품번호
				siteNo			: siteNo,			// 사이트번호
				chnnlNo			: chnnlNo,			// 채널번호
				vndrPrdtNoText	: vndrPrdtNoText	// 입점사관리번호
			};

		abc.open.popup({
			url 	:	"/product/product/detail",
			winname :	"product-detail",
			method	: 	"get",
			title 	:	"product detail",
			width 	:	1480,
			height	:	970,
			params	:	param
		});
	}

})();

