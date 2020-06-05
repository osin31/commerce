<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S:tab_content -->
<div id="tabContent1" class="tab-content">
	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fr">
			<a href="javascript:void(0);" class="btn-sm btn-func" data-button="clear"><i class="ico ico-refresh"></i>초기화</a>
		</div>
	</div>
	<!-- E : tbl-controller -->
	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>기본정보</caption>
		<colgroup>
			<col style="width: 160px"/>
			<col/>
			<col style="width: 160px"/>
			<col/>
		</colgroup>
		<tbody>
<%-- 판매상태 --%>
			<tr>
				<th scope="row">판매상태</th>
				<td class="input" colspan="3">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="sell-stat-code-none" name="sellStatCode" type="radio" value="" checked/>
								<label for="sell-stat-code-none">변경안함</label>
							</span>
						</li>
						<c:forEach var="item" items="${sellStatCodes }">
							<li>
								<span class="ui-rdo">
									<input type="radio" id="sell-stat-code-${item.codeDtlNo }" name="sellStatCode" value="${item.codeDtlNo }"/>
									<label for="sell-stat-code-${item.codeDtlNo }">${item.codeDtlName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
<%-- 판매기간 --%>
			<tr>
				<th scope="row">판매기간</th>
				<td class="input" colspan="3">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="sell-period-none" name="sellPeriod" type="radio" data-clear="sell-period" checked/>
								<label for="sell-period-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="sell-period-change" name="sellPeriod" type="radio" data-toggle="sell-period"/>
								<label for="sell-period-change">변경</label>
							</span>
						</li>
						<li data-toggle-target="sell-period">
							<input type="hidden" id="sell-start-dtm" name="sellStartDtm" data-clear-target="sell-period"/>
							<input type="hidden" id="sell-end-dtm" name="sellEndDtm" data-clear-target="sell-period"/>
							<span class="term-date-wrap">
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" id="sell-start-dtm-date" data-clear-target="sell-period"/>
								</span>
								<select class="ui-sel" title="시작시 선택" id="sell-start-dtm-hour" data-clear-target="sell-period">
									<c:forTokens items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" delims="," var="item">
										<option value="${item }"${item eq sellStartDtmHour ? ' selected' : '' }>${item }시</option>
									</c:forTokens>
								</select>
								<select class="ui-sel" title="시작분 선택" id="sell-start-dtm-minute" data-clear-target="sell-period">
									<c:forTokens items="00,10,20,30,40,50" delims="," var="item">
										<option value="${item }"${item eq sellStartDtmMinute ? 'selected' : '' }>${item }분</option>
									</c:forTokens>
								</select>
								<span class="text">~</span>
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" id="sell-end-dtm-date" data-clear-target="sell-period"/>
								</span>
								<select class="ui-sel" title="종료시 선택" id="sell-end-dtm-hour" data-clear-target="sell-period">
									<c:forTokens items="00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23" delims="," var="item">
										<option value="${item }"${item eq sellEndDtmHour ? ' selected' : '' }>${item }시</option>
									</c:forTokens>
								</select>
								<select class="ui-sel" title="종료분 선택" id="sell-end-dtm-minute" data-clear-target="sell-period">
									<c:forTokens items="00,10,20,30,40,50" delims="," var="item">
										<option value="${item }"${item eq sellEndDtmMinute ? ' selected' : '' }>${item }분</option>
									</c:forTokens>
								</select>
							</span>
						</li>
					</ul>
				</td>
			</tr>
<%-- 표준카테고리는 사이즈조견표와 연관된 것이므로 변경안함 --%>
<%--
			<tr>
				<th scope="row">표준 카테고리</th>
				<td class="input" colspan="3">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="radioCategory02" name="radioCategoryModule" type="radio" checked>
								<label for="radioCategory02">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="radioCategory03" name="radioCategoryModule" type="radio">
								<label for="radioCategory03">변경</label>
							</span>
						</li>
						<li>
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select class="ui-sel" title="대카테고리 선택">
									<option>대카테고리 선택</option>
								</select>
								<select class="ui-sel" title="중카테고리 선택">
									<option>중카테고리 선택</option>
								</select>
								<select class="ui-sel" title="소카테고리 선택">
									<option>소카테고리 선택</option>
								</select>
							</span>
							<!-- E : ip-text-box -->
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
--%>
<%-- 예약상품 여부 --%>
			<tr>
				<th scope="row">예약상품 여부</th>
				<td class="input" colspan="3">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="rsv-prdt-yn-none" name="rsvPrdtYn" type="radio" data-clear="reservation-product" value="" checked/>
								<label for="rsv-prdt-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="rsv-prdt-yn-n" name="rsvPrdtYn" type="radio" value="N" data-clear="reservation-product"/>
								<label for="rsv-prdt-yn-n">일반</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="rsv-prdt-yn-y" name="rsvPrdtYn" type="radio" value="Y" data-toggle="reservation-product"/>
								<label for="rsv-prdt-yn-y">예약</label>
							</span>
							<span class="date-box" data-toggle-target="reservation-product">
								<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="예상출고일" placeholder="예상출고일" id="rsv-dlvy-ymd" name="rsvDlvyYmd" data-clear-target="reservation-product"/>
							</span>
						</li>
					</ul>
				</td>
			</tr>
<%-- 무료배송 상품 여부 --%>
			<tr>
				<th scope="row">무료배송 상품 여부</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="free-dlvy-yn-none" name="freeDlvyYn" type="radio" value="" checked/>
								<label for="free-dlvy-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="free-dlvy-yn-n" name="freeDlvyYn" type="radio" value="N"/>
								<label for="free-dlvy-yn-n">적용안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="free-dlvy-yn-y" name="freeDlvyYn" type="radio" value="Y"/>
								<label for="free-dlvy-yn-y">적용대상</label>
							</span>
						</li>
					</ul>
				</td>
<%-- 가격예외적용 --%>
				<th scope="row">가격예외적용</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="price-exception-none" name="priceException" type="radio" data-clear="price-exception" checked/>
								<label for="price-exception-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="price-exception-change" name="priceException" type="radio" data-toggle="price-exception"/>
								<label for="price-exception-change">변경</label>
							</span>
						</li>
						<li data-toggle-target="price-exception">
							<span class="ui-chk">
								<input id="emp-dscnt-yn-n" type="checkbox" data-clear-target="price-exception"/>
								<label for="emp-dscnt-yn-n">임직원 할인 제외</label>
								<input type="hidden" id="emp-dscnt-yn" name="empDscntYn" value="" class="ui-input"/>
							</span>
						</li>
						<c:if test="${auth eq 'B' }"><%-- BO메뉴인 경우 노출 --%>
							<li data-toggle-target="price-exception">
								<span class="ui-chk">
									<input id="dprc-except-yn-y" type="checkbox" data-clear-target="price-exception"/>
									<label for="dprc-except-yn-y">감가제외</label>
									<input type="hidden" id="dprc-except-yn" name="dprcExceptYn" value="" class="ui-input"/>
								</span>
							</li>
						</c:if>
					</ul>
				</td>
			</tr>
			<c:if test="${auth eq 'B' }">
<%-- 배송예외 적용 --%>
				<tr>
					<th scope="row">배송예외 적용</th>
					<td class="input">
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<input id="delivery-exception-none" name="deliveryException" type="radio" data-clear="delivery-exception" checked/>
									<label for="delivery-exception-none">변경안함</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="delivery-exception-chnage" name="deliveryException" type="radio" data-toggle="delivery-exception"/>
									<label for="delivery-exception-chnage">변경</label>
								</span>
							</li>
							<li data-toggle-target="delivery-exception">
								<span class="ui-chk">
									<input id="store-pickup-psblt-yn-n" type="checkbox" data-clear-target="delivery-exception"/>
									<label for="store-pickup-psblt-yn-n">매장픽업불가</label>
									<input type="hidden" id="store-pickup-psblt-yn" name="storePickupPsbltYn" value="" class="ui-input"/>
								</span>
							</li>
							<%--
							<li data-toggle-target="delivery-exception">
								<span class="ui-chk">
									<input id="order-mnfct-yn-y" type="checkbox" data-clear-target="delivery-exception"/>
									<label for="order-mnfct-yn-y">주문제작</label>
									<input type="hidden" id="order-mnfct-yn" name="orderMnfctYn" value="" class="ui-input"/>
								</span>
							</li>
							--%>
						</ul>
					</td>
<%-- 제휴사전송여부 --%>
					<th scope="row">제휴사 전송여부</th>
					<td class="input">
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<input id="afflts-send-yn-none" name="affltsSendYn" type="radio" value="" checked/>
									<label for="afflts-send-yn-none">변경안함</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="afflts-send-yn-n" name="affltsSendYn" type="radio" value="N"/>
									<label for="afflts-send-yn-n">전송안함</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="afflts-send-yn-y" name="affltsSendYn" type="radio" value="Y"/>
									<label for="afflts-send-yn-y">전송</label>
								</span>
							</li>
						</ul>
					</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<!-- E : tbl-row -->
</div>
<!-- E:tab_content -->