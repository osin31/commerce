<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function() {
		abc.biz.sales.salesmain.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.sales.salesmain.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
	
		abc.biz.sales.salesmain.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.sales.salesmain.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.sales.salesmain.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.sales.salesmain.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.sales.salesmain.fromDash = "${fromDash}";
		
		<%-- 자사 매출 관리  초기 세팅. --%>
		abc.biz.sales.salesmain.initafterserviceSheet();
		
		<%-- 자사 매출 관리 input 박스  --%>
		abc.biz.sales.salesmain.asOrderNoSelect();
		
	
		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#prdtInfoSelect").trigger("change");
		
		<%-- 조건 검색시 주문번호 이벤트 발생 숫자만 입력됨. --%>
		$("#asOrderNoSelect").trigger("change");
		
		<%-- 기본 한달  --%>
		$("a[name^=per1Month]").trigger("click");
		
		<%-- dash 보드로 부터 접속 시  --%>
		abc.biz.sales.salesmain.fromDashboard();
		
		<%-- 이벤트 실행 --%>
		abc.biz.sales.salesmain.event();
	});


<%-- 그리드 체크박스 전체선택 체크 후 이벤트 --%>
function salesSheet_OnCheckAllEnd(Col, Value){
	
	if(Value == 0){
		$("#frmDownload").empty();
		
	}else if(Value == 1){
		var indexArray = salesSheet.FindCheckedRow(1).split('|');
		
		for(var i=0; i<indexArray.length; i++){
			var hiddenInput = "<input type='hidden' name='rowSeq' value='" + indexArray[i] + "'>";
			$("#frmDownload").append(hiddenInput);
		}
	}
}
</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">자사 매출 연동 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites">
							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>매출/정산/통계</li>
								<li>자사 매출 정산 관리</li>
								<li>자사 매출 연동 관리</li>
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
				<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
					<input type='hidden' id='panGbn' name='panGbn' value=""/>
					<input type='hidden' id='errorStatusYn' name='errorStatusYn' value=""/>
					<input type='hidden' id='salesCnclGbnType' name='salesCnclGbnType' value=""/>
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
													<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" >
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" >
												</span>
												<span class="btn-group">
													<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
													<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
													<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
													<a href="javascript:void(0);"   name="per1Year"  class="btn-sm btn-func">1년</a>
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</td>
									</tr>
									<tr>
										<th scope="row">판매구분</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkdeviceTypeAll" name="chkdeviceTypeAll" type="checkbox" value="all" checked>
														<label for="chkdeviceTypeAll">전체</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input name="deviceTypeCodeArr" type="checkbox"  id="chkdeviceType01" value="order"  checked/>
														<label for="chkdeviceType01">매출</label>
													</span>
												</li>
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input name="deviceTypeCodeArr" type="checkbox"  id="chkdeviceType02" value="return"  checked/>
														<label for="chkdeviceType02">취소</label>
													</span>
												</li>
											</ul>
											<!-- E : ip-box-list -->
										</td>
										<td></td>
										<th scope="row">판매채널</th>
										<td class="input">
											<!-- S : ip-box-list -->
											<ul class="ip-box-list">
												<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="siteNo" id="rdoSiteAll" value="" checked>
															<label for="rdoSiteAll">전체</label>
														</span>
													</li>
													<c:forEach items="${siteList}" var="sySite" varStatus="status">
														<li>
															<span class="ui-rdo">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input type="radio" name="siteNo" id="rdoSite${sySite.siteNo}" value="${sySite.siteNo}">
																<label for="rdoSite${sySite.siteNo}">${sySite.siteName}</label>
															</span>
														</li>
													</c:forEach>
											</ul>
											<!-- E : ip-box-list -->
										</td>
									</tr>
									<tr>
										<th scope="row">검색어</th>
										<td class="input">
											<!-- S : opt-keyword-box -->
											<div class="opt-keyword-box">
												<select class="ui-sel" id="asOrderNoSelect" title="검색어 타입 선택">
													<option value="srcmkcd">ERP상품풀번호</option>
													<option value="ordno">주문번호</option>
												</select>
												<input type="text" class="ui-input" name="srcmkcd" id="asOrderNoInp" value="" title="검색어 입력">
											</div>
											<!-- E : opt-keyword-box -->
										</td>
									</tr>
								</tbody>
							</table>
							<div class="confirm-box">
								<div class="fl">
									<a href="javascript:abc.biz.sales.salesmain.searchFormReset()" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
								</div>
								<div class="fr">
									<a href="javascript:abc.biz.sales.salesmain.salesDoAction('search')" class="btn-normal btn-func">검색</a>
								</div>
							</div>
						</div>
						<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
					</div>
					<!-- E : search-wrap -->
				</form>
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">매출 내역</h3>
					</div>
				</div>
				<!-- E : content-header -->
				
				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>매출 내역</caption>
					<colgroup>
						<col>
						<col>
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">매출액</th>
							<th scope="col">에누리</th>
							<th scope="col">구매포인트 사용액</th>
							<th scope="col">이벤트포인트 사용액</th>
						</tr> 
					</thead>
					<tbody>
						<tr>
							<td class="text-right" id="saleAmtSum">-</td>
							<td class="text-right" id="saleEnurySum">-</td>
							<td class="text-right" id="pointAmtSum">-</td>
							<td class="text-right" id="eventPointAmtSum">-</td>
						</tr>
					</tbody>
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
							<label class="title" for="selTermsModule">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<a href="javascript:abc.biz.sales.salesmain.downloadAllExcel();" class="btn-sm btn-func" id="downExcel">엑셀 다운로드</a>
					</div>
				</div>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="salesGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
				<!-- 다운로드할 클레임 목록의 Form -->
				<form id="frmDownload" name="frmDownload" method="post" onsubmit="return false;">
				</form>
			</div>
		</div>
		<!-- E : container -->
		
<script src="/static/common/js/biz/sales/abcmart.sales.salesmain.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>