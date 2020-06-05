/***
 * 클레임 교환상세와 관련된 함수 정의.
 */
(function() {
	
	var _claim = abc.object.createNestedObject(window,"abc.biz.claim.exchange.create");
	
	_claim.processIng = false;
	
	/**
	 * Const value(jsp에서 java const 지정 값 호출하여 set)
	 */
	_claim.consts = {
		ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT : "" // 추가배송비결제방법 : 추가결제
	   ,ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON : "" // 추가배송비결제방법 : 무료쿠폰
	   ,CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE : "" // 클레임 사유 코드(교환) : 옵션변경
	}
	
	_claim.SITE_NO_ART	= "";
	_claim.SITE_NO_OTS	= "";
	
	_claim.ABC_FO_URL	= "";
	_claim.OTS_FO_URL	= "";
	
	/**
	 * 일괄적용 처리용 콤보박스 변경
	 */
	_claim.changeComboForBatchApply = function() {
		if($("#codeDivSelect option:selected").val() == "customerNoticeOpts"){ // 고객알림내용
			$("#inputReasonText").val("");
			$("#inputReasonText").attr("placeholder", "내용");
			$("#clmRsnCodeSelect").hide();
		} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 교환사유
			$("#inputReasonText").val("");
			$("#inputReasonText").attr("placeholder", "사유");
			$("#clmRsnCodeSelect").show();
		}
	};
	
	/**
	 * 일괄적용
	 */
	_claim.batchApplyProduct = function() {
		// 입력 폼 check
		if($("#codeDivSelect option:selected").val() == "customerNoticeOpts"){ // 고객알림내용
			if(abc.text.isBlank($("#inputReasonText").val())) {
				alert("고객알림 내용을 입력하세요.");
				$("#inputReasonText").focus();
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
	 * 클레임 교환상품 등록 전처리 - 데이터 validate 및 배송비 결제인증(kcp) 처리
	 */
	_claim.registClaimExchangePreProc = function() {
		if(!_claim.registFormValidate()) { // 클레임 교환상품 등록 form validate
			return false;
		};
		
		var confirmMsg = "교환을 접수하시겠습니까?";
		var addDlvyAmt = $("#claimProductForm select[name=addDlvyAmt]").val();
		
		if(Number(addDlvyAmt) == 0) {
			confirmMsg = "교환배송비를 무료로 선택하셨습니다.\n결제없이 이대로 교환을 접수하시겠습니까?"
		}
		
		if(confirm(confirmMsg)) {
			var addDlvyAmtPymntType = $("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val();
			var request	=
						$.ajax({
							type :"post",
							url : "/claim/claim/validate-claim-exchange-product", // 클레임 교환 등록 validate
							data : $("#claimProductForm").serialize()
						})
						request.done(function(data){
							//console.log(data);
							
							if(Number(addDlvyAmt) > 0) { // 교환배송비가 발생한 경우
								if(addDlvyAmtPymntType == _claim.consts.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT) { // 배송비 가상계좌 결제 필요
									$("#order_info input[name=good_name]").val("교환배송비"); // 결제상품명
									$("#order_info input[name=good_mny]").val($("#addDlvyAmt").val()); // 결제총금액(선택한 배송비로)
									
									jsf__pay( document.order_info ); // 결제창 호출(결제 리턴 받는 부분에서 클레임 저장 registClaimExchange() 호출)
								} else { // 무료쿠폰인 경우
									_claim.registClaimExchange();
								}
							} else { // 교환배송비가 발생하지 않은 경우
								_claim.registClaimExchange();
							}
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수 처리에 실패했습니다.");
						});
		}
	};
	
	/**
	 * 클레임 교환상품 등록
	 */
	_claim.registClaimExchange = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		_claim.processIng = true;
		
		var request	=
					$.ajax({
						type :"post",
						url : "/claim/claim/regist-claim-exchange",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("저장에 성공했습니다.\n\n상세페이지로 전환합니다.");
						_claim.openerReload();
						$(location).attr('href', "/claim/claim/read-claim-exchange-detail-pop?clmNo="+data.clmNo);
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수 처리에 실패했습니다.");

						_claim.processIng = false;
					});
	};
	
	/**
	 * 클레임 교환상품 등록 form validate
	 */
	_claim.registFormValidate = function() {
		// 클레임 상품 check
		var loopCheck = true;
		$("#claimProductForm input[name=orderPrdtSeq]").each(function(idx, rowItem) {
			if( abc.text.isBlank($("#claimProductForm select[name=clmRsnCode]").eq(idx).val()) ) {
				alert("교환 유형을 선택하세요.");
				$("#claimProductForm select[name=clmRsnCode]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
			if( abc.text.isBlank($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val()) ) {
				alert("교환 사유를 입력하세요.");
				$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
			if( $("#claimProductForm select[name=clmRsnCode]").eq(idx).val() == _claim.consts.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE ) {
				if(abc.text.isBlank($("#claimProductForm input[name=changePrdtOptnNo]").eq(idx).val())) {
					alert("교환 유형이 옵션변경인 경우에는 옵션을 변경하셔야 합니다.");
					$("#claimProductForm textarea[name=changeOptnBtn]").eq(idx).focus();
					loopCheck = false;
					return false;
				}
			}
			if(abc.text.isLimitLength($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val(), 100)){
				alert("교환 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
			if(abc.text.isLimitLength($("#claimProductForm textarea[name=cstmrAlertContText]").eq(idx).val(), 100)) {
				alert("고객 알림내용 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#claimProductForm textarea[name=cstmrAlertContText]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
		});
		if(!loopCheck) return false;
		
		
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
			$("input[name='"+targetCheckBoxName+"']").prop("checked", true);
		} else {
			$("input[name='"+targetCheckBoxName+"']").prop("checked", false);
		}
	};
	
	/**
	 * 접수 초기 회수지, 재발송지 set(주문정보 배송지 이용)
	 */
	_claim.setDefaultAddrInfo = function() {
		// 회수지 정보
		$("#buyerName").val($("#order_rcvrName").val());
		$("#buyerHdphnNoText").val($("#order_rcvrHdphnNoText").val());
		$("#buyerPostCodeText").val($("#order_rcvrPostCodeText").val());
		$("#buyerPostAddrText").val($("#order_rcvrPostAddrText").val());
		$("#buyerDtlAddrText").val($("#order_rcvrDtlAddrText").val());
		
		// 재발송지 정보
		$("#rcvrName").val($("#order_rcvrName").val());
		$("#rcvrHdphnNoText").val($("#order_rcvrHdphnNoText").val());
		$("#rcvrPostCodeText").val($("#order_rcvrPostCodeText").val());
		$("#rcvrPostAddrText").val($("#order_rcvrPostAddrText").val());
		$("#rcvrDtlAddrText").val($("#order_rcvrDtlAddrText").val());
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
     * 화면 갱신(opener)
     */
    _claim.openerReload = function(){
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
	 * BO 관리자 상세 
	 */
    _claim.adminBoPop = function(el){	
    	abc.adminDetailPopup($(el).attr("adminno"));
	 }
    
    /**
	 * BO 주문 상세 
	 */
    _claim.orderBoPop = function(el){	
    	var url = "/order/read-detail-pop";
		var params = {}
		params.orderNo = $(el).attr("orderno");

		abc.open.popup({
			winname :	"주문 상세 팝업",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	 }
    
    /**
	 * BO 업체상세 팝업
	 */
    _claim.vndrBoPop = function(el){	
		abc.vendorInfoPop( $(el).attr("vndrno") );
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
    
})();