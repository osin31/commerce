(function() {

	var _approval = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display.detail.approval");
	
	/**
	 * 초기화
	 */
	_approval.init = function() {
		
		_approval.event();
	}
	
	/**
	 * 이벤트
	 */
	_approval.event = function(){
		
		/** 승인완료 Btn */
		$('#approveBtn').on('click', function() {
			
			var plndpNo = $('input[name=plndpNo]').val();
			var returnRsnText = $('#returnRsnText').val();
			var form = $.form(document.forms.saveForm);
			
			form.submit({
				url : '/promotion/planning-display/approval/approve',
				method : 'POST',
				valid	: function($f){
					return true;
				},
				success : function() {
					
					var flag = confirm('승인완료 처리되었습니다.\n추가정보 관리로 이동하시겠습니까?');
					
					if (flag) { // 기획전 상세
						location.href = '/promotion/planning-display/detail?plndpNo=' + plndpNo;
					} else { // 승인관리 목록
						location.href = '/promotion/planning-display/approval';
					}
				},
				error : function(e) {
					alert(e.message);
					
					if (e.fieldName != null && e.fieldName != '') {
						var $el = $('[name='+e.fieldName+']');
						
						if ($el.length > 0) {
							
							if (e.fieldName == 'pcDtlDescText') {
								location.href="#pcDtlDescTextArea";
							} else {
								$el.focus();								
							}
						}
					}
				}
			});
		});
		
		/** 승인반려 Btn */
		$('#returnBtn').on('click', function() {
			
			$('#returnArea').show();
		});
		
		/** 승인반려 저장 Btn */
		$('#returnSave').on('click', function() {
			
			var plndpNo = $('input[name=plndpNo]').val();
			var returnRsnText = $('#returnRsnText').val();
			
			$.ajax({
				dataType : 'text',
				type :'POST',
				url : '/promotion/planning-display/approval/feedback',
				data : {
					plndpNo : plndpNo,
					returnRsnText : returnRsnText
				}
			})
			.done(function(){
				
				alert('승인반려 처리되었습니다.');
				
				location.href = '/promotion/planning-display/approval';
			})
			.fail(function(e){
				console.log(e);
			});
		});
		
		/** 승인반려 취소 Btn */
		$('#returnCancel').on('click', function() {
			
			$('#returnArea').hide();
		});
	}
	
	$(function() {
		_approval.init();
	});
	
})();