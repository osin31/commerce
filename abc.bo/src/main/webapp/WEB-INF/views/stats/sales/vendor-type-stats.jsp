<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function(){
		abc.biz.stats.sales.commonSet();
		abc.biz.stats.sales.initVendorTypeSalesStatsSheet();
	});
	
	function vendorTypeSalesStatsSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		if ( Row != 0) {
			
			if(vendorTypeSalesStatsSheet.ColSaveName(Col) == 'vndrNo'){
				var url = '/stats/sales/vendor-type-stats/detail-pop';
				_params = {};
				_params.menuNo = abc.param.getParams().menuNo;
				_params.vndrNo = vendorTypeSalesStatsSheet.GetRowData(Row).vndrNo;
				
				//팝업에 쓰일 입점사명, 코드 가져옴.
				abc.biz.stats.sales.vendorSrchVal.srchDataSet.vndrNo = _params.vndrNo;
				abc.biz.stats.sales.vendorSrchVal.srchDataSet.vndrName = vendorTypeSalesStatsSheet.GetRowData(Row).vndrName;
				if(Row > 2) {
					abc.open.popup({
						url 	:	url,
						winname :	'vendor-type-stats-pop',
						width 	:	1250,
						height	:	950,
						params : _params
					});
				}
			}
		}
	};
</script>

<!-- S : container -->
		<div class="container">
			<form name="form" id="form">
				<input type="hidden" name="typeStats" value="vendorType">
				<input type="hidden" name="isAdmin" value="${isAdmin}">
				<div class="content-box">
					<!-- S : page-header -->
					<div class="page-header">
						<div class="fl">
							<h2 class="page-title">입점사 매출 통계</h2>
							<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
							<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
						</div>
						<div class="fr">
							<div class="navi-wrap">
								<ul class="navi">
									<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
									<li>매출/정산/통계</li>
									<li>매출통계 </li>
									<li>입점사 매출 통계</li>
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
									<col style="width: 79px;">
									<col style="width: 130px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">기간</th>
										<td class="input" colspan="4">
											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
	
												<!-- s : 일별 선택시 -->
												<span class="date-box" id="dayFromDateSpan">
													<input type="text" data-role="datepicker" name="dayFromDate" class="ui-cal js-ui-cal"  title="시작일 선택" maxlength="10">
												</span>
												<span class="text" id="dayWaveMark">~</span>
												<span class="date-box" id="dayToDateSpan">
													<input type="text" data-role="datepicker" name="dayToDate" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
												</span>
												<span class="btn-group">
													<a href="javascript:void(0);" class="btn-sm btn-func" id="toYesterday">어제</a>
													<a href="javascript:void(0);" class="btn-sm btn-func" id="toWeek">일주일</a>
													<a href="javascript:void(0);" class="btn-sm btn-func" id="toMonth">한달</a>
												</span>
												<!-- e : 일별 선택시 -->
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
									<tr>
										<th scope="row">사이트 구분</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="siteNoAll" value="" checked>
														<label for="siteNoAll">전체</label>
													</span>
												</li>
												<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="siteNo" id="radioSite${status.count}" value="${siteInfo.siteNo}">
															<label for="radioSite${status.count}"><c:out value="${siteInfo.siteName}"/></label>
														</span>
													</li>
												</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">입점사</th>
										<td class="input">
											<span class="ip-search-box">
												<c:choose>
													<c:when test="${isAdmin eq false}">
														<input type="text" name="vndrName" class="ui-input" id="vndrName" title="검색어 입력" value="${vndrName}" disabled/>
														<input type="hidden" name="vndrNo" id="vndrNo" value="${vndrNo}"/>
													</c:when>
													<c:otherwise>
														<input type="text" name="vndrName" class="ui-input" id="vndrName" title="검색어 입력" readonly/>
														<input type="hidden" name="vndrNo" id="vndrNo"/>
														<a href="javascript:void(0);" class="btn-search" id="searchVndrPop"><i class="ico-search"><span class="offscreen">검색</span></i></a>
													</c:otherwise>
												</c:choose>
											</span>
										</td>
									</tr>
									<tr>
										<th scope="row">결제수단</th>
										<td class="input" colspan="4">
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkPaymentAll" name="chkPaymentAll" type="checkbox" value="" checked="checked">
														<label for="chkPaymentAll">전체</label>
													</span>
												</li>
												<c:if test="${!empty codeList.PYMNT_MEANS_CODE}">
													<c:forEach items="${codeList.PYMNT_MEANS_CODE}" var="paymentCode">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkPayment${paymentCode.codeDtlNo}" value="${paymentCode.codeDtlNo}" custom="pymntMeansCode" name="pymntMeansCode" type="checkbox" checked="checked">
																<label for="chkPayment${paymentCode.codeDtlNo}">${paymentCode.codeDtlName}</label>
															</span>
														</li>
													</c:forEach>
												</c:if>
											</ul>
	
											<!-- S : member-grade-list -->
											<div class="member-grade-list">
												<span class="text">( 에스크로 적용</span>
	
												<!-- S : ip-box-list -->
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkEscroTrue" name="chkEscroTrue" custom="escrApplyYn" value="Y" type="checkbox" checked>
															<label for="chkEscroTrue">예</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkEscroFalse" name="chkEscroFalse" custom="escrApplyYn" value="N" type="checkbox" checked>
															<label for="chkEscroFalse">아니오</label>
														</span>
													</li>
												</ul>
												<!-- E : ip-box-list -->
	
												<span class="text">)</span>
											</div>
											<!-- E : member-grade-list -->
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
	
					<!-- S : tbl-desc-wrap -->
					<div class="tbl-desc-wrap border-box">
						<ul class="tbl-desc-list">
							<li>* 매출 통계 자료는 매출발생 시점 기준(결제완료 기준) 조회 통계이며, 주문취소 및 구매확정 후 교환/반품에 따라 정보가 상이할 수 있습니다.</li>
							<li>* 매출 통계 자료는 당일 발생 매출 데이터는 제외됩니다.</li>
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
							<span class="opt-group">
								<label class="title" for="pageCount">목록개수</label>
								<select class="ui-sel" id="pageCount">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
						</div>
						<div class="fr">
							<a href="javascript:void(O);" class="btn-sm btn-func disabled" id="downExcel">엑셀 다운로드</a>
						</div>
					</div>
	
					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="vendorTypeSaleStatsGrid" style="width:100%; height:429px;"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
			</form>
		</div>
		<!-- E : container -->


<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.order.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/stats/abcmart.stats.sales.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
