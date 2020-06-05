<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">이벤트 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
<!-- 						<button type="button" class="btn-favorites"> -->
<!-- 							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button> -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>이벤트 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">이벤트 등록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : 이벤트관리 > 상세일 경우 -->
				<!-- S : tbl-row -->
				<form id="saveForm" name="saveForm">
				<table class="tbl-row">
					<caption>이벤트 상세</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<c:if test="${not empty event}">
							<tr>
								<th scope="row">이벤트ID</th>
								<td colspan="3">${event.ctlgEventNo}</td>
								<input type="hidden" name="ctlgEventNo" value="${event.ctlgEventNo}">
							</tr>
						</c:if>
						<tr>
							<th scope="row">
								<span class="th-required">이벤트명</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="이벤트명 입력" name="eventName" value="${event.eventName}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">적용매장</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="storeCommonYn" class="radioStoreY" id="radioStoreY" value="Y" ${empty event || event.storeCommonYn eq 'Y' ? 'checked' : ''}>
											<label for="radioStoreY">공통</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="storeCommonYn" class="radioStoreN" id="radioStoreN" value="N" ${event.storeCommonYn eq 'N' ? 'checked' : ''}>
											<label for="radioStoreN">매장찾기</label>
										</span>
										<a href="#" class="btn-sm btn-link ${event.storeCommonYn eq 'N' ? '' : 'disabled'}" id="searchStoreBtn">오프라인매장 조회</a>
									</li>
								</ul>
								<!-- E : ip-box-list -->

								<!-- S : 적용매장 > 매장찾기 선택시 노출 -->
								<!-- S : item-list -->
								<ul class="item-list" id="storeList">
									<c:forEach items="${storeList}" var="store">
										<li>
											<input type="hidden" name="storeNoArr" value="${store.storeNo}">
											<span class="subject">${store.storeName}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">매장 삭제</span></span>
											</button>
										</li>
									</c:forEach>									
								</ul>
								<!-- E : item-list -->
								<!-- E : 적용매장 > 매장찾기 선택시 노출 -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">이벤트 기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" name="eventStartD" value="<fmt:formatDate value="${event.eventStartDtm}" pattern="yyyy.MM.dd"/>">
									</span>
									<select class="ui-sel" title="시작시 선택" name="eventStartH">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(event.eventStartDtm, 11, 13) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(event.eventStartDtm, 11, 13) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="시작분 선택" name="eventStartM">
										<c:forEach begin="0" end="50" var="num" step="10">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(event.eventStartDtm, 14, 16) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(event.eventStartDtm, 14, 16) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" name="eventEndD" value="<fmt:formatDate value="${event.eventEndDtm}" pattern="yyyy.MM.dd"/>">
									</span>
									<select class="ui-sel" title="종료시 선택" name="eventEndH">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(event.eventEndDtm, 11, 13) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(event.eventEndDtm, 11, 13) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="종료분 선택" name="eventEndM">
									
										<c:forEach begin="0" end="50" var="num" step="10">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(event.eventEndDtm, 14, 16) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(event.eventEndDtm, 14, 16) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">전시목록 이미지</span>
								<div> (최대 10MB까지 등록가능, <br />파일 유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td colspan="3" class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<input type="hidden" id="imageName" name="imageName" value="${event.imageName }">
												<input type="file" id="imageFile" name="imageFile" title="첨부파일 추가">
												<label for="imageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${event.imageName}</a>
											<button type="button" class="btn-file-del" ${empty event.imageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="altrnText" value="${event.altrnText}">
									</div>
									<div class="img-wrap">
										 <c:if test="${not empty event.imageUrl}">
										 	<img alt="${event.altrnText}" src="${event.imageUrl}">
										 </c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">내용</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : editor-wrap -->
								<div class="editor-wrap">
									<textarea id="eventInfo" name="eventInfo">${event.eventInfo}</textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">전시여부</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="radioDisplay02" name="dispYn" type="radio" value="Y" ${empty event || event.dispYn eq 'Y' ? 'checked' : ''}>
											<label for="radioDisplay02">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="radioDisplay03" name="dispYn" type="radio" value="N" ${event.dispYn eq 'N' ? 'checked' : ''}>
											<label for="radioDisplay03">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<c:if test="${not empty param.ctlgEventNo}">
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:abc.adminDetailPopup('${event.rgsterNo}');">${UtilsMasking.adminName(event.rgsterId, event.rgsterName)}</a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${event.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:abc.adminDetailPopup('${event.moderNo}');">${UtilsMasking.adminName(event.moderId, event.moderName)}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${event.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</c:if>
					</tbody>
				</table>
				</form>
				<!-- E : tbl-row -->
				<!-- E : 이벤트관리 > 상세일 경우 -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<!-- <a href="#" class="btn-normal btn-del">삭제</a> -->
					</div>
					<div class="fr">
						<a href="/display/a-connect/event" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" class="btn-lg btn-save" id="saveBtn">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
		
		<template id="store-list-tmpl">
			<div>
				<li>
					<input type="hidden" name="storeNoArr">
					<span class="subject">매장명</span>
					<button type="button" class="btn-item-del">
						<span class="ico ico-item-del"><span class="offscreen">매장 삭제</span></span>
					</button>
				</li>
			</div>
		</template>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.aconnect.event.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>