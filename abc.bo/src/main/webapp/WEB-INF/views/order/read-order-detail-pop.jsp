<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<%@include file="/WEB-INF/views/order/order-const.jsp"%>
<script type="text/javascript">
	/**
	 * jquery ui tab event
	 */
	$(function() {

		var param = new Object();

		param.memBankCode 		= '<c:out value="${orderDtail.memBankCode}"/>';				// 회원 은행 코드 정보
		param.memberTypeCode 	= '<c:out value="${orderDtail.memberTypeCode}"/>';			// 회원유형 코드

		abc.biz.order.info.tab.initData(param);

		$("#tabID").on( "tabsbeforeactivate", function( event, ui ) {
			ui.oldPanel.empty();
			tabPanelLoad(ui.newPanel.attr("id"));
		} );

		// 주문정보 탭 로드
		tabPanelLoad("tabOrderArea");

		$("#sendEmail").click(function(){
			sendEmail();
		});

		$("#sendSms").click(function(){
			sendSms();
		});

	});

	/**
	 * tab panel에 필요한 화면 ajax 로드
	 */
	function tabPanelLoad(tabid) {
		var callTabUrl;

		// 탭 url 설정
		if(tabid == "tabOrderArea") {
			callTabUrl = "/order/read-order-info-tab";
		} else if(tabid == "tabClaimArea") {
			callTabUrl = "/claim/claim/read-claim-info-tab";
		} else if(tabid == "tabMemoArea") {
			callTabUrl = "/order/read-memo-info-tab";
		} else {
			callTabUrl = "";
		}

		// 탭 화면 로드
		$.ajax({
			type :"post",
			url : callTabUrl,
			data : { orgOrderNo : '${orderDtail.orgOrderNo}', orderNo : '${orderDtail.orderNo}'},
			dataType : "html"
		})
		.done(function(data){
			$("#" + tabid).html(data);
		})
		.fail(function(e){
			alert("--------------fail ");
		});
	}

	function sendEmail(){
		var param = {};
		param.siteNo = $("#siteNo").val();
		param.rcvrMemberNo = $("#memberNo").val();
		if( $("#memberTypeCode").val() == "10002" ){
			param.rcvrEmailAddrText = $("#buyerEmailAddrText").val();
			param.rcvrName = $("#buyerName").val();
		}
		window.abc.sendMailPopup(param);
	}

	function sendSms(){
		var param = {};
		param.siteNo = $("#siteNo").val();
		param.memberNo = $("#memberNo").val();
		if( $("#memberTypeCode").val() == "10002" ){
			param.recvTelNoText = $("#buyerTelNoText").val();
			param.rcvrName = $("#buyerName").val();
		}
		window.abc.sendSmsPopup(param);
	}
</script>

