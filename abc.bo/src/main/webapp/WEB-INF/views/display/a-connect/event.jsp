<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">이벤트 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>이벤트 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">이벤트 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>이벤트 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">작성일자</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" title="기간 선택" name="searchDateType">
												<option value>선택</option>
												<option value="S">이벤트시작일</option>
												<option value="E">이벤트종료일</option>
												<option value="R">등록일시</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" id="dateSearchS" name="searchDateS">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" id="dateSearchE" name="searchDateE">
											</span>
											<span class="btn-group area-calendar-btn-group">
												<a href="#" class="btn-sm btn-func calendar-today">오늘</a>
												<a href="#" class="btn-sm btn-func calendar-week">일주일</a>
												<a href="#" class="btn-sm btn-func calendar-month">한달</a>
												<a href="#" class="btn-sm btn-func calendar-year">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="dispYn" id="radioDisplay01" value checked>
													<label for="radioDisplay01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="dispYn" id="radioDisplay02" value="Y">
													<label for="radioDisplay02">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="dispYn" id="radioDisplay03" value="N">
													<label for="radioDisplay03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">진행상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="progressYn" id="radioState01" value checked>
													<label for="radioState01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="progressYn" id="radioState02" value="Y">
													<label for="radioState02">진행</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="progressYn" id="radioState03" value="N">
													<label for="radioState03">진행안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">검색어</th>
									<td class="input" colspan="4">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="searchType">
												<option value>선택</option>
												<option value="I">이벤트ID</option>
												<option value="N">이벤트명</option>
												<option value="S">적용매장</option>
												<option value="R">작성자</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchWord" maxlength="50">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="clear-form"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
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
						<h3 class="content-title">이벤트 목록</h3>
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
						<a href="/display/a-connect/event/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="eventSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.aconnect.event.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>