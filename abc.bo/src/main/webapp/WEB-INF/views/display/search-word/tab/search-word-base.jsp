<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S:tab_content -->
<div id="tabContent1" class="tab-content">
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">검색창 검색어 검색 </h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : search-wrap -->
	<form id="baseSearchForm" name="baseSearchForm">
	<input type="hidden" name="srchWordGbnType" id="baseGbnType" value="S">
	<div class="search-wrap">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>검색창 검색어 검색</caption>
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
									<li>
										<span class="ui-rdo">
											<input type="radio" name="siteNo" id="baseSite${status.count}" class="baseSiteNo" value="${site.siteNo}" data-site-name="${site.siteName}" ${status.count == 1 ? 'checked' : ''}>
											<label for="baseSite${status.count}">${site.siteName}</label>
										</span>
									</li>
								</c:forEach>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<div class="confirm-box">
				<div class="fl">
					<a href="javascript:void(0);" class="btn-sm btn-func clearBtn baseType"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-func searchBtn baseType">검색</a>
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
			<h3 class="content-title">검색창 검색어 목록</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<!-- S : opt-group -->
			<span class="opt-group">
				<label class="title" for="basePageCount">목록개수</label>
				<select class="ui-sel pageCount baseType" id="basePageCount">
					<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
				</select>
				<button href="javascript:void(0);" class="btn-sm btn-del baseType">선택 삭제</button>
			</span>
			<!-- E : opt-group -->
		</div>
		<div class="fr">
			<div class="btn-group">
				<button type="button" class="btn-sm btn-link historyBtn baseType">히스토리 보기</button>
				<button type="button" class="btn-sm btn-func insertBtn baseType">행추가</button>
				<button type="button" class="btn-sm btn-save saveBtn baseType">저장</button>
			</div>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="baseSheet"></div>
	</div>
	<!-- E : ibsheet-wrap -->
</div>
<!-- E:tab_content -->