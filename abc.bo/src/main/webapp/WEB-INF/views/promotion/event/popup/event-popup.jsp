<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<div class="window-wrap">
	<div class="window-title">
		<h1>이벤트 찾기</h1>
	</div>
	<div class="window-content">
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="searchForm" name="searchForm">
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>검색</caption>
						<colgroup>
							<col style="width: 110px;">
							<col>
							<col style="width: 79px">
							<col style="width: 110px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기간조회</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select name="dateType" class="ui-sel" title="기간 선택">
											<option value="eventStartYmd">시작일</option>
											<option value="evnetEndYmd">종료일</option>
											<option value="rgster">최초작성일</option>
											<option value="moder">최종수정일</option>
										</select>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="startYmd" name="startYmd" class="ui-cal js-ui-cal inputCalendar inputCalendar" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="endYmd" name="endYmd" class="ui-cal js-ui-cal inputCalendar inputCalendar" title="종료일 선택">
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
								<th scope="row">유형</th>
								<td class="input">
									<select name="eventTypeCode" class="ui-sel" title="유형 선택">
										<option value="">전체</option>
										<c:forEach var="code" items="${eventTypeCodeList}">
											<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
										</c:forEach>
									</select>
								</td>
								<td></td>
								<th scope="row">전시여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="rdoDisplay01" name="dispYn" type="radio" checked value="">
												<label for="rdoDisplay01">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="rdoDisplay02" name="dispYn" type="radio" value="Y">
												<label for="rdoDisplay02">전시</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="rdoDisplay03" name="dispYn" type="radio" value="N">
												<label for="rdoDisplay03">미전시</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">검색어</th>
								<td class="input" colspan="4">
									<div class="opt-keyword-box">
										<select name="keywordType" class="ui-sel" title="유형 선택">
											<option value="">선택</option>
											<option value="eventNo">이벤트번호</option>
											<option value="eventName">이벤트명</option>
											<option value="insdMgmtInfoText">내부관리번호</option>
										</select>
										<input type="text" name="keyword" class="ui-input" title="검색어 입력" placeholder="검색어 입력">
									</div>
								</td>
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
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
		</form>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">목록</h3>
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
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="eventSheet">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>


<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.popup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp" %>