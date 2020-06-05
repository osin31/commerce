<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	<script type="text/javascript">
	$(function () {
		abc.biz.display.search.hashtag.searchFormInit();
		abc.biz.display.search.hashtag.initHashTagSheet();
		abc.biz.display.search.hashtag.doActionHashTag("search");
	});

	// 상담스크립트 상세 페이지 이동
	function hashTagSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( hashTagSheet.ColSaveName(Col) == "hshtgName" && Value != "" ) {
				var _url = "/display/hashtag/read-detail?menuNo="+abc.param.getParams().menuNo + "&hshtgSeq=" + hashTagSheet.GetCellValue(Row, "hshtgSeq");
				location.href = _url;
			}
		}
	}
	</script>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">해쉬태그 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>해쉬태그관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">해쉬테그 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->
			<form name="hashTagSearchForm" id="hashTagSearchForm">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>해쉬테그 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="siteNoAll" name="siteNo" type="radio" value="" checked>
													<label for="siteNoAll">전체</label>
												</span>
											</li>
											<c:forEach var="siteList" items="${siteList}" varStatus="status">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="siteNo<c:out value="${siteList.siteNo}" />"
														       value="<c:out value="${siteList.siteNo}" />" >
														<label for="siteNo<c:out value="${siteList.siteNo}" />" >
															<c:out value="${siteList.siteName}" />
														</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="useYn01" name="useYn" type="radio" value="" checked>
													<label for="useYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="useYn02" name="useYn" type="radio" value="Y">
													<label for="useYn02">예</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="useYn03" name="useYn" type="radio" value="N">
													<label for="useYn03">아니오</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">해쉬태그명</th>
									<td class="input" colspan="4"><input type="text" class="ui-input" title="해쉬태그 입력" id="orderSearchText" value="" name="hshtgName"></td>
								</tr>
								<tr>
									<th scope="row">사용기간</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
												<option value="dispStartYmd">시작일</option>
												<option value="dispEndYmd">종료일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" name="fromDate" id="fromDate" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" name="toDate" id="toDate" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group">
												<a href="javascript:void(0);" name="perToday"  id="perToday" class="btn-sm btn-func">오늘</a>
												<a href="javascript:void(0);" name="perWeek"   id="perWeek" class="btn-sm btn-func">일주일</a>
												<a href="javascript:void(0);" name="per1Month" id="per1Month" class="btn-sm btn-func">한달</a>
												<a href="javascript:void(0);" name="perYear"   id="perYear" class="btn-sm btn-func text-center">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="hashTagReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" id="hashTagSearch" class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->
			</form>
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">해쉬태그 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select name="pageCount" id="pageCount" class="ui-sel" id="selTermsModule">
								<option value="15">15개 보기</option>
								<option value="30">30개 보기</option>
								<option value="50">50개 보기</option>
								<option value="100">100개 보기</option>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-link" id="hashTagCreateForm">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="hashTagGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
<script src="/static/common/js/biz/display/abcmart.display.search.hashtag.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>