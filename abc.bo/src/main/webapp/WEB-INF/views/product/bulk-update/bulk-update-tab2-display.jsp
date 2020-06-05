<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<un:useConstants className="kr.co.abcmart.constant.Const" var="Const" />

<script type="text/javascript">
	$(function() {
		<%-- 2020.05.14 : Const.java 에서 내리는 채널별 카테고리 최대 LEVEL --%>
		abc.biz.product.bulkUpdate.MAX_CTGR_LEVEL_ART	= "${Const.MAX_CTGR_LEVEL_ART}";
		abc.biz.product.bulkUpdate.MAX_CTGR_LEVEL_ABC	= "${Const.MAX_CTGR_LEVEL_ABC}";
		abc.biz.product.bulkUpdate.MAX_CTGR_LEVEL_GS	= "${Const.MAX_CTGR_LEVEL_GS}";
		abc.biz.product.bulkUpdate.MAX_CTGR_LEVEL_OTS	= "${Const.MAX_CTGR_LEVEL_OTS}";
		abc.biz.product.bulkUpdate.MAX_CTGR_LEVEL_KIDS	= "${Const.MAX_CTGR_LEVEL_KIDS}";
		
		<%-- 2020.05.14 : 상품등록 채널에서 A-RT 건너뛰고 제거 --%>
		$(".chnnl-no").val("${Const.CHNNL_NO_ABCMART}").trigger("change");
		$(".chnnl-no option[value='${Const.CHNNL_NO_ART}']").remove();
	});
</script>

<!-- S:tab_content -->
<div id="tabContent2" class="tab-content">
	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>전시정보</caption>
		<colgroup>
			<col style="width: 160px">
			<col>
		</colgroup>
		<tbody>
<%-- 전시여부 --%>
			<tr>
				<th scope="row">전시여부</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="disp-yn-none" name="dispYn" type="radio" value="" checked/>
								<label for="disp-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="disp-yn-y" name="dispYn" type="radio" value="Y"/>
								<label for="disp-yn-y">전시</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="disp-yn-n" name="dispYn" type="radio" value="N"/>
								<label for="disp-yn-n">전시안함</label>
							</span>
						</li>
					</ul>
				</td>
			</tr>
