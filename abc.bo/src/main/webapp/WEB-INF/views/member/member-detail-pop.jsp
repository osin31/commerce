<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 초기화면 로드(회원정보 탭) --%>
		abc.biz.member.memberDetail.detailTabLoad("tabMember");

		<%-- 탭 클릭 이벤트 --%>
		$("#tabID").on( "tabsbeforeactivate", function( event, ui ) {
			console.log(event);
			abc.biz.member.memberDetail.detailTabChange(ui);
		} );

		<%-- SMS 보내기 --%>
		$("#btnSendTextMsg").off().on("click", function(f) {
			var _params = {
					siteNo : "",
					memberNo : "${memberNo}",
					recvTelNoText : "",
					rcvrName : ""
			};
			abc.sendSmsPopup(_params);
		});

		<%-- Email 보내기 --%>
		$("#btnSendMail").off().on("click", function(f) {
			var _params = {
					siteNo : "",
					rcvrMemberNo : "${memberNo}",
					rcvrEmailAddrText : "",
					rcvrName : ""
			};
			abc.sendMailPopup(_params);
		});
		
		<%-- 상품 검색 --%>
		$("#btnSearchProduct").off().on("click", function(f) {
			abc.productSearchPopup(true, null);
		});

	});

</script>

<body class="window-body">
	<div class="window-wrap">
		<input type="hidden" id="memberNo" name="memberNo" value="${memberNo}">
		<input type="hidden" id="safeKey" name="safeKey" value="${memberDefaultInfo.safeKey}">
		<input type="hidden" id="safeKeySeq" name="safeKeySeq" value="${memberDefaultInfo.safeKeySeq}">
		<input type="hidden" id="memberTypeCode" name="memberTypeCode" value="${memberDefaultInfo.memberTypeCode}">
		<div class="window-title">
			<h1>회원정보</h1>
		</div>
		<div class="window-content">
			<div class="user-info-wrap">
				<div class="user-info">
					<div>
						<!-- S : 190121 추가 | 회원이 블랙리스트 포함인 경우만 추가 -->
						<c:if test="${memberDefaultInfo.blackListYn eq 'Y'}">
						<span class="user-blacklist">블랙리스트</span>
						</c:if>
						<!-- E : 190121 추가 | 회원이 블랙리스트 포함인 경우만 추가 -->
						<span class="user-grade">[<c:out value="${memberDefaultInfo.memberTypeCodeName}"/>]</span>
						<span class="user-id"><c:out value="${memberDefaultInfo.detailLoginId}"/></span>
						<span class="user-name">(<c:out value="${memberDefaultInfo.detailMemberName}"/>)</span><span>님</span>
						<c:if test="${memberDefaultInfo.mbshpCardNo ne null}">
						<span class="user-membership">(멤버십: <c:out value="${memberDefaultInfo.mbshpCardNo}"/>)</span>
						</c:if>
					</div>
					<div class="user-login-info">
						<ul class="user-login-info-list">
							<li>최종 로그인 <fmt:formatDate value="${memberDefaultInfo.lastLoginDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></li>
							<c:if test="${memberDefaultInfo.memberState eq 'Y'}">
								<li class="dormancy-info">휴면예정</li>
							</c:if>
						</ul>
					</div>
				</div>
				<div class="user-info-util">
					<!-- DESC : html/common/BO-CM-01003.html 파일 확인 해주세요. -->
					<button type="button" id="btnSendTextMsg" class="btn-normal btn-link">SMS 보내기</button>
					<!-- DESC : html/common/BO-CM-01004.html 파일 확인 해주세요. -->
					<button type="button" id="btnSendMail" class="btn-normal btn-link">이메일 보내기</button>
					<div class="divide">
						<button type="button" id="btnSearchProduct" class="btn-normal btn-link">상품 검색</button>
					</div>
				</div>
			</div>
			<!-- S : tab-wrap -->
			<div id="tabID" class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item">
						<a href="#tabMember" id="tabMemberMove" class="tab-link">회원정보</a>
					</li>
					<li class="tab-item">
						<a href="#tabOrder" id="tabOrderMove" class="tab-link">주문내역</a>
					</li>
					<li class="tab-item">
						<a href="#tabGiftCardPurchase" id="tabGiftCardPurchaseMove" class="tab-link">기프트카드 구매내역</a>
					</li>
					<li class="tab-item">
						<a href="#tabInquiry" id="tabInquiryMove" class="tab-link">문의내역</a>
					</li>
					<li class="tab-item">
						<a href="#tabProdInquiry" id="tabProdInquiryMove" class="tab-link">상품 Q&amp;A</a>
					</li>
					<li class="tab-item">
						<a href="#tabProdReview" id="tabProdReviewMove" class="tab-link">상품후기</a>
					</li>
					<li class="tab-item">
						<a href="#tabCoupon" id="tabCouponMove" class="tab-link">쿠폰관리</a>
					</li>
					<li class="tab-item">
						<a href="#tabPoint" id="tabPointMove" class="tab-link">포인트 관리</a>
					</li>
					<li class="tab-item">
						<a href="#tabEvent" id="tabEventMove" class="tab-link">이벤트 내역</a>
					</li>
				</ul>
				<!-- S:tab_content 회원 정보  -->
				<div id="tabMember" class="tab-content"></div>
				<!-- E:tab_content 회원 정보  -->

				<!-- S:tab_content 주문 내역  -->
				<div id="tabOrder" class="tab-content"></div>
				<!-- E:tab_content 주문 내역  -->

				<!-- S:tab_content 주문 내역  -->
				<div id="tabGiftCardPurchase" class="tab-content"></div>
				<!-- E:tab_content 주문 내역  -->

				<!-- S:tab_content 문의 내역  -->
				<div id="tabInquiry" class="tab-content"></div>
				<!-- E:tab_content 문의 내역  -->

				<!-- S:tab_content 상품 Q&A  -->
				<div id="tabProdInquiry" class="tab-content"></div>
				<!-- E:tab_content 상품 Q&A  -->

				<!-- S:tab_content 상품 후기  -->
				<div id="tabProdReview" class="tab-content"></div>
				<!-- E:tab_content 상품 후기  -->

				<!-- S:tab_content 쿠폰 관리  -->
				<div id="tabCoupon" class="tab-content"></div>
				<!-- E:tab_content 쿠폰 관리  -->

				<!-- S:tab_content 포인트 관리  -->
				<div id="tabPoint" class="tab-content"></div>
				<!-- E:tab_content 포인트 관리  -->

				<!-- S:tab_content 이벤트 내역  -->
				<div id="tabEvent" class="tab-content"></div>
				<!-- E:tab_content 이벤트 내역  -->
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/member/abcmart.member.member.js<%=_DP_REV%>">
</script>