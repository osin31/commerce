(function() {

	var _brandDetail = abc.object.createNestedObject(window,"abc.biz.display.aconnect.brand.detail");
	
	/**
	 * 초기화
	 */
	_brandDetail.init = function(){
		
		_brandDetail.event();
	}
	
	/**
	 * 이벤트
	 */
	_brandDetail.event = function(){
				
		$('#saveBtn').on('click', function() {
			
			var url = '/display/a-connect/brand/modify';
			var form = $.form(document.forms.saveForm);
			
			form.submit({
				url : url,
				method : "POST",
				valid	: function($f){
					return true;
				},
				success : function(data) {
					
					alert('저장되었습니다.');
					
					location.href = '/display/a-connect/brand';
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});
		});
		
	}
	
	$(function() {
		_brandDetail.init();
	});
	
})();