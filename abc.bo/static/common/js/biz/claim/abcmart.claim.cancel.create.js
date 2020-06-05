/***
 * 클레임 취소 접수와 관련된 함수 정의.
 */
(function() {
	
	var _claim = abc.object.createNestedObject(window,"abc.biz.claim.cancel.create");
	
	_claim.processIng = false;
	
	/**
	 * Const value(jsp에서 java const 지정 값 호출하여 set)
	 */
	_claim.consts = {
		CLM_RSN_CODE_CANCEL_ETC : "" // 클레임 사유 코드(취소) : 기타
	}
	
	_claim.SITE_NO_ART	= "";
	_claim.SITE_NO_OTS	= "";
	
	_claim.ABC_FO_URL	= "";
	_claim.OTS_FO_URL	= "";
	
	/**
	 * 취소유형에 따른 사유 폼 제어
	 */
	_claim.setClmEtcRsnTextInputDisable = function() {
		if($("#clmRsnCodeSelect option:selected").val() == _claim.consts.CLM_RSN_CODE_CANCEL_ETC) { // 기타 사유
			$("#clmEtcRsnTextInput").prop("disabled", false);
		} else {
			$("#clmEtcRsnTextInput").prop("disabled", true);
			$("#clmEtcRsnTextInput").val("");
		}
	};
	
	/**
	 * 클레임 취소상품 등록 전처리 - 데이터 validate 및 배송비 결제인증(kcp) 처리
	 */
	_claim.registClaimCancelPreProc = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(!_claim.registFormValidate()) { // 클레임 취소상품 등록 form validate
			return false;
		};
		
		// 2020.03.30 : 주문상품 취소접수 후 남은 주문상품의 주문금액이 20,000원 미만일 경우 환수배송비가 발생할 수 있는데
		//              현재 '품절' 사유는 환수배송비가 발생하지 않게 추가 개발되어 있다.
		// 사이트 무료배송 기준금액
		var freeDlvyStdrAmt = Number($("#freeDlvyStdrAmt").val());
		// 주문의 총 주문금
		var totalOrderAmt = Number($("#totalOrderAmt").val());
		// 이미 취소된 주문금
		var alreadyCancelAmt = Number($("#alreadyCancelAmt").val());
		// 취소할 금액
		var cancelAmt = 0;
		// 전체취소/부분취소 여부를 가리기위해 사은품/배송비를 제외한 전체주문상품 목록 갯수
		var onlyOrderProductCnt = Number($("#onlyOrderProductCnt").val());
		// 취소할 상품 갯수
		var cancelPrdtCnt = 0;
		$("input[name='prdtOrderAmt']").each(function(){
			// 취소할 상품 주문금 sum
			cancelAmt = cancelAmt + Number($(this).val());
			// 취소할 상품 갯수 ++
			cancelPrdtCnt++;
		});
		console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
		console.log("cancelPrdtCnt : " + cancelPrdtCnt);
		// 부분취소이고 기존 취소금이 없고 총 주문금에서 취소하는 상품 금액을 뺀 금액이 무료배송기준금액보다 작으면
		if( onlyOrderProductCnt > cancelPrdtCnt && alreadyCancelAmt == 0 && freeDlvyStdrAmt > (totalOrderAmt - cancelAmt) ){
			if( $("#clmRsnCodeSelect").val() != "10002" ){
				alert("'품절'이외의 사유로 취소접수 시, 추가배송비가 발생할 수 있습니다.");
			}
		}
		
		var confirmMsg = "취소를 접수하시겠습니까?";
		
		if(confirm(confirmMsg)) {
			_claim.processIng = true;
			
			// 취소유형 및 선택사유 클레임대상 상품 정보에 각각 set
			$("#claimProductForm input[name=orderPrdtSeq]").each(function(idx, rowItem) {
				$("#claimProductForm input[name=clmRsnCode]").eq(idx).val($("#clmRsnCodeSelect").val());
				$("#claimProductForm input[name=clmEtcRsnText]").eq(idx).val($("#clmEtcRsnTextInput").val());
			});
			
			var request	=
						$.ajax({
							type :"post",
							url : "/claim/claim/validate-claim-cancel-product", // 클레임 취소 등록 validate
							data : $("#claimProductForm").serialize()
						})
						request.done(function(data){
							_claim.registClaimCancel();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수 처리에 실패했습니다.");
						});
		}
	};
	
	/**
	 * 클레임 취소상품 등록
	 */
	_claim.registClaimCancel = function() {
		var request	=
					$.ajax({
						type :"post",
						url : "/claim/claim/regist-claim-cancel",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("저장에 성공했습니다.\n\n상세페이지로 전환합니다.");
						_claim.openerReload();
						$(location).attr('href', "/claim/claim/read-claim-cancel-detail-pop?clmNo="+data.clmNo);
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수 처리에 실패했습니다.");
												
						_claim.processIng = false;
					});
	};
	
	/**
	 * 클레임 취소상품 등록 form validate
	 */
	_claim.registFormValidate = function() {
		if( abc.text.isBlank($("#claimProductForm select[name=clmRsnCodeSelect]").val()) ) {
			alert("취소 유형을 선택하세요.");
			$("#claimProductForm select[name=clmRsnCodeSelect]").focus();
			return false;
		}
		if($("#clmRsnCodeSelect option:selected").val() == _claim.consts.CLM_RSN_CODE_CANCEL_ETC) { // 기타 사유
			if( abc.text.isBlank($("#claimProductForm input[name=clmEtcRsnTextInput]").val()) ) {
				alert("취소 사유를 입력하세요.");
				$("#claimProductForm input[name=clmEtcRsnTextInput]").focus();
				return false;
			}
		}
		if(abc.text.isLimitLength($("#claimProductForm input[name=clmEtcRsnTextInput]").val(), 100)){
			alert("취소 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
			$("#claimProductForm input[name=clmEtcRsnTextInput]").focus();
			return false;
		}
		
		return true;
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