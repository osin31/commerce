(function() {

	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.cancel.popup");

	var alertSellCnclRsnText = "취소요청 내용 200자 이내로 입력해 주세요."
	var alertSaveOk			= "주문상품에 대한 판매 취소 요청이 처리되었습니다. ";
	var alertSaveFail			= "판매취소 요청이 실패 되었습니다.";

	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
	}; //end init

 
	/*
	 취소
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel
 

	 /*
	 판매취소 요청 - 저장하기
	*/
	 $("#pupupSave").click(function(){
         
		 //처리건수
       var  totalcnt = $("#totalcnt").html();
		//필수항목을 체크하기
		var sCondParam=FormQueryString(document.gForm, true);
		if(sCondParam==undefined) return;

			$.ajax({
				type 	:"post",
				data 	: FormQueryStringEnc(document.gForm),
				url		:"/delivery/vendor/delivery-order-vendor/cancel-popup/update"
			})
			.done(function(data){
				alert( totalcnt+"건의 "+alertSaveOk );
				self.close();
				opener.abc.biz.delivery.order.vendor.main.doActionJs("search");
			})
			.fail(function(e){
				alert( alertSaveFail);
				opener.abc.biz.delivery.order.vendor.main.doActionJs("search");
				self.close();
			});

		 }) //end #pupupSave


	/*
	 취소요청 내용
	*/
	$("#sellCnclRsnText").keyup(function(e){
		var content = $(this).val();
		_deliveryJsObject.stringLengthCheck(content);

	});  //end  $("#dlvyMemoText")
	 

	/**
	 * 메세지 글자수 체크 
	 */
	_deliveryJsObject.stringLengthCheck = function(obj) {
		if(obj.length <= 200){
			$('#counter').html(obj.length);
		}else{
			alert(alertSellCnclRsnText);
			$("#sellCnclRsnText").val(obj.substring(0, 200));
			return false;
		} //end if(obj.length  
	}; //end  stringLengthCheck

 
})();