<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>횟수 기록 히스토리</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">횟수 기록 히스토리</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<!-- S : search-wrap -->
			<div class="search-wrap">
			<form id="urlLogForm" name="urlLogForm" method="post" onsubmit="return false;">
				<input type="hidden" id="menuNo" name="menuNo" value="<c:out value="${param.menuNo}"/>">
				<input type="hidden" id="accessType" name="accessType" value="<c:out value="${param.accessType}"/>">
				
				<div class="search-inner">
					<table class="tbl-search">
						<caption>주문내역 검색</caption>
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
											<option value="<c:out value="${data.adminNo}"/>" <c:if test="${data.adminNo eq param.adminNo}">selected</c:if>><c:out value="${data.loginId}" /></option>
										</c:forEach>
									</select>
								</td>
								<td></td>
								<th scope="row">메뉴 카테고리</th>
								<td class="input">
									<span class="ip-text-box">
										<select class="ui-sel" title="메뉴 선택1" name="menuNo1" id="menuNo1" style="display:<c:if test="${empty MENU_DATA_1.menuNo}">none</c:if>;">
											<option value="">-- 전체 --</option>
											<c:forEach items="${SELECT_DATA_1}" var="data">
												<option value="${data.menuNo}" <c:if test="${data.menuNo eq MENU_DATA_1.menuNo}">selected</c:if>>${data.menuName}</option>	
											</c:forEach>
										</select>
										<select class="ui-sel" title="하위메뉴 선택2" name="menuNo2" id="menuNo2" style="display:<c:if test="${empty MENU_DATA_2.menuNo}">none</c:if>;" >
											<option value="">-- 전체 --</option>
											<c:forEach items="${SELECT_DATA_2}" var="data">
												<option value="${data.menuNo}" <c:if test="${data.menuNo eq MENU_DATA_2.menuNo}">selected</c:if>>${data.menuName}</option>	
											</c:forEach>
										</select>
										<select class="ui-sel" title="하위메뉴 선택3" name="menuNo3" id="menuNo3" style="display:<c:if test="${empty MENU_DATA_3.menuNo}">none</c:if>;" >
											<option value="">-- 전체 --</option>
											<c:forEach items="${SELECT_DATA_3}" var="data">
												<option value="${data.menuNo}" <c:if test="${data.menuNo eq MENU_DATA_3.menuNo}">selected</c:if>>${data.menuName}</option>	
											</c:forEach>
										</select>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<span class="term-date-wrap">
										<span class="date-box">
											<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10" value="<c:out value="${param.fromDate}"/>" readonly>
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10" value="<c:out value="${param.toDate}"/>" readonly>
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

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount" name="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="urlLogGrid" style="width:100%; height:429px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link">확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.menu.history.detail.js<%=_DP_REV%>" ></script>