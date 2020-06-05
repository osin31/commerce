<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp"%>

<body class="window-body">
	<div class="GridMain" id="IBSheetControls"
		style="left: 0px; top: 0px; width: 0px; height: 0px; visibility: visible;">
		<div class="GridMain1">
			<div class="GridMain2"></div>
		</div>
	</div>
	<div class="window-wrap">
		<div class="window-title">
			<h1>코너 콘텐츠 등록(인기상품)</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">인기상품정보 검색</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<form id="search-form">
				<input type="hidden" name="bestYn" value="Y" id="bestYn"/>
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>인기상품정보 검색 테이블이며 사이트 구분, 집계 기준, 상품코드, 브랜드, 테마,
								전시 채널 / 전시카테고리, 집계기간에 대해 검색합니다.</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px;">
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
													<input id="site-mall-ots" name="siteNo" checked="" type="radio" value="" <c:if test="${brand.siteNo eq '' }">checked</c:if>/>
													<label for="site-mall-ots">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="site-mall" name="siteNo" type="radio" value="10000" <c:if test="${brand.siteNo eq '10000' }">checked</c:if>/>
													<label for="site-mall">통합몰</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="site-ots" name="siteNo" type="radio" value="10001" <c:if test="${brand.siteNo eq '10001' }">checked</c:if>/>
													<label for="site-ots">ON THE SPOT</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">집계 기준</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li><span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="totalTypeC" name="totalType"
													type="radio" checked="" value="C"> <label for="totalTypeC">판매수량</label>
											</span></li>
											<li><span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="totalTypeP" name="totalType"
													type="radio" value="P"> <label for="totalTypeP">판매금액</label>
											</span></li>
										</ul> <!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">상품코드</th>
									<td class="input clear-float" rowspan="2">
										<!-- S : prod-code-box -->
										<div class="prod-code-box">
											<select class="ui-sel" title="상품코드 선택" name="prdtCodeType">
												<!-- S : 20190314 수정 // option 수정 -->
												<option value="prdt-code-erp" selected>상품코드</option>
												<option value="prdt-code-online">업체상품코드</option>
												<!-- E : 20190314 수정 // option 수정 -->
											</select>
	
											<!-- DESC : 20190228 수정 // 상품코드 입력 textarea 변경 -->
											<textarea class="ui-textarea" title="상품코드 입력" id="prdt-keyword" name="prdtCodeKeyword"></textarea>
										</div> <!-- E : prod-code-box -->
									</td>
									<td></td>
									<th scope="row">브랜드</th>
									<td class="input">
										<span class="ip-search-box">
											<input type="text" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" id="brand-name" name="brandName" value=""/>
											<input type="hidden" id="brand-no" name="brandNo" value=""/>
											<a href="javascript:void(0);" class="btn-search" data-button-popup="find-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
									</td>
								</tr>
								<tr>
									<td></td>
									<th scope="row">테마</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="gender-gbn-code" name="genderGbnCode"  type="radio" value="" checked/>
													<label for="gender-gbn-code">전체</label>
												</span>
											</li>
											<c:forEach var="item" items="${searchConditionGenderGbnCodeList }">
												<li>
													<span class="ui-rdo">
														<input id="gender-gbn-code-${item.codeDtlNo }" name="genderGbnCode"  type="radio" value="${item.codeDtlNo }"$/>
														<label for="gender-gbn-code-${item.codeDtlNo }">${item.codeDtlName }</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">전시 채널 <br>/ 전시카테고리
									</th>
									<td class="input" colspan="4">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select class="ui-sel dp-category-select chnnl-no" title="전시채널 선택" name="chnnlNo">
												<option value="">전시채널 전체</option>
												<c:forEach var="item" items="${searchConditionSiteChannelList }">
													<option value="${item.chnnlNo }">${item.chnnlName }</option>
												</c:forEach>
											</select>
											<select class="ui-sel dp-category-select 1depth" title="대카테고리 선택" data-category="display-1depth" name="stdrCtgrNo1">
												<option value="" data-init="display-1depth">대카테고리 전체</option>
											</select>
											<select class="ui-sel dp-category-select 2depth" title="중카테고리 선택" data-category="display-2depth" name="stdrCtgrNo2">
												<option value="" data-init="display-2depth">중카테고리 전체</option>
											</select>
											<select class="ui-sel dp-category-select 3depth" title="소카테고리 선택" name="ctgrNo" data-category="display-3depth" name="stdrCtgrNo3">
												<option value="" data-init="display-3depth">소카테고리 전체</option>
											</select>
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">집계기간</th>
									<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="periodStartDate"/>
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="periodEndDate"/>
									</span>
									<span class="btn-group">
										<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="today">오늘</a>
										<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="week">일주일</a>
										<a href="javascript:void(0);" class="btn-sm btn-func" data-button-period="month">한달</a>
										<!-- DESC : 20190220 수정 // 기간검색 전체 > 1년으로 공통수정 적용 -->
										<a href="javascript:void(0);" class="btn-sm btn-func text-center" data-button-period="year">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
								</tr>
							</tbody>
						</table>
	
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="reset"><i
									class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle">
						<span class="offscreen">닫기</span>
					</button>
				</div>
				<!-- E : search-wrap -->
			</form>
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">인기상품 목록</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group"> 
						<label class="title" for="selTermsModule2">목록개수</label> 
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
				<div class="fr">
					<button type="button" class="btn-sm btn-func" data-button="add-product">선택 상품 추가</button>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="cornerPopularSheet" style="width:100%; height:429px;" data-cont-type-code="${corner.cornerNameDispType}">
					ibsheet grid영역(div 삭제 필요)
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>


	<div id="ui-datepicker-div"
		class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>
	
	<script type="text/javascript">
		var codes = ${gridCodes };<%-- 판매상태 코드 --%>
	</script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.popular.pop.js<%=_DP_REV%>"></script>
</body>
</html>