(function() {


	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.miss.proc.popup");
	var alertSaveOk					= "분실 재배송 처리가 완료 되었습니다.";
	var alertSaveFail				= "분실 재배송 처리가 실패 되었습니다.";
	var alertCancelSaveOk			= "분실 취소 처리가 완료 되었습니다.";
	var alertCancelSaveFail			= "분실 취소 처리가 실패 되었습니다.";

    var booleanCertify = false;  //인증 처리 진행 상태
    var booleanCertifyAct = false;   //인증처리 중복 방지 상태

	/*	비회원인 경우                         : isAcntAuth = "N" 
	 *	회원이지만 환불계좌가 없는 경우 : isAcntAuth = "N"
	 *  회원인데 환불계좌가 있는 경우    : isAcntAuth = "Y"
	 *  본 팝업화면에서 환불계좌 인증    : isAcntAuth = "Y"
	 */ 
    _deliveryJsObject.isAcntAuth = "";
    
	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	

	}; //end init

	//계좌 정보 인증
	$("#accountCertifyBtn").click(function(e) {

		if (booleanCertifyAct) {
			 alert("계좌 인증 처리 중입니다.");
			 return;
		 }

		 if (booleanCertify) {
			 alert("이미 인증 처리된 상태입니다.");
			 return;
		 }
		_deliveryJsObject.accountCertify(this);
	});

	/*
	 취소
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel


	 //휴대폰 번호 숫자만
	$("#rfndAcntText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	}); //end if #rcvrHdphnNoText
 
	// 환불계좌항목 validate
	_deliveryJsObject.validateRfndAcnt = function() {
		
		if( abc.biz.delivery.miss.proc.popup.isAcntAuth == "N" ){
			alert("환불계좌정보를 인증해주세요.");
			return false;
		}
		
		return true;
	}

	/*
	  분실 취소 처리
	*/
	 $("#deliveryMissCancel").click(function(){
		  //필수항목을 체크하기
			var sCondParam=FormQueryString(document.gForm, true);
			if(sCondParam==undefined) return;
		  
			// 환불계좌항목 validate
			if( _deliveryJsObject.validateRfndAcnt() == false ){
				return;
			}
			
			if(!confirm("해당 상품들을 분실취소처리 하시겠습니까?")) {
				return;
			}
			
				var callbackFunc = document.gForm.callBackFunc.value;;

				$.ajax({
					type 	:"post",
					data 	: $("#gForm").serialize() ,
					url		:"/claim/claim/regist-claim-miss-cancel"
				})
				.done(function(data){

					if(data.success == "Y" ) {
						alert( alertCancelSaveOk );

						if(callbackFunc != "" ) {
							// 바닥창 콜백 함수 호출하면서 파라미터 전달
							var cbf = callbackFunc;
							var openerFunc = null;
							
							if(typeof(opener.window[cbf])!="undefined"){
								openerFunc = opener.window[cbf];
							}
							openerFunc();
						} else{
							if(window.opener != null) {
								opener.location.reload();
							}
						}
		
						window.close(); 
					}else{
						alert( alertCancelSaveFail);
					}
				})
				.fail(function(e){
					alert( alertCancelSaveFail);
				});

	 }) //end #deliveryMissCancel


	 
	/*
	  분실재배송
	*/ 
	 $("#deliveryMissReDelivery").click(function(){
		
		//필수항목을 체크하기
		var sCondParam=FormQueryString(document.gForm, true);
		if(sCondParam==undefined) return;
 
		// 환불계좌항목 validate
		/*
		if( _deliveryJsObject.validateRfndAcnt() == false ){
			return;
		}
		*/
		if(!confirm("해당 상품들을 분실재배송처리 하시겠습니까?")) {
			return;
		}
		
		    var callbackFunc = document.gForm.callBackFunc.value;;

			$.ajax({
				type 	:"post",
				data 	: FormQueryStringEnc(document.gForm),
				url		:"/delivery/delivery-miss-proc-popup/update"
			})
			.done(function(data){

				if(data.result == "Y" ) {
					alert( alertSaveOk );

					if(callbackFunc != "" ) {
						// 바닥창 콜백 함수 호출하면서 파라미터 전달
						var cbf = callbackFunc;
						var openerFunc = null;
						
						if(typeof(opener.window[cbf])!="undefined"){
							openerFunc = opener.window[cbf];
						}
						openerFunc();
					}else{
						if(window.opener != null) {
							opener.location.reload();
						}
					}
	
					window.close(); 
				}else{
					alert( alertSaveFail);
				}
			})
			.fail(function(e){
				alert( alertSaveFail);
			});

		 }) //end #pupupSave
 	
   /**
     * 계좌인증
     */
    _deliveryJsObject.accountCertify = function(){
 
    	if( abc.text.isBlank($("#bankCode").val()) ) {
			alert("은행을 선택하세요.");
			$("#bankCode").focus();
			return false;
		}
    	if( abc.text.isBlank($("#rfndAcntText").val()) ) {
			alert("계좌번호를 입력하세요.");
			$("#rfndAcntText").focus();
			return false;
		}
    	if( abc.text.isBlank($("#acntHldrName").val()) ) {
			alert("예금주를 입력하세요.");
			$("#acntHldrName").focus();
			return false;
		}
       
	   var booleanCertifyAct = true;

		$.ajax({
			type	 : "post",
			url		 : "/claim/claim/claim-account-auth",
			async 	 : false,
			dataType : "json",
			data	 : {
					     bankCode : $("#bankCode").val()
					   ,acntNoText : $("#rfndAcntText").val()
					   ,acntHldrName : $("#acntHldrName").val()
				       }
		})
		.done(function(data, textStatus, jqXHR){
			var flag 	= data.flag;
			
			if(flag == 'Y'){
				alert("인증되었습니다.");
				booleanCertify = true;
				
				$("#rfndAcntText").attr("readonly", true);
				$("#acntHldrName").attr("readonly", true);
				
				// 인증된 은행값만 제외하고 나머지 삭제
				var bankAddInfo4 = $("#bankCode option:selected").val();
				var bankCodeName = $("#bankCode option:selected").text();
				$("#bankCode").empty();
				$("#bankCode").append("<option value='" + bankAddInfo4 + "'>" + bankCodeName + "</option>");
				
				// 계좌인증 버튼 remove
				$("#accountCertifyBtn").remove();
				
				abc.biz.delivery.miss.proc.popup.isAcntAuth = "Y";
				
			}else{
				alert("계좌 인증에 실패하였습니다.");
				
				abc.biz.delivery.miss.proc.popup.isAcntAuth = "N";
			}
		})
		.fail(function(jqXHR, textStatus, error){
			booleanCertifyAct = false;
			alert("에러가 발생했습니다.\n" + jqXHR.responseJSON.message);
		}).always(function(){
			var booleanCertifyAct = false;
		});

    }
    



})();