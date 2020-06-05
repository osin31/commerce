(function() {

	//스크립트 Object선언
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.reservation.popup");

	var alertSellCnclRsnText = "발송지연 사유를 200자 이내로 입력해 주세요."
	var alertSaveOk			= "주문상품에 대한 발송지연 안내문자가 발송되었습니다. ";
	var alertSaveFail			= "대한 발송지연 안내문자가 발송이 실패 되었습니다.";
    var alertSearchDate ="오늘보다 이전 날짜는 선택 할 수없습니다.";

	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){
		
		var limitDlvyDtm = getLimitDelayDayDate($("#pymntDtm").val(), 90)
		
		// 최대 발송 지연 배송일
		$("#limitDlvyDtm").val( limitDlvyDtm );
		$("#dispLimitDtm").text( limitDlvyDtm );
		
	}; //end init

 
	//발송지연사유 선택시 내용에 셋팅
	$("#cnslScriptSeq").change(function(){
		  var  cnslScriptSeq = $('#cnslScriptSeq').val();
		  abc.getCounselScript( cnslScriptSeq,"mesgContText");
	});

	/*
	 취소
	*/
	 $("#popupCancel").click(function(){
		 self.close();
	 }) ;	//end #popupCancel
 

	 /*
	  발송 지연 안내 - 저장하기
	*/
	 $("#pupupSave").click(function(){
         
		 //처리건수
       var  totalcnt = $("#totalcnt").html();
		//필수항목을 체크하기
		var sCondParam=FormQueryString(document.gForm, true);
		if(sCondParam==undefined) return;

		var _Today = new Date(); 
		 var  delaySendDay = new Date( $('#delaySendDay').val() );

		 //선택 날짜 체크 오늘보다 이전날짜 확인
		if (_Today > delaySendDay){
			alert( alertSearchDate );
			return ;
		} 
		
		var limitDelayDay = $('#limitDlvyDtm').val();
		delaySendDay = $('#delaySendDay').val();
		
		if( abc.date.compareDate(delaySendDay, limitDelayDay) == 1 ){
			alert("결제일(" + $("#pymntDtm").val() + ")부터 3개월 이내 날짜(" + limitDelayDay + ")까지만 선택 가능합니다.")
			return;
		}

			$.ajax({
				type 	:"post",
				data 	: FormQueryStringEnc(document.gForm),
				url		:"/delivery/vendor/delivery-order-vendor/reservation-popup/update"
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
	$("#mesgContText").keyup(function(e){
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
			$("#mesgContText").val(obj.substring(0, 200));
			return false;
		} //end if(obj.length  
	}; //end  stringLengthCheck

	
})();

function getLimitDelayDayDate(date, num) { //date: 0000.00.00
	if(!date || typeof num == 'undefined') return;
	
	var arr = date.split('.');
	var obj1 = new Date(arr[0], arr[1]-1, arr[2]);
	var tmp = obj1.valueOf() + 1000 * 60 * 60 * 24 * num;
	var obj2 = new Date(tmp);

	var year = obj2.getFullYear();
	var month = (obj2.getMonth() + 1).toString();
	var day = (obj2.getDate()).toString();
	
	if(month.length == 1) month = '0' + month;
	if(day.length == 1) day = '0' + day;
	
	var str = [];
	str[str.length] = year;
	str[str.length] = month;
	str[str.length] = day;
	
	return str.join('.');
};
