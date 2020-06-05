<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 2020.01.30 : Const.java 에서 내리는 채널별 카테고리 최대 LEVEL --%>
		abc.biz.product.category.standard.MAX_CTGR_LEVEL_ART	= "${Const.MAX_CTGR_LEVEL_ART}";
		abc.biz.product.category.standard.MAX_CTGR_LEVEL_ABC	= "${Const.MAX_CTGR_LEVEL_ABC}";
		abc.biz.product.category.standard.MAX_CTGR_LEVEL_GS	= "${Const.MAX_CTGR_LEVEL_GS}";
		abc.biz.product.category.standard.MAX_CTGR_LEVEL_OTS	= "${Const.MAX_CTGR_LEVEL_OTS}";
		abc.biz.product.category.standard.MAX_CTGR_LEVEL_KIDS	= "${Const.MAX_CTGR_LEVEL_KIDS}";
	});
</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">표준카테고리 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>상품관리</li>
								<li>상품기준정보관리</li>
								<li>표준카테고리 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<div class="col-wrap col3-2by1 full-content">
					<div class="col">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">표준 카테고리 목록</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-controller -->
						<div class="tbl-controller">
							<div class="fl">
								<button type="button" class="btn-sm btn-only-ico next" id="nextBtn"><i class="fa fa-caret-down"></i></button>
								<button type="button" class="btn-sm btn-only-ico prev" id="prevBtn"><i class="fa fa-caret-up"></i></button>
							</div>
							<div class="fr">
								<button type="button" class="btn-sm btn-save" id="saveSortBtn">순서 저장</button>
								<button type="button" class="btn-sm btn-func" id="addStandardCategoryBtn">카테고리 등록</button>
							</div>
						</div>
						<!-- E : tbl-controller -->

						<!-- S : selectable-list-wrap -->
						<div class="ibsheet-wrap">
							<div id="standardSheet" style="width:100%; height:100%;"></div>
						</div>
						<!-- E : selectable-list-wrap -->
					</div>
					<div class="col">
						<!-- S : row-wrap -->
						<div class="row-wrap">
							<form id="standardCategoryForm" name="standardCategoryForm">
							<div class="row">
								<input type="hidden" id="status" name="status"/>
								<input type="hidden" id="level" name="level"/>
								<input type="hidden" id="stdCtgrNo" name="stdCtgrNo"/>
								<input type="hidden" id="upStdCtgrNo" name="upStdCtgrNo"/>
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">표준 카테고리 기본정보</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>표준 카테고리 기본정보</caption>
									<colgroup>
										<col style="width: 130px;">
										<col>
										<col style="width: 130px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">상위 카테고리</th>
											<td id="upStdCtgrName"></td>
											<th scope="row">표준 카테고리 코드</th>
											<td id="stdCtgrNoField"></td>
										</tr>
										<tr>
											<th scope="row">
												<span class="th-required">표준 카테고리명</span>
											</th>
											<td class="input">
												<input type="text" name="stdCtgrName" id="stdCtgrName" class="ui-input" title="표준 카테고리명 입력" maxlength="100">
											</td>
											<th scope="row">
												<span class="th-required">사용여부</span>
											</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioUseY" name="useYn" type="radio" value="Y" checked="checked">
															<label for="radioUseY">사용</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioUseN" name="useYn" value="N" type="radio">
															<label for="radioUseN">사용안함</label>
														</span>
													</li>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">상품고시정보<br /> 품목명 적용</th>
											<td class="input" colspan="3">
												<select class="ui-sel" id="itemCode" name="itemCode" title="품목명 선택">
													<option value>품목명</option>
													<c:forEach items="${itemCode}" var="code" varStatus="status">
														<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<th scope="row">Leaf Category 여부</th>
											<td class="input" colspan="3">
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="leafCtgrY" name="leafCtgrYn" type="radio" value="Y">
															<label for="leafCtgrY">예</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="leafCtgrN" name="leafCtgrYn" value="N" type="radio" checked="checked">
															<label for="leafCtgrN">아니오</label>
														</span>
													</li>
												</ul>
											</td>
<%/* 2019-11-25 양해상 차장 요청으로 샤방넷 카테고리 삭제
											<th scope="row">사방넷 상품속성</th>
											<td class="input">
												<input type="text" name="sabangnetPrdtAttrText" id="sabangnetPrdtAttrText" maxlength="3" class="ui-input" title="사방넷 상품속성 입력" maxlength="100" placeholder="상품속성 입력" disabled>
											</td>
*/%>
										</tr>
