<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>코너 콘텐츠 등록</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">전시 코너 기본정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<form name="searchForm">
			<input type="hidden" id="codeMap" value='${codeMap}' />
			<input type="hidden" name="ctgrNo" value="${ctgrNo}" id="ctgrNo" />
			<input type="hidden" name="dispPageNo" value="${dispPageNo}" id="dispPageNo" />
			<input type="hidden" name="dispTmplNo" value="${corner.dispTmplNo}" id="dispTmplNo" />
			<input type="hidden" name="dispTmplCornerSeq" value="${corner.dispTmplCornerSeq}" id="dispTmplCornerSeq" />
			<input type="hidden" name="dispTmplCornerSetSeq" value="" id="dispTmplCornerSetSeq" />
			<input type="hidden" name="cornerNameDispType" value="${corner.cornerNameDispType}" id="cornerNameDispType" />
			<input type="hidden" name="contTypeCode" value="" />
			</form>
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>전시 코너 기본정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">코너번호</th>
						<td>${corner.dispTmplCornerSeq }</td>
						<th scope="row">코너명</th>
						<td>${corner.cornerName}</td>
					</tr>
					<tr>
						<th scope="row">디바이스</th>
						<td>${deviceTypeName }</td>
						<th scope="row">전시코너명 노출 유형</th>
						<td>${corner.cornerNameDispTypeName}</td>
					</tr>
					<tr>
						<th scope="row">코너설명</th>
						<td colspan="3">${corner.noteText }</td>
					</tr>
					<!-- DESC : 20190227 삭제 // 세트 사용여부, 최대 세트 수 삭제 -->
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : 20190227 수정 // a.tab-link text 수정, 탭 영역별 컨텐츠 수정 -->
			<!-- S : tab-wrap -->
			<div class="tab-wrap multi-line">
				<ul class="tabs">
					<c:if test="${corner.cornerNameDispType ne 'N'}">
					<li class="tab-item"><a href="#tabContent0" class="tab-link">전시코너명</a></li>
					</c:if>
					<c:forEach var="item" begin="1" end="${corner.setCount }" varStatus="status">
						<li class="tab-item"><a href="#tabContent${item }" class="tab-link">전시세트 ${item < 10 ? '0' : '' }${item }</a></li>
					</c:forEach>
				</ul>
				
				<c:if test="${corner.cornerNameDispType ne 'N'}">
				<!-- S:tab_content -->
				<div id="tabContent0" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">전시코너명 (<span>프론트 노출 콘텐츠 개수: 1</span>)</h3>
						</div>
						<div class="fr" data-cont-type-code="${corner.cornerNameDispType}" data-grid-id="cornerNameSheet" data-set-seq="">
							<span class="term-date-wrap">
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal dispStartYmd" title="시작일 선택">
								</span>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal dispEndYmd" title="종료일 선택">
								</span>
								<span class="btn-group">
									<a href="javascript:void(0);" class="btn-sm btn-func apply-select-item">선택항목 일괄적용</a>
								</span>
							</span>
							<a href="javascript:void(0);" class="btn-sm btn-del">선택 삭제</a>
							<a href="javascript:void(0);" class="btn-sm btn-func open-reg-popup">추가</a>
							<a href="javascript:void(0);" class="btn-sm btn-save">저장</a>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="cornerNameSheet" style="width:100%; height:429px;" data-cont-type-code="${corner.cornerNameDispType}">
							ibsheet grid영역(div 삭제 필요)
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				</c:if>
				<c:forEach var="item" begin="1" end="${corner.setCount }" varStatus="status">
				<!-- S:tab_content -->
				<div id="tabContent${item }" class="tab-content">
					<c:forEach var="set" items="${map.get(item) }" varStatus="status">	
					<c:if test="${set.contTypeCode eq '10000' and  set.dispContCount > 0}">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">전시세트명 (<span>프론트 노출 콘텐츠 개수: ${set.dispContCount }</span>)</h3>
						</div>
						<div class="fr" data-cont-type-code="${set.contTypeCode}" data-grid-id="set${set.contTypeCode}${set.dispTmplCornerSetSeq}" data-set-seq="${set.dispTmplCornerSetSeq }">
							<a href="javascript:void(0);" class="btn-sm btn-del">선택 삭제</a>
							<a href="javascript:void(0);" class="btn-sm btn-func open-reg-popup">추가</a>
							<a href="javascript:void(0);" class="btn-sm btn-save">저장</a>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="set${set.contTypeCode}${set.dispTmplCornerSetSeq}" style="width:100%; height:429px;" data-cont-type-code="${set.contTypeCode}" data-set-seq="${set.dispTmplCornerSetSeq }">
							ibsheet grid영역(div 삭제 필요)
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
					</c:if>
					<c:if test="${set.contTypeCode ne '10000' and  set.dispContCount > 0 }">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">${set.contTypeCodeName} (<span>프론트 노출 콘텐츠 개수: ${set.dispContCount }</span>)</h3>
							</div>
							<!-- S : 191217 수정-->
							<c:if test="${set.contTypeCode ne '10006'and set.contTypeCode ne '10007'}">
								<div class="fr" data-cont-type-code="${set.contTypeCode}" data-grid-id="set${set.contTypeCode}${set.dispTmplCornerSetSeq}" data-set-seq="${set.dispTmplCornerSetSeq }">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<span class="date-box">
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal dispStartYmd" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal dispEndYmd" title="종료일 선택">
										</span>
										<span class="btn-group">
											<a href="javascript:void(0);" class="btn-sm btn-func apply-select-item">선택항목 일괄적용</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
									<a href="javascript:void(0);" class="btn-sm btn-del">선택 삭제</a>
									<c:if test="${set.contTypeCode eq '10001' }">
									<span class="gap"></span>
									<a href="javascript:void(0);" class="btn-sm btn-func open-popular-popup">집계보기</a>
									<span class="gap"></span>
									</c:if>
									<a href="javascript:void(0);" class="btn-sm btn-func open-reg-popup">추가</a>
									<a href="javascript:void(0);" class="btn-sm btn-save">저장</a>
								</div>
							</c:if>
							<!-- E : 191217 수정-->
						</div>
						<!-- E : content-header -->
						
						<!-- S : 191217 수정-->
						<c:if test="${set.contTypeCode ne '10006'and set.contTypeCode ne '10007'}">
							<!-- S : ibsheet-wrap -->
							<div class="ibsheet-wrap">
								<div id="set${set.contTypeCode}${set.dispTmplCornerSetSeq}" style="width:100%; height:429px;" data-cont-type-code="${set.contTypeCode}" data-set-seq="${set.dispTmplCornerSetSeq }">
									ibsheet grid영역(div 삭제 필요)
								</div>
							</div>
							<!-- E : ibsheet-wrap -->
						</c:if>
						<!-- E : 191217 수정-->
						
					</c:if>
					</c:forEach>
				</div>
				</c:forEach>
				<!-- E:tab_content -->
			</div>
			<!-- E : tab-wrap -->
			<!-- E : 20190227 수정 // a.tab-link text 수정, 탭 영역별 컨텐츠 수정 -->
		</div>
	</div>
	
	
	<form name="tempForm" style="display:none;"></form>
	<form name="saveForm" style="display:none;"></form>
	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.contents.corner.info.pop.js<%=_DP_REV%>"></script>
</body>
</html>