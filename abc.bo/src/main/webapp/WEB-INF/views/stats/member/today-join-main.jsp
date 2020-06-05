<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<!-- S : container -->
<div class="container">
	<form id="todayJoinForm" name="todayJoinForm" method="post" onsubmit="return false;">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">당일 회원가입 현황</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>매출/정산/통계</li>
						<li>실시간 현황 </li>
						<li>당일 회원가입현황</li>
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
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
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
											<input name="deviceTypeCodeArr" type="checkbox" id="chkdeviceType0${status.index}" value="${data.codeDtlNo}" class="check-item" checked />
											<label for="chkdeviceType0${status.index}">${data.codeDtlName}</label>
										</span>
									</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">시간대</th>
							<td class="input">
								<span class="ip-text-box">
									<select class="ui-sel" title="시 선택" name="startHour" id="startHour">
										<option value="">선택</option>
										<c:forEach var="data" begin="0" end="23" varStatus="i">
										<option value="${i.index}">${i.index}시</option>
										</c:forEach>
									</select>
									<span class="text">~</span>
									<select class="ui-sel" title="시 선택" name="endHour" id="endHour">
										<option value="">선택</option>
										<c:forEach var="data" begin="1" end="24" varStatus="i">
										<option value="${i.index}">${i.index}시</option>
										</c:forEach>
									</select>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="todayJoinFormReset"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-normal btn-func" id="todayJoinFormSearch">검색</a>
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
			<div class="fl">
				<!-- S : opt-group -->
				<%-- <span class="opt-group">
					<label class="title" for="selTermsModule">목록개수</label>
					<select id="pageCount" class="ui-sel">
					<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span> --%>
				<!-- E : opt-group -->
			</div>
			<div class="fr">
				<a href="javascript:void(0);" class="btn-sm btn-func disabled"  id="excel">엑셀 다운로드</a>
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div style="width:100%; height:429px;" id="todayJoinGrid"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
	</form>
</div>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.today.member.join.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
