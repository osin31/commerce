/***
 * 회원관리 상세 회원정보 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								회원 정보 탭
	 *************************************************************************/
	var _memberInfo = abc.object.createNestedObject(window,"abc.biz.member.member.info");
	
	_memberInfo.blackListCode;
	_memberInfo.blackListTypeCode;
	_memberInfo.otsVipYn;
	_memberInfo.smsRecvYn;
	_memberInfo.emailRecvYn;
	_memberInfo.nightSmsRecvYn;
	_memberInfo.resellerDoubtYn;
	_memberInfo.bankCode;
	
	// 인증 성공여부
	_memberInfo.isCertify = false;
	
	// 인증 상태 (0:최초, 1:인증요청중, 2:인증시간만료)
	_memberInfo.certifyStatus = 0;
	
	/**
	 * 회원정보 탭 체크박스 데이터 세팅
	 */
	_memberInfo.initSetData = function() {
				
		$("#certArea").hide();
		
		if(_memberInfo.otsVipYn == abc.consts.BOOLEAN_TRUE){
			$("#otsVipYn").prop('checked', true);
		}
		
		if(_memberInfo.smsRecvYn == abc.consts.BOOLEAN_TRUE){
			$("#smsRecvYn").prop('checked', true);
		}
		
		if(_memberInfo.emailRecvYn == abc.consts.BOOLEAN_TRUE){
			$("#emailRecvYn").prop('checked', true);
		}
		
		if(_memberInfo.nightSmsRecvYn == abc.consts.BOOLEAN_TRUE){
			$("#nightSmsRecvYn").prop('checked', true);
		}
		
		if(_memberInfo.resellerDoubtYn == abc.consts.BOOLEAN_TRUE){
			$("#resellerDoubtYn").prop('checked', true);
		}
		
		if(_memberInfo.blackListCode == abc.consts.BOOLEAN_TRUE){
			$("#blackListYn").prop("checked", true);
			$("#blackListTypeCode").attr("disabled", false);
			$("#blackListTypeCode").val(_memberInfo.blackListTypeCode).prop("selected", true);
		}
		
		$("#bankCode").val(_memberInfo.bankCode).prop("selected", true);
	}
	
	/**
	 * 회원이력 팝업 호출
	 */
	_memberInfo.memberHistoryPop = function() {
		var memberNo = $("#memberNo").val();
		
		var url = "/member/history-pop";
		var params = {}
		params.memberNo = memberNo;

		abc.open.popup({
			winname :	"memberHistoryPop",
			url 	:	url,
			width 	:	1240,
			height	:	880,
			params	:	params

		});
	}
	
	/**
	 * 비밀번호 초기화
	 */
	_memberInfo.pswdReset = function() {
		var memberNo = $("#memberNo").val();
		
		if(confirm("비밀번호를 초기화 하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/member/update-pswd-reset",
				data : { "memberNo" : memberNo }
			})
			.done(function(data){
				if(data.result == abc.consts.BOOLEAN_TRUE){
					alert("임시 비밀번호를 이메일로 전송하였습니다.");
					location.reload();
				}
			})
			.fail(function(e){
				alert("임시 비밀번호 이메일로 전송이 실패하였습니다.");
				location.reload();
			});
		}
	}
	
	/**
	 * 블랙리스트 체크설정
	 */
	_memberInfo.blackListCheckSet = function() {
		if($("#blackListYn").is(":checked")){
			$("#blackListTypeCode").attr("disabled", false);
		}else{
			$("#blackListTypeCode").val("").attr("selected", "selected");
			$("#blackListTypeCode").attr("disabled", true);
		}
	}
	
	/**
	 * 인증번호 발송
	 */
	_memberInfo.certificationSet = function() {
		// 인증번호 발송 로직 추가 필요
		var memberNo = $("#memberNo").val();
		var crtfcPath = abc.consts.CRTFC_PATH_CODE_CUSTOMER_CERT;
		var memberName = $("#hiddenMemberName").val();
		var hdphnNoText = $("#hdphnNoText").val();
		
		// 인증 성공여부 초기화
		_memberInfo.isCertify = false;
		var params = {}
		
		params.memberNo = memberNo;
		params.crtfcPathCode = crtfcPath;
		params.crtfcNoSendInfo = hdphnNoText;
		params.memberName = memberName;
		
		if(!abc.text.isPhoneNum(hdphnNoText)){
			alert("‘-’ 없이 입력 해주세요 (예: 01012345678)");
			return;
		}
		
		_memberInfo.certifyStatus = 1;
		
		$.ajax({
			type:"post",
			url: "/member/create-cert-number-hdphn",
			data: params
		}).done(function(data, textStatus, jqXHR) {
			alert("인증번호가 발송되었습니다. (1일 최대 3회)");
			$("#certArea").show();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("인증번호 발송이 실패하였습니다. 관리자에게 문의하세요.");
			_memberInfo.certifyStatus = 0;
			console.log(jqXHR, textStatus, errorThrown);
		});
	}
	
	/**
	 * 인증번호 확인
	 */
	_memberInfo.certificationConfirm = function() {
		if(abc.text.allNull($("#crtfcNoText").val())){
			alert("인증번호 6자리를 입력해 주세요.");
			return;
		}
		
		if (_memberInfo.isCertify) {
			return;
		}
		
		$.ajax({
			type:"post",
			url: "/member/validate-certification-number",
			async : false,
			data: {
				crtfcNoSendInfo: $("#hdphnNoText").val(),
				crtfcNoText: $("#crtfcNoText").val()
			}
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.member.member.info.validateCertifyNumberSuccess();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert(jqXHR.responseJSON.message);
		});
		return _memberInfo.isCertify;
	}
	
	/**
	 * 인증번호 확인 성공
	 */
	_memberInfo.validateCertifyNumberSuccess = function() {
		_memberInfo.certifyStatus = 0;
		_memberInfo.isCertify = true;
		
		$("#certConfirmYn").val(abc.consts.BOOLEAN_TRUE);
		alert("휴대폰 번호가 인증되었습니다.");
	}
	
	/**
	 * 회원 Action 관리
	 */
	_memberInfo.memberInfoAction = function(sAction) {
		switch(sAction) {
			case "save" :
				var form = $("#memberDetailForm");
				
				//저장에 필요한 파라메터 세팅
				var marketing = new Array();
				var accountInfo = new Array();
				var birthYmd = $("#birthYmd").val();
				var annvrYmd = $("#annvrYmd").val();
				var annvrName = $("#annvrName").val();
				var hdphnNoText = $("#hdphnNoText").val();
				var postCodeText = $("#postCodeText").val();
				var postAddrText = $("#postAddrText").val();
				var dtlAddrText = $("#dtlAddrText").val();
				var smsRecvYn =  $("input:checkbox[name='smsRecvYn']:checked").val();
				var emailRecvYn =  $("input:checkbox[name='emailRecvYn']:checked").val();
				var bankVal = $("#bankCode option:selected").val();
				var bankName = $("#bankCode option:selected").text();
				var acntNoText = $("#acntNoText").val();
				var acntHldrName = $("#acntHldrName").val();
				var certConfirmYn = $("#certConfirmYn").val();
				var memberName = $("#memberName").val();
				var hiddenMemberName = $("#hiddenMemberName").val();
				
				form.find(":checkbox").each(function(){
					if($(this).is(":checked")){
						$(this).val("Y");
					}else{
						var checkboxName = $(this).attr("name");
						form.append('<input type="hidden" name="'+checkboxName+'" value="N" />');
					}
				});
				
				if(abc.text.allNull(memberName)){
					alert("이름을 입력하세요.");
					return;
				}
				
				if(!abc.text.validateCheckStringAndNum(memberName)) {
					alert("이름은 한글, 영문, 숫자만 가능합니다.");
					return;
				}
				
				if(!abc.text.allNull(annvrName) && abc.text.allNull(annvrYmd)){
					alert("기념일을 입력해 주세요.");
					return;
				}
				
				if(abc.text.allNull(annvrName) && !abc.text.allNull(annvrYmd)){
					alert("기념일명을 입력해 주세요.");
					return;
				}
				
				if(abc.text.allNull(hdphnNoText)){
					alert("휴대폰번호를 입력하세요.");
					return;
				}
				
				if(!abc.text.allNull(birthYmd)){
					birthYmd = abc.text.validateStringSignRemove(birthYmd);
					if(!abc.text.isDate(birthYmd)){
						alert("유효하지 않은 날짜 입니다.");
						$("#birthYmd").focus();
						return;
					}
				}
				
				if(!abc.text.allNull(annvrYmd)){
					annvrYmd = abc.text.validateStringSignRemove(annvrYmd);
					if(!abc.text.isDate(annvrYmd)){
						alert("유효하지 않은 날짜 입니다.");
						$("#annvrYmd").focus();
						return;
					}
				}
				
				if(hiddenMemberName != memberName){
					if(certConfirmYn == abc.consts.BOOLEAN_FALSE){
						alert("이름 변경시에는 휴대폰 인증이 필수 입니다.");
						return ;
					}
				}
				
				if(!abc.text.allNull(dtlAddrText)){
					if((abc.text.allNull(postCodeText))||(abc.text.allNull(postAddrText))){
						alert("주소를 확인해주세요.");
						return false;
					}
				}
				
				// S : 마케팅 활용 동의 값 세팅(이력 저장에 필요한 데이터)
				if(!abc.text.allNull(smsRecvYn)){
					smsRecvYn = abc.consts.MEMBER_MARKETING_SMS;
					marketing.push(smsRecvYn);
				}
				
				if(!abc.text.allNull(emailRecvYn)){
					emailRecvYn = abc.consts.MEMBER_MARKETING_EMAIL;
					marketing.push(emailRecvYn);
				}
				// E : 마케팅 활용 동의 값 세팅(이력 저장에 필요한 데이터)
				
				// S : 환불계좌 정보 세팅(이력 저장에 필요한 데이터)
				if(!abc.text.allNull(bankVal)){
					accountInfo.push(bankName);
				}
				
				if(!abc.text.allNull(acntNoText)){
					accountInfo.push(acntNoText);
				}
				
				if(!abc.text.allNull(acntHldrName)){
					accountInfo.push(acntHldrName);
				}
				// E : 환불계좌 정보 세팅(이력 저장에 필요한 데이터)
				
				if(!abc.text.allNull(annvrName) && !abc.text.allNull(annvrYmd)){
					$("#memberDetailForm").append("<input type='hidden' name='anniversary' value='"+ annvrName +", "+ annvrYmd +"'>");
				}
				$("#memberDetailForm").append("<input type='hidden' name='dtlAddressText' value='"+ postAddrText +" "+ dtlAddrText +"'>");
				$("#memberDetailForm").append("<input type='hidden' name='marketing' value='"+ marketing +"'>");
				$("#memberDetailForm").append("<input type='hidden' name='accountInfo' value='"+ accountInfo +"'>");
				
				if(confirm("저장하시겠습니까?")){
					$.ajax({
						type :"post",
						url : "/member/update-member",
						data : $("#memberDetailForm").serialize()
					}).done(function(data){
						if(data.result == abc.consts.BOOLEAN_TRUE){
							alert("저장되었습니다.");
							location.reload();
						}
					})
					.fail(function(e){
						alert("저장에 실패하였습니다.");
					});
				}
				
				break;
			case "memoSave" :
				var memberNo = $("#memberNo").val();
				var memoText = $("#memoText").val();
				
				if(abc.text.allNull(memoText)){
					alert("메모를 입력하세요.");
					$("#memoText").focus();
					return false;
				}
				
				$.ajax({
					type :"post",
					url : "/member/create-member-memo",
					data : {"memberNo" : memberNo, "memoText" : memoText}
				}).done(function(data){
					
					var chrHtml;
					var rgstDtm = new Date(data.memoInfo[0].rgstDtm);
					
					chrHtml  = "<li>";
					chrHtml += "<span class='msg-list-info'>";
					chrHtml += "<span class='user-info'>";
					chrHtml += "<span class='user-id'>"+ data.memoInfo[0].memberMemoSeq+" / "+ data.memoInfo[0].loginId  +"</span>";
					chrHtml += "<span class='user-name'>("+ data.memoInfo[0].adminName +")</span>";
					chrHtml += "</span>";
					chrHtml += "<span class='regist-date-wrap'>";
					chrHtml += "<span class='regist-date'>"+ rgstDtm.format() +"</span>";
					chrHtml += "<input type='hidden' value='"+ data.memoInfo[0].memberMemoSeq +"'>";
					chrHtml += "<a href='javascript:void(0)' class='btn-msg-list-del'><i class='ico ico-del'><span class='offscreen'>메모 삭제</span></i></a>";
					chrHtml += "</span>";
					chrHtml += "</span>";
					chrHtml += "<p class='msg-desc'>" + data.memoInfo[0].memoText + "</p>";
					chrHtml += "</li>";
					
					$("#memoArea").prepend(chrHtml);
					$("#memoText").val("");
					
					alert("저장되었습니다.");
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});
				
				break;
			case "blackSave" :
				var memberNo = $("#memberNo").val();
				var blackListYn = $("input:checked[name='blackListYn']").is(":checked");
				var blackListYnVal =  $("input:checkbox[name='blackListYn']:checked").val();
				var blackListTypeVal = $("#blackListTypeCode option:selected").val();
				
				if(blackListYn && abc.text.allNull(blackListTypeVal)){
					alert("블랙리스트 유형을 선택해야 합니다.");
					return false;
				}
				
				if(blackListYn){
					blackListYnVal = abc.consts.BOOLEAN_TRUE;
				}else{
					blackListYnVal = abc.consts.BOOLEAN_FALSE;
				}
				
				$.ajax({
					type :"post",
					url : "/member/update-member-blacklist",
					data : {"memberNo" : memberNo, "blackListYn" : blackListYnVal, "blackListTypeCode" : blackListTypeVal},
				}).done(function(data){
					if(data.result == abc.consts.BOOLEAN_TRUE){
						alert("저장되었습니다.");
						location.reload();
					}
				})
				.fail(function(e){
					alert("저장에 실패하였습니다.");
				});
				
				break;
		}
	}
	
	/**
	 * 관리자 메모 글자수 체크(200자)
	 */
	_memberInfo.stringLengthCheck = function(obj) {
		if(obj.length <= 400){
			$('#counter').html(obj.length);
		}else{
			alert("메모는 400자를 초과할 수 없습니다.");
			$("#memoText").val(obj.substring(0, 400));
			return false;
		}
	}
	
	/**
	 * 관리자 메모 삭제
	 */
	_memberInfo.removeMemo = function(idx, classNm) {
		var memoSeq = $("."+classNm).eq(idx).find("input").val();
		var memberNo = $("#memberNo").val();
		
		$.ajax({
			type :"post",
			url : "/member/delete-member-memo",
			data : {"memberNo" : memberNo, "memberMemoSeq" : memoSeq},
		}).done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				$("."+classNm).parent('li').eq(idx).remove();
				alert("삭제되었습니다.");
			}
		})
		.fail(function(e){
			alert("삭제에 실패하였습니다.");
		});
	}
	
	/**
	 * 배송주소록 호출
	 */
	_memberInfo.searchDeliveryPop = function(){
		var memberNo = $("#memberNo").val();
		
		var url = "/member/member-delivery-pop";
		var params = {}
		params.memberNo = memberNo;

		abc.open.popup({
			winname :	"memberDeliveryPop",
			url 	:	url,
			width 	:	1480,
			height	:	630,
			params	:	params

		});
	}
	
	/**
	 * 환불계좌 저장
	 */
	_memberInfo.accountAuth = function(){
		var acntHldrName = $('#acntHldrName').val().replace(/ /gi,'');
		var bankCode 	 = $('#bankCode').children(":selected").attr("id");
		var acntNoText 	 = $('#acntNoText').val().replace(/ /gi,'');
		var memberNo 	 = $('#memberNo').val().replace(/ /gi,'');
		
		if(acntHldrName == '' || typeof acntHldrName == 'undefined'){
			alert("예금주를 입력하세요");
			$('#acntHldrName').focus();
			return;
		}else if(bankCode == '' || typeof bankCode == 'undefined'){
			alert("은행을 선택하세요");
			$('#bankCode').focus();
			return;
		}else if(acntNoText == '' || typeof acntNoText == 'undefined'){
			alert("계좌번호를 입력하세요");
			$('#acntNoText').focus();
			return;
		}else{
			$.ajax({
				type	 : "post",
				url		 : "/member/account-auth",
				async 	 : false,
				dataType : "json",
				data	 : {acntHldrName:acntHldrName, bankCode:bankCode, acntNoText:acntNoText, memberNo:memberNo}
			})
			.done(function(data, textStatus, jqXHR){
				var flag 	= data.flag;
				
				if(flag == abc.consts.BOOLEAN_TRUE){
					alert("인증되었습니다.");
					$("#acntCrtfcYn").val(abc.consts.BOOLEAN_TRUE);
				}else{
					alert("계좌 인증에 실패하였습니다.\n인증 실패한 계좌 정보는 저장하지 않습니다.");
					$("#acntCrtfcYn").val(abc.consts.BOOLEAN_FALSE);
				}
			})
			.fail(function(jqXHR, textStatus){
				authYn = "N";
				alert("에러가 발생했습니다.\n" + jqXHR.responseJSON.message);
			});
		}
	}
	
	/**
	 * 포인트 사용 비밀번호 초기화
	 */
	_memberInfo.pointReset = function(){
		var memberNo = $("#memberNo").val();
		
		if(confirm("비밀번호를 초기화 하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/member/update-point-pswd-reset",
				data : { "memberNo" : memberNo }
			})
			.done(function(data){
				if(data.result == "Y"){
					alert("저장하였습니다.");
					location.reload();
				}
			})
			.fail(function(e){
				alert("저장에 실패하였습니다.");
				location.reload();
			});
		}
	}

	// 우편번호 세팅
	_memberInfo.setPostCode = function(data){
		$("#postCodeText").val(data.postCode);
		$("#postAddrText").val(data.postAddress);
//		$("#buildingCode").val(data.buildingCode); 건물번호는 필요시 사용한다. (건물에 부여한 25자리 고유번호)	

		$("#dtlAddrText").focus();
	}
	
	/**
	 * 임직원 인증 초기화
	 */
	_memberInfo.empCrtfcReset = function(){
		var memberNo = $("#memberNo").val();
		
		if(confirm("임직원 인증을 초기화 하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/member/update-emp-cert-reset",
				data : { "memberNo" : memberNo }
			})
			.done(function(data){
				if(data.result == abc.consts.BOOLEAN_TRUE){
					alert(data.resultMsg);
					location.reload();
				}else{
					alert(data.resultMsg);
				}
			})
			.fail(function(e){
				alert(data.resultMsg);
				location.reload();
			});
		}
	}
	/**
	 * 계좌초기화
	 */
	_memberInfo.accountRemove = function(){
		$("#bankCode").val("").prop("selected", true);
		$("#acntNoText").val("");
		$("#acntHldrName").val("");
		$("#acntCrtfcYn").val(abc.consts.BOOLEAN_TRUE);
	}
	
	_memberInfo.memberLeave = function() {
		var leaveRsnCode = "10001"; // 강제탈퇴
		var memberNo = $("#memberNo").val();
		if(confirm("회원탈퇴를 진행하시겠습니까?")){
			$.ajax({
				type	 :"post",
				url		 : "/member/leave-process",
				async 	 : false,
				data	 : { memberNo:memberNo, leaveRsnCode:leaveRsnCode }
			})
			.done(function(data){			
				var success = data.success;
				console.log(data);
				//성공일 경우
				if(success == 1){
					alert("탈퇴가 완료되었습니다.");
				}else{
					alert("해당 회원은 탈퇴가 불가능합니다.\n주문 " + data.orderCnt + "건, 클레임 " + data.claimCnt + "건, A/S " + data.asCnt + "건이 있습니다.");
				}
				location.reload();
			})
			.fail(function(e){
				alert("회원탈퇴에 실패하였습니다.\n 시스템 관리자에게 문의하여 주시기 바랍니다.");
			});
		}
	}
	
})();