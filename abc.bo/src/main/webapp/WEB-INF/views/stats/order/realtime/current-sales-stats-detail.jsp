<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		abc.biz.stats.order.realtime.initSaleDetailStatsSheet();
		abc.biz.stats.order.realtime.srchOptionControll();
		
	});
</script>
<body class="window-body">
	<form name="form" id="form">
		<input type="hidden" name="typeStats" value="saleDetail">
		<input type="hidden" name="isTimeType" value="${timeType}">
		<div class="window-wrap">
			<div class="window-title">
				<h1>실시간 상품 매출 현황</h1>
			</div>
			<div class="window-content">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>당일 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<c:if test="${!empty timeType && timeType eq 'timeType'}">
									<tr>
										<th scope="row">기간</th>
										<td class="input">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
	
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
								</c:if>
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
								</tr>
								<tr>
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
															<input id="deviceCode${deviceCode.codeDtlName}" value="${deviceCode.codeDtlNo}"  custom="deviceCode" name="deviceCodes"  type="checkbox" checked="checked">
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
									<th scope="row">시간대 선택</th>
									<td class="input">
										<span class="ip-text-box">
											<select class="ui-sel" title="시 선택" name="fromTime">
												<option value="">선택</option>
												<c:forEach var="time" begin="0" step="1" end="24">
													<option value="<fmt:formatNumber value="${time}" pattern="00"/>"><fmt:formatNumber value="${time}" pattern="00"/>시</option>
												</c:forEach>
											</select>
											<select class="ui-sel" title="시 선택" name="toTime">
												<option value="">선택</option>
												<c:forEach var="time" begin="0" step="1" end="24">
													<option value="<fmt:formatNumber value="${time}" pattern="00"/>"><fmt:formatNumber value="${time}" pattern="00"/>시</option>
												</c:forEach>
											</select>
										</span>
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
						<c:if test="${empty timeType && timeType ne 'timeType'}">
							<li class="tc-red">* 기준일시 : <fmt:formatDate value="${currentDateTime}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></li>
						</c:if>
						<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다. </li>
						<li>* 당일 매출 현황 자료는 주문일 기준(결제완료 기준)이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
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
					<c:if test="${!empty timeType && timeType eq 'timeType'}">
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
					</c:if>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
					</div>
				</div>
	
				<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="currentSaleStatsDetailGrid" style="width:200%; height:429px;"></div>
					</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</form>
		<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
		<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.sales.js<%=_DP_REV%>"></script>
		<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.current.salestats.js<%=_DP_REV%>"></script>
	</div>
</body>
</html>