<%-- 아이콘 --%>
			<tr>
				<th scope="row" rowspan="2">아이콘 설정</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="icon-none" name="icon" type="radio" data-clear="icon" checked/>
								<label for="icon-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="icon-change" name="icon" type="radio" data-toggle="icon"/>
								<label for="icon-change">변경</label>
							</span>
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="input">
					<ul class="ip-box-list" data-toggle-target="icon">
						<c:forEach items="${icons }" var="iconItem">
							<li data-toggle-target="icon">
								<span class="ui-chk">
									<input id="prdt-icon-seq-list-${iconItem.prdtIconSeq }" name="productIcon.prdtIconSeq" type="checkbox" value="${iconItem.prdtIconSeq }" data-clear-target="icon"/>
									<label for="prdt-icon-seq-list-${iconItem.prdtIconSeq }">${iconItem.dispIconName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
<%-- 전시카테고리 --%>
			<tr>
				<th scope="row" rowspan="2">전시카테고리 설정</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="display-category-no-none" name="displayCategoryNo" type="radio" data-clear="display-category" data-clear-display-category="true" checked/>
								<label for="display-category-no-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="display-category-no-change" name="displayCategoryNo" type="radio" data-toggle="display-category"/>
								<label for="display-category-no-change">변경</label>
							</span>
						</li>
					</ul>
					<ul class="td-text-list">
						<li>* 선택한 전시채널에 속한 상품의 카테고리만 변경됩니다. 일괄 채널변경은 불가합니다.</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="input">
					<div class="tbl-header" data-toggle-target="display-category">
						<div class="fl">
							<div class="category-setting-box">
								<button type="button" class="btn-sm btn-del" data-button="remove-display-category" data-target="display-category-area">선택삭제</button>
								<select class="ui-sel dp-category-select chnnl-no" title="전시채널 선택" data-clear-target="display-category">
									<c:forEach items="${channels }" var="item">
										<option value="${item.chnnlNo }" data-site-no="${item.siteNo }">${item.chnnlName }</option>
									</c:forEach>
								</select>
								<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택">
									<option value="">대카테고리 전체</option>
								</select>
								<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택">
									<option value="">중카테고리 전체</option>
								</select>
								<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택" name="ctgrNo">
									<option value="">소카테고리 전체</option>
								</select>
							</div>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-func" data-button="add-display-category" data-target="display-category-area">적용추가</a>
						</div>
					</div>
					<table class="tbl-col" data-toggle-target="display-category">
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
										<input id="display-category-checkbox-all" type="checkbox" name="chkCategoryModule" data-checkbox="check-all" data-target="display-category-item"/>
										<label for="display-category-checkbox-all"></label>
									</span>
								</th>
								<th scope="col">전시채널명</th>
								<th scope="col">전시카테고리</th>
								<th scope="col">기준</th>
							</tr>
						</thead>
						<tbody id="display-category-area">
							<tr data-empty="display-category">
								<td class="text-center" colspan="4">
									<span>등록된 전시 카테고리가 없습니다.</span>
								</td>
							</tr>
						</tbody>
					</table>
					<script type="text/x-template" id="display-category-template">
						<tr>
							<td class="only-chk">
								<input type="hidden" name="displayCategories.divider" value=""/>
								<span class="ui-chk" data-template="display-category-checkbox">
									<input id="display-category-checkbox" type="checkbox" data-target="display-category-item"/>
									<label for="display-category-checkbox"></label>
								</span>
							</td>
							<td class="text-center" data-template="display-category-channel-name"></td>
							<td data-template="display-category-category-path"></td>
							<td class="only-rdo">
									<input type="hidden" name="displayCategories.ctgrNo" value="" data-template="display-category-ctgr-no"/>
								<span class="ui-rdo">
									<input type="radio" name="stdrCtgrNo" data-template="display-category-stdr-ctgr-no-radio"/>
									<label data-template="display-category-stdr-ctgr-no-label"></label>
								</span>
							</td>
						</tr>
					</script>
				</td>
			</tr>
<%-- 검색가능 여부 --%>
			<tr>
				<th scope="row">검색가능 여부</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="srch-psblt-yn-none" name="srchPsbltYn" type="radio" value="" checked/>
								<label for="srch-psblt-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="srch-psblt-yn-y" name="srchPsbltYn" type="radio" value="Y"/>
								<label for="srch-psblt-yn-y">검색가능</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="srch-psblt-yn-n" name="srchPsbltYn" type="radio" value="N"/>
								<label for="srch-psblt-yn-n">검색불가</label>
							</span>
						</li>
					</ul>
				</td>
			</tr>
<%-- 검색 키워드 --%>
			<tr>
				<th scope="row">검색 키워드</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="search-keyword-none" name="searchKeyword" type="radio" data-clear="search-keyword" checked/>
								<label for="search-keyword-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="search-keyword-change" name="searchKeyword" type="radio" data-toggle="search-keyword"/>
								<label for="search-keyword-change">변경</label>
							</span>
						</li>
						<li data-toggle-target="search-keyword">
							<input type="text" class="ui-input" title="검색 키워드 입력" name="srchKeyWordText" data-clear-target="search-keyword"/>
						</li>
					</ul>
				</td>
			</tr>
<%-- 사이즈조견표 전시여부 --%>
<%--
			<tr>
				<th scope="row">사이즈조견표 전시여부</th>
				<td class="input">
					<!-- S : ip-box-list -->
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="radioSizeDisplay01" name="radioSizeDisplayModule" type="radio" checked>
								<label for="radioSizeDisplay01">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="radioSizeDisplay02" name="radioSizeDisplayModule" type="radio">
								<label for="radioSizeDisplay02">전시</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="radioSizeDisplay03" name="radioSizeDisplayModule" type="radio">
								<label for="radioSizeDisplay03">전시안함</label>
							</span>
						</li>
						<li>
							<select class="ui-sel" title="사이즈 조견표 선택">
								<option>사이즈 조견표 선택</option>
							</select>
							<a href="#" class="btn-sm btn-link">미리보기</a>
						</li>
					</ul>
					<!-- E : ip-box-list -->
				</td>
			</tr>
			<!-- e : 색상정보 > 변경 선택시 -->
--%>
<%-- 색상정보 --%>
			<tr>
				<th scope="row" rowspan="2">색상정보</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="prdt-color-code-none" name="prdtColorCode" type="radio" data-clear="color" checked/>
								<label for="prdt-color-code-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="prdt-color-code-change" name="prdtColorCode" type="radio" data-toggle="color"/>
								<label for="prdt-color-code-change">변경</label>
							</span>
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="input">
					<ul class="ip-box-list" data-toggle-target="color">
						<c:forEach items="${prdtColorCodes }" var="colorCode">
							<li>
								<span class="ui-chk">
									<input id="prdt-color-code-${colorCode.codeDtlNo }" name="productColor.prdtColorCode" type="checkbox" value="${colorCode.codeDtlNo }" data-clear-target="color"/>
									<label for="prdt-color-code-${colorCode.codeDtlNo }">${colorCode.codeDtlName }</label>
								</span>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
<%-- 관련용품 추천 설정 --%>
			<tr>
				<th scope="row" rowspan="2"> 관련용품 추천 설정</th>
				<td class="input">
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="rltn-goods-setup-yn-none" name="rltnGoodsSetupYn" type="radio" value="" data-clear="relation-goods" data-clear-sheet-name="rltnGoodsSetupList" checked/>
								<label for="rltn-goods-setup-yn-none">변경안함</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="rltn-goods-setup-yn-y" name="rltnGoodsSetupYn" type="radio" value="Y" data-toggle="relation-goods"/>
								<label for="rltn-goods-setup-yn-y">설정</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="rltn-goods-setup-yn-n" name="rltnGoodsSetupYn" type="radio" value="N" data-clear="relation-goods" data-clear-sheet-name="rltnGoodsSetupList"/>
								<label for="rltn-goods-setup-yn-n">설정안함</label>
							</span>
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="input">
					<div class="tbl-header" data-toggle-target="relation-goods">
						<div class="fl">
							<div class="category-setting-box">
								<button type="button" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="rltnGoodsSetupList">선택삭제</button>
							</div>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-func" data-button="add-row-from-product" data-sheet-name="rltnGoodsSetupList">상품추가</a>
						</div>
					</div>
					<div class="ibsheet-wrap" data-toggle-target="relation-goods">
						<div id="rltn-goods-setup-list">
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!-- E:tab_content -->