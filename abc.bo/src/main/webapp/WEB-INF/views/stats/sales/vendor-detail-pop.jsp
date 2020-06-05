<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
$(function(){
	abc.biz.stats.sales.commonSet();
	abc.biz.stats.sales.vendorPopSrchOpt.init();
	abc.biz.stats.sales.initVendorPopSalesStatsSheet();
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>입점사 상품 매출 현황</h1>
		</div>
		<div class="window-content">
			<!--s : 190716 수정 // 검색영역 필드 순서변경, 결제수단 추가-->
			<!-- S : search-wrap -->
			<form name="form" id="form">
				<input type="hidden" name="typeStats" value="vendorPop">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>입점사 상품 매출 현황</caption>
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
									<th scope="row">입점사</th>
									<td class="input">
										<span class="ip-search-box">
											<input type="text" name="vndrName" class="ui-input" id="vndrName" title="검색어 입력" readonly>
											<input type="hidden" name="vndrNo" id="vndrNo">
											<a href="javascript:void(0);" class="btn-search" id="searchVndrPop"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">디바이스 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="deviceCodeAll" name="deviceCodeAll" type="checkbox" checked>
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
									<td></td>
									<th scope="row">시간대 선택</th>
									<td class="input">
										<span class="ip-text-box">
											<select class="ui-sel" title="시작시 선택" name="fromHour">
												<option value="">선택</option>
												<c:forEach var="time" begin="0" step="1" end="24">
													<option value="<fmt:formatNumber value="${time}" pattern="00"/>"><fmt:formatNumber value="${time}" pattern="00"/>시</option>
												</c:forEach>
											</select>
											<select class="ui-sel" title="종료시 선택" name="toHour">
												<option value="">선택</option>
												<c:forEach var="time" begin="0" step="1" end="24">
													<option value="<fmt:formatNumber value="${time}" pattern="00"/>"><fmt:formatNumber value="${time}" pattern="00"/>시</option>
												</c:forEach>
											</select>
										</span>
									</td>
								</tr>
								<tr>
									<th scope="row">결제수단</th>
									<td class="input" colspan="4">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPaymentAll" name="chkPaymentAll" type="checkbox" value="" checked>
													<label for="chkPaymentAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty codeList.PYMNT_MEANS_CODE}">
												<c:forEach items="${codeList.PYMNT_MEANS_CODE}" var="paymentCode">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkPayment${paymentCode.codeDtlNo}" value="${paymentCode.codeDtlNo}" custom="pymntMeansCode" name="pymntMeansCode" type="checkbox" checked>
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
				<!--e : 190716 수정 // 검색영역 필드 순서변경, 결제수단 추가-->
	
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ul class="tbl-desc-list">
						<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
						<li>* 상품 매출 현황 자료는 주문일 기준이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다. </li>
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
					<div id="vendorDetailPopGrid" style="width:100%; height:429px;"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</form>
	</div>

<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.sales.js<%=_DP_REV%>"></script>
	
	
	