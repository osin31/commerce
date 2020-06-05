/***
 * 입점관리 > 입점사 이메일전송.
 *
 */
(function() {

	var _vendorEmail = abc.object.createNestedObject(window,"abc.biz.vendor.email");
	var _form = $('#vendorEmailForm');
	
	_vendorEmail.event = function(){
	
		$("#vendorEmailReset").click(function(){
			$.form('#vendorEmailForm').reset();
				$("#rcvrUl li").each(function(){
					$(this).css("display","");
			});
			CKEDITOR.instances.emailContText.setData('');
		
		});
		
		// sms 발송 버튼.
		$("#sendMailBtn").off().on("click",function(){
			
			var confirmMsg = confirm("발송하시겠습니까?");
			if(confirmMsg){
				_vendorEmail.emailSend(confirmMsg);
			}
		});
		
		_vendorEmail.receiveTargetDel();
	};
	
	// 수신대상 삭제이벤트.
	_vendorEmail.receiveTargetDel = function() {

		$('[id^=rcvrDel_').off().on('click',function(e){
			//$(this).parent().css('display','none');
			
			$(this).parent().remove();
		});
	
	};
	
	// Email 발송함수.
	_vendorEmail.emailSend = function(confirmMsg,count){
		var emailContText = CKEDITOR.instances.emailContText.getData();
		
		if(abc.text.allNull($('#emailTitleText').val())){
			alert('메일 제목을 입력하세요.');
			$('#emailTitleText').focus();
			return false;
		}
		
		if(abc.text.allNull(emailContText)){
			alert('내용을 입력하세요.');
			CKEDITOR.instances.emailContText.focus();
			return false;
		}
		$('#emailContText').val(emailContText);
		
		if(abc.text.allNull(count)){
			$('#recvEmailAddrTextCount').val(0);
		}else{
			$('#recvEmailAddrTextCount').val(count);
		}
		
		$.ajax({
			url : '/vendor/info/email-send',
			method : "post",
			dataType : "json",
			data : $('#vendorEmailForm').serialize()
		})
		.done(function(data){
			
			if(data.code == abc.consts.BOOLEAN_TRUE){
				
				alert('발송 완료하였습니다.');
				window.self.close();
				
			} else {
				var failMsg = confirm('발송실패. ('+data.code+':'+data.message+')'
									+'\n재발송 하시겠습니까?');
				if(failMsg){
					_vendorEmail.emailSend(failMsg, data.recvEmailAddrTextCount);
				}
			}
			
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			alert('에러가 발생하였습니다. : ' + jqXHR.responseJSON.message);

		});
	}
		
})();