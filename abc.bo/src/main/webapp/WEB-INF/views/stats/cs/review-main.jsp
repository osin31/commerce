<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">상품 후기 통계</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>CS 통계 </li>
						<li>상품 후기 통계</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<!-- S : search-wrap -->
		<div class="search-wrap">
			<form id="form">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>검색 테이블이며 기간, 구입처구분, 사이트 구분, 회원 유형, 디바이스 구분에 대해 검색합니다.</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px;">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">기간</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box date">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="fromDate">
									</span>
									<span class="text date">~</span>
									<span class="date-box date">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="toDate">
									</span>
										<span class="btn-group date">
										<a href="javascript:void(0);"   name="perYesterday" class="btn-sm btn-func">어제</a>
										<a href="javascript:void(0);"   name="perWeek"  	class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0);"   name="per1Month" 	class="btn-sm btn-func">한달</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">구입처구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBuy01" name="onlnBuyYn" type="radio" value="" checked>
											<label for="radioBuy01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBuy02" name="onlnBuyYn" type="radio" value="Y">
											<label for="radioBuy02">온라인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBuy03" name="onlnBuyYn" type="radio" value="N">
											<label for="radioBuy03">오프라인</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">사이트 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="site-no-all" name="siteNo" type="radio" checked value="">
											<label for="site-no-all">전체</label>
										</span>
									</li>
									<c:forEach var="item" items="${searchConditionSiteList }">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="site-no-${item.siteNo }" name="siteNo" type="radio" value="${item.siteNo }">
												<label for="site-no-${item.siteNo }">${item.siteName }</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">회원 유형</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="member-type-code-all" type="checkbox" checked value="">
											<label for="member-type-code-all">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="member-type-code-10000" name="memberTypeCodeArr" type="checkbox" class="member-type-code" checked value="10000">
											<label for="member-type-code-10000">온라인회원</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="member-type-code-10001" name="memberTypeCodeArr" type="checkbox" class="member-type-code" checked value="10001">
											<label for="member-type-code-10001">통합멤버십</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<%--
							<th scope="row">디바이스 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="device-code-all" type="checkbox" checked value="">
											<label for="device-code-all">전체</label>
										</span>
									</li>
									<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
										<li>
											<span class="ui-chk">
												<input id="device-code-${deviceCode.codeDtlName}" class="device-code" value="${deviceCode.codeDtlNo}" name="deviceCodeArr"  type="checkbox" checked>
												<label for="device-code-${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							--%>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="#" class="btn-normal btn-func" id="search">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</form>
		</div>
		<!-- E : search-wrap -->
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li>* 상품후기 작성일 기준이므로 조회 일에 따라 차이가 있을 수 있으며,</li>
				<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다.</li>
			</ul>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<label class="title" for="page-count">목록개수</label>
					<select class="ui-sel" id="page-count">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<!-- E : opt-group -->
			</div>
			<div class="fr">
				<a href="#" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="reviewGrid" style="width:200%; height:429px;"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->
<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.cs.review.js<%=_DP_REV%>"></script>