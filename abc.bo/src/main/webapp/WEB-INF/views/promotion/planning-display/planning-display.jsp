<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.promotion.planning.display.codeCombo = ${codeCombo};
	abc.biz.promotion.planning.display.chnnlCombo = ${chnnlCombo};
	abc.biz.promotion.planning.display.isAdmin = ${isAdmin};
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
						<h2 class="page-title">기획전 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>기획전 관리</li>
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
				<!-- S : 20190329 수정 // 검색영역 전체 수정 -->
				<!-- S : search-wrap -->
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
												<option value="S">기획전 시작일</option>
												<option value="E">기획전 종료일</option>
												<option value="R">최초등록일</option>
												<option value="M">최종수정일</option>
												<option value="A">승인일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dateSearchS" id="dateSearchS">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dateSearchE" id="dateSearchE">
											</span>
											<span class="btn-group area-calendar-btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-today">오늘</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-week">일주일</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-month">한달</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-year">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<!-- S : 20190404 수정 // 기획전 구분 checkbox로 수정 -->
								<tr>
									<th scope="row">기획전 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkTypeAll" name="plndpTypeCodeAll" type="checkbox" value="A" checked>
													<label for="chkTypeAll">전체</label>
												</span>
											</li>
											<c:forEach items="${plndpTypeCodeList}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<input id="chkType${status.count}" class="plndpTypeCode" name="plndpTypeCodeArr" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="chkType${status.count}">${code.codeDtlName}</label>
													</span>
												</li>											
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row" ${!isAdmin ? 'style="display: none;"' : ''}>자사/입점 구분</th>
									<td class="input" ${!isAdmin ? 'style="display: none;"' : ''}>
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="radioStoreTypeAll" name="vndrGbnType" value type="radio" ${isAdmin ? 'checked ' : 'disabled'}>
													<label for="radioStoreTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioStoreTypeC" name="vndrGbnType" value="C" type="radio" ${isAdmin ? '' : 'disabled'}>
													<label for="radioStoreTypeC">자사</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioStoreTypeV" name="vndrGbnType" value="V" type="radio" ${isAdmin ? '' : 'checked '}>
													<label for="radioStoreTypeV">입점</label>
												</span>
												<span class="ip-search-box">
													<input type="hidden" name="vndrNo" value="${vndrNo}">
													<input type="text" class="ui-input" title="검색어 입력" name="vndrWord" value="${vndrName}" readonly>
													<a href="#" class="btn-search" id="vndrSearchBtn"><i class="ico-search"><span class="offscreen">검색</span></i></a>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<!-- E : 20190404 수정 // 기획전 구분 checkbox로 수정 -->
								<tr>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="rdoUseAll" name="useYn" type="radio" ${empty param.useYn ? 'checked' : ''} value>
													<label for="rdoUseAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="rdoUseY" name="useYn" type="radio" value="Y" ${param.useYn eq 'Y' ? 'checked' : ''}>
													<label for="rdoUseY">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="rdoUseN" name="useYn" type="radio" value="N">
													<label for="rdoUseN">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">진행상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkProgressStatusAll" name="progressAll" value="A" type="checkbox" ${param.progress ne 'O' ? 'checked' : ''}>
													<label for="chkProgressStatusAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<input id="chkStatusW" class="progressStatus" name="progressArr" value="W" type="checkbox" ${param.progress ne 'O' ? 'checked' : ''}>
													<label for="chkStatusW">대기</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<input id="chkStatusO" class="progressStatus" name="progressArr" value="O" type="checkbox" checked>
													<label for="chkStatusO">진행중</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<input id="chkStatusD" class="progressStatus" name="progressArr" value="D" type="checkbox" ${param.progress ne 'O' ? 'checked' : ''}>
													<label for="chkStatusD">종료</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">전시채널</th>
									<td class="input" colspan="2">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkChannelAll" name="chnnlNoAll" type="checkbox" ${isAdmin ? 'checked' : 'disabled'}>
													<label for="chkChannelAll">전체</label>
												</span>
											</li>											
											<c:forEach items="${chnnlList}" var="chnnl" varStatus="status">
												<c:if test="${chnnl.chnnlNo ne Const.SITE_CHNNL_ART}">
													<c:set var="chnnlStatus" value="${isAdmin || fn:indexOf(vndrChnnlList, chnnl.chnnlNo) > -1 ? 'checked' : 'disabled'}" />
													<li>
														<span class="ui-chk">
															<input id="chkChannel${status.count}" class="chnnlNo" name="chnnlNoArr" type="checkbox" value="${chnnl.chnnlNo}" ${chnnlStatus}>
															<label for="chkChannel${status.count}">${chnnl.chnnlName}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<c:if test="${!isAdmin}">
										<!-- PO일 경우에만 노출 -->
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
												<c:forEach items="${plndpStatCodeList}" var="code" varStatus="status">
													<li>
														<span class="ui-chk">
															<input id="chkStatus${status.count}" class="plndpStatCode" name="plndpStatCodeArr" type="checkbox" value="${code.codeDtlNo}" ${param.plndpStatCode eq code.codeDtlNo ? 'checked': ''}>
															<label for="chkStatus${status.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</c:if>
								</tr>
								<tr>
									<th scope="row">대상 회원</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkMemberAll" name="memberTypeCodeAll" type="checkbox" value="Y" checked>
													<label for="chkMemberAll">전체</label>
												</span>
											</li>
											<c:forEach items="${memberTypeCodeList}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<input id="chkMember${status.count}" class="memberTypeCode" name="memberTypeCodeArr" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="chkMember${status.count}">${code.codeDtlName}</label>
													</span>
												</li>							
											</c:forEach>
											<li>
												<span class="ui-chk">
													<input id="chkMemberEmp" class="memberTypeCode" name="empYn" type="checkbox" value="Y" checked>
													<label for="chkMemberEmp">임직원</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">대상 상품</th>
									<td class="input">
										<input type="text" class="ui-input" title="대상 상품 입력" placeholder="상품코드 입력" name="mappingPrdtNo">
									</td>
								</tr>
								<tr>
									<th scope="row">전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="radioDispAll" name="dispYn" type="radio" ${empty param.dispYn ? 'checked' : ''} value>
													<label for="radioDispAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioDispY" name="dispYn" value="Y" type="radio" ${param.dispYn eq 'Y' ? 'checked' : ''}>
													<label for="radioDispY">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="radioDispN" name="dispYn" value="N" type="radio">
													<label for="radioDispN">미전시</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">전시유형</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="rdoDispTypeAll" name="dispType" type="radio" value checked>
													<label for="rdoDispTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="rdoDispTypeB" name="dispType" value="B" type="radio">
													<label for="rdoDispTypeB">일반</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input id="rdoDispTypeS" name="dispType" value="S" type="radio">
													<label for="rdoDispTypeS">모아보기</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">디바이스</th>
									<td class="input">
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
									</td>
									<td></td>
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
								</tr>
								<c:if test="${isAdmin}">
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
								</c:if>
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
				<!-- E : search-wrap -->
				<!-- E : 20190329 수정 // 검색영역 전체 수정 -->
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
							<select class="ui-sel" id="pageCount" name="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="btn-group">
							<c:if test="${!isAdmin}">
								<a href="javascript:void(0);" class="btn-sm btn-del" id="removeBtn">선택 삭제</a>
								<a href="javascript:void(0);" class="btn-sm btn-func" id="requestBtn">선택 승인요청</a>
							</c:if>
						</span>
					</div>
					<div class="fr">
						<a href="/promotion/planning-display/detail" class="btn-sm btn-link">등록</a>
					</div>
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
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>