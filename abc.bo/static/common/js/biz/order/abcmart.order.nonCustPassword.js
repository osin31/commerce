/***
 * 관리자 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _order = abc.object.createNestedObject(window,"abc.order.nonCustPassword");
	
	
	$("#passwordSave").click(function(){
		var pswdText =  $("#pswdText").val();
				
		var pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]/;
		if(pswdText.length != 6) {
			alert("비밀번호 6자리 입력 필수 입니다.") ;
			$("#pswdText").focus();
			return;
		}
		if(pattern.test(pswdText)){
			alert("영문 숫자 특수문자 외에 다른 문자가 있습니다.");
			$("#pswdText").focus();
			return;
		}
		
		$.ajax({
			type :"post",
			url : "/order/passwordChange",
			data: $("#orderForm").serialize()
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
	});
	
})();