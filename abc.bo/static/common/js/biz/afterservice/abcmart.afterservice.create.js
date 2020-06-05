/***
 * 클레임 교환상세와 관련된 함수 정의.
 */
(function() {
	
	var _afterServiceCreate = abc.object.createNestedObject(window,"abc.biz.afterservice.create");
	
	/**
	 * 미처리표시 체크 박스 처리
	 */
	$("input:checkbox[id='unProcYnChk']").trigger("click");

	
	
	/**
	 * AS사유 팝업 호출
	 */
    $("#reasonBtn").click(function(){
    	var asGbnCode = $("#gbnCode option:selected").text();
    	var asRsnCode = $("#rsnCode option:selected").text();
    	var prdtName = $("#prdtName").val();
    	var asAcceptContText = $("#asAcceptContText").val();
    	var adminAcceptYn = $("#adminAcceptYn").val();
    	
		var url = "";
		var params = {asGbnCode : asGbnCode, asRsnCode:asRsnCode, prdtName:prdtName, asAcceptContText:asAcceptContText,adminAcceptYn:adminAcceptYn};
		url = "/afterservice/afterservice/create-afterservice-reason-pop"; // AS접수 내용 등록
		
		abc.open.popup({
			winname :	"",
			url 	:	url,
			width 	:	800,
			height	:	500,
			params	:	params
		})
    });
    
    
    /**
     * A/S 접수 유효성 검사 및 저장 프로세스 
     */
	_afterServiceCreate.acceptSaveBtn = function(){	
		var $asAcceptContText = $("#asAcceptContText");
		var $rcvrHdphnNoText = $("#rcvrHdphnNoText");
		var $rcvrName = $("#rcvrName");
		var $rcvrDtlAddrText = $("#rcvrDtlAddrText");
		var $rcvrPostAddrText = $("#rcvrPostAddrText");
		var $rcvrPostCodeText = $("#rcvrPostCodeText");
		var $buyerHdphnNoText = $("#buyerHdphnNoText");
		var $buyerName = $("#buyerName");
		var $buyerPostAddrText = $("#buyerPostAddrText");
		var $buyerDtlAddrText = $("#buyerDtlAddrText");
		var $buyerPostCodeText = $("#buyerPostCodeText");
		var $asRsnCode = $("#asRsnCode");
		var $asGbnCode = $("#asGbnCode");
		
		if(abc.text.isBlank($asAcceptContText.val())){
			alert("내용을  입력해 주십시요.");
			$("#reasonBtn").trigger("click");
			return false;
		}
		if(abc.text.isBlank($buyerPostCodeText.val())){
			alert("발송인 우편번호 입력해 주십시요.");
			$buyerPostCodeText.focus();
			return false;
		}
		
		if(abc.text.isBlank($buyerPostAddrText.val())){
			alert("발송인 주소를  입력해 주십시요.");
			$buyerPostAddrText.focus();
			return false;
		}
		
		if(abc.text.isBlank($buyerDtlAddrText.val())){
			alert("발송인 상세주소를 입력해 주십시요.");
			$buyerDtlAddrText.focus();
			return false;
		}
		
		if(abc.text.isLimitLength($buyerDtlAddrText.val(),100)){
			alert("발송인 상세주소가 최대 길이를 초과하였습니다.");
			$buyerName.focus();
			return false;
		}
		
		if(abc.text.isBlank($buyerName.val())){
			alert("발송인명  입력해 주십시요.");
			$buyerName.focus();
			return false;
		}
		
		if(abc.text.isLimitLength($buyerName.val(),50)){
			alert("발송인명  최대길이를 초과하였습니다.");
			$buyerName.focus();
			return false;
		}
		
		if(abc.text.isBlank($buyerHdphnNoText.val())){
			alert("발송인 연락처를 입력해 주십시요.");
			$buyerHdphnNoText.focus();
			return false;
		}
		
		if(!abc.text.validateCheckStringAndNum($buyerName.val())){
			alert("특수문자 포함되었습니다.");
			$buyerName.focus();
			return false;
		}
	
		if(abc.text.isBlank($rcvrPostCodeText.val())){
			alert("재수령자 우편번호를 입력해 주십시요.");
			$rcvrPostCodeText.focus();
			return false;
		}
		
		if(abc.text.isBlank($rcvrName.val())){
			alert("재수령자명 입력해 주십시요.");
			$rcvrName.focus();
			return false;
		}
		
		if(abc.text.isLimitLength($rcvrName.val(),50)){
			alert("재수령자명  최대길이를 초과하였습니다.");
			$rcvrName.focus();
			return false;
		}
		
		if(abc.text.isBlank($rcvrHdphnNoText.val())){
			alert("재수령자 연락처를 입력해 주십시요.");
			$rcvrHdphnNoText.focus();
			return false;
		}
		
		if(abc.text.isBlank($rcvrPostAddrText.val())){
			alert("재수령자 주소를 입력해 주십시요.");
			$rcvrPostAddrText.focus();
			return false;
		}

		if(abc.text.isBlank($rcvrDtlAddrText.val())){
			alert("재수령자 상세주소를 입력해 주십시요.");
			$rcvrDtlAddrText.focus();
			return false;
		}
		
		if(abc.text.isLimitLength($rcvrDtlAddrText.val(),100)){
			alert("재수령자 상세주소가 최대 길이를 초과하였습니다.");
			$rcvrDtlAddrText.focus();
			return false;
		}
		
		if(!abc.text.validateCheckStringAndNum($rcvrName.val())){
			alert("특수문자 포함되었습니다.");
			$rcvrName.focus();
			return false;
		}
		
		if($buyerHdphnNoText.val() != "" && $buyerHdphnNoText.val() != undefined){
			$buyerHdphnNoText.val(abc.text.validateStringSignRemove($buyerHdphnNoText.val()));
			if( !abc.text.isPhoneNum($buyerHdphnNoText.val()) ){
				alert("발송인 연락처 형식이 잘못되었습니다.");
				$buyerHdphnNoText.val('');
				return false;
			}
		}
		if($rcvrHdphnNoText.val() != "" &&  $rcvrHdphnNoText.val() != undefined){
			$rcvrHdphnNoText.val(abc.text.validateStringSignRemove($rcvrHdphnNoText.val()));
			if( !abc.text.isPhoneNum($rcvrHdphnNoText.val()) ){
				alert("재수령자 연락처 형식이 잘못되었습니다.");
				$rcvrHdphnNoText.val('');
				return false;
			}
		}
		
		// 2020.05.08 : A/S사유를  '오프라인 접수' 선택시 수거지시,수령완료 패스 경고 Alert
		if( $("#rsnCode").val() == "10018" ){
			alert("A/S사유를  '오프라인 접수'로 선택하여 접수 할 시, '수령완료'상태로 접수됩니다.");
		}
		
		if(confirm("AS접수를 진행  하시겠습니까?")){
			$.ajax({
				type:"post",
				url  : "/afterservice/afterservice/create-afterServiceAccept",
				data: $("#frmInfo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				opener.abc.biz.order.info.tab.asAcceptComplete(data);
				self.close();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("잠시후에 다시 시도해주시기 바랍니다.");
			});
		}else{
			$buyerHdphnNoText.val(abc.text.validateStringSignRemove($buyerHdphnNoText.val()));
			$rcvrHdphnNoText.val(abc.text.validateStringSignRemove($rcvrHdphnNoText.val()));
		}
	}
	
})();