(function() {

	//��ũ��Ʈ Object����
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.cancel.popup");

	var alertSellCnclRsnText = "��ҿ�û ���� 200�� �̳��� �Է��� �ּ���."
	var alertSaveOk			= "�ֹ���ǰ�� ���� �Ǹ� ��� ��û�� ó���Ǿ����ϴ�. ";
	var alertSaveFail			= "�Ǹ���� ��û�� ���� �Ǿ����ϴ�.";

	/**
	 * init �ʱ�ȭ
	 */
	_deliveryJsObject.init = function(){	
	}; //end init

 
	/*
	 ���
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel
 

	 /*
	 �Ǹ���� ��û - �����ϱ�
	*/
	 $("#pupupSave").click(function(){
         
		 //ó���Ǽ�
       var  totalcnt = $("#totalcnt").html();
		//�ʼ��׸��� üũ�ϱ�
		var sCondParam=FormQueryString(document.gForm, true);
		if(sCondParam==undefined) return;

			$.ajax({
				type 	:"post",
				data 	: FormQueryStringEnc(document.gForm),
				url		:"/delivery/vendor/delivery-order-vendor/cancel-popup/update"
			})
			.done(function(data){
				alert( totalcnt+"���� "+alertSaveOk );
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
	 ��ҿ�û ����
	*/
	$("#sellCnclRsnText").keyup(function(e){
		var content = $(this).val();
		_deliveryJsObject.stringLengthCheck(content);

	});  //end  $("#dlvyMemoText")
	 

	/**
	 * �޼��� ���ڼ� üũ 
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