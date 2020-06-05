/***
 * 클레임 취소 상세와 관련된 함수 정의.
 */
(function() {
	
	var _claim = abc.object.createNestedObject(window,"abc.biz.claim.cancel.detail");
	
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
	 * 클레임 취소상품 저장(사유 저장)
	 */
	_claim.saveClaimProduct = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
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
		
		if(confirm("저장하시겠습니까?")) {
			_claim.processIng = true;
			
			// 취소유형 및 선택사유 클레임대상 상품 정보에 각각 set
			$("#claimProductForm input[name=clmPrdtSeq]").each(function(idx, rowItem) {
				$("#claimProductForm input[name=clmRsnCode]").eq(idx).val($("#clmRsnCodeSelect").val());
				$("#claimProductForm input[name=clmEtcRsnText]").eq(idx).val($("#clmEtcRsnTextInput").val());
			});
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/save-claim-cancel-product",
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
							url : "/claim/claim/update-claim-cancel-wthdraw",
							data : $("#claimWthdrawForm").serialize(),
						})
						request.done(function(data){
							alert("접수철회를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
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
	 * 취소완료
	 */
	_claim.finishClaimAllProc = function() {
		if(_claim.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(!_claim.validateCnclAmt()) { //  결제수단별 취소금 validate
			return false;
		};
		
		if(confirm("취소완료 처리를 하시겠습니까?")) {
			_claim.processIng = true;
			
			var request	= 
						$.ajax({
							type :"post",
							url : "/claim/claim/claim-cancel-proc",
							data : $("#claimProductForm").serialize(),
							async : false
						})
						request.done(function(data){
							if(data.success == "Y") {
								alert("취소완료 처리를 처리하였습니다.\n현재 화면을 새로고침 합니다.");
							} else {
								alert(data.message);
							}
							
							_claim.selfOpenerReload();
						})
						request.fail(function(jqXHR, textStatus, errorThrown){
							console.log(jqXHR);
							_claim.ajaxErrorAlert(jqXHR.responseJSON.message, "취소완료 처리에 실패했습니다.");
							
							_claim.processIng = false;
						});
		}
	};
	
	/**
	 * 결제수단별 취소금 validate
	 */
	_claim.validateCnclAmt = function() {
		var chkAmt = true;
		var chngInputCnclAmtYn = "N";
		var cnclPymntAmtSum = 0;
		
		$("#claimProductForm input[name='pymntAmt']").each(function(idx, rowItem) {
			if( abc.text.isBlank($('#claimProductForm input[name=pymntAmt]').eq(idx).val()) ) {
				$('#claimProductForm input[name=pymntAmt]').eq(idx).val("0");
			}
		});
		
		$("#claimProductForm input[name='pymntAmt']").each(function(idx, rowItem) {
			if(Number($('#claimProductForm input[name=pymntAmt]').eq(idx).val()) > Number($('#claimProductForm input[name=remainPymntCnclAmt]').eq(idx).val())) {
				alert('결제수단 별 취소금은 동일 결제수단의 잔여 금액보다 클 수 없습니다.');
				$('#claimProductForm input[name=pymntAmt]').eq(idx).focus();
				chkAmt = false;
				return false;
			}
			cnclPymntAmtSum += Number($('#claimProductForm input[name=pymntAmt]').eq(idx).val());
		});
		
		if(!chkAmt) {
			return false;
		}
		
		if(cnclPymntAmtSum != Number($("#refundAllAmt").val())) {
			alert('입력한 취소 설정금액은 실제 환불금액(' + abc.text.isMakeComma($("#refundAllAmt").val()) + '원)과 같아야 합니다.');
			chkAmt = false;
		}
		
		if(!chkAmt) {
			return false;
		}
		
		// 금액 체크 후 입력폼 취소금 변경 여부 set
		$("#claimProductForm input[name='pymntAmt']").each(function(idx, rowItem) {
			if(Number($('#claimProductForm input[name=pymntAmt]').eq(idx).val()) != Number($('#claimProductForm input[name=orgPymntAmt]').eq(idx).val())) {
				chngInputCnclAmtYn = "Y";
				return false;
			}
		});
		
		$('#chngInputCnclAmtYn').val(chngInputCnclAmtYn);
		
		return true;
	}
	
	/**
	 * 취소 설정금액 초기화
	 */
	_claim.resetCnclAmt = function() {
		$("#claimProductForm input[name='pymntAmt']").each(function(idx, rowItem) {
			$('#claimProductForm input[name=pymntAmt]').eq(idx).val($('#claimProductForm input[name=orgPymntAmt]').eq(idx).val());
		});
	}
	
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