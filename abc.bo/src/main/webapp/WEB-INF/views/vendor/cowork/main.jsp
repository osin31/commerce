<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		//입점사검색 영역 세팅
		abc.biz.vendor.cowork.event();
		
		// 입점사 목록 그리드 초기화
		abc.biz.vendor.cowork.initVendorCoworkSheet();
		
		// enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#vendorCoworkSerch").trigger("click");
		    }
		});
		
		// 대시보드에서 더보기 클릭으로 왔을때 미답변 검색
		abc.biz.vendor.cowork.fromDashSearch('<c:out value="${fromDash}"/>', '<c:out value="${tabIdx}"/>');
	});
</script>

	<!-- S : container -->
	<div class="container">
	<form id="vendorForm" name="vendorForm">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">협력 게시판 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>입점관리</li>
							<li>협력 게시판 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">협력 게시판 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>협력 게시판 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">검색어</th>
								<td class="input" colspan="4">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
											<option value="">선택</option>
											<option value="inqryTitleText">제목</option>
											<option value="inqryName">작성자명</option>
											<option value="inqryLoginId">작성자ID</option>
											<option value="aswrName">답변자명</option>
											<option value="aswrLoginId">답변자ID</option>
										</select>
										<input type="text" id="searchValue" name="searchValue" class="ui-input" placeholder="검색어 입력" title="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">답변상태</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input type="radio" name="aswrStatCode" id="aswrStatCode0" value="" checked>
												<label for="aswrStatCode0">전체</label>
											</span>
										</li>
										<c:forEach var="codeList" items="${codeList.ASWR_STAT_CODE}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="aswrStatCode" id="aswrStatCode<c:out value="${status.count}" />"
														   value="<c:out value="${codeList.codeDtlNo}" />" >
													<label for="aswrStatCode<c:out value="${status.count}" />" >
														<c:out value="${codeList.codeDtlName}" />
													</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<c:if test="${userDetails.adminAuthorities[0].upAuthNo != 'ROLE_30000'}">
									<th scope="row">입점사 검색</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="inqryGbn" id="inqryGbnAll" checked>
													<label for="inqryGbnAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="inqryGbn" id="inqryGbnQ" value="Q">
													<label for="inqryGbnQ">문의</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="inqryGbn" id="inqryGbnA" value="A" >
													<label for="inqryGbnA">답변</label>
												</span>
											</li>
											<li>
												<input type="text" class="ui-input" title="입점업체 입력" id="vndrName" name="vndrName" size="10" readOnly style="display:none">
												<input type="hidden" class="ui-input" id="vndrNo" name="vndrNo" value="">
												<a href="javascript:void(0);" id="searchVendor" class="btn-sm btn-link">입점업체 찾기</a>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</c:if>
								<c:if test="${userDetails.adminAuthorities[0].upAuthNo == 'ROLE_30000'}">
									<th scope="row">작성/답변 구분</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="inqryGbn" id="inqryGbnAll" value="All" checked>
													<label for="inqryGbnAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
												<input type="radio" name="inqryGbn" id="inqryGbnQ" value="Q" >
													<label for="inqryGbnQ">작성 게시물만보기</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="inqryGbn" id="inqryGbnA" value="A" >
													<label for="inqryGbnA">답변대상 게시물만보기</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-text-box -->
									</td>
								</c:if>
								<%-- <th scope="row">작성자 구분</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel" title="작성자 구분 선택" name="inqryGbnType" id="inqryGbnType">
											<option value="">전체</option>
											<option value="<c:out value="${Const.INQRY_GBN_TYPE_ADMIN}"/>">ABC-MART</option>
											<option value="<c:out value="${Const.INQRY_GBN_TYPE_CALLCENTER}"/>">고객센터</option>
											<option value="<c:out value="${Const.INQRY_GBN_TYPE_VENDOR}"/>">입점업체</option>
										</select>
										<input type="text" class="ui-input" title="입점업체 입력" id="vndrName" name="vndrName" size="10" readOnly style="display:none">
										<input type="hidden" class="ui-input" id="vndrNo" name="vndrNo" value="">
										<a href="javascript:void(0);" id="searchVendor" class="btn-sm btn-link" style="display:none">입점업체 찾기</a>
									</span>
									<!-- E : ip-text-box -->
								</td> --%>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
											<option value="">선택</option>
											<option value="inqryDtm" selected>작성일</option>
											<option value="aswrDtm">답변일</option>
										</select>
										<span class="date-box">
											<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group">
											<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);" name="per1Year"  class="btn-sm btn-func">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="resetButton" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="vendorCoworkSerch" class="btn-normal btn-func">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">협력게시판 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="selTermsModule">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
					<!-- S : btn-group -->
					<span class="btn-group">
						<a href="javascript:void(0);" id="coworkDeleteBtn" class="btn-sm btn-del">선택삭제</a>
						<c:if test="${userDetails.adminAuthorities[0].upAuthNo != 'ROLE_30000'}">
							<a href="javascript:void(0);" id="sendCoworkEmail" class="btn-sm btn-link">선택 메일 보내기</a>
							<a href="javascript:void(0);" id="sendCoworkSms" class="btn-sm btn-link">선택 SMS 보내기</a>
						</c:if>
					</span>
					<!-- E : btn-group -->
				</div>
				<div class="fr">
					<a href="javascript:void(0);" id="vendorCoworkRegist" class="btn-sm btn-link">등록</a>
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="vendorCoworkGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
		<input type="hidden" id="adminNo" value="<c:out value="${userDetails.adminNo}" />">
		<input type="hidden" id="upAuthNo" value="<c:out value="${userDetails.adminAuthorities[0].upAuthNo}"/>" />">
		</form>
	</div>
	<!-- E : container -->
<script src="/static/common/js/biz/vendor/abcmart.vendor.cowork.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>