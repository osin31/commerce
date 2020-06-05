<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() { 
		
		//목록 그리드 초기화
		abc.biz.delivery.prod.standby.main.initDataListSheet();

	});
</script>

	<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상품대기조회</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>상품대기조회</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품대기 조회 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>상품대기조회 검색</caption>
							<colgroup>
								<col style="width: 110px;">
								<col>
								<col style="width: 79px">
								<col style="width: 110px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사이트 구분</th>
									<td class="input" colspan="4">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkSiteModule" id="chkSite01" checked>
													<label for="chkSite01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkSiteModule" id="chkSite02" checked>
													<label for="chkSite02">통합몰</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkSiteModule" id="chkSite03" checked>
													<label for="chkSite03">OTS</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">조회기간</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" title="기간 선택">
												<option>주문일</option>
												<option>발생일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group">
												<a href="#" class="btn-sm btn-func">오늘</a>
												<a href="#" class="btn-sm btn-func">일주일</a>
												<a href="#" class="btn-sm btn-func">한달</a>
												<a href="#" class="btn-sm btn-func text-center">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">주문</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택">
												<option>주문자</option>
												<option>수령자</option>
												<option>휴대폰번호</option>
												<option>주문번호</option>
												<option>주문자ID</option>
											</select>
											<input type="text" class="ui-input" title="검색어 입력">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">상품</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택">
												<option>상품명</option>
												<option>상품코드</option>
											</select>
											<input type="text" class="ui-input" title="검색어 입력">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">처리상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioProcess01" name="radioProcessModule" type="radio" checked="checked">
													<label for="radioProcess01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioProcess02" name="radioProcessModule" type="radio">
													<label for="radioProcess02">대기</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioProcess03" name="radioProcessModule" type="radio">
													<label for="radioProcess03">완료</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">미출처</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioSource01" name="radioSourceModule" type="radio" checked="checked">
													<label for="radioSource01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioSource02" name="radioSourceModule" type="radio">
													<label for="radioSource02">온라인</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioSource03" name="radioSourceModule" type="radio">
													<label for="radioSource03">스마트</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioSource04" name="radioSourceModule" type="radio">
													<label for="radioSource04">오프라인 매장</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">배송중단사유</th>
									<td class="input">
										<select class="ui-sel" title="배송중단사유 선택">
											<!-- DESC : option value="" 값 기존 AS-IS와 동일  -->
											<option value="">선택</option>
											<option value="01">매장수령</option>
											<option value="02">미출고 매장발송</option>
											<option value="03">교환건 매장발송</option>
											<option value="05">재고없음</option>
											<option value="14">점간이동 이오염</option>
											<option value="99">담당자 조작</option>
											<option value="15">장기미수령</option>
										</select>
									</td>
									<td></td>
									<th scope="row">배송유형</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkDeliveryModule" id="chkDelivery01" checked>
													<label for="chkDelivery01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkDeliveryModule" id="chkDelivery02" checked>
													<label for="chkDelivery02">일반택배</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkDeliveryModule" id="chkDelivery03" checked>
													<label for="chkDelivery03">편의점픽업</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chkDeliveryModule" id="chkDelivery04" checked>
													<label for="chkDelivery04">매장픽업</label>
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
								<a href="#" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ol class="tbl-desc-list">
						<li>* 매장픽업 건과 배송지 기준 제주도 배송건은 선택 대상에서 제외됩니다.</li>
					</ol>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품대기조회 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select class="ui-sel" id="selTermsModule">
								<option>15개 보기</option>
							</select>
						</span>
						<span class="opt-group">
							<label class="title" for="selProdModule01">선택한 상품</label>
							<select class="ui-sel" id="selProdModule01">
								<option>선택</option>
								<option>온라인</option>
								<option>스마트</option>
								<option>오프라인매장</option>
							</select>
						</span>
						<span class="btn-group">
							<a href="#" class="btn-sm btn-func">일괄 재배송</a>
						</span>
					</div>
					<div class="fr">
						<a href="#" class="btn-sm btn-func">엑셀 선택 다운로드</a>
						<a href="#" class="btn-sm btn-func">엑셀 전체 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.prod.standby.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
