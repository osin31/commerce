<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function() {
	abc.biz.board.podashboard.event();
	abc.mainPopup([${fn:join(adminNoticeMainPop, ",")}]);
	
});
</script>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<div class="dashboard-box">
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">업체명<span class="company-name">${vndrName }</span></h3>
							<input type="hidden" id="vndrNo" value=${vndrNo } />
							<button type="button" id="vendorInfo" class="btn-sm btn-func">조회</button>
						</div>
					</div>
					<div class="flex-box">
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">상품현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="productRefreshBtn"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">판매/전시중</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="productsellMore">${productsell}</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">일시품절</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="productsoldMore">${productsold}</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인요청</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="productAprvMore">${productAprv}</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인반려</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="productDenyAprvMore">${productDenyAprv}</a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">주문/배송 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="btnOrderRefresh"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">결제완료</span>
										<span class="item-value"><a href="#" class="cnt" id="paymentCompleteVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_COMPLETE_CNT}" default="0"/></fmt:formatNumber></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">상품준비중</span>
										<span class="item-value"><a href="#" class="cnt" id="productIngVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_PRODUCT_PREPARATION_CNT}" default="0"/></fmt:formatNumber></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">배송중</span>
										<span class="item-value"><a href="#" class="cnt" id="deveryIngVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_DLVYING_PICKUP_READY_CNT}" default="0"/></fmt:formatNumber></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">배송지연</span>
										<span class="item-value"><a href="#" class="cnt" id="deveryNotVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_DELAYED_DELIVERY_CNT}" default="0"/></fmt:formatNumber></a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">클레임 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="poClaimRefresh"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">

								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">클레임 접수(미확인 클레임)</span>
										<span class="item-value"><a href="#" id="claimReceipt" class="cnt"><c:out value="${orderPrdtStatCodeClaim}"/></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">회수중</span>
										<span class="item-value"><a href="#" id="recovering" class="cnt"><c:out value="${orderPrdtStatCodeDeliveryReturn}"/></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">교환품 배송중</span>
										<span class="item-value"><a href="#" id="exchangeDlvyIng" class="cnt"><c:out value="${orderPrdtStatCodeExchangeDelivery}"/></a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">클레임 완료</span>
										<span class="item-value"><a href="#" id="claimComplete" class="cnt"><c:out value="${orderPrdtStatCodeClaimComplete}"/></a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">판매 현황</span>
								</div>
							</div>
							<div class="card-body">
								<div class="selling-status-wrap">
									<p class="selling-status-range"><span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
									 ~ 
									 <span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span></p>
									<ul class="selling-status-list">
										<li class="status-item">
											<span class="item-label">주문건수</span>
											<span class="item-value"><a href="#" class="cnt" id="saleStatusOrderCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${poOrderCntAmt.orderCnt}"/></a>건</span>
										</li>
										<li class="status-item">
											<span class="item-label">주문금액</span>
											<span class="item-value"><a href="#" class="cnt blue" id="saleStatusOrderAmt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${poOrderCntAmt.orderAmt}"/></a>원</span>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="flex-box col-4by2">
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">미처리 문의현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="aswrRefreshBtn"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">1:1 문의</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="inquiryMoreView">${inquiryCount}</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">협력 게시판</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id="coworkMoreView">${coworkCount}</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">상품 Q&amp;A</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt" id = "vndrProductInquiryCountMore">${vndrProductInquiryCount}</a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">기획전 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="planningDisplaybtn"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">진행중인 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning_action" id="useplanningMore" data-val="W">${useplanning }</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인요청중인 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning_action" id="usereadyplanningMore" data-val="O">${usereadyplanning }</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인반려 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning_action" id="notuseplanningMore" data-val="D">${notuseplanning }</a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box table">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">공지사항</span>
									<a href="javascript:void(0);" class="btn-sm btn-func" id="adNoticeMoreView">더보기</a>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="adNoticeRefreshBtn"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<table class="tbl-col">
									<caption>공지사항</caption>
									<colgroup>
										<col>
										<col style="width: 130px;">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">제목</th>
											<th scope="col">등록일시</th>
										</tr>
									</thead>
									<tbody id="adNoticeTbody">
										<c:choose>
											<c:when test="${fn:length(adNoticeList) eq 0}">
												<tr>
													<td class="text-center" colspan="2">공지사항이 없습니다.</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="adNotice" items="${adNoticeList}">
													<tr>
														<td><a href="javascript:void(0);" name="${adNotice.adminNotcSeq}" class="detailViewadNotice" title="제목"><p class="noticeTitle">${adNotice.notcTitleText}</p></a></td>
														<td class="text-center">
															<fmt:formatDate value="${adNotice.rgstDtm }" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
														</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- E : container -->
		
<!-- S : 비밀번호 변경 레이어 -->
<%@include file="/WEB-INF/views/system/login/password-change.jsp" %>
<!-- E : 비밀번호 변경 레이어 -->
		
<script src="/static/common/js/biz/main/abcmart.main.po.dashboard.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>
	