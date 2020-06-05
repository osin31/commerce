<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.promotion.planning.display.manage.codeCombo = ${codeCombo};
	abc.biz.promotion.planning.display.manage.chnnlCombo = ${chnnlCombo};
});
</script>
	
		<!-- S : container -->
		<form id="saveForm" name="saveForm">
			<input type="hidden" name="plndpStatCode" value="${planningDisplay.plndpStatCode }">
			<input type="hidden" name="plndpNo" value="${planningDisplay.plndpNo }">
			<input type="hidden" name="vndrGbnType" value="${planningDisplay.vndrGbnType }">
			<input type="hidden" name="vndrNo" value="${planningDisplay.vndrNo}">
			<input type="hidden" name="vndrName" value="${planningDisplay.vndrName}">
			<input type="hidden" name="pageType" value="PM">
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">기획전 상품관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>기획전 승인 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">기획전 정보</h3>
					</div>
					<div class="fr">
						<a href="/promotion/planning-display/detail?plndpNo=${param.plndpNo}" class="btn-sm btn-link">정보관리로 이동</a>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>기획전 상품관리</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span class="th-required">기획전 명</span></th>
							<td class="input">
								<input type="text" class="ui-input" title="기획전 명 입력" name="plndpName" value="${planningDisplay.plndpName}">
							</td>
							<th scope="row">기획전 ID</th>
							<td>${planningDisplay.plndpNo}</td>
						</tr>
						<tr>
							<th scope="row"><span class="th-required">전시유형</span></th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoDisplayTypeA" name="dispType" value="B" type="radio" ${empty planningDisplay || planningDisplay.dispType == 'B' ? 'checked' : ''}>
											<label for="rdoDisplayTypeA">일반</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoDisplayTypeS" name="dispType" value="S" type="radio" ${planningDisplay.dispType eq 'S' ? 'checked' : ''} ${planningDisplay.chnnlNo eq '10003' ? ' disabled' : ''}>
											<label for="rdoDisplayTypeS">모아보기</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="th-required">기획전 소제목 적용</span></th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoExhibitionY" name="cornerApplyYn" value="Y" type="radio" ${planningDisplay.cornerApplyYn == 'Y' ? 'checked' : ''}>
											<label for="rdoExhibitionY">적용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoExhibitionN" name="cornerApplyYn" value="N" type="radio" ${empty planningDisplay || planningDisplay.cornerApplyYn == 'N' ? 'checked' : ''}>
											<label for="rdoExhibitionN">적용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr class="corner-disp-type-area" ${planningDisplay.cornerApplyYn eq 'Y' ? '' : 'style="display: none;"'}>
							<th scope="row"><span class="th-required">소제목 탭전시 여부</span></th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoCornerTypeA" name="cornerDispType" value="A" type="radio" ${empty planningDisplay || planningDisplay.cornerDispType == 'A' ? 'checked' : ''}>
											<label for="rdoCornerTypeA">전체노출</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoCornerTypeP" name="cornerDispType" value="P" type="radio" ${planningDisplay.cornerDispType == 'P' ? 'checked' : ''}>
											<label for="rdoCornerTypeP">PC만 노출</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoCornerTypeM" name="cornerDispType" value="M" type="radio" ${planningDisplay.cornerDispType == 'M' ? 'checked' : ''}>
											<label for="rdoCornerTypeM">Mobile만 노출</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoCornerTypeN" name="cornerDispType" value="N" type="radio" ${planningDisplay.cornerDispType == 'N' ? 'checked' : ''}>
											<label for="rdoCornerTypeN">노출안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${planningDisplay.moderNo}');">${UtilsMasking.adminName(planningDisplay.moderId, planningDisplay.moderName)}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${planningDisplay.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header product-sheet-area" ${planningDisplay.cornerApplyYn eq 'Y' ? 'style="display: none;"' : ''}>
					<div class="fl">
						<h3 class="content-title">기획전 상품 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller product-sheet-area" ${planningDisplay.cornerApplyYn eq 'Y' ? 'style="display: none;"' : ''}>
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="productPageCount">목록개수</label>
							<select class="ui-sel pageCount productType" id="productPageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="btn-group">
							<c:if test="${planningDisplay.plndpStatCode eq '10003' or (!isAdmin and (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'))}">
								<a href="javascript:void(0);" class="btn-sm btn-del" id="productRemoveBtn">선택삭제</a>
								<a href="javascript:void(0);" class="btn-sm btn-save" id="productSortBtn">순서저장</a>
							</c:if>
						</span>
					</div>
					<div class="fr">
						<c:if test="${planningDisplay.plndpStatCode eq '10003' or (!isAdmin and (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'))}">
							<a href="#" class="btn-sm btn-func" id="productBtn">상품검색추가</a>
						</c:if>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap product-sheet-area" ${planningDisplay.cornerApplyYn eq 'Y' ? 'style="display: none;"' : ''}>
					<div id="productSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
				
				<!-- S : content-header -->
				<div class="content-header corner-sheet-area" ${planningDisplay.cornerApplyYn eq 'N' ? 'style="display: none;"' : ''}>
					<div class="fl">
						<h3 class="content-title">기획전 소제목 정보</h3>
						<input type="hidden" name="plndpCornerSeq">
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller corner-sheet-area" ${planningDisplay.cornerApplyYn eq 'N' ? 'style="display: none;"' : ''}>
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="cornerPageCount">목록개수</label>
							<select class="ui-sel pageCount cornerType" id="cornerPageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="btn-group">
							<a href="javascript:void(0);" class="btn-sm btn-del" id="cornerRemoveBtn">선택삭제</a>
							<a href="javascript:void(0);" class="btn-sm btn-save" id="cornerSortBtn">순서저장</a>
						</span>
					</div>
					<div class="fr">
						<c:if test="${planningDisplay.plndpStatCode eq '10003' or (!isAdmin and (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'))}">
							<a href="#" class="btn-sm btn-func" id="cornerBtn">소제목 추가</a>
						</c:if>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap corner-sheet-area" ${planningDisplay.cornerApplyYn eq 'N' ? 'style="display: none;"' : ''}>
					<div id="cornerSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<c:if test="${((!isAdmin && (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'))
									or (isAdmin and planningDisplay.vndrGbnType eq 'C'))}">
							<a href="javascript:void(0);" class="btn-normal btn-del" id="deletePlndpBtn">삭제</a>
						</c:if>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-normal btn-link" id="listBtn">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<!-- S : content-bottom -->
				<div class="content-bottom">
					<c:if test="${planningDisplay.plndpStatCode eq '10003'}">
						<a href="javascript:void(0);" class="btn-lg btn-save corner-sheet-area manage-save" id="saveCornerBtn" ${planningDisplay.cornerApplyYn eq 'N' ? 'style="display: none;"' : ''}>저장</a>
						<a href="javascript:void(0);" class="btn-lg btn-save product-sheet-area manage-save" id="saveProductBtn" ${planningDisplay.cornerApplyYn eq 'Y' ? 'style="display: none;"' : ''}>저장</a>
					</c:if>
					<c:if test="${!isAdmin and (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002') }">
						<a href="javascript:void(0);" class="btn-lg btn-save btn-save-vndr save-tmprly">임시저장</a>
						<a href="javascript:void(0);" class="btn-lg btn-func btn-save-vndr rqst-apprvl">승인요청</a>
					</c:if>
				</div>
				<!-- E : content-bottom -->
				
				<div class="content-bottom">
					
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		</form>
		<!-- E : container -->
		
		<form name="cornerSaveForm" id="cornerSaveForm"></form>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.product.manage.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>