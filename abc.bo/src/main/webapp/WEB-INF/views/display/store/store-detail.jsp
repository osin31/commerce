<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.display.store.codeCombo = ${codeCombo};	
});
</script>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">오프라인 매장관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
<!-- 						<button type="button" class="btn-favorites"> -->
<!-- 							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button> -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>오프라인 매장관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">오프라인 매장 등록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<form id="storeForm" name="storeForm">
				<table class="tbl-row">
					<input type="hidden" name="storeNo" value="${store.storeNo}">
					<caption>오프라인 매장 등록</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">전시 사이트</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach items="${siteList}" var="site" varStatus="status">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="siteNo" id="radioSite${status.count}" value="${site.siteNo}" ${store.siteNo == site.siteNo || status.count == 1 ? 'checked' : ''}>
												<label for="radioSite${status.count}">${site.siteName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">전시여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input type="radio" name="dispYn" id="radioDisplayY" value="Y" ${store.dispYn == 'Y' || store.dispYn == null ? 'checked' : ''}>
											<label for="radioDisplayY">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input type="radio" name="dispYn" id="radioDisplayN" value="N" ${store.dispYn == 'N' ? 'checked' : ''}>
											<label for="radioDisplayN">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">매장명</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="매장명 입력" name="storeName" id="storeName" value="${store.storeName}" maxlength="100">
							</td>
							<th scope="row">
								<span class="th-required">전화번호</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="hidden" name="telNoText" id="telNoText">
									<input type="text" numberonly="true" class="ui-input num-unit100" title="전화번호 앞자리 입력" name="telNoText1" maxlength="3" value="${fn:split(store.telNoFormat, '-')[0]}">
									<span class="text">-</span>
									<input type="text" numberonly="true" class="ui-input num-unit1000" title="전화번호 중간자리 입력" name="telNoText2" maxlength="4" value="${fn:split(store.telNoFormat, '-')[1]}">
									<span class="text">-</span>
									<input type="text" numberonly="true" class="ui-input num-unit1000" title="전화번호 끝자리 입력" name="telNoText3" maxlength="4" value="${fn:split(store.telNoFormat, '-')[2]}">
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">택배연동 우편번호</span>
							</th>
							<td class="input">
								<span class="address-box">
									<span class="zip-code-wrap">
										<input type="text" class="ui-input" title="우편번호 입력" name="postCodeText" id="postCodeText" value="${store.postCodeText}" maxlength="7">
										<button type="button" class="btn-sm btn-link" id="postPopup">우편번호 찾기</button>
									</span>
								</span>
							</td>
							<th scope="row">
								<span class="th-required">택배연동 주소</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="택배연동 주소 입력" name="postAddrText" id="postAddrText" value="${store.postAddrText}" maxlength="200">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">택배연동 상세주소</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="택배연동 상세주소 입력" name="dtlAddrText" id="dtlAddrText" value="${store.dtlAddrText}" maxlength="200">
							</td>
						</tr>
						<tr>
							<th scope="row">찾아가는길</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="찾아가는길 입력" name="findGoStrtText" id="findGoStrtText" value="${store.findGoStrtText}" maxlength="2000">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">지역구분</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="광역시/도 선택" name="areaNo" id="areaNo">
										<option value>광역시/도</option>
										<c:forEach items="${areaList}" var="area">
											<option value="${area.areaNo}" ${store.areaNo == area.areaNo ? 'selected' : ''}>${area.areaName}</option>	
										</c:forEach>
									</select>
									<select class="ui-sel" title="구/군" name="areaDtlSeq" id="areaDtlSeq">
										<option value>구/군</option>
										<c:if test="${not empty areaDtlList}">
											<c:forEach items="${areaDtlList}" var="dtl">
												<option value="${dtl.areaDtlSeq}" ${store.areaDtlSeq == dtl.areaDtlSeq ? 'selected' : ''}>${dtl.areaDtlName}</option>
											</c:forEach>
										</c:if>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
							<th scope="row">
								<span class="th-required">직영여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="directYn" id="radioDirectY" value="Y" ${store.directYn == 'Y' || store.directYn == null ? 'checked' : ''}>
											<label for="radioDirectY">직영</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="directYn" id="radioDirectN" value="N" ${store.directYn == 'N' ? 'checked' : ''}>
											<label for="radioDirectN">준직영</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">매장 제공 서비스</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach items="${storeServiceCodeList}" var="code" varStatus="status">
										<c:set var="chk" value="" />
										<c:forEach items="${serviceList}" var="service">
											<c:if test="${service.storeServiceCode eq code.codeDtlNo && service.psbltYn eq 'Y'}">
												<c:set var="chk" value="checked" />
											</c:if>
										</c:forEach>
										<li>
											<span class="ui-chk">
												<input id="chkService${status.count}" name="storeServiceCode" type="checkbox" value="${code.codeDtlNo}" <c:out value="${chk}"/>>
												<label for="chkService${status.count}">${code.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">지도 좌표</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<span class="text">Y :</span>
											<input type="text" class="ui-input num-unit100000000" placeholder="0.0000000" title="Y좌표 입력" name="pointY" value="${store.pointY}" readonly>
										</span>
										<!-- E : ip-text-box -->
									</li>
									<li>
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<span class="text">X :</span>
											<input type="text" class="ui-input num-unit100000000" placeholder="0.0000000" title="X좌표 입력" name="pointX" value="${store.pointX}" readonly>
										</span>
										<!-- E : ip-text-box -->
									</li>
									<li>
										<a href="#" class="btn-sm btn-link" id="searchMap">지도검색</a>
										<!-- <a href="#" class="btn-sm btn-link" id="searchKeywordMap">지도키워드검색</a> -->
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">매장 ID</span>
							</th>
							<td class="input">
								<input type="text" class="ui-input" title="매장 ID 입력" name="storeIdText" id="storeIdText" value="${store.storeIdText}" maxlength="20">
							</td>
							<th scope="row">임직원가 구매 가능여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="empPriceBuyPsbltYn" id="radioPurchaseY" value="Y" ${store.empPriceBuyPsbltYn == 'Y' ? 'checked' : ''}>
											<label for="radioPurchaseY">가능</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="empPriceBuyPsbltYn" id="radioPurchaseN" value="N" ${store.empPriceBuyPsbltYn == 'N' || store.empPriceBuyPsbltYn == null ? 'checked' : ''}>
											<label for="radioPurchaseN">불가능</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">영업시간</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="hidden" name="businessStartTime" value>
									<input type="hidden" name="businessEndTime" value>
									<select class="ui-sel" title="시작시 선택" name="businessStartHour" id="businessStartHour">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(store.businessStartTime, 1, 2) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(store.businessStartTime, 0, 2) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="시작분 선택" name="businessStartMinute" id="businessStartMinute">
										<c:forEach begin="0" end="59" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(store.businessStartTime, 4, 5) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(store.businessStartTime, 3, 5) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<select class="ui-sel" title="종료시 선택" name="businessEndHour" id="businessEndHour">
										<c:forEach begin="0" end="23" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(store.businessEndTime, 1, 2) == num ? 'selected' : ''}>0${num}시</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(store.businessEndTime, 0, 2) == num ? 'selected' : ''}>${num}시</option>
											</c:if>
										</c:forEach>
									</select>
									<select class="ui-sel" title="종료분 선택" name="businessEndMinute" id="businessEndMinute">
										<c:forEach begin="0" end="59" var="num">
											<c:if test="${num < 10}">
												<option value="0${num}" ${fn:substring(store.businessEndTime, 4, 5) == num ? 'selected' : ''}>0${num}분</option>
											</c:if>
											<c:if test="${num > 9}">
												<option value="${num}" ${fn:substring(store.businessEndTime, 3, 5) == num ? 'selected' : ''}>${num}분</option>
											</c:if>
										</c:forEach>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
							<th scope="row">오픈일</th>
							<td class="input">
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="오픈일 선택" name="openYmd" id="openYmd" value="<fmt:formatDate value="${store.openYmd}" pattern="yyyy.MM.dd"/>">
								</span>
								<span class="text">*오프라인 매장 노출안할시에는 공백  또는 9999-12-31 입력 해주시기 바랍니다.</span>
							</td>
						</tr>
						<!-- S : 20190405 추가 // 이미지영역 pc/mobile 분기 -->
						<tr>
							<th scope="row">PC 배너
								<div>권장사이즈 940*100 (최대 10MB까지 등록가능, <br>파일 유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input" colspan="3">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" name="pcImageName" value="${store.pcImageName}">
												<input type="hidden" name="pcImagePathText" value="${store.pcImagePathText}">
												<input type="hidden" name="pcImageUrl" value="${store.pcImageUrl}">
												<input type="file" id="pcImageFile" name="pcImageFile" title="첨부파일 추가">
												<label for="pcImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${store.pcImageName}</a>
											<button type="button" class="btn-file-del store-image-del" ${empty store.pcImageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="pcAltrnText" value="${store.pcAltrnText}" maxlength="100">
									</div>
									<div class="img-wrap">
										 <c:if test="${not empty store.pcImageUrl}">
										 	<img alt="${store.pcAltrnText}" src="${store.pcImageUrl}">
										 </c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">Mobile 배너
								<div>권장사이즈 660*200 (최대 10MB까지 등록가능, <br>파일 유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input" colspan="3">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<input type="hidden" name="mobileImageName" value="${store.mobileImageName}">
												<input type="hidden" name="mobileImagePathText" value="${store.mobileImagePathText}">
												<input type="hidden" name="mobileImageUrl" value="${store.mobileImageUrl}">
												<input type="file" id="mobileImageFile" name="mobileImageFile" title="첨부파일 추가">
												<label for="mobileImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="#" class="subject">${store.mobileImageName}</a>
											<button type="button" class="btn-file-del store-image-del" ${empty store.mobileImageName ? 'style="display: none;"' : ''}>
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="mobileAltrnText" value="${store.mobileAltrnText}" maxlength="100">
									</div>
									<div class="img-wrap">
										 <c:if test="${not empty store.mobileImageUrl}">
										 	<img alt="${store.mobileAltrnText}" src="${store.mobileImageUrl}">										 
										 </c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<!-- E : 20190405 추가 // 이미지영역 pc/mobile 분기 -->
						<tr>
							<th scope="row">기타</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="기타 입력" name="storeEtcText" id="storeEtcText" value="${store.storeEtcText}" maxlength="200">
							</td>
						</tr>
						<tr>
							<th scope="row">URL</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="URL 입력" name="storeUrl" id="storeUrl" value="${store.storeUrl}" maxlength="100">
							</td>
						</tr>
						<tr>
							<th scope="row">매장구분</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach items="${storeGbnCodeList}" var="code" varStatus="status">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="storeGbnCode" id="radioStore${status.count}" value="${code.codeDtlNo}" ${store.storeGbnCode == code.codeDtlNo || (store.storeGbnCode == null && status.count == 1) ? 'checked' : ''}>
												<label for="radioStore${status.count}">${code.codeDtlName}</label>
											</span>
										</li>									
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">매장형태</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach items="${storeTypeCodeList}" var="code" varStatus="status">
										<li>
											<span class="ui-rdo">
												<input id="radioStoreForm${status.count}" name="storeTypeCode" type="radio" value="${code.codeDtlNo}" ${store.storeTypeCode == code.codeDtlNo || (store.storeTypeCode == null && status.count == 1) ? 'checked ' : ''} 
													${(code.codeDtlNo ne '10008' && store.siteNo eq '10001') || (code.codeDtlNo eq '10008' && store.siteNo eq '10000') ? 'disabled ': '' }>
												<label for="radioStoreForm${status.count}">${code.codeDtlName}</label>
											</span>
										</li>									
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">매장픽업 가능여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="pickupPsbltYn" id="radioPickupY" value="Y" ${store.pickupPsbltYn == 'Y' || store.pickupPsbltYn == null ? 'checked' : ''}>
											<label for="radioPickupY">가능</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="pickupPsbltYn" id="radioPickupN" value="N" ${store.pickupPsbltYn == 'N' ? 'checked' : ''}>
											<label for="radioPickupN">불가능</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row" id="businessStopTh" style="${store.pickupPsbltYn == 'N' ? 'display: none;' : ''}">영업 중단여부</th>
							<td class="input" id="businessStopTd" style="${store.pickupPsbltYn == 'N' ? 'display: none;' : ''}">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="영업 중단유형 선택" name="businessStopRsnCode" id="businessStopRsnCode">
										<option value="">선택</option>
										<c:forEach items="${businessStopRsnCodeList}" var="code">
											<option value="${code.codeDtlNo}" ${store.businessStopRsnCode == code.codeDtlNo ? 'selected' : ''}>${code.codeDtlName}</option>
										</c:forEach>
									</select>

									<!-- S : 폐점 선택시 노출 -->
									<span class="date-box businessStopArea" ${store.businessStopRsnCode != 10000 ? 'style="display: none;"' : ''}>
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="폐점일 선택" name="businessStopYmd" id="businessStopYmd" value="<fmt:formatDate value="${store.businessStopYmd}" pattern="yyyy.MM.dd"/>">
									</span>
									<!-- E : 폐점 선택시 노출 -->
								</span>
								<!-- E : ip-text-box -->

								<!-- S : 폐점 선택시 노출 -->
								<!-- S : td-text-list -->
								<ul class="td-text-list businessStopArea" ${store.businessStopRsnCode != 10000 ? 'style="display: none;"' : ''}>
									<li>*선택한 날짜 3일 전부터 픽업매장 선택 불가</li>
								</ul>
								<!-- E : td-text-list -->
								<!-- E : 폐점 선택시 노출 -->
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				<!-- E : tbl-row -->

				<!-- S : tbl-row -->
				<c:if test="${not empty store.storeNo}">
				<table class="tbl-row">
					<caption>오프라인 매장 등록 작성 정보</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">작성자</th>
							<td><a href="javascript:abc.adminDetailPopup('${store.rgsterNo}');">${UtilsMasking.adminName(store.rgsterId, store.rgsterName)}</a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${store.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${store.moderNo}');">${UtilsMasking.adminName(store.moderId, store.moderName)}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${store.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				</c:if>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<c:if test="${not empty param.storeNo}">
							<a href="javascript:void(0);" class="btn-normal btn-del" id="storeDeleteBtn">삭제</a>
						</c:if>
					</div>
					<div class="fr">
						<a href="/display/store" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="#" class="btn-lg btn-save" id="saveBtn">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.store.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>