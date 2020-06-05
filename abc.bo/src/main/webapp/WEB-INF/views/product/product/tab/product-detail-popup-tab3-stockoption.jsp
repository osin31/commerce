<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="tab-content">
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">재고 / 옵션 정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>재고 / 옵션 정보</caption>
		<colgroup>
			<col style="width:160px;"/>
			<col/>
			<col style="width:160px;"/>
			<col/>
		</colgroup>
		<tbody>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<input type="hidden" name="addOptnSetupYn" value="N"/><%-- 추가옵션여부. 자사는 사용안함. 입점은 사용으로 설정 --%>
					<input type="hidden" name="stockMgmtYn" value="Y"/><%-- 재고관리여부. 자사는 기본값 Y로 설정 --%>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="addOptnSetupYn" value="Y"/><%-- 추가옵션여부. 자사는 사용안함. 입점은 사용으로 설정 --%>
					<tr>
						<th scope="row">재고 관리 여부</th>
						<td class="input" colspan="3">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="stock-mgmt-yn-y" name="stockMgmtYn" type="radio" data-radio="stock-management" value="Y"${product.stockMgmtYn ne 'N' ? ' checked' : '' }/>
										<label for="stock-mgmt-yn-y">관리</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="stock-mgmt-yn-n" name="stockMgmtYn" type="radio" data-radio="stock-management" value="N"${product.stockMgmtYn eq 'N' ? ' checked' : '' }/>
										<label for="stock-mgmt-yn-n">관리안함</label>
									</span>
								</li>
							</ul>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<th scope="row">구매 수량 제한 여부</th>
				<td class="input" colspan="3">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="buy-limit-yn-y" name="buyLimitYn" type="radio" value="Y" data-radio="buy-limit"${product.buyLimitYn eq 'Y' ? ' checked' : '' }/>
								<label for="buy-limit-yn-y">제한함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="buy-limit-yn-n" name="buyLimitYn" type="radio" value="N" data-radio="buy-limit"${product.buyLimitYn ne 'Y' ? ' checked' : '' }/>
								<label for="buy-limit-yn-n">제한없음</label>
							</span>
						</li>
						<li id="product-buy-limit-yn-n">
							<span class="ip-text-box">
								<span class="text">최소구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="최소구매수량 입력" id="min-buy-psblt-qty" name="minBuyPsbltQty" value="${empty product.minBuyPsbltQty ? '1' : product.minBuyPsbltQty }"/>
								<span class="text">개</span>
							</span>
							<span class="ip-text-box">
								<span class="text">최대 구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="최대 구매수량 입력" id="max-buy-psblt-qty" name="maxBuyPsbltQty" value="${empty product.maxBuyPsbltQty ? '1' : product.maxBuyPsbltQty }"/>
								<span class="text">개</span>
							</span>
							<span class="ip-text-box">
								<span class="text">1일 최대 구매수량</span>
								<input type="text" class="ui-input text-right num-unit10000000" title="1일 최대 구매수량 입력" id="day-max-buy-psblt-qty" name="dayMaxBuyPsbltQty" value="${empty product.dayMaxBuyPsbltQty ? '1' : product.dayMaxBuyPsbltQty }"/>
								<span class="text">개</span>
							</span>
						</li>
					</ul>
				</td>
			</tr>
			<%-- 재입고 알림 설정 / 재고 통합여부 --%>
			<c:choose>
				<c:when test="${product.mmnyPrdtYn eq 'Y' }">
					<tr>
						<th scope="row">재입고 알림 설정</th>
						<td class="input">
							<ul class="ip-box-list">
								<li>
								<span class="ui-rdo">
									<input id="wrhs-alert-yn-y" name="wrhsAlertYn" type="radio" value="Y"${product.wrhsAlertYn ne 'N' ? ' checked' : ''}/>
									<label for="wrhs-alert-yn-y">설정</label>
								</span>
								</li>
								<li>
								<span class="ui-rdo">
									<input id="wrhs-alert-yn-n" name="wrhsAlertYn" type="radio" value="N"${product.wrhsAlertYn ne 'N' ? '' : ' checked'}/>
									<label for="wrhs-alert-yn-n">설정안함</label>
								</span>
								</li>
							</ul>
						</td>
						<th scope="row">재고 연동 여부</th>
						<td class="input">
							<input type="hidden" id="default-stock-intgr-yn" value="${empty product.stockIntgrYn ? 'Y' : product.stockIntgrYn }"/>
							<input type="hidden" id="default-stock-un-untgr-rsn-code" value="${empty product.stockUnIntgrRsnCode ? '' : product.stockUnIntgrRsnCode }"/>
							<ul class="ip-box-list">
								<li>
								<span class="ui-rdo">
									<input id="stock-intgr-yn-y" name="stockIntgrYn" type="radio" value="Y"${product.stockIntgrYn eq 'Y' ? ' checked' : ''}/>
									<label for="stock-intgr-yn-y">연동</label>
								</span>
								</li>
								<li>
								<span class="ui-rdo">
									<input id="stock-intgr-yn-n" name="stockIntgrYn" type="radio" value="N"${product.stockIntgrYn ne 'Y' ? ' checked' : '' }/>
									<label for="stock-intgr-yn-n">연동안함</label>
								</span>
									<select class="ui-sel" title="사유 선택" id="stock-un-intgr-rsn-code" name="stockUnIntgrRsnCode">
										<option value="">선택</option>
										<c:forEach items="${stockUnIntgrRsnCodeList }" var="item">
											<c:set var="stockUnIntgrRsnCodeSelect" value=""/>
											<c:if test="${product.stockUnIntgrRsnCode eq item.codeDtlNo }">
												<c:set var="stockUnIntgrRsnCodeSelect" value=" selected"/>
											</c:if>
											<option value="${item.codeDtlNo }"${stockUnIntgrRsnCodeSelect }>${item.codeDtlName }</option>
										</c:forEach>
									</select>
								</li>
							</ul>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="wrhsAlertYn" value="N"/><%-- 재입고알림 --%>
					<input type="hidden" name="stockIntgrYn" value="N"/><%-- 재고통합여부 --%>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<a href="javascript:void(0);" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="prdtOptnList">선택 항목 삭제</a>
		</div>
		<div class="fr">
			<span class="ip-text-box">
				<span class="text">선택한 상품</span>
				<select class="ui-sel" title="전시여부 일괄변경 선택" data-button-source="option-disp-yn">
					<option value="">선택</option>
					<option value="Y">전시</option>
					<option value="N">전시안함</option>
				</select>
				<c:if test="${product.mmnyPrdtYn ne 'Y' }"><%-- 상품신규등록 시, 입점업체권한사용자인 경우 --%>
					<select class="ui-sel" title="옵션판매상태 일괄변경 선택" data-button-source="option-sell-stat">
						<option value="">선택</option>
						<c:forEach var="sellStatCode" items="${sellStatCodeList}" varStatus="status">
							<option value="${sellStatCode.codeDtlNo}">${sellStatCode.codeDtlName}</option>
						</c:forEach>
					</select>
					<a href="javascript:void(0);" class="btn-sm btn-func" data-button="batch-update-sell-stat" data-source="option-sell-stat">판매상태일괄적용</a>
				</c:if>
				<a href="javascript:void(0);" class="btn-sm btn-func" data-button="batch-update-disp-yn" data-source="option-disp-yn">전시여부일괄적용</a>
			</span>
			<c:if test="${product.mmnyPrdtYn ne 'Y' }"><%-- 상품신규등록 시, 입점업체권한사용자인 경우 --%>
				<a href="javascript:void(0);" class="btn-sm btn-link" data-button="add-row" data-sheet-name="prdtOptnList">행추가</a>
			</c:if>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="prdt-optn-list">
		</div>
	</div>
	<!-- E : ibsheet-wrap -->
</div>