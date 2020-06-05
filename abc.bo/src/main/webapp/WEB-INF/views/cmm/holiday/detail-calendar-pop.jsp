<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- 캘린더 보기 팝업 --%>
		$('#year').on('change', function(event) {
			$('#month').val('01');
			abc.biz.system.holiday.popupHolidayCalendar("S");
		});
		
		$('#month').on('change', function(event) {
			abc.biz.system.holiday.popupHolidayCalendar("S");
		});
		
		<%--alert('year >>><c:out value ="${year}"/>\nmonth >>><c:out value ="${month}"/>');--%>
		
		$('#year').val('<c:out value ="${year}"/>');	// 년도선택 기본값(리시트의 선택 년도) 세팅
		$('#month').val('<c:out value ="${month}"/>');	// 년도월 기본값(1월) 세팅
	});
		
</script>

<body class="window-body">
<form id="holidayForm" name="holidayForm" method="post" onsubmit="return false;">
<input type="hidden" id="commonHoliday" name="commonHoliday" value="<%=Config.getString("holiday.common","C")%>"> 
<input type="hidden" id="standardHoliday" name="standardHoliday" value="<%=Config.getString("holiday.standard","S")%>">
<input type="hidden" id="vndrNo" name="vndrNo" value="<c:out value="${param.vndrNo}"/>">
<input type="hidden" id="role" name="role" value="${param.role}"><%-- 관리자의 권한 --%>
	<div class="window-wrap">
		<div class="window-title">
			<h1>캘린더보기</h1>
		</div>
		<div class="window-content">
			<!-- S : calendar-wrap -->
			<div class="calendar-wrap">
				<div class="calendar-header">
					<div class="date-sel">
						<select class="ui-sel-cal year" title="년도 선택" name="year" id="year">
							<option value="">선택</option>
							<c:forEach begin="${beforeTenYear}" end="${nextYear}" var="year">
							<option value="<c:out value="${year}"/>"><c:out value="${year}"/></option>
							</c:forEach>
						</select>
						<select class="ui-sel-cal month" title="월 선택" name="month" id="month">
							<c:forEach begin="1" end="12" varStatus="i">
							<c:set var="month">
								<c:choose>
									<c:when test="${i.count < 10}">0<c:out value="${i.count}"/></c:when>
									<c:otherwise><c:out value="${i.count}"/></c:otherwise>
									</c:choose>
							</c:set>
							<option value='<c:out value="${month}"/>'><c:out value="${month}"/>월</option>
							</c:forEach>
						</select>
					</div>
					<div class="lagend-box">
						<span class="tt holiday">휴일</span>
					</div>
				</div>

				<div class="calendar-content">
					<!-- DESC : row가 늘어나더라도 하나의 row 높이는 전체 높이값에서 1/n로 계산됨.
						<tr></tr> : row(주) / <td></td> : col(일)

						1. 날짜 기본 마크업 :
							<td><span class="date">1</span></td>

						2. 날짜 + 텍스트 마크업 :
							<td>
								<span class="date">10</span>
								<span class="desc">창립기념일</span>
							</td>

						3. 일요일을 제외한 휴일인경우, td에 holiday 클래스 추가 :
							<td class="holiday">
								<span class="date">10</span>
								<span class="desc">창립기념일</span>
							</td>
					 -->
					<table class="calendar">
						<caption>캘린더 보기</caption>
						<colgroup>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
						</colgroup>
						<thead>
							<tr>
								<th scope="col">SUN</th>
								<th scope="col">MON</th>
								<th scope="col">TUE</th>
								<th scope="col">WED</th>
								<th scope="col">THU</th>
								<th scope="col">FRI</th>
								<th scope="col">SAT</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty list}">
									<script>
										alert("달력 데이터가 없습니다.");
 										//191023 달력 닫히지 않게 수정
										//window.close();
									</script>
								</c:when>
								<c:otherwise>
									<c:set var="dayStartIndex" value="${dayStartIndex}" />	<%-- 월의 1일의 DAY_OF_WEEK의 인덱스 --%>
									<c:set var="endTdIndex" value="7" />	<%-- <td> 태그의 마지막 인덱스 7(토요일)로 세팅 --%>
									<%-- 월의 1일 시작 전 빈칸을 <td>로 채우는 loop --%>
									<c:forEach begin="1" end="${dayStartIndex-1}" varStatus="i">
										<c:if test="${i.first}"><tr></c:if>
											<td></td>
									</c:forEach>
									<%-- 월의 1일부터 말일까지 데이터를 뿌림 --%>
									<c:forEach items="${list}" var="data" varStatus="j">
										<c:if test="${j.first and dayStartIndex eq '1'}"><tr></c:if>	<%-- 일요일부터 1일이 시작인 월은 <tr> 태그 필요, j.first[한번만 뿌려야하므로] ex)201909 --%>
											<td <c:if test="${data.hldyYn eq 'Y'}">class="holiday"</c:if>> 
												<c:if test='${not empty data.hldyName and data.hldyName ne "토요일" and data.hldyName ne "일요일"}'>
												<span class="desc">
													<c:out value="${data.hldyName}"/>
												</span>
												</c:if>
												<span class="date"><c:out value="${j.count}"/></span>
											</td>
										<c:set var="endTdIndex" value="${endTdIndex-1}" />	<%-- endTdIndex를 -1해서 <td> 태그의 개수를 세팅 --%>
		
										<c:if test="${j.first and dayStartIndex%7 eq 0}"></tr><c:set var="endTdIndex" value="7" /></c:if>	<%-- 토요일부터 1일이 시작일 때 </tr>로 세팅해서 다음행에 날짜를 뿌림 ex)201906 --%>
										<c:if test="${not j.first and not j.last and (j.count+dayStartIndex-1)%7 eq 0}"></tr><tr><c:set var="endTdIndex" value="7" /></c:if>	<%-- 날짜가 토요일을 만나면 다음행에 뿌리기 위한 세팅 --%>
										<c:if test="${j.last and (j.count+dayStartIndex-1)%7 eq 0}"></tr><c:set var="endTdIndex" value="0" /></c:if> <%-- 월의 마지막 날짜가 토요일이면 </tr>을 닫음[하위 loop를 돌지 않으므로 </tr>태그로 마무리] 그리고 하위의 빈 <td>태그를 채울 필요가 없으므로 endTdIndex를 0으로 세팅 ex)201908, 201911 --%>	
									</c:forEach>
									
									<%-- 월의 날짜 이후 빈칸을 <td>로 채우는 loop, 최대6줄(42칸, 보통 35칸)이 나오는 경우가 있음 201903, 201906[1일은 금요일 또는 토요일 말일[31]이 월요일이면 6줄이 될수있음] --%>
									<c:forEach begin="1" end="${endTdIndex}" varStatus="k">
										<td></td>
										<c:if test="${k.last}"></tr></c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<!-- E : 날짜영역 -->
							
						</tbody>
					</table>
				</div>
			</div>
			<!-- E : calendar-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="javascript:window.close();" class="btn-normal btn-link">확인</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	</form>
	<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.holiday.js<%=_DP_REV%>"></script>
</body>
</html>
