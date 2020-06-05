(function() {

	var _afterServiceDetailCheck = abc.object.createNestedObject(window,"abc.biz.afterservice.detailInfoStatusCheck");

	
	/**
	 * FO 에서 접수하고 첫 화면일때 접수철회 버튼 및 수거지시 버튼 노출. 
	 */
	_afterServiceDetailCheck.withdrawAccept = function(){	
		var $asWthdrawContText = $("#asWthdrawContText");
		var $asWthdrawRsnCode = $("#asWthdrawRsnCode");
		
		// 관리자 접수 여부 
		var $adminAcceptYn = $("#adminAcceptYn");
		// 사유 구분 코드
		var $asGbnCode     =$("#asGbnCode");
		// 결제한 배송비 금액
		var $asPymntAmt    = $("#pymntAmt");
		// 사용자 접수이면서 수선일 경우  결제한 금액이 있으면 접수 철회시 결제 취소 금액을 먼저 물어보고 취소시켜야 한다.
		if($adminAcceptYn.val() == 'N' && $asGbnCode.val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
			if(Number($asPymntAmt.val()) > 0){
				alert("배송비가 존재합니다. A/S접수철회 요청 하기전에 배송비를 먼저 취소 해주세요.");
				return false;
			}
		}
		
		// 철회 유형 코드
		if(abc.text.isBlank($asWthdrawRsnCode.val())){
			alert("철회 유형 코드를 선택해주세요.");
			$asWthdrawRsnCode.focus();
			return false;
		}
		// 철회내용
		if(abc.text.isBlank($asWthdrawContText.val())){
			alert("접수 철회 사유를 입력하세요");
			$asWthdrawContText.focus();
			return false;
		}
		// 철회내용이 입력했다면 글자수 초과여부 확인
		if(abc.text.isLimitLength($asWthdrawContText.val(), 50)){
			alert("철회내용이  최대값을 초과하였습니다.");
			$asWthdrawContText.focus();
			return false;
			
		}

		if(confirm("A/S접수 건이 모두 철회 되며, 복원 되지 않습니다. A/S 접수를 철회 하시겠습니까?")){
			$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeWithdrawalAccept);
			$.ajax({
				type:"post",
				url  : "/afterservice/afterservice/updateAfterServiceStatCode",
				data: $("#frmInfo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				//심의 
				if(data.asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview){
					abc.biz.afterservice.detailInfoStatusCheck.detailReviewDisabled(data.asStatCode,data.adminAcceptYn,data.asGbnCode);
				//수선 
				}else{
					abc.biz.afterservice.detailInfoStatusCheck.detailRepairDisabled(data.asStatCode,data.adminAcceptYn,data.asGbnCode);
				}
				alert($('#withdrawAccept').text()+"완료 되었습니다.");
				location.reload();
				opener.abc.biz.afterservice.afterservicemain.afterserviceDoAction('search');
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		}
	}
	
	/**
	 * 심의 > 사용자, BO  접수일떄 상태별로 Disabled 영역 처리 작성
	 * stepBy 그 다음 진행을 dispay 역할 포함.
	 */
	_afterServiceDetailCheck.detailReviewDisabled = function(asStatCode,adminAcceptYn,asGbnCode){
		switch(asStatCode){
			// (10002) AS 접수 철회  하고 그 다음 진행 안됨. 
			case abc.biz.afterservice.constCode.asStatCodeWithdrawalAccept :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 진행되는 모든 input 및 선택 사항 비활성화 
				$("#temporaryStorage").hide();// 임시저장 버튼 숨김.
				$("#withdrawAccept").hide();// 첫 화면에 노출되었던 철회 버튼 숨김. 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeWithdrawalAcceptName);
				$("#statusChange").hide();//상태 변경 이벤트 제거 
				$("#close").show();// 확인 버튼 노출 그 다음 진행 없음. self.close(); 처리 
				$("#dlvyPayCancel").show();	// 배송비 결제 취소 버튼 
				break;	
			// BO, FO 접수 하고 난후 상세화면 OPEN시 접수철회 수거지시 버튼만 노출 나머지는 숨김.
			case abc.biz.afterservice.constCode.asStatCodeAccept:
				$("#asProcContText").prop("readonly", true);// 처리내용 비활성화 시킴
				$("select[name*=asProc]").attr("disabled",true);//// 처리 유형 코드 , 상세 코드 , 
				$(".LastChild").prop("disabled", true);// 택배사 및 송장번호 비활성화 처리 
				$("#asAmt").prop("readonly", true);// AS비용 비활성화 처리
				$("#temporaryStorage").hide();// 내용저장 버튼 숨김
				$("#dlvyPayCancel").show();// 결제 취소 버튼 노출 
				$("#withdrawAccept").text(abc.biz.afterservice.constCode.asStatCodeWithdrawalAcceptName);// 접수철회 네이밍 보여줌. 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePickupOrder);// 그 다음 진행상황 수거지시 버튼 노출 
				break;
			// (10004) 수거지시 -> (10005) 수령완료
			case abc.biz.afterservice.constCode.asStatCodePickupOrder :
				_afterServiceDetailCheck.displayStatus10004(adminAcceptYn,asGbnCode);//조건에 맞는 비활성화 처리
				$("#withdrawAccept").hide();// 접수 철회 버튼 숨김
				$("#temporaryStorage").show();// 내용저장 버튼 보임.
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePickupOrderName);// 상단 진행상태에 현 상태를 네이밍 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 그 다음 진행상테 수령완료 네이밍 처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinish);// 그 다음 진행상테 수령완료 코드  처리
				$("#hisStatVal").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#hisStatCode").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinish);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#messageVal").val("후  심의");// message alert 
				break;
			//  (10005) 수령완료 ->  (10006)심의완료  
			case abc.biz.afterservice.constCode.asStatCodeReceiveFinish :
				$("#withdrawAccept").hide();// 접수 철회 버튼 숨김 
				$(".Reason").prop("disabled", true); // 내용저장 버튼 보임.
				$("input[name*=buyer]").prop("readonly", true);// 회수지 정보 비활성화 처리
				$("input[name=Dlvr01]").prop("disabled", true);// 회수지 정보 체크 박스 비활성화 처리 
				$("#postSearchBtn").prop("disabled", true);// 회수지에 대한 우편번호 찾기 버튼 비활성화
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 현 진행상태를 수령완료로 표시 
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeJudgeFinishName);// 그 다음 진행상태인 심의완료 버튼 노출 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeJudgeFinish);// 그 다음 진행상태인 심의완료 코드 처리
				$("#hisStatVal").val(abc.biz.afterservice.constCode.asStatCodeJudgeFinishName);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#hisStatCode").val(abc.biz.afterservice.constCode.asStatCodeJudgeFinish);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				break;
			// (10006)심의완료   -> 수선완료
			case abc.biz.afterservice.constCode.asStatCodeJudgeFinish:
				_afterServiceDetailCheck.displayStatus10005(adminAcceptYn,asGbnCode);// 수령완료 단계에서 비활성화 처리 되는 표시 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeJudgeFinishName);// 진행상태를 심의완료로 표시 처리 
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 그 다음 단계인 수선완료 바튼 표시 처리
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeRepairFinish);// 그 다음 단계인 수선완료 코드 처리 
				$("#hisStatVal").val(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#hisStatCode").val(abc.biz.afterservice.constCode.asStatCodeRepairFinish);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				break;
				// 수선완료
			case abc.biz.afterservice.constCode.asStatCodeRepairFinish :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 수선완료 단계에서 비활성화 처리 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 진행상태를 심의완료로 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodePaymentFequestName);// 그 다음 단계인 비용결제 요청  네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePaymentFequest);// 그 다음 단계인 비용결제요청  코드 처리 
				break;
				// 비용결제요청 
			case abc.biz.afterservice.constCode.asStatCodePaymentFequest :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 비용결제요청 단계에서 비활성화 처리 모음 
				$("#temporaryStorage").hide();// 내용저장 버튼 숨김. 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePaymentFequestName);// 진행상태를 비용결제요청 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodePaymentFinishName);// 그 다음 단계인 비용결제완료  네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePaymentFinish);// 그 다음 단계인 비용결제완료  코드 처리 
				break;
				// 비용결제완료
			case abc.biz.afterservice.constCode.asStatCodePaymentFinish :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 비용결제완료 단계에서 비활성화 처리 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePaymentFinishName);// 진행상태를 비용결제완료 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 그 다음 단계인 배송중   네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeShipping);// 그 다음 단계인 배송중   코드 처리  
				break;
				// 무상 AS 일경우는 무상 AS 일경우 11111 값을 비용 관련 항목을 건너뛰고 AS배송중 단계
			case abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 무상 AS 이고 배송비가 존재하지 않으면 이 단계로 오고 그에 해당하는 버튼 및 edit 비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 진행상태를 수선완료  표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 그 다음 단계인 배송중   네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeShipping);// 그 다음 단계인 배송중   코드 처리  
				break;
				// 배송중 -->   A/S 완료 
			case abc.biz.afterservice.constCode.asStatCodeShipping :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 진행상태를 배송중으로 처리 
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeAsFinishName);// 그 다음 단계인 A/S 완료    네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeAsFinish);// 그 다음 단계인 A/S 완료    네이밍  처리 
				break;
				// A/S 완료 
			case abc.biz.afterservice.constCode.asStatCodeAsFinish :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeAsFinishName);// 진행창에 AS완료   
				_afterServiceDetailCheck.displayStatus10012();//  그 다음 진행상태 없으므로 확인 버튼만 노출 ( 그외 철회 버튼, 내용저장 모두 숨김.)
				break;
				// A/S불가 상태로 오면 	
			case abc.biz.afterservice.constCode.asStatCodeAsReject :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeAsRejectName);// 진행창에 AS불가 
				_afterServiceDetailCheck.displayStatus10012();//  그 다음 진행상태 없으므로 확인 버튼만 노출 ( 그외 철회 버튼, 내용저장 모두 숨김.)
				break;
			default:
		}
	}
	
	
	/**
	 * 수선  > 사용자, BO  접수일떄 상태별로 Disabled 영역 처리 작성
	 */
	_afterServiceDetailCheck.detailRepairDisabled = function(asStatCode,adminAcceptYn,asGbnCode){
		switch(asStatCode){
			// AS 접수 철회 하고 그 다음 진행 안됨.
			case abc.biz.afterservice.constCode.asStatCodeWithdrawalAccept :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode); //접수철회시 모든 EDIT 및 선택박스 disable
				$("#temporaryStorage").hide();// 임시저장 버튼 숨김.
				$("#withdrawAccept").hide();// 첫 화면에 노출되었던 철회 버튼 숨김.
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeWithdrawalAcceptName);// 접수철회 버튼 네이밍 
				$("#statusChange").hide(); // 그 다음 진행 없으므로 버튼 숨김처리.
				$("#close").show(); // 확인 버튼 노출 
				$("#dlvyPayCancel").hide();// 배송비 결제 취소 버튼 
				break;	
			// BO, FO 접수 하고 난후 상세화면 OPEN시 접수철회 수거지시 버튼만 노출 나머지는 숨김.
			case abc.biz.afterservice.constCode.asStatCodeAccept:
				$("#gbnCode").prop("disabled", true); // 수선일 경우 disabled
				$("#asProcContText").prop("readonly", true);// 처리내용 비활성화 시킴
				$("select[name*=asProc]").attr("disabled",true);// 처리 유형 코드 , 상세 코드 , 
				$(".LastChild").prop("disabled", true);// 택배사 및 송장번호 비활성화 처리 
				$("#asAmt").prop("readonly", true);// AS비용 비활성화
				$("#temporaryStorage").hide();// 내용저장 버튼 숨김
				$("#dlvyPayCancel").show();// 결제 취소 버튼 노출 
				$("#withdrawAccept").text(abc.biz.afterservice.constCode.asStatCodeWithdrawalAcceptName);// 접수철회 네이밍 표시 처림
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePickupOrder);// 그 다음 진행상황 수거지시 버튼 노출 
				break;
			// 수거지시  --> 수령완료 
			case abc.biz.afterservice.constCode.asStatCodePickupOrder:
				$("#gbnCode").prop("disabled", true); // 택배사 비활성화
				$("#withdrawAccept").hide();// 접수 철회 버튼 숨김
				$("#temporaryStorage").show();// 내용저장 버튼 보임.
				_afterServiceDetailCheck.displayStatus10004(adminAcceptYn,asGbnCode);// 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePickupOrderName);// 상단 진행상태에 현 상태를 네이밍 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 그 다음 진행상테 수령완료 네이밍 처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinish);	// 그 다음 진행상테 수령완료 코드  처리
				$("#hisStatVal").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#hisStatCode").val(abc.biz.afterservice.constCode.asStatCodeReceiveFinish);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#messageVal").val("후 수선 진행");// message alert 
				break;
			// 수령완료 --> 수선완료
			case abc.biz.afterservice.constCode.asStatCodeReceiveFinish:
				_afterServiceDetailCheck.displayStatus10005(adminAcceptYn,asGbnCode);// 수선완료 단계에서 비활성화 처리 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeReceiveFinishName);// 진행상태를 수령완료 표시 
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 그 다음 단계인 수선완료 바튼 표시 처리
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeRepairFinish);// 그 다음 단계인 수선완료 코드 처리 
				$("#hisStatVal").val(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				$("#hisStatCode").val(abc.biz.afterservice.constCode.asStatCodeRepairFinish);// 처리 유형이 수선불가 선택했다가 다시 다른 선택을 바꿨을경우 이전값으로 다시 SET 
				break;
			// 수선완료 --> 비용결제요청
			case abc.biz.afterservice.constCode.asStatCodeRepairFinish :
				_afterServiceDetailCheck.displayStatus10007();// 수선완료 단계에서 비활성화 처리 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 진행상태를 심의완료로 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodePaymentFequestName);// 그 다음 단계인 비용결제 요청  네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePaymentFequest);// 그 다음 단계인 비용결제요청  코드 처리 
				break;
			// 비용결제요청 --> 비용결제완료
			case abc.biz.afterservice.constCode.asStatCodePaymentFequest :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 비용결제요청 단계에서 비활성화 처리 모음 
				$("#temporaryStorage").hide();// 내용저장 버튼 숨김. 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePaymentFequestName);// 진행상태를 비용결제요청 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodePaymentFinishName);// 그 다음 단계인 비용결제완료  네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodePaymentFinish);// 그 다음 단계인 비용결제완료  코드 처리 
				break;
			// 비용결제완료 --> 배송중
			case abc.biz.afterservice.constCode.asStatCodePaymentFinish :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 비용결제완료 단계에서 비활성화 처리 모음 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodePaymentFinishName);// 진행상태를 비용결제완료 표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 그 다음 단계인 배송중   네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeShipping);// 그 다음 단계인 배송중   코드 처리  
				break;
			// 무상 A/S 일경우 11111 값을 비용 관련 항목을 건너뛰고 AS배송중 단계
			case abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// 무상 AS 이고 배송비가 존재하지 않으면 이 단계로 오고 그에 해당하는 버튼 및 edit 비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeRepairFinishName);// 진행상태를 수선완료  표시 처리
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 그 다음 단계인 배송중   네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeShipping);// 그 다음 단계인 배송중   코드 처리  
				break;
			// 배송중 -->   A/S 완료 
			case abc.biz.afterservice.constCode.asStatCodeShipping :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeShippingName);// 진행상태를 배송중으로 처리 
				$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeAsFinishName);// 그 다음 단계인 A/S 완료    네이밍  처리 
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeAsFinish);// 그 다음 단계인 A/S 완료    네이밍  처리 
				break;
			// A/S 완료 
			case abc.biz.afterservice.constCode.asStatCodeAsFinish :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeAsFinishName);// 진행창에 AS완료   
				_afterServiceDetailCheck.displayStatus10012();//  그 다음 진행상태 없으므로 확인 버튼만 노출 ( 그외 철회 버튼, 내용저장 모두 숨김.)
				break;
			// A/S불가 상태로 오면 	
			case abc.biz.afterservice.constCode.asStatCodeAsReject :
				_afterServiceDetailCheck.displayAllReadDisabled(asStatCode,adminAcceptYn,asGbnCode);// edit 가능한 부분 및 selectBox 모두비활성화 처리 
				//$("#asStatCodeNameDis").text(abc.biz.afterservice.constCode.asStatCodeAsRejectName);// 진행창에 AS불가 
				_afterServiceDetailCheck.displayStatus10012();//  그 다음 진행상태 없으므로 확인 버튼만 노출 ( 그외 철회 버튼, 내용저장 모두 숨김.)
				break;
			default:
		}
	}
	
	/**
	 * 공통 부분 한곳으로 빼기
	 * 비용 결제 , 비용 결제 완료 , A/S불가 , A/S완료 , 배송중 , 철회는 모두 disabled   혹은 readOnly 
	 */
	_afterServiceDetailCheck.displayAllReadDisabled = function(asStatCode,adminAcceptYn,asGbnCode){
		$(".Reason").prop("disabled", true); // A/S 사유 css name 으로 disabled 비활성화
		$("input[name*=buyer]").prop("readonly", true); // 회수지 정보 all 비활성화
		$("input[name*=rcvr]").prop("readonly", true);// 재수령자 all 비활성화  
		$("input[name*=Dlvr]").prop("disabled", true);  // 주소  체크 전부  비활성화
		$("#postSearchBtn").prop("disabled", true);// 회수지에 대한 우편번호 찾기 버튼 비활성화
		$("#rePostSearchBtn").prop("disabled", true);// 재수령지에 대한 우편번호 비활성화
		$("#asProcContText").prop("readonly", true);// 처리 유형 코드 , 상세 코드 , 내용 전부 비활성화
		$("select[name*=asProc]").attr("disabled",true);//내용 비활성화
		$("#asAmt").prop("readonly", true);// AS비용  비활성화
		$("select[name=addDlvyAmt]").attr("disabled",true);// 배송비 비활성화 
		$("#withdrawAccept").hide();// 접수 철회 버튼 숨김
		$("#temporaryStorage").hide();// 내용저장 버튼 숨김. 
		// 사유구분이 비용결제요청후 완료 버튼 노출되는 시점 (비용결제요청-> 완료  이 조건)
		if(asStatCode == abc.biz.afterservice.constCode.asStatCodePaymentFequest){
			$("#logisVndrCode").prop("disabled", false);// 택배사 활성화 
			$("#waybilNoText").prop("readonly", false);// 송장번호 활성화
		}else{
			$("#logisVndrCode").prop("disabled", true);// 택배사 비활성화 
			$("#waybilNoText").prop("readonly", true); // 송장번호 비활성화
		}
	}
	
	/**
	 * 수선완료 조건 
	 */
	_afterServiceDetailCheck.displayStatus10007 = function(){
		$("#withdrawAccept").hide();// 접수 철회 버튼 숨김
		$("#temporaryStorage").hide(); // 내용저장 버튼 숨김. 
		$(".Reason").prop("disabled", true);// AS사유코드 비활성 처리 
		$("input[name*=Dlvr]").prop("disabled", true);// 회수지 체크 , 재수령지 체크 박스 비활성 처리  
		$("input[name*=buyer]").prop("readonly", true);// 회수지  비활성 처리 
		$("input[name*=rcvr]").prop("readonly", true);// 재수령지 비활성 처리 
		$("#postSearchBtn").prop("disabled", true);// 회수지에 대한 우편번호 찾기 버튼 비활성화
		$("#rePostSearchBtn").prop("disabled", true);// 재수령지에 대한 우편번호 비활성화
		$("#asProcContText").prop("readonly", true);// 처리 내용 비활성 처리 
		$("select[name*=asProc]").attr("disabled",true);// 처리 유형 코드 비활성화 
		$("#asAmt").prop("readonly", true);// 비용 비활성화
		$("select[name=addDlvyAmt]").attr("disabled",true);// 배송비 비활성화 
		$("#logisVndrCode").prop("disabled", true);// 택배사 비활성화 
		$("#waybilNoText").prop("readonly", true);// 송장번호 비활성화 
	}
	
	/**
	 * 수령완료 조건 
	 */
	_afterServiceDetailCheck.displayStatus10004 = function(adminAcceptYn,asGbnCode){
		$("#withdrawAccept").hide();// 접수철회버튼 숨김
		$("input[name=Dlvr01]").prop("disabled", true);// 회수지 정보 체크 박스 비활성화 처리 
		$("input[name*=buyer]").prop("readonly", true);// 회수지 정보 비활성화 처리
		$("#postSearchBtn").prop("disabled", true);// 회수지에 대한 우편번호 찾기 버튼 비활성화
		$("#asProcContText").prop("readonly", true);// 처리 사유 내용 비활성화 처림
		$("select[name*=asProc]").attr("disabled",true);// 처리 유형 비할성화
		$("#asAmt").prop("readonly", true);// AS 비용 비활성화 
		$("#logisVndrCode").prop("disabled", true); // 택배사 비활성화
		$("#waybilNoText").prop("readonly", true); // 송장번호 비활성화
		// 사유구분이 수선이면 아래 조건 실행. 
		if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeRepair){
			$("#gbnCode").prop("disabled", true);
		}
	}
	/**
	 *  수령완료에서 심의 혹은 수선이 경우 조건 
	 */
	_afterServiceDetailCheck.displayStatus10005 = function(adminAcceptYn,asGbnCode){
		$("#withdrawAccept").hide();// 접수철회버튼 숨김
		$(".Reason").prop("disabled", true);// AS 사유코드 비활성화 처리 
		$("input[name=Dlvr01]").prop("disabled", true);// 회수지 정보 체크 박스 비활성화 처리 
		$("input[name*=buyer]").prop("readonly", true);// 회수지 정보 비활성화 처리
		$("#postSearchBtn").prop("disabled", true);// 회수지에 대한 우편번호 찾기 버튼 비활성화
		$("#asProcContText").prop("readonly", true);// 처리 사유 내용 비활성화 처림
		$("select[name*=asProc]").attr("disabled",true);// 처리 유형 비할성화
		$("#asAmt").prop("readonly", true);// AS 비용 비활성화 
		$("select[name=addDlvyAmt]").attr("disabled",false);// 배송비 활성화 
		
		if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeRepair){
			$("#asProcContText").prop("readonly", false);// 처리 사유 내용 활성화 
			$("select[name*=asProc]").attr("disabled",false);// 처리 유형 할성화
			$("#asAmt").prop("readonly", false);// AS 비용 활성화 
		}
		if(adminAcceptYn == 'N'){
			// 사용자 접수이면 결제 취소 버튼 노출 
			$("#dlvyPayCancel").show();
		}
	}
	
	/**
	 * A/S완료 , A/S불가 공통 버튼 
	 */
	_afterServiceDetailCheck.displayStatus10012 = function(){
		$("#withdrawAccept").hide(); // 철회버튼 숨김
		$("#temporaryStorage").hide(); // 임시저장 버튼 숨김 처리 
		$("#statusChange").hide(); // 완료 , 불가 버튼 숨김 
		$("#close").show(); // 확인 버튼 노출 
	}
	
})();