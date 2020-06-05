<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<!-- S : container -->
<div class="container">
	<form id="todayLoginForm" name="todayLoginForm" method="post" onsubmit="return false;">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">당일 로그인 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>실시간 현황 </li>
						<li>당일 로그인 현황</li>
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
			<div class="search-inner">
				<table class="tbl-search">
					<caption>검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">사이트 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioSiteAll" name="siteNo" value="All" type="radio" checked>
											<label for="radioSiteAll">전체</label>
										</span>
									</li>
									<c:forEach items="${siteList}" var="site" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="${site.siteNo}" value="${site.siteNo}">
												<label for="${site.siteNo}">${site.siteName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">디바이스 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkdeviceTypeAll" name="chkdeviceTypeAll" type="checkbox" checked>
											<label for="chkdeviceTypeAll">전체</label>
										</span>
									</li>
									<c:forEach var="data" items="${codeList.DEVICE_CODE}" varStatus="status">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input name="deviceTypeCodeArr" type="checkbox" id="chkdeviceType0${status.index}" value="${data.codeDtlNo }" class="check-item" checked/>
											<label for="chkdeviceType0${status.index}">${data.codeDtlName }</label>
										</span>
									</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">SNS 계정 로그인 여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="snsLoginYn" name="snsLoginYn" type="checkbox" value="Y">
											<label for="snsLoginYn">SNS 계정 로그인 여부</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">SNS 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkSnsTypeAll" name="chkSnsTypeAll" type="checkbox" value="" disabled>
											<label for="chkSnsTypeAll">전체</label>
										</span>
									</li>
									<c:forEach var="data" items="${codeList.SNS_GBN_CODE}" varStatus="status">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" id="chkSnsType0${status.index}" name="chkSnsTypeCodeArr" value="${data.codeDtlNo}" disabled>
												<label for="chkSnsType0${status.index}">${data.codeDtlName}</label>
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
						<a href="javascript:void(0);" class="btn-sm btn-func" id="todayLoginFormReset"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-normal btn-func" id="todayLoginFormSearch">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ul class="tbl-desc-list">
				<li class="tc-red">* 기준일시 : <fmt:formatDate value="${currentTime}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></li>
				<li>* 실 데이터에 대한 집계이므로 검색 시 느릴 수 있습니다. </li>
				<li>* 당일 매출 현황 자료는 온라인회원, 멤버십회원 대상 데이터입니다. </li>
				<li>* 온라인회원의 성별은 부가정보 내 정보로 정확하지 않을 수 있습니다.</li>
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
			<div class="fr">
				<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="MemberTodayLogingGrid"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
	</form>
</div>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.member.today.login.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>