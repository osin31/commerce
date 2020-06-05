<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(function() {

		<%-- 환불 목록/검색 변수 선언 --%>
		abc.biz.claim.refundmain.MEMBER_TYPE_NONMEMBER	= "${CommonCode.MEMBER_TYPE_NONMEMBER}";
		abc.biz.claim.refundmain.CLM_GBN_CODE_CANCEL	= "${CommonCode.CLM_GBN_CODE_CANCEL}";
		abc.biz.claim.refundmain.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}";
		abc.biz.claim.refundmain.CLM_GBN_CODE_RETURN	= "${CommonCode.CLM_GBN_CODE_RETURN}";
		abc.biz.claim.refundmain.PYMNT_STAT_CODE_REFUND_COMPLETE = "${CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE}";
		
		abc.biz.claim.refundmain.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.claim.refundmain.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.claim.refundmain.SYSTEM_ADMIN_NO = "${Const.SYSTEM_ADMIN_NO}";
		
		<%-- 환불 목록 초기 세팅. --%>
		abc.biz.claim.refundmain.initRefundSheet();
		
		<%-- 주문 selectBox 선택 후 입력 --%>
		abc.biz.claim.refundmain.orderSelect();
		
		<%-- 기간검색 (환불금액 발생일시/처리일) 선택 시 --%>
		abc.biz.claim.refundmain.dateSelect();
		
		<%-- 처리불가/처리완료 선택 시 --%>
		abc.biz.claim.refundmain.procImpsblt();
		
		<%-- 기본값 : 오늘날짜 포함 한달 후 목록 검색 --%>
		$("a[name^=per1Month]").trigger('click');
		
		<%-- 목록갯수 selectBox로 search --%>
		$("#pageCount").change(function(){
			abc.biz.claim.refundmain.refundDoAction("search");
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
		
		<%-- 환불 목록 초기 호출 
		abc.biz.claim.refundmain.refundDoAction('search');--%>
		
		<%-- enter 검색--%>
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#btnSearch").trigger("click");
			}
		});
		
	});
	
	<%-- 그리드 Click 이벤트 --%>
	function refundSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		abc.biz.claim.refundmain.refundSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 그리드 조회 후 데이터 가공 --%>
	function refundSheet_OnSearchEnd(){
		
		abc.biz.claim.refundmain.refundSheetOnSearchEnd();
	}
	
	<%-- 데이터 조회 후 변형 --%>
 	function refundSheet_OnSaveEnd(Code, Msg, StCode, StMsg, Response){

 		abc.biz.claim.refundmain.refundSheetOnSaveEnd(Code, Msg, StCode, StMsg, Response);
 	}
	
 	<%-- 그리드 리스트 리로드(현재 페이지 기준) --%>
 	function reloadGridList() {
 		abc.biz.claim.refundmain._CPage = refundSheet.GetCurrentPage();
 		abc.biz.claim.refundmain.refundDoAction('search');
 		abc.biz.claim.refundmain._CPage = 1;
 	}
 	
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">환불 금액 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>클레임 관리</li>
						<li>환불 금액 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">환불 클레임건 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
			<!-- 환불/환수 구분값 : 환불 F -->
			<input type="hidden" name="redempRfndGbnType" value="F">
			
			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>환불 클레임건 검색</caption>
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
									<input type="text" class="ui-input"  id="inpOrderNo" name="orgOrderNo" title="주문번호 입력" maxlength="13">
								</td>
							</tr>
							<tr>
								<th scope="row">지급상태</th>
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
												<c:if test="${pymntStatCode.codeDtlNo eq CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT || pymntStatCode.codeDtlNo eq CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE}">
													<li>
														<span class="ui-rdo">
															<input type="radio" name="pymntStatCode" id="rdoPayment${pymntStatCode.codeDtlNo}" value="${pymntStatCode.codeDtlNo}">
															<label for="rdoPayment${pymntStatCode.codeDtlNo}">${pymntStatCode.addInfo1}</label>
														</span>
													</li>
												</c:if>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">처리불가여부</th>
								<td class="input">
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="procImpsbltYn" name="procImpsbltYn" type="checkbox" value="Y">
										<label for="procImpsbltYn">처리불가</label>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="2">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select class="ui-sel" name="dateGbnType" title="기간 선택" id="dateGbnSelect">
											<option value="R">환불금액 발생일시</option>
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
								<th scope="row">환불계좌등록여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="rfndAcntExistYn" id="rfndAcntExistAll" value="" checked>
												<label for="rfndAcntExistAll">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="rfndAcntExistYn" id="rfndAcntExistY" value="Y">
												<label for="rfndAcntExistY">예</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="rfndAcntExistYn" id="rfndAcntExistN" value="N">
												<label for="rfndAcntExistN">아니오</label>
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
				<h3 class="content-title">환불 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<form id="claimForm" name="claimForm" method="post" onsubmit="return false;">
			<!-- 환불/환수 구분값 : 환불 F -->
			<input type="hidden" name="redempRfndGbnType" value="F">
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<span class="opt-group">
						<label class="title" for="selProcImpsblt">선택 항목</label>
						<select class="ui-sel" id="selProcImpsblt">
							<option value="">선택</option>
							<option value="Y">처리불가</option>
							<option value="N">처리완료</option>
						</select>
						<input type="text" class="ui-input" id="procImpsbltRsnText" title="처리불가 사유 입력 (20자 이내)" style="width:200px;" placeholder="처리불가 사유 입력 ( 20자 이내 )" style="display:none;">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="btnProcImpsblt">일괄적용</a>
					</span>
				</div>
				<div class="fr">
					<a href="javascript:void(0);" class="btn-sm btn-func" id="downloadExcel">엑셀 선택 다운로드</a>
					<a href="javascript:void(0);" class="btn-sm btn-func" id="downloadAllExcel">엑셀 전체 다운로드</a>
				</div>
			</div>
			<!-- E : tbl-controller -->
	
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="refundGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</form>
		
		<form id="updateForm" name="updateForm" method="post" onsubmit="return false;">
		</form>
		
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/claim/abcmart.claim.refundmain.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>