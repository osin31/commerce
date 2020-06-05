<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

	<!-- S : container -->
	<form name="pgForm">
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">PG 대사관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="javascript:void(0);"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>매출/정산/통계</li>
								<li>자사 매출 정산 관리</li>
								<li>PG 대사관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->
	
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">내역 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->
	
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>내역 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" id="fromDate" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" id="toDate" title="종료일 선택">
											</span>
											<span class="btn-group">
												<a href="javascript:void(0);" class="btn-sm btn-func" id="today">오늘</a>
												<a href="javascript:void(0);" class="btn-sm btn-func" id="week">일주일</a>
												<a href="javascript:void(0);" class="btn-sm btn-func" id="month">한달</a>
												<a href="javascript:void(0);" class="btn-sm btn-func text-center" id="year">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">사이트</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="siteNoAll" checked>
													<label for="siteNoAll">전체</label>
												</span>
											</li>
											<c:forEach var="siteNo" items="${siteNo}">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="siteNo_${siteNo.siteNo}" value="${siteNo.siteNo}">
													<label for="siteNo_${siteNo.siteNo}">${siteNo.siteName}</label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">거래구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="dealTypeAll" id="dealTypeAll" checked>
													<label for="dealTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="dealType" id="dealPay" checked>
													<label for="dealPay">결제</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="dealType" id="dealCancel" checked>
													<label for="dealCancel">취소</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="dealType" id="dealRefund" checked>
													<label for="dealRefund">환불</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select name="srchType" class="ui-sel" title="검색어 타입 선택">
												<option value="orderNo">주문번호</option>
												<option value="approvNo">승인번호</option>
											</select>
											<input type="text" name="searchWord" class="ui-input" placeholder="검색어 입력" title="검색어 입력">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">매칭여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="matchYn" id="matchAll" checked>
													<label for="matchAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="matchYn" id="matchYes">
													<label for="matchYes">매칭</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="matchYn" id="matchNo">
													<label for="matchNo">미매칭</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
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
						<h3 class="content-title">정산 내역</h3>
					</div>
				</div>
				<!-- E : content-header -->
				
				<!-- S : tbl-col -->
					<table class="tbl-col">
						<caption>정산 내역</caption>
						<colgroup>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col>
						</colgroup>
						<thead>
							<tr>
								<th scope="col" rowspan="3">PG사명</th>
								<th scope="colgroup" colspan="6">구분</th>
							</tr>
							<tr>
								<th scope="colgroup" colspan="2">당사 결제금액</th>
								<th scope="colgroup" colspan="2">PG사 결제금액</th>
								<th scope="colgroup" colspan="2">미매칭금액</th>
							</tr>
							<tr>
								<th scope="col">결제금액</th>
								<th scope="col">배송비</th>
								<th scope="col">결제금액</th>
								<th scope="col">배송비</th>
								<th scope="col">결제금액</th>
								<th scope="col">배송비</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><button type="button" class="link">KCP</button></td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
							</tr>
							<tr>
								<td><button type="button" class="link">네이버페이</button></td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
							</tr>
							<tr>
								<td><button type="button" class="link">카카오페이</button></td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
							</tr>
							<tr>
								<td><button type="button" class="link">기프트카드</button></td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th scope="row" class="tc-bold">합계</th>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
								<td class="text-right">0원</td>
							</tr>
						</tfoot>
					</table>
					<!-- E : tbl-col -->
				
				
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
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-save" id="saveBtn">저장</a>
						<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
					</div>
				</div>
	
				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div style="width:100%; height:429px;" id="pgGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
	</form>
	<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/settlement/abcmart.pg.main.js<%=_DP_REV%>"></script>	
<%@include file="/WEB-INF/views/common/footer.jsp"%>