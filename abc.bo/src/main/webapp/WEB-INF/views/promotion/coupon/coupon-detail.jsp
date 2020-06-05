<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	<script type="text/javascript">
	$(function () {
		$('#targetProductExcelUpLoad').click(function() {
			var cpnApplyType = $('[name=cpnApplyType]:checked').val();
			excelUpload(cpnApplyType);
			abc.biz.promotion.coupon.detail.beforeCpnApplyType = cpnApplyType;
		});

		$('#lessProductExcelUpLoad').click(function() {
			var cpnApplyType = $('[name=cpnApplyType]:checked').val();
			excelUpload(cpnApplyType);
			abc.biz.promotion.coupon.detail.beforeCpnApplyType = cpnApplyType;
		});

		excelUpload = function(cnpApplyType) {
			var msg = "";

			//console.log("cnpApplyType : " + cnpApplyType);

			var productListSheet = null;

			if (cnpApplyType == "P") {
				msg = "쿠폰 적용 대상 상품을 업로드 하시겠습니까?";
				productListSheet = product1list;
			} else {
				msg = "쿠폰 제외 대상 상품을 업로드 하시겠습니까?";
				productListSheet = product2list;
			}

			if (! confirm(msg)) {
				return;
			}

			var totalRows = productListSheet.GetTotalRows();  //업로드 전체 건수
			var callPopupType = $('#callPopupType').val();

			//다시 엑셀 업로드 진행시
			/* if(totalRows > 0 && callPopupType == "C"){
				if (! confirm("엑셀 업로드 후 저장 처리 하지않은 데이터가 존재합니다. \n\n다시 업로드시 기존 데이타는  유지 되지 않습니다. \n\n업로드를 진행 하시겠습니까?")) {
					return;
				}
			} */

			//엑셀 업로드시 매치 유형 선택 (기본값: 헤더 타이틀 기준 매치)
			var params = { Mode : "HeaderMatch",  StartRow: "1" ,  FileExt: "xls|xlsx"} ;
			productListSheet.LoadExcel(params);

		}

		$('#targetProducExcelCouponSampleDownLoad').click(function() {
			location.href = "/promotion/coupon/product/coupon-product-excel-sample-down"
		});

		$('#lessProductExcelCouponSampleDownLoad').click(function() {
			location.href = "/promotion/coupon/product/coupon-product-excel-sample-down"
		});

	});

	function product1list_OnLoadExcel(result, code, msg) {
		if (result) {
			abc.biz.promotion.coupon.detail.excelUploadProductSearch(product1list, $('[name=cpnApplyType]:checked').val());
			//console.log("GetTotalRows : " + product1list.GetTotalRows());
			alert("엑셀 로딩이 완료되었습니다.");
		} else {
			alert("엑셀 로딩중 오류가 발생하였습니다.\n[ Error Code :" +Code+" \nError Massage : "+ msg+"]");
		}
	}

	function product2list_OnLoadExcel(result, code, msg) {
		if (result) {
			abc.biz.promotion.coupon.detail.excelUploadProductSearch(product2list, $('[name=cpnApplyType]:checked').val());
			alert("엑셀 로딩이1 완료되었습니다.");
		} else {
			alert("엑셀 로딩중 오류가 발생하였습니다.\n[ Error Code :" +Code+" \nError Massage : "+ msg+"]");
		}
	}

	</script>
	<form id="searchFrm" name="searchFrm">
		<input type="hidden" name="prdtType" id="prdtType" value="T" />
		<input type="hidden" name="prdtNoArr" value="" />
		<input type="hidden" size="10"  name="callPopupType" id="callPopupType" value="C">
		<input type="hidden" name="pageCount" value="" />
	</form>
	<form id="frm" name="frm">
		<input type="hidden" name="cpnNo" id="cpnNo" value="${prCoupon.cpnNo}" />
		<input type="hidden" name="memberCouponCount" id="memberCouponCount" value="${empty prCoupon.memberCouponCount ? '0' : prCoupon.memberCouponCount}" />
		<input type="hidden" name="deleteCouponProductList" value="" />
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">쿠폰 관리</h2>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>쿠폰 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">쿠폰 ${empty prCoupon.cpnNo ? '등록' : '상세'}</h3>
					</div>
					<div class="fr">
						<c:if test="${empty prCoupon.cpnNo or nowDate <= issueEndDate}">
							<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</c:if>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>쿠폰 등록</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">쿠폰 유형</span>
							</th>
							<td class="input">
								<c:choose>
									<c:when test="${empty prCoupon.cpnNo}">
										<select name="cpnTypeCode" class="ui-sel" title="쿠폰 유형 선택">
											<option value="">선택</option>
											<c:forEach var="code" items="${cpnTypeCodeList}">
												<option ${prCoupon.cpnTypeCode eq code.codeDtlNo ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise><span class="text">${prCoupon.cpnTypeCodeName}<input type="hidden" name="cpnTypeCode" value="${prCoupon.cpnTypeCode}" /></span></c:otherwise>
								</c:choose>
							</td>
							<th scope="row">쿠폰 번호</th>
							<td class="input">
								<span class="text">${prCoupon.cpnNo}</span>
								<c:if test="${not empty prCoupon.cpnNo}"><a href="javascript:void(0);" id="couponIssueListPopup" class="btn-sm btn-link" data-paper-yn="${prCoupon.paperCrtCount > 0 and prCoupon.cpnCrtType eq 'P' ? 'Y' : 'N'}">쿠폰 현황 및 발급</a></c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">쿠폰 분류</span>
							</th>
							<!-- S : 쿠폰유형 > 제휴사 할인쿠폰 선택시 쿠폰 분류 td 영역 colspan="3" 속성 추가 해주세요 -->
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCouponType01" name="normalCpnYn" type="radio" ${prCoupon.normalCpnYn eq 'Y' or empty prCoupon.normalCpnYn ? 'checked' : ''} value="Y">
											<label for="radioCouponType01">일반쿠폰</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCouponType02" name="normalCpnYn" type="radio" ${prCoupon.normalCpnYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioCouponType02">플러스쿠폰 (중복사용 가능한 쿠폰)</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<!-- S : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
							<th scope="row">
								<span class="th-required">쿠폰 속성</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProperty01" name="cpnUseGbnType" type="radio" ${prCoupon.cpnUseGbnType eq 'E' or empty prCoupon.cpnUseGbnType ? 'checked' : ''} value="E">
											<label for="radioProperty01">행사_일반</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProperty04" name="cpnUseGbnType" type="radio" ${prCoupon.cpnUseGbnType eq 'F' ? 'checked' : ''} value="F">
											<label for="radioProperty04">행사_다운로드</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProperty02" name="cpnUseGbnType" type="radio" ${prCoupon.cpnUseGbnType eq 'C' ? 'checked' : ''} value="C">
											<label for="radioProperty02">CS</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProperty03" name="cpnUseGbnType" type="radio" ${prCoupon.cpnUseGbnType eq 'D' ? 'checked' : ''} value="D">
											<label for="radioProperty03">eDM</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<!-- E : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse01" name="useYn" type="radio" ${prCoupon.useYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioUse01">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse02" name="useYn" type="radio" ${prCoupon.useYn eq 'N' or empty prCoupon.useYn ? 'checked' : ''} value="N">
											<label for="radioUse02">사용안함</label>
										</span>
									</li>
								</ul>
							</td>
							<th scope="row">
								<span class="th-required">전시여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay01" name="dispYn" type="radio" ${prCoupon.dispYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioDisplay01">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02" name="dispYn" type="radio" ${prCoupon.dispYn eq 'N' or empty prCoupon.dispYn ? 'checked' : ''} value="N">
											<label for="radioDisplay02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">쿠폰명</span>
							</th>
							<td class="input">
								<!-- TODO : 기획 fix 후 입력 제한 글자수 변경 -->
								<input type="text" name="cpnName" class="ui-input" title="쿠폰명 입력" placeholder="100자 이내로 입력" maxlength="100" value="${prCoupon.cpnName}">
							</td>
							<th scope="row">
								<span class="th-required">쿠폰존 전시여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioZone01" name="cpnZoneDispYn" type="radio" ${prCoupon.cpnZoneDispYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioZone01">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioZone02" name="cpnZoneDispYn" type="radio" ${prCoupon.cpnZoneDispYn eq 'N' or empty prCoupon.cpnZoneDispYn ? 'checked' : ''} value="N">
											<label for="radioZone02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">발급기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<fmt:formatDate value="${prCoupon.issueStartDtm}" pattern="yyyy.MM.dd" var="issueStartYmd"/>
										<fmt:formatDate value="${prCoupon.issueStartDtm}" pattern="HH" var="issueStartH"/>
										<fmt:formatDate value="${prCoupon.issueStartDtm}" pattern="mm" var="issueStartM"/>
										<input type="text" name="paramIssueStartYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" value="${issueStartYmd}">
									</span>
									<select name="paramIssueStartH" class="ui-sel" title="시작시 선택">
										<c:forEach var="row" begin="0" end="23">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramIssueStartH" value="0${row}" />
													<option ${issueStartH eq paramIssueStartH ? 'selected' : ''} value="${paramIssueStartH}">${paramIssueStartH}시</option>
												</c:when>
												<c:otherwise><option ${issueStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramIssueStartM" class="ui-sel" title="시작분 선택">
										<c:forEach var="row" begin="0" end="59">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramIssueStartM" value="0${row}" />
													<option ${issueStartM eq paramIssueStartM ? 'selected' : ''} value="${paramIssueStartM}">${paramIssueStartM}분</option>
												</c:when>
												<c:otherwise><option ${issueStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<span class="date-box">
										<fmt:formatDate value="${prCoupon.issueEndDtm}" pattern="yyyy.MM.dd" var="issueEndYmd"/>
										<fmt:formatDate value="${prCoupon.issueEndDtm}" pattern="HH" var="issueEndH"/>
										<fmt:formatDate value="${prCoupon.issueEndDtm}" pattern="mm" var="issueEndM"/>
										<input type="text" name="paramIssueEndYmd" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" value="${issueEndYmd}">
									</span>
									<select name="paramIssueEndH" class="ui-sel" title="종료시 선택">
										<c:forEach var="row" begin="0" end="23" varStatus="i">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramIssueEndH" value="0${row}" />
													<option ${issueEndH eq paramIssueStartH ? 'selected' : ''} value="${paramIssueEndH}">${paramIssueEndH}시</option>
												</c:when>
												<c:otherwise><option ${issueEndH eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<select name="paramIssueEndM" class="ui-sel" title="종료분 선택">
										<c:forEach var="row" begin="0" end="59" varStatus="i">
											<c:choose>
												<c:when test="${row < 10}">
													<c:set var="paramIssueEndM" value="0${row}" />
													<option ${issueEndM eq paramIssueEndM ? 'selected' : ''} value="${paramIssueEndM}">${paramIssueEndM}분</option>
												</c:when>
												<c:otherwise><option ${issueEndM eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</span>
								<!-- E : term-date-wrap -->
								<span class="text">*사용자 쿠폰리스트에는 유효기간 기준으로 노출됩니다.</span>
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">유효기간</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo ip-label-instead">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioValidity01" name="validTermGbnType" type="radio" ${prCoupon.validTermGbnType eq 'T' or empty prCoupon.validTermGbnType ? 'checked' : ''} value="T">
											<label for="radioValidity01"></label>

											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<fmt:formatDate value="${prCoupon.validStartDtm}" pattern="yyyy.MM.dd" var="validStartYmd"/>
													<fmt:formatDate value="${prCoupon.validStartDtm}" pattern="HH" var="validStartH"/>
													<fmt:formatDate value="${prCoupon.validStartDtm}" pattern="mm" var="validStartM"/>
													<input type="text" name="paramValidStartYmd" data-role="datepicker" class="ui-cal js-ui-cal validDate" title="날짜 선택" value="${validStartYmd}">
												</span>
												<select name="paramValidStartH" class="ui-sel validDate" title="시작시 선택">
													<c:forEach var="row" begin="0" end="23">
														<c:choose>
															<c:when test="${row < 10}">
																<c:set var="paramValidStartH" value="0${row}" />
																<option ${validStartH eq paramValidStartH ? 'selected' : ''} value="${paramValidStartH}">${paramValidStartH}시</option>
															</c:when>
															<c:otherwise><option ${validStartH eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
												<select name="paramValidStartM" class="ui-sel validDate" title="시작분 선택">
													<c:forEach var="row" begin="0" end="59">
														<c:choose>
															<c:when test="${row < 10}">
																<c:set var="paramValidStartM" value="0${row}" />
																<option ${validStartM eq paramValidStartM ? 'selected' : ''} value="${paramValidStartM}">${paramValidStartM}분</option>
															</c:when>
															<c:otherwise><option ${validStartM eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
												<span class="text">~</span>
												<span class="date-box">
													<fmt:formatDate value="${prCoupon.validEndDtm}" pattern="yyyy.MM.dd" var="validEndYmd"/>
													<fmt:formatDate value="${prCoupon.validEndDtm}" pattern="HH" var="validEndH"/>
													<fmt:formatDate value="${prCoupon.validEndDtm}" pattern="mm" var="validEndM"/>
													<input type="text" name="paramValidEndYmd" data-role="datepicker" class="ui-cal js-ui-cal validDate" title="종료 날짜 선택" value="${validEndYmd}">
												</span>
												<select name="paramValidEndH" class="ui-sel validDate" title="종료시 선택">
													<c:forEach var="row" begin="0" end="23" varStatus="i">
														<c:choose>
															<c:when test="${row < 10}">
																<c:set var="paramValidEndH" value="0${row}" />
																<option ${validEndH eq paramValidEndH ? 'selected' : ''} value="${paramValidEndH}">${paramValidEndH}시</option>
															</c:when>
															<c:otherwise><option ${validEndH eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
												<select name="paramValidEndM" class="ui-sel validDate" title="종료분 선택">
													<c:forEach var="row" begin="0" end="59" varStatus="i">
														<c:choose>
															<c:when test="${row < 10}">
																<c:set var="paramValidEndM" value="0${row}" />
																<option ${validEndM eq paramValidEndM ? 'selected' : ''} value="${paramValidEndM}">${paramValidEndM}분</option>
															</c:when>
															<c:otherwise><option ${validEndM eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</span>
											<!-- E : term-date-wrap -->
										</span>
									</li>
									<li>
										<span class="ui-rdo ip-label-instead">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioValidity02" name="validTermGbnType" type="radio" ${prCoupon.validTermGbnType eq 'D' ? 'checked' : ''} value="D">
											<label for="radioValidity02"></label>

											<span class="ip-text-box">
												<span class="text">발급 후</span>
												<!-- DESC : 유효기간 날짜선택시 input disabled 속성 추가 해주세요 -->
												<input type="text" name="useLimitDayCount" class="ui-input num-unit100 afterValidDate" title="날짜 입력" disabled maxlength="3" value="${prCoupon.useLimitDayCount}">
												<span class="text">일까지 사용가능 (최대 12개월)</span>
											</span>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">유효기간 종료 안내</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo ip-label-instead">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioValidityEnd01" name="validTermAlertYn" type="radio" ${prCoupon.validTermAlertYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioValidityEnd01"></label>

											<span class="ip-text-box">
												<span class="text">유효기간 종료</span>
												<input type="text" name="alertSendDayCount" class="ui-input num-unit100 validTermAlert" title="날짜 입력" maxlength="3"
													${prCoupon.validTermAlertYn eq 'N' or empty prCoupon.validTermAlertYn ? 'disabled' : ''} value="${prCoupon.alertSendDayCount}">
												<span class="text">일 전 알림톡 발송</span>
											</span>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioValidityEnd02" name="validTermAlertYn" type="radio" ${prCoupon.validTermAlertYn eq 'N' or empty prCoupon.validTermAlertYn ? 'checked' : ''} value="N">
											<label for="radioValidityEnd02">안내안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">릴레이쿠폰 사용여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioRelayCoupon01" name="relayCpnUseYn" type="radio" ${prCoupon.relayCpnUseYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioRelayCoupon01">사용</label>
										</span>
										<!-- DESC : marketing/BO-MK-01006.html 파일 확인 해주세요. 사용안함 선택시 disabled 클래스 추가 -->
										<a href="javascript:void(0);" id="couponPopup" class="btn-sm btn-func disabled">쿠폰조회</a>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioRelayCoupon02" name="relayCpnUseYn" type="radio" ${prCoupon.relayCpnUseYn eq 'N' or empty prCoupon.relayCpnUseYn ? 'checked' : ''} value="N">
											<label for="radioRelayCoupon02">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->

								<!-- S : 쿠폰조회시 쿠폰 리스트 영역 -->
								<!-- S : item-list -->
								<ul id="relayCpnArea" class="item-list">
									<c:if test="${prCoupon.relayCpnUseYn eq 'Y' and not empty prCoupon.relayCpnNo}">
										<li>
											<span class="subject">${prCoupon.relayCpnName}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
											</button>
											<input type="hidden" name="relayCpnNo" value="${prCoupon.relayCpnNo}" />
										</li>
									</c:if>
								</ul>
								<!-- E : item-list -->
								<!-- E : 쿠폰조회시 쿠폰 리스트 영역 -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">다운로드 가능여부</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDownload01" name="dwldPsbltYn" type="radio" ${prCoupon.dwldPsbltYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioDownload01">가능</label>
										</span>

										<!-- DESC : 가능 선택시 input disabled 속성 삭제 해주세요 -->
										<c:set var="checkDayList">monYn_월_${prCoupon.monYn},tueYn_화_${prCoupon.tueYn},wedYn_수_${prCoupon.wedYn},thuYn_목_${prCoupon.thuYn},friYn_금_${prCoupon.friYn},satYn_토_${prCoupon.satYn},sunYn_일_${prCoupon.sunYn}</c:set>
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkDownload01" name="radioDownloadModule" class="checkAllDay" disabled type="checkbox">
													<label for="chkDownload01">전체</label>
												</span>
											</li>
											<c:forEach var="row" items="${checkDayList}" varStatus="i">
												<li>
													<span id="checkDayArea" class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDownload${i.count}" data-name="${fn:split(row, '_')[0]}" class="checkDay" disabled type="checkbox" ${fn:split(row, '_')[2] eq 'Y' ? 'checked' : ''}>
														<label for="chkDownload${i.count}">${fn:split(row, '_')[1]}</label>
													</span>
												</li>
											</c:forEach>
											<li>
												<!-- S : term-date-wrap -->
												<span class="term-date-wrap">
													<select name="paramDayStartH" class="ui-sel selectDwldTime" title="시작시 선택" disabled>
														<c:forEach var="row" begin="0" end="23">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayStartH" value="0${row}" />
																	<option ${fn:split(prCoupon.dwldStartTime, '::')[0] eq paramDayStartH ? 'selected' : ''} value="${paramDayStartH}">${paramDayStartH}시</option>
																</c:when>
																<c:otherwise><option ${fn:split(prCoupon.dwldStartTime, '::')[0] eq row ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<select name="paramDayStartM" class="ui-sel selectDwldTime" title="시작분 선택" disabled>
														<c:forEach var="row" begin="0" end="59">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayStartM" value="0${row}" />
																	<option ${fn:split(prCoupon.dwldStartTime, '::')[1] eq paramDayStartM ? 'selected' : ''} value="${paramDayStartM}">${paramDayStartM}분</option>
																</c:when>
																<c:otherwise><option ${fn:split(prCoupon.dwldStartTime, '::')[1] eq row ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<span class="text">~</span>
													<select name="paramDayEndH" class="ui-sel selectDwldTime" title="종료시 선택" disabled>
														<c:forEach var="row" begin="0" end="23" varStatus="i">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayEndM" value="0${row}" />
																	<option ${fn:split(prCoupon.dwldEndTime, '::')[0] eq paramDayEndM ? 'selected' : ''} value="${paramDayEndM}">${paramDayEndM}시</option>
																</c:when>
																<c:otherwise><option ${fn:split(prCoupon.dwldEndTime, '::')[0] eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}시</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<select name="paramDayEndM" class="ui-sel selectDwldTime" title="종료분 선택" disabled>
														<c:forEach var="row" begin="0" end="59" varStatus="i">
															<c:choose>
																<c:when test="${row < 10}">
																	<c:set var="paramDayEndM" value="0${row}" />
																	<option ${fn:split(prCoupon.dwldEndTime, '::')[1] eq paramDayEndM ? 'selected' : ''} value="${paramDayEndM}">${paramDayEndM}분</option>
																</c:when>
																<c:otherwise><option ${fn:split(prCoupon.dwldEndTime, '::')[1] eq row or (empty prCoupon.cpnNo and i.last) ? 'selected' : ''} value="${row}">${row}분</option></c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</span>
												<!-- E : term-date-wrap -->
											</li>
										</ul>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDownload02" name="dwldPsbltYn" type="radio" ${prCoupon.dwldPsbltYn eq 'N' or empty prCoupon.dwldPsbltYn ? 'checked' : '' ? 'checked' : ''} value="N">
											<label for="radioDownload02">불가능</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
						<tr class="affltsArea">
							<th scope="row">
								<span class="th-required">사용처 온/오프라인 여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOnOff01" name="usePlaceGbnType" type="radio" ${prCoupon.usePlaceGbnType eq 'O' or empty prCoupon.usePlaceGbnType ? 'checked' : ''} value="O">
											<label for="radioOnOff01">온라인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOnOff02" name="usePlaceGbnType" type="radio" ${prCoupon.usePlaceGbnType eq 'F' ? 'checked' : ''} value="F">
											<label for="radioOnOff02">오프라인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOnOff03" name="usePlaceGbnType" type="radio" ${prCoupon.usePlaceGbnType eq 'A' ? 'checked' : ''} value="A">
											<label for="radioOnOff03">온라인 및 오프라인</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">쿠폰 생성형태</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:choose>
										<c:when test="${not empty prCoupon.cpnNo}">
											<c:choose>
												<c:when test="${prCoupon.cpnCrtType eq 'O' }">
													<li><span class="text">온라인 생성</span></li>
												</c:when>
												<c:otherwise>
													<li>
														<span class="text">지류 생성 - ${prCoupon.paperCrtCount} 부</span>
														<c:if test="${prCoupon.paperCrtCount > 0 and prCoupon.cpnCrtType eq 'P'}">
															<a href="javascript:void(0);" id="couponPaperNumberDownload" class="btn-sm btn-link">난수 다운로드</a>
														</c:if>
													</li>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioProduce01" name="cpnCrtType" type="radio" ${prCoupon.cpnCrtType eq 'O' or empty prCoupon.cpnCrtType ? 'checked' : ''} value="O">
													<label for="radioProduce01">온라인 생성</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo ip-label-instead">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioProduce02" name="cpnCrtType" type="radio" ${prCoupon.cpnCrtType eq 'P' ? 'checked' : ''} value="P">
													<label for="radioProduce02"></label>
													<span class="ip-text-box">
														<span class="text">지류 생성</span>
														<input type="text" name="paperCrtCount" class="ui-input num-unit100 cpnCrt" title="숫자 입력" disabled value="${prCoupon.paperCrtCount}" maxlength="4">
														<span class="text">부</span>
													</span>
												</span>
											</li>
										</c:otherwise>
									</c:choose>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- E : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
						<tr class="onOffArea">
							<th scope="row">
								<span class="th-required">디바이스</span>
							</th>
							<td class="input">
								<!-- DESC : 사용처 온/오프라인 여부 > 오프라인 선택시, 디바이스 input 영역 전체 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:choose>
										<c:when test="${not empty prCoupon.cpnNo}">
											<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
												<c:set var="checkYn" value="" />
												<c:forEach var="row" items="${prCouponApplyDeviceList}">
													<c:if test="${code.codeDtlNo eq row.deviceCode}"><c:set var="checkYn" value="checked" /></c:if>
												</c:forEach>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="deviceCodes" id="chkDevice${i.count }" ${checkYn} value="${code.codeDtlNo}">
														<label for="chkDevice${i.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="code" items="${deviceCodeList}" varStatus="i">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="deviceCodes" id="chkDevice${i.count }" checked value="${code.codeDtlNo}">
														<label for="chkDevice${i.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">채널</span>
							</th>
							<td class="input">
								<!-- DESC : 사용처 온/오프라인 여부 > 오프라인 선택시, 채널 input 영역 전체 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:choose>
										<c:when test="${not empty prCoupon.cpnNo}">
											<c:forEach var="channel" items="${channelList}" varStatus="i">
												<c:set var="checkYn" value="" />
												<c:forEach var="row" items="${prCouponApplyChannelList}">
													<c:if test="${channel.chnnlNo eq row.chnnlNo}"><c:set var="checkYn" value="checked" /></c:if>
												</c:forEach>
												<li>
													<span id="checkChannelArea" class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="prCouponApplyChannelArr.chnnlNo" id="chkChannel${i.count }" class="checkChannel" data-site-no="${channel.siteNo}" ${checkYn} value="${channel.chnnlNo}">
														<label for="chkChannel${i.count}">${channel.chnnlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="channel" items="${channelList}" varStatus="i">
												<li>
													<span id="checkChannelArea" class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="prCouponApplyChannelArr.chnnlNo" id="chkChannel${i.count }" class="checkChannel" data-site-no="${channel.siteNo}" checked value="${channel.chnnlNo}">
														<label for="chkChannel${i.count}">${channel.chnnlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
						<tr class="onOffArea affltsArea">
							<!-- DESC : 매장명 리스트 없는 경우, rowspan="2" 속성 제거 해주세요 -->
							<th scope="row" rowspan="2">
								<span class="th-required">오프라인 매장 선택</span>
							</th>
							<td class="input" colspan="3">
								<!-- DESC : 사용처 온/오프라인 여부 > 온라인 선택시, 오프라인 매장 선택 input 영역 전체 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStore01" name="storeApplyType" type="radio" ${prCoupon.storeApplyType eq 'A' or empty prCoupon.storeApplyType ? 'checked' : ''} value="A">
											<label for="radioStore01">전체 매장</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStore02" name="storeApplyType" type="radio" ${prCoupon.storeApplyType eq 'U' ? 'checked' : ''} value="U">
											<label for="radioStore02">사용할 매장</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStore03" name="storeApplyType" type="radio" ${prCoupon.storeApplyType eq 'L' ? 'checked' : ''} value="L">
											<label for="radioStore03">제외할 매장</label>
										</span>
										<a href="javascript:void(0);" id="storePopup" class="btn-sm btn-func">매장 찾기</a>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 매장명 선택시 매장명 리스트 영역 -->
						<tr class="onOffArea affltsArea">
							<td class="input" colspan="3">
								<!-- S : item-list -->
								<ul id="storeArea" class="item-list">
									<c:forEach var="row" items="${prCouponApplyStoreList}">
										<li>
											<input type="hidden" name="prCouponApplyStoreArr.storeNo" value="${row.storeNo}" />
											<span class="subject">${row.storeName}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">매장 삭제</span></span>
											</button>
										</li>
									</c:forEach>
								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
						<!-- E : 매장명 선택시 매장명 리스트 영역 -->
						<tr class="onOffArea affltsArea">
							<th scope="row">
								<span class="th-required">매장 POS 전시여부</span>
							</th>
							<td class="input" colspan="3">
								<!-- DESC : 사용처 온/오프라인 여부 > 온라인 선택시, 매장 POS 전시여부 input 영역 전체 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02-01" name="storePosDispYn" type="radio" ${prCoupon.storePosDispYn eq 'Y' or empty prCoupon.storePosDispYn ? 'checked' : ''} value="Y">
											<label for="radioDisplay02-01">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02-02" name="storePosDispYn" type="radio" ${prCoupon.storePosDispYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioDisplay02-02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- E : 쿠폰유형 > 제휴사 할인쿠폰 선택시 미노출 -->
						<!-- S : 쿠폰유형 > 제휴사 할인쿠폰 선택시 노출 (제휴사 선택) -->
						<tr class="couponAffltsArea cpnTypeArea">
							<th scope="row">
								<span class="th-required">제휴사 선택</span>
							</th>
							<td class="input" colspan="3">
								<select name="affltsCode" class="ui-sel" title="제휴사 선택">
									<option value="">제휴사 선택</option>
									<c:forEach var="code"  items="${affltsCodeList}">
										<option ${prCoupon.affltsCode eq code.codeDtlNo ? 'selected' : ''} value="${code.codeDtlNo}">${code.codeDtlName}</option>
									</c:forEach>
								</select>
							</td>
							<%-- <th scope="row">
								<span class="th-required">임직원 진행여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProgress02-01" name="empApplyYn" type="radio" ${prCoupon.empApplyYn eq 'Y' or empty prCoupon.empApplyYn ? 'checked' : ''} value="Y">
											<label for="radioProgress02-01">진행</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioProgress02-02" name="empApplyYn" type="radio" ${prCoupon.empApplyYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioProgress02-02">진행안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td> --%>
						</tr>
						<!-- E : 쿠폰유형 > 제휴사 할인쿠폰 선택시 노출 (제휴사 선택) -->
						<tr>
							<th scope="row">
								<span class="th-required">쿠폰 발행수량 제한</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCountLimit02" name="totalIssueLimitYn" type="radio" ${prCoupon.totalIssueLimitYn eq 'N' or empty prCoupon.totalIssueLimitYn ? 'checked' : ''} value="N">
											<label for="radioCountLimit02">무제한</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo ip-label-instead">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCountLimit03" name="totalIssueLimitYn" type="radio" ${prCoupon.totalIssueLimitYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioCountLimit03"></label>
											<span class="ip-text-box">
												<!-- DESC : 무제한 선택시 input disabled 속성 추가 해주세요 -->
												<input type="text" name="totalIssueLimitCount" class="ui-input num-unit100 totalIssueLimit" title="명 수 입력" disabled maxlength="4" value="${prCoupon.totalIssueLimitCount}">
												<span class="text">장 제한</span>
											</span>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">ID당 발급횟수 제한</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioIDLimit02" name="per1psnIssueLimitYn" type="radio" ${prCoupon.per1psnIssueLimitYn eq 'N' or empty prCoupon.per1psnIssueLimitYn ? 'checked' : ''} value="N">
											<label for="radioIDLimit02">무제한</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo ip-label-instead">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioIDLimit03" name="per1psnIssueLimitYn" type="radio" ${prCoupon.per1psnIssueLimitYn eq 'Y' ? 'checked' : ''} value="Y">
											<label for="radioIDLimit03"></label>
											<span class="ip-text-box">
												<span class="text">ID 당</span>
												<!-- DESC : 무제한 선택시 input disabled 속성 추가 해주세요 -->
												<input type="text" name="per1psnMaxIssueCount" class="ui-input num-unit100 per1psnMaxIssue" title="횟 수 입력" disabled maxlength="4" value="${prCoupon.per1psnMaxIssueCount}">
												<span class="text">회 제한</span>
											</span>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">상품금액 제한</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="minLimitSellAmt" class="ui-input num-unit100000000" title="금액 입력" maxlength="8" value="${prCoupon.minLimitSellAmt}">
									<span class="text">원 이상 상품 구매 시, 사용가능</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
							<th scope="row">
								<span class="th-required">할인제외 감가율</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="limitDscntRate" class="ui-input num-unit100" maxlength="3" title="감가율 입력" value="${prCoupon.limitDscntRate}">
									<span class="text">% 초과 할인상품 쿠폰적용 불가</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">쿠폰 적용범위</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCoupon01" name="cpnApplyType" type="radio" ${prCoupon.cpnApplyType eq 'P' or empty prCoupon.cpnApplyType ? 'checked' : ''} value="P">
											<label for="radioCoupon01">상품</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCoupon02" name="cpnApplyType" type="radio" ${prCoupon.cpnApplyType eq 'C' ? 'checked' : ''} value="C">
											<label for="radioCoupon02">자사 상품 전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCoupon03" name="cpnApplyType" type="radio" ${prCoupon.cpnApplyType eq 'B' ? 'checked' : ''} value="B">
											<label for="radioCoupon03">입점사 상품 전체</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 쿠폰유형 > 할인쿠폰, 제휴사 할인쿠폰, 품절보상쿠폰 인 경우 노출되는 영역 -->
						<tr class="couponDscntArea cpnTypeArea">
							<th scope="row">
								<span class="th-required">할인형태</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleType02" name="dscntType" type="radio" ${prCoupon.dscntType eq 'R' or empty prCoupon.dscntType ? 'checked' : ''} value="R">
											<label for="radioSaleType02">정률 할인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleType03" name="dscntType" type="radio" ${prCoupon.dscntType eq 'A' ? 'checked' : ''} value="A">
											<label for="radioSaleType03">정액 할인</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">할인율(액)</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<input type="text" name="dscntValue" class="ui-input num-unit100000000" title="할인율(액) 입력" value="${prCoupon.dscntValue}">
									<span class="text">%(원)</span>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr class="couponDscntArea cpnTypeArea">
							<th scope="row">
								<span class="th-required">최대 할인금액 제한</span>
							</th>
							<td class="input">
								<!-- DESC : 할인형태 > 정액할인 선택시 input 영역 모두 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleLimit01" name="maxDscntLimitYn" type="radio" ${prCoupon.maxDscntLimitYn eq 'Y' or empty prCoupon.maxDscntLimitYn ? 'checked' : ''} value="Y">
											<label for="radioSaleLimit01">제한</label>
										</span>
										<span class="ip-text-box">
											<span class="text">최대</span>
											<input type="text" name="maxDscntLimitAmt" class="ui-input num-unit100000000 maxDscntLimit" title="금액 입력" value="${prCoupon.maxDscntLimitAmt}">
											<span class="text">원 할인</span>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSaleLimit02" name="maxDscntLimitYn" type="radio" ${prCoupon.maxDscntLimitYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioSaleLimit02">제한안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">상품당 쿠폰적용 <br /> 수량 제한</span>
							</th>
							<td class="input">
								<!-- DESC : 할인형태 > 정액할인 선택시 input 영역 모두 disabled 속성 추가 해주세요 -->
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCouponCountLimit01" name="maxCpnApplyYn" type="radio" ${prCoupon.maxCpnApplyYn eq 'Y' or empty prCoupon.maxCpnApplyYn ? 'checked' : ''} value="Y">
											<label for="radioCouponCountLimit01">제한</label>
										</span>
										<span class="ip-text-box">
											<span class="text">최대</span>
											<input type="text" name="maxCpnApplyQty" class="ui-input num-unit100000000 maxCpnApply" title="갯수 입력" maxlength="3" value="${prCoupon.maxCpnApplyQty}">
											<span class="text">족(개)까지 쿠폰적용</span>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioCouponCountLimit02" name="maxCpnApplyYn" type="radio" ${prCoupon.maxCpnApplyYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioCouponCountLimit02">제한안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<!-- S : 쿠폰유형 > 할인쿠폰, 제휴사 할인쿠폰, 품절보상쿠폰 인 경우 노출되는 영역 -->
						<tr>
							<th scope="row">쿠폰설명</th>
							<td class="input" colspan="3">
								<input type="text" name="cpnDescText" class="ui-input" title="설명 입력" maxlength="500" value="${prCoupon.cpnDescText}">
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header couponApplyItemArea couponApplyArea">
					<div class="fl">
						<h3 class="content-title">대상 상품 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : 적용대상목록 > 상품 선택시 -->
				<!-- S : tbl-controller -->
				<div class="tbl-controller couponApplyItemArea couponApplyArea">
					<div class="fl">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="targetProducExcelCouponSampleDownLoad">엑셀폼 다운로드</a>
						<a href="javascript:void(0);" class="btn-sm btn-func" id="targetProductExcelUpLoad">파일업로드</a>
						<span class="opt-group">
							<label class="title" for="pageCountTagetProduct">목록개수</label>
							<select class="ui-sel" id="pageCountTagetProduct" name="pageCountTagetProduct">
								<option value="100">100개 보기</option>
								<option value="200">200개 보기</option>
								<option value="300">300개 보기</option>
								<option value="400">400개 보기</option>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del productDelBtn" data-type="product1list">선택삭제</a>
						<a href="javascript:void(0);" class="btn-sm btn-func productSearchPop" data-type="product1list">상품검색 추가</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div id="product1Sheet" class="ibsheet-wrap couponApplyItemArea couponApplyArea"></div>
				<!-- E : ibsheet-wrap -->
				<!-- E : 적용대상목록 > 상품 선택시 -->

				<!-- S : tbl-row -->
				<%-- <table class="tbl-row">
					<caption>적용 대상 목록</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<!-- S : 적용대상목록 > 카테고리 선택시 -->
						<!-- S : 20190215 수정 // 채널선택 option 추가, select disabled 속성 추가 -->
						<tr class="couponApplyCategoryArea couponApplyArea">
							<th scope="row" rowspan="2">카테고리</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box dp-category-area">
									<select class="ui-sel dp-category-select chnnl-no" title="채널 선택"></select>
									<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택"><option value="">대카테고리 선택</option></select>
									<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택"><option value="">중카테고리 선택</option></select>
									<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택"><option value="">소카테고리 선택</option></select>
									<a href="javascript:void(0);" id="addCategory" class="btn-sm btn-func">추가</a>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<!-- E : 20190215 수정 // 채널선택 option 추가, select disabled 속성 추가 -->
						<tr class="couponApplyCategoryArea couponApplyArea">
							<td class="input">
								<!-- S : item-list -->
								<ul id="categoryArea" class="item-list">
									<c:forEach var="row" items="${prCouponApplyCategoryList}">
										<li>
											<span class="subject">[${row.chnnlName}] ${row.ctgrNamePath}</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
											</button>
											<input type="hidden" name="prCouponApplyCategoryArr.ctgrNo" value="${row.ctgrNo}" />
											<input type="hidden" name="prPromotionTargetCategoryArr.chnnlNo" value="${row.chnnlNo}" />
										</li>
									</c:forEach>
								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
						<!-- E : 적용대상목록 > 카테고리 선택시 -->
						<!-- S : 적용대상목록 > 브랜드 선택시 -->
						<tr class="couponApplyBrandArea couponApplyArea">
							<th scope="row" rowspan="2">브랜드</th>
							<td class="input">
								<a href="javascript:void(0);" id="searchBrand" class="btn-sm btn-link">브랜드 찾기</a>
							</td>
						</tr>
						<tr class="couponApplyBrandArea couponApplyArea">
							<td class="input">
								<!-- S : item-list -->
								<ul id="brandArea" class="item-list">
									<c:forEach var="row" items="${prCouponApplyBrandList}">
										<li>
											<span class="subject">${row.brandName}(row.brandNo)</span>
											<button type="button" class="btn-item-del">
												<span class="ico ico-item-del"><span class="offscreen">브랜드 삭제</span></span>
											</button>
										</li>
									</c:forEach>
								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
						<!-- E : 적용대상목록 > 브랜드 선택시 -->
					</tbody>
				</table> --%>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header couponApplyArea couponApplyVendorArea">
					<div class="fl">
						<h3 class="content-title">대상 입점사 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<%-- <table class="tbl-row">
					<caption>입점사 등록</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">입점사 진행여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStore02-01" name="vndrApplyYn" type="radio" ${prCoupon.vndrApplyYn eq 'Y' or empty prCoupon.vndrApplyYn ? 'checked' : ''} value="Y">
											<label for="radioStore02-01">진행</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioStore02-02" name="vndrApplyYn" type="radio" ${prCoupon.vndrApplyYn eq 'N' ? 'checked' : ''} value="N">
											<label for="radioStore02-02">진행안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">입점사</th>
							<td class="input">
								<!-- S : tbl-header -->
								<div class="tbl-header">
									<!-- TODO : Search Drop down 기능 협의 후 수정 -->
									<!-- S : search-dropdown-box -->
									<span class="search-dropdown-box"><button type="button" id="addVendor" class="btn-sm btn-func">추가</button></span>&nbsp;
									<!-- E : search-dropdown-box -->
								</div>
								<!-- E : tbl-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div id="couponVendorSheet"></div>
								</div>
								<!-- E : ibsheet-wrap -->
							</td>
						</tr>
					</tbody>
				</table> --%>
				<div class="tbl-controller couponApplyArea couponApplyVendorArea">
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del vendorDelBtn couponApplyArea couponApplyVendorChild">선택삭제</a>
						<a href="javascript:void(0);" id="addVendor" class="btn-sm btn-func couponApplyArea couponApplyVendorChild">입점사 추가</a>
						<span class="ip-text-box">
							<input type="text" id="shareRateVal" class="ui-input num-unit100000000" title="분담율 입력" placeholder="분담율(%)" maxlength="3">
							<a href="javascript:void(0);" id="shareRateBtn" class="btn-sm btn-func">분담율 적용</a>
						</span>
					</div>
				</div>

				<div id="couponVendorSheet" class="couponApplyArea couponApplyVendorArea"></div>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header couponApplyArea couponApplyItemExceptArea">
					<div class="fl">
						<h3 class="content-title">제외 대상 상품</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller couponApplyArea couponApplyItemExceptArea">
					<div class="fl">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="lessProductExcelCouponSampleDownLoad">엑셀폼 다운로드</a>
						<a href="javascript:void(0);" class="btn-sm btn-func" id="lessProductExcelUpLoad">파일업로드</a>
						<span class="opt-group">
							<label class="title" for="pageCountLessProduct">목록개수</label>
							<select class="ui-sel" id="pageCountLessProduct" name="pageCountLessProduct">
								<option value="100">100개 보기</option>
								<option value="200">200개 보기</option>
								<option value="300">300개 보기</option>
								<option value="400">400개 보기</option>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del productDelBtn" data-type="product2list">선택삭제</a>
						<a href="javascript:void(0);" class="btn-sm btn-func productSearchPop" data-type="product2list">상품검색 추가</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div id="product2Sheet" class="ibsheet-wrap couponApplyArea couponApplyItemExceptArea"></div>
				<!-- E : ibsheet-wrap -->

				<!-- S : tbl-row -->
				<c:if test="${not empty prCoupon.cpnNo}">
					<table class="tbl-row">
						<caption>쿠폰 등록 작성 정보</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${prCoupon.rgsterNo}">${prCoupon.rgsterInfo}</a></td>
								<th scope="row">작성일시</th>
								<td><fmt:formatDate value="${prCoupon.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:void(0);" class="adminDetailPop" data-admin-no="${prCoupon.moderNo}">${prCoupon.moderInfo}</a></td>
								<th scope="row">수정일시</th>
								<td><fmt:formatDate value="${prCoupon.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fr">
						<a href="/promotion/coupon" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<!-- <a href="/promotion/coupon" class="btn-lg btn-del">취소</a> -->
					<c:set var="now" value="<%=new java.util.Date()%>" />
					<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" var="nowDate" />
					<fmt:formatDate value="${prCoupon.issueEndDtm}" pattern="yyyyMMddHHmmss" var="issueEndDate"/>
					<a href="javascript:void(0);" id="save-coupon" class="btn-lg btn-save">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
	</form>
	<template id="LiTemplate">
		<li>
			<span class="subject"></span>
			<button type="button" class="btn-item-del">
				<span class="ico ico-item-del"><span class="offscreen">삭제</span></span>
			</button>
		</li>
	</template>
<script type="text/javascript">
	$(function() {
		abc.biz.promotion.coupon.product.codeCombo = ${codeCombo};
	})
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.coupon.vendor.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.coupon.product.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.coupon.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>