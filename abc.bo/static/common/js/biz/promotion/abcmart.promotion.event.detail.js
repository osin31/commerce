(function() {

	var _eventDetail = abc.object.createNestedObject(window,"abc.biz.promotion.event.detail");
	var cpnIdx = "";	//쿠폰 위치를 가르키는 변수
	var imgIdx = 0;	//룰렛 img 위치를 가리키는 변수
	var eventNo = $('[name=eventNo]').val();

	_eventDetail.init = function(){
		_eventDetail.event();

		//에디터 초기화
		CKEDITOR.replace( 'pcEventContText', {
			uploadUrl: '/promotion/event/editor/upload',
			filebrowserImageUploadUrl : "/cmm/editor/image/promotion/upload"
		});

		CKEDITOR.replace( 'mobileEventContText', {
			uploadUrl: '/promotion/event/editor/upload',
			filebrowserImageUploadUrl : "/cmm/editor/image/promotion/upload"
		});

		CKEDITOR.replace( 'eventStplatContText', {
			uploadUrl: '/promotion/event/editor/upload',
			filebrowserImageUploadUrl : "/cmm/editor/image/promotion/upload"
		});

		//image set
		new abc.biz.display.common.processImage( { file: '#pcEventBgImg', name: '#pcEventBgImgName' } );
		new abc.biz.display.common.processImage( { file: '#moEventBgImg', name: '#moEventBgImgName' } );
		new abc.biz.display.common.processImage( { file: '#pcEventImg', name: '#pcEventImgName' } );
		new abc.biz.display.common.processImage( { file: '#moEventImg', name: '#moEventImgName' } );

		_eventDetail.eventTypeDisplay($('[name=eventTypeCode]').val(), true);
		_eventDetail.fieldDisabled();

		if(eventNo != "") {
			imgIdx = $('[name*=rouletteImages]').length;
			_eventDetail.benefitIdxAddId('basic');
			_eventDetail.benefitIdxAddId('geBenefit');
			_eventDetail.benefitIdxAddId('gbn');
			if(imgIdx != 0) _eventDetail.imageSet();
		}
	}

	/**
	 * 버튼 이벤트 추가
	 */
	_eventDetail.event = function(){
		//저장
		$('.btn-save').on('click', function(e){
			console.log('save event');

			//임시저장체크
			var tmprSaveCheck = true;
			var tmprSaveYn = $(this).data('tmprSaveYn');
			$('[name=tmprSaveYn]').val(tmprSaveYn);

			if(tmprSaveYn == 'N') {
				tmprSaveCheck = _eventDetail.valid();
			}else {
				tmprSaveCheck = _eventDetail.tempValid();
			}

			if(tmprSaveCheck){
				var url = '/promotion/event/save';
				if($('#eventNo').val() != null && $('#eventNo').val() != ''){
					url = '/promotion/event/modify';
				}

				//채널 체크
				$('.inputCheckSiteNo').remove();
				$('.checkChannel').each(function(i, v){
					if($(this).is(":checked")) {
						$('#checkChannelArea').append('<input type="hidden" name="evEventTargetChannelArr.siteNo" class="inputCheckSiteNo" value="' + $(this).data('siteNo') + '" />');
					}
				});

				//임직원 체크
				$('#empYnVal').remove();
				if($('[name=empYn]').is(':checked')) {
					$('#eventTypeBefore').append('<input type="hidden" id="empYnVal" name="evEventTargetGradeArr.memberTypeCode" value="10000" />');
				}

				$('[name=rouletteImageChk]').remove();
				$('[name*=eventRuletBenefitSeq]').remove();
				var eventTypeCode = $('[name=eventTypeCode]').val();
				if(eventTypeCode == '10004') {
					var benefitGbnCode ='';
					$('.benefitGbnTr').each(function(i, v) {
						benefitGbnCode = $(this).find('[name*=benefitGbnCode]').val();
						$(this).append('<input type="hidden" name="evEventRouletteBenefitArr.eventRuletBenefitSeq" value="' + (i + 1) + '" />');
						if(benefitGbnCode != '10002') {
							$(this).append('<input type="hidden" name="rouletteImageChk" value="N" />'); //update 시 image 존재여부 확인을 위한 value
						} else {
							if($(this).find('.rouletteImgNameArea').text() != '') {
								$(this).append('<input type="hidden" name="rouletteImageChk" value="Y" />'); //image name area 가 존재하면 insert
							} else {
								$(this).append('<input type="hidden" name="rouletteImageChk" value="N" />');
							}
						}
					});
				}

				$('#imageSetArea').find('[name*=imageName]').remove();
				$('#imageSetArea').find('[name*=deviceType]').remove();
				$('.bannerImgName').each(function(i, v) {
					//if($(this).text() != '') {
						$('#imageSetArea').append('<input type="hidden" name="evEventImageArr.imageName" value="' + $(this).text() + '" />');
						if($(this).attr('id') == 'pcEventImgName' || $(this).attr('id') == 'pcEventBgImgName') {
							$('#imageSetArea').append('<input type="hidden" name="evEventImageArr.deviceType" value="PC" />');
						} else if ($(this).attr('id') == 'moEventImgName' || $(this).attr('id') == 'moEventBgImgName'){
							$('#imageSetArea').append('<input type="hidden" name="evEventImageArr.deviceType" value="MO" />');
						}
					//}
				});

				var form = $.form(document.forms.frm);
				var couponListArr = cpnList.ExportData({"Type":"json"}).data;
				var productListArr = productList.ExportData({"Type":"json"}).data;

				if(couponListArr.length > 3 && $('[name=eventJoinType][value=C]').is(':checked')) {
					alert('쿠폰은 3개까지 등록할 수 있습니다.');
					$('[name=eventJoinType][value=C]').focus();
					return false;
				}

				document.forms.frm.pcEventContText.innerText = CKEDITOR.instances.pcEventContText.getData();
				document.forms.frm.mobileEventContText.innerText = CKEDITOR.instances.mobileEventContText.getData();
				document.forms.frm.eventStplatContText.innerText = CKEDITOR.instances.eventStplatContText.getData();

				if(eventTypeCode == '10003' && tmprSaveYn == 'N') {
					if(productListArr.length == 0) {
						alert('상품을 지정해주세요.');
						return false;
					}

					//드로우상품은 1개만 등록하도록 수정됨(0번째 상품만 체크)
					if(productListArr[0].sellStatCode != '10000') {
						alert('판매 대기중인 상품만 등록이 가능합니다.');
						return false;
					}

					//자바에서 처리하므로 주석처리
					/*var drawDuplCheck = abc.biz.promotion.event.product.drawDuplCheckCount(productListArr[0].prdtNo, $('#eventNo').val());
					if(!drawDuplCheck) {
						alert('이미 진행중이거나 대기중인 이벤트에 등록된 상품입니다.');
						return false;
					}*/
					$("#benefitTemplate1Tbody").find('select').prop('disabled', false);
				}
				form.submit({
				    'type': 'POST',
				    'url': url,
				    'data' : $.paramObject({evEventTargetCouponArr : couponListArr, evEventTargetProductArr : productListArr}),
				    'success': function(d) {
				    	alert('저장 되었습니다.');
			    		location.href = '/promotion/event';
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}

		});

		//프로모션 타입별 show, hide Area
		var prev_val;
		var next_val;
		var success = false;
		$('[name=eventTypeCode]').focus(function(e) {
			 prev_val = $(this).val();
			 if(prev_val == '') success = true;
			 else success = false;
		}).change(function() {
			$(this).blur();
			next_val = $(this).val();
			if(success) {
				_eventDetail.eventTypeDisplay(next_val, false);
				_eventDetail.benefitTypeClear();
				_eventDetail.benefitTypeInit(next_val);
				_eventDetail.benefitTypeTrigger(next_val);
				_eventDetail.benefitTypeDisplay();
			} else {
				if(confirm('값이 초기화 됩니다 변경하시겠습니까?')) {
					_eventDetail.eventTypeDisplay(next_val, false);
					_eventDetail.benefitTypeClear();
					_eventDetail.benefitTypeInit(next_val);
					_eventDetail.benefitTypeTrigger(next_val);
					_eventDetail.benefitTypeDisplay();
				} else {
					 $(this).val(prev_val);
					 return false;
				}
			}
		});

		/*$('[name=eventTypeCode]').on('change', function(e) {
			var eventTypeCode = $(this).val();

			_eventDetail.eventTypeDisplay(eventTypeCode, false);
			_eventDetail.benefitTypeClear();
			_eventDetail.benefitTypeInit(eventTypeCode);
			_eventDetail.benefitTypeTrigger(eventTypeCode);
			_eventDetail.benefitTypeDisplay();
		});*/

		$('#duplCheck').on('click', function(e) {
			var insdMgmtInfoText = $('[name=insdMgmtInfoText]').val();
			if(insdMgmtInfoText == "") {
				alert('내부 관리번호를 입력해주세요.');
				return;
			}

			$.ajax({
	            type :'get',
	            data : {insdMgmtInfoText : insdMgmtInfoText, eventNo : $('[name=eventNo]').val()},
	            url:'/promotion/event/duplCheck'
	        }).done(function(d){
	        	if(d){
	        		alert('사용가능한 번호입니다.');
	        		$('#duplCheckVal').val('Y');
	        	} else {
	        		alert('중복된 번호입니다. 다른 번호를 입력해주세요.');
	        		$('#duplCheckVal').val('N');
	        	}
	        }).fail(function(e){
	        	alert(e.message);
		    	console.log(e);
	        });
		});

		$('[name=chkDeviceModule]').on('click', function(e){
			if($(this).is(':checked')) {
				$('[name=deviceCodes]').prop('checked', true);
			} else {
				$('[name=deviceCodes]').prop('checked', false);
			}
		});

		$('[name=deviceCodes]').on('click', function(e) {
			if($('[name=deviceCodes]:checked').length == $('[name=deviceCodes]').length) {
				$('[name=chkDeviceModule]').prop('checked', true);
			} else {
				$('[name=chkDeviceModule]').prop('checked', false);
			}
		});

		/* 12/17 채널 체크박스 형식 유지에 따라 복구 */
		$('[name=chkChannelModule]').on('click', function(e){
			if($(this).is(':checked')) {
				$('[name*=chnnlNo]').prop('checked', true);
			} else {
				$('[name*=chnnlNo]').prop('checked', false);
			}
		});

		$('[name*=chnnlNo]').on('click', function(e) {
			if($('[name*=chnnlNo]:checked').length == $('[name*=chnnlNo]').length) {
				$('[name=chkChannelModule]').prop('checked', true);
			} else {
				$('[name=chkChannelModule]').prop('checked', false);
			}
		});

		$('#chkMemberGrade0').on('click', function(e) {
			var memberChangeCheck = false;
			if($('[name=empYn]:checked').length > 0) memberChangeCheck = true;
			if($(this).is(':checked') && memberChangeCheck) {
				if(confirm('일반 고객 대상으로 변경하시겠습니까?')) {
					$('[name=empYn]').prop('checked', false);
				} else {
					$(this).prop('checked', false);
				}
			}

			if($(this).is(':checked')) {
				$('[name*=memberTypeCode]').prop('checked', true);
				$('[name*=mbshpGradeCode]').prop('checked', true);
				$('[name*=mbshpGradeCode]').prop('disabled', false);
			} else {
				$('[name*=memberTypeCode]').prop('checked', false);
				$('[name*=mbshpGradeCode]').prop('checked', false);
				$('[name*=mbshpGradeCode]').prop('disabled', true);
			}

			_eventDetail.benefitTypeDisplay();
		});

		$('[name*=memberTypeCode]').on('click', function(e) {
			var memberChangeCheck = false;

			if($('[name=empYn]:checked').length > 0) memberChangeCheck = true;
			if($(this).is(':checked') && memberChangeCheck) {
				if(confirm('일반 고객 대상으로 변경하시겠습니까?')) {
					$('[name=empYn]').prop('checked', false);
				} else {
					$(this).prop('checked', false);
				}
			}

			if($(this).val() == '10001') {
				if($(this).is(':checked')) {
					$('[name*=mbshpGradeCode]').prop('checked', true);
					$('[name*=mbshpGradeCode]').prop('disabled', false);
				} else {
					$('[name*=mbshpGradeCode]').prop('disabled', true);
					$('[name*=mbshpGradeCode]').prop('checked', false);
				}
			}

			if($('[name*=memberTypeCode][value=10000]').is(':checked') && $('[name=pointDdctYn][value=Y]').is(':checked')) {
				alert('온라인 회원은 포인트가 차감되지 않습니다.');
				$('[name=pointDdctYn][value=N]').prop('checked', true);
				$('[name=ddctPointAmt]').val(0);
				$('.ddctPointAmtArea').hide();
			}

			if($('[name*=memberTypeCode][value=10000]').is(':checked') && $('[name*=benefitType]').find("option[value=P]:selected").length > 0) {
				alert('대상회원에 온라인 회원이 포함되어있습니다. 온라인 회원은 포인트가 지급되지 않습니다.');
			}

			if($('[name*=memberTypeCode][value=10000]').is(':checked') && $('[name*=benefitGbnCode]').find("option[value=10001]:selected").length > 0) {
				alert('대상회원에 온라인 회원이 포함되어있습니다. 온라인 회원은 포인트가 지급되지 않습니다.');
			}

			if($('[name*=memberTypeCode]:checked').length == $('[name*=memberTypeCode]').length) {
				$('#chkMemberGrade0').prop('checked', true);
			} else {
				$('#chkMemberGrade0').prop('checked', false);
			}

			if($('[name*=memberTypeCode][value=10000]').is(':checked') && $('[name*=memberTypeCode]:checked').length == 1) {
				$('[name=pointDdctYn][value=N]').prop('checked', true);
				$('.ddctPointAmtArea').hide();
			}

			_eventDetail.benefitTypeDisplay();
		});

		$('[name=empYn]').on('click', function(e) {
			var memberChangeCheck = false;
			if($('[name*=memberTypeCode]:checked').length > 0) memberChangeCheck = true;

			if($(this).is(':checked') && memberChangeCheck) {
				if(confirm('임직원 대상으로 변경하시겠습니까?')) {
					$('[name*=memberTypeCode]').prop('checked', false);
					$('#chkMemberGrade0').prop('checked', false);
				} else {
					$(this).prop('checked', false);
				}
			}
			_eventDetail.benefitTypeDisplay();
		});

		$('body').on('change', '[name=eventStplatUseYn]', function(e) {
			if($(this).val() == 'Y') $('#stplatArea').show();
			else $('#stplatArea').hide();
		});

		$('body').on('change', '[name=eventJoinType]', function(e) {
			var eventJoinType = $(this).val();
			//cpnList.RemoveAll();
			if(eventJoinType != 'C') {
				$('#eventCouponSheet').hide();
			} else {
				$('#eventCouponSheet').show();
			}

			/*$('[name=loginIdDupPermYn]').prop('disabled', false);
			$('[name=eventPblicteNoCnt]').prop('disabled', false);
			$('#pblicteNoDownBtn').prop('disabled', false);
			$('.searchCouponPop').prop('disabled', false);*/
			$('.targetEventJoinType').prop('disabled', false);
			$('#chkIDType02').prop('checked', false);
			$('.benefit1').show();
			$('#benefit2Th').attr('rowspan', '');
			$('.cpnTrArea').hide();
			$('#benefit2Th').attr('rowspan', 1);
			$('[name=loginIdDupPermYn]').val('');
			$('.benefit11').hide();
			if(eventJoinType == '') {
				$('.benefit1').hide();
				$('#chkIDType02').prop('disabled', true);
				$('[name=eventPblicteNoCnt]').prop('disabled', true);
				$('.searchCouponPop').prop('disabled', true);
				$('#pblicteNoDownBtn').prop('disabled', true);
			} else if(eventJoinType == 'C') {
				$('.benefit1').hide();
				$('#benefit2Th').attr('rowspan', 2);
				$('.cpnTrArea').show();
				$('#chkIDType02').prop('disabled', true);
				$('[name=eventPblicteNoCnt]').prop('disabled', true);
				$('#pblicteNoDownBtn').prop('disabled', true);
			} else if(eventJoinType == 'D') {
				$('#chkIDType02').prop('disabled', true);
				$('.searchCouponPop').prop('disabled', true);
			} else if(eventJoinType == 'L') {
				$('#chkIDType02').prop('checked', true);
				$('[name=eventPblicteNoCnt]').prop('disabled', true);
				$('#pblicteNoDownBtn').prop('disabled', true);
				$('.searchCouponPop').prop('disabled', true);

				if($("#chkIDType02").is(':checked')){
					$('[name=loginIdDupPermYn]').val('Y');
				}
				$('.benefit11').show();
			}
		});

		$('body').on('click', '.searchCouponPop', function(e) {
			var params = {};
			params.callback = 'abc.biz.promotion.event.detail.appendCoupon';
			params.multiple = true;
			_eventDetail.couponPopup(params);
		});

		$('body').on('click', '.btnDelBenefit', function(e) {
			$(this).closest('li').remove();
			_eventDetail.benefitIdxAddId('basic');
		});

		$('body').on('click', '#addBasicBenefit', function(e) {
			var standardType = $('#standardType').val();
			var standardDayCount = $('#standardDayCount').val();

			if(standardType == "") {
				alert('혜택유형을 선택해주세요.');
				return;
			}

			if(standardType == "C") {
				$('#basicBenefitArea').append($('#basicBenefitTemplate1').html());
			} else {
				$('#basicBenefitArea').append($('#basicBenefitTemplate2').html());
			}
			$('#basicBenefitArea').find('[name*=benefitType]').last().val(standardType).prop('selected', true);
			$('#basicBenefitArea').find('[name*=atendDayCount]').last().val(standardDayCount);

			_eventDetail.benefitIdxAddId('basic');
		});
		

		$('body').on('click', '#addGeBenefit', function(e) {
			var benefitType = $('#selGeBenefitType').val();

			if(benefitType == "") {
				alert('혜택유형을 선택해주세요.');
				return;
			}

			if($("#geBenefitArea > li").length > 0){
				alert("혜택을 중복 적용할수 없습니다");
				return;
			}
			
			if(benefitType == "C") {
				$('#geBenefitArea').append($('#geBenefitTemplate1').html());
			} else {
				$('#geBenefitArea').append($('#geBenefitTemplate2').html());
			}
			_eventDetail.benefitIdxAddId('geBenefit');
		});

		$('body').on('click', '.searchBenefitCouponPop, .searchBenefitGbnCouponPop', function(e) {
			var params = {};
			cpnIdx = $(this).attr('id');
			params.callback = 'abc.biz.promotion.event.detail.appendBenefitCoupon';
			params.multiple = false;
			_eventDetail.couponPopup(params);
		});

		$('body').on('click' , '.btnDelCpn', function(e) {
			$(this).closest('.search-item').remove();
		});
		
		$('body').on('click' , '.btnDelGeBenefit', function(e) {
			$(this).closest('li').remove();
		});
		

		$('body').on('change', '[name*=benefitType]', function(e) {
			$(this).siblings('.benefitRemovePoint').remove();
			var benefitType = $(this).val();
			$(this).closest('.ip-text-box').find('.search-item').remove();
			if(benefitType == "C") {
				$(this).closest('.ip-text-box').append('<button type="button" class="btn-sm btn-link searchBenefitCouponPop benefitRemovePoint">쿠폰조회3</button>');
				$(this).closest('.ip-text-box').append('<input type="hidden" name="evEventAttendanceCheckBenefitArr.benefitValue" class="benefitRemovePoint" value="">');
			} else {
				$(this).closest('.ip-text-box').append('<input type="text" name="evEventAttendanceCheckBenefitArr.benefitValue" class="ui-input num-unit100000000 benefitRemovePoint" maxlength="8" title="포인트 입력">');
				$(this).closest('.ip-text-box').append('<span class="text benefitRemovePoint">포인트</span>');
			}

			_eventDetail.benefitIdxAddId('basic');
		});

		$('body').on('change', '[name*=benefitGbnCode]', function(e) {
			var benefitGbnCode = $(this).val();
			var appendArea = $(this).closest('.benefitGbnSpan');
			$(appendArea).find('.benefitGbnArea').remove();
			$(this).closest('.ip-text-box').find('.search-item').remove();
			if(benefitGbnCode == '10000') {
				$(appendArea).append($('#benefitGbnCouponTemplate').html());
				_eventDetail.benefitIdxAddId('gbn');
			} else if(benefitGbnCode == '10001') {
				$(appendArea).append($('#benefitGbnPointTemplate').html());
			} else if(benefitGbnCode == '10002'){
				imgIdx++;
				$(appendArea).append($('#benefitGbnImgTemplate').html());
				$(appendArea).find('.imgBoxArea').append('<input type="file" id="benefitImgIdx_' + imgIdx + '" name="rouletteImages.file" title="첨부파일 추가">');
				$(appendArea).find('.imgBoxArea').append('<label for="benefitImgIdx_' + imgIdx + '">찾아보기</label>');
				$(appendArea).find('.subject').attr('id', 'benefitImgNameIdx_' + imgIdx);
				new abc.biz.display.common.processImage( { file: '#benefitImgIdx_' + imgIdx, name: '#benefitImgNameIdx_' + imgIdx, allow: 'jpg, jpeg, png, gif, bmp' } );
			} else { //꽝
				$(appendArea).append('<div class="benefitGbnArea"><input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="" /></div>');
			}

			$(this).closest('.benefitGbnTr').find('[name*=issueLimitCount]').val('0');
			$(this).closest('.benefitGbnTr').find('[name*=issueLimitCount]').prop('readonly', false);
		});

		$('body').on('change', '[name*=winRateCode]', function(e) {
			if($(this).val() == '10006') {
				$(this).siblings('.benefitGbnManual').show();
				$(this).closest('.ip-text-box').find('.winRate').val('');
				$(this).siblings('.num-unit100').val('0')
			}
			else {
				$(this).closest('.ip-text-box').find('.winRate').val($(this).find('option:selected').data('rate'))
				$(this).siblings('.benefitGbnManual').hide();
				$(this).siblings('.num-unit100').val('0')
			}
		});

		$('body').on('change', '[name=pointDdctYn]', function(e) {
			if($(this).val() == 'N') {
				$('.ddctPointAmtArea').hide();
				$('[name=ddctPointAmt]').val(0);
			} else {
				$('.ddctPointAmtArea').show();
			}

			//온라인 회원만 포인트차감여부 X
			if($('[name*=memberTypeCode][value=10000]').is(':checked') && $('[name*=memberTypeCode]:checked').length > 0) {
				alert('온라인 회원은 포인트가 차감되지 않습니다.');
				$('[name=pointDdctYn][value=N]').prop('checked', true);
				$('[name=ddctPointAmt]').val(0);
				$('.ddctPointAmtArea').hide();
			}
		});
		
		$('body').on('change', '[name=geBenefitYn]', function(e) {
			if($(this).val() == 'N') {
				$('.geBenefitArea').hide();
				$("#geBenefitArea").empty();
			} else {
				$('.geBenefitArea').show();
			}
		});

		$('#chkMemberGrade2').on('change', function(e) {
			if($('[name=eventTypeCode]').val() == '10003') {
				 this.checked = true;
				 $('[name*=mbshpGradeCode]').prop('checked', true);
				 $('[name*=mbshpGradeCode]').prop('disabled', false);
				 _eventDetail.benefitTypeDisplay();
			}
		});

		$('#chkIDType02').on('change', function(e) {
			if($(this).is(':checked')) {
				$('[name=loginIdDupPermYn]').val('Y');
			} else {
				$('[name=loginIdDupPermYn]').val('N');
			}
		});

		$('.targetMembershp').on('change', function(e) {
			if($('[name=eventTypeCode]').val() == '10003') {
				this.checked = true;
			}
		});

		$('.storePopup').on('click', function(e) {
			abc.storeSearchPopup('abc.biz.promotion.event.detail.appendStore', true);
		});

		$('body').on('click', '.btnDelStore', function(e) {
			$(this).closest('li').remove();
		});

		/*$('[name=prdtRecptStoreType]').on('click', function(e) {
			if($(this).val() == 'A' || $(this).val() == 'O') {
				if($(this).is(':checked')) {
					$(this).closest('.ui-chk').siblings('.storePopup').show();
				} else {
					$(this).closest('.ui-chk').siblings('.storePopup').hide();
					$('#storeArea').empty();
				}
			}
		});*/
		$('.checkStore').on('click', function(e) {
			$('[name=pickupStartYmd]').val('');
			$('[name=pickupEndYmd]').val('');
			if($(this).is(':checked')) {
				$(this).closest('.ui-chk').siblings('.storePopup').show();
				$('.offlinePickupArea').show();
			} else {
				$(this).closest('.ui-chk').siblings('.storePopup').hide();
				$('#storeArea').empty();
				$('.offlinePickupArea').hide();
			}
		});

		$('.checkOnln').on('click', function() {
			if($(this).is(':checked')) $('[name=onlnRecptYn]').val('Y');
			else $('[name=onlnRecptYn]').val('N');
		});

		// 이벤트 기간 체크박스
		$('[name=eventTermSetupYn]').on('change', function(e) {
			if($(this).val() != 'Y') {
				$('.eventTermSetupArea').prop('disabled', true);
			} else {
				$('.eventTermSetupArea').prop('disabled', false);
			}
		});
		

		$('#pblicteNoDownBtn').on('click', function() {
			$('[name=frm]').attr('action', '/promotion/event/pblicte/excel/download')
			.prop("method", "post").submit();
		});

		// 당첨발표여부 체크박스
		$('body').on('change', '[name=przwrPblcYn]', function() {
			$('[name=paramPrzwrPblcTodoYmd]').val('');
			if($('[name=przwrPblcYn]:checked').val() != 'Y') {
				$('.pblcTodoTarget').prop('disabled', true);
			} else {
				$('.pblcTodoTarget').prop('disabled', false);
			}
		});

		$(".adminDetailPop").on('click', function(){
			abc.adminDetailPopup($(this).data("adminNo"));
		});

		$("[name*=benefitGbnCode]").on('change', function(){
			if($('#chkMemberGrade1').is(':checked') && $(this).val() == '10001') {
				alert('대상회원에 온라인 회원이 포함되어있습니다. 온라인 회원은 포인트가 지급되지 않습니다.');
			}
		});

		$('body').on('change', '[name*=benefitType], #standardType, #geBenefitType', function(){
			if($('#chkMemberGrade1').is(':checked') && $(this).val() == 'P') {
				alert('대상회원에 온라인 회원이 포함되어있습니다. 온라인 회원은 포인트가 지급되지 않습니다.');
			}
		});

		$('[name=ddctPointAmt]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=insdMgmtInfoText]').on("keyup change", function(e) {
			$('#duplCheckVal').val('N');
			if (!(e.keyCode >=37 && e.keyCode<=40)) {
				var v = $(this).val();
				$(this).val(v.replace(/[^a-z0-9~!@\#$%^&*\()\-=+_']/gi,''));
			}
		});

		$('[name=eventPblicteNoCnt]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('body').on("keyup change", '[name*=benefitValue]', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('body').on("keyup change", '.inputAtendDayCount', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('body').on("keyup change", '[name*=issueLimitCount]', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('body').on("keyup change", '[name*=winRateValue]', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('#roulettePreviewBtn').on('click', function() {
			var url = "/promotion/event/roulette/preivew/popup";
			abc.open.popup({
				url 	:	url,
				method	: 	"get",
				title 	:	"룰렛 영역",
				width 	:	'660',
				height	:	'720',
			});
		});
	}

	_eventDetail.benefitIdxAddId = function(benefitIdxType) {
		var idValue = '';
		if(benefitIdxType == 'basic') {
			idValue = 'basicBenefitIdx_';
			$('.searchBenefitCouponPop').each(function(i, v) {
				$(this).attr('id', idValue + i);
			});
		}else if(benefitIdxType == 'geBenefit'){
			idValue = 'geBenefitIdx_';
			$('.searchBenefitCouponPop').each(function(i, v) {
				$(this).attr('id', idValue + i);
			});
		}else {
			idValue = 'gbnBenefitIdx_';
			$('.searchBenefitGbnCouponPop').each(function(i, v) {
				$(this).attr('id', idValue + i);
			});
		}
	}

	_eventDetail.eventTypeDisplay = function(eventTypeCode, initType) {
		//type 별 input tag 수정

		if(!initType) $('.eventType').remove();

		$('.bgImageArea').hide();
		if(eventTypeCode == '10004') $('.bgImageArea').show();

		if(eventTypeCode == '10001' || eventTypeCode == '10002' || eventTypeCode == '10004') {
			$('#eventTypeBefore').after($('#eventType1Template').html());
		} else if(eventTypeCode == '10003') {
			$('#eventTypeBefore').after($('#eventType2Template').html());
			$("#benefitTemplate1Tbody").find('select').prop('disabled', true);
		} else {
			$('#eventTypeBefore').after($('#eventType1Template').html());
		}

		if(!initType) {
			$('[name=srchKeyWord]').val('');
			$('.dateTarget').val('');
		}

		//$('.dateTarget').datepicker();
		$(".ui-cal").each(function(){
			abc.namespace.front.backOffice.setDatepicker(this);
		});
	}

	_eventDetail.benefitTypeInit = function(eventTypeCode) {
		//type 별 응모조건 수정
		$('.dispTrArea').hide();
		if(eventTypeCode == '10001') {
			$('.benefit1').show();
		} else if(eventTypeCode == '10002') {
			$('.benefit3').show();
		} else if(eventTypeCode == '10003') {
			$('.benefit1').show();
			$('.benefit4').show();
		} else if(eventTypeCode == '10004') {
			$('.benefit1').show();
			$('.benefit5').show();
		} else {
			var eventJoinTypeVal = $('[name=eventJoinType]:checked').val();
			if(eventJoinTypeVal != ''){
				$('.benefit1').show();
			}
			$('.benefit2').show();

			if(eventJoinTypeVal == 'C') {
				$('.cpnTrArea').show();
				$('.benefit1').hide();
			}else if(eventJoinTypeVal == 'L'){
				$('.benefit11').show();
			}
		}
	}

	_eventDetail.benefitTypeTrigger = function(eventTypeCode) {
		$('#radioTerm02').prop('disabled', false);
		$('.targetMemberGrade').prop('checked', false);
		$('.targetMembershp').prop('checked', false);
		$('.targetMemberGrade').prop('disabled', false);
		$('.targetMembershp').prop('disabled', false);

		if(eventTypeCode == '10001') {
			$('#chkMemberGrade1').trigger('click');
			$('#chkMemberGrade2').trigger('click');
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		} else if(eventTypeCode == '10002') {
			$('#chkMemberGrade1').trigger('click');
			$('#chkMemberGrade2').trigger('click');
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		} else if(eventTypeCode == '10003') {
			$('#radioTerm02').prop('disabled', true);
			$('#chkMemberGrade2').trigger('click');
			$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade1').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
			$('#chkEmp01').prop('disabled', true);
		} else if(eventTypeCode == '10004') {
			$('#chkMemberGrade1').trigger('click');
			$('#chkMemberGrade2').trigger('click');
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		}
	}

	_eventDetail.benefitTypeDisplay = function() {
		$('#benefitTemplate1Tbody').empty();
		$('.targetMemberCheck:checked').each(function(i, v) {
			$('#benefitTemplate1Tbody').append($('#benefitTbodyTemplate').html());
			$('#benefitTemplate1Tbody').find('.targetGradeName').last().text($('label[for=' + $(v).attr('id') + ']').text());
		});
		if($('[name=eventTypeCode]').val() == "10003"){
			$("#benefitTemplate1Tbody").find('select').prop('disabled', true);
		}
	}

	_eventDetail.benefitTypeClear = function() {
		$('[name=eventName]').val('');
		$('[name=insdMgmtInfoText]').val('');
		$('[name=chkChannelModule]').prop('checked', true);

		/* 12/17 채널 체크박스 형식 유지에 따라 복구 */
		//$('[name*=chnnlNo]').eq(0).prop('checked', true);
		$('[name*=chnnlNo]').prop('checked', true);

		$('[name=artDispYn]').eq(0).prop('checked', true);
		$('[name=chkDeviceModule]').prop('checked', true);
		$('[name=deviceCodes]').prop('checked', true);
		$('.eventTermSetupArea').prop('disabled', false);

		$('#storeArea').empty();
		$('[name*=joinLimitTypeCode]').val('10000');
		$('[name=pointDdctYn]').eq(0).prop('checked', true);
		$('[name=ddctPointAmt]').val(0);
		$('.ddctPointAmtArea').hide();
		$('[name=eventTermSetupYn]').eq(0).prop('checked', true);
		$('[name=eventStplatUseYn]').eq(0).prop('checked', true);
		$('#stplatArea').hide();
		CKEDITOR.instances.eventStplatContText.setData("");
		$('[name=eventJoinType]').eq(0).prop('checked', true);
		$('[name=radioIDTypeModule]').eq(0).prop('checked', true);
		cpnList.RemoveAll();
		productList.RemoveAll();
		$('#standardDayCount').val('');
		$('#basicBenefitArea').empty();
		$('[name*=benefitName]').val('');
		$('[name*=benefitGbnCode]').val('');
		$('.benefitGbnArea').remove();
		$('.benefitGbnSpan').each(function(i, v) {
			$(this).find('.search-item').remove();
		});
		$('[name*=issueLimitCount]').val('0');
		$('.totalIssueCountArea').text('0');
		$('[name*=winRateCode]').val('10000');
		$('.winRate').val('100');
		$('.benefitGbnManual').hide();

		$('#pcEventBgImgName').text('');
		$('#moEventBgImgName').text('');
		$('#pcEventBgImgName').siblings('.btn-file-del').trigger('click');
		$('#moEventBgImgName').siblings('.btn-file-del').trigger('click');

		$('[name=prdtRecptStoreType]').prop('checked', false);
		$('.storePopup').hide();

		$('[name=paramEventStartYmd]').val('');
		$('[name=paramEventEndYmd]').val('');
		$('[name=paramEventStartH]').val('00');
		$('[name=paramEventStartM]').val('00');
		$('[name=paramEventEndH]').val('23');
		$('[name=paramEventEndM]').val('50');
		
		$('[name=paramPrzwrPblcTodoYmd]').val('');
		$('[name=paramPrzwrPblcTodoStartH]').val('00');
		$('[name=paramPrzwrPblcTodoStartM]').val('00');
		
		$('[name=paramPrzwrOrderStartYmd]').val('');
		$('[name=paramPrzwrOrderEndYmd]').val('');
		$('[name=paramPrzwrOrderStartH]').val('00');
		$('[name=paramPrzwrOrderStartM]').val('00');
		$('[name=paramPrzwrOrderEndH]').val('23');
		$('[name=paramPrzwrOrderEndM]').val('50');
		
		$('[name=pickupStartYmd]').val('');
		$('[name=pickupEndYmd]').val('');
		
	}

	_eventDetail.couponPopup = function(params){
		var url = "/promotion/coupon/popup";
		abc.open.popup({
			url 	:	url,
			method	: 	"get",
			title 	:	"coupon-search",
			width 	:	'1240',
			height	:	'950',
			params	:	params
		});
	}

	_eventDetail.appendCoupon = function(d) {
		var sheet = eval("cpnList");	// 시트 객체 획득
		var insertRowNumber = "";
		var initialRowData = {};	// 추가될 행 데이터
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		var duplCount = 0;
		$.each(d, function(i, v) {
			var duplCouponCheck = _eventDetail.duplCouponCheck(v.cpnNo);
			if(duplCouponCheck) {
				insertRowNumber = sheet.RowCount() + 1;
				initialRowData = {
					/*"checkCoupon" : "",*/
					"totalIssueLimitCount" : v.totalIssueLimitYn == 'Y' ? v.totalIssueLimitCount : '무제한',
					"per1psnMaxIssueCount" : v.per1psnIssueLimitYn == 'Y' ? v.per1psnMaxIssueCount : '무제한',
					"cpnNo"		: v.cpnNo,
					"cpnName"	: v.cpnName,
					"cpnTypeCodeName" : v.cpnTypeCodeName,
					"dscntText" : v.dscntText,
					"deviceCodeName" : v.deviceCodeName,
					"deviceCodeName" : v.deviceCodeName,
					"useYn" : v.useYn,
					"issueStartDtm" : v.issueStartDtm,
					"issueEndDtm" : v.issueEndDtm
				};
				sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
			} else {
				duplCount++;
			}
		});

		if(duplCount > 0) {
			alert('중복된 ' + duplCount + '건의 쿠폰은 제외됩니다.');
		}
	}

	_eventDetail.duplCouponCheck = function(cpnNo) {
		var cpnListData = cpnList.ExportData({"Type":"json"}).data;
		var duplCheck = true;

		$.each(cpnListData, function(i, v) {
			if(v.cpnNo == cpnNo) {
				duplCheck = false;
				return false;
			}
		});

		return duplCheck;
	}

	_eventDetail.appendBenefitCoupon = function(d) {
		$('#' + cpnIdx).siblings('.search-item').remove();
		$('#' + cpnIdx).after($('#searchItemTemplate').html());
		$('#' + cpnIdx).siblings('.search-item').find('.subject').text(d[0].cpnName);
		if(cpnIdx.indexOf('basic') != -1){
			$('#' + cpnIdx).closest('li').find('[name*=benefitValue]').val(d[0].cpnNo);
		}else if(cpnIdx.indexOf('geBenefit') != -1){
			$('#' + cpnIdx).closest('li').find('[name*=geBenefitValue]').val(d[0].cpnNo);
			$('#' + cpnIdx).closest('li').find('[name*=geBenefitName]').val(d[0].cpnName);
		}else {
			var limitCount = 0;
			if(d[0].totalIssueLimitYn == 'Y') limitCount = d[0].totalIssueLimitCount;
			else limitCount = 9999;

			var benefitValueType =  $('#' + cpnIdx).closest('.benefitGbnTr').find('[name*=benefitValue]').val();

			/*
			if ( (typeof benefitValueType !== 'undefined' ) || benefitValueType ==  undefined) {
				$('#' + cpnIdx).closest('.ip-text-box').find('.search-item').append('<input type="hidden" name="evEventRouletteBenefitArr.benefitValue" value="">');
			}
			*/

			$('#' + cpnIdx).closest('.benefitGbnTr').find('[name*=issueLimitCount]').val(limitCount);
			$('#' + cpnIdx).closest('.benefitGbnTr').find('[name*=issueLimitCount]').prop('readonly', true);
			$('#' + cpnIdx).closest('.benefitGbnTr').find('.totalIssueCountArea').val(d[0].totalIssueCount);
			$('#' + cpnIdx).closest('.benefitGbnTr').find('[name*=issueCount]').val(d[0].totalIssueCount);
			$('#' + cpnIdx).closest('.benefitGbnTr').find('[name*=benefitValue]').val(d[0].cpnNo);
		}
	}

	_eventDetail.appendStore = function(d) {
		if(d != null && d.length > 0) {
			$.each(d, function(i, v) {
				$('#storeArea').append($('#searchStoreTemplate').html());
				$('#storeArea').find('.subject').last().text(v.storeName);
				$('#storeArea').find('li').last().append('<input type="hidden" name="evEventProductReceiptStoreArr.storeNo" value="' + v.storeNo + '" />');
			});
		}
	}

	_eventDetail.fieldDisabled = function(){
		var eventTypeCode = $('[name=eventTypeCode]').val();

		//타입별 대상회원 disabled check
		if(eventTypeCode == '10001') {
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		} else if(eventTypeCode == '10002') {
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		} else if(eventTypeCode == '10003') {
			$('#radioTerm02').prop('disabled', true);
			$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade1').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
			$('#chkEmp01').prop('disabled', true);
		} else if(eventTypeCode == '10004') {
			//$('#chkMemberGrade0').prop('disabled', true);
			$('#chkMemberGrade3').prop('disabled', true);
		}

		if($('#chkMemberGrade2').is(':checked')) {
			$('[name*=mbshpGradeCode]').prop('disabled', false);
		}

		$('.targetMemberCheck:checked').each(function(i, v) {
			$('#benefitTemplate1Tbody').find('.targetGradeName').eq(i).text($('label[for=' + $(v).attr('id') + ']').text());
		});

		if($('[name*=memberTypeCode]:checked').length == $('[name*=memberTypeCode]').length) {
			$('#chkMemberGrade0').prop('checked', true);
		} else {
			$('#chkMemberGrade0').prop('checked', false);
		}

		var eventJoinType = $('[name=eventJoinType]:checked').val();
		$('.targetEventJoinType').prop('disabled', true);

		if(eventJoinType == '') {
			$('.benefit1').hide();
		} else if(eventJoinType == 'C') {
			$('.benefit1').hide();
			$('#benefit2Th').attr('rowspan', 2);
			$('.cpnTrArea').show();
			$('.searchCouponPop').prop('disabled', false);
		} else if(eventJoinType == 'D') {
			$('[name=eventJoinType]').prop('disabled', true);
			$('#pblicteNoDownBtn').prop('disabled', false);
		} else if(eventJoinType == 'L') {
			if($('[name=loginIdDupPermYn]').val() == 'Y' ) {
				$('#chkIDType02').prop('checked', true);
			}
			$('#chkIDType02').prop('disabled', false);
		}

		if($('[name=eventTermSetupYn]:checked').val() != 'Y') {
			$('.eventTermSetupArea').prop('disabled', true);
		} else {
			$('.eventTermSetupArea').prop('disabled', false);
		}

		_eventDetail.benefitTypeInit(eventTypeCode);

		if($('#storeArea').find('[name*=storeNo]').length != 0) {
			$('.offlinePickupArea').show();
		}

		if($('[name=deviceCodes]:checked').length == $('[name=deviceCodes]').length) {
			$('[name=chkDeviceModule]').prop('checked', true);
		} else {
			$('[name=chkDeviceModule]').prop('checked', false);
		}

		/* 12/17 채널 체크박스 형식 유지에 따라 복구 */
		if($('[name*=chnnlNo]:checked').length == $('[name*=chnnlNo]').length) {
			$('[name=chkChannelModule]').prop('checked', true);
		} else {
			$('[name=chkChannelModule]').prop('checked', false);
		}

		if($('[name*=memberTypeCode]:checked').length == $('[name*=memberTypeCode]').length) {
			$('#chkMemberGrade0').prop('checked', true);
		} else {
			$('#chkMemberGrade0').prop('checked', false);
		}

		if($('[name=przwrPblcYn]:checked').val() == 'Y') {
			$('.pblcTodoTarget').prop('disabled', false);
		} else {
			$('.pblcTodoTarget').prop('disabled', true);
		}
	}

	_eventDetail.imageSet = function() {
		$('[name*=rouletteImages]').each(function(i, v) {
			new abc.biz.display.common.processImage( { file: '#' + $(v).attr('id'), name: '#' + $(v).closest('.file-list').find('.subject').attr('id'), allow: 'jpg, jpeg, png, gif, bmp' } );
		});
	}

	_eventDetail.drawProductExistCheck = function() {

	}

	/**
	 * 유효성 체크
	 */
	_eventDetail.valid = function(){

		var frm = document.forms.frm;

		var eventTypeCode = $('[name=eventTypeCode]').val();

		var startYmd = new Date($('[name=paramEventStartYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramEventStartH]').val() + ':' + $('[name=paramEventStartM]').val() + ':00');
		var endYmd = new Date($('[name=paramEventEndYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramEventEndH]').val() + ':' + $('[name=paramEventEndM]').val() + ':00');

		if($('[name=eventTermSetupYn]:checked').val() == 'Y') {
			if($('[name=paramEventStartYmd]').val() == '') {
				alert('이벤트 기간을 입력해주세요.');
				$(frm.paramEventStartYmd).focus();
				return false;
			}

			if($('[name=paramEventEndYmd]').val() == '') {
				alert('이벤트 기간을 입력해주세요.');
				$(frm.paramEventEndYmd).focus();
				return false;
			}

			if(startYmd.getTime() > endYmd.getTime()) {
				alert('이벤트 종료일이 시작일보다 앞설 수 없습니다.');
				$(frm.paramEventEndYmd).focus();
				return false;
			}
		}

		if($('[name=insdMgmtInfoText]').val() != '' && $('#duplCheckVal').val() != 'Y') {
			alert('내부 관리번호 중복확인 해주시기 바랍니다.');
			return false;
		}

		if($('[name=przwrPblcYn]:checked').val() == 'Y') {
			
			if($('[name=paramPrzwrPblcTodoYmd]').val() == '') {
				alert('당첨발표 날짜를 입력해주세요.');
				$(frm.przwrPblcYn).focus();
				return false;
			}

		//	var przwrPblcTodoYmd = new Date($('[name=przwrPblcTodoYmd]').val().replaceAll('\\.', '/'));
			var przwrPblcTodoYmd = new Date($('[name=paramPrzwrPblcTodoYmd]').val().replaceAll('\\.', '/') + ' '
					+ $('[name=paramPrzwrPblcTodoStartH]').val() + ':' + $('[name=paramPrzwrPblcTodoStartM]').val() + ':00');
			console.log("przwrPblcTodoYmd : " + przwrPblcTodoYmd);
			if(przwrPblcTodoYmd.getTime() < endYmd.getTime()) {
				alert('당첨발표일이 이벤트일을 앞설 수 없습니다.');
				$(frm.przwrPblcYn).focus();
				return false;
			}
		}

		if(eventTypeCode == '10000') {
			if($('[name=eventJoinType]:checked').val() == 'D') {
				if($('[name=eventPblicteNoCnt]').val() == 0 || $('[name=eventPblicteNoCnt]').val() == '') {
					alert('난수를 1이상 입력해주세요.');
					$(frm.eventPblicteNoCnt).focus();
					return false;
				}
			}
		}

		if(eventTypeCode != '10002') {
			if(!$.isNumeric(frm.ddctPointAmt.value)){
				alert('포인트 차감여부는 숫자만 입력이 가능합니다.');
				$(frm.ddctPointAmt).focus();
				return false;
			}
		}

		if(eventTypeCode == '10002') {
			var atendDayCheck = true;
			var atendDayCountArr = [];
			$('#basicBenefitArea').find('[name*=atendDayCount]').each(function(i, v){
				if(!$.isNumeric($(v).val())) {
					atendDayCheck = false;
					return false;
				}

				atendDayCountArr.push($(v).val());
			});

			if(!atendDayCheck) {
				alert('출석일수는 숫자만 입력이 가능합니다.');
				return false;
			}

			var resultsAtendDay = '';
			var compareAtendDayCountArr = atendDayCountArr.slice().sort();
			for (var i = 0; i < atendDayCountArr.length - 1; i++) {
			    if (compareAtendDayCountArr[i + 1] == compareAtendDayCountArr[i]) {
			    	resultsAtendDay = compareAtendDayCountArr[i];
			    	break;
			    }
			}

			//atendDayCountArr = atendDayCountArr.filter((item, index) => atendDayCountArr.indexOf(item) !== index); ie에서 안먹힘
			if(resultsAtendDay != '' && resultsAtendDay != undefined) {
				alert('출석일수 ' + resultsAtendDay + '일이 중복되었습니다');
				return false;
			}
		}

		if(eventTypeCode == '10003') {
			if($('[name=paramPrzwrOrderStartYmd]').val() == '') {
				alert('당첨자 구입일을 입력해주세요.');
				$(frm.przwrOrderStartYmd).focus();
				return false;
			}

			if($('[name=paramPrzwrOrderEndYmd]').val() == '') {
				alert('당첨자 구입일을 입력해주세요.');
				$(frm.przwrOrderEndYmd).focus();
				return false;
			}

			
//			var przwrOrderStartYmd = new Date($('[name=przwrOrderStartYmd]').val().replaceAll('\\.', '/'));
//			var przwrOrderEndYmd = new Date($('[name=przwrOrderEndYmd]').val().replaceAll('\\.', '/'));
			
			var przwrOrderStartYmd = new Date($('[name=paramPrzwrOrderStartYmd]').val().replaceAll('\\.', '/') + ' '
					+ $('[name=paramPrzwrOrderStartH]').val() + ':' + $('[name=paramPrzwrOrderStartM]').val() + ':00');
			
			var przwrOrderEndYmd = new Date($('[name=paramPrzwrOrderEndYmd]').val().replaceAll('\\.', '/') + ' '
					+ $('[name=paramPrzwrOrderEndH]').val() + ':' + $('[name=paramPrzwrOrderEndM]').val() + ':00');

			if(przwrOrderStartYmd.getTime() > przwrOrderEndYmd.getTime()) {
				alert('당첨자구입 종료일이 시작일을 앞설 수 없습니다.');
				$(frm.przwrOrderEndYmd).focus();
				return false;
			}

			if(przwrOrderStartYmd.getTime() < endYmd.getTime()) {
				alert('당첨자구입 시작일이 이벤트종료일을 앞설 수 없습니다.');
				$(frm.przwrOrderStartYmd).focus();
				return false;
			}

			if(!$('.checkStore').is(':checked') && !$('.checkOnln').is(':checked')) {
				alert('픽업매장을 선택해주세요.');
				$('.checkOnln').focus();
				return false;
			}

			// 드로우 이벤트 픽업 매장 선택 체크
			if(!$('.checkOnln').is(':checked') && $('.checkStore').is(':checked')){
				$('[name=onlnRecptYn]').val('N');
			}

			if($('[name=przwrPblcYn]:checked').val() == 'Y') {
				if(przwrOrderStartYmd.getTime() < przwrPblcTodoYmd.getTime()) {
					alert('당첨발표일이 당첨자구입일을 앞설 수 없습니다.');
					$(frm.przwrPblcTodoYmd).focus();
					return false;
				}
			}

			if($('.checkStore').is(':checked')) {
				if($('[name*=storeNo]').length == 0) {
					alert('오프라인 매장을 선택해주세요.');
					$('.checkStore').focus();
					return false;
				}

				if($('[name=pickupStartYmd]').val() == '') {
					alert('오프라인 픽업가능 기간을 입력해주세요.');
					$(frm.pickupStartYmd).focus();
					return false;
				}

				if($('[name=pickupEndYmd]').val() == '') {
					alert('오프라인 픽업가능 기간을 입력해주세요.');
					$(frm.pickupEndYmd).focus();
					return false;
				}

				var pickupStartYmd = new Date($('[name=pickupStartYmd]').val().replaceAll('\\.', '/'));
				var pickupEndYmd = new Date($('[name=pickupEndYmd]').val().replaceAll('\\.', '/'));

				if(pickupStartYmd.getTime() > pickupEndYmd.getTime()) {
					alert('픽업가능 종료일이 시작일을 앞설 수 없습니다.');
					$(frm.pickupEndYmd).focus();
					return false;
				}

				if(pickupStartYmd.getTime() < endYmd.getTime()) {
					alert('픽업가능 시작일이 이벤트종료일을 앞설 수 없습니다.');
					$(frm.pickupStartYmd).focus();
					return false;
				}

			}
		}

		if(eventTypeCode == '10004') {
			var rouletteCheck = true;
			var winRate = 0;
			$('[name*=issueLimitCount]').each(function(i, v){
				if(!$.isNumeric($(v).val())) {
					rouletteCheck = false;
					return false;
				}
			});

			$('[name*=winRateValue]').each(function(i, v){
				if($(v).val() != '') {
					if(!$.isNumeric($(v).val())) {
						rouletteCheck = false;
						return false;
					} else {
						winRate += parseInt($(v).val());
					}
				}
			});

			$('[name*=winRateCode]').each(function(i, v){
				if($.isNumeric($(v).find('option:selected').data('rate'))) {
					winRate += parseInt($(v).find('option:selected').data('rate'));
				}
			});

			if(!rouletteCheck) {
				alert('제한수량 또는 당첨비율은 숫자만 입력이 가능합니다.');
				return false;
			}

			if(winRate != 100) {
				alert('당첨비율을 확인해주세요.');
				$('[name*=winRateCode]').focus();
				return false;
			}

			var rouletteBenefitCheck = true;
			var rouletteBenefitIndex = 0;
			$('.benefitGbnTr').each(function(i, v) {
				if($(this).find('[name*=benefitGbnCode]').val() != '10003') {
					if($(this).find('[name*=benefitGbnCode]').val() == '10002') {
						if($(this).find('.rouletteImgNameArea').text() == '') {
							rouletteBenefitCheck = false;
							rouletteBenefitIndex = i;
							return false;
						}
					} else {
						if($(v).find('[name*=benefitValue]').length == 0 || $(v).find('[name*=benefitValue]').val() == '') {
							rouletteBenefitCheck = false;
							rouletteBenefitIndex = i;
							return false;
						}
					}

				}
			});

			if(!rouletteBenefitCheck) {
				alert('룰렛 혜택을 확인해주세요.');
				$('[name*=benefitGbnCode]').eq(rouletteBenefitIndex).focus();
				return false;
			}
		}

		//if($('[name=deviceCodes][value=10000]').is(':checked')) {
			if($('#pcEventImgName').text() == '') {
				alert('PC 목록 섬네일은 필수입니다.');
				return false;
			}

			if(CKEDITOR.instances.pcEventContText.getData() == '') {
				alert('PC 상세(이미지 정보)는 필수입니다.');
				return false;
			}
		//}

		//if($('[name=deviceCodes][value=10001]').is(':checked') || $('[name=deviceCodes][value=10002]').is(':checked')) {
			if($('#moEventImgName').text() == '') {
				alert('MOBILE 목록 섬네일은 필수입니다.');
				return false;
			}

			if(CKEDITOR.instances.mobileEventContText.getData() == '') {
				alert('MOBILE 상세(이미지 정보)는 필수입니다.');
				return false;
			}
		//}

		if(eventTypeCode == '10004') {
			//if($('[name=deviceCodes][value=10000]').is(':checked')) {
				if($('#pcEventBgImgName').text() == '') {
					alert('PC 룰렛판이미지는 필수입니다.');
					return false;
				}
			//}

			//if($('[name=deviceCodes][value=10001]').is(':checked') || $('[name=deviceCodes][value=10002]').is(':checked')) {
				if($('#moEventBgImgName').text() == '') {
					alert('MOBILE 룰렛판이미지는 필수입니다.');
					return false;
				}
			//}
		}

		return true;
	}


	/**
	 * 임시저장 필수값 유효성 체크
	 */
	_eventDetail.tempValid = function(){

		var frm = document.forms.frm;

		var eventTypeCode = $('[name=eventTypeCode]').val();

	 	if($('[name=eventName]').val() == '') {
			alert('이벤트명을  입력해주세요.');
			$(frm.eventName).focus();
			return false;
		}

		if($('[name*=memberTypeCode]:checked').length  < 0) {
			alert('대상 회원을 선택해 주세요');
			$(frm.memberTypeCode).focus();
			return false;
		}

		var startYmd = new Date($('[name=paramEventStartYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramEventStartH]').val() + ':' + $('[name=paramEventStartM]').val() + ':00');
		var endYmd = new Date($('[name=paramEventEndYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramEventEndH]').val() + ':' + $('[name=paramEventEndM]').val() + ':00');

		if($('[name=eventTermSetupYn]:checked').val() == 'Y') {
			if($('[name=paramEventStartYmd]').val() == '') {
				alert('이벤트 기간을 입력해주세요.');
				$(frm.paramEventStartYmd).focus();
				return false;
			}

			if($('[name=paramEventEndYmd]').val() == '') {
				alert('이벤트 기간을 입력해주세요.');
				$(frm.paramEventEndYmd).focus();
				return false;
			}

			if(startYmd.getTime() > endYmd.getTime()) {
				alert('이벤트 종료일이 시작일보다 앞설 수 없습니다.');
				$(frm.paramEventEndYmd).focus();
				return false;
			}
		}

		if(eventTypeCode == '10002') {
			var atendDayCheck = true;
			var atendDayCountArr = [];
			$('#basicBenefitArea').find('[name*=atendDayCount]').each(function(i, v){
				if(!$.isNumeric($(v).val())) {
					atendDayCheck = false;
					return false;
				}

				atendDayCountArr.push($(v).val());
			});

			if(!atendDayCheck) {
				alert('출석일수는 숫자만 입력이 가능합니다.');
				return false;
			}

			var resultsAtendDay = '';
			var compareAtendDayCountArr = atendDayCountArr.slice().sort();
			for (var i = 0; i < atendDayCountArr.length - 1; i++) {
			    if (compareAtendDayCountArr[i + 1] == compareAtendDayCountArr[i]) {
			    	resultsAtendDay = compareAtendDayCountArr[i];
			    	break;
			    }
			}

			//atendDayCountArr = atendDayCountArr.filter((item, index) => atendDayCountArr.indexOf(item) !== index); ie에서 안먹힘
			if(resultsAtendDay != '' && resultsAtendDay != undefined) {
				alert('출석일수 ' + resultsAtendDay + '일이 중복되었습니다');
				return false;
			}
		}

		return true;
	}

	$(function() {
		_eventDetail.init();
	});
})();