
(function() {
	
	var _commonpop = abc.object.createNestedObject(window,"abc.biz.afterservice.commonpop");
	
	_commonpop.ABC_FO_URL = "";
	_commonpop.OTS_FO_URL = "";
	
	_commonpop.SITE_NO_ART = "";
	_commonpop.SITE_NO_OTS = "";
	
	/**
	 * 우편번호 pop 
	 */
	$("#postSearchBtn").click(function(){
		if($("input:checkbox[id='chkDeliveryModule']").is(":checked")){
			if(confirm("회수지에 대한 정보가 초기화 됩니다. 진행하시겠습니까?")){
				$("input:checkbox[id='chkDeliveryModule']").prop("checked",false);
				abc.postPopup(abc.biz.afterservice.commonpop.setPostCode);
			}
		}else{
			abc.postPopup(abc.biz.afterservice.commonpop.setPostCode);
		}
	});
  
	/**
	 * 우편번호 pop 에서 값을 선택햇을경우 SETTING 
	 */
	_commonpop.setPostCode = function(data){
		$("#buyerPostCodeText").val(data.postCode);
		$("#buyerPostAddrText").val(data.postAddress);
		$("#buyerDtlAddrText").focus();  // 상세주소에 포커스
	}

	/**
	 * 배송주소와 동일 선택시 
	 */

	$("#chkDeliveryModule").change(function(){
		var chk = $(this).is(":checked");//.attr('checked');
		if(chk){
			$("#buyerPostCodeText").val($('#orgBuyerPostCodeText').val());
			$("#buyerPostAddrText").val($('#orgBuyerPostAddrText').val());
			$("#buyerDtlAddrText").val($('#orgBuyerDtlAddrText').val()); 
			$("#buyerName").val($('#orgBuyerName').val()); 
			$("#buyerHdphnNoText").val($('#orgBuyerHdphnNotext').val()); 
		}else{
			//우편번호 pop 에서 선택한값
			$("#buyerPostCodeText").val("");
			$("#buyerPostAddrText").val("");
			$("#buyerDtlAddrText").val("");
			$("#buyerName").val(""); 
			$("#buyerHdphnNoText").val(""); 
	    }
	});
	
	
	
	/**
	 * 우편번호 pop 
	 */
	$("#rePostSearchBtn").click(function(){
		if($("input:checkbox[id='chkDeliveryModule02']").is(":checked")){
			if(confirm("재수령지에 대한 정보가 초기화 됩니다. 진행하시겠습니까?")){
				$("input:checkbox[id='chkDeliveryModule02']").prop("checked",false);
				abc.postPopup(abc.biz.afterservice.commonpop.setRePostCode);
			}
		}else{
			abc.postPopup(abc.biz.afterservice.commonpop.setRePostCode);
		}
	});
  
	/**
	 * 우편번호 pop 에서 값을 선택햇을경우 SETTING 
	 */
	_commonpop.setRePostCode = function(data){
		
		$("#rcvrPostCodeText").val(data.postCode);
		$("#rcvrPostAddrText").val(data.postAddress);
		$("#rcvrDtlAddrText").focus();  // 상세주소에 포커스
	}
	
	/**
	 * 재수령지 체크박스 선댁시  
	 */
	$("#chkDeliveryModule02").click(function(){
        var chk = $(this).is(":checked");//.attr('checked');
        if(chk){
        	$("#rcvrPostCodeText").val($('#orgBuyerPostCodeText').val());
    		$("#rcvrPostAddrText").val($('#orgBuyerPostAddrText').val());
    		$("#rcvrDtlAddrText").val($('#orgBuyerDtlAddrText').val()); 
    		$("#rcvrName").val($('#orgBuyerName').val()); 
			$("#rcvrHdphnNoText").val($('#orgBuyerHdphnNotext').val()); 
        }else{
        	//우편번호 pop 에서 선택한값
        	$("#rcvrPostCodeText").val("");
    		$("#rcvrPostAddrText").val("");
    		$("#rcvrDtlAddrText").val("");
    		$("#rcvrName").val(""); 
			$("#rcvrHdphnNoText").val(""); 
        }
    });
	
	/**
	 * 회원상세  팝업
	 */
	_commonpop.memberInfoPop = function(memberNo){	
		abc.memberDetailPopup(memberNo);
	 }
	
	/**
	 * 주문번호
	 */
	_commonpop.orderNoInfoPop = function(orderNo){	
		 abc.orderDetailPopup('',"",0,0,'orderNo='+orderNo);
	 }
	
	/**
	 * 접수자 팝업
	 */
	_commonpop.regsterInfoPop = function(adminYn,asRgsterno){
		if(adminYn == "Y"){
			abc.adminDetailPopup(asRgsterno);
		}else{
			abc.memberDetailPopup(asRgsterno);
		}
	 }
	/**
	 * 처리자 팝업
	 */
	_commonpop.moderInfoPop = function(moderNo){	
		 abc.adminDetailPopup(moderNo);
	 }
	/**
	 * BO 상품상세 
	 */
	_commonpop.prdtPop = function(prdtNo){	
		var param = { prdtNo : prdtNo };
		abc.readonlyProductDetailPopup(param);
	 }
	/**
	 * FO 상품상세 
	 */
	_commonpop.prdtInfoPop = function(prdtNo){	
		if($("#siteNo").val() == _commonpop.SITE_NO_ART){
			window.open(_commonpop.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
		} else {
			window.open(_commonpop.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
		}	
	 }
	
	/**
	 * 회수택배팝업
	 */
	_commonpop.rtrLogisVndrPopup = function(el) {
		
		var rtrvlWaybilNoText = $(el).text();
		var rtrvlLogisVndrCodeAddInfo1 = $("input[name='rtrvlLogisVndrCodeAddInfo1']").val();
		
		abc.dlvyInfoInquiry(rtrvlLogisVndrCodeAddInfo1, rtrvlWaybilNoText);
	}
	
    /*
     * 2020.05.08 : A/S사유 '오프라인 접수' 선택시, 회수지 배송지정보 체크가 안되어있으면  주문기본정보로 set
     */
    $("#rsnCode").change(function(){
    	if( $(this).val() == "10018" ){
    		if( !$("#chkDeliveryModule").is(":checked") ) {
    			$("input:checkbox[id='chkDeliveryModule']").trigger("click");
    		}
    	}
    });
	
})();