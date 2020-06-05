<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">이벤트 응모 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>프로모션</li>
						<li>이벤트 관리</li>
						<li>이벤트 응모 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">이벤트 기본정보</h3>
			</div>
			<div class="fr">
				<a href="/promotion/event/detail?eventNo=${evEvent.eventNo}" class="btn-sm btn-link">이벤트 보기</a>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>이벤트 기본정보</caption>
			<colgroup>
				<col style="width: 140px;">
				<col>
				<col style="width: 140px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">
						<span class="th-required">유형</span>
					</th>
					<td>
						${evEvent.eventTypeCodeName}
						<c:if test="${evEvent.eventTypeCode eq CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE}">
							<c:choose>
								<c:when test="${evEvent.eventJoinType eq 'C'}">(쿠폰형)</c:when>
								<c:when test="${evEvent.eventJoinType eq 'D'}">(추첨형)</c:when>
								<c:when test="${evEvent.eventJoinType eq 'L'}">(ID입력여부)</c:when>
								<c:otherwise>(없음)</c:otherwise>
							</c:choose>
						</c:if>
						<input type="hidden" id="eventJoinType" value="${evEvent.eventJoinType}" />
					</td>
					<th scope="row">이벤트 번호</th>
					<td>${evEvent.eventNo}</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">사용여부</span>
					</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="rdoUse02" name="useYn" type="radio" ${evEvent.useYn eq 'Y' ? 'checked' : ''} disabled>
									<label for="rdoUse02">사용</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="rdoUse03" name="useYn" type="radio" ${evEvent.useYn eq 'N' ? 'checked' : ''} disabled>
									<label for="rdoUse03">사용안함</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
					<th scope="row">
						<span class="th-required">전시여부</span>
					</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="rdoDisplay02" name="dispYn" type="radio" ${evEvent.dispYn eq 'Y' ? 'checked' : ''} disabled>
									<label for="rdoDisplay02">전시</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="rdoDisplay03" name="dispYn" type="radio" ${evEvent.dispYn eq 'N' ? 'checked' : ''} disabled>
									<label for="rdoDisplay03">미전시</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">이벤트명</span>
					</th>
					<td class="input" colspan="3">
						<!-- TODO : 기획 fix 후 입력 제한 글자수 변경 -->
						<input type="text" name="eventName" class="ui-input" title="이벤트명 입력" placeholder="20자 이내로 입력" readonly value="${evEvent.eventName}">
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span class="th-required">이벤트기간</span>
					</th>
					<td class="input" colspan="3">
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="radioTerm01" name="eventTermSetupYn" type="radio" ${evEvent.eventTermSetupYn eq 'Y' ? 'checked' : ''} disabled>
									<label for="radioTerm01">수시</label>
								</span>
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="yyyy.MM.dd" var="eventStartYmd"/>
										<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="HH" var="eventStartH"/>
										<fmt:formatDate value="${evEvent.eventStartDtm}" pattern="mm" var="eventStartM"/>
										<input type="text" name="paramEventStartYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" disabled value="${eventStartYmd}">
									</span>
									<select name="paramEventStartH" class="ui-sel" title="시작시 선택" disabled>
										<c:forEach var="row" begin="0" end="23">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramEventStartH" value="0${row}" />
													<option ${eventStartH eq paramEventStartH ? 'selected' : ''} value="${paramEventStartH}">${paramEventStartH}시</option>
												</c:when>
												<c:otherwise><option ${eventStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramEventStartM" class="ui-sel" title="시작분 선택" disabled>
										<c:forEach var="row" begin="0" end="5">
											<c:set var="paramEventStartM" value="${row}0" />
											<option ${eventStartM eq paramEventStartM ? 'selected' : ''} value="${paramEventStartM}">${paramEventStartM}분</option>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<span class="date-box">
										<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="yyyy.MM.dd" var="eventEndYmd"/>
										<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="HH" var="eventEndH"/>
										<fmt:formatDate value="${evEvent.eventEndDtm}" pattern="mm" var="eventEndM"/>
										<input type="text" name="paramEventEndYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" disabled value="${eventEndYmd}">
									</span>
									<select name="paramEventEndH" class="ui-sel" title="종료시 선택" disabled>
										<c:forEach var="row" begin="0" end="23">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramEventEndH" value="0${row}" />
													<option ${eventEndH eq paramEventEndH ? 'selected' : ''} value="${paramEventEndH}">${paramEventEndH}시</option>
												</c:when>
												<c:otherwise><option ${eventEndH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramEventEndM" class="ui-sel" title="종료분 선택" disabled>
										<c:forEach var="row" begin="0" end="5">
											<c:set var="paramEventEndM" value="${row}0" />
											<option ${eventEndM eq paramEventEndM ? 'selected' : ''} value="${paramEventEndM}">${paramEventEndM}분</option>
										</c:forEach>
									</select>
								</span>
								<!-- E : term-date-wrap -->
							</li>
							<li>
								<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="radioTerm02" name="eventTermSetupYn" type="radio" ${evEvent.eventTermSetupYn eq 'N' ? 'checked' : ''} disabled>
									<label for="radioTerm02">고정</label>
								</span>
							</li>
						</ul>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- E : tbl-row -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">응모 정보 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-row -->
		<form id="searchForm" name="searchForm">
			<input type="hidden" name="eventNo" value="${evEvent.eventNo}" />
			<input type="hidden" name="eventTypeCode" value="${evEvent.eventTypeCode}" />
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>응모 정보 검색</caption>
						<colgroup>
							<col style="width: 140px;">
							<col>
							<col style="width: 140px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기간조회</th>
								<td class="input" colspan="3">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select name="dateType" class="ui-sel" title="기간 선택">
											<!-- <option value="eventStartYmd">시작일</option>
											<option value="evnetEndYmd">종료일</option> -->
											<option value="join">참여일자</option>
											<!-- <option value="moder">최종수정일</option> -->
										</select>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="startYmd" name="startYmd" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="endYmd" name="endYmd" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group area-calendar-btn-group">
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-today">오늘</a>
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-week">일주일</a>
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-month">한달</a>
											<!-- DESC : 20190220 수정 // 기간검색 전체 > 1년으로 공통수정 적용 -->
											<a href="javascript:void(0);" class="btn-sm btn-func text-center calendar-year">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
							<tr>
								<th scope="row">대상회원</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkMemberTypeAll" name="chkMemberTypeAll" type="checkbox" checked>
												<label for="chkMemberTypeAll">전체</label>
											</span>
										</li>
										<c:forEach var="code" items="${memberTypeCodeList}" varStatus="i">
											<c:if test="${code.codeDtlNo ne CommonCode.MEMBER_TYPE_CODE_NONMEMBER}">
												<li>
													<span class="ui-chk">
														<input id="chkMemberType${i.count}" name="memberTypeCodes" type="checkbox" checked value="${code.codeDtlNo}">
														<label for="chkMemberType${i.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<input id="chkMemberTypeEmp" name="empYn" type="checkbox" value="Y">
												<label for="chkMemberTypeEmp">임직원</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
									<%-- <span class="member-grade-list">
										<span class="text">( 회원등급 :</span>
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<c:forEach var="code" items="${mbshpGradeCodeList}" varStatus="i">
												<li>
													<span class="ui-chk">
														<input id="chkMbshpGrade${i.count}" name="mbshpGradeCodes" type="checkbox" checked value="${code.codeDtlNo}">
														<label for="chkMbshpGrade${i.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
											</ul>
										<!-- E : ip-box-list -->
										<span class="text">)</span>
									</span> --%>
								</td>

								<th scope="row">디바이스</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkDevice01" name="chkDeviceModule" type="checkbox" checked>
												<label for="chkDevice01">전체</label>
											</span>
										</li>
										<c:forEach var="code"  items="${deviceCodeList}" varStatus="i">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="deviceCodes" id="chkDevice${i.count}" checked value="${code.codeDtlNo}">
													<label for="chkDevice${i.count}">${code.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${evEvent.eventTypeCode eq CommonCode.EVENT_TYPE_CODE_REPLY_TYPE}">
										<!-- S : 이벤트 유형 > 댓글형인 경우 -->
										<th scope="row">노출 여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkOutcrop01" type="checkbox" checked value="">
														<label for="chkOutcrop01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkOutcrop02" name="dispYnArr" type="checkbox" checked value="Y">
														<label for="chkOutcrop02">노출</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkOutcrop03" name="dispYnArr" type="checkbox" checked value="N">
														<label for="chkOutcrop03">비노출</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<!-- E : 이벤트 유형 > 댓글형인 경우 -->
									</c:when>
									<c:when test="${evEvent.eventTypeCode eq CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE}">
										<!-- S : 이벤트 유형 > 참여형(룰렛형)인 경우 -->
										<th scope="row">발급 여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkIssued01" type="checkbox" checked value="">
														<label for="chkIssued01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkIssued02" name="issueYnArr" type="checkbox" checked value="Y">
														<label for="chkIssued02">발급</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkIssued03" name="issueYnArr" type="checkbox" checked value="N">
														<label for="chkIssued03">발급안함</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<!-- E : 이벤트 유형 > 참여형(룰렛형)인 경우 -->
									</c:when>
								</c:choose>
								<th scope="row">회원 ID</th>
								<td class="input"><input type="text" name="loginId" class="ui-input" title="회원 ID" placeholder="회원 ID"></td>
							</tr>

						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<button type="submit" class="btn-normal btn-func">검색</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- E : tbl-row -->
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">응모 정보</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
			</div>
			<div class="fr">
				<!-- S : 이벤트 유형 > 참여형(출석체크, 룰렛형)인 경우 미노출 -->
				<a href="/promotion/event/result/detail?eventNo=${evEvent.eventNo}" class="btn-sm btn-link">당첨자 관리</a>
				<!-- E : 이벤트 유형 > 참여형(출석체크, 룰렛형)인 경우 미노출 -->
				<a href="javascript:void(0);" id="excelDownload" class="btn-sm btn-func">엑셀 다운로드</a>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="eventJoinSheet">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<form name="excelForm" method="post">
	<input type="hidden" name="eventNo" value="${evEvent.eventNo}" />
	<input type="hidden" name="eventTypeCode" value="${evEvent.eventTypeCode}" />
	<!-- <input type="hidden" name="rowsPerPage" value="9999" /> -->
</form>

<script type="text/javascript">
	$(function() {
		abc.biz.promotion.event.join.detail.codeCombo = ${codeCombo};
	})
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.join.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>