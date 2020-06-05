<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<!-- S : container -->
<form id="urlLogForm" name="urlLogForm" method="post" onsubmit="return false;">
	<input type="hidden" id="level" name="level" value="0">

	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">관리자 접근 이력</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>시스템 관리</li>
							<li>로그기록 관리</li>
							<li>관리자 접근 이력</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 접근 이력 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>관리자 접근 이력 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">관리자 선택</th>
								<td class="input">
									<select class="ui-sel" id="adminNo" name="adminNo" title="관리자 선택">
										<option value="">전체</option>
										<c:forEach items="${ADMIN_DATA}" var="data">
											<option value="<c:out value="${data.adminNo}"/>"><c:out value="${data.loginId}"/></option>
										</c:forEach>
									</select>
								</td>
								<td></td>
								<th scope="row">메뉴 카테고리</th>
								<td class="input">
									<span class="ip-text-box">
										<select class="ui-sel" title="메뉴 선택" name="menuNo1" id="menuNo1">
											<option value="">-- 전체 --</option>
											<c:forEach items="${MENU_DATA}" var="data">
												<option value="${data.menuNo}">${data.menuName}</option>	
											</c:forEach>
										</select>
										<select class="ui-sel" title="하위메뉴 선택" name="menuNo2" id="menuNo2" style="display:none;" >
											<option value="">-- 전체 --</option>
										</select>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<span class="term-date-wrap">
										<span class="date-box">
											<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10" readonly>
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10" readonly>
										</span>
										<span class="btn-group">
											<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);" name="perYear" class="btn-sm btn-func">1년</a>
										</span>
									</span>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0)" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-normal btn-func" id="searchBtn">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 접근 이력</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount" name="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="urlLogGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</form>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.menu.history.js<%=_DP_REV%>" ></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>