<%/* 2019-11-25 양해상 차장 요청으로 샤방넷 카테고리 삭제
										<tr>
											<th scope="row">사방넷 카테고리</th>
											<td class="input" colspan="3">
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<input type="text" name="sabangnetCtgr1Text" id="sabangnetCtgr1Text" maxlength="20" class="ui-input" title="대카테고리  입력" maxlength="100" placeholder="대카테고리코드 입력"disabled>
													<input type="text" name="sabangnetCtgr2Text" id="sabangnetCtgr2Text" maxlength="20" class="ui-input" title="중카테고리 입력" maxlength="100" placeholder="중카테고리코드 입력" disabled>
													<input type="text" name="sabangnetCtgr3Text" id="sabangnetCtgr3Text" maxlength="20" class="ui-input" title="소카테고리 입력" maxlength="100" placeholder="소카테고리코드 입력" disabled>
													<input type="text" name="sabangnetCtgr4Text" id="sabangnetCtgr4Text" maxlength="20" class="ui-input" title="세카테고리 입력" maxlength="100" placeholder="세카테고리코드 입력" disabled>
												</span>
												<!-- E : ip-text-box -->
											</td>
										</tr>
*/%>
										<tr id="display-category" style="display:none;">
											<th scope="row">전시 카테고리 설정</th>
											<td class="input" colspan="3">
												<div class="tbl-header">
													<div class="fl dp-category-area">
														<button type="button" class="btn-sm btn-del" data-button="remove-elements" data-target="display-category-area">선택삭제</button>
														<!-- DESC : 20190322 삭제 // 채널선택 삭제 -->
														<select class="ui-sel dp-category-select chnnl-no" title="채널 선택" id="display-category-channel">
															<option value="">전시 채널 선택</option>
															<c:forEach items="${chnnlList}" var="item">
															<option value="${item.chnnlNo}">${item.chnnlName }</option>
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
													<div class="fr">
														<a href="javascript:void(0);" class="btn-sm btn-func" data-button="add-element" data-target="display-category-area">적용추가</a>
													</div>
												</div>
												<table class="tbl-col">
													<caption>전시 카테고리 설정</caption>
													<colgroup>
														<col style="width: 10%;"/>
														<col/>
														<col style="width: 75%;"/>
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
														</tr>
													</thead>
													<tbody id="display-category-area">
													</tbody>
													<script type="text/x-template" id="display-category-template">
														<tr>
															<td class="only-chk">
																<input type="hidden" name="displayCategoryList.divider" value=""/>
																<span class="ui-chk" data-template="display-category-checkbox">
																	<input id="display-category-checkbox" type="checkbox" data-target="display-category-item"/>
																	<label for="display-category-checkbox"></label>
																</span>
																<input type="hidden" name="displayCategoryList.ctgrNo" value="" data-template="display-category-ctgr-no"/>
															</td>
															<td class="text-center" data-template="display-category-channel-name"></td>
															<td data-template="display-category-category-path"></td>
														</tr>
													</script>
												</table>
											</td>
										</tr>
										<tr>
											<th scope="row">수정자</th>
											<td id="moderInfo"></td>
											<th scope="row">수정일시</th>
											<td id="modDtm"></td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->

								<!-- S : 20190514 추가 // 소재별 취급방법 영역 추가 -->
								<!-- S : content-header -->
								<div class="content-header handling-precaution-box" style="display:none;">
									<div class="fl">
										<h3 class="content-title">소재별 취급방법</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<div class="tbl-controller handling-precaution-box" style="display:none;">
									<div class="fl">
										<button type="button" class="btn-sm btn-del" id="removePrecaution">선택 항목 삭제</button>
									</div>
									<div class="fr">
										<a href="#" class="btn-sm btn-func" id="addPrecaution">추가</a>
									</div>
								</div>
								<table class="tbl-col handling-precaution-box" style="display:none;">
									<caption>소재별 취급방법</caption>
									<colgroup>
										<col style="width: 43px">
										<col style="width: 20%;">
										<col>
									</colgroup>
									<thead>
										<tr>
											<th scope="col" class="only-chk">
												<span class="ui-chk">
													<input id="chkMaterialAll" type="checkbox">
													<label for="chkMaterialAll"></label>
												</span>
											</th>
											<th scope="col">소재명</th>
											<th scope="col">취급시 주의사항</th>
										</tr>
									</thead>
									<tbody class="handling-precaution-area"></tbody>
								</table>
								<!-- E : 20190514 추가 // 소재별 취급방법 영역 추가 -->


								<!-- S : content-bottom -->
								<div class="content-bottom">
									<a href="#" class="btn-lg btn-save" id="saveStandardCategoryBtn" style="display: none;">저장</a>
								</div>
								<!-- E : content-bottom -->
							</div>
							</form>
						</div>
						<!-- E : row-wrap -->
					</div>
				</div>
			</div>
		</div>
		<!-- E : container -->
		<!-- DESC : 행 선택시 tr selected-row 클래스 추가해주세요 -->
		<script type="text/x-template" id="handling-precaution-tmpl">
			<tr class="">
				<td class="only-chk">
					<span class="ui-chk">
						<input type="hidden" name="syHandlingPrecautionList.stdCtgrNo" class="stdCtgrNo">
						<input type="hidden" name="syHandlingPrecautionList.handlPrecauSeq" class="handlPrecauSeq">
						<input id="" class="delYn" type="checkbox" name="delYn" value="Y">
						<label for=""></label>
					</span>
				</td>
				<td class="input">
					<input type="text" class="ui-input titleText" name="syHandlingPrecautionList.titleText" title="소재명 입력" maxlength="100">
				</td>
				<td class="input">
					<textarea class="ui-textarea contText" name="syHandlingPrecautionList.contText" title="취급시 주의사항 입력" maxlength="1000" onkeyup="return abc.biz.product.category.standard.setMaxLength(this)"></textarea>
				</td>
			</tr>
		</script>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/product/abcmart.product.category.standard.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>