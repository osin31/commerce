/***
 * 로그인 비밀번호변경
 *
 */
(function() {

	var _pwdchange = abc.object.createNestedObject(window,"abc.biz.system.pwdchange");

	_pwdchange.popupopen;
	/***
	 * 로그인 비밀번호변경 저장
	 */
	_pwdchange.pswdChangeBtn = function() {

		// 비밀번호 유효성 체크
		if(!window.abc.checkPassword()){
			return false;
		}

		// 비밀번호 확인 유효성 체크
		if(!window.abc.checkConfirmPassword()){
			return false;
		}

		$.ajax({
			type :"post",
			url : "/noacl/login/password-update",
			data: $("#pswdChangeForm").serialize()
		})
		.done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				alert("수정되었습니다.");
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			alert("오류가 발생하였습니다");
		});
		_pwdchange.popupopen = "N";
		$('body').removeClass('dialog-open');
	}
})();