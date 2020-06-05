<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		//입점사검색 영역 세팅
		abc.biz.vendor.inquiry.event();

		// 입점사 목록 그리드 초기화
		abc.biz.vendor.inquiry.initVendorInquirySheet();
		
		// enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#vendorInquirySerch").trigger("click");
		    }
		});

		
		abc.biz.vendor.inquiry.fromDashSearch('<c:out value="${fromDash}"/>', '<c:out value="${tabIdx}"/>');
	});
</script>

	<!-- S : container -->
	<div class="container">
	<form id="vendorForm" name="vendorForm">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">입점/제휴 문의 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>입점관리</li>
							<li>입점/제휴 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점문의 관리</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>입점문의 검색</caption>
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
											<option value="memberName">작성자명</option>
											<option value="memberLoginId">작성자ID</option>
										</select>
										<input type="text" id="searchValue" name="searchValue" class="ui-input" placeholder="검색어 입력" title="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">문의유형</th>
								<td class="input">
									<div class="opt-keyword-box">
										<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" title="검색어 타입 선택">
											<option value="">선택</option>
											<c:forEach var="inqueryDtlCode" items="${inquiryDtlCodeList}">
											<option value="${inqueryDtlCode.codeDtlNo}">${inqueryDtlCode.codeDtlName}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td></td>
								<th scope="row">사이트 구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="siteNo0" value="" checked>
												<label for="siteNo0">전체</label>
											</span>
										</li>

										<c:forEach var="siteNoList" items="${siteNoList}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="siteNo<c:out value="${status.count}" />"
													       value="<c:out value="${siteNoList.siteNo}" />" >
													<label for="siteNo<c:out value="${status.count}" />" >
														<c:out value="${siteNoList.siteName}" />
													</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">작성일 검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
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
											<a href="javascript:void(0);" name="perYearMonth" class="btn-sm btn-func">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="vendorFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="vendorInquirySerch" class="btn-normal btn-func">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점문의 목록</h3>
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
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="vendorInquiryGrid"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>

	</form>
	</div>
	<!-- E : container -->

<script src="/static/common/js/biz/vendor/abcmart.vendor.inquiry.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>
