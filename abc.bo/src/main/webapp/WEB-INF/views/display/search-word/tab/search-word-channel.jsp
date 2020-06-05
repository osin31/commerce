<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S:tab_content -->
<div id="tabContent4" class="tab-content">
	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">채널 검색어 검색 </h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : search-wrap -->
	<form id="channelSearchForm" name="channelSearchForm">
	<input type="hidden" name="srchWordGbnType" id="channelGbnType" value="C">
	<div class="search-wrap">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>채널별 검색</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">채널</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<c:forEach items="${channelList}" var="channel" varStatus="status">
									<c:if test="${channel.chnnlNo ne Const.SITE_CHNNL_ART}">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="chnnlNo" id="channel${status.count}" class="channelType" value="${channel.chnnlNo}"  ${channel.chnnlNo eq Const.SITE_CHNNL_ABC ? 'checked' : ''}>
												<label for="channel${status.count}">${channel.chnnlName}</label>
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
					<a href="javascript:void(0);" class="btn-sm btn-func clearBtn channelType"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-func searchBtn channelType">검색</a>
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
			<h3 class="content-title">채널별 검색어 관리</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-controller -->
	<div class="tbl-controller">
		<div class="fl">
			<!-- S : opt-group -->
			<span class="opt-group">
				<label class="title" for="channelPageCount">목록개수</label>
				<select class="ui-sel pageCount channelType" id="channelPageCount">
					<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
				</select>
				<button href="javascript:void(0);" class="btn-sm btn-del channelType">선택 삭제</button>
			</span>
			<!-- E : opt-group -->
		</div>
		<div class="fr">
			<div class="btn-group">
				<!-- 
				<button type="button" class="btn-sm btn-link channelHistoryBtn channelType">히스토리 보기</button>
				-->
				<button type="button" class="btn-sm btn-func insertBtn channelType">행추가</button>
				<button type="button" class="btn-sm btn-save channelSaveBtn channelType">저장</button>
			</div>
		</div>
	</div>
	<!-- E : tbl-controller -->

	<!-- S : ibsheet-wrap -->
	<div class="ibsheet-wrap">
		<div id="channelSheet"></div>
	</div>
	<!-- E : ibsheet-wrap -->
</div>
<!-- E:tab_content -->