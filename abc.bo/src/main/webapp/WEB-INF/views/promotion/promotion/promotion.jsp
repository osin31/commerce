<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">프로모션 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>프로모션 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">프로모션  검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>프로모션 검색</caption>
								<colgroup>
									<col style="width: 120px;">
									<col>
									<col style="width: 79px">
									<col style="width: 120px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">기간조회</th>
										<td class="input" colspan="4">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<select name="dateType" class="ui-sel" title="기간 선택">
													<option value="promoStartYmd">프로모션 시작일</option>
													<option value="promoEndYmd">프로모션 종료일</option>
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
													<!-- DESC : 20190220 수정 // 기간검색 전체 > 1년으로 공통수정 적용 -->
													<a href="javascript:void(0);" class="btn-sm btn-func text-center calendar-year">1년</a>
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
									<tr>
										<th scope="row">프로모션 유형</th>
										<td class="input">
											<select name="promoTypeCode" class="ui-sel" title="쿠폰 유형 선택">
												<option value="">선택</option>
												<c:forEach var="code" items="${promoTypeCodeList}">
													<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
										<th scope="row">사용여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="useYn" id="radioUse01" checked value="">
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
										<th scope="row">채널</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkChannel00" name="chkChannelModule" type="checkbox" checked value="ALL">
														<label for="chkChannel00">전체</label>
													</span>
												</li>
												<c:forEach var="channel" items="${channelList}" varStatus="i">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkChannel${i.count}" name="chnnlNos" type="checkbox" checked value="${channel.chnnlNo}">
															<label for="chkChannel${i.count}">${channel.chnnlName}</label>
														</span>
													</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">디바이스</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkDevice00" name="chkDeviceModule" type="checkbox" checked value="ALL">
														<label for="chkDevice00">전체</label>
													</span>
												</li>
												<c:forEach var="code"  items="${deviceCodeList}" varStatus="i">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="deviceCodes" id="chkDevice${i.count}" checked value="${code.codeDtlNo}">
															<label for="chkDevice${i.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">대상회원</th>
										<td class="input" colspan="4">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkMemberTypeAll" name="chkMemberTypeAll" type="checkbox" checked value="ALL">
														<label for="chkMemberTypeAll">전체</label>
													</span>
												</li>
												<c:forEach var="code" items="${memberTypeCodeList}" varStatus="i">
													<li>
														<span class="ui-chk">
															<input id="chkMemberType${i.count}" name="memberTypeCodes" class="chkMemberTypeCode" type="checkbox" checked value="${code.codeDtlNo}">
															<label for="chkMemberType${i.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
												<li>
													<span class="ui-chk">
														<input id="chkMemberTypeEmp" name="empYn" class="chkMemberTypeCode" type="checkbox" checked value="Y">
														<label for="chkMemberTypeEmp">임직원</label>
													</span>
												</li>
											</ul>
											<%-- <span class="member-grade-list">
												<span class="text">( 회원유형 :</span>
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<c:forEach var="code" items="${mbshpGradeCodeList}" varStatus="i">
														<li>
															<span class="ui-chk">
																<input id="chkMbshpGrade${i.count}" name="mbshpGradeCodes" type="checkbox" checked value="${code.codeDtlNo}">
																<label for="chkMbshpGrade${i.count}">${code.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
													</ul>
												<!-- E : ip-box-list -->
												<span class="text">)</span>
											</span> --%>
										</td>
									</tr>
									<tr>
										<th scope="row">진행상태</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="promoProgressStatus" id="radioProgress01" checked value="">
														<label for="radioProgress01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="promoProgressStatus" id="radioProgress02" value="ing">
														<label for="radioProgress02">진행중</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="promoProgressStatus" id="radioProgress03" value="wait">
														<label for="radioProgress03">대기</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="promoProgressStatus" id="radioProgress04" value="end">
														<label for="radioProgress04">종료</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">상품코드</th>
										<td class="input">
											<div class="opt-keyword-box">
												<input type="text" name="prdtNo" class="ui-input" title="상품코드 입력" placeholder="상품코드 입력">
											</div>
										</td>
									</tr>
									<tr>
										<th scope="row">검색어</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<select name="keywordType" class="ui-sel" title="유형 선택">
													<option value="">선택</option>
													<option value="promoNo">프로모션 번호</option>
													<option value="promoName">프로모션명</option>
												</select>
												<input type="text" name="keyword" class="ui-input" title="검색어 입력" placeholder="검색어 입력">
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
									<button type="submit" class="btn-normal btn-func">검색</button>
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
						<h3 class="content-title">프로모션 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="/promotion/promotion/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="promoSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->


<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.promotion.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>