<body class="window-body">
	<input type="hidden" name="siteNo" id="siteNo" value="${orderDtail.siteNo}"/>
	<input type="hidden" name="memberNo" id="memberNo" value="${orderDtail.memberNo}"/>
	<input type="hidden" name="buyerEmailAddrText" id="buyerEmailAddrText" value="${orderDtail.buyerEmailAddrText}"/>
	<input type="hidden" name="buyerName" id="buyerName" value="${orderDtail.buyerName}"/>
	<input type="hidden" name="memberTypeCode" id="memberTypeCode" value="${orderDtail.memberTypeCode}"/>
	<input type="hidden" id="buyerTelNoText" value="${orderDtail.buyerTelNoText}"/>
	
	<!-- 2020.02.15 : 주결제수단이 에스크로 적용되었는지 여부 판단 위해 -->
	<!-- 2020.02.16 : 주결제수단이 무통장결제인지, 무통장결제 입금이 되었는지 여부 -->
	<!-- 2020.03.20 : 주결제수단이 카드일때, 법인카드인지 여부 -->
	<input type="hidden" id="mainPymntMeans" value="${mainPayment.pymntMeansCode }">
	<input type="hidden" id="mainPymntStat" value="${mainPayment.pymntStatCode }">
	<input type="hidden" id="escrApplyYn" value="${mainPayment.escrApplyYn }">
	<input type="hidden" id="mainPymntCardType" value="${mainPayment.cardType}">
	<input type="hidden" id="onlyOrderProductCnt" value="${onlyOrderProductCnt}">
	
	<!-- 2020.04.29 : 원주문번호에 걸린 취소접수상품 갯수 -->
	<input type="hidden" id="cancelAcceptCnt" value="${orderDtail.cancelAcceptCnt}">
	
	<div class="window-wrap">
		<div class="window-title">
			<h1>주문상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<c:choose>
						<c:when test="${orderDtail.memberTypeCode eq '10002' }">
							<h3 class="content-title"><span>${orderDtail.nonUserDpName}</span>님의 주문상세정보</h3>
						</c:when>
						<c:otherwise>
							<h3 class="content-title">
								<c:choose>
									<c:when test="${isPrivateAuth eq Const.BOOLEAN_TRUE}">
										<a href="#" class="link" id="memberPop"><span>${orderDtail.userDpName}</span></a>님의 주문상세정보
									</c:when>
									<c:otherwise>
										<span>${orderDtail.userDpName}</span>님의 주문상세정보
									</c:otherwise>
								</c:choose>
							</h3>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="fr">
					<!-- DESC : html/common/BO-CM-01003.html 파일 확인 해주세요. -->
					<a href="#" class="btn-normal btn-link" id="sendSms">SMS 보내기</a>
					<!-- DESC : html/common/BO-CM-01004.html 파일 확인 해주세요. -->
					<a href="#" class="btn-normal btn-link" id="sendEmail">이메일 보내기</a>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>회원 주문상세정보</caption>
				<colgroup>
					<col style="width: 120px;">
					<col>
					<col style="width: 120px;">
					<col>
					<col style="width: 120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">회원유형</th>
						<td class="input" >
							<span class="text">${orderDtail.memberTypeCodeName}</span>
							<c:if test="${orderDtail.memberTypeCode eq '10002'}">
							<a href="#" class="btn-sm btn-link" id="passwordChange">비회원 비밀번호 변경</a>
							</c:if>
						</td>
						<th scope="row">사이트</th>
						<td>${orderDtail.siteName}</td>  
						<th scope="row">전체주문건</th>
						<td class="input">
							<c:if test="${orderAllConfirmYn eq Const.BOOLEAN_TRUE }">
								<button type="button" class="btn-sm btn-func" id="orderConfirm" >구매확정</button>
							</c:if>
							<button type="button" class="btn-sm btn-del"  id="orderCancel">주문취소</button>
						</td>
					</tr>
					<tr>
						<th scope="row">주문번호</th>
						<td>
							${orderDtail.orderNo}
							&nbsp;
							<c:choose>
								<c:when test="${orderDtail.rsvOrderYn eq 'Y'}" ><font color="red">[예약 / <fmt:formatDate value="${orderDtail.rsvDlvyDtm}" pattern="yyyy-MM-dd"/>]</font></c:when>
								<c:otherwise>[일반]</c:otherwise>
							</c:choose>
						</td>
						<th scope="row">주문일시</th>
						<td><fmt:formatDate value="${orderDtail.orderDtm}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<th scope="row">입점사문의</th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select class="ui-sel" id="vndrNo" name="vndrNo"  title="입점사 선택">
									<option value="">선택</option>
									<c:forEach var="vendorList" items="${orderVendorList}" varStatus="status">
										<option value="${vendorList.vndrNo}">${vendorList.vndrName}</option>
									</c:forEach>
								</select>
								<a href="#" class="btn-sm btn-link" id="vendorInquiry">문의</a>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">클레임</th>
						<td class="input" colspan="5">
							<span class="text">교환 : <span><c:out value="${orderDtail.exchangeCnt}" default="0"/></span>건 /  반품 : <span><c:out value="${orderDtail.returnCnt}" default="0"/></span> 건 / 교환취소 : <span><c:out value="${orderDtail.exchangeCancelCnt}" default="0"/></span> 건 / 반품취소 : <span><c:out value="${orderDtail.returnCancelCnt}" default="0"/></span> 건  / A/S 접수 : <span><c:out value="${orderDtail.asAcceptCnt}" default="0"/></span>건</span>
						</td>
					</tr>
					<!-- S : 20190318 추가 // 총 결제금액 추가 -->
					<tr>
						<th scope="row">총결제금액</th>
						<td class="input" colspan="3">
							<a href="#" class="btn-sm btn-link" id="regInquiry">유선상담 접수</a>"
							<span class="text"> 총 <span><fmt:formatNumber value="${orderDtail.pymntAmt - orderDtail.cancelSumAmt}" pattern="#,###.##" type="number" /></span>원 | 결제금액 (<span><fmt:formatNumber value="${orderDtail.pymntAmt}" pattern="#,###.##" type="number" /></span>원) –클레임 완료금액(<span><fmt:formatNumber value="${orderDtail.cnclAmt}" pattern="#,###.##" type="number" /></span>원)</span>
						</td>
						<th scope="row">총 주문상품 수량</th>
						<td class="input" colspan="1">
							<span class="text"> 총 ${onlyOrderProductCnt }개</span>
						</td>
					</tr>
					<!-- E : 20190318 추가 // 총 결제금액 추가 -->
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tab-wrap -->
			<div id="tabID" class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item">
						<a href="#tabOrderArea" class="tab-link">주문정보</a>
					</li>
					<li class="tab-item"><a href="#tabClaimArea" class="tab-link">클레임관리</a></li>
					<li class="tab-item"><a href="#tabMemoArea" class="tab-link">관리자메모 <span id="totalMemoCnt">(<c:out value="${orderDtail.memoTotCnt}" default="0"/>)</span></a></li>
				</ul>
				<!-- S:tab_content 주문 정보  -->
				<div id="tabOrderArea" class="tab-content"></div>
				<!-- S:tab_content 주문 클레임 정보  -->
				<div id="tabClaimArea" class="tab-content"></div>
				<!-- S:tab_content 주문 메모 정보  -->
				<div id="tabMemoArea" class="tab-content"></div>
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/order/abcmart.order.detail.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/order/abcmart.order.const.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/order/abcmart.order.memo.tab.js<%=_DP_REV%>"></script>
