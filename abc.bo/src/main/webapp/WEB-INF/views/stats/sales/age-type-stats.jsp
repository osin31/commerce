<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
$(function(){
	abc.biz.stats.sales.commonSet();
	abc.biz.stats.sales.initAgeTypeSalesStatsSheet();
});
</script>
<!-- S : container -->
		<div class="container">
		<form name="form" id="form" method="post">
		<input type="hidden" name="typeStats" value="ageType">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">연령대별 매출통계</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="javascript:void(0);"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>매출/정산/통계</li>
								<li>매출통계 </li>
								<li>연령대별 매출통계</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>당일 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px;">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기간</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" name="choicePeriod" id="choicePeriod" title="기간 선택">
												<option value="date">일별</option>
												<option value="month">월별</option>
												<option value="year">년별</option>
											</select>
											
											<!-- s : 년별 선택시 -->
											<select class="ui-sel hidden" title="시작 기간 선택" id="yearFromDate" name="yearFromDate">
												<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}"  >
													<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
												</c:forEach>
											</select>
											<span class="text hidden" id="yearWaveMark">~</span>
											<select class="ui-sel hidden" title="종료 기간 선택" id="yearToDate" name="yearToDate">
												<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}"  >
													<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
												</c:forEach>
											</select
											<!-- e : 년별 선택시 -->
		
											<!-- s : 월별 선택시 -->
											<select class="ui-sel hidden" title="시작 기간 선택" id="monthFromDate" name="monthFromDate">
												<c:forEach var="yearData" begin="${thisYear - 1}" end="${thisYear}">
													<c:choose>
														<c:when test="${thisYear eq yearData}">
															<c:set var="monthEnd" value="${thisMonth}" />
														</c:when>
														<c:otherwise>
															<c:set var="monthEnd" value="12" />
														</c:otherwise>
													</c:choose>
													<c:forEach var="monthData" begin="1" end="${monthEnd}">
														<option value="${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/>" <c:if test="${yyyyMMData eq yyyyMM}">selected</c:if>>
															${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/></option>
													</c:forEach>
												</c:forEach>
											</select>
											<span class="text hidden" id="monthWaveMark">~</span>
											<select class="ui-sel hidden" title="종료 기간 선택" id="monthToDate" name="monthToDate">
												<c:forEach var="yearData" begin="${thisYear - 1}" end="${thisYear}">
													<c:choose>
														<c:when test="${thisYear eq yearData}">
															<c:set var="monthEnd" value="${thisMonth}" />
														</c:when>
														<c:otherwise>
															<c:set var="monthEnd" value="12" />
														</c:otherwise>
													</c:choose>
													<c:forEach var="monthData" begin="1" end="${monthEnd}">
														<option value="${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/>" <c:if test="${yyyyMMData eq yyyyMM}">selected</c:if>>
															${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/></option>
													</c:forEach>
												</c:forEach>
											</select>
											<!-- e : 월별 선택시 -->

											<!-- s : 일별 선택시 -->
											<span class="date-box" id="dayFromDateSpan">
												<input type="text" data-role="datepicker" name="dayFromDate" class="ui-cal js-ui-cal"  title="시작일 선택" maxlength="10">
											</span>
											<span class="text" id="dayWaveMark">~</span>
											<span class="date-box" id="dayToDateSpan">
												<input type="text" data-role="datepicker" name="dayToDate" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
											</span>
											<span class="btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-func" id="toYesterday">어제</a>
												<a href="javascript:void(0);" class="btn-sm btn-func" id="toWeek">일주일</a>
												<a href="javascript:void(0);" class="btn-sm btn-func" id="toMonth">한달</a>
											</span>
											<!-- e : 일별 선택시 -->
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="siteNoAll" value="" checked>
													<label for="siteNoAll">전체</label>
												</span>
											</li>
											<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="radioSite${status.count}" value="${siteInfo.siteNo}">
														<label for="radioSite${status.count}"><c:out value="${siteInfo.siteName}"/></label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">디바이스 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="deviceCodeAll" name="deviceCodeAll" type="checkbox" checked="checked">
													<label for="deviceCodeAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty codeList.DEVICE_CODE}">
												<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
													<li>
														<span class="ui-chk">
															<input id="deviceCode${deviceCode.codeDtlName}" value="${deviceCode.codeDtlNo}"  custom="deviceCode" name="deviceCodeArr"  type="checkbox" checked="checked">
															<label for="deviceCode${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
											</c:if>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">상품코드 구분</th>
									<td class="input">
										<span class="ip-text-box">
											<select class="ui-sel" title="상품코드 구분 선택" name="vndrGbnType" id="mmnyPrdtYn" style="width:150px;">
												<c:choose>
													<c:when test="${isAdmin eq true}">
														<option value="">전체</option>
														<option value="C">자사</option>
														<option value="V">입점</option>
													</c:when>
													<c:otherwise> 
														<option value="V">입점</option>
													</c:otherwise>
												</c:choose>
											</select>
											<select class="ui-sel hidden" title="자사/입점 선택" name="ourChannel" id="ourChannel" style="width:150px;">
												<option value="">전체</option>
											<c:forEach var="chnnlInfo" items="${chnnlInfo}" varStatus="status">
												<option value="${chnnlInfo.chnnlNo}">${chnnlInfo.chnnlName}</option>
											</c:forEach>
											</select>
											<select class="ui-sel hidden" title="자사/입점 선택" name="shoperSiteNo" id="shoperSiteNo" style="width:150px;">
												<option value="">전체</option>
											<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
												<option value="${siteInfo.siteNo}">${siteInfo.siteName}</option>
											</c:forEach>
											</select>
										</span>
									</td>
									<td></td>
									<c:if test="${isAdmin eq true}">
										<th scope="row">발송처</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="stockGbnCodeAll" name="stockGbnCodeAll" type="checkbox" checked>
														<label for="stockGbnCodeAll">전체</label>
													</span>
												</li>
												<c:if test="${!empty codeList.STOCK_GBN_CODE}">
													<c:forEach items="${codeList.STOCK_GBN_CODE}" var="stockGbnCode">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkStore${stockGbnCode.codeDtlNo}" name="stockGbnCodeArr" value="${stockGbnCode.codeDtlNo}" type="checkbox" checked>
																<label for="chkStore${stockGbnCode.codeDtlNo}">${stockGbnCode.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
												</c:if>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</c:if>
								</tr>
								<tr>
									<th scope="row">결제수단</th>
									<td class="input" colspan="4">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPaymentAll" name="chkPaymentAll" type="checkbox" value="all" checked="checked">
													<label for="chkPaymentAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty codeList.PYMNT_MEANS_CODE}">
												<c:forEach items="${codeList.PYMNT_MEANS_CODE}" var="paymentCode">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkPayment${paymentCode.codeDtlNo}" value="${paymentCode.codeDtlNo}" custom="pymntMeansCode" name="pymntMeansCode" type="checkbox" checked="checked">
															<label for="chkPayment${paymentCode.codeDtlNo}">${paymentCode.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
											</c:if>
										</ul>

										<!-- S : member-grade-list -->
										<div class="member-grade-list">
											<span class="text">( 에스크로 적용</span>

											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkEscroTrue" name="chkEscroTrue" custom="escrApplyYn" type="checkbox" value="Y" checked>
														<label for="chkEscroTrue">예</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkEscroFalse" name="chkEscroFalse" custom="escrApplyYn" type="checkbox" value="N" checked>
														<label for="chkEscroFalse">아니오</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->

											<span class="text">)</span>
										</div>
										<!-- E : member-grade-list -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-normal btn-func" id="searchBtn">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ul class="tbl-desc-list">
						<li>* 연령별 매출 통계는 멤버십회원 대상 데이터입니다. (비회원, 온라인회원 대상 제외)</li>
						<li>* 매출 통계 자료는 매출발생 시점 기준(결제완료 기준) 조회 통계이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
						<li>* 매출 통계 자료는 당일 발생 매출 데이터는 제외됩니다.</li>
					</ul>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">목록</h3>
					</div>
				</div>
				<!-- E : content-header -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="#" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
					</div>
				</div>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="ageTypeSaleStatsGrid" style="width:200%; height:429px;"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</form>
		</div>
		<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.sales.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>


