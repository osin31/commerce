<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S:tab_content -->
<div id="tabContent2" class="tab-content">
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">인기 검색어 검색 </h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : search-wrap -->
	<div class="search-wrap">
		<form id="hotSearchForm" name="hotSearchForm">
		<input type="hidden" name="srchWordGbnType" id="hotGbnType" value="P">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>인기 검색어 검색</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">사이트</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<c:forEach items="${siteList}" var="site" varStatus="status">
									<c:if test="${site.siteNo != ots}">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="siteNo" id="hotSite${status.count}" class="hotSiteNo" value="${site.siteNo}" data-site-name="${site.siteName}" ${status.count == 1 ? 'checked' : ''}>
												<label for="hotSite${status.count}">${site.siteName}</label>
											</span>
										</li>
									</c:if>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<div class="confirm-box">
				<div class="fl">
					<a href="javascript:void(0);" class="btn-sm btn-func clearBtn hotType"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-func searchBtn hotType">검색</a>
				</div>
			</div>
		</div>
		</form>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</div>
	<!-- E : search-wrap -->

	<div class="col-wrap col3-2by1">
		<div class="col">
			<!-- S : tab-wrap -->
			<div class="tab-wrap">
				<input type="hidden" name="poolType" value="R">
				<ul class="tabs">
					<li class="tab-item" id="rankingTab"><a href="#tabContentInner1" class="tab-link">검색어 순위</a></li>
					<li class="tab-item" id="jumpingTab"><a href="#tabContentInner2" class="tab-link">급등 검색어</a></li>
				</ul>
				<!-- S:tab_content -->
				<div id="tabContentInner1" class="tab-content">
					<form id="rankingSearchForm" name="rankingSearchForm">
						<input type="hidden" name="srchWordGbnType" id="rankingGbnType" value="K">
					</form>
					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="rankingSheet"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
				<!-- S:tab_content -->
				<div id="tabContentInner2" class="tab-content">
					<form id="jumpingSearchForm" name="jumpingSearchForm">
						<input type="hidden" name="srchWordGbnType" id="jumpingGbnType" value="J">
					</form>
					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="jumpingSheet"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
			</div>
			<!-- E : tab-wrap -->
		</div>
		<div class="control-btn-group">
			<div class="inner">
				<a href="#" class="btn-sm btn-only-ico ico-right-arr" id="addBtn"><i class="fa fa-caret-right"></i><span class="offscreen">추가</span></a>
			</div>
		</div>
		<div class="col">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">인기 검색어 관리</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="hotPageCount">목록개수</label>
						<select class="ui-sel pageCount hotType" id="hotPageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
				<div class="fr">
					<div class="btn-group">
						<button type="button" class="btn-sm btn-link historyBtn hotType">히스토리 보기</button>
						<button type="button" class="btn-sm btn-func insertBtn hotType">행추가</button>
						<button type="button" class="btn-sm btn-save saveBtn hotType">저장</button>
					</div>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="hotSheet"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</div>
<!-- E:tab_content -->