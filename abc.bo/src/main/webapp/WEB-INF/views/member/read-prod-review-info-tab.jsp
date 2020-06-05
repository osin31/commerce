<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>
<script>
$(function() {
	abc.biz.product.review.codeCombo = ${codeCombo};
});
</script>
<!-- S : 상품 후기 목록 -->
<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">후기 작성 포인트 지급 현황</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-col -->
<table class="tbl-col">
	<caption>후기 작성 포인트 지급 현황</caption>
	<colgroup>
		<col>
		<col>
		<col>
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">미지급</th>
			<th scope="col">지급완료</th>
			<th scope="col">확인완료</th>
			<th scope="col">합계</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="text-center">${reviewStats.ASWR_STAT_CODE_10000}건</td>
			<td class="text-center">${reviewStats.ASWR_STAT_CODE_10002}건</td>
			<td class="text-center">${reviewStats.CNFRM_Y}건</td>
			<td class="text-center">${reviewStats.ASWR_STAT_CODE_10000 + reviewStats.ASWR_STAT_CODE_10002 + reviewStats.CNFRM_Y}건</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-col -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">상품 후기 검색</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : search-wrap -->
<div class="search-wrap">
	<form id="search-form">
		<input type="hidden" name="memberNo" value="${memberNo }">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>상품 후기 검색</caption>
				<colgroup>
					<col style="width:15%;">
					<col>
					<col style="width:79px;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">후기구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="rvwType" id="radioReview01" value="" checked>
										<label for="radioReview01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="rvwType" id="radioReview02" value="P">
										<label for="radioReview02">일반</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="rvwType" id="radioReview03" value="G">
										<label for="radioReview03">포토</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">검색어</th>
						<td class="input">
							<div class="opt-keyword-box">
								<select class="ui-sel" title="검색어 타입 선택" name="searchKeywordType">
									<option value="">선택</option>
									<option value="prdt-name">상품명</option>
									<option value="style-info">스타일</option>
								</select>
								<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchKeyword">
							</div>
							<!-- E : opt-keyword-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">확인여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="onlnBuyYn" id="radioConfirm03-01"  value="" checked>
										<label for="radioConfirm03-01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="onlnBuyYn" id="radioConfirm03-02" value="Y">
										<label for="radioConfirm03-02">확인완료</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="onlnBuyYn" id="radioConfirm03-03" value="N">
										<label for="radioConfirm03-03">미확인</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">답변여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="aswrStatCode" id="radioAnswer01"  value="" checked>
										<label for="radioAnswer01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="aswrStatCode" id="radioAnswer02" value="${CommonCode.ASWR_STAT_CODE_CM }">
										<label for="radioAnswer02">답변완료</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="aswrStatCode" id="radioAnswer03" value="${CommonCode.ASWR_STAT_CODE_UN }">
										<label for="radioAnswer03">미답변</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">포인트지급여부</th>
						<td class="input" colspan="4">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="pointPayYn" id="radioPoint01" value="" checked>
										<label for="radioPoint01">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="pointPayYn" id="radioPoint02" value="Y">
										<label for="radioPoint02">지급</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="pointPayYn" id="radioPoint03" value="N">
										<label for="radioPoint03">미지급</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="pointPayYn" id="radioPoint04" value="C">
										<label for="radioPoint04">지급취소</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="pointPayYn" id="radioPoint05" value="I">
										<label for="radioPoint05">지급불가</label>
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
								<select class="ui-sel" name="periodType" title="기간 선택">
									<option value="">선택</option>
									<option value="write-dtm">작성일자</option>
									<option value="cnfrm-dtm">확인일자</option>
									<option value="aswr-dtm">답변일자</option>
									<option value="point-pay-dtm">포인트지급일자</option>
									<option value="point-pay-cncl-dtm">포인트환수일자</option>
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
		<h3 class="content-title">상품후기 내역</h3>
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
	<div id="review-list">
	</div>
</div>
<!-- E : ibsheet-wrap -->
<!-- E : 상품 후기 목록 -->
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.review.js<%=_DP_REV%>"></script>
<script>
$(function() {
	reviewSheet.SetColEditable('aswrStatCode', 0);
	reviewSheet.SetColEditable('cnfrmYn', 0);
	reviewSheet.SetColEditable('pointPayType', 0);
});
</script>
