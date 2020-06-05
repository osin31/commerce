<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">전시 카테고리 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>전시 기준정보 관리</li>
								<li>전시 카테고리 관리</li>
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
								<h3 class="content-title">전시 카테고리 목록</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tree-filter-wrap -->
						<div class="tree-filter-wrap">
							<form id="searchForm" name="searchForm">								
								<select class="ui-sel" title="채널 선택" id="chnnlNo" name="chnnlNo">
									<c:forEach var="list" items="${chnnlList}">
										<c:if test="${list.chnnlNo != Const.CHNNL_NO_ART}">
											<option value="${list.chnnlNo}" data-site-no="${list.siteNo}">${list.chnnlName}</option>
										</c:if>
									</c:forEach>
								</select>
								<select class="ui-sel" title="사용여부 선택" name="useYn">
									<option value>전체</option>
									<option value="Y" selected>사용</option>
									<option value="N">사용안함</option>
								</select>
								<input type="hidden" name="chnnlName">
							</form>
						</div>
						<!-- E : tree-filter-wrap -->
						
						<!-- S : tbl-controller -->
						<div class="tbl-controller">
							<div class="fl">
								<button type="button" class="btn-sm btn-only-ico next" id="nextBtn"><i class="fa fa-caret-down"></i></button>
								<button type="button" class="btn-sm btn-only-ico prev" id="prevBtn"><i class="fa fa-caret-up"></i></button>
							</div>
							<div class="fr">
								<button type="button" class="btn-sm btn-save saveSortBtn">순서 저장</button>
								<button type="button" class="btn-sm btn-func addCtgrBtn">카테고리 등록</button>
							</div>
						</div>
						<!-- E : tbl-controller -->						

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
							<div style="width:100%; height:100%;" id="dpCtgrSheet"></div>
						</div>
						<!-- E : ibsheet-wrap -->
					</div>

					<div class="col">
						<!-- S : tab-wrap -->
						<div class="tab-wrap">
							<ul class="tabs">
								<li class="tab-item"><a href="#tabContent1" class="tab-link" id="categoryArea">전시 카테고리 관리</a></li>
								<!-- DESC : 전시 콘텐츠 관리 탭 비활성화시, tab-link 영역 tab-disabled 클래스 추가 -->
								<li class="tab-item"><a href="#tabContent2" class="tab-link tab-disabled" id="contentsArea">전시 콘텐츠 관리</a></li>
							</ul>
							<!-- S:tab_content -->
							<form id="ctgrForm" name="ctgrForm">
							<div id="tabContent1" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">전시 카테고리 기본정보</h3>
									</div>
								</div>
								<!-- E : content-header -->
								
								<!-- S : tbl-row -->
								
								<table class="tbl-row">
									<input type="hidden" name="ctgrNo" value>
									<input type="hidden" name="upCtgrNo" value>
									<input type="hidden" name="ctgrLevel" value>
									<input type="hidden" name="siteNo" value>
									<input type="hidden" name="chnnlNo" value>
									<caption>전시 카테고리 기본정보</caption>
									<colgroup>
										<col style="width: 140px;">
										<col>
										<col style="width: 140px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">카테고리 경로</th>
											<td colspan="3" id="ctgrPath"></td>
										</tr>
										<tr>
											<th scope="row">전시 카테고리 코드</th>
											<td colspan="3" id="ctgrNoArea"></td>
										</tr>
										<tr>
											<th scope="row">
												<span class="th-required">전시카테고리명</span>
											</th>
											<td class="input" colspan="3">
												<input type="text" class="ui-input" name="ctgrName" id="ctgrName" title="전시카테고리명 입력" placeholder="20자 이내로 입력" maxlength="20">
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
										<tr>
											<th scope="row">
												<span class="th-required">Leaf Category 여부</span>
											</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioLeafY" name="leafCtgrYn" type="radio" value="Y">
															<label for="radioLeafY">예</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioLeafN" name="leafCtgrYn" type="radio" value="N" checked>
															<label for="radioLeafN">아니오</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
											<th scope="row">
												<span class="th-required">검색필터 사용여부</span>
											</th>
											<td class="input">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioFilterY" name="srchFilterUseYn" type="radio" value="Y">
															<label for="radioFilterY">사용</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioFilterN" name="srchFilterUseYn" type="radio" value="N" checked>
															<label for="radioFilterN">사용안함</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
										</tr>
										<tr class="pc-disp-tmpl-row">
											<th scope="row">PC 템플릿</th>
											<td class="input" colspan="3">
												<span class="ip-search-box">
													<input type="text" class="ui-input disp-tmpl-name" id="pcDispTmplName" disabled>
													<input type="hidden" id="pcDispTmplNo" name="pcDispTmplNo" class="disp-tmpl-no">
													<a href="#" class="btn-search searchTmpl forPc"><i class="ico-search"><span class="offscreen">검색</span></i></a>
													<button type="button" class="btn-reset reset-disp-tmpl-btn">선택 취소<i class="fas fa-times"></i></button>
												</span>
											</td>
										</tr>
										<tr class="mobile-disp-tmpl-row">
											<th scope="row">Mobile 템플릿</th>
											<td class="input" colspan="3">
												<span class="ip-search-box">
													<input type="text" class="ui-input disp-tmpl-name" id="mobileDispTmplName" disabled>
													<input type="hidden" id="mobileDispTmplNo" name="mobileDispTmplNo" class="disp-tmpl-no">
													<a href="#" class="btn-search searchTmpl forMo"><i class="ico-search"><span class="offscreen">검색</span></i></a>
													<button type="button" class="btn-reset reset-disp-tmpl-btn">선택 취소<i class="fas fa-times"></i></button>
												</span>
											</td>
										</tr>
										<tr style="display: none;">
											<th scope="row">카테고리명 노출 타입</th>
											<td class="input" colspan="3">
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioCategoryT" name="ctgrNameDispType" type="radio" value="T" checked>
															<label for="radioCategoryT">텍스트</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="radioCategoryI" name="ctgrNameDispType" type="radio" value="I">
															<label for="radioCategoryI">이미지</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
											</td>
										</tr>
										<!-- S : 카테고리명 노출 타입이 이미지인경우, 노출되는 영역 -->
										<tr id="pcImageArea" style="display: none;">
											<th scope="row">
												<span class="th-required">카테고리명 이미지(PC)</span>
												<div>N*N (최대 000MB까지 등록가능 <br />파일유형 : jpg, gif)</div>
											</th>
											<td class="input" colspan="3">
												<!-- S : file-wrap -->
												<div class="file-wrap">
													<ul class="file-list">
														<li>
															<span class="btn-box">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input type="hidden" name="pcImagePathText">
																<input type="hidden" name="pcImageName">
																<input type="file" id="imageFile01" class="pcImage" title="첨부파일 추가" name="pcImageFile">
																<label for="imageFile01">찾아보기</label>
															</span>
														</li>
														<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
														<li>
															<a href="#" class="subject" id="pcImageName"></a>
															<button type="button" class="btn-file-del" id="pcDelBtn">
																<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</ul>
													<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
													<div class="alt-wrap">
														<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" id="pcAltrnText" name="pcAltrnText">
													</div>
													<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
													<div class="img-wrap" id="pcImage"></div>
												</div>
												<!-- E : file-wrap -->
											</td>
										</tr>
										<tr id="moImageArea" style="display: none;">
											<th scope="row">
												<span class="th-required">카테고리명 이미지(Mobile)</span>
												<div>N*N (최대 000MB까지 등록가능 <br />파일유형 : jpg, gif)</div>
											</th>
											<td class="input" colspan="3">
												<!-- S : file-wrap -->
												<div class="file-wrap">
													<ul class="file-list">
														<li>
															<span class="btn-box">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																	<input type="hidden" name="mobileImagePathText">
																	<input type="hidden" name="mobileImageName">
																<input type="file" id="imageFile02" class="mobileImage" title="첨부파일 추가" name="mobileImageFile">
																<label for="imageFile02">찾아보기</label>
															</span>
														</li>
														<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
														<li>
															<a href="#" class="subject" id="mobileImageName"></a>
															<button type="button" class="btn-file-del delBtn" id="mobileDelBtn">
																<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</ul>
													<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
													<div class="alt-wrap">
														<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" id="mobileAltrnText" name="mobileAltrnText">
													</div>
													<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
													<div class="img-wrap" id="mobileImage"></div>
												</div>
												<!-- E : file-wrap -->
											</td>
										</tr>
										<!-- E : 카테고리명 노출 타입이 이미지인경우, 노출되는 영역 -->
									</tbody>
								</table>
								<!-- E : tbl-row -->								
								<div class="tbl-desc-wrap">
									<!-- s : 190918 추가 | 테이블 하단 안내문구 추가 -->
									<ol class="tbl-desc-list">
										<li>※ 템플릿 등록 또는 변경 시 1) 전시여부 '전시안함', 2) 사용여부 '사용' 선택 후 적용 가능합니다.</li>
										<li>※ 템플릿 적용 후 1) 사용여부 ‘사용’, 2)전시여부 ‘전시’로 다시 선택 후 저장버튼을 클릭해야 반영 됩니다.</li>
									</ol>
									<!-- e : 190918 추가 | 테이블 하단 안내문구 추가 -->
								</div>
								<!-- S : content-bottom -->
								<div class="content-bottom">
									<a href="#" class="btn-lg btn-save" id="saveBtn">저장</a>
								</div>
								<!-- E : content-bottom -->
							</div>
							</form>
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
												<a href="javascript:void(0);p" class="btn-sm btn-link" id="preview-pc-page">미리보기</a>
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
									<div style="width:100%; height:429px;" id="mobileCornerSheet">
										ibsheet grid영역(div 삭제 필요)
									</div>
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
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.category.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.category.contents.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>