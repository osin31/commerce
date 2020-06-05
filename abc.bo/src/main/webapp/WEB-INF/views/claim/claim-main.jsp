<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(function() {

		<%-- 클레임 목록/검색 변수 선언 --%>
		abc.biz.claim.claimmain.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.claimmain.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.claimmain.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.claim.claimmain.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.claim.claimmain.MEMBER_TYPE_NONMEMBER	= "<c:out value='${CommonCode.MEMBER_TYPE_NONMEMBER}'/>";
		
		abc.biz.claim.claimmain.CLM_GBN_CODE_CANCEL		= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.claim.claimmain.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.claim.claimmain.CLM_GBN_CODE_RETURN		= "${CommonCode.CLM_GBN_CODE_RETURN}";
		
		abc.biz.claim.claimmain.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT  = "${CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}";
		abc.biz.claim.claimmain.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER = "${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}";
		
		<%-- get parameter --%>
		abc.biz.claim.claimmain.getClmGbnCode = "${getClmGbnCode}";
		abc.biz.claim.claimmain.getMmnyPrdtYn = "${getMmnyPrdtYn}";
		
		<%-- 클레임 목록 초기 세팅. --%>
		abc.biz.claim.claimmain.initClaimSheet();
		
		<%-- 발송처 checkBox 체크 --%>
		abc.biz.claim.claimmain.sendPlaceCheck();
		
		<%-- 입점사 선택 --%>
		abc.biz.claim.claimmain.vendorSelect();
		
		<%-- 클레임/주문번호 선택 후, 입력 input --%>
		abc.biz.claim.claimmain.claimOrderNoInput();
		
		<%-- 주문 selectBox 선택 후, 입력 input --%>
		abc.biz.claim.claimmain.orderMemberInfoSelect();
		
		<%-- 상품 selectBox 선택 후, 입력 input --%>
		abc.biz.claim.claimmain.prdtInfoSelect();
		
		<%-- 브랜트 selectBox 선택 시 --%>
		abc.biz.claim.claimmain.brandSelect();
		
		<%-- 기간검색 선택 시 --%>
		abc.biz.claim.claimmain.dateSelect();
		
		<%-- 클레임별 진행 상태 selectBox 선택 시 --%>
		abc.biz.claim.claimmain.claimSelect();
		
		<%-- 결제수단 (환불) checkBox 선택 시 --%>
		abc.biz.claim.claimmain.pymntMeansCodeCheck();
		
		<%-- 기본값 : 오늘날짜 포함 한달 후 목록 검색 --%>
		$("a[name^=per1Month]").trigger('click');
		
		<%-- 데쉬보드 링크를 통해 클레임관리 접속 --%>
		abc.biz.claim.claimmain.fromDashBoard();
		
		<%-- 목록갯수 selectBox로 search --%>
		$("#pageCount").change(function(){
			abc.biz.claim.claimmain.claimDoAction("search");
		});
		
		<%-- 클레임 주문번호 번호만 입력제어 --%>
		$("#clmOrderNoInp").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 주문 검색어 선택 이벤트. --%>
		$("#orderMemberInfoSelect").change(function(){
			var searchType = $(this).val();
			abc.biz.claim.claimmain.ordMbInfoSelectChange(searchType);
		});
		
		<%-- 휴대폰 번호 뒷자리 입력 시 숫자만 입력되도록 설정 --%>
		$("#buyerHdphnBackNo").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- enter 검색--%>
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#btnSearch").trigger("click");
			}
		});
		
		<%-- 클레임 목록 초기 호출 
		abc.biz.claim.claimmain.claimDoAction('search');--%>
		
	});
	
	<%-- 그리드 조회 후 이벤트 --%>
	function claimSheet_OnSearchEnd(){
		
		abc.biz.claim.claimmain.claimSheetOnSearchEnd();
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function claimSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.claim.claimmain.claimSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 데이터 조회 후 변형 --%>
 	function claimSheet_OnSaveEnd(Code, Msg, StCode, StMsg, Response){

 		abc.biz.claim.claimmain.claimSheetOnSaveEnd(Code, Msg, StCode, StMsg, Response);
 	}
 	
 	<%-- 업체 선택 후 input에 업체명 set --%>
 	function setSelectedVendors(arg){
		$("#vndrName").val(arg.arrayVndrName);
		$("#vndrNo").val(arg.arrayVndrNo);
	}

 	<%-- 브랜드 선택 후 input에 브랜드명 set --%>
 	function setSelectedBrand(arg){
 		$("#brandNo").val(arg[0].brandNo);
		$("#brandName").val(arg[0].brandName);
 	}
 	
 	<%-- 그리드 리스트 리로드(현재 페이지 기준) --%>
 	function reloadGridList() {
 		abc.biz.claim.claimmain._CPage = claimSheet.GetCurrentPage();
 		abc.biz.claim.claimmain.claimDoAction('search');
 		abc.biz.claim.claimmain._CPage = 1;
 	}
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">클레임 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>클레임 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">클레임 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
			<!-- 에스크로 적용/미적용 여부 -->
			<input type="hidden" name="escrApplyYn" value="">
			<!-- 자사상품 포함여부  -->
			<input type="hidden" name="mmnyPrdtYn" value="">
			
			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>클레임 검색</caption>
						<colgroup>
							<col style="width: 110px;">
							<col>
							<col style="width: 79px">
							<col style="width: 110px;">
							<col>
						</colgroup>
						<tbody>
							<!-- S : 20190227 추가 // 접수처 추가 -->
							<tr>
								<th scope="row">접수처</th>
								<td class="input" colspan="4">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input type="radio" id="rdoTypeAll" name="oflnAcceptYn" value="" checked>
												<label for="rdoTypeAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input type="radio" id="rdoTypeOn" name="oflnAcceptYn" value="N">
												<label for="rdoTypeOn">온라인</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input type="radio" id="rdoTypeOff" name="oflnAcceptYn" value="Y">
												<label for="rdoTypeOff">오프라인</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- E : 20190227 추가 // 접수처 추가 -->
							
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
								<th scope="row">결제구분</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="deviceCode" id="rdoPaymentAll" value="" checked>
												<label for="rdoPaymentAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty DEVICE_CODE}">
											<c:forEach items="${DEVICE_CODE}" var="deviceCode">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="deviceCode" id="rdoPayment${deviceCode.codeDtlNo}" value="${deviceCode.codeDtlNo}" >
														<label for="rdoPayment${deviceCode.codeDtlNo}">${deviceCode.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">발송처</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<input id="stockGbnCodeAll" name="stockGbnCodeAll" type="checkbox" checked="checked">
												<label for="stockGbnCodeAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty STOCK_GBN_CODE}">
											<c:forEach items="${STOCK_GBN_CODE}" var="stockGbnCode">
												<li>
													<span class="ui-chk">
														<input id="stockGbnCode${stockGbnCode.codeDtlNo}" value="${stockGbnCode.codeDtlNo}" custom="stockGbnCode" name="stockGbnCodeList.stockGbnCode" type="checkbox" checked="checked">
														<label for="stockGbnCode${stockGbnCode.codeDtlNo}">${stockGbnCode.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">입점사</th>
								<td class="input">
									<span class="ip-search-box">
										<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName">
										<!--  <input type="hidden" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly>   -->
										<a href="javascript:void(0);" class="btn-search" id="searchVendor"><i class="ico-search"><span class="offscreen" >검색</span></i></a>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">클레임·주문번호</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="claimOrderNoSelect"title="유형 선택">
											<option value="clmNo">클레임번호</option>
											<option value="orgOrderNo">주문번호</option>
										</select>
										<input type="text" class="ui-input" name="clmNo" id="clmOrderNoInp" value="" title="검색어 입력" maxlength="13">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
								<td></td>
								<th scope="row">상품</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="prdtInfoSelect" title="택배사 선택">
											<option value="prdtNo">상품코드</option>
											<option value="prdtName" selected="selected">상품명</option>
											<option value="styleInfo">스타일코드</option>
											<option value="vndrPrdtNoText">업체상품코드</option>
										</select>
										<input type="text" class="ui-input" name="prdtName" id="prdtInfoInp" title="검색어 입력">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">주문</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" id="orderMemberInfoSelect" title="검색어 타입 선택">
											<option value="buyerName">주문자명</option>
											<option value="rcvrName">수령자</option>
											<option value="buyerHdphnNoText">휴대폰 번호</option>
											<option value="loginId">주문자ID</option>
										</select>
										<!-- DESC : 휴대폰 번호 선택시 placeholder="-없이 전체 입력" 속성 추가 해주세요 -->
										<input type="text" class="ui-input" name="searchBuyerName" id="orderMemberInfoInp" title="검색어 입력">
										<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
								<td></td>
								<th scope="row">브랜드</th>
								<td class="input">
									<span class="ip-search-box">
										<!-- <input type="hidden" id="brandNo" name="brandNo" value="">  -->
										<input type="text" class="ui-input" id="brandName" name="brandName" value="" title="검색어 입력">
										<a href="javascript:void(0)" class="btn-search" id="btnBrand" data-button-popup="find-brand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select class="ui-sel" name="dateGbnType" id="Type" title="기간 선택">
											<option value="O">주문일</option>
											<option value="C">접수일시</option>
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
							<tr>
								<th scope="row">클레임별진행상태</th>
								<td class="input" colspan="4">
									<!-- DESC : 클레임 유형에 따라 checkbox 항목 변경 -->
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<select class="ui-sel" id="clmPrcsStat" title="클레임 유형 선택">
												<option value="">전체</option>
												<option value="E">교환</option>
												<option value="R">반품</option>
												<option value="C">취소</option>
											</select>
										</li>
										<!-- S : 클레임별진행상태 > 교환선택시 option -->
										<ul class="ip-box-list"  id="checkExchange" style="display:none;">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="E_ClmAll" custum="clmStatCodeE" name="chkClaimModule" type="checkbox">
													<label for="E_ClmAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty CLM_STAT_CODE}">
												<c:forEach items="${CLM_STAT_CODE}" var="clmStatCode">
													<c:if test="${clmStatCode.addInfo1 eq CommonCode.CLM_GBN_CODE_EXCHANGE}">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkClaim${clmStatCode.codeDtlNo}" custum="clmStatCodeE" custum2="cmmClmStatCode" name="clmStatCodeList.clmStatCode" value="${clmStatCode.codeDtlNo}" type="checkbox">
																<label for="chkClaim${clmStatCode.codeDtlNo}">${clmStatCode.codeDtlName}</label>
															</span>
														</li>
													</c:if>
												</c:forEach>
											</c:if>
										</ul>
										<!-- E : 클레임별진행상태 > 교환선택시 option -->
	
										<!-- S : 클레임별진행상태 > 반품선택시 option -->
										<ul class="ip-box-list" id="checkReturn" style="display:none;">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="R_ClmAll" custum="clmStatCodeR" name="chkClaimModule" type="checkbox">
													<label for="R_ClmAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty CLM_STAT_CODE}">
												<c:forEach items="${CLM_STAT_CODE}" var="clmStatCode">
													<c:if test="${clmStatCode.addInfo1 eq CommonCode.CLM_GBN_CODE_RETURN}">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkClaim${clmStatCode.codeDtlNo}" custum="clmStatCodeR" custum2="cmmClmStatCode" name="clmStatCodeList.clmStatCode" value="${clmStatCode.codeDtlNo}" type="checkbox">
																<label for="chkClaim${clmStatCode.codeDtlNo}">${clmStatCode.codeDtlName}</label>
															</span>
														</li>
													</c:if>
												</c:forEach>
											</c:if>
										</ul>
										<!-- E : 클레임별진행상태 > 반품선택시 option -->
	
										<!-- S : 클레임별진행상태 > 취소선택시 option -->
										<ul class="ip-box-list" id="checkCancel" style="display:none;">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="C_ClmAll" custum="clmStatCodeC" name="chkClaimModule" type="checkbox">
													<label for="C_ClmAll">전체</label>
												</span>
											</li>
											<c:if test="${!empty CLM_STAT_CODE}">
												<c:forEach items="${CLM_STAT_CODE}" var="clmStatCode">
													<c:if test="${clmStatCode.addInfo1 eq CommonCode.CLM_GBN_CODE_CANCEL}">
														<li>
															<span class="ui-chk">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input id="chkClaim${clmStatCode.codeDtlNo}" custum="clmStatCodeC" custum2="cmmClmStatCode" name="clmStatCodeList.clmStatCode" value="${clmStatCode.codeDtlNo}" type="checkbox">
																<label for="chkClaim${clmStatCode.codeDtlNo}">${clmStatCode.codeDtlName}</label>
															</span>
														</li>
													</c:if>
												</c:forEach>
											</c:if>
										</ul>
										<!-- E : 클레임별진행상태 > 취소선택시 option -->
										<span class="ui-chk">
											<input id="chkClaim" name="unProcYn" value="Y" type="checkbox">
											<label for="chkClaim">미처리여부</label>
										</span>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">결제수단(환불)</th>
								<td class="input" colspan="4">
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkPaymentAll" name="chkPaymentAll" type="checkbox" checked="checked">
												<label for="chkPaymentAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty PYMNT_MEANS_CODE}">
											<c:forEach items="${PYMNT_MEANS_CODE}" var="paymentCode">											
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkPayment${paymentCode.codeDtlNo}" value="${paymentCode.codeDtlNo}" custom="pymntMeansCode" name="pymntMeansCodeList.pymntMeansCode" type="checkbox" checked="checked">
														<label for="chkPayment${paymentCode.codeDtlNo}">${paymentCode.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
										<li>
											<span class="text" id="leftESCR">에스크로 적용 (</span>
											<span class="ui-chk" style="margin-right:10px;">
												<input id="chkPaymentESCR" name="chkPaymentESCR" type="checkbox" custom="escrChk" checked="checked" value="Y">
												<label for="chkPaymentESCR">예</label>
											</span>
											<span class="ui-chk">
												<input id="chkPaymentNoESCR" name="chkPaymentNoESCR" type="checkbox" custom="escrChk" checked="checked" value="N">
												<label for="chkPaymentNoESCR">아니요</label>
											</span>
											<span class="text"id="rightESCR">)</span>
										</li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0)" class="btn-sm btn-func" id="btnReset"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0)" class="btn-normal btn-func" id="btnSearch">검색</a>
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
				<h3 class="content-title">클레임 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<form id="claimFrom" name="claimFrom" method="post" onsubmit="return false;">
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
					<a href="javascript:void(0)" class="btn-sm btn-func" id="downloadExcel">엑셀 선택 다운로드</a>
					<a href="javascript:void(0)" class="btn-sm btn-func" id="downloadAllExcel">엑셀 전체 다운로드</a>
				</div>
			</div>
			<!-- E : tbl-controller -->
	
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="claimGrid"style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</form>
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/claim/abcmart.claim.claimmain.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>