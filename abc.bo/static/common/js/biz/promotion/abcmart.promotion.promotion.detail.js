(function() {

	var _promotionDetail = abc.object.createNestedObject(window,"abc.biz.promotion.promotion.detail");
	
	_promotionDetail.init = function(){
		_promotionDetail.event();
		_promotionDetail.fieldDisabled();
		abc.biz.display.common.setDpCategoryEvent();
		
		if($('[name=promoProgressStatus]').val() != 'none' && $('[name=promoProgressStatus]').val() != 'wait'){
			_promotionDetail.setDisabled();
		}
	}
	
	/**
	 * 버튼 이벤트 추가
	 */
	_promotionDetail.event = function(){
		$('[name=frm] input').on('change', function() {
			if($('[name=promoTypeCode]').val() == '') {
				alert('프로모션 유형을 선택해주세요.');
			}
		});
		
		//저장
		$('#save-promotion').on('click', function(e){
			console.log('save promotion');
			var saveCheck = true;
			var promoTypeCode = $('[name=promoTypeCode]').val();
			
			if(_promotionDetail.valid()){
				var url = '/promotion/promotion/save';
				if($('#promoNo').val() != null && $('#promoNo').val() != ''){
					url = '/promotion/promotion/modify';
				}
				
				//채널 체크
				$('.inputCheckSiteNo').remove();
				$('.checkChannel').each(function(i, v){
					if($(this).is(":checked")) {
						$('#checkChannelArea').append('<input type="hidden" name="prPromotionTargetChannelArr.siteNo" class="inputCheckSiteNo" value="' + $(this).data('siteNo') + '" />');
					} 
				});
				
				var form = $.form(document.forms.frm);
				
				//var productListArr = '';
				var product1listArr = product1list.ExportData({"Type":"json"}).data;
				//var product2listArr = product2list.ExportData({"Type":"json"}).data;
				//if(product1listArr.length != 0) productListArr = product1listArr.concat(product2listArr);
				//else if(product2listArr.length != 0) productListArr = product2listArr.concat(product1listArr);
				
				if(product1listArr.length == 0) {
					alert('상품을 등록해주세요');
					return  false;
				} else {
					$.each(product1listArr, function(i, v) {
						if(v.cmsnRate > 100) {
							saveCheck = false;
							alert('프로모션 수수료율(%) 는 100을 넘을 수 없습니다.');
							return false;
						}
					});
					
					if(!saveCheck) return false;
				}
				
				if(promoTypeCode == '10001') {
					$.each(product1listArr, function(i, v) {
						if(v.duplPromoNo != '') {
							saveCheck = false;
							alert('타 프로모션에 등록된 상품은 다족할인 프로모션으로 등록할 수 없습니다.');
							return false;
						} 
					});
					
					if(!saveCheck) return false;
					
					if(abc.biz.promotion.promotion.product.vendorMultiBuyCheck()) {
						alert('다족구매 할인 프로모션은 동일한 업체 상품만 등록이 가능합니다.');
						return false;
					}
				}
				
				if(promoTypeCode == '10002' || promoTypeCode == '10004') {
					var imdtlDscntValue = $('[name=imdtlDscntValue]').val();
					if($('[name=imdtlDscntType]:checked').val() != 'R') {
						imdtlDscntValue = Math.floor(imdtlDscntValue);					// 소수점 버림
						imdtlDscntValue = (Math.ceil(imdtlDscntValue / 100) * 100)	// 10단위 올림처리
						$('[name=imdtlDscntValue]').val(imdtlDscntValue);
						
						$.each(product1listArr, function(i, v) {
							if(imdtlDscntValue > parseInt(v.displayProductPrice.replace(',', '').replace('원', ''))) {
								saveCheck = false;
								alert('할인액 또는 균일가가 상품의 판매가 보다 클 수 없습니다.');
								$('[name=imdtlDscntValue]').focus();
								return false;
							}
						});
						
						if(!saveCheck) return false;
					}
				}
				
				if(promoTypeCode == '10004') {
					var stockDdctType = $('[name=stockDdctType]:checked').val();
					
					$.each(product1listArr, function(i, v) {
						if(v.maxEventLimitQty == '') {
							saveCheck = false;
							alert('행사수량을 입력해주세요.');
							return false;
						}
						
						if(stockDdctType == 'A') {
							var orderQty = v.avaiableStockQty;
							var orderQtyMessage = '주문가능수량';
						} else {
							var orderQty = v.stockAiQty;
							var orderQtyMessage = '온라인재고';
						}
						
						if(v.maxEventLimitQty > orderQty && $('[name=promoNo]').val() == '') {
							saveCheck = false;
							alert('행사수량은 ' + orderQtyMessage + '보다 클 수 없습니다.');
							return false;
						}
					});
					
					if(!saveCheck) return false;
				}
				
				if(promoTypeCode == '10005') {
					if($('.giftInputArea').length == 0) {
						alert('행사수량을 입력해주세요');
						return false;
					} else {
						$('.giftInputArea').each(function(i, v){
							if($(v).val() == '' ) {
								saveCheck = false;
								alert('행사수량을 입력해주세요');
								return false;
							} 
						});
						
						if(!saveCheck) return false;
					}
					
					$('.giftTr').each(function(i, v) {
						if(parseInt($(v).find('[name=giftTotalStockQty]').val()) < parseInt($(v).find('[name*=maxEventLimitQty]').val())) {
							saveCheck = false;
							alert('행사수량이 재고수량보다 클 수 없습니다.');
							return false;
						}
					});
					
					if(!saveCheck) return false;
				}
				
				//임직원 체크
				$('#empYnVal').remove();
				if($('[name=empYn]').is(':checked')) {
					$('#trGradeArea').append('<input type="hidden" id="empYnVal" name="prPromotionTargetGradeArr.memberTypeCode" value="10000" />');
				}
				
				if(saveCheck) {
					form.submit({
						'type': 'POST',
						'url': url,
						'data' : $.paramObject({prPromotionTargetProductArr : product1listArr}),
						'success': function(d) {
							alert('저장 되었습니다.');
							location.href = '/promotion/promotion';
						},
						'error': function(e){
							alert(e.message);
							console.log(e);
						}
					});
				} // end saveCheck
			} //end valid
		});
		
		$('#multiBuyAdd').on('click', function(e){
			var xhtml = "";
			xhtml =  "<li class='addMultiBuyLi'>";
			xhtml += 	"<span class='ip-text-box'>";
			xhtml += 		"<span class='text multiBuyIdx'></span>";
			xhtml += 		"<input type='text' name='prPromotionMultiBuyDiscountArr.dscntRate' class='ui-input num-unit100' title='할인율 입력'>";
			xhtml += 		"<span class='text'>% 할인</span>";
			xhtml += 	"</span>";
			xhtml += "</li>";
			$('#multiBuyArea').append(xhtml);
			$('#multiBuyArea').find('.multiBuyIdx').last().text(($('#multiBuyArea').find('li').length) + '족');
		});
		
		$('#multiBuyDel').on('click', function(e){
			if($('#multiBuyArea').find('li').length > 1) {
				$('#multiBuyArea').find('li').last().remove();
			}
		});

		$('body').on('click', '.liDelBtn', function(e) {
			$(this).closest('li').remove();
		});
		
		$('#duplCheck').on('click', function(e) {
			var insdMgmtInfoText = $('[name=insdMgmtInfoText]').val();
			if(insdMgmtInfoText == "") {
				alert('내부 관리번호를 입력해주세요.');
				return;
			}
			
			$.ajax({
	            type :'post',
	            data : {insdMgmtInfoText : insdMgmtInfoText},
	            url:'/promotion/promotion/duplCheck'
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
		
		$('#addCategory').on('click', function(e) {
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
					return false;
				}
			});
			
			if(duplCheck) {
				alert('카테고리 중복입니다.');
				return;
			}
			
			$('#categoryArea').append($('#LiTemplate').html());
			$('#categoryArea').find('.subject').last().text('[' + $('.chnnl-no option:selected').text() + '] ' + selectText.join(' > '));
			$('#categoryArea').find('li').last().append('<input type="hidden" name="prPromotionTargetCategoryArr.ctgrNo" value="' + ctgrNo + '" />');
			$('#categoryArea').find('li').last().append('<input type="hidden" name="prPromotionTargetCategoryArr.chnnlNo" value="' + $('.chnnl-no').val() + '" />');
		});
		
		//프로모션 타입별 show, hide Area
		var prev_val;
		var next_val;
		var success = false;
		$('[name=promoTypeCode]').focus(function(e) {
			 prev_val = $(this).val();
			 if(prev_val == '') success = true;
			 else success = false;
		}).change(function() {
			$(this).blur();
			next_val = $(this).val();
			if(success) {
				_promotionDetail.promoTypeEvent(next_val);
			} else {
				if(confirm('값이 초기화 됩니다 변경하시겠습니까?')) {
					_promotionDetail.promoTypeEvent(next_val);
				} else {
					 $(this).val(prev_val);
					 return false; 
				}
			}
		});
		
		/*$('[name=promoApplyType]').on('change', function(e) {
			var promoApplyType = $(this).val();
			$('.promoApplyTypeArea').hide();
			$('.itemApplyTypeArea').hide();
			$('.itemExceptArea').show();
			if(promoApplyType == 'P') {
				$('.itemApplyTypeArea').show();
				$('.itemExceptArea').hide();
				product2list.RemoveAll();
			} else if(promoApplyType == 'C') {
				$('.categoryApplyTypeArea').show();
			} else {
				$('.brandApplyTypeArea').show();
			}
		});*/
		
		$('#promoStatusPop').on('click', function(e) {
			var params = { promoNo : $('[name=promoNo]').val() };
			abc.open.popup({
				url 	:	"/promotion/promotion/status/popup",
				method	: 	"post",
				title 	:	"프로모션 현황",
				width 	:	1220,
				height	:	620,
				params	:	params
			});
		});
		
		$('#searchBrandPop').on('click', function(e) {
			window.abc.brandSearchPopup(true, "abc.biz.promotion.promotion.detail.appendBrand");
		});
		
		$('#searchGiftPop').on('click', function(e) {
			var _params = { "isMultiple" : false, "callback" : "abc.biz.promotion.promotion.detail.appendGift" }
			_promotionDetail.freeGiftPop = abc.open.popup({
				url 	:	"/product/freeGift/detail/popup",
				winname :	"registration",
				method	: 	"get",
				title 	:	"사은품 등록",
				width 	:	1020,
				height	:	820,
				params	:	_params
			});
		});
		
		//new abc.biz.display.common.processImage( { file: '#pcBannerImg', name: '#pcBannerImgName' } );
		//new abc.biz.display.common.processImage( { file: '#mobileBannerImg', name: '#mobileBannerImgName' } );
		
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
			} else {
				$('[name*=memberTypeCode]').prop('checked', false);
			}
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
			
			if($('[name*=memberTypeCode]:checked').length == $('[name*=memberTypeCode]').length) {
				$('#chkMemberGrade0').prop('checked', true);
			} else {
				$('#chkMemberGrade0').prop('checked', false);
			}
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
		});
		
		$('.checkChannel').on('click', function(e) {
			$('.chnnl-no').empty();
			$('.checkChannel:checked').each(function(i, v) {
				if(i == 0) $('.chnnl-no').append('<option value="">채널선택</option>' );
				$('.chnnl-no').append('<option value="' + $(v).val() + '">' + $('label[for="' + $(this).attr('id') + '"]').text() + '</option>' );
			})
			$('.chnnl-no').val('').trigger('change');
		});
		
		$('.giftDelBtn').on('click', function(e) {
			//2019.10.11 진행중인 프로모션 상품 관계없이 지워달라고 요청이 와서 주석처리함
			/*if($('[name=promoProgressStatus]').val() != 'none' && $('[name=promoProgressStatus]').val() != 'wait'){
				if($('#giftTbodyArea').find('[name*=giftDelCheck]').last().val() == 'Y') {
					$('#giftTbodyArea').find('tr').last().remove();
				} else {
					alert('진행중 또는 종료된 프로모션 사은품은 삭제할 수 없습니다.');
					return false;
				}
			} else {
				$('#giftTbodyArea').find('tr').last().remove();
			}*/
			
			$('#giftTbodyArea').find('tr').last().remove();
		});
		
		$('[name=insdMgmtInfoText]').keyup(function(e) { 
			$('#duplCheckVal').val('N');
			if (!(e.keyCode >=37 && e.keyCode<=40)) {
				var v = $(this).val();
				$(this).val(v.replace(/[^a-z0-9~!@\#$%^&*\()\-=+_']/gi,''));
			}
		});
		
		$('#cmsnRateVal').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$('#eventLimitQtyVal').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$('[name=imdtlDscntValue]').on("keyup change", function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$('body').on("keyup change", '[name*=dscntRate]', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$('body').on("keyup change", '.giftInputArea', function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		//작성자 수정자 클릭시 관리자 상세 호출
		$(".adminDetailPop").on('click', function(){
			abc.adminDetailPopup($(this).data("adminNo"));
		});
		
		
		$('#clear-form').on('click', function() {
			if($('[name=promoProgressStatus]').val() != 'none' && $('[name=promoProgressStatus]').val() != 'wait'){
				$('[name=promoName]').val('');
				$('[name=noteText]').val('');
			} else {
				/*$('[name=promoTypeCode]').val('10001');
				$('.addMultiBuyLi').remove();
				_promotionDetail.promoTypeEvent('10001');*/
				location.reload();
			}
		});
	}
	
	_promotionDetail.promoTypeEvent = function(promoTypeCode) {
		$('[name=deviceCodes]').prop('checked', true);
		$('.checkChannel').prop('checked', true);
		$('input:text').val('');
		$('#chkMemberGrade0').prop('checked', true);
		$('[name*=memberTypeCode]').prop('checked', true);
		$('[name=mbshpGradeCodes]').prop('checked', true);
		
		//show, hide value clear
		$('.promoTypeCodeArea').hide();
		//$('.promoApplyTypeArea').hide();
		$('.itemApplyTypeArea').show();
		$('.giftArea').hide();
		$('#eventLimitQtyArea').hide();
		
		$('[name*=dscntRate]').val('');
		$('[name=imdtlDscntType]').eq(0).prop('checked', true);
		$('[name=imdtlDscntValue]').val('0');
		//$('[name=promoApplyType]').eq(0).prop('checked', true);
		$('[name=affltsCode]').val('');
		//$('[name=stdrSellAmt]').val('0');
		$('[name*=imageFile]').val('');
		$('.img-wrap').empty();
		$('.subject').text('');
		$('.btn-file-del').hide();
		$('#giftTbodyArea').empty();
		
		product1list.RemoveAll();
		$('#productTitleArea').text('대상 상품 목록');
		//product2list.RemoveAll();
		
		$('.addMultiBuyLi').remove();
		
		$('label[for="radioDisplay01"]').text('허용');
		$('[name=cpnSmtmApplyPsbltYn]').prop('disabled', false);
		$('.cpnSmtmApplyPsbltArea').show();
		$('[name=stockDdctType]').prop('disabled', false);
		$('[name=stockDdctType][value=A]').prop('checked', true);
		if(promoTypeCode == '10000') {				//1+1증정
			//$('.stdrSellAmtArea').show();
		} else if(promoTypeCode =='10001') {		//다족구매 할인
			$('.multiBuyDiscountArea').show();
			$('label[for="radioDisplay01"]').text('허용 (정액쿠폰만 사용 가능)');
			$('[name=stockDdctType]').prop('disabled', true);
		} else if(promoTypeCode == '10002') {		//즉시할인
			$('.imdtlDscntTypeArea').show();
			$('[name=stockDdctType]').prop('disabled', true);
			//$('.promoApplyTypeTopArea').show();
		} else if(promoTypeCode == '10003') {		//제휴사 즉시 할인
			$('.promotionAffltsArea').show();
			$('.imdtlDscntTypeArea').show();
			//$('.promoApplyTypeTopArea').show();
		} else if(promoTypeCode == '10004') {		//타임특가
			$('.imdtlDscntTypeArea').show();
			$('#eventLimitQtyArea').show();
			$('[name=cpnSmtmApplyPsbltYn]').eq(1).prop('checked', true);
			$('[name=cpnSmtmApplyPsbltYn]').prop('disabled', true);
		} else if(promoTypeCode == '10005') {		//사은품 지급
			$('.giftArea').show();
			$('.cpnSmtmApplyPsbltArea').hide();
		}
		
		abc.biz.promotion.promotion.product.changeColumn(promoTypeCode); 	//그리드 변경
	}
	
	_promotionDetail.fieldDisabled = function(){
		//프로모션 타입별 show, hide
		var promoTypeCode = $('[name=promoTypeCode] option:selected').val();
		abc.biz.promotion.promotion.product.sheet.init();	//product 그리드 init
		$('.promoTypeCodeArea').hide();
		if(promoTypeCode == '10000') {				//1+1증정
			//$('.stdrSellAmtArea').show();
		} else if(promoTypeCode =='10001') {		//다족구매 할인
			$('.multiBuyDiscountArea').show();
			$('label[for="radioDisplay01"]').text('허용 (정액쿠폰만 사용 가능)');
			$('[name=stockDdctType]').prop('disabled', true);
		} else if(promoTypeCode == '10002') {		//즉시할인
			$('.imdtlDscntTypeArea').show();
			$('[name=stockDdctType]').prop('disabled', true);
			//$('.promoApplyTypeTopArea').show();
		} else if(promoTypeCode == '10003') {		//제휴사 즉시 할인
			$('.promotionAffltsArea').show();
			$('.imdtlDscntTypeArea').show();
			//$('.promoApplyTypeTopArea').show();
		} else if(promoTypeCode == '10004') {		//타임특가
			$('.imdtlDscntTypeArea').show();
			$('#eventLimitQtyArea').show();
			$('[name=cpnSmtmApplyPsbltYn]').eq(1).prop('checked', true);
			$('[name=cpnSmtmApplyPsbltYn]').prop('disabled', true);
		} else if(promoTypeCode == '10005') {		//사은품 지급
			$('.giftArea').show();
			$('.cpnSmtmApplyPsbltArea').hide();
		}
		
		abc.biz.promotion.promotion.product.changeColumn(promoTypeCode); //그리드 변경
		
		//프로모션 적용범위별 show, hide
		/*$('.promoApplyTypeArea').hide();
		if(promoTypeCode == '10002' || promoTypeCode == '10003') {
			var promoApplyType = $('[name=promoApplyType]:checked').val();
			$('.itemApplyTypeArea').hide();
			if(promoApplyType == 'P') {
				$('.itemApplyTypeArea').show();
			} else if(promoApplyType == 'C') {
				$('.categoryApplyTypeArea').show();
				$('.itemExceptArea').show();
			} else if(promoApplyType == 'B'){
				$('.brandApplyTypeArea').show();
				$('.itemExceptArea').show();
			}
		}*/
		
		if($('#chkMemberGrade2').is(':checked')) {
			$('[name=mbshpGradeCodes]').prop('disabled', false);
		}
		
		$('.checkChannel:checked').each(function(i, v) {
			if(i == 0) $('.chnnl-no').append('<option value="">채널선택</option>' );
			$('.chnnl-no').append('<option value="' + $(v).val() + '">' + $('label[for="' + $(this).attr('id') + '"]').text() + '</option>' );
		})
		
		if($('[name*=memberTypeCode]:checked').length == $('[name*=memberTypeCode]').length) {
			$('#chkMemberGrade0').prop('checked', true);
		} else {
			$('#chkMemberGrade0').prop('checked', false);
		}
	}
	
	_promotionDetail.appendBrand = function(result) {
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
				$('#brandArea').find('li').last().append('<input type="hidden" name="prPromotionTargetBrandArr.brandNo" value="' + v.brandNo + '" />');
			}
		});
	}
	
	_promotionDetail.appendGift = function(d) {
		var html = '';
		$.each(d, function(i, v) {
			var duplCheck = true;
			//var product2listArr = product2list.ExportData({"Type":"json"}).data;
			
			/*$.each(product2listArr, function(j, x) {
				if(v.prdtNo == x.prdtNo) {
					duplCheck = false;
					return;
				}
			});*/
			$('#giftTbodyArea').empty();
			
			if($('.giftTr').length != 0) {
				$('.giftTr').each(function(j, x) {
					if(v.prdtNo == $(this).find('[name*=prdtNo]').val()) {
						duplCheck = false;
						return false;
					}
				});
			}
			
			if(duplCheck) {
				html =   '<tr class="giftTr">' +
								'<td class="text-center">' + ($('.giftTr').length + 1) + '</td>' +
								'<td class="text-center">' + v.prdtNo + '<input type="hidden" name="prPromotionTargetGiftArr.prdtNo" value="' + v.prdtNo + '" /><input type="hidden" name="prPromotionTargetGiftArr.giftDelCheck" value="Y" />' + '</td>' +
								'<td class="text-center">' + v.prdtName + '</td>' +
								'<td class="text-center">' + v.totalStockQty + '<input type="hidden" name="giftTotalStockQty" value="' + v.totalStockQty + '"/></td>' +
								'<td class="input"><input type="text" name="prPromotionTargetGiftArr.maxEventLimitQty" maxlength="4" class="ui-input text-center giftInputArea" title="수량 입력">' +
								'<input type="hidden" name="prPromotionTargetGiftArr.prdtType" value="G" /></td>' +
								'<td class="text-center">-</td>' + 
								'<td class="text-center">'+ 0 + '</td>'
							'</tr>';
				$('#giftTbodyArea').append(html);
			}
		});
		
		_promotionDetail.freeGiftPop.close();
	}
	
	_promotionDetail.setDisabled = function(){
		$('input').prop('disabled', true);
		$('select').prop('disabled', true);
		/*$('.productDelBtn').off();
		$('.giftDelBtn').off();*/
		$('#multiBuyAdd').off();
		$('#multiBuyDel').off();
		
		$('#eventLimitQtyVal').prop('disabled', false);
		$('#cmsnRateVal').prop('disabled', false);
		
		if($('[name=promoProgressStatus]').val() != 'end') {
			$('[name=promoNo]').prop('disabled', false);
			$('[name=useYn]').prop('disabled', false);
			$('[name=promoName]').prop('disabled', false);
			$('[name=noteText]').prop('disabled', false);
			$('[name=promoProgressStatus]').prop('disabled', false);
			$('[name*=prPromotionTargetGiftArr]').prop('disabled', false);
			
			$('[name=paramPromoEndYmd]').prop('disabled', false);
			$('[name=paramPromoEndH]').prop('disabled', false);
			$('[name=paramPromoEndM]').prop('disabled', false);
		}
	}
	
	/**
	 * 유효성 체크 
	 */
	_promotionDetail.valid = function(){
		var frm = document.forms.frm;
		
		if($('[name=promoNo]').val() == '') {
			if($('[name=insdMgmtInfoText]').val() != '' && $('#duplCheckVal').val() != 'Y') {
				alert('내부 관리번호 중복확인 해주시기 바랍니다.');
				return false;
			}
		}
		
		if($('[name=promoName]').val() == '') {
			alert('프로모션명은 필수입니다.');
			return false;
		}
		
		var startYmd = new Date($('[name=paramPromoStartYmd]').val().replaceAll('\\.', '/') + ' ' 
				+ $('[name=paramPromoStartH]').val() + ':' + $('[name=paramPromoStartM]').val() + ':00');
		var endYmd = new Date($('[name=paramPromoEndYmd]').val().replaceAll('\\.', '/') + ' ' 
				+ $('[name=paramPromoEndH]').val() + ':' + $('[name=paramPromoEndM]').val() + ':00');
		
		if(startYmd.getTime() > endYmd.getTime()) {
			alert('프로모션 종료일이 시작일보다 앞설 수 없습니다.');
			$(frm.paramPromoStartYmd).focus();
			return false;
		}
		
		if(startYmd.getTime() == endYmd.getTime()) {
			alert('시작일과 종료일이 같을 수 없습니다.');
			$(frm.paramPromoStartYmd).focus();
			return false;
		}
		
		if($('[name=promoProgressStatus]').val() == 'none' || $('[name=promoProgressStatus]').val() == 'wait'){
			if(startYmd.getTime() < new Date().getTime()) {
				alert('프로모션 시작일이 현재날짜보다 앞설 수 없습니다.');
				$(frm.paramPromoStartYmd).focus();
				return false;
			}
		}
		
		if($('[name=deviceCodes]:checked').length == 0) {
			alert('디바이스는 필수입니다.');
			$('[name=deviceCodes]').eq(0).focus();
			return false;
		}
		/*
		if($('[name*=chnnlNo]:checked').length == 0) {
			alert('채널은 필수입니다.');
			$('[name*=chnnlNo]').eq(0).focus()
			return false;
		}
		*/
		var chkMbTypeCode = false;
		$('[name="prPromotionTargetGradeArr.memberTypeCode"]').each(function(){
			if($(this).is(":checked")){
				if($(this).val() == '10000'){
					if(!$('[name="prPromotionTargetGradeArr.memberTypeCode"][value="10001"]').is(":checked")){
						 alert("톰합멤버십 회원도 포함되어야 합니다.");
						 chkMbTypeCode = true;
						 return false;
					}
				}else if($(this).val() == '10002'){
					if(!$('[name="prPromotionTargetGradeArr.memberTypeCode"][value="10000"]').is(":checked")){
						 alert("온라인 회원도 포함되어야 합니다.");
						 chkMbTypeCode = true;
						 return false;
					}
					if(!$('[name="prPromotionTargetGradeArr.memberTypeCode"][value="10001"]').is(":checked")){
						 alert("톰합멤버십 회원도 포함되어야 합니다.");
						 chkMbTypeCode = true;
						 return false;
					}
				}
			}
			
		});
		
		if(chkMbTypeCode){
			return false;
		}

		var promoTypeCode = $('[name=promoTypeCode] option:selected').val();
		if(promoTypeCode == '10000') {				//1+1증정
			/*if(!$.isNumeric(frm.stdrSellAmt.value)) {
				alert('프로모션 상품가격은 숫자만 입력이 가능합니다.');
				$(frm.stdrSellAmt).focus();
				return false;
			}*/
		} else if(promoTypeCode =='10001') {		//다족구매 할인
			var numCheck = false;
			$('#multiBuyArea input[name*=dscntRate]').each(function(i, v) {
				if(!$.isNumeric($(v).val())) {
					alert('족수별 할인율은 숫자만 입력이 가능합니다.');
					$(v).focus();
					numCheck = true;
					return false;
				}
				
				if(i != 0) {
					if(parseInt($(v).closest('li').prev().find('[name*=dscntRate]').val()) >= parseInt($(v).val())) {
						alert('전 족수보다 할인율이 높아야 합니다.');
						numCheck = true;
						return false;
					}
				}
			});
			if(numCheck) return false;
		} else if(promoTypeCode == '10002' || promoTypeCode == '10003' || promoTypeCode == '10004') {		//즉시할인, 제휴사 즉시 할인
			if(!$.isNumeric(frm.imdtlDscntValue.value)) {
				alert('할인율(액)은 숫자만 입력이 가능합니다.');
				$(frm.imdtlDscntValue).focus();
				return false;
			}
			
			if(frm.imdtlDscntValue.value == 0) {
				alert('할인율(액)은 1이상 입력해주세요.');
				$(frm.imdtlDscntValue).focus();
				return false;
			}
			
			if($('[name=imdtlDscntType]:checked').val() == 'R') {
				if(frm.imdtlDscntValue.value > 100) {
					alert('할인율은 100을 넘을 수 없습니다.');
					$(frm.imdtlDscntValue).focus();
					return false;
				}
			} else if($('[name=imdtlDscntType]:checked').val() == 'U') {
				if(frm.imdtlDscntValue.value < 100) {
					alert('균일가는 100 미만 일 수 없습니다.');
					$(frm.imdtlDscntValue).focus();
					return false;
				}
			} else {
				if(frm.imdtlDscntValue.value < 100) {
					alert('할인액은 100 미만 일 수 없습니다.');
					$(frm.imdtlDscntValue).focus();
					return false;
				}
			}
		}
		
		if(promoTypeCode == '10005') {
			if($('#giftTbodyArea').find('.giftTr').length == 0) {
				alert('사은품을 등록해주세요.');
				return false;
			}
		}
		
		return true;
	}
	
	$(function() {
		_promotionDetail.init();
	});
})();