<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script>
$(function() {
	abc.biz.stats.promotion.planning.isAdmin = ${isAdmin};
});
</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">기획전 현황 통계</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>프로모션 통계</li>
						<li>기획전 현황 통계</li>
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
		<div class="search-wrap">
			<form id="form">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>검색 테이블이며 사이트 구분, 디바이스 구분, 판매 기간, 기획전 기간, 기획전 검색에 대해 검색합니다.</caption>
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
							<th scope="row">디바이스 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="device-code-all" type="checkbox" checked value="">
											<label for="device-code-all">전체</label>
										</span>
									</li>
									<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
										<li>
											<span class="ui-chk">
												<input id="device-code-${deviceCode.codeDtlName}" class="device-code" value="${deviceCode.codeDtlNo}" name="deviceCodeArr"  type="checkbox" checked>
												<label for="device-code-${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">판매 기간</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<input type="hidden" name="dateKeywordType" value="order-date">
								<span class="text">주문 완료일 기준&nbsp;</span>
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="sellFromDate">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="sellToDate" class="ui-cal js-ui-cal" title="종료일 선택" name="sellToDate">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0);"   name="perYear"   class="btn-sm btn-func text-center">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">기획전 기간</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select class="ui-sel" title="유형 선택" name="choiceDate">
										<option value="start-date">시작일</option>
										<option value="end-date">종료일</option>
									</select>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="plndpFromDate">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="plndpToDate">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0);"   name="perYear"   class="btn-sm btn-func text-center">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">기획전 검색</th>
							<td class="input" colspan="4">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" title="유형 선택" name="searchKeywordType">
										<option value="planning-name" selected>기획전명</option>
										<option value="planning-id">기획전ID</option>
									</select>
									<input type="text" class="ui-input" title="검색어 입력" name="searchKeyword">
								</div>
								<!-- E : opt-keyword-box -->
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
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 기획전 통계 자료는 매출발생 시점 기준(결제완료 기준) 조회 통계이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
				<li>* 기획전 통계 자료는 당일 발생 매출 데이터는 포함되지 않습니다.</li>
				<%-- <li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li> --%>
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
			<div id="planningGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.promotion.planning.js<%=_DP_REV%>"></script>