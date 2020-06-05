<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
$(function(){
	abc.biz.member.history.codeCombo = ${codeCombo};
	abc.biz.member.history.setTabFocus();
	
	// tabsactivate event
	$("#historyTab").on("tabsactivate", function(event, ui) {
		if (ui.newTab.prevObject.attr('id') === "loginHistory") {
			location.hash = "tabLogin";
		} else if (ui.newTab.prevObject.attr('id') === "changeHistory") {
			location.hash = "tabChange";
		} else if (ui.newTab.prevObject.attr('id') === "certifyHistory") {
			location.hash = "tabCertify";
		} else if (ui.newTab.prevObject.attr('id') === "eventPointHistory") {
			location.hash = "tabEventPoint"; // 2020.05.27 추가 : 이벤트포인트 이력 탭
		}
		abc.biz.member.history.setTabFocus();
	});
	
	<%-- 캘린더 버튼 설정(일) --%>
	$("a[name^=perToday]").click(function(){
		abc.date.days(this);
	});
	
	<%-- 캘린더 버튼 설정(주)--%>
	$("a[name^=perWeek]").click(function(){
		abc.date.week(this);
	});
	
	<%-- 캘린더 버튼 설정(월) --%>
	$("a[name^=per1Month]").click(function(){
		abc.date.month(this);
	});
	
	<%-- 캘린더 버튼 설정(1년) --%>
	$("a[name^=perYear]").click(function(){
		abc.date.year(this);
	});
	
	$("a[name^=per1Month]").trigger("click");
	$("#searchLoginBtn").trigger("click");
	
});	

function changeSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if ( Row != 0) {
		if (changeSheet.ColSaveName(Col) == "rgsterDisplayName" && Value != "" ) {
			var adminNo = changeSheet.GetCellValue(Row, "rgsterNo");
			abc.adminDetailPopup(adminNo);
		}
	}
}
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>회원이력 보기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">
						[${member.memberTypeCodeName}]
						${member.detailLoginId} (${member.detailMemberName})님 <c:if test="${memberDefaultInfo.mbshpCardNo ne null}">(멤버십: ${member.mbshpCardNo})</c:if></h2>
				</div>
				<div class="fr">
					<span class="guide-text">최종 로그인  <fmt:formatDate value="${member.lastLoginDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/><c:if test="${member.memberState eq 'Y'}">| 휴면예정 </c:if></span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tab-wrap -->
			<div class="tab-wrap" id="historyTab">
				<ul class="tabs">
					<li class="tab-item">
						<a href="#tabContent1" class="tab-link" id="loginHistory">로그인 이력</a>
					</li>
					<li class="tab-item"><a href="#tabContent2" class="tab-link" id="changeHistory">변경 이력</a></li>
					<li class="tab-item"><a href="#tabContent3" class="tab-link" id="certifyHistory">본인인증 이력</a></li>
					<c:if test="${member.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}">
						<li class="tab-item"><a href="#tabContent4" class="tab-link" id="eventPointHistory">이벤트포인트 이력</a></li>
					</c:if>
				</ul>
				<!-- S:tab_content -->
				<div id="tabContent1" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">로그인 이력 검색</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="searchLoginForm" name="searchLoginForm">
					<input type="hidden" name="memberNo" value="${memberNo}">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>로그인 이력 검색</caption>
								<colgroup>
									<col style="width:7%;">
									<col>
									<col style="width:79px;">
									<col style="width:7%;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">로그인 경로</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="deviceCodeAll" type="checkbox" value="Y" checked>
														<label for="deviceCodeAll">전체</label>
													</span>
												</li>
												<c:forEach items="${deviceCode}" var="code">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="deviceCode${code.codeDtlNo}" name="deviceCode" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="deviceCode${code.codeDtlNo}">${code.codeDtlName}</label>
													</span>
												</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">회원구분</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberTypeAll" name="memberType" type="radio" checked value="">
														<label for="memberTypeAll">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberTypeNomal" name="memberType" type="radio" value="01">
														<label for="memberTypeNomal">일반회원</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberTypeEmp" name="memberType" type="radio" value="02">
														<label for="memberTypeEmp">임직원</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<%-- <td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberTypeCodeAll" type="checkbox" value="Y" checked>
														<label for="memberTypeCodeAll">전체</label>
													</span>
												</li>
												<c:forEach items="${memberTypeCode}" var="code">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="memberTypeCode${code.codeDtlNo}" name="memberTypeCode" data-group="memberType" value="${code.codeDtlNo}" type="checkbox" checked>
														<label for="memberTypeCode${code.codeDtlNo}">${code.codeDtlName}</label>
													</span>
												</li>
												</c:forEach>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="empYn" data-group="memberType" type="checkbox" value="Y" name="empYn" checked>
														<label for="empYn">임직원</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td> --%>
									</tr>
									<tr>
										<th scope="row">인증 결과</th>
										<td class="input">
											<select class="ui-sel" title="인증 결과 선택" id="cnnctrStatCode" name="cnnctrStatCode">
												<option value="">전체</option>
												<c:forEach items="${cnnctrStatCode}" var="code">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
										<th scope="row">로그인 일</th>
										<td class="input">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDateStr">
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDateStr">
												</span>
												<span class="btn-group">
													<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
													<a href="#" name="perToday" class="btn-sm btn-func">오늘</a>
													<a href="#" name="perWeek" class="btn-sm btn-func">일주일</a>
													<a href="#" name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="#" name="perYear" class="btn-sm btn-func">1년</a>
													<!-- E : 20190114 수정 -->
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
								</tbody>
							</table>

							<div class="confirm-box">
								<div class="fl">
									<a href="#" class="btn-sm btn-func" id="resetLoginBtn"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#" class="btn-normal btn-func" id="searchLoginBtn">검색</a>
									<!-- E : 20190114 수정 -->
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					</form>
					<!-- E : search-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">로그인 이력</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="loginPageCount">목록개수</label>
								<select class="ui-sel" id="loginPageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="loginGrid"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				<!-- S:tab_content -->
				<div id="tabContent2" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">변경 이력 검색</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="searchChangeForm" name="searchChangeForm">
					<input type="hidden" name="memberNo" value="${memberNo}">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>변경 이력 검색</caption>
								<colgroup>
									<col style="width:7%;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">구분</th>
										<td class="input">
											<ul class="ip-box-list">
												<li>
													<select class="ui-sel" title="구분 선택" id="changeGroup">
														<option value="">전체</option>
														<option value="basicInfo">회원 기본정보</option>
														<option value="addInfo">회원 부가정보</option>
