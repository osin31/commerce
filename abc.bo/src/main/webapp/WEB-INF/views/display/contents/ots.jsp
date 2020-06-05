<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">OTS 콘텐츠 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>OTS 콘텐츠 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">OTS 콘텐츠 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<form name="searchForm" id="searchForm">
				<input type="hidden" name="wbznType" value="OTS">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>OTS 콘텐츠 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" title="기간 선택" name="dateType">
												<option value="01">작성일</option>
												<option value="02">수정일</option>
												<option value="03">전시시작일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="startYmd" id="startYmd">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="endYmd" id="endYmd">
											</span>
											<span class="btn-group area-calendar-btn-group">
												<a href="#" class="btn-sm btn-func calendar-today">오늘</a>
												<a href="#" class="btn-sm btn-func calendar-week">일주일</a>
												<a href="#" class="btn-sm btn-func calendar-month">한달</a>
												<a href="#" class="btn-sm btn-func calendar-year">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">콘텐츠 유형</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="wbznTypeArr" value="" id="chkContentType01" checked>
													<label for="chkContentType01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="wbznTypeArr" class="wbznType-checkbox-item" id="chkContentType02" value="O" checked>
													<label for="chkContentType02">MAGAZINE</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="wbznTypeArr" class="wbznType-checkbox-item" id="chkContentType03" value="S" checked>
													<label for="chkContentType03">STYLING</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="wbznTypeArr" class="wbznType-checkbox-item" id="chkContentType04" value="E" checked>
													<label for="chkContentType04">EXCLUSIVE</label>
												</span>
											</li>
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
													<input type="radio" name="dispYn" id="radioDisplay01" checked value="">
													<label for="radioDisplay01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisplay02" value="Y">
													<label for="radioDisplay02">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="dispYn" id="radioDisplay03" value="N">
													<label for="radioDisplay03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<c:if test="${ wbznType == 'S'}">
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="keywordType">
												<option value="">선택</option>
												<option value="01">제목</option>
												<option value="02">작성자</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="keyword">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									</c:if>
								</tr>
								<c:if test="${ wbznType == 'O'}">
								<tr>
									<th scope="row">검색어</th>
									<td class="input" colspan="4">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="keywordType">
												<option value="">선택</option>
												<option value="01">제목</option>
												<option value="02">작성자</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="keyword">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0)" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<button type="submit" class="btn-normal btn-func" id="btn-search">검색</button>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->
				</form>

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">${ wbznType == 'O' ? 'OTS MAGAZINE ' : 'OTS Styling '} 목록</h3>
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
						<a href="/display/contents/ots/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="otsSheet" style="width:100%; height:429px;">
						ibsheet grid영역(div 삭제 필요)
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
		
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.contents.ots.js<%=_DP_REV%>"></script>
		
<%@include file="/WEB-INF/views/common/footer.jsp" %>