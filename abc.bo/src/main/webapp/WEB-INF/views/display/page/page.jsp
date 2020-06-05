
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script>
$(function() {
	abc.biz.display.page.combo = ${codeCombo};
});
</script>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">전시 페이지 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>전시 기준정보 관리</li>
								<li>전시 페이지 관리</li>
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
								<h3 class="content-title">전시 페이지 목록</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tree-filter-wrap -->
						<div class="tree-filter-wrap">
							<form name="searchForm" id="searchForm">
								<!-- S : 20190311 수정 // 사이트 select 삭제, option 수정 -->
								<select class="ui-sel" title="채널 선택" name="chnnlNo">
									<c:forEach items="${channelList}" var="list">
										<option value="${list.chnnlNo}">${list.chnnlName}</option>
									</c:forEach>
								</select>
								<select class="ui-sel" title="페이지 유형 선택" name="dispPageTypeCode">
									<option value>페이지 유형</option>
									<c:forEach items="${codeList.DISP_PAGE_TYPE_CODE}" var="code">
										<c:if test="${code.upCodeDtlNo eq null}">
											<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
										</c:if>
									</c:forEach>
								</select>
								<select class="ui-sel" title="사용여부 선택" name="useYn">
									<option value>전체</option>
									<option value="Y" selected>사용</option>
									<option value="N">사용안함</option>
								</select>
								<!-- E : 20190311 수정 // 사이트 select 삭제, option 수정 -->
							</form>
						</div>
						<!-- E : tree-filter-wrap -->

						<!-- S : 20190305 추가 // tree controller 추가 -->
						<!-- S : tbl-controller -->
						<div class="tbl-controller">
							<div class="fr">
								<button type="button" class="btn-sm btn-func" id="addPage" >페이지 등록</button>
							</div>
						</div>
						<!-- E : tbl-controller -->
						<!-- E : 20190305 추가 // tree controller 추가 -->

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
							<div style="width:100%; height:100%;" id="pageSheet"></div>
						</div>
						<!-- E : ibsheet-wrap -->
					</div>

					<div class="col">
						<!-- S : tab-wrap -->
						<div class="tab-wrap">
							<ul class="tabs">
								<li class="tab-item"><a href="#tabContent1" class="tab-link" id="pageArea">전시 페이지 관리</a></li>
								<!-- DESC : 전시 콘텐츠 관리 탭 비활성화시, tab-link 영역 tab-disabled 클래스 추가 -->
								<li class="tab-item"><a href="#tabContent2" class="tab-link tab-disabled" id="contentsArea">전시 콘텐츠 관리</a></li>
							</ul>
							<!-- S:tab_content -->
							<div id="tabContent1" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">전시 페이지 기본정보</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<form id="pageForm" name="pageForm">
								
								<input type="hidden" name="dispPageNo">
								<input type="hidden" name="upDispPageNo">
								<input type="hidden" name="dispPageTypeCode">
								<input type="hidden" name="siteNo">
								<input type="hidden" name="chnnlNo">								
								
								<table class="tbl-row">
									<caption>전시 페이지 기본정보</caption>
									<colgroup>
										<col style="width: 150px;">
										<col>
										<col style="width: 150px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">페이지 경로</th>
											<td id="pagePath" colspan="3"></td>
											<!-- 191216 상위 전시 페이지 코드 사용하지 않아 수정 
											<th scope="row">상위 전시 페이지 코드</th>
											<td id="upDispPageNoField"></td>
											 -->
										</tr>
										<tr>
											<th scope="row">전시 페이지 코드</th>
											<td id="dispPageNoField"></td>
											<th scope="row">
												<span class="th-required">전시페이지명</span>
											</th>
											<td class="input">
												<input type="text" class="ui-input"  title="전시페이지명 입력" name="dispPageName" placeholder="20자 이내로 입력" maxlength="20">
											</td>
										</tr>
										<tr>
											<th scope="row">PC 전시 URL</th>
											<td class="input">
												<input type="text" class="ui-input"  title="PC 전시 URL" name="pcDispUrl" placeholder="PC 전시 URL" maxlength="100">
											</td>
											<th scope="row">MOBILE 전시 URL
											</th>
											<td class="input">
												<input type="text" class="ui-input"  title="MOBILE 전시 URL" name="mobileDispUrl" placeholder="MOBILE 전시 URL" maxlength="100">
											</td>
										</tr>
										<tr>
											<th scope="row">
												<span class="th-required">사용여부</span>
											</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioUseY" name="useYn" type="radio" value="Y" checked>
															<label for="radioUseY">사용</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioUseN" name="useYn" type="radio" value="N">
															<label for="radioUseN">사용안함</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
											<th scope="row">
												<span class="th-required">전시여부</span>
											</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioDisplayY" name="dispYn" type="radio" value="Y" checked>
															<label for="radioDisplayY">전시</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioDisplayN" name="dispYn" type="radio" value="N">
															<label for="radioDisplayN">전시안함</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
										</tr>
										<tr class="">
											<th scope="row">
												<span class="th-required">전시 시작일</span>
											</th>
											<td class="input" colspan="3">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="dispStartDtm" id="" name="dispStartDtm">
												</span>
											</td>
										</tr>
										<tr class="">
											<th scope="row">PC 템플릿</th>
											<td class="input pc-disp-tmpl-row">
												<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
												<span class="ip-search-box">
													<input type="text" class="ui-input disp-tmpl-name" id="pcDispTmplName" disabled>
													<input type="hidden" id="pcDispTmplNo" name="pcDispTmplNo" class="disp-tmpl-no">
													<a href="#" class="btn-search searchTmpl forPc"><i class="ico-search"><span class="offscreen">검색</span></i></a>
													<button type="button" class="btn-reset reset-disp-tmpl-btn">선택 취소<i class="fas fa-times"></i></button>
												</span>
												<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
											</td>
											<th scope="row">Mobile 템플릿</th>
											<td class="input mobile-disp-tmpl-row">
												<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
												<span class="ip-search-box">
													<input type="text" class="ui-input disp-tmpl-name" id="mobileDispTmplName" disabled>
													<input type="hidden" id="mobileDispTmplNo" name="mobileDispTmplNo" class="disp-tmpl-no">
													<a href="#" class="btn-search searchTmpl forMobile"><i class="ico-search"><span class="offscreen">검색</span></i></a>
													<button type="button" class="btn-reset reset-disp-tmpl-btn">선택 취소<i class="fas fa-times"></i></button>
												</span>
												<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
											</td>
										</tr>
										<!-- S : 신규등록시 미노출 영역 -->
										<tr>
											<th scope="row">작성자</th>
											<td id="rgsterName"></td>
											<th scope="row">작성일시</th>
											<td id="rgstDtm"></td>
										</tr>
										<tr>
											<th scope="row">수정자</th>
											<td id="moderName"></td>
											<th scope="row">수정일시</th>
											<td id="modDtm"></td>
										</tr>
										<!-- E : 신규등록시 미노출 영역 -->
									</tbody>									
								</table>
								</form>
								<!-- E : tbl-row -->
								
								<!-- S : tbl-desc-wrap -->
								<div class="tbl-desc-wrap">
									<!-- s : 190918 추가 | 테이블 하단 안내문구 추가 -->
									<ol class="tbl-desc-list">
										<li>※ 전시 콘텐츠 등록 후에는 전시여부를 '전시'처리해야 전시 시작일에 반영됩니다.</li>
									</ol>
									<!-- e : 190918 추가 | 테이블 하단 안내문구 추가 -->
								</div>
								<!-- E : tbl-desc-wrap -->

								<!-- S : content-bottom -->
								<div class="content-bottom">
									<a href="#" class="btn-lg btn-save" id="saveBtn" style="display: none;">저장</a>
								</div>
								<!-- E : content-bottom -->
							</div>
							<!-- E:tab_content -->
							<!-- S:tab_content -->
							<div id="tabContent2" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">PC 코너 목록</h3>
									</div>
									<div class="fr">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<span class="btn-group" style="margin-right: 10px">
												<a href="#" class="btn-sm btn-link preview-btn pc-type">템플릿 보기</a>
											</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="미리보기 날짜 선택" id="preview-pc-date">
											</span>
											<select class="ui-sel" title="미리보기 기준 시 선택">
												<c:forEach begin="0" end="23" var="num">
													<c:if test="${num < 10}">
														<option>0${num}시</option>
													</c:if>
													<c:if test="${num > 9}">
														<option>${num}시</option>
													</c:if>
												</c:forEach>				
											</select>
											<select class="ui-sel" title="미리보기 기준 분 선택">
												<c:forEach begin="0" end="50" var="num" step="10">
													<c:if test="${num < 10}">
														<option value="${num}">0${num}분</option>
													</c:if>
													<c:if test="${num > 9}">
														<option>${num}분</option>
													</c:if>
												</c:forEach>
											</select>
											<span class="btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-link" id="preview-pc-page">미리보기</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div style="width:100%; height:429px;" id="pcCornerSheet"></div>
								</div>
								<!-- E : ibsheet-wrap -->

								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">Mobile 코너 목록</h3>
									</div>
									<div class="fr">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<span class="btn-group" style="margin-right: 10px">
												<a href="#" class="btn-sm btn-link preview-btn mobile-type">템플릿 보기</a>
											</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="미리보기 날짜 선택" id="preview-mobile-date">
											</span>
											<select class="ui-sel" title="미리보기 기준 시 선택">
												<c:forEach begin="0" end="23" var="num">
													<c:if test="${num < 10}">
														<option>0${num}시</option>
													</c:if>
													<c:if test="${num > 9}">
														<option>${num}시</option>
													</c:if>
												</c:forEach>				
											</select>
											<select class="ui-sel" title="미리보기 기준 분 선택">
												<c:forEach begin="0" end="50" var="num" step="10">
													<c:if test="${num < 10}">
														<option value="${num}">0${num}분</option>
													</c:if>
													<c:if test="${num > 9}">
														<option>${num}분</option>
													</c:if>
												</c:forEach>
											</select>
											<span class="btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-link" id="preview-mobile-page">미리보기</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div style="width:100%; height:429px;" id="mobileCornerSheet"></div>
								</div>
								<!-- E : ibsheet-wrap -->
							</div>
							<!-- E:tab_content -->
						</div>
						<!-- E : tab-wrap -->
					</div>
				</div>
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.page.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.page.contents.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>