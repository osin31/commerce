<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function(){
		abc.biz.product.warehousing.siteCombo = ${siteCombo};
		abc.biz.product.warehousing.codeCombo = ${codeCombo};
	})
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">재입고 알림 서비스 조회</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites">
					<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home">
							<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>재입고 알림 서비스 조회</li>
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
			<form name="searchForm" id="searchForm">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>상품 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기간</th>
								<td class="input" colspan="4">
									<span class="term-date-wrap">
										<span class="date-box">
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="startYmd" id="startYmd">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="endYmd" id="endYmd">
										</span>
										<span class="btn-group area-calendar-btn-group">
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-today">오늘</a>
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-week">일주일</a>
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-month">한달</a>
											<a href="javascript:void(0);" class="btn-sm btn-func calendar-year">1년</a>
										</span>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">사이트 구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="radioSiteAll" name="siteNo" type="radio" value checked>
												<label for="radioSiteAll">전체</label>
											</span>
										</li>
										<c:forEach var="site" items="${siteList}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<input id="radioSite${status.count}" name="siteNo" type="radio" value="${site.siteNo}">
													<label for="radioSite${status.count}">${site.siteName}</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">주문 여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOrder01" name="orderYn" type="radio" value checked>
											<label for="radioOrder01">전체</label>
										</span>
										</li>
										<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOrder02" name="orderYn" type="radio" value="Y">
											<label for="radioOrder02">예</label>
										</span>
										</li>
										<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioOrder03" name="orderYn" type="radio" value="N">
											<label for="radioOrder03">아니오</label>
										</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">회원</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel" title="회원 정보 선택" name="memberSearchType">
											<option value="N">회원이름</option>
											<option value="I">회원아이디</option>
										</select>
										<input type="text" class="ui-input" title="검색어 입력" placeholder="검색어 입력" name="memberSearchWord">
									</span>
									<!-- E : ip-text-box -->
								</td>
								<td></td>
								<th scope="row">상품</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" title="상품 타입 선택" name="productSearchType">
											<option value="N">상품명</option>
											<option value="I">상품ID</option>
											<option value="S">스타일 코드</option>
										</select>
										<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="productSearchWord">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">판매 상태</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<input id="chkSellStatusAll" name="sellStatCode" type="checkbox" value checked>
												<label for="chkSellStatusAll">전체</label>
											</span>
										</li>
										<c:forEach var="code" items="${sellStatCodeList}" varStatus="status">
											<li>
												<span class="ui-chk">
													<input id="chkSellStatus${status.count}" name="sellStatCodeArr" type="checkbox" value="${code.codeDtlNo}" checked>
													<label for="chkSellStatus${status.count}">${code.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">처리 상태</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<input id="chkProcessAll" name="wrhsAlertStatCode" type="checkbox" value checked>
												<label for="chkProcessAll">전체</label>
											</span>
										</li>
										<c:forEach var="code" items="${wrhsAlertStatCodeList}" varStatus="status">
											<li>
												<span class="ui-chk">
													<input id="chkProcess${status.count}" name="wrhsAlertStatCodeArr" type="checkbox" value="${code.codeDtlNo}" checked>
													<label for="chkProcess${status.count}">${code.codeDtlName}</label>
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
							<a href="javascript:void(0);" class="btn-sm btn-func" id="clearBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-normal btn-func" id="searchBtn">검색</a>
						</div>
					</div>
				</div>
			</form>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>		
		<!-- E : search-wrap -->

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
				<button type="button" class="btn-sm btn-func" id="atOnceBtn">일괄처리</button>
				<button type="button" class="btn-sm btn-func" id="cancelRequestBtn">신청취소</button>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="alertSheet"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/product/abcmart.product.warehousing.alert.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp" %>