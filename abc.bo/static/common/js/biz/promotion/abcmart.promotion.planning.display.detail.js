(function() {

	var _planning = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display");
	
	/**
	 * 초기화
	 */
	_planning.init = function() {
		
		_planning.event();
		_planning.initCheckbox();
		
		// 에디터
		CKEDITOR.replace('pcDtlDescText', {
			uploadUrl : '/promotion/planning-display/detail/editor/upload',
			filebrowserImageUploadUrl : "/cmm/editor/image/promotion/upload"
		});
		CKEDITOR.replace('mobileDtlDescText', {
			uploadUrl : '/promotion/planning-display/detail/editor/upload',
			filebrowserImageUploadUrl : "/cmm/editor/image/promotion/upload"
		});
		
		// 이미지 업로드
		new abc.biz.display.common.processImage({
			file: '#pcImageFile',
			name: '#imageName'
		});
		new abc.biz.display.common.processImage({
			file: '#mobileImageFile',
			name: '#imageName'
		});
		/*
		new abc.biz.display.common.processImage({
			file: '#movieImageFile',
			name: '#imageName',
			allow: ' jpg, jpeg, png, gif, bmp'
		});
		*/
		// 동영상 업로드
		new abc.biz.display.common.processImage({
			file: '#movieFile',
			name: '#imageName',
			type: 'M'
		});
		
		// 날짜 valid
		abc.biz.display.common.checkDateValid('input[name=plndpStartD]', 'input[name=plndpEndD]');
		
		// 링크 url 설정
		if ($('#linkUrlArea').length > 0) {			
			_planning.setLinkUrl();
		}
	}
	
	/**
	 * 이벤트
	 */
	_planning.event = function(){
		
		// 미리보기 btn
		var isPcExist = $('input[id^=chkDevice][value=10000]').is(':checked');
		var isMoExist = $('input[id^=chkDevice][value=10001]').is(':checked') || $('input[id^=chkDevice][value=10002]').is(':checked');
		
		if (isPcExist)	$('.btn-preview.pc-type').show();
		if (isMoExist)	$('.btn-preview.mobile-type').show();
		
		$('input[name=deviceCodeAll').on('click', function() {
			if ($('input[name=deviceCodeAll]').is(':checked')) {
				$('input[name=deviceCodeArr]').prop('checked', true);
			} else {
				$('input[name=deviceCodeArr]').prop('checked', false);
			}
		});
		
		$('input[name=deviceCodeArr]').on('click', function() {
			var len = $('input[name=deviceCodeArr]').length;
			var checked = $('input[name=deviceCodeArr]:checked').length;
			
			if(len == checked)
				$('input[name=deviceCodeAll]').prop('checked', true);
			else
				$('input[name=deviceCodeAll]').prop('checked', false);
		});
		
		$('input[name=memberTypeCodeAll]').on('click', function() {
			if($('input[name=memberTypeCodeAll]').is(':checked')) {
				$('input[name=memberTypeCodeArr]').prop('checked', true);
				$('.member-grade-list').show();
			} else {
				$('input[name=memberTypeCodeArr]').prop('checked', false);
				$('.member-grade-list').hide();
			}
		});
		
		$('input[name=memberTypeCodeArr]').on('click', function() {
			var len = $('input[name=memberTypeCodeArr]').length;
			var checked = $('input[name=memberTypeCodeArr]:checked').length;
			
			if(len == checked)
				$('input[name=memberTypeCodeAll]').prop('checked', true);
			else
				$('input[name=memberTypeCodeAll]').prop('checked', false);
		});
		
		$('input[name=memberTypeCodeArr][value=10001]').on('click', function() {
			if ($(this).is(':checked')) {
				$('.member-grade-list').show();
			} else {
				$('.member-grade-list').hide();
			}
		});
		
		/** 대상회원 */
		$('input[id^=chkMember]').on('click', function() {
			var name = $(this).attr('name');			
			var isMemberChk = $('input[id^=chkMember]:checked').not('#chkMemberEmp').length > 0 ? true : false;
			var isEmployeeChk = $('#chkMemberEmp:checked').length > 0 ? true : false;
			
			if (isMemberChk && name == 'empYn') { // 임직원
				if (confirm('임직원 대상으로 변경하시겠습니까?')) {
					$('input[id^=chkMember]').not('#chkMemberEmp').prop('checked', false);
				} else {
					$(this).prop('checked', false);
				}
			} else if (isEmployeeChk && name == 'memberTypeCodeArr') { // 일반회원
				if (confirm('일반 고객 대상으로 변경하시겠습니까?')) {
					$('#chkMemberEmp').prop('checked', false);
				} else {
					$(this).prop('checked', false);
				}
			}
		});
		
		/** 동영상 등록 (U/D) */
		$('input[name=movieUploadYn]').on('click', function() {
			if ($(this).val() == 'U') {
				$('.upload-type-url').show();
				$('.upload-type-direct').hide();
			} else if ($(this).val() == 'D') {
				$('.upload-type-url').hide();
				$('.upload-type-direct').show();
			}
		});	
		
		//전시여부 선택시 배너이미지 필수표시 hide
		$('input[name=dispYn]').change(function(){
			if($(this).val() == abc.consts.BOOLEAN_TRUE){
				$(".dispYn-control").show();
			}else{
				$(".dispYn-control").hide();
			}
		});
			
		/** 저장 (BO) */
		$('#saveBtn').on('click', function() {
			
			if(!_planning.checkLength()) {
				return false;
			};
			
			var type = 'add';
			
			if ($('input[name=plndpNo]').val() != '') {
				type = 'modify';
			}
			
			var url = '/promotion/planning-display/' + type;
			var form = $.form(document.forms.saveForm);
			
			document.forms.saveForm.pcDtlDescText.innerText = CKEDITOR.instances.pcDtlDescText.getData();
			document.forms.saveForm.mobileDtlDescText.innerText = CKEDITOR.instances.mobileDtlDescText.getData();
			
			form.submit({
				url : url,
				method : 'POST',
				valid	: function($f){
					return true;
				},
				success : function(data) {
					
					if (type == 'add') { // 등록
						var bool = confirm('기획전이 저장되었습니다. 기획전정보를 등록하시겠습니까?');

						if(bool) {
							location.href = '/promotion/planning-display/product/manage?plndpNo=' + data;
						} else {
							location.href = '/promotion/planning-display';
						}
					} else if (type == 'modify') { // 수정
						alert('저장되었습니다.');
						location.href = '/promotion/planning-display';
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
		
		/** 저장 (PO) */
		$('.btn-save-vndr').on('click', function() {
			
			if(!_planning.checkLength()) {
				return false;
			};
			
			isRqstApprvl = $(this).hasClass('rqst-apprvl');
			var plndpStatCode = $('input[name="plndpStatCode"]').val();
			var url = '/promotion/planning-display/' + ($('input[name=plndpNo]').val() != '' ? 'modify' : 'add');						
			
			$('input[name=plndpStatName]').val(isRqstApprvl ? 'rqstApprvl' : 'saveTmprly');			
			
			if (isRqstApprvl) { // 승인요청
				url = '/promotion/planning-display/approval/request';
			}
			
			var form = $.form(document.forms.saveForm);
			
			document.forms.saveForm.pcDtlDescText.innerText = CKEDITOR.instances.pcDtlDescText.getData();
			document.forms.saveForm.mobileDtlDescText.innerText = CKEDITOR.instances.mobileDtlDescText.getData();
			
			form.submit({
				url : url,
				method : 'POST',
				valid	: function($f){
					return true;
				},
				success : function(data) {
					
					if (isRqstApprvl) {
						alert('승인요청 상태로 저장됩니다.\n관리자 승인 후 전시 가능합니다.');
						location.href = '/promotion/planning-display';
					} else {
						var bool = confirm('기획전이 저장되었습니다. 기획전정보를 등록하시겠습니까?');
						
						if(bool) {
							location.href = '/promotion/planning-display/product/manage?plndpNo=' + data;
						} else {
							location.href = '/promotion/planning-display';
						}				
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
		
		$('#listBtn').on('click', function() {
			
			if ($('input[name=pageType]').val() == 'A')
				location.href = '/promotion/planning-display/approval';
			else
				location.href = '/promotion/planning-display';
		});
		
		/**
		 * 미리보기
		 */
		$('.btn-preview').on('click', function() {
			
			var plndpNo = $('input[name=plndpNo]').val();
			var chnnlNo = $('input[name=originalChnnlNo]').val();
			
			var foUrl = '';
			var moUrl = '';
			
			switch (chnnlNo) {
			case '10000':
				foUrl = $('input[name=artFoUrl]').val();
				moUrl = $('input[name=artMoUrl]').val();
				break;
			case '10001':
				foUrl = $('input[name=abcFoUrl]').val();
				moUrl = $('input[name=abcMoUrl]').val();
				break;
			case '10002':
				foUrl = $('input[name=gsFoUrl]').val();
				moUrl = $('input[name=gsMoUrl]').val();
				break;
			case '10003':
				foUrl = $('input[name=otsFoUrl]').val();
				moUrl = $('input[name=otsMoUrl]').val();
				break;
			case '10004':
				foUrl = $('input[name=kidsFoUrl]').val();
				moUrl = $('input[name=kidsMoUrl]').val();
				break;	
			default:
				break;
			}
			
			if ($(this).hasClass('mobile-type')) {
				moUrl += '/promotion/planning-display/detail';
				abc.open.popup({
					url 	:	moUrl,
					winname :	"preview",
					title 	:	"preview",
					method	: 	"get",
					width	:	490,
					height	:	1060,
					params	:	{
						isPreview : 'y',
						plndpNo : plndpNo,
						viewer : 'mobile'
					}
				});
			} else {
				foUrl += '/promotion/planning-display/detail';
				window.open(foUrl+'?isPreview=y&plndpNo='+plndpNo);
			}
		});
		
		// 이미지 삭제
		$('.btn-file-del.plndp-banner-img').on('click', function() {
			// 이미지 정보 삭제
			$(this).parent('li').siblings('li').find('input[name*=ImageName]').val('');
			$(this).parent('li').siblings('li').find('input[name*=ImagePathText]').val('');
			$(this).parent('li').siblings('li').find('input[name*=ImageUrl]').val('');
		});
		$('.btn-file-del.plndp-movie-info').on('click', function() {
			// 동영상 정보 삭제
			$('input[name=movieName]').val('');
			$('input[name=moviePathText]').val('');
			$('input[name=movieUrl]').val('');
			$('.movie-name-info').text('');
		});
		
		/** 기획전 삭제 */
		$('#deletePlndpBtn').on('click', function() {
			
			var flag = confirm('삭제하시겠습니까?');
			
			if (flag) {
				$.ajax({
					type :"POST",
					url : "/promotion/planning-display/approval/remove",
					data : { plndpNo : abc.param.getParams().plndpNo }
				})
				.done(function(){
					location.href = '/promotion/planning-display';
				})
				.fail(function(e){
					console.log(e);
				});
			}		
		});
		
		// 링크 url 설정
		if ($('#linkUrlArea').length > 0) {
			$('input[name=chnnlNo]').on('click', function() {
				_planning.setLinkUrl();
			});
		}
	}
	
	_planning.initCheckbox = function() {
		
		var len = $('input[name=deviceCodeArr]').length;
		var checked = $('input[name=deviceCodeArr]:checked').length;
		
		if(len == checked)
			$('input[name=deviceCodeAll]').prop('checked', true);
		else
			$('input[name=deviceCodeAll]').prop('checked', false);
		
		len = $('input[name=memberTypeCodeArr]').length;
		checked = $('input[name=memberTypeCodeArr]:checked').length;
		
		if(len == checked)
			$('input[name=memberTypeCodeAll]').prop('checked', true);
		else
			$('input[name=memberTypeCodeAll]').prop('checked', false);
	}
	
	/**
	 * 링크 URL 설정
	 */
	_planning.setLinkUrl = function() {
		var plndpNo = $('input[name=plndpNo]').val();
		var chnnlNo = $('input[name=chnnlNo]:checked').val();
		var foUrl = '';
		var moUrl = '';		
		var pathname = '/promotion/planning-display/detail?plndpNo='+plndpNo;
		
		switch (chnnlNo) {
		case '10000':
			foUrl = $('input[name=artFoUrl]').val();
			moUrl = $('input[name=artMoUrl]').val();
			break;
		case '10001':
			foUrl = $('input[name=abcFoUrl]').val();
			moUrl = $('input[name=abcMoUrl]').val();
			break;
		case '10002':
			foUrl = $('input[name=gsFoUrl]').val();
			moUrl = $('input[name=gsMoUrl]').val();
			break;
		case '10003':
			foUrl = $('input[name=otsFoUrl]').val();
			moUrl = $('input[name=otsMoUrl]').val();
			break;
		case '10004':
			foUrl = $('input[name=kidsFoUrl]').val();
			moUrl = $('input[name=kidsMoUrl]').val();
			break;	
		default:
			break;
		}
		
		if( abc.text.allNull(foUrl) || abc.text.allNull(moUrl) ){
			$('#foLinkUrl').text('도메인 미등록 채널');
			$('#moLinkUrl').text('도메인 미등록 채널');
		} else{
			foUrl += pathname;
			moUrl += pathname;
			$('#foLinkUrl').text(foUrl);
			$('#moLinkUrl').text(moUrl);
		}
	}
	
	/**
	 * length 체크
	 */
	_planning.checkLength = function() {		
		if (abc.text.isLimitLength($('#plndpName').val(), 50)) {
			alert('기획전명은 50자 이내로 입력할 수 있습니다.');
			$("#plndpName").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=srchWordText]').val(), 100)) {
			alert('검색키워드는 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=srchWordText]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=plndpContText]').val(), 500)) {
			alert('기획전 내용은 500Bytes 이내로 입력할 수 있습니다.');
			$("input[name=plndpContText]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=pcBannerImageAltrnText]').val(), 100)) {
			alert('이미지 대체 텍스트는 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=pcBannerImageAltrnText]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=mobileBannerImageAltrnText]').val(), 100)) {
			alert('이미지 대체 텍스트는 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=mobileBannerImageAltrnText]").focus();
			return false;
		}
		/*
		if (abc.text.isLimitLength($('input[name=movieImageAltrnText]').val(), 100)) {
			alert('이미지 대체 텍스트는 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=movieImageAltrnText]").focus();
			return false;
		}
		*/		
		if (abc.text.isLimitLength($('input[name=threedImageName]').val(), 100)) {
			alert('3D 이미지명은 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=threedImageName]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=threedImagePathText]').val(), 200)) {
			alert('3D 코드는 200Bytes 이내로 입력할 수 있습니다.');
			$("input[name=threedImagePathText]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=dispMovieName]').val(), 100)) {
			alert('동영상명은 100Bytes 이내로 입력할 수 있습니다.');
			$("input[name=dispMovieName]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=movieUrl]').val(), 200)) {
			alert('동영상 경로는 200Bytes 이내로 입력할 수 있습니다.');
			$("input[name=movieUrl]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=threedImageDispSeq]').val(), 4)) {
			alert('노출순서는 4Bytes 이내로 입력할 수 있습니다.');
			$("input[name=threedImageDispSeq]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=movieDispSeq]').val(), 4)) {
			alert('노출순서는 4Bytes 이내로 입력할 수 있습니다.');
			$("input[name=movieDispSeq]").focus();
			return false;
		}
		if (abc.text.isLimitLength($('input[name=dtlDescDispSeq]').val(), 4)) {
			alert('노출순서는 4Bytes 이내로 입력할 수 있습니다.');
			$("input[name=dtlDescDispSeq]").focus();
			return false;
		}
		
		return true;
	}
	
	$(function() {
		_planning.init();
	});
	
})();