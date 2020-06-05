(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.approval.detail");
	
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
		
		_object.stateControl($('#aprv-stat-code').val());
		
	}
	
	/**
	 * 승인상태별 제어
	 */
	_object.stateControl = function(aprvStatCode) {
		$('.manager-msg-wrap').remove();
		if(aprvStatCode == '10000') { // 임시저장
			
		} else if(aprvStatCode == '10001') { // 승인요청
			$('.window-btn-group').remove();
			_object.setDisableAccess(true);
		} else if(aprvStatCode == '10002') { // 승인대기
			$('.window-btn-group').remove();
		} else if(aprvStatCode == '10003') { // 승인반려
			//$('.window-btn-group .btn-normal.btn-save').remove();	// 입점상품 승인반려 시 임시저장 활성화를 위한 주석처리.
		} else if(aprvStatCode == '10004') { // 승인완료
			alert("승인완료 상품인 경우 상품관리에서 조회 가능 합니다.");
			window.close();
		}
	}
	
	/**
	 * 수정 제어
	 */
	_object.setDisableAccess = function(bool) {
		CKEDITOR.config.readOnly = bool;
		$('select').prop('disabled', bool);
		$('.btn-del').prop('disabled', bool);
		$('.ico-fdel').prop('disabled', bool);
		$('input[type="file"]').prop('disabled', bool);
		$('input[type="radio"]').prop('disabled', bool);
		$('input[type="checkbox"]').prop('disabled', bool);
		$('[data-button-popup="find-brand"]').prop('disabled', bool);
		$('input[type="text"]').prop('readonly', bool);
		$('input[type="number"]').prop('readonly', bool);
		$('#prdt-materl-cont-text').prop('disabled', bool);
		
		if(bool) {
			$('a.btn-del').addClass('disabled');
			$('#addSafeCrtfc').addClass('disabled');
			$('#add-image-tmpl').addClass('disabled');
			$('.anchor-tab-wrap a[data-button]').addClass('disabled');
			$('.btn-file-del').off();
		} else {
			$('a.btn-del').removeClass('disabled');
			$('#addSafeCrtfc').removeClass('disabled');
			$('#add-image-tmpl').removeClass('disabled');
			$('.anchor-tab-wrap a[data-button]').removeClass('disabled');
		}
		
//		cntcPrdtSetupList.SetEditable(!bool);
//		rltnGoodsSetupList.SetEditable(!bool);
//		prdtOptnList.SetEditable(!bool);
		
	}
	
	abc.biz.product.product.detail.callback.approval = _object.init;
	
})();