<!-- 														<option value="activeInfo">회원 활동정보</option> -->
													</select>
												</li>
												<!-- DESC : 회원 기본정보, 회원 부가정보, 회원활동정보 선택시 소분류 선택 select 노출 -->
												<li id="changeFieldTag" style="display:none;">
													<select class="ui-sel" title="소분류 선택" id="changeField" name="changeField">
														<option value="">전체</option>
													</select>
												</li>
											</ul>
										</td>
									</tr>
									<tr>
										<th scope="row">수정일</th>
										<td class="input">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDateStr">
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDateStr">
												</span>
												<span class="btn-group">
													<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
													<a href="#" name="perToday" class="btn-sm btn-func">오늘</a>
													<a href="#" name="perWeek" class="btn-sm btn-func">일주일</a>
													<a href="#" name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="#" name="perYear" class="btn-sm btn-func">1년</a>
													<!-- E : 20190114 수정 -->
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
								</tbody>
							</table>

							<div class="confirm-box">
								<div class="fl">
									<a href="#" class="btn-sm btn-func" id="resetChangeBtn"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#" class="btn-normal btn-func" id="searchChangeBtn">검색</a>
									<!-- E : 20190114 수정 -->
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					</form>
					<!-- E : search-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">변경 이력</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="changePageCount">목록개수</label>
								<select class="ui-sel" id="changePageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="changeGrid"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				<!-- S:tab_content -->
				<div id="tabContent3" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">본인인증 이력 검색</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="searchCertifyForm" name="searchCertifyForm">
					<input type="hidden" name="memberNo" value="${memberNo}">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>본인인증 이력 검색</caption>
								<colgroup>
									<col style="width:7%;">
									<col>
									<col style="width:79px;">
									<col style="width:7%;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">인증 수단</th>
										<td class="input">
											<select class="ui-sel" title="인증 수단 선택" name="crtfcTypeCode">
												<option value="">전체</option>
												<c:forEach items="${crtfcTypeCode}" var="code">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
										<th scope="row">인증 경로</th>
										<td class="input">
											<select class="ui-sel" title="인증 경로 선택" name="crtfcPathCode">
												<option value="">전체</option>
												<c:forEach items="${crtfcPathCode}" var="code">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th scope="row">인증 결과</th>
										<td class="input">
											<select class="ui-sel" title="인증 유형 선택" name="crtfcSuccessYn">
												<option value="">전체</option>
												<option value="Y">인증 성공</option>
												<option value="N">인증 실패</option>
											</select>
										</td>
										<td></td>
										<th scope="row">본인인증 일</th>
										<td class="input">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDateStr">
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDateStr">
												</span>
												<span class="btn-group">
													<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
													<a href="#" name="perToday" class="btn-sm btn-func">오늘</a>
													<a href="#" name="perWeek" class="btn-sm btn-func">일주일</a>
													<a href="#" name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="#" name="perYear" class="btn-sm btn-func">1년</a>
													<!-- E : 20190114 수정 -->
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
								</tbody>
							</table>

							<div class="confirm-box">
								<div class="fl">
									<a href="#" class="btn-sm btn-func" id="resetCertifyBtn"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#" class="btn-normal btn-func" id="searchCertifyBtn">검색</a>
									<!-- E : 20190114 수정 -->
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					</form>
					<!-- E : search-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h2 class="content-title">본인인증 이력</h2>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="certifyPageCount">목록개수</label>
								<select class="ui-sel" id="certifyPageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="certifyGrid">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				<!-- 2020.05.25 이벤트포인트 이력 탭 추가  -->
				<c:if test="${member.memberTypeCode eq CommonCode.MEMBER_TYPE_MEMBERSHIP}">
					<div id="tabContent4" class="tab-content">
						<!-- S : search-wrap -->
						<form id="eventPointForm" name="eventPointForm">
							<input type="hidden" name="memberNo" value="${memberNo}">
						</form>
						<!-- E : search-wrap -->
	
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h2 class="content-title">이벤트포인트 이력</h2>
							</div>
						</div>
						<!-- E : content-header -->
	
						<!-- S : tbl-controller -->
						<div class="tbl-controller">
							<div class="fl">
								<!-- S : opt-group -->
								<span class="opt-group">
									<label class="title" for="eventPointPageCount">목록개수</label>
									<select class="ui-sel" id="eventPointPageCount">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
						</div>
						<!-- E : tbl-controller -->
	
						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
							<div id="eventPointGrid"></div>
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
				</c:if>
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>
	
	<script src="/static/common/js/biz/member/abcmart.member.history.js<%=_DP_REV%>"></script>
	
</body>
</html>