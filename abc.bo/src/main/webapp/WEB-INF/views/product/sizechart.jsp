<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">사이즈가이드 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>상품관리</li>
								<li>상품기준 정보관리</li>
								<li>사이즈가이드 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">사이즈가이드 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>사이즈가이드 검색</caption>
								<colgroup>
									<col style="width:15%;">
									<col>
									<col style="width:79px;">
									<col style="width:15%;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">사이즈가이드 ID</th>
										<td class="input">
											<input type="number" class="ui-input" name="sizeChartSeq" title="사이즈가이드명 입력" placeholder="사이즈가이드 ID" maxlength="4"
												oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);">
										</td>
										<td></td>
										<th scope="row">사이즈가이드명</th>
										<td class="input">
											<input type="text" class="ui-input" name="sizeChartName" title="사이즈가이드명 입력" placeholder="사이즈가이드명">
										</td>
									</tr>
									<tr>
										<th scope="row">사용여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<input id="radioUse01" name="useYn" type="radio" checked="checked" value="">
														<label for="radioUse01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="radioUse02" name="useYn" type="radio" value="Y">
														<label for="radioUse02">사용</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="radioUse03" name="useYn" type="radio" value="N">
														<label for="radioUse03">사용안함</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">테마</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<input id="themeRadio01" name="themeType" type="radio" checked="checked" value="">
														<label for="themeRadio01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="themeRadio02" name="themeType" type="radio" value="M">
														<label for="themeRadio02">남성</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="themeRadio03" name="themeType" type="radio" value="F">
														<label for="themeRadio03">여성</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="themeRadio04" name="themeType" type="radio" value="C">
														<label for="themeRadio04">아동</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="themeRadio05" name="themeType" type="radio" value="A">
														<label for="themeRadio05">공용</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">표준 카테고리 선택 (대)</th>
										<td class="input">
											<select name="stdCtgrNo" class="ui-sel" title="대카테고리 선택">
												<option value="">대카테고리 선택</option>
												<c:forEach var="row" items="${standardCategoryList}">
													<option value="${row.stdCtgrNo}">${row.stdCtgrName}</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
										<th scope="row">적용 브랜드</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<input id="radioBrand01" name="brandAssignYn" type="radio" checked="checked" value="">
														<label for="radioBrand01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="radioBrand02" name="brandAssignYn" type="radio" value="N">
														<label for="radioBrand02">공통</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<input id="radioBrand03" name="brandAssignYn" type="radio" value="Y">
														<label for="radioBrand03">브랜드</label>
													</span>
	
													<span class="ip-search-box" style="display:none;">
														<input type="text" id="brandName" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" />
														<!-- <input type="hidden" id="brandNo" name="brandNo" /> -->
														<a href="javascript:void(0);" id="search-brand" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
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
									<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<button type="submit" id="btn-search" class="btn-normal btn-func">검색</button>
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
						<h3 class="content-title">사이즈가이드목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="/product/sizechart/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="sizechartSheet">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.sizechart.js<%=_DP_REV%>"></script>		
<%@include file="/WEB-INF/views/common/footer.jsp" %>