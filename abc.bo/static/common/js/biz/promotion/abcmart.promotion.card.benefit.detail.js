(function() {

	var _benefit = abc.object.createNestedObject(window,"abc.biz.product.benefit");
	
	/**
	 * 초기화
	 */
	_benefit.init = function(){
		
		this.event();
		
		new abc.biz.display.common.processImage({
			file: '#inputFile',
			name: '#imageName'
		});
		
		// 날짜 valid
		abc.biz.display.common.checkDateValid('input[name=startYmd]', 'input[name=endYmd]');
	}	
	
	/**
	 * 이벤트
	 */
	_benefit.event = function() {
		
		// 등록
		$("#saveBtn").on("click", function() {
			
			var url = "/promotion/card-benefit/dupCheck";
			var form = $.form(document.forms.benefitForm);
			var flag = true;
			
			form.submit({
				url : url,
				method : "POST",
				valid	: function($f){
					return true;
				},
				success : function(data) {
					
					if(data > 0 && $('#radioDisplayY').is(':checked')) {
						flag = confirm("동일한 전시기간 내에 이미 전시설정된 정보가 존재합니다.\n등록중인 정보로 노출하시겠습니까?");						
					}
					
					if(flag) {						
						url = "/promotion/card-benefit/save";
						
						form.submit({
							url : url,
							method : "POST",
							valid	: function($f){
								return true;
							},
							success : function(data) {
								
								alert('저장되었습니다.');
								location.href = "/promotion/card-benefit";						
							},
							error : function(e) {
								alert(e.message);
						    	console.log(e);
							}
						});
					}					
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});						
		});
		
		// 초기화
		$("#refreshBtn").on("click", function() {
			
			abc.biz.display.common.initFormData('benefitForm');
			$('.btn-file-del').trigger('click');
		});
	}	
	
	$(function() {
		
		_benefit.init();
	});
	
})();