(function() {


	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.store.pick.delivery.change.popup");
	var alertSaveOk			= "택배전환 처리가 완료 되었습니다.";
	var alertSaveFail			= "택배전환 처리가 실패 되었습니다.";
	var alertDeliveryMemo = "배송 메시지는 40자 이내로 입력해 주세요."

	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
		//수령지 보기 클릭시
	    _deliveryJsObject.getDeliveryAddressView(1);
		
		//deliveryAddressEmptyCnt

	}; //end init


	/**
	 * 우편번호 pop 
	 */
	$("#postSearchBtn").click(function(){
		abc.postPopup(abc.biz.store.pick.delivery.change.popup.setPostCode);
	});
  
	/**
	 * 우편번호 pop 에서 값을 선택햇을경우 SETTING 
	 */
	_deliveryJsObject.setPostCode = function(data){
		$("#rcvrPostCodeText").val(data.postCode);
		$("#rcvrPostAddrText").val(data.postAddress);
		$("#rcvrDtlAddrText").val("");
		$("#rcvrDtlAddrText").focus();  // 상세주소에 포커스
	};


	/**
	 * 배송주소와 동일 선택시 
	 */
	 $("#chkDeliveryModule").click(function(){
		if( $(this).is(":checked") ){
			$("#rcvrName").val($('#orgRcvrName').val());
			$("#rcvrHdphnNoText").val(  $('#orgRcvrHdphnNoText').val().replace(/[-]/gi,'') )  ;
			$("#rcvrTelNoText").val(  $('#orgRcvrHdphnNoText').val().replace(/[-]/gi,'') )  ;
			$("#rcvrPostCodeText").val($('#orgRcvrPostCodeText').val());
			$("#rcvrPostAddrText").val($('#orgRcvrPostAddrText').val());
			$("#rcvrDtlAddrText").val($('#orgRcvrDtlAddrText').val());
		}else{
			$("#rcvrName").val("");
			$("#rcvrHdphnNoText").val("");
			$("#rcvrPostCodeText").val("");
			$("#rcvrPostAddrText").val("");
			$("#rcvrDtlAddrText").val("");
			$("#rcvrTelNoText").val("");
		} //end if
	 });	//end   $("#postSearchBtn").
	

	/*
	 취소
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel


	 //휴대폰 번호 숫자만
	$("#rcvrHdphnNoText").off().on('keyup', function() {
		abc.text.validateOnlyNumber(this);
	}); //end if #rcvrHdphnNoText


	//수령지 보기 클릭시
_deliveryJsObject.getDeliveryAddressView = function(obj) {
		  var rcvrName = $('#rcvrName'+obj).val() ; //이름
	      var rcvrHdphnNoText = $('#rcvrHdphnNoText'+obj).val() ;  //핸드폰 번호
	      var rcvrPostCodeText = $('#rcvrPostCodeText'+obj).val() ; //우편번호
	      var rcvrPostAddrText = $('#rcvrPostAddrText'+obj).val() ; //주소 1
	      var rcvrDtlAddrText = $('#rcvrDtlAddrText'+obj).val() ;  //주소2
	      var dlvyMemoText = $('#dlvyMemoText'+obj).val() ;  //메모
	      var dlvyStatCodeNm = $('#dlvyStatCodeNm'+obj).val() ;  //진행상태
	      var logisVndrCodeNm = $('#logisVndrCodeNm'+obj).val() ; //택배사
	      var waybilNoText = $('#waybilNoText'+obj).val() ;  //송장번호
	      var rcvrTelNoText = $('#rcvrTelNoText'+obj).val() ;  //전화번호

			$("#rcvrName").val( rcvrName );
			$("#rcvrHdphnNoText").val( rcvrHdphnNoText )  ;
			$("#rcvrPostCodeText").val( rcvrPostCodeText);
			$("#rcvrPostAddrText").val( rcvrPostAddrText);
			$("#rcvrDtlAddrText").val(rcvrDtlAddrText );
			$("#dlvyMemoText").val(dlvyMemoText );
			$("#rcvrTelNoText").val(rcvrTelNoText );
			$("#dlvyStatCodeNm").html(dlvyStatCodeNm );
			$("#logisVndrCodeNm").html(logisVndrCodeNm );
			$("#waybilNoText").html(waybilNoText );


	};

 

	 /*
	 확인 - 저장하기
	*/
	 $("#pupupSave").click(function(){
		
		//필수항목을 체크하기
		var sCondParam=FormQueryString(document.gForm, true);
		if(sCondParam==undefined) return;
      
		    var callbackFunc = document.gForm.callBackFunc.value;;

			$.ajax({
				type 	:"post",
				data 	: FormQueryStringEnc(document.gForm),
				url		:"/delivery/store-pick-delivery-change-popup/update"
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

	$("#dlvyMemoText").keyup(function(e){
		var content = $(this).val();
		_deliveryJsObject.stringLengthCheck(content);

	});  //end  $("#dlvyMemoText")
	 

	/**
	 * 배송메세지 글자수 체크 
	 */
	_deliveryJsObject.stringLengthCheck = function(obj) {
		if(obj.length <= 40){
			$('#counter').html(obj.length);
		}else{
			alert(alertDeliveryMemo);
			$("#dlvyMemoText").val(obj.substring(0, 40));
			return false;
		} //end if(obj.length  
	}; //end  stringLengthCheck


	//상품 상세보기
	_deliveryJsObject.getPorductDetail = function(prdtNo) {
			var prdtNo =  prdtNo;
			var param = { prdtNo : prdtNo };
			abc.readonlyProductDetailPopup(param);
	};

})();