/***
 * 입점관리 > 입점사 정보등록
 *
 */
(function() {

	var _vendorTms = abc.object.createNestedObject(window,"abc.biz.vendor.tms");
	var _form = $('#vendorTmsForm');
	
	_vendorTms.event = function(){
	
		$("#vendorTmsReset").click(function(){
			$.form("#vendorTmsForm").reset();
				$("#rcvrUl li").each(function(){
					$(this).css("display","");
			});
		});
		
		// 보내는 번호 숫자만 입력가능.
//		abc.text.validateOnlyNumber('inputRprsntTelNoText');
		
		$('#inputRprsntTelNoText').keyup(function(){
			
			var reg = /[^0-9]/gi;
			var v = $(this).val();
			
			if(reg.test(v)){
				$(this).val(v.replace(reg,''));
				$(this).focus();
			}
		});
		
		$('#mesgContText').keyup(function(){
			
			if($('#mesgContText').val().uniLength() <= 80){
				$('#mesgContextTitle').prop('readonly',true);
				$('#mesgContTextMaxByte').text('80');
			} else{
				$('#mesgContextTitle').prop('readonly',false);
				$('#mesgContTextMaxByte').text('2000');
			}
			
			if($('#mesgContText').val().uniLength() > 2000){
				var str = abc.text.substringByte($('#mesgContText').val(), 2000);
				$('#mesgContText').val(str);
			}
			
			$("#mesgContTextByteLength").val($("#mesgContText").val().uniLength());
		});
		
		// sms 발송 버튼.
		$("#sendTextMsgBtn").off().on("click",function(){
			
			var confirmMsg = confirm("발송하시겠습니까?");
				_vendorTms.smsSend(confirmMsg);

		});
		
		_vendorTms.receiveTargetDel();
	};
	
	// 수신대상 삭제이벤트.
	_vendorTms.receiveTargetDel = function() {

		$('[id^=rcvrDel_').off().on('click',function(e){
			//$(this).parent().css('display','none');
			
			//hidden의 전화번호 삭제.
			//$('input[class='+this.name+']').remove();
			$(this).parent().remove();
		});
	
	};
	
	// sms 발송함수.
	_vendorTms.smsSend = function(confirmMsg,count){
		var sendTelNoCount;
		var sendTypeCode;
		
		if($('#mesgContText').val() == '' || $('#mesgContText').val() == null){
			alert('내용을 입력하세요.');
			$('#mesgContText').focus();
			return false;
		}
		
		if(count == undefined){
			this.sendTelNoCount = 0;
		} else {
			this.sendTelNoCount = count;
		}
		
		if($("#mesgContText").val().uniLength() <= 80){
			this.sendTypeCode = '10000'; // sms발송
		} else{
			this.sendTypeCode = '10001'; // lms발송
		}
		
		if(confirmMsg) {
			
			$.ajax({
				url : '/vendor/info/sms-send',
				method : "post",
				dataType : "json",
				data : _form.serialize()
						+ '&recvTelNoTextCount='+this.sendTelNoCount
						+ '&sendTypeCode='+this.sendTypeCode
				
			})
			.done(function(data){
				
				if(data.code == abc.consts.BOOLEAN_TRUE){
					
					if($("#mesgContText").val().uniLength() <= 80){
						alert("SMS를 발송하였습니다.");
				
					} else {
						alert("LMS를 발송하였습니다.");
					}
					window.self.close();
					
				} else {
					var failMsg = confirm('발송실패. ('+data.code+':'+data.message+')'
											+'\n재발송 하시겠습니까?');
					if(failMsg){
						_vendorTms.smsSend(failMsg, data.recvTelNoTextCount);
					}
				}
				
			})
			.fail(function(jqXHR, textStatus, errorThrown){
				alert('에러가 발생하였습니다. : ' + jqXHR.responseJSON.message);

			});
		}
		
	}
		
})();