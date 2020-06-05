(function() {

	var _feedback = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display.feedback");
	var params = abc.param.getParams();
	
	/**
	 * 초기화
	 */
	_feedback.init = function() {
		
		_feedback.event();
		
	}
	
	/**
	 * 이벤트
	 */
	_feedback.event = function(){
		
		/** 취소 */
		$('#closeBtn').on('click', function() {
			
			window.close();
		});
		
		/** 승인 반려 btn */
		$('#returnBtn').on('click', function() {
			
			var reason = $('#returnRsnText').val();
						
			// callback
			var cb = 'abc.biz.promotion.planning.display.approval.returnCallback';
			var opFunc = new Function('return opener.'+cb)();
			opFunc(reason);
			window.close();
		});
		
	}
	
	$(function() {
		_feedback.init();
	});
	
})();