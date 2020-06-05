(function() {

	var _couponDetail = abc.object.createNestedObject(window,"abc.biz.promotion.coupon.detail");

	_couponDetail.init = function(){
		_couponDetail.event();
		_couponDetail.fieldDisabled();
		abc.biz.display.common.setDpCategoryEvent();

		if($('[name=memberCouponCount]').val() > 0 || $('#save-coupon').length == 0){
			_couponDetail.setDisabled();
		}
		
		_couponDetail.beforeCpnApplyType;
		_couponDetail.cpnApplyTypeStatus = false;
	}

	/**
	 * 버튼 이벤트 추가
	 */
	_couponDetail.event = function(){
		$('[name=frm] input').on('change', function() {
			if($('[name=cpnTypeCode]').val() == '') {
				alert('쿠폰 유형을 선택해주세요.');
			}
		});

		//저장
		$('#save-coupon').on('click', function(e){
			console.log('save coupon');

			if(_couponDetail.valid()){
				var url = '/promotion/coupon/save';
				if($('#cpnNo').val() != null && $('#cpnNo').val() != ''){
					url = '/promotion/coupon/modify';
				}

				var cpnTypeCode = $('[name=cpnTypeCode]').val();
				if(cpnTypeCode == '10000' || cpnTypeCode == '10006' || cpnTypeCode == '10001') {
					var dscntValue = $('[name=dscntValue]').val();
					var maxDscntLimitAmt = $('[name=maxDscntLimitAmt]').val();
					if($('[name=dscntType]:checked').val() == 'A') {
						dscntValue = Math.floor(dscntValue);					// 소수점 버림
						dscntValue = (Math.ceil(dscntValue / 100) * 100)	// 10단위 올림처리
						$('[name=dscntValue]').val(dscntValue);
					}

					if($('[name=maxDscntLimitYn]:checked').val() == 'Y') {
						maxDscntLimitAmt = Math.floor(maxDscntLimitAmt);					// 소수점 버림
						maxDscntLimitAmt = (Math.ceil(maxDscntLimitAmt / 100) * 100)	// 10단위 올림처리
						$('[name=maxDscntLimitAmt]').val(maxDscntLimitAmt);
					}
				}

				//요일 체크
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

				//채널 체크
				$('.inputCheckSiteNo').remove();
				$('.checkChannel').each(function(i, v){
					if($(this).is(":checked")) {
						$('#checkChannelArea').append('<input type="hidden" name="prCouponApplyChannelArr.siteNo" class="inputCheckSiteNo" value="' + $(this).data('siteNo') + '" />');
					}
				});

				var form = $.form(document.forms.frm);
				var vendorListArr = vendorlist.ExportData({"Type":"json"}).data;

				var productListArr = '';
				var product1listArr = product1list.ExportData({"Type":"json"}).data;
				var product2listArr = product2list.ExportData({"Type":"json"}).data;
				if(product1listArr.length != 0) productListArr = product1listArr.concat(product2listArr);
				else if(product2listArr.length != 0) productListArr = product2listArr.concat(product1listArr);

				form.submit({
				    'type': 'POST',
				    'url': url,
				    'data' : $.paramObject({prCouponVendorShareRateArr : vendorListArr, prCouponApplyProductArr : productListArr}),
				    'success': function(d) {
				    	alert('저장 되었습니다.');
			    		location.href = '/promotion/coupon';
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
//				    	location.reload();
				    }
				});
			}

		});

		$('[name=validTermGbnType]').on('change', function(e) {
			if($(this).val() == 'T') {
				$('.validDate').prop('disabled', false);
				$('.afterValidDate').prop('disabled', true);
				$('[name=useLimitDayCount]').val('');
			} else {
				$('.validDate').prop('disabled', true);
				$('.afterValidDate').prop('disabled', false);
				$('[name=paramValidStartYmd]').val('');
				$('[name=paramValidEndYmd]').val('');
				$('[name=paramValidStartH]').val('00');
				$('[name=paramValidEndH]').val('00');
				$('[name=paramValidStartM]').val('00');
				$('[name=paramValidEndM]').val('00');
			}
		});

		$('[name=validTermAlertYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.validTermAlert').prop('disabled', false);
			} else {
				$('.validTermAlert').prop('disabled', true);
			}
		});

		$('[name=relayCpnUseYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('#couponPopup').removeClass('disabled');
			} else {
				$('#relayCpnArea').empty();
				$('#couponPopup').addClass('disabled');
			}
		});

		$('[name=storeApplyType]').on('click', function(e) {
			if($(this).val() == 'A') $('#storePopup').addClass('disabled');
			else $('#storePopup').removeClass('disabled');
		});

		$('#couponPopup').on('click', function(e) {
			var params = {};
			params.callback = 'abc.biz.promotion.coupon.detail.appendCoupon';
			params.multiple = false;
			_couponDetail.couponPopup(params);
		});

		/*$('#searchBrand').on('click', function(e) {
			window.abc.brandSearchPopup(true, "abc.biz.promotion.coupon.detail.appendBrand");
		});*/

		$('body').on('click', '.btn-item-del', function(e) {
			$(this).closest('li').remove();
		});

		$('[name=dwldPsbltYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.checkDay').prop('disabled', false);
				$('.checkAllDay').prop('disabled', false);
				$('.selectDwldTime').prop('disabled', false);
				$('.checkAllDay').prop('checked', true);
				$('.checkDay').prop('checked', true);
			} else {
				$('.checkDay').prop('disabled', true);
				$('.checkAllDay').prop('disabled', true);
				$('.selectDwldTime').prop('disabled', true);
				$('.checkAllDay').prop('checked', false);
				$('.checkDay').prop('checked', false);
			}
		});

		$('[name=cpnCrtType]').on('change', function(e) {
			if($(this).val() == 'O') {
				$('[name=totalIssueLimitYn]').prop('disabled', false);
				$('[name=per1psnIssueLimitYn]').prop('disabled', false);

				if($('[name=totalIssueLimitYn][value=Y]').is(':checked')) {
					$('[name=totalIssueLimitCount]').prop('disabled', false);
					$('[name=per1psnMaxIssueCount]').prop('disabled', false);
				}
				$('.cpnCrt').prop('disabled', true);
			} else {
				$('[name=totalIssueLimitYn][value=N]').prop('checked', true);
				$('[name=per1psnIssueLimitYn][value=N]').prop('checked', true);
				$('[name=totalIssueLimitYn]').prop('disabled', true);
				$('[name=per1psnIssueLimitYn]').prop('disabled', true);
				$('[name=totalIssueLimitCount]').val('');
				$('[name=totalIssueLimitCount]').prop('disabled', true);
				$('[name=per1psnMaxIssueCount]').val('');
				$('[name=per1psnMaxIssueCount]').prop('disabled', true);

				if($('#couponPaperNumberDownload').length == 0) {
					$('.cpnCrt').prop('disabled', false);
				}
			}
		});

		$('[name=totalIssueLimitYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.totalIssueLimit').prop('disabled', false);
			} else {
				$('.totalIssueLimit').prop('disabled', true);
			}
		});

		$('[name=per1psnIssueLimitYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.per1psnMaxIssue').prop('disabled', false);
			} else {
				$('.per1psnMaxIssue').prop('disabled', true);
			}
		});

		$('[name=maxDscntLimitYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.maxDscntLimit').prop('disabled', false);
			} else {
				$('[name=maxDscntLimitAmt]').val('0');
				$('.maxDscntLimit').prop('disabled', true);
			}
		});

		$('[name=maxCpnApplyYn]').on('change', function(e) {
			if($(this).val() == 'Y') {
				$('.maxCpnApply').prop('disabled', false);
			} else {
				$('[name=maxCpnApplyQty]').val('0');
				$('.maxCpnApply').prop('disabled', true);
			}
		});

		$('#chkDownload01').on('click', function(e) {
			if($(this).is(':checked')) {
				$('.checkDay').prop('checked', true);
			} else {
				$('.checkDay').prop('checked', false);
			}
		});

		$('.checkDay').on('click', function(e) {
			if($('.checkDay').length == $('.checkDay:checked').length) {
				$('.checkAllDay').prop('checked', true);
			} else {
				$('.checkAllDay').prop('checked', false);
			}
		});
		
		$('[name=cpnApplyType]').on('click', function(e) {
			var after = $(this).val();
			var status = _couponDetail.changeCpnApplyType(after);
			if(status){
				alert("대상 상품영역 삭제후 변경 가능합니다.");
				return false;
			}
		});
		
		$('[name=cpnApplyType]').on('change', function(e) {
			var cpnApplyType = $(this).val();
			$('.couponApplyArea').hide();
			product1list.RemoveAll();
			product2list.RemoveAll();
			vendorlist.RemoveAll();
			//$('#categoryArea').empty();
			//$('#brandArea').empty();
			//$('.itemExceptArea').show();

			/*if(cpnApplyType == 'P') {
				$('.couponApplyItemArea').show();
				product2list.RemoveAll();
				$('.itemExceptArea').hide();
			} else if(cpnApplyType == 'C') {
				$('.couponApplyCategoryArea').show();
			} else {
				$('.couponApplyBrandArea').show();
			}*/

			if(cpnApplyType == 'P') {
				$('.couponApplyItemArea').show();
				$('.couponApplyVendorArea').show();
			} else if(cpnApplyType == 'C') {
				$('.couponApplyItemExceptArea').show();
			} else {
				$('.couponApplyVendorArea').show();
				$('.couponApplyVendorChild').show();
				$('.couponApplyItemExceptArea').show();
			}
		});

		$('[name=cpnTypeCode]').on('change', function(e) {
			var cpnTypeCode = $(this).val();
			$('.cpnTypeArea').hide();

			$('[name=useYn][value=N]').prop('checked', true);
			$('[name=dispYn][value=N]').prop('checked', true);
			$('[name=cpnZoneDispYn][value=N]').prop('checked', true);

			$('[name=validTermGbnType][value=T]').prop('checked', true);
			$('[name=paramValidStartYmd]').val('');
			$('[name=paramValidEndYmd]').val('');
			$('[name=paramValidStartH]').val('00');
			$('[name=paramValidStartM]').val('00');
			$('.validDate').prop('disabled', false);
			$('.afterValidDate').prop('disabled', true);
			$('[name=useLimitDayCount]').val('');

			$('[name=validTermAlertYn][value=N]').prop('checked', true);
			$('[name=alertSendDayCount]').prop('disabled', true);
			$('[name=alertSendDayCount]').val('');

			$('[name=relayCpnYn][value=N]').prop('checked', true);
			$('#couponPopup').addClass('disabled');
			$('#relayCpnArea').empty();

			$('[name=dwldPsbltYn][value=N]').trigger('click');

			$('[name=deviceCodes]').prop('checked', true);
			$('[name*=chnnlNo]').prop('checked', true);

			$('[name=maxDscntLimitYn]').eq(0).prop('checked', true);

			$('[name=dscntType][value=R]').prop('checked', true)
			$('[name=dscntValue]').val(0);
			$('[name=maxDscntLimitAmt]').val(0);

			$('[name=cpnCrtType]').eq(0).prop('checked', true);
			$('[name=paperCrtCount]').val('');
			$('[name=paperCrtCount]').prop('disabled', true);

			$('[name=totalIssueLimitYn]').eq(0).prop('checked', true);
			$('[name=totalIssueLimitCount]').val('');
			$('[name=totalIssueLimitCount]').prop('disabled', true);

			$('[name=per1psnIssueLimitYn]').eq(0).prop('checked', true);
			$('[name=per1psnMaxIssueCount]').val('');
			$('[name=per1psnMaxIssueCount]').prop('disabled', true);

			$('[name=affltsCode]').val('');
			$('.affltsArea').show();
			if(cpnTypeCode == '10000' || cpnTypeCode == '10006') {
				$('.couponDscntArea').show();
			} else if(cpnTypeCode == '10001') {
				$('.affltsArea').hide();
				$('.couponAffltsArea').show();
				$('.couponDscntArea').show();
			}

			$('[name=minLimitSellAmt]').prop('disabled', false);
			$('[name=limitDscntRate]').prop('disabled', false);
			if(cpnTypeCode == '10002' || cpnTypeCode == '10004' || cpnTypeCode == '10005') {
				$('[name=minLimitSellAmt]').prop('disabled', true);
				$('[name=limitDscntRate]').prop('disabled', true);
			} else if(cpnTypeCode == '10003') {
				$('[name=limitDscntRate]').prop('disabled', true);
			}

			$('[name=usePlaceGbnType][value=O]').trigger('click');
			if(cpnTypeCode == '10003' || cpnTypeCode == '10004' || cpnTypeCode == '10005') {
				$('[name=usePlaceGbnType]').not('[value=O]').prop('disabled', true);
			} else {
				$('[name=usePlaceGbnType]').not('[value=O]').prop('disabled', false);
			}

		});

		$('[name=usePlaceGbnType]').on('change', function(e) {
			var usePlaceGbnType = $(this).val();
			/*$('.onOffArea').show();
			if(usePlaceGbnType == 'F') {
				$('[name=deviceCodes]').prop('checked', false);
				$('[name*=chnnlNo]').prop('checked', false);
				$('[name=storeApplyType]').eq(0).prop('checked', true);
				$('[name=storePosDispYn]').eq(0).prop('checked', true);
				$('#storeArea').empty();
				$('.onOffArea').hide();
			}*/

			$('[name=storeApplyType][value=A]').prop('checked', true);
			$('#storePopup').addClass('disabled');
			$('#storeArea').empty();
			if(usePlaceGbnType == 'O') {
				$('[name=deviceCodes]').prop('checked', true);
				$('[name*=chnnlNo]').prop('checked', true);
				$('[name=deviceCodes]').prop('disabled', false);
				$('[name*=chnnlNo]').prop('disabled', false);
				$('[name=storeApplyType]').prop('disabled', true);
				$('[name=storePosDispYn]').prop('disabled', true);
			} else if(usePlaceGbnType == 'F') {
				$('[name=deviceCodes]').prop('checked', false);
				$('[name*=chnnlNo]').prop('checked', false);
				$('[name=deviceCodes]').prop('disabled', true);
				$('[name*=chnnlNo]').prop('disabled', true);
				$('[name=storeApplyType]').prop('disabled', false);
				$('[name=storePosDispYn]').prop('disabled', false);
			} else {
				$('[name=deviceCodes]').prop('checked', true);
				$('[name*=chnnlNo]').prop('checked', true);
				$('[name=deviceCodes]').prop('disabled', false);
				$('[name*=chnnlNo]').prop('disabled', false);
				$('[name=storeApplyType]').prop('disabled', false);
				$('[name=storePosDispYn]').prop('disabled', false);
			}
		});

		$('.checkChannel').on('click', function(e) {
			$('.chnnl-no').empty();
			$('.checkChannel:checked').each(function(i, v) {
				if(i == 0) $('.chnnl-no').append('<option value="">채널선택</option>' );
				$('.chnnl-no').append('<option value="' + $(v).val() + '">' + $('label[for="' + $(this).attr('id') + '"]').text() + '</option>' );
			})
			$('.chnnl-no').val('').trigger('change');
		});

		$('#storePopup').on('click', function(e) {
			abc.storeSearchPopup('abc.biz.promotion.coupon.detail.appendStore', true);
		});

		$('#couponIssueListPopup').on('click', function(e) {
			_couponDetail.memberCouponPopup($(this).data('paperYn'));
		});

		/*$('#addCategory').on('click', function(e) {
			var selectText = new Array();
			var duplCheck = false;
			var ctgrNo = '';
			$('.dp-category-select').each(function(i, v) {
				if($(v).val() != '' && !$(this).hasClass('chnnl-no')) {
					selectText.push($(this).find('option:selected').text());
					ctgrNo = $(v).val();
				}
			});

			$('[name*=ctgrNo]').each(function(i, v) {
				if($(v).val() == ctgrNo)  {
					duplCheck = true;
					return;
				}
			});

			if(duplCheck) {
				alert('카테고리 중복입니다.');
				return;
			}

			$('#categoryArea').append($('#LiTemplate').html());
			$('#categoryArea').find('.subject').last().text('[' + $('.chnnl-no option:selected').text() + '] ' + selectText.join(' > '));
			$('#categoryArea').find('li').last().append('<input type="hidden" name="prCouponApplyCategoryArr.ctgrNo" value="' + ctgrNo + '" />');
			$('#categoryArea').find('li').last().append('<input type="hidden" name="prCouponApplyCategoryArr.chnnlNo" value="' + $('.chnnl-no').val() + '" />');
		});*/

		/** 초기화 */
		$('#clear-form').on('click', function() {
//			if($('[name=memberCouponCount]').val() > 0){
//				$('[name=cpnName]').val('');
//				$('[name=cpnDescText]').val('');
//				$('[name=cpnUseGbnType][value=E]').prop('checked', true);
//				$('[name=useYn][value=Y]').prop('checked', true);
//				$('[name=dispYn][value=Y]').prop('checked', true);
//				$('[name=validTermAlertYn][value=Y]').trigger('click');
//			} else {
//				abc.biz.display.common.initFormData('frm', false);
//				vendorlist.RemoveAll();
//				product1list.RemoveAll();
//				product2list.RemoveAll();
//				//$('#categoryArea').empty();
//				//$('#brandArea').empty();
//				$('#storeArea').empty();
//				$('#relayCpnArea').empty();
//				$('.onOffArea').show();
//				//$('.couponApplyArea').hide();
//				$('.couponApplyItemArea').show();
//				$('.cpnTypeArea').hide();
//			}
			if(location.search === "") {
				// 등록 페이지
				$("select[name='cpnTypeCode']").val("").trigger("change");
				vendorlist.RemoveAll();
				product1list.RemoveAll();
				product2list.RemoveAll();
			} else {
				// 수정 페이지
				location.reload();
			}
		});

		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminDetailPop").on('click', function(){
			abc.adminDetailPopup($(this).data("adminNo"));
		});

		$('[name=storeApplyType]').on('change', function() {
			if($(this).val() == 'A') $('#storeArea').empty();
		});

		$('#couponPaperNumberDownload').on('click', function() {
			$('[name=frm]').attr('action', '/promotion/coupon/paper/number/excel/download');
			document.getElementById('frm').submit();
		});

		$('[name=useLimitDayCount]').on("keyup change", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=alertSendDayCount]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=paperCrtCount]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=totalIssueLimitCount]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=per1psnMaxIssueCount]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=minLimitSellAmt]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=limitDscntRate]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=maxDscntLimitAmt]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=dscntValue]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('[name=maxCpnApplyQty]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

		$('#shareRateVal').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
	}

	_couponDetail.fieldDisabled = function(){
		if($('#radioValidity01').is(':checked')) {
			$('.validDate').prop('disabled', false);
			$('.afterValidDate').prop('disabled', true);
		} else {
			$('.validDate').prop('disabled', true);
			$('.afterValidDate').prop('disabled', false);
		}

		if(!$('#radioDownload01').is(':checked')) {
			$('.checkDay').prop('disabled', true);
			$('.checkAllDay').prop('disabled', true);
			$('.selectDwldTime').prop('disabled', true);
		}

		if(!$('#radioProduce01').is(':checked')) {
			if($('[name=paperCrtCount]').val() == '' || $('[name=paperCrtCount]').val() == '0') {
				$('.cpnCrt').prop('disabled', false);
			}
		}

		if(!$('#radioCountLimit02').is(':checked')) {
			$('.totalIssueLimit').prop('disabled', false);
		}

		if(!$('#radioIDLimit02').is(':checked')) {
			$('.per1psnMaxIssue').prop('disabled', false);
		}

		if($('[name=validTermAlertYn][value=N]').is(':checked')) {
			$('[name=alertSendDayCount]').prop('disabled', true);
		}

		var cpnTypeCode = $('[name=cpnTypeCode]').val();
		$('.cpnTypeArea').hide();
		if(cpnTypeCode == '10000' || cpnTypeCode == '10006') {
			$('.couponDscntArea').show();
		} else if(cpnTypeCode == '10001') {
			$('.cpnTypeArea').show();
			$('.affltsArea').hide();
		}

		$('[name=minLimitSellAmt]').prop('disabled', false);
		$('[name=limitDscntRate]').prop('disabled', false);
		if(cpnTypeCode == '10002' || cpnTypeCode == '10004' || cpnTypeCode == '10005') {
			$('[name=minLimitSellAmt]').prop('disabled', true);
			$('[name=limitDscntRate]').prop('disabled', true);
		} else if(cpnTypeCode == '10003') {
			$('[name=limitDscntRate]').prop('disabled', true);
		}

		if(cpnTypeCode == '10003' || cpnTypeCode == '10004' || cpnTypeCode == '10005') {
			$('[name=usePlaceGbnType][value=O]').trigger('click');
			$('[name=usePlaceGbnType]').not('[value=O]').prop('disabled', true);
		} else {
			$('[name=usePlaceGbnType]').not('[value=O]').prop('disabled', false);
		}

		var cpnApplyType = $('[name=cpnApplyType]:checked').val();
		$('.couponApplyArea').hide();
		if(cpnApplyType == 'P') {
			$('.couponApplyItemArea').show();
			$('.couponApplyVendorArea').show();
			//$('.itemExceptArea').hide();
		} else if(cpnApplyType == 'C') {
			$('.couponApplyItemExceptArea').show();
			//$('.couponApplyCategoryArea').show();
		} else {
			$('.couponApplyVendorArea').show();
			$('.couponApplyVendorChild').show();
			$('.couponApplyItemExceptArea').show();
			//$('.couponApplyBrandArea').show();
		}

		var usePlaceGbnType = $('[name=usePlaceGbnType]:checked').val();
		if(usePlaceGbnType == 'O') {
			$('[name=storeApplyType][value=A]').prop('checked', true);
			$('#storePopup').addClass('disabled');
			$('#storeArea').empty();
			$('[name=deviceCodes]').prop('disabled', false);
			$('[name*=chnnlNo]').prop('disabled', false);
			$('[name=storeApplyType]').prop('disabled', true);
			$('[name=storePosDispYn]').prop('disabled', true);
		} else if(usePlaceGbnType == 'F') {
			$('[name=deviceCodes]').prop('disabled', true);
			$('[name*=chnnlNo]').prop('disabled', true);
			$('[name=storeApplyType]').prop('disabled', false);
			$('[name=storePosDispYn]').prop('disabled', false);
		}

		var storeApplyType = $('[name=storeApplyType]:checked').val();
		if(storeApplyType == 'A') $('#storePopup').addClass('disabled');
		else $('#storePopup').removeClass('disabled');

		$('.checkChannel:checked').each(function(i, v) {
			if(i == 0) $('.chnnl-no').append('<option value="">채널선택</option>' );
			$('.chnnl-no').append('<option value="' + $(v).val() + '">' + $('label[for="' + $(this).attr('id') + '"]').text() + '</option>' );
		})

		if($('[name=dwldPsbltYn]:checked').val() == 'Y') {
			$('.checkDay').prop('disabled', false);
			$('.checkAllDay').prop('disabled', false);
			$('.selectDwldTime').prop('disabled', false);
		} else {
			$('.checkDay').prop('disabled', true);
			$('.checkAllDay').prop('disabled', true);
			$('.selectDwldTime').prop('disabled', true);
			$('.checkDay').prop('checked', false);
		}

		if($('.checkDay').length == $('.checkDay:checked').length) {
			$('.checkAllDay').prop('checked', true);
		} else {
			$('.checkAllDay').prop('checked', false);
		}


		if($('[name=relayCpnUseYn]:checked').val() == 'Y') {
			$('#couponPopup').removeClass('disabled');
		} else {
			$('#couponPopup').addClass('disabled');
		}

		if($('[name=maxDscntLimitYn]:checked').val() == 'N') {
			$('[name=maxDscntLimitAmt]').prop('disabled', true);
		}

		if($('[name=maxCpnApplyYn]:checked').val() == 'N') {
			$('[name=maxCpnApplyQty]').prop('disabled', true);
		}

		if($('#couponPaperNumberDownload').length > 0) {
			$('[name=totalIssueLimitYn]').prop('disabled', true);
			$('[name=per1psnIssueLimitYn]').prop('disabled', true);
			$('[name=cpnCrtType]').prop('disabled', true);
		}
	}
	
	//라디오 버튼 변경시 이벤트
	_couponDetail.changeCpnApplyType = function(after){
		_couponDetail.cpnApplyTypeStatus = false;
		if(_couponDetail.beforeCpnApplyType != after){
			if(_couponDetail.beforeCpnApplyType == 'P') {
				//1sheet
				if(product1list.GetTotalRows() > 0 || product1list.SearchRows() > 0){
					_couponDetail.cpnApplyTypeStatus = true;
				}
			} else if(_couponDetail.beforeCpnApplyType == 'C') {
				//2sheet
				if(product2list.GetTotalRows() > 0 || product2list.SearchRows() > 0){
					_couponDetail.cpnApplyTypeStatus = true;
				}
			} else {
				//2sheet
				if(product2list.GetTotalRows() > 0 || product2list.SearchRows() > 0){
					_couponDetail.cpnApplyTypeStatus = true;
				}
			}
			if(!_couponDetail.cpnApplyTypeStatus){
				_couponDetail.beforeCpnApplyType = after;
				_couponDetail.cpnApplyTypeStatus = false;
			}
		}
		return _couponDetail.cpnApplyTypeStatus;
	}

	/**
	 * 유효성 체크
	 */
	_couponDetail.valid = function(){

		var frm = document.forms.frm;

		if(frm.cpnName.value == '') {
			alert('쿠폰명을 입력해주세요.');
			$(frm.cpnName).focus();
			return false;
		}

		if(frm.useLimitDayCount.value > 365) {
			alert('최대 12개월(365일)까지 설정 가능합니다.');
			$(frm.useLimitDayCount).focus();
			return false;
		}

		if($('[name=relayCpnUseYn]:checked').val() == 'Y') {
			if($('[name=relayCpnNo]').length == 0 || $('[name=relayCpnNo]').val() == '') {
				alert('릴레이쿠폰을 선택해주세요');
				$(frm.relayCpnUseYn).focus();
				return false;
			}
		}

		if($('[name=dwldPsbltYn]:checked').val() == 'Y') {
			var paramStartDay = $('[name=paramDayStartH]').val() + $('[name=paramDayStartM]').val();
			var paramEndDay = $('[name=paramDayEndH]').val() + $('[name=paramDayEndM]').val();
			if(parseInt(paramStartDay) >= parseInt(paramEndDay)) {
				alert('다운로드 종료시간이 시작시간을 앞설 수 없습니다.');
				$(frm.paramDayStartH).focus();
				return false;
			}

			if($('.checkDay:checked').length == 0) {
				alert('요일을 체크해주세요.');
				$(frm.dwldPsbltYn).focus();
				return false;
			}
		}

		var issueStartYmd = new Date($('[name=paramIssueStartYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramIssueStartH]').val() + ':' + $('[name=paramIssueStartM]').val() + ':00');
		var issueEndYmd = new Date($('[name=paramIssueEndYmd]').val().replaceAll('\\.', '/') + ' '
				+ $('[name=paramIssueEndH]').val() + ':' + $('[name=paramIssueEndM]').val() + ':00');

		if(issueStartYmd.getTime() >= issueEndYmd.getTime()) {
			alert('발급시작일보다 이전날짜를 선택할 수 없습니다.');
			$(frm.paramIssueStartH).focus();
			return false;
		}

		if($('[name=validTermGbnType]:checked').val() == 'T') {
			var validStartYmd = new Date($('[name=paramValidStartYmd]').val().replaceAll('\\.', '/') + ' '
					+ $('[name=paramValidStartH]').val() + ':' + $('[name=paramValidStartM]').val() + ':00');
			var validEndYmd = new Date($('[name=paramValidEndYmd]').val().replaceAll('\\.', '/') + ' '
					+ $('[name=paramValidEndH]').val() + ':' + $('[name=paramValidEndM]').val() + ':00');

			if(validStartYmd.getTime() >= validEndYmd.getTime()) {
				alert('유효시작일보다 이전날짜를 선택할 수 없습니다.');
				$(frm.paramValidStartH).focus();
				return false;
			}
		}

		if($('[name=validTermGbnType]:checked').val() == 'D' && $('[name=validTermAlertYn]:checked').val() == 'Y') {
			if(Number($('[name=useLimitDayCount]').val()) <= Number($('[name=alertSendDayCount]').val())) {
				alert('유효기간 종료 안내일 수가 유효기간 발급 후 사용가능일 수보다 같거나 클 수 없습니다.');
				$('[name=alertSendDayCount]').focus();
				return false;
			}
		}

		if($('#radioValidity02').is(':checked')) {
			if(!$.isNumeric(frm.useLimitDayCount.value)){
				alert('발급후 날짜는 숫자만 입력이 가능합니다.');
				$(frm.useLimitDayCount).focus();
				return false;
			}
		}

		if($('#radioProduce02').is(':checked')) {
			if(!$.isNumeric(frm.paperCrtCount.value)){
				alert('지류생성은 숫자만 입력이 가능합니다.');
				$(frm.paperCrtCount).focus();
				return false;
			}

			if(frm.paperCrtCount.value == 0) {
				alert('지류생성은 1 이상 입력해주세요.');
				$(frm.paperCrtCount).focus();
				return false;
			}
		}

		if($('[name=deviceCodes]:checked').length == 0 && !$('[name=usePlaceGbnType][value=F]').is(':checked')) {
			alert('디바이스는 필수입니다.');
			$('[name=deviceCodes]').eq(0).focus();
			return false;
		}

		if($('[name*=chnnlNo]:checked').length == 0 && !$('[name=usePlaceGbnType][value=F]').is(':checked')) {
			alert('채널은 필수입니다.');
			$('[name*=chnnlNo]').eq(0).focus()
			return false;
		}

		if($('#radioCountLimit03').is(':checked')) {
			if(!$.isNumeric(frm.totalIssueLimitCount.value)){
				alert('전체 발급횟수 제한은 숫자만 입력이 가능합니다.');
				$(frm.totalIssueLimitCount).focus();
				return false;
			}
		}

		if($('#radioIDLimit03').is(':checked')) {
			if(!$.isNumeric(frm.per1psnMaxIssueCount.value)){
				alert('ID당 발급횟수 제한은 숫자만 입력이 가능합니다.');
				$(frm.per1psnMaxIssueCount).focus();
				return false;
			}
		}

		if($('[name=cpnApplyType]:checked').val() != 'C') {
			var validVendorlist = vendorlist.ExportData({"Type":"json"}).data;
			if(validVendorlist.length > 0) {
				var validVendor = true;
				$.each(validVendorlist, function(i, v){
					if(!$.isNumeric(v.shareRate)) {
						validVendor = false;
						return false;
					}
				});
				if(!validVendor) {
					alert('입점사 분담률은 숫자만 입력이 가능합니다.');
					return false;
				}
			}
		}

		var cpnTypeCode = $('[name=cpnTypeCode]').val();
		if(cpnTypeCode == '10000' || cpnTypeCode == '10006' || cpnTypeCode == '10001') {
			if($('#radioCouponCountLimit01').is(':checked')) {
				if(!$.isNumeric(frm.maxCpnApplyQty.value)){
					alert('상품당 쿠폰적용 수량 제한은 숫자만 입력이 가능합니다.');
					$(frm.maxCpnApplyQty).focus();
					return false;
				}

				if(frm.maxCpnApplyQty.value == 0) {
					alert('상품당 쿠폰적용 수량 제한은 1 이상 입력해주세요.');
					$(frm.maxCpnApplyQty).focus();
					return false;
				}
			}


			if($('#radioSaleLimit01').is(':checked')) {
				if(!$.isNumeric(frm.maxDscntLimitAmt.value)){
					alert('최대 할인 금액은 숫자만 입력이 가능합니다.');
					$(frm.maxDscntLimitAmt).focus();
					return false;
				}

				if(frm.maxDscntLimitAmt.value == 0) {
					alert('최대 할인 금액은 1 이상 입력해주세요.');
					$(frm.maxDscntLimitAmt).focus();
					return false;
				}

				if(frm.maxDscntLimitAmt.value < 100) {
					alert('최대 할인 금액은 100 미만 일 수 없습니다.');
					$(frm.maxDscntLimitAmt).focus();
					return false;
				}
			}

			if(!$.isNumeric(frm.dscntValue.value)) {
				alert('할인율(액)은 숫자만 입력이 가능합니다.');
				$(frm.dscntValue).focus();
				return false;
			}

			if(frm.dscntValue.value == 0) {
				alert('할인율(액)은 1 이상 입력해주세요.');
				$(frm.dscntValue).focus();
				return false;
			}

			if($('[name=dscntType]:checked').val() == 'R') {
				if(frm.dscntValue.value > 100) {
					alert('할인율은 100%를 넘을 수 없습니다.');
					$(frm.dscntValue).focus();
					return false;
				}

				// 2020.02.07 : 쿠폰할인유형 == 정률할인 일때
				var limitDscntRate = Number($("input[name='limitDscntRate']").val()); 	// 할인제외 감가율
				var dscntValue     = Number($("input[name='dscntValue']").val());		// 할인률
				if( limitDscntRate < dscntValue ) {
					alert('할인율은 할인제외 감가율을 넘을 수 없습니다.');
					$(frm.dscntValue).focus();
					return false;
				}
			}

			if($('[name=dscntType]:checked').val() == 'A') {
				if(frm.dscntValue.value < 100) {
					alert('할인액은 100 미만 일 수 없습니다.');
					$(frm.dscntValue).focus();
					return false;
				}
			}

			if($('[name=usePlaceGbnType]:checked').val() == 'F' && $('[name=dscntType]:checked').val() == 'R') {
				if(frm.dscntValue.value > 35) {
					alert('오프라인 매장 쿠폰 할인율은 35%를 넘을 수 없습니다.');
					$(frm.dscntValue).focus();
					return false;
				}
			}
		}

		if(cpnTypeCode != '10002' && cpnTypeCode != '10004' && cpnTypeCode != '10005') {
			if(cpnTypeCode != '10003') {
				if(!$.isNumeric(frm.limitDscntRate.value)) {
					alert('할인제외 감가율은 숫자만 입력이 가능합니다.');
					$(frm.limitDscntRate).focus();
					return false;
				}

				if(frm.limitDscntRate.value > 100) {
					alert('할인제외 감가율은 100%를 넘을 수 없습니다.');
					$(frm.limitDscntRate).focus();
					return false;
				}
			}

			if(!$.isNumeric(frm.minLimitSellAmt.value)){
				alert('상품금액 제한은 숫자만 입력이 가능합니다.');
				$(frm.minLimitSellAmt).focus();
				return false;
			}
		}

		return true;
	}

	_couponDetail.couponPopup = function(params){

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

	_couponDetail.appendCoupon = function(d) {
		$('#relayCpnArea').empty();
		$('#relayCpnArea').append($('#LiTemplate').html());
		$('#relayCpnArea').find('.subject').last().text(d[0].cpnName);
		$('#relayCpnArea').find('li').last().append('<input type="hidden" name="relayCpnNo" value="' + d[0].cpnNo + '" />');
	}

	_couponDetail.appendStore = function(d) {
		$('#storeArea').empty();
		$.each(d, function(i, v) {
			$('#storeArea').append($('#LiTemplate').html());
			$('#storeArea').find('.subject').last().text(v.storeName);
			$('#storeArea').find('li').last().append('<input type="hidden" name="prCouponApplyStoreArr.storeNo" value="' + v.storeNo + '" />');
		});
	}

	_couponDetail.appendBrand = function(result) {
		$.each(result, function(i, v) {
			var duplCheck = true;
			$('[name*=brandNo]').each(function(j, x) {
				if(v.brandNo == $(x).val()) {
					duplCheck = false;
					return false;
				}
			});

			if(duplCheck) {
				$('#brandArea').append($('#LiTemplate').html());
				$('#brandArea').find('.subject').last().text(v.brandName);
				$('#brandArea').find('li').last().append('<input type="hidden" name="prCouponApplyBrandArr.brandNo" value="' + v.brandNo + '" />');
			}
		});
	}

	_couponDetail.setDisabled = function(){
		$('input').prop('disabled', true);
		$('select').prop('disabled', true);

		$('[name=cpnNo]').prop('disabled', false);
		$('[name=memberCouponCount]').prop('disabled', false);
		$('[name=cpnUseGbnType]').prop('disabled', false);
		$('[name=useYn]').prop('disabled', false);
		$('[name=dispYn]').prop('disabled', false);
		$('[name=cpnZoneDispYn]').prop('disabled', false);
		$('[name=cpnName]').prop('disabled', false);
		$('[name=cpnDescText]').prop('disabled', false);
		$('[name=validTermAlertYn]').prop('disabled', false);
		if($('[name=validTermAlertYn][value=Y]').is(':checked')) {
			$('[name=alertSendDayCount]').prop('disabled', false);
		}
		$('[name=relayCpnNo]').prop('disabled', false);
		$('#shareRateVal').prop('disabled', false);

		/*if($('[name=cpnApplyType][value=C]').is(':checked')) {
			$('.productSearchPop').off();
		} else {
			$('.productDelBtn').off();
		}*/
	}

	_couponDetail.memberCouponPopup = function(paperYn){
		var cpnNo = $('#cpnNo').val();
		var url = "/promotion/coupon/member/popup";
		var params = {};
		params.cpnNo = cpnNo;
		params.paperYn = paperYn;
		abc.open.popup({
			url 	:	url,
			method	: 	"post",
			title 	:	"쿠폰 현황",
			width 	:	'1240',
			height	:	'950',
			params	:	params
		});
	}

	_couponDetail.excelUploadProductSearch = function(sheetName, cnpApplyType) {

		var prdtNoArr = [];
		var sheet = sheetName; // 해쉬태그 상품 목록 그리드
		var list = sheetName.ExportData({"Type": "json", "Cols" : "prdtNo"}).data; // 해쉬태그 상품 목록에 추가된 상품 번호 리스트

		$.each(list, function(idx, prdt) {
			prdtNoArr.push(prdt.prdtNo);
		});

		$("input[name='prdtNoArr']").val(prdtNoArr);

		var param = { url : '/display/hashtag/product/excelupload-read-list'
			, onePageRow : 20000
			, subparam : FormQueryStringEnc(document.forms.searchFrm)
			, CPage : 1
			, sheet : cnpApplyType == "P" ? "product1list" : "product2list"
			, cpnNo : $("#cpnNo").val()
		};

		DataSearchPaging(param);

		var vndrNoArr = new Array();

		// 기존 등록 상품 중 입점 업체 정보를 조회한다.
		if (! abc.text.isNull($("#cpnNo").val())) {

			param = {
				cpnNo : $("#cpnNo").val()
			};

			$.ajax({
				type :"post",
				url : "/promotion/coupon/vendor/list/read",
				async: false,
				data: param
			})
			.done(function(data){
				$.each(data, function(i, vndrShareRate) {
					vndrNoArr.push(vndrShareRate.vndrNo)
				});
			})
			.fail(function(jqXHR, textStatus, errorThrown){
				console.log(jqXHR.responseJSON.message);
				return false;
			});
		}

		console.log("vndrNoArr ===] " + vndrNoArr);

		// 엑셀 업로드한 상품  중  입점 업체 코드를 조회한다.
		$.each(sheetName.ExportData({"Type":"json"}).data, function(i, v) {
			if(v.mmnyPrdtYn != '자사' && v.mmnyPrdtYn != 'Y') {
				var isVndrNo = false;
				$.each(vndrNoArr, function(i, vndrNo) {
					if (vndrNo == v.vndrNo) {
						isVndrNo = true;
						return false;
					}
				});
				if (! isVndrNo) {
					vndrNoArr.push(v.vndrNo);
				}
			}
		});

		console.log("vndrNoArr ===] " + vndrNoArr);

		// 쿠폰 적용 범위가 상품일 경우 대상 입점사 목록을 재조회한다.
		if($('[name=cpnApplyType]:checked').val() == 'P') {
			vendorlist.RemoveAll();
			if (vndrNoArr.length > 0) {
				abc.biz.promotion.coupon.vendor.getBasicInfoList(vndrNoArr, $("#cpnNo").val());
			}
		}
/*
		$.each(sheetName.ExportData({"Type":"json"}).data, function(i, v) {
			if(v.mmnyPrdtYn != '자사' && v.mmnyPrdtYn != 'Y') {
				var isVndrNo = false;
				$.each(vndrNoArr, function(i, vndrNo) {
					if (vndrNo == v.vndrNo) {
						isVndrNo = true;
						return false;
					}
				});
				if (! isVndrNo) {
					vndrNoArr.push(v.vndrNo);
				}
			}
		});

		console.log("vndrNoArr ===] " + vndrNoArr)

		var addVndrNoArr = new Array();

		if($('[name=cpnApplyType]:checked').val() == 'P') {
			$.each(vendorlist.ExportData({"Type":"json"}).data, function(i, vndr) {
				var isVndrNo = false;
				var newVndrNo = "";
				$.each(vndrNoArr, function(i, vndrNo) {
					newVndrNo = vndrNo;
					if (vndr.vndrNo == vndrNo) {
						isVndrNo = true;
						return false;
					}
				});
				if (! isVndrNo) {
					addVndrNoArr.push(newVndrNo);
				}
			});
			// 실
			if (addVndrNoArr.length > 0) {
				vendorlist.RemoveAll();
			}
		}

		if (addVndrNoArr.length > 0) {
			abc.biz.promotion.coupon.vendor.appendVendor(addVndrNoArr);
		}
*/
	}

	$(function() {
		_couponDetail.init();
	});
})();