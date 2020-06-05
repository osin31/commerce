<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

	<!-- S : container -->
	<div class="container">
		<input type="hidden" name="foUrl" value="<%= Config.getString("preview.fo.url","")%>">
		<input type="hidden" name="moUrl" value="<%= Config.getString("preview.mo.url","")%>">
		<input type="hidden" name="otsUrl" value="<%= Config.getString("preview.ots.fo.url","")%>">
		<input type="hidden" name="otsMoUrl" value="<%= Config.getString("preview.ots.mo.url","")%>">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">팝업 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>전시관리</li>
							<li>전시 기준정보 관리</li>
							<li>팝업 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">팝업 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<form id="searchForm" name="searchForm">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>팝업 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기간조회</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select name="dateType" class="ui-sel" title="기간 선택">
												<option value="dispStartDtm">전시 시작일</option>
												<option value="dispEndDtm">전시 종료일</option>
												<option value="rgster">작성일</option>
												<option value="moder">수정일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" id="startYmd" name="startYmd" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" id="endYmd" name="endYmd" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group area-calendar-btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-today">오늘</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-week">일주일</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-month">한달</a>
												<a href="javascript:void(0);" class="btn-sm btn-func calendar-year">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">팝업 유형</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPopupTypeAll" name="popupTypes" type="checkbox" value="" checked>
													<label for="chkPopupTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPopupType01" class="check-item" name="popupTypes" type="checkbox" value="G" checked>
													<label for="chkPopupType01">일반 팝업</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPopupType02" class="check-item" name="popupTypes" type="checkbox" value="E" checked>
													<label for="chkPopupType02">이벤트 팝업</label>
												</span>
											</li>
										</ul>
									</td>
									<td></td>
									<th scope="row">전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisp01"  checked value="">
													<label for="radioDisp01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisp02" value="Y">
													<label for="radioDisp02">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisp03" value="N">
													<label for="radioDisp03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">전시사이트</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="radioDisplaySite" checked value="">
													<label for="radioDisplaySite">전체</label>
												</span>
											</li>
											<c:forEach items="${siteList}" var="site" varStatus="i">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="radioDisplaySite${i.count }" value="${site.siteNo }">
														<label for="radioDisplaySite${i.count}">${site.siteName }</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse01"  checked value="">
													<label for="radioUse01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse02" value="Y">
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse03" value="N">
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">디바이스</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="deviceCode" id="radioDevice"  checked value="">
													<label for="radioDevice">전체</label>
												</span>
											</li>
											<c:forEach var="code"  items="${deviceCodeList}" varStatus="i">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="deviceCode" id="radioDevice${i.count}" value="${code.codeDtlNo}">
														<label for="radioDevice${i.count}">${code.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">팝업타입</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="devicePopupType" id="radioPopupType01"  checked value="">
													<label for="radioPopupType01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="devicePopupType" id="radioPopupType02" value="L">
													<label for="radioPopupType02">레이어</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="devicePopupType" id="radioPopupType03" value="N">
													<label for="radioPopupType03">윈도우</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">특정일 전시 사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispDaySetupYn" id="radioConfigUse01"  checked value="">
													<label for="radioConfigUse01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispDaySetupYn" id="radioConfigUse02" value="Y">
													<label for="radioConfigUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispDaySetupYn" id="radioConfigUse03" value="N">
													<label for="radioConfigUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select name="keywordType" class="ui-sel" title="검색어 타입 선택">
												<option value="">선택</option>
												<option value="title">제목</option>
												<option value="rgster">작성자이름</option>
												<option value="rgsterId">작성자ID</option>
												<option value="moder">수정자이름</option>
												<option value="moderId">수정자ID</option>
											</select>
											<input type="text" name="keyword" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="50">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<button href="submit" class="btn-normal btn-func">검색</button>
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
					<h3 class="content-title">팝업 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selTermsModule">목록개수</label>
						<select class="ui-sel" id="selTermsModule">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
				<div class="fr">
					<a href="/display/standard/popup/detail" class="btn-sm btn-link">등록</a>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="popupSheet"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	<!-- E : container -->
	
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/standard/abcmart.display.standard.popup.js<%=_DP_REV%>"></script>		
<%@include file="/WEB-INF/views/common/footer.jsp" %>