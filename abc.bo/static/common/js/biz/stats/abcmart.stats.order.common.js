/***
 * Order Stats 업무와 관련된 공통 함수 정의.
 *
 */
(function() {
	var _orderStatsCommon = abc.object.createNestedObject(window,"abc.biz.stats.order.common");

	_orderStatsCommon.init = function() {

		/** 상품 구분 코드 변경 함수 **/
		if (! abc.text.allNull($("#mmnyPrdtYn"))) {
			$("#mmnyPrdtYn").change(function() {
				if ($(this).val() == abc.consts.BOOLEAN_TRUE) { // 자사 상품일 경우
					$("#chnnl").show();
					$("#shopSite").hide();
				} else if ($(this).val() == abc.consts.BOOLEAN_FALSE) { // 입점사 상품일 경우
					$("#chnnl").hide();
					$("#shopSite").show();
				} else {
					$("#chnnl").hide();
					$("#shopSite").hide();
				}
			});
		}

		/** 발송처 변경 함수  */
		if (! abc.text.allNull($("#stockGbnCodeAll"))) {
			$("#stockGbnCodeAll").click(function(){
				if($(this).is(":checked")){
					$("input[custom=stockGbnCode]").prop("checked",true);
				} else {
					$("input[custom=stockGbnCode]").prop("checked",false);
				}
			});

			$("input[custom=stockGbnCode]").each(function(){
				$(this).click(function(){
					if($(this).is(":checked")){
						var unChecked = $("input[custom=stockGbnCode]:not(:checked)");
						if (unChecked.length == 0) {
							$("#stockGbnCodeAll").prop("checked",true);
						}
					} else {
						$("#stockGbnCodeAll").prop("checked",false);
					}
				})
			});
		}

		/** 결제수단 변경 함수 */
		if (! abc.text.allNull($("#chkPaymentAll"))) {
			$("#chkPaymentAll").click(function(){
				if($(this).is(":checked")){
					$("input[custom=pymntMeansCode]").prop("checked",true);
					$("#chkEscroTrue").prop("checked",true);
					$("#chkEscroFalse").prop("checked",true);
				} else {
					$("input[custom=pymntMeansCode]").prop("checked",false);
					$("#chkEscroTrue").prop("checked",false);
					$("#chkEscroFalse").prop("checked",false);
				}
			});

			$("#dlvyTypeCode").change(function(){
				var value = $(this).val();
				if (value == '10002' || value == '99999') {
					abc.biz.order.order.getChannelList(value);
					$("#chnnlNo").show();
				}else{
					$("#chnnlNo").hide();
				}
			});

			$("input[custom=pymntMeansCode]").each(function(){
				$(this).click(function(){
					if($(this).is(":checked")){
						var unChecked = $("input[custom=pymntMeansCode]:not(:checked)");
						if (unChecked.length == 0) {
							$("#chkPaymentAll").prop("checked",true);
						}
						if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){
							$("#chkEscroTrue").prop("checked",true);
							$("#chkEscroFalse").prop("checked",true);
						}
						
					} else {
						$("#chkPaymentAll").prop("checked",false);
						if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){
							if(!$("#chkPayment10001").is(":checked") && !$("#chkPayment10002").is(":checked")){
								$("#chkEscroTrue").prop("checked",false);
								$("#chkEscroFalse").prop("checked",false);
							}
						}
					}
				})
			});

			$("input[custom=escrApplyYn]").each(function(){
				$(this).click(function(){
					if($(this).is(":checked")){
						var unChecked = $("input[custom=escrApplyYn]:not(:checked)");
						if (unChecked.length > 0) {
							$("#chkPayment10001").prop("checked",true);
							$("#chkPayment10002").prop("checked",true);
						}
					} else {
					}
				})
			});
		}

		/** 결제구분 변경 함수  */
		if (! abc.text.allNull($("#deviceCodeAll"))) {
			$("#deviceCodeAll").click(function(){
				if($(this).is(":checked")){
					$("input[custom=deviceCode]").prop("checked",true);
				} else {
					$("input[custom=deviceCode]").prop("checked",false);
				}
			});

			$("input[custom=deviceCode]").each(function(){
				$(this).click(function(){
					if($(this).is(":checked")){
						var unChecked = $("input[custom=deviceCode]:not(:checked)");
						if (unChecked.length == 0) {
							$("#deviceCodeAll").prop("checked",true);
						}
					} else {
						$("#deviceCodeAll").prop("checked",false);
					}
				})
			});
		}
		
		/**회원구분 변경 함수 */
		if(! abc.text.allNull($('#chkMemberAll'))) {
			$('#chkMemberAll').off().on('click', function(){
				if($(this).is(':checked')) {
					$('input[name=chkMember]').prop('checked', true);
				} else {
					$('input[name=chkMember]').prop('checked', false);
				}
			});
			
			$('input[name=chkMember]').each(function() {
				$(this).off().on('click', function() {
					if($(this).is(':checked')) {
						var unChecked = $('input[name=chkMember]:not(:checked)');
						if(unChecked.length == 0) {
							$('#chkMemberAll').prop('checked', true);
						}
					} else {
						$('#chkMemberAll').prop('checked', false);
					}
				});
			});
		}
		
		/**발송처구분 변경 함수 */
		if(! abc.text.allNull($('#stockGbnCodeAll'))) {
			$('#stockGbnCodeAll').off().on('click', function() {
				if($(this).is(':checked')) {
					$('input[name=stockGbnCodeArr]').prop('checked', true);
				} else {
					$('input[name=stockGbnCodeArr]').prop('checked', false);
				}
			});
			
			$('input[name=stockGbnCodeArr]').each(function(){
				$(this).off().on('click', function(){
					if($(this).is(':checked')) {
						var unChecked = $('input[name=stockGbnCodeArr]:not(:checked)');
						if(unChecked.length == 0) {
							$('#stockGbnCodeAll').prop('checked', true);
						}
					} else {
						$('#stockGbnCodeAll').prop('checked', false);
					}
				});
			});
		}
		
		//에스크로 둘다 선택시 무통장입금, 실시관계좌이체 체크
		$('#chkEscroTrue, #chkEscroFalse').off().on('click', function(){
			var escroUnChecked = $('input[custom=escrApplyYn]:not(:checked)');
			
			if(escroUnChecked.length == 2) {
				$('#chkPayment10001, #chkPayment10002, #chkPaymentAll').prop('checked',false);
			} else if(escroUnChecked.length == 0) {
				$('#chkPayment10001, #chkPayment10002').prop('checked',true);
				
				$('input[name=pymntMeansCode]').each(function(){
					if($(this).is(':checked')) {
						var unChecked = $('input[name=pymntMeansCode]:not(:checked)');
						if(unChecked.length == 0){
							$('#chkPaymentAll').prop('checked',true);
						}
					} else {
						$('#chkPaymentAll').prop('checked',false);
					}
				});
			}
		});
		
		//상품코드 구분
		$('#mmnyPrdtYn').off().on('change', function(){
			if($(this).val() == 'C' || $(this).val() == 'Y') {
				$('#ourChannel').removeClass('hidden');
				$('#shoperSiteNo').addClass('hidden').val('');
				
				//자사 선택시, 입점사 비노출, 나머지 노출
				$('#chkStore10003').prop('checked', false).parent().parent().addClass('hidden');
				$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', true).parent().parent().removeClass('hidden');
				_salesStats.chnnlNo = $('#ourChannel').val();
			} else if($(this).val() == 'V' || $(this).val() == 'N') {
				$('#ourChannel').addClass('hidden').val('');
				$('#shoperSiteNo').removeClass('hidden');
				
				//입점 선택시, 입점사 노출, 나머지 비노출
				$('#chkStore10003').prop('checked', true).parent().parent().removeClass('hidden');
				$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false).parent().parent().addClass('hidden');
				_salesStats.chnnlNo = $('#shoperSiteNo').val();
			} else if($(this).val() == '') {
				$('#ourChannel').addClass('hidden').val('');
				$('#shoperSiteNo').addClass('hidden').val('');
				
				//전체 선택시 모두 노출
				$('input[name=stockGbnCodeArr]').prop('checked', true).parent().parent().removeClass('hidden');
				_salesStats.chnnlNo = '';
			}
		});

		//po용 상품코드 구분
		var mmnyPrdt = $('#mmnyPrdtYn');
		if(mmnyPrdt.children().length <= 1 && (mmnyPrdt.val() == 'V' || mmnyPrdt.val() == 'N')) {
			console.log('here~');
			mmnyPrdt.prop('disabled', true);
			$('#shoperSiteNo').addClass('hidden').val('');
			$('#chkStore10003').prop('checked', true).parent().parent().removeClass('hidden');
			$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false).parent().parent().addClass('hidden');
			//_salesStats.chnnlNo = $('#shoperSiteNo').val();
		}
		
	}

})();
