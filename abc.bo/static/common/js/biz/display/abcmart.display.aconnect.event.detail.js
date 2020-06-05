(function() {

	var _eventDetail = abc.object.createNestedObject(window,"abc.biz.display.aconnect.event.detail");
	
	/**
	 * 초기화
	 */
	_eventDetail.init = function(){
		
		_eventDetail.event();
		
		// 에디터
		CKEDITOR.replace('eventInfo');
		
		new abc.biz.display.common.processImage({
			file: '#imageFile',
			name: '#imageName'
		});
		
		// 날짜 valid
		abc.biz.display.common.checkDateValid('input[name=eventStartD]', 'input[name=eventEndD]');
	}
	
	/**
	 * 이벤트
	 */
	_eventDetail.event = function(){
		
		/** 적용매장 */
		$('input[name=storeCommonYn]').on('click', function() {
			
			if ($(this).hasClass('radioStoreY')) {
				$('#searchStoreBtn').addClass('disabled');
			} else {
				$('#searchStoreBtn').removeClass('disabled');
			}
		});
		
		/** 오프라인매장 조회 */
		$('#searchStoreBtn').on('click', function() {
			
			abc.storeSearchPopup('abc.biz.display.aconnect.event.detail.searchStoreCallback', true);
		});
		
		/** 오프라인매장 삭제 */
		$(document).on('click', '.btn-item-del', function() {
			
			$(this).parents('li').remove();
		});
		
		/** 저장 */
		$('#saveBtn').on('click', function() {
						
			if (_eventDetail.validation()) {
				_eventDetail.saveEvent();				
			}
			
		});
	}
	
	/** 오프라인매장 조회 callback */
	_eventDetail.searchStoreCallback = function(data) {
		
		var existingStoreList = $('#storeList input[name=storeNoArr]');
				
		var tmpl = $('#store-list-tmpl').clone();
		tmpl = $(tmpl.html());
		tmpl = $(tmpl);
		
		$.each(data, function(i,v) {
			
			var isExist = false;
			
			$.each(existingStoreList, function(j,existing) {
				if (v.storeNo == existing.value) {
					isExist = true;
				}
			});
			
			if (!isExist) {
				tmpl.find('.subject').html(v.storeName);
				tmpl.find('input').val(v.storeNo);
				
				$('#storeList').append(tmpl.html());				
			}
		});
	}
	
	/** 저장 */
	_eventDetail.saveEvent = function() {
		
		var url;
		var form = $.form(document.forms.saveForm);
		var ctlgEventNo = $('input[name=ctlgEventNo]').val();
		
		if (ctlgEventNo == null) {
			url = '/display/a-connect/event/add';
		} else {
			url = '/display/a-connect/event/modify'; 
		}
		
		document.forms.saveForm.eventInfo.innerText = CKEDITOR.instances.eventInfo.getData();
				
		form.submit({
			url : url,
			method : "POST",
			valid	: function($f){
				return true;
			},
			success : function(data) {
				
				alert('저장되었습니다.');
				
				location.href = '/display/a-connect/event';
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			}
		});		
	}
	
	/**
	 * validation
	 */
	_eventDetail.validation = function() {
		var fromDate = $('input[name=eventStartD]').val();
		var toDate = $('input[name=eventEndD]').val();
		
		if (fromDate == null || fromDate == '' || toDate == null || toDate == '') {
			alert('이벤트 기간을 입력해주세요.');
			return false;
		}
		
		fromDate = new Date(fromDate);
		toDate = new Date(toDate);
		
		if (fromDate > toDate) {
			alert('종료일이 시작일을 앞설 수 없습니다.');
			return false;
		}
		
		return true;
	}
	
	$(function() {
		_eventDetail.init();
	});
	
})();