(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.giftCard.detail");
	
	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		
		new abc.biz.display.common.processImage( { file: '#image', name: '#image-name' } );
		
		// 수정불가항목 설정
		_object.setDisableAccess(!abc.text.allNull($('input[name="giftCardNo"]').val()));
		
		// 카드구분 변경 시
		$('input[name="giftCardGbnType"]').change(function() {
			abc.biz.product.giftCard.detail.checkGiftCardGbnType();
		});
		
		// 저장
		$("#save").click(function(e) {
			e.preventDefault();
			_object.setDisableAccess(false);
			var suffix = !$('input[name="giftCardNo"]').val() ? 'save' : 'modify';
			var option = {
					url : "/product/giftCard/" + suffix,
					type : "POST",
					dataType : "json",
					success : function(data) {
						alert("기프트카드가 "+ (suffix == 'save' ? '등록' : '수정') +"되었습니다.");
						opener.abc.biz.product.giftCard.getList();
						window.close();
					},
					error : function(e) {
						if(e != null && e.message != null && e.message != "") {
							alert(e.message);
							_object.setDisableAccess(false);
						}
						console.log(e);
						
					}
				};
			$.form("#detail-form").submit(option);
			_object.setDisableAccess(true);
		});
		
		abc.biz.product.giftCard.detail.checkGiftCardGbnType();
		
	}
	
	_object.checkGiftCardGbnType = function() {
		if($('input[name="giftCardGbnType"]:checked').val() == '0') {
			$('#sell-amt-tr').show();
		} else {
			$('#sell-amt-tr').hide();
		}
	}
	
	_object.setDisableAccess = function(bool) {
		$('input[name="giftCardTypeCodeArr"]').prop('disabled', bool);
		$('input[name="giftCardGbnType"]').prop('disabled', bool);
		$('input[name="sellAmt"]').prop('disabled', bool);
		$('select[name="validTermType"]').prop('disabled', bool);
	}
	
	$(function() {
		_object.init();
	});
	
})();