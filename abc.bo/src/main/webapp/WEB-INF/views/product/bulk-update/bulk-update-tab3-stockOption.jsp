<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S:tab_content -->
<div id="tabContent3" class="tab-content">
	<table class="tbl-row">
		<caption>재고/옵션정보</caption>
		<colgroup>
			<col style="width: 160px"/>
			<col/>
			<col style="width: 160px"/>
			<col/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"> 구매수량 제한 여부</th>
				<td class="input" colspan="3">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="buy-limit-yn-none" name="buyLimitYn" type="radio" value="" data-clear="buy-limit-yn" checked/>
								<label for="buy-limit-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="buy-limit-yn-y" name="buyLimitYn" type="radio" value="Y" data-toggle="buy-limit-yn"/>
								<label for="buy-limit-yn-y">제한함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="buy-limit-yn-n" name="buyLimitYn" type="radio" value="N" data-clear="buy-limit-yn"/>
								<label for="buy-limit-yn-n">제한없음</label>
							</span>
						</li>
						<li data-toggle-target="buy-limit-yn">
							<span class="ip-text-box">
								<span class="text">최소구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="최소구매수량 입력" id="min-buy-psblt-qty" name="minBuyPsbltQty" data-clear-target="buy-limit-yn"/>
								<span class="text">개</span>
							</span>
							<span class="ip-text-box">
								<span class="text">최대 구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="최대 구매수량 입력" id="max-buy-psblt-qty" name="maxBuyPsbltQty" data-clear-target="buy-limit-yn"/>
								<span class="text">개</span>
							</span>
							<span class="ip-text-box">
								<span class="text">1일 최대 구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="1일 최대 구매수량 입력" id="day-max-buy-psblt-qty" name="dayMaxBuyPsbltQty" data-clear-target="buy-limit-yn"/>
								<span class="text">개</span>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<c:if test="${auth eq 'B' }"><%-- BO메뉴인 경우 노출 --%>
<%-- 재입고 알림 설정 --%>
				<tr>
					<th scope="row">재입고 알림 설정</th>
					<td class="input">
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<input id="wrhs-alert-yn-none" name="wrhsAlertYn" type="radio" value="" checked/>
									<label for="wrhs-alert-yn-none">변경안함</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="wrhs-alert-yn-y" name="wrhsAlertYn" type="radio" value="Y"/>
									<label for="wrhs-alert-yn-y">설정</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="wrhs-alert-yn-n" name="wrhsAlertYn" type="radio" value="N"/>
									<label for="wrhs-alert-yn-n">설정안함</label>
								</span>
							</li>
						</ul>
					</td>
<%-- 재고 통합여부 --%>
					<th scope="row">재고 연동 여부</th>
					<td class="input">
						<ul class="ip-box-list">
							<li>
								<span class="ui-rdo">
									<input id="stock-intgr-yn-none" name="stockIntgrYn" type="radio" value="" checked/>
									<label for="stock-intgr-yn-none">변경안함</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="stock-intgr-yn-y" name="stockIntgrYn" type="radio" value="Y" data-clear="stock-intgr-yn"/>
									<label for="stock-intgr-yn-y">연동</label>
								</span>
							</li>
							<li>
								<span class="ui-rdo">
									<input id="stock-intgr-yn-n" type="radio" name="stockIntgrYn" value="N" data-toggle="stock-intgr-yn"/>
									<label for="stock-intgr-yn-n">연동안함</label>
								</span>
								<select class="ui-sel" title="선택" id="stock-un-intgr-rsn-code" name="stockUnIntgrRsnCode" data-clear-target="stock-intgr-yn" data-toggle-target="stock-intgr-yn">
									<option value="">선택</option>
									<c:forEach items="${stockUnIntgrRsnCodes }" var="item">
										<option value="${item.codeDtlNo }">${item.codeDtlName }</option>
									</c:forEach>
								</select>
							</li>
						</ul>
					</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>
<!-- E:tab_content -->