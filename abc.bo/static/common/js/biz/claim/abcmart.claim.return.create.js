/***
 * 클레임 반품상세와 관련된 함수 정의.
 */
(function() {
	
	var _claim = abc.object.createNestedObject(window,"abc.biz.claim.create");
	
	_claim.processIng = false;
	
	/**
	 * Const value(jsp에서 java const 지정 값 호출하여 set)
	 */
	_claim.consts = {
		ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT : "" // 추가배송비결제방법 : 추가결제
	   ,ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON : "" // 추가배송비결제방법 : 무료쿠폰
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
		} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 반품사유
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
		} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 반품사유
			if(abc.text.isBlank($("#clmRsnCodeSelect option:selected").val())) {
				alert("반품 유형을 선택하세요.");
				$("#clmRsnCodeSelect").focus();
				return false;
			}
			if(abc.text.isBlank($("#inputReasonText").val())) {
				alert("반품 사유를 입력하세요.");
				$("#inputReasonText").focus();
				return false;
			}
			if(abc.text.isLimitLength($("#inputReasonText").val(), 100)){
				alert("반품 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
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
			} else if($("#codeDivSelect option:selected").val() == "clmRsnCodeOpts"){ // 반품유형, 사유
				$("#claimProductForm select[name=clmRsnCode]").each(function(idx, rowItem) {
					if($('#claimProductForm input[name=chkTarget]').eq(idx).prop("checked")) { // 체크 한것만
						$("#claimProductForm select[name=clmRsnCode]").eq(idx).val($("#clmRsnCodeSelect").val());
						$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val($("#inputReasonText").val());
					}
				});
			}
		}
		
	};
	
	/**
	 * 클레임 반품상품 등록 전처리 - 데이터 validate 및 배송비 결제인증(kcp) 처리
	 */
	_claim.registClaimReturnPreProc = function() {
		if(!_claim.registFormValidate()) { // 클레임 반품상품 등록 form validate
			return false;
		};
		
		var confirmMsg = "반품을 접수하시겠습니까?";
		var addDlvyAmt = $("#claimProductForm select[name=addDlvyAmt]").val();
		
		if(Number(addDlvyAmt) == 0) {
			confirmMsg = "반품배송비를 무료로 선택하셨습니다.\n결제없이 이대로 반품을 접수하시겠습니까?"
		}
		
		if(confirm(confirmMsg)) {
			// 수령지 정보를 회수지 정보로 set
			$("#rcvrPostCodeText").val($("#buyerPostCodeText").val());
			$("#rcvrPostAddrText").val($("#buyerPostAddrText").val());
			$("#rcvrDtlAddrText").val($("#buyerDtlAddrText").val());
			$("#rcvrName").val($("#buyerName").val());
			$("#rcvrHdphnNoText").val($("#buyerHdphnNoText").val());
			
			var addDlvyAmtPymntType = $("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val();
			
			if(Number(addDlvyAmt) > 0) { // 반품배송비가 발생한 경우
				if(addDlvyAmtPymntType == _claim.consts.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT) { // 배송비 가상계좌 결제 필요
					$("#order_info input[name=good_name]").val("반품배송비"); // 결제상품명
					$("#order_info input[name=good_mny]").val($("#addDlvyAmt").val()); // 결제총금액(선택한 배송비로)
					
					jsf__pay( document.order_info ); // 결제창 호출(결제 리턴 받는 부분에서 클레임 저장 registClaimReturn() 호출)
				} else { // 무료쿠폰인 경우
					_claim.registClaimReturn();
				}
			} else { // 반품배송비가 발생하지 않은 경우
				_claim.registClaimReturn();
			}
		}
	};
	
	/**
	 * 클레임 반품상품 등록
	 */
	_claim.registClaimReturn = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		_claim.processIng = true;
		
		var request	=
					$.ajax({
						type :"post",
						url : "/claim/claim/regist-claim-return",
						data : $("#claimProductForm").serialize()
					})
					request.done(function(data){
						alert("저장에 성공했습니다.\n\n상세페이지로 전환합니다.");
						_claim.openerReload();
						$(location).attr('href', "/claim/claim/read-claim-return-detail-pop?clmNo="+data.clmNo);
					})
					request.fail(function(jqXHR, textStatus, errorThrown){
						_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "접수 처리에 실패했습니다.");
						
						_claim.processIng = false;
					});
	};
	
	/**
	 * 클레임 반품상품 등록 form validate
	 */
	_claim.registFormValidate = function() {
		// 클레임 상품 check
		var loopCheck = true;
		$("#claimProductForm input[name=orderPrdtSeq]").each(function(idx, rowItem) {
			if( abc.text.isBlank($("#claimProductForm select[name=clmRsnCode]").eq(idx).val()) ) {
				alert("반품 유형을 선택하세요.");
				$("#claimProductForm select[name=clmRsnCode]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
			if( abc.text.isBlank($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val()) ) {
				alert("반품 사유를 입력하세요.");
				$("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).focus();
				loopCheck = false;
				return false;
			}
			if(abc.text.isLimitLength($("#claimProductForm textarea[name=clmEtcRsnText]").eq(idx).val(), 100)){
				alert("반품 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
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
		
		// 2020.03.25 : 상품 사유 중 하나라도 '오프라인 반품'이라면 상품 모두 '오프라인 반품' 사유여야 한다.
		// 2020.04.02 : 상품 사유 중 하나라도 '미출반품'이라면 상품 모두 '미출반품' 사유여야 한다.
		var onlineClmRsnCheck = true;
		var normalReturnClmRsnCheck = true;
		var orderPrdtCnt = 0;
		$("#claimProductForm select[name=clmRsnCode]").each(function() {
			orderPrdtCnt++;
			if( $(this).val() == "10019" ){ // 한 상품이라도 '오프라인반품'이면
				onlineClmRsnCheck = false;
				console.log("onlineClmRsnCheck : " + onlineClmRsnCheck);
			}
			if( $(this).val() == "10018" ){ // 한 상품이라도 '미출반품'이면
				normalReturnClmRsnCheck = false;
				console.log("normalReturnClmRsnCheck : " + normalReturnClmRsnCheck);
			}
		});
		// 반품접수 주문상품이 2개 이상일때 한 상품이라도 '오프라인반품'이면
		if( orderPrdtCnt > 1 && onlineClmRsnCheck == false ){
			var allClmRsnIsOffline = true;
			$("#claimProductForm select[name=clmRsnCode]").each(function() {
				// 그 외의 모든 상품 사유가 '오프라인반품'인지 아닌지 검사
				if( $(this).val() != "10019" ){ // 한 상품이라도 '오프라인반품'이 아니면
					allClmRsnIsOffline = false;
					console.log("allClmRsnIsOffline : " + allClmRsnIsOffline);
				}
			});
			if( allClmRsnIsOffline == false ){
				alert("모든 주문상품의 반품사유가 '오프라인반품'이 아니면 오프라인 반품접수 불가능합니다.");
				return false;
			}
		}
		// 반품접수 주문상품이 2개 이상일때 한 상품이라도 '미출반품'이면
		if( orderPrdtCnt > 1 && normalReturnClmRsnCheck == false ){
			var allClmRsnIsLoss = true;
			$("#claimProductForm select[name=clmRsnCode]").each(function() {
				// 그 외의 모든 상품 사유가 '미출반품'인지 아닌지 검사
				if( $(this).val() != "10018" ){ // 한 상품이라도 '미출반품'이 아니면
					allClmRsnIsLoss = false;
					console.log("allClmRsnIsLoss : " + allClmRsnIsLoss);
				}
			});
			if( allClmRsnIsLoss == false ){
				alert("모든 주문상품의 반품사유가 '미출반품'이 아니면 미출반품 반품접수 불가능합니다.");
				return false;
			}
		}
		
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
		
		// 반품배송비 check
		if(Number($("#claimProductForm select[name=addDlvyAmt]").val()) > 0) {
			if(abc.text.isUndefined($("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val())) {
				alert("반품배송비 결제수단을 선택하세요.");
				$("#claimProductForm input:radio[name=addDlvyAmtPymntType]").eq(0).focus();
				return false;
			}
			if($("#claimProductForm input:radio[name=addDlvyAmtPymntType]:checked").val() == _claim.consts.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON) {
				if(abc.text.isBlank($("#holdCpnSeq option:selected").val())) {
					alert("무료반품 쿠폰을 선택하세요.");
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
	 * 접수 초기 회수지 기본정보 set(주문정보 배송지 이용)
	 */
	_claim.setDefaultAddrInfo = function() {
		// 회수지 정보
		$("#buyerName").val($("#order_rcvrName").val());
		$("#buyerHdphnNoText").val($("#order_rcvrHdphnNoText").val());
		$("#buyerPostCodeText").val($("#order_rcvrPostCodeText").val());
		$("#buyerPostAddrText").val($("#order_rcvrPostAddrText").val());
		$("#buyerDtlAddrText").val($("#order_rcvrDtlAddrText").val());
	};
	
	/**
	 * 배송주소와 동일 체크박스 클릭
	 */
	_claim.toggleSameDelivery = function(object) {
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
	};
	
	/**
	 * 배송비 환불금차감, 선결제 선택 시 폼 제어
	 */
	_claim.toggleDeliveryAmtForm = function(object) {
		if(object.id == "radioPayment01") { // 환불금차감
			if($(object).is(":checked")) {
				$("#radioPayment02").prop("checked", false);
				$("#radioPickup01").prop("checked", false);
				$("#radioPickup01").prop("disabled", true);
				$("#radioPickup02").prop("disabled", true);
				$("#holdCpnSeq").prop("disabled", true);
			}
		} else if(object.id == "radioPayment02") { // 선결제
			if($(object).is(":checked")) {
				$("#radioPickup01").prop("checked", true);
				$("#radioPickup01").prop("disabled", false);
				$("#radioPickup02").prop("disabled", false);
				$("#holdCpnSeq").prop("disabled", false);
			}
		}
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
    
    /*
     * 2020.05.07: 주문상세에서 넘어오는 비회원 AS반풉접수 여부를 따져 화면 set func
     */
    _claim.asReturn = function(){
    	var asReturnYn = $("#asReturnYn").val();
    	if( asReturnYn == "N" ){ // 비회원전용 AS반풉접수가 아니면 그냥 return;
    		return;
    	}
    	
    	$("#codeDivSelect").attr("readonly", "readonly");
    	$("#clmRsnCodeSelect").attr("readonly", "readonly");
    	$("#inputReasonText").attr("readonly", "readonly");
    	$("#batchApplyBtn").remove();
    	$("#returnNotice").text("※ 비회원 AS반품접수 시, '심의완료' 상태로 반품접수 됩니다.")
    	
    	$("select[name='clmRsnCode']").val("10021");
    	$("select[name='clmRsnCode']").attr("readonly", "readonly");
    	$("textarea[name='clmEtcRsnText']").val("A/S 불가");
    	$("textarea[name='clmEtcRsnText']").attr("readonly", "readonly");
    	
    	$("#addDlvyAmt").val("0");
    	$("#addDlvyAmt > option[value='2500']").remove();
    	$("#addDlvyAmt > option[value='5000']").remove();
    	$("#addDlvyAmt").attr("readonly", "readonly");
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