<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function(){
		abc.biz.stats.order.realtime.initClaimChangeStatsSheet();
		abc.biz.stats.order.realtime.initClaimReturnStatsSheet();
		abc.biz.stats.order.realtime.srchOptionControll();
		
		<%-- 목록갯수 selectBox로 search --%>
		$("#pageCount").change(function(){
			abc.biz.stats.order.realtime.currentSaleDoAction("search");
		});
	});
</script>
<!-- S : container -->
<div class="container">
	<form name="form" id="form">
		<input type="hidden" name="typeStats" value="claim">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">당일 반품/교환 처리 현황 통계</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="javascript:void(0);"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>매출/정산/통계</li>
							<li>실시간 현황 </li>
							<li>당일 반품/교환 처리 현황 통계</li>
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
				<div class="search-inner">
					<table class="tbl-search">
						<caption>검색</caption>
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
												<input type="radio" name="siteNo" id="radioSite01" value="" checked>
												<label for="radioSite01">전체</label>
											</span>
										</li>
										<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="radioSite${status.count}" value="${siteInfo.siteNo}">
													<label for="radioSite${status.count}"><c:out value="${siteInfo.siteName}"/></label>
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
												<input id="deviceCodeAll" name="deviceCodeAll" type="checkbox" checked="checked">
												<label for="deviceCodeAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty codeList.DEVICE_CODE}">
											<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
												<li>
													<span class="ui-chk">
														<input id="deviceCode${deviceCode.codeDtlName}" value="${deviceCode.codeDtlNo}"  custom="deviceCode" name="deviceCodes"  type="checkbox" checked="checked">
														<label for="deviceCode${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">요청 구분</th>
								<td class="input" colspan="4">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="claimReturn" name="claimType" type="radio" value="return" checked>
												<label for="claimReturn">반품</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="claimChange" name="claimType" type="radio" value="change">
												<label for="claimChange">교환</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-normal btn-func" id="searchBtn">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->
	
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap border-box">
				<ul class="tbl-desc-list">
					<li class="tc-red">* 기준일시 : <fmt:formatDate value="${currentDateTime}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></li>
					<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다. </li>
					<li>* 당일 매출 현황 자료는 주문일 기준(결제완료 기준)이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
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
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
				</div>
			</div>
	
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="currentClaimReturnSaleStatsGrid" style="width:100%; height:429px;"></div>
				<div id="currentClaimChangeSaleStatsGrid" style="width:100%; height:429px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</form>
</div>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.current.salestats.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>

