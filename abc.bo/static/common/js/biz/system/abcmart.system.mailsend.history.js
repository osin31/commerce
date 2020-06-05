/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _mailSendHistory = abc.object.createNestedObject(window,"abc.biz.system.mailsend.history");
	var $form 	= $("#mailHistoryForm");

	/***
	 * 메일발송 이력 상세
	 */
	_mailSendHistory.getMailHistoryDetail = function(){
		mailHistoryForm.action = "/cmm/email-send-history/detail";
		mailHistoryForm.submit();
	}
	
	/***
	 * 메일발송 이력 목록
	 */
	_mailSendHistory.getMailHistoryList = function(){
		mailHistoryForm.action = "/cmm/email-send-history";
		mailHistoryForm.submit();
	}

})();