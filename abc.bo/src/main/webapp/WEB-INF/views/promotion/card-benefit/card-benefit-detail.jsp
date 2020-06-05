<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">카드사 혜택 등록</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>카드사 혜택관리</li>
								<li>카드사 혜택</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">카드사 혜택정보</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="#" class="btn-sm btn-func" id="refreshBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<form id="benefitForm">
				<input type="hidden" name="cardBenefitSeq" value="${cardBenefit.cardBenefitSeq}">
				<table class="tbl-row">
					<caption>카드사 혜택 정보</caption>
					<colgroup>
						<col style="width: 160px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">무이자 할부 행사명</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" id="cardBenefitName" name="cardBenefitName" class="ui-input" title="무이자 할부 행사명 입력" value="${cardBenefit.cardBenefitName}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">전시여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplayY" value="Y" ${cardBenefit.dispYn == 'Y' || cardBenefit.dispYn == null ? 'checked' : ''}>
											<label for="radioDisplayY">예</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplayN" value="N" ${cardBenefit.dispYn == 'N' ? 'checked' : ''}>
											<label for="radioDisplayN">아니오</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">전시기간</span>
							</th>
							<td class="input">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" id="startYmd" name="startYmd" title="시작일 선택" value="<fmt:formatDate value="${cardBenefit.dispStartYmd}" pattern="yyyy.MM.dd"/>">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" id="endYmd" name="endYmd" title="종료일 선택" value="<fmt:formatDate value="${cardBenefit.dispEndYmd}" pattern="yyyy.MM.dd"/>">
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">행사카드</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" id="eventCardText" name="eventCardText" title="행사카드 입력" value="${cardBenefit.eventCardText}">
							</td>
						</tr>
						<tr>
							<th scope="row">행사대상</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" id="eventTrgtText" name="eventTrgtText" title="행사대상 입력" value="${cardBenefit.eventTrgtText}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">카드혜택 이미지</span>
								<div>(최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input" colspan="3">
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="file" id="inputFile" class="card_benefit" name="cardBenefitImageFile" title="첨부파일 추가">
												<label for="inputFile">찾아보기</label>
											</span>
										</li>
										<li>
											<input type="hidden" name="cardBenefitImageName" class="card_benefit" value="${cardBenefit.cardBenefitImageName}">
											<input type="hidden" name="cardBenefitImagePathText" value="${cardBenefit.cardBenefitImagePathText}">
											<span class="subject card_benefit">${cardBenefit.cardBenefitImageName}</span>
											<button type="button" class="btn-file-del card_benefit" ${cardBenefit.cardBenefitImageName == null ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" id="cardBenefitAltrnText" name="cardBenefitAltrnText" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${cardBenefit.cardBenefitAltrnText}">
									</div>
									<div class="img-wrap card_benefit">
										<c:if test="${cardBenefit.cardBenefitImageUrl != null}">
											<img alt="${cardBenefit.cardBenefitAltrnText}" src="${cardBenefit.cardBenefitImageUrl}">
										</c:if>
									</div>
								</div>
							</td>
						</tr>
						<c:if test="${not empty cardBenefit.cardBenefitSeq}">
							<tr>
								<th scope="row">최종 수정자</th>
								<td><a href="javascript:abc.adminDetailPopup('${cardBenefit.moderNo}');">${UtilsMasking.adminName(cardBenefit.moderId, cardBenefit.moderName)}</a></td>
								<th scope="row">최종 수정일시</th>
								<td><fmt:formatDate value="${cardBenefit.modDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:if>
					</tbody>
				</table>
				</form>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fr">
						<a href="/promotion/card-benefit" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="#" class="btn-lg btn-save" id="saveBtn">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/promotion/abcmart.promotion.card.benefit.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>