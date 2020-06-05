<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<div class="window-wrap">
	<div class="window-title">
		<h1>당첨자 목록</h1>
	</div>
	<div class="window-content">
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<!-- S : tbl-col -->
		<form name="searchForm">
			<table class="tbl-col">
				<caption>온라인 회원 혜택</caption>
				<colgroup>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">발표명</th>
						<th scope="col">당첨자수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center">
							<input type="hidden" name="eventNo" value="${eventNo}"	 />
							<input type="hidden" name="eventTypeCode" value="${eventTypeCode}"	 />
							<c:set var="totalCount" value="0"></c:set>
							<select id="resultBenefit" name="eventRsltBenefitSeq" class="ui-sel">
								<option value="">전체</option>
								<c:forEach var="row" items="${evEventResultBenefitList}">
									<option data-win-count="${row.winCount}" value="${row.eventRsltBenefitSeq}">${row.benefitName}</option>
									<c:set var="totalCount" value="${totalCount + row.winCount }" />
								</c:forEach>
							</select>
						</td>
						<td class="text-center"><a href="javascript:void(0);" id="winCountArea">${totalCount}</a></td>
					</tr>
				</tbody>
			</table>
		</form></br>
		<!-- E : tbl-col -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="eventWinSheet">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>


<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.win.popup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp" %>