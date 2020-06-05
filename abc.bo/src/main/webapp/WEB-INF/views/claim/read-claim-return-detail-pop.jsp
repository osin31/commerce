<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="nowDate" value="${now}" pattern="${Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS}" />

<%-- S : KCP 결제관련 --%>
<script type="text/javascript" src="<c:out value='${kcpJsUrl}'/>"></script>
<script type="text/javascript">
	/****************************************************************/
	/* m_Completepayment  설명                                      */
	/****************************************************************/
	/* 인증완료시 재귀 함수                                         */
	/* 해당 함수명은 절대 변경하면 안됩니다.                        */
	/* 해당 함수의 위치는 payplus.js 보다먼저 선언되어여 합니다.    */
	/* Web 방식의 경우 리턴 값이 form 으로 넘어옴                   */
	/****************************************************************/
	function m_Completepayment( FormOrJson, closeEvent ) {
		var frm = document.order_info;

		/********************************************************************/
		/* FormOrJson은 가맹점 임의 활용 금지                               */
        /* frm 값에 FormOrJson 값이 설정 됨 frm 값으로 활용 하셔야 됩니다.  */
        /* FormOrJson 값을 활용 하시려면 기술지원팀으로 문의바랍니다.       */
        /********************************************************************/
	    GetField( frm, FormOrJson );

		if(frm.res_cd.value == "0000") {
			/* 가맹점 리턴값 처리 영역 */
			$("#claimProductForm input[name=siteCd]").val($("#order_info input[name=site_cd]").val());
			$("#claimProductForm input[name=reqTx]").val($("#order_info input[name=req_tx]").val());
			$("#claimProductForm input[name=goodMny]").val($("#order_info input[name=good_mny]").val());
			$("#claimProductForm input[name=ordrIdxx]").val($("#order_info input[name=ordr_idxx]").val());
			$("#claimProductForm input[name=encInfo]").val($("#order_info input[name=enc_info]").val());
			$("#claimProductForm input[name=encData]").val($("#order_info input[name=enc_data]").val());
			$("#claimProductForm input[name=tranCd]").val($("#order_info input[name=tran_cd]").val());
			$("#claimProductForm input[name=usePayMethod]").val($("#order_info input[name=use_pay_method]").val());
	        
			closeEvent();
			
			if($("#order_info input[name=requestDiv]").val() == "new") {
				abc.biz.claim.detail.saveClaimDeliveryInfo(); // 접수기본정보 및 배송비 저장(가상계좌 배송비 신규발급)
			} else if($("#order_info input[name=requestDiv]").val() == "again") {
				abc.biz.claim.detail.requestVirtualAccountAgain(); // 배송비 가상계좌 재발급
			} else if($("#order_info input[name=requestDiv]").val() == "point") {
				abc.biz.claim.detail.acceptClaimRedempPointProc(); // 환수포인트 가상계좌발급
			} else if($("#order_info input[name=requestDiv]").val() == "pointagain") {
				abc.biz.claim.detail.requestVirtualAccountPointAgain(); // 환수포인트 가상계좌 재발급
			}
		} else {
			alert( "[" + frm.res_cd.value + "] " + frm.res_msg.value );
			closeEvent();
		}
	}

	/* 표준웹 실행 */
	function jsf__pay( form ) {
		try
		{
			KCP_Pay_Execute( form );
		}
		catch (e)
		{
			/* IE 에서 결제 정상종료시 throw로 스크립트 종료 */
		}
	}
</script>
<%-- E : KCP 결제관련 --%>

<script type="text/javascript">
	$(function() {
		<%-- Const 지정 --%>
		abc.biz.claim.detail.consts.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT = "${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}"; // 추가배송비결제방법 : 추가결제
		abc.biz.claim.detail.consts.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON = "${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON}"; // 추가배송비결제방법 : 무료쿠폰
		abc.biz.claim.detail.consts.VNDR_GBN_TYPE_V = "${Const.VNDR_GBN_TYPE_V}"; // 업체구분코드 : 업체
		
		abc.biz.claim.detail.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.detail.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.detail.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.detail.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.claim.detail.CLM_PROC_TYPE_CODE_RETURN_BAD            = "${CommonCode.CLM_PROC_TYPE_CODE_RETURN_BAD}";
		abc.biz.claim.detail.CLM_PROC_TYPE_CODE_RETURN_WRONG_DELIVERY = "${CommonCode.CLM_PROC_TYPE_CODE_RETURN_WRONG_DELIVERY}";
		abc.biz.claim.detail.PYMNT_STAT_CODE_PAYMENT_FINISH			  = "${CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH}";
		
		<%-- 일괄적용 처리용 콤보박스 변경 --%>
		$("#codeDivSelect").change(function() {
			abc.biz.claim.detail.changeComboForBatchApply();
		});
		
		<%-- 일괄적용 --%>
		$("#batchApplyBtn").click(function(e) {
		//$(document).on("click", "#batchApplyBtn", function(e) {
			e.preventDefault();
			abc.biz.claim.detail.batchApplyProduct();
		});
		
		<%-- 선택한 상품 일괄 적용(특수문자 제거) --%>
		$("#inputReasonText").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 클레임 상품 저장 --%>
		$("#claimProductSaveBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.saveClaimProduct();
		});
		
		<%-- 고객알림내용 메모 입력 제한(특수문자 제거) --%>
		$("textarea[name='cstmrAlertContText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 회수지 우편번호 찾기 --%>
	    $("#retrievalPostSearchBtn").click(function() {
	        abc.postPopup(abc.biz.claim.detail.setRetrievalPost);
	    });
	
	    <%-- 배송주소와 동일 체크박스 클릭 --%>
		$("#chkDeliveryModule").click(function() {
			abc.biz.claim.detail.toggleSameDelivery(this);
	    });
		
		<%-- 회수지 우편번호 입력 제한(숫자만) --%>
		$("#buyerPostCodeText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 회수지 연락처 입력 제한(숫자만) --%>
		$("#buyerHdphnNoText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 동봉배송비 입력 제한(숫자만) --%>
		$("#eclsDlvyAmt").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 접수기본정보 및 배송비 저장 --%>
		$("#deliveryInfoSaveBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.saveClaimDeliveryInfoPreProc();
		});
		
		<%-- 동봉 배송비 저장 --%>
		$("#eclsDlvyAmtSaveBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.saveEclsDlvyAmt();
		});
		
		<%-- 2020.02.26 추가 '환불금차감' 일시에만 가능 --%>
		<%-- 배송비 환불금차감으로 반품완료했으나 차감한 배송비를 결제취소해서 환불해주는 버튼 --%>
		$("#refundDlvyAmtPymntCancelBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.cancelRefundDlvyAmtPymnt();
		});
		
		<%-- 체크박스 전체 체크/해제 --%>
		$("#chkClaimBatchAll").click(function() {
			abc.biz.claim.detail.toggleCheckAll("chkClaimBatchAll", "chkTarget");
	    });
		
		<%-- 배송비 환불금차감, 선결제 선택 시 폼 제어 --%>
		$("#radioPayment01, #radioPayment02").click(function() {
			abc.biz.claim.detail.toggleDeliveryAmtForm(this);
	    });
		
		// 화면 로드 시 반품사유 콤보 표현 이벤트 강제 발생
		//$("#codeDivSelect").change();
		
		// 화면 로드 시 배송비 환불금차감 선택 폼 제어 이벤트 강제 발생
		$("#radioPayment01").click();
		
		<%-- 클레임 메모 등록 --%>
		$("#registClaimMemoBtn").click(function() {
			abc.biz.claim.detail.registClaimMemo();
	    });
		
		<%-- 클레임 메모 입력 제한(특수문자 제거) --%>
		/* $("textarea[name='memoText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		}); */
		
		<%-- 클레임 메모 삭제 : ajax 화면 호출 후 dom에 호출화면의 이벤트 트리거 없이 document로 접근  --%>
		$(document).on("click", ".btn-msg-list-del", function(e) {
			e.preventDefault();
			abc.biz.claim.detail.deleteClaimMemo($(".btn-msg-list-del").index($(this)));
	    });
		
		<%-- 미처리  --%>
		$("#unProcYn").click(function() {
			abc.biz.claim.detail.updateClaimUnProcYn();
	    });
		
	    <%-- 접수철회  --%>
		$("#clmWthdrawBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.updateClaimWthdraw();
	    });
		
		<%-- 수거지시  --%>
		$("#clmRetrievalBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.requestClaimRetrieval();
	    });
		
		<%-- 수령완료  --%>
		$("#clmReceiptBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.finishclaimReceipt();
	    });
		
		<%-- 심의완료  --%>
		$("#clmJudgeBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.finishClaimJudge();
	    });
		
		<%-- 반품완료  --%>
		$("#clmFinishBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.finishClaimAllProc();
	    });
	    
	    <%-- 포인트환수접수  --%>
		$("#clmRedempPointBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.acceptClaimRedempPointPreProc();
	    });
		
		<%-- 환수포인트 가상계좌재발급  --%>
		$("#requestVirtualAccountRdempPointBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.requestVirtualAccountPointAgainPreProc();
	    });
		
		<%-- 반품배송비 가상계좌재발급  --%>
		$("#requestVirtualAccountBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.requestVirtualAccountAgainPreProc();
	    });
		
		<%-- 반품배송비  결제취소  --%>
		$("#cancelAddDeliveryAmtBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.cancelAddDeliveryAmt("pay");
	    });
		
		<%-- 반품배송비  무료쿠폰 사용복원  --%>
		$("#cancelAddDeliveryCouponBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.cancelAddDeliveryAmt("coupon");
        });
		
        <%-- 반품배송비  환불금차감 무료변경  --%>
		$("#cancelAddDeliveryRefundDeductionBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.cancelAddDeliveryAmt("refund");
        });
		
		<%-- 점간이동여부  --%>
		$("#radioBranchMove01, #radioBranchMove02").click(function() {
			//abc.biz.claim.detail.updateBranchMoveCode($(this).val());
        });
		
		<%-- 클레임 메모 목록 ajax 호출 --%>
		abc.biz.claim.detail.getClaimMemoList();
		
		<%-- 취소 설정금액 초기화  --%>
		$("#resetCnclAmtBtn").click(function(e) {
			abc.biz.claim.detail.resetCnclAmt();
        });
		
		<%-- 결제수단별 취소금 입력 제한(숫자만) --%>
		$("input[name='pymntAmt']").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 계좌번호 입력 제한(숫자만) --%>
		$("#rfndAcntText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 환불계좌 소유주명 입력제한 --%>
		$("#acntHldrName").off().on('keyup', function() {
			// 한글,영문입력, 띄어쓰기 가능
			if(!(event.keyCode >=37 && event.keyCode<=40)){
				var inputVal = $(this).val();
				$("#"+this.id).val(inputVal.replace(/[^ㄱ-힣a-zA-Z\ ]/gi,''));
			}
		});
		
		<%-- 계좌변경 --%>
		$("#accountChangeBtn").click(function(e) {
			e.preventDefault();
			$("#changeAccountArea").show();
        });
		
		<%-- 계좌인증 --%>
		$("#accountCertifyBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.accountCertify(this);
        });
		
		$("a[name='prdtBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.prdtBoPop(this);
        });
		
		$("a[name='prdtFo']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.prdtFo(this);
        });
		
		$("a[name='vndrBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.vndrBoPop(this);
		});
		
		$(".getMemberDetail").click(function(e) {
			e.preventDefault();
			abc.memberDetailPopup( $(this).attr("memberno") );
        });
		
		$(".getAdminDetail").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.getAdminPop();
		});
		
		$(".getOrderDetail").click(function(e) {
			e.preventDefault();
			abc.biz.claim.detail.getOrderDetail()
		});
		
		<%-- 버튼, 입력 영역 제어 --%>
		uiControlSet();
		
		<%-- 2020.03.03 : 상품전체 체크박스 클릭 --%>
		$("#chkClaimBatchAll").trigger("click");
		
	});
