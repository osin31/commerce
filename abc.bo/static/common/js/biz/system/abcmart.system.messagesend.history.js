/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _mailSendHistory = abc.object.createNestedObject(window,"abc.biz.system.messagesend.history");
	var $form 	= $("#messageHistoryForm");

	/***
	 * 메일발송 이력 상세
	 */
	_mailSendHistory.getMailHistoryDetail = function(){
		messageHistoryForm.action = "/cmm/email-send-history/detail";
		messageHistoryForm.submit();
	}
	
	/***
	 * 메일발송 이력 목록
	 */
	_mailSendHistory.getMailHistoryList = function(){
		messageHistoryForm.action = "/cmm/email-send-history";
		messageHistoryForm.submit();
	}

})();