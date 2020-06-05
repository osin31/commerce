<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">공유하기 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>기타 통계 </li>
						<li>공유하기 현황</li>
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
			<form id="form">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>검색 테이블이며 기간, 구입처구분, 사이트 구분, 회원 유형, 디바이스 구분에 대해 검색합니다.</caption>
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
									<select class="ui-sel" id="choice-period" title="기간 선택" name="choicePeriod">
										<option value="date">일별</option>
										<option value="month">월별</option>
										<option value="year">년별</option>
									</select>
									<!-- s : 년별 선택시 -->
									<select class="ui-sel year" title="시작 기간 선택" id="year-from-date" style="display: none;">
										<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}">
											<option value="${yearData}" ${yearData eq thisYear ? ' selected' : '' }>${yearData}</option>
										</c:forEach>
									</select>
									<span class="text year" style="display: none;">~</span>
									<select class="ui-sel year" title="종료 기간 선택" id="year-to-date" style="display: none;">
										<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}">
											<option value="${yearData}" ${yearData eq thisYear ? ' selected' : '' }>${yearData}</option>
										</c:forEach>
									</select
									<!-- e : 년별 선택시 -->
									<!-- s : 월별 선택시 -->
									<select class="ui-sel month" title="시작 기간 선택" id="month-from-date" style="display: none;">
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
												<option value="${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/>" ${yyyyMMData eq yyyyMM ? ' selected' : '' }>
													${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/></option>
											</c:forEach>
										</c:forEach>
									</select>
									<span class="text month" style="display: none;">~</span>
									<select class="ui-sel month" title="종료 기간 선택" id="month-to-date" style="display: none;">
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
												<option value="${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/>" ${yyyyMMData eq yyyyMM ? ' selected' : '' }>
													${yearData}.<fmt:formatNumber value="${monthData}" pattern="00"/></option>
											</c:forEach>
										</c:forEach>
									</select>
									<!-- e : 월별 선택시 -->
									<!-- s : 일별 선택시 -->
									<span class="date-box date">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDate">
									</span>
									<span class="text date">~</span>
									<span class="date-box date">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDate">
									</span>
									<span class="btn-group date">
										<a href="javascript:void(0);"   name="perYesterday" class="btn-sm btn-func">어제</a>
										<a href="javascript:void(0);"   name="perWeek"  	class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   name="per1Month" 	class="btn-sm btn-func">한달</a>
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
											<input id="site-no-1" name="siteNo" type="radio" value="" checked>
											<label for="site-no-1">전체</label>
										</span>
									</li>
									<c:forEach var="item" items="${searchConditionSiteList }">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="site-no-${item.siteNo }" name="siteNo" type="radio"  value="${item.siteNo }">
												<label for="site-no-${item.siteNo }">${item.siteName }</label>
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
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="device-code-all" type="checkbox" checked value="">
											<label for="device-code-all">전체</label>
										</span>
									</li>
									<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
										<li>
											<span class="ui-chk">
												<input id="device-code-${deviceCode.codeDtlName}" class="device-code" value="${deviceCode.codeDtlNo}" name="deviceCodeArr"  type="checkbox" checked>
												<label for="device-code-${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func" id="search">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</form>
		</div>
		<!-- E : search-wrap -->
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 현일 기준 조회 정보는 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
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
					<label class="title" for="page-count">목록개수</label>
					<select class="ui-sel" id="page-count">
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
			<div id="shareGrid" style="width:200%; height:429px;"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.etc.share.js<%=_DP_REV%>"></script>