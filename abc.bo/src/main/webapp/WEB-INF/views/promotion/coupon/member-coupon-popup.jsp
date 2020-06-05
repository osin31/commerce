<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>쿠폰 현황 및 발급</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">쿠폰 현황 및 발급</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tab-wrap -->
			<div class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item"><a href="#tabContent1" class="tab-link">발급쿠폰 목록</a></li>
					<c:if test="${paperYn eq 'N'}"><li class="tab-item"><a href="#tabContent2" class="tab-link">쿠폰 발급</a></li></c:if>
				</ul>
				<!-- S:tab_content -->
				<div id="tabContent1" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">발급쿠폰 검색</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="searchForm1" name="searchForm1">
						<div class="search-wrap">
							<div class="search-inner">
								<table class="tbl-search">
									<caption>발급쿠폰 검색</caption>
									<colgroup>
										<col style="width: 170px;">
										<col>
										<col style="width: 79px">
										<col style="width: 170px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">쿠폰번호</th>
											<td colspan="4">${cpnNo}<input type="hidden" name="cpnNo" value="${cpnNo}" /></td>
										</tr>
										<tr>
											<th scope="row">회원 ID</th>
											<td class="input">
												<input type="text" name="loginId" class="ui-input" title="ID 입력">
											</td>
											<td></td>
											<th scope="row">회원명</th>
											<td class="input">
												<input type="text" name="memberName" class="ui-input" title="회원명 입력">
											</td>
										</tr>
										<tr>
											<th scope="row">쿠폰 사용처 온/오프라인 여부</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioOnOff00" name="usePlaceGbnType" type="radio" checked value="">
															<label for="radioOnOff00">전체</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioOnOff01" name="usePlaceGbnType" type="radio" value="O">
															<label for="radioOnOff01">온라인</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioOnOff02" name="usePlaceGbnType" type="radio" value="F">
															<label for="radioOnOff02">오프라인</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioOnOff03" name="usePlaceGbnType" type="radio" value="A">
															<label for="radioOnOff03">온라인 및 오프라인</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
											<td></td>
											<th scope="row">사용여부</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" id="chkUse01" checked vlaue="">
															<label for="chkUse01">전체</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="cpnUseYns" id="chkUse02" value="Y">
															<label for="chkUse02">사용완료</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="cpnUseYns" id="chkUse03" value="N">
															<label for="chkUse03">미사용</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="cpnUseYns" id="chkUse04" value="S">
															<label for="chkUse04">사용중지</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="cpnUseYns" id="chkUse05" value="E">
															<label for="chkUse05">기간만료</label>
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
										<a href="javascript:void(0);" class="btn-sm btn-func clear-form"><i class="ico ico-refresh"></i>초기화</a>
									</div>
									<div class="fr">
										<button href="submit" class="btn-normal btn-func">검색</button>
									</div>
								</div>
							</div>
							<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
						</div>
					</form>
					<!-- E : search-wrap -->

					<!-- S : tbl-desc-wrap -->
					<div class="tbl-desc-wrap border-box">
						<ol class="tbl-desc-list">
							<li>※쿠폰 미사용일 경우 쿠폰사용처가 없으므로 쿠폰사용처는 전체로 선택하여 검색</li>
							<li>* [재발급] 쿠폰을 다시 발급 함</li>
							<li>* [사용중지] 아직 사용하지 않은 쿠폰을 사용중지 함 / 재발급 받은 쿠폰을 사용중지 함</li>
							<li>* [중지해제] 사용중지한 쿠폰을 사용가능 할 수 있도록 중지해제 함</li>
						</ol>
					</div>
					<!-- E : tbl-desc-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">발급쿠폰 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="selTermsModule">목록개수</label>
								<select class="ui-sel selectPageCount" id="pageCount1" data-form-type="formType1">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="memberCouponSheet">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				<!-- S:tab_content -->
				<div id="tabContent2" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">회원 검색</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : search-wrap -->
					<form id="searchForm2" name="searchForm2">
						<input type="hidden" name="cpnNo" value="${cpnNo}" />
						<div class="search-wrap">
							<div class="search-inner">
								<table class="tbl-search">
									<caption>회원 검색</caption>
									<colgroup>
										<col style="width: 110px;">
										<col>
										<col style="width: 79px">
										<col style="width: 110px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">회원 ID</th>
											<td class="input">
												<input type="text" name="loginId" class="ui-input" title="ID 입력">
											</td>
											<td></td>
											<th scope="row">회원명</th>
											<td class="input">
												<input type="text" name="memberName" class="ui-input" title="회원명 입력">
											</td>
										</tr>
										<tr>
											<th scope="row">회원유형</th>
											<td class="input" colspan="4">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk">
															<input id="chkMemberTypeAll" name="chkMemberTypeAll" type="checkbox" checked>
															<label for="chkMemberTypeAll">전체</label>
														</span>
													</li>
													<c:forEach var="code" items="${memberTypeCodeList}" varStatus="i">
														<li>
															<span class="ui-chk">
																<input id="chkMemberType${i.count}" name="memberTypeCodes" class="chkMember" type="checkbox" checked value="${code.codeDtlNo}">
																<label for="chkMemberType${i.count}">${code.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
													<li>
														<span class="ui-chk">
															<input id="chkMemberTypeEmp" name="empYn" type="checkbox" value="Y">
															<label for="chkMemberTypeEmp">임직원</label>
														</span>
													</li>
												</ul>
												<%-- <span class="member-grade-list">
													<span class="text">( 회원유형 :</span>
													<!-- S : ip-box-list -->
													<ul class="ip-box-list">
														<c:forEach var="code" items="${mbshpGradeCodeList}" varStatus="i">
															<li>
																<span class="ui-chk">
																	<input id="chkMbshpGrade${i.count}" name="mbshpGradeCodes" type="checkbox" checked value="${code.codeDtlNo}">
																	<label for="chkMbshpGrade${i.count}">${code.codeDtlName}</label>
																</span>
															</li>
														</c:forEach>
														</ul>
													<!-- E : ip-box-list -->
													<span class="text">)</span>
												</span> --%>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="confirm-box">
									<div class="fl">
										<a href="javascript:void(0);" class="btn-sm btn-func clear-form"><i class="ico ico-refresh"></i>초기화</a>
									</div>
									<div class="fr">
										<button href="submit" id="searchForm1Btn" class="btn-normal btn-func">검색</button>
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
							<h3 class="content-title">회원 목록</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<span class="opt-group">
								<label class="title" for="selTermsModule02">목록개수</label>
								<select class="ui-sel selectPageCount" id="pageCount2" data-form-type="formType2">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="addMemberCoupon" class="btn-sm btn-func">선택추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="memberCouponAddSheet">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 id="targetMemberCount" class="content-title">발급할 회원 목록 (총 <span id="checkMemberCount">0</span>건)</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<form name="excelForm">
						<div class="tbl-controller">
							<div class="fl">
								<a href="javascript:void(0);" id="removeAllMember" class="btn-sm btn-del">전체삭제</a>
							</div>
							<div class="fr">
								<div class="file-wrap">
									<ul id="excelArea" class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="file" id="inputFile" name="excelUpload" title="첨부파일 추가">
												<label for="inputFile">찾아보기</label>
											</span>
										</li>
									</ul>
								</div>
								<a href="javascript:void(0);" id="excelUpload" class="btn-sm btn-func">엑셀 파일 업로드</a>
								<a href="javascript:void(0);" id="excelDownload" class="btn-sm btn-func">엑셀 폼 다운로드</a>
								
								<%-- 
									 2020.05.13 :  CS 고객센터는 이 두아이디만 쿠폰발급 버튼 노출
									  	- csmizie109 / cs_j0202j70
								--%>
								<c:set var="substringNoCpnBtnLoinId" value="${fn:substring(noCpnBtnLoinId,0,2)}" />
								<c:choose>
									<c:when test="${substringNoCpnBtnLoinId eq 'cs'}">
										<c:if test="${noCpnBtnLoinId eq 'csmizie109' or noCpnBtnLoinId eq 'cs_j0202j70'}">
											<a href="javascript:void(0);" id="addExcelCoupon" class="btn-sm btn-func">쿠폰발급</a>
										</c:if>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" id="addExcelCoupon" class="btn-sm btn-func">쿠폰발급</a>
									</c:otherwise>
								</c:choose>
							
							</div>
						</div>
					</form>
					<!-- E : tbl-controller -->

					<form name="addMemberCouponForm">
						<input type="hidden" name="cpnNo" value="${cpnNo}" />
						<div id="" class="white-box">
							<ul id="addMemberArea" class="item-list"></ul>
						</div>
					</form>
				</div>
				<!-- E:tab_content -->
			</div>
			<!-- E : tab-wrap -->
		</div>
	</div>

	<template id="addMemberTemplate">
		<li class="addLi"><span class="subject"></span>
			<button type="button" class="btn-file-del">
				<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
			</button>
		</li>
	</template>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.member.coupon.popup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp" %>
