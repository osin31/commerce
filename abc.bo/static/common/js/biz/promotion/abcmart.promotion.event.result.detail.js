(function() {

	var _eventResultDetail = abc.object.createNestedObject(window,"abc.biz.promotion.event.result.detail");
	var _eventResultAlert = '';
	var eventNo = $('[name=eventNo]').val();
	var trIdx = $('.resultAddTr').length;

	_eventResultDetail.init = function(){
		_eventResultDetail.event();
		_eventResultDetail.fieldDisabled();
		CKEDITOR.replace( 'pblcContText' );
		
		//그리드에서 타고 넘어올시에 param 추가
		if(abc.text.allNull($("#eventTypeCode").val()) && !abc.text.allNull($("#eventNo").val())){
			var eventTypeCode = abc.param.getParams().eventTypeCode;
			$("#eventTypeCode").val(eventTypeCode);
		}
	}

	/**
	 * 버튼 이벤트 추가
	 */
	_eventResultDetail.event = function(){
		//저장
		$('#save-result').on('click', function(e){
			console.log('save event');

			if(_eventResultDetail.valid()){
				var url = '/promotion/event/result/save';
				if($('#existEventNo').val() != null && $('#existEventNo').val() != ''){
					url = '/promotion/event/result/modify';
				}

				var form = $.form(document.forms.frm);

				document.forms.frm.pblcContText.innerText = CKEDITOR.instances.pblcContText.getData();

				form.submit({
				    'type': 'POST',
				    'url': url,
				    'success': function(d) {
				    	alert('저장 되었습니다.');
			    		location.href = '/promotion/event/result';
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}
		});

		$('#eventSearchPop').on('click', function(e) {
			var params = {};
			params.callback = 'abc.biz.promotion.event.result.detail.appendEvent';
			_eventResultDetail.eventPopup(params);
		});

		$('body').on('click', '.resultMemberPop', function(e) {
			if($('[name=eventNo]').val() == '') {
				alert('이벤트를 선택해주세요.');
				return false;
			}
			var params = {};
			params.callback = 'abc.biz.promotion.event.result.detail.appendResultMember';
			params.trIdxName = $(this).closest('.resultAddTr').attr('id');
			params.eventNo = $('[name=eventNo]').val();
			params.eventTypeCode = $('[name=eventTypeCode]').val();
			_eventResultDetail.eventResultMemberPopup(params);
		});

		$('#resultAdd').on('click', function(e) {
			trIdx++;
			$('#resultAddTbody').append($('#resultAddTemplate').html());
			$('#resultAddTbody').find('.benefitName').last().val($('#resultAddInput').val());
			$('#resultAddTbody').find('.resultAddTr').last().attr('id', 'trIdx' + trIdx);
			_eventResultDetail.addIdx();
		});

		$('body').on('click', '.resultDelBtn', function(e) {
			$(this).closest('tr').remove();
			_eventResultDetail.addIdx();
		});

		$('body').on('click', '.btn-file-del', function(e) {
			$('#inputFile').val('');
			$(this).closest('li').remove();
		});

		//excel 업로드
		$('#inputFile').on('change', function(e){
			var ext = this.files[0].name.substring(this.files[0].name.lastIndexOf('.') + 1);
			if(ext != 'xlsx' && ext != 'xls') {
				alert('등록 가능한 확장자명은 xlsx, xls입니다.');
				return;
			}

			if(this.files && this.files[0]){
				$('#excelName').empty();

				var reader = new FileReader();

				reader.readAsDataURL(this.files[0]);

				$('#excelArea').find('.addLi').remove();

				$('#excelArea').append($('#excelAddTemplate').html());
				$('#excelArea').find('.subject').last().text(this.files[0].name);
			}
		});

		$('#excelUpload').on('click', function(e){
			if($('#excelArea').find('.subject').length == 0) {
				alert('엑셀 파일을 추가해주세요.');
			} else {
				$('#resultAddExcelTbody').empty();
				if($('[name=eventNo]').val() == '') {
					alert('이벤트를 선택해주세요.');
					return;
				}

				$('#resultAddExcelTbody').empty();
				var form = $.form(document.forms.frm);

				form.submit({
				    'type': 'POST',
				    'url': '/promotion/event/result/member/excel/list',
				    'data' : {
				    	'eventNo' : $('[name=eventNo]').val() ,
				    	'eventTypeCode' : $('[name=eventTypeCode]').val()
				    },
				    'success': function(d) {
				    	if(d != null && d.length > 0) {
				    		var winCount = 0;
				    		var noneTargetCount = 0;
				    		var eventRsltBenefitSeq = 0;
				    		var totalRowNum = d[0].totalRowNum;

				    		$.each(d, function(i, v) {
				    			if( (i - 1 >= 0 && d[i - 1].sortSeq != v.sortSeq) || i == 0 ) {
				    				eventRsltBenefitSeq ++;
				    				$('#resultAddExcelTbody').append($('#resultAddExcelTemplate').html());
				    				$('#resultAddExcelTbody').find('.sortSeq').last().val(v.sortSeq);
				    				$('#resultAddExcelTbody').find('.benefitName').last().val(v.benefitName);
				    				winCount = 0;
				    			}
				    			//console.log($('#trIdx' + trIdx).data('eventRsltBenefitSeq'));
				    			//console.log(trIdx);
				    			//console.log($('#trIdx' + trIdx));
				    			if( i != 0 && ((d[i - 1].sortSeq == v.sortSeq) && (d[i - 1].memberNo == v.memberNo))) {
				    				noneTargetCount++;
				    			} else {
				    				winCount++;
				    				if(winCount != 0) $('#resultAddExcelTbody').find('.winCount').last().text('(' + winCount + ')');
				    				$('#resultAddExcelTbody').find('.resultHiddenArea').last().append('<input type="hidden" name="evEventResultBenefitMemberArr.eventRsltBenefitSeq" value="' + eventRsltBenefitSeq + '" />');
				    				$('#resultAddExcelTbody').find('.resultHiddenArea').last().append('<input type="hidden" name="evEventResultBenefitMemberArr.memberNo" value="' + v.memberNo + '" />');
				    				$('#resultAddExcelTbody').find('.resultHiddenArea').last().append('<input type="hidden" name="evEventResultBenefitMemberArr.evEventJoinMemberSeq" value="" />');
				    			}
				    		});

				    		alert('유효하지 않은 응모자 ' + Number(totalRowNum - (d.length - noneTargetCount))+ '명을 제외한 ' + (d.length - noneTargetCount) + '명 등록되었습니다.');
				    		_eventResultDetail.addIdx();
				    	} else {
				    		alert('응모자가 없습니다.');
				    	}
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}
		});

		//엑셀폼 다운로드
		$('#excelDownload').on('click', function(e){
			$('[name=frm]').attr('action', '/promotion/event/result/member/excel/form/download');
			$('[name=frm]').submit();
		});


		$('[name=indvdlRgstYn]').on('change', function(e) {
			var indvdlRgstYn = $(this).val();

			if(indvdlRgstYn == 'Y') {
				if(confirm("등록 된 내용이 초기화 됩니다. 개별등록으로 재등록 하시겠습니까?") == false){
					$('[name=indvdlRgstYn][value=N]').prop('checked', true);
				} else {
					$('.indvdlRgstArea').hide();
					$('#resultAddTbody').empty();
					$('.benefitArea').show();

					$('.benefitTableExcelArea').find('input').prop('disabled', true);
					$('.benefitTableArea').find('input').prop('disabled', false);
				}
			} else {
				if(confirm("등록 된 내용이 초기화 됩니다. 엑셀 일괄 업로드로 재등록 하시겠습니까?") == false){
					$('[name=indvdlRgstYn][value=Y]').prop('checked', true);
				} else {
					$('.indvdlRgstArea').hide();
					$('#resultAddExcelTbody').empty();
					$('.benefitExcelArea').show();

					$('.benefitTableExcelArea').find('input').prop('disabled', false);
					$('.benefitTableArea').find('input').prop('disabled', true);
				}
			}
		});

		$(".adminDetailPop").on('click', function(){
			abc.adminDetailPopup($(this).data("adminNo"));
		});
	}

	_eventResultDetail.addIdx = function() {
		$('.resultAddTr').each(function(i, v) {
			$(v).data('eventRsltBenefitSeq', i + 1);
			$(v).find('[name*=eventRsltBenefitSeq]').val(i + 1);
		});
	}

	_eventResultDetail.eventPopup = function(params){
		var url = "/promotion/event/popup";
		abc.open.popup({
			url 	:	url,
			method	: 	"get",
			title 	:	"event-search",
			width 	:	'1240',
			height	:	'830',
			params	:	params
		});
	}

	_eventResultDetail.eventResultMemberPopup = function(params){
		var url = "/promotion/event/result/member/popup";
		_eventResultAlert = abc.open.popup({
			url 	:	url,
			method	: 	"get",
			title 	:	"result-search",
			width 	:	'1240',
			height	:	'830',
			params	:	params
		});
	}

	_eventResultDetail.appendEvent = function(d) {
		$.ajax({
            type :'get',
            data : {eventNo : d.eventNo},
            url:'/promotion/event/result/duplCheck'
        }).done(function(success){
        	if(success){
        		$('#resultAddTbody').empty();
        		$('#resultAddExcelTbody').empty();

        		$('#eventNameArea').empty();
        		$('#eventNameArea').text(d.eventNo + ' / ' + d.eventName);
        		$('[name=eventNo]').val(d.eventNo);
        		$('[name=eventTypeCode]').val(d.eventTypeCode);
        		$('#przwrPblcTodoYmd').text(new Date(d.przwrPblcTodoYmd).format(abc.consts.DEFAULT_DATETIME_PATTERN));
        	} else {
        		alert('이미 당첨자 등록이 된 이벤트 입니다.');
        	}
        }).fail(function(e){
        	alert(e.message);
	    	console.log(e);
        });
	}
	_eventResultDetail.appendResultMember = function(d, trIdxName) {
		var callCheck = false;
		$('.resultAddTr').each(function(i, v) {
			if($(v).attr('id') == trIdxName) {
				callCheck = true;
				return false;
			}
		});

		if(!callCheck) {
			alert('발표대상이 삭제되었습니다.');
			return;
		}

		$('#' + trIdxName).find('.resultHiddenArea > [name*=evEventResultBenefitMemberArr]').remove();

		var winCount = 0;
		$.each(d, function(i, v) {
			var memberDuplCheck = true;
			//$('#' + trIdxName).find('[name*=evEventJoinMemberSeq]').each(function (j, x) {
			$('#resultAddTbody').find('[name*=evEventJoinMemberSeq]').each(function (j, x) {
				if($(x).val() == v.evEventJoinMemberSeq) {
					memberDuplCheck = false;
					return false;
				}
			});
			if(memberDuplCheck) {
				winCount++;
				$('#' + trIdxName).find('.resultHiddenArea').append('<input type="hidden" name="evEventResultBenefitMemberArr.eventRsltBenefitSeq" value="' + $('#' + trIdxName).data('eventRsltBenefitSeq') + '" />');
				$('#' + trIdxName).find('.resultHiddenArea').append('<input type="hidden" name="evEventResultBenefitMemberArr.memberNo" value="' + v.memberNo + '" />');
				$('#' + trIdxName).find('.resultHiddenArea').append('<input type="hidden" name="evEventResultBenefitMemberArr.evEventJoinMemberSeq" value="' + v.evEventJoinMemberSeq + '" />');
			}
		});
		$('#' + trIdxName).find('.winCount').text('(' + winCount + ')');

		if(winCount > 0 && winCount != d.length) {
			_eventResultAlert.alert('중복된 회원을 제외한 ' + winCount + '명 추가 되었습니다.');
		} else if(winCount == 0) {
			_eventResultAlert.alert('중복된 회원입니다.');
		}
	}

	_eventResultDetail.fieldDisabled = function(){
		$('.indvdlRgstArea').hide();
		if($('[name=indvdlRgstYn]:checked').val() == 'Y') {
			$('.benefitArea').show();

			$('.benefitTableExcelArea').find('input').prop('disabled', true);
			$('.benefitTableArea').find('input').prop('disabled', false);
			$('.benefitTableExcelArea').find(".resultAddExcelTr").remove();
		} else {
			$('.benefitExcelArea').show();

			$('.benefitTableExcelArea').find('input').prop('disabled', false);
			$('.benefitTableArea').find('input').prop('disabled', true);
			$('.benefitTableArea').find(".resultAddTr").remove();
		}
	}

	/**
	 * 유효성 체크
	 */
	_eventResultDetail.valid = function(){
		var selector = '';
		if($('[name=indvdlRgstYn]:checked').val() == 'Y') {
			selector = '.benefitTableArea';
			if($(selector).find(".resultAddTr").length == 0){
				alert('당첨자선정을 등록하세요');
				return false;
			}
		} else {
			selector = '.benefitTableExcelArea';
			if($(selector).find(".resultAddExcelTr").length == 0){
				alert('엑셀파일 업로드를 해주세요');
				return false;
			}
		}


		var valid = true;;
		$(selector).find('[name*=sortSeq]').each(function(i, v) {
			if(!$.isNumeric($(v).val())) {
				alert('우선순위는 숫자만 입력이 가능합니다.');
				$(v).focus();
				valid = false;
				return false;
			}
		});

		if(valid){
			$(selector).find('[name*=benefitName]').each(function(i, v) {
				if(abc.text.allNull($(v).val())) {
					alert('발표명을 입력하세요');
					$(v).focus();
					valid = false;
					return false;
				}
			});
		}

		if(valid){
			$(selector).find('.winCount').each(function(i, v) {
				if(abc.text.validateStringSignRemove($(this).text()) == 0) {
					alert('당첨자를  입력하세요');
					$(v).focus();
					valid = false;
					return false;
				}
			});
		}

		if(!valid){
			return false;
		}

		return true;
	}

	$(function() {
		_eventResultDetail.init();
	});
})();