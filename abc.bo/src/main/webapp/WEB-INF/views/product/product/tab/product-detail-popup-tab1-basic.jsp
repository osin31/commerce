<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="PRDT_TYPE_CODE_FREEGIFT" value="10003"/>
<c:set var="PRDT_TYPE_CODE_ONLINE_ONLY" value="10001"/>
<div class="tab-content">
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">기본 정보</h3>
		</div>
	</div>

	<table class="tbl-row">
		<caption>기본 정보</caption>
		<colgroup>
			<col style="width:140px;"/>
			<col/>
			<col style="width:140px;"/>
			<col/>
		</colgroup>
		<tbody>
			<%-- 상품코드(내부관리번호) --%>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<input type="hidden" name="vndrPrdtNoText" value="${vndrPrdtNoText }"/>
				</c:when>
				<c:otherwise>
					<tr>
						<th scope="row">업체상품코드</th>
						<td class="input" colspan="3">
							<input type="text" class="ui-input" title="상품코드 입력" name="vndrPrdtNoText" value="${vndrPrdtNoText }" />
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<%-- 온라인/매장전용 --%>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<tr>
						<th scope="row">판매처 구분</th>
						<td class="input">
							<ul class="ip-box-list">
								<c:forEach items="${prdtTypeCodeList }" var="item" varStatus="status">
									<c:if test="${item.addInfo1 ne 'N' and item.codeDtlNo ne PRDT_TYPE_CODE_FREEGIFT }"><!-- 미노출항목 필터 -->
										<li>
											<span class="ui-rdo">
												<input id="prdt-type-code-${item.codeDtlNo }" name="prdtTypeCode" type="radio" value="${item.codeDtlNo }"<c:if test="${item.codeDtlNo eq product.prdtTypeCode or (status.first and (empty product or empty product.prdtTypeCode)) }"> checked</c:if> />
												<label for="prdt-type-code-${item.codeDtlNo }">${item.codeDtlName }</label>
											</span>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</td>
						<th scope="row">매장등급</th>
						<td>
							<c:set var="productBasicDispFlagText" value=""/>
							<c:if test="${not empty product and not empty product.dispFlagText }">
								<c:forEach items="${Const.DISP_FLAG_TEXT_OF_ST }" var="item">
									<c:if test="${item eq product.dispFlagText }">
										<c:set var="productBasicDispFlagText" value="ST"/>
									</c:if>
								</c:forEach>
								<c:forEach items="${Const.DISP_FLAG_TEXT_OF_MS }" var="item">
									<c:if test="${item eq product.dispFlagText }">
										<c:set var="productBasicDispFlagText" value="${productBasicDispFlagText } MS"/>
									</c:if>
								</c:forEach>
								<c:forEach items="${Const.DISP_FLAG_TEXT_OF_GS }" var="item">
									<c:if test="${item eq product.dispFlagText }">
										<c:set var="productBasicDispFlagText" value="${productBasicDispFlagText } GS"/>
									</c:if>
								</c:forEach>
								<c:forEach items="${Const.DISP_FLAG_TEXT_OF_MG }" var="item">
									<c:if test="${item eq product.dispFlagText }">
										<c:set var="productBasicDispFlagText" value="${productBasicDispFlagText } MG"/>
									</c:if>
								</c:forEach>
							</c:if>
							<c:set var="productBasicDispFlagText" value="${fn:trim(productBasicDispFlagText) }"/><%-- 앞뒤 공백 제거 --%>
							<c:set var="productBasicDispFlagText" value="${fn:replace(productBasicDispFlagText, ' ', ', ') }"/><!-- 중간 공백에 콤마 추가 -->
							<c:out value="${productBasicDispFlagText }"/><c:if test="product.tierFlagCodeName != null or product.tierFlagCodeName != ''">(${product.tierFlagCodeName})</c:if>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="prdtTypeCode" value="${PRDT_TYPE_CODE_ONLINE_ONLY }" />
				</c:otherwise>
			</c:choose>
			<tr>
				<th scope="row"><span class="th-required">상품명 (국문)</span></th>
				<td class="input">
					<input type="text" class="ui-input" title="상품명 입력" placeholder="상품명 (국문)" name="prdtName" maxlength="50" value="${product.prdtName }"/>
				</td>
				<th scope="row"><span class="th-required">상품명 (영문)</span></th>
				<td class="input">
					<input type="text" class="ui-input" title="상품명 입력" placeholder="상품명 (영문)" name="engPrdtName" maxlength="50" value="${product.engPrdtName }"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="th-required">판매상태</span></th>
				<td class="input">
					<ul class="ip-box-list">
						<c:forEach items="${sellStatCodeList }" var="item" varStatus="status">
							<c:set var="checked" value=""/><%-- 수정일 때, checked를 출력하기 위한 변수 --%>
							<li>
								<span class="ui-rdo">
									<input id="sell-stat-code-${item.codeDtlNo }" name="sellStatCode" type="radio" value="${item.codeDtlNo }"<c:if test="${product.sellStatCode eq item.codeDtlNo or (status.first and empty product) }"> checked</c:if>/>
									<label for="sell-stat-code-${item.codeDtlNo }">${item.codeDtlName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
				<th scope="row"><span class="th-required">브랜드</span></th>
				<td class="input">
					<span class="ip-search-box">
						<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" value="${product.brandName }" data-button-popup="find-brand" readonly/>
						<input type="hidden" id="brand-no" name="brandNo" value="${product.brandNo }"/>
						<a href="javascript:void(0);" class="btn-search" name="brandName" data-button-popup="find-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="th-required">판매기간</span></th>
				<td class="input">
					<input type="hidden" id="sell-start-dtm" name="sellStartDtm" value="${product.sellStartDtm }"/>
					<input type="hidden" id="sell-end-dtm" name="sellEndDtm" value="${product.sellEndDtm }"/>
					<fmt:formatDate var="sellStartDtmDate" pattern="yyyy.MM.dd" value="${product.sellStartDtm }"/>
					<fmt:formatDate var="sellStartDtmHour" pattern="HH" value="${product.sellStartDtm }"/>
					<fmt:formatDate var="sellStartDtmMinute" pattern="mm" value="${product.sellStartDtm }"/>
					<fmt:formatDate var="sellEndDtmDate" pattern="yyyy.MM.dd" value="${product.sellEndDtm }"/>
					<fmt:formatDate var="sellEndDtmHour" pattern="HH" value="${product.sellEndDtm }"/>
					<fmt:formatDate var="sellEndDtmMinute" pattern="mm" value="${product.sellEndDtm }"/>
					<span class="term-date-wrap">
						<span class="date-box">
						${sellStartDtmHour }..${sellStartDtmMinute }
							<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="날짜 선택" id="sell-start-dtm-date" value="${sellStartDtmDate }"/>
						</span>
						<select class="ui-sel" title="시작시 선택" id="sell-start-dtm-hour">
							<c:forTokens items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" delims="," var="item">
								<option value="${item }"${item eq sellStartDtmHour ? ' selected' : '' }>${item }시</option>
							</c:forTokens>
						</select>
						<select class="ui-sel" title="시작분 선택" id="sell-start-dtm-minute">
							<c:forTokens items="00,10,20,30,40,50" delims="," var="item">
								<option value="${item }"${item eq sellStartDtmMinute ? ' selected' : '' }>${item }분</option>
							</c:forTokens>
						</select>
						<span class="text">~</span>
						<span class="date-box">
							<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료 날짜 선택" id="sell-end-dtm-date" value="${sellEndDtmDate }"/>
						</span>
						<select class="ui-sel" title="종료시 선택" id="sell-end-dtm-hour">
							<c:forTokens items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" delims="," var="item">
								<option value="${item }"${item eq sellEndDtmHour ? ' selected' : '' }>${item }시</option>
							</c:forTokens>
						</select>
						<select class="ui-sel" title="종료분 선택" id="sell-end-dtm-minute">
							<c:forTokens items="00,10,20,30,40,50" delims="," var="item">
								<option value="${item }"${item eq sellEndDtmMinute ? ' selected' : '' }>${item }분</option>
							</c:forTokens>
						</select>
					</span>
				</td>
				<th scope="row"><span>출시일 여부</span></th>
				<td class="input">
					<input type="hidden" id="relis-todo-dtm" name="relisTodoDtm" value="${product.relisTodoDtm }"/>
					<fmt:formatDate var="relisTodoDtmDate" pattern="yyyy.MM.dd" value="${product.relisTodoDtm }"/>
					<fmt:formatDate var="relisTodoDtmHour" pattern="HH" value="${product.relisTodoDtm }"/>
					<fmt:formatDate var="relisTodoDtmMinute" pattern="mm" value="${product.relisTodoDtm }"/>
					<span class="ui-chk">
						<input type="checkbox" id="relis-todo-yn-y"${product.relisTodoYn eq 'Y' ? ' checked' : '' }/>
						<label for="relis-todo-yn-y">출시일</label>
						<input type="hidden" id="relis-todo-yn" name="relisTodoYn" value="${product.relisTodoYn }"/>
					</span>
					<span class="term-date-wrap" data-toggle="relis-todo-dtm">
						<span class="date-box">
							<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="출시일 선택" id="relis-todo-dtm-date" value="${relisTodoDtmDate }" placeholder="출시일"/>
						</span>
						<select class="ui-sel" title="출시일 시간 선택" id="relis-todo-dtm-hour">
							<c:forTokens items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" delims="," var="item">
								<option value="${item }"${item eq relisTodoDtmHour ? ' selected' : '' }>${item }시</option>
							</c:forTokens>
						</select>
						<select class="ui-sel" title="출시일 분 선택" id="relis-todo-dtm-minute">
							<c:forTokens items="00,10,20,30,40,50" delims="," var="item">
								<option value="${item }"${item eq relisTodoDtmMinute ? ' selected' : '' }>${item }분</option>
							</c:forTokens>
						</select>
					</span>
				</td>
			</tr>
<%-- 표준 카테고리 --%>
			<tr>
				<th scope="row">
					<span class="th-required">표준 카테고리</span>
				</th>
				<td class="input">
					<input type="hidden" id="std-ctgr-no" value="${product.stdCtgrNo }"/><%-- 표준카테고리번호 --%>
					<span class="ip-text-box stdCtgrArea">
						<select class="ui-sel stdCtgrSel 1depth" title="대카테고리 선택" id="std-ctgr-no-depth-1">
							<option value="">대카테고리 선택</option>
						</select>
						<select class="ui-sel stdCtgrSel 2depth" title="중카테고리 선택">
							<option value="">중카테고리 선택</option>
						</select>
						<select class="ui-sel stdCtgrSel 3depth" title="소카테고리 선택" name="stdCtgrNo" id="prdDtdStdCtgrNo">
							<option value="">소카테고리 선택</option>
						</select>
					</span>
				</td>
<%-- 예약상품 여부 --%>
				<th scope="row">예약상품 여부</th>
				<td class="input">
					<span class="ui-chk">
						<input type="checkbox" id="rsv-prdt-yn-y"${product.rsvPrdtYn eq 'Y' ? ' checked' : '' }/>
						<label for="rsv-prdt-yn-y">예약상품</label>
						<input type="hidden" id="rsv-prdt-yn" name="rsvPrdtYn" value="${product.rsvPrdtYn }"/>
					</span>
					<span class="date-box" data-toggle="rsv-dlvy-ymd">
						<label for="rsv-dlvy-ymd">예상출고일</label>
						<fmt:formatDate var="rsvDlvyYmd" pattern="yyyy.MM.dd" value="${product.rsvDlvyYmd }"/>
						<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="예약출고일 선택" id="rsv-dlvy-ymd" name="rsvDlvyYmd" value="${rsvDlvyYmd }" placeholder="예상출고일"/>
					</span>
				</td>
			</tr>
<%-- 제조사 --%>
			<tr>
				<th scope="row">
					<span class="th-required">제조사</span>
				</th>
				<td class="input">
					<input type="text" class="ui-input" placeholder="제조사" title="제조사 입력" name="mnftrName" maxlength="25" value="${product.mnftrName }"/>
				</td>
				<th scope="row">
					<span class="th-required">제조국/원산지</span>
				</th>
				<td class="input">
					<select class="ui-sel" title="제조국/원산지 선택" name="orgPlaceCode">
						<option value="">선택하세요</option>
						<c:forEach items="${orgPlaceCodeList }" var="item">
							<option value="${item.codeDtlNo }"<c:if test="${item.codeDtlNo eq product.orgPlaceCode }"> selected</c:if>>${item.codeDtlName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
<%-- 스타일 --%>
			<tr>
				<th scope="row">
					<span class="th-required">스타일</span>
				</th>
				<td class="input">
					<input type="text" class="ui-input" title="스타일 입력" name="styleInfo" maxlength="25" value="${product.styleInfo }"/>
				</td>
				<th scope="row">
					<span class="th-required">색상코드</span>
				</th>
				<td class="input">
					<input type="text" class="ui-input" title="색상코드 입력" name="prdtColorInfo" maxlength="50" value="${product.prdtColorInfo }" placeholder="${product.prdtColorInfo }"/>
				</td>
			</tr>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<tr>
<%-- 제휴사전송여부 --%>
						<th scope="row">
							<span class="th-required">제휴사 전송 여부</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="afflts-send-yn-y" name="affltsSendYn" type="radio" value="Y"<c:if test="${product.affltsSendYn eq 'Y' }"> checked</c:if>/>
										<label for="afflts-send-yn-y">전송</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="afflts-send-yn-n" name="affltsSendYn" type="radio" value="N"<c:if test="${product.affltsSendYn ne 'Y' }"> checked</c:if>/>
										<label for="afflts-send-yn-n">전송안함</label>
									</span>
								</li>
							</ul>
						</td>
<%-- 사방넷연동여부 --%>
						<th scope="row">
							<span class="th-required">사방넷 연동 여부</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="sabangnet-send-yn-y" name="sabangnetSendYn" type="radio" value="Y"<c:if test="${product.sabangnetSendYn eq 'Y' }"> checked</c:if>/>
										<label for="sabangnet-send-yn-y">연동</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="sabangnet-send-yn-n" name="sabangnetSendYn" type="radio" value="N"<c:if test="${product.sabangnetSendYn ne 'Y' }"> checked</c:if>/>
										<label for="sabangnet-send-yn-n">연동안함</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="affltsSendYn" value="N"/>
					<input type="hidden" name="sabangnetSendYn" value="N"/>
				</c:otherwise>
			</c:choose>
<%-- 테마/성별 --%>
			<tr>
				<th scope="row">
					<span class="th-required">테마/성별</span>
				</th>
				<td class="input" colspan="3">
					<ul class="ip-box-list">
						<c:forEach items="${genderGbnCodeList }" var="item">
							<li>
								<span class="ui-rdo">
									<input type="radio" id="gender-gbn-code-${item.codeDtlNo }" name="genderGbnCode" value="${item.codeDtlNo }"<c:if test="${product.genderGbnCode eq item.codeDtlNo or (empty product.genderGbnCode and item.codeDtlNo eq '10003') }"> checked</c:if>/>${item.codeDtlName }
									<label for="gender-gbn-code-${item.codeDtlNo }">${item.codeDtlName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>

	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">가격 정보</h3>
		</div>
	</div>

	<table class="tbl-row">
		<caption>가격 정보</caption>
		<colgroup>
			<col style="width:140px;"/>
			<col/>
			<col style="width:140px;"/>
			<col/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">가격정보</th>
				<td class="input" colspan="3">
					<div class="tbl-header">
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="search-price-history">가격 변경이력 조회</a>
						</div>
					</div>
					<c:choose>
						<c:when test="${product.mmnyPrdtYn eq 'Y' }">
							<table class="tbl-col">
								<caption>가격정보</caption>
								<colgroup>
									<col/>
									<col/>
									<col/>
									<col/>
									<col/>
								</colgroup>
								<thead>
									<tr>
										<th scope="col">정상가</th>
										<th scope="col">임직원가 (할인율)</th>
										<th scope="col">오프라인 판매가 (할인율)</th><%-- 기간계 --%>
										<th scope="col">온라인 할인 적용가 (할인율)</th>
										<th scope="col">온라인 노출 판매가 (할인율)</th>
									</tr>
								</thead>
								<c:set var="productBasicPriceNormalAmt" value="${product.productPriceHistory[0].normalAmt }" />
								<c:set var="productBasicPriceSellAmt" value="${product.productPriceHistory[0].sellAmt }" />
								<c:set var="productBasicPriceErpSellAmt" value="${product.productPriceHistory[0].erpSellAmt }" />
								<c:set var="productBasicPriceErpDscntRate" value="${product.productPriceHistory[0].erpDscntRate }" />
								<c:set var="productBasicPriceEmpDscntRate" value="${product.productPriceHistory[0].empDscntRate }" />
								<c:set var="productBasicPriceOnlnSellAmt" value="${product.productPriceHistory[0].onlnSellAmt }" />
								<c:set var="productBasicPriceOnlnDscntRate" value="${product.productPriceHistory[0].onlnDscntRate }" />
								<c:set var="productBasicPriceDisplayPrice" value="${product.productPriceHistory[0].displayProductPrice }" />
								<c:set var="productBasicPriceDisplayRate" value="${product.productPriceHistory[0].displayDiscountRate }" />
								<tbody>
									<tr>
										<td class="text-center"><%-- 정상가 --%>
											<input type="hidden" name="productPriceHistory.divider" value="" />
											<input type="hidden" name="productPriceHistory.mmnyPrdtYn" value="${product.mmnyPrdtYn }" />
											<input type="hidden" id="normal-amt" name="productPriceHistory.normalAmt" value="${productBasicPriceNormalAmt }" />
											<fmt:formatNumber value="${productBasicPriceNormalAmt }" groupingUsed="true"/>원
										</td>
										<td class="text-center"><%-- 임직원할인율 --%>
											<fmt:formatNumber value="${productBasicPriceNormalAmt - (productBasicPriceNormalAmt * (productBasicPriceEmpDscntRate / 100)) }" groupingUsed="true"/>원
											&nbsp;/&nbsp;
											<fmt:formatNumber value="${not empty productBasicPriceEmpDscntRate ? productBasicPriceEmpDscntRate / 100 : '0' }" type="percent"/>
											<input type="hidden" name="productPriceHistory.empDscntRate" value="${productBasicPriceEmpDscntRate }"/>
										</td>
										<td class="text-center"><%-- 기간계할인율 --%>
											<fmt:formatNumber value="${productBasicPriceErpSellAmt }" groupingUsed="true"/>원
											&nbsp;/&nbsp;
											<fmt:formatNumber value="${not empty productBasicPriceErpDscntRate ? productBasicPriceErpDscntRate / 100 : '0' }" type="percent"/>
											<input type="hidden" name="productPriceHistory.erpDscntRate" value="${productBasicPriceErpDscntRate }"/>
											<input type="hidden" name="productPriceHistory.erpSellAmt" value="${productBasicPriceErpSellAmt }"/>
										</td>
										<td class="text-center"><%-- 온라인할인율 --%>
											<c:choose>
												<c:when test="${not empty productBasicPriceDisplayPrice and not empty productBasicPriceDisplayRate }">
													<fmt:formatNumber value="${productBasicPriceDisplayPrice }" groupingUsed="true"/>원
													&nbsp;/&nbsp;
													<fmt:formatNumber value="${not empty productBasicPriceDisplayRate ? productBasicPriceDisplayRate / 100 : '0' }" type="percent"/>
													<input type="hidden" id="onln-dscnt-rate" name="productPriceHistory.onlnDscntRate" value="${productBasicPriceDisplayRate }" />
													<input type="hidden" id="onln-sell-amt" name="productPriceHistory.onlnSellAmt" value="${productBasicPriceDisplayPrice }"/>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${productBasicPriceOnlnSellAmt }" groupingUsed="true"/>원
													&nbsp;/&nbsp;
													<fmt:formatNumber value="${not empty productBasicPriceOnlnDscntRate ? productBasicPriceOnlnDscntRate / 100 : '0' }" type="percent"/>
													<input type="hidden" id="onln-dscnt-rate" name="productPriceHistory.onlnDscntRate" value="${productBasicPriceOnlnDscntRate }" />
													<input type="hidden" id="onln-sell-amt" name="productPriceHistory.onlnSellAmt" value="${productBasicPriceOnlnSellAmt }"/>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="text-center"><%-- 온라인 판매가 --%>
											<c:choose>
												<c:when test="${not empty productBasicPriceDisplayPrice and not empty productBasicPriceDisplayRate }">
													<c:choose>
														<c:when test="${productBasicPriceErpSellAmt < productBasicPriceOnlnSellAmt && productBasicPriceErpSellAmt ne 0}">
															<fmt:formatNumber value="${not empty productBasicPriceErpSellAmt ? productBasicPriceErpSellAmt : '0' }" groupingUsed="true"/>원
																&nbsp;/&nbsp;
															<fmt:formatNumber value="${not empty productBasicPriceErpDscntRate ? productBasicPriceErpDscntRate / 100 : '0' }" type="percent"/>
														</c:when>
														<c:when test="${productBasicPriceErpSellAmt > productBasicPriceOnlnSellAmt && productBasicPriceOnlnSellAmt ne 0}">
															<fmt:formatNumber value="${not empty productBasicPriceOnlnSellAmt ? productBasicPriceOnlnSellAmt : '0' }" groupingUsed="true"/>원
																&nbsp;/&nbsp;
															<fmt:formatNumber value="${not empty productBasicPriceOnlnDscntRate ? productBasicPriceOnlnDscntRate / 100 : '0' }" type="percent"/>
														</c:when>
														<c:when test="${productBasicPriceErpSellAmt eq productBasicPriceOnlnSellAmt}">
															<fmt:formatNumber value="${not empty productBasicPriceDisplayPrice ? productBasicPriceDisplayPrice : '0' }" groupingUsed="true"/>원
																&nbsp;/&nbsp;
															<fmt:formatNumber value="${not empty productBasicPriceDisplayRate ? productBasicPriceDisplayRate / 100 : '0' }" type="percent"/>
														</c:when>
													</c:choose>
												</c:when>
												<c:otherwise>
													-<%-- 최초등록 시에는 프론트 노출 가격을 가림 --%>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<table class="tbl-col">
								<caption>가격정보</caption>
								<colgroup>
									<col/>
									<col/>
									<!-- 2020-01-02 업체상품은 임직원가 미노출 요청 <col/> -->
									<col/>
									<col/>
								</colgroup>
								<thead>
									<tr>
										<th scope="col">정상가</th>
										<th scope="col">판매가</th>
										<!--2020-01-02 업체상품은 임직원가 미노출 요청 <th scope="col">임직원가 (할인율)</th> -->
										<th scope="col">수수료율</th>
										<th scope="col">온라인할인 노출가 (할인율)</th>
									</tr>
								</thead>
								<c:set var="productBasicPriceNormalAmt" value="${product.productPriceHistory[0].normalAmt }" />
								<c:set var="productBasicPriceOnlnSellAmt" value="${product.productPriceHistory[0].sellAmt }" />
								<c:set var="productBasicPriceOnlnDscntRate" value="${product.productPriceHistory[0].onlnDscntRate }" />
								<c:set var="productBasicPriceEmpDscntRate" value="${product.productPriceHistory[0].empDscntRate }" />
								<c:set var="productBasicPriceDisplayPrice" value="${product.productPriceHistory[0].displayProductPrice }" />
								<c:set var="productBasicPriceDisplayRate" value="${product.productPriceHistory[0].displayDiscountRate }" />
								<input type="hidden" id="orgPriceChangeYn" name="orgPriceChangeYn" value="N"/>
								<tbody>
									<tr>
										<td class="text-center"><%-- 정상가 --%>
											<input type="hidden" name="productPriceHistory.divider" value="" />
											<input type="hidden" name="productPriceHistory.mmnyPrdtYn" value="${product.mmnyPrdtYn }" />
											<input type="text" class="ui-input text-center" title="정상가" id="normal-amt" name="productPriceHistory.normalAmt" value="<fmt:formatNumber value="${empty productBasicPriceNormalAmt ? '0' : productBasicPriceNormalAmt }" groupingUsed="true"/>원" data-focusout="online-discount-rate" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" maxlength="7"/>
											<input type="hidden" id="org-normal-amt" value="${empty productBasicPriceNormalAmt ? '0' : productBasicPriceNormalAmt }"/>
										</td>
										<td class="text-center"<%--  data-focusout-target="online-discount-rate" --%>><%-- 온라인할인율 --%>
											<input type="hidden" id="onln-dscnt-rate" name="productPriceHistory.onlnDscntRate" value="${productBasicPriceOnlnDscntRate }" />
											<input type="text" class="ui-input text-center" title="판매가" id="onln-sell-amt" name="productPriceHistory.onlnSellAmt" value="<fmt:formatNumber value="${empty productBasicPriceOnlnSellAmt ? '0' : productBasicPriceOnlnSellAmt }" groupingUsed="true"/>원" data-focusout="online-discount-rate" onKeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"/>
											<input type="hidden" id="org-onln-sell-amt" value="${empty productBasicPriceOnlnSellAmt ? '0' : productBasicPriceOnlnSellAmt }"/>
										</td>
<%--
임직원할인율 -2020-01-02 업체상품은 임직원가 미노출 요청
										<td class="text-center" data-product-price="employee-discount-rate">  
											${not empty productBasicPriceEmpDscntRate ? productBasicPriceEmpDscntRate : '0' }%
											&nbsp;/&nbsp;
											<fmt:formatNumber value="${productBasicPriceNormalAmt - (productBasicPriceNormalAmt * (productBasicPriceEmpDscntRate / 100)) }" groupingUsed="true"/>원
										</td>
--%>
										<td class="text-center"><%-- 수수료율 --%>
											<span data-product-price="commition-rate">0</span>%</td>
										<td class="text-center"><%-- 판매가 --%>
										<input type="hidden" id="emp-dscnt-rate" name="productPriceHistory.empDscntRate" value="${productBasicPriceEmpDscntRate }"/>
											<c:choose>
												<c:when test="${not empty productBasicPriceDisplayPrice and not empty productBasicPriceDisplayRate }">
													<fmt:formatNumber value="${not empty productBasicPriceDisplayPrice ? productBasicPriceDisplayPrice : '0' }" groupingUsed="true"/>원
													&nbsp;/&nbsp;
													<fmt:formatNumber value="${not empty productBasicPriceDisplayRate ? productBasicPriceDisplayRate / 100 : '0' }" type="percent"/>
												</c:when>
												<c:otherwise>
													-<%-- 최초등록 시에는 프론트 노출 가격을 가림 --%>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<tr>
						<th scope="row">가격 예외 적용</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<input id="emp-dscnt-yn-n" type="checkbox" value="N"<c:if test="${product.empDscntYn ne 'Y' }"> checked</c:if>/>
										<label for="emp-dscnt-yn-n">임직원 할인 제외</label>
										<input type="hidden" id="emp-dscnt-yn" name="empDscntYn" value="${product.empDscntYn }"/>
									</span>
								</li>
								<li>
									<span class="ui-chk">
										<input id="dprc-except-yn-y" type="checkbox" value="Y"<c:if test="${product.dprcExceptYn eq 'Y' }"> checked</c:if>/>
										<label for="dprc-except-yn-y">감가제외</label>
										<input type="hidden" id="dprc-except-yn" name="dprcExceptYn" value="${product.dprcExceptYn }"/>
									</span>
								</li>
							</ul>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="empDscntYn" value="N"/><%-- 입점사는 임직원 할인 사용 안함 --%>
					<input type="hidden" name="dprcExceptYn" value="Y"/><%-- 감가제외인 경우, 온라인가로 저장되므로 입점사 기본가로 설정 --%>
				</c:otherwise>
			</c:choose>
			<tr>
				<th scope="row">적용 프로모션 번호</th>
				<td data-promotion="applying"></td>
				<th scope="row">적용 쿠폰 번호</th>
				<td data-coupon="applying"></td>
			</tr>
		</tbody>
	</table>

	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">배송 정보</h3>
		</div>
	</div>

	<table class="tbl-row">
		<caption>배송 정보</caption>
		<colgroup>
			<col style="width:140px;"/>
			<col/>
			<col style="width:140px;"/>
			<col/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><span class="th-required">무료배송 상품 여부</span></th>
				<td class="input"${product.mmnyPrdtYn eq 'Y' ? '' : ' colspan="3"' }>
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="free-dlvy-yn-n" name="freeDlvyYn" type="radio" value="N"<c:if test="${product.freeDlvyYn ne 'Y' }"> checked</c:if>/>
								<label for="free-dlvy-yn-n">적용안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="free-dlvy-yn-y" name="freeDlvyYn" type="radio" value="Y"<c:if test="${product.freeDlvyYn eq 'Y' }"> checked</c:if>/>
								<label for="free-dlvy-yn-y">적용대상</label>
							</span>
						</li>
					</ul>
				</td>
				<c:choose>
					<c:when test="${product.mmnyPrdtYn eq 'Y' }"><%-- 자사인 경우 --%>
						<th scope="row">배송예외 적용</th>
							<td class="input">
								<ul class="ip-box-list">
									<%-- <li>
										<span class="ui-chk">
											<input id="order-mnfct-yn-y" type="checkbox"<c:if test="${product.orderMnfctYn eq 'Y' }"> checked</c:if>/>
											<label for="order-mnfct-yn-y">주문제작</label>
											<input type="hidden" id="order-mnfct-yn" name="orderMnfctYn" value="${product.orderMnfctYn }"/>
										</span>
									</li> --%>
									<input type="hidden" name="orderMnfctYn" value="N"/><%-- 주문제작여부 미사용으로 인한 숨김처리 --%>
									<li>
										<span class="ui-chk">
											<input id="store-pickup-psblt-yn-n" type="checkbox"<c:if test="${product.storePickupPsbltYn eq 'N' }"> checked</c:if>/>
											<label for="store-pickup-psblt-yn-n">매장픽업불가</label>
											<input type="hidden" id="store-pickup-psblt-yn" name="storePickupPsbltYn" value="${product.storePickupPsbltYn }"/>
										</span>
									</li>
								</ul>
							</td>
					</c:when>
					<c:otherwise><%-- 입점사인 경우 --%>
						<input type="hidden" name="orderMnfctYn" value="N"/>
						<input type="hidden" name="storePickupPsbltYn" value="N"/>
					</c:otherwise>
				</c:choose>
			</tr>
		</tbody>
	</table>

	<c:if test="${not empty product and not empty product.rgsterNo }">
		<table class="tbl-row">
			<caption>작성 정보</caption>
			<colgroup>
				<col style="width:140px;"/>
				<col/>
				<col style="width:140px;"/>
				<col/>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">등록자</th>
					<td>
						<c:choose>
							<c:when test="${not empty product.rgsterNo }">${product.rgsterName } (${product.rgsterId })</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</td>
					<th scope="row">등록일시</th>
					<td>
						<c:choose>
							<c:when test="${not empty product.rgsterNo }">
								<fmt:formatDate var="rgstDtm" pattern="yyyy-MM-dd hh:mm:ss" value="${product.rgstDtm }"/>
								${rgstDtm }
							</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row">최종승인자</th>
					<td>
						<c:choose>
							<c:when test="${not empty product.aprverNo }">${product.aprverName } (${product.aprverId })</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</td>
					<th scope="row">최종승인일시</th>
					<td class="input">
						<c:choose>
							<c:when test="${not empty product.aprverNo }">
								<fmt:formatDate var="aprverDtm" pattern="yyyy-MM-dd hh:mm:ss" value="${product.aprverDtm }"/>
								<span class="text">${aprverDtm }</span>
								<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="search-approvement-history">승인이력</a>
							</c:when>
							<c:otherwise>
								<span class="text">-</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row">최종수정자</th>
					<td>
						<c:choose>
							<c:when test="${not empty product.moderNo }">${product.moderName } (${product.moderId })</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</td>
					<th scope="row">최종수정일시</th>
					<td class="input">
						<c:choose>
							<c:when test="${not empty product.moderNo }">
								<fmt:formatDate var="modDtm" pattern="yyyy-MM-dd hh:mm:ss" value="${product.modDtm }"/>
								<span class="text">${modDtm }</span>
								<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="search-change-history">변경이력</a>
							</c:when>
							<c:otherwise>
								<span class="text">-</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
</div>
