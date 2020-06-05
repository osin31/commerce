<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
$(function(){
	abc.biz.stats.member.leave.initLeaveSheet();
	//어제
	$("a[name^=periodDateYesterday]").click(function(){
		abc.date.statDays(this);
	});
	//일주일
	$("a[name^=periodDateWeek]").click(function(){
		abc.date.statWeek(this);
	});
	//한달
	$("a[name^=periodDateMonth]").click(function(){
		abc.date.statMonth(this);
	});
	$("a[name^=periodDateWeek]").trigger("click");
	
	//엑셀 다운로드 버튼
	$("#BtnExcelDownload").click(function(){
		abc.biz.stats.member.leave.excelDownload();
	});
	
	//검색
	$("#leaveSearch").click(function(){
		if(abc.date.searchValidate()){
			abc.biz.stats.member.leave.leaveSearch();
		}
	});
	
	//페이지 목록 개수 클릭시
	$("#pageCount").change(function(){
		$("#leaveSearch").trigger('click');
	});
	
	
});	

	function leaveSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response) {
		var frozenRow 	= leaveSheet.GetFrozenRows();
		var currentPage = leaveSheet.GetCurrentPage();
		var periodType  = $('#periodType').val();
		var gridText = abc.biz.stats.member.leave.gridText(periodType);
		
		//일별, 월별, 년별 그리드 텍스트 변경
		leaveSheet.SetCellText(1, 0, gridText);
		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=leaveSheet.SearchRows(); i++){
				var saveName = leaveSheet.GetCellValue(i, "totalDayC");
				if(saveName == "합계"){
					leaveSheet.RowDelete(i, 0);		
				}
			}
		}
		leaveSheet.SetRowBackColor(2, '#e2f5ff');
		leaveSheet.SetFrozenRows(1);
		
		if(leaveSheet.SearchRows() > 0){
			$('#BtnExcelDownload').removeClass('disabled');
		} else {
			$('#BtnExcelDownload').addClass('disabled');
		}
	}
	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">탈퇴 사유 통계</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>회원통계 </li>
						<li>탈퇴 사유 통계</li>
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
					</colgroup>
					<form id="leaveForm" name="leaveForm">
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
											<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}"  >
												<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
											</c:forEach>
										</select>
										<span class="text" id="periodDashYear" style="display:none;">~</span>
										<select class="ui-sel" title="종료 기간 선택" id="periodYearEnd" name="periodYearEnd" style="display:none;">
											<c:forEach var="yearData" begin="${thisYear-10}" end="${thisYear}"  >
												<option value="${yearData}" <c:if test="${yearData eq thisYear}">selected</c:if>>${yearData}</option>
											</c:forEach>
										</select
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
								<th scope="row">회원 구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="memberType" name="memberTypeCode" type="radio" value="" checked>
												<label for="memberType">전체</label>
											</span>
										</li>
										<c:forEach var="memberTypeCode" items="${memberTypeCode}">
											<c:if test="${memberTypeCode.codeDtlNo ne '10002'}">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberType${memberTypeCode.codeDtlNo}" name="memberTypeCode" value="${memberTypeCode.codeDtlNo}" type="radio">
														<label for="memberType${memberTypeCode.codeDtlNo}">${memberTypeCode.codeDtlName}</label>
													</span>
												</li>
											</c:if>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
						</tbody>
					</form>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="leaveSearch" class="btn-normal btn-func">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 탈퇴사유 통계는 당일 데이터를 제외한 전일 기준의 온라인회원, 멤버십회원 대상 데이터입니다.</li>
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
					<select class="ui-sel" id="pageCount" name="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<!-- E : opt-group -->
			</div>
			<div class="fr">
				<a href="javascript:void(0)" id="BtnExcelDownload" class="btn-sm btn-func disabled">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="leaveGrid" style="width:100%; height:429px;">
				
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/stats/abcmart.stats.member.leave.js<%=_DP_REV%>">
	
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>