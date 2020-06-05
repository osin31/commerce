<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>
<script>
$(function() {
	abc.biz.product.inquiry.codeCombo = ${codeCombo};
});
</script>
<!-- S : 상품 Q&A 목록 -->
<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">문의 현황</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-col -->
<table class="tbl-col">
	<caption>문의 현황</caption>
	<colgroup>
		<col>
		<col>
		<col>
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">미답변</th>
			<th scope="col">답변보류</th>
			<th scope="col">답변완료</th>
			<th scope="col">합계</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="text-center">${inquiryStats.ASWR_STAT_CODE_10000}건</td>
			<td class="text-center">${inquiryStats.ASWR_STAT_CODE_10001}건</td>
			<td class="text-center">${inquiryStats.ASWR_STAT_CODE_10002}건</td>
			<td class="text-center">${inquiryStats.ASWR_STAT_CODE_10000 + inquiryStats.ASWR_STAT_CODE_10001 + inquiryStats.ASWR_STAT_CODE_10002}건</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-col -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">상품 Q&amp;A 검색</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : search-wrap -->
<div class="search-wrap">
	<form id="search-form">
		<input type="hidden" name="memberNo" value="${memberNo }">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품 Q&amp;A 검색</caption>
				<colgroup>
					<col style="width:15%;">
					<col>
					<col style="width:79px;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">검색어</th>
						<td class="input" colspan="4">
							<!-- TODO : 기획 fix후 option 변경 -->
							<!-- S : opt-keyword-box -->
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchKeywordType">
									<option value="">선택</option>
									<option value="prdt-name">상품명</option>
									<option value="style-info">스타일</option>
									<option value="title-cont">제목+내용</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">답변상태</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="aswr-stat-code-all" name="aswrStatCode" type="radio" value="" checked>
										<label for="aswr-stat-code-all">전체</label>
									</span>
								</li>
								<c:forEach var="item" items="${aswrStatCode}">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="aswr-stat-code-${item.codeDtlNo }" name="aswrStatCode" type="radio" value="${item.codeDtlNo }">
											<label for="aswr-stat-code-${item.codeDtlNo }">${item.codeDtlName}</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">문의유형</th>
						<td class="input">
							<!-- TODO : 기획 fix후 option 변경 -->
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select id="cnsl-type-code" name="cnslTypeCode" class="ui-sel" title="문의유형 선택">
									<option value="">문의유형</option>
									<c:forEach var="item" items="${cnslTypeCode}">
										<option value="${item.codeDtlNo}">${item.codeDtlName}</option>
									</c:forEach>
								</select>
								<select id="cnsl-type-dtl-code" name="cnslTypeDtlCode" class="ui-sel" title="문의구분 선택">
									<option value="">문의구분</option>
									<c:forEach var="item" items="${cnslTypeDtlCode}">
										<option value="${item.codeDtlNo}">${item.codeDtlName}</option>
									</c:forEach>
								</select>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">기간검색</th>
						<td class="input" colspan="4">
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<select class="ui-sel" name="periodType" title="기간 선택">
									<option value="">선택</option>
									<option value="inqry-dtm">문의일자</option>
									<option value="aswr-dtm">답변일자</option>
								</select>
								<span class="date-box">
									<input type="text" name="periodStartDate" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" name="periodEndDate" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
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
		<h3 class="content-title">상품 Q&amp;A 내역</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-controller -->
<div class="tbl-controller">
	<div class="fl">
		<span class="opt-group">
			<label class="title" for="page-count">목록개수</label>
			<select class="ui-sel" id="page-count">
				<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
			</select>
		</span>
	</div>
</div>
<!-- E : tbl-controller -->

<!-- S : ibsheet-wrap -->
<div class="ibsheet-wrap">
	<div id="inquiry-list">
	</div>
</div>
<!-- E : ibsheet-wrap -->
<!-- E : 상품 Q&A 목록 -->
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.inquiry.js<%=_DP_REV%>"></script>
