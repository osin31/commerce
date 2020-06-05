<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		
		<%-- Const 지정 --%>
		abc.biz.claim.cancel.detail.consts.CLM_RSN_CODE_CANCEL_ETC = "${CommonCode.CLM_RSN_CODE_CANCEL_ETC}"; // 클레임 사유 코드(취소) : 기타
		
		abc.biz.claim.cancel.detail.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.cancel.detail.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.cancel.detail.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.cancel.detail.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		<%-- 취소유형 변경 시 기타 사유 활성화 --%>
		$("#clmRsnCodeSelect").change(function() {
			abc.biz.claim.cancel.detail.setClmEtcRsnTextInputDisable();
	    });
		
		<%-- 클레임 상품 저장 --%>
		$("#claimProductSaveBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.saveClaimProduct();
		});
		
		<%-- 클레임 메모 등록 --%>
		$("#registClaimMemoBtn").click(function() {
			abc.biz.claim.cancel.detail.registClaimMemo();
        });
		
		<%-- 클레임 메모 입력 제한(특수문자 제거) --%>
		$("textarea[name='memoText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 클레임 메모 삭제 : ajax 화면 호출 후 dom에 호출화면의 이벤트 트리거 없이 document로 접근  --%>
		$(document).on("click", ".btn-msg-list-del", function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.deleteClaimMemo($(".btn-msg-list-del").index($(this)));
        });
		
		<%-- 미처리  --%>
		$("#unProcYn").click(function() {
			abc.biz.claim.cancel.detail.updateClaimUnProcYn();
        });
		
        <%-- 접수철회  --%>
		$("#clmWthdrawBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.updateClaimWthdraw();
        });
		
		<%-- 취소완료  --%>
		$("#clmFinishBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.finishClaimAllProc();
        });
        
        <%-- 환수환불 접수  --%>
		$("#clmRedempRefundBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.acceptClaimRedempRefund();
        });
		
		<%-- 클레임 메모 목록 ajax 호출 --%>
		abc.biz.claim.cancel.detail.getClaimMemoList();
		
		<%-- 취소 설정금액 초기화  --%>
		$("#resetCnclAmtBtn").click(function(e) {
			abc.biz.claim.cancel.detail.resetCnclAmt();
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
			abc.biz.claim.cancel.detail.accountCertify(this);
        });
		
		$("a[name='prdtBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.prdtBoPop(this);
        });
		
		$("a[name='prdtFo']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.prdtFo(this);
        });
		
		$("a[name='vndrBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.vndrBoPop(this);
        });
		
		$(".getMemberDetail").click(function(e) {
			e.preventDefault();
			abc.memberDetailPopup( $(this).attr("memberno") );
        });

		$(".getAdminDetail").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.getAdminPop();
        });
		
		$(".getOrderDetail").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.detail.getOrderDetail()
		});
		
		<%-- 버튼, 입력 영역 제어 --%>
		uiControlSet();
	});
</script>