</script>

<script type="text/javascript">
	function uiControlSet() {
		<c:choose>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT}"><%-- 반품접수 --%>
				<%-- 저장버튼 --%>
				$("#claimProductSaveBtn").show(); // 상품정보저장 버튼 show
				$("#deliveryInfoSaveBtn").show(); // 접수기본정보저장 버튼 show
				<%-- 상태변경버튼 --%>
				$("#clmWthdrawBtn").show(); // 반품접수철회
				$("#clmRetrievalBtn").show(); // 수거지시
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT_CANCEL}"><%-- 반품접수취소 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT}"><%-- 반품접수철회 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER}"><%-- 수거지시 --%>
				<%-- 저장버튼 --%>
				$("#claimProductSaveBtn").show(); // 상품정보저장 버튼 show
				$("#deliveryInfoSaveBtn").show(); // 접수기본정보저장 버튼 show
				<%-- 상태변경버튼 --%>
				$("#clmWthdrawBtn").show(); // 반품접수철회 2020.03.10 : 수거지시에서도 철회 가능하도록 추가
				$("#clmReceiptBtn").show(); // 수령완료
				<%-- 입력폼 --%>
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH}"><%-- 수령완료 --%>
				<%-- 저장버튼 --%>
				$("#claimProductSaveBtn").show(); // 상품정보저장 버튼 show
				$("#deliveryInfoSaveBtn").show(); // 접수기본정보저장 버튼 show
				<%-- 상태변경버튼 --%>
				$("#clmJudgeBtn").show(); // 심의완료
				<%-- 입력폼 --%>
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH}"><%-- 심의완료 --%>
				<%-- 저장버튼 --%>
				$("#claimProductSaveBtn").show(); // 상품정보저장 버튼 show
				<%-- 상태변경버튼 --%>
				$("#clmRedempPointBtn").show(); // 포인트환수접수
				$("#clmFinishBtn").show(); // 반품완료
				<%-- 입력폼 --%>
				<%--clmRsnInputDisable();--%> // 클레임유형,사유 입력 비활성화 // 2020.03.19 심의완료에도 수정 가능하게 변경
				recptYnInputDisable(); // 수령 입력 비활성화
				<%--clmProcTypeInputDisable();--%> // 처리(심의)유형/내용 비활성화 // 2020.03.19 심의완료에도 수정 가능하게 변경
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_REDEPTION_ACCEPT}"><%-- 환수접수 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT}"><%-- 환불접수 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_FINISH}"><%-- 반품완료 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE}"><%-- 반품불가 --%>
				<%-- 입력폼 --%>
				clmRsnInputDisable(); // 클레임유형,사유 입력 비활성화
				recptYnInputDisable(); // 수령 입력 비활성화
				clmProcTypeInputDisable(); // 처리(심의)유형/내용 비활성화
				retrievalInputObjectDisable(); // 회수지 입력 비활성화
				clmDlvyBtnDisable(); // 클레임 배송비 관련 버튼 비활성화
			</c:when>
		</c:choose>
	}
	
	// 회수지 입력 비활성화
	function retrievalInputObjectDisable() {
		$("#buyerPostCodeText").prop("disabled", true); // 우편번호 input
		$("#retrievalPostSearchBtn").hide(); // 우편번호찾기 button
		$("#chkDeliveryModule").prop("disabled", true); // 배송주소동일 check
		$("#buyerPostAddrText").prop("disabled", true); // 주소 input
		$("#buyerDtlAddrText").prop("disabled", true); // 상세주소 input
		$("#buyerName").prop("disabled", true); // 발송인명 input
		$("#buyerHdphnNoText").prop("disabled", true); // 발송인연락처 input
	}
	
	// 클레임유형,사유 입력 비활성화
	function clmRsnInputDisable() {
		$("#claimProductForm select[name=clmRsnCode]").prop("disabled", true); // 클레임유형 select
		$("#claimProductForm textarea[name=clmEtcRsnText]").prop("disabled", true); // 클레임사유 textarea
	}
	
	// 수령 입력 비활성화
	function recptYnInputDisable() {
		$("#claimProductForm input[name=chkRecptYn]").prop("disabled", true); // 수령 check
		$("#claimProductForm input[name=recptYn]").prop("disabled", true); // 수령 input
	}
	
	// 처리(심의)유형/내용 비활성화
	function clmProcTypeInputDisable() {
		$("#claimProductForm select[name=clmProcTypeCode]").prop("disabled", true); // 수령 select
		$("#claimProductForm textarea[name=clmProcContText]").prop("disabled", true); // 수령 textarea
	}
	
	// 클레임 배송비 관련 버튼 비활성화
	function clmDlvyBtnDisable() {
		//$("#cancelAddDeliveryAmtBtn").hide(); // 결제취소 버튼
		$("#cancelAddDeliveryRefundDeductionBtn").hide(); // 무료변경 버튼
		//$("#cancelAddDeliveryCouponBtn").hide(); // 쿠폰사용복원 버튼
		$("#requestVirtualAccountBtn").hide(); // 배송비 가상계좌재발급 버튼
		$("#requestVirtualAccountRdempPointBtn").hide(); // 환수포인트 가상계좌재발급 버튼
		$("#eclsDlvyAmtSaveBtn").hide(); // 동봉배송비저장 버튼
	}
	
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>반품 상세</h1>
		</div>
		<div class="window-content">
		
			<%-- 반품완료 전 체크하기위해 --%>
			<input type="hidden" id="addDlvyAmtPymntType" value="${claimInfo.addDlvyAmtPymntType}"/>
			<input type="hidden" id="addDlvyAmtPymntStatCode" value="${publicAddDlvyPymntInfo.pymntStatCode}"/>
			
			<%-- S : claimProductForm --%>
			<form id="claimProductForm" name="claimProductForm" method="post">
			<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${claimInfo.orgOrderNo}"/>
			<input type="hidden" id="clmNo" name="clmNo" value="${claimInfo.clmNo}"/>
			<input type="hidden" id="clmStatCode" name="clmStatCode" value="${claimInfo.clmStatCode}"/>
			<input type="hidden" id="clmStatCodeName" name="clmStatCodeName" value="${claimInfo.clmStatCodeName}"/>
			<input type="hidden" id="clmGbnCode" name="clmGbnCode" value="${claimInfo.clmGbnCode}"/>
			<input type="hidden" id="clmPymntSeq" name="clmPymntSeq" value="${addDeliveryPaymentInfo.clmPymntSeq}"/>
			<input type="hidden" id="vndrGbnType" name="vndrGbnType" value="${claimInfo.vndrGbnType}"/>
			
			<input type="hidden" id="order_rcvrName" name="order_rcvrName" value="${orderInfo.rcvrName}"/>
			<input type="hidden" id="order_rcvrHdphnNoText" name="order_rcvrHdphnNoText" value="${fn:trim(fn:replace(orderInfo.rcvrHdphnNoText, '-', ''))}"/>
			<input type="hidden" id="order_rcvrPostCodeText" name="order_rcvrPostCodeText" value="${fn:trim(fn:replace(orderInfo.rcvrPostCodeText, '-', ''))}"/>
			<input type="hidden" id="order_rcvrPostAddrText" name="order_rcvrPostAddrText" value="${orderInfo.rcvrPostAddrText}"/>
			<input type="hidden" id="order_rcvrDtlAddrText" name="order_rcvrDtlAddrText" value="${orderInfo.rcvrDtlAddrText}"/>
			
			<input type="hidden" id="chngInputCnclAmtYn" name="chngInputCnclAmtYn" value="N"/><%-- 결제수단별 취소금액 변경 여부 --%>
			<input type="hidden" id="siteNo" name="siteNo" value="${claimInfo.siteNo}"/>
			
			<%-- 환수포인트 관련 제어 값 설정 --%>
			<c:set var="redempSavePointPaymentProcYn" value="N" /><%-- 환수포인트 결제 처리 완료 여부 --%>
			<c:set var="redempSavePointBtnShowYn" value="N" /><%-- 환수포인트 처리 버튼 show 여부 --%>
			<c:choose>
				<%-- 환수포인트가 없는 경우 --%>
				<c:when test="${claimInfo.redempSavePoint eq '0'}">
					<c:set var="redempSavePointPaymentProcYn" value="Y" />
					<c:set var="redempSavePointBtnShowYn" value="N" />
				</c:when>
				<%-- 환수포인트가 발생된 경우 --%>
				<c:otherwise>
					<c:choose>
						<c:when test="${redempPointPaymentInfo eq null}">
							<%-- 환수포인트가 결제 내역이 없는 경우 --%>
							<c:set var="redempSavePointPaymentProcYn" value="N" />
							<c:set var="redempSavePointBtnShowYn" value="Y" />
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${redempPointPaymentInfo.redempAmtRndmProcYn eq Const.BOOLEAN_TRUE}">
									<%-- 임의처리 경우 --%>
									<c:set var="redempSavePointPaymentProcYn" value="Y" />
									<c:set var="redempSavePointBtnShowYn" value="N" />
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${redempPointPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE}">
											<%-- 결제완료인 경우 --%>
											<c:set var="redempSavePointPaymentProcYn" value="Y" />
											<c:set var="redempSavePointBtnShowYn" value="N" />
										</c:when>
										<c:otherwise>
											<c:set var="redempSavePointPaymentProcYn" value="N" />
											<c:set var="redempSavePointBtnShowYn" value="N" />
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="redempSavePointPaymentProcYn" value="${redempSavePointPaymentProcYn}" /><%-- 환수포인트 결제 처리 완료 여부 --%>
			
			<%-- 클레임배송비 결제여부 set --%>
			<c:set var="addDlvyPayFinishYn" value="Y"/>
			<%-- 가상계좌 입금대기인 경우 클레임배송비 결제하지 않음으로 표현 --%>
			<c:if test="${claimInfo.addDlvyAmt ne '0'}">
				<c:if test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}"><%-- 선결제 --%>
					<c:if test="${addDeliveryPaymentInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}"><%-- 가상계좌 --%>
						<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT}"><%-- 입금대기 --%>
							<c:set var="addDlvyPayFinishYn" value="N"/>
						</c:if>
					</c:if>
				</c:if>
			</c:if>
			<input type="hidden" id="addDlvyPayFinishYn" name="addDlvyPayFinishYn" value="${addDlvyPayFinishYn}"/>
			
			<%-- KCP 인증 전달용 --%>
			<input type="hidden" id="siteCd" name="siteCd" value=""/>
			<input type="hidden" id="reqTx" name="reqTx" value=""/>
			<input type="hidden" id="goodMny" name="goodMny" value=""/>
			<input type="hidden" id="ordrIdxx" name="ordrIdxx" value=""/>
			<input type="hidden" id="encInfo" name="encInfo" value=""/>
			<input type="hidden" id="encData" name="encData" value=""/>
			<input type="hidden" id="retPayMethod" name="retPayMethod" value=""/>
			<input type="hidden" id="tranCd" name="tranCd" value=""/>
			<input type="hidden" id="usePayMethod" name="usePayMethod" value=""/>
		
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<c:choose>
						<c:when test="${claimInfo.memberNo eq Const.NON_MEMBER_NO}">
							<h3 class="content-title">[클레임번호 <c:out value="${claimInfo.clmNo}"/>] <span>${claimInfo.memberName}</span>님의 반품 정보</h3>
						</c:when>
						<c:otherwise>
							<h3 class="content-title">[클레임번호 <c:out value="${claimInfo.clmNo}"/>] <a href="javascript:void(0)" class="link getMemberDetail" memberno="${claimInfo.memberNo}"><span>${claimInfo.loginId}</span> (<span>${claimInfo.memberName}</span>)</a>님의 반품 정보</h3>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="fr">
					<span class="guide-text">처리일시: <fmt:formatDate value="${claimInfo.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>반품 상세 정보</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
					<col style="width: 150px;">
					<col>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">미처리여부</th>
						<td class="input">
							<span class="ui-chk">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="unProcYn" type="checkbox" <c:if test="${claimInfo.unProcYn eq Const.BOOLEAN_TRUE}">checked</c:if>>
								<label for="unProcYn">미처리표시</label>
							</span>
						</td>
						<th scope="row">접수자</th>
						<td>
							<input type="hidden" id="rgsterNo" value="${claimInfo.rgsterNo}">
							<c:choose>
								<c:when test="${claimInfo.adminAcceptYn eq 'Y'}">
									<a href="javascript:void(0)" class="link getAdminDetail">${claimInfo.rgsterId}(${claimInfo.rgsterName})</a>
								</c:when>
								<c:when test="${claimInfo.adminAcceptYn eq 'N'}">
									<c:choose>
										<c:when test="${claimInfo.memberNo eq Const.NON_MEMBER_NO}">
											<span>${claimInfo.memberName}</span>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0)" class="link getMemberDetail" memberno="${claimInfo.memberNo}">${claimInfo.loginId}(${claimInfo.memberName})</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
						<th scope="row">접수일시</th>
						<td><fmt:formatDate value="${claimInfo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
					</tr>
					<tr>
						<th scope="row">사이트·주문번호</th>
						<td>
							<c:choose>
								<c:when test="${claimInfo.rsvOrderYn eq Const.BOOLEAN_TRUE}">[예약]&nbsp;</c:when>
								<c:otherwise>[일반]&nbsp;</c:otherwise>
							</c:choose>
							${claimInfo.siteName}&nbsp;<a href="javascript:void(0)" class="link getOrderDetail">${claimInfo.orgOrderNo}</a>
						</td>
						<th scope="row">결제수단</th>
						<td>
							<c:forEach var="orderPaymentList" items="${orderPaymentList}" varStatus="status">
								<c:choose>
									<c:when test="${orderPaymentList.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_CARD}">
										<c:set var="cardTypeName" value="" />
										<c:choose>
											<c:when test="${orderPaymentList.cardType eq CommonCode.CARD_TYPE_NORMAL}">
												<c:set var="cardTypeName" value="개인" />
											</c:when>
											<c:when test="${orderPaymentList.cardType eq CommonCode.CARD_TYPE_CORPORATE}">
												<c:set var="cardTypeName" value="법인" />
											</c:when>
											<c:otherwise>
												<c:set var="cardTypeName" value="개인" />
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${orderPaymentList.instmtTermCount eq 0}">
												[${cardTypeName}] ${orderPaymentList.pymntOrganName} - 일시불
											</c:when>
											<c:otherwise>
												[${cardTypeName}] ${orderPaymentList.pymntOrganName} - ${orderPaymentList.instmtTermCount}개월
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:when test="${orderPaymentList.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT}">
										${orderPaymentList.pymntMeansCodeName}
									</c:when>
									<c:otherwise>
										${orderPaymentList.pymntMeansCodeName}&nbsp;${orderPaymentList.pymntOrganName}
									</c:otherwise>
								</c:choose>
								<c:if test="${!status.last}"><br/><br/></c:if>
							</c:forEach>
						</td>
						<th scope="row">결제상태·결제완료일시</th>
						<td>
							<c:forEach var="payList" items="${orderPaymentList}" varStatus="status">
								<c:if test="${payList.mainPymntMeansYn eq Const.BOOLEAN_TRUE}">
									${payList.pymntStatCodeName}&nbsp;<fmt:formatDate value="${payList.pymntDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="row">클레임전체상태</th>
						<td colspan="5">
							${claimInfo.clmStatCodeName}
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">
						<c:choose>
							<c:when test="${claimProductList[0].mmnyPrdtYn eq Const.BOOLEAN_TRUE}">자사&nbsp;</c:when>
							<c:when test="${claimProductList[0].mmnyPrdtYn eq Const.BOOLEAN_FALSE}">업체&nbsp;</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>상품 반품 정보
					</h3>
				</div>
				<div class="fr">
					<span class="guide-text">
						<c:choose>
							<c:when test="${claimProductList[0].mmnyPrdtYn eq Const.BOOLEAN_TRUE}"></c:when><%-- 자사 --%>
							<c:when test="${claimProductList[0].mmnyPrdtYn eq Const.BOOLEAN_FALSE}"><a href="javascript:void(0)" class="link" vndrno="${claimProductList[0].vndrNo}" name="vndrBoPop">${claimProductList[0].vndrName}(${claimProductList[0].vndrNo})</a></c:when><%-- 업체 --%>
							<c:otherwise></c:otherwise>
						</c:choose>
					</span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selProdModule01">선택한 상품</label>
						<select id="codeDivSelect" class="ui-sel">
							<option value="customerNoticeOpts">고객알림내용</option>
							<option value="clmRsnCodeOpts">반품사유(*)</option>
							<option value="clmProcTypeCodeOpts">처리(심의)유형/내용</option>
						</select>
						<!-- DESC : 선택한 상품 selectbox option값에 따라 유형 선택 option값 변경 -->
						<select id="clmRsnCodeSelect" class="ui-sel" title="유형 선택" style="display:none;">
							<option value="">반품유형 선택</option>
							<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
								<c:if test="${code.addInfo1 eq CommonCode.CLM_GBN_CODE_RETURN}">
									<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
								</c:if>
							</c:forEach>
						</select>
						<select id="clmProcTypeCodeSelect" class="ui-sel" title="처리(심의)유형 선택" style="display:none;">
							<option value="">처리(심의)유형 선택</option>
							<c:forEach var="code" items="${CLM_PROC_TYPE_CODE}" varStatus="status">
								<c:if test="${code.addInfo1 eq CommonCode.CLM_GBN_CODE_RETURN}">
									<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
								</c:if>
							</c:forEach>
						</select>
						<input id="inputReasonText" type="text" class="ui-input" title="사유 또는 처리내용 입력" placeholder="내용">
					</span>
					<span class="btn-group">
						<a href="#" class="btn-sm btn-func" id="batchApplyBtn">일괄 적용</a>
					</span>
				</div>
				<div class="fr">
					<a href="#" class="btn-sm btn-save" id="claimProductSaveBtn" style="display:none;">저장</a>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : 반품정보 상품 리스트 테이블 -->
			<div id="claimProductArea" class="tbl-wrap scroll-x">
			
				<table class="tbl-col">
					<caption>구매활동정보</caption>
					<colgroup>
						<col style="width: 65px">
						<col style="width: 100px;">
						<col style="width: 100px;">
						<col style="width: 120px;">
						<col style="width: 300px;">
						<col style="width: 100px;">
						<col style="width: 130px;">
						<col style="width: 70px;">
						<col style="width: 100px;">
						<col style="width: 120px;">
						<col style="width: 140px;">
						<col style="width: 140px;">
						<col style="width: 120px;">
						<col style="width: 120px;">
						<col style="width: 300px;">
						<col style="width: 300px;">
						<col style="width: 120px;">
						<col style="width: 300px;">
						<col style="width: 120px;">
						<%-- <col style="width: 120px;">--%>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="only-chk">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkClaimBatchAll" type="checkbox">
									<label for="chkClaimBatchAll"></label>
								</span>
							</th>
							<th scope="col">주문상품순번</th>
							<th scope="col">상품코드</th>
							<th scope="col">업체상품코드</th>
							<th scope="col">상품명</th>
							<th scope="col">스타일</th>
							<th scope="col">옵션</th>
							<th scope="col">수량</th>
							<th scope="col">결제금액</th>
							<th scope="col">발송처</th>
							<th scope="col">매장명</th>
							<th scope="col">주문배송ID</th>
							<th scope="col">주문배송현황</th>
							<th scope="col">진행상태</th>
							<th scope="col"><span class="th-required">반품사유</span></th>
							<th scope="col">고객알림내용</th>
							<th scope="col">반품상품수령여부</th>
							<th scope="col">처리(심의)유형/내용</th>
							<th scope="col">반송배송정보</th>
							<%-- <th scope="col">클레임 불가</th>--%>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="claimProduct" items="${claimProductList}" varStatus="status">
					<c:set var="loopIdx" value="${status.index}"/>
					<c:if test="${claimProduct.prdtTypeCode ne CommonCode.PRDT_TYPE_CODE_GIFT}"><%-- 사은품은 표현하지 않음 --%>
						<tr>
							<td class="only-chk">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkClaimBatch${loopIdx}" type="checkbox" name="chkTarget"><%-- 클레임상품순번 --%>
									<label for="chkClaimBatch${loopIdx}"></label>
									<input type="hidden" name="clmNo" value="${claimProduct.clmNo}"><%-- 클레임번호 --%>
									<input type="hidden" name="clmPrdtSeq" value="${claimProduct.clmPrdtSeq}"><%-- 클레임상품순번 --%>
									<input type="hidden" name="clmPrdtStatCode" value="${claimProduct.clmPrdtStatCode}"><%-- 클레임상품 상태코드 --%>
								</span>
							</td>
							<td class="text-center">${claimProduct.orderPrdtSeq}</td>
							<td class="text-center"><a href="javascript:void(0)" class="link" prdtno="${claimProduct.prdtNo}" name="prdtBoPop">${claimProduct.prdtNo}</a></td>
							<td class="text-center">${claimProduct.vndrPrdtNoText}</td>
							<td class="input clear-float">
								<!-- S : prod-box -->
								<span class="prod-box">
									<c:choose>
										<c:when test="${claimProduct.prdtTypeCode ne CommonCode.PRDT_TYPE_CODE_DELIVERY}">
											<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
														썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
														이미지 크기 변경시 css 수정 필요 -->
											<c:choose>
												<c:when test="${not empty claimProduct.imageUrl}">
													<span class="thumb-box" style="background-image:url(${claimProduct.imageUrl}?shrink=100:100);  background-size: 100px 100px;"></span>
												</c:when>
												<c:otherwise>
													<span class="thumb-box" style="background-image:url('/static/images/common/no_image.png');"></span>
												</c:otherwise>
											</c:choose>
											<a href="javascript:void(0)" class="link prdtDetail" prdtno="${claimProduct.prdtNo}" name="prdtFo">
												${claimProduct.prdtName}
												<c:choose>
													<c:when test="${!empty claimProduct.giftPrdtName and claimProduct.giftPrdtName ne null}">
														<input type="hidden" name="orderGiftPrdtSeq" value="${claimProduct.orderGiftPrdtSeq}"/>
														<br/>┖사은품 : ${claimProduct.giftPrdtName}
													</c:when>
													<c:otherwise>
														<input type="hidden" name="orderGiftPrdtSeq" value=""/>
													</c:otherwise>
												</c:choose>
											</a>
										</c:when>
										<c:otherwise>
											<span class="link prdtDetail">
												${claimProduct.prdtName}
												<input type="hidden" name="orderGiftPrdtSeq" value=""/>
											</span>
										</c:otherwise>
									</c:choose>
								</span>
								<!-- E : prod-box -->
							</td>
							<td class="text-center">${claimProduct.styleInfo}</td>
							<td class="text-center">${claimProduct.optnName}</td>
							<td class="text-center">${claimProduct.clmQty}</td>
							<td class="text-center"><fmt:formatNumber value="${claimProduct.orderAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></td>
							<td class="text-center">${claimProduct.stockGbnCodeName}</td>
							<td class="text-center">${claimProduct.storeName }</td>
							<td class="text-center">${claimProduct.dlvyIdText}</td>
							<td class="text-center">${claimProduct.dlvyStatCodeName}</td>
							<td class="text-center">
								<input type="hidden" id="historyClmNo" value="${claimProduct.clmNo}"><%-- 클레임번호 --%>
								<input type="hidden" id="historyClmPrdtSeq" value="${claimProduct.clmPrdtSeq}"><%-- 클레임상품순번 --%>
								<a href="#" id="btnClaimHistory" class="link" onclick="abc.biz.claim.detail.openClaimHistoryPop(this);">${claimProduct.clmPrdtStatCodeName}</a>
							</td>
							<td class="input">
								<select class="ui-sel" title="반품유형 선택" name="clmRsnCode">
									<option value="">반품유형 선택</option>
									<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
										<option value="${code.codeDtlNo}" <c:if test="${code.codeDtlNo eq claimProduct.clmRsnCode}">selected</c:if>>${code.codeDtlName}</option>
									</c:forEach>
								</select>
								<textarea class="ui-textarea" title="반품사유 입력" name="clmEtcRsnText">${claimProduct.clmEtcRsnText}</textarea>
							</td>
							<td class="input">
								<textarea class="ui-textarea" title="고객알림 내용 입력" name="cstmrAlertContText">${claimProduct.cstmrAlertContText}</textarea>
							</td>
							<td class="input text-center">
								<span class="ui-chk">
									<c:set var="recptYnVal" value="N"/>
									<c:if test="${claimProduct.recptYn eq Const.BOOLEAN_TRUE}">
										<c:set var="recptYnVal" value="Y"/>
									</c:if>
									
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkClaimReceipt${loopIdx}" type="checkbox" name="chkRecptYn" <c:if test="${claimProduct.recptYn eq Const.BOOLEAN_TRUE}">checked="checked"</c:if> >
									<label for="chkClaimReceipt${loopIdx}">수령</label>
									<input type="hidden" name="recptYn" value="${recptYnVal}">
								</span>
							</td>
							<td>
								<select class="ui-sel" title="처리(심의)유형 선택" name="clmProcTypeCode">
									<option value="">처리(심의)유형 선택</option>
									<c:forEach var="code" items="${CLM_PROC_TYPE_CODE}" varStatus="status">
										<option value="${code.codeDtlNo}" <c:if test="${code.codeDtlNo eq claimProduct.clmProcTypeCode}">selected</c:if>>${code.codeDtlName}</option>
									</c:forEach>
								</select>
								<textarea class="ui-textarea" title="처리(심의)유형 내용 입력" name="clmProcContText">${claimProduct.clmProcContText}</textarea>
								<input type="hidden" name="clmProcTypeCodeValue" value="${claimProduct.clmProcTypeCode}"/><%-- 입력내용 확인용 --%>
								<input type="hidden" name="clmProcContTextValue" value="${claimProduct.clmProcContText}"/><%-- 입력내용 확인용 --%>
							</td>
							<td class="text-center">
								<select class="ui-sel" title="택배사 선택" name="logisVndrCode">
									<option value="">택배사 선택</option>
									<c:forEach var="code" items="${LOGIS_VNDR_CODE}" varStatus="status">
										<option value="${code.codeDtlNo}" <c:if test="${code.codeDtlNo eq claimProduct.logisVndrCode}">selected</c:if>>${code.codeDtlName}</option>
									</c:forEach>
								</select>
								<input type="text" class="ui-input" title="운송장번호" placeholder="운송장번호" name="waybilNoText" value="${claimProduct.deliveryWaybilNoText}" maxlength="20" style="margin-top: 6px;">
								<%-- <button type="button" class="btn-sm btn-link" onclick="" style="margin-top: 6px;">배송조회</button>--%>
							</td>
							<%-- 
							<td class="text-center">
								<c:choose>
									<c:when test="${claimProduct.clmImpsbltRsnCode ne null and !empty claimProduct.clmImpsbltRsnCode}">
										<a href="#" class="link" onclick="abc.biz.claim.exchange.detail.readClaimImpossibilityDetailPop('${claimProduct.clmNo}', '${claimProduct.clmPrdtSeq}', '${claimProduct.orderNo}', '${claimProduct.orderPrdtSeq}');">클레임불가</a>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn-sm btn-link" onclick="abc.biz.claim.detail.registClaimImpossibilityFormPop('${claimProduct.clmNo}', '${claimProduct.clmPrdtSeq}', '${claimProduct.orderNo}', '${claimProduct.orderPrdtSeq}');">클레임 불가</button>
									</c:otherwise>
								</c:choose>
							</td>
							--%>
						</tr>
					</c:if>
					</c:forEach>
					
					</tbody>
				</table>
			
			</div>
			<!-- E : 반품정보 상품 리스트 테이블 -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">접수 기본정보 및 배송비</h3>
				</div>
				<div class="fr">
					<a href="#" class="btn-sm btn-save" id="deliveryInfoSaveBtn" style="display:none;">저장</a>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : col-wrap -->
			<div class="col-wrap">
				<div class="col">
					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>접수 기본정보 및  배송비</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">접수처</th>
								<td>온라인</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">반품상품 회수지</span>
								</th>
								<td class="input">
									<span class="address-box">
										<span class="zip-code-wrap">
											<span class="fl">
												<input type="text" class="ui-input" title="우편번호 입력" id="buyerPostCodeText" name="buyerPostCodeText" value="${fn:trim(claimInfo.buyerPostCodeText)}" maxlength="5">
												<button type="button" class="btn-sm btn-link" id="retrievalPostSearchBtn">우편번호 찾기</button>
											</span>
											<span class="fr">
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkDeliveryModule" type="checkbox">
													<label for="chkDeliveryModule">배송주소와 동일</label>
												</span>
											</span>
										</span>
										<span class="address-wrap">
											<input type="text" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" id="buyerPostAddrText" name="buyerPostAddrText" value="${claimInfo.buyerPostAddrText}">
											<input type="text" class="ui-input" placeholder="상세주소" title="상세주소 입력" id="buyerDtlAddrText" name="buyerDtlAddrText" value="${claimInfo.buyerDtlAddrText}">
										</span>
									</span>
								</td>
							</tr>
							<%--
							<tr>
								<th scope="row">회수 택배정보</th>
								<td>대한통운 <a href="#" class="link">1234567889900</a></td>
							</tr>
							 --%>
							<tr>
								<th scope="row">
									<span class="th-required">발송인 명&#47;연락처</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" class="ui-input name" title="발송인명 입력" id="buyerName" name="buyerName" value="${claimInfo.buyerName}">
										<span class="text">&#47;</span>
										<input type="text" class="ui-input phone-number" title="연락처 입력" id="buyerHdphnNoText" name="buyerHdphnNoText" value="${claimInfo.buyerHdphnNoText}">
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
				<div class="col">
					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>접수 기본정보 및 배송비</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
						
						<%-- 추가결제한 배송비가 없는 경우 --%>
						<c:if test="${claimInfo.addDlvyAmt eq '0'}">
							<tr>
								<th scope="row">배송비 결제방식</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
									
										<%-- 2020.03.16 : 주결제수단이 휴대폰결제일 경우 환불금 차감 안되어야한다. --%>
										<c:if test="${mainPayment.pymntMeansCode ne CommonCode.PYMNT_MEANS_CODE_HANDPHONE}">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioPayment01" type="radio" name="addDlvyAmtPymntType" value="${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION}" checked>
													<label for="radioPayment01">환불금액에서 차감</label>
												</span>
											</li>
										</c:if>
										
										<%-- 2020.03.16 : 주결제수단이 휴대폰결제일 경우 선결제 기본 체크 --%>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioPayment02" name="radioPaymentModule" type="radio"<c:if test="${mainPayment.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_HANDPHONE}">checked</c:if>>
												<label for="radioPayment02">선결제</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">반품 배송비</span>
								</th>
								<!-- DESC : 반품 배송비 텍스트로 들어갈경우, td영역 input 클래스 제거 해주세요 -->
								<td class="input">
									<select class="ui-sel" title="반품 배송비 선택" id="addDlvyAmt" name="addDlvyAmt">
										<option value="0" <c:if test="${claimInfo.addDlvyAmt eq '0'}">selected="selected"</c:if>>무료</option>
										<option value="2500" <c:if test="${claimInfo.addDlvyAmt eq '2500'}">selected="selected"</c:if>>2,500</option>
										<option value="5000" <c:if test="${claimInfo.addDlvyAmt eq '5000'}">selected="selected"</c:if>>5,000</option>
										<%-- 
										<option value="0">무료</option>
										<option value="2500" <c:if test="${claimDeliveryAmt eq '2500'}">selected="selected"</c:if>>2,500</option>
										<option value="5000" <c:if test="${claimDeliveryAmt eq '5000'}">selected="selected"</c:if>>5,000</option>
										--%>
									</select>
								</td>
							</tr>
							<!-- S : 선결제 선택한 경우 -->
							<tr>
								<th scope="row">결제수단</th>
								<td class="input">
									<!-- DESC : 환불금액에서 차감 선택시 radio input disabled 속성 추가 해주세요 -->
									<!-- S : ip-box-list -->
									<ul class="ip-box-list vertical">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioPickup01" type="radio" name="addDlvyAmtPymntType" value="${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}">
												<label for="radioPickup01">가상계좌 발급</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioPickup02" type="radio" name="addDlvyAmtPymntType" value="${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON}" 
													<c:if test="${empty usableCouponList or usableCouponList eq null}">disabled="disabled"</c:if>>
												<label for="radioPickup02">
													무료반품 쿠폰
													<c:if test="${empty usableCouponList or usableCouponList eq null}">&nbsp;(선택 가능한 무료반품 쿠폰이 없습니다.)</c:if>
												</label>
											</span>
											<!-- DESC : 무료반품쿠폰 선택시 disabled 속성 삭제해주세요 -->
											<select class="ui-sel size-full" title="무료반품 쿠폰 선택" id="holdCpnSeq" name="holdCpnSeq" 
													<c:if test="${empty usableCouponList or usableCouponList eq null}">disabled="disabled"</c:if>>
												<option value="">무료반품쿠폰선택</option>
												<c:forEach var="coupon" items="${usableCouponList}" varStatus="status">
													<option value="${coupon.holdCpnSeq}">${coupon.cpnName}</option>
												</c:forEach>
											</select>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- E : 선결제 선택한 경우 -->
						</c:if>
						
						<%-- 추가결제한 배송비가 있는 경우 --%>
						<c:if test="${claimInfo.addDlvyAmt ne '0'}">
							<!-- S : 배송비 결제된 경우 -->
								<th scope="row">배송비 결제방식</th>
								<td>
									<c:choose>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION}">
											환불금액에서 차감
										</c:when>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}">
											선결제
										</c:when>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON}">
											무료쿠폰
										</c:when>
										<c:otherwise>
											무료처리
										</c:otherwise>
									</c:choose>
								</td>
							<tr>
								<th scope="row">
									<span>반품 배송비</span>
								</th>
								<td>
									<fmt:formatNumber value="${claimInfo.addDlvyAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원
									<input type="hidden" id="addDlvyAmt" name="addDlvyAmt" value="${claimInfo.addDlvyAmt}">
								</td>
							</tr>
							<tr>
								<th scope="row">결제수단</th>
								<td class="input">
									<c:choose>
										<%-- 추가결제 --%>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}">
											<c:choose>
												<%-- 카드, 실시간 계좌이체 결제 --%>
												<c:when test="${addDeliveryPaymentInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_CARD or addDeliveryPaymentInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}">
													<!-- S : td-box-both -->
													<span class="td-box-both">
														<span class="fl">
															<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH}">	
																<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT 
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT}">
																	<%-- 반품접수 / 수거지시 / 수령완료 / 심의완료 / 반품불가 / 반품접수철회 --%>
																	<a href="#" class="btn-sm btn-del" id="cancelAddDeliveryAmtBtn">결제취소</a>
																</c:if>
															</c:if>
															<span class="text">&nbsp;</span>
														</span>
														<span class="text">
															<c:choose>
																<c:when test="${addDeliveryPaymentInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_CARD}">
																	<c:set var="cardTypeName" value="" />
																	<c:choose>
																		<c:when test="${addDeliveryPaymentInfo.cardType eq CommonCode.CARD_TYPE_NORMAL}">
																			<c:set var="cardTypeName" value="개인" />
																		</c:when>
																		<c:when test="${addDeliveryPaymentInfo.cardType eq CommonCode.CARD_TYPE_CORPORATE}">
																			<c:set var="cardTypeName" value="법인" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="cardTypeName" value="개인" />
																		</c:otherwise>
																	</c:choose>
																	<c:choose>
																		<c:when test="${addDeliveryPaymentInfo.instmtTermCount eq 0}">
																			[${cardTypeName}] ${addDeliveryPaymentInfo.pymntOrganName} - 일시불
																		</c:when>
																		<c:otherwise>
																			[${cardTypeName}] ${addDeliveryPaymentInfo.pymntOrganName} - ${addDeliveryPaymentInfo.instmtTermCount}개월
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	${addDeliveryPaymentInfo.pymntMeansCodeName}&nbsp;${addDeliveryPaymentInfo.pymntOrganName}
																</c:otherwise>
															</c:choose>
															(상태 : ${addDeliveryPaymentInfo.pymntStatCodeName})
														</span>
													</span>
													<!-- E : td-box-both -->
												</c:when>
												<%-- 가상계좌 결제 2020.03.18 입금은행명 노출되도록 수정 --%>
												<c:when test="${addDeliveryPaymentInfo.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}">
													<!-- S : td-box-both -->
													<span class="td-box-both">
														<span class="fl">
															<%-- 입금대기 --%>
															<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT}">
																<a href="#" class="btn-sm btn-func" id="requestVirtualAccountBtn">가상계좌재발급</a>
															</c:if>
															<%-- 결제완료 --%>
															<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH}">
																<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT 
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE
																			or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT}">
																	<%-- 반품접수 / 수거지시 / 수령완료 / 심의완료 / 반품불가 / 반품접수철회 --%>
																	<a href="#" class="btn-sm btn-del" id="cancelAddDeliveryAmtBtn">결제취소</a>
																</c:if>
															</c:if>
														</span>
														<span class="text">
															<%-- 입금대기 --%>
															<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT}">
																&nbsp;입금만료일 : <fmt:formatDate value="${addDeliveryPaymentInfo.vrtlAcntExprDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
															</c:if>
															<%-- 결제완료 --%>
															<c:if test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH}">
																&nbsp;결제완료일 : <fmt:formatDate value="${addDeliveryPaymentInfo.pymntDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
															</c:if>
														</span>
													</span>
													<!-- E : td-box-both -->
				
													<span class="text">
														<%-- 입금대기 --%>
														<c:choose>
															<c:when test="${addDeliveryPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT}">
																<fmt:formatDate var="vrtlAcntExprDtm" value="${addDeliveryPaymentInfo.vrtlAcntExprDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS}" />
																<c:if test="${vrtlAcntExprDtm >= nowDate}">
																	<span class="tc-blue">[${addDeliveryPaymentInfo.pymntStatCodeName}]</span>&nbsp;
																</c:if>
																<c:if test="${vrtlAcntExprDtm < nowDate}">
																	<span class="tc-red">[입금기한만료]</span>&nbsp;
																</c:if>
															</c:when>
															<c:otherwise>
																<span class="tc-blue">[${addDeliveryPaymentInfo.pymntStatCodeName}]</span>&nbsp;
															</c:otherwise>
														</c:choose>
														${addDeliveryPaymentInfo.bankCodeName}&nbsp;${addDeliveryPaymentInfo.acntNoText}&nbsp;(예금주 : ${addDeliveryPaymentInfo.acntHldrName})
													</span>
												</c:when>
												<c:otherwise>
													<%-- // none --%>
												</c:otherwise>
											</c:choose>
										</c:when>
										<%-- 무료쿠폰 --%>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON}">
											<!-- S : td-box-both -->
											<span class="td-box-both">
												<span class="fl">
													<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT 
																or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER
																or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH
																or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH
																or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE
																or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT}">
														<%-- 반품접수 / 수거지시 / 수령완료 / 심의완료 / 반품불가 / 반품접수철회 --%>
														<a href="#" class="btn-sm btn-del" id="cancelAddDeliveryCouponBtn">쿠폰사용복원</a>
													</c:if>
												</span>
												<span class="text">
													무료쿠폰사용
												</span>
											</span>
											<!-- E : td-box-both -->
										</c:when>
										<%-- 환불금차감 --%>
										<c:when test="${claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION}">
											<!-- S : td-box-both -->
											<span class="td-box-both">
												<span class="fl" style="margin-right:10px;">
													<a href="#" class="btn-sm btn-del" id="cancelAddDeliveryRefundDeductionBtn">무료변경</a>
												</span>
												<span class="fl text">
													환불금차감
												</span>
												
												<!-- // 2020.02.26 : 배송비 환불금차감으로 반품완료했으나 차감한 배송비를 결제취소해서 환불해줘야하는 경우 버튼 노출되도록  -->
												<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_FINISH and claimInfo.addDlvyAmtPymntType eq CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION and claimInfo.addDlvyAmt gt 0}">
													<span class="fl" style="margin-left:10px;">
														<a href="javascript:void(0)" class="btn-sm btn-del" id="refundDlvyAmtPymntCancelBtn">환불금차감 반품배송비 결제취소</a>
													</span>
												</c:if>
											</span>
											<!-- E : td-box-both -->
										</c:when>
										<c:otherwise>
											<%-- // none --%>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<!-- E : 배송비 결제된 경우 -->
						</c:if>

							<tr>
								<th scope="row">점간 이동여부</th>
								<td class="input">
									<span class="ui-chk ip-label-instead">
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBranchMove01" type="radio" name="branchMoveCode" value="${CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL}" 
												<c:if test="${claimInfo.branchMoveCode eq CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL}">checked="checked"</c:if>>
											<label for="radioBranchMove01">원거래</label>
										</span>
										<span class="ui-rdo">
											<input id="radioBranchMove02" type="radio" name="branchMoveCode" value="${CommonCode.BRANCH_MOVE_CODE_BRANCH_MOVE}" 
												<c:if test="${claimInfo.branchMoveCode eq CommonCode.BRANCH_MOVE_CODE_BRANCH_MOVE}">checked="checked"</c:if>>
											<label for="radioBranchMove02">점간이동</label>
										</span>
									</span>
								</td>
							</tr>
							
							<tr>
								<th scope="row">동봉 배송비</th>
								<td class="input">
									<span class="ui-chk ip-label-instead">
										<span class="ip-text-box">
											<input type="text" class="ui-input num-unit100000000" title="금액 입력" id="eclsDlvyAmt" name="eclsDlvyAmt" value="${claimInfo.eclsDlvyAmt}" maxlength="6">
											<span class="text">원</span>
										</span>
									</span>
									<span style="margin-left:10px;">
										<a href="javascript:void(0)" class="btn-sm btn-save" id="eclsDlvyAmtSaveBtn">동봉 배송비 저장</a>
									</span>
								</td>
							</tr>
							
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
			</div>
			<!-- E : col-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">환수&#47;환불 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<%-- S : 금액 설정 영역 --%>
			<c:set var="cancelAllAmt" value="0" /><%-- 취소금액(클레임금액):상품금액+배송비 --%>
			<c:set var="redempAllAmt" value="0" /><%-- 추가비용(환수비용) --%>
			<c:set var="refundAllAmt" value="0" /><%-- 환불금액(결제취소대상) --%>
			
			<c:set var="cancelAllAmt" value="${claimPossibleSumAmt + addMultiBuyDifferSumAmt}" />
			
			<c:forEach var="redempList" items="${redempClaimPaymentList}" varStatus="status">
				<c:set var="redempAllAmt" value="${redempAllAmt + redempList.pymntAmt}" />
			</c:forEach>
			
			<c:set var="refundAllAmt" value="${claimPossibleSumAmt - redempAllAmt + addMultiBuyDifferSumAmt}" />
			
			<input type="hidden" id="cancelAllAmt" value="${cancelAllAmt}"/>
			<input type="hidden" id="redempAllAmt" value="${redempAllAmt}"/>
			<input type="hidden" id="refundAllAmt" value="${refundAllAmt}"/>
			
			<%-- E : 금액 설정 영역 --%>
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>환수&#47;환불 정보</caption>
				<colgroup>
					<col style="width: 160px;">
					<col>
					<col style="width: 160px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">잔여 취소 가능금액</th>
						<td colspan="3">
							<fmt:formatNumber value="${orderInfo.pymntAmt - orderInfo.cnclAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원 
							(최초결제금액 : <fmt:formatNumber value="${orderInfo.pymntAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원 
							– 기존 취소금액 : <fmt:formatNumber value="${orderInfo.cnclAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원)
						</td>
					</tr>
					<tr>
						<th scope="row">클레임 총 금액 </th>
						<td colspan="3">
							<fmt:formatNumber value="${claimSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원
							<input type="hidden" name="expectCnclAmt" value="${claimAmountCalc.expectCnclAmt}"/>
						</td>
					</tr>
					<tr>
						<th scope="row">결제수단별 <br />취소금액설정①</th>
						<td class="input">
							<div class="tbl-header">
								<div class="fl">
									<span class="text tc-bold">취소금액 설정</span>
								</div>
								
								<!-- 2020.02.28 : 반품완료 이전 / 이후 설정 -->
								<c:if test="${claimInfo.clmStatCode ne CommonCode.CLM_STAT_CODE_RETURN_FINISH }">
									<div class="fr">
										<a href="javascript:void(0);" class="btn-sm btn-func" id="resetCnclAmtBtn">취소 설정금액 초기화</a>
									</div>
								</c:if>
							</div>

							<!-- S : ip-box-list -->
							<ul class="ip-box-list vertical">
							<c:forEach var="expectCnclAble" items="${calcPaymentList}" varStatus="status">
								<li>
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<span class="text">${expectCnclAble.pymntMeansCodeName}</span>
										<c:choose><%-- 결제수단 별 form 구성 --%>
											<c:when test="${expectCnclAble.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_BUY_POINT or expectCnclAble.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_EVENT_POINT}">
												<c:set var="pymntAmtTitle" value="포인트 입력" />
												<c:set var="pymntAmtCurrency" value="Point" />
												<c:set var="pymntAmtReadOnly" value="" />
												<c:if test="${expectCnclAble.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_EVENT_POINT}">
													<c:set var="pymntAmtReadOnly" value="readonly" />
												</c:if>
											</c:when>
											<c:otherwise>
												<c:set var="pymntAmtTitle" value="금액 입력" />
												<c:set var="pymntAmtCurrency" value="원" />
												<c:set var="pymntAmtReadOnly" value="" />
											</c:otherwise>
										</c:choose>
										
										<!-- 2020.02.28 : 반품완료 이전 / 이후 설정 -->								
										<c:choose>
											<c:when test="${claimInfo.clmStatCode ne CommonCode.CLM_STAT_CODE_RETURN_FINISH }">
												<input type="text" class="ui-input num-unit100000000" title="${pymntAmtTitle}" name="pymntAmt" value="${expectCnclAble.thisPymntCnclAmt}" ${pymntAmtReadOnly}>
											</c:when>
											<c:otherwise>
												<span class="text" title="${pymntAmtTitle}" name="pymntAmt">${expectCnclAble.thisPymntCnclAmt}</span>
											</c:otherwise>
										</c:choose>
										
										<span class="text">${pymntAmtCurrency}</span>
										<input type="hidden" name="orgPymntAmt" value="${expectCnclAble.thisPymntCnclAmt}"/><%-- 원 취소금액 --%>
										<input type="hidden" name="pymntMeansCode" value="${expectCnclAble.pymntMeansCode}"/><%-- 결제수단코드 --%>
										<input type="hidden" name="remainPymntCnclAmt" value="${expectCnclAble.remainPymntCnclAmt}"/><%-- 취소잔여금액(초기화용) --%>
									</span>
									<!-- E : ip-text-box -->
								</li>
							</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td class="input" colspan="2">
							<div class="tbl-header">
								<div class="fl">
									<span class="text tc-bold">취소가능 잔여금액</span>
								</div>
							</div>

							<!-- S : ip-box-list -->
							<ul class="ip-box-list vertical">
							<c:forEach var="remainCnclAble" items="${calcPaymentList}" varStatus="status">
								<li>
									<!-- S : td-box-both -->
									<div class="td-box-both">
										<div class="fl">
											<span class="text">${remainCnclAble.pymntMeansCodeName}</span>
										</div>
										<div class="fr">
											<span class="text"><fmt:formatNumber value="${remainCnclAble.remainPymntCnclAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>
											<c:choose>
												<c:when test="${remainCnclAble.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_BUY_POINT or remainCnclAble.pymntMeansCode eq CommonCode.PYMNT_MEANS_CODE_EVENT_POINT}">
													Point
												</c:when>
												<c:otherwise>
													원
												</c:otherwise>
											</c:choose>
											</span>
										</div>
									</div>
									<!-- E : td-box-both -->
								</li>
							</c:forEach>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">처리(예정)내역</th>
						<td class="input" colspan="3">
							<ul class="td-text-list" style="margin-bottom:5px;">
								<li>* 환불금액은 상단 결제수단별 금액 조정 후 취소처리 시 변경됩니다.(총 금액은 동일) </li>
								<li>* 취소처리 시 결제취소 실패로 인한 금액은 자동으로 재경팀 환불접수 처리가 됩니다. </li>
							</ul>
							<table class="tbl-row">
								<caption>반품 상세 정보</caption>
								<colgroup>
									<col style="width: 0px;">
									<col>
									<col>
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"></th>
										<td class="input row">
											<span class="ip-text-box">
												<span class="text tc-bold">취소금액①&nbsp;&nbsp;<fmt:formatNumber value="${cancelAllAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</span>
											</span>
										</td>
										<td class="input">
											<span class="ip-text-box">
												<span class="text tc-bold">추가비용②&nbsp;&nbsp;<fmt:formatNumber value="${redempAllAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</span>
											</span>
										</td>
										<td class="input">
											<span class="ip-text-box">
												<span class="text tc-bold">환불금액(①-②)&nbsp;&nbsp;<fmt:formatNumber value="${refundAllAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</span>
											</span>
										</td>
									</tr>
									
									<tr>
										<th scope="row"></th>
										<td class="input">
											<ul class="td-text-list">
												<li>상품금액&nbsp;<fmt:formatNumber value="${claimPossibleSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</li>
												<c:if test="${addMultiBuyDifferSumAmt ne '0'}">
													<li>프로모션&nbsp;<fmt:formatNumber value="${addMultiBuyDifferSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</li>
												</c:if>
											</ul>
										</td>
										<td class="input">
											<ul class="td-text-list">
												<c:forEach var="redempList" items="${redempClaimPaymentList}" varStatus="status">
													<li>
														${redempList.ocrncRsnCodeName}&nbsp;
														<fmt:formatNumber value="${redempList.pymntAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원
													</li>
												</c:forEach>
											</ul>
										</td>
										<td class="input">
											<ul class="td-text-list">
												<c:forEach var="refundList" items="${refundClaimPaymentList}" varStatus="status">
													<li>
														${refundList.pymntMeansCodeName}&nbsp;<fmt:formatNumber value="${refundList.pymntAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원
														<%-- (${refundList.pymntStatCodeName})--%>
													</li>
												</c:forEach>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<th scope="row">재경팀 환불접수금</th>
						<td>
							<c:forEach var="mmnyRefundList" items="${mmnyRefundPaymentList}" varStatus="status">
								<fmt:formatNumber value="${mmnyRefundList.pymntAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원
								<c:choose>
									<c:when test="${mmnyRefundList.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE}">
										처리완료일시 <fmt:formatDate value="${mmnyRefundList.redempRfndOpetrDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${mmnyRefundList.procImpsbltYn eq Const.BOOLEAN_TRUE}">
												처리불가 <fmt:formatDate value="${mmnyRefundList.procImpsbltCmlptDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>(${mmnyRefundList.procImpsbltRsnText})
											</c:when>
											<c:otherwise>
												- 미처리
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<c:if test="${status.count ne fn:length(mmnyRefundPaymentList)}">
									/
								</c:if>
							</c:forEach>
						</td>
						<th scope="row">증감예정 적립 포인트</th>
						<td><fmt:formatNumber value="${claimInfo.irdsSavePoint}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/> Point</td>
					</tr>
					<!-- S : 환수금액 -->
					<tr>
						<th scope="row">환수포인트</th>
						<td class="input" colspan="3">
							<ul class="td-text-list" style="margin-bottom:5px;">
								<li>
									환수포인트  <fmt:formatNumber value="${claimInfo.redempSavePoint}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/> Point
									<input type="hidden" name="redempSavePoint" value="${claimInfo.redempSavePoint}" />
								</li>
							</ul>
							<c:if test="${claimInfo.redempSavePoint ne '0'}">
								<c:choose>
									<c:when test="${redempPointPaymentInfo eq null}">
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoVirtualModule1" name="redempAmtRndmProcYn" type="radio" value="N" checked="checked">
													<label for="rdoVirtualModule1">가상계좌발급</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoVirtualModule2" name="redempAmtRndmProcYn" type="radio" value="Y">
													<label for="rdoVirtualModule2">임의처리</label>
												</span>
											</li>
										</ul>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${redempPointPaymentInfo.redempAmtRndmProcYn eq Const.BOOLEAN_TRUE}">
												<span class="text">
													<span class="tc-blue">[임의처리]</span>
												</span>
											</c:when>
											<c:otherwise>
												<%-- 환수접수(입금대기) --%>
												<c:if test="${redempPointPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT}">
													<span class="text">
														<a href="#" class="btn-sm btn-func" id="requestVirtualAccountRdempPointBtn">가상계좌재발급</a>
														&nbsp;입금만료일 : <fmt:formatDate value="${redempPointPaymentInfo.vrtlAcntExprDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
														<fmt:formatDate var="vrtlAcntExprDtm" value="${redempPointPaymentInfo.vrtlAcntExprDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS}" />
														<c:if test="${vrtlAcntExprDtm >= nowDate}">
															<span class="tc-blue">[${redempPointPaymentInfo.pymntStatCodeName}]</span>&nbsp;
														</c:if>
														<c:if test="${vrtlAcntExprDtm < nowDate}">
															<span class="tc-red">[입금기한만료]</span>&nbsp;
														</c:if>
													</span>
												</c:if>
												<%-- 환수완료(결제완료) --%>
												<c:if test="${redempPointPaymentInfo.pymntStatCode eq CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE}">
													<span class="text">
														결제완료일 : <fmt:formatDate value="${redempPointPaymentInfo.pymntDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
														<span class="tc-blue">[${redempPointPaymentInfo.pymntStatCodeName}]</span>
													</span>
												</c:if>
												<span class="text">
													&nbsp;${redempPointPaymentInfo.bankCodeName}&nbsp;${redempPointPaymentInfo.acntNoText}&nbsp;(예금주 : ${redempPointPaymentInfo.acntHldrName})
												</span>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
								<ul class="td-text-list" style="margin-bottom:5px;">
									<li>&nbsp;</li>
									<li>* 포인트에 의한 환수금액 발생시에만 임의처리가 가능합니다.</li>
									<li>* 임의 처리할 경우 가상계좌는 발급 되지 않으며 ,고객에게 환수금액 및 입금 계좌 정보가 고지 되지 않습니다.</li>
									<li>* 취소완료 후에는 가상계좌발급 또는 임의처리로 변경이 불가합니다.</li>
								</ul>
							</c:if>
						</td>
					</tr>
					<!-- E : 환수금액 -->
					
					<%-- 
					<!-- S : 환수금액 발생한 경우 -->
					<tr>
						<th scope="row">환수계좌</th>
						<td colspan="3">
							<!-- S : td-box-both -->
							<span class="td-box-both">
								<span class="fl">
									<span class="tc-blue">[입금완료 or 결제완료]</span><span class="tc-red">[입금대기]</span> 신한 1102345677890   (예금주 : 예금주명text)
								</span>
								<span class="fr">
									2019.02.20 11:11:37
								</span>
							</span>
							<!-- E : td-box-both -->
						</td>
					</tr>
					<!-- E : 환수금액 발생한 경우 -->
					--%>
					
					<!-- S : 환불 계좌정보 -->
					<tr>
						<th scope="row">환불계좌</th>
						<td class="input" colspan="3">
							<%-- <span class="text"><span class="tc-red">[입금대기]</span> 신한 1102345677890 (예금주 : 예금주명text)</span>--%>
							<c:choose>
								<c:when test="${claimInfo.bankCode ne null}">
									<a href="#" class="btn-sm btn-func" id="accountChangeBtn">계좌변경</a>
									<span class="text">${claimInfo.bankCodeName}&nbsp;${claimInfo.rfndAcntText} (예금주 : ${claimInfo.acntHldrName})</span>
									
									<div style="margin-top:10px;display:none;" id="changeAccountArea">
										<span class="refund-account-box">
											<select id="bankCode" name="bankCode" class="ui-sel" title="은행선택">
												<option value="">은행선택</option>
												<c:forEach var="code" items="${BANK_CODE}" varStatus="status">
													<option value="${code.addInfo4}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
											<span class="refund-input">
												<input type="text" class="ui-input account-number" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력" id="rfndAcntText" name="rfndAcntText">
												<input type="text" class="ui-input account-holder" placeholder="예금주명" title="예금주명 입력" id="acntHldrName" name="acntHldrName">
												<button type="button" class="btn-sm btn-func" id="accountCertifyBtn">계좌인증</button>
											</span>
										</span>
									</div>
								</c:when>
								<c:otherwise>
									<span class="refund-account-box">
										<select id="bankCode" name="bankCode" class="ui-sel" title="은행선택">
											<option value="">은행선택</option>
											<c:forEach var="code" items="${BANK_CODE}" varStatus="status">
												<option value="${code.addInfo4}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
										<span class="refund-input">
											<input type="text" class="ui-input account-number" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력" id="rfndAcntText" name="rfndAcntText">
											<input type="text" class="ui-input account-holder" placeholder="예금주명" title="예금주명 입력" id="acntHldrName" name="acntHldrName">
											<button type="button" class="btn-sm btn-func" id="accountCertifyBtn">계좌인증</button>
										</span>
									</span>
								</c:otherwise>
							</c:choose>
							<%-- 2020.03.27 : 환불접수 상태일때 노출되는 환불계좌 등록일자 --%>
							<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT and claimInfo.rfndAcntText ne null}">
								<span class="text" style="margin-left:10px;">/&nbsp;&nbsp;환불계좌 등록일시 : <fmt:formatDate value="${claimInfo.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
							</c:if>
						</td>
					</tr>
					<!-- E : 환불 계좌정보 -->
					
					<%-- 
					<!-- S : 환불 계좌정보가 없는 경우 -->
					<tr>
						<th scope="row">환불계좌</th>
						<td class="input" colspan="3">
							<span class="refund-account-box">
								<select class="ui-sel" id="selBankStockCompany" title="은행/증권사 선택">
									<option>은행/증권사 선택</option>
								</select>
								<span class="refund-input">
									<input type="text" class="ui-input account-number" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력">
									<input type="text" class="ui-input account-holder" placeholder="예금주명" title="예금주명 입력">
									<button type="button" class="btn-sm btn-func">계좌인증</button>
								</span>
							</span>
						</td>
					</tr>
					<!-- E : 환불 계좌정보가 없는 경우 -->
					<!-- S : 환불금액 입금 완료, ‘환불 금액 관리’메뉴에서 ‘처리불가’판정 된 경우  -->
					<tr>
						<th scope="row">환불계좌</th>
						<td colspan="3">
							<!-- S : td-box-both -->
							<span class="td-box-both">
								<span class="fl">
									<span class="tc-blue">[입금완료 or 결제완료]</span><span class="tc-red">[입금대기]</span> 신한 1102345677890   (예금주 : 예금주명text)
								</span>
								<span class="fr">2019.02.20 11:16:37</span>
							</span>
							<!-- E : td-box-both -->
						</td>
					</tr>
					<!-- E : 환불금액 입금 완료, ‘환불 금액 관리’메뉴에서 ‘처리불가’판정 된 경우  -->
					--%>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 환수 : 고객에게 추가로 입금 받아야 하는 금액 (-)</li>
					<li>* 환불 : 고객에게 추가로  환불 입금 처리해야하는 금액 (+)</li>
				</ol>
				<%-- 
				<div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div>
				--%>
			</div>
			<!-- E : tbl-desc-wrap -->

			</form>
			<%-- E : claimProductForm --%>

			<%-- 2020.03.10 : 반품클레임철회 조건에 수거지시 상태도 추가 --%>
			<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_ACCEPT or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT}">
			<!-- S : content-header -->
			<div class="content-header"></div>
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">클레임 접수 철회 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<%--  S : claimWthdrawForm --%>
				<form id="claimWthdrawForm" name="claimWthdrawForm" method="post">
				<input type="hidden" name="clmNo" value="${claimInfo.clmNo}"/>
				<input type="hidden" name="clmGbnCode" value="${claimInfo.clmGbnCode}"/>
			
				<caption>클레임 접수 철회 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">철회 사유</th>
						<td class="input">
							<select id="clmWthdrawRsnCode" name="clmWthdrawRsnCode" class="ui-sel" title="철회유형 선택">
								<option value="">철회유형 선택</option>
								<c:forEach var="code" items="${CLM_WTHDRAW_RSN_CODE}" varStatus="status">
									<option value="${code.codeDtlNo}" <c:if test="${code.codeDtlNo eq claimInfo.clmWthdrawRsnCode}">selected</c:if>>${code.codeDtlName}</option>
								</c:forEach>
							</select>

							<textarea class="ui-textarea" id="clmWthdrawContText" name="clmWthdrawContText" title="철회 사유 입력">${claimInfo.clmWthdrawContText}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">철회일시</th>
						<td>
							<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT}">
								<fmt:formatDate value="${claimInfo.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
							</c:if>
						</td>
					</tr>
				</tbody>
				
				</form>
				<%--  E : claimWthdrawForm --%>
			</table>
			<!-- E : tbl-row -->
			</c:if>
			
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- DESC : 화면별 버튼 변경시 버튼 UI .btn-normal.btn-func 사용 -->
				<a href="#" class="btn-normal btn-func" id="clmWthdrawBtn" style="display:none;">반품접수철회</a>
				<a href="#" class="btn-normal btn-func" id="clmRetrievalBtn" style="display:none;">수거지시</a>
				<a href="#" class="btn-normal btn-func" id="clmReceiptBtn" style="display:none;">수령완료</a>
				<a href="#" class="btn-normal btn-func" id="clmJudgeBtn" style="display:none;">심의완료</a>
				<a href="#" class="btn-normal btn-func" id="clmFinishBtn" style="display:none;">반품완료</a>
				<c:if test="${redempSavePointBtnShowYn eq Const.BOOLEAN_TRUE}"><%-- 환수포인트 결제 버튼 --%>
					<a href="#" class="btn-normal btn-func" id="clmRedempPointBtn" style="display:none;">포인트환수접수</a>
				</c:if>
				<a href="#" class="btn-normal btn-func" id="winClostBtn" onclick="window.close();">확인</a>
			</div>
			<!-- E : window-btn-group -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 메모</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<%--  S : claimMemoForm --%>
			<form id="claimMemoForm" name="claimMemoForm">
			<input type="hidden" name="clmNo" value="${claimInfo.clmNo}"/>
			<input type="hidden" name="clmGbnCode" value="${claimInfo.clmGbnCode}"/>
			<input type="hidden" name="orgOrderNo" value="${claimInfo.orgOrderNo}"/>
			
			<!-- S : manager-msg-wrap -->
			<div class="manager-msg-wrap">
				<div class="msg-box">
					<textarea title="관리자 메모 입력" id="memoText" name="memoText"></textarea>
				</div>
				<button type="button" class="btn-normal btn-save" id="registClaimMemoBtn">저장</button>
			</div>
			<!-- E : manager-msg-wrap -->
			
			<!-- S : msg-list-wrap -->
			<div class="msg-list-wrap" id="claimMemoListArea">
			</div>
			<!-- E : msg-list-wrap -->
			
			</form>
			<%--  E : claimMemoForm --%>

		</div>
		
		<%-- KCP 결제 폼 --%>
		<form id="order_info" name="order_info" method="post" >
			<input type="hidden" name="requestDiv" value=""/>
			<input type="hidden" name="pay_method" value="<c:out value='${kcpPaymentApproval.payMethod}'/>"/>  <%-- 지불방법 --%>
			<input type="hidden" name="ordr_idxx" value="<c:out value='${kcpPaymentApproval.ordrIdxx}'/>"/>    <%-- 주문번호 --%>
			<input type="hidden" name="good_name" value="<c:out value='${kcpPaymentApproval.goodName}'/>"/>    <%-- 상품명(good_name) --%>
			<input type="hidden" name="good_mny" value="<c:out value='${kcpPaymentApproval.goodMny}'/>"/>      <%-- 결제금액(good_mny) - ※ 필수 : 값 설정시 ,(콤마)를 제외한 숫자만 입력하여 주십시오. --%>
			<input type="hidden" name="buyr_name" value="<c:out value='${kcpPaymentApproval.buyrName}'/>"/>    <%-- 주문자명(buyr_name) --%>
			<input type="hidden" name="buyr_mail" value="<c:out value='${kcpPaymentApproval.buyrMail}'/>" />   <%-- 주문자 E-mail(buyr_mail) --%>
			<input type="hidden" name="buyr_tel1" value="<c:out value='${kcpPaymentApproval.buyrTel1}'/>"/>    <%-- 주문자 연락처1(buyr_tel1) --%>
			<input type="hidden" name="buyr_tel2" value="<c:out value='${kcpPaymentApproval.buyrTel2}'/>"/>    <%-- 휴대폰번호(buyr_tel2) --%>
			<input type="hidden" name="req_tx" value="<c:out value='${kcpPaymentApproval.reqTx}'/>" />         <%-- 요청종류 : 승인(pay)/취소,매입(mod) 요청시 사용 --%>
			<input type="hidden" name="site_cd" value="<c:out value="${siteCd}"/>" />
			<input type="hidden" name="site_name" value="<c:out value="${siteName}"/>" />
			<input type="hidden" name="quotaopt" value="<c:out value="${kcpPaymentApproval.quotaopt}"/>"/>     <%-- 할부옵션 --%>
			<input type="hidden" name="currency" value="<c:out value="${kcpPaymentApproval.currency}"/>"/>     <%-- 필수 항목 : 결제 금액/화폐단위 --%>
			<input type="hidden" name="module_type" value="<c:out value='${moduleType}'/>"/>    <%-- 표준웹 설정 정보입니다(변경 불가) --%>
			<input type="hidden" name="skin_indx" value="<c:out value='${kcpPaymentApproval.skinIndx}'/>"/>    <%-- 결제창 스킨 --%>
			<input type="hidden" name="site_logo" value="<c:out value='${kcpPaymentApproval.siteLogo}'/>"/>    <%-- 사이트 로고 이미지 --%>
			<input type="hidden" name="escw_used" value="<c:out value='${kcpPaymentApproval.escwUsed}'/>"/>
			<input type="hidden" name="pay_mod" value="<c:out value='${kcpPaymentApproval.payMod}'/>"/>
			<input type="hidden" name="bask_cntx" value="<c:out value='${kcpPaymentApproval.baskCntx}'/>"/>
			<input type="hidden" name="deli_term" value="<c:out value='${kcpPaymentApproval.deliTerm}'/>"/>
			<input type="hidden" name="good_info" value="${kcpPaymentApproval.goodInfo}"/>
			<input type="hidden" name="disp_tax_yn" value="Y"/>	<%-- 현금영수증 등록 창을 출력 여부를 설정하는 파라미터 입니다 --%>
			<input type="hidden" name="vcnt_expire_term" value="<c:out value='${kcpPaymentApproval.vcntExpireTerm}'/>"/>	<%--  가상계좌 입금 기한 설정하는 파라미터 - 발급일 + 설정일 --%>
			<input type="hidden" name="vcnt_expire_term_time" value="<c:out value='${kcpPaymentApproval.vcntExpireTermTime}'/>"/>	<%--  가상계좌 입금 시간 설정하는 파라미터 HHMMSS형식설정을 안하시는경우 기본적으로 23시59분59초가 세팅이 됩니다 --%>
			<input type="hidden" name="res_cd" value=""/>				<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="res_msg" value=""/>				<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="enc_info" value=""/>				<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="enc_data" value=""/>				<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="ret_pay_method" value=""/>		<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="tran_cd" value=""/>				<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="bank_name" value=""/>
			<input type="hidden" name="bank_issu" value=""/>
			<input type="hidden" name="use_pay_method" value=""/>		<%--  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다. 값을 설정하지 마십시오 --%>
			<input type="hidden" name="ordr_chk" value=""/>				<%--  주문정보 검증 관련 정보 : 표준웹 에서 설정하는 정보입니다 --%>
			<input type="hidden" name="cash_tsdtime" value=""/>
			<input type="hidden" name="cash_yn" value="N"/>				<%--  현금영수증 관련 정보 : 표준웹 에서 설정하는 정보입니다 --%>
			<input type="hidden" name="cash_authno" value=""/>
			<input type="hidden" name="cash_tr_code" value=""/>			<%--  현금영수증 관련 정보 : 표준웹 에서 설정하는 정보입니다 --%>
			<input type="hidden" name="cash_id_info" value=""/>			<%--  현금영수증 관련 정보 : 표준웹 에서 설정하는 정보입니다 --%>
			<input type="hidden" name="good_expr" value="0"/>			<%--  제공 기간 설정 0:일회성 1:기간설정(ex 1:2012010120120131) --%>
			<input type="hidden" name="orderNum" value="<c:out value='${kcpPaymentApproval.ordrIdxx}'/>"/>
		</form>
		
	</div>
</body>

<script src="/static/common/js/biz/claim/abcmart.claim.return.detail.js<%=_DP_REV%>"></script>
