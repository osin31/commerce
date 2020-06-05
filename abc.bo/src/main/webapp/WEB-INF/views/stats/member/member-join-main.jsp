<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<!-- S : container -->
<div class="container">
	<form id="gForm" name="gForm" method="post" onsubmit="return false;">
	<input type="hidden" id="memberType" name="memberType" value="1">
	
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">회원가입 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>회원통계 </li>
						<li>회원가입 현황</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">전일 기준 회원 수 현황</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<table class="tbl-col">
			<caption>전일 기준 회원 수 현황</caption>
			<colgroup>
				<col>
				<col>
				<col>
			</colgroup>
			<thead>
				<tr>
					<th scope="col">전체</th>
					<th scope="col">온라인회원</th>
					<th scope="col">멤버십회원</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="text-center"><fmt:formatNumber value="${yesterdayMemberInfo.onlnTotalMemberCount + yesterdayMemberInfo.mbshpTotalMemberCount}" pattern="#,###.##" type="number"/>명</td>
					<td class="text-center"><fmt:formatNumber pattern="#,###.##" type="number"><c:out value="${yesterdayMemberInfo.onlnTotalMemberCount}" default="0"/> </fmt:formatNumber>명</td>
					<td class="text-center">  <fmt:formatNumber pattern="#,###.##" type="number"><c:out value="${yesterdayMemberInfo.mbshpTotalMemberCount}" default="0" /></fmt:formatNumber>명</td>
				</tr>
			</tbody>
		</table>

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
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">기간</th>
							<td class="input">
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
									</select
									<!-- e : 년별 선택시 -->

									<!-- s : 월별 선택시 -->
									<select class="ui-sel" title="시작 기간 선택" id="periodMonthStart" name="periodMonthStart" style="display:none;">
										<c:forEach var="yearData" begin="${thisYear-1}" end="${thisYear}">
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
										<c:forEach var="yearData" begin="${thisYear-1}" end="${thisYear}">
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
											<input name="deviceTypeCodeArr" type="checkbox" id="chkdeviceType0${status.index}" value="${data.codeDtlNo }" class="check-item" checked />
											<label for="chkdeviceType0${status.index}">${data.codeDtlName }</label>
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
						<a href="#" class="btn-sm btn-func" id="gFormReset"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func" id="gFormSearch">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : tab-wrap -->
		<div class="tab-wrap">
			<ul class="tabs">
				<li class="tab-item"><a href="#tabContent1" id="memberType1" class="tab-link">회원가입 현황</a></li>
				<li class="tab-item"><a href="#tabContent2" id="memberType2" class="tab-link">연령별 회원가입 현황</a></li>
			</ul>
			<!-- S:tab_content -->
			<div id="tabContent1" class="tab-content">
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ul class="tbl-desc-list">
						<li>* 회원가입 현황은 당일 데이터를 제외한 전일 기준의 온라인회원, 멤버십회원 대상 데이터입니다.</li>
						<li>* 온라인회원의 성별은 부가정보 내 정보로 정확하지 않을 수 있습니다.</li>
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
							<label class="title" for="selTermsModule">목록개수</label>
							<select id="pageCount1" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel1">엑셀 다운로드</a>
					</div>
				</div>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="todayJoinGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
			<!-- E:tab_content -->
			<!-- S:tab_content -->
			<div id="tabContent2" class="tab-content">
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ul class="tbl-desc-list">
						<li>* 연령별 회원가입 현황은 당일 데이터를 제외한 전일 기준의 멤버십회원 대상 데이터입니다.</li>
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
							<label class="title" for="selTermsModule">목록개수</label>
							<select id="pageCount2" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel2">엑셀 다운로드</a>
					</div>
				</div>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="todayAgeJoinGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
			<!-- E:tab_content -->
		</div>
		<!-- E : tab-wrap -->
	</div>
	</form>
</div>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.member.join.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>