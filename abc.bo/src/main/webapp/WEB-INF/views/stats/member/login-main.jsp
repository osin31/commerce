<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<!-- S : container -->
<div class="container">
	<form id="loginForm" name="loginForm" method="post" onsubmit="return false;">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">로그인 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>회원통계 </li>
						<li>로그인 현황</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">조건 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>조건 검색</caption>
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
									<select class="ui-sel" title="기간 선택" name="periodType" id="periodType">
										<option value="day">일별</option>
										<option value="month">월별</option>
										<option value="year">년별</option>
									</select>
									<!-- s : 년별 선택시 -->
									<select class="ui-sel" title="시작 기간 선택" id="periodYearStart" name="periodYearStart" style="display:none;">
										<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear+10}"  >
											<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
										</c:forEach>
									</select>
									<span class="text" id="periodDashYear" style="display:none;">~</span>
									<select class="ui-sel" title="종료 기간 선택" id="periodYearEnd" name="periodYearEnd" style="display:none;">
										<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear+10}"  >
											<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
										</c:forEach>
									</select>
									<!-- e : 년별 선택시 -->

									<!-- s : 월별 선택시 -->
									<select class="ui-sel" title="시작 기간 선택" id="periodMonthStart" name="periodMonthStart" style="display:none;">
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
												<c:if test="${monthData < 10}"> <c:set var="monthData" value="0${monthData}" /></c:if>
												<c:if test="${monthData > 9}"> <c:set var="monthData" value="${monthData}" /></c:if>
												<c:set var="yyyyMMData" value="${yearData}${monthData}" />
												<option value="${yearData}${monthData}" <c:if test="${yyyyMMData eq yyyyMM}">selected</c:if>>${yearData}.${monthData}</option>
											</c:forEach>
										</c:forEach>
									</select>
									<span class="text" id="periodDashMonth" style="display:none;">~</span>
									<select class="ui-sel" title="종료 기간 선택" id="periodMonthEnd" name="periodMonthEnd" style="display:none;">
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
												<c:if test="${monthData < 10}"> <c:set var="monthData" value="0${monthData}" /></c:if>
												<c:if test="${monthData > 9}"> <c:set var="monthData" value="${monthData}" /></c:if>
												<c:set var="yyyyMMData" value="${yearData}${monthData}" />
												<option value="${yearData}${monthData}" <c:if test="${yyyyMMData eq yyyyMM}">selected</c:if>>${yearData}.${monthData}</option>
											</c:forEach>
										</c:forEach>
									</select>
									<!-- e : 월별 선택시 -->
									
									<!-- s : 일별 선택시 -->
									<span class="date-box" id="startDayArea">
										<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text" id="dashArea">~</span>
									<span class="date-box" id="endDayArea">
										<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
									<span class="btn-group" id="periodButtonArea">
										<a href="javascript:void(0);" name="periodDateYesterday" id="periodDateYesterday" class="btn-sm btn-func">어제</a>
										<a href="javascript:void(0);" name="periodDateWeek" id="periodDateWeek" class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);" name="periodDateMonth" id="periodDateMonth" class="btn-sm btn-func">한달</a>
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
											<input id="radioSiteAll" name="siteNo" value="All" type="radio" checked>
											<label for="radioSiteAll">전체</label>
										</span>
									</li>
									<c:forEach items="${siteList}" var="site" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="${site.siteNo}" value="${site.siteNo}">
												<label for="${site.siteNo}">${site.siteName}</label>
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
											<input id="chkdeviceTypeAll" name="chkdeviceTypeAll" type="checkbox" checked>
											<label for="chkdeviceTypeAll">전체</label>
										</span>
									</li>
									<c:forEach var="data" items="${codeList.DEVICE_CODE}" varStatus="status">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input name="deviceTypeCodeArr" type="checkbox" id="chkdeviceType0${status.index}" value="${data.codeDtlNo }" class="check-item" checked/>
											<label for="chkdeviceType0${status.index}">${data.codeDtlName }</label>
										</span>
									</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">회원 구분</th>
							<td class="input" colspan="4">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioMemberAll" name="memberTypeCode" value="All" type="radio" checked>
											<label for="radioMemberAll">전체</label>
										</span>
									</li>
									<c:forEach items="${codeList.MEMBER_TYPE_CODE}" var="member" varStatus="status">
										<li>
											<span class="ui-rdo">
												<c:if test="${member.codeDtlNo ne '10002'}">
												<input type="radio" id="radioMemberType0${status.index}" name="memberTypeCode" value="${member.codeDtlNo}">
												<label for="radioMemberType0${status.index}">${member.codeDtlName}</label>
												</c:if>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">SNS 계정 로그인 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="snsLoginYn" name="snsLoginYn" type="checkbox" value="Y">
											<label for="snsLoginYn">SNS 계정 로그인 여부</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">SNS 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkSnsTypeAll" name="chkSnsTypeAll" type="checkbox" value="" disabled>
											<label for="chkSnsTypeAll">전체</label>
										</span>
									</li>
									<c:forEach var="data" items="${codeList.SNS_GBN_CODE}" varStatus="status">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" id="chkSnsType0${status.index}" name="chkSnsTypeCodeArr" value="${data.codeDtlNo}" disabled>
												<label for="chkSnsType0${status.index}">${data.codeDtlName}</label>
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
						<a href="javascript:void(0);" class="btn-sm btn-func" id="loginFormReset"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-normal btn-func" id="loginFormSearch">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 로그인 현황은 당일 데이터를 제외한 전일 기준의 온라인회원, 멤버십회원 대상 데이터입니다.</li>
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
			<div class="fr">
				<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="loginGrid"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
	</form>
</div>
<!-- E : container -->


<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.member.login.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
