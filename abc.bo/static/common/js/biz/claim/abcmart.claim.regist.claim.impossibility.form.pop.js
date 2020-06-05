/***
 * 클레임 불가처리 등록 팝업 화면과 관련된 함수 정의.
 */
(function() {
	
	var _rgstClmImpsbltFrmPop = abc.object.createNestedObject(window,"abc.biz.claim.regist.impossibility");
	
	/*************************************************************************
	 *								클레임 불가 등록 팝업 화면
	 *************************************************************************/
	
	/**
	 * 최초 화면 반송지 정보 갱신
	 */
	_rgstClmImpsbltFrmPop.initRcvrInfo = function(){
		// 수취인 명
		$("#rcvrName").val( $("#idRcvrName").val() );
		// 수취인 핸드폰번호
		$("#rcvrHdphnNoText").val( $("#idRcvrHdphnNoText").val() );
		// 수취인 우편번호
		$("#rcvrPostCodeText").val( $("#idRcvrPostCodeText").val() );
		// 수취인 우편주소
		$("#rcvrPostAddrText").val( $("#idRcvrPostAddrText").val() );
		// 수취인 상세주소
		$("#rcvrDtlAddrText").val( $("#idRcvrDtlAddrText").val() );
		// 배송 시 요청사항
		$("#dlvyMemoText").val( $("#idDlvyMemoText").val() );
		$("#dlvyMemoTextLength").text($("#dlvyMemoText").val().length);
	}
	
	/**
	 * 반송지 정보 초기화
	 */
	_rgstClmImpsbltFrmPop.emptyRcvrInfo = function(){
		// 수취인 명
		$("#rcvrName").val( "" );
		// 수취인 핸드폰번호
		$("#rcvrHdphnNoText").val( "" );
		// 수취인 우편번호
		$("#rcvrPostCodeText").val( "" );
		// 수취인 우편주소
		$("#rcvrPostAddrText").val( "" );
		// 수취인 상세주소
		$("#rcvrDtlAddrText").val( "" );
		// 배송 시 요청사항
		$("#dlvyMemoText").val( "" );
		$("#dlvyMemoTextLength").text("0");
	}
	
	/**
	 * 우편번호 찾기
	 */
	_rgstClmImpsbltFrmPop.setRetrievalPostPost = function(data) {
        $("#rcvrPostCodeText").val(data.postCode);
        $("#rcvrPostAddrText").val(data.postAddress);
        $("#rcvrDtlAddrText").val("");
        $("#rcvrDtlAddrText").focus();
    }
	
	/**
	 * 주문자 정보 체크박스 클릭 감지
	 */
	_rgstClmImpsbltFrmPop.checkBox = function(){
		
		$("#chkModule").click(function(){
			
			if( $("#chkModule").is(":checked") )
				_rgstClmImpsbltFrmPop.initRcvrInfo();
			else
				_rgstClmImpsbltFrmPop.emptyRcvrInfo();
		});
	}
	
	/**
	 * 요청내용 keyup
	 */
	_rgstClmImpsbltFrmPop.clmImpsbltContTextKeyup = function(){
		$("#clmImpsbltContTextLength").text( _rgstClmImpsbltFrmPop.strLengthCheck("#clmImpsbltContText", 500) );
	}
	
	/**
	 * 배송 시 요청사항 keyup
	 */
	_rgstClmImpsbltFrmPop.dlvyMemoTextKeyup = function(){
		$("#dlvyMemoTextLength").text( _rgstClmImpsbltFrmPop.strLengthCheck("#dlvyMemoText", 40) );
	}
	
	/**
	 * keyup 문자열 길이 체크후 반환
	 */
	_rgstClmImpsbltFrmPop.strLengthCheck = function(id, limitLength){
		var str = $(id).val();
		var strLength = str.length;
		
		if(strLength > limitLength){
			alert(limitLength + "자를 넘길 수 없습니다.");
			$(id).val( str.substring(0, limitLength) );
			
			return limitLength;
		}
		
		return strLength;
	}
	
	/**
	 * validation
	 */
	_rgstClmImpsbltFrmPop.validation = function(){
		// 클레임 불가 사유 코드
		var clmImpsbltRsnCode = $("#clmImpsbltRsnCode option:selected").val();
		
		if( abc.text.allNull(clmImpsbltRsnCode) ){
			alert("클레임 불가 사유를 선택해주세요.");
			$("#clmImpsbltRsnCode").focus();
			return false;
		}
		
		// 클레임 불가 : 요청 내용
		var clmImpsbltContText = $("#clmImpsbltContText").val();
		
		if( !abc.text.allNull(clmImpsbltContText) ){
			if(clmImpsbltContText.length > 500){
				
				alert("500자를 넘을수 없습니다. "); 
				$("#clmImpsbltContText").focus();
				return false;
			}
		} else{
			alert("요청 내용을 입력해주세요.");
			$("#clmImpsbltContText").focus();
			return false;
		}
		
		// 택배사 선택
		var logisVndrCode = $("#logisVndrCode option:selected").val();
		
		if( abc.text.allNull(logisVndrCode) ){
			alert("택배사를 선택해주세요.");
			$("#logisVndrCode").focus();
			return false;
		}
		
		// 운송장 번호
		var waybilNoText = $("#waybilNoText").val();
		
		if( !abc.text.allNull(waybilNoText) ){
			if(waybilNoText.length > 50){
				_rgstClmImpsbltFrmPop.strLengthCheck("#waybilNoText", 50);
				$("#waybilNoText").focus();
				return false;
			}
		} else{
			alert("운송장 번호를 입력해주세요.");
			$("#waybilNoText").focus();
			return false;
		}
		
		// 발송예정일
		var dlvyTodoYmd = $("#dlvyTodoYmd").val();
		
		if( abc.text.allNull(dlvyTodoYmd) ){
			alert("발송예정일을 선택해주세요.");
			$("#dlvyTodoYmd").focus();
			return false;
		} else{
			// . 제거
			$("#dlvyTodoYmd").val( dlvyTodoYmd.replace(/\./gi, "") );
		}
		
		// 수취인 명
		var rcvrName = $("#rcvrName").val();
		
		if( !abc.text.allNull(rcvrName) ){
			if(rcvrName.length > 15){
				_rgstClmImpsbltFrmPop.strLengthCheck("#rcvrName", 15);
				$("#rcvrName").focus();
				return false;
			}
		
		} else {
			alert("수령자명을 입력해주세요.");
			$("#rcvrName").focus();
			return false;
		}
		
		// 수취인 휴대폰번호
		var rcvrHdphnNoText = $("#rcvrHdphnNoText").val();
		
		if( !abc.text.allNull(rcvrHdphnNoText) ){
			if(rcvrHdphnNoText.length > 15){
				_rgstClmImpsbltFrmPop.strLengthCheck("#rcvrHdphnNoText", 15);
				$("#rcvrHdphnNoText").focus();
				return false;
			}
		
		} else {
			alert("수령자명을 입력해주세요.");
			$("#rcvrHdphnNoText").focus();
			return false;
		}
		
		// 상세주소
		var rcvrDtlAddrText = $("#rcvrDtlAddrText").val();
		
		if( !abc.text.allNull(rcvrDtlAddrText) ){
			if(rcvrDtlAddrText.length > 200){
				_rgstClmImpsbltFrmPop.strLengthCheck("#rcvrDtlAddrText", 200);
				$("#rcvrDtlAddrText").focus();
				return false;
			}
		
		} else {
			alert("상세주소를 입력해주세요.");
			$("#rcvrDtlAddrText").focus();
			return false;
		}
		
		// 배송 시 요청사항
		var dlvyMemoText = $("#dlvyMemoText").val();
		
		if( !abc.text.allNull(dlvyMemoText) ){
			if(dlvyMemoText.length > 40){
				alert("40자를 넘을수 없습니다."); 
				$("#dlvyMemoText").focus();
				return false;
			}
		}
	}
	
	/**
	 * 클레임 불가 등록
	 */
	_rgstClmImpsbltFrmPop.registImpossibility = function(){
		// validation
		if( _rgstClmImpsbltFrmPop.validation() == false ) { return; }
		
		if (!confirm('클레임 불가 처리를 하시겠습니까?')) return;
		
		$.ajax({
			type : "post",
			url  : "/claim/claim/regist-claim-impossibility",
			data : FormQueryStringEnc(document.frmRegist)
					
		}).done(function(data){
			alert("클레임 불가 처리를 하였습니다.");
			top.opener.location.reload();
			window.close();
		}).fail(function(e){
			alert("클레임 불가 처리에 실패했습니다.");
		});
	}
	
})();