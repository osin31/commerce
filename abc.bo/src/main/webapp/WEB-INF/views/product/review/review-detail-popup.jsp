<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품후기 상세</h1>
		</div>
		<div class="window-content">
			<!-- S : col-wrap -->
			<div class="col-wrap">
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">게시물 조회</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>게시물 조회</caption>
						<colgroup>
							<col style="width:130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea class="ui-textarea" title="내용 입력" onkeypress="return false;">${review.rvwContText }</textarea>
								</td>
							</tr>
							<c:if test="${not empty review.productReviewImages }">
								<tr>
									<th scope="row">이미지</th>
									<td class="input">
										<div class="photo-comment">
											<!-- S : repeat -->
											<c:forEach var="item" items="${review.productReviewImages }" varStatus="status">
												<div class="img">
													<img src="${item.imageUrl }" alt="${item.imageName }">
												</div>
											</c:forEach>
										</div>
										<!-- E : photo-comment -->
									</td>
								</tr>
							</c:if>
							<tr>
								<th scope="row">평가점수</th>
								<td class="input">
									<div class="rating-text-both">
										<input type="number" class="ip-rating" value="${review.productReviewEvlts[0].evltScore }" title="평가점수 선택" readonly>
										<span class="text">${review.productReviewEvlts[1].prdtRvwCodeName }&nbsp;${review.productReviewEvlts[1].evltScore }&nbsp;/&nbsp;${review.productReviewEvlts[2].prdtRvwCodeName }&nbsp;${review.productReviewEvlts[2].evltScore }</span>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">상품 정보</th>
								<td class="input">
									<ul class="td-text-list">
										<li><span class="text">표준카테고리: ${review.stdCtgrName }</span></li>
										<li><span class="text">사이트 / 채널: ${review.siteName } / ${review.chnnlName } </span></li>
										<li><span class="text">업체상품코드 / 상품코드: ${review.vndrPrdtNoText } /</span> <a href="javascript:void(0)" class="link" onclick="abc.readonlyProductDetailPopup({ prdtNo : '${review.prdtNo}' })">${review.prdtNo }</a></li>
										<li><span class="text">브랜드 : ${review.brandName }</span></li>
										<li><span class="text">상품명 : </span><a href="javascript:void(0)" class="link" id="product-link" onclick="abc.productFrontUrl('${review.prdtNo}');">${review.prdtName }</a></li>
										<li><span class="text">스타일 : ${review.styleInfo }</span></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">주문정보</th>
								<td class="input">
									<ul class="td-text-list">
										<li><span class="text">구입처 : ${review.onlnBuyYn == 'Y' ? '온라인' : '오프라인' }</span></li>
										<li><span class="text">주문번호 : </span>
											<c:choose>
												<c:when test="${review.onlnBuyYn == 'Y' }">
													<a href="javascript:void(0)" onclick="abc.orderDetailPopup('', '', '', '', 'orderNo=${review.orderProduct.orderNo }');" class="link">${review.orderProduct.orderNo }</a>
												</c:when>
												<c:when test="${review.onlnBuyYn == 'N' }">
													${review.offDealHistory.dealNo }
												</c:when>
											</c:choose>
										</li>
										<li><span class="text">결제금액 : ${review.orderProduct.orderAmt }${review.offDealHistory.salePrice }원</span></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">회원</th>
								<td><a href="javascript:void(0)" class="link" onclick="abc.memberDetailPopup('${review.memberNo}')">${UtilsMasking.adminName(review.memberId, review.memberName) }</a> / ${review.memberTypeCodeName }</td>
							</tr>
							<tr>
								<th scope="row">후기작성일시</th>
								<td>
									<fmt:formatDate value="${review.writeDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
									<c:if test="${0 < review.orderDtm }">
										/ 주문일로부터 ${review.orderDtm }일
									</c:if>
									<c:if test="${0 < review.buyDcsnDtm }">
										, 구매확정일로부터 ${review.buyDcsnDtm }일
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">답변관리</h3>
						</div>
						<div class="fr">
							<div class="btn-group">
								<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
							</div>
						</div>
					</div>
					<!-- E : content-header -->
					<form id="detail-form">
						<input type="hidden" name="prdtRvwSeq" value="${review.prdtRvwSeq }">
						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>답변관리</caption>
							<colgroup>
								<col style="width:130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">답변상태</th>
									<td>${review.aswrStatCodeName }</td>
								</tr>
								<tr>
									<th scope="row">답변 스크립트 선택</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select id="cnsl-type-code" name="cnslTypeCode" class="ui-sel" title="상담유형 선택">
												<option value="">직접입력</option>
												<c:forEach var="item" items="${cnslTypeCode}">
													<c:if test="${item.codeDtlNo eq CommonCode.CNSL_TYPE_CODE_PRODUCT_REVIEW }">
														<option value="${item.codeDtlNo}" selected>${item.codeDtlName}</option>
													</c:if>
												</c:forEach>
											</select>
											<select id="cnsl-type-dtl-code" name="cnslTypeDtlCode" class="ui-sel" title="상담분류 선택">
												<option value="">상담분류</option>
												<c:forEach var="item" items="${cnslTypeDtlCode}">
													<option value="${item.codeDtlNo}">${item.codeDtlName}</option>
												</c:forEach>
											</select>
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">답변내용</span>
									</th>
									<td class="input">
										<select id="counsel-script-list" name="counselScriptList" class="ui-sel" title="스크립트제목 선택">
											<option value="">스크립트제목</option>
										</select>
										<textarea class="ui-textarea" title="답변내용 입력" name="aswrContText" id="aswr-cont-text">${review.aswrContText }</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row">포인트 지급</th>
									<td class="input">
										<span class="ip-text-box">
											<c:if test="${review.buyDcsnDtm <= 30 }">
												<!-- 통합회원만 -->
												<c:if test="${review.memberTypeCode eq '10001' }">
													<select class="ui-sel" title="포인트 지급 유형 선택" id="point-pay-type" name="pointPayType">
														<c:choose>
															<c:when test="${review.pointPayType eq 'G'}">
																<option value="G" selected>일반포인트 지급</option>
																<option value="C">지급취소</option>
															</c:when>
															<c:when test="${review.pointPayType eq 'P'}">
																<option value="P" selected>포토포인트 지급</option>
																<option value="C">지급취소</option>
															</c:when>
															<c:when test="${review.pointPayType eq 'C'}">
																<option value="G">일반포인트 지급</option>
																<c:if test="${review.rvwType eq 'P'}">
																	<option value="P">포토포인트 지급</option>
																</c:if>
																<option value="C" selected>지급취소</option>
															</c:when>
															<c:when test="${review.pointPayType eq 'N'}">
																<option value="N">미지급</option>
																<option value="G" selected>일반포인트 지급</option>
																<c:if test="${review.rvwType eq 'P'}">
																	<option value="P" selected>포토포인트 지급</option>
																</c:if>
															</c:when>
															<c:when test="${review.pointPayType eq 'I'}">
																<option value="G">일반포인트 지급</option>
																<c:if test="${review.rvwType eq 'P'}">
																	<option value="P">포토포인트 지급</option>
																</c:if>
																<option value="I" selected>지급불가</option>
															</c:when>
														</c:choose>
													</select>
												</c:if>
												<c:if test="${review.pointPayType eq 'G' or review.pointPayType eq 'P' }">
													<span class="text">${review.payPointAmt }P / <fmt:formatDate value="${review.pointPayDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
												</c:if>
												<c:if test="${review.pointPayType eq 'C' }">
													<span class="text">지급취소일 : <fmt:formatDate value="${review.pointPayCnclDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
												</c:if>
												<c:if test="${review.pointPayType eq 'I' }">
													<!-- 지급불가일은 컬럼이 따로 없다 -->
													<span class="text">지급불가 지정일 :
														<c:if test="${not empty review.modDtm }">
															<fmt:formatDate value="${review.modDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
														</c:if>
														<c:if test="${empty review.modDtm }">
															<fmt:formatDate value="${review.writeDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
														</c:if>
													</span>
												</c:if>
											</c:if>
											<c:if test="${review.buyDcsnDtm > 30 }">
												<c:choose>
													<c:when test="${review.pointPayType eq 'G'}">
														<span class="text">일반포인트 지급 : ${review.payPointAmt }P / <fmt:formatDate value="${review.pointPayDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
													</c:when>
													<c:when test="${review.pointPayType eq 'P'}">
														<span class="text">포토포인트 지급 : ${review.payPointAmt }P / <fmt:formatDate value="${review.pointPayDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
													</c:when>
													<c:when test="${review.pointPayType eq 'C'}">
														<span class="text">지급취소일 : <fmt:formatDate value="${review.pointPayCnclDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
													</c:when>
													<c:when test="${review.pointPayType eq 'I' }">
														<!-- 지급불가일은 컬럼이 따로 없다 -->
														<span class="text">지급불가 지정일 :
															<c:if test="${not empty review.modDtm }">
																<fmt:formatDate value="${review.modDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
															</c:if>
															<c:if test="${empty review.modDtm }">
																<fmt:formatDate value="${review.writeDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
															</c:if>
														</span>
													</c:when>
													<c:when test="${review.pointPayType eq 'N'}">
														<span class="text">지급불가 : 구매확정일 30일 초과</span>
														<input type="hidden" name="pointPayType" value="I">
													</c:when>
												</c:choose>
											</c:if>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">전시여부 </th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="disp-yn-y" name="dispYn" type="radio" value="Y" ${review.dispYn eq 'Y' or empty review.dispYn ? 'checked' : ''}>
													<label for="disp-yn-y">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="disp-yn-n" name="dispYn" type="radio" value="N" ${review.dispYn eq 'N' ? 'checked' : ''}>
													<label for="disp-yn-n">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">베스트여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioBest02" name="bestYn" type="radio" value="N" ${review.bestYn eq 'N' or empty review.bestYn ? 'checked' : ''}>
													<label for="radioBest02">아니오</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioBest03" name="bestYn" value="Y" type="radio" ${review.bestYn eq 'Y' ? 'checked' : ''}>
													<label for="radioBest03">예</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">답변자</th>
									<td>
										<c:choose>
											<c:when test="${not empty review.aswrId and not empty review.aswrName }">
												<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${review.aswrNo }')">${UtilsMasking.adminName(review.aswrId, review.aswrName) }</a>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th scope="row">답변일시</th>
									<td>
										<c:choose>
											<c:when test="${not empty review.aswrDtm }">
												<fmt:formatDate value="${review.aswrDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th scope="row">수정자</th>
									<td>
										<c:choose>
											<c:when test="${not empty review.moderId and not empty review.moderName }">
												<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${review.moderNo }')">${UtilsMasking.adminName(review.moderId, review.moderName) }</a>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th scope="row">수정일시</th>
									<td>
										<c:choose>
											<c:when test="${not empty review.modDtm }">
												<fmt:formatDate value="${review.modDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					<!-- E : tbl-row -->
				</div>
			</div>
			<!-- E : col-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" id="save" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.review.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>