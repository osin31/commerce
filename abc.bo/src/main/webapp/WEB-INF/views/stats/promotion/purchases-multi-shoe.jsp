<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function(){
		abc.biz.stats.promotion.shoe.initShoeStatsSheet();
		abc.biz.stats.promotion.shoe.event();
	});
</script>
<!-- S : container -->
<div class="container">
	<form name="form" id="form" method="post">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">다족구매 족수별 현황 통계</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="javascript:void(0);"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>매출/정산/통계</li>
							<li>프로모션통계 </li>
							<li>다족구매 족수별 현황 통계</li>
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
						<caption>검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px;">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사이트 구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="radioSite01" value="" checked>
												<label for="radioSite01">전체</label>
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
										<input type="text" data-role="datepicker" name="dayFromDate" class="ui-cal js-ui-cal"  title="시작일 선택">
									</span>
									<span class="text" id="dayWaveMark">~</span>
									<span class="date-box" id="dayToDateSpan">
										<input type="text" data-role="datepicker" name="dayToDate" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);" class="btn-sm btn-func" id="toToday">오늘</a>
										<a href="javascript:void(0);" class="btn-sm btn-func" id="toWeek">일주일</a>
										<a href="javascript:void(0);" class="btn-sm btn-func" id="toMonth">한달</a>
										<a href="javascript:void(0);" class="btn-sm btn-func" id="toYear">1년</a>
									</span>
									<!-- e : 일별 선택시 -->
								</span>
								<!-- E : term-date-wrap -->
								</td>
							</tr>
							<tr>
								<th scope="row">프로모션 검색</th>
								<td class="input" colspan="4">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" title="유형 선택" name="promotionType" id="promotionType">
											<option value="promoName">프로모션명</option>
											<option value="promoNo">프로모션번호</option>
										</select>
										<input type="text" class="ui-input" title="검색어 입력" id="searchKeyword" size="10">
									</div>
									<!-- E : opt-keyword-box -->
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
					<li>* 프로모션 통계 자료는 매출발생 시점 기준(결제완료 기준) 조회 통계이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
					<li>* 프로모션 통계 자료는 당일 발생 매출 데이터는 포함됩니다.</li>
					<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
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
					<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="shoeStatsGrid" style="width:100%; height:429px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</form>
</div>
<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.promotion.shoe.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