<script type="text/javascript">
	function uiControlSet() {
		<c:choose>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT}"><%-- 취소접수 --%>
				<%-- $("#claimProductSaveBtn").show(); --%>
				// 상품 주문취소정보 저장 버튼
				// 2020.03.30 : 환수배송비 발생/미발생된 경우 상품상태 저장만으로는
				//              추가배송비가 재계산되지 않아 철회를 유도한다.
				$("#clmWthdrawBtn").show(); // 취소접수철회 버튼
				$("#clmFinishBtn").show(); // 취소완료 버튼
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT_CANCEL}"><%-- 취소접수취소 --%>
				<%-- none --%>
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT}"><%-- 취소접수철회 --%>
				<%-- none --%>
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_REDEPTION_ACCEPT}"><%-- 환수접수 --%>
				<%-- none --%>
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT}"><%-- 환불접수 --%>
				<%-- none --%>
			</c:when>
			<c:when test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_FINISH}"><%-- 취소완료 --%>
				<%-- none --%>
			</c:when>
		</c:choose>
	}
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>주문 취소 상세</h1>
		</div>
		<div class="window-content">
		
			<%-- S : claimProductForm --%>
			<form id="claimProductForm" name="claimProductForm" method="post">
			<input type="hidden" name="orgOrderNo" value="${claimInfo.orgOrderNo}"/>
			<input type="hidden" name="orderNo" value="${claimInfo.orderNo}"/>
			<input type="hidden" name="clmNo" value="${claimInfo.clmNo}"/>
			<input type="hidden" name="clmStatCode" value="${claimInfo.clmStatCode}"/>
			<input type="hidden" name="clmStatCodeName" value="${claimInfo.clmStatCodeName}"/>
			<input type="hidden" name="clmGbnCode" value="${claimInfo.clmGbnCode}"/>
			
			<input type="hidden" id="chngInputCnclAmtYn" name="chngInputCnclAmtYn" value="N"/><%-- 결제수단별 취소금액 변경 여부 --%>
			<input type="hidden" id="siteNo" name="siteNo" value="${claimInfo.siteNo}"/>
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<c:choose>
						<c:when test="${claimInfo.memberNo eq Const.NON_MEMBER_NO}">
							<h3 class="content-title">[클레임번호 <c:out value="${claimInfo.clmNo}"/>] <span>${claimInfo.memberName}</span>님의 주문취소 정보</h3>
						</c:when>
						<c:otherwise>
							<h3 class="content-title">[클레임번호 <c:out value="${claimInfo.clmNo}"/>] <a href="javascript:void(0)" class="link getMemberDetail" memberno="${claimInfo.memberNo}"><span>${claimInfo.loginId}</span> (<span>${claimInfo.memberName}</span>)</a>님의 주문취소 정보</h3>
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
				<caption>주문 취소 정보</caption>
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
				<%-- 
				<div class="fl">
					<h3 class="content-title">
						<c:choose>
							<c:when test="${claimInfo.vndrGbnType eq Const.VNDR_GBN_TYPE_C}">자사&nbsp;</c:when>
							<c:when test="${claimInfo.vndrGbnType eq Const.VNDR_GBN_TYPE_V}">업체&nbsp;</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>상품 주문 취소 정보
					</h3>
				</div>
				<div class="fr">
					<span class="guide-text">
						<c:choose>
							<c:when test="${claimInfo.vndrGbnType eq Const.VNDR_GBN_TYPE_C}"></c:when><!-- 자사 -->
							<c:when test="${claimInfo.vndrGbnType eq Const.VNDR_GBN_TYPE_V}">${claimInfo.vndrName}(${claimInfo.vndrNo})</c:when><!-- 업체 -->
							<c:otherwise></c:otherwise>
						</c:choose>
					</span>
				</div>
				--%>
				<div class="fl">
					<h3 class="content-title">
						상품 주문 취소 정보 (상품수: ${claimProductList.size()}건)
					</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selProdModule01">취소사유</label>
						<select id="clmRsnCodeSelect" name="clmRsnCodeSelect" class="ui-sel" title="교환유형 선택" readonly>
							<option value="">취소유형 선택</option>
							<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
								<option value="${code.codeDtlNo}" <c:if test="${claimProductList[0].clmRsnCode eq code.codeDtlNo}">selected="selected"</c:if>>${code.codeDtlName}</option>
							</c:forEach>
						</select>
						<!-- DESC : 취소유형이 기타일 경우에만 노출되는 input -->
						<input id="clmEtcRsnTextInput" name="clmEtcRsnTextInput" type="text" class="ui-input" title="기타 사유 입력" placeholder="내용" disabled="disabled">
					</span>
				</div>
				<div class="fr">
					<a href="#" class="btn-sm btn-save" id="claimProductSaveBtn" style="display:none;">저장</a>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : 상품 취소정보 리스트 테이블 -->
			<div id="claimProductArea" class="tbl-wrap scroll-x">
				<table class="tbl-col">
					<caption>구매활동정보</caption>
					<colgroup>
						<col style="width: 100px;">
						<col style="width: 100px;">
						<col style="width: 100px;">
						<col style="width: 80px;">
						<col style="width: 150px;">
						<col style="width: 300px;">
						<col style="width: 100px;">
						<col style="width: 130px;">
						<col style="width: 70px;">
						<col style="width: 100px;">
						<col style="width: 200px;">
						<col style="width: 120px;">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">주문상품순번</th>
							<th scope="col">상품코드</th>
							<th scope="col">업체상품코드</th>
							<th scope="col">자사상품</th>
							<th scope="col">업체명(업체코드)</th>
							<th scope="col">상품명</th>
							<th scope="col">스타일</th>
							<th scope="col">옵션</th>
							<th scope="col">수량</th>
							<th scope="col">결제금액</th>
							<th scope="col">취소사유</th>
							<th scope="col">진행상태</th>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="claimProduct" items="${claimProductList}" varStatus="status">
					<c:set var="loopIdx" value="${status.index}"/>
					<c:if test="${claimProduct.prdtTypeCode ne CommonCode.PRDT_TYPE_CODE_GIFT}"><%-- 사은품은 표현하지 않음 --%>
						<tr>
							<td class="text-center">${claimProduct.orderPrdtSeq}</td>
							<td class="text-center">
								<a href="javascript:void(0)" class="link" prdtno="${claimProduct.prdtNo}" name="prdtBoPop">${claimProduct.prdtNo}</a>
								<input type="hidden" name="clmNo" value="${claimProduct.clmNo}"><%-- 클레임번호 --%>
								<input type="hidden" name="clmPrdtSeq" value="${claimProduct.clmPrdtSeq}"><%-- 클레임상품순번 --%>
								<input type="hidden" name="clmPrdtStatCode" value="${claimProduct.clmPrdtStatCode}"><%-- 클레임상품 상태코드 --%>
								<input type="hidden" name="clmRsnCode" value="${claimProduct.clmRsnCode}"/>
								<input type="hidden" name="clmEtcRsnText" value="${claimProduct.clmEtcRsnText}"/>
							</td>
							<td class="text-center">${claimProduct.vndrPrdtNoText}</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${claimProduct.mmnyPrdtYn eq Const.BOOLEAN_TRUE}">
										예
									</c:when>
									<c:otherwise>
										아니오
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${claimProduct.mmnyPrdtYn eq Const.BOOLEAN_TRUE}">
										<a href="javascript:void()" class="link" vndrno="${claimProduct.vndrNo}" name="vndrBoPop">${claimProduct.vndrName}</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void()" class="link" vndrno="${claimProduct.vndrNo}" name="vndrBoPop">${claimProduct.vndrName}<br/>(${claimProduct.vndrNo})</a>
									</c:otherwise>
								</c:choose>
							</td>
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
													<span class="thumb-box" style="background-image:url(${claimProduct.imageUrl}?shrink=100:100); background-size: 100px 100px;"></span>
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
							<td class="text-center">1</td>
							<td class="text-center"><fmt:formatNumber value="${claimProduct.orderAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></td>
							<td class="text-center">${claimProduct.clmEtcRsnText}</td>
							<td class="text-center">
								<input type="hidden" id="historyClmNo" value="${claimProduct.clmNo}"><%-- 클레임번호 --%>
								<input type="hidden" id="historyClmPrdtSeq" value="${claimProduct.clmPrdtSeq}"><%-- 클레임상품순번 --%>
								<a href="#" id="btnClaimHistory" class="link" onclick="abc.biz.claim.cancel.detail.openClaimHistoryPop(this);">${claimProduct.clmPrdtStatCodeName}</a>
							</td>
						</tr>
					</c:if>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- E : 상품 취소정보 리스트 테이블 -->

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
			
			<c:set var="cancelAllAmt" value="${claimProductSumAmt + claimDlvySumAmt + refundPreviousRedempDlvySumAmt + addMultiBuyDifferSumAmt}" />
			
			<c:forEach var="redempList" items="${redempClaimPaymentList}" varStatus="status">
				<c:set var="redempAllAmt" value="${redempAllAmt + redempList.pymntAmt}" />
			</c:forEach>
			
			<c:forEach var="refundList" items="${refundClaimPaymentList}" varStatus="status">
				<c:set var="refundAllAmt" value="${refundAllAmt + refundList.pymntAmt}" />
			</c:forEach>
			
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
								<div class="fr">
									<a href="javascript:void(0);" class="btn-sm btn-func" id="resetCnclAmtBtn">취소 설정금액 초기화</a>
								</div>
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
										<input type="text" class="ui-input num-unit100000000" title="${pymntAmtTitle}" name="pymntAmt" value="${expectCnclAble.thisPymntCnclAmt}" ${pymntAmtReadOnly}>
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
										<td class="input">
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
												<li>상품금액&nbsp;<fmt:formatNumber value="${claimProductSumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</li>
												<c:if test="${claimDlvySumAmt ne '0'}">
													<li>주문배송비&nbsp;<fmt:formatNumber value="${claimDlvySumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</li>
												</c:if>
												<c:if test="${refundPreviousRedempDlvySumAmt ne '0'}">
													<li>추가배송비&nbsp;<fmt:formatNumber value="${refundPreviousRedempDlvySumAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>원</li>
												</c:if>
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
						<td colspan="3">
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
						<%-- 
						<th scope="row">증감예정 적립 포인트</th>
						<td>취소예정 : ${thisClaimVariationSavePoint} Point</td>
						--%>
					</tr>
					<!-- S : 환수금액 -->
					<%-- 
					<tr>
						<th scope="row">환수계좌</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoVirtualModule1" type="radio" checked="checked">
										<label for="rdoVirtualModule1">가상계좌발급</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoVirtualModule2" type="radio">
										<label for="rdoVirtualModule2">임의처리</label>
									</span>
								</li>
							</ul>
							<ul class="td-text-list" style="margin-bottom:5px;">
								<li>* 포인트에 의한 환수금액 발생시에만 임의처리가 가능합니다.</li>
								<li>* 임의 처리할 경우 가상계좌는 발급 되지 않으며 ,고객에게 환수금액 및 입금 계좌 정보가 고지 되지 않습니다.</li>
								<li>* 취소완료 후에는 가상계좌발급 또는 임의처리로 변경이 불가합니다.</li>
							</ul>
						</td>
					</tr>
					--%>
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
							<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT and claimInfo.rfndAcntText ne null}">
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
			
			<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT or claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT}">
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
							<c:if test="${claimInfo.clmStatCode eq CommonCode.CLM_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT}">
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
				<a href="#" class="btn-normal btn-func" id="clmWthdrawBtn" style="display:none;">취소접수철회</a>
				<a href="#" class="btn-normal btn-func" id="clmFinishBtn" style="display:none;">취소완료</a>
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
	</div>
</body>

<script src="/static/common/js/biz/claim/abcmart.claim.cancel.detail.js<%=_DP_REV%>"></script>
