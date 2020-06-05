<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="RLTN_PRDT_TYPE_CODE_LINKED" value="10000"/><%-- 관련상품유형코드 중 연관상품 --%>
<c:set var="RLTN_PRDT_TYPE_CODE_RELATED" value="10001"/><%-- 관련상품유형코드 중 관련상품 --%>
<div class="tab-content">
	<!-- S : 20190311 수정 // 전시정보 탭 전면수정 -->
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">전시정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row" >
		<caption>전시정보</caption>
		<colgroup>
			<col style="width:160px;"/>
			<col/>
		</colgroup>
		<tbody>
			<%-- 전시여부 --%>
			<tr>
				<th scope="row">
					<span class="th-required">전시여부</span>
				</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="disp-yn-y" name="dispYn" type="radio" value="Y"${product.dispYn eq 'Y' ? ' checked' : ''}/>
								<label for="disp-yn-y">전시</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="disp-yn-h" name="dispYn" type="radio" value="N"${product.dispYn ne 'Y' ? ' checked' : ''}/>
								<label for="disp-yn-h">전시안함</label>
							</span>
							<!-- DESC : 20190314 삭제 // 통합몰 전시 예외 삭제 -->
						</li>
						<li>
							<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="search-display-area">상품전시영역 확인</a>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<%-- 아이콘 --%>
			<tr>
				<th scope="row">아이콘 설정</th>
				<td class="input">
					<ul class="ip-box-list">
						<c:forEach items="${iconList }" var="iconItem">
							<%-- 아이콘 체크 여부 확인 --%>
							<c:set var="productIconSelect" value=""/>
							<c:forEach items="${product.productIcon }" var="productItem">
								<c:if test="${iconItem.prdtIconSeq eq productItem.prdtIconSeq }">
									<c:set var="productIconSelect" value=" checked"/>
								</c:if>
							</c:forEach>
							<li>
								<span class="ui-chk">
									<input id="prdt-icon-seq-list-${iconItem.prdtIconSeq }" name="productIcon.prdtIconSeq" type="checkbox" value="${iconItem.prdtIconSeq }"${productIconSelect }/>
									<label for="prdt-icon-seq-list-${iconItem.prdtIconSeq }">${iconItem.dispIconName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<%-- 전시카테고리 --%>
			<tr>
				<th scope="row">
					<span class="th-required">전시 카테고리 설정</span>
				</th>
				<td class="input">
					<div class="tbl-header">
						<div class="fl dp-category-area">
							<button type="button" class="btn-sm btn-del" data-button="remove-elements" data-target="display-category-area">선택삭제</button>
							<!-- DESC : 20190322 삭제 // 채널선택 삭제 -->
							<c:choose>
								<c:when test="${product.mmnyPrdtYn eq 'Y' }">
									<%-- BO권한인 경우, A-RT채널과 현재 선택된 채널에 대한 선택 가능하도록 설정 --%>
									<select class="ui-sel dp-category-select chnnl-no" title="채널 선택" id="display-category-channel">
									<!-- <select class="ui-sel" title="채널 선택" id="display-category-channel"> -->
										<c:forEach items="${channelList }" var="item">
											<c:choose>
												<c:when test="${item.chnnlNo eq CHANNEL_ART }">
													<%-- ART채널 --%>
													<option value="${item.chnnlNo }" data-site-no="${item.siteNo }">${item.chnnlName }</option>
												</c:when>
												<c:when test="${item.chnnlNo eq chnnlNo }">
													<%-- 현재 상품에 대한 채널 --%>
													<option value="${item.chnnlNo }" data-site-no="${item.siteNo }" data-removeable="true">${item.chnnlName }</option>
												</c:when>
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<input type="hidden" class="ui-sel dp-category-select chnnl-no" id="display-category-channel" data-site-no="${siteNo }" value="${not empty chnnlNo ? chnnlNo : CHANNEL_ART }"/>
								</c:otherwise>
							</c:choose>
							<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택">
								<option value="">대카테고리 전체</option>
							</select>
							<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택">
								<option value="">중카테고리 전체</option>
							</select>
							<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택" name="ctgrNo">
								<option value="">소카테고리 전체</option>
							</select>
							<c:if test="${product.mmnyPrdtYn ne 'Y' }">
								<%-- BO권한이 아닌 경우, 상품에 대한 채널번호로 설정 --%>
								<input type="hidden" id="display-category-channel" value="${chnnlNo }"/>
							</c:if>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-func" data-button="add-element" data-target="display-category-area">적용추가</a>
						</div>
					</div>

					<!-- S : tbl-col -->
					<table class="tbl-col">
						<caption>전시 카테고리 설정</caption>
						<colgroup>
							<col style="width: 10%;"/>
							<col/>
							<col style="width: 65%;"/>
							<col style="width: 10%;"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" class="only-chk">
								<span class="ui-chk">
									<input id="display-category-checkbox-all" type="checkbox" data-checkbox="check-all" data-target="display-category-item"/>
									<label for="display-category-checkbox-all"></label>
								</span>
								</th>
								<th scope="col">전시채널명</th>
								<th scope="col">전시카테고리</th>
								<th scope="col">기준</th>
							</tr>
						</thead>
						<tbody id="display-category-area">
							<c:choose>
								<c:when test="${empty product.displayCategoryList }">
									<tr data-empty="display-category">
										<td class="text-center" colspan="4">
											<span>등록된 전시 카테고리가 없습니다.</span>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<%-- 기존 전시 카테고리 출력 --%>
									<c:forEach items="${product.displayCategoryList }" var="item" varStatus="status">
										<tr>
											<td class="only-chk">
												<input type="hidden" name="displayCategoryList.divider" value=""/>
												<span class="ui-chk">
													<input id="display-category-checkbox${item.ctgrNo }" type="checkbox" data-checkbox="product-add-info-display-category" data-target="display-category-item"/>
													<label for="display-category-checkbox${item.ctgrNo }"></label>
												</span>
											</td>
											<td class="text-center">${item.chnnlName }</td>
											<td>${item.ctgrNamePath }</td>
											<td class="only-rdo">
												<%-- 전시카테고리 번호 --%>
												<input type="hidden" name="displayCategoryList.ctgrNo" value="${item.ctgrNo }"/>
												<span class="ui-rdo">
													<%-- 전시카테고리 중 기준카테고리 번호 --%>
													<input id="stdr-ctgr-no${item.ctgrNo }" type="radio" name="stdrCtgrNo" value="${item.ctgrNo }"${item.ctgrNo eq product.stdrCtgrNo ? ' checked' : ''}/>
													<label for="stdr-ctgr-no${item.ctgrNo }"></label>
												</span>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<script type="text/x-template" id="display-category-template">
								<tr>
									<td class="only-chk">
										<input type="hidden" name="displayCategoryList.divider" value=""/>
										<span class="ui-chk" data-template="display-category-checkbox">
											<input id="display-category-checkbox" type="checkbox" data-checkbox="product-add-info-display-category" data-target="display-category-item"/>
											<label for="display-category-checkbox"></label>
										</span>
									</td>
									<td class="text-center" data-template="display-category-channel-name"></td>
									<td data-template="display-category-category-path"></td>
									<td class="only-rdo">
											<input type="hidden" name="displayCategoryList.ctgrNo" value="" data-template="display-category-ctgr-no"/>
										<span class="ui-rdo">
											<input type="radio" name="stdrCtgrNo" data-template="display-category-stdr-ctgr-no-radio"/>
											<label data-template="display-category-stdr-ctgr-no-label"></label>
										</span>
									</td>
								</tr>
							</script>
						</tbody>
					</table>
					<!-- E : tbl-col -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">검색가능 여부</span>
				</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
						<span class="ui-rdo">
							<input id="srch-psblt-yn-y" name="srchPsbltYn" type="radio" value="Y"${product.srchPsbltYn ne 'N' ? ' checked' : ''}/>
							<label for="srch-psblt-yn-y">검색가능</label>
						</span>
						</li>
						<li>
						<span class="ui-rdo">
							<input id="srch-psblt-yn-n" name="srchPsbltYn" type="radio" value="N"${product.srchPsbltYn eq 'N' ? ' checked' : ''}/>
							<label for="srch-psblt-yn-n">검색불가</label>
						</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<tr>
				<th scope="row">검색 키워드</th>
				<td class="input">
					<input type="text" class="ui-input" title="검색 키워드 입력" name="srchKeyWordText" value="${product.srchKeyWordText }"/>
					<ul class="td-text-list">
						<li>* 키워드를 쉼표 (,)로 구분하여 주세요 (키워드는 100개까지 가능)</li>
					</ul>
				</td>
			</tr>
			<tr>
				<th scope="row">사이즈가이드 전시여부</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
						<span class="ui-rdo">
							<input id="size-chart-disp-yn-y" name="sizeChartDispYn" type="radio" value="Y"${product.sizeChartDispYn eq 'Y' ? ' checked' : ''}/>
							<label for="size-chart-disp-yn-y">전시</label>
						</span>
						</li>
						<li>
						<span class="ui-rdo">
							<input id="size-chart-disp-yn-n" name="sizeChartDispYn" type="radio" value="N"${product.sizeChartDispYn ne 'Y' ? ' checked' : ''}/>
							<label for="size-chart-disp-yn-n">전시안함</label>
						</span>
						</li>
						<li data-hidden="size-chart-disp-yn">
							<select class="ui-sel" title="사이즈가이드 선택" id="size-chart-list">
								<option value="" data-removeable="false">사이즈조견표 선택</option>
							</select>
							<input type="hidden" id="size-chart-seq" name="sizeChartSeq" value="${product.sizeChartSeq }"/>
							<a href="javascript:void(0);" class="btn-sm btn-link" data-button-popup="preview-size-chart">미리보기</a>
						</li>
					</ul>
				</td>
			</tr>
			<!-- DESC : 20190314 삭제 // 브랜드 스타일 삭제 -->
			<tr>
				<th scope="row"><span class="th-required">색상정보</span></th>
				<td class="input">
					<ul class="ip-box-list">
						<c:forEach items="${prdtColorCodeList }" var="colorCode">
							<c:set var="productColorSelect" value=""/>
							<c:forEach items="${product.productColor }" var="productColor">
								<%-- 상품색상코드 선택여부 설정 --%>
								<c:if test="${colorCode.codeDtlNo eq productColor.prdtColorCode }">
									<c:set var="productColorSelect" value=" checked"/>
								</c:if>
							</c:forEach>
							<li>
								<span class="ui-chk">
									<input id="prdt-color-code-${colorCode.codeDtlNo }" name="productColor.prdtColorCode" type="checkbox" value="${colorCode.codeDtlNo }"${productColorSelect }/>
									<label for="prdt-color-code-${colorCode.codeDtlNo }">${colorCode.codeDtlName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<th scope="row" data-rowspan="cntc-prdt-setup-yn" rowspan="2">색상연계상품 정보 설정</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="cntc-prdt-setup-yn-y" name="cntcPrdtSetupYn" type="radio" value="Y"${product.cntcPrdtSetupYn eq 'Y' ? ' checked' : ''}/>
								<label for="cntc-prdt-setup-yn-y">설정</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="cntc-prdt-setup-yn-n" name="cntcPrdtSetupYn" type="radio" value="N"${product.cntcPrdtSetupYn ne 'Y' ? ' checked' : ''}/>
								<label for="cntc-prdt-setup-yn-n">설정안함</label>
							</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<tr data-hidden="cntc-prdt-setup-yn">
				<td class="input">
					<div class="tbl-header">
						<div class="fl">
							<a href="javascript:void(0);" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="cntcPrdtSetupList">선택삭제</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-link" data-button="add-row-from-product" data-sheet-name="cntcPrdtSetupList">상품추가</a>
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="cntc-prdt-setup-list">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</td>
			</tr>
			<tr>
				<th scope="row" data-rowspan="rltn-goods-setup-yn" rowspan="2">관련용품 추천 설정</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
						<span class="ui-rdo">
							<input id="rltn-goods-setup-yn-y" name="rltnGoodsSetupYn" type="radio" value="Y"${product.rltnGoodsSetupYn eq 'Y' ? ' checked' : '' }/>
							<label for="rltn-goods-setup-yn-y">설정</label>
						</span>
						</li>
						<li>
						<span class="ui-rdo">
							<input id="rltn-goods-setup-yn-n" name="rltnGoodsSetupYn" type="radio" value="N"${product.rltnGoodsSetupYn ne 'Y' ? ' checked' : '' }/>
							<label for="rltn-goods-setup-yn-n">설정안함</label>
						</span>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<tr data-hidden="rltn-goods-setup-yn">
				<td class="input">
					<div class="tbl-header">
						<div class="fl">
							<a href="javascript:void(0);" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="rltnGoodsSetupList">선택삭제</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-link" data-button="add-row-from-product" data-sheet-name="rltnGoodsSetupList">상품추가</a>
						</div>
					</div>

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="rltn-goods-setup-list">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
	<!-- E : 20190311 수정 // 전시정보 탭 전면수정 -->
</div>
