<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(function() {

		<%-- 환수 목록/검색 변수 선언 --%>
		abc.biz.claim.redemptionmain.MEMBER_TYPE_NONMEMBER	= "${CommonCode.MEMBER_TYPE_NONMEMBER}";
		abc.biz.claim.redemptionmain.CLM_GBN_CODE_CANCEL	= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.claim.redemptionmain.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.claim.redemptionmain.CLM_GBN_CODE_RETURN	= "${CommonCode.CLM_GBN_CODE_RETURN}";
		abc.biz.claim.redemptionmain.PYMNT_STAT_CODE_REDEMP_ACCEPT = "${CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT}";
		abc.biz.claim.redemptionmain.PYMNT_STAT_CODE_REDEMP_COMPLETE = "${CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE}";
		
		<%-- 환수 목록 초기 세팅. --%>
		abc.biz.claim.redemptionmain.initRedemptionSheet();
		
		<%-- 주문 selectBox 선택 후 입력 --%>
		abc.biz.claim.redemptionmain.orderSelect();
		
		<%-- 기간검색 (환수금액 발생일시/처리일) 선택 시 --%>
		abc.biz.claim.redemptionmain.dateSelect();
		
		<%-- 기본값 : 오늘날짜 포함 한달 후 목록 검색 --%>
		$("a[name^=per1Month]").trigger('click');
		
		<%-- 목록갯수 selectBox로 search --%>
		$("#pageCount").change(function(){
			abc.biz.claim.redemptionmain.redemptionDoAction("search");
		});
		
		<%-- 주문번호 번호만 입력제어 --%>
		$("#inpOrderNo").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 주문 selectBox 휴대폰번호 선택 시 번호만 입력제어 --%>
		$("#orderInp").keyup(function(){
			if( $("#orderSelect").val() == "buyerHdphnNoText" ){
				abc.text.validateOnlyNumber(this);
			}
		});
		
		<%-- 환수 목록 초기 호출 
		abc.biz.claim.redemptionmain.redemptionDoAction('search');--%>
		
		<%-- enter 검색--%>
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#btnSearch").trigger("click");
			}
		});
		
	});

	<%-- 그리드 조회 후 이벤트 --%>
	function redemptionSheet_OnSearchEnd(){
		
		abc.biz.claim.redemptionmain.redemptionSheetOnSearchEnd();
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function redemptionSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.claim.redemptionmain.redemptionSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 데이터 조회 후 변형 --%>
 	function redemptionSheet_OnSaveEnd(Code, Msg, StCode, StMsg, Response){

 		abc.biz.claim.redemptionmain.redemptionSheetOnSaveEnd(Code, Msg, StCode, StMsg, Response);
 	}
	
 	<%-- 그리드 리스트 리로드(현재 페이지 기준) --%>
 	function reloadGridList() {
 		abc.biz.claim.redemptionmain._CPage = redemptionSheet.GetCurrentPage();
 		abc.biz.claim.redemptionmain.redemptionDoAction('search');
 		abc.biz.claim.redemptionmain._CPage = 1;
 	}
 	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">환수 금액 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>환수 금액 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">환수 클레임건 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
			<!-- 환불/환수 구분값 : 환수 E -->
			<input type="hidden" name="redempRfndGbnType" value="E">
			
			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>환수 클레임건 검색</caption>
						<colgroup>
							<col style="width: 110px;">
							<col>
							<col style="width: 79px">
							<col style="width: 110px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사이트</th>
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
										<c:forEach items="${SITE_TYPE}" var="sySite" varStatus="status">
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
								<td></td>
								<th scope="row">클레임구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="clmGbnCode" id="rdoClaimAll" value="" checked>
												<label for="rdoClaimAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty CLM_GBN_CODE}">
											<c:forEach items="${CLM_GBN_CODE}" var="clmGbnCode">
												<c:if test="${clmGbnCode.codeDtlNo eq CommonCode.CLM_GBN_CODE_CANCEL || clmGbnCode.codeDtlNo eq CommonCode.CLM_GBN_CODE_EXCHANGE || clmGbnCode.codeDtlNo eq CommonCode.CLM_GBN_CODE_RETURN}">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="clmGbnCode" id="rdoClaim${clmGbnCode.codeDtlNo}" value="${clmGbnCode.codeDtlNo}">
															<label for="rdoClaim${clmGbnCode.codeDtlNo}">${clmGbnCode.codeDtlName}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">주문</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="orderSelect" title="유형 선택">
											<option value="buyerName">주문자명</option>
											<option value="loginId">주문자 아이디</option>
											<option value="buyerHdphnNoText">휴대폰번호</option>
										</select>
										<input type="text" class="ui-input" name="searchBuyerName" id="orderInp" value="" title="검색어 입력">
										<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
								<td></td>
								<th scope="row">주문번호</th>
								<td class="input">
									<input type="text" class="ui-input" id="inpOrderNo" name="orgOrderNo" title="주문번호 입력" maxlength="13">
								</td>
							</tr>
							<tr>
								<th scope="row">수급상태</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="pymntStatCode" id="rdoPaymentAll" value="" checked>
												<label for="rdoPaymentAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty PYMNT_STAT_CODE}">
											<c:forEach items="${PYMNT_STAT_CODE}" var="pymntStatCode">
												<c:if test="${pymntStatCode.codeDtlNo eq CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT || pymntStatCode.codeDtlNo eq CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE}">
													<li>
														<span class="ui-rdo">
															<input type="radio" name="pymntStatCode" id="rdoPayment${pymntStatCode.codeDtlNo}" value="${pymntStatCode.codeDtlNo}">
															<label for="rdoPayment${pymntStatCode.codeDtlNo}">${pymntStatCode.addInfo1}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="pymntStatCode" id="vrtlAcntExprYn" value="Y">
													<label for="vrtlAcntExprYn">입금기한만료</label>
												</span>
											</li>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">임의처리여부</th>
								<td class="input">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="redempAmtRndmProcYn" id="rndmProcAll" value="" checked>
												<label for="rndmProcAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="redempAmtRndmProcYn" id="rndmProcY" value="Y">
												<label for="rndmProcY">임의처리</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="redempAmtRndmProcYn" id="rndmProcN" value="N">
												<label for="rndmProcN">임의처리안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select class="ui-sel" name="dateGbnType" title="기간 선택" id="dateGbnSelect">
											<option value="R">환수금액 발생일시</option>
											<option value="M">처리일시</option>
										</select>
										<span class="date-box">
											<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group">
											<a href="javascript:void(0);"   name="perToday"  class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);"   name="perWeek"   class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);"   name="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);"   name="perYear"   class="btn-sm btn-func">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" class="btn-sm btn-func" id="btnReset"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-normal btn-func" id="btnSearch">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->
			<input type="hidden" name="sortColumn" id="sortColumn" value="">
			<input type="hidden" name="sortType" id="sortType" value="">
		</form>

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">환수 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<form id="claimForm" name="claimForm" method="post" onsubmit="return false;">
			<!-- 환불/환수 구분값 : 환수 E -->
			<input type="hidden" name="redempRfndGbnType" value="E">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<%--
					<span class="opt-group">
						<label class="title" for="procImpsbltRsnText">선택 항목 처리불가</label>
						<input type="text" class="ui-input" id="procImpsbltRsnText" title="처리불가 사유 입력" placeholder="처리불가 사유 입력">
						<a href="javascript:void(0)" class="btn-sm btn-func" id="btnProcImpsblt">일괄적용</a>
					</span>
					 --%>
				</div>
				<div class="fr">
							<a href="javascript:void(0);" class="btn-sm btn-func" id="downloadExcel">엑셀 선택 다운로드</a>
					<a href="javascript:void(0);" class="btn-sm btn-func" id="downloadAllExcel">엑셀 전체 다운로드</a>
				</div>
			</div>
			<!-- E : tbl-controller -->
	
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="redemptionGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</form>
		
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/claim/abcmart.claim.redemptionmain.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>