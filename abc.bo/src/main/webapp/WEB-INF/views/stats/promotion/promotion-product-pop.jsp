<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp"%>
<div class="window-wrap">
	<div class="window-title">
		<h1>상품 매출 현황</h1>
	</div>
	<div class="window-content">
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li class="tc-red">* 기준일시 : <fmt:formatDate value="${currentTime}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></li>
				<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
				<li>* 당일 매출 현황 자료는 주문일 기준(결제완료 기준)이며, 주문취소 및 구매확정 후 교환/반품에 따라
					정보가 상이할 수 있습니다.</li>
			</ul>
		</div>
		<!-- E : tbl-desc-wrap -->
		<form id="form">
			<input type="hidden" name="planningId" value="${planningId}" id="plnId">
			<input type="hidden" name="promoNo" value="${promoNo}" id="proNo">
		<!-- S : search-wrap -->
		<div class="content-header">
			<table class="tbl-row">
				<caption>기본 정보 테이블이며 사이트 구분 ,디바이스 구분,기획전ID/명, 기획전 판매기간, 기획전 기간을(를) 나타낸 표입니다.</caption>
				<colgroup>
					<col style="width:140px;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">사이트 구분</th>
						<td class="text">${siteType}
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="">디바이스 구분</span>
						</th>
						<td class="text">${deviceType}
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="">${planningId != null ? '기획전ID/명' : '프로모션 번호/명' }</span>
						</th>
						<td class="text"><span>${planningId != null ? planningId : promoNo}</span> / ${planningName != null ? planningName : promoName}</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="">${planningId != null ? '기획전 판매기간' : '프로모션 판매기간' }</span>
						</th>
						<td class="text">${sellDate == ' ~ ' ? '설정 없음' : sellDate}
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="">${planningId != null ? '기획전 기간' : '프로모션 기간' }</span>
						</th>
						<td class="text">${planningDate == ' ~ ' ? '설정 없음' : planningDate}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- E : search-wrap -->
		</form>
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
			<div id="productGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.promotion.promotion.product.popup.js<%=_DP_REV%>"></script>