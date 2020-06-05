/***
 * 클레임 교환상세와 관련된 함수 정의.
 */
(function() {
	
	var _claim = abc.object.createNestedObject(window,"abc.biz.claim.exchange.detail");
	
	_claim.processIng = false;
	
	/**
	 * Const value(jsp에서 java const 지정 값 호출하여 set)
	 */
	_claim.consts = {
			ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT : "" // 추가배송비결제방법 : 추가결제
		   ,ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON : "" // 추가배송비결제방법 : 무료쿠폰
		   ,CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE : "" // 클레임 사유 코드(교환) : 옵션변경
		   ,CLM_PROC_TYPE_CODE_EXCHANGE_IMPOSSIBLE : "" // 클레임 사유 코드(교환) : 교환불가
		   ,CLM_PROC_TYPE_CODE_EXCHANGE_UNRELEASED_PROC : "" // 클레임 처리유형 코드(교환) : 미출반품처리
		   ,VNDR_GBN_TYPE_V : "" // 업체구분코드 : 업체
	}
	
	_claim.SITE_NO_ART	= "";
	_claim.SITE_NO_OTS	= "";
	
	_claim.ABC_FO_URL	= "";
	_claim.OTS_FO_URL	= "";
	
	_claim.CLM_PROC_TYPE_CODE_EXCHANGE_BAD			  = "";
	_claim.CLM_PROC_TYPE_CODE_EXCHANGE_WRONG_DELIVERY = "";
	_claim.PYMNT_STAT_CODE_PAYMENT_FINISH			  = "";
	
	/**
	 * 일괄적용 처리용 콤보박스 변경
	 */
	_claim.changeComboForBatchApply = function() {
		if($("#codeDivSelect option:selected").val() == "customerNoticeOpts"){ // 고객알림내용
			$("#inputReasonText").val("");
			$("#inputReasonText").attr("placeholder", "내용");
			$("#clmRsnCodeSelect").hide();
			$("#clmProcTypeCodeSelect").hide();
		} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 교환사유
			$("#inputReasonText").val("");
			$("#inputReasonText").attr("placeholder", "사유");
			$("#clmRsnCodeSelect").show();
			$("#clmProcTypeCodeSelect").hide();
		} else if($("#codeDivSelect option:selected").val() == "clmProcTypeCodeOpts"){ // 처리(심의)유형/내용
			$("#inputReasonText").val("");
			$("#inputReasonText").attr("placeholder", "내용");
			$("#clmRsnCodeSelect").hide();
			$("#clmProcTypeCodeSelect").show();
		}
	};
	
	/**
	 * 일괄적용
	 */
	_claim.batchApplyProduct = function() {
		if($("#codeDivSelect option:selected").val() == "customerNoticeOpts"){ // 고객알림내용
			if(abc.text.isBlank($("#inputReasonText").val())) {
				alert("고객알림 내용을 입력하세요.");
				$("#inputReasonText").focus()
				return false;
			}
			if(abc.text.isLimitLength($("#inputReasonText").val(), 100)){
				alert("고객알림 내용이 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#inputReasonText").focus();
				return false;
			}
		} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 교환사유
			if(abc.text.isBlank($("#clmRsnCodeSelect option:selected").val())) {
				alert("교환 유형을 선택하세요.");
				$("#clmRsnCodeSelect").focus();
				return false;
			}
			if(abc.text.isBlank($("#inputReasonText").val())) {
				alert("교환 사유를 입력하세요.");
				$("#inputReasonText").focus();
				return false;
			}
			if(abc.text.isLimitLength($("#inputReasonText").val(), 100)){
				alert("교환 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#inputReasonText").focus();
				return false;
			}
		} else if($("#codeDivSelect option:selected").val() == "clmProcTypeCodeOpts"){ // 처리(심의)유형/내용
			if(abc.text.isBlank($("#clmProcTypeCodeSelect option:selected").val())) {
				alert("처리(심의)유형을 선택하세요.");
				$("#clmProcTypeCodeSelect").focus();
				return false;
			}
			if(abc.text.isBlank($("#inputReasonText").val())) {
				alert("처리(심의)유형 내용을 입력하세요.");
				$("#inputReasonText").focus();
				return false;
			}
			if(abc.text.isLimitLength($("#inputReasonText").val(), 100)){
				alert("처리(심의)유형 내용이 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#inputReasonText").focus();
				return false;
			}
		}
		
		if($('#claimProductForm input[name=chkTarget]').filter(':checked').length < 1) {
			alert("일괄적용 대상 상품을 선택하십시오.");
			return false;
		}
		
		if(confirm("일괄적용 하시겠습니까??")){
			if($("#codeDivSelect option:selected").val() == "customerNoticeOpts"){ // 고객알림내용
				$("#claimProductForm textarea[name=cstmrAlertContText]").each(function(idx, rowItem) {
					if($('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) { // 체크 한것만
						$("#claimProductForm textarea[name=cstmrAlertContText]").eq(idx).val($("#inputReasonText").val());
					}
				});
			} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 교환유형, 사유
				$("#claimProductForm select[name=clmRsnCode]").each(function(idx, rowItem) {
					if($('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) { // 체크 한것만
						$("#claimProductForm select[name=clmRsnCode]").eq(idx).val($("#clmRsnCodeSelect").val());
						$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val($("#inputReasonText").val());
						
						if($("#clmRsnCodeSelect").val() == _claim.consts.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE) {
							_claim.setFormOptionChange(true, idx);
						} else {
							_claim.setFormOptionChange(false, idx);
						}
					}
				});
			} else if($("#codeDivSelect option:selected").val() == "clmProcTypeCodeOpts"){ // 처리(심의)유형/내용
				$("#claimProductForm select[name=clmProcTypeCode]").each(function(idx, rowItem) {
					if($('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) { // 체크 한것만
						$("#claimProductForm select[name=clmProcTypeCode]").eq(idx).val($("#clmProcTypeCodeSelect").val());
						$("#claimProductForm textarea[name=clmProcContText]").eq(idx).val($("#inputReasonText").val());
					}
				});
			}
		}
	};
	
	/**
	 * 교환유형에 따른 옵션 관련 폼 제어
	 */
	_claim.setFormOptionChange = function(isShow, idx) {
		if(isShow) {
			$("#claimProductForm button[name=changeOptnBtn]").eq(idx).show();
		} else {
			$("#claimProductForm button[name=changeOptnBtn]").eq(idx).hide();
			$("#claimProductForm input[name=changePrdtOptnNo]").eq(idx).val("");
			$("#claimProductForm input[name=changeOptnName]").eq(idx).val("");
			
			$("#changeOptnText" + idx).text("");
			$("#changeOptnText" + idx).hide();
		}
	};
	
	/**
	 * 클레임 교환상품 저장
	 */
	_claim.saveClaimProduct = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(!_claim.saveClaimProductValidate()) { // 클레임 교환상품 저장 form validate
			return false;
		};
		
		if(confirm("저장하시겠습니까?")) {
			_claim.processIng = true;
			
			$("#claimProductForm input[name=chkTarget]").each(function(idx, rowItem) {
				if(!$('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) {
					$('#claimProductForm input[name=clmPrdtSeq]').eq(idx).val("");
				}
			});
			
			$("#claimProductForm input[name=chkRecptYn]").each(function(idx, rowItem) {
				if($('#claimProductForm input[name=chkRecptYn]').eq(idx).prop("checked")) {
					$('#claimProductForm input[name=recptYn]').eq(idx).val("Y");
				} else {
					$('#claimProductForm input[name=recptYn]').eq(idx).val("N");
				}
			});
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/save-claim-exchange-product",
							data : $("#claimProductForm").serialize()
						})
						request.done(function(data){
							alert("저장에 성공했습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "저장 처리에 실패했습니다.");
														
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 클레임 교환상품 저장 form validate
	 */
	_claim.saveClaimProductValidate = function() {
		if($("#claimProductForm input[name=chkTarget]").filter(":checked").length < 1) {
			alert("저장 대상을 선택하세요.");
			return false;
		}
		
		// 클레임 상품 check
		var loopCheck = true;
		$("#claimProductForm input[name=clmPrdtSeq]").each(function(idx, rowItem) {
			if($('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) { // 체크 한것만
				if( $("#claimProductForm select[name=clmRsnCode]").eq(idx).prop("disabled") ) {
					if( abc.text.isBlank($("#claimProductForm select[name=clmRsnCode]").eq(idx).val()) ) {
						alert("교환 유형을 선택하세요.");
						$("#claimProductForm select[name=clmRsnCode]").eq(idx).focus();
						loopCheck = false;
						return false;
					}
				}
				if( !$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).prop("disabled") ) {
					if( abc.text.isBlank($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val()) ) {
						alert("교환 사유를 입력하세요.");
						$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).focus();
						loopCheck = false;
						return false;
					}
				}
				if( $("#claimProductForm select[name=clmRsnCode]").eq(idx).val() == _claim.consts.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE ) {
					if(abc.text.isBlank($("#claimProductForm input[name=changePrdtOptnNo]").eq(idx).val())) {
						alert("교환 유형이 옵션변경인 경우에는 옵션을 변경하셔야 합니다.");
						$("#claimProductForm textarea[name=changeOptnBtn]").eq(idx).focus();
						loopCheck = false;
						return false;
					}
				}
				if( !$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).prop("disabled") ) {
					if(abc.text.isLimitLength($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val(), 100)){
						alert("교환 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
						$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).focus();
						loopCheck = false;
						return false;
					}
				}
				if(abc.text.isLimitLength($("#claimProductForm textarea[name=cstmrAlertContText]").eq(idx).val(), 100)) {
					alert("고객 알림내용 최대 길이(100-영문1,한글2)를 초과하였습니다.");
					$("#claimProductForm textarea[name=cstmrAlertContText]").eq(idx).focus();
					loopCheck = false;
					return false;
				}
				if( !$("#claimProductForm textarea[name=clmProcContText]").eq(idx).prop("disabled") ) {
					if(abc.text.isLimitLength($("#claimProductForm textarea[name=clmProcContText]").eq(idx).val(), 100)){
						alert("처리(심의)유형 내용이 최대 길이(100-영문1,한글2)를 초과하였습니다.");
						$("#claimProductForm textarea[name=clmProcContText]").eq(idx).focus();
						loopCheck = false;
						return false;
					}
				}
			}
		});
		if(!loopCheck) return false;
		
		return true;
	};
	
	/**
	 * 회수지 우편번호 set
	 */
	_claim.setRetrievalPost = function(data) {
        $("#buyerPostCodeText").val(data.postCode);
        $("#buyerPostAddrText").val(data.postAddress);
        $("#buyerDtlAddrText").val("");
        $("#buyerDtlAddrText").focus(); // 상세주소 포커스
        //data.buildingCode 건물번호
    };

	/**
	 * 재배송지 우편번호 set
	 */
	_claim.setDeliveryPost = function(data) {
        $("#rcvrPostCodeText").val(data.postCode);
        $("#rcvrPostAddrText").val(data.postAddress);
        $("#rcvrDtlAddrText").val("");
        $("#rcvrDtlAddrText").focus(); // 상세주소 포커스
        //data.buildingCode 건물번호
    };
    
    /**
	 * 목록 체크박스 전체 체크/해제
	 */
	_claim.toggleCheckAll = function(allCheckBoxId, targetCheckBoxName) {
		if($("#" + allCheckBoxId).prop("checked")) {
			$("input[name='"+targetCheckBoxName+"']").prop("checked",true);
		} else {
			$("input[name='"+targetCheckBoxName+"']").prop("checked",false);
		}
	};
	
	/**
	 * 배송주소와 동일, 교환품수거지와 동일 체크박스 클릭
	 */
	_claim.toggleSameDelivery = function(object) {
		if(object.id == "chkDeliveryModule") { // 배송주소와 동일 체크박스
			if($(object).is(":checked")) {
				$("#buyerName").val($("#order_rcvrName").val());
				$("#buyerHdphnNoText").val($("#order_rcvrHdphnNoText").val());
				$("#buyerPostCodeText").val($("#order_rcvrPostCodeText").val());
				$("#buyerPostAddrText").val($("#order_rcvrPostAddrText").val());
				$("#buyerDtlAddrText").val($("#order_rcvrDtlAddrText").val());
			} else {
				$("#buyerName").val("");
				$("#buyerHdphnNoText").val("");
				$("#buyerPostCodeText").val("");
				$("#buyerPostAddrText").val("");
				$("#buyerDtlAddrText").val("");
			}
		} else if(object.id == "chkDeliveryModule02") { // 교환품수거지와 동일 체크박스
			if($(object).is(":checked")) {
				$("#rcvrName").val($("#buyerName").val());
				$("#rcvrHdphnNoText").val($("#buyerHdphnNoText").val());
				$("#rcvrPostCodeText").val($("#buyerPostCodeText").val());
				$("#rcvrPostAddrText").val($("#buyerPostAddrText").val());
				$("#rcvrDtlAddrText").val($("#buyerDtlAddrText").val());
			} else {
				$("#rcvrName").val("");
				$("#rcvrHdphnNoText").val("");
				$("#rcvrPostCodeText").val("");
				$("#rcvrPostAddrText").val("");
				$("#rcvrDtlAddrText").val("");
			}
		}
	};
	
	/**
	 * 옵션변경 팝업
	 */
	_claim.optionChangePop = function(idx) {
		var orderNo = $("#claimProductForm input[name=orderNo]").eq(idx).val();
		var orderPrdtSeq = $("#claimProductForm input[name=orderPrdtSeq]").eq(idx).val();
		var callbackFunction = "optionChangePopCallBack";
		var strParam = idx;
		var layerType = "Claim";
		
		window.abc.optionChangePopup(orderNo , orderPrdtSeq , callbackFunction , strParam , layerType);
		/*
		abc.open.popup({
			winname :	"optionChangePop",
			url 	:	"/order/option-change",
			width 	:	640,
			height	:	745,
			params	:	{
				orderNo : $("#orderNo").val(),
				orderPrdtSeq : $("#claimProductForm input[name=orderPrdtSeq]").eq(idx).val()
			}
		});
		*/
	};
	
	/**
	 * 옵션변경 팝업 콜백 처리
	 */
	_claim.optionChangePopCallBackProc = function(data) {
		$("#claimProductForm input[name=changePrdtOptnNo]").eq(Number(data.toParams.strParam)).val(data.prdtOptnNo);
		$("#claimProductForm input[name=changeOptnName]").eq(Number(data.toParams.strParam)).val(data.optnName);
		
		$("#changeOptnText" + data.toParams.strParam).text(data.optnName);
		$("#changeOptnText" + data.toParams.strParam).show();
	};
	
	/**
	 * 미처리
	 */
	_claim.updateClaimUnProcYn = function() {
		var unProcYn = "N";
		
		if($("#unProcYn").prop("checked")) {
			unProcYn = "Y";
		}
		
		var request	= 
			$.ajax({
				type :"get",
				url : "/claim/claim/update-claim-unprocyn",
				data : {
					clmNo : $("#claimProductForm input[name=clmNo]").val(),
					unProcYn : unProcYn
				}
			})
			request.done(function(data){
				alert("미처리표시 상태를 변경하였습니다.");
			})
			request.fail(function(jqXHR, textStatus, errorThrown){
				_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "미처리 변경에 실패했습니다.");
			});
	};
	
	/**
	 * 점간이동여부 처리
	 */
	_claim.updateBranchMoveCode = function(checkVal) {
		var request	= 
			$.ajax({
				type :"get",
				url : "/claim/claim/update-claim-branchmovecode",
				data : {
					clmNo : $("#claimProductForm input[name=clmNo]").val(),
					branchMoveCode : checkVal
				}
			})
			request.done(function(data){
				alert("점간이동여부를 변경하였습니다.");
			})
			request.fail(function(jqXHR, textStatus, errorThrown){
				_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "점간이동여부 변경에 실패했습니다.");
			});
	};
	
	/**
	 * 클레임 접수철회
	 */
	_claim.updateClaimWthdraw = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(abc.text.isBlank($("#clmWthdrawRsnCode").val())) {
			alert("철회 유형을 선택하세요.");
			$("#clmWthdrawRsnCode").focus();
			return false;
		}
		if(abc.text.isBlank($("#clmWthdrawContText").val())) {
			alert("철회 사유를 입력하세요.");
			$("#clmWthdrawContText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#clmWthdrawContText").val(), 100)){
			alert("철회 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#clmWthdrawContText").focus();
			return false;
		}
		
		if(confirm("접수 철회를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/update-claim-exchange-wthdraw",
							data : $("#claimWthdrawForm").serialize(),
							async : false
						})
						request.done(function(data){
							if(data.success == "Y") {
								alert("접수철회를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							} else {
								alert(data.message);
							}
							
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수철회 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 수거지시
	 */
	_claim.requestClaimRetrieval = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if($("#claimProductForm input[name=vndrGbnType]").val() == _claim.consts.VNDR_GBN_TYPE_V) { // 업체 클레임
			alert("입점사에서 해당 클레임건 확인 및 처리중입니다.");
			return false;
		}
		
		if($("#addDlvyPayFinishYn").val() == 'N') {
			alert("클레임배송비 입금완료 상태가 아닙니다.");
			return false;
		}
		
		if(confirm("수거 지시를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/request-claim-retrieval",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("수거 지시를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "수거지시 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 수령완료
	 */
	_claim.finishclaimReceipt = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if($("#claimProductForm input[name=vndrGbnType]").val() == _claim.consts.VNDR_GBN_TYPE_V) { // 업체 클레임
			alert("입점사에서 해당 클레임건 확인 및 처리중입니다.");
			return false;
		}
		
		// 전체 상품이 수령완료 체크 상태가 아니면 수령상태 전환 불가
		var loopCheck = true;
		$('#claimProductForm input[name=chkRecptYn]').each(function(idx, rowItem) {
			if($('#claimProductForm input[name=recptYn]').eq(idx).val() == 'N') {
				loopCheck = false;
				return false;
			}
		});
		if(!loopCheck) {
			alert("상품이 모두 수령상태가 아닙니다.\n상품을 모두 수령상태로 저장 후 상태변경을 하세요.");
			return false;
		}
		
		if(confirm("수령완료 처리를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/finish-claim-receipt",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("수령완료 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "수령완료 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 심의완료
	 */
	_claim.finishClaimJudge = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if($("#claimProductForm input[name=vndrGbnType]").val() == _claim.consts.VNDR_GBN_TYPE_V) { // 업체 클레임
			alert("입점사에서 해당 클레임건 확인 및 처리중입니다.");
			return false;
		}
		
		// 처리(심의)유형/내용 체크
		var loopCheck = true;
		$('#claimProductForm input[name=chkRecptYn]').each(function(idx, rowItem) {
			if( abc.text.isBlank($("#claimProductForm input[name=clmProcTypeCodeValue]").eq(idx).val()) ) {
				loopCheck = false;
				return false;
			}
			if( abc.text.isBlank($("#claimProductForm input[name=clmProcContTextValue]").eq(idx).val()) ) {
				loopCheck = false;
				return false;
			}
		});
		if(!loopCheck) {
			alert("상품의 처리(심의)유형 및 내용이 모두 입력되지 않았습니다.\n상품의 처리(심의)유형 및 내용 저장 후 상태변경을 하세요.");
			return false;
		}
		
		if(confirm("심의완료 처리를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/finish-claim-exchange-judge",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("심의완료 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "심의완료 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 배송지시
	 */
	_claim.cmdClaimDeliveryProc = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		// 2020.03.18 : 불량 오배송 사유일시 배송비무료가 아니라면 validate
		var isProcTypeOk = true;
		$("select[name='clmProcTypeCode']").each(function(){
			// 불량 == 10000 / 오배송 == 10001
			if( $(this).val() == _claim.CLM_PROC_TYPE_CODE_EXCHANGE_BAD || $(this).val() == _claim.CLM_PROC_TYPE_CODE_EXCHANGE_WRONG_DELIVERY ){
				//console.log(_claim.CLM_PROC_TYPE_CODE_EXCHANGE_BAD);
				//console.log(_claim.CLM_PROC_TYPE_CODE_EXCHANGE_WRONG_DELIVERY);
				isProcTypeOk = false;
			}
		});
		
		if(isProcTypeOk == false){
			var addDlvyAmtPymntType     = $("#addDlvyAmtPymntType").val();
			var addDlvyAmtPymntStatCode = $("#addDlvyAmtPymntStatCode").val();
			// 선결제 P : 결제취소 되었는지
			if( addDlvyAmtPymntType == "P" ){
				//console.log("결제완료 코드 : " + _claim.PYMNT_STAT_CODE_PAYMENT_FINISH)
				//console.log("결제상태 코드 : " + addDlvyAmtPymntStatCode);
				if( addDlvyAmtPymntStatCode != _claim.PYMNT_STAT_CODE_PAYMENT_FINISH ){
					isProcTypeOk = true;
				}
				if(isProcTypeOk == false){
					alert("처리(심의)유형이 한개의 상품이라도 '불량 확인' 또는 '오배송 확인'일 경우, 배송비 결제취소 후에 배송지시를 진행할 수 있습니다.");
					return false;
				}
			}
			// 무료배송비쿠폰 C : 쿠폰이 환불되었는지
			else if( addDlvyAmtPymntType == "C" ){
				alert("처리(심의)유형이 한개의 상품이라도 '불량 확인' 또는 '오배송 확인'일 경우, 배송비 쿠폰사용복원 후에 배송지시를 진행할 수 있습니다.");
				return false;
			}

		}
		
		if(confirm("배송지시 처리를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/cmd-claim-exchange-delivery-proc",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("배송지시 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "배송지시 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 교환완료
	 */
	_claim.finishClaimAllProc = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		var unreleasedCheck = true; // 미출반품 지정 확인
		var chngDlvyStatCheck = true; // 재배송상품 배송상태 확인
		
		/*
		// 미출반품 지정 확인
		$('#claimProductForm input[name=orderPrdtSeq]').each(function(idx, rowItem) {
			if( $("#claimProductForm input[name=changeDlvyDscntcYn]").eq(idx).val() == 'Y' ) { // 배송중단
				// 배송중단인 경우 처리 유형이 미출반품 처리로 되어있는지 확인(입력상태가 아닌 저장된 값 확인)
				if( $("#claimProductForm input[name=clmProcTypeCodeValue]").eq(idx).val() != _claim.consts.CLM_PROC_TYPE_CODE_RETURN_UNRELEASED_PROC ) {
					unreleasedCheck = false;
					return false;
				}
			}
		});
		if(!unreleasedCheck) {
			alert("배송중단 상품의 처리(심의)유형이 미출반품처리가 아닙니다.\n상품의 처리(심의)유형을 미출반품처리로 변경하여 저장 후 교환완료 처리를 하세요.");
			return false;
		}
		*/
		
		// 재배송상품 배송상태 확인
		$('#claimProductForm input[name=orderPrdtSeq]').each(function(idx, rowItem) {
			if( $("#claimProductForm input[name=changeDlvyDscntcYn]").eq(idx).val() == 'N' ) {
				// 배송중단이 아닌 경우 재배송 상품 운송장이 없는 경우
				if( abc.text.isBlank($("#claimProductForm input[name=changeWaybilNoText]").eq(idx).val()) ) {
					chngDlvyStatCheck = false;
					return false;
				}
			}
		});

		if(!chngDlvyStatCheck) {
			alert("교환 배송정보가 없는 상품이 존재합니다.");
			//강제로 처리? 아니면 처리불가?
			//return false;
		}
		
		if(confirm("교환완료 처리를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/finish-claim-exchange-all-proc",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("교환완료 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "교환완료 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 2020.04.22 : 
	 * 교환배송지시가 내려진 상태에서 교환재배송상품 중 하나라도 미출반품처리대상이되어 배송중단이 내려지면 전체 교환불가
	 */
	_claim.setExchangeImpsbl = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(confirm("교환불가 처리를 진행하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/set-claim-exchange-impossible",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("미출로인한 교환불가 처리가 정상적으로 완료되었습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "교환불가 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 환수환불 접수
	 */
	_claim.acceptClaimRedempRefund = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(confirm("환수환불 접수를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/accept-claim-redemp-refund",
							data : $("#claimProductForm").serialize(),
						})
						request.done(function(data){
							alert("환수환불 접수 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "환수환불 접수 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 접수기본정보 및 배송비 저장 전처리 - 데이터 validate 및 배송비 결제인증(kcp) 처리
	 */
	_claim.saveClaimDeliveryInfoPreProc = function() {
		// 회수지 정보 check
		if(abc.text.isBlank($("#buyerPostCodeText").val())) {
			alert("회수지 우편번호를 입력하세요.");
			$("#buyerPostCodeText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#buyerPostCodeText").val(), 6)){
			alert("회수지 우편번호가 최대 길이(6)를 초과하였습니다.");
			$("#buyerPostCodeText").focus();
			return false;
		}
		if(abc.text.isBlank($("#buyerPostAddrText").val())) {
			alert("회수지 주소를 입력하세요.");
			$("#buyerPostAddrText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#buyerPostAddrText").val(), 200)){
			alert("회수지 주소가 최대 길이(200-영문1,한글2)를 초과하였습니다.");
			$("#buyerPostAddrText").focus();
			return false;
		}
		if(abc.text.isBlank($("#buyerDtlAddrText").val())) {
			alert("회수지 상세주소 입력하세요.");
			$("#buyerDtlAddrText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#buyerDtlAddrText").val(), 100)){
			alert("회수지 상세주소가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#buyerDtlAddrText").focus();
			return false;
		}
		if(abc.text.isBlank($("#buyerName").val())) {
			alert("발송인 명을 입력하세요.");
			$("#buyerName").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#buyerName").val(), 100)){
			alert("발송인 명 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#buyerName").focus();
			return false;
		}
		if(abc.text.isBlank($("#buyerHdphnNoText").val())) {
			alert("발송인 연락처를 입력하세요.");
			$("#buyerHdphnNoText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#buyerHdphnNoText").val(), 11)){
			alert("발송인 연락처 최대 길이(11-영문1,한글2)를 초과하였습니다.");
			$("#buyerHdphnNoText").focus();
			return false;
		}
		
		// 수령지 정보 check
		if(abc.text.isBlank($("#rcvrPostCodeText").val())) {
			alert("재발송 수령지 우편번호를 입력하세요.");
			$("#rcvrPostCodeText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#rcvrPostCodeText").val(), 6)){
			alert("재발송 수령지 우편번호가 최대 길이(6)를 초과하였습니다.");
			$("#rcvrPostCodeText").focus();
			return false;
		}
		if(abc.text.isBlank($("#rcvrPostAddrText").val())) {
			alert("재발송 수령지 주소를 입력하세요.");
			$("#rcvrPostAddrText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#rcvrPostAddrText").val(), 200)){
			alert("재발송 수령지 주소가 최대 길이(200-영문1,한글2)를 초과하였습니다.");
			$("#rcvrPostAddrText").focus();
			return false;
		}
		if(abc.text.isBlank($("#rcvrDtlAddrText").val())) {
			alert("재발송 수령지 상세주소 입력하세요.");
			$("#rcvrDtlAddrText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#rcvrDtlAddrText").val(), 100)){
			alert("재발송 수령지 상세주소 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#rcvrDtlAddrText").focus();
			return false;
		}
		if(abc.text.isBlank($("#rcvrName").val())) {
			alert("재수령자 명을 입력하세요.");
			$("#rcvrName").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#rcvrName").val(), 100)){
			alert("재수령자 명 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#rcvrName").focus();
			return false;
		}
		if(abc.text.isBlank($("#rcvrHdphnNoText").val())) {
			alert("재수령자 연락처를 입력하세요.");
			$("#rcvrHdphnNoText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#rcvrHdphnNoText").val(), 11)){
			alert("재수령자 연락처 최대 길이(11-영문1,한글2)를 초과하였습니다.");
			$("#rcvrHdphnNoText").focus();
			return false;
		}
		
		// 교환배송비 check
		if(Number($("#claimProductForm select[name=addDlvyAmt]").val()) > 0) {
			if(abc.text.isUndefined($("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val())) {
				alert("교환배송비 결제수단을 선택하세요.");
				$("#claimProductForm input:radio[name=addDlvyAmtPymntType]").eq(0).focus();
				return false;
			}
			if($("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val() == _claim.consts.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON) {
				if(abc.text.isBlank($("#holdCpnSeq option:selected").val())) {
					alert("무료교환 쿠폰을 선택하세요.");
					$("#holdCpnSeq").focus();
					return false;
				}
			}
		}
		
		if(confirm("기본정보 및 배송비 정보를 저장하시겠습니까?")) {
			if(Number($("#claimProductForm select[name=addDlvyAmt]").val()) > 0) {
				if( abc.text.isBlank($("#claimProductForm input[name=clmPymntSeq]").val()) ) { // 교환배송비 결제 내역이 없는 경우
					$("#order_info input[name=requestDiv]").val("new"); // 결제요청구분(again:재발급, new:신규발급)
					$("#order_info input[name=good_name]").val("교환배송비"); // 결제상품명
					$("#order_info input[name=good_mny]").val($("#addDlvyAmt").val()); // 결제총금액(선택한 배송비로)
					
					jsf__pay( document.order_info ); // 결제창 호출(결제 리턴 받는 부분에서 클레임 저장 saveClaimDeliveryInfo() 호출)
				} else {
					_claim.saveClaimDeliveryInfo();
				}
			} else {
				_claim.saveClaimDeliveryInfo();
			}
		}
	};
	
	/**
	 * 접수기본정보 및 배송비 저장
	 */
	_claim.saveClaimDeliveryInfo = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		_claim.processIng = true;
		
		var request	= 
					$.ajax({
						type :"post",
						url : "/claim/claim/save-claim-exchange-delivery-info",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("저장에 성공했습니다.\n현재 화면을 새로고침 합니다.");
						//location.reload();
						_claim.selfOpenerReload();
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "저장 처리에 실패했습니다.");
						
						_claim.processIng = false;
					});
	};
	
	/**
	 * 2020.02.03 : 동봉 배송비 업데이트 - 심의완료 전까지만
	 */
	_claim.saveEclsDlvyAmt = function() {
		
		if(abc.text.allNull($("#eclsDlvyAmt").val())){
			alert('동봉 배송비를 입력해주세요.');
			$("#eclsDlvyAmt").focus();
			return false;
		}
		
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		_claim.processIng = true;
		
		var request	= 
					$.ajax({
						type :"post",
						url : "/claim/claim/save-ecls-dlvy-amt",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("저장에 성공했습니다.\n현재 화면을 새로고침 합니다.");
						//location.reload();
						_claim.selfOpenerReload();
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "저장 처리에 실패했습니다.");
						
						_claim.processIng = false;
					});
	};
	
	/**
	 * 교환배송비 가상계좌재발급 전처리 - 데이터 validate 및 배송비 결제인증(kcp) 처리
	 */
	_claim.requestVirtualAccountAgainPreProc = function() {
		if(confirm("가상계좌재발급 신청을 하시겠습니까?")) {
			$("#order_info input[name=requestDiv]").val("again"); // 결제요청구분(again:재발급, new:신규발급)
			$("#order_info input[name=good_name]").val("교환배송비"); // 결제상품명
			$("#order_info input[name=good_mny]").val($("#addDlvyAmt").val()); // 결제총금액(선택한 배송비로)
			
			jsf__pay( document.order_info ); // 결제창 호출(결제 리턴 받는 부분에서 클레임 저장 requestVirtualAccountAgain() 호출)
		}
	};
	
	/**
	 * 교환배송비 가상계좌재발급
	 */
	_claim.requestVirtualAccountAgain = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		_claim.processIng = true;
		
		var request	= 
					$.ajax({
						type :"post",
						url : "/claim/claim/request-virtual-account-again",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("가상계좌재발급 신청을 처리하였습니다.\n현재 화면을 새로고침 합니다.");
						//location.reload();
						_claim.selfOpenerReload();
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "가상계좌재발급 신청에 실패했습니다.");
						
						_claim.processIng = false;
					});
	};
	
	/**
	 * 교환배송비 결제취소/무료쿠폰 사용 복원
	 */
	_claim.cancelAddDeliveryAmt = function(div) {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		var confirmMsg = "";
		
		if(div == "pay") {
			confirmMsg = "교환배송비 결제취소를 하시겠습니까?";
		} else if(div == "coupon") {
			confirmMsg = "교환배송비가 무료로 변경됩니다.\n\n무료쿠폰 사용복원을 하시겠습니까?";
		}
		
		if(confirm(confirmMsg)) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/cancel-claim-delivery-amt",
							data : {
								clmNo : $("#claimProductForm input[name=clmNo]").val()
							},
							async : false
						})
						request.done(function(data){
							if(data.success == "Y") {
								alert("교환배송비 결제취소(무료변경)를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							} else {
								alert(data.message);
							}
							
							//location.reload();
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, error){
							console.log(jqXHR);
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "교환배송비 결제취소 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 클레임불가 처리 팝업
	 */
	_claim.registClaimImpossibilityFormPop = function(clmNo, clmPrdtSeq, orderNo, orderPrdtSeq) {
		abc.open.popup({
			winname :	"registClaimImpossibilityFormPop",
			url 	:	"/claim/claim/regist-claim-impossibility-form-pop",
			width 	:	1000,
			height	:	800,    
			params	:	{
				clmNo : clmNo,
				clmPrdtSeq : clmPrdtSeq,
				orderNo : orderNo,
				orderPrdtSeq : orderPrdtSeq
			}
		});
    };
	
	/**
	 * 클레임불가 상세 팝업
	 */
	_claim.readClaimImpossibilityDetailPop = function(clmNo, clmPrdtSeq, orderNo, orderPrdtSeq) {
		abc.open.popup({
			winname :	"readClaimImpossibilityDetailPop",
			url 	:	"/claim/claim/read-claim-impossibility-detail-pop",
			width 	:	1000,
			height	:	800,
			params	:	{
				clmNo : clmNo,
				clmPrdtSeq : clmPrdtSeq,
				orderNo : orderNo,
				orderPrdtSeq : orderPrdtSeq
			}
		});
    };
    
	/**
	 * 클레임 이력 팝업 호출
	 */
	_claim.openClaimHistoryPop = function(obj) {
		abc.open.popup({
			winname :	"claimHistoryPop",
			url 	:	"/claim/claim/claim-history-pop",
			width 	:	800,
			height	:	700,
			params	:	{
				clmNo : $(obj).siblings("#historyClmNo").val(),
				clmPrdtSeq : $(obj).siblings("#historyClmPrdtSeq").val()
			}
		});
	}
    
    /**
	 * 클레임 메모 목록 조회
	 */
	_claim.getClaimMemoList = function() {
		$("#claimMemoListArea").empty();

		var request	= 
					$.ajax({
						type :"post",
						url : "/claim/claim/read-claim-memo-list",
						data : {
							clmNo : $("#claimProductForm input[name=clmNo]").val(),
							clmGbnCode : $("#claimProductForm input[name=clmGbnCode]").val()
						},
						dataType : "html"
					})
					request.done(function(data){
						$("#claimMemoListArea").html(data);
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "클레임 메모 목록 조회에 실패했습니다.");
					});
	};
	
    /**
	 * 클레임 메모 등록
	 */
	_claim.registClaimMemo = function() {
		if(abc.text.isBlank($("#memoText").val())) {
			alert("관리자 메모를 입력하세요.");
			$("#memoText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#memoText").val(), 1000)){
			alert("관리자 메모 최대 길이(1000-영문1,한글2)를 초과하였습니다.");
			$("#memoText").focus();
			return false;
		}
		
		if(confirm("관리자 메모를 등록하시겠습니까?")) {
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/create-claim-memo",
							data : $("#claimMemoForm").serialize(),
							dataType : "html"
						})
						request.done(function(data){
							$("#memoText").val("");
							_claim.getClaimMemoList();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "클레임 메모 등록에 실패했습니다.");
						});
		}
    };
    
    /**
	 * 클레임 메모 삭제
	 */
	_claim.deleteClaimMemo = function(idx) {
		if(confirm("관리자 메모를 삭제하시겠습니까?")) {
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/delete-claim-memo",
							data : {
								clmNo : $("#claimMemoForm input[name=clmNo]").eq(idx).val(),
								clmMemoSeq : $("#claimMemoForm input[name=clmMemoSeq]").eq(idx).val()
							},
							dataType : "html"
						})
						request.done(function(data){
							_claim.getClaimMemoList();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "클레임 메모 삭제에 실패했습니다.");
						});
		}
    };
    
    /**
     * 관리자 팝업
     */
    _claim.getAdminPop = function(){
    	
    	abc.adminDetailPopup($("#rgsterNo").val());
    }
    
    /**
     * 회원 팝업
     */
    _claim.getMemberPop = function(){
    	
    	abc.MemberDetailPopup($("#rgsterNo").val());
    }
    
    /**
     * 주문상세 팝업
     */
    _claim.getOrderDetail = function(){
    	
    	var url = "/order/read-detail-pop";
		var params = {
				orderNo : $("input[name='orgOrderNo']").val()
		}
		
		abc.open.popup({
			winname :	"주문 상세 팝업",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
    }
    
    /**
     * 상품상세 팝업
     */
    _claim.getProductDetail = function(el){
    	
    	var param = { prdtNo : $(el).attr("prdtno") };
		abc.readonlyProductDetailPopup(param);
    }
    
    /**
     * 계좌인증
     */
    _claim.accountCertify = function(el){
    	if( abc.text.isBlank($("#bankCode").val()) ) {
			alert("은행을 선택하세요.");
			$("#bankCode").focus();
			return false;
		}
    	if( abc.text.isBlank($("#rfndAcntText").val()) ) {
			alert("계좌번호를 입력하세요.");
			$("#rfndAcntText").focus();
			return false;
		}
    	if( abc.text.isBlank($("#acntHldrName").val()) ) {
			alert("예금주를 입력하세요.");
			$("#acntHldrName").focus();
			return false;
		}
    	
		$.ajax({
			type	 : "post",
			url		 : "/claim/claim/claim-account-auth",
			async 	 : false,
			dataType : "json",
			data	 : {
						clmNo : $("#claimProductForm input[name=clmNo]").val()
					   ,bankCode : $("#bankCode").val()
					   ,acntNoText : $("#rfndAcntText").val()
					   ,acntHldrName : $("#acntHldrName").val()
				       }
		})
		.done(function(data, textStatus, jqXHR){
			var flag 	= data.flag;
			
			if(flag == 'Y'){
				if(confirm("인증되었습니다.\n\n계좌정보를 저장하시겠습니까?")) {
					_claim.saveRefundAccount(); // 환불계좌 저장
				} else {
					return;
				}
			}else{
				alert("계좌 인증에 실패하였습니다.");
			}
		})
		.fail(function(jqXHR, textStatus, error){
			alert("에러가 발생했습니다.\n" + jqXHR.responseJSON.message);
		});
    }
    
    /**
     * 환불계좌 저장
     */
    _claim.saveRefundAccount = function(el){
		$.ajax({
			type	 : "post",
			url		 : "/claim/claim/save-refund-account-info",
			async 	 : false,
			data	 : {
						clmNo : $("#claimProductForm input[name=clmNo]").val()
					   ,bankCode : $("#bankCode").val()
					   ,rfndAcntText : $("#rfndAcntText").val()
					   ,acntHldrName : $("#acntHldrName").val()
				       }
		})
		.done(function(data){
			alert("환불계좌가 저장되었습니다.");
			//location.reload();
			_claim.selfOpenerReload();
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "에러가 발생했습니다.");
		});
    }
    
    /**
     * 화면 갱신(현재 창)
     */
    _claim.selfReload = function(){
    	location.reload();
    }
    
    /**
     * 화면 갱신(opener 포함)
     */
    _claim.selfOpenerReload = function(){
    	location.reload();
    	
		if(window.opener != null) {
			if (typeof(window.opener.reloadGridList) == "function" || typeof(window.opener.reloadGridList) == "object") {
				opener.reloadGridList();
			} else {
				opener.location.reload();
			}
		}
    }
    
	/**
	 * BO 상품상세 팝업
	 */
    _claim.prdtBoPop = function(el){	
		var param = { prdtNo : $(el).attr("prdtno") };
		abc.readonlyProductDetailPopup(param);
	 }
    
	/**
	 * FO 상품상세 
	 */
    _claim.prdtFo = function(el){	
		if($("#siteNo").val() == _claim.SITE_NO_ART){
			window.open(_claim.ABC_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno"))
		} else {
			window.open(_claim.OTS_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno"))
		}	
	 }
    
    /**
	 * BO 업체상세 팝업
	 */
    _claim.vndrBoPop = function(el){	
		abc.vendorInfoPop( $(el).attr("vndrno") );
	 }
    
    /**
     * 배송조회 팝업
     */
    _claim.logisVndrPop = function(el){
    	var clmPrdtSeq = $(el).attr("clmprdtseq");
		var logisVndrCodeAddInfo = $("input[name='changeLogisVndrCodeAddInfo'][clmprdtseq='" + clmPrdtSeq + "']").val();
		var waybilNoText = $("input[name='changeWaybilNoText'][clmprdtseq='" + clmPrdtSeq + "']").val();
		
		//alert(logisVndrCodeAddInfo + " / " + clmPrdtSeq + " / " + waybilNoText);
		abc.dlvyInfoInquiry(logisVndrCodeAddInfo, waybilNoText);
    }
    
    /**
     * 서버쪽 validation 메세지와 일반 에러메세지를 구분하여 알러트 발생
     */
    _claim.ajaxErrorAlert = function(xhrJsonMsg, commonMsg) {
    	if(xhrJsonMsg != undefined) {
			if(/validMsg:/.test(xhrJsonMsg)) {
				alert(xhrJsonMsg.replace( /validMsg:/gi, ''));
			} else {
				alert(commonMsg);
			}
		} else {
			alert(commonMsg);
		}
    };
    
    /**
    * 우편번호 세팅
    */
    _claim.setPostCode = function(data){
		$("#rcvrPostCodeText").val(data.postCode);
		$("#rcvrPostAddrText").val(data.postAddress);
//		$("#buildingCode").val(data.buildingCode); 건물번호는 필요시 사용한다. (건물에 부여한 25자리 고유번호)	

		$("#rcvrDtlAddrText").focus();
	}
    
})();