<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.promotion.planning.display.codeCombo = ${codeCombo};
});
</script>
	
		<!-- S : container -->
		<div class="container">
			<form id="saveForm" name="saveForm">
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
			
			<input type="hidden" name="originalChnnlNo" value="${planningDisplay.chnnlNo}">
			<!-- 미리보기 URL -->
			<input type="hidden" name="plndpNo" value="${planningDisplay.plndpNo}">
			<input type="hidden" name="plndpStatCode" value="${planningDisplay.plndpStatCode}">
			<input type="hidden" name="plndpStatName">
			<input type="hidden" name="pageType" value="${param.pageType}">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">기획전 정보관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>기획전 관리</li>
								<li>기획전 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->
				
				<c:forEach items="${plndpStatCodeList}" var="code">
					<c:if test="${planningDisplay.plndpStatCode eq code.codeDtlNo}">
						<c:set var="plndpStatCodeLabel" value="${code.codeDtlName}" />
					</c:if>
				</c:forEach>
				
				<c:if test="${not empty param.plndpNo and (!isAdmin or (isApproval and plndpStatCodeLabel ne '임시저장'))}">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">							
							<h3 class="content-title">승인상태 : ${plndpStatCodeLabel}</h3>
						</div>
						<div class="fr">
							<c:if test="${isAdmin and plndpStatCodeLabel ne '승인반려'}">
								<a href="#" class="btn-sm btn-func" id="approveBtn">승인완료</a>
								<a href="#" class="btn-sm btn-del" id="returnBtn">승인반려</a>
							</c:if>
						</div>
					</div>
					<!-- E : content-header -->
					
					<!-- S : 승인반려일 경우, 노출되는 영역 -->
					<!-- S : tbl-row -->
					<table class="tbl-row" id="returnArea" ${plndpStatCodeLabel ne '승인반려' ? 'style="display: none;"' : ''}>
						<caption>승인반려</caption>
						<colgroup>
							<col style="width: 150px">
							<col>
							<col style="width: 120px">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">반려사유 입력</th>
								<td class="input">
									<textarea class="ui-textarea" title="반려사유 입력" name="returnRsnText" id="returnRsnText" ${plndpStatCodeLabel eq '승인반려' ? 'disabled' : ''}>${planningDisplay.returnRsnText}</textarea>
								</td>
								<td class="input">
									<c:if test="${isAdmin and plndpStatCodeLabel ne '승인반려'}">
										<a href="#" class="btn-sm btn-save" id="returnSave">저장</a>
										<a href="#" class="btn-sm btn-del" id="returnCancel">취소</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
					<!-- E : 승인반려일 경우, 노출되는 영역 -->
				</c:if>

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">기획전 기본정보</h3>
					</div>
					<c:if test="${not empty param.plndpNo}">
						<div class="fr">
							<a href="/promotion/planning-display/product/manage?plndpNo=${param.plndpNo}" class="btn-sm btn-link">상품관리로 이동</a>
						</div>
					</c:if>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>기획전 기본정보</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span class="th-required">기획전 명</span></th>
							<td class="input">
								<!-- TODO : 기획 fix 후 입력 제한 글자수 변경 -->
								<input type="text" class="ui-input" title="기획전 명 입력" placeholder="50자 이내로 입력" name="plndpName" id="plndpName" value="${planningDisplay.plndpName}">
							</td>
							<th scope="row">기획전 ID</th>
							<td>${planningDisplay.plndpNo}</td>						
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">기획전 기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" name="plndpStartD" value="<fmt:formatDate value="${planningDisplay.plndpStartDtm}" pattern="yyyy.MM.dd"/>">
									</span>
									<select class="ui-sel" title="시작시 선택" name="plndpStartH">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(planningDisplay.plndpStartDtm, 12, 13) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(planningDisplay.plndpStartDtm, 11, 13) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="시작분 선택" name="plndpStartM">
										<c:forEach begin="0" end="59" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(planningDisplay.plndpStartDtm, 15, 16) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(planningDisplay.plndpStartDtm, 14, 16) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" name="plndpEndD" value="<fmt:formatDate value="${planningDisplay.plndpEndDtm}" pattern="yyyy.MM.dd"/>">
									</span>
									<select class="ui-sel" title="종료시 선택" name="plndpEndH">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(planningDisplay.plndpEndDtm, 12, 13) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(planningDisplay.plndpEndDtm, 11, 13) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="종료분 선택" name="plndpEndM">
										<c:forEach begin="0" end="59" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(planningDisplay.plndpEndDtm, 15, 16) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(planningDisplay.plndpEndDtm, 14, 16) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<c:if test="${isAdmin}">
							<tr>
								<th scope="row"><span class="th-required">A-RT 전시 여부</span></th>
								<td class="input" colspan="3">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="radioArtDispY" name="artDispYn" value="Y" type="radio" ${not empty planningDisplay and planningDisplay.artDispYn eq 'Y' or empty planningDisplay.artDispYn ? 'checked' : ''}>
												<label for="radioArtDispY">전시</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="radioArtDispN" name="artDispYn" value="N" type="radio" ${not empty planningDisplay and planningDisplay.artDispYn eq 'N' ? 'checked' : ''}>
												<label for="radioArtDispN">미전시</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
						</c:if>
						<c:if test="${!isAdmin}">
							<input id="radioArtDispN" name="artDispYn" value="N" type="hidden">
						</c:if>
						<tr>
							<!-- S : 20190329 수정 // 사이트구분 > 채널구분 수정 -->
							<th scope="row"><span class="th-required">전시채널</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach items="${chnnlList}" var="chnnl" varStatus="status">
										<c:if test="${chnnl.chnnlNo ne Const.SITE_CHNNL_ART}">
											<c:choose>
												<c:when test="${empty vndrChnnlList}">
													<c:choose>
														<c:when test="${empty planningDisplay.chnnlNo}">
															<c:set var="chnnlStatus" value="${status.count == 2 ? 'checked' : ''}" />
														</c:when>
														<c:otherwise>
															<c:set var="chnnlStatus" value="${planningDisplay.chnnlNo eq chnnl.chnnlNo ? 'checked' : ''}" />
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>					
													<c:set var="chnnlStatus" value="${fn:indexOf(vndrChnnlList, chnnl.chnnlNo) > -1 ? (planningDisplay.chnnlNo eq chnnl.chnnlNo || fn:indexOf(vndrChnnlList, chnnl.chnnlNo) == 0 ? 'checked' : '') : 'disabled'}" />
												</c:otherwise>
											</c:choose>
											<li>
												<span class="ui-rdo">
													<input id="rdoChannel${status.count}" name="chnnlNo" type="radio" value="${chnnl.chnnlNo}" ${chnnlStatus}>
													<label for="rdoChannel${status.count}">${chnnl.chnnlName}</label>
												</span>
											</li>
										</c:if>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<!-- E : 20190329 수정 // 사이트구분 > 채널구분 수정 -->
							<th scope="row"><span class="th-required">전시여부</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoDisplayY" name="dispYn" type="radio" value="Y" ${(empty planningDisplay && isAdmin) || planningDisplay.dispYn eq 'Y' ? 'checked' : ''} ${isAdmin ? '' : ' disabled'}>
											<label for="rdoDisplayY">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoDisplayN" name="dispYn" type="radio" value="N" ${!isAdmin || planningDisplay.dispYn eq 'N' ? 'checked' : ''} ${isAdmin ? '' : ' disabled'}>
											<label for="rdoDisplayN">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<c:if test="${isAdmin}">
						<tr>
							<th scope="row">
								<span class="th-required">대상회원</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<!-- <li>
										<span class="ui-chk">
											<input id="chkMemberAll" name="memberTypeCodeAll" type="checkbox">
											<label for="chkMemberAll">전체</label>
										</span>
									</li> -->
									<c:forEach items="${memberTypeCodeList}" var="code" varStatus="status">
										<c:set var="chk" value="" />
										<c:forEach items="${prPlanningDisplayApplyGradeList}" var="type">
											<c:if test="${type.memberTypeCode eq code.codeDtlNo}">
												<c:set var="chk" value="checked" />
											</c:if>
											<c:if test="${type.memberTypeCode eq '10001'}">
												<c:set var="mbshpYn" value="Y" />
											</c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<input id="chkMember${status.count}" name="memberTypeCodeArr" value="${code.codeDtlNo}" type="checkbox" ${chk}>
												<label for="chkMember${status.count}">${code.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
									<li>
										<span class="ui-chk">
											<input id="chkMemberEmp" name="empYn" type="checkbox" value="Y" ${planningDisplay.empYn eq 'Y' ? 'checked' : ''}>
											<label for="chkMemberEmp">임직원</label>
										</span>
									</li>									
								</ul>
								<!-- E : ip-box-list -->
								<!-- S : td-text-list -->
								<ul class="td-text-list">
									<li>* 일반 고객(온라인 회원, 통합멤버십회원, 비회원)  또는 임직원 단독으로만 체크 가능합니다.</li>
									<li>* 일반 고객만 체크 시 고객 화면에만 노출 되며, 임직원 화면에는 노출 되지 않습니다.</li>
									<li>* 임직원만 체크 시 임직원 전용으로 진행 되며, 고객 화면에는 노출 되지 않습니다.</li>
								</ul>
								<!-- E : td-text-list -->
								
							</td>
						</tr>
						</c:if>
						<tr>
							<th scope="row">
								<span class="th-required">디바이스</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="chkDeviceAll" name="deviceCodeAll" type="checkbox">
											<label for="chkDeviceAll">전체</label>
										</span>
									</li>
									<c:forEach items="${deviceCodeList}" var="code" varStatus="status">
										<c:set var="chk" value="" />
										<c:forEach items="${prPlanningDisplayApplyDeviceList}" var="device">
											<c:if test="${device.deviceCode eq code.codeDtlNo}">
												<c:set var="chk" value="checked" />
											</c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<input id="chkDevice${status.count}" name="deviceCodeArr" value="${code.codeDtlNo}" type="checkbox" ${empty param.plndpNo ? 'checked' : chk}>
												<label for="chkDevice${status.count}">${code.codeDtlName}</label>
											</span>
										</li>								
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row"><span class="th-required">사용여부</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="rdoUseY" name="useYn" type="radio" value="Y" ${empty planningDisplay || planningDisplay.useYn eq 'Y' ? 'checked' : ''} ${isAdmin ? '' : ' disabled'}>
											<label for="rdoUseY">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="rdoUseN" name="useYn" type="radio" value="N" ${planningDisplay.useYn eq 'N' ? 'checked' : ''} ${isAdmin ? '' : ' disabled'}>
											<label for="rdoUseN">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 20190404 추가 // 링크 URL 위치 이동 -->
						<c:if test="${not empty planningDisplay.plndpNo and isAdmin}">
							<tr id="linkUrlArea">
								<th scope="row">링크 URL</th>
								<td colspan="3" class="input">
									<ul class="td-text-list">
										<li>PC : <span id="foLinkUrl"></span></li>
										<li>Mobile : <span id="moLinkUrl"></span></li>
									</ul>
								</td>
							</tr>
						</c:if>
						<!-- E : 20190404 추가 // 링크 URL 위치 이동 -->
						<tr>
							<th scope="row">
								<span class="th-required">검색키워드</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="검색키워드 입력" name="srchWordText" value="${planningDisplay.srchWordText}" maxlength="50">
								<ul class="td-text-list">
									<li>* 키워드를 쉼표 (,)로 구분하여 주세요</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">기획전 내용</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="내용 입력" name="plndpContText" value="${planningDisplay.plndpContText}" maxlength="250">
							</td>
						</tr>
						<!-- S : 20190329 추가 -->
						<c:if test="${isAdmin}">
							<tr>
								<th scope="row">기획전 유형</th>
								<td class="input" colspan="3">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<c:forEach items="${plndpTypeCodeList}" var="code" varStatus="status">
											<c:set var="chk" value=""></c:set>
											<c:forEach items="${prPlanningDisplayTypeList}" var="type">
												<c:if test="${code.codeDtlNo eq type.plndpTypeCode}">
													<c:set var="chk" value="checked"></c:set>
												</c:if>
											</c:forEach>
											<li>
												<span class="ui-chk">
													<input id="chkType${status.count}" type="checkbox" name="plndpTypeCodeArr" value="${code.codeDtlNo}" ${chk}>
													<label for="chkType${status.count}">${code.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty planningDisplay.plndpNo}">
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:abc.adminDetailPopup('${planningDisplay.rgsterNo}');">${UtilsMasking.adminName(planningDisplay.rgsterId, planningDisplay.rgsterName)}</a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${planningDisplay.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:abc.adminDetailPopup('${planningDisplay.moderNo}');">${UtilsMasking.adminName(planningDisplay.moderId, planningDisplay.moderName)}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${planningDisplay.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</c:if>
						<!-- E : 20190329 추가 -->
					</tbody>
				</table>
				<!-- E : tbl-row -->
				
				<!-- S : 20190329 수정 -->
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">기획전 배너이미지 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>기획전 배너이미지 정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span>기획전 배너 이미지<br /> 등록 (PC)<span class="th-required dispYn-control" <c:if test="${planningDisplay.dispYn eq Const.BOOLEAN_FALSE}">style='display:none;'</c:if> ></span></span>
								<div>권장사이즈 388*500 (최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<input type="hidden" name="pcBannerImageName" value="${planningDisplay.pcBannerImageName}">
												<input type="hidden" name="pcBannerImagePathText" value="${planningDisplay.pcBannerImagePathText}">
												<input type="hidden" name="pcBannerImageUrl" value="${planningDisplay.pcBannerImageUrl}">
												<input type="file" id="pcImageFile" name="pcImageFile" title="첨부파일 추가">
												<label for="pcImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${planningDisplay.pcBannerImageName}</a>
											<button type="button" class="btn-file-del plndp-banner-img" ${empty planningDisplay.pcBannerImageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="pcBannerImageAltrnText" value="${planningDisplay.pcBannerImageAltrnText}" maxlength="50">
									</div>
									<div class="img-wrap">
										<c:if test="${planningDisplay.pcBannerImageUrl != null}">
											<img alt="" src="${planningDisplay.pcBannerImageUrl}">
										</c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span>기획전 배너 이미지 <br />등록 (Mobile)<span class="th-required dispYn-control" <c:if test="${planningDisplay.dispYn eq Const.BOOLEAN_FALSE}">style='display:none;'</c:if>></span></span>
								<div>권장사이즈 660*850 (최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<input type="hidden" name="mobileBannerImageName" value="${planningDisplay.mobileBannerImageName}">
												<input type="hidden" name="mobileBannerImagePathText" value="${planningDisplay.mobileBannerImagePathText}">
												<input type="hidden" name="mobileBannerImageUrl" value="${planningDisplay.mobileBannerImageUrl}">
												<input type="file" id="mobileImageFile" name="mobileImageFile" title="첨부파일 추가">
												<label for="mobileImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${planningDisplay.mobileBannerImageName}</a>
											<button type="button" class="btn-file-del plndp-banner-img" ${empty planningDisplay.mobileBannerImageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="mobileBannerImageAltrnText" value="${planningDisplay.mobileBannerImageAltrnText}" maxlength="50">
									</div>
									<div class="img-wrap">
										 <c:if test="${planningDisplay.mobileBannerImageUrl != null}">
											<img alt="" src="${planningDisplay.mobileBannerImageUrl}">
										</c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">기획전 상세설명 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>기획전 상세설명 정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 130px;">
					</colgroup>
					<tbody>
						<tr ${!isAdmin ? 'style="display: none;"' : ''}>
							<th scope="row">3D 이미지 등록</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<span class="text">3D 이미지명 : </span>
									<input type="text" class="ui-input" placeholder="동영상명" title="3D 이미지명 입력" name="threedImageName" value="${planningDisplay.threedImageName}" maxlength="50">

									<span class="text">3D 코드 입력 : </span>
									<input type="text" class="ui-input url" title="3D 경로입력" name="threedImagePathText" value="${planningDisplay.threedImagePathText}" maxlength="100">
								</span>
								<!-- E : ip-text-box -->
							</td>
							<td class="input vertical-middle">
								<input type="number" min="0" class="ui-input text-center" placeholder="노출순서" title="노출순서 입력" name="threedImageDispSeq" value="${empty planningDisplay.threedImageDispSeq ? (!isAdmin ? '0' : '1') : planningDisplay.threedImageDispSeq}">
							</td>							
						</tr>
						<tr>
							<th scope="row" rowspan="3">동영상 등록</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
									<span class="ui-rdo">
										<input id="radioEffectImgU" name="movieUploadYn" value="U" type="radio" ${empty planningDisplay || planningDisplay.movieUploadYn == 'U' ? 'checked' : ''}>
										<label for="radioEffectImgU">URL 입력 </label>
									</span>
									</li>
									<li>
									<span class="ui-rdo">
										<input id="radioEffectImgD" name="movieUploadYn" value="D" type="radio" ${planningDisplay.movieUploadYn == 'D' ? 'checked' : ''}>
										<label for="radioEffectImgD">직접 업로드</label>
									</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td class="input vertical-middle" rowspan="3">
								<input type="number" min="0" class="ui-input text-center" placeholder="노출순서" title="노출순서 입력" name="movieDispSeq" value="${empty planningDisplay.movieDispSeq ? (!isAdmin ? '1' : '2') : planningDisplay.movieDispSeq}">
							</td>							
						</tr>
						<tr>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<span class="text">동영상 명 : </span>
									<input type="text" class="ui-input" name="dispMovieName" placeholder="동영상명" title="브랜드 동영상 이름 입력" value="${planningDisplay.dispMovieName}" maxlength="50">

									<!-- S :브랜드 동영상 정보 > URL 입력 선택시 노출되는 영역 -->
									<span class="text upload-type-url" ${empty planningDisplay || planningDisplay.movieUploadYn == 'U' ? '' : 'style="display: none;"'}>동영상 경로 : </span>
									<input type="text" class="ui-input url upload-type-url" name="movieUrl" placeholder="URL 입력 예시 : https://youtu.be/AnCFR0yIKfw" title="브랜드 동영상 URL 입력" value="${planningDisplay.movieUrl}" ${empty planningDisplay || planningDisplay.movieUploadYn == 'U' ? '' : 'style="display: none;"'} maxlength="200">
									<!-- E :브랜드 동영상 정보 > URL 입력 선택시 노출되는 영역 -->

									<!-- S :브랜드 동영상 정보 > 직접 업로드 선택시 노출되는 영역 -->									
									<span class="text upload-type-direct" ${planningDisplay.movieUploadYn == 'D' ? '' : 'style="display: none;"'}>동영상 파일등록 : </span>
									<div class="file-wrap inline upload-type-direct" ${planningDisplay.movieUploadYn == 'D' ? '' : 'style="display: none;"'}>
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<input type="hidden" name="movieName" value="${planningDisplay.movieName}">
													<input type="hidden" name="moviePathText" value="${planningDisplay.moviePathText}">
													<input type="file" id="movieFile" name="movieFile" title="첨부파일 추가">
													<label for="movieFile">찾아보기</label>
												</span>
											</li>
											<li>
												<span class="subject movie-name-info">${planningDisplay.movieName}</span>
												<button type="button" class="btn-file-del plndp-movie-info" ${empty planningDisplay.movieName ? 'style="display: none;"' : ''}>
													<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</li>
										</ul>
									</div>
									<!-- E :브랜드 동영상 정보 > 직접 업로드 선택시 노출되는 영역 -->
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
						<%--
							<td class="input">
								<div class="file-wrap">
									<ul class="file-list">
										<li>
										<span class="btn-box">
											<input type="hidden" name="movieImageName" value="${planningDisplay.movieImageName}">
											<input type="hidden" name="movieImagePathText" value="${planningDisplay.movieImagePathText}">
											<input type="hidden" name="movieImageUrl" value="${planningDisplay.movieImageUrl}">
											<input type="file" id="movieImageFile" name="movieImageFile" title="첨부파일 추가">
											<label for="movieImageFile">찾아보기</label>
										</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${planningDisplay.movieImageName}</a>
											<button type="button" class="btn-file-del plndp-banner-img" ${empty planningDisplay.movieImageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" name="movieImageAltrnText" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${planningDisplay.movieImageAltrnText}" maxlength="50">
									</div>
									<div class="img-wrap">
										<c:if test="${planningDisplay.movieImageUrl ne null}">
											<img alt="${planningDisplay.movieImageAltrnText}" src="${planningDisplay.movieImageUrl}">
										</c:if>
									</div>
								</div>

								<ul class="td-text-list">
									<li>최대 10MB까지 등록가능, 파일 유형 : jpg, jpeg, png, gif, bmp</li>
								</ul>
							</td>
							 --%>
						</tr>
						<tr>
							<th scope="row">PC 상세<br/> 1200*가변<span class="th-required dispYn-control" <c:if test="${planningDisplay.dispYn eq Const.BOOLEAN_FALSE}">style='display:none;'</c:if>></span></th>
							<td class="input">
								<!-- S : editor-wrap -->
								<div class="editor-wrap" id="pcDtlDescTextArea">
									<textarea id="pcDtlDescText" name="pcDtlDescText" ${(plndpStatCodeLabel eq '승인완료' or plndpStatCodeLabel eq '승인요청') and !isAdmin ? 'readonly' : ''}>${planningDisplay.pcDtlDescText}</textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
							<td class="input vertical-middle" rowspan="2">
								<input type="number" min="0" class="ui-input text-center" placeholder="노출순서" title="노출순서 입력" name="dtlDescDispSeq" value="${empty planningDisplay.dtlDescDispSeq ? (!isAdmin ? '2' : '3') : planningDisplay.dtlDescDispSeq}">
							</td>
						</tr>
						<tr>
							<th scope="row">MOBILE 상세<br />660*가변<span class="th-required dispYn-control" <c:if test="${planningDisplay.dispYn eq Const.BOOLEAN_FALSE}">style='display:none;'</c:if>></span></th>
							<td class="input">
								<!-- S : editor-wrap -->
								<div class="editor-wrap">
									<textarea id="mobileDtlDescText" name="mobileDtlDescText" ${(plndpStatCodeLabel eq '승인완료' or plndpStatCodeLabel eq '승인요청') and !isAdmin ? 'readonly' : ''}>${planningDisplay.mobileDtlDescText}</textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
				<!-- E : 20190329 수정 -->
				
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<c:if test="${param.pageType eq 'A'}">
							<a href="javascript:void(0);" class="btn-normal btn-del btn-preview pc-type" style="display: none;">PC 미리보기</a>
							<a href="javascript:void(0);" class="btn-normal btn-del btn-preview mobile-type" style="display: none;">모바일 미리보기</a>
						</c:if>
						<c:if test="${empty param.pageType &&
									((!isAdmin && (planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'))
									or (isAdmin and planningDisplay.vndrGbnType eq 'C'))}">
							<a href="#" class="btn-normal btn-del" id="deletePlndpBtn">삭제</a>
						</c:if>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-normal btn-link" id="listBtn">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->
				
				<!-- S : content-bottom -->
				<div class="content-bottom">
					<c:if test="${isAdmin and (empty planningDisplay or planningDisplay.plndpStatCode eq '10003')}">
						<a href="javascript:void(0);" class="btn-lg btn-save" id="saveBtn">저장</a>
					</c:if>
					<c:if test="${!isAdmin}">
						<c:if test="${empty planningDisplay}">
							<a href="javascript:void(0);" class="btn-lg btn-save btn-save-vndr save-tmprly">저장</a>						
						</c:if>
						<c:if test="${planningDisplay.plndpStatCode eq '10000' or planningDisplay.plndpStatCode eq '10002'}">
							<a href="javascript:void(0);" class="btn-lg btn-save btn-save-vndr save-tmprly">임시저장</a>
							<a href="javascript:void(0);" class="btn-lg btn-func btn-save-vndr rqst-apprvl">승인요청</a>
						</c:if>
					</c:if>
				</div>
				<!-- E : content-bottom -->
			</div>
			</form>
		</div>
		<!-- E : container -->
		<form name="cornerSaveForm" id="cornerSaveForm"></form>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.detail.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.detail.approval.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>