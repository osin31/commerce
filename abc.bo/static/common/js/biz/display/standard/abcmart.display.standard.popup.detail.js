(function() {

	var _popupDetail = abc.object.createNestedObject(window,"abc.biz.display.standard.popup.detail");
	
	_popupDetail.init = function(){
		_popupDetail.event();
		_popupDetail.fieldDisabled();
		
		// 날짜 valid
		abc.biz.display.common.checkDateValid('input[name=paramStartYmd]', 'input[name=paramEndYmd]');
		
		new abc.biz.display.common.processImage({
			file: '#pcImageFilePopup',
			name: '#pcImageNamePopup'
		});
		new abc.biz.display.common.processImage({
			file: '#moImageFilePopup',
			name: '#moImageNamePopup'
		});
		new abc.biz.display.common.processImage({
			file: '#pcImageFileEvent',
			name: '#pcImageNameEvent'
		});
		new abc.biz.display.common.processImage({
			file: '#moImageFileEvent',
			name: '#moImageNameEvent'
		});
	}
	
	/**
	 * 버튼 이벤트 추가
	 */
	_popupDetail.event = function(){
		//삭제
		$('#del-popup').on('click', function(e){
			console.log('delete popup');
			
			var form = $.form(document.forms.frm);
			var popupSeq = $('#popupSeq').val();
			if(confirm("삭제 하시겠습니까?")){
				form.submit({
					'type': 'POST',
					'url': '/display/standard/popup/remove',
					'data' : { popupSeq :  popupSeq},
					'success': function(d) {
						alert('삭제 되었습니다.');
						location.href = '/display/standard/popup';
					},
					'error': function(e){
						alert(e.message);
						console.log(e);
					}
				});
			}
		});
		
		//목록
		$('#go-list').on('click', function(e){
			location.href = '/display/standard/popup';
		});
		
		//저장
		$('#save-popup').on('click', function(e){
			console.log('save popup');
			
			if(_popupDetail.valid()){
				var url = '/display/standard/popup/save';
				if($('#popupSeq').val() != null && $('#popupSeq').val() != ''){
					url = '/display/standard/popup/modify';
				}
				
				$('.inputCheckDay').remove();
				$('.checkDay').each(function(i, v){
					var checkValue = '';
					if($(this).is(":checked")) {
						checkValue = 'Y';
					} else {
						checkValue = 'N';
					}
					$('#checkDayArea').append('<input type="hidden" name="' + $(this).data('name') + '" class="inputCheckDay" value="' + checkValue + '" />');
				});
				
				var form = $.form(document.forms.frm);
				
				form.submit({
				    'type': 'POST',
				    'url': url,
				    'success': function(d) {
				    	alert('저장 되었습니다.');
			    		location.href = '/display/standard/popup';
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}
			
		});

		/*//이미지 업로드
		$('body').on('change', '.imageFile', function(e){
			var parent = $(this).closest('.file-list');
			
			if(this.files && this.files[0]){

				$(parent).find('.img-wrap').html('');
				
				var img = new Image();
				var reader = new FileReader();
				reader.onload = function(e){
					$(img).attr('src', e.target.result);
					$(img).addClass('targetImage');
				}
				
				reader.readAsDataURL(this.files[0]);
				$(this).closest('.file-wrap').find('.img-wrap').empty();
				$(this).closest('.file-wrap').find('.img-wrap').append(img);
				$(parent).find('.subject').text(this.files[0].name);
				$(parent).find('.imageName').val(this.files[0].name);
				$(parent).find('.btn-file-del').show();
			}
			
		});

		//이미지 삭제 
		$('body').on('click', '.btn-file-del', function(e){
			var parent = $(this).closest('.file-list');
			$(this).closest('.file-wrap').find('.img-wrap').html('');
			$(parent).find('.subject').text('');
			$(parent).find('.imageFile').val('');
			$(parent).find('.btn-file-del').hide();
			$(parent).find('.imageName').val('');
		});*/
		
		$('body').on('click', '.add-dispPosition', function(e){
			if($(this).data('deviceType') == 'PC') {
				$('#pcDispPositionArea').append($('#pcDispPosition-tmpl').html());
			} else {
				$('#moDispPositionArea').append($('#moDispPosition-tmpl').html());
			}
		});
		
		$('body').on('click', '.del-dispPosition', function(e){
			$(this).closest('li').remove();
		});
		
		$('#radioDisplay01').on('click', function(e) {
			$('.displayDate').prop('disabled', false);
			$('#popupTypeAppendArea').show();
		});
		
		$('#radioDisplay02').on('click', function(e) {
			$('.displayDate').prop('disabled', true);
			$('.checkDay').prop('checked', false);
			$('#popupTypeAppendArea').hide();
		});
		
		$('[name=dispDaySetupYn]').on('change', function() {
			if($('[name=dispDaySetupYn]:checked').val() == 'Y') {
				$('.checkAllDay').prop('disabled', false);
				$('.checkDay').prop('disabled', false);
				$('.checkAllDay').prop('checked', true);
				$('.checkDay').prop('checked', true);
				$('.selectDisplayTime').prop('disabled', false);
			} else {
				$('.checkAllDay').prop('disabled', true);
				$('.checkDay').prop('disabled', true);
				$('.checkAllDay').prop('checked', false);
				$('.checkDay').prop('checked', false);
				$('.selectDisplayTime').prop('disabled', true);
				$('.checkDay').prop('checked', false);
			}
		}); 
		
		/*$('#popupPreview').on('click', function(e) {
			_popupDetail.popupPreview();
		});*/
		
		$('[name=popupType]').on('change', function(e) {
			var popupType = $(this).val();
			$('.popupTypeArea').remove();
			
			if(popupType == 'G') {
				$('#popupTypeAppendArea').after($('#popupType1Template').html());
				
				new abc.biz.display.common.processImage({
					file: '#pcImageFilePopup',
					name: '#pcImageNamePopup'
				});
				new abc.biz.display.common.processImage({
					file: '#moImageFilePopup',
					name: '#moImageNamePopup'
				});
			} else {
				$('#popupTypeAppendArea').after($('#popupType2Template').html());
				
				new abc.biz.display.common.processImage({
					file: '#pcImageFileEvent',
					name: '#pcImageNameEvent'
				});
				new abc.biz.display.common.processImage({
					file: '#moImageFileEvent',
					name: '#moImageNameEvent'
				});
			}
			
		});
		
		$('body').on('click', 'input[id*=LinkType]', function() {
			
			var type = $(this).val();
			var position = '.' + $(this).data('position');
			
			if (type == 'U') {
				$(position).eq(0).prop('disabled', false);
				$(position).eq(1).prop('disabled', true);
				$(position).eq(2).prop('disabled', true);
				$(position+'-area').eq(0).show();
				$(position+'-area').eq(1).hide();
				$(position+'-area').eq(2).hide();
			} else if (type == 'M') {
				$(position).eq(0).prop('disabled', true);
				$(position).eq(1).prop('disabled', false);
				$(position).eq(2).prop('disabled', true);
				$(position+'-area').eq(0).hide();
				$(position+'-area').eq(1).show();
				$(position+'-area').eq(2).hide();
			} else if (type == 'N') {
				$(position).prop('disabled', true);
				$(position+'-area').hide();
			} else if (type == 'P' || type == 'B') {
				
				var data = $(this).data();
				
				$(position+'-link-url').text(data.linkUrl);
				$(position+'-input-info').val('');
				$(position+'-input-info').attr('placeholder', data.hint);
				
				$(position).eq(0).prop('disabled', true);
				$(position).eq(1).prop('disabled', true);
				$(position).eq(2).prop('disabled', false);
				$(position+'-area').eq(0).hide();
				$(position+'-area').eq(1).hide();
				$(position+'-area').eq(2).show();
			}
		});
		
		$('.checkAllDay').on('change', function() {
			if($(this).is(':checked') == true) {
				$('.checkDay').prop('checked', true);
			} else {
				$('.checkDay').prop('checked', false);
			}
		});
		
		$('.checkDay').on('change', function() {
			if($('.checkDay').length == $('.checkDay:checked').length) {
				$('.checkAllDay').prop('checked', true);
			} else {
				$('.checkAllDay').prop('checked', false);
			}
		});
		
		$('body').on('change', '.pcDisplaySelect', function(){
			if($(this).val() == '10000') {
				$(this).siblings('.ui-input').prop('readonly', true);
			} else {
				$(this).siblings('.ui-input').prop('readonly', false);
			}
		});
		
		$('body').on('change', '.moDisplaySelect', function(){
			if($(this).val() == '10000') {
				$(this).siblings('.ui-input').prop('readonly', true);
			} else {
				$(this).siblings('.ui-input').prop('readonly', false);
			}
		});
		
		$('body').on('change', '[name=deviceCode]', function() {
			$('#pcDispPositionArea').empty();
			$('#moDispPositionArea').empty();
			$('.pcPopupTypeArea').show();
			$('.deviceArea').hide();
			if($(this).val() == '10000') {
				$('.displayArea').hide();
				$('.pcDisplayArea').show();
				$('#pcDispPositionArea').append($('#pcDispPosition-firstTmpl').html());
				$('.pcDeviceArea').show();
			} else if($(this).val() == '10001') {
				$('.displayArea').hide();
				$('.pcPopupTypeArea').hide();
				$('.moDisplayArea').show();
				$('#moDispPositionArea').append($('#moDispPosition-firstTmpl').html());
				$('.moDeviceArea').show();
			} else {
				$('.displayArea').show();
				$('#pcDispPositionArea').append($('#pcDispPosition-firstTmpl').html());
				$('#moDispPositionArea').append($('#moDispPosition-firstTmpl').html());
				$('.pcDeviceArea').show();
				$('.moDeviceArea').show();
			}
		});
		
		$('[name=priorSeq]').on("keyup change", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('frm', false);
			
			$('.popupTypeArea').remove();
			$('#popupTypeAppendArea').after($('#popupType1Template').html());
			
			$('.displayDate').prop('disabled', false);
			$('#popupTypeAppendArea').show();
			
			$('.checkDay').prop('checked', true);
			
			$('#pcDispPositionArea').empty();
			$('#moDispPositionArea').empty();
			$('.pcPopupTypeArea').show();
			$('.deviceArea').hide();
			$('.displayArea').hide();
			$('.pcDeviceArea').show();
			$('.pcDisplayArea').show();
			$('#pcDispPositionArea').append($('#pcDispPosition-firstTmpl').html());
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminDetailPop").on('click', function(){
			abc.adminDetailPopup($(this).data("adminNo"));
		});
	}
	
	_popupDetail.fieldDisabled = function(){
		if($('#radioDisplay02').is(':checked')) {
			$('.displayDate').prop('disabled', true);
			$('#popupTypeAppendArea').hide();
		}
		
		if($('.checkDay').length == $('.checkDay:checked').length) {
			$('.checkAllDay').prop('checked', true);
		}
		
		if($('#radioConfigDisplay02').is(':checked')) {
			$('.checkAllDay').prop('disabled', true);
			$('.checkDay').prop('disabled', true);
			$('.selectDisplayTime').prop('disabled', true);
			$('.checkAllDay').prop('checked', false);	
			$('.checkDay').prop('checked', false);	
		}
		
		
		if($('[name=deviceCode]:checked').val() == '10000') {
			$('.displayArea').hide();
			$('.pcDisplayArea').show();
			$('.deviceArea').hide();
			$('.pcDeviceArea').show();
		} else if($('[name=deviceCode]:checked').val() == '10001') {
			$('.displayArea').hide();
			$('.moDisplayArea').show();
			$('.pcPopupTypeArea').hide();
			$('.deviceArea').hide();
			$('.moDeviceArea').show();
		} else {
			//$('.displayArea').show();
		}
	}
	
	/**
	 * 유효성 체크 
	 */
	_popupDetail.valid = function(){
		
		var frm = document.forms.frm;
		
		if($('[name=popupType]:checked').val() == 'G') {
			if(!$.isNumeric(frm.priorSeq.value)){
				alert('우선순위는 숫자만 입력이 가능합니다.');
				$(frm.priorSeq).focus();
				return false;
			}
			
			if($('input[name=deviceCode]:checked').val() != '10001') { // 디바이스코드 전체,PC인 경우에만 체크
				if(!$.isNumeric(frm['bdPopupDevice.pcPopupXPostn'].value)){
					alert('PC 팝업창 위치는 숫자만 입력이 가능합니다.');
					$(frm['bdPopupDevice.pcPopupXPostn']).focus();
					return false;
				}
				
				if(!$.isNumeric(frm['bdPopupDevice.pcPopupYPostn'].value)){
					alert('PC 팝업창 위치는 숫자만 입력이 가능합니다.');
					$(frm['bdPopupDevice.pcPopupYPostn']).focus();
					return false;
				}
			}			
			
			/*if(!$.isNumeric(frm['bdPopupDevice.pcPopupWidthNum'].value)){
				alert('PC 팝업창 크기는 숫자만 입력이 가능합니다.');
				$(frm['bdPopupDevice.pcPopupWidthNum']).focus();
				return false;
			}
			
			if(!$.isNumeric(frm['bdPopupDevice.pcPopupHeightNum'].value)){
				alert('PC 팝업창 크기는 숫자만 입력이 가능합니다.');
				$(frm['bdPopupDevice.pcPopupHeightNum']).focus();
				return false;
			}
			
			if(!$.isNumeric(frm['bdPopupDevice.moPopupWidthNum'].value)){
				alert('MOBILE 팝업창 크기는 숫자만 입력이 가능합니다.');
				$(frm['bdPopupDevice.moPopupWidthNum']).focus();
				return false;
			}
			
			if(!$.isNumeric(frm['bdPopupDevice.moPopupHeightNum'].value)){
				alert('MOBILE 팝업창 크기는 숫자만 입력이 가능합니다.');
				$(frm['bdPopupDevice.moPopupHeightNum']).focus();
				return false;
			}*/
		}
		
		return true;
	}
	
	/*_popupDetail.popupPreview = function(){
		
		//수정해야됨

		var url = "/display/standard/popup/preview";
		var params = {};
		// 제목
		params.popupTitleText = $('[name=popupTitleText]').val();
		//이미지
		params.imageSrc = $('.targetImage').eq(0).attr('src');

		abc.open.popup({
			url 	:	url,
			method	: 	"post",
			title 	:	"팝업 미리보기",
			width 	:	$('[name*=pcPopupWidthNum]').val(), 400,
			height	:	$('[name*=pcPopupHeightNum]').val() 400,
			params	:	params
		});
	}*/
	
	$(function() {
		_popupDetail.init();
	});
})();