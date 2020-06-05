(function() {

	var _afterServiceDetail = abc.object.createNestedObject(window,"abc.biz.afterservice.detailInfo");
	
	$("input:checkbox[id='chkDeliveryModule02']").trigger("click");
	
	$("input:checkbox[id='chkDeliveryModule']").trigger("click");
	
	// ajax 처리중 alert : 중복 처리 방지
	_afterServiceDetail.processIng = false;
	
	/*************************************************************************
	 *								클레임 목록/검색 변수 선언
	 *************************************************************************/
	
	/**
	 * 내용보기 
	 */
	_afterServiceDetail.readReason = function(){
	    	var asGbnCode = $("#gbnCode option:selected").text();
	    	var asRsnCode = $("#rsnCode option:selected").text();
	    	var prdtName = $("#prdtName").val();
	    	var asAcceptContText = $("#asAcceptContText").val();
	    	var adminAcceptYn = $("#adminAcceptYn").val();
	    	var asAcceptNo = $("#asAcceptNo").val();
	    	var asAcceptPrdtSeq = $("#asAcceptPrdtSeq").val();
	    	var asStatCode = $("#asStatCode").val();

			var url = "";
			var params = { asAcceptNo:asAcceptNo 
					      ,asAcceptPrdtSeq:asAcceptPrdtSeq 
					      ,asGbnCode:asGbnCode 
					      ,asRsnCode:asRsnCode
					      ,prdtName:prdtName
					      ,asAcceptContText:asAcceptContText
					      ,adminAcceptYn:adminAcceptYn
					      ,asStatCode:asStatCode
					      };
			url = "/afterservice/afterservice/read-afterservice-reason-pop"; 
			
			abc.open.popup({
				winname :	"",
				url 	:	url,
				width 	:	800,
				height	:	500,
				params	:	params
			});
	   }
	
	/**
	 * 화면로드시 사유 선택한 값  
	 */
	_afterServiceDetail.detailAsGbnCodeSet = function(asGbnCode){
		$("#gbnCode").val(asGbnCode).attr("selected", "selected");
	}
	
	/**
	 * 사유 2차 선택한값 
	 */
	_afterServiceDetail.detailAsRsnCodeSet = function(asRsnCode){
		$("#rsnCode").val(asRsnCode).attr("selected", "selected");
	}
	
	/**
	 *  처리내용  
	 */
	_afterServiceDetail.detailAsProcTypeCodeSet = function(asProcTypeCode){
		$("#asProcTypeCode").val(asProcTypeCode).attr("selected", "selected");
	}
	
	/**
	 * 처리  2차 선택한값  
	 */ 
	_afterServiceDetail.detailAsProcTypeDtlCodeSet = function(asProcTypeDtlCode){
		$("#asProcTypeDtlCode").val(asProcTypeDtlCode).attr("selected", "selected");
		$('select[name=asProcTypeDtlCode] option').each(function() {
			if(asProcTypeDtlCode != $(this).val()) $(this).attr('disabled','disabled').hide();
		});
	}
	
	/**
	 * 배송비 FO 에서 입력한값
	 */
	_afterServiceDetail.detailAddDlvyAmtCodeSet = function(addDlvyAmt){
		$("#addDlvyAmt").val(addDlvyAmt).attr("selected", "selected");
	}
	
	/**
	 * 철회유형 
	 */
	_afterServiceDetail.detailAsWthdrawRsnCodeSet = function(asWthdrawRsnCode){
		$("#asWthdrawRsnCode").val(asWthdrawRsnCode).attr("selected", "selected");
	}
	
	/**
	 * 택배가 선택한 값
	 */
	var logisVndrCode = '<c:out value="${asInfo.logisVndrCode}"/>';
	_afterServiceDetail.detailLogisVndrCodeSet = function(logisVndrCode){
		$("#logisVndrCode").val(logisVndrCode).attr("selected", "selected");
	}
	
	/**
	 * 첫번째인지 유무를 판단
	 */
	_afterServiceDetail.statusFirst = function(){
		$('#asStatCode').val(abc.biz.afterservice.constCode.asStatCodePickupOrder);
		_afterServiceDetail.statusChange(); 
	}
	
	/**
	 *  상태별 업데이트  
	 */
	//var _mainObj = opener.parent.abc.biz.afterservice.afterservicemain.data;
	_afterServiceDetail.statusChange = function(){
		
		if(_afterServiceDetail.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		// 처리유형 코드
		var $asProcTypeCode = $("#asProcTypeCode");
		// 처리 유형 상세 코드
		var $asProcTypeDtlCode = $("#asProcTypeDtlCode");
		// 처리 내용 
		var $asProcContText = $("#asProcContText");
		// 택바사 코드 
		var $logisVndrCode = $("#logisVndrCode");
		// 송장번호
		var $waybilNoText = $("#waybilNoText");
		// AS 비용
		var $asAmt = $("#asAmt");
		// 수령자 HP 번호
		var $buyerHdphnNoText = $("#buyerHdphnNoText");
		// 재수령자 HP 번호
		var $rcvrHdphnNoText = $("#rcvrHdphnNoText");
		// 사유구분
		var asGbnCode     = $('#asGbnCode').val();
		// 얼럿 메세지 변수 
		var $messageVal =  $('#messageVal');
		// 결제한 배송비 금액
		var $asPymntAmt    = $("#pymntAmt");
		// 관리자 접수 여부 
		var adminAcceptYn = $("#adminAcceptYn").val();
		// 합계 금액
		var totalPymntAmt = $("#totalPymntAmt").val();
		// 합계 금액 display용 
		var PymntAmtDisp = Number($("#totalPymntAmtDisp").text().replace("원",''));
		
		// 수선이면서 수선완료일때 처리(심의)유형 필수 및 배송비 체크  
		if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeRepair  && $("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodeRepairFinish ){
			if(abc.text.isBlank($asProcTypeCode.val())){
				alert("처리(심의)유형을 선택해주세요.");
				$asProcTypeCode.focus();
				return false;
			}
			if(abc.text.isBlank($asProcTypeDtlCode.val())){
				alert("처리(심의)상세 코드를 선택해주세요.");
				$asProcTypeDtlCode.focus();
				return false;
			}
			if(abc.text.isBlank($asProcContText.val())){
				alert("처리(심의)내용을 선택해주세요.");
				$asProcContText.focus();
				return false;
			}
			if(abc.text.isLimitLength($asProcContText.val(),50)){
				alert("처리(심의)내용이 최대값을 초과하였습니다.");
				$asProcContText.focus();
				return false;
			}
			if($("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeRemark){
				if(abc.text.isBlank($asAmt.val())){
					alert("A/S 비용을 입력해주세요.");
					$asAmt.focus();
					return false;
				}
			}
			if($("#addDlvyAmt option:selected").val() == '선택'){
				alert("배송비를 선택해주세요.");
				$("#addDlvyAmt option:selected").focus();
				return false;
			}
			
		
		// 심의완료 상태이면 필수 체크 로직 
		}else if($("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodeJudgeFinish){
			if(abc.text.isBlank($asProcTypeCode.val())){
				alert("처리(심의)유형을 선택해주세요.");
				$asProcTypeCode.focus();
				return false;
			}
			if(abc.text.isBlank($asProcTypeDtlCode.val())){
				alert("처리(심의)상세 코드를 선택해주세요.");
				$asProcTypeDtlCode.focus();
				return false;
			}
			if(abc.text.isBlank($asProcContText.val())){
				alert("처리(심의)내용을 선택해주세요.");
				$asProcContText.focus();
				return false;
			}
			if(abc.text.isLimitLength($asProcContText.val(),50)){
				alert("처리(심의)내용이 최대값을 초과하였습니다.");
				$asProcContText.focus();
				return false;
			}
			if($("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeRemark){
				if(abc.text.isBlank($asAmt.val())){
					alert("A/S 비용을 입력해주세요.");
					$asAmt.focus();
					return false;
				}
			}
			
		
		// 사유가 심의 이면서 상태는 수선완료 이면 
		}else if(asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview && $("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodeRepairFinish ){
			if($("#addDlvyAmt option:selected").text() == "선택"){
				alert("배송비를 선택해주세요.");
				$("#addDlvyAmt option:selected").focus();
				return false;
			}
		}
		// 합계 금액 유,무에 따라 택배사 및 송장번호를 필수로 체크 로직 하는 함수 정의 
		if(!_afterServiceDetail.dlvyAmtValidation(PymntAmtDisp,adminAcceptYn)){
			return;
		}
		
		// 업데이트시 접수 기본 정보에 햊당하는 정보를 체크 하는 로직 
		if(!_afterServiceDetail.updateValidation()){
			return;
		}
			
		// 처리유형이 수선불가 일 경우 
		if($("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeRepairReject){
			var dlvyPymntYn = (Number($("#pymntAmt").val()) == 0 || $("#pymntAmt").val() == undefined) ? 'N':'Y';
			
			if(dlvyPymntYn == 'Y'){
				if(confirm("결제한 배송비가 존재합니다.\n먼저 결제를 취소 진행해주세요.")){
					return false;
				}else{
					return false;
				}
			}
			
			if(abc.text.isBlank($asProcTypeDtlCode.val())){
				alert("처리(심의)상세 코드를 선택해주세요.");
				$asProcTypeDtlCode.focus();
				return false;
			}
			if(abc.text.isBlank($asProcContText.val())){
				alert("처리(심의)내용을 선택해주세요.");
				$asProcContText.focus();
				return false;
			}
			// 수선불가 이고 반송일 경우에만 택배사 , 송장번호 필수 체크 한다.
			if($("#asProcTypeDtlCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeDtlCodeReject){
				if(abc.text.isBlank($logisVndrCode.val())){
					alert("택배사를 선택해주세요.");
					$logisVndrCode.focus();
					return false;
				}
				if(abc.text.isBlank($waybilNoText.val())){
					alert("송장번호를 입력해주세요.");
					$waybilNoText.focus();
					return false;
				}
				if(abc.text.isLimitLength($waybilNoText.val(),20)){
					alert("송장번호가  최대값을 초과하였습니다.");
					$waybilNoText.focus();
					return false;
				}
			}
		}
		// 배송비가 무료일 경우 999 를 0으로 바꿔서 DB에 INSERT 되게 바꿈. 
		$('#addDlvyAmt').val($('select[name=addDlvyAmt]').val() == '999' ? '0':$('select[name=addDlvyAmt]').val());
		
		if(confirm($('#statusChange').text()+" 처리"+$messageVal.val()+"를 하시겠습니까?")){
			
			// 2020.04.29 : ajax 중복 처리 방지 
			_afterServiceDetail.processIng = true;
			
			$.ajax({
				type:"post",
				url  : "/afterservice/afterservice/updateAfterServiceStatCode",
				data: $("#frmInfo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				// 합계 금액
				var totalPymntAmt = $('#totalPymntAmt').val();
				
				//심의 
				if(data.asGbnCode == abc.biz.afterservice.constCode.asGbnCodeReview){
					//무상 선택일때   11111 값을 비용 관련 항목을 건너뛰고 AS배송중 단계
					if(Number(totalPymntAmt) == 0 && $("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeFree && data.asStatCode == abc.biz.afterservice.constCode.asStatCodeRepairFinish){
						abc.biz.afterservice.detailInfoStatusCheck.detailReviewDisabled(abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip,data.adminAcceptYn,data.asGbnCode);
					}else{
						// 유상 , 수선불가 일때 
						abc.biz.afterservice.detailInfoStatusCheck.detailReviewDisabled(data.asStatCode,data.adminAcceptYn,data.asGbnCode);
					}
				//수선 
				}else{
					//무상 선택일때  11111 값을 비용 관련 항목을 건너뛰고 AS배송중 단계
					if(Number(totalPymntAmt) == 0 && $("#asProcTypeCode option:selected").val() == abc.biz.afterservice.constCode.asPocTypeCodeFree && data.asStatCode == abc.biz.afterservice.constCode.asStatCodeRepairFinish){
						abc.biz.afterservice.detailInfoStatusCheck.detailRepairDisabled(abc.biz.afterservice.constCode.asPocTypeCodeFreeSkip,data.adminAcceptYn,data.asGbnCode);
					}else{
						// 유상 , 수선불가 일때 
						abc.biz.afterservice.detailInfoStatusCheck.detailRepairDisabled(data.asStatCode,data.adminAcceptYn,data.asGbnCode);
					}
				}
				alert("저장되었습니다.");
				// 수선불가 > 교환 또는 불가 일경우 클레임 pop으로 넘김.
				if(data.clmNo != null && data.clmNo != ""){
					self.close();
					opener.abc.biz.afterservice.afterservicemain.openClaimDetailPop(data.clmNo,data.clmGbnCode);
				}
				location.reload();
				opener.abc.biz.afterservice.afterservicemain.afterserviceDoAction('search');
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert(jqXHR.responseJSON.message);
				
				// 2020.04.29 : ajax 중복 처리 방지 
				_afterServiceDetail.processIng = false;
			});
		}
	}
	
	/**
	 * 임시저장 temporaryStorage
	 */
	_afterServiceDetail.temporaryStorage = function(){	
		
		if(_afterServiceDetail.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		if(confirm("내용 저장 하시겠습니까?")){
			
			// 2020.04.29 : ajax 중복 처리 방지 
			_afterServiceDetail.processIng = true;
			
			$.ajax({
				type:"post",
				url  : "/afterservice/afterservice/updateAfterServiceTemporaryStorage",
				data: $("#frmInfo").serialize()
			}).done(function(data, textStatus, jqXHR) {
				alert("내용 저장  되었습니다.");
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
				
				// 2020.04.29 : ajax 중복 처리 방지 
				_afterServiceDetail.processIng = false;
			});
		}
	}
	
	/**
	 * 관리자메모등록
	 */
	_afterServiceDetail.createMemo = function(){	
		var asAcceptNo = $("#asAcceptNo").val();
		var asAcceptPrdtSeq = $("#asAcceptPrdtSeq").val();
		var memoText = $("#memoText").val();
		
		if(abc.text.allNull(memoText)){
			alert("메모를 입력하세요.");
			$("#memoText").focus();
			return false;
		}
		$.ajax({
			type :"post",
			url : "/afterservice/afterservice/create-ocAsAccept-memo",
			data : {"asAcceptNo" : asAcceptNo, "asAcceptPrdtSeq":asAcceptPrdtSeq , "memoText" : memoText},
		}).done(function(data){
			var chrHtml;
			var rgstDtm = new Date(data.memoInfo[0].rgstDtm);
			chrHtml  = "<li>";
			chrHtml += "<span class='msg-list-info'>";
			chrHtml += "<span class='user-info'>";
			chrHtml += "<span class='user-id'>"+ data.memoInfo[0].asMemoSeq  +" "+"/"+" "+ data.memoInfo[0].loginId  +"</span>";
			chrHtml += "<span class='user-name'>("+ data.memoInfo[0].adminName +")</span>";
			chrHtml += "</span>";
			chrHtml += "<span class='regist-date-wrap'>";
			chrHtml += "<span class='regist-date'>"+ rgstDtm.format() +"</span>";
			chrHtml += "<input type='hidden' value='"+ data.memoInfo[0].asMemoSeq +"'>";
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
	}
	
	/**
	 * 관리자 메모 글자수 체크 
	 */
	_afterServiceDetail.stringLengthCheck = function(obj) {
		if(obj.length <= 500){
			$('#counter').html(obj.length);
		}else{
			alert("메모는 500자를 초과할 수 없습니다.");
			$("#memoText").val(obj.substring(0, 500));
			return false;
		}
	}
	
	/**
	 * 관리자 메모 삭제
	 */
	_afterServiceDetail.removeMemo = function(idx, classNm) {
		var asMemoSeq = $("."+classNm).eq(idx).find("input").val();
		var asAcceptNo = $("#asAcceptNo").val();
		
		$.ajax({
			type :"post",
			url : "/afterservice/afterservice/update-ocAsAccept-memo",
			data : {"asAcceptNo" : asAcceptNo, "asMemoSeq" : asMemoSeq},
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
	 * 처리내용 onChange 이벤트 
	 */
	$('select[name=asProcTypeCode]').off().on('change', function() {
		// 두번째 셀렉트 박스 초기화 시킴.
		// 수선불가 체인지 대비 하기 위한 이전 버튼 값 및 코드값 가지고 있기 위함.
		$('select[name=asProcTypeDtlCode]').val('');
		var sel1Val = $(this).val();
		if(sel1Val == ""){
//			console.log("3");
			$('select[name=asProcTypeDtlCode]').val("");
			if($('#adminAcceptYn').val() == 'N' && $('#asGbnCode').val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
				$('#totalPymntAmtDisp').text(''); // 합계 초기화 
				$("#statusChange").text($("#hisStatVal").val()); // 이전 버튼 기억 값 
				$("#asStatCode").val($("#hisStatCode").val()); // 이전 버튼 기억 코드 값
				//$('#asProcContText').val('');
				$('#asAmtDisplay').text(''); // AS 비용 초기화
				$('#asAmt').val('0'); // AS 비용 초기화 
				$('#totalAsAmt').val('0'); // AS 비용 초기화
				$('#totalPymntAmt').text('0'); //  합계비용 초기화 
				$('#totalPymntAmtDisp').text(''); // 합계비용 DISPLAY 용 초기화 
				$("#asAmt").prop("readonly", false);
			}else{
				$("#statusChange").text($("#hisStatVal").val());
				$("#asStatCode").val($("#hisStatCode").val());
				//$('#asProcContText').val('');
				$('#asAmtDisplay').text('');
				$('#asAmt').val('0');
				$('#totalAsAmt').val('0');
				$('#totalPymntAmt').val('0');
				$('#totalPymntAmtDisp').text('');
				$('select[name=addDlvyAmt]').val('');
				$("#asAmt").prop("readonly", false);
			}
		}else{
//			console.log("4");
			$("#statusChange").text($("#hisStatVal").val());
			$("#asStatCode").val($("#hisStatCode").val());
			if($("#asProcTypeCode option:selected").val() ==  abc.biz.afterservice.constCode.asPocTypeCodeRemark){
//				console.log("5");
				// 유상일때만 금액 활성화 
				$("#asAmt").prop("disabled", false);
				$("#addDlvyAmt option:eq(1)").replaceWith("<option value='0'>무료</option>");
			}else if($("#asProcTypeCode option:selected").val() ==  abc.biz.afterservice.constCode.asPocTypeCodeRepairReject){
//				console.log("6");
				$("#addDlvyAmt option:eq(1)").replaceWith("<option value='0'>무료</option>");
				//수선불가일때
				$("#statusChange").text("A/S불가");
				$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeAsReject);
				$("#asAmt").prop("disabled", true);
				$('#totalPymntAmtDisp').text('');
				$('#asAmtDisplay').text('');
				$('#asAmt').val('');
				// FO 접수건이면서 수선일경우
				if($('#adminAcceptYn').val() == 'N' && $('#asGbnCode').val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
//					console.log("7");
					// FO 접수 이면서 결제한 금액이 존재하고   수선이면  
					if($('#adminAcceptYn').val() == 'N' && Number($('#pymntAmt').val()) > 0 && $('#asGbnCode').val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
//						console.log("8");
					}else{
//						console.log("9");
						$('#totalPymntAmtDisp').text('');
					}
//					console.log("10");
					$('#asAmtDisplay').text('');
					$('#asAmt').val('');
				}
			}else{
//				console.log("11");
				$("#addDlvyAmt option:eq(1)").replaceWith("<option value='0'>무료</option>");
				// 그외 비활성화 
				$('#asAmt').val("");
				$('#asAmtDisplay').text('');
				$('select[name=addDlvyAmt]').val('');
				$("#asAmt").prop("disabled", true);
				
				if($('#adminAcceptYn').val() == 'N' && Number($('#pymntAmt').val()) > 0 && $('#asGbnCode').val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
//					console.log("12");
				}else{
//					console.log("13");
					$('#totalPymntAmtDisp').text('');
				}
//				console.log("14");
			}
			// 처리내용이 선택이 되었을때만 show 
			$('select[name=asProcTypeDtlCode] option').each(function() {
				$(this).removeAttr('disabled').show();
				if(sel1Val != $(this).data('info')){
					$(this).attr('disabled','disabled').hide();
				}
			});
			$('select[name=asProcTypeDtlCode]').removeAttr('disabled').show();
		}
	});
	
	/**
	 * 상세코드 입력시 금액 표시 
	 */
	
	$('select[name=asProcTypeDtlCode]').off().on('change', function() {
		var sel2Val = $(this).val();
		//수선불가일 경우 하단 버튼을 불가 버튼으로 바꾸고 그 다은 진행 안되게 불가 상태를 전달. 
		if($('select[name=asProcTypeCode]').val() == abc.biz.afterservice.constCode.asPocTypeCodeRepairReject){
			//반송 , 교환, 반품 
			$("#statusChange").text(abc.biz.afterservice.constCode.asStatCodeAsRejectName);
			$("#asStatCode").val(abc.biz.afterservice.constCode.asStatCodeAsReject);
		}
		$(this).find('option').each(function() {
			// 유상 금액이 존재할때만 금액을 AS비용 하단에 넣어줌. 
			if($(this).data('price') != '') {
				if($(this).val() == sel2Val) {
					$('#asAmt').val($(this).data('price'));
					$('#totalAsAmt').val($(this).data('price'));
					$('#asAmtDisplay').text($(this).data('price')+'원');
					// FO 접수건이면서 수선인경우 선결제한 배송비 제외한  A/S 비용만  합계에 보여주는 로직
					if($('#adminAcceptYn').val() == 'N' && $('#asGbnCode').val() == abc.biz.afterservice.constCode.asGbnCodeRepair){
						$('#totalPymntAmtDisp').text(Number($('#asAmt').val())+'원');	
						$('#totalPymntAmt').val($('#totalPymntAmtDisp').text().replace('원',''));
					}else{
						// 옵션이 체인지 될때 배송비 플러스 A/S 비용 합산 
						$('#totalPymntAmtDisp').text(Number($('select[name=addDlvyAmt]').val() == 999 ? 0:$('select[name=addDlvyAmt]').val())+Number($('#asAmt').val())+'원');
						$('#totalPymntAmt').val($('#totalPymntAmtDisp').text().replace('원',''));
					}
				}
			}else{
				// 수선불가 이면서 교환 또는 반품인 경우  클레임쪽으로 넘김 송장번호 불필요
				if(sel2Val == abc.biz.afterservice.constCode.asPocTypeDtlCodeChange || sel2Val == abc.biz.afterservice.constCode.asPocTypeDtlCodeReturn ){
					$("#logisVndrCode").prop("disabled", true);// 택배사 비활성화 
					$("#waybilNoText").prop("readonly", true); // 송장번호 비활성화
					$("#logisVndrCode").val('');// 택배사 선택했던 값 초기화  
					$("#waybilNoText").val(''); // 송장번호 입력했던 값 초기화 
				}else{
					$("#logisVndrCode").prop("disabled", false);// 택배사 활성화
					$("#waybilNoText").prop("readonly", false); // 송장번호 활성화
				}
			}
		});
	});
	
	/**
	 * 배송비 체인지 시 합꼐 금액 표기 
	 */
	$('select[name=addDlvyAmt]').off().on('change', function() {
		//console.log("1");
		// selectBox 배송비 금액 
		var selAmtVal = $(this).val();
		if(selAmtVal == "999"){
			//console.log("999 ::" + selAmtVal);
			selAmtVal = "0";
		}
		//console.log("2 ::" + selAmtVal);
		// as비용 
		var asAmtVal = $('#asAmt').val();
		//console.log("3 ::" + asAmtVal);
		if(selAmtVal == ''){
			//console.log("4 ::" + asAmtVal);
			if($('#asAmt').val() != '' && $('#asAmt').val() != null){
				//console.log("5 ::" + asAmtVal);
				$('#totalPymntAmtDisp').text($('#asAmt').val()+'원');
				$('#totalPymntAmt').val($('#totalPymntAmtDisp').text());
			}else{
				//console.log("6 ::" + asAmtVal);
				$('#totalPymntAmtDisp').text('');
			}
			//console.log("7 ::" + asAmtVal);
		}else{
			//console.log("8 ::" + asAmtVal);
			// 옵션이 체인지 될때 배송비 플러스 A/S 비용 합산 
			$('#totalPymntAmtDisp').text(Number($('select[name=addDlvyAmt]').val() == 999 ? 0:$('select[name=addDlvyAmt]').val())+Number($('#asAmt').val())+'원');
			$('#totalPymntAmt').val($('#totalPymntAmtDisp').text().replace('원',''));
		}
	});
	
	/**
	 * 수거지시 상태 에서 심의 --> 수선으로 변경시  
	 */
	$("#gbnCode").change(function() {
		$("#asGbnCode").val($(this).val());
	});
	
	/**
	 * 사유 상세 코드 수정
	 */
	$("#rsnCode").change(function() {
		$("#asRsnCode").val($(this).val());
	});
	
	/**
	 *  업데이트시 기본적인 사항 체크 하는 로직 
	 */
	_afterServiceDetail.updateValidation  = function(){
		var $buyerPostCodeText = $("#buyerPostCodeText");
		var $buyerPostAddrText = $("#buyerPostAddrText");
		var $buyerDtlAddrText = $("#buyerDtlAddrText");
		var $buyerName = $("#buyerName");
		var $buyerHdphnNoText = $("#buyerHdphnNoText");
		var $rcvrPostCodeText = $("#rcvrPostCodeText");
		var $rcvrPostAddrText = $("#rcvrPostAddrText");
		var $rcvrDtlAddrText = $("#rcvrDtlAddrText");
		var $rcvrName = $("#rcvrName");
		var $rcvrHdphnNoText = $("#rcvrHdphnNoText");
		
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
		
		return true;
	}
	
	/**
	 * 배송비 + A/S 비용이 0일경우 체크 로직 
	 */
	_afterServiceDetail.dlvyAmtValidation  = function(PymntAmtDisp,adminAcceptYn){
		// 택바사 코드 
		var $logisVndrCode = $("#logisVndrCode");
		// 송장번호
		var $waybilNoText = $("#waybilNoText");
		// FO 접수건 시작 
		if(adminAcceptYn == 'N'){
			// 배송비 + A/S 비용이 없으면 시작 
			if($("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodeRepairFinish && PymntAmtDisp == 0){
				//FO접수건 비용없음 A/S : ○(*)
				if(abc.text.isBlank($logisVndrCode.val())){
					alert("택배사를 선택해주세요.");
					$logisVndrCode.focus();
					return false;
				}
				if(abc.text.isBlank($waybilNoText.val())){
					alert("송장번호를 입력해주세요.");
					$waybilNoText.focus();
					return false;
				}
				if(abc.text.isLimitLength($waybilNoText.val(),20)){
					alert("송장번호가  최대값을 초과하였습니다.");
					$waybilNoText.focus();
					return false;
				}
			}else if($("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodePaymentFinish && PymntAmtDisp != 0 ){
				if(abc.text.isBlank($logisVndrCode.val())){
					alert("택배사를 선택해주세요.");
					$logisVndrCode.focus();
					return false;
				}
				if(abc.text.isBlank($waybilNoText.val())){
					alert("송장번호를 입력해주세요.");
					$waybilNoText.focus();
					return false;
				}
				if(abc.text.isLimitLength($waybilNoText.val(),20)){
					alert("송장번호가  최대값을 초과하였습니다.");
					$waybilNoText.focus();
					return false;
				}
			}
		}else{
			if($("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodeRepairFinish && PymntAmtDisp == 0){
					//*BO접수건 비용없음 : ○(*)
					if(abc.text.isBlank($logisVndrCode.val())){
						alert("택배사를 선택해주세요.");
						$logisVndrCode.focus();
						return false;
					}
					if(abc.text.isBlank($waybilNoText.val())){
						alert("송장번호를 입력해주세요.");
						$waybilNoText.focus();
						return false;
					}
					if(abc.text.isLimitLength($waybilNoText.val(),20)){
						alert("송장번호가  최대값을 초과하였습니다.");
						$waybilNoText.focus();
						return false;
					}
					if($("#addDlvyAmt option:selected").val() == '선택'){
						alert("배송비를 선택해주세요.");
						$("#addDlvyAmt option:selected").focus();
						return false;
					}
			}else if($("#asStatCode").val() == abc.biz.afterservice.constCode.asStatCodePaymentFinish && PymntAmtDisp != 0){
				if(abc.text.isBlank($logisVndrCode.val())){
					alert("택배사를 선택해주세요.");
					$logisVndrCode.focus();
					return false;
				}
				if(abc.text.isBlank($waybilNoText.val())){
					alert("송장번호를 입력해주세요.");
					$waybilNoText.focus();
					return false;
				}
				if(abc.text.isLimitLength($waybilNoText.val(),20)){
					alert("송장번호가  최대값을 초과하였습니다.");
					$waybilNoText.focus();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 배송비 결제 취소 
	 */
	_afterServiceDetail.dlvyPayCancel = function() {
		
		if(_afterServiceDetail.processIng) {
			alert('처리중입니다.');
			return false;
		}
		
		var asAcceptNo = $("#asAcceptNo").val();
		var asPymntSeq = $("#asPymntSeq").val();
		if(confirm("배송비 결제 취소시 배송비 재결제가 불가하며,\n결제하신 결제수단으로 배송비 취소 및 환불처리로 변경 됩니다. \n결제를 취소하시겠습니까?")){
			
			// 2020.04.29 : ajax 중복 처리 방지 
			_afterServiceDetail.processIng = true;
			
			$.ajax({
				type :"post",
				url : "/afterservice/afterservice/update-ocAsPayment-cancel",
				data : {"asAcceptNo" : asAcceptNo, "asPymntSeq" : asPymntSeq},
			}).done(function(data){
				alert("결제가 정상적으로 취소되었습니다.");
				//TODO
				location.reload();
			})
			.fail(function(e){
				alert("배송비 결제 취소에  실패하였습니다.");
				
				// 2020.04.29 : ajax 중복 처리 방지 
				_afterServiceDetail.processIng = false;
			});
		}
	}
})();