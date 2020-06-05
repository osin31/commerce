<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">입점사 수수료 변경이력 조회</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>기타 통계/현황</li>
						<li>입점사 수수료 변경이력 조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">변경이력 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<!-- S : search-wrap -->
		<div class="search-wrap">
			<form id="form">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>변경이력 검색 테이블이며 사이트 구분, 수수료 유형, 입점사, 상품, 변경일, 표준 카테고리(대), 브랜드에 대해 검색합니다.</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px;">
						<col style="width: 130px;">
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
											<input id="site-no-all" name="siteNo" type="radio" value="" checked>
											<label for="site-no-all">전체</label>
										</span>
									</li>
									<c:forEach var="item" items="${searchConditionSiteList }">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="site-no-${item.siteNo }" name="siteNo" type="radio" value="${item.siteNo }">
												<label for="site-no-${item.siteNo }">${item.siteName }</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">수수료 유형</th>
							<td class="input">
								<select class="ui-sel" title="수수료 유형 선택" name="cmsnKind">
									<option value="">전체</option>
									<option value="기본">기본</option>
									<option value="예외">예외</option>
									<option value="프로모션">프로모션</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="th-required">입점사</span></th>
							<td class="input">
								<span class="ip-search-box">
									<input type="text" id="vndr-name" class="ui-input" title="입점사명 입력" placeholder="입점사명" readonly/>
									<a href="javascript:void(0);" id="search-vndr" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									<!-- <input type="hidden" id="vndr-no" name="vndrNo" />  -->
								</span>
							</td>
							<td></td>
							<th scope="row">상품</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" title="유형 선택" name="searchKeywordType">
										<option value="prdt-name">상품명(국문/영문)</option>
										<option value="style-info">스타일</option>
										<option value="color-code">색상코드</option>
										<option value="srch-keyword">상품검색어</option>
									</select>
									<input type="text" class="ui-input" title="검색어 입력" name="searchKeyword">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">변경일</span>
							</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDate">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDate">
									</span>
									<span class="btn-group" >
										<a href="javascript:void(0);"   name="perYesterday" class="btn-sm btn-func">어제</a>
										<a href="javascript:void(0);"   name="perWeek"  	class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   name="per1Month" 	class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0);"   name="perYear"   	class="btn-sm btn-func text-center">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">표준 카테고리 (대)</th>
							<td class="input">
								<select class="ui-sel" title="표준 카테고리 (대) 선택" name="vndrStdCtgrNo">
									<option value="">선택</option>
									<c:forEach var="item" items="${searchConditionStandardCategoryList}" varStatus="status">
										<option value="${item.stdCtgrNo}">${item.stdCtgrName}</option>
									</c:forEach>
								</select>
							</td>
							<td></td>
							<th scope="row">브랜드</th>
							<td class="input">
								<span class="ip-search-box">
									<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName" value=""/>
									<!-- <input type="hidden" id="brand-no" name="brandNo" /> -->
									<a href="#" class="btn-search" id="search-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func" id="search">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</form>
		</div>
		<!-- E : search-wrap -->
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">변경이력 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<label class="title" for="page-count">목록개수</label>
					<select class="ui-sel" id="page-count">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<!-- E : opt-group -->
			</div>
			<div class="fr">
				<a href="#" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div style="width:100%; height:429px;" id="cmsn-stat-list">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.etc.commission.js<%=_DP_REV%>"></script>