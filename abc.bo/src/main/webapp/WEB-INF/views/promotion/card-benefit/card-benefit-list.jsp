<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">카드사 혜택관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>카드사 혜택관리</li>
								<li>카드사 혜택</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">카드사 혜택 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<form id="cardBenefitForm">
				<!-- S : search-wrap -->				
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>카드사 혜택 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">카드 할부 행사명</th>
									<td class="input">
										<input type="text" id="cardBenefitName" name="cardBenefitName" class="ui-input" title="카드 할부 행사명 입력">
									</td>
									<td></td>
									<th scope="row">전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoDisplay01" name="dispYn" type="radio" value checked>
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
													<label for="rdoDisplay03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" id="condition" name="condition" title="기간 선택">
												<option value="M">최종수정일</option>
												<option value="S">전시시작일</option>
												<option value="E">전시종료일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" name="startYmd" id="startYmd" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" name="endYmd" id="endYmd" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group area-calendar-btn-group">
												<a href="#" class="btn-sm btn-func calendar-today">오늘</a>
												<a href="#" class="btn-sm btn-func calendar-week">일주일</a>
												<a href="#" class="btn-sm btn-func calendar-month">한달</a>
												<!-- DESC : 20190220 수정 // 기간검색 전체 > 1년으로 공통수정 적용 -->
												<a href="#" class="btn-sm btn-func calendar-year text-center">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="refreshBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<button type="submit" class="btn-normal btn-func" id="searchBtn">검색</button>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>				
				<!-- E : search-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">카드사 할부혜택 목록</h3>
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
						<a href="/promotion/card-benefit/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>				
				<!-- E : tbl-controller -->
				</form>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="cardBenefitSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/promotion/abcmart.promotion.card.benefit.list.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>