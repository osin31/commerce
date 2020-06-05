(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.vndrApproval.detail");
	
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
		
		// 선택상품 반려화면 표시
		$("#return-on").click(function() {
			$('#return-table').show();
		});
		
		// 선택상품 반려화면 숨김
		$("#return-off").click(function() {
			$('#return-table').hide();
		});
		
		// 선택상품 반려
		$("#return").click(function() {
			_object.approvalSubmit(opener.abc.biz.product.vndrApproval.APRV_STAT_CODE_REJECT);
		});
		
		// 선택상품 승인
		$("#approval").click(function() {
			if($('#return-table').is(':visible')) {
				if ($('textarea[name="returnRsnText"]').val()) {
					if(!confirm("입력된 반려사유가 있습니다.\n승인완료하시겠습니까?")) {
						return false;
					}
				} else if(!confirm("승인반려가 선택되어 있습니다.\n승인완료하시겠습니까?")) {
					return false;
				}
			}
			_object.approvalSubmit(opener.abc.biz.product.vndrApproval.APRV_STAT_CODE_CONFIRM);
		});
		
		_object.stateControl($('#aprv-stat-code').val());
		
	}
	
	/**
	 * 승인상태 수정
	 */
	_object.approvalSubmit = function(aprvStatCode) {
		$('#aprv-stat-code').val(aprvStatCode);
		var form = $.form($("#product-form"));
		form.submit({
			url : "/product/vndrApproval/modify",
			method : "POST",
			valid	: function($f){
				return true;
			},
			success : function(data) {
				if(aprvStatCode == opener.abc.biz.product.vndrApproval.APRV_STAT_CODE_CONFIRM) {
					alert('승인완료 처리되었습니다.');
				} else {
					alert('승인반려 처리되었습니다.');
				}
				opener.abc.biz.product.vndrApproval.getList();
				window.close();
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			}
		});
	}
	
	/**
	 * 승인상태별 제어
	 */
	_object.stateControl = function(aprvStatCode) {
		_object.setProductChannel();
		_object.setDisableAccess(true);
		$('.manager-msg-wrap').remove();
		$('.window-btn-group').remove();
	}
	
	/**
	 * 수정 제어
	 */
	_object.setDisableAccess = function(bool) {
		
		CKEDITOR.config.readOnly = bool;
		$('select').prop('disabled', bool);
		//$('.btn-del').prop('disabled', bool);
		$('.ico-fdel').prop('disabled', bool);
		$('input[type="file"]').prop('disabled', bool);
		$('input[type="radio"]').prop('disabled', bool);
		$('input[type="checkbox"]').prop('disabled', bool);
		$('[data-button-popup="find-brand"]').prop('disabled', bool);
		$('input[type="text"]').prop('readonly', bool);
		$('input[type="number"]').prop('readonly', bool);
		$('#prdt-materl-cont-text').prop('disabled', bool);

		if(bool) {
			$('a.btn-del:not(#return-off,#return-on)').addClass('disabled');
			$('#addSafeCrtfc').addClass('disabled');
			$('#add-image-tmpl').addClass('disabled');
			$('.anchor-tab-wrap a[data-button]').addClass('disabled');
			$('.btn-file-del').off();
			$('[data-button]').addClass('disabled');
		} else {
			$('a.btn-del:not(#return-off,#return-on)').removeClass('disabled');
			$('#addSafeCrtfc').removeClass('disabled');
			$('#add-image-tmpl').removeClass('disabled');
			$('.anchor-tab-wrap a[data-button]').removeClass('disabled');
			$('[data-button]').removeClass('disabled');
		}
		
//		cntcPrdtSetupList.SetEditable(!bool);
//		rltnGoodsSetupList.SetEditable(!bool);
//		prdtOptnList.SetEditable(!bool);
		
	}
	
	_object.setProductChannel = function() {
		/**
		 * 태그를 제거. 태그 내 텍스트는 남김
		 */
		
		$.each($("[data-channel-area]"), function(i, v) {
			if($(v).find("[data-button='add-product']").length > 0) {
				$(v).text("-");	// 상품추가 텍스트/링크 제거
			} else {
				$(v).text($(v).text());	// 상품정보 텍스트 링크 제거
			}
		});
		$("[data-button='regist-by-copy']").parent().text("-");	// 복사등록 텍스트/링크 제거
	}
	
	abc.biz.product.product.detail.callback.approval = _object.init;
	
})();