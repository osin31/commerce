<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.promotion.planning.display.approval.codeCombo = ${codeCombo};
	abc.biz.promotion.planning.display.approval.chnnlCombo = ${chnnlCombo};
	abc.biz.promotion.planning.display.approval.isAdmin = ${isAdmin};
});
</script>
	
		<!-- S : container -->
		<div class="container">
			<input type="hidden" name="isAdmin" value="${isAdmin}">
			<!-- 미리보기 URL -->
			<input type="hidden" name="artFoUrl" value="${Const.SERVICE_DOMAIN_ART_FO }">
			<input type="hidden" name="artMoUrl" value="${Const.SERVICE_DOMAIN_ART_MO }">
			<input type="hidden" name="abcFoUrl" value="${Const.SERVICE_DOMAIN_ABC_FO }">
			<input type="hidden" name="abcMoUrl" value="${Const.SERVICE_DOMAIN_ABC_MO }">
			<input type="hidden" name="gsFoUrl" value="${Const.SERVICE_DOMAIN_GS_FO }">
			<input type="hidden" name="gsMoUrl" value="${Const.SERVICE_DOMAIN_GS_MO }">
			
			<!-- TODO: kis 도메인 등록 후 값 설정 -->
			<input type="hidden" name="kidsFoUrl" value="${Const.SERVICE_DOMAIN_KIDS_FO }">
			<input type="hidden" name="kidsMoUrl" value="${Const.SERVICE_DOMAIN_KIDS_MO }">
			
			<input type="hidden" name="otsFoUrl" value="${Const.SERVICE_DOMAIN_OTS_FO }">
			<input type="hidden" name="otsMoUrl" value="${Const.SERVICE_DOMAIN_OTS_MO }">
			<!-- 미리보기 URL -->
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">기획전 승인 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>기획전 승인관리</li>
								<li>기획전 승인요청</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">기획전 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm">
				<c:if test="${!isAdmin}">
					<input type="hidden" name="">
				</c:if>
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>기획전 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기간조회</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" title="기간 선택" name="dateSearchType">
												<option value="A">승인요청일</option>
												<option value="R">최초등록일</option>
												<option value="F">반려처리일</option>
												<option value="S">기획전 시작일</option>
												<option value="E">기획전 종료일</option>
												<option value="M">최종수정일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dateSearchS" id="dateSearchS">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dateSearchE" id="dateSearchE">
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
									<!-- S : 20190329 수정 // 사이트구분 > 채널구분 수정 -->
									<th scope="row">전시채널</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="rdoChannelAll" name="chnnlNo" type="radio" value checked>
													<label for="rdoChannelAll">전체</label>
												</span>
											</li>
											<c:forEach items="${chnnlList}" var="chnnl" varStatus="status">
												<c:if test="${chnnl.chnnlNo ne Const.SITE_CHNNL_ART}">
													<li>
														<span class="ui-rdo">
															<input id="rdoChannel${status.count}" name="chnnlNo" type="radio" value="${chnnl.chnnlNo}">
															<label for="rdoChannel${status.count}">${chnnl.chnnlName}</label>
														</span>
													</li>
												</c:if>								
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<!-- E : 20190329 수정 // 사이트구분 > 채널구분 수정 -->
									<td></td>
									<c:if test="${isAdmin}">
										<th scope="row">입점사</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<input type="text" class="ui-input" title="업체명" name="vndrName" readonly>												
												<input type="hidden" name="vndrNo">
												<input type="hidden" name="vndrGbnType">
												<a href="javascript:void(0);" class="btn-sm btn-link" id="searchVendor">입점사 검색</a>
											</div>
											<!-- E : opt-keyword-box -->
										</td>							
									</c:if>
									<c:if test="${!isAdmin}">
										<input type="hidden" name="vndrNo" value="${vndrNo}">
										<input type="hidden" name="vndrGbnType" value="V">
									</c:if>
								</tr>
								<tr>
									<th scope="row">승인상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkStatusAll" name="plndpStatCodeAll" type="checkbox" value="A" ${empty param.plndpStatCode ? 'checked' : ''}>
													<label for="chkStatusAll">전체</label>
												</span>
											</li>
											<c:set var="except" value="${isAdmin ? '10000' : '10003'}"></c:set>
											<c:forEach items="${plndpStatCodeList}" var="code" varStatus="status">
												<c:if test="${code.codeDtlNo != '10003' && code.codeDtlNo != except}">
													<li>
														<span class="ui-chk">
															<input id="chkStatus${status.count}" class="plndpStatCode" name="plndpStatCodeArr" type="checkbox" value="${code.codeDtlNo}" ${(param.plndpStatCode eq code.codeDtlNo) or empty param.plndpStatCode ? 'checked' : ''}>
															<label for="chkStatus${status.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">디바이스</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkDeviceAll" name="deviceCodeAll" type="checkbox" value="A" checked>
													<label for="chkDeviceAll">전체</label>
												</span>
											</li>
											<c:forEach items="${deviceCodeList}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<input id="chkDevice${status.count}" class="deviceCode" name="deviceCodeArr" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="chkDevice${status.count}">${code.codeDtlName}</label>
													</span>
												</li>											
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="유형 선택" name="searchType">
												<option value>선택</option>
												<option value="I">기획전 ID</option>
												<option value="N">기획전명</option>
											</select>
											<input type="text" class="ui-input" title="검색어 입력" placeholder="검색어 입력" name="searchWord">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">전시유형</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="radioTypeAll" name="dispType" type="radio" value checked>
													<label for="radioTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioTypeB" name="dispType" type="radio" value="B">
													<label for="radioTypeB">일반</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioTypeS" name="dispType" type="radio" value="S">
													<label for="radioTypeS">모아보기</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">A-RT 전시 여부</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="radioArtDispAll" name="artDispYn" value="" type="radio" checked>
													<label for="radioArtDispAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioArtDispY" name="artDispYn" value="Y" type="radio">
													<label for="radioArtDispY">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioArtDispN" name="artDispYn" value="N" type="radio">
													<label for="radioArtDispN">미전시</label>
												</span>
											</li>
										</ul>
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
						<h3 class="content-title">기획전목록</h3>
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
						<span class="btn-group">
							<c:choose>
								<c:when test="${isAdmin}">
									<a href="javascript:void(0);" class="btn-sm btn-func" id="returnBtn">선택 일괄반려</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" class="btn-sm btn-del" id="removeBtn">선택 삭제</a>
									<a href="javascript:void(0);" class="btn-sm btn-func" id="requestBtn">선택 승인요청</a>
								</c:otherwise>
							</c:choose>							
						</span>
					</div>
					<div class="fr"></div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dispSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.approval.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>