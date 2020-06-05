<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">당첨자 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>프로모션</li>
						<li>이벤트관리</li>
						<li>당첨자 관리</li>
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
		<form id="searchForm" name="searchForm">
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>검색</caption>
						<colgroup>
							<col style="width: 120px;">
							<col>
							<col style="width: 79px">
							<col style="width: 120px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기간조회</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select name="dateType" class="ui-sel" title="기간 선택">
										<option value="pblcYmd">당첨자 발표일</option>
											<!-- <option value="eventStartYmd">시작일</option>
											<option value="evnetEndYmd">종료일</option> -->
											<option value="rgster">작성일</option>
											<!-- <option value="moder">수정일</option> -->
										</select>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="startYmd" name="startYmd" class="ui-cal js-ui-cal inputCalendar" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" id="endYmd" name="endYmd" class="ui-cal js-ui-cal inputCalendar" title="종료일 선택">
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
								<th scope="row">디바이스</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkDevice01" name="chkDeviceModule" type="checkbox" checked value="ALL">
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
								<th scope="row">채널</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkChannel01" name="chkChannelModule" type="checkbox" checked value="ALL">
												<label for="chkChannel01">전체</label>
											</span>
										</li>
										<c:forEach var="channel" items="${channelList}" varStatus="i">
											<%--
											<c:if test="${channel.chnnlNo ne Const.SITE_CHNNL_ART}">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkChannel${i.count}" name="chnnlNos" type="checkbox" checked value="${channel.chnnlNo}">
														<label for="chkChannel${i.count}">${channel.chnnlName}</label>
													</span>
												</li>		
											</c:if>
											--%>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkChannel${i.count}" name="chnnlNos" type="checkbox" checked value="${channel.chnnlNo}">
													<label for="chkChannel${i.count}">${channel.chnnlName}</label>
												</span>
											</li>			
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">검색어</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select name="keywordType" class="ui-sel" title="유형 선택">
											<option value="">선택</option>
											<option value="eventNo">이벤트번호</option>
											<option value="eventName">이벤트명</option>
											<option value="insdMgmtInfoText">내부관리번호</option>
										</select>
										<input type="text" name="keyword" class="ui-input" title="검색어 입력" placeholder="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
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
			<div class="fr">
				<a href="/promotion/event/result/detail" class="btn-sm btn-link">등록</a>
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
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.result.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>