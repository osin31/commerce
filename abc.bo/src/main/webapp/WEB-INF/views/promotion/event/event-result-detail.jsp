<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<form name="frm">
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">당첨자 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>프로모션</li>
							<li>이벤트 관리</li>
							<li>당첨자 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">당첨자 등록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>당첨자 등록</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">이벤트</span>
						</th>
						<td class="input">
							<c:if test="${empty evEventResult.eventNo and empty eventNo}"><a href="javascript:void(0);" id="eventSearchPop" class="btn-sm btn-link">이벤트 찾기</a></c:if>
							<span id="eventNameArea" class="text">
								<c:choose>
									<c:when test="${not empty evEventResult.eventNo}">
										${evEventResult.eventNo} / ${evEventResult.eventName}
										<input type="hidden" id="eventNo" name="eventNo" value="${evEventResult.eventNo}" />
									</c:when>
									<c:when test="${not empty eventNo}"><!-- 이벤트 등록관리에서 넘어온 경우  -->
										${eventNo} / ${eventName}
										<input type="hidden" id="eventNo" name="eventNo" value="${eventNo}" />
									</c:when>
								</c:choose>
							</span>
							<input type="hidden" id="existEventNo" value="${evEventResult.eventNo}" />
							<input type="hidden" id="eventNo" name="eventNo" value="${evEventResult.eventNo}" />
							<input type="hidden" id="eventTypeCode" name="eventTypeCode" value="${evEventResult.eventTypeCode}" />
							<input type="hidden" id="tmprSaveYn" name="tmprSaveYn" value="N" />
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">내용</span>
						</th>
						<td class="input">
							<!-- S : editor-wrap -->
							<textarea id="pblcContText" name="pblcContText" class="editor-wrap">${evEventResult.pblcContText}</textarea>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">당첨자선정</span>
						</th>
						<td class="input">
							<!-- S : tbl-header -->
							<div class="tbl-controller">
								<div class="fl">
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoResultType01" name="indvdlRgstYn" type="radio" ${evEventResult.indvdlRgstYn eq 'Y' or empty evEventResult.pblcYn ? 'checked' : ''} value="Y">
										<label for="rdoResultType01">개별등록</label>
									</span>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="rdoResultType02" name="indvdlRgstYn" type="radio" ${evEventResult.indvdlRgstYn eq 'N' ? 'checked' : ''} value="N">
										<label for="rdoResultType02">엑셀 일괄 업로드</label>
									</span>
									<br>
									<!-- S : ip-text-box -->
									<span class="ip-text-box indvdlRgstArea benefitArea">
										<input type="text" id="resultAddInput" class="ui-input text-unit20" placeholder="당첨발표명을 입력해주세요." title="당첨발표명 입력">
										<a href="javascript:void(0);" id="resultAdd" class="btn-sm btn-func">추가</a>
									</span>
									<!-- E : ip-text-box -->
								</div>
								<div class="fr indvdlRgstArea benefitExcelArea">
									<div class="file-wrap">
										<ul id="excelArea" class="file-list">
											<li>
												<span class="btn-box">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="file" id="inputFile" name="excelUpload" title="첨부파일 추가">
													<label for="inputFile">당첨자 일괄 업로드</label>
												</span>
											</li>
										</ul>
									</div>
									<a href="javascript:void(0);" id="excelUpload" class="btn-sm btn-func">엑셀 파일 업로드</a>
									<a href="javascript:void(0);" id="excelDownload" class="btn-sm btn-func">엑셀 폼 다운로드</a>
								</div>
							</div>
							<!-- E : tbl-header -->
							<table class="tbl-col indvdlRgstArea benefitArea benefitTableArea">
								<caption>당첨자선정</caption>
								<colgroup>
									<col style="width: 8%">
									<col>
									<col>
									<col style="width: 10%">
									<col style="width: 8%">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">우선순위</th>
										<th scope="col">발표명</th>
										<th scope="col">당첨자수</th>
										<th scope="col">당첨자선정</th>
										<th scope="col"></th>
									</tr>
								</thead>
								<tbody id="resultAddTbody">
									<c:forEach var="row" items="${evEventResultBenefitList}" varStatus="i">
										<tr id="trIdx${i.count}" data-event-rslt-benefit-seq="${row.eventRsltBenefitSeq}" class="resultAddTr">
											<td class="input resultHiddenArea">
												<input type="text" class="ui-input sortSeq" name="evEventResultBenefitArr.sortSeq" title="우선순위 입력" value="${row.sortSeq}">
												<c:forEach var="subRow" items="${row.benefitMemberList}" varStatus="j">
													<input type="hidden" name="evEventResultBenefitMemberArr.eventRsltBenefitSeq" value="${subRow.eventRsltBenefitSeq}" />
													<input type="hidden" name="evEventResultBenefitMemberArr.memberNo" value="${subRow.memberNo}" />
													<input type="hidden" name="evEventResultBenefitMemberArr.evEventJoinMemberSeq" value="${subRow.evEventJoinMemberSeq}" />
												</c:forEach>
											</td>
											<td class="input">
												<input type="text" class="ui-input benefitName" name="evEventResultBenefitArr.benefitName" title="발표명 입력" value="${row.benefitName}">
											</td>
											<td class="input clear-float text-center">
												<span class="text winCount">(${row.winCount})</span>
											</td>
											<td class="input clear-float text-center">
												<a href="javascript:void(0);" class="btn-sm btn-link resultMemberPop">등록</a>
											</td>
											<td class="input clear-float text-center">
												<a href="javascript:void(0);" class="btn-sm btn-del resultDelBtn">삭제</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<table class="tbl-col indvdlRgstArea benefitExcelArea benefitTableExcelArea">
								<caption>당첨자선정</caption>
								<colgroup>
									<col style="width: 8%">
									<col>
									<col>
								</colgroup>
								<thead>
									<tr>
										<th scope="col">우선순위</th>
										<th scope="col">발표명</th>
										<th scope="col">당첨자수</th>
									</tr>
								</thead>
								<tbody id="resultAddExcelTbody">
									<c:forEach var="row" items="${evEventResultBenefitList}" varStatus="i">
										<tr id="trIdx${i.count}" data-event-rslt-benefit-seq="${row.eventRsltBenefitSeq}" class="resultAddExcelTr">
											<td class="input resultHiddenArea">
												<input type="text" class="ui-input sortSeq" name="evEventResultBenefitArr.sortSeq" title="우선순위 입력" value="${row.sortSeq}">
												<c:forEach var="subRow" items="${row.benefitMemberList}" varStatus="j">
													<input type="hidden" name="evEventResultBenefitMemberArr.eventRsltBenefitSeq" value="${subRow.eventRsltBenefitSeq}" />
													<input type="hidden" name="evEventResultBenefitMemberArr.memberNo" value="${subRow.memberNo}" />
													<input type="hidden" name="evEventResultBenefitMemberArr.evEventJoinMemberSeq" value="${subRow.evEventJoinMemberSeq}" />
												</c:forEach>
											</td>
											<td class="input">
												<input type="text" class="ui-input benefitName" name="evEventResultBenefitArr.benefitName" title="발표명 입력" value="${row.benefitName}">
											</td>
											<td class="input clear-float text-center">
												<span class="text winCount">(${row.winCount})</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<th scope="row">당첨자발표일</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<%-- <li>
									<span class="ui-rdo">
										<input id="rdoUse02" name="pblcYn" type="radio" ${evEventResult.pblcYn eq 'N' or empty evEventResult.pblcYn ? 'checked' : ''} value="N">
										<label for="rdoUse02">미발표</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="rdoUse03" name="pblcYn" type="radio" ${evEventResult.pblcYn eq 'Y' ? 'checked' : ''} value="Y">
										<label for="rdoUse03">발표완료</label>
									</span>

									<span class="date-box">
										<fmt:formatDate value="${evEventResult.pblcYmd}" pattern="yyyy.MM.dd" var="eventResultYmd"/>
										<input type="text" name="pblcYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" value="${eventResultYmd}">
									</span>
								</li> --%>
								<li>
									<span id="przwrPblcTodoYmd" class="text">
										<fmt:parseDate value="${evEventResult.przwrPblcTodoYmd}" pattern="yyyy-MM-dd" var="przwrPblcTodoYmd"/>
										<fmt:formatDate value="${przwrPblcTodoYmd}" pattern="yyyy.MM.dd" />
									</span>
									<input type="hidden" name="pblcYn" value="N">
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-row -->
			<c:if test="${not empty evEventResult.eventNo}">
				<table class="tbl-row">
					<caption>이벤트 등록 작성 정보</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">작성자</th>
							<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${evEventResult.rgsterNo}">${evEventResult.rgsterInfo}</a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${evEventResult.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${evEventResult.moderNo}">${evEventResult.moderInfo}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${evEventResult.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<!-- <div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div> -->
				<div class="fr">
					<a href="/promotion/event/result" class="btn-normal btn-link">목록</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<!-- <a href="/promotion/event/result" class="btn-lg btn-del">취소</a> -->
				<!-- <a href="javascript:void(0);" class="btn-lg btn-save">임시저장</a> -->
				<a href="javascript:void(0);" id="save-result" class="btn-lg btn-save">등록</a>
			</div>
			<!-- E : content-bottom -->
		</div>
	</div>
	<!-- E : container -->
</form>

<template id="excelAddTemplate">
	<li class="addLi"><span class="subject"></span>
		<button type="button" class="btn-file-del">
			<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
		</button>
	</li>
</template>

<script type="text/x-template" id="resultAddTemplate">
	<tr class="resultAddTr">
		<td class="input resultHiddenArea">
			<input type="text" name="evEventResultBenefitArr.sortSeq" class="ui-input sortSeq" title="우선순위 입력">
		</td>
		<td class="input">
			<input type="text" name="evEventResultBenefitArr.benefitName" class="ui-input benefitName" title="발표명 입력">
		</td>
		<td class="input clear-float text-center">
			<span class="text winCount">(0)</span>
		</td>
		<td class="input clear-float text-center">
			<a href="javascript:void(0);" class="btn-sm btn-link resultMemberPop">등록</a>
		</td>
		<td class="input clear-float text-center">
			<a href="javascript:void(0);" class="btn-sm btn-del resultDelBtn">삭제</a>
		</td>
	</tr>
</script>

<script type="text/x-template" id="resultAddExcelTemplate">
	<tr class="resultAddExcelTr">
		<td class="input resultHiddenArea">
			<input type="text" name="evEventResultBenefitArr.sortSeq" class="ui-input sortSeq" title="우선순위 입력">
		</td>
		<td class="input">
			<input type="text" name="evEventResultBenefitArr.benefitName" class="ui-input benefitName" title="발표명 입력">
		</td>
		<td class="input clear-float text-center">
			<span class="text winCount">(0)</span>
		</td>
	</tr>
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.event.result.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>