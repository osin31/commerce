<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

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
			abc.biz.claim.exchange.create.registClaimExchange(); // 클레임 교환 등록
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
		abc.biz.claim.exchange.create.consts.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT = "${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT}"; // 추가배송비결제방법 : 추가결제
		abc.biz.claim.exchange.create.consts.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON = "${CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON}"; // 추가배송비결제방법 : 무료쿠폰
		abc.biz.claim.exchange.create.consts.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE = "${CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE}"; // 클레임 사유 코드(교환) : 옵션변경
		
		abc.biz.claim.exchange.create.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.exchange.create.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.exchange.create.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.exchange.create.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		<%-- 접수 초기 회수지, 재발송지 기본정보 set(주문정보 배송지 이용) --%>
		abc.biz.claim.exchange.create.setDefaultAddrInfo();
		
		<%-- 일괄적용 처리용 콤보박스 변경 --%>
		$("#codeDivSelect").change(function() {
			abc.biz.claim.exchange.create.changeComboForBatchApply();
		});
		
		<%-- 일괄적용 --%>
		$("#batchApplyBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.batchApplyProduct();
		});
		
		<%-- 클레임 교환 접수 --%>
		$("#claimRegistBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.registClaimExchangePreProc();
		});
		
		<%-- 선택한 상품 일괄 적용(특수문자 제거) --%>
		$("#inputReasonText").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 클레임상품사유 입력 제한(특수문자 제거) --%>
		$("textarea[name='clmEtcRsnText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 고객알림내용 입력 제한(특수문자 제거) --%>
		$("textarea[name='cstmrAlertContText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 클레임 메모 입력 제한(특수문자 제거) --%>
		$("textarea[name='memoText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 회수지 우편번호 찾기 --%>
	    $("#retrievalPostSearchBtn").click(function() {
	        abc.postPopup(abc.biz.claim.exchange.create.setRetrievalPost);
	    });

	    <%-- 재배송지 우편번호 찾기 --%>
	    $("#deliveryPostSearchBtn").click(function() {
	        abc.postPopup(abc.biz.claim.exchange.create.setDeliveryPost);
	    });
	
		<%-- 배송주소와 동일, 교환품수거지와 동일 체크박스 클릭 --%>
		$("#chkDeliveryModule, #chkDeliveryModule02").click(function() {
			abc.biz.claim.exchange.create.toggleSameDelivery(this);
        });

		<%-- 회수지 우편번호 입력 제한(숫자만) --%>
		$("#buyerPostCodeText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 회수자 이름 입력 제한(특수문자 제거) --%>
		$("#buyerName").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 회수지 연락처 입력 제한(숫자만) --%>
		$("#buyerHdphnNoText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 재발송 수령지 우편번호 입력 제한(숫자만) --%>
		$("#rcvrPostCodeText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 재발송 수령자 이름 입력 제한(특수문자 제거) --%>
		$("#rcvrName").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 재발송 수령지 연락처 입력 제한(숫자만) --%>
		$("#rcvrHdphnNoText").off().on('keyup', function() {
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 체크박스 전체 체크/해제 --%>
		$("#chkClaimBatchAll").click(function() {
			abc.biz.claim.exchange.create.toggleCheckAll("chkClaimBatchAll", "chkTarget");
        });
		
		<%-- 교환유형 변경 --%>
		$("#claimProductForm select[name=clmRsnCode]").change(function() {
			if($(this).val() == abc.biz.claim.exchange.create.consts.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE) {
				abc.biz.claim.exchange.create.setFormOptionChange(true, $("#claimProductForm select[name=clmRsnCode]").index(this));
			} else {
				abc.biz.claim.exchange.create.setFormOptionChange(false, $("#claimProductForm select[name=clmRsnCode]").index(this));
			}
        });
		
		<%-- 옵션변경 팝업 --%>
		$("#claimProductForm button[name=changeOptnBtn]").click(function() {
			abc.biz.claim.exchange.create.optionChangePop($("#claimProductForm button[name=changeOptnBtn]").index(this));
        });
		
		// 화면 로드시 교환사유 콤보 표현
		$("#codeDivSelect").change();
		
		$("#adminBoPop").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.adminBoPop(this);
        });
		
		$("#orderBoPop").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.orderBoPop(this);
        });
		
		$("a[name='prdtBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.prdtBoPop(this);
        });
		
		$("a[name='prdtFo']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.prdtFo(this);
        });
		
		$("a[name='vndrBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.exchange.create.vndrBoPop(this);
		});
		
	});
	
	// 옵션변경 팝업 콜백펑션
	function optionChangePopCallBack(data) {
		abc.biz.claim.exchange.create.optionChangePopCallBackProc(data);
	}
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>교환 접수</h1>
		</div>
		
		<div class="window-content">
		
			<form id="claimProductForm" name="claimProductForm" method="post">
			<input type="hidden" id="orderNo" value="${orderInfo.orderNo}"/>
			<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${orderInfo.orgOrderNo}"/>
			<input type="hidden" id="order_rcvrName" name="order_rcvrName" value="${orderInfo.rcvrName}"/>
			<input type="hidden" id="order_rcvrHdphnNoText" name="order_rcvrHdphnNoText" value="${fn:trim(fn:replace(orderInfo.rcvrHdphnNoText, '-', ''))}"/>
			<input type="hidden" id="order_rcvrPostCodeText" name="order_rcvrPostCodeText" value="${fn:trim(fn:replace(orderInfo.rcvrPostCodeText, '-', ''))}"/>
			<input type="hidden" id="order_rcvrPostAddrText" name="order_rcvrPostAddrText" value="${orderInfo.rcvrPostAddrText}"/>
			<input type="hidden" id="order_rcvrDtlAddrText" name="order_rcvrDtlAddrText" value="${orderInfo.rcvrDtlAddrText}"/>
			
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
		
			<input type="hidden" id="siteNo" value="${orderInfo.siteNo}"/>
		
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title"><a href="#" class="link"><span>${orderInfo.loginId}</span> (<span>${orderInfo.buyerName}</span>)</a>님의 교환접수 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>교환 상세 정보</caption>
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
								<input id="chkModule" type="checkbox" name="unProcYn" value="Y">
								<label for="chkModule">미처리표시</label>
							</span>
						</td>
						<th scope="row">접수자</th>
						<td><a href="javascript:void(0)" class="link" adminno="${adminNo}" id="adminBoPop">${adminLoginId}(${adminLoginName})</a></td>
						<th scope="row">접수일시</th>
						<td>-</td>
					</tr>
					<tr>
						<th scope="row">사이트·주문번호</th>
						<td>
							<c:choose>
								<c:when test="${orderInfo.rsvOrderYn eq Const.BOOLEAN_TRUE}">[예약]&nbsp;</c:when>
								<c:otherwise>[일반]&nbsp;</c:otherwise>
							</c:choose>
							${orderInfo.siteName}&nbsp;<a href="javascript:void(0)" class="link" orderno="${orderInfo.orgOrderNo}" id="orderBoPop">${orderInfo.orgOrderNo}</a>
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
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">
						<c:choose>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_C}">자사&nbsp;</c:when>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_V}">업체&nbsp;</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>상품 교환 정보
					</h3>
				</div>
				<div class="fr">
					<span class="guide-text">
						<c:choose>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_C}"></c:when><%-- 자사 --%>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_V}"><a href="javascript:void(0)" class="link" vndrno="${orderProductList[0].vndrNo}" name="vndrBoPop">${orderProductList[0].vndrName}(${orderProductList[0].vndrNo})</a></c:when><%-- 업체 --%>
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
							<option value="clmRsnCodeOpts">교환사유(*)</option>
							<option value="customerNoticeOpts">고객알림내용</option>
						</select>
						<!-- DESC : 선택한 상품 selectbox option값에 따라 유형 선택 option값 변경 -->
						<select id="clmRsnCodeSelect" class="ui-sel" title="교환유형 선택" style="display:none;">
							<option value="">교환유형 선택</option>
							<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
								<!-- 2020.04.16 : 교환시 '브랜드박스훼손' 사유 X -->
								<c:if test="${code.codeDtlNo ne CommonCode.CLM_RSN_CODE_EXCHANGE_BOX_DAMAGE}">
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
			</div>
			<!-- E : tbl-controller -->

			<!-- S : 상품 교환정보 리스트 테이블 -->
			<div class="tbl-wrap scroll-x">
				<table class="tbl-col">
					<caption>구매활동정보</caption>
					<colgroup>
						<col style="width: 65px">
						<col style="width: 100px;">
						<col style="width: 120px;">
						<col style="width: 300px;">
						<col style="width: 100px;">
						<col style="width: 130px;">
						<col style="width: 130px;">
						<col style="width: 70px;">
						<col style="width: 100px;">
						<col style="width: 120px;">
						<col style="width: 140px;">
						<col style="width: 120px;">
						<col style="width: 300px;">
						<col style="width: 300px;">
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
							<th scope="col">상품코드</th>
							<th scope="col">업체상품코드</th>
							<th scope="col">상품명</th>
							<th scope="col">스타일</th>
							<th scope="col">옵션</th>
							<th scope="col">변경후옵션</th>
							<th scope="col">수량</th>
							<th scope="col">결제금액</th>
							<th scope="col">발송처</th>
							<th scope="col">배송ID</th>
							<th scope="col">주문배송현황</th>
							<th scope="col"><span class="th-required">교환사유</span></th>
							<th scope="col">고객알림내용</th>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="orderProduct" items="${orderProductList}" varStatus="status">
					<c:set var="loopIdx" value="${status.index}"/>
						<tr>
							<td class="only-chk">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkClaimBatch${loopIdx}" type="checkbox" name="chkTarget"><%-- 클레임상품순번 --%>
									<label for="chkClaimBatch${loopIdx}"></label>
									<input type="hidden" name="orderNo" value="${orderProduct.orderNo}"/>
									<input type="hidden" name="orderPrdtSeq" value="${orderProduct.orderPrdtSeq}"/>
									<input type="hidden" name="vndrNo" value="${orderProduct.vndrNo}"/>
									<input type="hidden" name="prdtNo" value="${orderProduct.prdtNo}"/>
									<input type="hidden" name="dlvyStatCode" value="${orderProduct.dlvyStatCode}"/>
								</span>
							</td>
							<td class="text-center">
								<a href="javascript:void(0)" class="link" prdtno="${orderProduct.prdtNo}" name="prdtBoPop">${orderProduct.prdtNo}</a>
							</td>
							<td class="text-center">${orderProduct.vndrPrdtNoText}</td>
							<td class="input clear-float">
								<!-- S : prod-box -->
								<span class="prod-box">
									<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
												썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
												이미지 크기 변경시 css 수정 필요 -->
									<c:choose>
										<c:when test="${not empty orderProduct.imageUrl}">
											<span class="thumb-box" style="background-image:url(${orderProduct.imageUrl}?shrink=100:100); background-size: 100px 100px;"></span>
										</c:when>
										<c:otherwise>
											<span class="thumb-box" style="background-image:url('/static/images/common/no_image.png');"></span>
										</c:otherwise>
									</c:choose>
									<a href="javascript:void(0)" class="link prdtDetail" prdtno="${orderProduct.prdtNo}" name="prdtFo">
										${orderProduct.prdtName}
										<c:choose>
											<c:when test="${!empty orderProduct.giftPrdtName and orderProduct.giftPrdtName ne null}">
												<input type="hidden" name="orderGiftPrdtSeq" value="${orderProduct.orderGiftPrdtSeq}"/>
												<br/>┖사은품 : ${orderProduct.giftPrdtName}
											</c:when>
											<c:otherwise>
												<input type="hidden" name="orderGiftPrdtSeq" value=""/>
											</c:otherwise>
										</c:choose>
									</a>
								</span>
								<!-- E : prod-box -->
							</td>
							<td class="text-center">${orderProduct.styleInfo}</td>
							<td class="input clear-float text-center">
								<span class="text">${orderProduct.optnName}</span>
								<input type="hidden" name="prdtOptnNo" value="${orderProduct.prdtOptnNo}"/>
								<input type="hidden" name="optnName" value="${orderProduct.optnName}"/>
							</td>
							<td class="input clear-float text-center">
								<span class="text" id="changeOptnText${loopIdx}" style="display:none"></span>
								<br/>
								<button type="button" class="btn-sm btn-link" name="changeOptnBtn" style="display:none">옵션(사이즈)변경</button>
								<input type="hidden" name="changePrdtOptnNo"/>
								<input type="hidden" name="changeOptnName"/>
							</td>
							<td class="text-center">1</td>
							<td class="text-center"><fmt:formatNumber value="${orderProduct.orderAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></td>
							<td class="text-center">${orderProduct.stockGbnCodeName}</td>
							<td class="text-center">${orderProduct.dlvyIdText}</td>
							<td class="text-center">${orderProduct.dlvyStatCodeName}</td>
							<td class="input">
								<select class="ui-sel" title="교환유형 선택" name="clmRsnCode">
									<option value="">교환유형 선택</option>
									<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
										<!-- 2020.04.16 : 교환시 '브랜드박스훼손' 사유 X -->
										<c:if test="${code.codeDtlNo ne CommonCode.CLM_RSN_CODE_EXCHANGE_BOX_DAMAGE}">
											<option value="${code.codeDtlNo}" <c:if test="${code.codeDtlNo eq claimProduct.clmRsnCode}">selected</c:if>>${code.codeDtlName}</option>
										</c:if>
									</c:forEach>
								</select>
								<textarea class="ui-textarea" title="교환사유 입력" name="clmEtcRsnText"></textarea>
							</td>
							<td class="input">
								<textarea class="ui-textarea" title="고객알림 내용 입력" name="cstmrAlertContText"></textarea>
							</td>
						</tr>
						
					</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- E : 상품 교환정보 리스트 테이블 -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">접수 기본정보 및 배송비</h3>
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
									<span class="th-required">교환상품 회수지</span>
								</th>
								<td class="input">
									<span class="address-box">
										<span class="zip-code-wrap">
											<span class="fl">
												<input type="text" class="ui-input" title="우편번호 입력" id="buyerPostCodeText" name="buyerPostCodeText" maxlength="5" readonly>
												<button type="button" class="btn-sm btn-link" id="retrievalPostSearchBtn">우편번호 찾기</button>
											</span>
											<span class="fr">
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkDeliveryModule" type="checkbox" checked="checked">
													<label for="chkDeliveryModule">배송주소와 동일</label>
												</span>
											</span>
										</span>
										<span class="address-wrap">
											<input type="text" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" id="buyerPostAddrText" name="buyerPostAddrText" readonly>
											<input type="text" class="ui-input" placeholder="상세주소" title="상세주소 입력" id="buyerDtlAddrText" name="buyerDtlAddrText">
										</span>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">회수 택배정보</th>
								<%-- <td>대한통운 <a href="#" class="link">1234567889900</a></td> --%>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">발송인 명&#47;연락처</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" class="ui-input name" title="발송인명 입력" id="buyerName" name="buyerName">
										<span class="text">&#47;</span>
										<input type="text" class="ui-input phone-number" title="연락처 입력" id="buyerHdphnNoText" name="buyerHdphnNoText">
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">교환상품 <br />재발송 수령지</span>
								</th>
								<td class="input">
									<span class="address-box">
										<span class="zip-code-wrap">
											<span class="fl">
												<input type="text" class="ui-input" title="우편번호 입력" id="rcvrPostCodeText" name="rcvrPostCodeText" maxlength="5" readonly>
												<button type="button" class="btn-sm btn-link" id="deliveryPostSearchBtn">우편번호 찾기</button>
											</span>
											<span class="fr">
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkDeliveryModule02" type="checkbox" checked="checked">
													<label for="chkDeliveryModule02">교환상품수거지와 동일</label>
												</span>
											</span>
										</span>
										<span class="address-wrap">
											<input type="text" class="ui-input" placeholder="시/군/구 동" title="시/군/구 동 입력" id="rcvrPostAddrText" name="rcvrPostAddrText" readonly>
											<input type="text" class="ui-input" placeholder="상세주소" title="상세주소 입력" id="rcvrDtlAddrText" name="rcvrDtlAddrText">
										</span>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">재수령자명&#47;연락처</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" class="ui-input name" title="재수령자명 입력" id="rcvrName" name="rcvrName">
										<span class="text">&#47;</span>
										<input type="text" class="ui-input phone-number" title="연락처 입력" id="rcvrHdphnNoText" name="rcvrHdphnNoText">
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
						<caption>접수 기본정보 및  배송비</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									<span class="th-required">교환 배송비</span>
								</th>
								<td class="input">
									<select class="ui-sel" title="교환 배송비 선택" id="addDlvyAmt" name="addDlvyAmt">
										<option value="0">무료</option>
										<option value="2500" <c:if test="${claimDeliveryAmt eq '2500'}">selected="selected"</c:if>>2,500</option>
										<option value="5000" <c:if test="${claimDeliveryAmt eq '5000'}">selected="selected"</c:if>>5,000</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">결제수단</th>
								<td class="input">
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
													무료교환 쿠폰
													<c:if test="${empty usableCouponList or usableCouponList eq null}">&nbsp;(선택 가능한 무료교환 쿠폰이 없습니다.)</c:if>
												</label>
											</span>
											<!-- DESC : 무료교환쿠폰 선택시 disabled 속성 삭제해주세요 -->
											<select class="ui-sel size-full" title="무료교환 쿠폰 선택" id="holdCpnSeq" name="holdCpnSeq" 
													<c:if test="${empty usableCouponList or usableCouponList eq null}">disabled="disabled"</c:if>>
												<option value="">무료교환쿠폰선택</option>
												<c:forEach var="coupon" items="${usableCouponList}" varStatus="status">
													<option value="${coupon.holdCpnSeq}">${coupon.cpnName}</option>
												</c:forEach>
											</select>
										</li>
									</ul>
									<!-- E : ip-box-list -->
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
					<h3 class="content-title">관리자 메모</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : manager-msg-wrap -->
			<div class="manager-msg-wrap">
				<div class="msg-box" style="width:100%;">
					<textarea title="관리자 메모 입력" name="memoText"></textarea>
				</div>
			</div>
			<!-- E : manager-msg-wrap -->
			
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- DESC : 화면별 버튼 변경시 버튼 UI .btn-normal.btn-func 사용 -->
				<a href="#" class="btn-normal btn-func" id="claimRegistBtn">교환접수</a>
			</div>
			<!-- E : window-btn-group -->
			
			</form>

		</div>

		<%-- KCP 결제 폼 --%>
		<form id="order_info" name="order_info" method="post" >
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
			<input type="hidden" name="vcnt_expire_term_time" value="<c:out value='${kcpPaymentApproval.vcntExpireTermTime}'/>"/>	<%--  가가상계좌 입금 시간 설정하는 파라미터 HHMMSS형식설정을 안하시는경우 기본적으로 23시59분59초가 세팅이 됩니다 --%>
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

<script src="/static/common/js/biz/claim/abcmart.claim.exchange.create.js<%=_DP_REV%>"></script>
