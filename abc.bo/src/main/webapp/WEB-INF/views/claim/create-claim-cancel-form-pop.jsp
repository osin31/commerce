<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- Const 지정 --%>
		abc.biz.claim.cancel.create.consts.CLM_RSN_CODE_CANCEL_ETC = "${CommonCode.CLM_RSN_CODE_CANCEL_ETC}"; // 클레임 사유 코드(취소) : 기타
		
		abc.biz.claim.cancel.create.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.cancel.create.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.cancel.create.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.cancel.create.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		<%-- 클레임 주문취소 접수 --%>
		$("#claimRegistBtn").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.registClaimCancelPreProc();
		});
		
		<%-- 클레임상품사유 입력 제한(특수문자 제거) --%>
		$("#clmEtcRsnTextInput").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 클레임 메모 입력 제한(특수문자 제거) --%>
		$("textarea[name='memoText']").off().on('keyup', function() {
			$(this).val( abc.text.validateStringSignRemove( $(this).val() ) );
		});
		
		<%-- 취소유형 변경 시 기타 사유 활성화 --%>
		$("#clmRsnCodeSelect").change(function() {
			abc.biz.claim.cancel.create.setClmEtcRsnTextInputDisable();
	    });
		
		$("#adminBoPop").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.adminBoPop(this);
        });
		
		$("#orderBoPop").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.orderBoPop(this);
        });
		
		$("a[name='prdtBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.prdtBoPop(this);
        });
		
		$("a[name='prdtFo']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.prdtFo(this);
        });
		
		$("a[name='vndrBoPop']").click(function(e) {
			e.preventDefault();
			abc.biz.claim.cancel.create.vndrBoPop(this);
		});
		
	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>주문 취소 접수</h1>
		</div>
		<div class="window-content">
		
			<input type="hidden" id="freeDlvyStdrAmt" value="${sySite.freeDlvyStdrAmt }">
			<input type="hidden" id="totalOrderAmt" value="${orderInfo.pymntAmt }">
			<input type="hidden" id="alreadyCancelAmt" value="${orderInfo.cnclAmt }">
			<input type="hidden" id="onlyOrderProductCnt" value="${onlyOrderProductCnt }">
		
			<form id="claimProductForm" name="claimProductForm" method="post">
			<input type="hidden" id="orderNo" name="orderNo" value="${orderInfo.orderNo}"/>
			<input type="hidden" id="orgOrderNo" name="orgOrderNo" value="${orderInfo.orgOrderNo}"/>
			
			<input type="hidden" id="siteNo" value="${orderInfo.siteNo}"/>
			
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title"><a href="#" class="link"><span>${orderInfo.loginId}</span> (<span>${orderInfo.buyerName}</span>)</a>님의 주문 취소 정보</h3>
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
							${orderInfo.siteName}&nbsp;<a href="javascript:void(0)" class="link" orderno="${orderInfo.orderNo}" id="orderBoPop">${orderInfo.orderNo}</a>
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
				<%-- 
				<div class="fl">
					<h3 class="content-title">
						<c:choose>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_C}">자사&nbsp;</c:when>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_V}">업체&nbsp;</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>상품 주문 취소 정보
					</h3>
				</div>
				<div class="fr">
					<span class="guide-text">
						<c:choose>
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_C}"></c:when><!-- 자사 -->
							<c:when test="${orderProductList[0].vndrGbnType eq Const.VNDR_GBN_TYPE_V}">${orderProductList[0].vndrName}(${orderProductList[0].vndrNo})</c:when><!-- 업체 -->
							<c:otherwise></c:otherwise>
						</c:choose>
					</span>
				</div>
				--%>
				<div class="fl">
					<h3 class="content-title">
						상품 주문 취소 정보
					</span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selProdModule01">취소사유</label>
						<select id="clmRsnCodeSelect" name="clmRsnCodeSelect" class="ui-sel" title="취소유형 선택">
							<option value="">취소유형 선택</option>
							<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
								<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
							</c:forEach>
						</select>
						<!-- DESC : 취소유형이 기타일 경우에만 노출되는 input -->
						<input id="clmEtcRsnTextInput" name="clmEtcRsnTextInput" type="text" class="ui-input" title="기타 사유 입력" placeholder="내용" disabled="disabled">
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : 상품 취소정보 리스트 테이블 -->
			<div class="tbl-wrap scroll-x">
				<table class="tbl-col">
					<caption>구매활동정보</caption>
					<colgroup>
						<col style="width: 100px;">
						<col style="width: 100px;">
						<col style="width: 80px;">
						<col style="width: 150px;">
						<col style="width: 300px;">
						<col style="width: 100px;">
						<col style="width: 130px;">
						<col style="width: 70px;">
						<col style="width: 100px;">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">상품코드</th>
							<th scope="col">업체상품코드</th>
							<th scope="col">자사상품</th>
							<th scope="col">업체명(업체코드)</th>
							<th scope="col">상품명</th>
							<th scope="col">스타일</th>
							<th scope="col">옵션</th>
							<th scope="col">수량</th>
							<th scope="col">결제금액</th>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="orderProduct" items="${orderProductList}" varStatus="status">
					<c:set var="loopIdx" value="${status.index}"/>
						<tr>
							<td class="text-center">
								<a href="javascript:void(0)" class="link" prdtno="${orderProduct.prdtNo}" name="prdtBoPop">${orderProduct.prdtNo}</a>
								<input type="hidden" name="orderPrdtSeq" value="${orderProduct.orderPrdtSeq}"/>
								<input type="hidden" name="vndrNo" value="${orderProduct.vndrNo}"/>
								<input type="hidden" name="clmRsnCode" value=""/>
								<input type="hidden" name="clmEtcRsnText" value=""/>
							</td>
							<td class="text-center">${orderProduct.vndrPrdtNoText}</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${orderProduct.mmnyPrdtYn eq Const.BOOLEAN_TRUE}">
										예
									</c:when>
									<c:otherwise>
										아니오
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${orderProduct.mmnyPrdtYn eq Const.BOOLEAN_TRUE}">
										<a href="javascript:void(0)" class="link" vndrno="${orderProduct.vndrNo}" name="vndrBoPop">${orderProduct.vndrName}</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" class="link" vndrno="${orderProduct.vndrNo}" name="vndrBoPop">${orderProduct.vndrName}<br/>(${orderProduct.vndrNo})</a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="input clear-float">
								<!-- S : prod-box -->
								<span class="prod-box">
									<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
												썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
												이미지 크기 변경시 css 수정 필요 -->
									<c:choose>
										<c:when test="${not empty orderProduct.imageUrl}">
											<span class="thumb-box" style="background-image:url(${orderProduct.imageUrl}?shrink=100:100);  background-size: 100px 100px;"></span>
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
							<td class="text-center">${orderProduct.optnName}</td>
							<td class="text-center">1</td>
							<td class="text-center"><fmt:formatNumber value="${orderProduct.orderAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/></td>
						</tr>
						
						<input type="hidden" name="prdtOrderAmt" value="${orderProduct.orderAmt}">
						
					</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- E : 상품 취소정보 리스트 테이블 -->

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
				<a href="#" class="btn-normal btn-func" id="claimRegistBtn">취소접수</a>
			</div>
			<!-- E : window-btn-group -->
			
			</form>
			
		</div>
	</div>
</body>

<script src="/static/common/js/biz/claim/abcmart.claim.cancel.create.js<%=_DP_REV%>"></script>
