<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">쿠폰 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>프로모션 관리</li>
								<li>쿠폰 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">쿠폰 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm">
					<div class="search-wrap">
						<div class="search-inner">
							<table class="tbl-search">
								<caption>쿠폰 검색</caption>
								<colgroup>
									<col style="width: 110px;">
									<col>
									<col style="width: 79px">
									<col style="width: 110px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">기간조회</th>
										<td class="input" colspan="4">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<select name="dateType" class="ui-sel" title="기간 선택">
													<option value="issueStartYmd">발급 시작일</option>
													<option value="issueEndYmd">발급 종료일</option>
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
										<th scope="row">쿠폰 유형</th>
										<td class="input">
											<select name="cpnTypeCode" class="ui-sel" title="쿠폰 유형 선택">
												<option value="">전체</option>
												<c:forEach var="code" items="${cpnTypeCodeList}">
													<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
										<th scope="row">쿠폰 분류</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="normalCpnYn" id="radioType01" checked value="">
														<label for="radioType01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="normalCpnYn" id="radioType02" value="Y">
														<label for="radioType02">일반쿠폰</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="normalCpnYn" id="radioType03" value="N">
														<label for="radioType03">플러스쿠폰</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">쿠폰 속성</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="chkSiteModule" id="chkSite01" checked value="ALL">
														<label for="chkSite01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="cpnUseGbnTypes" id="chkSite02" value="E" checked>
														<label for="chkSite02">행사_일반</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="cpnUseGbnTypes" id="chkSite05" value="F" checked>
														<label for="chkSite05">행사_다운로드</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="cpnUseGbnTypes" id="chkSite03" value="C" checked>
														<label for="chkSite03">CS</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="cpnUseGbnTypes" id="chkSite04" value="D" checked>
														<label for="chkSite04">eDM</label>
													</span>
												</li>
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
										<th scope="row">전시여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioDisplay01" name="dispYn" type="radio" checked value="">
														<label for="radioDisplay01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioDisplay02" name="dispYn" type="radio" value="Y">
														<label for="radioDisplay02">전시</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioDisplay03" name="dispYn" type="radio" value="N">
														<label for="radioDisplay03">전시안함</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">쿠폰존 전시여부</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="cpnZoneDispYn" id="radioZone01" checked value="">
														<label for="radioZone01">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="cpnZoneDispYn" id="radioZone02" value="Y">
														<label for="radioZone02">전시</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="cpnZoneDispYn" id="radioZone03" value="N">
														<label for="radioZone03">전시안함</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">
											<span class="th-required">사용처 <br />온/오프라인 여부</span>
										</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioOnOff00" name="usePlaceGbnType" type="radio" checked value="">
														<label for="radioOnOff00">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioOnOff01" name="usePlaceGbnType" type="radio" value="O">
														<label for="radioOnOff01">온라인</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioOnOff02" name="usePlaceGbnType" type="radio" value="F">
														<label for="radioOnOff02">오프라인</label>
													</span>
												</li>
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="radioOnOff03" name="usePlaceGbnType" type="radio" value="A">
														<label for="radioOnOff03">온라인 및 오프라인</label>
													</span>
												</li>
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
														<input id="chkDevice01" name="chkDeviceModule" type="checkbox" checked value="ALL">
														<label for="chkDevice01">전체</label>
													</span>
												</li>
												<c:forEach var="code"  items="${deviceCodeList}" varStatus="i">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="deviceCodes" id="chkDevice${i.count}" value="${code.codeDtlNo}" checked>
															<label for="chkDevice${i.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:forEach>
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
														<input id="chkChannel01" name="chkChannelModule" type="checkbox" checked value="ALL">
														<label for="chkChannel01">전체</label>
													</span>
												</li>
												<c:forEach var="channel" items="${channelList}" varStatus="i">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkChannel${i.count}" name="chnnlNos" type="checkbox" value="${channel.chnnlNo}" checked>
															<label for="chkChannel${i.count}">${channel.chnnlName}</label>
														</span>
													</li>
												</c:forEach>
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
													<option value="cpnNo">쿠폰 번호</option>
													<option value="cpnName">쿠폰명</option>
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
						<h3 class="content-title">쿠폰 목록</h3>
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
						<a href="/promotion/coupon/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="couponSheet">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->


<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.coupon.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>