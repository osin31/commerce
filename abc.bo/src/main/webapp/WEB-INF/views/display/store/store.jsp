<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.display.store.codeCombo = ${codeCombo};
	
	$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
		if(e.keyCode === 13) {
			$("#searchBtn").trigger("click");
		}
	});
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
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
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
						<h3 class="content-title">오프라인 매장 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<form id="searchForm" name="searchForm" onsubmit="return false;">
				<input type="hidden" class="ui-input" name="temp">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>오프라인 매장 검색</caption>
							<colgroup>
								<col style="width: 140px;">
								<col>
								<col style="width: 79px">
								<col style="width: 140px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">전시 사이트</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="radioSite01" value checked>
													<label for="radioSite01">전체</label>
												</span>
											</li>
											<c:forEach items="${siteList}" var="site" varStatus="status">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="${site.siteNo}" value="${site.siteNo}">
														<label for="${site.siteNo}">${site.siteName}</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisplayAll" value checked>
													<label for="radioDisplayAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisplayY" value="Y">
													<label for="radioDisplayY">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisplayN" value="N">
													<label for="radioDisplayN">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">매장 제공 서비스</th>
									<td class="input" colspan="4">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="storeServiceCodeAll" name="storeServiceCodeAll" type="checkbox" value="Y" checked>
													<label for="storeServiceCodeAll">전체</label>
												</span>
											</li>
											<c:forEach items="${storeServiceCodeList}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<input id="chkService${status.count}" class="storeServiceCode" name="storeServiceCode" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="chkService${status.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">매장형태</th>
									<td class="input" colspan="4">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<input id="chkStoreAll" name="chkStoreAll" type="checkbox" value checked>
													<label for="chkStoreAll">전체</label>
												</span>
											</li>
											<c:forEach items="${storeTypeCodeList}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<input id="chkStore${status.count}" class="storeTypeCode" name="storeTypeCodes" type="checkbox" value="${code.codeDtlNo}" checked>
														<label for="chkStore${status.count}">${code.codeDtlName}</label>
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
													<input type="radio" name="pickupPsbltYn" id="radioPickupAll" value checked>
													<label for="radioPickupAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="pickupPsbltYn" id="radioPickupY" value="Y">
													<label for="radioPickupY">가능</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="pickupPsbltYn" id="radioPickupN" value="N">
													<label for="radioPickupN">불가능</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">임직원가 구매 가능여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="empPriceBuyPsbltYn" id="radioPurchaseAll" value checked>
													<label for="radioPurchaseAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="empPriceBuyPsbltYn" id="radioPurchaseY" value="Y">
													<label for="radioPurchaseY">가능</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="empPriceBuyPsbltYn" id="radioPurchaseN" value="N">
													<label for="radioPurchaseN">불가능</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">지역구분</th>
									<td class="input">
<!-- 										<select class="ui-sel" title="지역구분 선택" name="areaNo" id="areaNo"> -->
<!-- 											<option value>광역시/도 전체</option> -->
<%-- 											<c:forEach items="${areaList}" var="area"> --%>
<%-- 												<option value="${area.areaNo}">${area.areaName}</option> --%>
<%-- 											</c:forEach> --%>
<!-- 										</select> -->
										
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
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="searchType" id="searchType">
												<option value="N">매장명</option>
												<option value="I">매장ID</option>
												<option value="A">주소</option>
												<option value="R">작성자</option>
												<option value="M">수정자</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchWord" id="searchWord" maxlength="50">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="clear-form"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">오프라인 매장 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount" name="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="excelDownBtn">엑셀다운로드</a>
						<a href="/display/store/detail" class="btn-sm btn-link" id="rgstBtn">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->
				</form>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="storeSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.store.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>